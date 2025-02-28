<template>
  <div class="gi_table_page">
    <GiTable
      title=""
      row-key="id"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1300 }"
      :pagination="pagination"
      :disabled-tools="['size']"
      :disabled-column-keys="['name']"
      @refresh="search"
    >
      <template #toolbar-left>
        <a-input-search v-model="queryForm.description" placeholder="search by name/code/description" allow-clear @search="search" />
        <a-select
          v-model="queryForm.status"
          :options="DisEnableStatusList"
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
        <a-button v-permission="['system:storage:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
      </template>

      <template #name="{ record }">
        <a-space fill>
          <span>{{ record.name }}</span>
          <a-tag v-if="record.isDefault" color="arcoblue" size="small" class="gi_round">
            <template #default>default</template>
          </a-tag>
        </a-space>

      </template>
      <template #type="{ record }">
        <GiCellTag :value="record.type" :dict="storage_type_enum" />
      </template>
      <template #status="{ record }">
        <GiCellStatus :status="record.status" />
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:storage:update']" title="update" @click="onUpdate(record)">update</a-link>
          <a-link
            v-permission="['system:storage:delete']"
            status="danger"
            :disabled="record.isDefault"
            :title="record.isDefault ? 'default storage cannot be deleted' : 'delete'"
            @click="onDelete(record)"

          >
            delete
          </a-link>
        </a-space>

      </template>
    </GiTable>

    <StorageAddDrawer ref="StorageAddDrawerRef" @save-success="search" />
  </div>
</template>

<script setup lang="ts">
import StorageAddDrawer from './StorageAddDrawer.vue'
import { type StorageQuery, type StorageResp, deleteStorage, listStorage } from '@/apis/system/storage'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { DisEnableStatusList } from '@/constant/common'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'SystemStorage' })

const { storage_type_enum } = useDict('storage_type_enum')

const queryForm = reactive<StorageQuery>({
  sort: ['createTime,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listStorage({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  {
    title: 'number',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
  },

  { title: 'name', dataIndex: 'name', slotName: 'name', width: 140, ellipsis: true, tooltip: true },
  { title: 'code', dataIndex: 'code', ellipsis: true, tooltip: true },
  { title: 'status', dataIndex: 'status', slotName: 'status', align: 'center' },

  { title: 'type', dataIndex: 'type', slotName: 'type', align: 'center', ellipsis: true, tooltip: true },
  { title: 'access key', dataIndex: 'accessKey', ellipsis: true, tooltip: true },
  { title: 'endpoint', dataIndex: 'endpoint', ellipsis: true, tooltip: true },

  { title: 'bucket name', dataIndex: 'bucketName', ellipsis: true, tooltip: true },
  { title: 'domain', dataIndex: 'domain', ellipsis: true, tooltip: true },
  { title: 'description', dataIndex: 'description', ellipsis: true, tooltip: true },
  { title: 'create user', dataIndex: 'createUserString', ellipsis: true, tooltip: true, show: false },

  { title: 'create time', dataIndex: 'createTime', width: 180 },
  { title: 'update user', dataIndex: 'updateUserString', ellipsis: true, tooltip: true, show: false },
  { title: 'update time', dataIndex: 'updateTime', width: 180, show: false },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 130,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['system:storage:update', 'system:storage:delete']),
  },
]

// 重置
const reset = () => {
  queryForm.description = undefined
  queryForm.status = undefined
  search()
}

// 删除
const onDelete = (record: StorageResp) => {
  return handleDelete(() => deleteStorage(record.id), {
    content: `are you sure to delete the storage「${record.name}」?`,
    showModal: true,
  })
}


const StorageAddDrawerRef = ref<InstanceType<typeof StorageAddDrawer>>()
// 新增
const onAdd = () => {
  StorageAddDrawerRef.value?.onAdd()
}

// 修改
const onUpdate = (record: StorageResp) => {
  StorageAddDrawerRef.value?.onUpdate(record.id)
}
</script>

<style scoped lang="scss"></style>
