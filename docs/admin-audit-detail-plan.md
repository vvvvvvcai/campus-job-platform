# 管理后台审核详情功能开发计划

> 文档创建时间：2026-09-16
> 状态：计划确认中

---

## 一、需求背景

管理后台的「职位信息审核」和「企业资质审核」页面，目前列表只展示核心字段，操作列仅支持「通过 / 拒绝」。管理员在审核时需要查看被审核对象的完整信息，因此需要：

1. **职位信息审核页面**：操作列新增「详情」按钮，点击弹窗查看职位完整信息
2. **企业资质审核页面**：操作列新增「详情」按钮，点击弹窗查看企业完整信息（含营业执照、HR联系方式等）

## 二、详情信息展示内容

### 2.1 职位详情（基于 t_job 表 + t_company 表）

| 分组 | 字段 | 来源 |
|------|------|------|
| 基本信息 | 职位名称 `title` | t_job |
| 基本信息 | 岗位类别 `category` | t_job |
| 基本信息 | 工作城市 `city` | t_job |
| 基本信息 | 薪资范围 `salary_min` ~ `salary_max` | t_job |
| 基本信息 | 学历要求 `education` | t_job |
| 基本信息 | 经验要求 `experience` | t_job |
| 基本信息 | 工作类型 `job_type`（全职/实习/兼职） | t_job |
| 基本信息 | 招聘人数 `headcount` | t_job |
| 企业信息 | 企业名称 `company_name` | t_company（跨服务） |
| 内容信息 | 职位描述 `description` | t_job |
| 内容信息 | 任职要求 `requirement` | t_job |
| 内容信息 | 福利待遇 `benefits`（JSON 标签） | t_job |
| 审核信息 | 审核状态 `audit_status` | t_job |
| 审核信息 | 审核备注 `audit_remark` | t_job |
| 审核信息 | 审核时间 `audit_time` | t_job |
| 状态统计 | 职位状态 `status` | t_job |
| 状态统计 | 浏览量 `view_count` / 投递量 `apply_count` | t_job |
| 时间 | 创建时间 `create_time` / 更新时间 `update_time` | t_job |

### 2.2 企业详情（基于 t_company 表 + t_user 表）

| 分组 | 字段 | 来源 |
|------|------|------|
| 基本信息 | 企业名称 `company_name` | t_company |
| 基本信息 | 所属行业 `industry` | t_company |
| 基本信息 | 企业规模 `scale` | t_company |
| 基本信息 | 企业性质 `nature` | t_company |
| 基本信息 | 企业地址 `address` | t_company |
| 基本信息 | 企业官网 `website` | t_company |
| 认证信息 | 企业Logo `logo` | t_company |
| 认证信息 | 营业执照URL `license_url`（点击可查看大图） | t_company |
| 内容信息 | 企业简介 `description` | t_company |
| HR信息 | 联系人姓名 `real_name` | t_user |
| HR信息 | 联系电话 `phone`（脱敏显示） | t_user |
| HR信息 | 电子邮箱 `email` | t_user |
| 审核信息 | 审核状态 `audit_status` | t_company |
| 审核信息 | 审核备注 `audit_remark` | t_company |
| 审核信息 | 审核时间 `audit_time` | t_company |
| 时间 | 创建时间 `create_time` / 更新时间 `update_time` | t_company |

## 三、接口设计（后端新增 2 个管理端详情接口）

> 不使用现有白名单接口 `/api/job/detail/{id}`（会自增浏览量）与 `/api/company/info`（基于登录HR身份），统一新增管理员专属详情接口。

### 3.1 管理员职位详情

```
GET /api/job/admin/detail/{id}
Headers:
  X-User-Id: {adminUserId}    // 网关从 Token 注入

响应 data：
{
  "id": 1,
  "companyId": 2,
  "companyName": "阿里巴巴集团",
  "title": "Java开发工程师",
  "category": "后端开发",
  "city": "杭州",
  "salaryMin": 15000,
  "salaryMax": 25000,
  "education": "本科",
  "experience": "1-3年",
  "jobType": 1,
  "headcount": 5,
  "description": "负责核心业务系统开发",
  "requirement": "1.扎实的Java基础\n2.熟悉Spring Boot",
  "benefits": "[\"五险一金\",\"年终奖金\"]",
  "status": 0,
  "auditStatus": 0,
  "auditRemark": null,
  "auditTime": null,
  "viewCount": 10,
  "applyCount": 3,
  "createTime": "2026-09-15T10:00:00",
  "updateTime": "2026-09-15T10:00:00"
}
```

**实现要点：**
- 复用现有 `JobInfoVO`（字段已齐全），缺 `company_name` 与 `update_time` 两个字段补全
- 企业名称需跨服务获取：
  - 方案A（推荐）：job-service 新增 OpenFeign 客户端调用 company-service 的 `GET /api/company/admin/detail/{id}` 获取企业名称
  - 方案B（简单）：管理员审核场景下，企业名称从列表已有的 companyName 字段带入选参，后端不强依赖跨服务调用
- **建议选方案A**，同时为公司信息后续扩展打基础；实现时需注意 Feign 调用失败时降级（companyName 返回 null 不影响主流程）

### 3.2 管理员企业详情

```
GET /api/company/admin/detail/{id}
Headers:
  X-User-Id: {adminUserId}    // 网关从 Token 注入

响应 data：
{
  "id": 2,
  "userId": 12,
  "companyName": "腾讯科技",
  "industry": "互联网",
  "scale": "10000人以上",
  "nature": "上市公司",
  "address": "深圳市南山区科技中一路",
  "website": "https://www.tencent.com",
  "logo": "https://.../logo.png",
  "description": "腾讯是中国领先的互联网服务提供商",
  "licenseUrl": "https://.../license.jpg",
  "auditStatus": 1,
  "auditRemark": "审核通过",
  "auditTime": "2026-09-16T09:00:00",
  "createTime": "2026-09-10T09:00:00",
  "updateTime": "2026-09-16T09:00:00",
  "hrName": "李经理",
  "hrPhone": "138****0012",
  "hrEmail": "hr_li@company.com"
}
```

**实现要点：**
- 复用现有 `CompanyInfoVO`（字段已齐全），缺 `hrName/hrPhone/hrEmail` 三个字段
- 通过 `user_id` 关联 user-service 查询 HR 信息（同样走 Feign，失败时降级为 null）
- 手机号由前端或后端脱敏（`138****0012`）

### 3.3 网关角色校验（AuthFilter 修改）

在 `ADMIN_PATHS` 中追加两个新路径：

```java
"/api/job/admin/detail/",
"/api/company/admin/detail/"
```

> 注意：`/api/company/detail/**` 已在白名单中，新路径必须使用 `/admin/` 前缀，避免误触白名单。

## 四、前端改动

### 4.1 类型定义（admin-frontend/src/types/index.ts）

新增 `JobDetail` 与 `CompanyDetail` 接口：

```typescript
export interface JobDetail {
  id: number
  companyId?: number
  companyName?: string
  title: string
  category?: string
  city?: string
  salaryMin?: number
  salaryMax?: number
  education?: string
  experience?: string
  jobType?: number
  headcount?: number
  description?: string
  requirement?: string
  benefits?: string
  status: number
  auditStatus: number
  auditRemark?: string
  auditTime?: string
  viewCount?: number
  applyCount?: number
  createTime: string
  updateTime?: string
}

export interface CompanyDetail {
  id: number
  userId?: number
  companyName: string
  industry?: string
  scale?: string
  nature?: string
  address?: string
  website?: string
  logo?: string
  description?: string
  licenseUrl?: string
  auditStatus: number
  auditRemark?: string
  auditTime?: string
  createTime: string
  updateTime?: string
  hrName?: string
  hrPhone?: string
  hrEmail?: string
}
```

### 4.2 API 封装

- `admin-frontend/src/api/job.ts` 新增：

```typescript
export function getJobAdminDetail(id: number) {
  return request.get<any, Result<JobDetail>>(`/api/job/admin/detail/${id}`)
}
```

- `admin-frontend/src/api/company.ts` 新增：

```typescript
export function getCompanyAdminDetail(id: number) {
  return request.get<any, Result<CompanyDetail>>(`/api/company/admin/detail/${id}`)
}
```

### 4.3 页面组件

#### 职位信息审核（admin-frontend/src/views/JobAudit.vue）

- 操作列：对**所有状态**（待审核/已通过/已拒绝）都显示「详情」按钮，放在「通过/拒绝」左侧
- 点击「详情」→ 调用 `getJobAdminDetail(id)` → 打开**详情弹窗（Modal）**
- 弹窗内以分组展示（基本信息 / 职位描述 / 任职要求 / 福利待遇 / 审核信息 / 状态统计）
- 福利待遇为 JSON 字符串，渲染为标签（Tag）形式
- 关闭后无刷新需求，不影响审核操作

#### 企业资质审核（admin-frontend/src/views/EnterpriseAudit.vue）

- 操作列：同样对所有状态显示「详情」按钮
- 点击「详情」→ 调用 `getCompanyAdminDetail(id)` → 打开**详情弹窗（Modal）**
- 弹窗内以分组展示（基本信息 / 认证信息 / 企业简介 / HR联系方式 / 审核信息）
- 营业执照 URL 渲染为可点击图片，点击在新窗口打开大图

## 五、涉及文件清单

### 后端（8 个文件）

| 服务 | 文件 | 变更 |
|------|------|------|
| gateway-service | `filter/AuthFilter.java` | ADMIN_PATHS 追加 2 个路径 |
| job-service | `controller/JobController.java` | +`admin/detail/{id}` 接口 |
| job-service | `service/JobService.java` | +方法签名 |
| job-service | `service/impl/JobServiceImpl.java` | +实现（含 Feign 组装企业名） |
| job-service | `vo/JobInfoVO.java` | +`updateTime` 字段、补 `companyName` |
| company-service | `controller/CompanyController.java` | +`admin/detail/{id}` 接口 |
| company-service | `service/CompanyService.java` | +方法签名 |
| company-service | `service/impl/CompanyServiceImpl.java` | +实现（含 Feign 组装 HR 信息） |
| company-service | `vo/CompanyInfoVO.java` | +`hrName/hrPhone/hrEmail` 字段 |
| job-service | `remote/CompanyFeignClient.java`（新增） | 调用 company-service 获取详情 |
| company-service | `remote/UserFeignClient.java`（新增） | 调用 user-service 获取 HR 信息 |

### 前端（4 个文件）

| 文件 | 变更 |
|------|------|
| `admin-frontend/src/types/index.ts` | +`JobDetail`、`CompanyDetail` 类型 |
| `admin-frontend/src/api/job.ts` | +`getJobAdminDetail` 方法 |
| `admin-frontend/src/api/company.ts` | +`getCompanyAdminDetail` 方法 |
| `admin-frontend/src/views/JobAudit.vue` | +「详情」按钮 + 详情弹窗 |
| `admin-frontend/src/views/EnterpriseAudit.vue` | +「详情」按钮 + 详情弹窗 |

## 六、实施步骤

```
第一步：后端
  1. 确认 user-service 暴露 HR 信息查询接口（/api/user/info 可复用）
  2. 新增 CompanyFeignClient / UserFeignClient
  3. company-service 新增 /api/company/admin/detail/{id} + CompanyInfoVO 补字段
  4. job-service 新增 /api/job/admin/detail/{id} + JobInfoVO 补字段
  5. AuthFilter ADMIN_PATHS 追加 2 个路径
  6. 重启服务，Postman/Knife4j 验证 2 个接口 + 权限校验

第二步：前端
  7. types/index.ts 新增类型
  8. api/job.ts、api/company.ts 新增方法
  9. JobAudit.vue / EnterpriseAudit.vue 详情弹窗（共用样式可抽离或各自实现）
  10. npm run dev 本地联调验证

第三步：测试
  11. 不同审核状态的职位/企业点击详情均可展示完整信息
  12. 非管理员访问新接口返回 403
  13. 后端单元/接口自测（Feign 超时降级）
```

## 七、风险与注意事项

1. **跨服务 Feign 调用**：若 company-service / user-service 未启动，Feign 调用会失败。需设置超时与降级（try-catch 返回 null），保证职位/企业详情主流程不受影响
2. **路径冲突**：`/api/company/detail/**` 已在网关白名单，新管理员接口必须用 `/admin/` 前缀路径
3. **手机号脱敏**：HR 联系方式在前端展示时脱敏（`138****0012`），避免敏感信息泄露
4. **福利待遇 JSON 渲染**：`benefits` 是 JSON 字符串，前端需 `JSON.parse` 后渲染为标签，解析失败时兜底显示原文
5. **文档一致性**：数据库设计文档为本表结构依据，字段新增仅涉及 VO 层，无需改表

## 八、待确认事项

- [ ] 详情展示形式：弹窗（Modal）还是右侧抽屉（Drawer）？— 默认弹窗，样式与现有拒绝弹窗保持一致
- [ ] 企业 HR 信息是否需要在后端联查返回，还是前端不展示仅展示企业自身信息？— 默认后端联查返回
- [ ] 跨服务方式是走 OpenFeign（推荐）还是降级为空企业名？— 默认 OpenFeign + 降级