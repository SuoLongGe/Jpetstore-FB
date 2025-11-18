<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { GET } from '@/api/tool'
import Header from '@/layouts/components/header.vue'
import PayPageHeader from './components/PayPageHeader.vue'
import PayTabs from './components/PayTabs.vue'
import { PATCH } from '@/api/tool'
import { useRouter } from 'vue-router'

const router = useRouter()

const route = useRoute()
const orderId = ref('')
const orderData = ref(null)
const loading = ref(false)
const error = ref(null)

// 获取订单数据
const fetchOrder = async () => {
  try {
    loading.value = true
    error.value = null
    orderId.value = route.query.orderid
    console.log('获取订单:', orderId.value)
    
    const res = await GET(`/c/orders/${orderId.value}`,{})
    
    if (res.success == true) {
      orderData.value = {
        products: res.data.products.map(p => ({
          productId: p.productid,
          quantity: p.quantity,
          name: p.name 
        })),
        address: res.data.address,
        recipient: res.data.recipient,
        isPaid: res.data.is_paid === '1',
        totalPrice: Number.isInteger(res.data.price) ? res.data.price : parseFloat(res.data.price.toFixed(2))
      }
    } else if(res.success == false) {
        console.error('获取订单失败:', res.message)
    }else{
      throw new Error(res.message || '获取订单失败')
    }
  } catch (err) {
    error.value = err.message
    console.error('获取订单失败:', err)
  } finally {
    loading.value = false
  }
}

const handleSubmit = async () => {
  try {
    loading.value = true
    error.value = null
    
    const response = await PATCH(`/c/orders/${orderId.value}/paid`, {
    })

    if (response.success) {
      // 支付成功处理
      console.log('支付成功:', response.data)
      // 可以添加支付成功跳转或提示
      orderData.value.isPaid = true
      router.push('/c/order')
    } else {
      throw new Error(response.message || '支付失败')
    }
  } catch (err) {
    error.value = err.message
    console.error('支付失败:', err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchOrder()
})
</script>

<template>
  <div class="container-fluid" style="min-height: 50vh;">
    <Header />
    <PayPageHeader />
    
    <div class="d-flex justify-content-center">
      <div style="width: 35%;">
        <div v-if="loading" class="text-center py-5">
          加载中...
        </div>
        
        <div v-else-if="error" class="alert alert-danger">
          {{ error }}
        </div>
        
        <div v-else-if="orderData" class="row">
          <div class="col-md-12 d-flex flex-column">
            <PayTabs 
              :orderId="orderId"
              :products="orderData.products"
              :address="orderData.address"
              :recipient="orderData.recipient"
              :totalPrice="orderData.totalPrice"
              @submit="handleSubmit"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
