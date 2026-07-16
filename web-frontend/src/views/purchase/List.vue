<template>
  <el-card>
    <template #header>
      <div style="display:flex;justify-content:space-between;align-items:center">
        <span>采购订单</span>
        <el-button type="primary" @click="dialogVisible=true">新增订单</el-button>
      </div>
    </template>
    <el-table :data="list" stripe border>
      <el-table-column prop="orderNo" label="订单号" width="150" />
      <el-table-column label="供应商" width="120"><template #default="{row}">{{ row.supplier?.name||'\''-'\'' }}</template></el-table-column>
      <el-table-column label="食材" width="120"><template #default="{row}">{{ row.ingredient?.name||'\''-'\'' }}</template></el-table-column>
      <el-table-column prop="quantity" label="数量" width="100" />
      <el-table-column prop="totalPrice" label="总价(元)" width="100">
        <template #default="{row}">{{ row.totalPrice?.toFixed(2) }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{row}">
          <el-tag :type="row.status==='\''COMPLETED'\''?'\''success'\'':row.status==='\''CANCELLED'\''?'\''danger'\'':'\''warning'\''">
            {{ row.status==='\''COMPLETED'\''?'\''已完成'\'':row.status==='\''CANCELLED'\''?'\''已取消'\'':'\''待处理'\'' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="orderDate" label="下单日期" width="120" />
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{row}">
          <el-button size="small" @click="editRow(row)">编辑</el-button>
          <el-popconfirm title="确定删除?" @confirm="handleDelete(row.id)">
            <template #reference><el-button size="small" type="danger">删除</el-button></template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="editingId?'编辑订单':'新增订单'" width="500">
      <el-form :model="form" label-width="80px">
        <el-form-item label="供应商">
          <el-select v-model="form.supplierId" style="width:100%">
            <el-option v-for="s in suppliers" :key="s.id" :label="s.name" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="食材">
          <el-select v-model="form.ingredientId" style="width:100%">
            <el-option v-for="i in ingredients" :key="i.id" :label="i.name" :value="i.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="数量"><el-input-number v-model="form.quantity" :min="0" :step="1" style="width:100%" /></el-form-item>
        <el-form-item label="总价"><el-input-number v-model="form.totalPrice" :min="0" :precision="2" style="width:100%" /></el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width:100%">
            <el-option label="待处理" value="PENDING" />
            <el-option label="已完成" value="COMPLETED" />
            <el-option label="已取消" value="CANCELLED" />
          </el-select>
        </el-form-item>
        <el-form-item label="日期"><el-date-picker v-model="form.orderDate" type="date" style="width:100%" /></el-form-item>
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
import { orderApi, supplierApi, ingredientApi } from '\''../../api/index.js'\''
const list = ref([]); const suppliers = ref([]); const ingredients = ref([])
const dialogVisible = ref(false); const editingId = ref(null)
const form = reactive({ supplierId:null, ingredientId:null, quantity:0, totalPrice:0, status:'\'PENDING\'', orderDate:null, remark:'\'' })
const loadData = async () => { try { const [res, supRes, ingRes] = await Promise.all([orderApi.list(), supplierApi.list(), ingredientApi.list()]); list.value = res.data; suppliers.value = supRes.data; ingredients.value = ingRes.data } catch(e){} }
const editRow = (row) => { Object.assign(form, row); form.supplierId = row.supplier?.id; form.ingredientId = row.ingredient?.id; editingId.value = row.id; dialogVisible.value = true }
const handleSubmit = async () => {
  const payload = { ...form }
  if (payload.supplierId) payload.supplier = { id: payload.supplierId }
  if (payload.ingredientId) payload.ingredient = { id: payload.ingredientId }
  if (editingId.value) { await orderApi.update(editingId.value, payload); ElMessage.success('\''更新成功'\'') }
  else { await orderApi.create(payload); ElMessage.success('\''创建成功'\'') }
  dialogVisible.value = false; editingId.value = null; loadData()
}
const handleDelete = async (id) => { await orderApi.delete(id); ElMessage.success('\''删除成功'\''); loadData() }
onMounted(loadData)
</script>
