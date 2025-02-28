<template>
  <a-modal
    v-model:visible="visible"
    title="modify basic information"
    :mask-closable="false"
    :esc-to-close="false"
    :width="width >= 500 ? 500 : '100%'"

    draggable
    @before-ok="save"
    @close="reset"
  >
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
  </a-modal>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { Message } from '@arco-design/web-vue'
import { updateUserBaseInfo } from '@/apis/system'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useUserStore } from '@/stores'
import { useResetReactive } from '@/hooks'

const { width } = useWindowSize()
const userStore = useUserStore()

const userInfo = computed(() => userStore.userInfo)
const visible = ref(false)
const formRef = ref<InstanceType<typeof GiForm>>()

const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const [form, resetForm] = useResetReactive({
  nickname: userInfo.value.nickname,
  gender: userInfo.value.gender,
})

const columns: Columns = reactive([
  {
    label: 'nickname',
    field: 'nickname',
    type: 'input',
    rules: [{ required: true, message: 'please enter the nickname' }],
  },

  {
    label: 'gender',
    field: 'gender',
    type: 'radio-group',
    options: [

      { label: 'male', value: 1 },
      { label: 'female', value: 2 },
      { label: 'unknown', value: 0, disabled: true },

    ],
    rules: [{ required: true, message: 'please select the gender' }],
  },
])


// 重置
const reset = () => {
  formRef.value?.formRef?.resetFields()
  resetForm()
}

// 保存
const save = async () => {
  const isInvalid = await formRef.value?.formRef?.validate()
  if (isInvalid) return false
  try {
    await updateUserBaseInfo(form)
    Message.success('update successfully')
    // 修改成功后，重新获取用户信息
    await userStore.getInfo()
    return true
  } catch (error) {
    return false
  }
}

// 修改
const onUpdate = async () => {
  reset()
  visible.value = true
}

defineExpose({ onUpdate })
</script>
