<template>
  <div >
    <label class="form-label" style="font-size: 1.2em;">验证码：</label>
    <div >
      <input
        v-model="inputValue"
        class="form-control my-2"
        placeholder="请输入验证码"
        style="width: 100%;"
      />
      <div class="mt-2">
        <img
          :src="captchaUrl"
          class="captcha-image"
          @click="refreshCaptcha"
           style="cursor: pointer;"
        />
        <button type="button" class="btn btn-primary my-2" @click="refreshCaptcha">
          生成新验证码
        </button>
      </div>
    </div>
    <span v-if="error" class="error-message" style="color: red;font-size: 1.0em">{{ error }}</span>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { GET } from '@/api/tool'

const props = defineProps({
  modelValue: String,
  error: String
})

const emit = defineEmits(['update:modelValue'])

const captchaUrl = ref('/default-captcha.jpg')

const inputValue = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val)
})

const refreshCaptcha = () => {
  GET('/createImageCode', {
    params: {
      t: Date.now()
    },
    responseType: 'blob'
  })
  .then(response => {
    // 释放之前的URL对象
    if (captchaUrl.value.startsWith('blob:')) {
      URL.revokeObjectURL(captchaUrl.value)
    }
    captchaUrl.value = URL.createObjectURL(response)
  })
  .catch(error => {
    console.error('获取验证码失败:', error)
    captchaUrl.value = '/default-captcha.jpg'
  })
}

// 初始化时获取验证码
refreshCaptcha()
</script>

<style scoped>
.captcha-group {
  margin-bottom: 1.5rem;
}

.captcha-input-group {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.captcha-image {
  height: 40px;
  border: 1px solid #ddd;
  cursor: pointer;
}

.refresh-btn {
  white-space: nowrap;
}
</style>
