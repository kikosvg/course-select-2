<template>
  <div class="gi_table_page">
    <GiTable
      title="teacher management"
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
        <a-input-search v-model="queryForm.teacherNo" placeholder="please enter the teacher number" allow-clear @search="search" />
        <a-input-search v-model="queryForm.name" placeholder="please enter the name" allow-clear @search="search" />

        <a-input-search v-model="queryForm.gender" placeholder="please enter the gender" allow-clear @search="search" />
        <a-select
          v-model="queryForm.collegeId"

          :options="college_id_enum"
          placeholder="please select the college"
          allow-clear
          style="width: 150px"
          @change="search"

        />
        <a-select
          v-model="queryForm.status"
          :options="teacher_status"
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
        <a-button v-permission="['selectclass:teacher:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
        <a-button v-permission="['selectclass:teacher:export']" @click="onExport">

          <template #icon><icon-download /></template>
          <template #default>export</template>
        </a-button>
      </template>

      <template #status="{ record }">
        <GiCellTag :value="record.status" :dict="teacher_status" />
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['selectclass:teacher:detail']" title="details" @click="onDetail(record)">details</a-link>
          <a-link v-permission="['selectclass:teacher:update']" title="modify" @click="onUpdate(record)">modify</a-link>
          <a-link

            v-permission="['selectclass:teacher:delete']"
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

    <TeacherAddModal ref="TeacherAddModalRef" @save-success="search" />
    <TeacherDetailDrawer ref="TeacherDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import TeacherAddModal from './TeacherAddModal.vue'
import TeacherDetailDrawer from './TeacherDetailDrawer.vue'
import { type TeacherQuery, type TeacherResp, deleteTeacher, exportTeacher, listTeacher } from '@/apis/selectclass/teacher'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Teacher' })

const { teacher_status } = useDict('teacher_status')

const queryForm = reactive<TeacherQuery>({
  teacherNo: undefined,
  name: undefined,
  gender: undefined,
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
} = useTable((page) => listTeacher({ ...queryForm, ...page }), { immediate: true })
const columns = ref<TableInstanceColumns[]>([
  { title: 'teacher ID', dataIndex: 'id', slotName: 'id' },
  { title: 'teacher number', dataIndex: 'teacherNo', slotName: 'teacherNo' },
  { title: 'name', dataIndex: 'name', slotName: 'name' },
  { title: 'gender', dataIndex: 'gender', slotName: 'gender' },

  { title: 'title', dataIndex: 'title', slotName: 'title' },
  { title: 'phone', dataIndex: 'phone', slotName: 'phone' },
  { title: 'email', dataIndex: 'email', slotName: 'email' },
  { title: 'college', dataIndex: 'collegeId', slotName: 'collegeId' },
  { title: 'status', dataIndex: 'status', slotName: 'status' },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['selectclass:teacher:detail', 'selectclass:teacher:update', 'selectclass:teacher:delete']),
  },
])

// 重置
const reset = () => {
  queryForm.teacherNo = undefined
  queryForm.name = undefined
  queryForm.gender = undefined
  queryForm.collegeId = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: TeacherResp) => {
  return handleDelete(() => deleteTeacher(record.id), {
    content: `are you sure to delete the data?`,
    showModal: true,
  })
}


// 导出
const onExport = () => {
  useDownload(() => exportTeacher(queryForm))
}

const TeacherAddModalRef = ref<InstanceType<typeof TeacherAddModal>>()
// 新增
const onAdd = () => {
  TeacherAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: TeacherResp) => {
  TeacherAddModalRef.value?.onUpdate(record.id)
}

const TeacherDetailDrawerRef = ref<InstanceType<typeof TeacherDetailDrawer>>()
// 详情
const onDetail = (record: TeacherResp) => {
  TeacherDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
