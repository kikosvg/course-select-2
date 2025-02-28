import http from '@/utils/http'

const BASE_URL = '/selectclass/course'

export interface CourseResp {
  id: string
  courseNo: string
  name: string
  credit: string
  hours: string
  type: string
  collegeId: string
  description: string
  status: string
  createUserString: string
  updateUserString: string
  disabled: boolean
}
export interface CourseDetailResp {
  id: string
  courseNo: string
  name: string
  credit: string
  hours: string
  type: string
  collegeId: string
  description: string
  status: string
  createTime: string
  updateTime: string
  createUser: string
  updateUser: string
  createUserString: string
  updateUserString: string
}
export interface CourseQuery {
  courseNo: string | undefined
  name: string | undefined
  credit: string | undefined
  hours: string | undefined
  type: string | undefined
  collegeId: string | undefined
  status: string | undefined
  sort: Array<string>
}
export interface CoursePageQuery extends CourseQuery, PageQuery {}

/** @desc 查询课程列表 */
export function listCourse(query: CoursePageQuery) {
  return http.get<PageRes<CourseResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询课程详情 */
export function getCourse(id: string) {
  return http.get<CourseDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增课程 */
export function addCourse(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改课程 */
export function updateCourse(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除课程 */
export function deleteCourse(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出课程 */
export function exportCourse(query: CourseQuery) {
  return http.download(`${BASE_URL}/export`, query)
}
