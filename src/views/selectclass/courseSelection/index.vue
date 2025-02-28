<template>
  <div class="gi_table_page">
    <GiTable
      title="student course selection"
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
        <a-input
          v-model="queryForm.studentId"
          :options="student_id_enum"
          placeholder="please select the student"
          allow-clear
          style="width: 150px"
          @change="search"

        />
        <a-input
          v-model="queryForm.scheduleId"
          :options="schedule_id_enum"
          placeholder="please select the course schedule"
          allow-clear
          style="width: 150px"
          @change="search"

        />
        <a-date-picker
          v-model="queryForm.selectTime"
          placeholder="please select the select time"
          show-time
          format="YYYY-MM-DD HH:mm:ss"
          style="height: 32px"

        />
        <a-select
          v-model="queryForm.status"
          :options="select_course_status"
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
        <a-button v-permission="['selectclass:courseSelection:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>select course</template>
        </a-button>
        <a-button v-permission="['selectclass:courseSelection:export']" @click="onExport">

          <template #icon><icon-download /></template>
          <template #default>export</template>
        </a-button>
      </template>

      <template #status="{ record }">
        <GiCellTag :value="record.status" :dict="select_course_status" />
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['selectclass:courseSelection:detail']" title="details" @click="onDetail(record)">details</a-link>
          <a-link v-permission="['selectclass:courseSelection:update']" title="modify" @click="onUpdate(record)">modify</a-link>
          <a-link

            v-permission="['selectclass:courseSelection:delete']"
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

    <CourseSelectionAddModal ref="CourseSelectionAddModalRef" @save-success="search" />
    <CourseSelectionDetailDrawer ref="CourseSelectionDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import CourseSelectionAddModal from './CourseSelectionAddModal.vue'
import CourseSelectionDetailDrawer from './CourseSelectionDetailDrawer.vue'
import { type CourseSelectionQuery, type CourseSelectionResp, deleteCourseSelection, exportCourseSelection, listCourseSelection } from '@/apis/selectclass/courseSelection'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'CourseSelection' })

const { select_course_status } = useDict('select_course_status')

const queryForm = reactive<CourseSelectionQuery>({
  studentId: undefined,
  scheduleId: undefined,
  selectTime: undefined,
  status: undefined,
  sort: ['id,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listCourseSelection({ ...queryForm, ...page }), { immediate: true })
const columns = ref<TableInstanceColumns[]>([
  { title: 'select course record ID', dataIndex: 'id', slotName: 'id' },
  { title: 'student', dataIndex: 'studentId', slotName: 'studentId' },
  { title: 'course schedule', dataIndex: 'scheduleId', slotName: 'scheduleId' },

  { title: 'select time', dataIndex: 'selectTime', slotName: 'selectTime' },
  { title: 'score', dataIndex: 'score', slotName: 'score' },
  { title: 'status', dataIndex: 'status', slotName: 'status' },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['selectclass:courseSelection:detail', 'selectclass:courseSelection:update', 'selectclass:courseSelection:delete']),
  },
])

// 重置
const reset = () => {
  queryForm.studentId = undefined
  queryForm.scheduleId = undefined
  queryForm.selectTime = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: CourseSelectionResp) => {
  return handleDelete(() => deleteCourseSelection(record.id), {
    content: `are you sure to delete the data?`,
    showModal: true,
  })
}


// 导出
const onExport = () => {
  useDownload(() => exportCourseSelection(queryForm))
}

const CourseSelectionAddModalRef = ref<InstanceType<typeof CourseSelectionAddModal>>()
// 新增
const onAdd = () => {
  CourseSelectionAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: CourseSelectionResp) => {
  CourseSelectionAddModalRef.value?.onUpdate(record.id)
}

const CourseSelectionDetailDrawerRef = ref<InstanceType<typeof CourseSelectionDetailDrawer>>()
// 详情
const onDetail = (record: CourseSelectionResp) => {
  CourseSelectionDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
