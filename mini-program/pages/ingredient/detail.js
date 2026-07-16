const api = require('../../utils/api')
Page({
  data: { item: {}, id: null },
  onLoad(options) { this.data.id = options.id; this.loadData() },
  loadData() {
    api.getIngredient(this.data.id).then(res => { this.setData({ item: res.data }) }).catch(() => {})
  },
  recordConsume() {
    wx.showModal({ title: '消耗记录', content: '输入消耗数量', editable: true, success: (res) => {
      if (res.confirm) {
        const qty = parseFloat(res.content)
        if (isNaN(qty) || qty <= 0) { wx.showToast({ title: '请输入有效数量', icon: 'none' }); return }
        api.consumeIngredient(this.data.id, { quantity: qty, type: 'CONSUME', remark: '' }).then(() => {
          wx.showToast({ title: '记录成功' }); this.loadData()
        }).catch(() => {})
      }
    }})
  },
  deleteItem() {
    wx.showModal({ title: '确认删除', content: '确定删除这个食材吗？', success: (res) => {
      if (res.confirm) { api.deleteIngredient(this.data.id).then(() => { wx.showToast({ title: '删除成功' }); setTimeout(() => wx.navigateBack(), 1500) }).catch(() => {}) }
    }})
  }
})