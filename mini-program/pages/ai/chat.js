const api = require('../../utils/api')
Page({
  data: {
    messages: [{ role: 'ai', content: '您好！我是AI食材管理助手，请问有什么可以帮助您的？' }],
    query: ''
  },
  onInput(e) { this.setData({ query: e.detail.value }) },
  sendMessage() {
    const q = this.data.query.trim()
    if (!q) return
    const msgs = [...this.data.messages, { role: 'user', content: q }]
    this.setData({ messages: msgs, query: '' })
    api.aiChat({ query: q }).then(res => {
      this.setData({ messages: [...this.data.messages, { role: 'ai', content: res.data.response }] })
    }).catch(() => {
      this.setData({ messages: [...this.data.messages, { role: 'ai', content: 'AI服务暂时不可用' }] })
    })
  }
})