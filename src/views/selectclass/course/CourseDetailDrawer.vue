<template>
  <a-drawer v-model:visible="visible" title=" course details" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">

      <a-descriptions-item label="course ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="course ID">{{ dataDetail?.courseNo }}</a-descriptions-item>
      <a-descriptions-item label="course name">{{ dataDetail?.name }}</a-descriptions-item>
      <a-descriptions-item label="credit(10 points)">{{ dataDetail?.credit }}</a-descriptions-item>

      <a-descriptions-item label="hours">{{ dataDetail?.hours }}</a-descriptions-item>
      <a-descriptions-item label="type">{{ dataDetail?.type }}</a-descriptions-item>
      <a-descriptions-item label="college ID">{{ dataDetail?.collegeId }}</a-descriptions-item>

      <a-descriptions-item label="description">{{ dataDetail?.description }}</a-descriptions-item>
      <a-descriptions-item label="status">{{ dataDetail?.status }}</a-descriptions-item>
      <a-descriptions-item label="create time">{{ dataDetail?.createTime }}</a-descriptions-item>

      <a-descriptions-item label="update time">{{ dataDetail?.updateTime }}</a-descriptions-item>
      <a-descriptions-item label="create user">{{ dataDetail?.createUser }}</a-descriptions-item>
      <a-descriptions-item label="create user">{{ dataDetail?.createUserString }}</a-descriptions-item>

      <a-descriptions-item label="update user">{{ dataDetail?.updateUser }}</a-descriptions-item>
      <a-descriptions-item label="update user">{{ dataDetail?.updateUserString }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>

</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type CourseDetailResp, getCourse as getDetail } from '@/apis/selectclass/course'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<CourseDetailResp>()
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
