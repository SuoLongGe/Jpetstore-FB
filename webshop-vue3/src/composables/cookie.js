import { useCookies } from '@vueuse/integrations/useCookies'

const cookie = useCookies()

// ============================== Token 令牌 ==============================

// 存储在 Cookie 中的 key
const TOKEN_KEY = 'Authorization'
const USER_INFO_KEY = 'UserInfo'

// 获取 Token 值
export function getToken() {
    return cookie.get(TOKEN_KEY)
}

// 设置 Token 到 Cookie 中
export function setToken(token) {
    console.log('Setting token cookie with options')
    return cookie.set(TOKEN_KEY, token, {
        path: '/',
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'lax',
        expires: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000), // 7天有效期
        httpOnly: false
    })
}

// 存储用户信息
export function setUserInfo(userInfo) {
    console.log('Storing user info in cookie')
    return cookie.set(USER_INFO_KEY, JSON.stringify(userInfo), {
        path: '/',
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'lax',
        expires: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000), // 7天有效期
        httpOnly: false
    })
}

// 获取用户信息
export function getUserInfo() {
    const userInfo = cookie.get(USER_INFO_KEY)
    try {
        return userInfo && typeof userInfo === 'string' ? JSON.parse(userInfo) : userInfo
    } catch (e) {
        console.error('Failed to parse user info:', e)
        return null
    }
}

// 删除 Token 和用户信息
export function removeAuthData() {
    cookie.remove(TOKEN_KEY, { path: '/' })
    cookie.remove(USER_INFO_KEY, { path: '/' })
}

