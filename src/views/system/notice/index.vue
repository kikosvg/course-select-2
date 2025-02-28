<template>
  <div class="gi_table_page">
    <GiTable
      row-key="id"
      title=""
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1200 }"
      :pagination="pagination"
      :disabled-tools="['size']"
      :disabled-column-keys="['title']"
      @refresh="search"
    >
      <template #toolbar-left>
        <a-input-search v-model="queryForm.title" placeholder="search title" allow-clear @search="search" />
        <a-select
          v-model="queryForm.type"
          :options="notice_type"
          placeholder="please select the type"
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
        <a-button v-permission="['system:notice:add']" type="primary" @click="onAdd">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
      </template>

      <template #type="{ record }">
        <GiCellTag :value="record.type" :dict="notice_type" />
      </template>
      <template #status="{ record }">
        <GiCellTag :value="record.status" :dict="notice_status_enum" />
      </template>
      <template #action="{ record }">
        <a-space>
          <a-link v-permission="['system:notice:detail']" title="detail" @click="onDetail(record)">detail</a-link>
          <a-link v-permission="['system:notice:update']" title="modify" @click="onUpdate(record)">modify</a-link>

          <a-link v-permission="['system:notice:delete']" status="danger" title="delete" @click="onDelete(record)">delete</a-link>
        </a-space>
      </template>

    </GiTable>
  </div>
</template>

<script setup lang="ts">
import { type NoticeQuery, type NoticeResp, deleteNotice, listNotice } from '@/apis/system'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'SystemNotice' })

const { notice_type, notice_status_enum } = useDict('notice_type', 'notice_status_enum')

const router = useRouter()
const queryForm = reactive<NoticeQuery>({
  sort: ['id,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listNotice({ ...queryForm, ...page }), { immediate: true })
const columns: TableInstanceColumns[] = [
  {
    title: 'number',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
  },

  { title: 'title', dataIndex: 'title', slotName: 'title', minWidth: 200, ellipsis: true, tooltip: true },
  { title: 'type', dataIndex: 'type', slotName: 'type', align: 'center' },
  { title: 'status', dataIndex: 'status', slotName: 'status', align: 'center' },

  { title: 'effective time', dataIndex: 'effectiveTime', width: 180 },
  { title: 'terminate time', dataIndex: 'terminateTime', width: 180 },
  { title: 'create user', dataIndex: 'createUserString', show: false, ellipsis: true, tooltip: true },
  { title: 'create time', dataIndex: 'createTime', width: 180 },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['system:notice:detail', 'system:notice:update', 'system:notice:delete']),
  },
]

// 重置
const reset = () => {
  queryForm.title = undefined
  queryForm.type = undefined
  search()
}

// 删除
const onDelete = (record: NoticeResp) => {
  return handleDelete(() => deleteNotice(record.id), {
    content: `是否确定删除公告「${record.title}」？`,
    showModal: true,
  })
}

// 新增
const onAdd = () => {
  router.push({ path: '/system/notice/add' })
}

// 修改
const onUpdate = (record: NoticeResp) => {
  router.push({ path: '/system/notice/add', query: { id: record.id, type: 'update' } })
}

// 详情
const onDetail = (record: NoticeResp) => {
  router.push({ path: '/system/notice/detail', query: { id: record.id } })
}
</script>

<style scoped lang="scss"></style>
