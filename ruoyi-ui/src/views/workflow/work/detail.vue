<template>
  <div class="app-container">
    <el-tabs tab-position="top">
      <el-tab-pane label="任务办理" v-if="finished === 'true'">
        <el-card class="box-card" shadow="never">
          <el-row>
            <el-col :span="20" :offset="2">
              <el-form ref="taskForm" :model="taskForm" :rules="rules" label-width="80px">
                <el-form-item label="审批意见" prop="comment">
                  <el-input type="textarea" :rows="5" v-model="taskForm.comment" placeholder="请输入 审批意见" />
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
              <el-button icon="el-icon-circle-close" type="danger" @click="handleReject">驳回</el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-tab-pane>
      <el-tab-pane label="表单信息">
        <el-card class="box-card" shadow="never">
          <!--流程处理表单模块-->
          <el-col :span="20" :offset="2" v-if="variableOpen">
            <div>
              <parser :key="new Date().getTime()" :form-conf="variablesData"/>
            </div>
          </el-col>

          <!--初始化流程加载表单信息-->
          <el-col :span="16" :offset="4" v-if="formConfOpen">
            <div class="form-conf">
              <parser :key="new Date().getTime()" :form-conf="formConf" @submit="submitForm" ref="parser" @getData="getData"/>
            </div>
          </el-col>
        </el-card>
      </el-tab-pane >

      <el-tab-pane label="流转记录">
        <el-card class="box-card" shadow="never">
          <el-col :span="18" :offset="3">
            <div class="block">
              <el-timeline>
                <el-timeline-item v-for="(item,index) in flowRecordList" :key="index" :icon="setIcon(item.finishTime)" :color="setColor(item.finishTime)">
                  <p style="font-weight: 700">{{ item.taskName }}</p>
                  <el-card class="box-card" shadow="hover">
                    <el-descriptions column="5" :labelStyle="{'font-weight': 'bold'}">
                      <el-descriptions-item label="实际办理">{{ item.assigneeName || '-'}}</el-descriptions-item>
                      <el-descriptions-item label="候选办理">{{ item.candidate || '-'}}</el-descriptions-item>
                      <el-descriptions-item label="接收时间">{{ item.createTime || '-'}}</el-descriptions-item>
                      <el-descriptions-item label="办结时间">{{ item.finishTime || '-' }}</el-descriptions-item>
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
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-col>
        </el-card>
      </el-tab-pane>

      <el-tab-pane label="流程跟踪">
        <el-card class="box-card" shadow="never">
          <process-viewer :key="`designer-${loadIndex}`" :style="'height:' + height" :xml="xmlData"
                          :finishedInfo="finishedInfo" :allCommentList="null"
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

    <el-dialog :title="userDialogTitle" :visible.sync="userOpen" width="60%" append-to-body>
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
          <el-table ref="userTable" height="500" :data="userList" highlight-current-row @current-change="changeCurrentUser">
            <el-table-column width="30">
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
        <el-button @click="userOpen = false">取 消</el-button>
        <el-button type="primary" v-if="userDialogTitle === '委派任务'" @click="submitDelegate">确 定</el-button>
        <el-button type="primary" v-if="userDialogTitle === '转办任务'" @click="submitTransfer">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getDetailInstance } from '@/api/workflow/instance'
import Parser from '@/utils/generator/parser'
import { definitionStart, getFlowViewer, getProcessVariables, readXml } from '@/api/workflow/definition'
import { complete, delegate, transfer,getNextFlowNode, rejectTask, returnList, returnTask } from '@/api/workflow/todo'
import { treeselect } from '@/api/system/dept'
import ProcessViewer from '@/components/ProcessViewer'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Treeselect from '@riophae/vue-treeselect'
import { listUser } from '@/api/system/user'

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
      taskList: [],
      // 部门名称
      deptName: undefined,
      // 部门树选项
      deptOptions: undefined,
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
      flowRecordList: [],
      formConfCopy: {},
      variablesForm: {},
      taskForm:{
        returnTaskShow: false,
        delegateTaskOpen: false,
        defaultTaskShow: true,
        sendUserShow: false,
        multiple: false,
        comment:"", // 意见内容
        procInsId: "", // 流程实例编号
        instanceId: "", // 流程实例编号
        deployId: "",  // 流程定义编号
        taskId: "" ,// 流程任务编号
        definitionId: "",  // 流程编号
        vars: "",
        targetKey:""
      },
      rules: {
        comment: [{ required: true, message: '请输入审批意见', trigger: 'blur' }],
      },
      currentUserId: null,
      userDataList:[], // 流程候选人
      assignee: null,
      formConf: {}, // 默认表单数据
      formConfOpen: false, // 是否加载默认表单数据
      variables: [], // 流程变量数据
      variablesData: {}, // 流程变量数据
      variableOpen: false, // 是否加载流程变量数据
      returnTaskList: [],  // 回退列表数据
      finished: 'false',
      returnTitle: null,
      returnOpen: false,
      rejectOpen: false,
      rejectTitle: null,
      userDialogTitle: '',
      userOpen: false,
      userData:[],
    };
  },
  created() {
    this.taskForm.deployId = this.$route.query && this.$route.query.deployId;
    this.taskForm.definitionId = this.$route.query && this.$route.query.definitionId;
    this.taskForm.taskId  = this.$route.query && this.$route.query.taskId;
    this.taskForm.procInsId = this.$route.query && this.$route.query.procInsId;
    this.taskForm.instanceId = this.$route.query && this.$route.query.procInsId;
    this.finished =  this.$route.query && this.$route.query.finished
    // 流程任务重获取变量表单
    if (this.taskForm.taskId) {
      this.processVariables( this.taskForm.taskId)
      // this.getNextFlowNode(this.taskForm.taskId)
      this.taskForm.deployId = null
    }
    this.getFlowRecordList( this.taskForm.procInsId, this.taskForm.deployId);
    Promise.all([this.getFlowViewer(this.taskForm.procInsId), this.getModelDetail(this.taskForm.definitionId)]).then(() => {
      this.loadIndex = this.taskForm.procInsId;
    });
  },
  methods: {
    /** 查询部门下拉树结构 */
    getTreeSelect() {
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
      this.userData = selection
      const val = selection.map(item => item.userId)[0];
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
    // 关闭标签
    handleClose(tag) {
      this.userData.splice(this.userData.indexOf(tag), 1);
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
    /** 流程流转记录 */
    getFlowRecordList(procInsId, deployId) {
      const params = {procInsId: procInsId, deployId: deployId}
      getDetailInstance(params).then(res => {
        this.flowRecordList = res.data.flowList;
        console.log("res flowList => ", this.flowRecordList)
        // 流程过程中不存在初始化表单 直接读取的流程变量中存储的表单值
        if (res.data.formData) {
          this.formConf = res.data.formData;
          this.formConfOpen = true
        }
      }).catch(res => {
        this.goBack();
      })
    },
    fillFormData(form, data) {
      form.fields.forEach(item => {
        const val = data[item.__vModel__]
        if (val) {
          item.__config__.defaultValue = val
        }
      })
    },
    /** 获取流程变量内容 */
    processVariables(taskId) {
      if (taskId) {
        // 提交流程申请时填写的表单存入了流程变量中后续任务处理时需要展示
        getProcessVariables(taskId).then(res => {
          this.variablesData = res.data.variables;
          this.variableOpen = true
        });
      }
    },
    /** 根据当前任务或者流程设计配置的下一步节点 */
    getNextFlowNode(taskId) {
      // 根据当前任务或者流程设计配置的下一步节点 todo 暂时未涉及到考虑网关、表达式和多节点情况
      const params = {taskId: taskId}
      getNextFlowNode(params).then(res => {
        const data = res.data;
        if (data) {
          if (data.type === 'assignee') {
            this.userDataList = res.data.userList;
          } else if (data.type === 'candidateUsers') {
            this.userDataList = res.data.userList;
            this.taskForm.multiple = true;
          } else if (data.type === 'candidateGroups') {
            res.data.roleList.forEach(role => {
              role.userId = role.roleId;
              role.nickName = role.roleName;
            })
            this.userDataList = res.data.roleList;
            this.taskForm.multiple = false;
          } else if (data.type === 'multiInstance') {
            this.userDataList = res.data.userList;
            this.taskForm.multiple = true;
          }
          this.taskForm.sendUserShow = true;
        }
      })
    },
    /** 通过任务 */
    handleComplete() {
      this.$refs['taskForm'].validate(valid => {
          if (valid) {
            complete(this.taskForm).then(response => {
              this.$modal.msgSuccess(response.msg);
              this.goBack();
            });
          }
      });
    },
    /** 委派任务 */
    handleDelegate() {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          this.userDialogTitle = '委派任务'
          this.userOpen = true;
          this.getTreeSelect();
        }
      })
    },
    /** 转办任务 */
    handleTransfer(){
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          this.userDialogTitle = '转办任务'
          this.userOpen = true;
          this.getTreeSelect();
        }
      })
    },
    /** 驳回任务 */
    handleReject() {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          rejectTask(this.taskForm).then(res => {
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
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.go(-1)
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
    submitDelegate() {
      if (!this.taskForm.comment) {
        this.$modal.msgError("请输入审批意见");
        return false;
      }
      if (!this.currentUserId) {
        this.$modal.msgError("请选择委派用户");
        return false;
      }
      this.taskForm.userId = this.currentUserId;
      delegate(this.taskForm).then(res => {
        this.$modal.msgSuccess(res.msg);
        this.goBack();
      });
    },
    submitTransfer() {
      if (!this.taskForm.comment) {
        this.$modal.msgError("请输入审批意见");
        return false;
      }
      if (!this.currentUserId) {
        this.$modal.msgError("请选择受理用户");
        return false;
      }
      this.taskForm.userId = this.currentUserId;
      transfer(this.taskForm).then(res => {
        this.$modal.msgSuccess(res.msg);
        this.goBack();
      });
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
.form-conf {
  margin: 15px auto;
  width: 800px;
  padding: 15px;
}

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
</style>
