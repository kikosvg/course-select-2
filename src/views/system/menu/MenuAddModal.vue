<template>
  <a-modal
    v-model:visible="visible"
    :title="title"
    :mask-closable="false"
    :esc-to-close="false"
    :body-style="{ maxHeight: width >= 650 ? '76vh' : '100vh' }"
    :width="width >= 650 ? 650 : '100%'"
    draggable
    @before-ok="save"
    @close="reset"
  >
    <a-form ref="formRef" :model="form" :rules="formRules" auto-label-width :layout="width >= 700 ? 'horizontal' : 'vertical'">
      <a-form-item label="menu type">
        <a-radio-group v-model="form.type" type="button" :disabled="isUpdate" @change="onChangeType">
          <a-radio :value="1">directory</a-radio>

          <a-radio :value="2">menu</a-radio>
          <a-radio :value="3">button</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="parent menu">
        <a-tree-select

          v-model="form.parentId"
          placeholder="please select the parent menu"
          allow-clear
          allow-search
          :data="(menuSelectTree as any)"
          :fallback-option="false"
          :filter-tree-node="filterOptions"

        />
      </a-form-item>
      <a-row>
        <a-col v-bind="colProps">
          <a-form-item label="menu title">
            <a-input v-model.trim="form.title" placeholder="please enter the menu title" :max-length="30" show-word-limit allow-clear />
          </a-form-item>

        </a-col>
        <a-col v-bind="colProps">
          <a-form-item v-if="[1, 2].includes(form.type)" label="menu icon">
            <GiIconSelector v-model="form.icon" />
          </a-form-item>

          <a-form-item v-else label="permission">
            <a-input v-model.trim="form.permission" placeholder="system:user:add" allow-clear />
          </a-form-item>

        </a-col>
      </a-row>
      <a-row>
        <a-col v-bind="colProps">
          <a-form-item v-if="[1, 2].includes(form.type)" label="route address">
            <a-input v-model.trim="form.path" placeholder="please enter the route address" allow-clear />
          </a-form-item>
        </a-col>

        <a-col v-bind="colProps">
          <a-form-item v-if="form.type === 1 || (form.type === 2 && !form.isExternal)" label="redirect">
            <a-input v-model.trim="form.redirect" placeholder="please enter the redirect address" allow-clear />
          </a-form-item>

          <a-form-item v-if="form.type === 2 && form.isExternal" label="component path">
            <a-input v-model.trim="form.component" placeholder="please enter the component path" allow-clear />
          </a-form-item>

        </a-col>
      </a-row>
      <a-form-item v-if="form.type === 2 && !form.isExternal" label="component path">
        <a-select v-model="form.component" placeholder="please enter or select the component path" allow-clear allow-create :options="componentOptions">
          <template #label="{ data }">

            {{ data?.value }}
          </template>
        </a-select>
      </a-form-item>
      <a-row>
        <a-col v-bind="colProps">
          <a-form-item v-if="form.type === 1 || (form.type === 2 && !form.isExternal)" label="component name">
            <a-input v-model.trim="form.name" placeholder="please enter the component name" :max-length="50" show-word-limit allow-clear />
            <template #extra>
              <div v-if="componentName">

                <span>建议组件名称：</span>
                <a-tag checkable @check="inputComponentName">{{ componentName }}</a-tag>
              </div>
            </template>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row v-if="[1, 2].includes(form.type)" :gutter="16">
        <a-col :xs="12" :sm="12" :md="8" :lg="8" :xl="8" :xxl="8">
          <a-form-item label="is hidden">
            <a-switch
              v-model="form.isHidden"
              :checked-value="true"
              :unchecked-value="false"
              checked-text="yes"
              unchecked-text="no"

              type="round"
            />
          </a-form-item>
        </a-col>
        <a-col :xs="12" :sm="12" :md="8" :lg="8" :xl="8" :xxl="8">
          <a-form-item label="is cache">
            <a-switch
              v-model="form.isCache"
              :checked-value="true"
              :unchecked-value="false"
              checked-text="yes"
              unchecked-text="no"

              type="round"
            />
          </a-form-item>
        </a-col>
        <a-col :xs="12" :sm="12" :md="8" :lg="8" :xl="8" :xxl="8">
          <a-form-item v-if="form.type === 2" label="is external">
            <a-switch
              v-model="form.isExternal"

              :checked-value="true"
              :unchecked-value="false"
              checked-text="yes"
              unchecked-text="no"
              type="round"
            />

          </a-form-item>
        </a-col>
      </a-row>
      <a-form-item label="menu sort">
        <a-input-number v-model="form.sort" placeholder="please enter the menu sort" :min="1" mode="button" style="width: 150px" />
      </a-form-item>
      <a-form-item label="status">
        <a-switch

          v-model="form.status"
          type="round"
          :checked-value="1"
          :unchecked-value="2"
          checked-text="enable"
          unchecked-text="disable"
        />

      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { type ColProps, type FormInstance, Message, type TreeNodeData } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { mapTree } from 'xe-utils'
import { type MenuResp, addMenu, getMenu, updateMenu } from '@/apis/system/menu'
import { useResetReactive } from '@/hooks'
import { filterTree, transformPathToName } from '@/utils'
import { useComponentPaths } from '@/hooks/modules/useComponentPaths'

interface Props {
  menus: MenuResp[]
}
const props = withDefaults(defineProps<Props>(), {
  menus: () => [],
})

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const colProps: ColProps = { xs: 24, sm: 24, md: 12, lg: 12, xl: 12, xxl: 12 }

const dataId = ref('')
const visible = ref(false)
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? 'modify menu' : 'add menu'))
const formRef = ref<FormInstance>()


const [form, resetForm] = useResetReactive({
  type: 1,
  sort: 999,
  isExternal: false,
  isCache: false,
  isHidden: false,
  status: 1,
})

const componentName = computed(() => transformPathToName(form.path))

const { componentOptions } = useComponentPaths()

const rules: FormInstance['rules'] = {
  parentId: [{ required: true, message: 'please select the parent menu' }],
  title: [{ required: true, message: 'please enter the menu title' }],
  path: [{ required: true, message: 'please enter the route address' }],
  name: [{ required: true, message: 'please enter the component name' }],
  component: [{ required: true, message: 'please enter the component path' }],

  permission: [{ required: true, message: 'please enter the permission' }],

}
// eslint-disable-next-line vue/return-in-computed-property
const formRules = computed(() => {
  if ([1, 2].includes(form.type)) {
    const { title, name, path } = rules
    return { title, name, path } as FormInstance['rules']
  }
  if (form.type === 3) {
    const { parentId, title, permission } = rules
    return { parentId, title, permission } as FormInstance['rules']
  }
})

// 重置
const reset = () => {
  formRef.value?.resetFields()
  resetForm()
}

// 设置建议组件名
const inputComponentName = () => {
  form.name = componentName.value
}

// 切换类型清除校验
const onChangeType = () => {
  formRef.value?.clearValidate()
}

// 转换为菜单树
const menuSelectTree = computed(() => {
  const menus = JSON.parse(JSON.stringify(props.menus)) as MenuResp[]
  const data = filterTree(menus, (i) => [1, 2].includes(i.type))
  return mapTree(data, (i) => ({
    key: i.id,
    title: i.title,
    children: i.children,
  }))
})

// 过滤树
const filterOptions = (searchKey: string, nodeData: TreeNodeData) => {
  if (nodeData.title) {
    return nodeData.title.toLowerCase().includes(searchKey.toLowerCase())
  }
  return false
}

// 保存
const save = async () => {
  try {
    const isInvalid = await formRef.value?.validate()
    if (isInvalid) return false
    if (isUpdate.value) {
      await updateMenu(form, dataId.value)
      Message.success('modify successfully')
    } else {
      await addMenu(form)
      Message.success('add successfully')
    }

    emit('save-success')
    return true
  } catch (error) {
    return false
  }
}

// 新增
const onAdd = (id?: string) => {
  reset()
  form.parentId = id
  dataId.value = ''
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getMenu(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
