<template>
  <form @submit.prevent="handleSubmit">
    <div class="mb-3">
      <label class="form-label" style="font-size: 1.0em;">昵称：</label>
      <input 
        v-model="formData.name"
        class="form-control" 
        type="text" 
        placeholder="请输入昵称..."
      >
    </div>
    <div class="mb-3">
      <label class="form-label" style="font-size: 1.0em;">密码：</label>
      <input 
        v-model="formData.password"
        class="form-control" 
        type="password" 
        placeholder="请输入密码..."
      >
    </div>
    <div class="mb-3">
      <label class="form-label" style="font-size: 1.0em;">邮箱：</label>
      <input 
        v-model="formData.email"
        class="form-control" 
        type="email" 
        placeholder="请输入邮箱..."
      >
    </div>
    <div class="mb-3">
      <label class="form-label" style="font-size: 1.0em;">地址：</label>
      <input 
        v-model="formData.address"
        class="form-control" 
        type="text" 
        placeholder="请输入地址..."
      >
    </div>
    <div class="mb-3">
      <button 
        class="btn btn-primary w-100" 
        type="submit" 
        style="background-color: rgb(255, 98, 0);border: none;"
        :disabled="props.loading"
      >
        {{ props.loading ? '提交中...' : '提交' }}
      </button>
    </div>
    
    <div class="mb-3">
      <button 
        class="btn btn-outline-secondary w-100"
        type="button"
        @click="handleGiteeBind"
      >
        {{ props.userInfo.bindgitee ? '更改Gitee账户绑定' : '绑定Gitee账户' }}
      </button>
      <div v-if="props.userInfo.bindgitee" class="text-muted small mt-1">
        已绑定Gitee账户
      </div>
    </div>
  </form>

  <div v-if="props.error" class="alert alert-danger mt-3">
    {{ props.error }}
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  userInfo: {
    type: Object,
    required: true
  },
  loading: {
    type: Boolean,
    default: false
  },
  error: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['submit', 'gitee-bind'])

const formData = ref({
  name: '',
  password: '',
  email: '',
  address: ''
})

const message = ref('')

// 监听props变化初始化表单数据
watch(() => props.userInfo, (newVal) => {
  if (newVal) {
    formData.value = {
      name: newVal.name || '',
      password: newVal.password || '',
      email: newVal.email || '',
      address: newVal.address || ''
    }
  }
}, { immediate: true })

const handleSubmit = () => {
  emit('submit', formData.value)
}

const handleGiteeBind = () => {
  emit('gitee-bind')
}
</script>
