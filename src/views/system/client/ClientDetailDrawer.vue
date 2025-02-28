<template>
  <a-drawer v-model:visible="visible" title="client details" :width="width >= 600 ? 600 : '100%'" :footer="false">
    <a-descriptions :column="1" size="large" class="general-description">
      <a-descriptions-item label="ID">{{ dataDetail?.id }}</a-descriptions-item>
      <a-descriptions-item label="client ID"><a-typography-paragraph :copyable="!!dataDetail?.clientId">{{ dataDetail?.clientId }}</a-typography-paragraph></a-descriptions-item>

      <a-descriptions-item label="client key">{{ dataDetail?.clientKey }}</a-descriptions-item>

      <a-descriptions-item label="client secret">{{ dataDetail?.clientSecret }}</a-descriptions-item>
      <a-descriptions-item label="authentication type">
        <a-space>
          <GiCellTag v-for="(item, index) in dataDetail?.authType" :key="index" :value="item" :dict="auth_type_enum" />
        </a-space>

      </a-descriptions-item>
      <a-descriptions-item label="client type">
        <GiCellTag :value="dataDetail?.clientType" :dict="client_type" />
      </a-descriptions-item>

      <a-descriptions-item label="Token active timeout">{{ dataDetail?.activeTimeout }}</a-descriptions-item>
      <a-descriptions-item label="Token expiration">{{ dataDetail?.timeout }}</a-descriptions-item>
      <a-descriptions-item label="status">
        <GiCellStatus :status="dataDetail?.status" />

      </a-descriptions-item>
      <a-descriptions-item label="create user">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="create time">{{ dataDetail?.createTime }}</a-descriptions-item>
      <a-descriptions-item label="update user">{{ dataDetail?.updateUserString }}</a-descriptions-item>

      <a-descriptions-item label="update time">{{ dataDetail?.updateTime }}</a-descriptions-item>
    </a-descriptions>
  </a-drawer>

</template>

<script setup lang="ts">
import { useWindowSize } from '@vueuse/core'
import { type ClientDetailResp, getClient as getDetail } from '@/apis/system/client'
import { useDict } from '@/hooks/app'

const {
  client_type,
  auth_type_enum,
} = useDict('client_type', 'auth_type_enum')

const { width } = useWindowSize()

const dataId = ref('')
const dataDetail = ref<ClientDetailResp>()
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
