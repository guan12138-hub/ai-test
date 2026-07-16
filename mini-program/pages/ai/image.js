const api = require('../../utils/api')
Page({
  data: { prompt: '', imageUrl: '' },
  onPromptInput(e) { this.setData({ prompt: e.detail.value }) },
  generate() {
    if (!this.data.prompt) { wx.showToast({ title: '请输入描述', icon: 'none' }); return }
    wx.showLoading({ title: '绘图...' })
    api.generateImage({ prompt: this.data.prompt }).then(res => {
      wx.hideLoading(); this.setData({ imageUrl: res.data.imageUrl })
    }).catch(() => { wx.hideLoading() })
  }
})