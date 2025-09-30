import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // 后端服务地址
  timeout: 5000
})

// 请求拦截器：自动带上 token
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, error => Promise.reject(error))

// 响应拦截器：统一处理
api.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response && error.response.status === 403) {
      alert("权限不足")
    }
    return Promise.reject(error)
  }
)

export default api
