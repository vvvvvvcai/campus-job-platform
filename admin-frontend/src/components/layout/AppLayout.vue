<script setup lang="ts">
import AppSidebar from './AppSidebar.vue'
import AppHeader from './AppHeader.vue'
</script>

<template>
  <div class="min-h-screen bg-background">
    <AppSidebar />
    <div class="pl-64">
      <AppHeader />
      <main class="relative pt-14 w-full px-gutter-lg min-h-screen bg-background">
        <div class="flex flex-col w-full pb-margin">
          <router-view />
        </div>
      </main>
    </div>
  </div>
</template>
