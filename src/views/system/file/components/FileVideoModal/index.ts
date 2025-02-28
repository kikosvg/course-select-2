import { h } from 'vue'
import { Modal } from '@arco-design/web-vue'
import ModalContent from './ModalContent.vue'
import type { FileItem } from '@/apis/system'

export function previewFileVideoModal(data: FileItem) {
  return Modal.open({
    title: 'video play',
    width: 'auto',
    modalStyle: {},
    content: () => h(ModalContent, { data }),
  })
}
