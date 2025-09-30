<template>
  <div style="padding:20px;">
    <h2>管理员界面</h2>
    <el-table :data="users" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="100"/>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="role" label="角色" width="120"/>
    </el-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'

const users = ref([])

onMounted(async () => {
  try {
    const res = await api.get('/admin/users')
    console.log("后端返回数据:", res)

    // 如果后端返回格式是 { code, msg, data }
    if (res.code === 200 && res.data) {
      users.value = res.data
    } 
    // 如果拦截器直接返回数组
    else if (Array.isArray(res)) {
      users.value = res
    } 
    else {
      console.warn("返回数据格式不符合预期:", res)
    }
  } catch (e) {
    console.error("获取用户失败:", e)
  }
})
</script>
