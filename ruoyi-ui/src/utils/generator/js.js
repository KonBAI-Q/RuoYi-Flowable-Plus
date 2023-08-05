import { isArray } from 'util'
import { exportDefault, titleCase } from '@/utils/index'
import { trigger } from './config'

const units = {
  KB: '1024',
  MB: '1024 / 1024',
  GB: '1024 / 1024 / 1024'
}
let confGlobal
const inheritAttrs = {
  file: '',
  dialog: 'inheritAttrs: false,'
}


export function makeUpJs(conf, type) {
  confGlobal = conf = JSON.parse(JSON.stringify(conf))
  const dataList = []
  const ruleList = []
  const optionsList = []
  const propsList = []
  const methodList = mixinMethod(type)
  const uploadVarList = []

  conf.fields.forEach(el => {
    buildAttributes(el, dataList, ruleList, optionsList, methodList, propsList, uploadVarList)
  })

  const script = buildexport(
    conf,
    type,
    dataList.join('\n'),
    ruleList.join('\n'),
    optionsList.join('\n'),
    uploadVarList.join('\n'),
    propsList.join('\n'),
    methodList.join('\n')
  )
  confGlobal = null
  return script
}

function buildAttributes(el, dataList, ruleList, optionsList, methodList, propsList, uploadVarList) {
  buildData(el, dataList)
  buildRules(el, ruleList)

  if (el.__slot__) {
    if (el.__slot__.options && el.__slot__.options.length) {
      buildOptions(el, optionsList)
    }
  } else {
    if (el.options && el.options.length) {
      buildOptions(el, optionsList)
      if (el.__config__.dataType === 'dynamic') {
        const model = `${el.__vModel__}Options`
        const options = titleCase(model)
        buildOptionMethod(`get${options}`, model, methodList)
      }
    }
  }

  if (el.props && el.props.props) {
    buildProps(el, propsList)
  }

  if (el.action && el.__config__.tag === 'el-upload') {
    uploadVarList.push(
      `${el.__vModel__}Action: '${el.action}',
      ${el.__vModel__}fileList: [],`
    )
    methodList.push(buildBeforeUpload(el))
    if (!el['auto-upload']) {
      methodList.push(buildSubmitUpload(el))
    }
  }

  if (el.__config__.children) {
    el.__config__.children.forEach(el2 => {
      buildAttributes(el2, dataList, ruleList, optionsList, methodList, propsList, uploadVarList)
    })
  }
}

function mixinMethod(type) {
  const list = []; const
    minxins = {
      file: confGlobal.formBtns ? {
        submitForm: `submitForm() {
        this.$refs['${confGlobal.formRef}'].validate(valid => {
          if(!valid) return
          // TODO 提交表单
        })
      },`,
        resetForm: `resetForm() {
        this.$refs['${confGlobal.formRef}'].resetFields()
      },`
      } : null,
      dialog: {
        onOpen: 'onOpen() {},',
        onClose: `onClose() {
        this.$refs['${confGlobal.formRef}'].resetFields()
      },`,
        close: `close() {
        this.$emit('update:visible', false)
      },`,
        handleConfirm: `handleConfirm() {
        this.$refs['${confGlobal.formRef}'].validate(valid => {
          if(!valid) return
          this.close()
        })
      },`
      }
    }

  const methods = minxins[type]
  if (methods) {
    Object.keys(methods).forEach(key => {
      list.push(methods[key])
    })
  }

  return list
}

function buildData(conf, dataList) {
  if (conf.__vModel__ === undefined) return
  let defaultValue
  if (typeof (conf.__config__.defaultValue) === 'string' && !conf.multiple) {
    defaultValue = `'${conf.__config__.defaultValue}'`
  } else {
    defaultValue = `${JSON.stringify(conf.__config__.defaultValue)}`
  }
  dataList.push(`${conf.__vModel__}: ${defaultValue},`)
}

function buildRules(conf, ruleList) {
  if (conf.__vModel__ === undefined) return
  const rules = []
  if (trigger[conf.__config__.tag]) {
    if (conf.__config__.required) {
      const type = isArray(conf.__config__.defaultValue) ? 'type: \'array\',' : ''
      let message = isArray(conf.__config__.defaultValue) ? `请至少选择一个${conf.__vModel__}` : conf.placeholder
      if (message === undefined) message = `${conf.__config__.label}不能为空`
      rules.push(`{ required: true, ${type} message: '${message}', trigger: '${trigger[conf.__config__.tag]}' }`)
    }
    if (conf.__config__.regList && isArray(conf.__config__.regList)) {
      conf.__config__.regList.forEach(item => {
        if (item.pattern) {
          rules.push(`{ pattern: ${eval(item.pattern)}, message: '${item.message}', trigger: '${trigger[conf.__config__.tag]}' }`)
        }
      })
    }
    ruleList.push(`${conf.__vModel__}: [${rules.join(',')}],`)
  }
}

function buildOptions(conf, optionsList) {
  if (conf.__vModel__ === undefined) return
  if (conf.__config__.dataType === 'dynamic') { conf.options = [] }
  const options = conf.__config__.tag ==='el-cascader'?conf.options:conf.__slot__.options;
  const str = `${conf.__vModel__}Options: ${JSON.stringify(options)},`
  optionsList.push(str)
}

function buildProps(conf, propsList) {
  if (conf.__config__.dataType === 'dynamic') {
    conf.valueKey !== 'value' && (conf.props.props.value = conf.valueKey)
    conf.labelKey !== 'label' && (conf.props.props.label = conf.labelKey)
    conf.childrenKey !== 'children' && (conf.props.props.children = conf.childrenKey)
  }
  const str = `${conf.__vModel__}Props: ${JSON.stringify(conf.props.props)},`
  propsList.push(str)
}

function buildBeforeUpload(conf) {
  const unitNum = units[conf.__config__.sizeUnit]; let rightSizeCode = ''; let acceptCode = ''; const
    returnList = []
  if (conf.__config__.fileSize) {
    rightSizeCode = `let isRightSize = file.size / ${unitNum} < ${conf.__config__.fileSize}
    if(!isRightSize){
      this.$message.error('文件大小超过 ${conf.__config__.fileSize}${conf.__config__.sizeUnit}')
    }`
    returnList.push('isRightSize')
  }
  if (conf.accept) {
    acceptCode = `let isAccept = new RegExp('${conf.accept}').test(file.type)
    if(!isAccept){
      this.$message.error('应该选择${conf.accept}类型的文件')
    }`
    returnList.push('isAccept')
  }
  const str = `${conf.__vModel__}BeforeUpload(file) {
    ${rightSizeCode}
    ${acceptCode}
    return ${returnList.join('&&')}
  },`
  return returnList.length ? str : ''
}

function buildSubmitUpload(conf) {
  const str = `submitUpload() {
    this.$refs['${conf.__vModel__}'].submit()
  },`
  return str
}

function buildOptionMethod(methodName, model, methodList) {
  const str = `${methodName}() {
    // TODO 发起请求获取数据
    this.${model}
  },`
  methodList.push(str)
}

function buildexport(conf, type, data, rules, selectOptions, uploadVar, props, methods) {
  const str = `${exportDefault}{
  ${inheritAttrs[type]}
  components: {},
  props: [],
  data () {
    return {
      ${conf.formModel}: {
        ${data}
      },
      ${conf.formRules}: {
        ${rules}
      },
      ${uploadVar}
      ${selectOptions}
      ${props}
    }
  },
  computed: {},
  watch: {},
  created () {},
  mounted () {},
  methods: {
    ${methods}
  }
}`
  return str
}
