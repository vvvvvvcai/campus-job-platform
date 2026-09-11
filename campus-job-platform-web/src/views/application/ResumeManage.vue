<template>
  <div class="relative w-full max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-xl">
    <div class="absolute top-10 left-1/3 w-96 h-96 bg-primary/5 rounded-full blur-3xl pointer-events-none -z-10"></div>
    <div class="absolute top-48 right-10 w-80 h-80 bg-secondary-container/10 rounded-full blur-2xl pointer-events-none -z-10"></div>

    <nav aria-label="路径导航" class="flex items-center gap-space-xs mb-space-sm text-on-surface-variant font-label-md text-label-md">
      <router-link class="hover:text-primary transition-colors flex items-center gap-1" to="/profile">
        <span class="material-symbols-outlined text-[16px]">account_circle</span>
        <span>个人中心</span>
      </router-link>
      <span class="material-symbols-outlined text-[14px] text-outline">chevron_right</span>
      <span class="text-primary font-semibold">我的简历</span>
    </nav>

    <div class="flex flex-col lg:flex-row lg:items-center justify-between gap-space-md mb-space-xl">
      <div class="space-y-1">
        <div class="flex items-center gap-space-sm">
          <h1 class="font-headline-xl text-headline-xl text-on-surface tracking-tight font-bold">我的简历档案库</h1>
          <span class="px-2.5 py-0.5 rounded-full bg-surface-container-high text-primary font-label-sm text-label-sm font-semibold tracking-wide uppercase">
            {{ resumes.length }}/5 已创建
          </span>
        </div>
        <p class="font-body-md text-body-md text-on-surface-variant max-w-2xl">
          支持维护多份针对性简历，根据算法、前端或央企国企精准投递，告别"千企一面"，有效提升面试邀约率。
        </p>
      </div>
      <div class="flex flex-wrap items-center gap-space-sm shrink-0">
        <button class="group flex items-center gap-space-xs px-space-md py-2.5 bg-surface-container-lowest hover:bg-surface-container-low text-on-surface rounded-lg shadow-sm hover:shadow-md transition-all duration-200" type="button">
          <span class="material-symbols-outlined text-secondary text-[20px] group-hover:scale-110 transition-transform">auto_awesome</span>
          <span class="font-label-lg text-label-lg font-medium">AI 简历润色诊断</span>
        </button>
        <button class="group flex items-center gap-space-xs px-space-md py-2.5 bg-surface-container-lowest hover:bg-surface-container-low text-on-surface rounded-lg shadow-sm hover:shadow-md transition-all duration-200" type="button">
          <span class="material-symbols-outlined text-on-surface-variant text-[20px]">picture_as_pdf</span>
          <span class="font-label-lg text-label-lg font-medium">一键导出 PDF</span>
        </button>
        <router-link to="/resume/edit" class="flex items-center gap-space-xs px-space-lg py-2.5 bg-primary-container hover:bg-primary text-on-primary rounded-lg shadow-sm hover:shadow-md transition-all duration-200">
          <span class="material-symbols-outlined text-[20px]">add</span>
          <span class="font-label-lg text-label-lg font-semibold">新建简历</span>
        </router-link>
      </div>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-12 gap-space-xl items-start">
      <div class="lg:col-span-4 space-y-space-md">
        <div class="bg-surface-container-lowest rounded-xl p-space-md shadow-sm">
          <div class="flex items-start gap-space-sm">
            <div class="w-10 h-10 rounded-lg bg-surface-container-high text-primary flex items-center justify-center shrink-0">
              <span class="material-symbols-outlined text-[24px]">query_stats</span>
            </div>
            <div class="flex-1 min-w-0">
              <div class="flex items-center justify-between gap-1">
                <span class="font-label-md text-label-md font-bold text-on-surface">投递实时热度</span>
                <span class="flex items-center gap-1 font-label-sm text-label-sm text-secondary font-medium">
                  <span class="w-1.5 h-1.5 rounded-full bg-secondary-container animate-pulse"></span>
                  近7天动态
                </span>
              </div>
              <p class="font-body-sm text-body-sm text-on-surface-variant mt-1 leading-snug">
                默认简历被 <span class="font-bold text-primary">{{ stats.viewedByHR }}</span> 位名企 HR 重点查阅，初筛通过率达 <span class="font-bold text-secondary">{{ stats.passRate }}%</span>，超越 {{ stats.surpassPercent }}% 同级生。
              </p>
            </div>
          </div>
        </div>

        <div class="flex items-center justify-between px-1">
          <span class="font-label-md text-label-md font-semibold text-on-surface tracking-wider uppercase">
            简历列表
          </span>
          <span class="font-label-sm text-label-sm text-on-surface-variant">点击卡片切换右侧预览</span>
        </div>

        <div class="space-y-space-sm">
          <div
            v-for="(resume, index) in resumes"
            :key="resume.id"
            class="relative rounded-xl p-space-md cursor-pointer transition-all duration-200"
            :class="[
              index === activeResumeIndex
                ? 'bg-surface-container-lowest shadow-md'
                : 'bg-surface-container-lowest hover:bg-surface-container-low/40 shadow-sm hover:shadow-md group'
            ]"
            @click="activeResumeIndex = index"
          >
            <div v-if="index === activeResumeIndex" class="absolute top-0 right-0 overflow-hidden w-20 h-20 pointer-events-none">
              <div class="absolute transform rotate-45 bg-primary text-on-primary text-[10px] font-bold py-0.5 right-[-35px] top-[18px] w-[120px] text-center shadow-sm">
                预览中
              </div>
            </div>

            <div class="flex items-center justify-between">
              <div class="flex items-center gap-space-xs mb-1">
                <span v-if="index === 0" class="px-2 py-0.5 rounded-full bg-primary text-on-primary font-label-sm text-label-sm font-semibold">
                  默认投递
                </span>
                <span v-else class="px-2 py-0.5 rounded-full bg-surface-container text-on-surface font-label-sm text-label-sm font-medium">
                  {{ resume.tag }}
                </span>
                <span v-if="index === 0" class="px-2 py-0.5 rounded-full bg-surface-container-high text-on-surface font-label-sm text-label-sm font-medium">
                  研发岗专用
                </span>
              </div>
              <button
                v-if="index > 0"
                class="font-label-sm text-label-sm text-primary hover:underline font-semibold flex items-center gap-0.5"
                type="button"
                @click.stop="setDefaultResume(index)"
              >
                <span class="material-symbols-outlined text-[14px]">star</span>
                设为默认
              </button>
            </div>

            <h2 class="font-headline-sm text-headline-sm font-bold mt-2 pr-12 line-clamp-1"
              :class="[
                index === activeResumeIndex
                  ? 'text-on-surface'
                  : 'text-on-surface font-semibold group-hover:text-primary transition-colors'
              ]"
            >
              {{ resume.title }}
            </h2>

            <div class="mt-space-sm space-y-1">
              <div class="flex justify-between items-center font-label-sm text-label-sm">
                <span class="text-on-surface-variant">简历完整度</span>
                <span class="font-semibold" :class="index === 0 ? 'text-primary' : 'text-on-surface'">{{ resume.completeness }}%</span>
              </div>
              <div class="w-full bg-surface-container-low h-1.5 rounded-full overflow-hidden">
                <div
                  class="h-full rounded-full transition-all duration-500"
                  :class="[
                    index === 0 ? 'bg-primary' : index === 1 ? 'bg-secondary-container' : 'bg-secondary'
                  ]"
                  :style="{ width: resume.completeness + '%' }"
                ></div>
              </div>
            </div>

            <div class="flex items-center justify-between mt-space-md pt-space-xs text-on-surface-variant font-label-sm text-label-sm">
              <span class="flex items-center gap-1">
                <span class="material-symbols-outlined text-[14px]">schedule</span>
                {{ resume.updatedAt }}
              </span>
              <template v-if="index === 0">
                <span class="text-on-surface-variant/60 font-medium">默认简历不可删除</span>
              </template>
              <template v-else>
                <div class="flex items-center gap-1">
                  <button class="p-1 text-on-surface-variant hover:text-primary rounded transition-colors" title="编辑简历" type="button" @click.stop="editResume(resume.id)">
                    <span class="material-symbols-outlined text-[18px]">edit_note</span>
                  </button>
                  <button class="p-1 text-on-surface-variant hover:text-error rounded transition-colors" title="删除简历" type="button" @click.stop="deleteResume(resume.id)">
                    <span class="material-symbols-outlined text-[18px]">delete</span>
                  </button>
                </div>
              </template>
            </div>

            <div v-if="index === activeResumeIndex && index === 0" class="mt-space-sm pt-space-sm bg-surface-container-low/50 -mx-space-md -mb-space-md px-space-md py-2.5 rounded-b-xl flex items-center justify-between">
              <span class="font-label-sm text-label-sm text-primary font-semibold flex items-center gap-1">
                <span class="material-symbols-outlined text-[16px]">visibility</span> 当前显示中
              </span>
              <div class="flex items-center gap-2">
                <button class="px-3 py-1 bg-surface-container-lowest hover:bg-surface-container text-on-surface rounded-md font-label-sm text-label-sm font-medium shadow-sm transition-colors flex items-center gap-1" type="button">
                  <span class="material-symbols-outlined text-[14px]">edit</span>
                  编辑内容
                </button>
                <button class="p-1 text-on-surface-variant hover:text-primary rounded-md transition-colors" title="快速导出PDF" type="button">
                  <span class="material-symbols-outlined text-[18px]">download</span>
                </button>
              </div>
            </div>
          </div>

          <router-link
            to="/resume/edit"
            class="w-full p-space-lg rounded-xl bg-surface-container-low/40 hover:bg-surface-container-low text-on-surface-variant hover:text-primary transition-all duration-200 flex flex-col items-center justify-center gap-space-xs group shadow-sm"
          >
            <div class="w-10 h-10 rounded-full bg-surface-container-lowest flex items-center justify-center text-on-surface-variant group-hover:text-primary group-hover:scale-110 transition-all shadow-sm">
              <span class="material-symbols-outlined text-[24px]">add</span>
            </div>
            <span class="font-label-lg text-label-lg font-semibold">新建简历版本</span>
            <span class="font-label-sm text-label-sm text-on-surface-variant/70">还可创建 {{ 5 - resumes.length }} 份专用简历 (上限 5 份)</span>
          </router-link>
        </div>

        <div class="bg-surface-container rounded-xl p-space-md text-on-surface">
          <div class="flex items-center gap-space-xs mb-2">
            <span class="material-symbols-outlined text-secondary text-[20px]">lightbulb</span>
            <span class="font-headline-sm text-headline-sm font-semibold">就业导师建议</span>
          </div>
          <p class="font-body-sm text-body-sm text-on-surface-variant leading-relaxed">
            算法类岗位重点考察顶会论文、竞赛名次与模型微调实际业务指标；央国企则注重专业课程成绩排位与综合素养认证。建议保持 2 份以上差异化版本！
          </p>
        </div>
      </div>

      <div class="lg:col-span-8">
        <div class="bg-surface-container-lowest rounded-t-xl px-space-lg py-space-sm shadow-sm flex flex-wrap items-center justify-between gap-space-sm mb-1">
          <div class="flex items-center gap-space-sm">
            <span class="w-2.5 h-2.5 rounded-full bg-primary-container"></span>
            <span class="font-label-md text-label-md font-semibold text-on-surface">
              实时高保真排版预览 (标准 A4 · 紧凑单页)
            </span>
            <span class="px-2 py-0.5 rounded bg-surface-container text-on-surface-variant font-label-sm text-label-sm">
              缩放: 100%
            </span>
          </div>
          <div class="flex items-center gap-space-xs">
            <button class="px-3 py-1.5 bg-surface-container-low hover:bg-surface-container text-on-surface rounded-md font-label-sm text-label-sm font-medium transition-colors flex items-center gap-1" type="button">
              <span class="material-symbols-outlined text-[16px]">palette</span>
              切换简历主题模版
            </button>
            <button class="px-3 py-1.5 bg-surface-container-low hover:bg-surface-container text-on-surface rounded-md font-label-sm text-label-sm font-medium transition-colors flex items-center gap-1" type="button">
              <span class="material-symbols-outlined text-[16px]">share</span>
              生成投递短链
            </button>
            <button class="px-3 py-1.5 bg-primary-container hover:bg-primary text-on-primary rounded-md font-label-sm text-label-sm font-semibold transition-colors flex items-center gap-1 shadow-sm" type="button">
              <span class="material-symbols-outlined text-[16px]">print</span>
              直接打印
            </button>
          </div>
        </div>

        <div class="bg-surface-container-lowest rounded-b-xl shadow-xl p-space-xl lg:p-space-2xl text-on-surface space-y-space-lg select-text">
          <div class="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-space-lg pb-space-lg bg-surface-container-low/30 rounded-xl p-space-lg">
            <div class="flex items-center gap-space-lg min-w-0">
              <img alt="林晨个人照片" class="w-24 h-24 rounded-full object-cover shadow-md shrink-0 bg-surface-container" :src="preview.avatar" />
              <div class="space-y-1.5 min-w-0">
                <div class="flex items-center gap-space-sm flex-wrap">
                  <h3 class="font-headline-lg text-headline-lg font-bold text-on-surface tracking-tight">{{ preview.name }}</h3>
                  <span class="px-2.5 py-0.5 rounded-full bg-secondary-fixed text-on-secondary-fixed-variant font-label-sm text-label-sm font-semibold">
                    {{ preview.status }}
                  </span>
                </div>
                <p class="font-headline-sm text-headline-sm text-primary font-semibold">
                  求职意向：{{ preview.targetPosition }}
                </p>
                <p class="font-body-sm text-body-sm text-on-surface-variant flex items-center gap-2 flex-wrap">
                  <span>{{ preview.school }}</span>
                  <span>·</span>
                  <span>{{ preview.major }}</span>
                  <span>·</span>
                  <span>{{ preview.degree }}</span>
                </p>
                <div class="flex items-center gap-space-md pt-1 text-on-surface-variant font-body-sm text-body-sm flex-wrap">
                  <span class="flex items-center gap-1">
                    <span class="material-symbols-outlined text-[16px] text-primary">call</span>
                    {{ preview.phone }}
                  </span>
                  <span class="flex items-center gap-1">
                    <span class="material-symbols-outlined text-[16px] text-primary">mail</span>
                    {{ preview.email }}
                  </span>
                  <span class="flex items-center gap-1">
                    <span class="material-symbols-outlined text-[16px] text-primary">location_on</span>
                    {{ preview.location }}
                  </span>
                </div>
              </div>
            </div>
            <div class="hidden sm:flex flex-col items-center justify-center p-3 rounded-xl bg-surface-container-lowest shadow-sm shrink-0">
              <div class="relative w-14 h-14 flex items-center justify-center">
                <svg class="w-full h-full -rotate-90" viewBox="0 0 36 36">
                  <path class="text-surface-container" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" fill="none" stroke="currentColor" stroke-width="3.5"></path>
                  <path class="text-primary" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" fill="none" stroke="currentColor" stroke-dasharray="96, 100" stroke-linecap="round" stroke-width="3.5"></path>
                </svg>
                <span class="absolute font-headline-sm text-headline-sm font-bold text-primary">{{ preview.matchScore }}</span>
              </div>
              <span class="font-label-sm text-label-sm text-on-surface-variant mt-1 font-medium">算法岗匹配度</span>
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-3 gap-space-md p-space-md bg-surface-container-low rounded-xl">
            <div>
              <span class="font-label-sm text-label-sm text-on-surface-variant uppercase tracking-wider block">求职性质</span>
              <span class="font-label-lg text-label-lg font-semibold text-on-surface mt-0.5 block">{{ preview.jobType }}</span>
            </div>
            <div>
              <span class="font-label-sm text-label-sm text-on-surface-variant uppercase tracking-wider block">期望城市</span>
              <span class="font-label-lg text-label-lg font-semibold text-on-surface mt-0.5 block">{{ preview.cities }}</span>
            </div>
            <div>
              <span class="font-label-sm text-label-sm text-on-surface-variant uppercase tracking-wider block">期望薪资范围</span>
              <span class="font-label-lg text-label-lg font-semibold text-secondary mt-0.5 block">{{ preview.salary }}</span>
            </div>
          </div>

          <section class="space-y-space-sm">
            <div class="flex items-center gap-space-xs pb-1">
              <span class="w-1.5 h-4 bg-primary rounded-full"></span>
              <h4 class="font-headline-sm text-headline-sm font-bold text-on-surface uppercase tracking-wide">教育背景</h4>
            </div>
            <div class="space-y-space-sm">
              <div v-for="edu in preview.education" :key="edu.school" class="p-space-md rounded-xl bg-surface-container-low/40 hover:bg-surface-container-low transition-colors">
                <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-1">
                  <div class="flex items-center gap-space-sm">
                    <span class="font-headline-sm text-headline-sm font-bold text-on-surface">{{ edu.school }}</span>
                    <span class="px-2 py-0.5 rounded bg-surface-container-highest text-on-surface font-label-sm text-label-sm">{{ edu.tag }}</span>
                  </div>
                  <span class="font-label-md text-label-md text-on-surface-variant font-medium">{{ edu.period }}</span>
                </div>
                <div class="flex items-center gap-space-md mt-1 text-on-surface-variant font-body-md text-body-md">
                  <span class="font-medium text-on-surface">{{ edu.department }}</span>
                  <span>|</span>
                  <span>{{ edu.gpa }}</span>
                  <span>|</span>
                  <span>{{ edu.honor }}</span>
                </div>
              </div>
            </div>
          </section>

          <section class="space-y-space-sm">
            <div class="flex items-center gap-space-xs pb-1">
              <span class="w-1.5 h-4 bg-primary rounded-full"></span>
              <h4 class="font-headline-sm text-headline-sm font-bold text-on-surface uppercase tracking-wide">专业技术栈与工具</h4>
            </div>
            <div class="flex flex-wrap gap-2">
              <span
                v-for="(skill, idx) in preview.skills"
                :key="skill"
                class="px-3 py-1.5 rounded-lg bg-surface-container font-label-md text-label-md font-semibold"
                :class="idx < 2 ? 'text-primary' : 'text-on-surface'"
              >
                {{ skill }}
              </span>
            </div>
          </section>

          <section class="space-y-space-sm">
            <div class="flex items-center gap-space-xs pb-1">
              <span class="w-1.5 h-4 bg-primary rounded-full"></span>
              <h4 class="font-headline-sm text-headline-sm font-bold text-on-surface uppercase tracking-wide">核心实习经历</h4>
            </div>
            <div class="space-y-space-md">
              <div v-for="exp in preview.experience" :key="exp.company" class="p-space-md rounded-xl bg-surface-container-low/40">
                <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-1 mb-1">
                  <div class="flex items-center gap-space-sm">
                    <span class="font-headline-sm text-headline-sm font-bold text-on-surface">{{ exp.company }}</span>
                    <span class="px-2 py-0.5 rounded bg-surface-container text-primary font-label-sm text-label-sm font-semibold">{{ exp.role }}</span>
                  </div>
                  <span class="font-label-md text-label-md text-on-surface-variant font-medium">{{ exp.period }}</span>
                </div>
                <ul class="mt-2 space-y-1.5 text-on-surface-variant font-body-md text-body-md list-disc list-inside">
                  <li v-for="(item, i) in exp.highlights" :key="i" v-html="item"></li>
                </ul>
              </div>
            </div>
          </section>

          <section class="space-y-space-sm">
            <div class="flex items-center gap-space-xs pb-1">
              <span class="w-1.5 h-4 bg-primary rounded-full"></span>
              <h4 class="font-headline-sm text-headline-sm font-bold text-on-surface uppercase tracking-wide">项目经历</h4>
            </div>
            <div class="space-y-space-md">
              <div v-for="proj in preview.projects" :key="proj.name" class="p-space-md rounded-xl bg-surface-container-low/40">
                <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-1 mb-1">
                  <span class="font-headline-sm text-headline-sm font-bold text-on-surface">{{ proj.name }}</span>
                  <span class="font-label-md text-label-md text-on-surface-variant font-medium">{{ proj.period }}</span>
                </div>
                <p class="font-body-md text-body-md text-on-surface-variant leading-relaxed">
                  {{ proj.description }}
                </p>
              </div>
            </div>
          </section>

          <section class="space-y-space-sm">
            <div class="flex items-center gap-space-xs pb-1">
              <span class="w-1.5 h-4 bg-primary rounded-full"></span>
              <h4 class="font-headline-sm text-headline-sm font-bold text-on-surface uppercase tracking-wide">重要荣誉与语言资质</h4>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-space-sm">
              <div v-for="cert in preview.certifications" :key="cert.name" class="flex items-center gap-space-sm p-space-sm rounded-lg bg-surface-container-low/50">
                <span class="material-symbols-outlined text-[20px]" :class="cert.iconColor">{{ cert.icon }}</span>
                <div class="text-on-surface font-body-md text-body-md">
                  <span class="font-bold">{{ cert.name }}</span> {{ cert.detail }}
                </div>
              </div>
            </div>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const activeResumeIndex = ref(0)

const stats = ref({
  viewedByHR: 48,
  passRate: 86,
  surpassPercent: 92
})

const resumes = ref([
  {
    id: 1,
    title: '2025届计算机研发与算法专向简历',
    tag: '默认投递',
    completeness: 95,
    updatedAt: '2025-03-08 14:20',
    isDefault: true
  },
  {
    id: 2,
    title: '互联网全栈 / 前端开发通用简历',
    tag: '备用版本',
    completeness: 88,
    updatedAt: '2025-03-02 09:15',
    isDefault: false
  },
  {
    id: 3,
    title: '央国企及科研院所投递专版 (附政审与成绩单)',
    tag: '院所专版',
    completeness: 90,
    updatedAt: '2025-02-25 18:40',
    isDefault: false
  }
])

const preview = ref({
  name: '林晨',
  avatar: 'https://lh3.googleusercontent.com/aida/AEtjO1XPn4bde-4FIJg1FALQjcAhAP80q33UdVIKCmaL-MAe9qcVJd1HGVT9JHwj4c4QhSIgeZf6fGHV-oqpvv3w5-87SYzFzqbVd0-YPQdK6tEvWoHOQx475L_8KeDWSwOZBUrjnYSWTt9dKqeDHaVvG9ctKkXLwoQ426PlISPNlGKBCtkMQX3MOc1pilemHGmKg4CLXdO-0J9yWSmaT7xsJQ_8aKhapFEg27CHOD2qPJ_04HKwZ1zR7RmuDcQ',
  status: '在校 · 积极寻职中 (随时到岗)',
  targetPosition: '大模型算法工程师 / 全栈架构研发',
  school: '北京科技重点大学',
  major: '计算机科学与技术',
  degree: '2025届统招工学硕士',
  phone: '188****6820',
  email: 'linchen.cs@campus.edu.cn',
  location: '北京 · 海淀区',
  matchScore: 96,
  jobType: '2025届全职校招 / 实习转正',
  cities: '北京、上海、深圳 (支持驻场)',
  salary: '25k - 40k / 月 (可面议)',
  education: [
    {
      school: '北京科技重点大学',
      tag: '双一流 / 985高校',
      period: '2022.09 — 2025.06 (硕士毕业)',
      department: '计算机学院 · 计算机科学与技术 (学术硕士)',
      gpa: '专业成绩：前 5%',
      honor: '荣誉：国家励志奖学金、校级一等学业奖学金'
    },
    {
      school: '北京邮电大学',
      tag: '教育部直属高校',
      period: '2018.09 — 2022.06 (学士)',
      department: '计算机与软件学院 · 软件工程 (工学学士)',
      gpa: '综合绩点：3.82 / 4.0',
      honor: '荣誉：北京市优秀毕业生'
    }
  ],
  skills: [
    'Python (熟练 / 核心研发语言)',
    'PyTorch & DeepSpeed',
    'C++ 17 (高并发与底层系统)',
    '大语言模型微调 (SFT / LoRA / RLHF)',
    'vLLM / TensorRT-LLM 加速推理',
    'Linux 内核优化 & Docker / K8s',
    '分布式通信 NCCL & Ray',
    'Gitflow / CI-CD Pipeline'
  ],
  experience: [
    {
      company: '智维未来人工智能研究院',
      role: '大模型算法实习生',
      period: '2024.06 — 至今 (北京)',
      highlights: [
        '主导基于 Llama-3 与 Qwen-2 百亿量级多模态模型的指令微调 (SFT) 数据工程构建，清洗高价值多轮对齐语料 450万+ 条。',
        '结合 DeepSpeed ZeRO-3 与 FlashAttention-2 优化集群分布式通信拓扑，显存占用降低 <strong class="text-on-surface font-semibold">28%</strong>，训练集群吞吐提升 <strong class="text-on-surface font-semibold">35%</strong>。',
        '引入 AWQ 4-bit 权重激活感知量化算法与 vLLM 连续批处理（Continuous Batching）推理框架，服务首字响应延迟降低至 45ms，并发能力提升 2.4 倍。'
      ]
    },
    {
      company: '字节跳动 (ByteDance)',
      role: '后端架构研发实习生',
      period: '2023.07 — 2023.11 (北京)',
      highlights: [
        '深度参与高并发信息流推荐服务重构，基于 Go + gRPC 微服务链路设计多级本地缓存架构 (LRU + Redis Cluster)。',
        '定位并治理网络 IO 阻塞热点，通过协程池精细化调度与内存逃逸优化，使得核心服务 P99 延迟由 120ms 压降至 42ms，保障千万级 QPS 稳定运行。',
        '主笔输出《微服务高可用熔断与兜底降级方案白皮书》，荣获实习生最佳工程技术分享奖。'
      ]
    }
  ],
  projects: [
    {
      name: '"智绘校园"基于大模型的智能问答协同助理 (国家级大学生创新创业计划)',
      period: '核心主导 · 2023.12 — 2024.05',
      description: '针对高校庞杂办事指南与校级公文检索痛点，设计多模态 RAG (检索增强生成) 检索流水线。构建基于 Milvus 向量库与 BGE-Reranker 的二次重排机制，准确率达 94.2%。服务目前已全校试运行，累计解答师生政策咨询超 20 万次。'
    },
    {
      name: 'ACM-ICPC 算法训练平台分布式沙箱评测引擎',
      period: '独立开发者 · 2022.10 — 2023.04',
      description: '使用 Linux cgroups 及 seccomp 构建微秒级安全沙箱隔离环境，支持 C++ / Java / Python 多语言判题与并发资源压制。系统开源至 GitHub 获得 1.2k+ Star，被全国多所高校集训队采用。'
    }
  ],
  certifications: [
    {
      icon: 'military_tech',
      iconColor: 'text-tertiary-container',
      name: 'ACM-ICPC 国际大学生程序设计竞赛',
      detail: '亚洲区域赛金牌 (2021)'
    },
    {
      icon: 'military_tech',
      iconColor: 'text-tertiary-container',
      name: '全国研究生数学建模竞赛',
      detail: '国家一等奖 (2023)'
    },
    {
      icon: 'translate',
      iconColor: 'text-secondary',
      name: '大学英语六级 (CET-6)',
      detail: '：612 分 (流利查阅外文前沿论文)'
    },
    {
      icon: 'verified',
      iconColor: 'text-secondary',
      name: '计算机技术与软件专业资格',
      detail: '：系统分析师 (高级)'
    }
  ]
})

function setDefaultResume(index) {
  const target = resumes.value[index]
  const current = resumes.value[0]
  const tempTag = current.tag
  current.tag = target.tag
  current.isDefault = false
  target.tag = tempTag
  target.isDefault = true
  resumes.value.splice(0, 1, target)
  resumes.value.splice(index, 1, current)
  activeResumeIndex.value = 0
}

function editResume(id) {
  router.push({ name: 'ResumeEdit', params: { id } })
}

function deleteResume(id) {
  const index = resumes.value.findIndex(r => r.id === id)
  if (index > -1) {
    resumes.value.splice(index, 1)
    if (activeResumeIndex.value >= resumes.value.length) {
      activeResumeIndex.value = Math.max(0, resumes.value.length - 1)
    }
  }
}

onMounted(() => {
  activeResumeIndex.value = 0
})
</script>
