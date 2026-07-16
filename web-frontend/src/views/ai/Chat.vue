<template>
  <el-card style="height:calc(100vh - 140px);display:flex;flex-direction:column">
    <template #header>
      <span><el-icon><ChatDotRound /></el-icon> AI智能问答助手</span>
    </template>
    <div class="chat-messages" ref="msgContainer">
      <div v-for="(msg, i) in messages" :key="i" :class="['\''message'\'', msg.role === '\''user'\'' ? '\''user-msg'\'' : '\''ai-msg'\'']">
        <div class="msg-content">{{ msg.content }}</div>
      </div>
      <div v-if="loading" class="message ai-msg"><div class="msg-content">AI正在思考...</div></div>
    </div>
    <div class="chat-input">
      <el-input v-model="query" placeholder="请输入您的问题，如：如何储存新鲜蔬菜？" size="large" @keyup.enter="sendMessage" :disabled="loading">
        <template #append><el-button type="primary" @click="sendMessage" :loading="loading" icon="Promotion">发送</el-button></template>
      </el-input>
    </div>
  </el-card>
</template>
<script setup>
import { ref, onMounted, nextTick } from '\''vue'\''
import { aiApi } from '\''../../api/index.js'\''
const messages = ref([{ role: '\''ai'\'', content: '\''您好！我是AI食材管理助手，可以帮您解答食材知识、库存管理、菜品搭配等问题。请问有什么可以帮助您的？'\'' }])
const query = ref('\'''\''); const loading = ref(false); const msgContainer = ref(null)
const scrollToBottom = () => { nextTick(() => { if (msgContainer.value) msgContainer.value.scrollTop = msgContainer.value.scrollHeight }) }
const sendMessage = async () => {
  if (!query.value.trim() || loading.value) return
  messages.value.push({ role: '\''user'\'', content: query.value.trim() })
  const q = query.value; query.value = '\'''\''; loading.value = true; scrollToBottom()
  try {
    const res = await aiApi.chat({ query: q, userId: '\'''\'', contentType: '\''CHAT'\'' })
    messages.value.push({ role: '\''ai'\'', content: res.data.response })
    scrollToBottom()
  } catch (e) { messages.value.push({ role: '\''ai'\'', content: '\''AI服务暂时不可用，请稍后重试。'\'' }) }
  finally { loading.value = false; scrollToBottom() }
}
onMounted(scrollToBottom)
</script>
<style scoped>
.chat-messages { flex:1; overflow-y:auto; padding:15px; background:#f5f7fa; border-radius:4px; margin-bottom:15px; }
.message { margin-bottom:15px; display:flex; }
.user-msg { justify-content:flex-end; }
.ai-msg { justify-content:flex-start; }
.msg-content { max-width:70%; padding:12px 16px; border-radius:8px; line-height:1.6; font-size:14px; }
.user-msg .msg-content { background:#409EFF; color:#fff; border-radius:8px 0 8px 8px; }
.ai-msg .msg-content { background:#fff; color:#303133; border:1px solid #e6e6e6; border-radius:0 8px 8px 8px; }
.chat-input { flex-shrink:0; }
</style>
