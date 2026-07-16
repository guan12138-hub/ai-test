<template>
  <el-card>
    <template #header><span>用户管理</span></template>
    <el-table :data="list" stripe border>
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="displayName" label="显示名称" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column label="角色" width="100">
        <template #default="{row}"><el-tag :type="row.role==='\''ADMIN'\''?'\''danger'\'':'\'''\''">{{ row.role }}</el-tag></template>
      </el-table-column>
      <el-table-column label="状态" width="80">
        <template #default="{row}"><el-tag :type="row.enabled?'\''success'\'':'\''danger'\''">{{ row.enabled?'\''启用'\'':'\''禁用'\'' }}</el-tag></template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{row}">
          <el-button size="small" @click="editRow(row)">编辑</el-button>
          <el-popconfirm :title="row.enabled?'\''确定禁用该用户?'\'':'\''确定启用该用户?'\''" @confirm="toggleEnabled(row)">
            <template #reference><el-button size="small" :type="row.enabled?'\''warning'\'':'\''success'\''">{{ row.enabled?'\''禁用'\'':'\''启用'\'' }}</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>
<script setup>
import { ref, onMounted } from '\''vue'\''
import { ElMessage } from '\''element-plus'\''
import { userApi } from '\''../../api/index.js'\''
const list = ref([])
const loadData = async () => { try { const res = await userApi.list(); list.value = res.data } catch(e){} }
const editRow = (row) => { /* inline editing could be added */ }
const toggleEnabled = async (row) => { await userApi.update(row.id, { ...row, enabled: !row.enabled }); ElMessage.success('\''操作成功'\''); loadData() }
onMounted(loadData)
</script>
