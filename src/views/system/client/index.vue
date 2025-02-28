<template>
  <div class="gi_table_page">
    <GiTable
      title=""
      row-key="id"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1500 }"
      :pagination="pagination"
      :disabled-tools="['size']"
      :disabled-column-keys="['clientKey']"
      @refresh="search"
    >
      <template #toolbar-left>
        <a-input-search v-model="queryForm.clientKey" placeholder="search client key" allow-clear @search="search" />
        <a-input-search v-model="queryForm.clientSecret" placeholder="search client secret" allow-clear @search="search" />
        <a-select
          v-model="queryForm.clientType"

          :options="client_type"
          placeholder="please select the client type"
          allow-clear
          style="width: 160px"
          @change="search"

        />
        <a-select
          v-model="queryForm.status" :options="DisEnableStatusList" placeholder="please select the status" allow-clear
          style="width: 150px"
          @change="search"

        />
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>reset</template>
        </a-button>
      </template>

      <template #toolbar-right>
        <a-button v-permission="['system:client:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
      </template>

      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:client:detail']" title="details" @click="onDetail(record)">details</a-link>
          <a-link v-permission="['system:client:update']" title="modify" @click="onUpdate(record)">modify</a-link>
          <a-link
            v-permission="['system:client:delete']"

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

    <ClientAddModal ref="ClientAddModalRef" @save-success="search" />
    <ClientDetailDrawer ref="ClientDetailDrawerRef" />
  </div>
</template>

<script setup lang="tsx">
import type { LabelValue } from '@arco-design/web-vue/es/tree-select/interface'
import ClientAddModal from './ClientAddModal.vue'
import ClientDetailDrawer from './ClientDetailDrawer.vue'
import { type ClientQuery, type ClientResp, deleteClient, listClient } from '@/apis/system/client'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { DisEnableStatusList } from '@/constant/common'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'
import CellCopy from '@/components/CellCopy/index.vue'
import GiCellTag from '@/components/GiCell/GiCellTag.vue'
import GiCellTags from '@/components/GiCell/GiCellTags.vue'
import GiCellStatus from '@/components/GiCell/GiCellStatus.vue'

defineOptions({ name: 'SystemClient' })

const {
  client_type,
  auth_type_enum,
} = useDict('client_type', 'auth_type_enum')

const queryForm = reactive<ClientQuery>({
  clientKey: '',
  clientSecret: '',
  authType: [] as string[],
  clientType: '',
  status: '',
  sort: ['id,desc'],
})
const formatAuthType = (data: string[]) => {
  return data.map((item: string) => {
    return auth_type_enum.value.find((d: LabelValue) => d.value === item).label
  })
}

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listClient({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  {
    title: 'index',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
    fixed: !isMobile() ? 'left' : undefined,
  },

  {
    title: 'client ID',
    dataIndex: 'clientId',
    slotName: 'clientId',
    ellipsis: true,
    tooltip: true,
    render: ({ record }) => {

      return (
        <CellCopy content={record.clientId} />
      )
    },
  },
  { title: 'client key', dataIndex: 'clientKey', slotName: 'clientKey', ellipsis: true, tooltip: true, align: 'center' },
  { title: 'client secret', dataIndex: 'clientSecret', slotName: 'clientSecret', ellipsis: true, tooltip: true, align: 'center' },

  {
    title: 'authentication type',
    dataIndex: 'authType',
    slotName: 'authType',
    ellipsis: true,
    tooltip: true,
    align: 'center',

    render: ({ record }) => {
      return (
        <GiCellTags data={formatAuthType(record.authType)} />
      )
    },
  },
  {
    title: 'client type',
    dataIndex: 'clientType',
    slotName: 'clientType',
    ellipsis: true,
    tooltip: true,
    align: 'center',

    render: ({ record }) => {
      return <GiCellTag value={record.clientType} dict={client_type.value} />
    },
  },
  { title: 'Token active frequency', dataIndex: 'activeTimeout', slotName: 'activeTimeout', width: 180, align: 'center', render: ({ record }) => `${record.activeTimeout} s` },
  { title: 'Token expiration', dataIndex: 'timeout', slotName: 'timeout', align: 'center', render: ({ record }) => `${record.timeout} s` },
  {
    title: 'status',

    dataIndex: 'status',
    slotName: 'status',
    align: 'center',
    render: ({ record }) => {
      return <GiCellStatus status={record.status} />
    },
  },
  { title: 'create user', dataIndex: 'createUserString', width: 140, ellipsis: true, tooltip: true, show: false },
  { title: 'create time', dataIndex: 'createTime', width: 180 },
  { title: 'update user', dataIndex: 'updateUserString', width: 140, ellipsis: true, tooltip: true, show: false },

  { title: 'update time', dataIndex: 'updateTime', width: 180, show: false },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['system:client:detail', 'system:client:update', 'system:client:delete']),
  },
]

// 重置
const reset = () => {
  queryForm.clientKey = ''
  queryForm.clientSecret = ''
  queryForm.authType = []
  queryForm.clientType = ''
  queryForm.status = ''
  search()
}

// 删除
const onDelete = (record: ClientResp) => {
  return handleDelete(() => deleteClient(record.id), {
    content: `are you sure to delete the client「${record.clientKey}(${record.clientId})」?`,
    showModal: true,
  })
}


const ClientAddModalRef = ref<InstanceType<typeof ClientAddModal>>()
// 新增
const onAdd = () => {
  ClientAddModalRef.value?.onAdd()
}

// 修改
const onUpdate = (record: ClientResp) => {
  ClientAddModalRef.value?.onUpdate(record.id)
}

const ClientDetailDrawerRef = ref<InstanceType<typeof ClientDetailDrawer>>()
// 详情
const onDetail = (record: ClientResp) => {
  ClientDetailDrawerRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss"></style>
