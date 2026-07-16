const api = require('../../utils/api')
Page({
  data: {
    categories: [], units: ['kg', 'g', 'L', '个', '箱', '袋'],
    selectedCategory: '', selectedUnit: 'kg', expiryDate: '', categoryId: null
  },
  onLoad() {
    api.getCategories().then(res => { this.setData({ categories: res.data || [] }) }).catch(() => {})
  },
  onCategoryChange(e) {
    const cat = this.data.categories[e.detail.value]
    this.setData({ selectedCategory: cat.name, categoryId: cat.id })
  },
  onUnitChange(e) { this.setData({ selectedUnit: this.data.units[e.detail.value] }) },
  onDateChange(e) { this.setData({ expiryDate: e.detail.value }) },
  handleSubmit(e) {
    const { name, stockQuantity } = e.detail.value
    if (!name) { wx.showToast({ title: '请输入名称', icon: 'none' }); return }
    const data = {
      name, stockQuantity: parseFloat(stockQuantity) || 0,
      unit: this.data.selectedUnit, expiryDate: this.data.expiryDate || null,
      category: this.data.categoryId ? { id: this.data.categoryId } : null
    }
    api.createIngredient(data).then(() => {
      wx.showToast({ title: '保存成功' })
      setTimeout(() => wx.navigateBack(), 1500)
    }).catch(() => {})
  }
})