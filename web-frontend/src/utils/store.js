import { defineStore } from '\''pinia'\''
import { ref } from '\''vue'\''

export const useUserStore = defineStore('\''user'\'', () => {
  const token = ref(localStorage.getItem('\''token'\'') || '\'''\'')
  const username = ref(localStorage.getItem('\''username'\'') || '\'''\'')
  const displayName = ref(localStorage.getItem('\''displayName'\'') || '\'''\'')
  const role = ref(localStorage.getItem('\''role'\'') || '\'''\'')

  function setUser(data) {
    token.value = data.token; username.value = data.username
    displayName.value = data.displayName; role.value = data.role
    localStorage.setItem('\''token'\'', data.token)
    localStorage.setItem('\''username'\'', data.username)
    localStorage.setItem('\''displayName'\'', data.displayName)
    localStorage.setItem('\''role'\'', data.role)
  }

  function logout() {
    token.value = '\'''\''; username.value = '\'''\''; displayName.value = '\'''\''; role.value = '\'''\''
    localStorage.clear()
  }

  return { token, username, displayName, role, setUser, logout }
})
