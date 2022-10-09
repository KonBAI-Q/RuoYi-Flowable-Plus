<template>
  <div class="app-container">
    <el-tabs tab-position="top" :value="finished === 'true' ? 'approval' : 'form'">

      <el-tab-pane label="任务办理" name="approval" v-if="finished === 'true'">
        <el-card class="box-card" shadow="hover" v-if="taskFormOpen">
          <div slot="header" class="clearfix">
            <span>填写表单</span>
          </div>
          <el-col :span="20" :offset="2">
            <parser :form-conf="taskFormData" ref="taskFormParser"/>
          </el-col>
        </el-card>
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>审批流程</span>
          </div>
          <el-row>
            <el-col :span="20" :offset="2">
              <el-form ref="taskForm" :model="taskForm" :rules="rules" label-width="80px">
                <el-form-item label="审批意见" prop="comment">
                  <el-input type="textarea" :rows="5" v-model="taskForm.comment" placeholder="请输入 审批意见" />
                </el-form-item>
                <el-form-item label="抄送人" prop="copyUserIds">
                  <el-tag
                    :key="index"
                    v-for="(item, index) in userData.copyUser"
                    closable
                    :disable-transitions="false"
                    @close="handleClose(item)">
                    {{ item.label }}
                  </el-tag>
                  <el-button class="button-new-tag" type="primary" icon="el-icon-plus" size="mini" circle @click="onSelectUsers" />
                </el-form-item>
              </el-form>
            </el-col>
          </el-row>
          <el-row :gutter="10" type="flex" justify="center" >
            <el-col :span="1.5">
              <el-button icon="el-icon-circle-check" type="success" @click="handleComplete">通过</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="el-icon-chat-line-square" type="primary" @click="handleDelegate">委派</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="el-icon-thumb" type="success" @click="handleTransfer">转办</el-button>
            </el-col>
<!--            <el-col :span="2">-->
<!--              <el-button  icon="el-icon-edit-outline" type="primary"" @click="handle">签收</el-button>-->
<!--            </el-col>-->
            <el-col :span="1.5">
              <el-button icon="el-icon-refresh-left" type="warning" @click="handleReturn">退回</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="el-icon-circle-close" type="danger" @click="handleReject">拒绝</el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="表单信息" name="form">
        <div v-if="formOpen">
          <el-card class="box-card" shadow="never" v-for="formInfo in processFormList">
            <div slot="header" class="clearfix">
              <span>{{ formInfo.title }}</span>
            </div>
            <!--流程处理表单模块-->
            <el-col :span="20" :offset="2">
              <parser :form-conf="formInfo"/>
            </el-col>
          </el-card>
        </div>
      </el-tab-pane >

      <el-tab-pane label="流转记录" name="record">
        <el-card class="box-card" shadow="never">
          <el-col :span="20" :offset="2">
            <div class="block">
              <el-timeline>
                <el-timeline-item v-for="(item,index) in historyProcNodeList" :key="index" :icon="setIcon(item.endTime)" :color="setColor(item.endTime)">
                  <p style="font-weight: 700">{{ item.activityName }}</p>
                  <el-card v-if="item.activityType === 'startEvent'" class="box-card" shadow="hover">
                    {{ item.assigneeName }} 在 {{ item.createTime }} 发起流程
                  </el-card>
                  <el-card v-if="item.activityType === 'userTask'" class="box-card" shadow="hover">
                    <el-descriptions :column="5" :labelStyle="{'font-weight': 'bold'}">
                      <el-descriptions-item label="实际办理">{{ item.assigneeName || '-'}}</el-descriptions-item>
                      <el-descriptions-item label="候选办理">{{ item.candidate || '-'}}</el-descriptions-item>
                      <el-descriptions-item label="接收时间">{{ item.createTime || '-'}}</el-descriptions-item>
                      <el-descriptions-item label="办结时间">{{ item.endTime || '-' }}</el-descriptions-item>
                      <el-descriptions-item label="耗时">{{ item.duration || '-'}}</el-descriptions-item>
                    </el-descriptions>
                    <div v-if="item.commentList && item.commentList.length > 0" v-for="comment in item.commentList">
                      <el-divider content-position="left">
                        <el-tag :type="approveTypeTag(comment.type)" size="mini">{{ commentType(comment.type) }}</el-tag>
                        <el-tag type="info" effect="plain" size="mini">{{ comment.time }}</el-tag>
                      </el-divider>
                      <span>{{ comment.fullMessage }}</span>
                    </div>
                  </el-card>
                  <el-card v-if="item.activityType === 'endEvent'" class="box-card" shadow="hover">
                    {{ item.createTime }} 结束流程
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-col>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="流程跟踪" name="track">
        <el-card class="box-card" shadow="never">
          <process-viewer :key="`designer-${loadIndex}`" :style="'height:' + height" :xml="xmlData"
                          :finishedInfo="finishedInfo" :allCommentList="historyProcNodeList"
          />
        </el-card>
      </el-tab-pane>
    </el-tabs>

    <!--退回流程-->
    <el-dialog :title="returnTitle" :visible.sync="returnOpen" width="40%" append-to-body>
      <el-form ref="taskForm" :model="taskForm" label-width="80px" >
        <el-form-item label="退回节点" prop="targetKey">
          <el-radio-group v-model="taskForm.targetKey">
            <el-radio-button
              v-for="item in returnTaskList"
              :key="item.id"
              :label="item.id"
            >{{item.name}}</el-radio-button>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="returnOpen = false">取 消</el-button>
        <el-button type="primary" @click="submitReturn">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog :title="userData.title" :visible.sync="userData.open" width="60%" append-to-body>
      <el-row type="flex" :gutter="20">
        <!--部门数据-->
        <el-col :span="5">
          <el-card shadow="never" style="height: 100%">
            <div slot="header">
              <span>部门列表</span>
            </div>
            <div class="head-container">
              <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search"/>
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
        <el-col :span="18">
          <el-table ref="userTable"
                    :key="userData.type"
                    height="500"
                    v-loading="userLoading"
                    :data="userList"
                    highlight-current-row
                    @current-change="changeCurrentUser"
                    @selection-change="handleSelectionChange">
            <el-table-column v-if="userData.type === 'copy'" width="55" type="selection" />
            <el-table-column v-else width="30">
              <template slot-scope="scope">
                <el-radio :label="scope.row.userId" v-model="currentUserId">{{''}}</el-radio>
              </template>
            </el-table-column>
            <el-table-column label="用户名" align="center" prop="nickName" />
            <el-table-column label="手机" align="center" prop="phonenumber" />
            <el-table-column label="部门" align="center" prop="dept.deptName" />
          </el-table>
          <pagination
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="userData.open = false">取 消</el-button>
        <el-button type="primary" @click="submitUserData">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { detailProcess } from '@/api/workflow/process'
import Parser from '@/utils/generator/parser'
import { definitionStart, getFlowViewer, readXml } from '@/api/workflow/definition'
import { complete, delegate, transfer, getNextFlowNode, rejectTask, returnList, returnTask } from '@/api/workflow/todo'
import { selectUser, deptTreeSelect } from '@/api/system/user'
import ProcessViewer from '@/components/ProcessViewer'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Treeselect from '@riophae/vue-treeselect'
import { forceLogout } from '@/api/monitor/online';

export default {
  name: "Detail",
  components: {
    ProcessViewer,
    Parser,
    Treeselect
  },
  props: {},
  computed: {
    commentType() {
      return val => {
        switch (val) {
          case '1': return '通过'
          case '2': return '退回'
          case '3': return '驳回'
          case '4': return '委派'
          case '5': return '转办'
        }
      }
    },
    approveTypeTag() {
      return val => {
        switch (val) {
          case '1': return 'success'
          case '2': return 'warning'
          case '3': return 'danger'
          case '4': return 'primary'
          case '5': return 'success'
        }
      }
    }
  },
  data() {
    return {
      height: document.documentElement.clientHeight - 205 + 'px;',
      // 模型xml数据
      loadIndex: 0,
      xmlData: undefined,
      finishedInfo: {
        finishedSequenceFlowSet: [],
        finishedTaskSet: [],
        unfinishedTaskSet: [],
        rejectedTaskSet: []
      },
      historyProcNodeList: [],
      // 部门名称
      deptName: undefined,
      // 部门树选项
      deptOptions: undefined,
      userLoading: false,
      // 用户表格数据
      userList: null,
      deptProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        deptId: undefined
      },
      total: 0,
      // 遮罩层
      loading: true,
      taskForm:{
        comment:"", // 意见内容
        procInsId: "", // 流程实例编号
        deployId: "",  // 流程定义编号
        taskId: "" ,// 流程任务编号
        definitionId: "",  // 流程编号
        copyUserIds: "", // 抄送人Id
        vars: "",
        targetKey:""
      },
      rules: {
        comment: [{ required: true, message: '请输入审批意见', trigger: 'blur' }],
      },
      currentUserId: null,
      variables: [], // 流程变量数据
      taskFormOpen: false,
      taskFormData: {}, // 流程变量数据
      processFormList: [], // 流程变量数据
      formOpen: false, // 是否加载流程变量数据
      returnTaskList: [],  // 回退列表数据
      finished: 'false',
      returnTitle: null,
      returnOpen: false,
      rejectOpen: false,
      rejectTitle: null,
      userData: {
        title: '',
        type: 'copy',
        open: false,
        currentUserId: null,
        copyUser: [],
      },
      userMultipleSelection: [],
      userDialogTitle: '',
      userOpen: false
    };
  },
  activated() {
    this.taskForm.deployId = this.$route.query && this.$route.query.deployId;
    this.taskForm.definitionId = this.$route.query && this.$route.query.definitionId;
    this.taskForm.taskId  = this.$route.query && this.$route.query.taskId;
    this.taskForm.procInsId = this.$route.query && this.$route.query.procInsId;
    this.finished =  this.$route.query && this.$route.query.finished
    // 流程任务重获取变量表单
    if (this.taskForm.taskId) {
      this.getProcessDetails(this.taskForm.procInsId, this.taskForm.deployId, this.taskForm.taskId);
    }
    Promise.all([this.getFlowViewer(this.taskForm.procInsId), this.getModelDetail(this.taskForm.definitionId)]).then(() => {
      this.loadIndex = this.taskForm.procInsId;
    });
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeSelect() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询用户列表 */
    getList() {
      this.userLoading = true;
      selectUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.toggleSelection(this.userMultipleSelection);
        this.userLoading = false;
      });
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
    /** xml 文件 */
    getModelDetail(definitionId) {
      return new Promise(resolve => {
        // 发送请求，获取xml
        readXml(definitionId).then(res => {
          this.xmlData = res.data
          resolve()
        })
      })
    },
    getFlowViewer(procInsId) {
      return new Promise(resolve => {
        getFlowViewer(procInsId).then(res => {
          let data = res.data;
          if (data) {
            this.finishedInfo.finishedTaskSet = data.finishedTaskSet;
            this.finishedInfo.unfinishedTaskSet = data.unfinishedTaskSet;
            this.finishedInfo.rejectedTaskSet = data.rejectedTaskSet;
            this.finishedInfo.finishedSequenceFlowSet = data.finishedSequenceFlowSet;
          }
          resolve()
        })
      })
    },
    setIcon(val) {
      if (val) {
        return "el-icon-check";
      } else {
        return "el-icon-time";
      }
    },
    setColor(val) {
      if (val) {
        return "#2bc418";
      } else {
        return "#b3bdbb";
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userMultipleSelection = selection
    },
    toggleSelection(selection) {
      if (selection && selection.length > 0) {
        this.$nextTick(()=> {
          selection.forEach(item => {
            let row = this.userList.find(k => k.userId === item.userId);
            this.$refs.userTable.toggleRowSelection(row);
          })
        })
      } else {
        this.$nextTick(() => {
          this.$refs.userTable.clearSelection();
        });
      }
    },
    // 关闭标签
    handleClose(tag) {
      let userObj = this.userMultipleSelection.find(item => item.userId === tag.id);
      this.userMultipleSelection.splice(this.userMultipleSelection.indexOf(userObj), 1);
      this.userData.copyUser.splice(this.userData.copyUser.indexOf(tag), 1);
      // 设置抄送人ID
      if (this.userData.copyUser && this.userData.copyUser.length > 0) {
        const val = this.userData.copyUser.map(item => item.id);
        this.taskForm.copyUserIds = val instanceof Array ? val.join(',') : val;
      } else {
        this.taskForm.copyUserIds = '';
      }
    },
    /** 流程变量赋值 */
    handleCheckChange(val) {
      if (val instanceof Array) {
        this.taskForm.values = {
          "approval": val.join(',')
        }
      } else {
        this.taskForm.values = {
          "approval": val
        }
      }
    },
    getProcessDetails(procInsId, deployId, taskId) {
      const params = {procInsId: procInsId, deployId: deployId, taskId: taskId}
      detailProcess(params).then(res => {
        const data = res.data;
        this.processFormList = data.processFormList;
        this.taskFormOpen = data.existTaskForm;
        if (this.taskFormOpen) {
          this.taskFormData = data.taskFormData;
        }
        this.historyProcNodeList = data.historyProcNodeList;
        this.formOpen = true
      })
    },
    onSelectUsers() {
      this.userData.title = '添加抄送人';
      this.userData.type = 'copy';
      this.getTreeSelect();
      this.getList()
      this.userData.open = true;
      this.$nextTick(() => {
        this.$refs.userTable.clearSelection();
      });
    },
    /** 通过任务 */
    handleComplete() {
      // 校验表单
      const taskFormRef = this.$refs.taskFormParser;
      const isExistTaskForm = taskFormRef !== undefined;
      // 若无任务表单，则 taskFormPromise 为 true，即不需要校验
      const taskFormPromise = !isExistTaskForm ? true : new Promise((resolve, reject) => {
        taskFormRef.$refs[taskFormRef.formConfCopy.formRef].validate(valid => {
          valid ? resolve() : reject()
        })
      });
      const approvalPromise = new Promise((resolve, reject) => {
        this.$refs['taskForm'].validate(valid => {
          valid ? resolve() : reject()
        })
      });
      Promise.all([taskFormPromise, approvalPromise]).then(() => {
        if (isExistTaskForm) {
          this.taskForm.variables = taskFormRef[taskFormRef.formConfCopy.formModel]
        }
        complete(this.taskForm).then(response => {
          this.$modal.msgSuccess(response.msg);
          this.goBack();
        });
      })
    },
    /** 委派任务 */
    handleDelegate() {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          this.userData.type = 'delegate';
          this.userData.title = '委派任务'
          this.userData.open = true;
          this.getTreeSelect();
        }
      })
    },
    /** 转办任务 */
    handleTransfer(){
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          this.userData.type = 'transfer';
          this.userData.title = '转办任务';
          this.userData.open = true;
          this.getTreeSelect();
        }
      })
    },
    /** 拒绝任务 */
    handleReject() {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          const _this = this;
          this.$modal.confirm('拒绝审批单流程会终止，是否继续？').then(function() {
            return rejectTask(_this.taskForm);
          }).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.goBack();
          });
        }
      });
    },
    changeCurrentUser(val) {
      this.currentUserId = val.userId
    },
    /** 返回页面 */
    goBack() {
      // 关闭当前标签页并返回上个页面
      this.$tab.closePage(this.$route)
      this.$router.back()
    },
    /** 接收子组件传的值 */
    getData(data) {
      if (data) {
        const variables = [];
        data.fields.forEach(item => {
          let variableData = {};
          variableData.label = item.__config__.label
          // 表单值为多个选项时
          if (item.__config__.defaultValue instanceof Array) {
            const array = [];
            item.__config__.defaultValue.forEach(val => {
              array.push(val)
            })
            variableData.val = array;
          } else {
            variableData.val = item.__config__.defaultValue
          }
          variables.push(variableData)
        })
        this.variables = variables;
      }
    },
    /** 申请流程表单数据提交 */
    submitForm(data) {
      if (data) {
        const variables = data.valData;
        const formData = data.formData;
        formData.disabled = true;
        formData.formBtns = false;
        if (this.taskForm.definitionId) {
          variables.variables = formData;
          // 启动流程并将表单数据加入流程变量
          definitionStart(this.taskForm.definitionId, JSON.stringify(variables)).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.goBack();
          })
        }
      }
    },
    submitUserData() {
      let type = this.userData.type;
      if (type === 'copy') {
        if (!this.userMultipleSelection || this.userMultipleSelection.length <= 0) {
          this.$modal.msgError("请选择用户");
          return false;
        }
        this.userData.copyUser = this.userMultipleSelection.map(k => {
          return { id: k.userId, label: k.nickName }
        })
        // 设置抄送人ID
        if (this.userData.copyUser && this.userData.copyUser.length > 0) {
          const val = this.userData.copyUser.map(item => item.id);
          this.taskForm.copyUserIds = val instanceof Array ? val.join(',') : val;
        } else {
          this.taskForm.copyUserIds = '';
        }
        this.userData.open = false;
      } else {
        if (!this.taskForm.comment) {
          this.$modal.msgError("请输入审批意见");
          return false;
        }
        if (!this.currentUserId) {
          this.$modal.msgError("请选择用户");
          return false;
        }
        this.taskForm.userId = this.currentUserId;
        if (type === 'delegate') {
          delegate(this.taskForm).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.goBack();
          });
        }
        if (type === 'transfer') {
          transfer(this.taskForm).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.goBack();
          });
        }
      }

    },
    /** 可退回任务列表 */
    handleReturn() {
      this.$refs['taskForm'].validate(valid => {
        if (valid) {
          this.returnTitle = "退回流程";
          returnList(this.taskForm).then(res => {
            this.returnTaskList = res.data;
            this.taskForm.values = null;
            this.returnOpen = true;
          })
        }
      });

    },
    /** 提交退回任务 */
    submitReturn() {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          if (!this.taskForm.targetKey) {
            this.$modal.msgError("请选择退回节点！");
          }
          returnTask(this.taskForm).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.goBack()
          });
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
  margin-bottom: 20px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}

.button-new-tag {
  margin-left: 10px;
}
</style>
