<template>
  <a-drawer v-model:visible="visible" title="settings" width="300px" unmount-on-close :footer="false">
    <a-space :size="15" direction="vertical" fill>
      <a-alert v-if="settingOpen" :show-icon="false" type="info">

        click the「copy configuration」button, and paste the configuration to the src/config/settings.ts file.
      </a-alert>
      <a-divider v-if="settingOpen" orientation="center">system layout</a-divider>
      <a-row v-if="settingOpen" justify="center">

        <a-space>
          <a-badge>
            <template #content>
              <icon-check-circle-fill
                v-if="appStore.layout === 'left'" style="color: rgb(var(--success-6))"
                :size="16"
              ></icon-check-circle-fill>
            </template>
            <LayoutItem mode="left" @click="appStore.layout = 'left'"></LayoutItem>
            <p class="layout-text">default layout</p>
          </a-badge>
          <a-badge>
            <template #content>

              <icon-check-circle-fill
                v-if="appStore.layout === 'mix'" :size="16"
                style="color: rgb(var(--success-6))"
              ></icon-check-circle-fill>
            </template>
            <LayoutItem mode="mix" @click="appStore.layout = 'mix'"></LayoutItem>
            <p class="layout-text">mixed layout</p>
          </a-badge>
        </a-space>

      </a-row>

      <a-divider orientation="center">system theme</a-divider>
      <a-row justify="center">
        <ColorPicker

          theme="dark" :color="appStore.themeColor" :sucker-hide="true" :colors-default="defaultColorList"
          @change-color="changeColor"
        ></ColorPicker>
      </a-row>

      <a-divider v-if="settingOpen" orientation="center">interface display</a-divider>


      <a-descriptions v-if="settingOpen" :column="1" :align="{ value: 'right' }" :value-style="{ paddingRight: 0 }">
        <a-descriptions-item label="tab display">
          <a-switch v-model="appStore.tab" />
        </a-descriptions-item>

        <a-descriptions-item label="tab style">
          <a-select
            v-model="appStore.tabMode" placeholder="please select" :options="tabModeList" :disabled="!appStore.tab"

            :trigger-props="{ autoFitPopupMinWidth: true }" :style="{ width: '120px' }"
          >
          </a-select>
        </a-descriptions-item>
        <a-descriptions-item label="animation display">
          <a-switch v-model="appStore.animate" />
        </a-descriptions-item>
        <a-descriptions-item label="animation display">

          <a-select
            v-model="appStore.animateMode" placeholder="please select" :options="animateModeList"
            :disabled="!appStore.animate" :style="{ width: '120px' }"
          >
          </a-select>

        </a-descriptions-item>
        <a-descriptions-item label="dark menu">
          <a-switch v-model="appStore.menuDark" />
        </a-descriptions-item>

        <a-descriptions-item label="accordion effect">
          <a-switch v-model="appStore.menuAccordion" />
        </a-descriptions-item>

        <a-descriptions-item label="copyright display">
          <a-switch v-model="appStore.copyrightDisplay" />
        </a-descriptions-item>

        <a-descriptions-item label="watermark display">
          <a-switch v-model="appStore.isOpenWatermark" />
        </a-descriptions-item>

        <a-descriptions-item label="watermark information">
          <a-input v-model="appStore.watermark" placeholder="leave blank to display username" />
        </a-descriptions-item>
      </a-descriptions>


      <a-divider orientation="center">other</a-divider>
      <a-descriptions :column="1" :align="{ value: 'right' }" :value-style="{ paddingRight: 0 }">
        <a-descriptions-item label="color weakness mode">

          <a-switch v-model="appStore.enableColorWeaknessMode" />
        </a-descriptions-item>
        <a-descriptions-item v-if="settingOpen" label="mourning mode">
          <a-switch v-model="appStore.enableMourningMode" />
        </a-descriptions-item>

      </a-descriptions>
      <a-space v-if="settingOpen" direction="vertical" fill>
        <a-button type="primary" long @click="copySettings">
          <template #icon>
            <icon-copy />
          </template>
          copy configuration
        </a-button>
      </a-space>

    </a-space>
  </a-drawer>
</template>

<script setup lang="ts">
import { ColorPicker } from 'vue-color-kit'
import 'vue-color-kit/dist/vue-color-kit.css'
import { useClipboard } from '@vueuse/core'
import { Message } from '@arco-design/web-vue'
import LayoutItem from './components/LayoutItem.vue'
import { useAppStore } from '@/stores'

defineOptions({ name: 'SettingDrawer' })
const appStore = useAppStore()
const visible = ref(false)
const settingOpen = JSON.parse(import.meta.env.VITE_APP_SETTING)
const tabModeList: App.TabItem[] = [
  { label: 'card', value: 'card' },
  { label: 'card-gutter', value: 'card-gutter' },
  { label: 'rounded', value: 'rounded' },
]


const animateModeList: App.AnimateItem[] = [
  { label: 'zoom-fade', value: 'zoom-fade' },
  { label: 'fade-slide', value: 'fade-slide' },
  { label: 'fade', value: 'fade' },
  { label: 'fade-bottom', value: 'fade-bottom' },
  { label: 'fade-scale', value: 'fade-scale' },
]


const open = () => {
  visible.value = true
}

// 默认显示的主题色列表
const defaultColorList = [
  '#165DFF',
  '#409EFF',
  '#18A058',
  '#2d8cf0',
  '#007AFF',
  '#5ac8fa',
  '#5856D6',
  '#536dfe',
  '#9c27b0',
  '#AF52DE',
  '#0096c7',
  '#00C1D4',
  '#43a047',
  '#e53935',
  '#f4511e',
  '#6d4c41',
]

interface ColorObj {
  hex: string
  hsv: { h: number, s: number, v: number }
  rgba: { r: number, g: number, b: number, a: number }
}

// 改变主题色
const changeColor = (colorObj: ColorObj) => {
  if (!/^#[0-9A-Z]{6}/i.test(colorObj.hex)) return
  appStore.setThemeColor(colorObj.hex)
}

// 复制配置
const copySettings = () => {
  const settings: App.AppSettings = {
    theme: 'light',
    themeColor: appStore.themeColor,
    tab: appStore.tab,
    tabMode: appStore.tabMode,
    animate: appStore.animate,
    animateMode: appStore.animateMode,
    menuCollapse: appStore.menuCollapse,
    menuAccordion: appStore.menuAccordion,
    menuDark: appStore.menuDark,
    copyrightDisplay: appStore.copyrightDisplay,
    layout: appStore.layout,
    isOpenWatermark: appStore.isOpenWatermark,
    watermark: appStore.watermark,
    enableColorWeaknessMode: appStore.enableColorWeaknessMode,
    enableMourningMode: appStore.enableMourningMode,
  }

  const settingJson = JSON.stringify(settings, null, 2)
  const { isSupported, copy } = useClipboard({ source: settingJson })
  if (isSupported) {
    copy(settingJson)
    Message.success({ content: 'copy successfully!' })
  } else {
    Message.error({ content: 'please check if the browser permission is enabled' })
  }

}

defineExpose({ open })
</script>

<style scoped lang="scss">
:deep(.arco-descriptions-item-label-block) {
  color: var(--color-text-1);
}

.layout-text {
  font-size: 12px;
  text-align: center;
  color: var(--color-text-2);
  margin-top: 4px;
}
</style>
