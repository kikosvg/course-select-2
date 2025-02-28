import http from '@/utils/http'

const BASE_URL = '/selectclass/class'

export interface ClassResp {
  id: string
  name: string
  code: string
  majorId: string
  grade: string
  description: string
  status: string
  createUserString: string
  updateUserString: string
  disabled: boolean
}
export interface ClassDetailResp {
  id: string
  name: string
  code: string
  majorId: string
  grade: string
  description: string
  status: string
  createTime: string
  updateTime: string
  createUser: string
  updateUser: string
  createUserString: string
  updateUserString: string
}
export interface ClassQuery {
  name: string | undefined
  code: string | undefined
  majorId: string | undefined
  grade: string | undefined
  status: string | undefined
  sort: Array<string>
}
export interface ClassPageQuery extends ClassQuery, PageQuery {}

/** @desc 查询班级列表 */
export function listClass(query: ClassPageQuery) {
  return http.get<PageRes<ClassResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询班级详情 */
export function getClass(id: string) {
  return http.get<ClassDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增班级 */
export function addClass(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改班级 */
export function updateClass(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除班级 */
export function deleteClass(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出班级 */
export function exportClass(query: ClassQuery) {
  return http.download(`${BASE_URL}/export`, query)
}
