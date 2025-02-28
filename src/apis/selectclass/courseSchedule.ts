import http from '@/utils/http'

const BASE_URL = '/selectclass/courseSchedule'

export interface CourseScheduleResp {
  id: string
  courseId: string
  teacherId: string
  semester: string
  classroom: string
  dayOfWeek: string
  startTime: string
  endTime: string
  maxStudent: string
  selected: string
  status: string
  createUserString: string
  updateUserString: string
  disabled: boolean
}
export interface CourseScheduleDetailResp {
  id: string
  courseId: string
  teacherId: string
  semester: string
  classroom: string
  dayOfWeek: string
  startTime: string
  endTime: string
  maxStudent: string
  selected: string
  status: string
  createTime: string
  updateTime: string
  createUser: string
  updateUser: string
  createUserString: string
  updateUserString: string
}
export interface CourseScheduleQuery {
  courseId: string | undefined
  teacherId: string | undefined
  semester: string | undefined
  classroom: string | undefined
  dayOfWeek: string | undefined
  startTime: string | undefined
  endTime: string | undefined
  maxStudent: string | undefined
  selected: string | undefined
  status: string | undefined
  sort: Array<string>
}
export interface CourseSchedulePageQuery extends CourseScheduleQuery, PageQuery {}

/** @desc 查询课程安排列表 */
export function listCourseSchedule(query: CourseSchedulePageQuery) {
  return http.get<PageRes<CourseScheduleResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询课程安排详情 */
export function getCourseSchedule(id: string) {
  return http.get<CourseScheduleDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增课程安排 */
export function addCourseSchedule(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改课程安排 */
export function updateCourseSchedule(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除课程安排 */
export function deleteCourseSchedule(id: string) {
  return http.del(`${BASE_URL}/${id}`)
}

/** @desc 导出课程安排 */
export function exportCourseSchedule(query: CourseScheduleQuery) {
  return http.download(`${BASE_URL}/export`, query)
}
