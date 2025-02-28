<template>
  <a-card title="security settings" bordered class="gradient-card">
    <div v-for="item in modeList" :key="item.title">
      <div class="item">

        <div class="icon-wrapper"><GiSvgIcon :name="item.icon" :size="26" /></div>
        <div class="info">
          <div class="info-top">
            <span class="label">{{ item.title }}</span>
            <span class="bind">
              <icon-check-circle-fill v-if="item.status" :size="14" class="success" />
              <icon-exclamation-circle-fill v-else :size="14" class="warning" />
              <span style="font-size: 12px" :class="item.status ? 'success' : 'warning'">{{ item.statusString }}</span>
            </span>
          </div>
          <div class="info-desc">
            <span class="value">{{ item.value }}</span>
            {{ item.subtitle }}
          </div>
        </div>
        <div class="btn-wrapper">
          <a-button
            v-if="item.jumpMode === 'modal'"
            class="btn"
            :type="item.status ? 'secondary' : 'primary'"
            @click="onUpdate(item.type, item.status)"
          >
            {{ ['password'].includes(item.type) || item.status ? 'modify' : 'bind' }}
          </a-button>
        </div>
      </div>

    </div>
  </a-card>

  <VerifyModel ref="verifyModelRef" />
</template>

<script setup lang="ts">
import type { ModeItem } from '../type'
import VerifyModel from '../components/VerifyModel.vue'
import { useUserStore } from '@/stores'

const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)

const modeList = ref<ModeItem[]>([])
modeList.value = [
  {
    title: 'security phone',
    icon: 'phone-color',
    value: userInfo.value.phone,
    subtitle: `${userInfo.value.phone ? '' : 'phone'} can be used for login, identity verification, password retrieval, and notification reception`,
    type: 'phone',
    jumpMode: 'modal',
    status: !!userInfo.value.phone,

    statusString: userInfo.value.phone ? 'bound' : 'not bound',
  },

  {
    title: 'security email',
    icon: 'email-color',
    value: userInfo.value.email,
    subtitle: `${userInfo.value.email ? '' : 'email'} can be used for login, identity verification, password retrieval, and notification reception`,
    type: 'email',
    jumpMode: 'modal',
    status: !!userInfo.value.email,
    statusString: userInfo.value.email ? 'bound' : 'not bound',

  },
  {
    title: 'login password',
    icon: 'password-color',
    subtitle: userInfo.value.pwdResetTime ? 'for your account security, it is recommended to modify the password regularly' : 'please set the password, and login with the account and password',
    type: 'password',
    jumpMode: 'modal',
    status: !!userInfo.value.pwdResetTime,
    statusString: userInfo.value.pwdResetTime ? 'set' : 'not set',

  },
]

const verifyModelRef = ref<InstanceType<typeof VerifyModel>>()
// 修改
const onUpdate = (type: string) => {
  verifyModelRef.value?.open(type)
}
</script>

<style scoped lang="scss"></style>
