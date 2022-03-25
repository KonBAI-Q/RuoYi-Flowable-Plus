import request from '@/utils/request'

// 查询流程部署关联表单信息
export function getFormByDeployId(deployId) {
  return request({
    url: '/workflow/deploy/form/' + deployId,
    method: 'get',
  })
}
