import request from '@/utils/request'

// 查询已办任务列表
export function finishedList(query) {
  return request({
    url: '/workflow/task/finishedList',
    method: 'get',
    params: query
  })
}

// 任务流转记录
export function flowRecord(query) {
  return request({
    url: '/workflow/task/flowRecord',
    method: 'get',
    params: query
  })
}

// 撤回任务
export function revokeProcess(data) {
  return request({
    url: '/workflow/task/revokeProcess',
    method: 'post',
    data: data
  })
}

// 部署流程实例
export function deployStart(deployId) {
  return request({
    url: '/workflow/process/startFlow/' + deployId,
    method: 'get',
  })
}

// 查询流程定义详细
export function getDeployment(id) {
  return request({
    url: '/system/deployment/' + id,
    method: 'get'
  })
}

// 新增流程定义
export function addDeployment(data) {
  return request({
    url: '/system/deployment',
    method: 'post',
    data: data
  })
}

// 修改流程定义
export function updateDeployment(data) {
  return request({
    url: '/system/deployment',
    method: 'put',
    data: data
  })
}

// 删除流程定义
export function delDeployment(id) {
  return request({
    url: '/system/deployment/' + id,
    method: 'delete'
  })
}

// 导出流程定义
export function exportDeployment(query) {
  return request({
    url: '/system/deployment/export',
    method: 'get',
    params: query
  })
}
