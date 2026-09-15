<script setup lang="ts">
import { ref } from 'vue'
import { dashboardKpiData } from '@/mock/dashboard'

const activeTimeRange = ref<'7d' | '30d'>('7d')

function switchTrend(type: '7d' | '30d') {
  activeTimeRange.value = type
}
</script>

<template>
  <div>
    <!-- Page header -->
    <div class="flex flex-col md:flex-row md:items-center justify-between gap-space-md py-space-lg">
      <div>
        <div class="flex items-center gap-space-xs mb-1">
          <span class="w-1.5 h-1.5 rounded-full bg-tertiary-fixed"></span>
          <span class="font-label-sm text-label-sm text-on-surface-variant">数据中心准实时同步中</span>
        </div>
        <div class="flex items-baseline gap-space-md">
          <h1 class="font-display-lg text-display-lg text-on-surface tracking-tight">数据统计与成功指标总览</h1>
          <span class="font-body-sm text-body-sm text-outline hidden sm:inline-block">GB/T 综合指标监管看板 · 达成度自动核算</span>
        </div>
      </div>
      <div class="flex items-center gap-space-sm self-start md:self-auto">
        <div class="flex items-center bg-surface-container-low p-1 rounded-xl shadow-sm">
          <button
            class="px-space-md py-1 rounded-lg font-label-md text-label-md transition-all"
            :class="
              activeTimeRange === '7d'
                ? 'bg-surface-container-lowest text-primary shadow-sm'
                : 'text-on-surface-variant hover:text-on-surface'
            "
            @click="switchTrend('7d')"
          >
            近 7 天
          </button>
          <button
            class="px-space-md py-1 rounded-lg font-label-md text-label-md transition-all"
            :class="
              activeTimeRange === '30d'
                ? 'bg-surface-container-lowest text-primary shadow-sm'
                : 'text-on-surface-variant hover:text-on-surface'
            "
            @click="switchTrend('30d')"
          >
            近 30 天
          </button>
        </div>
        <button class="flex items-center gap-1.5 px-space-md py-2 rounded-xl bg-primary text-on-primary font-label-md text-label-md hover:bg-primary/90 transition-all shadow-sm">
          <span class="material-symbols-outlined text-[18px]">download</span>
          <span>导出分析报表</span>
        </button>
      </div>
    </div>

    <!-- KPI Cards -->
    <div class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-5 gap-gutter mb-gutter-lg">
      <div
        v-for="(kpi, index) in dashboardKpiData"
        :key="index"
        class="relative overflow-hidden rounded-xl bg-surface-container-lowest p-space-lg shadow-sm hover:shadow-md transition-all group"
      >
        <!-- Background glow -->
        <div class="absolute -top-10 -right-10 w-28 h-28 rounded-full blur-2xl transition-all"
          :class="[
            index === 0 ? 'bg-primary/5 group-hover:bg-primary/10' :
            index === 1 ? 'bg-secondary/5 group-hover:bg-secondary/10' :
            index === 2 ? 'bg-tertiary-fixed/30 group-hover:bg-tertiary-fixed/50' :
            index === 3 ? 'bg-tertiary/10 group-hover:bg-tertiary/20' :
            'bg-primary-container/10 group-hover:bg-primary-container/20'
          ]"
        ></div>

        <!-- Header row -->
        <div class="flex items-center justify-between mb-space-sm">
          <span class="font-label-sm text-label-sm text-on-surface-variant">{{ kpi.title }}</span>
          <span class="w-8 h-8 rounded-lg flex items-center justify-center" :class="[kpi.iconBg, kpi.iconColor]">
            <span class="material-symbols-outlined text-[18px]">{{ kpi.icon }}</span>
          </span>
        </div>

        <!-- Single value KPI cards -->
        <template v-if="kpi.value">
          <div class="flex items-baseline gap-2 mb-1">
            <span class="font-metric-xl text-metric-xl text-on-surface tracking-tight">{{ kpi.value }}</span>
            <span class="font-label-sm text-label-sm font-semibold flex items-center" :class="kpi.trendUp ? 'text-tertiary' : 'text-primary'">
              <span v-if="kpi.trendUp" class="material-symbols-outlined text-[14px]">trending_up</span>
              {{ kpi.trend }}
            </span>
          </div>

          <!-- Details row -->
          <div v-if="kpi.details && kpi.details.length > 0" class="flex items-center gap-space-xs font-body-sm text-body-sm text-on-surface-variant pt-space-xs mt-space-xs">
            <template v-for="(detail, i) in kpi.details" :key="i">
              <span v-if="i > 0" class="text-outline-variant">/</span>
              <span class="w-2 h-2 rounded-full" :class="'bg-' + detail.value" v-if="detail.value"></span>
              <span>{{ detail.label }}</span>
            </template>
          </div>

          <!-- Badge in details -->
          <div v-if="kpi.details && kpi.details.length === 1 && kpi.details[0].value === ''" class="flex items-center justify-between font-body-sm text-body-sm text-on-surface-variant pt-space-xs mt-space-xs">
            <span>{{ kpi.details[0].label }}</span>
            <span v-if="kpi.title === '在线有效岗位数'" class="px-2 py-0.5 rounded-full bg-error-container text-on-error-container font-label-sm text-label-sm">待审核 8</span>
            <span v-if="kpi.title === '累计投递总量'" class="text-tertiary font-label-sm text-label-sm font-medium">峰值活跃中</span>
          </div>

          <!-- Footer -->
          <div v-if="kpi.footer" class="mt-space-sm pt-space-xs bg-surface-container-low px-2 py-1 rounded font-code-xs text-code-xs flex items-center justify-between"
            :class="kpi.footer.value.includes('达成') || kpi.footer.value.includes('已达标') ? 'text-primary' : 'text-on-surface-variant'"
          >
            <span>{{ kpi.footer.label }}</span>
            <span class="font-bold" :class="kpi.footer.value.includes('达成') || kpi.footer.value.includes('已达标') ? 'text-primary' : 'text-on-surface'">{{ kpi.footer.value }}</span>
          </div>

          <!-- Progress bar -->
          <div v-if="kpi.progress" class="w-full bg-surface-container-high h-1.5 rounded-full overflow-hidden mt-space-sm">
            <div class="bg-tertiary-container h-full rounded-full" :style="{ width: kpi.progress + '%' }"></div>
          </div>
        </template>

        <!-- Multi-metric KPI card (智能推荐) -->
        <template v-else-if="kpi.subMetrics">
          <div class="space-y-2">
            <div v-for="(metric, i) in kpi.subMetrics" :key="i" :class="i > 0 ? 'pt-1' : ''">
              <div class="flex justify-between items-baseline mb-0.5">
                <span class="font-body-sm text-body-sm text-on-surface-variant">{{ metric.label }}</span>
                <span class="font-title-md text-title-md font-bold" :class="metric.color">{{ metric.value }}</span>
              </div>
              <div class="flex items-center justify-between font-code-xs text-code-xs text-outline">
                <span>目标 ≥ {{ i === 0 ? '15%' : '5%' }}</span>
                <span class="text-tertiary font-medium">超额 {{ i === 0 ? '21.3%' : '28.0%' }}</span>
              </div>
            </div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>
