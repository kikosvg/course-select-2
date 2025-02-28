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
import { List } from 'echarts'
import { addCourseSchedule, getCourseSchedule, updateCourseSchedule } from '@/apis/selectclass/courseSchedule'
import { type Columns, GiForm, type Options } from '@/components/GiForm'
import { useResetReactive } from '@/hooks'
import { useDict } from '@/hooks/app'
import { listTeacher } from '@/apis/selectclass/teacher'
import { listCourse } from '@/apis/selectclass/course'

const emit = defineEmits<{
  (e: 'save-success'): void
}>()

const { width } = useWindowSize()

const dataId = ref('')
const visible = ref(false)
const isUpdate = computed(() => !!dataId.value)
const title = computed(() => (isUpdate.value ? 'modify course schedule' : 'add course schedule'))
const formRef = ref<InstanceType<typeof GiForm>>()
const { course_schedule_status } = useDict('course_schedule_status')


const options: Options = {
  form: { size: 'large' },
  btns: { hide: true },
}

const [form, resetForm] = useResetReactive({
  // todo 待补充
})
const teacherList = ref([])
const courseList = ref([])
const columns: Columns = reactive<Columns>([
  {
    label: 'course',
    field: 'courseId',
    type: 'select',
    props: {
      placeholder: 'please select the course',
    },
    options: courseList,
    rules: [{ required: true, message: 'please enter the course' }],

  },

  {
    label: 'teacher',
    field: 'teacherId',
    type: 'select',
    props: {
      placeholder: 'please select the teacher',
    },
    options: teacherList,
    rules: [{ required: true, message: 'please enter the teacher' }],

  },

  {
    label: 'semester',
    field: 'semester',
    type: 'input',
    props: {
      placeholder: 'please enter the semester',
    },
    rules: [{ required: true, message: 'please enter the semester' }],


  },
  {
    label: 'classroom',
    field: 'classroom',
    type: 'input',
    props: {
      placeholder: 'please enter the classroom',
    },
    rules: [{ required: true, message: 'please enter the classroom' }],


  },
  {
    label: 'day of week',
    field: 'dayOfWeek',
    type: 'input-number',
    props: {
      placeholder: 'please enter the day of week',
    },
    rules: [{ required: true, message: 'please enter the day of week' }],


  },
  {
    label: 'start time',
    field: 'startTime',
    type: 'date-picker',
    props: {
      placeholder: 'please enter the start time',
      showTime: true,
    },
    rules: [{ required: true, message: 'please enter the start time' }],
  },

  {
    label: 'end time',
    field: 'endTime',
    type: 'date-picker',
    props: {
      placeholder: 'please enter the end time',
      showTime: true,
    },
    rules: [{ required: true, message: 'please enter the end time' }],

  },  

  {
    label: 'max student',
    field: 'maxStudent',
    type: 'input-number',
    props: {
      placeholder: 'please enter the max student',
    },
    rules: [{ required: true, message: 'please enter the max student' }],

  },

  {
    label: 'status',
    field: 'status',
    type: 'select',
    props: {
      placeholder: 'please select the status',
    },
    options: course_schedule_status,
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
      await updateCourseSchedule(form, dataId.value)
      Message.success('modify successfully')
    } else {
      await addCourseSchedule(form)
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
  const { data: { list: teacherListData } } = await listTeacher({
    teacherNo: '',
    name: '',
    gender: '',
    collegeId: '',
    status: '',
    sort: [],
    page: 1,
    size: 1000,
  })
  const {data: { list: courseListData }} = await listCourse({
    courseNo: '',
    name: '',
    credit: '',
    hours: '',
    type: '',
    collegeId: '',
    status: '',
    sort: [],
    page: 1,
    size: 1000,
  })
  teacherList.value = teacherListData.map((item: any) => {
    return {
      label: item.name,
      value: item.name,
    }
  })
  courseList.value = courseListData.map((item: any) => {
    return {
      label: item.name,
      value: item.name,
    }
  })
  reset()
  dataId.value = ''
  visible.value = true
}

// 修改
const onUpdate = async (id: string) => {
  reset()
  dataId.value = id
  const { data } = await getCourseSchedule(id)
  Object.assign(form, data)
  visible.value = true
}

defineExpose({ onAdd, onUpdate })
</script>

<style scoped lang="scss"></style>
