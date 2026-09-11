<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup>
import { useUserStore } from './stores/user'

const userStore = useUserStore()

// Check if user is logged in on app mount
const token = localStorage.getItem('token')
if (token) {
  userStore.setToken(token)
}
</script>

<style>
#app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
