import http from '@/utils/http'

const BASE_URL = '/selectclass/courseSelection'

export interface CourseSelectionResp {
  id: string
  studentId: string
  scheduleId: string
  selectTime: string
  score: string
  status: string
  createUserString: string
  updateUserString: string
  disabled: boolean
}
export interface CourseSelectionDetailResp {
  id: string
  studentId: string
  scheduleId: string
  selectTime: string
  score: string
  status: string
  createTime: string
  updateTime: string
  createUser: string
  updateUser: string
  createUserString: string
  updateUserString: string
}
export interface CourseSelectionQuery {
  studentId: string | undefined
  scheduleId: string | undefined
  selectTime: string | undefined
  status: string | undefined
  sort: Array<string>
}
export interface CourseSelectionPageQuery extends CourseSelectionQuery, PageQuery {}

/** @desc 查询选课记录列表 */
export function listCourseSelection(query: CourseSelectionPageQuery) {
  return http.get<PageRes<CourseSelectionResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询选课记录详情 */
export function getCourseSelection(id: string) {
  return http.get<CourseSelectionDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增选课记录 */
export function addCourseSelection(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改选课记录 */
export function updateCourseSelection(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除选课记录 */
export function deleteCourseSelection(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出选课记录 */
export function exportCourseSelection(query: CourseSelectionQuery) {
  return http.download(`${BASE_URL}/export`, query)
}
