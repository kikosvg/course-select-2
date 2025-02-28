<template>
  <a-drawer v-model:visible="visible" title="course selection details" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">

      <a-descriptions-item label="course selection ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="student ID">{{ dataDetail?.studentId }}</a-descriptions-item>
      <a-descriptions-item label="course schedule ID">{{ dataDetail?.scheduleId }}</a-descriptions-item>
      <a-descriptions-item label="select time">{{ dataDetail?.selectTime }}</a-descriptions-item>

      <a-descriptions-item label="score">{{ dataDetail?.score }}</a-descriptions-item>
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
import { type CourseSelectionDetailResp, getCourseSelection as getDetail } from '@/apis/selectclass/courseSelection'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<CourseSelectionDetailResp>()
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
