<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '\''64px'\'' : '\''220px'\''" class="aside">
      <div class="logo">{{ isCollapse ? '\''AI'\'' : '\''AI智能食材管理'\'' }}</div>
      <el-menu :default-active="route.path" router :collapse="isCollapse" background-color="#001529" text-color="#fff" active-text-color="#409EFF">
        <el-menu-item index="/dashboard"><el-icon><DataAnalysis /></el-icon><span>工作台</span></el-menu-item>
        <el-sub-menu index="2"><template #title><el-icon><Goods /></el-icon><span>食材管理</span></template>
          <el-menu-item index="/ingredients">食材列表</el-menu-item>
          <el-menu-item index="/categories">分类管理</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/suppliers"><el-icon><UserFilled /></el-icon><span>供应商管理</span></el-menu-item>
        <el-menu-item index="/orders"><el-icon><List /></el-icon><span>采购订单</span></el-menu-item>
        <el-sub-menu index="5"><template #title><el-icon><MagicStick /></el-icon><span>AI智能</span></template>
          <el-menu-item index="/ai/chat">AI问答助手</el-menu-item>
          <el-menu-item index="/ai/article">文章生成</el-menu-item>
          <el-menu-item index="/ai/image">AI绘图</el-menu-item>
          <el-menu-item index="/ai/analysis">智能分析</el-menu-item>
          <el-menu-item index="/ai/history">历史记录</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/reports"><el-icon><TrendCharts /></el-icon><span>数据报表</span></el-menu-item>
        <el-menu-item v-if="userStore.role === '\''ADMIN'\''" index="/users"><el-icon><Setting /></el-icon><span>用户管理</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <el-icon style="cursor:pointer;font-size:20px" @click="isCollapse=!isCollapse"><Fold /></el-icon>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info"><el-icon><User /></el-icon> {{ userStore.displayName || userStore.username }}</span>
            <template #dropdown>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main"><router-view /></el-main>
    </el-container>
  </el-container>
</template>
<script setup>
import { ref } from '\''vue'\''
import { useRoute, useRouter } from '\''vue-router'\''
import { useUserStore } from '\''../utils/store.js'\''
const route = useRoute(); const router = useRouter(); const userStore = useUserStore()
const isCollapse = ref(false)
const logout = () => { userStore.logout(); router.push('\''/login'\'') }
</script>
<style scoped>
.layout-container { height: 100vh; }
.aside { background: #001529; overflow-y: auto; transition: width 0.3s; }
.logo { height: 60px; line-height: 60px; text-align: center; color: #fff; font-size: 16px; font-weight: bold; border-bottom: 1px solid #0c2135; }
.header { background: #fff; border-bottom: 1px solid #e6e6e6; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; }
.header-right { display: flex; align-items: center; gap: 20px; }
.user-info { cursor: pointer; display: flex; align-items: center; gap: 5px; }
.main { background: #f0f2f5; padding: 20px; overflow-y: auto; }
</style>
