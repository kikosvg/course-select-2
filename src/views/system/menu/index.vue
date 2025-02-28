<template>
  <div class="gi_table_page">
    <GiTable
      ref="tableRef"
      title=""
      row-key="id"
      :data="dataList"
      :columns="columns"
      :loading="loading"
      :scroll="{ x: '100%', y: '100%', minWidth: 1700 }"
      :pagination="false"
      :disabled-column-keys="['title']"
      @refresh="search"
    >
      <template #expand-icon="{ expanded }">
        <IconDown v-if="expanded" />
        <IconRight v-else />
      </template>
      <template #toolbar-left>
        <a-input v-model="title" placeholder="search menu title" allow-clear>
          <template #prefix><icon-search /></template>
        </a-input>
        <a-button @click="reset">

          <template #icon><icon-refresh /></template>
          <template #default>reset</template>
        </a-button>
      </template>

      <template #toolbar-right>
        <a-button v-permission="['system:menu:add']" type="primary" @click="onAdd()">
          <template #icon><icon-plus /></template>
          <template #default>add</template>
        </a-button>
        <a-tooltip content="expand/collapse">

          <a-button @click="onExpanded">
            <template #icon>
              <icon-list v-if="!isExpanded" />
              <icon-mind-mapping v-else />
            </template>
          </a-button>
        </a-tooltip>
      </template>
      <template #title="{ record }">
        <GiSvgIcon :name="record.icon" :size="15" />
        <span style="margin-left: 5px; vertical-align: middle">{{ record.title }}</span>
      </template>
      <template #type="{ record }">
        <a-tag v-if="record.type === 1" color="arcoblue">directory</a-tag>
        <a-tag v-if="record.type === 2" color="green">menu</a-tag>
        <a-tag v-if="record.type === 3">button</a-tag>
      </template>

      <template #status="{ record }">
        <GiCellStatus :status="record.status" />
      </template>
      <template #isExternal="{ record }">
        <a-tag v-if="record.isExternal" color="arcoblue" size="small">yes</a-tag>
        <a-tag v-else color="red" size="small">no</a-tag>
      </template>

      <template #isHidden="{ record }">
        <a-tag v-if="record.isHidden" color="arcoblue" size="small">yes</a-tag>
        <a-tag v-else color="red" size="small">no</a-tag>
      </template>

      <template #isCache="{ record }">
        <a-tag v-if="record.isCache" color="arcoblue" size="small">yes</a-tag>
        <a-tag v-else color="red" size="small">no</a-tag>
      </template>
      <template #action="{ record }">

        <a-space>
          <a-link v-permission="['system:menu:update']" title="modify" @click="onUpdate(record)">modify</a-link>
          <a-link v-permission="['system:menu:delete']" status="danger" title="delete" @click="onDelete(record)">delete</a-link>
          <a-link
            v-permission="['system:menu:add']"

            :disabled="![1, 2].includes(record.type)"
            :title="![1, 2].includes(record.type) ? 'cannot add submenu' : 'add'"
            @click="onAdd(record.id)"
          >

            新增
          </a-link>
        </a-space>
      </template>
    </GiTable>

    <MenuAddModal ref="MenuAddModalRef" :menus="dataList" @save-success="search" />
  </div>
</template>

<script setup lang="ts">
import MenuAddModal from './MenuAddModal.vue'
import { type MenuQuery, type MenuResp, deleteMenu, listMenu } from '@/apis/system/menu'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import type GiTable from '@/components/GiTable/index.vue'
import { useTable } from '@/hooks'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'SystemMenu' })

const queryForm = reactive<MenuQuery>({})

const {
  tableData,
  loading,
  search,
  handleDelete,
} = useTable(() => listMenu(queryForm), { immediate: true })

// 过滤树
const searchData = (title: string) => {
  const loop = (data: MenuResp[]) => {
    const result = [] as MenuResp[]
    data.forEach((item: MenuResp) => {
      if (item.title?.toLowerCase().includes(title.toLowerCase())) {
        result.push({ ...item })
      } else if (item.children) {
        const filterData = loop(item.children)
        if (filterData.length) {
          result.push({
            ...item,
            children: filterData,
          })
        }
      }
    })
    return result
  }
  return loop(tableData.value)
}

const title = ref('')
const dataList = computed(() => {
  if (!title.value) return tableData.value
  return searchData(title.value)
})

const columns: TableInstanceColumns[] = [
  { title: 'menu title', dataIndex: 'title', slotName: 'title', width: 170, fixed: !isMobile() ? 'left' : undefined },
  { title: 'type', dataIndex: 'type', slotName: 'type', align: 'center' },
  { title: 'status', dataIndex: 'status', slotName: 'status', align: 'center' },

  { title: 'sort', dataIndex: 'sort', align: 'center', show: false },
  { title: 'route address', dataIndex: 'path', ellipsis: true, tooltip: true },
  { title: 'component name', dataIndex: 'name', ellipsis: true, tooltip: true },

  { title: 'component path', dataIndex: 'component', minWidth: 180, ellipsis: true, tooltip: true },
  { title: 'permission', dataIndex: 'permission', minWidth: 180, ellipsis: true, tooltip: true },
  { title: 'external link', dataIndex: 'isExternal', slotName: 'isExternal', align: 'center' },

  { title: 'hidden', dataIndex: 'isHidden', slotName: 'isHidden', align: 'center' },
  { title: 'cache', dataIndex: 'isCache', slotName: 'isCache', align: 'center' },
  { title: 'create user', dataIndex: 'createUserString', ellipsis: true, tooltip: true, show: false },
  { title: 'create time', dataIndex: 'createTime', width: 180 },

  { title: 'update user', dataIndex: 'updateUserString', ellipsis: true, tooltip: true, show: false },

  { title: 'update time', dataIndex: 'updateTime', width: 180, show: false },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 160,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr(['system:menu:update', 'system:menu:delete', 'system:menu:add']),
  },
]

// 重置
const reset = () => {
  title.value = ''
}

// 删除
const onDelete = (record: MenuResp) => {
  return handleDelete(() => deleteMenu(record.id), {
    content: `are you sure to delete the menu「${record.title}」?`,
    showModal: true,
  })

}

const isExpanded = ref(false)
const tableRef = ref<InstanceType<typeof GiTable>>()
// 展开/折叠
const onExpanded = () => {
  isExpanded.value = !isExpanded.value
  tableRef.value?.tableRef?.expandAll(isExpanded.value)
}

const MenuAddModalRef = ref<InstanceType<typeof MenuAddModal>>()
// 新增
const onAdd = (parentId?: string) => {
  MenuAddModalRef.value?.onAdd(parentId)
}

// 修改
const onUpdate = (record: MenuResp) => {
  MenuAddModalRef.value?.onUpdate(record.id)
}
</script>

<style scoped lang="scss"></style>
