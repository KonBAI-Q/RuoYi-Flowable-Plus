<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>发起流程</span>
      </div>
      <div class="form-conf" v-if="formOpen">
        <v-form-render :form-json="formModel" :form-data="formData" ref="vFormRef"></v-form-render>
        <div class="cu-submit">
          <el-button type="primary" @click="submit">提交</el-button>
          <el-button @click="reset">重置</el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getProcessForm, startProcess } from '@/api/workflow/process'

export default {
  name: 'WorkStart',
  data() {
    return {
      definitionId: null,
      deployId: null,
      procInsId: null,
      formOpen: false,
      formModel: {},
      formData: {}
    }
  },
  created() {
    this.initData();
  },
  methods: {
    initData() {
      this.deployId = this.$route.params && this.$route.params.deployId;
      this.definitionId = this.$route.query && this.$route.query.definitionId;
      this.procInsId = this.$route.query && this.$route.query.procInsId;
      getProcessForm({
        definitionId: this.definitionId,
        deployId: this.deployId,
        procInsId: this.procInsId
      }).then(response => {
        const data = response.data;
        this.formModel = data.formModel;
        this.formData = data.formData || {};
        this.formOpen = true
        this.$nextTick(() => {
          this.$refs.vFormRef.setFormJson(this.formModel || {formConfig: {}, widgetList: []})
        })
      })
    },
    submit() {
      this.$refs.vFormRef.getFormData().then(formData => {
        if (this.definitionId) {
          // 启动流程并将表单数据加入流程变量
          startProcess(this.definitionId, JSON.stringify(formData)).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.$tab.closeOpenPage({
              path: '/work/own'
            })
          })
        }
      }).catch(err=>{
        this.$modal.msgError(err);
      })
    },
    reset() {
      this.$refs.vFormRef.resetForm()
    },
  }
}
</script>

<style lang="scss" scoped>
.form-conf {
  margin: 15px auto;
  width: 80%;
  /*padding: 15px;*/
}

.cu-submit {
  margin-top: 15px;
  text-align: center;
}
</style>
