<template>
  <div v-if="isDesktop" class="login pc">
    <h3 class="login-logo">
      <img v-if="logo" :src="logo" alt="logo" />
      <img v-else src="/logo.svg" alt="logo" />
      <span>{{ title }}</span>
    </h3>
    <a-row align="stretch" class="login-box">
      <a-col :xs="0" :sm="12" :md="13">
        <div class="login-left">
          <!-- <img class="login-left__img" src="@/assets/images/banner.png" alt="banner" /> -->
          <a-typography>
            <a-typography-title :heading="5">About Us</a-typography-title>
            <a-typography-paragraph>
              Hey there, awesome students!
              We totally get it. Course registration can feel like a huge mass. With all those tabs open, hunting for the right classes, and somehow landing that dreaded 8AM lecture you never wanted, it can be super stressful. That's exactly why we’ve created this platform. We want to help make the whole process smoother, quicker, and way less of a headache for you!
              Here’s how we’re here to help:<br>
              1. Course Selection Reminders: Say goodbye to last-minute craziness! Set up notifications so you’re always in the loop for important registration dates.<br>
              2. Automated Schedule Planning: Let our system do the heavy lifting and create the perfect, conflict-free schedule just for you.<br>
              3. Smart Optimization: Discover the best course combos based on your likes and schedule, but no more guessing!<br>
              Our mission? To help you stress less about your schedule and focus more on your education, your passions, and maybe even getting a little extra sleep!
              So why not give it a shot? Let’s make course registration a walk in the park! We can’t wait to welcome you aboard.
              Happy scheduling!
              The xxxxx Team
            </a-typography-paragraph>
          </a-typography>
        </div>
      </a-col>
      <a-col :xs="24" :sm="12" :md="11">
        <div class="login-right">
          <h3 v-if="isEmailLogin" class="login-right__title">email login</h3>
          <EmailLogin v-if="isEmailLogin" />
          <a-tabs
            v-else
            v-model:activeKey="activeTab"
            class="login-right__form"
          >
            <a-tab-pane key="1" title="account login">
              <component :is="AccountLogin" v-if="activeTab === '1'" />
            </a-tab-pane>
            <a-tab-pane key="2" title="phone login">
              <component :is="PhoneLogin" v-if="activeTab === '2'" />
            </a-tab-pane>
          </a-tabs>
          <div class="login-right__oauth">
            <a-divider orientation="center">other login methods</a-divider>
            <div class="list">
              <div
                v-if="isEmailLogin"
                class="mode item"
                @click="toggleLoginMode"
              >
                <icon-user /> account/phone login
              </div>

              <div v-else class="mode item" @click="toggleLoginMode">
                <icon-email /> email login
              </div>
            </div>
          </div>
        </div>
      </a-col>
    </a-row>

    <div v-if="isDesktop" class="footer">
      <div class="beian">
        <div class="below text">
          {{ appStore.getCopyright()
          }}{{ appStore.getForRecord() ? ` · ${appStore.getForRecord()}` : "" }}
        </div>
      </div>
    </div>

    <ToggleDark class="theme-btn" />
    <Background />
  </div>

  <div v-else class="login h5">
    <div class="login-logo">
      <img v-if="logo" :src="logo" alt="logo" />
      <img v-else src="/logo.svg" alt="logo" />
      <span>{{ title }}</span>
    </div>
    <a-row align="stretch" class="login-box">
      <a-col :xs="24" :sm="12" :md="11">
        <div class="login-right">
          <h3 v-if="isEmailLogin" class="login-right__title">email login</h3>
          <EmailLogin v-if="isEmailLogin" />
          <a-tabs
            v-else
            v-model:activeKey="activeTab"
            class="login-right__form"
          >
            <a-tab-pane key="1" title="account login">
              <component :is="AccountLogin" v-if="activeTab === '1'" />
            </a-tab-pane>
            <a-tab-pane key="2" title="phone login">
              <component :is="PhoneLogin" v-if="activeTab === '2'" />
            </a-tab-pane>
          </a-tabs>
        </div>
      </a-col>
    </a-row>
    <div class="login-right__oauth">
      <a-divider orientation="center">other login methods</a-divider>
      <div class="list">
        <div v-if="isEmailLogin" class="mode item" @click="toggleLoginMode">
          <icon-user /> account/phone login
        </div>

        <div v-else class="mode item" @click="toggleLoginMode">
          <icon-email /> email login
        </div>
        <a
          class="item"
          title="use Gitee account login"
          @click="onOauth('gitee')"
        >
          <GiSvgIcon name="gitee" :size="24" />
        </a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import Background from './components/background/index.vue'
import AccountLogin from './components/account/index.vue'
import PhoneLogin from './components/phone/index.vue'
import EmailLogin from './components/email/index.vue'
import { socialAuth } from '@/apis/auth'
import { useAppStore } from '@/stores'
import { useDevice } from '@/hooks'
import router from '@/router'

defineOptions({ name: 'Login' })

const { isDesktop } = useDevice()
const appStore = useAppStore()
const title = computed(() => appStore.getTitle())
const logo = computed(() => appStore.getLogo())

const isEmailLogin = ref(false)
const activeTab = ref('1')

// 切换登录模式
const toggleLoginMode = () => {
  isEmailLogin.value = !isEmailLogin.value
}

// 第三方登录授权
const onOauth = async (source: string) => {
  const { data } = await socialAuth(source)
  window.location.href = data.authorizeUrl
}
const toAboutUs = () => {
  router.push({ path: '/login/aboutUs' })
}
</script>

<style scoped lang="scss">
@media screen and (max-width: 570px) {
  .pc {
    display: none !important;
    background-color: white !important;
  }

  .login {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: center;
    background-color: var(--color-bg-5);
    color: #121314;

    &-logo {
      width: 100%;
      height: 104px;
      font-weight: 700;
      font-size: 20px;
      line-height: 32px;
      display: flex;
      padding: 0 20px;
      align-items: center;
      justify-content: start;
      background-image: url("/src/assets/images/login_h5.jpg");
      background-size: 100% 100%;
      box-sizing: border-box;

      img {
        width: 34px;
        height: 34px;
        margin-right: 8px;
      }
    }

    &-box {
      width: 100%;
      display: flex;
      z-index: 999;
    }
  }

  .login-right {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    padding: 30px 30px 0;
    box-sizing: border-box;

    &__title {
      color: var(--color-text-1);
      font-weight: 500;
      font-size: 20px;
      line-height: 32px;
      margin-bottom: 20px;
    }

    &__form {
      :deep(.arco-tabs-nav-tab) {
        display: flex;
        justify-content: start;
        align-items: center;
      }

      :deep(.arco-tabs-tab) {
        color: var(--color-text-2);
        margin: 0 20px 0 0;
      }

      :deep(.arco-tabs-tab-title) {
        font-size: 16px;
        font-weight: 500;
        line-height: 22px;
      }

      :deep(.arco-tabs-content) {
        margin-top: 10px;
      }

      :deep(.arco-tabs-tab-active),
      :deep(.arco-tabs-tab-title:hover) {
        color: rgb(var(--arcoblue-6));
      }

      :deep(.arco-tabs-nav::before) {
        display: none;
      }

      :deep(.arco-tabs-tab-title:before) {
        display: none;
      }
    }

    &__oauth {
      width: 100%;
      position: fixed;
      bottom: 0;
      left: 0;
      padding-bottom: 20px;

      // margin-top: auto;
      // margin-bottom: 20px;
      :deep(.arco-divider-text) {
        color: var(--color-text-4);
        font-size: 12px;
        font-weight: 400;
        line-height: 20px;
      }

      .list {
        align-items: center;
        display: flex;
        justify-content: center;
        width: 100%;

        .item {
          margin-right: 15px;
        }

        .mode {
          color: var(--color-text-2);
          font-size: 12px;
          font-weight: 400;
          line-height: 20px;
          padding: 6px 10px;
          align-items: center;
          border: 1px solid var(--color-border-3);
          border-radius: 32px;
          box-sizing: border-box;
          display: flex;
          height: 32px;
          justify-content: center;
          cursor: pointer;

          .icon {
            width: 21px;
            height: 20px;
          }
        }

        .mode svg {
          font-size: 16px;
          margin-right: 10px;
        }

        .mode:hover,
        .mode svg:hover {
          background: rgba(var(--primary-6), 0.05);
          border: 1px solid rgb(var(--primary-3));
          color: rgb(var(--arcoblue-6));
        }
      }
    }
  }

  .theme-btn {
    position: fixed;
    top: 20px;
    right: 30px;
    z-index: 9999;
  }

  .footer {
    align-items: center;
    box-sizing: border-box;
    position: absolute;
    bottom: 10px;
    z-index: 999;

    .beian {
      .text {
        font-size: 12px;
        font-weight: 400;
        letter-spacing: 0.2px;
        line-height: 20px;
        text-align: center;
      }

      .below {
        align-items: center;
        display: flex;
      }
    }
  }
}

@media screen and (min-width: 571px) {
  .h5 {
    display: none !important;
  }

  .login {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: var(--color-bg-5);

    &-logo {
      position: fixed;
      top: 20px;
      left: 30px;
      z-index: 9999;
      color: var(--color-text-1);
      font-weight: 500;
      font-size: 20px;
      line-height: 32px;
      margin-bottom: 20px;
      display: flex;
      justify-content: center;
      align-items: center;

      img {
        width: 34px;
        height: 34px;
        margin-right: 8px;
      }
    }

    &-box {
      width: 86%;
      max-width: 850px;
      height: 490px;
      display: flex;
      z-index: 999;
      box-shadow: 0 2px 4px 2px rgba(0, 0, 0, 0.08);
    }
  }

  .login-left {
    padding-left: 20px;
    padding-right: 20px;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    overflow: hidden;

    &__img {
      width: 100%;
      position: absolute;
      bottom: 0;
      right: 0;
      top: 50%;
      left: 50%;
      transform: translateX(-50%) translateY(-50%);
      transition: all 0.3s;
      object-fit: cover;
    }
  }

  .login-right {
    width: 100%;
    height: 100%;
    background: var(--color-bg-1);
    display: flex;
    flex-direction: column;
    padding: 30px 30px 0;
    box-sizing: border-box;

    &__title {
      color: var(--color-text-1);
      font-weight: 500;
      font-size: 20px;
      line-height: 32px;
      margin-bottom: 20px;
    }

    &__form {
      :deep(.arco-tabs-nav-tab) {
        display: flex;
        justify-content: center;
        align-items: center;
      }

      :deep(.arco-tabs-tab) {
        color: var(--color-text-2);
      }

      :deep(.arco-tabs-tab-title) {
        font-size: 16px;
        font-weight: 500;
        line-height: 22px;
      }

      :deep(.arco-tabs-content) {
        margin-top: 10px;
      }

      :deep(.arco-tabs-tab-active),
      :deep(.arco-tabs-tab-title:hover) {
        color: rgb(var(--arcoblue-6));
      }

      :deep(.arco-tabs-nav::before) {
        display: none;
      }

      :deep(.arco-tabs-tab-title:before) {
        display: none;
      }
    }

    &__oauth {
      margin-top: auto;
      margin-bottom: 20px;

      :deep(.arco-divider-text) {
        color: var(--color-text-4);
        font-size: 12px;
        font-weight: 400;
        line-height: 20px;
      }

      .list {
        align-items: center;
        display: flex;
        justify-content: center;
        width: 100%;

        .item {
          margin-right: 15px;
        }

        .mode {
          color: var(--color-text-2);
          font-size: 12px;
          font-weight: 400;
          line-height: 20px;
          padding: 6px 10px;
          align-items: center;
          border: 1px solid var(--color-border-3);
          border-radius: 32px;
          box-sizing: border-box;
          display: flex;
          height: 32px;
          justify-content: center;
          cursor: pointer;

          .icon {
            width: 21px;
            height: 20px;
          }
        }

        .mode svg {
          font-size: 16px;
          margin-right: 10px;
        }

        .mode:hover {
          background: rgba(var(--primary-6), 0.05);
          border: 1px solid rgb(var(--primary-3));
          color: rgb(var(--arcoblue-6));
        }
      }
    }
  }

  .theme-btn {
    position: fixed;
    top: 20px;
    right: 30px;
    z-index: 9999;
  }

  .footer {
    align-items: center;
    box-sizing: border-box;
    position: absolute;
    bottom: 10px;
    z-index: 999;

    .beian {
      .text {
        font-size: 12px;
        font-weight: 400;
        letter-spacing: 0.2px;
        line-height: 20px;
        text-align: center;
      }

      .below {
        align-items: center;
        display: flex;
      }
    }
  }
}
</style>
