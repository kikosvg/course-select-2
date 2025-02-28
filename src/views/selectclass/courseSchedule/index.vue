<template>
  <div class="gi_table_page">
    <GiTable
      title="course schedule management"
      row-key="id"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }"
      :pagination="pagination"

      :disabled-tools="['size']"
      :disabled-column-keys="['name']"
      @refresh="search"
    >
      <template #toolbar-left>
        <a-input-search v-model="queryForm.semester" placeholder="please enter the semester" allow-clear @search="search" />
        <a-input-search v-model="queryForm.classroom" placeholder="please enter the classroom" allow-clear @search="search" />

        <a-input-search v-model="queryForm.dayOfWeek" placeholder="please enter the day of week" allow-clear @search="search" />
        <a-date-picker
          v-model="queryForm.startTime"

          placeholder="please select the start time"
          show-time
          format="YYYY-MM-DD HH:mm:ss"
          style="height: 32px"

        />
        <a-date-picker
          v-model="queryForm.endTime"
          placeholder="please select the end time"
          show-time
          format="YYYY-MM-DD HH:mm:ss"
          style="height: 32px"

        />
        <a-input-search v-model="queryForm.maxStudent" placeholder="please enter the max student" allow-clear @search="search" />
        <a-input-search v-model="queryForm.selected" placeholder="please enter the selected" allow-clear @search="search" />
        <a-select

          v-model="queryForm.status"
          :options="course_schedule_status"
          placeholder="please select the status"
          allow-clear
          style="width: 150px"
          @change="search"

        />
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>reset</template>
        </a-button>
      </template>

      <template #toolbar-right>
        <a-button v-permission="['selectclass:courseSchedule:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
        <a-button v-permission="['selectclass:courseSchedule:export']" @click="onExport">

          <template #icon><icon-download /></template>
          <template #default>export</template>
        </a-button>
      </template>

      <template #status="{ record }">
        <GiCellTag :value="record.status" :dict="course_schedule_status" />
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['selectclass:courseSchedule:detail']" title="details" @click="onDetail(record)">details</a-link>
          <a-link v-permission="['selectclass:courseSchedule:update']" title="modify" @click="onUpdate(record)">modify</a-link>
          <a-link

            v-permission="['selectclass:courseSchedule:delete']"
            status="danger"
            :disabled="record.disabled"
            :title="record.disabled ? 'cannot delete' : 'delete'"
            @click="onDelete(record)"
          >
            delete
          </a-link>
        </a-space>

      </template>
    </GiTable>

    <CourseScheduleAddModal ref="CourseScheduleAddModalRef" @save-success="search" />
    <CourseScheduleDetailDrawer ref="CourseScheduleDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import CourseScheduleAddModal from './CourseScheduleAddModal.vue'
import CourseScheduleDetailDrawer from './CourseScheduleDetailDrawer.vue'
import { type CourseScheduleQuery, type CourseScheduleResp, deleteCourseSchedule, exportCourseSchedule, listCourseSchedule } from '@/apis/selectclass/courseSchedule'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'CourseSchedule' })

const { course_schedule_status } = useDict('course_schedule_status')

const queryForm = reactive<CourseScheduleQuery>({
  courseId: undefined,
  teacherId: undefined,
  semester: undefined,
  classroom: undefined,
  dayOfWeek: undefined,
  startTime: undefined,
  endTime: undefined,
  maxStudent: undefined,
  selected: undefined,
  status: undefined,
  sort: ['id,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listCourseSchedule({ ...queryForm, ...page }), { immediate: true })
const columns = ref<TableInstanceColumns[]>([
  { title: 'course schedule ID', dataIndex: 'id', slotName: 'id' },
  { title: 'course', dataIndex: 'courseId', slotName: 'courseId' },
  { title: 'teacher', dataIndex: 'teacherId', slotName: 'teacherId' },

  { title: 'semester', dataIndex: 'semester', slotName: 'semester' },
  { title: 'classroom', dataIndex: 'classroom', slotName: 'classroom' },
  { title: 'day of week', dataIndex: 'dayOfWeek', slotName: 'dayOfWeek' },
  { title: 'start time', dataIndex: 'startTime', slotName: 'startTime' },

  { title: 'end time', dataIndex: 'endTime', slotName: 'endTime' },
  { title: 'max student', dataIndex: 'maxStudent', slotName: 'maxStudent' },
  { title: 'selected', dataIndex: 'selected', slotName: 'selected' },
  { title: 'status', dataIndex: 'status', slotName: 'status' },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['selectclass:courseSchedule:detail', 'selectclass:courseSchedule:update', 'selectclass:courseSchedule:delete']),
  },
])

// 重置
const reset = () => {
  queryForm.courseId = undefined
  queryForm.teacherId = undefined
  queryForm.semester = undefined
  queryForm.classroom = undefined
  queryForm.dayOfWeek = undefined
  queryForm.startTime = undefined
  queryForm.endTime = undefined
  queryForm.maxStudent = undefined
  queryForm.selected = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: CourseScheduleResp) => {
  return handleDelete(() => deleteCourseSchedule(record.id), {
    content: `are you sure to delete the course schedule?`,
    showModal: true,
  })
}


// 导出
const onExport = () => {
  useDownload(() => exportCourseSchedule(queryForm))
}

const CourseScheduleAddModalRef = ref<InstanceType<typeof CourseScheduleAddModal>>()
// 新增
const onAdd = () => {
  CourseScheduleAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: CourseScheduleResp) => {
  CourseScheduleAddModalRef.value?.onUpdate(record.id)
}

const CourseScheduleDetailDrawerRef = ref<InstanceType<typeof CourseScheduleDetailDrawer>>()
// 详情
const onDetail = (record: CourseScheduleResp) => {
  CourseScheduleDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
