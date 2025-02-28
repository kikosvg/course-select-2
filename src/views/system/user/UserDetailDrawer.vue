<template>
  <a-drawer v-model:visible="visible" title="user details" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">
      <a-descriptions-item label="ID" :span="2">

        <a-typography-paragraph copyable>{{ dataDetail?.id }}</a-typography-paragraph>
      </a-descriptions-item>
      <a-descriptions-item label="username">{{ dataDetail?.username }}</a-descriptions-item>
      <a-descriptions-item label="nickname">{{ dataDetail?.nickname }}</a-descriptions-item>
      <a-descriptions-item label="gender">
        <span v-if="dataDetail?.gender === 1">male</span>

        <span v-else-if="dataDetail?.gender === 2">female</span>
        <span v-else>unknown</span>
      </a-descriptions-item>
      <a-descriptions-item label="status">
        <a-tag v-if="dataDetail?.status === 1" color="green">enable</a-tag>

        <a-tag v-else color="red">disable</a-tag>
      </a-descriptions-item>

      <a-descriptions-item label="手机号">{{ dataDetail?.phone || '暂无' }}</a-descriptions-item>
      <a-descriptions-item label="email">{{ dataDetail?.email || '暂无' }}</a-descriptions-item>
      <a-descriptions-item label="department">{{ dataDetail?.deptName }}</a-descriptions-item>
      <a-descriptions-item label="role"><GiCellTags :data="dataDetail?.roleNames" /></a-descriptions-item>

      <a-descriptions-item label="create user">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="create time">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="update user">{{ dataDetail?.updateUserString }}</a-descriptions-item>
      <a-descriptions-item label="update time">{{ dataDetail?.updateTime }}</a-descriptions-item>

      <a-descriptions-item label="description" :span="2">{{ dataDetail?.description }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>

</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type UserDetailResp, getUser as getDetail } from '@/apis/system/user'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<UserDetailResp>()
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
