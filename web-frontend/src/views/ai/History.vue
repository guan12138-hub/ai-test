<template>
  <el-card>
    <template #header>
      <div style="display:flex;justify-content:space-between;align-items:center">
        <span>AI内容历史记录</span>
        <div>
          <el-select v-model="filterType" style="width:150px;margin-right:10px" @change="loadData">
            <el-option label="全部" value="" />
            <el-option label="文章" value="ARTICLE" />
            <el-option label="图片" value="IMAGE" />
            <el-option label="分析报告" value="ANALYSIS" />
          </el-select>
        </div>
      </div>
    </template>
    <el-table :data="list" stripe border v-loading="loading">
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column label="类型" width="100">
        <template #default="{row}">
          <el-tag :type="row.contentType==='\''ARTICLE'\''?'\''success'\'':row.contentType==='\''IMAGE'\''?'\''primary'\'':'\''warning'\''">
            {{ row.contentType==='\''ARTICLE'\''?'\''文章'\'':row.contentType==='\''IMAGE'\''?'\''图片'\'':'\''分析'\'' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="收藏" width="80">
        <template #default="{row}">
          <el-icon :color="row.favorite?'\''#E6A23C'\'':'\''#ccc'\''" style="cursor:pointer" @click="toggleFavorite(row.id)">
            <StarFilled />
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" width="180" />
      <el-table-column label="操作" width="120">
        <template #default="{row}">
          <el-button size="small" @click="viewDetail(row)">查看</el-button>
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="detailVisible" :title="detailItem?.title" width="700">
      <div v-if="detailItem?.contentType==='\''IMAGE'\''" style="text-align:center">
        <el-image :src="detailItem.imageUrl" fit="contain" style="max-width:100%;max-height:500px" />
      </div>
      <div v-else style="white-space:pre-wrap;line-height:1.8">{{ detailItem?.content }}</div>
    </el-dialog>
  </el-card>
</template>
<script setup>
import { ref, onMounted } from '\''vue'\''
import { ElMessage } from '\''element-plus'\''
import { aiApi } from '\''../../api/index.js'\''
const list = ref([]); const loading = ref(false); const filterType = ref('\'''\''); const detailVisible = ref(false); const detailItem = ref({})
const loadData = async () => { loading.value = true; try { const res = await aiApi.contents({ contentType: filterType.value || undefined }); list.value = res.data } catch(e){} finally { loading.value = false } }
const toggleFavorite = async (id) => { await aiApi.toggleFavorite(id); loadData() }
const handleDelete = async (id) => { await aiApi.deleteContent(id); ElMessage.success('\''已删除'\''); loadData() }
const viewDetail = (row) => { detailItem.value = row; detailVisible.value = true }
onMounted(loadData)
</script>
