<template>
  <a-spin :loading="loading">
    <a-form
      ref="formRef"
      :model="form"
      :rules="rules"
      auto-label-width
      label-align="left"
      :layout="width >= 500 ? 'horizontal' : 'vertical'"
      :disabled="!isUpdate"
      scroll-to-first-error
    >
      <a-form-item
        field="LOGIN_CAPTCHA_ENABLED"
        :label="loginConfig.LOGIN_CAPTCHA_ENABLED.name"
      >
        <a-switch
          v-model="form.LOGIN_CAPTCHA_ENABLED"
          type="round"
          :checked-value="1"
          :unchecked-value="0"
        >
          <template #checked>yes</template>
          <template #unchecked>no</template>
        </a-switch>
      </a-form-item>

      <a-space style="margin-bottom: 16px">
        <a-button v-if="!isUpdate" v-permission="['system:config:update']" type="primary" @click="onUpdate">
          <template #icon><icon-edit /></template>modify
        </a-button>
        <a-button v-if="!isUpdate" v-permission="['system:config:reset']" @click="onResetValue">
          <template #icon><icon-undo /></template>restore

        </a-button>
        <a-button v-if="isUpdate" type="primary" @click="handleSave">
          <template #icon><icon-save /></template>save
        </a-button>
        <a-button v-if="isUpdate" @click="reset">
          <template #icon><icon-refresh /></template>reset

        </a-button>
        <a-button v-if="isUpdate" @click="handleCancel">
          <template #icon><icon-undo /></template>cancel
        </a-button>
      </a-space>

    </a-form>
  </a-spin>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type FormInstance, Message, Modal } from '@arco-design/web-vue'
import { type LoginConfig, type OptionResp, listOption, resetOptionValue, updateOption } from '@/apis/system'
import { useResetReactive } from '@/hooks'

defineOptions({ name: 'LoginSetting' })
const { width } = useWindowSize()

const loading = ref<boolean>(false)
const formRef = ref<FormInstance>()
const [form] = useResetReactive({
  LOGIN_CAPTCHA_ENABLED: 1,
})
const rules: FormInstance['rules'] = {
  LOGIN_CAPTCHA_ENABLED: [{ required: true, message: 'please select' }],
}


const loginConfig = ref<LoginConfig>({
  LOGIN_CAPTCHA_ENABLED: {},
})

// 重置
const reset = () => {
  formRef.value?.resetFields()
  form.LOGIN_CAPTCHA_ENABLED = loginConfig.value.LOGIN_CAPTCHA_ENABLED.value || 0
}

const isUpdate = ref(false)
// 修改
const onUpdate = () => {
  isUpdate.value = true
}

// 取消
const handleCancel = () => {
  reset()
  isUpdate.value = false
}

const queryForm = {
  category: 'LOGIN',
}
// 查询列表数据
const getDataList = async () => {
  try {
    loading.value = true
    const { data } = await listOption(queryForm)
    loginConfig.value = data.reduce((obj: LoginConfig, option: OptionResp) => {
      obj[option.code] = { ...option, value: Number.parseInt(option.value) }
      return obj
    }, {})
    handleCancel()
  } finally {
    loading.value = false
  }
}

// 保存
const handleSave = async () => {
  const isInvalid = await formRef.value?.validate()
  if (isInvalid) return false
  await updateOption(
    Object.entries(form).map(([key, value]) => {
      return { id: loginConfig.value[key].id, code: key, value }
    }),
  )
  await getDataList()
  Message.success('save successfully')
}


// 恢复默认
const handleResetValue = async () => {
  await resetOptionValue(queryForm)
  Message.success('restore successfully')
  await getDataList()
}

const onResetValue = () => {
  Modal.warning({
    title: 'warning',
    content: 'are you sure to restore the login configuration to the default value?',
    hideCancel: false,
    maskClosable: false,
    onOk: handleResetValue,
  })
}

onMounted(() => {
  getDataList()
})
</script>

<style scoped lang="scss">
:deep(.arco-form-item.arco-form-item-has-help) {
  margin-bottom: 5px;
}

.input-width {
  width: 200px;
}
</style>
