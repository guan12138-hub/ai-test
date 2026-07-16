<template>
  <div>
    <el-card>
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <span>食材列表</span>
          <el-button type="primary" @click="$router.push('\''/ingredients/add'\'')">新增食材</el-button>
        </div>
      </template>
      <el-table :data="list" stripe border style="width:100%" v-loading="loading">
        <el-table-column prop="name" label="食材名称" min-width="120" />
        <el-table-column label="分类" min-width="100">
          <template #default="{row}">{{ row.category?.name || '\''-'\'' }}</template>
        </el-table-column>
        <el-table-column prop="stockQuantity" label="库存量" width="100" />
        <el-table-column prop="unit" label="单位" width="80" />
        <el-table-column prop="unitPrice" label="单价(元)" width="100">
          <template #default="{row}">{{ row.unitPrice?.toFixed(2) }}</template>
        </el-table-column>
        <el-table-column prop="expiryDate" label="过期日期" width="120" />
        <el-table-column label="状态" width="100">
          <template #default="{row}">
            <el-tag :type="row.status===1?'\''success'\'':row.status===2?'\''warning'\'':'\''danger'\''">
              {{ row.status===1?'\''正常'\'':row.status===2?'\''预警'\'':'\''过期'\'' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{row}">
            <el-button size="small" @click="$router.push(`/ingredients/edit/${row.id}`)">编辑</el-button>
            <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
              <template #reference><el-button size="small" type="danger">删除</el-button></template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from '\''vue'\''
import { ElMessage } from '\''element-plus'\''
import { ingredientApi } from '\''../../api/index.js'\''
const list = ref([]); const loading = ref(false)
const loadData = async () => { loading.value = true; try { const res = await ingredientApi.list(); list.value = res.data } catch(e){} finally { loading.value = false } }
const handleDelete = async (id) => { await ingredientApi.delete(id); ElMessage.success('\''删除成功'\''); loadData() }
onMounted(loadData)
</script>
