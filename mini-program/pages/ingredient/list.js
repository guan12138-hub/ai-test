const api = require('../../utils/api')
Page({
  data: { list: [], keyword: '' },
  onLoad() { this.loadData() },
  loadData() {
    api.getIngredients().then(res => {
      let list = res.data || []
      if (this.data.keyword) {
        list = list.filter(i => i.name.includes(this.data.keyword))
      }
      this.setData({ list })
    }).catch(() => {})
  },
  onSearch(e) { this.data.keyword = e.detail.value; this.loadData() },
  goAdd() { wx.navigateTo({ url: '/pages/ingredient/add' }) },
  goDetail(e) { wx.navigateTo({ url: '/pages/ingredient/detail?id=' + e.currentTarget.dataset.id }) }
})