const api = require('../../utils/api')
Page({
  data: {
    types: [
      { label: '菜谱文案', value: 'RECIPE' },
      { label: '采购计划表', value: 'PURCHASE_PLAN' },
      { label: '食堂菜单方案', value: 'MENU' },
      { label: '食材存储科普', value: 'STORAGE' },
      { label: '损耗分析报告', value: 'ANALYSIS' }
    ],
    selectedType: '菜谱文案', selectedTypeVal: 'RECIPE', topic: '', content: ''
  },
  onTypeChange(e) { const t = this.data.types[e.detail.value]; this.setData({ selectedType: t.label, selectedTypeVal: t.value }) },
  onTopicInput(e) { this.setData({ topic: e.detail.value }) },
  generate() {
    if (!this.data.topic) { wx.showToast({ title: '请输入主题', icon: 'none' }); return }
    wx.showLoading({ title: '生成中...' })
    api.generateArticle({ topic: this.data.topic, type: this.data.selectedTypeVal }).then(res => {
      wx.hideLoading(); this.setData({ content: res.data.content })
    }).catch(() => { wx.hideLoading() })
  }
})