const api = require('../../utils/api')
Page({
  data: {
    stats: [
      { label: '食材总数', value: 0 },
      { label: '即将过期', value: 0 },
      { label: '已过期', value: 0 },
      { label: '供应商', value: 0 }
    ]
  },
  onLoad() {
    api.getDashboard().then(res => {
      const d = res.data
      this.setData({
        stats: [
          { label: '食材总数', value: d.totalIngredients },
          { label: '即将过期', value: d.warningCount },
          { label: '已过期', value: d.expiredCount },
          { label: '供应商', value: d.totalSuppliers }
        ]
      })
    }).catch(() => {})
  }
})