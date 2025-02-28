<template>
  <a-drawer
    v-model:visible="visible"
    title="import user"
    :mask-closable="false"
    :esc-to-close="false"
    :width="width >= 600 ? 600 : '100%'"
    ok-text="confirm import"
    cancel-text="cancel import"
    @before-ok="save"

    @close="reset"
  >
    <a-form ref="formRef" :model="form" size="large" auto-label-width>
      <a-alert v-if="!form.disabled" style="margin-bottom: 15px">
        please fill in the data according to the template requirements, and then upload and parse the data.
        <template #action>
          <a-link @click="downloadTemplate">

            <template #icon><GiSvgIcon name="file-excel" :size="16" /></template>
            <template #default>download template</template>
          </a-link>
        </template>

      </a-alert>
      <fieldset>
        <legend>1.parse data</legend>
        <div class="file-box">
          <a-upload

            draggable
            :custom-request="handleUpload"
            :limit="1"
            :show-retry-butto="false"
            :show-cancel-button="false"
            tip="only support xls, xlsx format"
            :file-list="uploadFile"
            accept=".xls, .xlsx, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
          />

        </div>
        <div v-if="dataResult.importKey">
          <div class="file-box">
            <a-space size="large">
              <a-statistic title="total rows" :value="dataResult.totalRows" />
              <a-statistic title="valid rows" :value="dataResult.validRows" />
            </a-space>
          </div>

          <div class="file-box">
            <a-space size="large">
              <a-statistic title="duplicate user" :value="dataResult.duplicateUserRows" />
              <a-statistic title="duplicate email" :value="dataResult.duplicateEmailRows" />
              <a-statistic title="duplicate phone" :value="dataResult.duplicatePhoneRows" />

            </a-space>
          </div>
        </div>
      </fieldset>
      <fieldset>
        <legend>2.import strategy</legend>
        <a-form-item label="user already exists" field="duplicateUser">
          <a-radio-group v-model="form.duplicateUser" type="button">

            <a-radio :value="1">skip this row</a-radio>
            <a-radio :value="3">stop import</a-radio>
            <a-radio :value="2">modify data</a-radio>
          </a-radio-group>

        </a-form-item>
        <a-form-item label="email already exists" field="duplicateEmail">
          <a-radio-group v-model="form.duplicateEmail" type="button">
            <a-radio :value="1">skip this row</a-radio>

            <a-radio :value="3">stop import</a-radio>
          </a-radio-group>

        </a-form-item>
        <a-form-item label="phone already exists" field="duplicatePhone">
          <a-radio-group v-model="form.duplicatePhone" type="button">
            <a-radio :value="1">skip this row</a-radio>
            <a-radio :value="3">stop import</a-radio>

          </a-radio-group>
        </a-form-item>
        <a-form-item label="default status" field="defaultStatus">
          <a-switch
            v-model="form.defaultStatus"
            :checked-value="1"
            :unchecked-value="2"
            checked-text="enable"
            unchecked-text="disable"
            type="round"
          />

        </a-form-item>
      </fieldset>
    </a-form>
  </a-drawer>
</template>

<script setup lang="ts">
import { type FormInstance, Message, type RequestOption } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import {
  type UserImportResp,
  downloadUserImportTemplate,
  importUser,
  parseImportUser,
} from '@/apis/system/user'
import { useDownload, useResetReactive } from '@/hooks'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const visible = ref(false)
const formRef = ref<FormInstance>()
const uploadFile = ref([])

const [form, resetForm] = useResetReactive({
  errorPolicy: 1,
  duplicateUser: 1,
  duplicateEmail: 1,
  duplicatePhone: 1,
  defaultStatus: 1,
})

const dataResult = ref<UserImportResp>({
  importKey: '',
  totalRows: 0,
  validRows: 0,
  duplicateUserRows: 0,
  duplicateEmailRows: 0,
  duplicatePhoneRows: 0,
})

// 重置
const reset = () => {
  formRef.value?.resetFields()
  dataResult.value.importKey = ''
  uploadFile.value = []
  resetForm()
}

// 下载模板
const downloadTemplate = () => {
  useDownload(() => downloadUserImportTemplate())
}

// 上传解析导入数据
const handleUpload = (options: RequestOption) => {
  const controller = new AbortController();
  (async function requestWrap() {
    const { onProgress, onError, onSuccess, fileItem, name = 'file' } = options
    onProgress(20)
    const formData = new FormData()
    formData.append(name as string, fileItem.file as Blob)
    try {
      const res = await parseImportUser(formData)
      dataResult.value = res.data
      Message.success('upload and parse successfully')
      onSuccess(res)

    } catch (error) {
      onError(error)
    }
  })()
  return {
    abort() {
      controller.abort()
    },
  }
}

// 执行导入
const save = async () => {
  try {
    if (!dataResult.value.importKey) {
      Message.warning('please upload the file and parse the import data')
      return false
    }
    form.importKey = dataResult.value.importKey
    const res = await importUser(form)
    Message.success(`import successfully! add ${res.data.insertRows}, update ${res.data.updateRows}`)
    emit('save-success')

    return true
  } catch (error) {
    return false
  }
}

// 打开
const onOpen = () => {
  reset()
  visible.value = true
}

defineExpose({ onOpen })
</script>

<style scoped lang="scss">
fieldset {
  padding: 15px 15px 0 15px;
  margin-bottom: 15px;
  border: 1px solid var(--color-neutral-3);
  border-radius: 3px;
}

fieldset legend {
  color: rgb(var(--gray-10));
  padding: 2px 5px 2px 5px;
  border: 1px solid var(--color-neutral-3);
  border-radius: 3px;
}

.file-box {
  margin-bottom: 20px;
  margin-left: 10px;
}
</style>
