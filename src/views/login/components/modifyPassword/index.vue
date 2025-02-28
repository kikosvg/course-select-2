<template>
  <a-form
    ref="formRef" :model="form" :rules="rules" layout="vertical" :label-col-style="{ lineHeight: '10px' }"
    :wrapper-col-style="{ flex: 1 }" size="large" @submit="onModify"
  >
    <a-form-item field="oldPassword" label="current password">
      <a-input-password v-model="form.oldPassword" placeholder="please enter current password" allow-clear />
    </a-form-item>
    <a-form-item field="newPassword" label="new password">

      <a-input-password v-model="form.newPassword" placeholder="please enter new password" allow-clear />
    </a-form-item>
    <a-form-item field="confirmPassword" label="confirm password">

      <a-input-password v-model="form.confirmPassword" placeholder="please enter new password again" allow-clear />
    </a-form-item>
    <a-form-item>

      <a-space direction="vertical" fill class="w-full">
        <a-button class="btn" type="primary" :loading="loading" html-type="submit" size="large" long>
          立即修改
        </a-button>
      </a-space>
    </a-form-item>
  </a-form>
</template>

<script setup lang="ts">
import { type FormInstance, Message } from '@arco-design/web-vue'
import { updateUserPassword } from '@/apis/system'
import { encryptByRsa } from '@/utils/encrypt'

interface Form {
  oldPassword: string
  newPassword: string
  confirmPassword?: string
}
const formRef = ref<FormInstance>()
const form = reactive<Form>({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
})

const rules: FormInstance['rules'] = {
  oldPassword: [
    { required: true, message: 'please enter current password' },
  ],

  newPassword: [{ required: true, message: 'please enter new password' }, {
    validator: (value, cd) => {
      return new Promise((resolve) => {
        if (value === form.oldPassword) {

          cd('new password cannot be the same as the old password')
        }
        resolve(true)
      })

    },
  }],
  confirmPassword: [{ required: true, message: 'please enter new password again' }, {
    validator: (value, cd) => {
      return new Promise((resolve) => {

        if (value !== form.newPassword) {
          cd('the two passwords are inconsistent')
        }
        resolve(true)

      })
    },
  }],
}
const router = useRouter()
const loading = ref(false)

// 登录
const onModify = async () => {
  const isInvalid = await formRef.value?.validate()
  if (isInvalid) return
  try {
    loading.value = true
    const params = {
      oldPassword: encryptByRsa(form.oldPassword) || '',
      newPassword: encryptByRsa(form.newPassword) || '',
    }
    await updateUserPassword(params)
    router.push({
      path: '/login',
    })
    Message.success('modify successfully')
  } catch (error) {


  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.arco-input-wrapper,
:deep(.arco-select-view-single) {
  height: 40px;
  border-radius: 4px;
  font-size: 13px;
}

.arco-input-wrapper.arco-input-error {
  background-color: rgb(var(--danger-1));
  border-color: rgb(var(--danger-3));
}

.arco-input-wrapper.arco-input-error:hover {
  background-color: rgb(var(--danger-1));
  border-color: rgb(var(--danger-6));
}

.arco-input-wrapper :deep(.arco-input) {
  font-size: 13px;
  color: var(--color-text-1);
}

.arco-input-wrapper:hover {
  border-color: rgb(var(--arcoblue-6));
}

.captcha-btn {
  height: 40px;
  margin-left: 12px;
  min-width: 98px;
  border-radius: 4px;
}

.btn {
  height: 40px;
}
</style>
