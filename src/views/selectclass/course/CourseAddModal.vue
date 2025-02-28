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
import { addCourse, getCourse, updateCourse } from '@/apis/selectclass/course'
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
const title = computed(() => (isUpdate.value ? 'modify course' : 'add course'))
const formRef = ref<InstanceType<typeof GiForm>>()
const { course_type, course_status } = useDict('course_type', 'course_status')
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
    label: 'course ID',
    field: 'courseNo',
    type: 'input',
    props: {
      placeholder: 'please enter the course ID',
    },
    rules: [{ required: true, message: 'please enter the course ID' }],


  },
  {
    label: 'course name',
    field: 'name',
    type: 'input',
    props: {
      placeholder: 'please enter the course name',
    },
    rules: [{ required: true, message: 'please enter the course name' }],

  },

  {
    label: 'credit(10 points)',
    field: 'credit',
    type: 'input-number',
    props: {
      placeholder: 'please enter the credit(10 points)',
    },
    rules: [{ required: true, message: 'please enter the credit(10 points)' }],


  },
  {
    label: 'hours',
    field: 'hours',
    type: 'input-number',
    props: {
      placeholder: 'please enter the hours',
    },
    rules: [{ required: true, message: 'please enter the hours' }],


  },
  {
    label: 'type',
    field: 'type',
    type: 'select',
    props: {
      placeholder: 'please select the type',
    },
    options: course_type,
    rules: [{ required: true, message: 'please enter the type' }],
  },


  {
    label: 'college ID',
    field: 'collegeId',
    type: 'tree-select',
    data: deptList,
    props: {
      placeholder: 'please enter the college ID',

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
    rules: [{ required: true, message: 'please enter the college ID' }],


  },
  {
    label: 'description',
    field: 'description',
    type: 'textarea',
    props: {
      placeholder: 'please enter the description',

      autoSize: true,
      maxLength: 200,
      showWordLimit: true,
    },
  },
  {
    label: 'status',
    field: 'status',
    type: 'select',
    props: {
      placeholder: 'please select the status',
    },
    options: course_status,
    rules: [{ required: true, message: 'please enter the status' }],



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
      await updateCourse(form, dataId.value)
      Message.success('modify successfully')
    } else {
      await addCourse(form)
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
  const { data } = await getCourse(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
