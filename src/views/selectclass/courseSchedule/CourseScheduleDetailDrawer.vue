<template>
  <a-drawer v-model:visible="visible" title="course schedule details" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">

      <a-descriptions-item label="course schedule ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="course ID">{{ dataDetail?.courseId }}</a-descriptions-item>
      <a-descriptions-item label="teacher ID">{{ dataDetail?.teacherId }}</a-descriptions-item>
      <a-descriptions-item label="semester">{{ dataDetail?.semester }}</a-descriptions-item>

      <a-descriptions-item label="classroom">{{ dataDetail?.classroom }}</a-descriptions-item>
      <a-descriptions-item label="day of week">{{ dataDetail?.dayOfWeek }}</a-descriptions-item>
      <a-descriptions-item label="start time">{{ dataDetail?.startTime }}</a-descriptions-item>

      <a-descriptions-item label="end time">{{ dataDetail?.endTime }}</a-descriptions-item>
      <a-descriptions-item label="max student">{{ dataDetail?.maxStudent }}</a-descriptions-item>
      <a-descriptions-item label="selected">{{ dataDetail?.selected }}</a-descriptions-item>

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
import { type CourseScheduleDetailResp, getCourseSchedule as getDetail } from '@/apis/selectclass/courseSchedule'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<CourseScheduleDetailResp>()
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
