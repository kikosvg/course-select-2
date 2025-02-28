<template>
  <div class="gi_table_page">
    <GiTable
      title="class management"
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
        <a-input-search v-model="queryForm.name" placeholder="please enter the class name" allow-clear @search="search" />
        <a-input-search v-model="queryForm.code" placeholder="please enter the class code" allow-clear @search="search" />
        <a-input-search v-model="queryForm.majorId" placeholder="please enter the major ID(pure number)" allow-clear @search="search" />

        <a-input-search v-model="queryForm.grade" placeholder="please enter the grade" allow-clear @search="search" />
        <a-select
          v-model="queryForm.status"

          :options="class_status"
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
        <a-button v-permission="['selectclass:class:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add class</template>
        </a-button>
        <a-button v-permission="['selectclass:class:export']" @click="onExport">

          <template #icon><icon-download /></template>
          <template #default>export class data</template>
        </a-button>
      </template>

      <template #status="{ record }">
        <GiCellTag :value="record.status" :dict="class_status" />
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['selectclass:class:detail']" title="details" @click="onDetail(record)">details</a-link>
          <a-link v-permission="['selectclass:class:update']" title="modify" @click="onUpdate(record)">modify</a-link>
          <a-link

            v-permission="['selectclass:class:delete']"
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

    <ClassAddModal ref="ClassAddModalRef" @save-success="search" />
    <ClassDetailDrawer ref="ClassDetailDrawerRef" />
  </div>
</template>

<script setup lang="ts">
import ClassAddModal from './ClassAddModal.vue'
import ClassDetailDrawer from './ClassDetailDrawer.vue'
import { type ClassQuery, type ClassResp, deleteClass, exportClass, listClass } from '@/apis/selectclass/class'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useDownload, useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'Class' })

const { class_status } = useDict('class_status')

const queryForm = reactive<ClassQuery>({
  name: undefined,
  code: undefined,
  majorId: undefined,
  grade: undefined,
  status: undefined,
  sort: ['id,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listClass({ ...queryForm, ...page }), { immediate: true })
const columns = ref<TableInstanceColumns[]>([
  { title: 'class ID', dataIndex: 'id', slotName: 'id' },
  { title: 'class name', dataIndex: 'name', slotName: 'name' },
  { title: 'class code', dataIndex: 'code', slotName: 'code' },
  { title: 'major ID', dataIndex: 'majorId', slotName: 'majorId' },

  { title: 'grade', dataIndex: 'grade', slotName: 'grade' },
  { title: 'description', dataIndex: 'description', slotName: 'description' },
  { title: 'status', dataIndex: 'status', slotName: 'status' },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['selectclass:class:detail', 'selectclass:class:update', 'selectclass:class:delete']),
  },
])

// 重置
const reset = () => {
  queryForm.name = undefined
  queryForm.code = undefined
  queryForm.majorId = undefined
  queryForm.grade = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: ClassResp) => {
  return handleDelete(() => deleteClass(record.id), {
    content: `are you sure to delete the class「${record.name}」？`,
    showModal: true,
  })

}

// 导出
const onExport = () => {
  useDownload(() => exportClass(queryForm))
}

const ClassAddModalRef = ref<InstanceType<typeof ClassAddModal>>()
// 新增
const onAdd = () => {
  ClassAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: ClassResp) => {
  ClassAddModalRef.value?.onUpdate(record.id)
}

const ClassDetailDrawerRef = ref<InstanceType<typeof ClassDetailDrawer>>()
// 详情
const onDetail = (record: ClassResp) => {
  ClassDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
