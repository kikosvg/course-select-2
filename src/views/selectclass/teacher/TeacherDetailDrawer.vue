<template>
  <a-drawer v-model:visible="visible" title="teacher management details" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="2" size="large" class="general-description">

      <a-descriptions-item label="teacher ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="teacher number">{{ dataDetail?.teacherNo }}</a-descriptions-item>
      <a-descriptions-item label="name">{{ dataDetail?.name }}</a-descriptions-item>
      <a-descriptions-item label="gender">{{ dataDetail?.gender }}</a-descriptions-item>

      <a-descriptions-item label="title">{{ dataDetail?.title }}</a-descriptions-item>
      <a-descriptions-item label="phone">{{ dataDetail?.phone }}</a-descriptions-item>
      <a-descriptions-item label="email">{{ dataDetail?.email }}</a-descriptions-item>

      <a-descriptions-item label="college">{{ dataDetail?.collegeId }}</a-descriptions-item>
      <a-descriptions-item label="status"><GiCellStatus :status="dataDetail?.status" /></a-descriptions-item>
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
import { type TeacherDetailResp, getTeacher as getDetail } from '@/apis/selectclass/teacher'

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<TeacherDetailResp>()
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
