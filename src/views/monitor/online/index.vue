<template>
  <div class="gi_table_page">
    <GiTable
      title=""
      row-key="id"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }"
      :pagination="pagination"
      :disabled-tools="['size']"
      @refresh="search"
    >
      <template #toolbar-left>
        <a-input-search v-model="queryForm.nickname" placeholder="search by nickname" allow-clear @search="search" />
        <DateRangePicker v-model="queryForm.loginTime" @change="search" :placeholder="['select start time', 'select end time']" />
        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>reset</template>


        </a-button>
      </template>
      <template #nickname="{ record }">{{ record.nickname }}({{ record.username }})</template>
      <template #action="{ record }">

        <a-space>
          <a-popconfirm
            type="warning"
            content="are you sure to kick out the user?"
            :ok-button-props="{ status: 'danger' }"
            @ok="handleKickout(record.token)"
          >

            <a-link
              v-permission="['monitor:online:kickout']"
              status="danger"
              :title="currentToken === record.token ? 'can not kick out yourself' : 'kick out'"
              :disabled="currentToken === record.token"
            >
              kick out

            </a-link>
          </a-popconfirm>
        </a-space>
      </template>
    </GiTable>
  </div>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { type OnlineUserQuery, kickout, listOnlineUser } from '@/apis/monitor'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import DateRangePicker from '@/components/DateRangePicker/index.vue'
import { useUserStore } from '@/stores'
import { useTable } from '@/hooks'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'MonitorOnline' })

const userStore = useUserStore()
const currentToken = userStore.token

const queryForm = reactive<OnlineUserQuery>({
  sort: ['createTime,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
} = useTable((page) => listOnlineUser({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  {
    title: 'number',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
  },

  { title: 'nickname', dataIndex: 'nickname', slotName: 'nickname', ellipsis: true, tooltip: true },
  { title: 'login IP', dataIndex: 'ip', ellipsis: true, tooltip: true },
  { title: 'login address', dataIndex: 'address', ellipsis: true, tooltip: true },
  { title: 'browser', dataIndex: 'browser', ellipsis: true, tooltip: true },

  { title: 'terminal system', dataIndex: 'os', ellipsis: true, tooltip: true },
  { title: 'login time', dataIndex: 'loginTime', width: 180 },
  { title: 'last active time', dataIndex: 'lastActiveTime', width: 180 },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,
    show: has.hasPermOr(['monitor:online:kickout']),

  },
]

// 重置
const reset = () => {
  queryForm.nickname = undefined
  queryForm.loginTime = undefined
  search()
}

// 强退
const handleKickout = (token: string) => {
  kickout(token).then(() => {
    search()
    Message.success('kick out successfully')
  })

}
</script>

<style scoped lang="scss"></style>
