<template>
  <GiTable
    row-key="id"
    :data="dataList"
    :columns="columns"
    :loading="loading"
    :scroll="{ x: '100%', y: '100%', minWidth: 1000 }"
    :pagination="pagination"
    :disabled-tools="['size', 'setting']"
    @filter-change="filterChange"
    @refresh="search"
  >
    <template #toolbar-left>
      <a-input v-model="queryForm.createUserString" placeholder="search login user" allow-clear @change="search">
        <template #prefix><icon-search /></template>
      </a-input>

      <a-input v-model="queryForm.ip" placeholder="search login IP or location" allow-clear @change="search">
        <template #prefix><icon-search /></template>
      </a-input>

      <DateRangePicker v-model="queryForm.createTime" @change="search" />
      <a-button @click="reset">
        <template #icon><icon-refresh /></template>
        <template #default>reset</template>
      </a-button>
    </template>

    <template #toolbar-right>
      <a-button v-permission="['monitor:log:export']" @click="onExport">
        <template #icon><icon-download /></template>
        <template #default>export</template>
      </a-button>
    </template>

    <template #status="{ record }">
      <a-tag v-if="record.status === 1" color="green">
        <GiDot type="success" style="width: 5px; height: 5px" />
        <span style="margin-left: 5px">success</span>
      </a-tag>
      <a-tooltip v-else :content="record.errorMsg">

        <a-tag color="red" style="cursor: pointer">
          <GiDot type="danger" style="width: 5px; height: 5px" />
          <span style="margin-left: 5px">failed</span>
        </a-tag>
      </a-tooltip>

    </template>
  </GiTable>
</template>

<script setup lang="ts">
import dayjs from 'dayjs'
import { type LogQuery, exportLoginLog, listLog } from '@/apis/monitor'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import DateRangePicker from '@/components/DateRangePicker/index.vue'
import { useDownload, useTable } from '@/hooks'

defineOptions({ name: 'LoginLog' })

const queryForm = reactive<LogQuery>({
  module: '登录',
  createTime: [
    dayjs().subtract(6, 'day').startOf('day').format('YYYY-MM-DD HH:mm:ss'),
    dayjs().endOf('day').format('YYYY-MM-DD HH:mm:ss'),
  ],
  sort: ['createTime,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
} = useTable((page) => listLog({ ...queryForm, ...page }), { immediate: true })

const columns: TableInstanceColumns[] = [
  {
    title: 'number',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
  },

  { title: 'login time', dataIndex: 'createTime', width: 180 },
  { title: 'user nickname', dataIndex: 'createUserString', ellipsis: true, tooltip: true },
  { title: 'login behavior', dataIndex: 'description' },

  {
    title: 'status',
    slotName: 'status',
    align: 'center',
    filterable: {

      filters: [
        {
          text: 'success',
          value: '1',
        },

        {
          text: 'failed',
          value: '2',
        },

      ],
      filter: () => {
        return true
      },
      alignLeft: true,
    },
  },
  { title: 'login IP', dataIndex: 'ip', ellipsis: true, tooltip: true },
  { title: 'login location', dataIndex: 'address', ellipsis: true, tooltip: true },
  { title: 'browser', dataIndex: 'browser', ellipsis: true, tooltip: true },
  { title: 'terminal system', dataIndex: 'os', ellipsis: true, tooltip: true },
]

// 重置
const reset = () => {
  queryForm.ip = undefined
  queryForm.createUserString = undefined
  queryForm.createTime = [
    dayjs().subtract(6, 'day').startOf('day').format('YYYY-MM-DD HH:mm:ss'),
    dayjs().endOf('day').format('YYYY-MM-DD HH:mm:ss'),
  ]
  queryForm.status = undefined
  search()
}

// 过滤查询
const filterChange = (dataIndex, filteredValues) => {
  try {
    const slotName = columns[dataIndex.split('_').pop()].slotName as string
    queryForm[slotName] = filteredValues.join(',')
    search()
  } catch (error) {
    search()
  }
}

// 导出
const onExport = () => {
  useDownload(() => exportLoginLog(queryForm))
}
</script>

<style scoped lang="scss"></style>
