<template>
  <div class="flex-grow-1 d-flex flex-column justify-content-center align-items-center p-2" style="overflow-y: auto;">
    <div v-if="orders.length === 0" class="text-center">
      <span style="color: red; font-size: 1.5em;">暂无订单</span>
    </div>
    
    <div v-else class="p-2" style="flex: 1; min-height: 100px; width: 100%;">
      <OrderItem 
        v-for="(order, index) in orders" 
        :key="`${order.orderId}-${index}`"
        :order="order"
      />
    </div>
  </div>
</template>

<script setup>
import OrderItem from './OrderItem.vue'

defineProps({
  orders: {
    type: Array,
    default: () => []
  }
})

defineEmits(['refresh'])
</script>
