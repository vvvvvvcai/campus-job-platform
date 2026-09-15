<script setup lang="ts">
import { useRoute } from 'vue-router'

const route = useRoute()
</script>

<template>
  <div class="py-space-lg">
    <h1 class="font-display-md text-display-md text-on-surface">{{ route.meta.title || '页面开发中' }}</h1>
    <p class="font-body-md text-body-md text-on-surface-variant mt-2">该页面将在后续阶段实现。</p>
  </div>
</template>
