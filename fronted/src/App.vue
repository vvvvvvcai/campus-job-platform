<template>
  <div class="min-h-screen bg-background text-on-background font-sans flex flex-col">
    <nav class="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-surface-container-high shadow-sm">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10">
        <div class="flex items-center justify-between h-16">
          <router-link to="/" class="flex items-center gap-2">
            <span class="material-symbols-outlined text-primary text-[28px]">school</span>
            <div class="hidden sm:block">
              <span class="text-base font-bold text-on-surface block leading-tight">校园就业综合服务平台</span>
              <span class="text-[10px] text-on-surface-variant">智慧校招与实习育人服务系统</span>
            </div>
          </router-link>
          <div v-if="store.isLoggedIn" class="hidden md:flex items-center gap-1">
            <template v-if="store.role === 'hr'">
              <router-link v-for="item in enterpriseNavItems" :key="item.path" :to="item.path"
                class="px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                :class="$route.path === item.path ? 'text-primary font-bold' : 'text-on-surface-variant hover:text-on-surface'">
                {{ item.label }}
                <span v-if="item.badge" class="ml-1 px-1.5 py-0.5 bg-primary/10 text-primary text-[10px] font-semibold rounded">{{ item.badge }}</span>
              </router-link>
            </template>
            <template v-else>
              <router-link v-for="item in navItems" :key="item.path" :to="item.path"
                class="px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                :class="$route.path === item.path ? 'text-primary font-bold' : 'text-on-surface-variant hover:text-on-surface'">
                {{ item.label }}
              </router-link>
            </template>
          </div>
          <div class="flex items-center gap-3">
            <template v-if="store.isLoggedIn">
              <!-- Bell Icon -->
              <button @click="notificationUnavailable" class="relative w-9 h-9 flex items-center justify-center rounded-lg hover:bg-surface-container-low transition-colors">
                <span class="material-symbols-outlined text-on-surface-variant text-[22px]">notifications</span>
              </button>
              <!-- User Avatar & Dropdown -->
              <div class="relative" ref="userDropdownRef">
                <button @click="showUserDropdown = !showUserDropdown" class="flex items-center gap-2.5 pl-1 pr-3 py-1 rounded-full hover:bg-surface-container-low transition-colors">
                  <span class="w-9 h-9 rounded-full bg-primary/10 flex items-center justify-center text-primary font-bold border-2 border-primary/20">
                    {{ displayName.charAt(0) }}
                  </span>
                  <div class="text-left hidden sm:block">
                    <div class="flex items-center gap-1.5">
                      <p class="text-sm font-semibold text-on-surface leading-tight">{{ displayName }}</p>
                      <span v-if="store.role === 'hr'" class="px-1.5 py-0.5 bg-primary/10 text-primary text-[9px] font-semibold rounded">企业端</span>
                      <span v-else class="px-1.5 py-0.5 bg-emerald-50 text-emerald-600 text-[9px] font-semibold rounded">学生端</span>
                    </div>
                    <p class="text-[11px] text-on-surface-variant">{{ store.role === 'hr' ? '企业HR账号' : '学生账号' }}</p>
                  </div>
                  <span class="material-symbols-outlined text-on-surface-variant text-[18px] transition-transform" :class="showUserDropdown ? 'rotate-180' : ''">expand_more</span>
                </button>
                <!-- Dropdown Menu -->
                <Transition name="dropdown">
                  <div v-if="showUserDropdown" class="absolute right-0 top-full mt-2 w-52 bg-surface-container-lowest rounded-xl shadow-xl border border-surface-container-high py-2 z-50">
                    <template v-if="store.role === 'hr'">
                      <router-link to="/enterprise/dashboard" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">dashboard</span>
                        企业工作台
                      </router-link>
                      <router-link to="/enterprise/jobs" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">work</span>
                        职位管理
                      </router-link>
                      <router-link to="/enterprise/post-job" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">add_circle</span>
                        发布职位
                      </router-link>
                      <router-link to="/enterprise/candidates" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">people</span>
                        简历投递
                      </router-link>
                      <router-link to="/enterprise/cert" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">verified</span>
                        企业认证
                      </router-link>
                      <router-link to="/change-password" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">settings</span>
                        账号设置
                      </router-link>
                      <div class="my-1.5 border-t border-surface-container"></div>
                      <button @click="switchToStudent" class="w-full flex items-center gap-3 px-4 py-2.5 text-sm text-primary hover:bg-primary/5 transition-colors">
                        <span class="material-symbols-outlined text-[20px]">swap_horiz</span>
                        切换为学生端
                      </button>
                    </template>
                    <template v-else>
                      <router-link to="/profile" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">person</span>
                        个人中心
                      </router-link>
                      <router-link to="/applications" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">send</span>
                        我的投递
                      </router-link>
                      <router-link to="/favorites" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">bookmark</span>
                        我的收藏
                      </router-link>
                      <router-link to="/resume/manage" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">description</span>
                        在线简历
                      </router-link>
                      <router-link to="/change-password" @click="showUserDropdown = false" class="flex items-center gap-3 px-4 py-2.5 text-sm text-on-surface hover:bg-surface-container-low transition-colors">
                        <span class="material-symbols-outlined text-[20px] text-on-surface-variant">settings</span>
                        账号设置
                      </router-link>
                      <div class="my-1.5 border-t border-surface-container"></div>
                      <button @click="switchToHR" class="w-full flex items-center gap-3 px-4 py-2.5 text-sm text-primary hover:bg-primary/5 transition-colors">
                        <span class="material-symbols-outlined text-[20px]">swap_horiz</span>
                        切换为企业端
                      </button>
                    </template>
                    <div class="my-1.5 border-t border-surface-container"></div>
                    <button @click="handleLogout" class="w-full flex items-center gap-3 px-4 py-2.5 text-sm text-error hover:bg-error-container/30 transition-colors">
                      <span class="material-symbols-outlined text-[20px]">logout</span>
                      退出登录
                    </button>
                  </div>
                </Transition>
              </div>
            </template>
            <template v-else>
              <button @click="showLoginModal = true" class="px-4 py-2 text-sm font-medium text-primary hover:bg-surface-container-low rounded-lg transition-colors">登录</button>
              <button @click="showRegisterModal = true" class="px-4 py-2 text-sm font-semibold text-on-primary bg-primary hover:bg-primary-container rounded-lg transition-colors shadow-sm">注册</button>
            </template>
          </div>
        </div>
      </div>
    </nav>
    <main class="flex-1">
      <router-view />
    </main>
    <footer class="bg-inverse-surface text-inverse-on-surface mt-auto">
      <div class="max-w-7xl mx-auto px-4 md:px-6 lg:px-10 py-10">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <div class="space-y-4">
            <div class="flex items-center gap-2">
              <span class="material-symbols-outlined text-inverse-primary text-[28px]">school</span>
              <span class="text-base font-bold">校园就业综合服务平台</span>
            </div>
            <p class="text-xs text-inverse-on-surface/80 leading-relaxed">服务高校大学生实习求职与校企直聘的数字化一站式就业枢纽平台。</p>
          </div>
          <div>
            <h4 class="text-base font-semibold mb-4">快速链接</h4>
            <ul class="space-y-2">
              <li class="text-xs"><a class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">关于我们</a></li>
              <li class="text-xs"><a class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">联系方式</a></li>
              <li class="text-xs"><a class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">隐私保护政策</a></li>
            </ul>
          </div>
          <div>
            <h4 class="text-base font-semibold mb-4">合作通道</h4>
            <ul class="space-y-2">
              <li class="text-xs"><a class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">高校就业中心入驻</a></li>
              <li class="text-xs"><a class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">名企专属招聘通告</a></li>
            </ul>
          </div>
          <div>
            <h4 class="text-base font-semibold mb-4">技术支持</h4>
            <ul class="space-y-2">
              <li class="text-xs"><a class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">帮助中心</a></li>
              <li class="text-xs"><a class="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">系统状态</a></li>
            </ul>
          </div>
        </div>
        <div class="border-t border-inverse-on-surface/20 mt-8 pt-6 flex flex-col sm:flex-row items-center justify-between gap-4">
          <span class="text-xs text-inverse-on-surface/60">© 2025 校园就业综合服务平台 · 保留所有权利</span>
        </div>
      </div>
    </footer>

    <!-- ==================== LOGIN MODAL ==================== -->
    <Teleport to="body">
      <Transition name="modal">
        <div v-if="showLoginModal" class="fixed inset-0 z-[100] flex items-center justify-center p-4" @click.self="closeLoginModal">
          <div class="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>
          <div class="relative w-full max-w-[960px] min-h-[580px] bg-surface-container-lowest rounded-2xl shadow-2xl overflow-hidden flex">
            <button @click="closeLoginModal" class="absolute top-4 right-4 z-20 w-8 h-8 rounded-full bg-surface-container-low/80 hover:bg-surface-container flex items-center justify-center transition-colors">
              <span class="material-symbols-outlined text-on-surface-variant text-[20px]">close</span>
            </button>

            <!-- Left Panel -->
            <div class="hidden lg:flex lg:w-5/12 relative overflow-hidden bg-gradient-to-br from-primary via-primary-container to-[#0a2472]">
              <div class="absolute inset-0 opacity-10 pointer-events-none">
                <svg class="w-full h-full" preserveAspectRatio="none" viewBox="0 0 100 100">
                  <defs><pattern id="lg" height="10" patternUnits="userSpaceOnUse" width="10"><path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" stroke-width="0.5"/></pattern></defs>
                  <rect fill="url(#lg)" height="100" width="100"/>
                </svg>
              </div>
              <div class="absolute -top-32 -left-32 w-96 h-96 rounded-full bg-primary-fixed/40 blur-3xl pointer-events-none"></div>
              <div class="absolute -bottom-40 -right-40 w-[28rem] h-[28rem] rounded-full bg-secondary-fixed/50 blur-3xl pointer-events-none"></div>
              <div class="relative z-10 flex flex-col justify-between p-10 text-on-primary w-full">
                <div class="space-y-6">
                  <div class="flex items-center gap-3">
                    <div class="w-11 h-11 rounded-lg bg-surface-container-lowest/15 backdrop-blur-md p-0.5 flex items-center justify-center shadow-sm">
                      <span class="material-symbols-outlined text-surface-bright text-[28px]">school</span>
                    </div>
                    <div>
                      <span class="text-sm text-surface-bright font-bold tracking-tight block">校园就业综合服务平台</span>
                      <span class="text-[10px] text-on-primary-container tracking-[0.2em] uppercase">Campus Career Nexus</span>
                    </div>
                  </div>
                  <div class="space-y-3">
                    <div class="inline-flex items-center gap-1 px-3 py-1 rounded-full bg-surface-container-lowest/20 backdrop-blur-sm">
                      <span class="w-2 h-2 rounded-full bg-secondary-container animate-pulse"></span>
                      <span class="text-xs text-surface-bright font-medium">2025届 春季名企直聘专场进行中</span>
                    </div>
                    <h1 class="text-[1.75rem] font-bold leading-tight">开启你的<br>职业梦想新征程</h1>
                    <p class="text-xs text-on-primary-container leading-relaxed">汇聚产学优质资源，打通从校园实习到高质量就职的全链路闭环通道。</p>
                  </div>
                  <div class="space-y-3 pt-1">
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                        <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">verified</span>
                      </div>
                      <div><h4 class="text-xs text-surface-bright font-semibold">12,000+ 知名企业直接触达</h4><p class="text-[11px] text-on-primary-container">央企国企、行业龙头、科研院所全覆盖，HR 直播与专场直收</p></div>
                    </div>
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                        <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">security</span>
                      </div>
                      <div><h4 class="text-xs text-surface-bright font-semibold">全国高校实名认证背书</h4><p class="text-[11px] text-on-primary-container">学籍真实联审，岗位资质与招聘安全性全流程双向核验保障</p></div>
                    </div>
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                        <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">psychology</span>
                      </div>
                      <div><h4 class="text-xs text-surface-bright font-semibold">AI 简历诊断与精准撮合</h4><p class="text-[11px] text-on-primary-container">人岗语义匹配评分，配套一对一导师生涯规划与模拟面试辅导</p></div>
                    </div>
                  </div>
                </div>
                <div class="p-4 rounded-lg bg-surface-container-lowest/10 backdrop-blur-md">
                  <div class="flex items-center justify-between">
                    <div class="flex items-center gap-2">
                      <span class="material-symbols-outlined text-tertiary-fixed-dim text-[20px]">workspace_premium</span>
                      <span class="text-xs text-surface-bright font-medium">985/211 重点高校专属推荐通告</span>
                    </div>
                    <span class="text-[10px] text-secondary-fixed-dim font-semibold">网签直通</span>
                  </div>
                  <p class="text-[11px] text-on-primary-container mt-1">支持"全国高校毕业生网上签约系统"无缝同步与档案转递</p>
                </div>
              </div>
            </div>

            <!-- Right Panel -->
            <div class="w-full lg:w-7/12 flex items-center justify-center p-6 sm:p-10 overflow-y-auto">
              <div class="w-full max-w-md">
                <div class="lg:hidden flex items-center gap-2 mb-6">
                  <span class="material-symbols-outlined text-primary text-3xl">school</span>
                  <span class="text-xl font-bold text-on-surface">校园就业平台</span>
                </div>

                <!-- Dynamic header based on login mode -->
                <div class="flex items-center justify-between pb-1">
                  <div>
                    <span class="text-xs text-primary font-semibold tracking-wider uppercase">{{ loginMode === 'student' ? 'Student / Candidate' : 'Enterprise / HR' }}</span>
                    <h2 class="text-2xl text-on-surface font-bold mt-1">欢迎登录</h2>
                    <p class="text-sm text-on-surface-variant mt-0.5">{{ loginMode === 'student' ? '进入系统查看最新校招动态、投递进度与面试安排' : '管理招聘需求、查看候选人简历与面试安排' }}</p>
                  </div>
                  <div class="hidden sm:flex flex-col items-end">
                    <div class="inline-flex items-center gap-1.5 px-3 py-1 rounded-full bg-surface-container text-xs text-on-surface font-medium">
                      <span class="w-2 h-2 rounded-full bg-primary"></span>
                      {{ loginMode === 'student' ? '学生通道' : '企业通道' }}
                    </div>
                    <button @click="loginMode = loginMode === 'student' ? 'hr' : 'student'" class="text-xs text-primary hover:underline mt-1">
                      {{ loginMode === 'student' ? '切换为企业 HR 登录 →' : '← 切换为学生登录' }}
                    </button>
                  </div>
                </div>

                <!-- Tabs -->
                <div class="flex bg-surface-container-low rounded-lg p-1 mt-5 mb-5">
                  <button @click="loginTab = 'password'" :class="['flex-1 py-2 rounded-md text-sm font-semibold transition-all', loginTab === 'password' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface']">密码快捷登录</button>
                  <button @click="loginTab = 'sms'" :class="['flex-1 py-2 rounded-md text-sm font-medium transition-all', loginTab === 'sms' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface']">手机验证码登录</button>
                </div>

                <!-- Password Login -->
                <form v-if="loginTab === 'password'" @submit.prevent="handleLogin" class="space-y-3.5">
                  <div class="space-y-1.5">
                    <label class="block text-xs text-on-surface font-medium">手机号码</label>
                    <div class="flex rounded-lg shadow-sm bg-surface-container-lowest">
                      <div class="relative shrink-0">
                        <select class="h-10 pl-3 pr-7 bg-surface-container-low text-on-surface text-sm rounded-l-lg appearance-none cursor-pointer focus:outline-none border-0"><option>+86 (中国大陆)</option></select>
                        <span class="material-symbols-outlined pointer-events-none absolute right-1.5 top-2.5 text-on-surface-variant text-[18px]">arrow_drop_down</span>
                      </div>
                      <input v-model="loginForm.phone" type="tel" maxlength="11" placeholder="请输入11位注册手机号" class="flex-1 h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-r-lg focus:outline-none border-0" />
                    </div>
                    <p v-if="loginErrors.phone" class="text-error text-xs">{{ loginErrors.phone }}</p>
                  </div>
                  <div class="space-y-1.5">
                    <div class="flex items-center justify-between">
                      <label class="block text-xs text-on-surface font-medium">登录密码</label>
                      <a href="#" class="text-xs text-primary hover:underline">忘记密码？</a>
                    </div>
                    <div class="relative">
                      <input v-model="loginForm.password" :type="showLoginPass ? 'text' : 'password'" placeholder="请输入登录密码（6-20位字符）" class="w-full h-10 px-3 pr-10 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border-0" />
                      <button type="button" @click="showLoginPass = !showLoginPass" class="absolute right-2.5 top-2.5 text-outline hover:text-on-surface transition-colors">
                        <span class="material-symbols-outlined text-[20px]">{{ showLoginPass ? 'visibility' : 'visibility_off' }}</span>
                      </button>
                    </div>
                    <p v-if="loginErrors.password" class="text-error text-xs">{{ loginErrors.password }}</p>
                  </div>
                  <div class="flex items-center justify-between pt-0.5">
                    <label class="flex items-center gap-2 cursor-pointer select-none">
                      <input v-model="loginForm.remember" type="checkbox" class="w-4 h-4 rounded accent-primary cursor-pointer" />
                      <span class="text-sm text-on-surface-variant">30天内记住登录状态</span>
                    </label>
                  </div>
                  <button type="submit" class="w-full h-11 mt-1 bg-gradient-to-r from-primary-container to-primary text-on-primary hover:opacity-95 text-sm font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2">
                    <span>立即登录</span>
                    <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
                  </button>
                  <div class="flex items-start gap-2 pt-0.5">
                    <input v-model="loginForm.agree" type="checkbox" class="mt-1 w-3.5 h-3.5 rounded accent-primary shrink-0 cursor-pointer" />
                    <label class="text-xs text-on-surface-variant leading-snug cursor-pointer">我已认真阅读并同意<a href="#" class="text-primary hover:underline">《用户服务协议》</a>、<a href="#" class="text-primary hover:underline">《个人信息保护与隐私政策》</a>及高校毕业生求职诚信承诺。</label>
                  </div>
                </form>

                <!-- SMS Login -->
                <form v-else @submit.prevent="handleSmsLogin" class="space-y-3.5">
                  <div class="space-y-1.5">
                    <label class="block text-xs text-on-surface font-medium">手机号码</label>
                    <div class="flex rounded-lg shadow-sm bg-surface-container-lowest">
                      <div class="relative shrink-0">
                        <select class="h-10 pl-3 pr-7 bg-surface-container-low text-on-surface text-sm rounded-l-lg appearance-none cursor-pointer focus:outline-none border-0"><option>+86 (中国大陆)</option></select>
                        <span class="material-symbols-outlined pointer-events-none absolute right-1.5 top-2.5 text-on-surface-variant text-[18px]">arrow_drop_down</span>
                      </div>
                      <input v-model="smsForm.phone" type="tel" maxlength="11" placeholder="请输入11位注册手机号" class="flex-1 h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-r-lg focus:outline-none border-0" />
                    </div>
                    <p v-if="smsErrors.phone" class="text-error text-xs">{{ smsErrors.phone }}</p>
                  </div>
                  <div class="space-y-1.5">
                    <label class="block text-xs text-on-surface font-medium">短信验证码</label>
                    <div class="flex gap-2">
                      <input v-model="smsForm.code" type="text" maxlength="6" placeholder="6位短信验证码" class="flex-1 h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border-0" />
                      <button type="button" @click="sendSmsCode" :disabled="smsCooldown > 0" class="px-4 h-10 bg-surface-container-low text-primary hover:bg-surface-container text-xs font-semibold rounded-lg transition-colors whitespace-nowrap" :class="smsCooldown > 0 ? 'opacity-50 cursor-not-allowed' : ''">
                        {{ smsCooldown > 0 ? `${smsCooldown}秒` : '获取验证码' }}
                      </button>
                    </div>
                    <p v-if="smsErrors.code" class="text-error text-xs">{{ smsErrors.code }}</p>
                  </div>
                  <div class="flex items-center pt-0.5">
                    <label class="flex items-center gap-2 cursor-pointer select-none">
                      <input v-model="smsForm.remember" type="checkbox" class="w-4 h-4 rounded accent-primary cursor-pointer" />
                      <span class="text-sm text-on-surface-variant">30天内记住登录状态</span>
                    </label>
                  </div>
                  <button type="submit" class="w-full h-11 mt-1 bg-gradient-to-r from-primary-container to-primary text-on-primary hover:opacity-95 text-sm font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2">
                    <span>立即登录</span>
                    <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
                  </button>
                  <div class="flex items-start gap-2 pt-0.5">
                    <input v-model="smsForm.agree" type="checkbox" class="mt-1 w-3.5 h-3.5 rounded accent-primary shrink-0 cursor-pointer" />
                    <label class="text-xs text-on-surface-variant leading-snug cursor-pointer">我已认真阅读并同意<a href="#" class="text-primary hover:underline">《用户服务协议》</a>、<a href="#" class="text-primary hover:underline">《个人信息保护与隐私政策》</a>及高校毕业生求职诚信承诺。</label>
                  </div>
                </form>

                <!-- Footer -->
                <div class="flex items-center justify-between text-center mt-5 text-sm text-on-surface-variant">
                  <span>还没有{{ loginMode === 'student' ? '学生' : '企业' }}账号？</span>
                  <div class="space-x-3">
                    <button @click="switchToRegister" class="text-primary font-semibold hover:underline">免费注册{{ loginMode === 'student' ? '学生' : '企业' }}账号</button>
                    <span class="text-outline-variant">|</span>
                    <button @click="switchToRegister" class="text-on-surface hover:text-primary font-medium">{{ loginMode === 'student' ? '用人单位注册' : '学生注册' }}</button>
                  </div>
                </div>
                <div class="pt-5 text-center text-xs text-outline">全国高校毕业生就业一体化指导服务体系认证支持</div>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </Teleport>

    <!-- ==================== REGISTER MODAL ==================== -->
    <Teleport to="body">
      <Transition name="modal">
        <div v-if="showRegisterModal" class="fixed inset-0 z-[100] flex items-center justify-center p-4" @click.self="closeRegisterModal">
          <div class="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>
          <div class="relative w-full max-w-[960px] min-h-[620px] bg-surface-container-lowest rounded-2xl shadow-2xl overflow-hidden flex">
            <button @click="closeRegisterModal" class="absolute top-4 right-4 z-20 w-8 h-8 rounded-full bg-surface-container-low/80 hover:bg-surface-container flex items-center justify-center transition-colors">
              <span class="material-symbols-outlined text-on-surface-variant text-[20px]">close</span>
            </button>

            <!-- Left Panel -->
            <div class="hidden lg:flex lg:w-5/12 relative overflow-hidden bg-gradient-to-br from-primary via-primary-container to-[#0a2472]">
              <div class="absolute inset-0 opacity-10 pointer-events-none">
                <svg class="w-full h-full" preserveAspectRatio="none" viewBox="0 0 100 100">
                  <defs><pattern id="rg" height="10" patternUnits="userSpaceOnUse" width="10"><path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" stroke-width="0.5"/></pattern></defs>
                  <rect fill="url(#rg)" height="100" width="100"/>
                </svg>
              </div>
              <div class="absolute -top-32 -left-32 w-96 h-96 rounded-full bg-primary-fixed/40 blur-3xl pointer-events-none"></div>
              <div class="absolute -bottom-40 -right-40 w-[28rem] h-[28rem] rounded-full bg-secondary-fixed/50 blur-3xl pointer-events-none"></div>
              <div class="relative z-10 flex flex-col justify-between p-10 text-on-primary w-full">
                <div class="space-y-6">
                  <div class="flex items-center gap-3">
                    <div class="w-11 h-11 rounded-lg bg-surface-container-lowest/15 backdrop-blur-md p-0.5 flex items-center justify-center shadow-sm">
                      <span class="material-symbols-outlined text-surface-bright text-[28px]">school</span>
                    </div>
                    <div>
                      <span class="text-sm text-surface-bright font-bold tracking-tight block">校园就业综合服务平台</span>
                      <span class="text-[10px] text-on-primary-container tracking-[0.2em] uppercase">Campus Employment System</span>
                    </div>
                  </div>
                  <div class="space-y-3">
                    <div class="inline-flex items-center gap-1 px-3 py-1 rounded-full bg-surface-container-lowest/20 backdrop-blur-sm">
                      <span class="w-2 h-2 rounded-full bg-secondary-container animate-pulse"></span>
                      <span class="text-xs text-surface-bright font-medium">2025秋招与春季实习专场</span>
                    </div>
                    <h1 class="text-[1.75rem] font-bold leading-tight">加入我们，<br>找到理想工作</h1>
                    <p class="text-xs text-on-primary-container leading-relaxed">汇聚全国名企高校资源，开启高质量实习就业通道，赋能青年人才职业跃升。</p>
                  </div>
                  <div class="space-y-3 pt-1">
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                        <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">bolt</span>
                      </div>
                      <div><h4 class="text-xs text-surface-bright font-semibold">快速入职通道</h4><p class="text-[11px] text-on-primary-container">海量世界500强与专精特新企业实习直通车，支持免笔试极速内推</p></div>
                    </div>
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                        <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">auto_awesome</span>
                      </div>
                      <div><h4 class="text-xs text-surface-bright font-semibold">精准AI人岗匹配</h4><p class="text-[11px] text-on-primary-container">多维解析学科背景与求职画像，实现岗位推荐精准度达94.2%</p></div>
                    </div>
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                        <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">gavel</span>
                      </div>
                      <div><h4 class="text-xs text-surface-bright font-semibold">权威签约合规保障</h4><p class="text-[11px] text-on-primary-container">直达高校就业中心，全面接入实名认证电子三方协议网签与法律援助</p></div>
                    </div>
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                        <span class="material-symbols-outlined text-secondary-fixed-dim text-[18px]">groups</span>
                      </div>
                      <div><h4 class="text-xs text-surface-bright font-semibold">双重身份协同生态</h4><p class="text-[11px] text-on-primary-container">支持应届高校学子极速投递与名企HR快捷入驻招聘，高效互联</p></div>
                    </div>
                  </div>
                </div>
                <div class="flex items-center justify-between text-on-primary/60 text-[11px] pt-4">
                  <div class="flex items-center gap-1"><span class="material-symbols-outlined text-[16px]">verified_user</span> 全国 1,280+ 高校认证</div>
                  <div class="flex items-center gap-1"><span class="material-symbols-outlined text-[16px]">work</span> 32,000+ 在招岗位</div>
                </div>
              </div>
            </div>

            <!-- Right Panel -->
            <div class="w-full lg:w-7/12 flex items-center justify-center p-6 sm:p-10 overflow-y-auto">
              <div class="w-full max-w-md">
                <div class="lg:hidden flex items-center gap-2 mb-6">
                  <span class="material-symbols-outlined text-primary text-3xl">school</span>
                  <span class="text-xl font-bold text-on-surface">校园就业平台</span>
                </div>

                <div class="pb-1">
                  <h2 class="text-2xl text-on-surface font-bold">创建您的专属账号</h2>
                  <p class="text-sm text-on-surface-variant mt-1">开启您的优质校园求职之旅，开启前沿生涯航程</p>
                </div>

                <!-- Role Toggle -->
                <div class="mt-5 mb-5">
                  <label class="block text-xs text-on-surface font-medium mb-2">请选择注册身份类型</label>
                  <div class="flex bg-surface-container-low rounded-lg p-1">
                    <button @click="regRole = 'student'" :class="['flex-1 py-2.5 rounded-md text-sm font-semibold transition-all flex items-center justify-center gap-1.5', regRole === 'student' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface']">
                      <span class="material-symbols-outlined text-[18px]">school</span> 我是高校学生（实习/校招）
                    </button>
                    <button @click="regRole = 'hr'" :class="['flex-1 py-2.5 rounded-md text-sm font-medium transition-all flex items-center justify-center gap-1.5', regRole === 'hr' ? 'bg-surface-container-lowest text-primary shadow-sm' : 'text-on-surface-variant hover:text-on-surface']">
                      <span class="material-symbols-outlined text-[18px]">business</span> 我是企业雇主（招聘人才）
                    </button>
                  </div>
                </div>

                <form @submit.prevent="handleRegister" class="space-y-3.5">
                  <!-- Name: dynamic based on role -->
                  <div class="space-y-1.5">
                    <div class="flex items-center justify-between">
                      <label class="block text-xs text-on-surface font-medium">{{ regRole === 'hr' ? '招聘联系人姓名' : '真实姓名' }}</label>
                      <span class="text-[11px] text-on-surface-variant">{{ regRole === 'hr' ? '需与企业授权书一致' : '需与学籍/身份证保持一致' }}</span>
                    </div>
                    <div class="relative">
                      <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-outline text-[20px]">badge</span>
                      <input v-model="regForm.name" type="text" :placeholder="regRole === 'hr' ? '请输入企业招聘联系人真实姓名' : '请输入您的真实姓名（如：林晨）'" class="w-full h-10 pl-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border border-surface-container-high focus:border-primary transition-colors" />
                    </div>
                    <p v-if="regErrors.name" class="text-error text-xs">{{ regErrors.name }}</p>
                  </div>

                  <!-- Phone -->
                  <div class="space-y-1.5">
                    <label class="block text-xs text-on-surface font-medium">手机号码</label>
                    <div class="flex rounded-lg shadow-sm bg-surface-container-lowest">
                      <div class="relative shrink-0">
                        <select class="h-10 pl-3 pr-7 bg-surface-container-low text-on-surface text-sm rounded-l-lg appearance-none cursor-pointer focus:outline-none border-0"><option>+86 中国</option></select>
                        <span class="material-symbols-outlined pointer-events-none absolute right-1.5 top-2.5 text-on-surface-variant text-[18px]">arrow_drop_down</span>
                      </div>
                      <input v-model="regForm.phone" type="tel" maxlength="11" placeholder="请输入11位手机号" class="flex-1 h-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-r-lg focus:outline-none border-0" />
                    </div>
                    <p v-if="regErrors.phone" class="text-error text-xs">{{ regErrors.phone }}</p>
                  </div>

                  <!-- Password -->
                  <div class="space-y-1.5">
                    <label class="block text-xs text-on-surface font-medium">设置登录密码</label>
                    <div class="relative">
                      <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-outline text-[20px]">lock</span>
                      <input v-model="regForm.password" :type="showRegPass ? 'text' : 'password'" placeholder="8-20位字符，包含字母与数字" class="w-full h-10 pl-10 pr-10 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border border-surface-container-high focus:border-primary transition-colors" />
                      <button type="button" @click="showRegPass = !showRegPass" class="absolute right-2.5 top-2.5 text-outline hover:text-on-surface transition-colors">
                        <span class="material-symbols-outlined text-[20px]">{{ showRegPass ? 'visibility' : 'visibility_off' }}</span>
                      </button>
                    </div>
                    <!-- Strength indicator -->
                    <div v-if="regForm.password" class="flex items-center gap-2 mt-1">
                      <div class="flex-1 flex gap-1">
                        <div v-for="i in 4" :key="i" class="h-1 flex-1 rounded-full transition-colors" :class="i <= regPassStrength ? regPassColor : 'bg-surface-container'"></div>
                      </div>
                      <span class="text-[11px]" :class="regPassLabel.color">{{ regPassLabel.text }}</span>
                    </div>
                    <p v-if="regErrors.password" class="text-error text-xs">{{ regErrors.password }}</p>
                  </div>

                  <!-- Confirm Password -->
                  <div class="space-y-1.5">
                    <label class="block text-xs text-on-surface font-medium">确认登录密码</label>
                    <div class="relative">
                      <span class="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-outline text-[20px]">lock_reset</span>
                      <input v-model="regForm.confirmPassword" type="password" placeholder="请再次输入上方设置的密码" class="w-full h-10 pl-10 px-3 bg-surface-container-lowest text-on-surface placeholder:text-outline text-sm rounded-lg shadow-sm focus:outline-none border border-surface-container-high focus:border-primary transition-colors" />
                    </div>
                    <p v-if="regForm.confirmPassword && regForm.password !== regForm.confirmPassword" class="text-error text-xs">两次输入的密码不一致</p>
                  </div>

                  <!-- Agreement -->
                  <div class="flex items-start gap-2 pt-0.5">
                    <input v-model="regForm.agree" type="checkbox" class="mt-1 w-3.5 h-3.5 rounded accent-primary shrink-0 cursor-pointer" />
                    <label class="text-xs text-on-surface-variant leading-snug cursor-pointer">我已认真阅读并完全同意<a href="#" class="text-primary hover:underline">《用户服务协议》</a>与<a href="#" class="text-primary hover:underline">《个人信息保护与隐私政策》</a>，并授权平台进行合法学籍或企业资质核验。</label>
                  </div>

                  <button type="submit" class="w-full h-11 bg-gradient-to-r from-primary-container to-primary text-on-primary hover:opacity-95 text-sm font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2">
                    <span>立即注册并开启体验</span>
                    <span class="material-symbols-outlined text-[18px]">arrow_forward</span>
                  </button>
                </form>

                <div class="flex items-center justify-center gap-1 text-sm text-on-surface-variant mt-5">
                  <span>已有平台账号？</span>
                  <button @click="switchToLogin" class="text-primary font-semibold hover:underline">立即登录 ›</button>
                </div>

                <div class="flex items-center justify-center gap-4 pt-4 text-[11px] text-on-surface-variant">
                  <div class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px] text-primary">verified_user</span> 256-bit 银行级数据加密</div>
                  <div class="flex items-center gap-1"><span class="material-symbols-outlined text-[14px] text-primary">school</span> 高校实名认证背书</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </Transition>
    </Teleport>

    <!-- Toast -->
    <Teleport to="body">
      <Transition name="toast">
        <div v-if="toast.show" :class="[
          'fixed top-6 right-6 z-[110] flex items-center gap-3 px-5 py-3 rounded-xl shadow-lg text-sm font-medium',
          toast.type === 'success' ? 'bg-emerald-50 text-emerald-700 border border-emerald-200' : 'bg-red-50 text-red-700 border border-red-200'
        ]">
          <span class="material-symbols-outlined text-xl">{{ toast.type === 'success' ? 'check_circle' : 'error' }}</span>
          {{ toast.message }}
        </div>
      </Transition>
    </Teleport>
  </div>
</template>

<script setup>
import { reactive, ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from './stores/app'
import { login as apiLogin, register as apiRegister } from './api/user'

const router = useRouter()
const store = useAppStore()

const showUserDropdown = ref(false)
const userDropdownRef = ref(null)

function handleClickOutside(e) {
  if (userDropdownRef.value && !userDropdownRef.value.contains(e.target)) {
    showUserDropdown.value = false
  }
}

onMounted(() => {
  store.restoreLogin()
  window.addEventListener('open-login-modal', openLogin)
  document.addEventListener('click', handleClickOutside)
})
onUnmounted(() => {
  window.removeEventListener('open-login-modal', openLogin)
  document.removeEventListener('click', handleClickOutside)
})

const navItems = [
  { path: '/', label: '首页' },
  { path: '/jobs', label: '职位搜索' },
  { path: '/favorites', label: '我的收藏' },
]

const enterpriseNavItems = [
  { path: '/enterprise/dashboard', label: '企业工作台' },
  { path: '/enterprise/jobs', label: '职位管理' },
  { path: '/enterprise/post-job', label: '发布职位' },
  { path: '/enterprise/candidates', label: '简历投递' },
  { path: '/enterprise/cert', label: '企业认证' },
]

const displayName = computed(() => (store.user && store.user.name) || '用户')

function notificationUnavailable() {
  showToast('error', '消息中心接口暂未开放')
}

function handleLogout() { store.logout(); router.push('/') }

function switchToHR() {
  showUserDropdown.value = false
  store.logout()
  loginMode.value = 'hr'
  showLoginModal.value = true
}

function switchToStudent() {
  showUserDropdown.value = false
  store.logout()
  loginMode.value = 'student'
  showLoginModal.value = true
}

// ─── Modal State ───
const showLoginModal = ref(false)
const showRegisterModal = ref(false)
function openLogin() { showRegisterModal.value = false; showLoginModal.value = true }
function switchToRegister() { showLoginModal.value = false; showRegisterModal.value = true }
function switchToLogin() { showRegisterModal.value = false; showLoginModal.value = true }
function closeLoginModal() { showLoginModal.value = false }
function closeRegisterModal() { showRegisterModal.value = false }

// ─── Toast ───
const toast = reactive({ show: false, type: 'success', message: '' })
function showToast(type, message) { toast.show = true; toast.type = type; toast.message = message; setTimeout(() => { toast.show = false }, 3000) }

// ─── Login ───
const loginMode = ref('student')
const loginTab = ref('password')
const showLoginPass = ref(false)
const smsCooldown = ref(0)
const loginForm = reactive({ phone: '', password: '', remember: true, agree: false })
const loginErrors = reactive({ phone: '', password: '' })
const smsForm = reactive({ phone: '', code: '', remember: true, agree: false })
const smsErrors = reactive({ phone: '', code: '' })

function validatePhone(phone) {
  if (!phone) return '请输入手机号'
  if (!/^1[3-9]\d{9}$/.test(phone)) return '手机号格式不正确'
  return ''
}

async function handleLogin() {
  loginErrors.phone = validatePhone(loginForm.phone)
  loginErrors.password = !loginForm.password ? '请输入密码' : loginForm.password.length < 6 ? '密码至少6位' : ''
  if (loginErrors.phone || loginErrors.password) return
  if (!loginForm.agree) { showToast('error', '请先阅读并同意用户协议与隐私政策'); return }
  try {
    const res = await apiLogin({ phone: loginForm.phone, password: loginForm.password })
    const userRole = res.role === 1 ? 'hr' : 'student'
    store.login({ phone: loginForm.phone, name: res.username, avatar: res.avatar }, userRole, res.token, res.userId)
    showLoginModal.value = false
    showToast('success', '登录成功')
    if (userRole === 'hr') {
      router.push('/enterprise/dashboard')
    } else {
      const redirect = router.currentRoute.value.query.redirect
      if (redirect) router.push(redirect)
    }
  } catch (e) {
    console.error('登录失败:', e)
    showToast('error', e.message || '登录失败，请检查手机号和密码')
  }
}

function handleSmsLogin() {
  smsErrors.phone = validatePhone(smsForm.phone)
  smsErrors.code = !smsForm.code ? '请输入验证码' : smsForm.code.length < 6 ? '验证码为6位' : ''
  if (smsErrors.phone || smsErrors.code) return
  if (!smsForm.agree) { showToast('error', '请先阅读并同意用户协议与隐私政策'); return }
  showToast('error', '后端暂未实现短信登录，请使用密码登录')
}

function sendSmsCode() {
  const phoneError = validatePhone(smsForm.phone)
  if (phoneError) { smsErrors.phone = phoneError; return }
  smsErrors.phone = ''
  showToast('error', '后端暂未实现短信发送功能')
}

// ─── Register ───
const regRole = ref('student')
const showRegPass = ref(false)
const regForm = reactive({ name: '', phone: '', password: '', confirmPassword: '', agree: false })
const regErrors = reactive({ name: '', phone: '', password: '' })

const regPassStrength = computed(() => {
  const p = regForm.password
  if (!p) return 0
  let s = 0
  if (p.length >= 8 && p.length <= 20) s++
  if (/[a-zA-Z]/.test(p)) s++
  if (/[0-9]/.test(p)) s++
  if (/[^a-zA-Z0-9]/.test(p)) s++
  return s
})

const regPassLabel = computed(() => {
  const s = regPassStrength.value
  if (s <= 1) return { text: '弱', color: 'text-error' }
  if (s === 2) return { text: '一般', color: 'text-tertiary-container' }
  if (s === 3) return { text: '良好', color: 'text-secondary' }
  return { text: '强', color: 'text-primary' }
})

const regPassColor = computed(() => {
  const s = regPassStrength.value
  if (s <= 1) return 'bg-error'
  if (s === 2) return 'bg-tertiary-container'
  if (s === 3) return 'bg-secondary'
  return 'bg-primary'
})

async function handleRegister() {
  regErrors.name = !regForm.name ? '请输入真实姓名' : ''
  regErrors.phone = validatePhone(regForm.phone)
  regErrors.password = !regForm.password ? '请输入密码' : regForm.password.length < 8 ? '密码至少8位' : ''
  if (regErrors.name || regErrors.phone || regErrors.password) return
  if (regForm.password !== regForm.confirmPassword) { showToast('error', '两次输入的密码不一致'); return }
  if (!regForm.agree) { showToast('error', '请先阅读并同意用户协议与隐私政策'); return }
  try {
    const roleNum = regRole.value === 'hr' ? 1 : 0
    await apiRegister({
      phone: regForm.phone,
      password: regForm.password,
      username: regForm.name,
      role: roleNum
    })
    showRegisterModal.value = false
    showToast('success', '注册成功，请登录')
    showLoginModal.value = true
  } catch (e) {
    console.error('注册失败:', e)
    showToast('error', e.message || '注册失败，请稍后重试')
  }
}

</script>

<style scoped>
.modal-enter-active, .modal-leave-active { transition: all 0.3s ease; }
.modal-enter-from, .modal-leave-to { opacity: 0; }
.toast-enter-active, .toast-leave-active { transition: all 0.3s ease; }
.toast-enter-from, .toast-leave-to { opacity: 0; transform: translateY(-12px); }
.dropdown-enter-active, .dropdown-leave-active { transition: all 0.15s ease; }
.dropdown-enter-from, .dropdown-leave-to { opacity: 0; transform: translateY(-6px) scale(0.97); }
</style>
