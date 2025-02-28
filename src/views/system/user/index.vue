<template>
  <div class="gi_page">
    <!-- <a-row justify="space-between" align="center" class="header page_header">
      <a-space wrap>
        <div class="title">用户管理</div>
      </a-space>
    </a-row> -->
    <SplitPanel size="20%">
      <template #left>
        <DeptTree @node-click="handleSelectDept" />
      </template>
      <template #main>
        <a-row align="stretch" :gutter="14" class="h-full page_content">
          <a-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" :xxl="24" class="h-full overflow-hidden">
            <GiTable
              row-key="id"
              :data="dataList"
              :columns="columns"
              :loading="loading"
              :scroll="{ x: '100%', y: '100%', minWidth: 1500 }"
              :pagination="pagination"
              :disabled-tools="['size']"
              :disabled-column-keys="['nickname']"
              @refresh="search"
            >
              <template #top>
                <GiForm v-model="queryForm" :options="options" :columns="queryFormColumns" @search="search" @reset="reset"></GiForm>
              </template>
              <template #toolbar-left>
                <a-button v-permission="['system:user:add']" type="primary" @click="onAdd">
                  <template #icon><icon-plus /></template>
                  <template #default>add</template>
                </a-button>
                <a-button v-permission="['system:user:import']" @click="onImport">
                  <template #icon><icon-upload /></template>

                  <template #default>import</template>
                </a-button>
              </template>

              <template #toolbar-right>
                <a-button v-permission="['system:user:export']" @click="onExport">
                  <template #icon><icon-download /></template>
                  <template #default>export</template>
                </a-button>
              </template>

              <template #nickname="{ record }">
                <GiCellAvatar :avatar="record.avatar" :name="record.nickname" />
              </template>
              <template #gender="{ record }">
                <GiCellGender :gender="record.gender" />
              </template>
              <template #roleNames="{ record }">
                <GiCellTags :data="record.roleNames" />
              </template>
              <template #status="{ record }">
                <GiCellStatus :status="record.status" />
              </template>
              <template #isSystem="{ record }">
                <a-tag v-if="record.isSystem" color="red" size="small">yes</a-tag>
                <a-tag v-else color="arcoblue" size="small">no</a-tag>
              </template>
              <template #action="{ record }">

                <a-space>
                  <a-link v-permission="['system:user:detail']" title="detail" @click="onDetail(record)">detail</a-link>
                  <a-link v-permission="['system:user:update']" title="update" @click="onUpdate(record)">update</a-link>
                  <a-link
                    v-permission="['system:user:delete']"

                    status="danger"
                    :disabled="record.isSystem"
                    :title="record.isSystem ? 'system data cannot be deleted' : 'delete'"
                    @click="onDelete(record)"
                  >

                    delete
                  </a-link>
                  <a-dropdown>
                    <a-button v-if="has.hasPermOr(['system:user:resetPwd', 'system:user:updateRole'])" type="text" size="mini" title="more">

                      <template #icon>
                        <icon-more :size="16" />
                      </template>
                    </a-button>
                    <template #content>
                      <a-doption v-permission="['system:user:resetPwd']" title="reset password" @click="onResetPwd(record)">reset password</a-doption>
                      <a-doption v-permission="['system:user:updateRole']" title="assign role" @click="onUpdateRole(record)">assign role</a-doption>
                    </template>
                  </a-dropdown>

                </a-space>
              </template>
            </GiTable>
          </a-col>
        </a-row>
      </template>
    </SplitPanel>

    <UserAddDrawer ref="UserAddDrawerRef" @save-success="search" />
    <UserImportDrawer ref="UserImportDrawerRef" @save-success="search" />
    <UserDetailDrawer ref="UserDetailDrawerRef" />
    <UserResetPwdModal ref="UserResetPwdModalRef" />
    <UserUpdateRoleModal ref="UserUpdateRoleModalRef" @save-success="search" />
  </div>
</template>

<script setup lang="ts">
import DeptTree from './dept/index.vue'
import UserAddDrawer from './UserAddDrawer.vue'
import UserImportDrawer from './UserImportDrawer.vue'
import UserDetailDrawer from './UserDetailDrawer.vue'
import UserResetPwdModal from './UserResetPwdModal.vue'
import UserUpdateRoleModal from './UserUpdateRoleModal.vue'
import { type UserResp, deleteUser, exportUser, listUser } from '@/apis/system/user'
import type { Columns, Options } from '@/components/GiForm'
import type { TableInstanceColumns } from '@/components/GiTable/type'
import { DisEnableStatusList } from '@/constant/common'
import { useDownload, useResetReactive, useTable } from '@/hooks'
import { isMobile } from '@/utils'
import has from '@/utils/has'

defineOptions({ name: 'SystemUser' })

const options: Options = reactive({
  form: { layout: 'inline' },
  grid: { cols: { xs: 1, sm: 1, md: 2, lg: 3, xl: 3, xxl: 3 } },
  fold: { enable: true, index: 1, defaultCollapsed: true },
})
const [queryForm, resetForm] = useResetReactive({
  sort: ['t1.id,desc'],
})
const queryFormColumns: Columns = reactive([
  {
    type: 'input',
    field: 'description',
    formItemProps: {
      hideLabel: true,
    },
    props: {
      placeholder: 'search the username/nickname/description',
    },
  },

  {
    type: 'select',
    field: 'status',
    options: DisEnableStatusList,
    formItemProps: {
      hideLabel: true,
    },
    props: {
      placeholder: 'please select the status',
    },
  },

  {
    type: 'range-picker',
    field: 'createTime',
    span: { lg: 2, xl: 2, xxl: 1 },
    formItemProps: {
      placeholder: ['select start time', 'select end time'],
      hideLabel: true,
    },
  },
])

const {
  tableData: dataList,
  loading,
  pagination,
  search,
  handleDelete,
} = useTable((page) => listUser({ ...queryForm, ...page }), { immediate: false })
const columns: TableInstanceColumns[] = [
  {
    title: 'number',
    width: 66,
    align: 'center',
    render: ({ rowIndex }) => h('span', {}, rowIndex + 1 + (pagination.current - 1) * pagination.pageSize),
    fixed: !isMobile() ? 'left' : undefined,

  },
  {
    title: 'nickname',
    dataIndex: 'nickname',
    slotName: 'nickname',
    minWidth: 140,
    ellipsis: true,
    tooltip: true,
    fixed: !isMobile() ? 'left' : undefined,

  },
  { title: 'username', dataIndex: 'username', slotName: 'username', minWidth: 140, ellipsis: true, tooltip: true },
  { title: 'status', dataIndex: 'status', slotName: 'status', align: 'center' },
  { title: 'gender', dataIndex: 'gender', slotName: 'gender', align: 'center' },

  { title: 'department', dataIndex: 'deptName', minWidth: 180, ellipsis: true, tooltip: true },
  { title: 'role', dataIndex: 'roleNames', slotName: 'roleNames', minWidth: 165 },

  { title: 'phone', dataIndex: 'phone', minWidth: 170, ellipsis: true, tooltip: true },
  { title: 'email', dataIndex: 'email', minWidth: 170, ellipsis: true, tooltip: true },
  { title: 'system', dataIndex: 'isSystem', slotName: 'isSystem', width: 100, align: 'center', show: false },

  { title: 'description', dataIndex: 'description', minWidth: 130, ellipsis: true, tooltip: true },
  { title: 'create user', dataIndex: 'createUserString', width: 140, ellipsis: true, tooltip: true, show: false },
  { title: 'create time', dataIndex: 'createTime', width: 180 },

  { title: 'update user', dataIndex: 'updateUserString', width: 140, ellipsis: true, tooltip: true, show: false },
  { title: 'update time', dataIndex: 'updateTime', width: 180, show: false },

  {
    title: 'action',
    dataIndex: 'action',
    slotName: 'action',
    width: 190,
    align: 'center',
    fixed: !isMobile() ? 'right' : undefined,

    show: has.hasPermOr([
      'system:user:detail',
      'system:user:update',
      'system:user:delete',
      'system:user:resetPwd',
      'system:user:updateRole',
    ]),
  },
]

// 重置
const reset = () => {
  resetForm()
  search()
}

// 删除
const onDelete = (record: UserResp) => {
  return handleDelete(() => deleteUser(record.id), {
    content: `are you sure to delete the user「${record.nickname}(${record.username})」?`,
    showModal: true,
  })

}

// 导出
const onExport = () => {
  useDownload(() => exportUser(queryForm))
}

// 根据选中部门查询
const handleSelectDept = (keys: Array<any>) => {
  queryForm.deptId = keys.length === 1 ? keys[0] : undefined
  search()
}

const UserImportDrawerRef = ref<InstanceType<typeof UserImportDrawer>>()
// 导入
const onImport = () => {
  UserImportDrawerRef.value?.onOpen()
}

const UserAddDrawerRef = ref<InstanceType<typeof UserAddDrawer>>()
// 新增
const onAdd = () => {
  UserAddDrawerRef.value?.onAdd()
}

// 修改
const onUpdate = (record: UserResp) => {
  UserAddDrawerRef.value?.onUpdate(record.id)
}

const UserDetailDrawerRef = ref<InstanceType<typeof UserDetailDrawer>>()
// 详情
const onDetail = (record: UserResp) => {
  UserDetailDrawerRef.value?.onOpen(record.id)
}

const UserResetPwdModalRef = ref<InstanceType<typeof UserResetPwdModal>>()
// 重置密码
const onResetPwd = (record: UserResp) => {
  UserResetPwdModalRef.value?.onOpen(record.id)
}

const UserUpdateRoleModalRef = ref<InstanceType<typeof UserUpdateRoleModal>>()
// 分配角色
const onUpdateRole = (record: UserResp) => {
  UserUpdateRoleModalRef.value?.onOpen(record.id)
}
</script>

<style scoped lang="scss">
.page_header {
  flex: 0 0 auto;
}

.page_content {
  flex: 1;
  overflow: auto;
}
</style>
