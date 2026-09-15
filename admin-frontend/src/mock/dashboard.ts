import type { KpiCard } from '@/types'

export const dashboardKpiData: KpiCard[] = [
  {
    title: '注册用户总数',
    value: '1,280',
    trend: '+18.4%',
    trendUp: true,
    icon: 'group',
    iconBg: 'bg-primary-fixed',
    iconColor: 'text-primary',
    details: [
      { label: '学生 1,150 人', value: 'primary' },
      { label: '企业 130 家', value: 'secondary-container' },
    ],
    footer: { label: '阶段目标: ≥1000 人', value: '128% 达成' },
  },
  {
    title: '在线有效岗位数',
    value: '342',
    trend: '+12.6%',
    trendUp: true,
    icon: 'work',
    iconBg: 'bg-secondary-fixed',
    iconColor: 'text-secondary',
    details: [
      { label: '全职 214 / 实习 128', value: '' },
    ],
    footer: { label: '岗位企业比', value: '2.63 岗/企' },
  },
  {
    title: '累计投递总量',
    value: '4,520',
    trend: '今日 +128',
    trendUp: true,
    icon: 'send',
    iconBg: 'bg-tertiary-container/10',
    iconColor: 'text-tertiary-container',
    details: [
      { label: '人均投递 3.93 次', value: '' },
    ],
    footer: { label: '投递响应时延', value: '4.2 小时中位' },
  },
  {
    title: '投递成功率',
    value: '32.8%',
    trend: '+2.8% 环比',
    trendUp: true,
    icon: 'verified',
    iconBg: 'bg-tertiary-container/10',
    iconColor: 'text-tertiary',
    details: [
      { label: '目标标准 ≥ 30%', value: '已达标' },
    ],
    progress: 82,
  },
  {
    title: '智能推荐转化指标',
    value: '',
    icon: 'auto_awesome',
    iconBg: 'bg-primary-fixed',
    iconColor: 'text-primary',
    subMetrics: [
      { label: '岗位点击率 (CTR)', value: '18.2%', color: 'text-primary' },
      { label: '最终投递率 (CVR)', value: '6.4%', color: 'text-secondary-container' },
    ],
  },
]
