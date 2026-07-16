<template>
  <el-card>
    <template #header>{{ isEdit ? '\''编辑食材'\'' : '\''新增食材'\'' }}</template>
    <el-form :model="form" label-width="100px" style="max-width:600px">
      <el-form-item label="食材名称"><el-input v-model="form.name" /></el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.categoryId" placeholder="选择分类" style="width:100%">
          <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="条码"><el-input v-model="form.barcode" /></el-form-item>
      <el-form-item label="库存量"><el-input-number v-model="form.stockQuantity" :min="0" :step="1" /></el-form-item>
      <el-form-item label="最低库存"><el-input-number v-model="form.minStockQuantity" :min="0" :step="1" /></el-form-item>
      <el-form-item label="单位">
        <el-select v-model="form.unit" style="width:100%">
          <el-option label="kg" value="kg" /><el-option label="g" value="g" />
          <el-option label="L" value="L" /><el-option label="个" value="个" />
          <el-option label="箱" value="箱" /><el-option label="袋" value="袋" />
        </el-select>
      </el-form-item>
      <el-form-item label="单价(元)"><el-input-number v-model="form.unitPrice" :min="0" :precision="2" /></el-form-item>
      <el-form-item label="生产日期"><el-date-picker v-model="form.productionDate" type="date" style="width:100%" /></el-form-item>
      <el-form-item label="过期日期"><el-date-picker v-model="form.expiryDate" type="date" style="width:100%" /></el-form-item>
      <el-form-item label="储存方式">
        <el-select v-model="form.storageMethod" style="width:100%">
          <el-option label="冷藏" value="冷藏" /><el-option label="冷冻" value="冷冻" />
          <el-option label="常温" value="常温" />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商">
        <el-select v-model="form.supplierId" placeholder="选择供应商" style="width:100%" clearable>
          <el-option v-for="s in suppliers" :key="s.id" :label="s.name" :value="s.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="3" /></el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">保存</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script setup>
import { ref, reactive, onMounted, computed } from '\''vue'\''
import { useRoute, useRouter } from '\''vue-router'\''
import { ElMessage } from '\''element-plus'\''
import { ingredientApi, supplierApi } from '\''../../api/index.js'\''
const route = useRoute(); const router = useRouter()
const isEdit = computed(() => !!route.params.id)
const categories = ref([]); const suppliers = ref([]); const submitting = ref(false)
const form = reactive({ name:'\'', barcode:'\'', stockQuantity:0, minStockQuantity:0, unit:'\'kg\'', unitPrice:0, productionDate:null, expiryDate:null, storageMethod:'\'常温\'', remark:'\'', categoryId:null, supplierId:null })
const loadOptions = async () => {
  try { const [catRes, supRes] = await Promise.all([ingredientApi.categories.list(), supplierApi.list()]); categories.value = catRes.data; suppliers.value = supRes.data } catch(e){}
  if (isEdit.value) { try { const res = await ingredientApi.getById(route.params.id); const d = res.data; Object.assign(form, d); form.categoryId = d.category?.id; form.supplierId = d.supplier?.id } catch(e){} }
}
const handleSubmit = async () => {
  submitting.value = true
  try {
    const payload = { ...form }
    if (payload.categoryId) payload.category = { id: payload.categoryId }
    if (payload.supplierId) payload.supplier = { id: payload.supplierId }
    if (isEdit.value) { await ingredientApi.update(route.params.id, payload); ElMessage.success('\''更新成功'\'') }
    else { await ingredientApi.create(payload); ElMessage.success('\''创建成功'\'') }
    router.push('\''/ingredients'\'')
  } catch(e){} finally { submitting.value = false }
}
onMounted(loadOptions)
</script>
