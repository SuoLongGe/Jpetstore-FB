<template>
  <div class="container-fluid" style="min-height: 50vh; width: auto; min-width: 70vw;">
    <Header />
    <OrderPageHeader />
    
    <div class="row">
      <div class="col-md-12 d-flex flex-column">
        <OrderList 
          :orders="orders"
          @refresh="fetchOrders"
        />
        <div class="d-flex justify-content-center mt-3">
          <el-pagination
            v-model:current-page="pagination.current"
            :page-size="pagination.size"
            :total="pagination.total"
            layout="prev, pager, next"
            @current-change="fetchOrders"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import Header from '@/layouts/components/header.vue'
import OrderPageHeader from './components/OrderPageHeader.vue'
import OrderList from './components/OrderList.vue'
import { GET } from '@/api/tool'

// 订单数据
const orders = ref([])
// 分页信息
const pagination = ref({
  current: 1,
  size: 5,
  total: 0,
  pages: 1
})

// 获取订单数据
const fetchOrders = async (current = 1, size = 5) => {
  GET(`/c/orders/current/${current}/size/${size}`,{})
    .then(res => {
      if (res.success) {
        
        orders.value = res.data.data.map(order => ({
          orderId: order.orderid,
          paid: order.is_paid === '1',
          address: order.address,
          recipient: order.recipient,
          goodsList: order.products.map(product => ({
            productId: product.productid,
            quantity: product.quantity,
            name: `${product.name}`,
            picture: product.picture,
            price: product.product_price
          }))
        }))
        
        // 更新分页信息
        pagination.value = {
          current: res.data.current,
          size: res.data.size,
          total: res.data.total,
          pages: res.data.pages
        }
        
      } else if(res.success == false) {
        console.error('获取订单失败:', res.message)
      }else{
        console.error('服务器连接失败')
      }
    })
    .catch(err => {
      console.error('API调用失败:', err)
    })
}

onMounted(() => {
  fetchOrders(pagination.value.current, pagination.value.size)
})
</script>
