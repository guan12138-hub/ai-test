<template>
  <el-row :gutter="20">
    <el-col :span="10">
      <el-card>
        <template #header><span>AI文章生成</span></template>
        <el-form label-width="100px">
          <el-form-item label="生成类型">
            <el-select v-model="articleType" style="width:100%">
              <el-option label="菜谱文案" value="RECIPE" />
              <el-option label="采购计划表" value="PURCHASE_PLAN" />
              <el-option label="食堂菜单方案" value="MENU" />
              <el-option label="食材存储科普" value="STORAGE" />
              <el-option label="损耗分析报告" value="ANALYSIS" />
            </el-select>
          </el-form-item>
          <el-form-item label="主题/关键词">
            <el-input v-model="topic" placeholder="请输入文章主题" type="textarea" :rows="4" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="generate" :loading="loading" style="width:100%">生成文章</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
    <el-col :span="14">
      <el-card>
        <template #header><span>生成结果</span></template>
        <div v-if="!content && !loading" style="text-align:center;color:#909399;padding:60px 0">请设置主题并点击生成</div>
        <div v-if="loading" style="text-align:center;padding:60px 0"><el-icon class="is-loading" :size="32"><Loading /></el-icon><p style="margin-top:10px">AI正在生成中...</p></div>
        <div v-if="content && !loading" class="article-content">{{ content }}</div>
      </el-card>
    </el-col>
  </el-row>
</template>
<script setup>
import { ref } from '\''vue'\''
import { ElMessage } from '\''element-plus'\''
import { aiApi } from '\''../../api/index.js'\''
const topic = ref('\'''\''); const articleType = ref('\''RECIPE'\''); const content = ref('\'''\''); const loading = ref(false)
const generate = async () => {
  if (!topic.value.trim()) { ElMessage.warning('\''请输入主题'\''); return }
  loading.value = true; content.value = '\'''\''
  try { const res = await aiApi.generateArticle({ topic: topic.value, type: articleType.value }); content.value = res.data.content; ElMessage.success('\''生成成功'\'') }
  catch(e){} finally { loading.value = false }
}
</script>
<style scoped>
.article-content { white-space:pre-wrap; line-height:1.8; font-size:14px; padding:10px; }
</style>
