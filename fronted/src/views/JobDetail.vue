<template>
  <div class="min-h-screen bg-background">
    <!-- Hero Card -->
    <section class="max-w-6xl mx-auto px-6 pt-6">
      <div class="bg-gradient-to-br from-surface-container-lowest via-surface-container-lowest to-primary/5 rounded-3xl border border-surface-container-high p-6 lg:p-8">
        <div class="flex gap-8">
          <!-- Left: Job Info -->
          <div class="flex-1 min-w-0 flex flex-col justify-between">
            <!-- Top Tags -->
            <div class="flex flex-wrap items-center gap-2 mb-4">
              <span class="px-3 py-1 rounded-full text-xs font-semibold bg-primary text-on-primary">2025届校招</span>
              <span class="px-3 py-1 rounded-full text-xs font-medium border border-primary/30 text-primary">可转正实习</span>
              <span class="px-3 py-1 rounded-full text-xs font-medium border border-surface-container-high text-on-surface-variant flex items-center gap-1">
                <span class="material-symbols-outlined text-sm">workspace_premium</span>
                高校直聘示范岗
              </span>
            </div>

            <!-- Title -->
            <h1 class="text-2xl lg:text-[28px] font-bold text-on-surface mb-4 leading-tight">{{ job.title }}</h1>

            <!-- Salary -->
            <div class="flex items-center gap-3 mb-4">
              <span class="text-3xl font-extrabold text-primary leading-none">{{ job.salary }}</span>
              <span class="text-sm text-on-surface-variant self-end mb-0.5">· {{ job.salaryPeriod }}</span>
              <span v-if="job.internSalary" class="ml-1 px-3 py-1.5 border border-primary/40 text-primary rounded-full text-xs font-medium self-end mb-0.5">
                或{{ job.internSalary }}
              </span>
            </div>

            <!-- Info Row -->
            <div class="flex flex-wrap items-center gap-x-4 gap-y-2 text-sm text-on-surface-variant mb-2">
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">location_on</span>
                {{ job.location }}
              </span>
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">school</span>
                {{ job.education }}
              </span>
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">group</span>
                招聘 {{ job.headcount }} 人
              </span>
              <span class="flex items-center gap-1.5">
                <span class="material-symbols-outlined text-base">schedule</span>
                {{ job.type }}
              </span>
            </div>

            <!-- Audience -->
            <div class="flex items-center gap-1.5 text-sm text-on-surface-variant mb-5">
              <span class="material-symbols-outlined text-base">event</span>
              {{ job.audience }}
            </div>

            <!-- Badge Pills -->
            <div class="flex flex-wrap gap-2">
              <span v-for="badge in job.badges" :key="badge.text"
                class="px-3 py-1.5 rounded-full text-xs font-medium flex items-center gap-1.5 border"
                :class="badge.class">
                <span class="material-symbols-outlined text-sm">{{ badge.icon }}</span>
                {{ badge.text }}
              </span>
            </div>
          </div>

          <!-- Right: HR + Match Card -->
          <div class="w-72 shrink-0 hidden lg:flex flex-col gap-4">
            <!-- HR Card -->
            <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
              <div class="flex items-center gap-3 mb-3">
                <div class="w-11 h-11 rounded-full bg-gradient-to-br from-primary to-primary-container flex items-center justify-center text-on-primary font-bold text-sm">
                  张
                </div>
                <div>
                  <div class="flex items-center gap-1.5">
                    <span class="text-sm font-bold text-on-surface">张经理</span>
                    <span class="material-symbols-outlined text-sm text-primary">verified</span>
                  </div>
                  <p class="text-xs text-on-surface-variant">校招技术招聘负责人</p>
                </div>
              </div>
              <p class="text-xs text-on-surface-variant mb-4 flex items-center gap-1.5">
                <span class="w-2 h-2 rounded-full bg-green-500"></span>
                今日活跃 · 平均 24 小时内极速初筛
              </p>
              <button class="w-full py-2.5 bg-primary text-on-primary rounded-xl text-sm font-semibold hover:bg-primary/90 transition-colors flex items-center justify-center gap-2 mb-2">
                <span class="material-symbols-outlined text-base">send</span>
                立即投递简历
              </button>
              <button class="w-full py-2.5 border border-primary text-primary rounded-xl text-sm font-semibold hover:bg-primary/5 transition-colors flex items-center justify-center gap-2 mb-3">
                <span class="material-symbols-outlined text-base">chat</span>
                与HR直聊
              </button>
              <button @click="isFavorited = !isFavorited"
                class="w-full py-1.5 text-on-surface-variant text-xs flex items-center justify-center gap-1.5 hover:text-primary transition-colors">
                <span class="material-symbols-outlined text-sm">{{ isFavorited ? 'bookmark' : 'bookmark_border' }}</span>
                {{ isFavorited ? '已收藏' : '收藏职位' }}
              </button>
            </div>

            <!-- Match Score Card -->
            <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
              <div class="flex items-center gap-2 mb-2">
                <span class="material-symbols-outlined text-base text-primary">auto_awesome</span>
                <span class="text-sm font-bold text-on-surface">林晨的档案与职位匹配度</span>
              </div>
              <div class="flex items-end gap-2 mb-1">
                <span class="text-3xl font-extrabold text-primary leading-none">96%</span>
                <span class="text-xs text-on-surface-variant mb-0.5">投高匹配</span>
              </div>
              <p class="text-xs text-on-surface-variant">专业与AI算法项目背景高度契合</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <div class="max-w-6xl mx-auto px-6 py-6">
      <div class="flex gap-6">
        <!-- Left Content -->
        <div class="flex-1 space-y-6">
          <!-- 工作内容 / 岗位职责 -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6">
            <div class="flex items-center justify-between mb-4">
              <h2 class="text-lg font-bold text-on-surface flex items-center gap-2">
                <span class="w-1 h-5 bg-primary rounded-full"></span>
                工作内容 / 岗位职责
              </h2>
              <span class="px-2.5 py-1 bg-primary/10 text-primary text-xs font-medium rounded-full">核心研发部门直招</span>
            </div>
            <p class="text-sm text-on-surface-variant leading-relaxed mb-5">{{ job.intro }}</p>

            <div class="space-y-4">
              <div v-for="(item, index) in responsibilities" :key="index" class="flex gap-4">
                <div class="w-7 h-7 rounded-full bg-primary flex items-center justify-center text-on-primary text-xs font-bold shrink-0 mt-0.5">
                  {{ String(index + 1).padStart(2, '0') }}
                </div>
                <div>
                  <h4 class="text-sm font-bold text-on-surface mb-1">{{ item.title }}</h4>
                  <p class="text-sm text-on-surface-variant leading-relaxed">{{ item.desc }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- 任职要求 -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6">
            <div class="flex items-center justify-between mb-4">
              <h2 class="text-lg font-bold text-on-surface flex items-center gap-2">
                <span class="w-1 h-5 bg-primary rounded-full"></span>
                任职要求
              </h2>
              <span class="text-xs text-primary font-medium flex items-center gap-1 cursor-pointer">
                <span class="material-symbols-outlined text-sm">verified</span>
                学历要求可在院校辅验
              </span>
            </div>

            <div class="space-y-4 mb-5">
              <div v-for="(req, index) in requirements" :key="index" class="flex gap-3">
                <span class="material-symbols-outlined text-primary text-lg mt-0.5">check_circle</span>
                <p class="text-sm text-on-surface-variant leading-relaxed">
                  <span class="font-semibold text-on-surface">{{ req.label }}：</span>{{ req.desc }}
                </p>
              </div>
            </div>

            <div class="flex flex-wrap gap-2">
              <span v-for="tag in techTags" :key="tag"
                class="px-3 py-1 bg-surface-container-low border border-surface-container-high rounded-lg text-xs text-on-surface-variant">
                {{ tag }}
              </span>
            </div>
          </div>

          <!-- 福利待遇与培养通道 -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-6">
            <div class="flex items-center justify-between mb-5">
              <h2 class="text-lg font-bold text-on-surface flex items-center gap-2">
                <span class="w-1 h-5 bg-primary rounded-full"></span>
                福利待遇与培养通道
              </h2>
              <span class="px-2.5 py-1 bg-secondary-container text-on-secondary-container text-xs font-medium rounded-full">高校毕业生特别增益计划</span>
            </div>

            <div class="grid grid-cols-2 gap-4">
              <div v-for="(benefit, index) in benefits" :key="index"
                class="p-4 bg-surface-container-low rounded-xl border border-surface-container-high hover:border-primary/30 transition-colors">
                <div class="flex items-center gap-2.5 mb-2.5">
                  <div class="w-8 h-8 rounded-lg bg-primary/10 flex items-center justify-center">
                    <span class="material-symbols-outlined text-primary text-lg">{{ benefit.icon }}</span>
                  </div>
                  <h4 class="text-sm font-bold text-on-surface">{{ benefit.title }}</h4>
                </div>
                <p class="text-xs text-on-surface-variant leading-relaxed mb-3">{{ benefit.desc }}</p>
                <button class="text-xs text-primary font-medium flex items-center gap-1 hover:underline">
                  {{ benefit.link }}
                  <span class="material-symbols-outlined text-sm">arrow_forward</span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Sidebar -->
        <div class="w-72 shrink-0 hidden lg:block space-y-4">
          <!-- Company Card -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
            <div class="flex items-center gap-3 mb-4">
              <div class="w-12 h-12 rounded-xl bg-gradient-to-br from-primary to-primary-container flex items-center justify-center text-on-primary font-bold text-lg">
                智
              </div>
              <div>
                <div class="flex items-center gap-1.5">
                  <span class="text-sm font-bold text-on-surface">智航未来科技</span>
                  <span class="material-symbols-outlined text-sm text-primary">verified</span>
                </div>
                <p class="text-xs text-on-surface-variant">已通过教育部校企合作认证</p>
              </div>
            </div>

            <div class="grid grid-cols-2 gap-3 mb-4">
              <div class="bg-surface-container-low rounded-lg p-3 text-center">
                <p class="text-lg font-bold text-primary">500-999人</p>
                <p class="text-[10px] text-on-surface-variant">人员规模</p>
              </div>
              <div class="bg-surface-container-low rounded-lg p-3 text-center">
                <p class="text-lg font-bold text-primary">C轮·独角兽</p>
                <p class="text-[10px] text-on-surface-variant">融资阶段</p>
              </div>
            </div>

            <div class="space-y-2.5 mb-4">
              <div class="flex items-start gap-2 text-xs text-on-surface-variant">
                <span class="material-symbols-outlined text-sm mt-0.5">apartment</span>
                <span>所展行业：人工智能 / 大模型与生成式AI</span>
              </div>
              <div class="flex items-start gap-2 text-xs text-on-surface-variant">
                <span class="material-symbols-outlined text-sm mt-0.5">emoji_events</span>
                <span>资质荣誉：国家高新技术企业、专精特新"小巨人"</span>
              </div>
              <div class="flex items-start gap-2 text-xs text-on-surface-variant">
                <span class="material-symbols-outlined text-sm mt-0.5">location_on</span>
                <span>北京市海淀区中关村东路1号院清华科技园科技大厦</span>
              </div>
            </div>

            <button class="w-full py-2.5 bg-primary text-on-primary rounded-xl text-sm font-semibold hover:bg-primary/90 transition-colors flex items-center justify-center gap-2">
              查看企业主页与全部在招岗位职位
              <span class="px-1.5 py-0.5 bg-on-primary/20 rounded text-[10px] font-bold">18</span>
            </button>
          </div>

          <!-- Similar Jobs -->
          <div class="bg-surface-container-lowest rounded-2xl border border-surface-container-high p-5">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-sm font-bold text-on-surface">相似优质职位推荐</h3>
              <button class="text-xs text-primary font-medium hover:underline">换一批</button>
            </div>
            <div class="space-y-3">
              <div v-for="job in similarJobs" :key="job.id" @click="goToJob(job.id)"
                class="block py-3 border-b border-surface-container-high last:border-0 hover:bg-surface-container-low -mx-2 px-2 rounded-lg transition-colors cursor-pointer">
                <p class="text-xs font-medium text-on-surface">{{ job.title }}</p>
                <p class="text-[10px] text-on-surface-variant mt-0.5">{{ job.company }}</p>
                <div class="flex items-center justify-between mt-1.5">
                  <span class="text-[10px] text-primary font-medium">{{ job.salary }}</span>
                  <span class="text-[10px] text-on-surface-variant">{{ job.location }}</span>
                </div>
                <div class="flex items-center gap-1 mt-2 text-[10px] text-primary font-medium">
                  查看原招聘职位
                  <span class="material-symbols-outlined text-xs">open_in_new</span>
                </div>
              </div>
            </div>
            <button class="w-full mt-3 py-2 text-xs text-on-surface-variant hover:text-primary transition-colors text-center flex items-center justify-center gap-1">
              探索更多人工智能算法职位
              <span class="material-symbols-outlined text-sm">arrow_forward</span>
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile Sticky Bottom Bar -->
    <div class="lg:hidden fixed bottom-0 left-0 right-0 bg-surface-container-lowest border-t border-surface-container-high px-6 py-3 flex items-center gap-3 z-40">
      <button @click="isFavorited = !isFavorited"
        :class="['w-12 h-12 rounded-xl border flex items-center justify-center shrink-0 transition-colors',
          isFavorited ? 'border-red-200 bg-red-50 text-red-500' : 'border-surface-container-high text-on-surface-variant']">
        <span class="material-symbols-outlined">{{ isFavorited ? 'favorite' : 'favorite_border' }}</span>
      </button>
      <button @click="showApplyModal = true" class="flex-1 py-3 bg-primary text-on-primary rounded-xl font-medium text-sm hover:bg-primary/90 transition-colors">
        立即投递
      </button>
    </div>

    <!-- Apply Modal -->
    <Teleport to="body">
      <Transition name="modal">
        <div v-if="showApplyModal" class="fixed inset-0 z-50 flex items-center justify-center p-6" @click.self="showApplyModal = false">
          <div class="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>
          <div class="relative bg-surface-container-lowest rounded-2xl shadow-xl w-full max-w-md p-6">
            <div class="flex items-center justify-between mb-5">
              <h3 class="font-bold text-on-surface">确认投递</h3>
              <button @click="showApplyModal = false" class="text-on-surface-variant hover:text-on-surface">
                <span class="material-symbols-outlined">close</span>
              </button>
            </div>
            <div class="bg-surface-container-low rounded-xl p-4 mb-5">
              <p class="text-sm font-medium text-on-surface">大模型算法工程师</p>
              <p class="text-xs text-on-surface-variant mt-0.5">智航未来科技 · 北京</p>
            </div>
            <div class="space-y-3 mb-5">
              <div class="flex items-center gap-3">
                <span class="material-symbols-outlined text-primary text-lg">description</span>
                <div class="flex-1">
                  <p class="text-sm text-on-surface">使用简历：<strong>林晨的在线简历</strong></p>
                </div>
              </div>
              <div class="flex items-center gap-3">
                <span class="material-symbols-outlined text-primary text-lg">mail</span>
                <div class="flex-1">
                  <p class="text-sm text-on-surface">联系电话：<strong>138****6789</strong></p>
                </div>
              </div>
            </div>
            <div class="flex gap-3">
              <button @click="showApplyModal = false" class="flex-1 py-3 border border-surface-container-high rounded-xl text-sm font-medium text-on-surface-variant hover:bg-surface-container-low transition-colors">
                取消
              </button>
              <button @click="handleApply" :disabled="applying"
                class="flex-1 py-3 bg-primary text-on-primary rounded-xl text-sm font-medium hover:bg-primary/90 transition-colors disabled:opacity-50">
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
import { useRouter } from 'vue-router'

const router = useRouter()
const isFavorited = ref(false)
const showApplyModal = ref(false)
const applying = ref(false)

const job = {
  title: '大模型算法工程师（2025届校招/可转正实习）',
  salary: '25k - 40k',
  salaryPeriod: '16薪',
  internSalary: '600-800元/天（实习期）',
  location: '北京·中关村清华科技园',
  education: '硕士及以上',
  headcount: 3,
  type: '全职 / 实习均可',
  audience: '2025届应届 / 2026届实习',
  badges: [
    { text: '校企联合育人基地', icon: 'emoji_events', class: 'border-surface-container-high text-on-surface-variant bg-surface-container-low/50' },
    { text: '重点高校直聘通道', icon: 'diamond', class: 'border-surface-container-high text-on-surface-variant bg-surface-container-low/50' },
    { text: '实习转正率 92%', icon: 'trending_up', class: 'border-surface-container-high text-on-surface-variant bg-surface-container-low/50' },
    { text: '支持网签三方协议', icon: 'description', class: 'border-surface-container-high text-on-surface-variant bg-surface-container-low/50' }
  ],
  intro: '你将加入智航未来基础大模型实验室（LLM Research Lab），同顶级科学家和工程架构师共事，主导从基座模型训练室至千亿级参数多模态部署落地全链。具体职责涵盖：'
}

const responsibilities = [
  { title: '千亿级大模型预训练与优化', desc: '深入参与千亿级多模态大语言模型（LLM）的预训练数据飞轮构建、高质量合成数据清洗、指令微调（SFT）与基于人类反馈的强化学习（RLHF / DPO）算法迭代。' },
  { title: '垂直行业 Agent 与 RAG 体系构建', desc: '负责各类业务场景落地下的多 Agent 工作流编排、工具调用推理（Tool Use / Function Calling）以及基于语义向量的高精度 RAG 知识库检索引擎技术攻坚。' },
  { title: '前沿学术突破与论文发表', desc: '持续追踪国际前沿学术动态（涵盖长上下文建模、思维链 CoT、多模态对齐等），协助发表顶级人工智能学术会议论文（NeurIPS、ICML、ICLR、CVPR、ACL 等）并申请核心技术专利。' }
]

const requirements = [
  { label: '学历与专业背景', desc: '2025届应届毕业生或2026届在读研究生，计算机科学、人工智能、自动化、应用数学、软件工程或电子信得等相关专业硕士或博士学历。' },
  { label: '技术栈掌握深度', desc: '熟练掌握 Python 与现代 C++ 语言，对数据结构与算法设计有扎实体会；深入理解 PyTorch 框架，熟悉 Megatron-LM、DeepSpeed、vLLM 或 TensorRT-LLM 等主流大模型分布式训练与推理优化框架。' },
  { label: '竞赛与顶会优先（加分项）', desc: '在 ACM-ICPC、CCPC、Kaggle、天池或国内外知名顶级算法竞赛中取得过优异成绩的候选人优先；有以第一作者身份在 NeurIPS、ICML、CVPR、ACL 等顶级会议发表过学术论文者优先。' },
  { label: '自我驱动与协同', desc: '对模型与通用人工智能（AGI）保有强烈的好奇心与技术激情，具备纯熟的英文文献检索能力与科技论文快速阅读写作能力；有严谨的代码工程习惯与跨学科协同作战素养。' }
]

const techTags = ['Python', 'PyTorch', 'DeepSpeed', 'Megatron-LM', 'RLHF / PPO', 'RAG 检索增强', 'Agent 开发']

const benefits = [
  { icon: 'account_balance_wallet', title: '丰厚薪资与安居支持', desc: '年度目标奖金（3-6 个月），高额六险二金（公积金全额顶格缴纳）；正式员工入职即可享受最高购房 50 万元免息安居购房借款计划。', link: '六险二金标准明细' },
  { icon: 'school', title: '一对一顶级专家导师', desc: '应届生与实习生全员配备 Staff / Principal Engineer 级别技术专家，全程定制成长路径，从工程落地到学术论文全链路带教。', link: '专属智航成长路径' },
  { icon: 'apartment', title: '生活全方位关怀', desc: '北京户口择业重点申报指标；提供青年专属品质公寓或每月 2,500 元租房补贴，三餐自助免费， envelope 下午茶、团建假期与家属关怀体检。', link: '应届新户特有政策详情' },
  { icon: 'handshake', title: '转正保障与三方签订', desc: '实习期满考核合格直接转正，历史转正率高达 92%；通过全国高校就业平台直接网签《全国普通高等学校毕业生就业协议书》，毕业无级入职。', link: '快速办理三方' }
]

const similarJobs = [
  { id: 101, title: '具身智能算法工程师（实习）', company: '影元机器人', salary: '20k-35k', location: '北京 / 上海·硕士' },
  { id: 102, title: 'AIGC多模态研发实习生', company: '讯星智研研发中心', salary: '400-600元/天', location: '北京·本科起投' },
  { id: 103, title: '深度学习平台开发工程师', company: '华域中央研究院', salary: '22k-38k', location: '北京·2025届' }
]

function handleApply() {
  applying.value = true
  setTimeout(() => {
    applying.value = false
    showApplyModal.value = false
  }, 1500)
}

function goToJob(id) {
  router.push({ name: 'JobDetail', params: { id } })
}
</script>

<style scoped>
.modal-enter-active, .modal-leave-active { transition: all 0.3s ease; }
.modal-enter-from, .modal-leave-to { opacity: 0; }
.modal-enter-active .relative, .modal-leave-active .relative { transition: transform 0.3s ease; }
.modal-enter-from .relative { transform: scale(0.95); }
.modal-leave-to .relative { transform: scale(0.95); }
</style>
