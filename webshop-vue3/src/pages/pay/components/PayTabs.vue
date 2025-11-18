<template>
  <div class="card border-0" style="width: 500px; min-height: 63.5%; background-color: white;">
    <!-- 选项卡按钮 -->
    <div class="d-flex justify-content-around mb-4">
      <button 
        type="button" 
        class="tab-button" 
        :class="{ active: activeTab === 'customer' }"
        @click="activeTab = 'customer'"
      >
        客户信息
      </button>
      <button 
        type="button" 
        class="tab-button" 
        :class="{ active: activeTab === 'product' }"
        @click="activeTab = 'product'"
      >
        商品信息
      </button>
      <button 
        type="button" 
        class="tab-button" 
        :class="{ active: activeTab === 'payment' }"
        @click="activeTab = 'payment'"
      >
        支付信息
      </button>
    </div>

    <!-- 选项卡内容 -->
    <PayCustomerInfo 
      v-show="activeTab === 'customer'"
      :address="props.address"
      :recipient="props.recipient"
      @next="activeTab = 'product'"
    />
    <PayProductInfo 
      v-show="activeTab === 'product'"
      :products="props.products"
      @prev="activeTab = 'customer'"
      @next="activeTab = 'payment'"
    />
    <PayPaymentInfo 
      v-show="activeTab === 'payment'"
      :totalPrice="props.totalPrice"
      @prev="activeTab = 'product'"
      @submit="handleSubmit"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import PayCustomerInfo from './PayCustomerInfo.vue'
import PayProductInfo from './PayProductInfo.vue'
import PayPaymentInfo from './PayPaymentInfo.vue'

const activeTab = ref('customer')

const handleSubmit = (formData) => {
  emit('submit', {
    ...formData,
    orderId: props.orderId
  })
}

const props = defineProps({
  orderId: {
    type: String,
    required: true
  },
  products: {
    type: Array,
    required: true,
    default: () => []
  },
  address: {
    type: String,
    default: ''
  },
  recipient: {
    type: String,
    default: ''
  },
  totalPrice: {
    type: [String, Number],
    default: 0
  }
})

const emit = defineEmits(['submit'])
</script>

<style scoped>
.tab-button {
  background: none;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
}
.tab-button.active {
  border-bottom: 2px solid #007bff;
  font-weight: bold;
}
</style>
