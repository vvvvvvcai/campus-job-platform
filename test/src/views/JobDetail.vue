<template>
  <div class="min-h-screen bg-[var(--background)]">
    <!-- Hero Card -->
    <div class="bg-gradient-to-r from-[var(--primary)] to-[var(--primary-container)] py-8">
      <div class="max-w-5xl mx-auto px-6">
        <div class="flex items-start justify-between">
          <div class="flex items-start gap-5">
            <div class="w-16 h-16 rounded-2xl bg-white/20 backdrop-blur-sm flex items-center justify-center text-white text-2xl font-bold border border-white/30 shrink-0">
              字
            </div>
            <div class="text-white">
              <div class="flex items-center gap-3 mb-1">
                <h1 class="text-2xl font-bold">高级前端开发工程师</h1>
                <span class="px-3 py-1 bg-[var(--secondary-container)] text-[var(--primary)] rounded-full text-xs font-bold">92%匹配</span>
              </div>
              <p class="text-white/80 text-sm">字节跳动 · 北京市海淀区 · 技术团队</p>
              <div class="flex items-center gap-4 mt-3">
                <span class="text-xl font-bold">25k-40k</span>
                <span class="text-white/60">·</span>
                <span class="text-sm text-white/80">14薪</span>
                <span class="text-white/60">·</span>
                <span class="text-sm text-white/80">发布于 2天前</span>
              </div>
              <div class="flex items-center gap-2 mt-3">
                <button @click="showApplyModal = true"
                  class="px-6 py-2 bg-white text-[var(--primary)] rounded-xl font-medium text-sm hover:bg-white/90 transition-colors">
                  立即投递
                </button>
                <button @click="isFavorited = !isFavorited"
                  :class="['px-4 py-2 rounded-xl text-sm font-medium border transition-all flex items-center gap-1.5',
                    isFavorited ? 'border-white/40 bg-white/10 text-white' : 'border-white/30 text-white/80 hover:bg-white/10']">
                  <span class="material-symbols-outlined text-lg">{{ isFavorited ? 'favorite' : 'favorite_border' }}</span>
                  {{ isFavorited ? '已收藏' : '收藏' }}
                </button>
              </div>
            </div>
          </div>
          <button class="text-white/60 hover:text-white transition-colors">
            <span class="material-symbols-outlined text-2xl">share</span>
          </button>
        </div>
      </div>
    </div>

    <div class="max-w-5xl mx-auto px-6 py-6">
      <div class="flex gap-6">
        <!-- Left Column -->
        <div class="flex-1 space-y-6">
          <!-- Job Duties -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-4">
              <span class="material-symbols-outlined text-[var(--primary)]">task_alt</span>
              职位描述
            </h3>
            <div class="space-y-3">
              <div v-for="(duty, index) in jobDuties" :key="index" class="flex items-start gap-3">
                <span class="material-symbols-outlined text-[var(--primary)] text-lg mt-0.5">check_circle</span>
                <span class="text-sm text-[var(--on-surface-variant)] leading-relaxed">{{ duty }}</span>
              </div>
            </div>
          </div>

          <!-- Requirements -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-4">
              <span class="material-symbols-outlined text-[var(--primary)]">checklist</span>
              任职要求
            </h3>
            <div class="space-y-3">
              <div v-for="(req, index) in requirements" :key="index" class="flex items-start gap-3">
                <span class="material-symbols-outlined text-gray-300 text-lg mt-0.5">radio_button_unchecked</span>
                <span class="text-sm text-[var(--on-surface-variant)] leading-relaxed">{{ req }}</span>
              </div>
            </div>
          </div>

          <!-- Benefits -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-4">
              <span class="material-symbols-outlined text-[var(--primary)]">card_giftcard</span>
              福利待遇
            </h3>
            <div class="grid grid-cols-3 sm:grid-cols-4 gap-3">
              <div v-for="benefit in benefits" :key="benefit.label"
                class="flex flex-col items-center gap-2 p-3 bg-[var(--surface-container-low)] rounded-xl">
                <span class="material-symbols-outlined text-[var(--primary)] text-xl">{{ benefit.icon }}</span>
                <span class="text-xs text-[var(--on-surface-variant)]">{{ benefit.label }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Sidebar -->
        <div class="hidden lg:block w-80 shrink-0 space-y-5">
          <!-- Company Profile -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-4">
              <span class="material-symbols-outlined text-[var(--primary)]">business</span>
              公司简介
            </h3>
            <div class="flex items-center gap-3 mb-4">
              <div class="w-12 h-12 rounded-xl bg-gradient-to-br from-blue-500 to-blue-600 flex items-center justify-center text-white font-bold text-lg">字</div>
              <div>
                <p class="font-semibold text-sm text-[var(--on-surface)]">字节跳动</p>
                <p class="text-xs text-[var(--on-surface-variant)]">互联网 · 10000人以上</p>
              </div>
            </div>
            <p class="text-xs text-[var(--on-surface-variant)] leading-relaxed mb-4">
              字节跳动是全球领先的科技公司，旗下产品包括抖音、今日头条、TikTok等。公司致力于用技术和创新连接人与信息，推动数字化发展。
            </p>
            <div class="grid grid-cols-2 gap-3 mb-4">
              <div class="bg-[var(--surface-container-low)] rounded-lg p-3 text-center">
                <p class="text-lg font-bold text-[var(--primary)]">10万+</p>
                <p class="text-[10px] text-[var(--on-surface-variant)]">员工规模</p>
              </div>
              <div class="bg-[var(--surface-container-low)] rounded-lg p-3 text-center">
                <p class="text-lg font-bold text-[var(--primary)]">D轮+</p>
                <p class="text-[10px] text-[var(--on-surface-variant)]">融资阶段</p>
              </div>
            </div>
            <div class="space-y-2">
              <div class="flex items-center gap-2 text-xs text-[var(--on-surface-variant)]">
                <span class="material-symbols-outlined text-sm">language</span> www.bytedance.com
              </div>
              <div class="flex items-center gap-2 text-xs text-[var(--on-surface-variant)]">
                <span class="material-symbols-outlined text-sm">location_on</span> 北京市海淀区
              </div>
            </div>
          </div>

          <!-- Similar Jobs -->
          <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-6">
            <h3 class="font-bold text-[var(--on-surface)] flex items-center gap-2 mb-4">
              <span class="material-symbols-outlined text-[var(--primary)]">compare_arrows</span>
              相似职位推荐
            </h3>
            <div class="space-y-3">
              <div v-for="job in similarJobs" :key="job.title"
                class="py-3 border-b border-gray-50 last:border-0 cursor-pointer hover:bg-[var(--surface-container-low)] -mx-2 px-2 rounded-lg transition-colors">
                <p class="text-xs font-medium text-[var(--on-surface)]">{{ job.title }}</p>
                <p class="text-[10px] text-[var(--on-surface-variant)] mt-0.5">{{ job.company }}</p>
                <div class="flex items-center justify-between mt-1.5">
                  <span class="text-[10px] text-[var(--primary)] font-medium">{{ job.salary }}</span>
                  <span class="text-[10px] text-[var(--on-surface-variant)]">{{ job.location }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile Sticky Bottom Bar -->
    <div class="lg:hidden fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 px-6 py-3 flex items-center gap-3 z-40">
      <button @click="isFavorited = !isFavorited"
        :class="['w-12 h-12 rounded-xl border flex items-center justify-center shrink-0 transition-colors',
          isFavorited ? 'border-red-200 bg-red-50 text-red-500' : 'border-gray-200 text-[var(--on-surface-variant)]']">
        <span class="material-symbols-outlined">{{ isFavorited ? 'favorite' : 'favorite_border' }}</span>
      </button>
      <button @click="showApplyModal = true" class="flex-1 py-3 bg-[var(--primary)] text-white rounded-xl font-medium text-sm hover:bg-[var(--primary-container)] transition-colors">
        立即投递
      </button>
    </div>

    <!-- Apply Modal -->
    <Teleport to="body">
      <Transition name="modal">
        <div v-if="showApplyModal" class="fixed inset-0 z-50 flex items-center justify-center p-6" @click.self="showApplyModal = false">
          <div class="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>
          <div class="relative bg-white rounded-2xl shadow-xl w-full max-w-md p-6">
            <div class="flex items-center justify-between mb-5">
              <h3 class="font-bold text-[var(--on-surface)]">确认投递</h3>
              <button @click="showApplyModal = false" class="text-[var(--on-surface-variant)] hover:text-[var(--on-surface)]">
                <span class="material-symbols-outlined">close</span>
              </button>
            </div>
            <div class="bg-[var(--surface-container-low)] rounded-xl p-4 mb-5">
              <p class="text-sm font-medium text-[var(--on-surface)]">高级前端开发工程师</p>
              <p class="text-xs text-[var(--on-surface-variant)] mt-0.5">字节跳动 · 北京</p>
            </div>
            <div class="space-y-3 mb-5">
              <div class="flex items-center gap-3">
                <span class="material-symbols-outlined text-[var(--primary)] text-lg">description</span>
                <div class="flex-1">
                  <p class="text-sm text-[var(--on-surface)]">使用简历：<strong>前端工程师简历</strong></p>
                </div>
              </div>
              <div class="flex items-center gap-3">
                <span class="material-symbols-outlined text-[var(--primary)] text-lg">mail</span>
                <div class="flex-1">
                  <p class="text-sm text-[var(--on-surface)]">联系电话：<strong>138****6789</strong></p>
                </div>
              </div>
            </div>
            <div class="flex gap-3">
              <button @click="showApplyModal = false" class="flex-1 py-3 border border-gray-200 rounded-xl text-sm font-medium text-[var(--on-surface-variant)] hover:bg-gray-50 transition-colors">
                取消
              </button>
              <button @click="handleApply" :disabled="applying"
                class="flex-1 py-3 bg-[var(--primary)] text-white rounded-xl text-sm font-medium hover:bg-[var(--primary-container)] transition-colors disabled:opacity-50">
                {{ applying ? '投递中...' : '确认投递' }}
              </button>
            </div>
          </div>
        </div>
      </Transition>
    </Teleport>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const isFavorited = ref(false)
const showApplyModal = ref(false)
const applying = ref(false)

const jobDuties = [
  '负责公司核心产品的前端架构设计与开发，保障产品的高性能和高可用',
  '参与技术方案评审，持续优化前端工程化体系，提升团队研发效率',
  '与产品、设计、后端团队紧密协作，高质量完成需求开发和迭代',
  '主导前端性能优化，包括首屏加载、运行时性能、包体积优化等',
  '研究和引入前沿技术，推动团队技术栈演进',
  '参与 Code Review，保障代码质量和团队代码规范一致性'
]

const requirements = [
  '本科及以上学历，计算机相关专业优先',
  '3年以上前端开发经验，精通 Vue.js 或 React 框架',
  '扎实的 JavaScript/TypeScript 基础，熟悉 ES6+ 语法',
  '熟悉前端工程化工具：Webpack、Vite、Rollup 等',
  '了解 Node.js，有 SSR 或全栈开发经验者优先',
  '具备良好的性能优化意识和实践经验',
  '优秀的沟通能力和团队协作精神，有技术博客或开源贡献者优先'
]

const benefits = [
  { icon: 'health_and_safety', label: '六险一金' },
  { icon: 'restaurant', label: '免费三餐' },
  { icon: 'schedule', label: '弹性工作' },
  { icon: 'fitness_center', label: '健身房' },
  { icon: 'paraguell', label: '带薪年假' },
  { icon: 'savings', label: '股票期权' },
  { icon: 'emoji_events', label: '年终奖' },
  { icon: 'local_movies', label: '团建活动' }
]

const similarJobs = [
  { title: '资深前端开发工程师', company: '阿里巴巴', salary: '30k-50k', location: '杭州' },
  { title: '前端架构师', company: '美团', salary: '35k-55k', location: '北京' },
  { title: 'React开发工程师', company: '腾讯', salary: '25k-40k', location: '深圳' },
  { title: '全栈开发工程师', company: '京东', salary: '22k-35k', location: '北京' }
]

function handleApply() {
  applying.value = true
  setTimeout(() => {
    applying.value = false
    showApplyModal.value = false
    isFavorited.value = false
  }, 1500)
}
</script>

<style scoped>
.modal-enter-active, .modal-leave-active { transition: all 0.3s ease; }
.modal-enter-from, .modal-leave-to { opacity: 0; }
.modal-enter-active .bg-white, .modal-leave-active .bg-white { transition: transform 0.3s ease; }
.modal-enter-from .bg-white { transform: scale(0.95); }
.modal-leave-to .bg-white { transform: scale(0.95); }
</style>
