<template>
  <a-modal
    v-model:visible="visible" :title="title" :mask-closable="false" :esc-to-close="false"
    :width="width >= 600 ? 600 : '100%'" draggable @before-ok="save" @ok="saveAfter" @close="reset"
  >
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns">
      <template #captcha>
        <a-input v-model="form.captcha" placeholder="please enter the captcha" :max-length="6" allow-clear style="flex: 1 1" />
        <a-button
          class="captcha-btn" :loading="captchaLoading" :disabled="captchaDisable" size="large"
          @click="onCaptcha"
        >
          {{ captchaBtnName }}
        </a-button>
      </template>
    </GiForm>
    <Verify
      ref="VerifyRef" :captcha-type="captchaType" :mode="captchaMode"
      :img-size="{ width: '330px', height: '155px' }" @success="getCaptcha"
    />
  </a-modal>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { Message } from '@arco-design/web-vue'
import NProgress from 'nprogress'
import { type BehaviorCaptchaReq, getEmailCaptcha, updateUserEmail, updateUserPassword, updateUserPhone } from '@/apis'
import { encryptByRsa } from '@/utils/encrypt'
import { useUserStore } from '@/stores'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useResetReactive } from '@/hooks'
import * as Regexp from '@/utils/regexp'
import modalErrorWrapper from '@/utils/modal-error-wrapper'
import router from '@/router'

const { width } = useWindowSize()
const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const verifyType = ref()
const title = computed(
  () => `modify${verifyType.value === 'phone' ? 'phone' : verifyType.value === 'email' ? 'email' : 'password'}`,
)
const formRef = ref<InstanceType<typeof GiForm>>()


const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const [form, resetForm] = useResetReactive({
  phone: '',
  email: '',
  captcha: '',
  oldPassword: '',
  newPassword: '',
  rePassword: '',
})

const columns: Columns = reactive([
  {
    label: 'phone',
    field: 'phone',
    type: 'input',
    rules: [

      { required: true, message: 'please enter the phone' },
      { match: Regexp.Phone, message: 'please enter the correct phone' },
    ],

    hide: () => {
      return verifyType.value !== 'phone'
    },
  },
  {
    label: 'email',
    field: 'email',
    type: 'input',
    rules: [

      { required: true, message: 'please enter the email' },
      { match: Regexp.Email, message: 'please enter the correct email' },

    ],
    hide: () => {
      return verifyType.value !== 'email'
    },
  },
  {
    label: 'captcha',
    field: 'captcha',
    type: 'input',
    rules: [{ required: true, message: 'please enter the captcha' }],
    hide: () => {

      return !['phone', 'email'].includes(verifyType.value)
    },
  },
  {
    label: 'old password',
    field: 'oldPassword',
    type: 'input-password',
    rules: [{ required: true, message: 'please enter the old password' }],
    hide: () => {

      return !userInfo.value.pwdResetTime
    },
  },
  {
    label: 'new password',
    field: 'newPassword',
    type: 'input-password',
    rules: [

      { required: true, message: 'please enter the new password' },
      {
        validator: (value, callback) => {

          if (value === form.oldPassword) {
            callback('the new password cannot be the same as the current password')
          } else {
            callback()
          }

        },
      },
    ],
    hide: () => {
      return verifyType.value !== 'password'
    },
  },
  {
    label: 'confirm new password',
    field: 'rePassword',
    type: 'input-password',
    props: {

      placeholder: 'please enter the new password again',
    },

    rules: [
      { required: true, message: 'please enter the new password again' },
      {
        validator: (value, callback) => {

          if (value !== form.newPassword) {
            callback('the two passwords are inconsistent')
          } else {
            callback()
          }

        },
      },
    ],
    hide: () => {
      return verifyType.value !== 'password'
    },
  },
])

const VerifyRef = ref<InstanceType<any>>()
const captchaType = ref('blockPuzzle')
const captchaMode = ref('pop')
const captchaLoading = ref(false)
// 弹出行为验证码
const onCaptcha = async () => {
  if (captchaLoading.value) return
  const isInvalid = await formRef.value?.formRef?.validateField(verifyType.value === 'phone' ? 'phone' : 'email')
  if (isInvalid) return
  VerifyRef.value.show()
}

const captchaTimer = ref()
const captchaTime = ref(60)
const captchaBtnName = ref('get captcha')
const captchaDisable = ref(false)
// 重置验证码

const resetCaptcha = () => {
  window.clearInterval(captchaTimer.value)
  captchaTime.value = 60
  captchaBtnName.value = 'get captcha'
  captchaDisable.value = false

}

// 重置
const reset = () => {
  formRef.value?.formRef?.resetFields()
  resetForm()
  resetCaptcha()
}

// 获取验证码
const getCaptcha = async (captchaReq: BehaviorCaptchaReq) => {
  // 发送验证码
  try {
    captchaLoading.value = true
    captchaBtnName.value = 'sending...'
    if (verifyType.value === 'phone') {
      // await getSmsCaptcha(form.phone, captchaReq)

    } else if (verifyType.value === 'email') {
      await getEmailCaptcha(form.email, captchaReq)
    }
    captchaLoading.value = false
    captchaDisable.value = true
    captchaBtnName.value = `get captcha(${(captchaTime.value -= 1)}s)`
    // Message.success('发送成功')
    Message.success('only for demonstration, please cancel the relevant comments for actual use')

    captchaTimer.value = window.setInterval(() => {
      captchaTime.value -= 1
      captchaBtnName.value = `get captcha(${captchaTime.value}s)`
      if (captchaTime.value <= 0) {

        resetCaptcha()
      }
    }, 1000)
  } catch (error) {
    resetCaptcha()
  } finally {
    captchaLoading.value = false
  }
}

// 保存
const save = async () => {
  const isInvalid = await formRef.value?.formRef?.validate()
  if (isInvalid) return false
  try {
    if (verifyType.value === 'phone') {
      await updateUserPhone({
        phone: form.phone,
        captcha: form.captcha,
        oldPassword: encryptByRsa(form.oldPassword) as string,
      })
      Message.success('modify successfully')

    } else if (verifyType.value === 'email') {
      await updateUserEmail({
        email: form.email,
        captcha: form.captcha,
        oldPassword: encryptByRsa(form.oldPassword) as string,
      })
      Message.success('modify successfully')

    } else if (verifyType.value === 'password') {
      if (form.newPassword !== form.rePassword) {
        Message.error('the two new passwords are inconsistent')
        return false
      }

      if (form.newPassword === form.oldPassword) {
        Message.error('the new password cannot be the same as the old password')
        return false
      }

      await updateUserPassword({
        oldPassword: encryptByRsa(form.oldPassword) || '',
        newPassword: encryptByRsa(form.newPassword) || '',
      })
    }
    return true
  } catch (error) {
    return false
  }
}
const saveAfter = async () => {
  if (verifyType.value === 'password') {
    modalErrorWrapper({
      title: 'prompt',
      content: 'password modified successfully! please save the new password and use the new password to log in again',
      maskClosable: false,
      escToClose: false,
      okText: 're-login',
      async onOk() {

        NProgress.done()
        const userStore = useUserStore()
        await userStore.logoutCallBack()
        await router.replace('/login')
      },
    })
  } else {
    // 修改成功后，重新获取用户信息
    await userStore.getInfo()
  }
}

const visible = ref(false)
// 打开弹框
const open = (type: string) => {
  verifyType.value = type
  visible.value = true
}

defineExpose({ open })
</script>

<style scoped lang="scss">
.captcha-btn {
  margin-left: 12px;
  min-width: 98px;
  border-radius: 4px;
}
</style>
