<template>
  <Teleport to="body">
    <Transition name="modal">
      <div v-if="visible" class="fixed inset-0 z-[100] flex items-center justify-center p-4" @click.self="$emit('close')">
        <div class="absolute inset-0 bg-black/50 backdrop-blur-sm"></div>
        <div class="relative w-full max-w-2xl max-h-[85vh] bg-surface-container-lowest rounded-2xl shadow-2xl overflow-hidden flex flex-col">
          <!-- 头部 -->
          <div class="flex items-center justify-between px-6 py-4 border-b border-surface-container-high">
            <h3 class="font-bold text-on-surface">候选人简历</h3>
            <button @click="$emit('close')" class="w-8 h-8 rounded-lg hover:bg-surface-container flex items-center justify-center text-on-surface-variant">
              <span class="material-symbols-outlined text-lg">close</span>
            </button>
          </div>

          <!-- 内容 -->
          <div class="flex-1 overflow-y-auto p-6">
            <div v-if="loading" class="py-16 text-center text-sm text-on-surface-variant">
              <span class="material-symbols-outlined text-4xl text-surface-container-high mb-3 block">hourglass_top</span>
              正在加载简历…
            </div>
            <div v-else-if="error" class="py-16 text-center text-sm text-error">{{ error }}</div>
            <div v-else-if="resume" class="space-y-6">
              <!-- 基本信息 -->
              <div class="flex items-start gap-4">
                <div class="w-14 h-14 rounded-xl bg-primary/10 flex items-center justify-center text-primary font-bold text-xl shrink-0">
                  {{ (resume.name || '简').charAt(0) }}
                </div>
                <div>
                  <div class="flex items-center gap-2 flex-wrap">
                    <h4 class="text-lg font-bold text-on-surface">{{ resume.name || '未填写姓名' }}</h4>
                    <span v-if="educationText" class="px-2 py-0.5 bg-primary/10 text-primary text-[10px] font-semibold rounded">{{ educationText }}</span>
                    <span v-if="resume.workStatus != null" class="px-2 py-0.5 bg-emerald-50 text-emerald-600 text-[10px] font-semibold rounded">{{ workStatusText }}</span>
                  </div>
                  <p class="text-xs text-on-surface-variant mt-1">
                    {{ [resume.school, resume.major, resume.graduationYear ? resume.graduationYear + '届' : ''].filter(Boolean).join(' · ') || '未填写教育背景' }}
                  </p>
                  <div class="flex flex-wrap items-center gap-4 mt-2 text-xs text-on-surface-variant">
                    <span class="flex items-center gap-1"><span class="material-symbols-outlined text-sm">phone</span>{{ resume.phone || '未填写' }}</span>
                    <span v-if="resume.email" class="flex items-center gap-1"><span class="material-symbols-outlined text-sm">email</span>{{ resume.email }}</span>
                  </div>
                </div>
              </div>

              <!-- 求职意向 -->
              <div class="bg-surface-container-low rounded-xl p-4 grid grid-cols-2 gap-3 text-xs">
                <div><span class="text-on-surface-variant">期望城市：</span><span class="font-medium text-on-surface">{{ resume.expectCity || '未填写' }}</span></div>
                <div><span class="text-on-surface-variant">期望薪资：</span><span class="font-medium text-primary">{{ resume.expectSalary || '面议' }}</span></div>
                <div><span class="text-on-surface-variant">期望行业：</span><span class="font-medium text-on-surface">{{ resume.expectIndustry || '未填写' }}</span></div>
                <div><span class="text-on-surface-variant">期望岗位：</span><span class="font-medium text-on-surface">{{ resume.expectJobType || '未填写' }}</span></div>
              </div>

              <!-- 技能 -->
              <div>
                <h5 class="text-sm font-bold text-on-surface mb-2">技能特长</h5>
                <div v-if="skills.length" class="flex flex-wrap gap-1.5">
                  <span v-for="s in skills" :key="s" class="px-2.5 py-1 bg-primary/5 border border-primary/10 rounded-lg text-xs text-primary">{{ s }}</span>
                </div>
                <p v-else class="text-xs text-on-surface-variant">未填写</p>
              </div>

              <!-- 经历 -->
              <div>
                <h5 class="text-sm font-bold text-on-surface mb-2">实习 / 工作经历</h5>
                <div v-if="experiences.length" class="space-y-3">
                  <div v-for="(e, i) in experiences" :key="i" class="border border-surface-container-high rounded-lg p-3">
                    <div class="flex items-center justify-between">
                      <span class="text-xs font-semibold text-on-surface">{{ e.company || '未填写公司' }}<span v-if="e.position" class="text-primary ml-2">{{ e.position }}</span></span>
                      <span class="text-[10px] text-on-surface-variant">{{ [e.start, e.end].filter(Boolean).join(' — ') }}</span>
                    </div>
                    <p v-if="e.desc" class="text-xs text-on-surface-variant mt-1.5 leading-relaxed">{{ e.desc }}</p>
                  </div>
                </div>
                <p v-else class="text-xs text-on-surface-variant">未填写</p>
              </div>

              <!-- 项目 -->
              <div>
                <h5 class="text-sm font-bold text-on-surface mb-2">项目经历</h5>
                <div v-if="projects.length" class="space-y-3">
                  <div v-for="(p, i) in projects" :key="i" class="border border-surface-container-high rounded-lg p-3">
                    <div class="flex items-center justify-between">
                      <span class="text-xs font-semibold text-on-surface">{{ p.name || '未命名项目' }}<span v-if="p.role" class="text-primary ml-2">{{ p.role }}</span></span>
                      <span v-if="p.period" class="text-[10px] text-on-surface-variant">{{ p.period }}</span>
                    </div>
                    <p v-if="p.desc" class="text-xs text-on-surface-variant mt-1.5 leading-relaxed">{{ p.desc }}</p>
                  </div>
                </div>
                <p v-else class="text-xs text-on-surface-variant">未填写</p>
              </div>

              <!-- 自我介绍 -->
              <div>
                <h5 class="text-sm font-bold text-on-surface mb-2">自我介绍</h5>
                <p class="text-xs text-on-surface-variant leading-relaxed whitespace-pre-wrap">{{ resume.selfIntroduction || '未填写' }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { getCandidateResume } from '../api/application'

const props = defineProps({
  visible: { type: Boolean, default: false },
  applicationId: { type: [Number, String], default: null }
})
defineEmits(['close'])

const loading = ref(false)
const error = ref('')
const resume = ref(null)

const educationNames = ['大专', '本科', '硕士', '博士']

const educationText = computed(() => {
  const edu = resume.value?.education
  if (!edu) return ''
  if (typeof edu === 'number') return educationNames[edu - 1] || ''
  return String(edu)
})

const workStatusText = computed(() => ['在校', '应届生', '往届生'][resume.value?.workStatus] || '在校')

function parseList(field) {
  if (Array.isArray(field)) return field
  if (typeof field === 'string') {
    try {
      const v = JSON.parse(field)
      return Array.isArray(v) ? v : []
    } catch { /* ignore */ }
  }
  return []
}

const skills = computed(() => parseList(resume.value?.skills).filter(s => typeof s === 'string'))
const experiences = computed(() => parseList(resume.value?.experiences))
const projects = computed(() => parseList(resume.value?.projects))

watch(() => [props.visible, props.applicationId], async ([visible, applicationId]) => {
  if (!visible || !applicationId) { resume.value = null; error.value = ''; return }
  loading.value = true
  error.value = ''
  try {
    resume.value = await getCandidateResume(applicationId)
  } catch (e) {
    error.value = e.message || '加载简历失败'
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.modal-enter-active, .modal-leave-active { transition: opacity 0.2s ease; }
.modal-enter-from, .modal-leave-to { opacity: 0; }
</style>
