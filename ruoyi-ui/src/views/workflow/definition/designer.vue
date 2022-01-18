<template>
  <div>
    <process-designer :key="`designer-${loadIndex}`" :flowEntryInfo="formFlowEntryData" @save="onSaveFlowEntry" />
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
      formFlowEntryData: {
        entryId: undefined,
        processDefinitionName: undefined,
        processDefinitionKey: undefined,
        categoryId: undefined,
        bindFormType: [
          {
            id: 0,
            name: '动态表单',
            symbol: 'ONLINE_FORM'
          },
          {
            id: 1,
            name: '路由表单',
            symbol: 'ROUTER_FORM'
          }
        ],
        pageId: undefined,
        defaultFormId: undefined,
        defaultRouterName: undefined,
        bpmnXml: undefined
      },
    }
  },
  created() {
    const deployId = this.$route.query && this.$route.query.deployId;
    //  查询流程xml
    if (deployId) {
      this.getModelDetail(deployId);
    }
    this.getDicts("sys_process_category").then(res => {
      this.categorys = res.data;
    });
  },
  methods: {
    /** xml 文件 */
    getModelDetail(deployId) {
      // 发送请求，获取xml
      readXml(deployId).then(res =>{
        this.formFlowEntryData.bpmnXml = res.data;
        this.loadIndex = deployId;
      })
    },
    onSaveFlowEntry ({ saveData, modeler }) {
      this.formFlowEntryData.bpmnXml = saveData;
      // TODO 2022/01/05 改进获取流程名称的方式
      const process = modeler.get('elementRegistry').find(el => el.type === 'bpmn:Process');
      saveXml({
        name: process.businessObject.name,
        category: process.businessObject.processCategory,
        xml: this.formFlowEntryData.bpmnXml
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
