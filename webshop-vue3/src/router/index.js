import Index from '@/pages/index/index.vue'
import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/pages/login/login.vue'
import Register from '@/pages/register/register.vue'
import UserSetting from '@/pages/usersetting/usersetting.vue'
import Order from '@/pages/order/order.vue'
import shoppingcart from '@/pages/shoppingcart/shoppingcart.vue'
import Pay from '@/pages/pay/pay.vue'
import Goodsinfo from '@/pages/goodsinfo/goodsinfo.vue'
import GiteeCallback from '@/pages/login/giteeCallback.vue'
import UserSettingGiteeCallback from '@/pages/usersetting/components/GiteeCallback.vue'
//统一声明所有路由
const routes = [
    {
        path: '/',  //路由地址
        component: Index,   //对应组件
        meta:{  //meta信息
            title: '首页' //页面标题
        }
    },
    {
        path: '/login', // 登录页
        component: Login,
        meta: {
            title: '登录'
        }
    },
    {
        path: '/oauth/callback', // Gitee登录回调
        component: GiteeCallback,
        meta: {
            title: 'Gitee登录'
        }
    },
    {
        path: '/c/usersetting/gitee-callback', // Gitee绑定回调
        component: UserSettingGiteeCallback,
        meta: {
            title: 'Gitee账户绑定'
        }
    },
    {
        path: '/c/order', //我的订单页
        component: Order,
        meta: {
            title: '我的订单'
        }
    },
    {
        path: '/c/shoppingcart', // 登录页
        component: shoppingcart,
        meta: {
            title: '购物车'
        }
    },
    {
        path: '/register',
        component: Register,
        meta: {
            title: '注册'
        }
    },
    {
        path: '/c/pay',
        component: Pay,
        meta: {
            title: '支付'
        }
    },
    {
        path: '/goodsinfo',
        component: Goodsinfo,
        meta: {
            title: '商品'
        },
        props: true
    },
    {
        path: '/c/usersetting',
        component: UserSetting,
        meta: {
            title: '用户信息编辑'
        },
        props: true
    },
]

//创建路由
const router = createRouter({
    //指定路由的历史管理方式,hash模式指的是URL的路径是通过hash符号#进行标识
    history: createWebHistory(),
    routes,
})


// ES6模块导出语句,它用于将router对象导出,以使其他文件可以导入和使用这个对象
export default router
