<template>
  <div class="min-h-screen bg-[var(--background)]">
    <div class="max-w-[1100px] mx-auto px-4 md:px-6 lg:px-10 py-6">
      <!-- Header -->
      <div class="flex items-center justify-between mb-6">
        <div class="flex items-center gap-3">
          <button @click="router.push('/resume/manage')" class="p-2 rounded-lg hover:bg-gray-100 transition-colors text-[var(--on-surface-variant)]">
            <span class="material-symbols-outlined">arrow_back</span>
          </button>
          <h1 class="text-2xl font-bold text-[var(--on-surface)]">{{ resumeId ? '编辑求职简历' : '新建求职简历' }}</h1>
        </div>
        <div class="flex items-center gap-3">
          <button @click="router.push('/resume/manage')" class="px-4 py-2 bg-white text-[var(--on-surface)] rounded-xl text-sm font-medium border border-gray-200 hover:bg-gray-50 transition-colors shadow-sm">
            返回档案库
          </button>
          <button @click="handleSave" :disabled="saving" class="px-5 py-2 bg-[var(--primary)] text-white rounded-xl text-sm font-semibold hover:bg-[var(--primary-container)] transition-colors flex items-center gap-2 shadow-sm disabled:opacity-50">
            <span class="material-symbols-outlined text-[16px]">{{ saving ? 'hourglass_top' : 'check_circle' }}</span>
            {{ saving ? '保存中...' : '保存简历' }}
          </button>
        </div>
      </div>

      <!-- 简历标题 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <label class="block text-xs font-semibold text-[var(--on-surface-variant)] mb-2">简历标题</label>
        <input v-model="form.title" type="text" placeholder="如：算法岗专用简历 / 央国企投递版"
          class="w-full px-4 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
        <p class="text-[11px] text-[var(--on-surface-variant)] mt-1.5">最多可创建 5 份简历，用于不同岗位方向的精准投递。</p>
      </div>

      <!-- 1. 基本信息 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center gap-2 mb-4">
          <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
          <span class="material-symbols-outlined text-[var(--primary)]">person</span>
          <h3 class="font-bold text-[var(--on-surface)]">基本信息</h3>
        </div>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">姓名 *</label>
            <input v-model="form.name" type="text" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">性别</label>
            <select v-model="form.gender" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)]">
              <option :value="0">未知</option>
              <option :value="1">男</option>
              <option :value="2">女</option>
            </select>
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">出生日期</label>
            <input v-model="form.birthDate" type="date" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">联系电话</label>
            <input v-model="form.phone" type="tel" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">电子邮箱</label>
            <input v-model="form.email" type="email" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">求职状态</label>
            <select v-model="form.workStatus" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)]">
              <option :value="0">在校</option>
              <option :value="1">应届生</option>
              <option :value="2">往届生</option>
            </select>
          </div>
        </div>
      </div>

      <!-- 2. 教育背景 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center gap-2 mb-4">
          <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
          <span class="material-symbols-outlined text-[var(--primary)]">school</span>
          <h3 class="font-bold text-[var(--on-surface)]">教育背景</h3>
        </div>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">最高学历</label>
            <select v-model="form.education" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)]">
              <option value="大专">大专</option>
              <option value="本科">本科</option>
              <option value="硕士">硕士</option>
              <option value="博士">博士</option>
            </select>
          </div>
          <div class="lg:col-span-2">
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">毕业院校</label>
            <input v-model="form.school" type="text" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">毕业年份</label>
            <input v-model="form.graduationYear" type="number" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div class="lg:col-span-2">
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">专业</label>
            <input v-model="form.major" type="text" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
        </div>
      </div>

      <!-- 3. 求职意向 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center gap-2 mb-4">
          <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
          <span class="material-symbols-outlined text-[var(--primary)]">target</span>
          <h3 class="font-bold text-[var(--on-surface)]">求职意向</h3>
        </div>
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望城市（逗号分隔）</label>
            <input v-model="form.expectCity" type="text" placeholder="北京、上海、深圳" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望行业</label>
            <input v-model="form.expectIndustry" type="text" placeholder="互联网、人工智能" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望岗位类型</label>
            <input v-model="form.expectJobType" type="text" placeholder="算法工程师 / 全栈研发" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
          <div>
            <label class="block text-xs text-[var(--on-surface-variant)] mb-1.5">期望薪资</label>
            <input v-model="form.expectSalary" type="text" placeholder="25k-40k / 月（可面议）" class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          </div>
        </div>
      </div>

      <!-- 4. 技能特长 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center gap-2 mb-4">
          <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
          <span class="material-symbols-outlined text-[var(--primary)]">psychology</span>
          <h3 class="font-bold text-[var(--on-surface)]">技能特长</h3>
        </div>
        <div class="flex flex-wrap gap-2 mb-3">
          <span v-for="(skill, i) in skillList" :key="i"
            class="inline-flex items-center gap-1.5 px-3 py-1.5 bg-[#eef2ff] text-[var(--primary)] rounded-lg text-xs font-medium border border-[var(--primary)]/20">
            {{ skill }}
            <button @click="removeSkill(i)" class="hover:text-red-500"><span class="material-symbols-outlined text-[14px]">close</span></button>
          </span>
          <span v-if="skillList.length === 0" class="text-xs text-gray-400">暂无技能，添加一个吧</span>
        </div>
        <div class="flex gap-2">
          <input v-model="newSkill" type="text" placeholder="输入技能名称，如 Python / Vue / MySQL" @keyup.enter="addSkill"
            class="flex-1 px-3 py-2 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10" />
          <button @click="addSkill" class="px-4 py-2 bg-[var(--primary)]/10 text-[var(--primary)] rounded-xl text-xs font-semibold hover:bg-[var(--primary)]/20 transition-colors">添加技能</button>
        </div>
      </div>

      <!-- 5. 实习/工作经历 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center gap-2">
            <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
            <span class="material-symbols-outlined text-[var(--primary)]">work</span>
            <h3 class="font-bold text-[var(--on-surface)]">实习 / 工作经历</h3>
          </div>
          <button @click="addWork" class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
            <span class="material-symbols-outlined text-[14px]">add</span>添加一段经历
          </button>
        </div>
        <div v-for="(exp, i) in workList" :key="i" class="border border-gray-100 rounded-xl p-4 mb-3">
          <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3">
            <div>
              <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">公司名称</label>
              <input v-model="exp.company" type="text" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]" />
            </div>
            <div>
              <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">职位</label>
              <input v-model="exp.position" type="text" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]" />
            </div>
            <div>
              <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">开始时间</label>
              <input v-model="exp.start" type="text" placeholder="2024.06" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]" />
            </div>
            <div>
              <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">结束时间</label>
              <input v-model="exp.end" type="text" placeholder="至今" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]" />
            </div>
          </div>
          <div class="mt-3">
            <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">工作内容与成果</label>
            <textarea v-model="exp.desc" rows="3" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]"></textarea>
          </div>
          <button @click="removeWork(i)" class="mt-2 text-xs text-red-400 hover:text-red-500 flex items-center gap-1">
            <span class="material-symbols-outlined text-[14px]">delete_outline</span>删除该经历
          </button>
        </div>
        <p v-if="workList.length === 0" class="text-xs text-gray-400">暂无经历，点击右上角「添加一段经历」。</p>
      </div>

      <!-- 6. 项目经历 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center gap-2">
            <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
            <span class="material-symbols-outlined text-[var(--primary)]">folder_open</span>
            <h3 class="font-bold text-[var(--on-surface)]">项目经历</h3>
          </div>
          <button @click="addProject" class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
            <span class="material-symbols-outlined text-[14px]">add</span>添加项目
          </button>
        </div>
        <div v-for="(proj, i) in projectList" :key="i" class="border border-gray-100 rounded-xl p-4 mb-3">
          <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">
            <div>
              <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">项目名称</label>
              <input v-model="proj.name" type="text" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]" />
            </div>
            <div>
              <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">担任角色</label>
              <input v-model="proj.role" type="text" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]" />
            </div>
            <div>
              <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">时间</label>
              <input v-model="proj.period" type="text" placeholder="2023.12 — 2024.05" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]" />
            </div>
          </div>
          <div class="mt-3">
            <label class="block text-[11px] text-[var(--on-surface-variant)] mb-1">项目描述与成果</label>
            <textarea v-model="proj.desc" rows="3" class="w-full px-3 py-2 rounded-lg border border-gray-200 text-sm focus:border-[var(--primary)]"></textarea>
          </div>
          <button @click="removeProject(i)" class="mt-2 text-xs text-red-400 hover:text-red-500 flex items-center gap-1">
            <span class="material-symbols-outlined text-[14px]">delete_outline</span>删除该项目
          </button>
        </div>
        <p v-if="projectList.length === 0" class="text-xs text-gray-400">暂无项目，点击右上角「添加项目」。</p>
      </div>

      <!-- 7. 自我介绍 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center gap-2 mb-4">
          <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
          <span class="material-symbols-outlined text-[var(--primary)]">edit_note</span>
          <h3 class="font-bold text-[var(--on-surface)]">自我介绍</h3>
        </div>
        <textarea v-model="form.selfIntroduction" rows="5" placeholder="简要介绍自己的技术方向、优势与求职目标..."
          class="w-full px-3 py-2.5 rounded-xl border border-gray-200 text-sm bg-white focus:border-[var(--primary)] focus:ring-2 focus:ring-[var(--primary)]/10"></textarea>
      </div>

      <!-- 8. 附件与作品图片 -->
      <div class="bg-white rounded-2xl border border-gray-100 shadow-sm p-5 mb-6">
        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center gap-2">
            <span class="w-1 h-5 bg-[var(--primary)] rounded-full"></span>
            <span class="material-symbols-outlined text-[var(--primary)]">attach_file</span>
            <h3 class="font-bold text-[var(--on-surface)]">附件与作品图片</h3>
          </div>
          <button @click="triggerAttach" class="text-xs text-[var(--primary)] hover:underline flex items-center gap-1">
            <span class="material-symbols-outlined text-[14px]">add_photo_alternate</span>
            {{ attaching ? '上传中...' : '上传图片' }}
          </button>
        </div>
        <div v-if="attachmentList.length" class="grid grid-cols-3 sm:grid-cols-4 gap-3">
          <div v-for="(att, i) in attachmentList" :key="att" class="relative group">
            <img :src="att" alt="附件图片" class="w-full h-24 object-cover rounded-xl border border-gray-100" />
            <button @click="removeAttachment(i)" class="absolute -top-2 -right-2 w-6 h-6 bg-red-500 text-white rounded-full hidden group-hover:flex items-center justify-center shadow">
              <span class="material-symbols-outlined text-[14px]">close</span>
            </button>
          </div>
        </div>
        <p v-else class="text-xs text-gray-400">可上传作品截图、证书扫描件等图片，随简历一起提交给 HR。</p>
        <input ref="attachInput" type="file" accept="image/*" class="hidden" @change="onAttachChange" />
      </div>

      <!-- Bottom Save Bar -->
      <div class="flex justify-end gap-3 pb-10">
        <button @click="router.push('/resume/manage')" class="px-6 py-2.5 bg-white text-[var(--on-surface)] rounded-xl text-sm font-medium border border-gray-200 hover:bg-gray-50 transition-colors">
          取消
        </button>
        <button @click="handleSave" :disabled="saving" class="px-8 py-2.5 bg-[var(--primary)] text-white rounded-xl text-sm font-semibold hover:bg-[var(--primary-container)] transition-colors disabled:opacity-50">
          {{ saving ? '保存中...' : '保存简历' }}
        </button>
      </div>
    </div>

    <!-- Toast -->
    <div v-if="toast" class="fixed bottom-6 left-1/2 -translate-x-1/2 px-6 py-3 bg-[var(--on-surface)] text-white rounded-xl shadow-lg text-sm font-medium z-[110]">
      {{ toast }}
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '../stores/app'
import { getResumeInfo, saveResume, updateResume } from '../api/resume'
import { uploadImage } from '../api/upload'

const route = useRoute()
const router = useRouter()
const store = useAppStore()
const saving = ref(false)
const toast = ref('')
const newSkill = ref('')
const resumeId = computed(() => route.query.id ? Number(route.query.id) : null)

const form = reactive({
  title: '我的简历',
  name: '',
  gender: 0,
  birthDate: '',
  phone: '',
  email: '',
  education: '本科',
  school: '',
  major: '',
  graduationYear: 2026,
  workStatus: 1,
  expectCity: '',
  expectIndustry: '',
  expectJobType: '',
  expectSalary: '',
  selfIntroduction: '',
  skills: '[]',
  experiences: '[]',
  projects: '[]',
  awards: '[]',
  attachments: '[]'
})

const skillList = ref([])
const workList = ref([])
const projectList = ref([])
const attachmentList = ref([])
const attachInput = ref(null)
const attaching = ref(false)

function showToast(msg) {
  toast.value = msg
  setTimeout(() => { toast.value = '' }, 2500)
}

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

onMounted(async () => {
  if (store.user) {
    form.name = form.name || store.user.name || ''
    form.phone = form.phone || store.user.phone || ''
  }
  if (resumeId.value) {
    try {
      const data = await getResumeInfo(resumeId.value)
      Object.keys(form).forEach(key => {
        if (data[key] !== null && data[key] !== undefined) form[key] = data[key]
      })
      skillList.value = parseList(form.skills)
      workList.value = parseList(form.experiences)
      projectList.value = parseList(form.projects)
      attachmentList.value = parseList(form.attachments)
    } catch (e) {
      showToast('加载简历失败：' + (e.message || '请重试'))
    }
  }
})

function addSkill() {
  const s = newSkill.value.trim()
  if (!s) return
  if (skillList.value.includes(s)) { newSkill.value = ''; return }
  skillList.value.push(s)
  newSkill.value = ''
}

function removeSkill(i) {
  skillList.value.splice(i, 1)
}

function addWork() {
  workList.value.push({ company: '', position: '', start: '', end: '', desc: '' })
}

function removeWork(i) {
  workList.value.splice(i, 1)
}

function addProject() {
  projectList.value.push({ name: '', role: '', period: '', desc: '' })
}

function removeProject(i) {
  projectList.value.splice(i, 1)
}

function triggerAttach() {
  attachInput.value && attachInput.value.click()
}

async function onAttachChange(e) {
  const file = e.target.files && e.target.files[0]
  e.target.value = ''
  if (!file) return
  if (!file.type.startsWith('image/')) { showToast('请选择图片文件'); return }
  attaching.value = true
  try {
    const url = await uploadImage(file)
    attachmentList.value.push(url)
    showToast('图片上传成功')
  } catch (err) {
    showToast(err.message || '上传失败，请重试')
  } finally {
    attaching.value = false
  }
}

function removeAttachment(i) {
  attachmentList.value.splice(i, 1)
}

async function handleSave() {
  if (!form.title.trim()) { showToast('请填写简历标题'); return }
  if (!form.name.trim()) { showToast('请填写姓名'); return }
  saving.value = true
  try {
    // 把可编辑列表序列化回 JSON 字段
    form.skills = JSON.stringify(skillList.value)
    form.experiences = JSON.stringify(workList.value.filter(e => e.company || e.position))
    form.projects = JSON.stringify(projectList.value.filter(p => p.name))
    form.attachments = JSON.stringify(attachmentList.value)
    const payload = { ...form }
    // 空字符串日期后端无法转 LocalDate，置为 null
    if (!payload.birthDate) payload.birthDate = null
    if (resumeId.value) {
      await updateResume(resumeId.value, payload)
      showToast('简历已更新')
    } else {
      await saveResume(payload)
      showToast('简历创建成功')
    }
    // 保存成功跳回「我的简历」档案库
    setTimeout(() => router.push('/resume/manage'), 500)
  } catch (e) {
    showToast(e.message || '保存失败，请重试')
  } finally {
    saving.value = false
  }
}
</script>
