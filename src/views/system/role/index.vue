<template>
  <div class="gi_table_page">
    <GiTable
      title=""
      row-key="id"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :bordered="false"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }"
      :pagination="pagination"
      :disabled-tools="['size']"
      :disabled-column-keys="['name']"
      @refresh="search"
    >
      <template #toolbar-left>
        <a-input-search v-model="queryForm.description" placeholder="search name/code/description" allow-clear @search="search" />
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>reset</template>

        </a-button>
      </template>
      <template #toolbar-right>
        <a-button v-permission="['system:role:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
      </template>

      <template #dataScope="{ record }">
        <GiCellTag :value="record.dataScope" :dict="data_scope_enum" />
      </template>
      <template #isSystem="{ record }">
        <a-tag v-if="record.isSystem" color="red" size="small">yes</a-tag>
        <a-tag v-else color="arcoblue" size="small">no</a-tag>
      </template>
      <template #action="{ record }">

        <a-space>
          <a-link v-permission="['system:role:detail']" title="detail" @click="onDetail(record)">detail</a-link>
          <a-link v-permission="['system:role:update']" title="modify" @click="onUpdate(record)">modify</a-link>

          <a-link v-permission="['system:role:assign']" title="assign" @click="onAssign(record)">assign</a-link>
          <a-link
            v-permission="['system:role:delete']"

            status="danger"
            :disabled="record.isSystem"
            :title="record.isSystem ? 'system built-in data cannot be deleted' : 'delete'"
            @click="onDelete(record)"
          >
            delete
          </a-link>
        </a-space>

      </template>
    </GiTable>

    <RoleAddModal ref="RoleAddModalRef" @save-success="search" />
    <RoleUpdateDrawer ref="RoleUpdateDrawerRef" @save-success="search" />
    <RoleDetailDrawer ref="RoleDetailDrawerRef" />
    <RoleAssignModal ref="RoleAssignModalRef" />
  </div>
</template>

<script setup lang="ts">
import RoleAddModal from './RoleAddModal.vue'
import RoleUpdateDrawer from './RoleUpdateDrawer.vue'
import RoleDetailDrawer from './RoleDetailDrawer.vue'
import RoleAssignModal from './RoleAssignModal.vue'
import { type RoleQuery, type RoleResp, deleteRole, listRole } from '@/apis/system/role'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'SystemRole' })

const { data_scope_enum } = useDict('data_scope_enum')

const queryForm = reactive<RoleQuery>({
  sort: ['id,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listRole({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  {
    title: 'number',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
  },

  { title: 'name', dataIndex: 'name', slotName: 'name', ellipsis: true, tooltip: true },
  { title: 'code', dataIndex: 'code', ellipsis: true, tooltip: true },
  { title: 'data scope', dataIndex: 'dataScope', slotName: 'dataScope', ellipsis: true, tooltip: true },
  { title: 'sort', dataIndex: 'sort', align: 'center', show: false },

  { title: 'system built-in', dataIndex: 'isSystem', slotName: 'isSystem', align: 'center', show: false },
  { title: 'description', dataIndex: 'description', ellipsis: true, tooltip: true },
  { title: 'create user', dataIndex: 'createUserString', ellipsis: true, tooltip: true, show: false },
  { title: 'create time', dataIndex: 'createTime', width: 180 },

  { title: 'update user', dataIndex: 'updateUserString', ellipsis: true, tooltip: true, show: false },
  { title: 'update time', dataIndex: 'updateTime', width: 180, show: false },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 200,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr([
      'system:role:detail',
      'system:role:update',
      'system:role:delete',
      'system:role:assign',
    ]),
  },
]

// 重置
const reset = () => {
  queryForm.description = undefined
  search()
}

// 删除
const onDelete = (record: RoleResp) => {
  return handleDelete(() => deleteRole(record.id), {
    content: `are you sure to delete the role「${record.name}(${record.code})」?`,
    showModal: true,
  })

}

const RoleAddModalRef = ref<InstanceType<typeof RoleAddModal>>()
// 新增
const onAdd = () => {
  RoleAddModalRef.value?.onOpen()
}

const RoleUpdateDrawerRef = ref<InstanceType<typeof RoleUpdateDrawer>>()
// 修改
const onUpdate = (record: RoleResp) => {
  RoleUpdateDrawerRef.value?.onOpen(record.id)
}

const RoleDetailDrawerRef = ref<InstanceType<typeof RoleDetailDrawer>>()
// 详情
const onDetail = (record: RoleResp) => {
  RoleDetailDrawerRef.value?.onOpen(record.id)
}

const RoleAssignModalRef = ref<InstanceType<typeof RoleAssignModal>>()
// 分配
const onAssign = (record: RoleResp) => {
  RoleAssignModalRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
