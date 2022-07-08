<template>
  <div class="workflow-designer">
    <el-container>
      <el-header height="70px">
        <el-row type="flex" align="middle">
          <el-col :span="3">
            <div style="display:flex;justify-content:center;">
              <el-button type="danger" @click="onClose()">关闭</el-button>
              <el-button type="primary" @click="onPrevious()" :disabled="activeStep <= 0">上一步</el-button>
            </div>
          </el-col>
          <el-col :offset="1" :span="16">
            <el-steps :active="activeStep" finish-status="success" align-center>
              <el-step title="基础设置"></el-step>
              <el-step title="流程设计"></el-step>
              <el-step title="完成"></el-step>
            </el-steps>
          </el-col>
          <el-col :offset="1" :span="3">
            <div style="display:flex;justify-content:center;">
              <el-button type="primary" @click="onNext()" :disabled="activeStep >= 1">下一步</el-button>
              <el-button type="success" @click="onSave()" :disabled="activeStep !== 1">保存</el-button>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <div v-if="activeStep === 0">
          <div class="app-container">
            <el-row :gutter="20">
              <el-col :span="10">
                <el-divider content-position="left">
                  <span style="font-size: 18px">基础信息</span>
                </el-divider>
                <el-form :model="designerForm" ref="designerForm" size="small" label-width="80px" :rules="rules">
                  <el-form-item label="模型标识" prop="processKey">
                    <el-input v-model="designerForm.processKey" clearable disabled />
                  </el-form-item>
                  <el-form-item label="模型名称" prop="processName">
                    <el-input v-model="designerForm.processName" clearable />
                  </el-form-item>
                  <el-form-item label="分类" prop="category">
                    <el-select v-model="designerForm.category" placeholder="请选择分类" clearable style="width:100%">
                      <el-option v-for="item in categoryOptions" :key="item.categoryId" :label="item.categoryName" :value="item.code" />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="描述" prop="description">
                    <el-input v-model="designerForm.description" type="textarea" placeholder="请输入内容" maxlength="200" show-word-limit/>
                  </el-form-item>
                  <el-form-item label="表单类型" prop="formType">
                    <el-radio-group v-model="designerForm.formType">
                      <el-radio :label="0">内置表单</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="表单" prop="formId">
                    <el-select v-model="designerForm.formId" placeholder="请选择表单" @change="handleFormChange(designerForm.formId)" clearable style="width:100%">
                      <el-option v-for="item in formOptions" :key="item.formId" :label="item.formName" :value="item.formId" />
                    </el-select>
                  </el-form-item>
                </el-form>
              </el-col>
              <el-col :span="13" :offset="1">
                <el-divider content-position="left">
                  <span style="font-size: 18px">表单预览</span>
                </el-divider>
                <parser v-if="formShow" :form-conf="formContent" />
                <el-empty v-else description="暂无数据" />
              </el-col>
            </el-row>
          </div>
        </div>
        <div v-if="activeStep === 1">
          <process-designer
            ref="modelDesigner"
            v-loading="loading"
            :bpmnXml="bpmnXml"
            :designerForm="designerForm"
          />
        </div>
        <div v-if="activeStep === 2">
          <el-result :icon="result.icon" :title="result.title" :subTitle="result.describe">
            <template slot="extra">
              <el-button type="primary" size="medium" @click="onClose()">关闭</el-button>
            </template>
          </el-result>
        </div>

      </el-main>
      <el-footer>
      </el-footer>
    </el-container>
  </div>

</template>

<script>
import { getModel, getBpmnXml, saveModel } from "@/api/workflow/model";
import { getForm, listForm } from "@/api/workflow/form";
import ProcessDesigner from '@/components/ProcessDesigner';
import Parser from '@/utils/generator/parser'
import { listAllCategory } from '@/api/workflow/category';

export default {
  name: 'Designer',
  components: { ProcessDesigner, Parser },
  props: {
    modelId: {
      type: String
    },
    processName: {
      type: String
    },
    processKey: {
      type: String
    }
  },
  data () {
    return {
      activeStep: 0,
      designerForm: {},
      rules: {
        modelKey: [
          { required: true, message: '请输入模型标识', trigger: 'blur' }
        ],
        modelName: [
          { required: true, message: '请输入模型名称', trigger: 'blur' }
        ],
        category: [
          { required: true, message: '请选择分类', trigger: 'change' }
        ],
        formType: [
          { required: true, message: '请选择表单类型', trigger: 'change' }
        ],
        formId: [
          { required: true, message: '请选择表单', trigger: 'change' }
        ]
      },
      categoryOptions: [],
      formOptions: [],
      formShow: false,
      formContent: {},
      loadIndex: 0,
      loading: false,
      bpmnXml: '',
      result: {
        icon: 'info',
        title: null,
        describe: null
      }
    };
  },
  created() {
    this.onLoad();
  },
  methods: {
    onClose () {
      this.$emit('close');
    },
    onPrevious() {
      this.activeStep--;
    },
    onNext() {
      this.activeStep++;
    },
    /**
     * 初始化加载
     */
    onLoad() {
      this.getCategoryList();
      this.getFormList();
      if (this.modelId) {
        getModel(this.modelId).then(response => {
          const data = response.data
          this.designerForm = {
            processName: data.modelName,
            processKey: data.modelKey,
            category: data.category,
            description: data.description,
            formType: data.formType,
            formId: data.formId
          }
          if (data.bpmnXml) {
            this.bpmnXml = data.bpmnXml
          }
          if (data.content) {
            this.formContent = JSON.parse(data.content)
            this.formShow = true
          }
        })
      } else {
        this.designerForm = {
          processName: this.processName,
          processKey: this.processKey,
          formType: 0
        }
      }
    },
    /** 查询流程分类列表 */
    getCategoryList() {
      listAllCategory().then(response => this.categoryOptions = response.data)
    },
    /** 查询流程分类列表 */
    getFormList() {
      listForm().then(response => this.formOptions = response.rows)
    },
    handleFormChange(formId) {
      this.formShow = false
      if (formId) {
        getForm(formId).then(res =>{
          this.formContent = JSON.parse(res.data.content)
          this.formShow = true
        })
      }
    },
    onSave() {

      let refProcessDesigner = this.$refs.modelDesigner.$refs.processDesigner;
      refProcessDesigner.onSave().then(xml => {
        this.bpmnXml = xml;
        let dataBody = {
          modelName: this.designerForm.processName,
          modelKey: this.designerForm.processKey,
          category: this.designerForm.category,
          description: this.designerForm.description,
          formType: this.designerForm.formType,
          formId: this.designerForm.formId,
          bpmnXml: this.bpmnXml
        }
        this.$confirm("是否将此模型保存为新版本？", "提示", {
          distinguishCancelAndClose: true,
          confirmButtonText: '是',
          cancelButtonText: '否'
        }).then(() => {
          this.confirmSave(dataBody, true)
        }).catch(action => {
          if (action === 'cancel') {
            this.confirmSave(dataBody, false)
          }
        })
      })
    },
    confirmSave(body, newVersion) {
      this.loading = true;
      saveModel(Object.assign(body, {
        newVersion: newVersion
      })).then(res => {
        this.result.icon = 'success';
        this.result.title = '成功';
        this.result.describe = res.msg;
      }).catch(res => {
        this.result.icon = 'error';
        this.result.title = '失败';
        this.result.describe = res.msg;
      }).finally(() => {
        this.loading = false;
        this.onNext();
        this.$emit('save');
      })
    },
    /** xml 文件 */
    getModelDetail(modelId) {
      this.loading = true;
      // 发送请求，获取xml
      getBpmnXml(modelId).then(res => {
        this.bpmnXml = res.data;
        this.loadIndex = modelId;
        this.loading = false;
      })
    }
  }
}
</script>

<style scoped>
.workflow-designer {
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: #ebeef5;
  top: 0;
  left: 0;
  z-index: 1010;
  padding: 10px;
}
.workflow-designer >>> .el-header {
  padding: 10px;
  background: #ffffff;
  margin-bottom: 5px;
}

.workflow-designer >>> .el-main {
  background: #ffffff;
  padding: 0;
}
</style>
