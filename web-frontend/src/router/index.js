import { createRouter, createWebHistory } from '\''vue-router'\''

const routes = [
  { path: '\''/login'\'', name: '\''Login'\'', component: () => import('\''../views/auth/Login.vue'\'') },
  { path: '\''/'\'', redirect: '\''/dashboard'\'' },
  {
    path: '\''/'\'', component: () => import('\''../views/Layout.vue'\''), children: [
      { path: '\''dashboard'\'', name: '\''Dashboard'\'', component: () => import('\''../views/dashboard/Index.vue'\'') },
      { path: '\''ingredients'\'', name: '\''Ingredients'\'', component: () => import('\''../views/ingredient/List.vue'\'') },
      { path: '\''ingredients/add'\'', name: '\''AddIngredient'\'', component: () => import('\''../views/ingredient/Form.vue'\'') },
      { path: '\''ingredients/edit/:id'\'', name: '\''EditIngredient'\'', component: () => import('\''../views/ingredient/Form.vue'\'') },
      { path: '\''categories'\'', name: '\''Categories'\'', component: () => import('\''../views/ingredient/Category.vue'\'') },
      { path: '\''suppliers'\'', name: '\''Suppliers'\'', component: () => import('\''../views/supplier/List.vue'\'') },
      { path: '\''orders'\'', name: '\''Orders'\'', component: () => import('\''../views/purchase/List.vue'\'') },
      { path: '\''ai/chat'\'', name: '\''AiChat'\'', component: () => import('\''../views/ai/Chat.vue'\'') },
      { path: '\''ai/article'\'', name: '\''AiArticle'\'', component: () => import('\''../views/ai/Article.vue'\'') },
      { path: '\''ai/image'\'', name: '\''AiImage'\'', component: () => import('\''../views/ai/Image.vue'\'') },
      { path: '\''ai/analysis'\'', name: '\''AiAnalysis'\'', component: () => import('\''../views/ai/Analysis.vue'\'') },
      { path: '\''ai/history'\'', name: '\''AiHistory'\'', component: () => import('\''../views/ai/History.vue'\'') },
      { path: '\''reports'\'', name: '\''Reports'\'', component: () => import('\''../views/report/Index.vue'\'') },
      { path: '\''users'\'', name: '\''Users'\'', component: () => import('\''../views/system/UserManage.vue'\'') }
    ]
  }
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('\''token'\'')
  if (to.path !== '\''/login'\'' && !token) { next('\''/login'\'') }
  else { next() }
})

export default router
