const api = require('../../utils/api')
Page({
  data: {
    statsCards: [
      { label: '食材总数', value: 0 },
      { label: '即将过期', value: 0 },
      { label: '已过期', value: 0 },
      { label: '库存价值', value: '¥0' }
    ],
    menuList: [
      { label: '食材录入', url: '/pages/ingredient/add', icon: '/images/add.png' },
      { label: '食材列表', url: '/pages/ingredient/list', icon: '/images/list.png' },
      { label: 'AI助手', url: '/pages/ai/chat', icon: '/images/ai.png' },
      { label: '数据报告', url: '/pages/report/index', icon: '/images/report.png' }
    ]
  },
  onLoad() { this.loadDashboard() },
  loadDashboard() {
    api.getDashboard().then(res => {
      const d = res.data
      this.setData({
        statsCards: [
          { label: '食材总数', value: d.totalIngredients },
          { label: '即将过期', value: d.warningCount },
          { label: '已过期', value: d.expiredCount },
          { label: '库存价值', value: '¥' + (d.totalStockValue || 0).toFixed(2) }
        ]
      })
    }).catch(() => {})
  },
  goToPage(e) { wx.navigateTo({ url: e.currentTarget.dataset.url }) }
})