<template>
  <a-modal
    v-model:visible="visible"
    :title="title"
    :mask-closable="false"
    :esc-to-close="false"
    :width="width >= 500 ? 500 : '100%'"
    draggable
    @before-ok="save"
    @close="reset"
  >
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns">
      <template #color>
        <a-input v-model="form.color" placeholder="please select or enter the label color" allow-clear>
          <template #suffix>
            <a-color-picker v-model="form.color" />
          </template>

        </a-input>
      </template>
    </GiForm>
  </a-modal>
</template>

<script setup lang="ts">
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { addDictItem, getDictItem, updateDictItem } from '@/apis/system/dict'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useResetReactive } from '@/hooks'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const dataId = ref('')
const dictId = ref('')
const visible = ref(false)
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? 'modify dictionary item' : 'add dictionary item'))
const formRef = ref<InstanceType<typeof GiForm>>()


const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const [form, resetForm] = useResetReactive({
  color: 'blue',
  sort: 999,
  status: 1,
})

const columns: Columns = reactive([
  { label: 'label', field: 'label', type: 'input', rules: [{ required: true, message: 'please enter the label' }] },
  { label: 'value', field: 'value', type: 'input', rules: [{ required: true, message: 'please enter the value' }] },
  { label: 'label color', field: 'color', type: 'input' },
  {

    label: 'sort',
    field: 'sort',
    type: 'input-number',
    props: {

      min: 1,
      mode: 'button',
    },
  },
  {
    label: 'description',
    field: 'description',
    type: 'textarea',
    props: {

      maxLength: 200,
      autoSize: { minRows: 3, maxRows: 5 },
    },
  },
  {
    label: 'status',
    field: 'status',
    type: 'switch',
    props: {

      type: 'round',
      checkedValue: 1,
      uncheckedValue: 2,
      checkedText: 'enable',
      uncheckedText: 'disable',

    },
  },
])

// 重置
const reset = () => {
  formRef.value?.formRef?.resetFields()
  resetForm()
}

// 保存
const save = async () => {
  try {
    const isInvalid = await formRef.value?.formRef?.validate()
    if (isInvalid) return false
    if (isUpdate.value) {
      await updateDictItem(form, dataId.value)
      Message.success('modify successfully')
    } else {
      await addDictItem({ ...form, dictId: dictId.value })
      Message.success('add successfully')
    }
    emit('save-success')
    return true

  } catch (error) {
    return false
  }
}

// 新增
const onAdd = (id: string) => {
  reset()
  dataId.value = ''
  dictId.value = id
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getDictItem(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
