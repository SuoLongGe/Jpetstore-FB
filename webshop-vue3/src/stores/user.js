import { defineStore } from "pinia";
import { ref } from "vue";
import {GET} from"@/api/tool.js"
import { removeAuthData } from "@/composables/cookie";

export const useUserStore = defineStore("user", ()=>{
        const userInfo = ref({})     // 定义一个 userInfo 变量，初始值为 {}

        function setUserInfo() {
            GET("/c/users",{}).then(res=>{
                if(res.success === true){
                    userInfo.value = res.data
                }
            })
        }
        
        function logout() {
            removeAuthData()
            userInfo.value = {}
        }

        return {
            userInfo,
            setUserInfo,
            logout
        }
    },
    {
        persist: true,
    }
)  
