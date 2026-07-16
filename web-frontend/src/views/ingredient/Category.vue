<template>
  <el-card>
    <template #header>
      <div style="display:flex;justify-content:space-between;align-items:center">
        <span>食材分类管理</span>
        <el-button type="primary" @click="dialogVisible=true">新增分类</el-button>
      </div>
    </template>
    <el-table :data="list" stripe border>
      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column prop="sortOrder" label="排序" width="80" />
      <el-table-column label="操作" width="150">
        <template #default="{row}">
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" title="新增分类" width="400">
      <el-form :model="form">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sortOrder" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>
<script setup>
import { ref, reactive, onMounted } from '\''vue'\''
import { ElMessage } from '\''element-plus'\''
import { ingredientApi } from '\''../../api/index.js'\''
const list = ref([]); const dialogVisible = ref(false)
const form = reactive({ name:'\'', description:'\'', sortOrder:0 })
const loadData = async () => { try { const res = await ingredientApi.categories.list(); list.value = res.data } catch(e){} }
const handleSubmit = async () => { await ingredientApi.categories.create(form); ElMessage.success('\''创建成功'\''); dialogVisible.value = false; loadData() }
const handleDelete = async (id) => { await ingredientApi.categories.delete(id); ElMessage.success('\''删除成功'\''); loadData() }
onMounted(loadData)
</script>
