<template>
  <div ref="containerRef" class="detail">
    <div class="detail_header">
      <a-affix :target="(containerRef as HTMLElement)">
        <a-page-header title="notice" :subtitle="title" @back="onBack">
          <template #extra>
            <a-button type="primary" @click="save">
              <template #icon>

                <icon-save v-if="isUpdate" />
                <icon-send v-else />
              </template>
              <template #default>
                {{ isUpdate ? 'save' : 'publish' }}
              </template>
            </a-button>

          </template>
        </a-page-header>
      </a-affix>
    </div>
    <div class="detail_content" style="display: flex; flex-direction: column;">
      <GiForm ref="formRef" v-model="form" :options="options" :columns="columns">
        <template #noticeUsers>
          <a-select
            v-model="form.noticeUsers"
            :options="userList"
            multiple
            :max-tag-count="4"
            :allow-clear="true"
          />
          <a-tooltip content="select user">
            <a-button @click="onOpenSelectUser">
              <template #icon>
                <icon-plus />

              </template>
            </a-button>
          </a-tooltip>
        </template>
      </GiForm>
      <div style="flex: 1;">
        <AiEditor v-model="form.content" />
      </div>
    </div>

    <a-modal
      v-model:visible="visible"
      title="用户选择"
      :mask-closable="false"
      :esc-to-close="false"
      :width="width >= 1100 ? 1100 : '100%'"
      draggable
      @close="reset"
    >
      <UserSelect v-if="visible" ref="UserSelectRef" v-model:value="form.noticeUsers" @select-user="onSelectUser" />
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import AiEditor from './components/index.vue'
import { addNotice, getNotice, updateNotice } from '@/apis/system/notice'
import { listUserDict } from '@/apis'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import type { LabelValueState } from '@/types/global'
import { useTabsStore } from '@/stores'
import { useResetReactive } from '@/hooks'
import { useDict } from '@/hooks/app'

const { width } = useWindowSize()
const route = useRoute()
const router = useRouter()
const tabsStore = useTabsStore()

const { id, type } = route.query
const isUpdate = computed(() => type === 'update')
const title = computed(() => (isUpdate.value ? '修改' : '新增'))
const containerRef = ref<HTMLElement | null>()
const formRef = ref<InstanceType<typeof GiForm>>()
const { notice_type } = useDict('notice_type')

const options: Options = {
  form: { size: 'large' },
  grid: { cols: 2 },
  btns: { hide: true },
}

const [form, resetForm] = useResetReactive({
  title: '',
  type: '',
  effectiveTime: '',
  terminateTime: '',
  content: '',
  noticeScope: 1,
})

const columns: Columns = reactive([
  {
    label: 'title',
    field: 'title',
    type: 'input',
    props: {

      maxLength: 150,
      showWordLimit: true,
    },
    rules: [{ required: true, message: 'please enter the title' }],
  },

  {
    label: 'type',
    field: 'type',
    type: 'select',
    options: notice_type,
    rules: [{ required: true, message: 'please enter the type' }],
  },

  {
    label: 'effective time',
    field: 'effectiveTime',
    type: 'date-picker',
    props: {

      showTime: true,
    },
  },
  {
    label: 'terminate time',
    field: 'terminateTime',
    type: 'date-picker',
    props: {

      showTime: true,
    },
  },
  {
    label: 'notice scope',
    field: 'noticeScope',
    type: 'radio-group',
    options: [{ label: 'all', value: 1 }, { label: 'specified user', value: 2 }],
    rules: [{ required: true, message: 'please select the notice scope' }],
  },

  {
    label: 'specified user',
    field: 'noticeUsers',
    type: 'select',
    hide: () => {

      return form.noticeScope === 1
    },
    rules: [{ required: true, message: 'please select the specified user' }],
  },
])


// 修改
const onUpdate = async (id: string) => {
  resetForm()
  const res = await getNotice(id)
  Object.assign(form, res.data)
}

// 返回
const onBack = () => {
  router.back()
  tabsStore.closeCurrent(route.path)
}

// 保存
const save = async () => {
  const isInvalid = await formRef.value?.formRef?.validate()
  if (isInvalid) return false
  try {
    // 通知范围 所有人 去除指定用户
    form.noticeUsers = form.noticeScope === 1 ? null : form.noticeUsers
    if (isUpdate.value) {
      await updateNotice(form, id as string)
      Message.success('modify successfully')
    } else {
      await addNotice(form)
      Message.success('add successfully')
    }

    onBack()
    return true
  } catch (error) {
    console.error(error)
    return false
  }
}

// 打开用户选择窗口
const visible = ref(false)
const onOpenSelectUser = () => {
  visible.value = true
}

const UserSelectRef = ref()
// 重置
const reset = () => {
  UserSelectRef.value?.onClearSelected()
}

// 用户选择回调
const onSelectUser = (value: string[]) => {
  form.noticeUsers = value
  formRef.value?.formRef?.validateField('noticeUsers')
}

const userList = ref<LabelValueState[]>([])
onMounted(async () => {
  // 当id存在与type为update时，执行修改操作
  if (id && isUpdate.value) {
    await onUpdate(id as string)
  }
  // 获取所有用户
  const { data } = await listUserDict()
  userList.value = data.map((item) => ({ ...item, value: `${item.value}` }))
})
</script>

<style scoped lang="less"></style>
