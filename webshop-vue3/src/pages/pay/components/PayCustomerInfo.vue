<template>
  <div>
    <div class="mb-3">
      <label for="username" class="form-label">收件人:</label>
      <input 
        type="text" 
        id="username" 
        v-model="formData.username" 
        class="form-control"
      >
    </div>
    <div class="mb-3">
      <label for="address" class="form-label">地址:</label>
      <input 
        type="text" 
        id="address" 
        v-model="formData.address" 
        class="form-control"
      >
    </div>
    <div class="d-flex justify-content-around mt-4 mb-3">
      <button 
        type="button" 
        class="btn btn-secondary" 
        @click="$emit('next')"
      >
        下一步
      </button>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const props = defineProps({
  address: {
    type: String,
    default: ''
  },
  recipient: {
    type: String,
    default: ''
  }
})

const formData = reactive({
  username: props.recipient,
  address: props.address
})

defineEmits(['next'])
</script>
