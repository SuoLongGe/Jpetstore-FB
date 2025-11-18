<template>
  <div class="flex-grow-1 d-flex flex-column justify-content-start p-2" style="overflow-y: auto;">
    <div v-if="items.length === 0" class="text-center">
      <span style="color: red;font-size: 1.5em">购物车为空</span>
    </div>
    
    <div v-else class="border p-2" style="flex: 1; min-height: 100px;">
        <ShoppingCartItem 
          v-for="(item, index) in items" 
          :key="`${item.id}-${index}`"
          :item="item"
          @update-quantity="handleUpdateQuantity"
          @delete-item="handleDeleteItem"
          @toggle-select="(id, isSelected) => {
            console.log('ShoppingCartList received toggle-select:', id, isSelected)
            $emit('toggle-select', id, isSelected)
          }"
        />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ShoppingCartItem from './ShoppingCartItem.vue'

const props = defineProps({
  items: {
    type: Array,
    default: () => []
  }
})

const emit = defineEmits(['update-quantity', 'delete-item', 'toggle-select'])

const handleUpdateQuantity = (id, quantity) => {
  emit('update-quantity', id, quantity)
}

const handleDeleteItem = (id) => {
  emit('delete-item', id)
}


</script>
