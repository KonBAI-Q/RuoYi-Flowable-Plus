<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="deployTime">
        <el-date-picker clearable size="small"
                        v-model="queryParams.deployTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择时间">
        </el-date-picker>
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
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
          v-hasPermi="['workflow:definition:designer']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['workflow:definition:designer']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['workflow:definition:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workflow:definition:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" fit :data="definitionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="流程标识" align="center" prop="processKey" :show-overflow-tooltip="true" />
      <el-table-column label="流程名称" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-button type="text" @click="handleProcessView(scope.row)">
            <span>{{ scope.row.processName }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="流程分类" align="center" prop="categoryName">
        <template slot-scope="scope">
          <span>{{ categoryOptions.find(k => k.code === scope.row.category).categoryName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务表单" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-button v-if="scope.row.formId" type="text" @click="handleForm(scope.row.formId)">
            <span>{{ scope.row.formName }}</span>
          </el-button>
          <label v-else>暂无表单</label>
        </template>
      </el-table-column>
      <el-table-column label="流程版本" align="center">
        <template slot-scope="scope">
          <el-tag size="medium" >v{{ scope.row.version }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-if="!scope.row.suspended">激活</el-tag>
          <el-tag type="warning" v-if="scope.row.suspended">挂起</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="部署时间" align="center" prop="deploymentTime" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="mini"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['workflow:definition:designer']"
          >编辑</el-button>
          <el-button
            type="text"
            size="mini"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workflow:definition:remove']"
          >删除</el-button>
          <el-dropdown>
            <span class="el-dropdown-link">
              <i class="el-icon-d-arrow-right el-icon--right"></i>更多
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                icon="el-icon-view"
                @click.native="handleProcessView(scope.row)"
                v-hasPermi="['workflow:definition:view']"
              >流程图</el-dropdown-item>
              <el-dropdown-item
                icon="el-icon-connection"
                v-if="scope.row.formId == null"
                @click.native="handleAddForm(scope.row)"
              >配置表单</el-dropdown-item>
              <el-dropdown-item
                icon="el-icon-price-tag"
                @click.native="handlePublish(scope.row)"
                v-hasPermi="['workflow:definition:list']"
              >版本管理</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- bpmn20.xml导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xml"
        :headers="upload.headers"
        :action="upload.url + '?name=' + upload.name+'&category='+ upload.category"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          流程名称：<el-input v-model="upload.name"/>
          流程分类：<el-input v-model="upload.category"/>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“bpmn20.xml”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 流程图 -->
    <el-dialog :title="processView.title" :visible.sync="processView.open" width="70%" append-to-body>
       <process-viewer :xml="processView.xmlData" :style="{height: '400px'}" />
    </el-dialog>

    <!--  编辑流程  -->
    <el-dialog :title="process.title" :visible.sync="process.open" width="500px" append-to-body>
      <el-form :model="process.form" size="mini" label-width="80px">
        <el-form-item label="流程标识">
          <el-input v-model="process.form.processKey" clearable disabled />
        </el-form-item>
        <el-form-item label="流程名称">
          <el-input v-model="process.form.processName" clearable />
        </el-form-item>
        <el-form-item label="流程分类">
          <el-select v-model="process.form.category" placeholder="请选择" clearable style="width:100%">
            <el-option v-for="item in categoryOptions" :key="item.categoryId" :label="item.categoryName" :value="item.code" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleLoadXml(process.form)">确 定</el-button>
        <el-button @click="process.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 版本管理 -->
    <el-dialog title="版本管理" :visible.sync="publish.open" width="50%" append-to-body>
      <el-table v-loading="publish.loading" :data="publish.dataList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="流程标识" align="center" prop="processKey" :show-overflow-tooltip="true" />
        <el-table-column label="流程名称" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button type="text" @click="handleProcessView(scope.row)">
              <span>{{ scope.row.processName }}</span>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column label="流程版本" align="center">
          <template slot-scope="scope">
            <el-tag size="medium" >v{{ scope.row.version }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <el-tag type="success" v-if="!scope.row.suspended">激活</el-tag>
            <el-tag type="warning" v-if="scope.row.suspended">挂起</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="mini"
              icon="el-icon-video-pause"
              v-if="!scope.row.suspended"
              @click.native="handleUpdateSuspended(scope.row)"
              v-hasPermi="['workflow:definition:update']"
            >挂起</el-button>
            <el-button
              type="text"
              size="mini"
              icon="el-icon-video-play"
              v-if="scope.row.suspended"
              @click.native="handleUpdateSuspended(scope.row)"
              v-hasPermi="['workflow:definition:update']"
            >激活</el-button>
            <el-button
              type="text"
              size="mini"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['workflow:definition:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="publishTotal > 0"
        :total="publishTotal"
        :page.sync="publishQueryParams.pageNum"
        :limit.sync="publishQueryParams.pageSize"
        @pagination="getPublishList"
      />
    </el-dialog>

    <!-- 表单配置详情 -->
    <el-dialog :title="formTitle" :visible.sync="formConfOpen" width="50%" append-to-body>
      <div class="test-form">
        <parser :key="new Date().getTime()"  :form-conf="formConf" />
      </div>
    </el-dialog>

    <!-- 挂载表单 -->
    <el-dialog :title="formDeployTitle" :visible.sync="formDeployOpen" width="60%" append-to-body>
      <el-row :gutter="24">
        <el-col :span="10" :xs="24">
          <el-table
            ref="singleTable"
            :data="formList"
            border
            highlight-current-row
            @current-change="handleCurrentChange"
            style="width: 100%">
            <el-table-column label="表单编号" align="center" prop="formId" />
            <el-table-column label="表单名称" align="center" prop="formName" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="submitFormDeploy(scope.row)">确定</el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            small
            layout="prev, pager, next"
            v-show="formTotal>0"
            :total="formTotal"
            :page.sync="formQueryParams.pageNum"
            :limit.sync="formQueryParams.pageSize"
            @pagination="ListFormDeploy"
          />
        </el-col>
        <el-col :span="14" :xs="24">
          <div v-if="currentRow">
            <parser :key="new Date().getTime()" :form-conf="currentRow" />
          </div>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { listDefinition, publishList, updateState, delDeployment, exportDeployment, definitionStart, readXml} from "@/api/workflow/definition";
import { getForm, addDeployForm ,listForm } from "@/api/workflow/form";
import { listCategory } from '@/api/workflow/category'
import Parser from '@/utils/generator/parser'
import ProcessViewer from '@/components/ProcessViewer'
import { getToken } from "@/utils/auth";

export default {
  name: "Definition",
  components: {
    Parser,
    ProcessViewer
  },
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
      // 流程定义表格数据
      definitionList: [],
      categoryOptions: [],
      process: {
        title: '',
        open: false,
        form: {}
      },
      publish: {
        open: false,
        loading: false,
        dataList: []
      },
      publishTotal: 0,
      publishQueryParams: {
        pageNum: 1,
        pageSize: 10,
        processKey: ""
      },
      formConfOpen: false,
      formTitle: "",
      formDeployOpen: false,
      formDeployTitle: "",
      formList: [],
      formTotal:0,
      formConf: {}, // 默认表单数据
      processView: {
        title: '',
        open: false,
        xmlData:"",
      },
      // bpmn.xml 导入
      upload: {
        // 是否显示弹出层（xml导入）
        open: false,
        // 弹出层标题（xml导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        name: null,
        category: null,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/flowable/definition/import"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        category: null,
        key: null,
        tenantId: null,
        deployTime: null,
        derivedFrom: null,
        derivedFromRoot: null,
        parentDeploymentId: null,
        engineVersion: null
      },
      formQueryParams:{
        pageNum: 1,
        pageSize: 10,
      },
      // 挂载表单到流程实例
      formDeployParam:{
        formId: null,
        deployId: null
      },
      currentRow: null,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    listCategory().then(response => {
      this.categoryOptions = response.rows
    })
    this.getList();
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      listDefinition(this.queryParams).then(response => {
        this.definitionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getPublishList() {
      this.publish.loading = true;
      publishList(this.publishQueryParams).then(response => {
        this.publish.dataList = response.rows;
        this.publishTotal = response.total;
        this.publish.loading = false;
      })
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        category: null,
        key: null,
        tenantId: null,
        deployTime: null,
        derivedFrom: null,
        derivedFromRoot: null,
        parentDeploymentId: null,
        engineVersion: null
      };
      this.resetForm("form");
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 跳转到流程设计页面 */
    handleLoadXml(row) {
      this.process.open = false;
      this.$router.push({
        path: '/definition/designer',
        query: {
          definitionId: row.definitionId,
          processId: row.processKey,
          processName: row.processName,
          category: row.category
        }
      })
    },
    handlePublish(row) {
      this.publishQueryParams.processKey = row.processKey;
      this.publish.open = true;
      this.getPublishList();
    },
    /** 表单查看 */
    handleForm(formId){
      getForm(formId).then(res =>{
        this.formTitle = "表单详情";
        this.formConfOpen = true;
        this.formConf = JSON.parse(res.data.content)
      })
    },
    /** 启动流程 */
    handleDefinitionStart(row){
      definitionStart(row.id).then(res =>{
        this.$modal.msgSuccess(res.msg);
      })
    },
    /** 查看流程图 */
    handleProcessView(row) {
      let definitionId = row.definitionId;
      this.processView.title = "流程图";
      // 发送请求，获取xml
      readXml(definitionId).then(res => {
        this.processView.xmlData = res.data;
        this.processView.open = true;
      })
    },
    /** 挂载表单弹框 */
    handleAddForm(row){
      this.formDeployParam.deployId = row.deploymentId
      this.ListFormDeploy()
    },
    /** 挂载表单列表 */
    ListFormDeploy(){
      listForm(this.formQueryParams).then(res =>{
        this.formList = res.rows;
        this.formTotal = res.total;
        this.formDeployOpen = true;
        this.formDeployTitle = "挂载表单";
      })
    },
    // /** 更改挂载表单弹框 */
    // handleEditForm(row){
    //   this.formDeployParam.deployId = row.deploymentId
    //   const queryParams = {
    //     pageNum: 1,
    //     pageSize: 10
    //   }
    //   listForm(queryParams).then(res =>{
    //     this.formList = res.rows;
    //     this.formDeployOpen = true;
    //     this.formDeployTitle = "挂载表单";
    //   })
    // },
    /** 挂载表单 */
    submitFormDeploy(row){
      this.formDeployParam.formId = row.formId;
      addDeployForm(this.formDeployParam).then(res =>{
        this.$modal.msgSuccess(res.msg);
        this.formDeployOpen = false;
        this.getList();
      })
    },
    handleCurrentChange(data) {
      if (data) {
        this.currentRow = JSON.parse(data.content);
      }
    },
    /** 挂起/激活流程 */
    handleUpdateSuspended(row) {
      const params = {
        definitionId: row.definitionId,
        suspended: !row.suspended
      }
      updateState(params).then(res => {
        this.$modal.msgSuccess(res.msg)
        this.getPublishList();
      });
    },
    handleAdd() {
      const dateTime = new Date().getTime();
      this.process.title = '新增流程';
      this.process.form = {
        processKey: `Process_${dateTime}`,
        processName: `业务流程_${dateTime}`
      };
      this.process.open = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.process.title = '编辑流程';
      this.process.form = JSON.parse(JSON.stringify(row));
      this.process.open = true;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // const ids = row.deploymentId || this.ids;
      const params = {
        deployId: row.deploymentId
      }
      this.$confirm('是否确认删除流程定义编号为"' + params.deployId + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delDeployment(params);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有流程定义数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportDeployment(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    /** 导入bpmn.xml文件 */
    handleImport(){
      this.upload.title = "bpmn20.xml文件导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$message(response.msg);
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>
