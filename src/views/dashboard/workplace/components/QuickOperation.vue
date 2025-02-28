<template>
  <a-card
    class="general-card"
    title="Quick Operation"
    :header-style="{ paddingBottom: '0' }"
    :body-style="{ padding: '24px 20px 16px 20px' }"

  >
    <a-row :gutter="8">
      <a-col
        v-for="link in links"
        :key="link.text"
        :span="8"
        class="wrapper"
        @click="router.replace({ path: link.path })"
      >
        <div class="icon">
          <GiSvgIcon :name="link.icon" />
        </div>
        <a-typography-paragraph class="text">
          {{ link.text }}
        </a-typography-paragraph>
      </a-col>
    </a-row>
  </a-card>
</template>

<script setup lang="ts">
const router = useRouter()

const links = [
  { text: 'User Management', icon: 'user', path: '/system/user' },
  { text: 'Role Management', icon: 'user-group', path: '/system/role' },
  { text: 'Menu Management', icon: 'menu', path: '/system/menu' },
  { text: 'File Management', icon: 'file', path: '/system/file' },
  { text: 'System Log', icon: 'history', path: '/monitor/log' },

]
</script>

<style scoped lang="less"></style>
