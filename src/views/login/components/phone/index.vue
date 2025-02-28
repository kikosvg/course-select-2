<template>
  <a-form
    ref="formRef"
    :model="form"
    :rules="rules"
    :label-col-style="{ display: 'none' }"
    :wrapper-col-style="{ flex: 1 }"
    size="large"
    @submit="handleLogin"
  >
    <a-form-item field="phone" hide-label>
      <a-input v-model="form.phone" placeholder="please enter phone number" :max-length="11" allow-clear />
    </a-form-item>
    <a-form-item field="captcha" hide-label>

      <a-input v-model="form.captcha" placeholder="please enter captcha" :max-length="4" allow-clear style="flex: 1 1" />
      <a-button
        class="captcha-btn"

        :loading="captchaLoading"
        :disabled="captchaDisable"
        size="large"
        @click="onCaptcha"
      >
        {{ captchaBtnName }}
      </a-button>
    </a-form-item>
    <a-form-item>
      <a-space direction="vertical" fill class="w-full">
        <a-button disabled class="btn" type="primary" :loading="loading" html-type="submit" size="large" long>login</a-button>
      </a-space>
    </a-form-item>

    <Verify
      ref="VerifyRef"
      :captcha-type="captchaType"
      :mode="captchaMode"
      :img-size="{ width: '330px', height: '155px' }"
      @success="getCaptcha"
    />
  </a-form>
</template>

<script setup lang="ts">
import { type FormInstance, Message } from '@arco-design/web-vue'
import type { BehaviorCaptchaReq } from '@/apis'
// import { type BehaviorCaptchaReq, getSmsCaptcha } from '@/apis'
import { useTabsStore, useUserStore } from '@/stores'
import * as Regexp from '@/utils/regexp'

const formRef = ref<FormInstance>()
const form = reactive({
  phone: '',
  captcha: '',
})

const rules: FormInstance['rules'] = {
  phone: [
    { required: true, message: 'please enter phone number' },
    { match: Regexp.Phone, message: 'please enter correct phone number' },
  ],
  captcha: [{ required: true, message: 'please enter captcha' }],

}


const userStore = useUserStore()
const tabsStore = useTabsStore()
const router = useRouter()
const loading = ref(false)
// 登录
const handleLogin = async () => {
  const isInvalid = await formRef.value?.validate()
  if (isInvalid) return
  try {
    loading.value = true
    await userStore.phoneLogin(form)
    tabsStore.reset()
    const { redirect, ...othersQuery } = router.currentRoute.value.query
    await router.push({
      path: (redirect as string) || '/',
      query: {
        ...othersQuery,
      },
    })
    Message.success('welcome to use')
  } catch (error) {
    form.captcha = ''

  } finally {
    loading.value = false
  }
}

const VerifyRef = ref<InstanceType<any>>()
const captchaType = ref('blockPuzzle')
const captchaMode = ref('pop')
const captchaLoading = ref(false)
// 弹出行为验证码
const onCaptcha = async () => {
  if (captchaLoading.value) return
  const isInvalid = await formRef.value?.validateField('phone')
  if (isInvalid) return
  // 重置行为参数
  VerifyRef.value.instance.refresh()
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

// 获取验证码
// eslint-disable-next-line unused-imports/no-unused-vars
const getCaptcha = async (captchaReq: BehaviorCaptchaReq) => {
  try {
    captchaLoading.value = true
    captchaBtnName.value = 'sending...'
    // await getSmsCaptcha(form.phone, captchaReq)
    captchaLoading.value = false
    captchaDisable.value = true
    captchaBtnName.value = `get captcha(${(captchaTime.value -= 1)}s)`
    // Message.success('短信发送成功')

    Message.success('only for demonstration, please view the code to cancel the relevant comments for actual use')
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
