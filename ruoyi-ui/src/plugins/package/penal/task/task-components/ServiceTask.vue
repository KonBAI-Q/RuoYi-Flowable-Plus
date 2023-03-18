<template>
  <div>
    <el-form-item label="执行类型" key="executeType">
      <el-select v-model="serviceTaskForm.executeType">
        <el-option label="Java类" value="class" />
        <el-option label="表达式" value="expression" />
        <el-option label="代理表达式" value="delegateExpression" />
      </el-select>
    </el-form-item>
    <el-form-item
      v-if="serviceTaskForm.executeType === 'class'"
      label="Java类"
      prop="class"
      key="execute-class"
    >
      <el-input v-model="serviceTaskForm.class" clearable @change="updateElementTask" />
    </el-form-item>
    <el-form-item
      v-if="serviceTaskForm.executeType === 'expression'"
      label="表达式"
      prop="expression"
      key="execute-expression"
    >
      <el-input v-model="serviceTaskForm.expression" clearable @change="updateElementTask" />
    </el-form-item>
    <el-form-item
      v-if="serviceTaskForm.executeType === 'delegateExpression'"
      label="代理表达式"
      prop="delegateExpression"
      key="execute-delegate"
    >
      <el-input v-model="serviceTaskForm.delegateExpression" clearable @change="updateElementTask" />
    </el-form-item>
  </div>
</template>

<script>
export default {
  name: "ServiceTask",
  props: {
    id: String,
    type: String
  },
  data() {
    return {
      defaultTaskForm: {
        executeType: "",
        class: "",
        expression: "",
        delegateExpression: ""
      },
      serviceTaskForm: {}
    };
  },
  watch: {
    id: {
      immediate: true,
      handler() {
        this.bpmnElement = window.bpmnInstances.bpmnElement;
        this.$nextTick(() => this.resetTaskForm());
      }
    }
  },
  methods: {
    resetTaskForm() {
      for (let key in this.defaultTaskForm) {
        let value = this.bpmnElement?.businessObject[key] || this.defaultTaskForm[key];
        if (value) this.$set(this.serviceTaskForm, "executeType", key);
        this.$set(this.serviceTaskForm, key, value);
      }
    },
    updateElementTask() {
      let taskAttr = Object.create(null);
      const type = this.serviceTaskForm.executeType;
      for (let key in this.serviceTaskForm) {
        if (key !== 'executeType' && key !== type) taskAttr[key] = null;
      }
      taskAttr[type] = this.serviceTaskForm[type] || "";
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, taskAttr);
    }
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
