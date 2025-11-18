<template>
  
    <form class="d-flex justify-content-center" @submit.prevent="() => handleSearch()">
      <div class="input-group" style="width: 50%">
        <select v-model="selectedCategory" class="form-select" style="width: 10%;height: 100%;" @click="handleSelectCategory">
          <option value="">全部</option>
          <option v-for="category in categories" :key="category.id" :value="category.id">
            {{ category.name }}
          </option>
        </select>
        <input
          v-model="searchKeyword"
          type="text"
          class="form-control me-2"
          placeholder="请输入搜索内容..."
          @input="handleInput"
          style="width: 85%; height: 100%;border-radius: 0;box-shadow: none !important;"
        />
      </div>
      <button type="submit" class="btn btn-primary" style="height: 100%;background-color: rgb(255, 98, 0);border: none;">搜索</button>
    </form>

    <div class="d-flex justify-content-center" style="margin-top: 0px;margin-left: 2.1%;height: 0px;z-index: 9999;">
        <div ref="suggestionsRef" class="alert alert-info" style="width: 44.47%; text-align: left; margin: 0;border-radius: 0;padding: 0;background:white;z-index: 9999;">
            <ul v-if="showSuggestions && suggestions.length" class="suggest-list" style="list-style-type: none; padding: 0; margin: 0;font-size: 1.0em;z-index: 9999;">
                <li
                v-for="(suggestion, index) in suggestions"
                :key="index"
                class="suggest-item"
                @click="selectSuggestion(suggestion.name)"
                >
                {{ suggestion.name }}
                </li>
            </ul>
        </div>    
    </div>

</template>

<script setup>
import { ref } from 'vue'
import { useDebounceFn, onClickOutside } from '@vueuse/core'
import{GET}from "@/api/tool"
// 暂时写死的测试数据
const categories = ref([{ id: 1, name: '电子产品' }, { id: 2, name: '图书'}])
const suggestions = ref([])
const searchKeyword = ref('')
const selectedCategory = ref('')
const showSuggestions = ref(false)
const suggestionsRef = ref(null)

// 点击建议框外部时关闭
onClickOutside(suggestionsRef, () => {
  showSuggestions.value = false
})

const fetchSuggestions = useDebounceFn(() => {
  if (!searchKeyword.value.trim()) {
    suggestions.value = []
    showSuggestions.value = false
    return
  }

  const currentPage = 1
  const pageSize = 10
  // 单次URL编码
  const searchKey = encodeURIComponent(searchKeyword.value.trim())
  
  const apiPath = selectedCategory.value 
    ? `/goods/current/${currentPage}/size/${pageSize}/categoryid/${selectedCategory.value}/searchkey/${searchKey}`
    : `/goods/current/${currentPage}/size/${pageSize}/searchkey/${searchKey}`

  GET(apiPath, {}).then(res => {
    if (res.success) {
      suggestions.value = res.data.map(item => ({
        id: item.goodsid,
        name: item.goodsname,
      }))
      showSuggestions.value = true
    }
  }).catch(err => {
    console.error('获取搜索建议失败:', err)
    showSuggestions.value = false
  })
}, 300)

const handleInput = () => {
  fetchSuggestions()
  

}

const selectSuggestion = (suggestion) => {
  searchKeyword.value = suggestion
  handleSearch()
}

const emit = defineEmits(['search-result'])

const handleSearch = (page) => {
  const currentPage = page || 1
  const pageSize = 10
  const searchKey = encodeURIComponent(searchKeyword.value.trim())
  
  let apiPath
  if (selectedCategory.value && searchKeyword.value.trim()) {
    apiPath = `/goods/current/${currentPage}/size/${pageSize}/categoryid/${selectedCategory.value}/searchkey/${searchKey}`
  } else if (selectedCategory.value) {
    apiPath = `/goods/current/${currentPage}/size/${pageSize}/categoryid/${selectedCategory.value}`
  } else if (searchKeyword.value.trim()) {
    apiPath = `/goods/current/${currentPage}/size/${pageSize}/searchkey/${searchKey}`
  } else {
    apiPath = `/goods/current/${currentPage}/size/${pageSize}`
  }

  GET(apiPath, {}).then(res => {
    if (res.success) {
      emit('search-result', {
        goods: res.data,
        pagination: {
          total: res.total,
          size: res.size,
          current: res.current,
          pages: res.pages
        }
      })
    }
  }).catch(err => {
    console.error('搜索失败:', err)
    showMessage('搜索失败，请稍后再试', 'error')
  })
}

const handleSelectCategory = () => {
  // 处理分类选择逻辑
  GET('/categorys',{}).then(res => {
    if(res.success == true){
        categories.value = res.data.map(item => ({
          id: item.categoryid,
          name: item.categoryname
        }))
    }
})
}

// 暴露方法和数据给父组件
defineExpose({
  handleSearch,
  searchKeyword,
  selectedCategory
})

</script>



<style scoped>

.suggest-item {
    padding-left: 1.5%;
    z-index: 9999;
    background-color: #fff;
}

.suggest-item:hover {
    background-color: #cccccc;
}

</style>