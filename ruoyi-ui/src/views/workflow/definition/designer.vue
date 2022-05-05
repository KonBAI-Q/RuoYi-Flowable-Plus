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
              <el-button type="success" @click="onSave()" :disabled="activeStep < 1">保存</el-button>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <div v-show="activeStep === 0">
          <div class="app-container">
            <el-form size="small" label-width="80px">
              <el-form-item label="流程标识">
                <el-col :span="11">
                  <el-input v-model="designerForm.processKey" clearable disabled />
                </el-col>
              </el-form-item>
              <el-form-item label="流程名称">
                <el-col :span="11">
                  <el-input v-model="designerForm.processName" clearable />
                </el-col>
              </el-form-item>
              <el-form-item label="流程分类">
                <el-col :span="11">
                  <el-select v-model="designerForm.category" placeholder="请选择" clearable style="width:100%">
                    <el-option v-for="item in categoryOptions" :key="item.categoryId" :label="item.categoryName" :value="item.code" />
                  </el-select>
                </el-col>
              </el-form-item>
            </el-form>
          </div>
        </div>
        <div v-show="activeStep === 1">
          <process-designer
            ref="modelDesigner"
            v-loading="loading"
            :key="`designer-${loadIndex}`"
            :bpmnXml="bpmnXml"
            :designerForm="designerForm"
          />
        </div>
        <div v-show="activeStep === 2">
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
import { readXml, saveXml } from "@/api/workflow/definition";
import ProcessDesigner from '@/components/ProcessDesigner';
import { listCategory } from '@/api/workflow/category';

export default {
  name: 'Designer',
  components: { ProcessDesigner },
  props: {
    designerForm: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      activeStep: 0,
      basicInfoForm: {},
      categoryOptions: [],
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
    this.getCategoryList();
    if (this.designerForm.definitionId) {
      this.getModelDetail(this.designerForm.definitionId)
    }
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
    onSave() {
      let refProcessDesigner = this.$refs.modelDesigner.$refs.processDesigner;
      refProcessDesigner.onSave().then(xml => {
        this.bpmnXml = xml;
        saveXml({
          name: this.designerForm.processName,
          category: this.designerForm.category,
          xml: this.bpmnXml
        }).then(res => {
          this.result.icon = 'success';
          this.result.title = '成功';
          this.result.describe = res.msg;
        }).catch(res => {
          this.result.icon = 'error';
          this.result.title = '失败';
          this.result.describe = res.msg;
        }).finally(() => {
          this.onNext();
          this.$emit('save');
        })
      })
    },
    /** 查询流程分类列表 */
    getCategoryList() {
      listCategory().then(response => this.categoryOptions = response.rows)
    },
    /** xml 文件 */
    getModelDetail(definitionId) {
      this.loading = true;
      // 发送请求，获取xml
      readXml(definitionId).then(res => {
        this.bpmnXml = res.data;
        this.loadIndex = definitionId;
        this.loading = false;
      })
    },
    saveProcess(xml) {
      this.bpmnXml = xml;
      saveXml({
        name: this.designerForm.processName,
        category: this.designerForm.category,
        xml: this.bpmnXml
      }).then(res => {
        this.$message(res.msg)
        this.$emit('save');
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
