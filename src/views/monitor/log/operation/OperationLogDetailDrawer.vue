<template>
  <a-drawer v-model:visible="visible" title="log detail" :width="720" :footer="false">
    <a-descriptions title="basic information" :column="2" size="large" class="general-description">
      <a-descriptions-item label="log ID">{{ dataDetail?.id }}</a-descriptions-item>

      <a-descriptions-item label="Trace ID"><a-typography-paragraph :copyable="!!dataDetail?.traceId">{{ dataDetail?.traceId }}</a-typography-paragraph></a-descriptions-item>
      <a-descriptions-item label="operation user">{{ dataDetail?.createUserString }}</a-descriptions-item>
      <a-descriptions-item label="operation time">{{ dataDetail?.createTime }}</a-descriptions-item>

      <a-descriptions-item label="operation content">{{ dataDetail?.description }}</a-descriptions-item>
      <a-descriptions-item label="module">{{ dataDetail?.module }}</a-descriptions-item>

      <a-descriptions-item label="operation IP"><a-typography-paragraph :copyable="!!dataDetail?.ip">{{ dataDetail?.ip }}</a-typography-paragraph></a-descriptions-item>
      <a-descriptions-item label="operation location">{{ dataDetail?.address }}</a-descriptions-item>
      <a-descriptions-item label="browser">{{ dataDetail?.browser }}</a-descriptions-item>

      <a-descriptions-item label="terminal system">{{ dataDetail?.os }}</a-descriptions-item>
      <a-descriptions-item label="status">

        <a-tag v-if="dataDetail?.status === 1" color="green">success</a-tag>
        <a-tag v-else color="red">failed</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="time taken">
        <a-tag v-if="dataDetail?.timeTaken > 500" color="red"> {{ dataDetail?.timeTaken }}ms </a-tag>

        <a-tag v-else-if="dataDetail?.timeTaken > 200" color="orange"> {{ dataDetail?.timeTaken }}ms </a-tag>
        <a-tag v-else color="green">{{ dataDetail?.timeTaken }} ms</a-tag>
      </a-descriptions-item>
      <a-descriptions-item label="request URI" :span="2">
        <a-typography-paragraph :copyable="!!dataDetail?.requestUrl">{{ dataDetail?.requestUrl }}</a-typography-paragraph>
      </a-descriptions-item>

    </a-descriptions>
    <a-descriptions
      title="response information"
      :column="2"
      size="large"
      class="general-description http"
      style="margin-top: 20px; position: relative"

    >
      <a-descriptions-item :span="2">
        <a-tabs type="card">
          <a-tab-pane key="1" title="response headers">
            <JsonPretty v-if="dataDetail?.responseHeaders" :json="dataDetail?.responseHeaders" />
            <span v-else>无</span>

          </a-tab-pane>
          <a-tab-pane key="2" title="response body">
            <JsonPretty v-if="dataDetail?.responseBody" :json="dataDetail?.responseBody" />
            <span v-else>无</span>

          </a-tab-pane>
        </a-tabs>
      </a-descriptions-item>
    </a-descriptions>
    <a-descriptions
      title="request information"
      :column="2"
      size="large"
      class="general-description http"
      style="margin-top: 20px; position: relative"

    >
      <a-descriptions-item :span="2">
        <a-tabs type="card">
          <a-tab-pane key="1" title="request headers">
            <JsonPretty v-if="dataDetail?.requestHeaders" :json="dataDetail?.requestHeaders" />
            <span v-else>无</span>

          </a-tab-pane>
          <a-tab-pane key="2" title="request body">
            <JsonPretty v-if="dataDetail?.requestBody" :json="dataDetail?.requestBody" />
            <span v-else>无</span>

          </a-tab-pane>
        </a-tabs>
      </a-descriptions-item>
    </a-descriptions>
  </a-drawer>
</template>

<script setup lang="ts">
import { type LogDetailResp, getLog as getDetail } from '@/apis/monitor'

const dataId = ref('')
const dataDetail = ref<LogDetailResp>()
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

<style scoped lang="scss">
.http :deep(.arco-descriptions-item-label-block) {
  padding-right: 0;
}

:deep(.arco-tabs-content) {
  padding-top: 5px;
  padding-left: 15px;
}
</style>
