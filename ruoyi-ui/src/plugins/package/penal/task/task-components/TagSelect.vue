<template>
  <div class="tag-select">
    <div class="tag-box">
      <el-tag v-for="item in selectValues" :key="item.userId" effect="dark"
        style="margin-right: 5px;" type="primary" size="mini" closable
        @close="onDeleteTag(item)"
      >
        {{item.nickName}}
      </el-tag>
    </div>
    <slot name="append" />
  </div>
</template>

<script>
export default {
  name: 'TagSelect',
  props: {
    value: {
      type: [Object, Array]
    }
  },
  data () {
    return {
      selectValues: []
    }
  },
  methods: {
    onDeleteTag (tag) {
      let temp = this.selectValues.filter(item => {
        return item !== tag;
      });
      if (Array.isArray(this.value)) {
        this.$emit('input', temp);
      } else {
        this.$emit('input', temp.map(item => item.id).join(','));
      }
    }
  },
  watch: {
    value: {
      handler (newValue) {
        if (newValue == null || newValue === '') {
          this.selectValues = [];
        } else {
          if (Array.isArray(newValue)) {
            this.selectValues = [...newValue];
          } else {
            this.selectValues = [{
              userId: newValue.userId,
              /* eslint-disable-next-line */
              nickName: newValue.nickName === '${startUserName}' ? '流程发起人' : newValue.nickName
            }]
          }
        }
      },
      immediate: true
    }
  }
}
</script>

<style scoped>
  .tag-select {
    border: 1px solid #DCDFE6;
    border-radius: 4px;
    display: flex;
  }
  .tag-box {
    flex-grow: 1;
    padding: 0px 5px;
  }
</style>
