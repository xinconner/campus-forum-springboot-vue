import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";

const authItemName = "authorize"

const accessHeader = () => {
    return {
        'Authorization': `Bearer ${takeAccessToken()?.token}`
    }
}

const defaultError = (error) => {
    console.error(error)
    const response = error.response
    const status = response?.status
    const message = response?.data?.message
    if (message && status >= 400 && status < 500) {
        ElMessage.warning(message)
        return
    }
    if (status === 429) {
        ElMessage.error(message)
    } else {
        ElMessage.error('发生了一些错误，请联系管理员')
    }
}

const defaultFailure = (message, status, url) => {
    console.warn(`请求地址: ${url}, 状态码: ${status}, 错误信息: ${message}`)
    ElMessage.warning(message)
}

function takeAccessToken() {
    const str = localStorage.getItem(authItemName) || sessionStorage.getItem(authItemName);
    if(!str) return null
    const authObj = JSON.parse(str)
    if(new Date(authObj.expire) <= new Date()) {
        deleteAccessToken()
        ElMessage.warning("登录状态已过期，请重新登录！")
        return null
    }
    return authObj
}

function storeAccessToken(remember, token, expire,role){
    const authObj = { token, expire, role }
    const str = JSON.stringify(authObj)
    if(remember) {
        sessionStorage.removeItem(authItemName)
        localStorage.setItem(authItemName, str)
    } else {
        localStorage.removeItem(authItemName)
        sessionStorage.setItem(authItemName, str)
    }
}

function deleteAccessToken(redirect = false) {
    localStorage.removeItem(authItemName)
    sessionStorage.removeItem(authItemName)
    if(redirect) {
        router.push({ name: 'welcome-login' })
    }
}

function internalPost(url, data, headers, success, failure, error = defaultError){
    axios.post(url, data, { headers: headers }).then(({data}) => {
        if(data.code === 200) {
            success(data.data)
        } else if(data.code === 401 && url !== '/api/auth/login') {
            failure('登录状态已过期，请重新登录！')
            deleteAccessToken(true)
        } else {
            failure(data.message, data.code, url)
        }
    }).catch(err => {
        const response = err.response
        failure(
            response?.data?.message || '网络请求失败，请稍后重试',
            response?.status,
            url
        )
    })
}

function internalGet(url, headers, success, failure, error = defaultError){
    axios.get(url, { headers: headers }).then(({data}) => {
        if(data.code === 200) {
            success(data.data)
        } else if(data.code === 401) {
            failure(data.message ? data.message : '登录状态已过期，请重新登录！')
            deleteAccessToken(true)
        } else {
            failure(data.message, data.code, url)
        }
    }).catch(err => error(err))
}

function login(username, password, remember, success, failure = defaultFailure){
    internalPost('/api/auth/login', {
        username: username,
        password: password
    }, {
        'Content-Type': 'application/x-www-form-urlencoded'
    }, (data) => {
        storeAccessToken(remember, data.token, data.expire,data.role)
        ElMessage.success(`登录成功，欢迎 ${data.username} 来到我们的系统`)
        success(data)
    }, failure)
}

function post(url, data, success, failure = defaultFailure) {
    internalPost(url, data, accessHeader() , success, failure)
}

function fetchPost(url, data) {
    return fetch(axios.defaults.baseURL + url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${takeAccessToken()?.token}`
        },
        body: JSON.stringify(data)
    })
}

function logout(success, failure = defaultFailure){
    get('/api/auth/logout', () => {
        deleteAccessToken()
        ElMessage.success(`退出登录成功，欢迎您再次使用`)
        success()
    }, failure)
}

function get(url, success, failure = defaultFailure) {
    internalGet(url, accessHeader(), success, failure)
}

function isUnauthorized() {
    return !takeAccessToken()
}

function isRoleAdmin() {
    return takeAccessToken()?.role === 'admin'
}

export { post, get, login, logout,isRoleAdmin, isUnauthorized, accessHeader,fetchPost }
