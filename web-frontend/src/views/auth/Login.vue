<template>
  <div class="login-container">
    <div class="login-card">
      <h2 class="login-title">AI智能食材管理系统</h2>
      <el-form :model="form" :rules="rules" ref="formRef" size="large">
        <el-form-item prop="username"><el-input v-model="form.username" placeholder="用户名" :prefix-icon="User" /></el-form-item>
        <el-form-item prop="password"><el-input v-model="form.password" type="password" placeholder="密码" :prefix-icon="Lock" show-password /></el-form-item>
        <el-form-item>
          <el-button type="primary" style="width:100%" @click="handleLogin" :loading="loading">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="width:100%" @click="handleRegister">注册账号</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script setup>
import { ref, reactive } from '\''vue'\''
import { useRouter } from '\''vue-router'\''
import { User, Lock } from '\''@element-plus/icons-vue'\''
import { ElMessage } from '\''element-plus'\''
import { authApi } from '\''../../api/index.js'\''
import { useUserStore } from '\''../../utils/store.js'\''
const router = useRouter(); const userStore = useUserStore(); const formRef = ref(null); const loading = ref(false)
const form = reactive({ username: '\'''\'', password: '\'''\'' })
const rules = { username: [{ required: true, message: '\''请输入用户名'\'', trigger: '\''blur'\'' }], password: [{ required: true, message: '\''请输入密码'\'', trigger: '\''blur'\'' }] }
const handleLogin = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return; loading.value = true
  try {
    const res = await authApi.login(form)
    userStore.setUser(res.data)
    ElMessage.success('\''登录成功'\'')
    router.push('\''/dashboard'\'')
  } catch (e) { /* error in interceptor */ }
  finally { loading.value = false }
}
const handleRegister = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return; loading.value = true
  try {
    await authApi.register(form)
    ElMessage.success('\''注册成功，请登录'\'')
  } catch (e) { /* error in interceptor */ }
  finally { loading.value = false }
}
</script>
<style scoped>
.login-container { height: 100vh; display: flex; justify-content: center; align-items: center; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.login-card { width: 400px; padding: 40px; background: #fff; border-radius: 8px; box-shadow: 0 20px 60px rgba(0,0,0,0.3); }
.login-title { text-align: center; margin-bottom: 30px; color: #303133; font-size: 24px; }
</style>
