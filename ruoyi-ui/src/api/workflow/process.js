import request from '@/utils/request'

// 我的发起的流程
export function myProcessList(query) {
  return request({
    url: '/workflow/task/myProcess',
    method: 'get',
    params: query
  })
}

// 完成任务
export function complete(data) {
  return request({
    url: '/workflow/task/complete',
    method: 'post',
    data: data
  })
}

// 取消申请
export function stopProcess(data) {
  return request({
    url: '/workflow/task/stopProcess',
    method: 'post',
    data: data
  })
}

// 驳回任务
export function rejectTask(data) {
  return request({
    url: '/workflow/task/reject',
    method: 'post',
    data: data
  })
}

// 可退回任务列表
export function returnList(data) {
  return request({
    url: '/workflow/task/returnList',
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

// 删除流程实例
export function delProcess(id) {
  return request({
    url: '/workflow/instance/delete/?instanceId=' + id,
    method: 'delete'
  })
}
