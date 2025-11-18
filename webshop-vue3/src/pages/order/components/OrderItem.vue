<template>
  <div class="order-item border p-3 mb-3" style="border-radius: 5px; box-shadow: 0 0 5px rgba(0,0,0,0.1);">
    <div class="order-header d-flex justify-content-between align-items-center mb-2">
      <h5>
        订单 ID: {{ order.orderId }}, 
        状态: {{ order.paid ? '已支付' : '未支付' }},
        地址: {{ order.address }},
        收件人: {{ order.recipient }}
      </h5>
      <div>
        <h6 class="d-inline-block me-3">总价: {{ totalPrice }}</h6>
        <button 
          v-if="!order.paid"
          @click="goToPay(order.orderId)"
          class="btn btn-primary btn-sm"
        >
          去支付
        </button>
      </div>
    </div>
    
    <ul class="list-unstyled">
      <li 
        v-for="(goods, index) in order.goodsList" 
        :key="index"
        class="d-flex justify-content-between align-items-center mb-2"
      >
        <div class="col-6">
          <span>商品名: {{ goods.name }}, 数量: {{ goods.quantity }}</span>
        </div>
        <div class="col-3">
          <img 
            :src="goods.picture.startsWith('http') ? goods.picture : `/src/assets/pictures/${goods.picture}`" 
            :alt="goods.name" 
            style="width: auto; height: 10vh; margin-right: 10px;"
          >
        </div>
        <div class="col-3">
          <span>单价: {{ goods.price }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>


<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const props = defineProps({
  order: {
    type: Object,
    required: true
  }
})

const totalPrice = computed(() => {
  const total = props.order.goodsList.reduce((sum, goods) => {
    return sum + (goods.price * goods.quantity)
  }, 0)
  return total.toFixed(2)
})

const goToPay = (orderId) => {
  router.push({
    path: '/c/pay',
    query: {
      orderid: encodeURIComponent(orderId)
    }
  })
}
</script>
