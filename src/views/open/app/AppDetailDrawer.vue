<template>
  <a-drawer v-model:visible="visible" title="application details" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">

      <a-descriptions-item label="ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="name">{{ dataDetail?.name }}</a-descriptions-item>
      <a-descriptions-item label="access key" :span="2"><CellCopy :content="dataDetail?.accessKey" /></a-descriptions-item>

      <a-descriptions-item label="status"><GiCellStatus :status="dataDetail?.status" /></a-descriptions-item>
      <a-descriptions-item label="expire time">{{ dataDetail?.expireTime }}</a-descriptions-item>
      <a-descriptions-item label="create user">{{ dataDetail?.createUserString }}</a-descriptions-item>

      <a-descriptions-item label="create time">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="update user">{{ dataDetail?.updateUserString }}</a-descriptions-item>
      <a-descriptions-item label="update time">{{ dataDetail?.updateTime }}</a-descriptions-item>
      <a-descriptions-item label="description">{{ dataDetail?.description }}</a-descriptions-item>

    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type AppResp, getApp as getDetail } from '@/apis/open/app'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<AppResp>()
const visible = ref(false)

// 查询详情
const getDataDetail = async () => {
  const { data } = await getDetail(dataId.value)
  dataDetail.value = data
}

// 打开
const onOpen = async (id: string) => {
  dataId.value = id
  await getDataDetail()
  visible.value = true
}

defineExpose({ onOpen })
</script>

<style scoped lang="scss"></style>
