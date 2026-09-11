import { useState, useCallback } from 'react';
import logger from '../utils/logger';

const LoginPage = () => {
  const [activeTab, setActiveTab] = useState('pwd');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [password, setPassword] = useState('');
  const [smsCode, setSmsCode] = useState('');
  const [countryCode, setCountryCode] = useState('+86');
  const [rememberMe, setRememberMe] = useState(true);
  const [termsAccepted, setTermsAccepted] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [toast, setToast] = useState({ visible: false, message: '', isError: false });
  const [countdown, setCountdown] = useState(0);

  const showToast = useCallback((message, isError = false) => {
    logger.info(`Toast: ${message}`, { isError });
    setToast({ visible: true, message, isError });
    setTimeout(() => {
      setToast({ visible: false, message: '', isError: false });
    }, 3000);
  }, []);

  const handleSendCode = useCallback(() => {
    if (!phoneNumber || phoneNumber.length < 7) {
      showToast('请输入有效的手机号后再获取验证码', true);
      return;
    }
    let count = 60;
    setCountdown(count);
    showToast('验证码已发送至您的手机，请查收');
    const timer = setInterval(() => {
      count--;
      if (count > 0) {
        setCountdown(count);
      } else {
        clearInterval(timer);
        setCountdown(0);
      }
    }, 1000);
  }, [phoneNumber, showToast]);

  const handleSubmit = useCallback((e) => {
    e.preventDefault();
    if (!termsAccepted) {
      showToast('请先阅读并勾选用户服务与隐私协议', true);
      return;
    }
    logger.info('Login submitted', { phoneNumber, activeTab });
    showToast('登录验证成功，正在同步学籍档案...');
    setTimeout(() => {
      window.location.hash = '#home';
    }, 1200);
  }, [termsAccepted, phoneNumber, activeTab, showToast]);

  const countryCodes = [
    { value: '+86', label: '+86 (中国大陆)' },
    { value: '+852', label: '+852 (中国香港)' },
    { value: '+853', label: '+853 (中国澳门)' },
    { value: '+886', label: '+886 (中国台湾)' },
    { value: '+1', label: '+1 (美国/加拿大)' },
    { value: '+44', label: '+44 (英国)' },
    { value: '+65', label: '+65 (新加坡)' },
  ];

  return (
    <>
      {/* ===== HEADER ===== */}
      <header className="fixed top-0 left-0 right-0 w-full z-50 bg-surface-container-lowest border-b border-surface-container shadow-sm">
        <div className="max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop h-16 flex items-center justify-between gap-space-md">
          <div className="flex items-center gap-space-sm shrink-0">
            <div className="w-8 h-8 rounded-lg bg-primary flex items-center justify-center">
              <span className="material-symbols-outlined text-on-primary text-[20px]">school</span>
            </div>
            <div className="flex flex-col">
              <span className="font-headline-sm text-headline-sm text-primary tracking-tight font-bold leading-tight">校园就业综合服务平台</span>
              <span className="font-label-sm text-label-sm text-on-surface-variant hidden sm:inline-block leading-tight">智能校招实习求职系统</span>
            </div>
          </div>
          <nav className="hidden lg:flex items-center gap-space-lg h-full">
            <a className="h-full flex items-center px-space-2xs text-on-surface-variant font-body-md text-body-md hover:text-primary transition-colors border-b-2 border-transparent" href="#">首页</a>
            <a className="h-full flex items-center px-space-2xs text-on-surface-variant font-body-md text-body-md hover:text-primary transition-colors border-b-2 border-transparent" href="#">职位搜索</a>
          </nav>
          <div className="flex items-center gap-space-md shrink-0">
            <a className="px-4 py-2 border border-outline-variant rounded-lg font-label-lg text-label-lg text-on-surface hover:bg-surface-container-low transition-colors" href="#">登录</a>
            <a className="px-4 py-2 bg-primary text-on-primary rounded-lg font-label-lg text-label-lg font-semibold hover:bg-surface-tint transition-colors" href="#">注册</a>
          </div>
        </div>
      </header>

      {/* ===== MAIN ===== */}
      <main className="w-full pt-16 bg-surface flex-1">
        <div className="relative w-full min-h-[calc(100vh-4rem)] flex items-center justify-center p-margin-mobile md:p-margin-tablet lg:p-space-2xl overflow-hidden">
          {/* Decorative blurs */}
          <div className="absolute -top-32 -left-32 w-96 h-96 rounded-full bg-primary-fixed/40 blur-3xl pointer-events-none"></div>
          <div className="absolute -bottom-40 -right-40 w-[28rem] h-[28rem] rounded-full bg-secondary-fixed/50 blur-3xl pointer-events-none"></div>
          <div className="absolute top-1/2 left-1/3 w-64 h-64 rounded-full bg-surface-container-high/60 blur-2xl pointer-events-none"></div>

          {/* Main container */}
          <div className="relative w-full max-w-6xl bg-surface-container-lowest rounded-xl shadow-xl overflow-hidden grid grid-cols-1 lg:grid-cols-12 z-10">
            {/* Left panel */}
            <div className="lg:col-span-5 bg-gradient-to-br from-primary via-primary-container to-secondary p-space-xl lg:p-space-2xl flex flex-col justify-between relative overflow-hidden text-on-primary">
              <div className="absolute inset-0 opacity-10 pointer-events-none">
                <svg className="w-full h-full" preserveAspectRatio="none" viewBox="0 0 100 100">
                  <defs>
                    <pattern height="10" id="grid-pattern" patternUnits="userSpaceOnUse" width="10">
                      <path d="M 10 0 L 0 0 0 10" fill="none" stroke="currentColor" strokeWidth="0.5"></path>
                    </pattern>
                  </defs>
                  <rect fill="url(#grid-pattern)" height="100" width="100"></rect>
                </svg>
              </div>

              <div className="relative z-10 space-y-space-xl">
                <div className="flex items-center gap-space-sm">
                  <div className="w-11 h-11 rounded-lg bg-surface-container-lowest/15 backdrop-blur-md p-space-2xs flex items-center justify-center shadow-sm">
                    <span className="material-symbols-outlined text-surface-bright text-[28px]">school</span>
                  </div>
                  <div>
                    <span className="font-headline-sm text-headline-sm text-surface-bright font-bold tracking-tight block">校园就业综合服务平台</span>
                    <span className="font-label-sm text-label-sm text-on-primary-container tracking-widest uppercase">Campus Career Nexus</span>
                  </div>
                </div>

                <div className="space-y-space-sm">
                  <div className="inline-flex items-center gap-space-2xs px-space-xs py-1 rounded-full bg-surface-container-lowest/20 backdrop-blur-sm">
                    <span className="w-2 h-2 rounded-full bg-secondary-container animate-pulse"></span>
                    <span className="font-label-sm text-label-sm text-surface-bright font-medium">2025届 春季名企直聘专场进行中</span>
                  </div>
                  <h1 className="font-headline-xl text-headline-xl text-surface-bright font-bold leading-tight">
                    开启你的<br/>职业梦想新征程
                  </h1>
                  <p className="font-body-md text-body-md text-on-primary-container leading-relaxed">
                    汇聚产学优质资源，打通从校园实习到高质量就职的全链路闭环通道。
                  </p>
                </div>

                <div className="space-y-space-md pt-space-xs">
                  <div className="flex items-start gap-space-sm group">
                    <div className="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                      <span className="material-symbols-outlined text-secondary-fixed-dim text-[18px]">verified</span>
                    </div>
                    <div>
                      <h4 className="font-label-lg text-label-lg text-surface-bright font-semibold">12,000+ 知名企业直接触达</h4>
                      <p className="font-body-sm text-body-sm text-on-primary-container">央企国企、行业龙头、科研院所全覆盖，HR 直播与专场直收</p>
                    </div>
                  </div>
                  <div className="flex items-start gap-space-sm group">
                    <div className="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                      <span className="material-symbols-outlined text-secondary-fixed-dim text-[18px]">security</span>
                    </div>
                    <div>
                      <h4 className="font-label-lg text-label-lg text-surface-bright font-semibold">全国高校实名认证背书</h4>
                      <p className="font-body-sm text-body-sm text-on-primary-container">学籍真实联审，岗位资质与招聘安全性全流程双向核验保障</p>
                    </div>
                  </div>
                  <div className="flex items-start gap-space-sm group">
                    <div className="w-8 h-8 rounded-lg bg-surface-container-lowest/15 flex items-center justify-center shrink-0 mt-0.5">
                      <span className="material-symbols-outlined text-secondary-fixed-dim text-[18px]">psychology</span>
                    </div>
                    <div>
                      <h4 className="font-label-lg text-label-lg text-surface-bright font-semibold">AI 简历诊断与精准撮合</h4>
                      <p className="font-body-sm text-body-sm text-on-primary-container">人岗语义匹配评分，配套一对一导师生涯规划与模拟面试辅导</p>
                    </div>
                  </div>
                </div>
              </div>

              <div className="relative z-10 pt-space-xl mt-space-md">
                <div className="p-space-md rounded-lg bg-surface-container-lowest/10 backdrop-blur-md">
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-space-xs">
                      <span className="material-symbols-outlined text-tertiary-fixed-dim text-[20px]">workspace_premium</span>
                      <span className="font-label-md text-label-md text-surface-bright font-medium">985/211 重点高校专属推荐通告</span>
                    </div>
                    <span className="font-label-sm text-label-sm text-secondary-fixed-dim font-semibold">网签直通</span>
                  </div>
                  <p className="font-body-sm text-body-sm text-on-primary-container mt-1">
                    支持"全国高校毕业生网上签约系统"无缝同步与档案转递
                  </p>
                </div>
              </div>
            </div>

            {/* Right panel */}
            <div className="lg:col-span-7 p-space-lg md:p-space-xl lg:p-space-2xl bg-surface-container-lowest flex flex-col justify-between">
              <div className="w-full max-w-md mx-auto space-y-space-lg">
                <div className="flex items-center justify-between pb-space-xs">
                  <div>
                    <span className="font-label-md text-label-md text-primary font-semibold tracking-wide uppercase">Student / Candidate</span>
                    <h2 className="font-headline-lg text-headline-lg text-on-surface font-bold mt-1">欢迎登录</h2>
                    <p className="font-body-sm text-body-sm text-on-surface-variant mt-0.5">进入系统查看最新校招动态、投递进度与面试安排</p>
                  </div>
                  <div className="hidden sm:flex flex-col items-end">
                    <div className="inline-flex items-center gap-1.5 px-space-xs py-1 rounded-full bg-surface-container font-label-sm text-label-sm text-on-surface font-medium">
                      <span className="w-2 h-2 rounded-full bg-primary"></span>
                      <span>学生通道</span>
                    </div>
                    <a className="font-label-sm text-label-sm text-primary hover:underline mt-1" href="#">切换为企业 HR 登录 →</a>
                  </div>
                </div>

                <div className="flex p-1 bg-surface-container-low rounded-lg">
                  <button
                    className={`flex-1 py-2 rounded-md font-label-lg text-label-lg transition-all text-center ${activeTab === 'pwd' ? 'font-semibold text-primary bg-surface-container-lowest shadow-sm' : 'font-medium text-on-surface-variant hover:text-on-surface'}`}
                    type="button"
                    onClick={() => setActiveTab('pwd')}
                  >
                    密码快捷登录
                  </button>
                  <button
                    className={`flex-1 py-2 rounded-md font-label-lg text-label-lg transition-all text-center ${activeTab === 'code' ? 'font-semibold text-primary bg-surface-container-lowest shadow-sm' : 'font-medium text-on-surface-variant hover:text-on-surface'}`}
                    type="button"
                    onClick={() => setActiveTab('code')}
                  >
                    手机验证码登录
                  </button>
                </div>

                <form className="space-y-space-md" onSubmit={handleSubmit}>
                  <div className="space-y-1.5">
                    <label className="block font-label-md text-label-md text-on-surface font-medium" htmlFor="phone-input">手机号码</label>
                    <div className="flex rounded-lg shadow-sm bg-surface-container-lowest">
                      <div className="relative shrink-0">
                        <select
                          className="h-10 pl-3 pr-7 bg-surface-container-low text-on-surface font-body-md text-body-md rounded-l-lg appearance-none cursor-pointer focus:outline-none"
                          value={countryCode}
                          onChange={(e) => setCountryCode(e.target.value)}
                        >
                          {countryCodes.map((c) => (<option key={c.value} value={c.value}>{c.label}</option>))}
                        </select>
                        <span className="material-symbols-outlined pointer-events-none absolute right-2 top-2.5 text-on-surface-variant text-[18px]">arrow_drop_down</span>
                      </div>
                      <div className="relative flex-1">
                        <input
                          className="w-full h-10 px-3.5 bg-surface-container-lowest text-on-surface placeholder:text-outline font-body-md text-body-md rounded-r-lg focus:outline-none transition-all"
                          id="phone-input" placeholder="请输入11位注册手机号" required type="tel"
                          value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)}
                        />
                      </div>
                    </div>
                  </div>

                  {activeTab === 'pwd' && (
                    <div className="space-y-1.5">
                      <div className="flex items-center justify-between">
                        <label className="block font-label-md text-label-md text-on-surface font-medium" htmlFor="password-input">登录密码</label>
                        <a className="font-label-sm text-label-sm text-primary hover:underline" href="#">忘记密码？</a>
                      </div>
                      <div className="relative">
                        <input
                          className="w-full h-10 pl-3.5 pr-10 bg-surface-container-lowest text-on-surface placeholder:text-outline font-body-md text-body-md rounded-lg shadow-sm focus:outline-none transition-all"
                          id="password-input" placeholder="请输入登录密码（6-20位字符）" required
                          type={showPassword ? 'text' : 'password'}
                          value={password} onChange={(e) => setPassword(e.target.value)}
                        />
                        <button
                          aria-label="显示或隐藏密码"
                          className="absolute right-2.5 top-2.5 text-outline hover:text-on-surface transition-colors"
                          type="button" onClick={() => setShowPassword(!showPassword)}
                        >
                          <span className="material-symbols-outlined text-[20px]">{showPassword ? 'visibility' : 'visibility_off'}</span>
                        </button>
                      </div>
                    </div>
                  )}

                  {activeTab === 'code' && (
                    <div className="space-y-1.5">
                      <label className="block font-label-md text-label-md text-on-surface font-medium" htmlFor="sms-code-input">短信验证码</label>
                      <div className="flex gap-2">
                        <input
                          className="flex-1 h-10 px-3.5 bg-surface-container-lowest text-on-surface placeholder:text-outline font-body-md text-body-md rounded-lg shadow-sm focus:outline-none transition-all"
                          id="sms-code-input" maxLength="6" placeholder="6位短信验证码" type="text"
                          value={smsCode} onChange={(e) => setSmsCode(e.target.value)}
                        />
                        <button
                          className={`px-4 h-10 bg-surface-container-low text-primary hover:bg-surface-container font-label-md text-label-md font-semibold rounded-lg transition-colors whitespace-nowrap ${countdown > 0 ? 'opacity-50 cursor-not-allowed' : ''}`}
                          type="button" onClick={handleSendCode} disabled={countdown > 0}
                        >
                          {countdown > 0 ? `${countdown}秒后重新获取` : '获取验证码'}
                        </button>
                      </div>
                    </div>
                  )}

                  <div className="flex items-center justify-between pt-1">
                    <label className="flex items-center gap-2 cursor-pointer select-none">
                      <input className="w-4 h-4 rounded text-primary focus:ring-0 cursor-pointer accent-primary" type="checkbox" checked={rememberMe} onChange={(e) => setRememberMe(e.target.checked)} />
                      <span className="font-body-sm text-body-sm text-on-surface-variant">30天内记住登录状态</span>
                    </label>
                    <span className="font-body-sm text-body-sm text-on-surface-variant sm:hidden">
                      <a className="text-primary hover:underline" href="#">忘记密码</a>
                    </span>
                  </div>

                  <button className="w-full h-11 mt-2 bg-gradient-to-r from-primary-container to-primary text-on-primary hover:opacity-95 font-label-lg text-label-lg font-semibold rounded-lg shadow-md hover:shadow-lg transition-all flex items-center justify-center gap-2" type="submit">
                    <span>立即登录</span>
                    <span className="material-symbols-outlined text-[18px]">arrow_forward</span>
                  </button>

                  <div className="flex items-start gap-2 pt-1">
                    <input className="mt-1 w-3.5 h-3.5 rounded text-primary focus:ring-0 accent-primary shrink-0" type="checkbox" checked={termsAccepted} onChange={(e) => setTermsAccepted(e.target.checked)} />
                    <label className="font-body-sm text-body-sm text-on-surface-variant leading-snug cursor-pointer">
                      我已认真阅读并同意
                      <a className="text-primary hover:underline" href="#">《用户服务协议》</a>、
                      <a className="text-primary hover:underline" href="#">《个人信息保护与隐私政策》</a>
                      及高校毕业生求职诚信承诺。
                    </label>
                  </div>
                </form>

                <div className="space-y-space-md pt-space-xs">
                  <div className="relative flex items-center justify-center">
                    <div className="w-full h-px bg-surface-container"></div>
                    <span className="absolute px-3 bg-surface-container-lowest font-label-sm text-label-sm text-on-surface-variant">高校统一认证与快捷登录</span>
                  </div>
                  <div className="grid grid-cols-3 gap-space-sm">
                    <button className="flex flex-col items-center justify-center gap-1.5 p-2.5 rounded-lg bg-surface-container-low hover:bg-surface-container hover:text-primary transition-all text-on-surface-variant group" title="全国高校统一身份认证 (CARSI / CAS)" type="button">
                      <span className="material-symbols-outlined text-primary text-[24px]">account_balance</span>
                      <span className="font-label-sm text-label-sm font-medium">校园统一认证</span>
                    </button>
                    <button className="flex flex-col items-center justify-center gap-1.5 p-2.5 rounded-lg bg-surface-container-low hover:bg-surface-container hover:text-primary transition-all text-on-surface-variant group" title="微信扫码安全快捷登录" type="button">
                      <span className="material-symbols-outlined text-secondary text-[24px]">qr_code_scanner</span>
                      <span className="font-label-sm text-label-sm font-medium">微信扫码</span>
                    </button>
                    <button className="flex flex-col items-center justify-center gap-1.5 p-2.5 rounded-lg bg-surface-container-low hover:bg-surface-container hover:text-primary transition-all text-on-surface-variant group" title="学信网实名一键核验" type="button">
                      <span className="material-symbols-outlined text-tertiary text-[24px]">badge</span>
                      <span className="font-label-sm text-label-sm font-medium">学信网实名</span>
                    </button>
                  </div>
                  <div className="flex items-center justify-between text-center pt-space-xs font-body-sm text-body-sm text-on-surface-variant">
                    <span>还没有学生账号？</span>
                    <div className="space-x-3">
                      <a className="text-primary font-semibold hover:underline" href="#">免费注册毕业生账号</a>
                      <span className="text-outline-variant">|</span>
                      <a className="text-on-surface hover:text-primary font-medium" href="#">用人单位注册</a>
                    </div>
                  </div>
                </div>
              </div>

              <div className="pt-space-lg text-center font-label-sm text-label-sm text-outline">全国高校毕业生就业一体化指导服务体系认证支持</div>
            </div>
          </div>

          {/* Toast */}
          <div className={`fixed bottom-6 right-6 z-50 transform transition-all duration-300 pointer-events-none ${toast.visible ? 'translate-y-0 opacity-100' : 'translate-y-20 opacity-0'}`}>
            <div className="bg-inverse-surface text-inverse-on-surface px-space-md py-2.5 rounded-lg shadow-xl flex items-center gap-2">
              <span className={`material-symbols-outlined text-[20px] ${toast.isError ? 'text-error' : 'text-secondary-fixed'}`}>
                {toast.isError ? 'error' : 'check_circle'}
              </span>
              <span className="font-body-md text-body-md">{toast.message}</span>
            </div>
          </div>
        </div>
      </main>

      {/* ===== FOOTER ===== */}
      <footer className="w-full bg-inverse-surface text-inverse-on-surface mt-auto">
        <div className="max-w-7xl mx-auto px-margin-mobile md:px-margin-tablet lg:px-margin-desktop py-space-2xl">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-space-xl">
            <div className="space-y-space-md">
              <div className="flex items-center gap-space-xs">
                <span className="material-symbols-outlined text-inverse-primary text-[28px]">school</span>
                <span className="font-headline-sm text-headline-sm text-inverse-on-surface font-bold">校园就业综合服务平台</span>
              </div>
              <p className="font-body-sm text-body-sm text-inverse-on-surface/80 leading-relaxed">服务高校大学生实习求职与校企直聘的数字化一站式就业枢纽平台。构筑产学融合协同桥梁，赋能青年人才高质量就业与职业成长。</p>
            </div>
            <div>
              <h4 className="font-headline-sm text-headline-sm text-inverse-on-surface font-semibold mb-space-md">快速链接</h4>
              <ul className="space-y-space-xs">
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">关于我们</a></li>
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">联系方式</a></li>
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">隐私保护政策</a></li>
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">用户服务协议</a></li>
              </ul>
            </div>
            <div>
              <h4 className="font-headline-sm text-headline-sm text-inverse-on-surface font-semibold mb-space-md">合作通道</h4>
              <ul className="space-y-space-xs">
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">高校就业中心入驻</a></li>
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">名企专属招聘通告</a></li>
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">双选会承办合作</a></li>
              </ul>
            </div>
            <div>
              <h4 className="font-headline-sm text-headline-sm text-inverse-on-surface font-semibold mb-space-md">技术支持</h4>
              <ul className="space-y-space-xs">
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">帮助中心</a></li>
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">安全中心</a></li>
                <li className="font-body-sm text-body-sm"><a className="text-inverse-on-surface/75 hover:text-inverse-on-surface transition-colors" href="#">开放平台</a></li>
              </ul>
            </div>
          </div>
          <div className="mt-space-2xl pt-space-lg border-t border-inverse-on-surface/20 flex flex-col md:flex-row items-center justify-between gap-space-md">
            <p className="font-body-sm text-body-sm text-inverse-on-surface/60">© 2025 Campus Career Nexus. 保留所有权利。</p>
            <div className="flex items-center gap-space-md">
              <span className="font-body-sm text-body-sm text-inverse-on-surface/60">京ICP备XXXXXXXX号-1</span>
              <span className="font-body-sm text-body-sm text-inverse-on-surface/60">京公网安备 XXXXXXXXXXXXX号</span>
            </div>
          </div>
        </div>
      </footer>
    </>
  );
};

export default LoginPage;
