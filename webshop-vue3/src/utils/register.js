import { useRegisterStore } from '@/stores/registerStore'

export const registerConfig = {
  setMockData: (data) => {
    const store = useRegisterStore()
    store.updateMockData(data)
  },
  setValidationRules: (rules) => {
    // 可以扩展验证规则
  }
}