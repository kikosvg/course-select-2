<template>
  <div class="gi_table_page">
    <GiTable
      row-key="id"
      title="消息中心"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1000 }"
      :pagination="pagination"
      :disabled-tools="['size', 'setting']"
      :disabled-column-keys="['name']"
      :row-selection="{ type: 'checkbox', showCheckedAll: true }"
      :selected-keys="selectedKeys"
      @select-all="selectAll"
      @select="select"
      @refresh="search"
    >
      <template #toolbar-left>
        <a-input v-model="queryForm.title" placeholder="please enter the title" allow-clear @change="search">
          <template #prefix><icon-search /></template>
        </a-input>
        <a-select

          v-model="queryForm.isRead"
          placeholder="please select the status"
          allow-clear
          style="width: 150px"
          @change="search"

        >
          <a-option :value="false">unread</a-option>
          <a-option :value="true">read</a-option>
        </a-select>

        <a-button @click="reset">
          <template #icon><icon-refresh /></template>
          <template #default>reset</template>
        </a-button>
      </template>

      <template #toolbar-right>
        <a-button type="primary" status="danger" :disabled="!selectedKeys.length" :title="!selectedKeys.length ? 'please select' : ''" @click="onDelete">
          <template #icon><icon-delete /></template>
          <template #default>delete</template>
        </a-button>

        <a-button type="primary" :disabled="!selectedKeys.length" :title="!selectedKeys.length ? 'please select' : ''" @click="onRead">
          <template #default>mark as read</template>
        </a-button>

        <a-button type="primary" :disabled="selectedKeys.length" :title="!selectedKeys.length ? 'please select' : ''" @click="onReadAll">
          <template #default>all read</template>
        </a-button>
      </template>

      <template #title="{ record }">
        <a-tooltip :content="record.content"><span>{{ record.title }}</span></a-tooltip>
      </template>
      <template #isRead="{ record }">
        <a-tag :color="record.isRead ? '' : 'arcoblue'">
          {{ record.isRead ? 'read' : 'unread' }}
        </a-tag>
      </template>

      <template #type="{ record }">
        <GiCellTag :value="record.type" :dict="message_type" />
      </template>
    </GiTable>
  </div>
</template>

<script setup lang="ts">
import { Message, Modal } from '@arco-design/web-vue'
import { type MessageQuery, deleteMessage, listMessage, readMessage } from '@/apis'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { useTable } from '@/hooks'
import { useDict } from '@/hooks/app'

defineOptions({ name: 'SystemMessage' })

const { message_type } = useDict('message_type')

const queryForm = reactive<MessageQuery>({
  sort: ['createTime,desc'],
})

const {
  tableData: dataList,
  loading,
  pagination,
  selectedKeys,
  select,
  selectAll,
  search,
  handleDelete,
} = useTable((page) => listMessage({ ...queryForm, ...page }), { immediate: true })

const columns: TableInstanceColumns[] = [
  {
    title: 'number',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
  },

  { title: 'title', dataIndex: 'title', slotName: 'title', minWidth: 100, ellipsis: true, tooltip: true },
  { title: 'status', dataIndex: 'isRead', slotName: 'isRead', align: 'center' },
  { title: 'time', dataIndex: 'createTime', width: 180 },

  { title: 'type', dataIndex: 'type', slotName: 'type', width: 180, ellipsis: true, tooltip: true },

]

// 重置
const reset = () => {
  queryForm.title = undefined
  queryForm.type = undefined
  queryForm.isRead = undefined
  search()
}

// 删除
const onDelete = () => {
  if (!selectedKeys.value.length) {
    return Message.warning('please select the data')
  }
  return handleDelete(() => deleteMessage(selectedKeys.value), { showModal: false })

}

// 标记为已读
const onRead = async () => {
  if (!selectedKeys.value.length) {
    return Message.warning('please select the data')
  }
  await readMessage(selectedKeys.value)
  Message.success('operation successful')
  search()
}


// 全部已读
const onReadAll = async () => {
  Modal.warning({
    title: 'all read',
    content: 'are you sure to mark all messages as read?',
    hideCancel: false,
    maskClosable: false,
    onOk: async () => {

      await readMessage([])
      Message.success('operation successful')
      search()

    },
  })
}
</script>

<style scoped lang="scss"></style>
