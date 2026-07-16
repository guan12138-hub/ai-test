App({
  globalData: {
    baseUrl: '\''https://your-domain.com/api'\'',
    token: '\'''\'',
    userInfo: null
  },
  onLaunch() {
    const token = wx.getStorageSync('\''token'\'')
    const userInfo = wx.getStorageSync('\''userInfo'\'')
    if (token) { this.globalData.token = token; this.globalData.userInfo = userInfo }
  }
})
