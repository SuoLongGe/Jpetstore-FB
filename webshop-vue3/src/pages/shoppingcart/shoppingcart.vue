<template>
  <div class="container-fluid" style="min-height: 50vh; width: auto; min-width: 70vw;">
    <ShoppingCartHeader />
    
  <div class="row">
      <!-- 左侧商品列表 -->
      <div class="col-md-9 d-flex flex-column border-end" style="min-height: 20vh;">
        <ShoppingCartList 
          :key="`page-${pagination.current}`"
          :items="cartItems"
          @update-quantity="handleUpdateQuantity"
          @delete-item="handleDeleteItem"
          @toggle-select="handleToggleSelect"
        />
        <div class="d-flex justify-content-center mt-3">
          <el-pagination
            v-model:current-page="pagination.current"
            :page-size="pagination.size"
            :total="pagination.total"
            layout="prev, pager, next"
            @current-change="handlePageChange"
          />
        </div>
      </div>
      
      <!-- 右侧结算区域 -->
      <ShoppingCartSummary 
        :selected-items="selectedItems"
        :total-price="totalPrice"
        @checkout="handleCheckout"
        @search="handleSearch"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import ShoppingCartHeader from './ShoppingCartHeader.vue'
import ShoppingCartList from './ShoppingCartList.vue'
import ShoppingCartSummary from './ShoppingCartSummary.vue'
import { GET, PATCH, DELETE, POST } from '@/api/tool'
import { showMessage } from '@/composables/util'
import { getUserInfo } from '@/composables/cookie'

// 购物车数据
const cartItems = ref([])
const selectedItems = ref([])
const totalPrice = ref(0)
const pagination = ref({
  current: 1,
  size: 4,
  total: 0,
  pages: 1
})

// 获取购物车数据
const fetchCartItems = async () => {
  GET(`/c/shoppingcarts/current/${pagination.value.current}/size/${pagination.value.size}`,{})
    .then(res => {
      if(res.success == true){
        cartItems.value = res.data.map(item => ({
          id: item.shoppingcartid,
          productId: item.productid,
          name: item.productname,
          price: parseFloat(item.productprice),
          quantity: item.quantity,
          image: item.goodspicture.startsWith('http') 
            ? item.goodspicture 
            : `/src/assets/pictures/${item.goodspicture}`,
          goodsId: item.goodsid
        }))
        pagination.value = {
          current: res.current,
          size: res.size,
          total: res.total,
          pages: res.pages
        }
      }else if(res.success == false){
        showMessage(res.message, 'error')
      }else{
        showMessage('服务器连接失败', 'error')
      }
    })
}

// 处理页码变化
const handlePageChange = (page) => {
  // 清空已选商品
  selectedItems.value = []
  // 重置总价
  totalPrice.value = 0
  // 更新页码
  pagination.value.current = page
  // 获取新页数据
  fetchCartItems()
}

// 更新商品数量
const handleUpdateQuantity = (id, quantity) => {
  const item = cartItems.value.find(item => item.id === id)
  if (item) {
    PATCH(`/c/shoppingcarts/shoppingcartid/${id}/quantity/${quantity}`, {})
      .then(res => {
        if (res.success) {
         showMessage('更新数量成功')
          item.quantity = quantity
          calculateTotalPrice()
        } else {
          showMessage(res.message, 'error')
          // 恢复原数量
          fetchCartItems()
        }
      })
      .catch(() => {
        showMessage('更新数量失败', 'error')
        fetchCartItems()
      })
  }
}

// 删除商品
const handleDeleteItem = (id) => {
  DELETE(`/c/shoppingcarts/shoppingcartid/${id}`)
    .then(res => {
      if (res.success) {
        cartItems.value = cartItems.value.filter(item => item.id !== id)
        selectedItems.value = selectedItems.value.filter(itemId => itemId !== id)
        calculateTotalPrice()
        showMessage('删除成功', 'success')
      } else {
        showMessage(res.message, 'error')
      }
    })
    .catch(() => {
      showMessage('删除失败', 'error')
    })
}

// 切换商品选择状态
const handleToggleSelect = (id, isSelected) => {
  console.log('Before update:', selectedItems.value)
  if (isSelected) {
    selectedItems.value = [...selectedItems.value, id]
  } else {
    selectedItems.value = selectedItems.value.filter(itemId => itemId !== id)
  }
  console.log('After update:', selectedItems.value)
  calculateTotalPrice()
}

// 计算总价
const calculateTotalPrice = () => {
  console.log('Calculating total price...')
  totalPrice.value = cartItems.value
    .filter(item => selectedItems.value.includes(item.id))
    .reduce((sum, item) => {
      const itemTotal = item.price * item.quantity
      console.log(`Item ${item.id}: ${item.price} * ${item.quantity} = ${itemTotal}`)
      return sum + itemTotal
    }, 0)
  console.log('Total price:', totalPrice.value)
}



// 处理结算
const handleCheckout = async (items) => {
  try {
    // 1. 准备订单数据
    const orderData = {
      shoppingcarts: items.map(id => ({
        shoppingcartid: id
      })),
      address: '', // 从用户信息获取
      recipient: '' // 从用户信息获取
    }

    // 2. 获取用户信息
    const userInfo = getUserInfo();
    
    orderData.address = userInfo.address
    orderData.recipient = userInfo.name

    // 3. 发送订单请求
    POST('/c/orders', orderData).then(res => {
      if(res.success == true){
        showMessage('订单创建成功', 'success')
        // 清空已选商品
        selectedItems.value = []
        calculateTotalPrice()
        // 刷新购物车数据
        fetchCartItems()
      }else if(res.success == false){
        showMessage(res.message, 'error')
      }else{
        showMessage('服务器连接失败', 'error')
      }
    })
  } catch (error) {
    console.error(error)
    showMessage('订单创建失败', 'error')
  }
}

// 处理搜索
const handleSearch = (keyword) => {
  // 在ShoppingCartSummary中处理搜索逻辑了已经
  // 其实应该放在这里会好一些
  console.log('搜索关键词:', keyword)
}

onMounted(() => {
  fetchCartItems()
})
</script>
