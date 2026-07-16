<template>
  <div class="dashboard">
    <el-row :gutter="20" style="margin-bottom:20px">
      <el-col :span="6" v-for="item in statsCards" :key="item.label">
        <el-card shadow="hover">
          <div class="stat-item">
            <div class="stat-value">{{ item.value }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card><template #header>食材分类分布</template>
          <div id="categoryChart" style="height:350px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card><template #header>库存状态</template>
          <div id="statusChart" style="height:350px"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top:20px">
      <el-col :span="24">
        <el-card><template #header>月度消耗趋势</template>
          <div id="trendChart" style="height:350px"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script setup>
import { ref, onMounted, nextTick } from '\''vue'\''
import * as echarts from '\''echarts'\''
import { dashboardApi } from '\''../../api/index.js'\''
const statsCards = ref([
  { label: '\''食材总数'\'', value: 0 }, { label: '\''即将过期'\'', value: 0 },
  { label: '\''已过期'\'', value: 0 }, { label: '\''库存价值(元)'\'', value: 0 }
])
const loadStats = async () => {
  try {
    const res = await dashboardApi.stats()
    const d = res.data
    statsCards.value = [
      { label: '\''食材总数'\'', value: d.totalIngredients },
      { label: '\''即将过期'\'', value: d.warningCount },
      { label: '\''已过期'\'', value: d.expiredCount },
      { label: '\''库存价值(元)'\'', value: '\''¥'\'' + d.totalStockValue.toFixed(2) }
    ]
    nextTick(() => {
      // Category chart
      const catChart = echarts.init(document.getElementById('\''categoryChart'\''))
      catChart.setOption({
        tooltip: { trigger: '\''item'\'' },
        series: [{
          type: '\''pie'\'', radius: ['\''40%'\'', '\''70%'\''],
          data: Object.entries(d.categoryDistribution || {}).map(([k, v]) => ({ name: k, value: v }))
        }]
      })
      // Status chart
      const statusChart = echarts.init(document.getElementById('\''statusChart'\''))
      statusChart.setOption({
        tooltip: { trigger: '\''item'\'' },
        series: [{
          type: '\''pie'\'', radius: '\''60%'\'',
          data: [
            { name: '\''正常'\'', value: d.normalCount, itemStyle: { color: '\''#67C23A'\'' } },
            { name: '\''预警'\'', value: d.warningCount, itemStyle: { color: '\''#E6A23C'\'' } },
            { name: '\''过期'\'', value: d.expiredCount, itemStyle: { color: '\''#F56C6C'\'' } }
          ]
        }]
      })
      // Trend chart
      const trendChart = echarts.init(document.getElementById('\''trendChart'\''))
      const months = Object.keys(d.monthlyConsumption || {})
      const values = Object.values(d.monthlyConsumption || {})
      trendChart.setOption({
        tooltip: { trigger: '\''axis'\'' },
        xAxis: { type: '\''category'\'', data: months },
        yAxis: { type: '\''value'\'', name: '\''消耗量'\'' },
        series: [{ type: '\''line'\'', data: values, smooth: true, areaStyle: { opacity: 0.3 } }]
      })
    })
  } catch (e) { /* handled */ }
}
onMounted(loadStats)
</script>
<style scoped>
.stat-item { text-align: center; }
.stat-value { font-size: 28px; font-weight: bold; color: #409EFF; }
.stat-label { font-size: 14px; color: #909399; margin-top: 5px; }
</style>
