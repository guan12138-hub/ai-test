const app = getApp()
Page({
  data: { username: '', role: '', initial: '?' },
  onShow() {
    const user = app.globalData.userInfo || wx.getStorageSync('userInfo') || {}
    this.setData({
      username: user.displayName || user.username || '',
      role: user.role || '',
      initial: (user.displayName || user.username || '?')[0]
    })
  },
  goTo(e) { wx.navigateTo({ url: e.currentTarget.dataset.url }) },
  logout() {
    wx.showModal({
      title: '确认退出', content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          app.globalData.token = ''; app.globalData.userInfo = null
          wx.removeStorageSync('token'); wx.removeStorageSync('userInfo')
          wx.reLaunch({ url: '/pages/index/index' })
        }
      }
    })
  }
})