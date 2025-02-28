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
import { Message } from '@arco-design/web-vue'
import { useWindowSize } from '@vueuse/core'
import { addCourseSelection, getCourseSelection, updateCourseSelection } from '@/apis/selectclass/courseSelection'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useResetReactive } from '@/hooks'
import { useDict } from '@/hooks/app'
import { getUserInfo } from '@/apis'
import { listCourseSchedule } from '@/apis/selectclass/courseSchedule'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const dataId = ref('')
const visible = ref(false)
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? 'modify course selection' : 'add course selection'))
const formRef = ref<InstanceType<typeof GiForm>>()
const { select_course_status } = useDict('select_course_status')


const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const [form, resetForm] = useResetReactive({
  // todo 待补充
})
const courseAssignList = ref<any>([])
const columns: Columns = reactive<Columns>([
  {
    label: 'student',
    field: 'studentId',
    type: 'input',
    props: {

      placeholder: 'please enter the student name',
    },
    rules: [{ required: true, message: 'please enter the student ID' }],

  },
  {
    label: 'course schedule',
    field: 'scheduleId',
    type: 'select',
    options: courseAssignList,
    rules: [{ required: true, message: 'please enter the course schedule' }],
  },

  {
    label: 'select time',
    field: 'selectTime',
    type: 'date-picker',
    props: {

      showTime: true,
    },
    rules: [{ required: true, message: 'please enter the select time' }],
  },
  {
    label: 'status',
    field: 'status',
    type: 'select',

    options: select_course_status,
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
      await updateCourseSelection(form, dataId.value)
      Message.success('modify successfully')
    } else {
      await addCourseSelection(form)
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
  const { data: { list: courseList } } = await listCourseSchedule({
    courseId: '',
    teacherId: '',
    semester: '',
    classroom: '',
    dayOfWeek: '',
    startTime: '',
    endTime: '',
    maxStudent: '',
    selected: '',
    status: '',
    sort: [],
    page: 1,
    size: 1000  ,
  })
  // 过滤掉已满的课程，并返回有效的选项
  courseAssignList.value = courseList
    .filter((item: any) => item.selected < item.maxStudent)
    .map((item: any) => ({
      label: item.courseId,
      value: item.courseId,
    }))
  reset()
  dataId.value = ''
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getCourseSelection(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
