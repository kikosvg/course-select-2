<template>
  <div class="gi_table_page">
    <GiTable
      title="course management"
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
        <a-input-search v-model="queryForm.courseNo" placeholder="please enter the course ID" allow-clear @search="search" />
        <a-input-search v-model="queryForm.name" placeholder="please enter the course name" allow-clear @search="search" />
        <a-input-search v-model="queryForm.credit" placeholder="please enter the credit(10 points)" allow-clear @search="search" />

        <a-input-search v-model="queryForm.hours" placeholder="please enter the hours" allow-clear @search="search" />
        <a-select
          v-model="queryForm.type"
          :options="course_type"
          placeholder="please select the type"
          allow-clear

          style="width: 150px"
          @change="search"
        />
        <a-select
          v-model="queryForm.type"
          :options="course_status"
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
        <a-button v-permission="['selectclass:course:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
        <a-button v-permission="['selectclass:course:export']" @click="onExport">
          <template #icon><icon-download /></template>

          <template #default>export</template>
        </a-button>
      </template>
      <template #type="{ record }">
        <GiCellTag :value="record.type" :dict="course_type" />
      </template>
      <template #status="{ record }">
        <GiCellTag :value="record.status" :dict="course_status" />
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['selectclass:course:detail']" title="details" @click="onDetail(record)">details</a-link>
          <a-link v-permission="['selectclass:course:update']" title="modify" @click="onUpdate(record)">modify</a-link>
          <a-link
            v-permission="['selectclass:course:delete']"

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

    <CourseAddModal ref="CourseAddModalRef" @save-success="search" />
    <CourseDetailDrawer ref="CourseDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import CourseAddModal from './CourseAddModal.vue'
import CourseDetailDrawer from './CourseDetailDrawer.vue'
import { type CourseQuery, type CourseResp, deleteCourse, exportCourse, listCourse } from '@/apis/selectclass/course'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Course' })

const { course_type, course_status } = useDict('course_type', 'course_status')
const queryForm = reactive<CourseQuery>({
  courseNo: undefined,
  name: undefined,
  credit: undefined,
  hours: undefined,
  type: undefined,
  collegeId: undefined,
  status: undefined,
  sort: ['id,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listCourse({ ...queryForm, ...page }), { immediate: true })
const columns = ref<TableInstanceColumns[]>([
  { title: 'course ID', dataIndex: 'id', slotName: 'id' },
  { title: 'course ID', dataIndex: 'courseNo', slotName: 'courseNo' },
  { title: 'course name', dataIndex: 'name', slotName: 'name' },
  { title: 'credit(10 points)', dataIndex: 'credit', slotName: 'credit' },

  { title: 'hours', dataIndex: 'hours', slotName: 'hours' },
  { title: 'type', dataIndex: 'type', slotName: 'type' },
  { title: 'college ID', dataIndex: 'collegeId', slotName: 'collegeId' },
  { title: 'description', dataIndex: 'description', slotName: 'description' },

  { title: 'status', dataIndex: 'status', slotName: 'status' },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',

    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['selectclass:course:detail', 'selectclass:course:update', 'selectclass:course:delete']),
  },
])

// 重置
const reset = () => {
  queryForm.courseNo = undefined
  queryForm.name = undefined
  queryForm.credit = undefined
  queryForm.hours = undefined
  queryForm.type = undefined
  queryForm.collegeId = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: CourseResp) => {
  return handleDelete(() => deleteCourse(record.id), {
    content: `是否确定删除该条数据？`,
    showModal: true,
  })
}

// 导出
const onExport = () => {
  useDownload(() => exportCourse(queryForm))
}

const CourseAddModalRef = ref<InstanceType<typeof CourseAddModal>>()
// 新增
const onAdd = () => {
  CourseAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: CourseResp) => {
  CourseAddModalRef.value?.onUpdate(record.id)
}

const CourseDetailDrawerRef = ref<InstanceType<typeof CourseDetailDrawer>>()
// 详情
const onDetail = (record: CourseResp) => {
  CourseDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
