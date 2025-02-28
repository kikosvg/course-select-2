import http from '@/utils/http'

const BASE_URL = '/selectclass/teacher'

export interface TeacherResp {
  id: string
  teacherNo: string
  name: string
  gender: string
  title: string
  phone: string
  email: string
  collegeId: string
  status: string
  createUserString: string
  updateUserString: string
  disabled: boolean
}
export interface TeacherDetailResp {
  id: string
  teacherNo: string
  name: string
  gender: string
  title: string
  phone: string
  email: string
  collegeId: string
  status: string
  createTime: string
  updateTime: string
  createUser: string
  updateUser: string
  createUserString: string
  updateUserString: string
}
export interface TeacherQuery {
  teacherNo: string | undefined
  name: string | undefined
  gender: string | undefined
  collegeId: string | undefined
  status: string | undefined
  sort: Array<string>
}
export interface TeacherPageQuery extends TeacherQuery, PageQuery {}

/** @desc 查询教师管理列表 */
export function listTeacher(query: TeacherPageQuery) {
  return http.get<PageRes<TeacherResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询教师管理详情 */
export function getTeacher(id: string) {
  return http.get<TeacherDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增教师管理 */
export function addTeacher(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改教师管理 */
export function updateTeacher(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除教师管理 */
export function deleteTeacher(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出教师管理 */
export function exportTeacher(query: TeacherQuery) {
  return http.download(`${BASE_URL}/export`, query)
}
