<template>
  <div style="margin-top: 16px">
    <el-form-item label="候选类型">
<!--      <el-select v-model="formData.groupType" placeholder="请选择分组类型" @change="onGroupTypeChange">-->
      <el-select v-model="formData.groupType" placeholder="请选择分组类型">
        <el-option label="固定用户" value="ASSIGNEE" />
<!--        <el-option label="候选用户" value="USERS" />-->
<!--        <el-option label="候选组" value="ROLE" />-->
      </el-select>
    </el-form-item>
    <el-form-item label="指定方式" v-if="formData.groupType === 'ASSIGNEE'">
      <el-radio v-model="formData.assignType" label="1">固定</el-radio>
      <el-radio v-model="formData.assignType" label="2">动态</el-radio>
    </el-form-item>
    <el-form-item label="处理用户" v-if="formData.groupType === 'ASSIGNEE'">
      <tag-select v-if="formData.assignType === '1'" v-model="userTaskForm.assignee">
        <el-button slot="append" class="append-add" type="default" icon="el-icon-plus" @click="onSelectAssignee()" />
      </tag-select>
      <el-select v-if="formData.assignType === '2'" v-model="userTaskForm.assignee" collapse-tags @change="updateElementTask('assignee')">
        <el-option v-for="item in variableData" :key="item.value" :label="item.label" :value="item.value">
          <span style="float: left">{{ item.label }}</span>
          <span style="float: right; color: #8492a6;">{{ item.value }}</span>
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="候选用户" v-if="formData.groupType === 'USERS'">
      <tag-select v-model="userTaskForm.candidateUsers">
        <el-button slot="append" class="append-add" type="default" icon="el-icon-plus" @click="onSelectAssignee()" />
      </tag-select>
    </el-form-item>
<!--    <el-form-item label="候选分组">-->
<!--      <el-select v-model="userTaskForm.candidateGroups" multiple collapse-tags @change="updateElementTask('candidateGroups')">-->
<!--        <el-option v-for="gk in mockData" :key="'ass-' + gk" :label="`分组${gk}`" :value="`group${gk}`" />-->
<!--      </el-select>-->
<!--    </el-form-item>-->
    <el-form-item label="到期时间">
      <el-input v-model="userTaskForm.dueDate" clearable @change="updateElementTask('dueDate')" />
    </el-form-item>
    <el-form-item label="跟踪时间">
      <el-input v-model="userTaskForm.followUpDate" clearable @change="updateElementTask('followUpDate')" />
    </el-form-item>
    <el-form-item label="优先级">
      <el-input v-model="userTaskForm.priority" clearable @change="updateElementTask('priority')" />
    </el-form-item>

    <!-- 候选用户弹窗 -->
    <el-dialog title="候选用户" :visible.sync="candidateVisible" width="60%" append-to-body>
      <el-row type="flex" :gutter="20">
        <!--部门数据-->
        <el-col :span="5">
          <el-card shadow="never" style="height: 100%">
            <div slot="header">
              <span>部门列表</span>
            </div>
            <div class="head-container">
              <el-input
                v-model="deptName"
                placeholder="请输入部门名称"
                clearable
                size="small"
                prefix-icon="el-icon-search"
                style="margin-bottom: 20px"
              />
              <el-tree
                :data="deptOptions"
                :props="deptProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="tree"
                default-expand-all
                @node-click="handleNodeClick"
              />
            </div>
          </el-card>
        </el-col>
        <el-col :span="14">
          <el-table
            ref="singleTable"
            height="600"
            :data="userList"
            border
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="用户名" align="center" prop="nickName" />
            <el-table-column label="部门" align="center" prop="dept.deptName" />
          </el-table>
          <pagination
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>
        <el-col :span="5">
          <el-card shadow="never" style="height: 100%">
            <div slot="header">
              <span>已选人员</span>
            </div>
            <el-tag
              v-for="tag in selectedUserDate"
              :key="tag.nickName"
              closable
              @close="handleClose(tag)">
              {{tag.nickName}} {{tag.dept.deptName}}
            </el-tag>
          </el-card>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleTaskComplete">确 定</el-button>
<!--          <el-input style="width: 50%;margin-right: 34%" type="textarea" v-model="taskForm.comment"-->
<!--                    placeholder="请输入处理意见"-->
<!--          />-->
<!--          <el-button @click="completeOpen = false">取 消</el-button>-->
        </div>
    </el-dialog>
  </div>

</template>

<script>
import { listUser, getUser } from "@/api/system/user";
import { treeselect } from '@/api/system/dept'
import TagSelect from "./TagSelect";

export default {
  name: "UserTask",
  props: {
    id: String,
    type: String
  },
  components: {
    TagSelect
  },
  data() {
    return {
      defaultTaskForm: {
        assignee: "",
        candidateUsers: [],
        candidateGroups: [],
        dueDate: "",
        followUpDate: "",
        priority: ""
      },
      formData: {
        groupType: 'ASSIGNEE',
        assignType: '1'
      },
      userTaskForm: {},
      candidateVisible: false,
      deptName: undefined,
      deptOptions: [],
      deptProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        deptId: undefined
      },
      userList: [],
      total: 0,
      selectedUserDate: [],
      userMockDate: [],
      variableData: [{
        label: "流程发起人",
        value: "${INITIATOR}"
      }],
      mockData: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    };
  },
  watch: {
    id: {
      immediate: true,
      handler() {
        this.bpmnElement = window.bpmnInstances.bpmnElement;
        this.$nextTick(() => this.resetTaskForm());
      }
    },
    'userTaskForm.assignee': {
      handler () {
        this.updateElementTask('assignee');
      }
    },
    'userTaskForm.candidateUsers': {
      handler () {
        this.updateElementTask('candidateUsers');
      }
    },
  },
  created() {
    listUser().then(response => {
      this.userMockDate = response.rows;
    })
  },
  methods: {
    resetTaskForm() {
      for (let key in this.defaultTaskForm) {
        if (key === "candidateUsers" || key === "candidateGroups") {
          let val = this.bpmnElement?.businessObject[key] ? this.bpmnElement.businessObject[key].split(",") : [];
          // TODO 2022/01/10 添加候选组的设值 this.$set(this.userTaskForm, key, value);
        } else if (key === "assignee") {
          let val = this.bpmnElement?.businessObject[key] || this.defaultTaskForm[key];
          // 判断是否为动态用户
          if (val && val.startsWith('${') && val.endsWith('}')) {
            this.formData.assignType = '2';
            this.$set(this.userTaskForm, key, val);
          } else {
            this.formData.assignType = '1';
            getUser(val).then(response => {
              let user = response.data.user
              this.$set(this.userTaskForm, key, user);
            })
          }
        }
      }
    },
    updateElementTask(key) {
      const taskAttr = Object.create(null);
      if (key === "candidateUsers" || key === "candidateGroups") {
        if (this.userTaskForm[key] && this.userTaskForm[key].length > 0) {
          // TODO 2022/01/10 添加候选组的设值
          // taskAttr[key] = this.userTaskForm[key]
        }
        // taskAttr[key] = this.userTaskForm[key] && this.userTaskForm[key].length ? this.userTaskForm[key].join() : null;
      } else {
        if (this.userTaskForm[key]) {
          if (this.formData.assignType === '1') {
            taskAttr[key] = this.userTaskForm[key].userId || null;
          } else if (this.formData.assignType === '2') {
            taskAttr[key] = this.userTaskForm[key] || null;
          }
        }
      }
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, taskAttr);
    },
    /**
     * 查询部门下拉树结构
     */
    getDeptTreeSelect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询用户列表 */
    getList() {
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
        }
      );
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // 关闭标签
    handleClose(tag) {
      this.selectedUserDate.splice(this.selectedUserDate.indexOf(tag), 1);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.selectedUserDate = selection;
    },
    handleTaskComplete() {
      if (!this.selectedUserDate) {
        this.userTaskForm.assignee = null;
        this.userTaskForm.candidateUsers = null;
      } else {
        let val = null;
        if (this.formData.groupType === 'ASSIGNEE') {
          val = this.selectedUserDate[0];
          this.userTaskForm.assignee = val;
          // this.updateElementTask('assignee')
        } else {
          val = this.selectedUserDate;
          this.userTaskForm.candidateUsers = val;
          // this.updateElementTask('candidateUsers')
        }

      }
      this.candidateVisible = false;
    },
    onSelectAssignee() {
      this.getDeptTreeSelect()
      this.candidateVisible = true;
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>

<style scoped>
</style>
