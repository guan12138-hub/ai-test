<template>
  <el-card>
    <template #header><span>数据报表</span></template>
    <el-row :gutter="20">
      <el-col :span="12"><div id="catReportChart" style="height:400px"></div></el-col>
      <el-col :span="12"><div id="trendReportChart" style="height:400px"></div></el-col>
    </el-row>
  </el-card>
</template>
<script setup>
import { onMounted, nextTick } from '\''vue'\''
import * as echarts from '\''echarts'\''
import { dashboardApi } from '\''../../api/index.js'\''
const loadData = async () => {
  try { const res = await dashboardApi.stats(); const d = res.data
    nextTick(() => {
      const catChart = echarts.init(document.getElementById('\''catReportChart'\''))
      catChart.setOption({ title: { text: '\''食材分类分布'\'', left: '\''center'\'' }, tooltip: { trigger: '\''item'\'' }, series: [{ type: '\''pie'\'', radius: '\''50%'\'', data: Object.entries(d.categoryDistribution||{}).map(([k,v])=>({name:k,value:v})) }] })
      const trendChart = echarts.init(document.getElementById('\''trendReportChart'\''))
      trendChart.setOption({ title: { text: '\''月度消耗趋势'\'', left: '\''center'\'' }, tooltip: { trigger: '\''axis'\'' }, xAxis: { type: '\''category'\'', data: Object.keys(d.monthlyConsumption||{}) }, yAxis: { type: '\''value'\'' }, series: [{ type: '\''bar'\'', data: Object.values(d.monthlyConsumption||{}) }] })
    })
  } catch(e){}
}
onMounted(loadData)
</script>
