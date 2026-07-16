<template>
  <el-row :gutter="20">
    <el-col :span="10">
      <el-card>
        <template #header><span>AI绘图生成</span></template>
        <el-form label-width="80px">
          <el-form-item label="描述">
            <el-input v-model="prompt" placeholder="请输入图片描述，如：一盘精美的红烧肉" type="textarea" :rows="6" />
          </el-form-item>
          <el-form-item label="示例">
            <p style="font-size:12px;color:#909399">菜品效果图、食材分类海报、食堂菜单配图、食材储存示意图</p>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="generate" :loading="loading" style="width:100%">生成图片</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
    <el-col :span="14">
      <el-card>
        <template #header><span>生成结果</span></template>
        <div v-if="!imageUrl && !loading" style="text-align:center;color:#909399;padding:60px 0">输入描述并点击生成</div>
        <div v-if="loading" style="text-align:center;padding:60px 0"><el-icon class="is-loading" :size="32"><Loading /></el-icon><p>AI正在绘制中...</p></div>
        <div v-if="imageUrl && !loading" style="text-align:center">
          <el-image :src="imageUrl" fit="contain" style="max-width:100%;max-height:500px" />
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>
<script setup>
import { ref } from '\''vue'\''
import { ElMessage } from '\''element-plus'\''
import { aiApi } from '\''../../api/index.js'\''
const prompt = ref('\'''\''); const imageUrl = ref('\'''\''); const loading = ref(false)
const generate = async () => {
  if (!prompt.value.trim()) { ElMessage.warning('\''请输入描述'\''); return }
  loading.value = true; imageUrl.value = '\'''\''
  try { const res = await aiApi.generateImage({ prompt: prompt.value }); imageUrl.value = res.data.imageUrl; ElMessage.success('\''生成成功'\'') }
  catch(e){} finally { loading.value = false }
}
</script>
