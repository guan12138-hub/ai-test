<template>
  <el-row :gutter="20">
    <el-col :span="10">
      <el-card>
        <template #header><span>AI智能分析</span></template>
        <el-form label-width="100px">
          <el-form-item label="分析类型">
            <el-select v-model="analysisType" style="width:100%">
              <el-option label="库存损耗分析" value="损耗分析" />
              <el-option label="采购成本分析" value="成本分析" />
              <el-option label="保质期预警分析" value="预警分析" />
              <el-option label="消耗习惯分析" value="习惯分析" />
            </el-select>
          </el-form-item>
          <el-form-item label="数据描述">
            <el-input v-model="dataContext" placeholder="请输入相关数据或描述场景" type="textarea" :rows="8" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="analyze" :loading="loading" style="width:100%">开始分析</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
    <el-col :span="14">
      <el-card>
        <template #header><span>分析报告</span></template>
        <div v-if="!analysis && !loading" style="text-align:center;color:#909399;padding:60px 0">请选择分析类型并输入数据</div>
        <div v-if="loading" style="text-align:center;padding:60px 0"><el-icon class="is-loading" :size="32"><Loading /></el-icon><p>AI正在分析中...</p></div>
        <div v-if="analysis && !loading" class="analysis-content">{{ analysis }}</div>
      </el-card>
    </el-col>
  </el-row>
</template>
<script setup>
import { ref } from '\''vue'\''
import { ElMessage } from '\''element-plus'\''
import { aiApi } from '\''../../api/index.js'\''
const analysisType = ref('\''损耗分析'\''); const dataContext = ref('\'''\''); const analysis = ref('\'''\''); const loading = ref(false)
const analyze = async () => {
  if (!dataContext.value.trim()) { ElMessage.warning('\''请输入数据'\''); return }
  loading.value = true; analysis.value = '\'''\''
  try { const res = await aiApi.analyze({ dataType: analysisType.value, dataContext: dataContext.value }); analysis.value = res.data.analysis; ElMessage.success('\''分析完成'\'') }
  catch(e){} finally { loading.value = false }
}
</script>
<style scoped>
.analysis-content { white-space:pre-wrap; line-height:1.8; font-size:14px; padding:10px; }
</style>
