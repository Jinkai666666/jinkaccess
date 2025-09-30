<template>
  <div style="max-width: 400px; margin: 50px auto;">
    <el-card>
      <h2 style="text-align: center;">JinkAccess 登录</h2>
      <el-form :model="form">
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import api from '../api/index.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = reactive({ username: '', password: '' })

const login = async () => {
  try {
    const res = await api.post('/user/login', form)
    if (res.code === 200) {
      localStorage.setItem('token', res.data) // 保存 token
      alert('登录成功')
      router.push('/home')
    } else {
      alert(res.msg)
    }
  } catch (e) {
    alert('登录失败')
  }
}
</script>
