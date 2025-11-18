<template>
  <div>
    <div class="mb-3">
      <label class="form-label">总价:</label>
      <input 
        type="text" 
        class="form-control" 
        :value="totalPrice" 
        readonly
      >
    </div>

    <div class="mb-3">
      <label class="form-label">支付方式:</label>
      <select 
        v-model="paymentMethod" 
        class="form-control"
      >
        <option value="wechat">微信</option>
        <option value="alipay">支付宝</option>
      </select>
    </div>

    <div class="d-flex justify-content-around mt-4 mb-3">
      <button 
        type="button" 
        class="btn btn-secondary" 
        @click="$emit('prev')"
      >
        上一步
      </button>
      <button 
        type="button" 
        class="btn btn-success" 
        @click="handleSubmit"
      >
        确认购买
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const paymentMethod = ref('wechat')

const props = defineProps({
  totalPrice: {
    type: [String, Number],
    required: true
  }
})

const emit = defineEmits(['prev', 'submit'])

const handleSubmit = () => {
  emit('submit', {
    paymentMethod: paymentMethod.value
  })
}
</script>
