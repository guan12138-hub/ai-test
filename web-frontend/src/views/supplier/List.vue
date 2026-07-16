<template>
  <el-card>
    <template #header>
      <div style="display:flex;justify-content:space-between;align-items:center">
        <span>供应商管理</span>
        <el-button type="primary" @click="dialogVisible=true">新增供应商</el-button>
      </div>
    </template>
    <el-table :data="list" stripe border>
      <el-table-column prop="name" label="供应商名称" min-width="150" />
      <el-table-column prop="contactPerson" label="联系人" width="120" />
      <el-table-column prop="phone" label="电话" width="130" />
      <el-table-column prop="address" label="地址" min-width="200" />
      <el-table-column prop="remark" label="备注" min-width="150" />
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{row}">
          <el-button size="small" @click="editRow(row)">编辑</el-button>
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="editingId?'编辑供应商':'新增供应商'" width="500">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="联系人"><el-input v-model="form.contactPerson" /></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" type="textarea" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" /></el-form-item>
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
import { supplierApi } from '\''../../api/index.js'\''
const list = ref([]); const dialogVisible = ref(false); const editingId = ref(null)
const form = reactive({ name:'\'', contactPerson:'\'', phone:'\'', address:'\'', remark:'\'' })
const loadData = async () => { try { const res = await supplierApi.list(); list.value = res.data } catch(e){} }
const editRow = (row) => { Object.assign(form, row); editingId.value = row.id; dialogVisible.value = true }
const handleSubmit = async () => {
  if (editingId.value) { await supplierApi.update(editingId.value, form); ElMessage.success('\''更新成功'\'') }
  else { await supplierApi.create(form); ElMessage.success('\''创建成功'\'') }
  dialogVisible.value = false; editingId.value = null; Object.assign(form, { name:'\'', contactPerson:'\'', phone:'\'', address:'\'', remark:'\'' }); loadData()
}
const handleDelete = async (id) => { await supplierApi.delete(id); ElMessage.success('\''删除成功'\''); loadData() }
onMounted(loadData)
</script>
