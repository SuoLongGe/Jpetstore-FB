<template>
    <div class="container-fluid" >
        <Header></Header>
    </div>
    <div class="container-fluid p-4">
    <SearchBar ref="searchBarRef" @search-result="handleSearchResult"></SearchBar>
    </div>

    <div class="container" style="height: auto;min-height: 85vh;">
        <div class="row">
            <GoodsCard
          v-for="agoods in goods"
          :key="agoods.id"
          :goods="agoods"
        />
      </div>
      <div class="row mt-4">
        <div class="col-12 d-flex justify-content-center">
          <nav aria-label="Page navigation">
            <ul class="pagination">
              <li class="page-item" :class="{ disabled: currentPage === 1 }">
                <button class="page-link" @click="changePage(currentPage - 1)">上一页</button>
              </li>
              <li class="page-item" v-for="page in pageCount" :key="page" :class="{ active: currentPage === page }">
                <button class="page-link" @click="changePage(page)">{{ page }}</button>
              </li>
              <li class="page-item" :class="{ disabled: currentPage === pageCount }">
                <button class="page-link" @click="changePage(currentPage + 1)">下一页</button>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>

    <Footer></Footer>
</template>

<script setup>
import { onMounted, ref, nextTick, watch } from 'vue';
import { useRoute } from 'vue-router';
import { initCollapses } from 'flowbite';
import Header from '@/layouts/components/header.vue';
import Footer from '@/layouts/components/footer.vue';
import SearchBar from '@/layouts/components/searchBar.vue';
import GoodsCard from '@/layouts/components/goodsCard.vue';
import { getToken, setUserInfo } from '@/composables/cookie';
import { GET } from '@/api/tool';

const route = useRoute()
// 暂时写死的商品数据
const emit = defineEmits(['page-change'])
const goods = ref([])
const currentPage = ref(1)
const pageCount = ref(1)
const searchBarRef = ref(null)


const handleSearchResult = (result) => {
      goods.value = result.goods?.map(goods => ({
        id: goods.goodsid,
        name: goods.goodsname,
        price: parseFloat(goods.goodsprice),
        image: goods.goodspicture.startsWith('http') 
          ? goods.goodspicture 
          : `/src/assets/pictures/${goods.goodspicture}`
      })) || []
      currentPage.value = result.pagination?.current || 1
      pageCount.value = result.pagination?.pages || 1
}



const changePage = (page) => {
  if (page < 1 || page > pageCount.value) return
  currentPage.value = page
  console.log('Changing to page:', page)
  // 确保组件引用存在
  nextTick(() => {
    if (searchBarRef.value && searchBarRef.value.handleSearch) {
      searchBarRef.value.handleSearch(currentPage.value)
    } else {
      console.error('SearchBar reference not available')
    }
  })
}


// 监听路由search参数变化
watch(() => route.query.search, (newSearch) => {
  if (newSearch && searchBarRef.value) {
    nextTick(() => {
      searchBarRef.value.searchKeyword = newSearch
      searchBarRef.value.handleSearch(1)
    })
  }
}, { immediate: true })

onMounted(()=>{
    initCollapses();
    // 页面加载时自动搜索
    nextTick(() => {
      if (route.query.search&&searchBarRef.value && searchBarRef.value.handleSearch) {
        searchBarRef.value.searchKeyword = route.query.search
        searchBarRef.value.handleSearch(1)
      }
      else if (searchBarRef.value && searchBarRef.value.handleSearch) {
        searchBarRef.value.handleSearch(1) // 默认加载第一页
        console.log('SearchBar reference available')
      }
    })

    // 获取用户信息
    const token = getToken()
    if (token) {
      GET('/c/users').then(userInfo => {
        console.log('用户信息:', userInfo)
        setUserInfo(userInfo.data) // 存储用户信息到cookie
      }).catch(error => {
        console.error('获取用户信息失败:', error)
      })
    }
})
</script>