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
import { addTeacher, getTeacher, updateTeacher } from '@/apis/selectclass/teacher'
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
const title = computed(() => (isUpdate.value ? '修改教师管理' : '新增教师管理'))
const formRef = ref<InstanceType<typeof GiForm>>()
const { teacher_status, gender_type } = useDict('teacher_status', 'gender_type')
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
    label: 'teacher number',
    field: 'teacherNo',
    type: 'input',
    props: {
      placeholder: 'please enter the teacher number',
    },
    rules: [{ required: true, message: 'please enter the teacher number' }],
  },


  {
    label: 'name',
    field: 'name',
    type: 'input',
    props: {
      placeholder: 'please enter the name',
    },
    rules: [{ required: true, message: 'please enter the name' }],

  },

  {
    label: 'gender',
    field: 'gender',
    type: 'select',
    props: {
      placeholder: 'please select the gender',
    },
    options: gender_type,
    rules: [{ required: true, message: 'please enter the gender' }],

  },

  {
    label: 'title',
    field: 'title',
    type: 'input',
    props: {
      placeholder: 'please enter the title',
    },
  },


  {
    label: 'phone',
    field: 'phone',
    type: 'input',
    props: {
      placeholder: 'please enter the phone',
    },
  },


  {
    label: 'email',
    field: 'email',
    type: 'input',
    props: {
      placeholder: 'please enter the email',
    },
  },


  {
    label: 'college',
    field: 'collegeId',
    type: 'tree-select',
    data: deptList,
    props: {
      placeholder: 'please enter the college',
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
    rules: [{ required: true, message: 'please enter the college' }],
  },
  {
    label: 'status',
    field: 'status',
    type: 'select',
    props: {
      placeholder: 'please select the status',
    },
    options: teacher_status,
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
      await updateTeacher(form, dataId.value)
      Message.success('modify successfully')
    } else {
      await addTeacher(form)
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
  const { data } = await getTeacher(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
