<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="表单名称" prop="formName">
        <el-input
          v-model="queryParams.formName"
          placeholder="请输入表单名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['workflow:form:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['workflow:form:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['workflow:form:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workflow:form:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="formList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="表单主键" align="center" prop="formId" />
      <el-table-column label="表单名称" align="center" prop="formName" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workflow:form:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workflow:form:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改流程表单对话框 -->
    <el-dialog title="表单信息" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="表单名称" prop="formName">
          <el-input v-model="form.formName" placeholder="请输入表单名称" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open=false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 设计表单对话框 -->
    <el-dialog :visible.sync="designerFormOpen" custom-class="vf-designer" fullscreen append-to-body>
      <v-form-designer ref="vfDesigner" :resetFormJson="true" :designer-config="{ externalLink: false, toolbarMaxWidth: 480 }">
        <!-- 自定义按钮插槽 -->
        <template #customToolButtons>
          <el-button type="text" @click="open=true"><i class="el-icon-finished"/>保存</el-button>
        </template>
      </v-form-designer>
    </el-dialog>

    <!-- 预览表单对话框 -->
    <el-dialog title="表单预览" :visible.sync="renderFormOpen" width="60%" append-to-body>
      <v-form-render :form-json="{}" :form-data="{}" ref="vFormRef"></v-form-render>
    </el-dialog>
  </div>
</template>

<script>
import { listForm, getForm, delForm, addForm, updateForm } from "@/api/workflow/form";

export default {
  name: "Form",
  components: {},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 流程表单表格数据
      formList: [],
      // 弹出层标题
      title: "",
      designerFormOpen: false,
      renderFormOpen: false,
      formTitle: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        formName: null,
        content: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        formName: [
          {required: true, message: "表单名称不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  activated() {
    this.getList();
  },
  methods: {
    /** 查询流程表单列表 */
    getList() {
      this.loading = true;
      listForm(this.queryParams).then(response => {
        this.formList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 表单重置
    reset() {
      this.form = {
        formId: null,
        formName: null,
        content: null,
        createTime: null,
        updateTime: null,
        createBy: null,
        updateBy: null,
        remark: null
      };
      this.resetForm("form");
    },
    clearDesigner() {
      this.$nextTick(() => {
        this.$refs.vfDesigner.clearDesigner()
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.formId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 表单配置信息 */
    handleDetail(row) {
      this.renderFormOpen = true;
      this.$nextTick(() => {
        this.$refs.vFormRef.setFormJson(row.content || {formConfig: {}, widgetList: []})
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.designerFormOpen = true;
      //清理表单设计器
      this.clearDesigner()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const formId = row.formId || this.ids
      getForm(formId).then(response => {
        this.form = response.data;
        this.designerFormOpen = true;
        //清理表单设计器
        this.clearDesigner()
        this.$nextTick(() => {
          let {content} = this.form
          if (content) {
            this.$refs.vfDesigner.setFormJson(content)
          }
        })
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let formJson = this.$refs.vfDesigner.getFormJson();
          this.form.content = JSON.stringify(formJson)
          if (this.form.formId != null) {
            updateForm(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.designerFormOpen=false
              this.getList();
            });
          } else {
            addForm(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.designerFormOpen=false
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const formIds = row.formId || this.ids;
      this.$confirm('是否确认删除流程表单编号为"' + formIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delForm(formIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      let _this = this
      this.$confirm('是否确认导出所有流程表单数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        _this.download('/workflow/form/export', {
        ..._this.queryParams
      }, `form_${new Date().getTime()}.xlsx`)
      })
    }
  }
};
</script>

<style lang="scss">
.vf-designer .el-dialog__body {
  padding: 0;
  box-sizing: border-box;
  overflow-y: auto;
}
</style>
