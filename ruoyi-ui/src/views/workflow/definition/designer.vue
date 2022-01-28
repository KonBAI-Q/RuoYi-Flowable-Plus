<template>
  <div>
    <process-designer
      v-loading="loading"
      :key="`designer-${loadIndex}`"
      :bpmnXml="bpmnXml"
      :designerForm="designerForm"
      @save="onSaveFlowEntry"
    />
  </div>
</template>

<script>
import {readXml, roleList, saveXml, userList} from "@/api/workflow/definition";
import ProcessDesigner from '@/components/ProcessDesigner';

export default {
  name: 'index',
  components: { ProcessDesigner },
  data () {
    return {
      loadIndex: 0,
      loading: false,
      bpmnXml: '',
      designerForm: {
        definitionId: undefined,
        processId: undefined,
        processName: undefined,
        category: undefined
      }
    }
  },
  created() {
    const query = this.$route.query
    if (query) {
      this.designerForm = query;
      //  查询流程xml
      if (query.definitionId) {
        this.getModelDetail(query.definitionId);
      }
    }
  },
  methods: {
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
    onSaveFlowEntry (saveData) {
      this.bpmnXml = saveData;
      saveXml({
        name: this.designerForm.processName,
        category: this.designerForm.category,
        xml: this.bpmnXml
      }).then(res => {
        this.$message(res.msg)
        // 关闭当前标签页并返回上个页面
        this.$store.dispatch("tagsView/delView", this.$route);
        this.$router.go(-1)
      })
    },
  }
}
</script>

<style scoped>
</style>
