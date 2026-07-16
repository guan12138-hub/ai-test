import request from '\''./request.js'\''

export const authApi = {
  login: data => request.post('\''/auth/login'\'', data),
  register: data => request.post('\''/auth/register'\'', data)
}

export const ingredientApi = {
  list: () => request.get('\''/ingredients'\''),
  page: (params) => request.get('\''/ingredients/page'\'', { params }),
  getById: id => request.get(`/ingredients/${id}`),
  create: data => request.post('\''/ingredients'\'', data),
  update: (id, data) => request.put(`/ingredients/${id}`, data),
  delete: id => request.delete(`/ingredients/${id}`),
  consume: (id, params) => request.post(`/ingredients/${id}/consume`, null, { params }),
  getConsumption: id => request.get(`/ingredients/${id}/consumption`),
  categories: { list: () => request.get('\''/ingredients/categories'\''), create: data => request.post('\''/ingredients/categories'\'', data), delete: id => request.delete(`/ingredients/categories/${id}`) }
}

export const supplierApi = {
  list: () => request.get('\''/suppliers'\''),
  create: data => request.post('\''/suppliers'\'', data),
  update: (id, data) => request.put(`/suppliers/${id}`, data),
  delete: id => request.delete(`/suppliers/${id}`)
}

export const orderApi = {
  list: () => request.get('\''/orders'\''),
  create: data => request.post('\''/orders'\'', data),
  update: (id, data) => request.put(`/orders/${id}`, data),
  delete: id => request.delete(`/orders/${id}`)
}

export const aiApi = {
  chat: data => request.post('\''/ai/chat'\'', data),
  generateArticle: params => request.post('\''/ai/article'\'', null, { params }),
  generateImage: params => request.post('\''/ai/image'\'', null, { params }),
  analyze: params => request.post('\''/ai/analyze'\'', null, { params }),
  conversations: () => request.get('\''/ai/conversations'\''),
  contents: params => request.get('\''/ai/contents'\'', { params }),
  toggleFavorite: id => request.post(`/ai/contents/${id}/favorite`),
  deleteContent: id => request.delete(`/ai/contents/${id}`)
}

export const dashboardApi = {
  stats: () => request.get('\''/dashboard/stats'\'')
}

export const userApi = {
  list: () => request.get('\''/users'\''),
  update: (id, data) => request.put(`/users/${id}`, data)
}
