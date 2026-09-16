# 管理后台前后端接口对接计划

> 文档创建时间：2026-09-15
> 状态：后端扩展完成，待前端对接

---

## 一、项目架构

### 1.1 微服务结构

| 服务 | 端口 | Nacos注册 | 说明 |
|------|------|-----------|------|
| gateway-service | 9000 | 127.0.0.1:8848 | API网关，统一入口 |
| user-service | 8081 | 127.0.0.1:8848 | 用户服务 |
| company-service | 8082 | 127.0.0.1:8848 | 企业服务 |
| job-service | 8083 | 127.0.0.1:8848 | 职位服务 |
| application-service | 8084 | 127.0.0.1:8848 | 投递服务 |
| recommend-service | 8085 | 127.0.0.1:8848 | 推荐服务 |

### 1.2 认证机制

- **Token类型**: JWT Bearer Token
- **网关鉴权**: `AuthFilter` 全局过滤器，校验Token有效性
- **下游传递**: 网关注入 `X-User-Id`、`X-User-Phone`、`X-User-Role` 头
- **用户角色**: `0=学生`、`1=企业HR`、`2=管理员`

### 1.3 统一响应结构

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

---

## 二、可直接复用的现有接口

以下接口无需修改，前端可直接调用：

| 前端功能 | 接口路径 | Method | 说明 |
|---------|---------|--------|------|
| 管理员登录 | `/api/user/login` | POST | 响应包含 `role: 2`，前端判断角色即可 |
| 获取管理员信息 | `/api/user/info` | GET | 通过 `X-User-Id` 获取当前用户信息 |
| 浏览已发布职位 | `/api/job/search` | GET | 前端可直接调用查看职位列表 |
| 查看职位详情 | `/api/job/detail/{id}` | GET | 无修改 |

### 2.1 登录接口详情

**请求**
```json
POST /api/user/login
{
  "phone": "13800138000",
  "password": "admin123"
}
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "userId": 1,
    "username": "超级管理员",
    "role": 2,
    "avatar": "https://..."
  }
}
```

### 2.2 获取用户信息详情

**请求**
```
GET /api/user/info
Headers:
  X-User-Id: 1
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "phone": "13800138000",
    "username": "超级管理员",
    "role": 2,
    "avatar": "...",
    "email": "admin@example.com",
    "realName": "管理员",
    "gender": 1,
    "status": 1
  }
}
```

---

## 三、需要新增的接口（共 9 个）

### 3.1 user-service（+3 个接口）

#### 接口1：获取用户列表

```
GET /api/user/list
Headers:
  X-User-Id: {adminUserId}
Query Parameters:
  keyword: string (可选)    // 搜索关键词（用户名/手机号）
  role: integer (可选)      // 角色筛选：0=学生, 1=企业HR, 2=管理员
  status: integer (可选)    // 状态筛选：0=禁用, 1=正常
  page: integer (默认1)     // 页码
  pageSize: integer (默认20) // 每页数量
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "phone": "138****9210",
        "username": "张三",
        "role": 0,
        "roleLabel": "学生",
        "avatar": "...",
        "email": "zhangsan@example.com",
        "status": 1,
        "statusLabel": "正常",
        "createTime": "2024-01-15T10:30:00",
        "lastLoginTime": "2024-10-24T14:20:00"
      }
    ],
    "total": 1280,
    "page": 1,
    "pageSize": 20,
    "totalPages": 64
  }
}
```

#### 接口2：禁用/启用用户

```
PUT /api/user/status/{id}
Headers:
  X-User-Id: {adminUserId}
Query Parameters:
  status: integer // 0=禁用, 1=启用
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": null
}
```

#### 接口3：用户统计

```
GET /api/user/count
Headers:
  X-User-Id: {adminUserId}
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 1280,
    "byRole": {
      "student": 980,
      "hr": 295,
      "admin": 5
    },
    "byStatus": {
      "active": 1200,
      "disabled": 80
    }
  }
}
```

---

### 3.2 company-service（+3 个接口）

#### 接口4：获取企业列表

```
GET /api/company/list
Headers:
  X-User-Id: {adminUserId}
Query Parameters:
  keyword: string (可选)          // 搜索关键词（企业名称/统一社会信用代码）
  auditStatus: integer (可选)     // 审核状态：0=待审核, 1=已通过, 2=已拒绝
  industry: string (可选)         // 行业筛选
  page: integer (默认1)           // 页码
  pageSize: integer (默认10)      // 每页数量
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "userId": 101,
        "companyName": "未来智能科技(北京)有限公司",
        "industry": "人工智能/大数据",
        "scale": "100-499人",
        "auditStatus": 0,
        "auditStatusLabel": "待审核",
        "licenseUrl": "https://...",
        "createTime": "2024-10-24T09:12:00"
      }
    ],
    "total": 45,
    "page": 1,
    "pageSize": 10,
    "totalPages": 5
  }
}
```

#### 接口5：通过企业审核

```
PUT /api/company/audit/approve/{id}
Headers:
  X-User-Id: {adminUserId}
```

**响应**
```json
{
  "code": 200,
  "message": "审核通过",
  "data": null
}
```

#### 接口6：拒绝企业审核

```
PUT /api/company/audit/reject/{id}
Headers:
  X-User-Id: {adminUserId}
Content-Type: application/json

{
  "remark": "营业执照模糊，请重新上传"
}
```

**响应**
```json
{
  "code": 200,
  "message": "已拒绝",
  "data": null
}
```

---

### 3.3 job-service（+3 个接口）

#### 接口7：管理员获取职位列表

```
GET /api/job/admin/list
Headers:
  X-User-Id: {adminUserId}
Query Parameters:
  keyword: string (可选)          // 搜索关键词（职位名称）
  auditStatus: integer (可选)     // 审核状态：0=待审核, 1=已通过, 2=已拒绝
  status: integer (可选)          // 职位状态：0=待发布, 1=招聘中, 2=已关闭
  category: string (可选)         // 职位类别
  city: string (可选)             // 城市
  page: integer (默认1)           // 页码
  pageSize: integer (默认10)      // 每页数量
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "records": [
      {
        "id": 1,
        "title": "Java后端开发实习生",
        "companyName": "未来智能科技(北京)有限公司",
        "category": "技术开发",
        "city": "杭州",
        "salaryMin": 260,
        "salaryMax": 320,
        "education": "本科及以上",
        "jobType": 2,
        "jobTypeLabel": "实习",
        "headcount": 5,
        "status": 1,
        "statusLabel": "招聘中",
        "auditStatus": 0,
        "auditStatusLabel": "待审核",
        "createTime": "2024-10-24T10:24:00"
      }
    ],
    "total": 156,
    "page": 1,
    "pageSize": 10,
    "totalPages": 16
  }
}
```

#### 接口8：审核职位

```
PUT /api/job/audit/{id}
Headers:
  X-User-Id: {adminUserId}
Content-Type: application/json

{
  "auditStatus": 1,
  "remark": ""
}
```

**auditStatus 值说明**
- `1` = 通过
- `2` = 拒绝（需提供 remark）

**响应**
```json
{
  "code": 200,
  "message": "审核完成",
  "data": null
}
```

#### 接口9：职位统计

```
GET /api/job/count
Headers:
  X-User-Id: {adminUserId}
```

**响应**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 520,
    "byStatus": {
      "pending": 45,
      "recruiting": 420,
      "closed": 55
    },
    "byAuditStatus": {
      "pending": 30,
      "approved": 460,
      "rejected": 30
    }
  }
}
```

---

## 四、前端对接方案

### 4.1 创建 Axios 实例

```typescript
// src/utils/request.ts
import axios from 'axios'
import { useAuthStore } from '@/stores/auth'
import router from '@/router'

const request = axios.create({
  baseURL: '', // 使用 Vite 代理，请求会被转发到 localhost:9000
  timeout: 10000
})

// 请求拦截器：自动附加 Token
request.interceptors.request.use(config => {
  const token = localStorage.getItem('admin_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 响应拦截器：统一处理错误
request.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('admin_token')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

export default request
```

### 4.2 跨域解决方案（Vite 代理）

在 `admin-frontend/vite.config.ts` 中添加：

```typescript
export default defineConfig({
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:9000',
        changeOrigin: true
      }
    }
  }
})
```

### 4.3 Token 存储

| 项目 | 值 |
|------|-----|
| 存储位置 | `localStorage` |
| Key | `admin_token` |
| 存储内容 | JWT Token 字符串 |
| 过期处理 | 响应拦截器捕获 401，跳转登录页 |

### 4.4 管理员接口鉴权

**需在 `AuthFilter` 中增加角色校验逻辑：**

```java
// 管理员接口路径
private static final List<String> ADMIN_PATHS = Arrays.asList(
    "/api/user/list",
    "/api/user/status",
    "/api/user/count",
    "/api/company/list",
    "/api/company/audit/approve",
    "/api/company/audit/reject",
    "/api/job/admin/list",
    "/api/job/audit",
    "/api/job/count"
);

// 检查是否为管理员接口
boolean isAdminPath = ADMIN_PATHS.stream().anyMatch(path::startsWith);
if (isAdminPath) {
    Integer role = claims.get("role", Integer.class);
    if (role == null || role != 2) {
        // 非管理员，返回403
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        // ...
    }
}
```

---

## 五、后端文件变更清单

### 5.1 需要修改的文件（12 个）

| 服务 | 文件路径 | 变更内容 |
|------|---------|---------|
| user-service | `controller/UserController.java` | +3 个方法（list/status/count） |
| user-service | `service/UserService.java` | +3 个方法签名 |
| user-service | `service/impl/UserServiceImpl.java` | +3 个实现 |
| user-service | `vo/UserInfoVO.java` | +createTime 字段 |
| company-service | `controller/CompanyController.java` | +3 个方法（list/approve/reject） |
| company-service | `service/CompanyService.java` | +3 个方法签名 |
| company-service | `service/impl/CompanyServiceImpl.java` | +3 个实现 |
| job-service | `controller/JobController.java` | +3 个方法（admin/list/audit/count） |
| job-service | `service/JobService.java` | +3 个方法签名 |
| job-service | `service/impl/JobServiceImpl.java` | +3 个实现 |
| job-service | `vo/JobInfoVO.java` | +auditStatus/auditRemark/auditTime 字段 |
| gateway | `filter/AuthFilter.java` | +角色校验逻辑 |

### 5.2 需要新增的文件（4 个）

| 服务 | 文件路径 | 说明 |
|------|---------|------|
| user-service | `vo/UserListVO.java` | 用户列表分页响应 |
| company-service | `vo/CompanyListVO.java` | 企业列表分页响应 |
| company-service | `dto/CompanyAuditHandleDTO.java` | 审核操作DTO（remark） |
| job-service | `dto/JobAuditDTO.java` | 审核操作DTO（auditStatus + remark） |

---

## 六、实施步骤

### 第一步：后端扩展（9 个接口）

```
1. user-service
   ├── GET /api/user/list      （用户列表）
   ├── PUT /api/user/status/{id} （禁用/启用）
   └── GET /api/user/count     （用户统计）

2. company-service
   ├── GET /api/company/list      （企业列表）
   ├── PUT /api/company/audit/approve/{id} （通过审核）
   └── PUT /api/company/audit/reject/{id}  （拒绝审核）

3. job-service
   ├── GET /api/job/admin/list  （职位列表）
   ├── PUT /api/job/audit/{id}  （审核职位）
   └── GET /api/job/count       （职位统计）

4. gateway-service
   └── AuthFilter 增加角色校验
```

### 第二步：前端对接

```
1. 配置 Vite 代理（vite.config.ts）
2. 创建 axios 实例 + 拦截器（src/utils/request.ts）
3. 封装 API 调用函数（src/api/*.ts）
4. 修改各页面组件，替换 Mock 数据为 API 调用
5. 删除 mock/ 目录
```

### 第三步：测试验证

```
1. 后端接口测试（Knife4j / Postman）
2. 前端功能测试
3. 权限测试（非管理员无法访问管理接口）
```

---

## 七、附录：状态字段对照表

### 7.1 企业审核状态（auditStatus）

| 值 | 含义 | 说明 |
|----|------|------|
| 0 | 待审核 | 企业提交认证申请后 |
| 1 | 已通过 | 管理员审核通过 |
| 2 | 已拒绝 | 管理员审核拒绝（需填写原因） |

### 7.2 职位状态（status）

| 值 | 含义 | 说明 |
|----|------|------|
| 0 | 待发布 | 职位草稿 |
| 1 | 招聘中 | 正常展示 |
| 2 | 已关闭 | 下架/结束 |

### 7.3 职位审核状态（auditStatus）

| 值 | 含义 | 说明 |
|----|------|------|
| 0 | 待审核 | 企业发布后 |
| 1 | 已通过 | 管理员审核通过 |
| 2 | 已拒绝 | 管理员审核拒绝（需填写原因） |

### 7.4 用户状态（status）

| 值 | 含义 | 说明 |
|----|------|------|
| 0 | 已禁用 | 管理员禁用 |
| 1 | 正常 | 可正常使用 |

### 7.5 用户角色（role）

| 值 | 含义 | 说明 |
|----|------|------|
| 0 | 学生 | 求职者 |
| 1 | 企业HR | 招聘方 |
| 2 | 管理员 | 后台管理 |

---

## 八、待确认事项

- [ ] 确认管理员账号已存在于数据库（role=2）
- [ ] 确认 Nacos 服务已启动
- [ ] 确认数据库已初始化表结构
- [x] 跨域方案：已确定使用 Vite 代理
