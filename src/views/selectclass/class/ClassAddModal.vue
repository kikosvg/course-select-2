<template>
  <a-modal
    v-model:visible="visible"
    :title="title"
    :mask-closable="false"
    :esc-to-close="false"
    :width="width >= 600 ? 600 : '100%'"
    draggable
    @before-ok="save"
    @close="reset"
  >
    <GiForm ref="formRef" v-model="form" :options="options" :columns="columns" />
  </a-modal>
</template>

<script setup lang="ts">
import type { TreeNodeData } from '@arco-design/web-vue'
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { addClass, getClass, updateClass } from '@/apis/selectclass/class'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useResetReactive } from '@/hooks'
import { useDept, useDict } from '@/hooks/app'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const dataId = ref('')
const visible = ref(false)
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? 'modify class' : 'add class'))
const formRef = ref<InstanceType<typeof GiForm>>()
const { class_status } = useDict('class_status')
const { deptList, getDeptList } = useDept()


const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const [form, resetForm] = useResetReactive({
  // todo 待补充
})

const columns: Columns = reactive<Columns>([
  {
    label: 'class name',
    field: 'name',
    type: 'input',
    props: {
      placeholder: 'please enter the class name',
    },
    rules: [{ required: true, message: 'please enter the class name' }],


  },
  {
    label: 'class code',
    field: 'code',
    type: 'input',
    props: {
      placeholder: 'please enter the class code',
    },
    rules: [{ required: true, message: 'please enter the class code' }],


  },
  {
    label: 'major ID',
    field: 'majorId',
    type: 'tree-select',
    data: deptList,
    props: {
      placeholder: 'please enter the major ID',
      allowClear: true,
      allowSearch: true,
      fallbackOption: false,
      filterTreeNode(searchKey: string, nodeData: TreeNodeData) {

        if (nodeData.title) {
          return nodeData.title.toLowerCase().includes(searchKey.toLowerCase())
        }
        return false
      },
    },
    rules: [{ required: true, message: 'please enter the major ID' }],
  },

  {
    label: 'grade',
    field: 'grade',
    type: 'input',
    props: {
      placeholder: 'please enter the grade',
    },
    rules: [{ required: true, message: 'please enter the grade' }],

  },



  {
    label: 'description',
    field: 'description',
    type: 'textarea',
    props: {
      placeholder: 'please enter the description',
      maxLength: 200,
      showWordLimit: true,
      autoSize: { minRows: 3, maxRows: 5 },
    },
  },
  {
    label: 'status',
    field: 'status',
    type: 'select',
    props: {
      placeholder: 'please select the status',
    },
    options: class_status,
    rules: [{ required: true, message: 'please select the status' }],

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
      await updateClass(form, dataId.value)
      Message.success('modify successfully')
    } else {
      await addClass(form)
      Message.success('add successfully')
    }
    emit('save-success')
    return true
  } catch (error) {
    return false
  }
}

// 新增
const onAdd = async () => {
  await getDeptList()
  reset()
  dataId.value = ''
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getClass(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
