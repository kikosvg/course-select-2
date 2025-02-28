<template>
  <a-row justify="center" align="center" style="padding: 0 5%">
    <a-form ref="formRef" :model="form" auto-label-width class="w-full">
      <a-form-item
        label="file name"
        field="name"
        :rules="[{ required: true, message: 'please enter the file name' }]"
        style="margin-bottom: 0"

      >
        <a-input v-model="form.name" placeholder="please enter the file name" allow-clear />
      </a-form-item>
    </a-form>

  </a-row>
</template>

<script setup lang="ts">
import type { FormInstance } from '@arco-design/web-vue'
import type { FileItem } from '@/apis/system'

interface Props {
  data: FileItem
}
const props = withDefaults(defineProps<Props>(), {})

const formRef = ref<FormInstance>()
const form = reactive({
  name: props.data?.name || '',
})

defineExpose({ formRef })
</script>

<style scoped lang="scss"></style>
