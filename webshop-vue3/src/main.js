import '@/assets/main.css'

import { createApp } from 'vue'     //引入createApp方法
import App from '@/App.vue'     //引入App.vue方法
//import './assets/main.css'  
import router from '@/router'
// 导入全局路由守卫
import '@/permission'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'animate.css'
import 'nprogress/nprogress.css'
// 引入全局状态管理 Pinia
import pinia from '@/stores'

//创建应用,并将App根组件挂载到<div id="#app"></div>中
const app = createApp(App)

app.use(router)
app.use(pinia)

for (const [key, component] of Object.entries(ElementPlusIconsVue)){
    app.component(key, component)
}

app.mount('#app')