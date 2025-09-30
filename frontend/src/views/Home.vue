<template>
  <div style="padding:20px;">
    <h2>欢迎回来，{{ user.username }}</h2>
    <p>角色: {{ user.role }}</p>
    <el-button type="primary" @click="goAdmin" v-if="user.role==='ADMIN'">
      进入管理员界面
    </el-button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
import { useRouter } from 'vue-router'

const user = ref({})
const router = useRouter()

onMounted(async () => {
  const res = await api.get('/user/info')
  if (res.code === 200) {
    user.value = res.data
  }
})

const goAdmin = () => {
  router.push('/admin')
}
</script>
