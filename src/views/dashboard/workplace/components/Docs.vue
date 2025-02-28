<template>
  <a-card
    class="general-card"
    title="Help Documents"
    :header-style="{ paddingBottom: 0 }"
    :body-style="{ paddingTop: '5px' }"
    style="height: 166px"

  >
    <template #extra>
      <a-link href="https://continew.top" target="_blank" rel="noopener">more</a-link>
    </template>
    <a-row>

      <a-col v-for="link in links" :key="link.text" :span="12">
        <a-link
          :href="link.url"
          target="_blank"
          rel="noopener"
        >
          {{ link.text }}
        </a-link>
      </a-col>
    </a-row>
  </a-card>
</template>

<script setup lang="ts">
const links = [
  { text: 'Project Introduction', url: 'https://continew.top/admin/intro/what-is.html' },
  { text: 'Quick Start', url: 'https://continew.top/admin/intro/quick-start.html' },
  { text: 'Common Problems', url: 'https://continew.top/faq.html' },
  { text: 'Update Log', url: 'https://continew.top/admin/other/changelog.html' },
  { text: 'Contributing Guide', url: 'https://continew.top/admin/other/contributing.html' },
  { text: 'Sponsor Support 💖', url: 'https://continew.top/sponsor.html' },

]
</script>

<style lang="less" scoped>
.arco-card-body .arco-link {
  margin: 5px 0;
  color: rgb(var(--gray-8));
}
</style>
