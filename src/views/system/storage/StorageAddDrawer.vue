<template>
  <a-drawer
    v-model:visible="visible"
    :title="title"
    :mask-closable="false"
    :esc-to-close="false"
    :width="width >= 600 ? 600 : '100%'"
    @before-ok="save"
    @close="reset"
  >
    <a-form ref="formRef" :model="form" :rules="rules" size="large" auto-label-width>
      <a-form-item label="name" field="name">
        <a-input v-model.trim="form.name" placeholder="please enter the name" />
      </a-form-item>
      <a-form-item label="code" field="code">
        <a-input v-model.trim="form.code" placeholder="please enter the code" :disabled="isUpdate" />
      </a-form-item>
      <a-form-item label="type" field="type">
        <a-select v-model.trim="form.type" :options="storage_type_enum" placeholder="please select the type" :disabled="isUpdate" />
      </a-form-item>
      <a-form-item v-if="form.type === 1" label="access key" field="accessKey">
        <a-input v-model.trim="form.accessKey" placeholder="please enter the access key" :max-length="255" />
      </a-form-item>
      <a-form-item v-if="form.type === 1" label="secret key" field="secretKey">
        <a-input
          v-model.trim="form.secretKey"
          placeholder="please enter the secret key"
          :max-length="255"
        />

      </a-form-item>
      <a-form-item v-if="form.type === 1" label="endpoint" field="endpoint">
        <a-input v-model.trim="form.endpoint" placeholder="please enter the endpoint" />
      </a-form-item>

      <a-form-item label="bucket name" field="bucketName">
        <a-input v-model.trim="form.bucketName" placeholder="please enter the bucket name" />
      </a-form-item>

      <a-form-item v-if="form.type === 1" label="domain" field="domain">
        <a-input v-model.trim="form.domain" placeholder="please enter the domain" />
        <template #extra>
          <div v-if="defaultDomain">
            <span>leave blank for default domain: {{ defaultDomain }}</span>
          </div>
        </template>

      </a-form-item>
      <a-form-item
        v-if="form.type === 2"
        label="domain"
        field="domain"

        :rules="[
          {
            required: true,
            message: 'please enter the domain',
          },
        ]"
      >
        <a-input v-model.trim="form.domain" placeholder="please enter the domain" />
      </a-form-item>
      <a-form-item label="sort" field="sort">
        <a-input-number v-model="form.sort" placeholder="please enter the sort" :min="1" mode="button" />
      </a-form-item>
      <a-form-item label="description" field="description">

        <a-textarea
          v-model.trim="form.description"
          placeholder="please enter the description"
          show-word-limit
          :max-length="200"
          :auto-size="{ minRows: 3, maxRows: 5 }"

        />
      </a-form-item>
      <a-form-item label="default storage" field="isDefault">
        <a-switch
          v-model="form.isDefault"
          type="round"
          :checked-value="true"
          :unchecked-value="false"

          checked-text="yes"
          unchecked-text="no"
        />

      </a-form-item>
      <a-form-item label="status" field="status">
        <a-switch
          v-model="form.status"
          type="round"
          :checked-value="1"
          :unchecked-value="2"

          checked-text="enable"
          unchecked-text="disable"
        />

      </a-form-item>
    </a-form>
  </a-drawer>
</template>

<script setup lang="ts">
import { type FormInstance, Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { addStorage, getStorage, updateStorage } from '@/apis/system/storage'
import { useResetReactive } from '@/hooks'
import { useDict } from '@/hooks/app'
import { encryptByRsa } from '@/utils/encrypt'
import { isIPv4 } from '@/utils/validate'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const dataId = ref('')
const visible = ref(false)
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? 'update storage' : 'add storage'))
const formRef = ref<FormInstance>()
const { storage_type_enum } = useDict('storage_type_enum')


const rules: FormInstance['rules'] = {
  name: [{ required: true, message: 'please enter the name' }],
  code: [{ required: true, message: 'please enter the code' }],
  type: [{ required: true, message: 'please select the type' }],
  accessKey: [{ required: true, message: 'please enter the access key' }],
  secretKey: [{ required: true, message: 'please enter the secret key' }],

  endpoint: [{ required: true, message: 'please enter the endpoint' }],
  bucketName: [{ required: true, message: 'please enter the bucket name' }],

}

const [form, resetForm] = useResetReactive({
  type: 2,
  isDefault: false,
  sort: 999,
  status: 1,
})
/** 获取url的protocol和endpoint */
const stripProtocol = (url: string): { endpoint: string, protocol: string } => {
  if (url.startsWith('http://')) {
    return { endpoint: url.substring(7), protocol: 'http://' }
  } else if (url.startsWith('https://')) {
    return { endpoint: url.substring(8), protocol: 'https://' }
  }
  return { endpoint: url, protocol: 'http://' }
}
/** 按规则拼接当前默认domain */
const defaultDomain = computed(() => {
  const { endpoint: initialEndpoint, bucketName, domain, type } = form
  if (domain || type !== 1 || !initialEndpoint || !bucketName) {
    return
  }
  const { endpoint, protocol } = stripProtocol(initialEndpoint)
  return isIPv4(endpoint) ? `${protocol}${endpoint}/${bucketName}/` : `${protocol}${bucketName}.${endpoint}/`
})

// 重置
const reset = () => {
  formRef.value?.resetFields()
  resetForm()
}

// 保存
const save = async () => {
  try {
    const isInvalid = await formRef.value?.validate()
    if (isInvalid) return false
    const data = {
      ...form,
      secretKey: form.type === 1 && !form.secretKey.includes('*') ? encryptByRsa(form.secretKey) : null,
      domain: form.domain || defaultDomain.value,
    }
    if (isUpdate.value) {
      await updateStorage(data, dataId.value)
      Message.success('update successfully')
    } else {
      await addStorage(data)
      Message.success('add successfully')
    }

    emit('save-success')
    return true
  } catch (error) {
    return false
  }
}

// 新增
const onAdd = () => {
  reset()
  dataId.value = ''
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getStorage(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>
