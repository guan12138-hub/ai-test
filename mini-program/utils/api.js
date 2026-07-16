const app = getApp()

const request = (url, method = '\''GET'\'', data = {}) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url: app.globalData.baseUrl + url,
      method,
      data,
      header: {
        '\''Authorization'\'': '\''Bearer '\'' + app.globalData.token,
        '\''Content-Type'\'': '\''application/json'\''
      },
      success: (res) => {
        if (res.statusCode === 200) { resolve(res.data) }
        else if (res.statusCode === 401) {
          wx.removeStorageSync('\''token'\'')
          wx.reLaunch({ url: '\''/pages/index/index'\'' })
          wx.showToast({ title: '\''登录过期'\'', icon: '\''none'\'' })
          reject(res)
        } else { wx.showToast({ title: res.data?.message || '\''请求失败'\'', icon: '\''none'\'' }); reject(res) }
      },
      fail: (err) => { wx.showToast({ title: '\''网络错误'\'', icon: '\''none'\'' }); reject(err) }
    })
  })
}

module.exports = {
  // Auth
  login: (data) => request('\''/auth/login'\'', '\''POST'\'', data),
  register: (data) => request('\''/auth/register'\'', '\''POST'\'', data),
  // Dashboard
  getDashboard: () => request('\''/dashboard/stats'\''),
  // Ingredients
  getIngredients: () => request('\''/ingredients'\''),
  getIngredient: (id) => request(`/ingredients/${id}`),
  createIngredient: (data) => request('\''/ingredients'\'', '\''POST'\'', data),
  updateIngredient: (id, data) => request(`/ingredients/${id}`, '\''PUT'\'', data),
  deleteIngredient: (id) => request(`/ingredients/${id}`, '\''DELETE'\''),
  consumeIngredient: (id, params) => request(`/ingredients/${id}/consume`, '\''POST'\'', null, { params }),
  // AI
  aiChat: (data) => request('\''/ai/chat'\'', '\''POST'\'', data),
  generateArticle: (params) => request('\''/ai/article'\'', '\''POST'\'', null, { params }),
  generateImage: (params) => request('\''/ai/image'\'', '\''POST'\'', null, { params }),
  analyze: (params) => request('\''/ai/analyze'\'', '\''POST'\'', null, { params }),
  // Categories
  getCategories: () => request('\''/ingredients/categories'\''),
  // Suppliers
  getSuppliers: () => request('\''/suppliers'\'')
}
