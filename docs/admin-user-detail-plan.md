# 管理后台用户详情功能开发计划

> 文档创建时间：2026-09-16
> 状态：待实施

---

## 一、需求背景

管理后台的「用户权限管理」页面，操作列目前仅支持「禁用/启用」。管理员在管理用户时需要查看用户的完整信息，因此需要：

- 操作列新增「详情」按钮，点击弹窗查看用户完整信息

## 二、详情信息展示内容

### 用户详情（基于 t_user 表）

| 分组 | 字段 | 来源 |
|------|------|------|
| 基本信息 | 用户名 `username` | t_user |
| 基本信息 | 真实姓名 `real_name` | t_user |
| 基本信息 | 性别 `gender`（男/女/未知） | t_user |
| 基本信息 | 手机号 `phone` | t_user |
| 基本信息 | 邮箱 `email` | t_user |
| 基本信息 | 头像 `avatar` | t_user |
| 账号信息 | 角色 `role`（学生/企业HR/管理员） | t_user |
| 账号信息 | 状态 `status`（正常/禁用） | t_user |
| 时间 | 注册时间 `create_time` | t_user |
| 时间 | 更新时间 `update_time` | t_user |

## 三、接口设计

### 管理员获取用户详情

```
GET /api/user/admin/detail/{id}
Headers:
  X-User-Id: {adminUserId}    // 网关从 Token 注入

响应 data：
{
  "id": 1,
  "phone": "138****0001",
  "username": "zhangsan",
  "realName": "张三",
  "gender": 1,
  "genderLabel": "男",
  "email": "zhangsan@edu.cn",
  "avatar": null,
  "role": 0,
  "roleLabel": "学生",
  "status": 1,
  "statusLabel": "正常",
  "createTime": "2026-09-15T10:00:00",
  "updateTime": "2026-09-15T10:00:00"
}
```

**实现要点：**
- 复用现有 `UserInfoVO`（字段已齐全），无需新增 VO
- 手机号由前端或后端脱敏（`138****0012`）
- 性别、角色、状态返回对应的中文标签

### 网关角色校验（AuthFilter 修改）

在 `ADMIN_PATHS` 中追加：

```java
"/api/user/admin/detail/"
```

## 四、前端改动

### 4.1 类型定义（admin-frontend/src/types/index.ts）

新增 `UserDetail` 接口：

```typescript
export interface UserDetail {
  id: number
  phone: string
  username: string
  realName?: string
  gender?: number
  genderLabel?: string
  email?: string
  avatar?: string
  role: number
  roleLabel: string
  status: number
  statusLabel: string
  createTime: string
  updateTime?: string
}
```

### 4.2 API 封装

- `admin-frontend/src/api/user.ts` 新增：

```typescript
export function getUserAdminDetail(id: number) {
  return request.get<any, Result<UserDetail>>(`/api/user/admin/detail/${id}`)
}
```

### 4.3 页面组件（admin-frontend/src/views/UserManagement.vue）

- 操作列：对所有状态显示「详情」按钮，放在「禁用/启用」左侧
- 点击「详情」→ 调用 `getUserAdminDetail(id)` → 打开**详情弹窗（Modal）**
- 弹窗内以分组展示（基本信息 / 账号信息 / 时间信息）
- 关闭后无刷新需求，不影响禁用/启用操作

## 五、涉及文件清单

### 后端（4个文件）

| 服务 | 文件 | 变更 |
|------|------|------|
| gateway-service | `filter/AuthFilter.java` | ADMIN_PATHS 追加 1 个路径 |
| user-service | `controller/UserController.java` | +`admin/detail/{id}` 接口 |
| user-service | `service/UserService.java` | +方法签名 |
| user-service | `service/impl/UserServiceImpl.java` | +实现 |

### 前端（3个文件）

| 文件 | 变更 |
|------|------|
| `admin-frontend/src/types/index.ts` | +`UserDetail` 类型 |
| `admin-frontend/src/api/user.ts` | +`getUserAdminDetail` 方法 |
| `admin-frontend/src/views/UserManagement.vue` | +「详情」按钮 + 详情弹窗 |

## 六、实施步骤

```
第一步：后端
  1. UserController 新增 /api/user/admin/detail/{id} 接口
  2. UserService 新增 getUserAdminDetail 方法签名
  3. UserServiceImpl 实现 getUserAdminDetail
  4. AuthFilter ADMIN_PATHS 追加 /api/user/admin/detail/
  5. 重启 user-service

第二步：前端
  6. types/index.ts 新增 UserDetail 类型
  7. api/user.ts 新增 getUserAdminDetail 方法
  8. UserManagement.vue 添加「详情」按钮和详情弹窗
  9. 测试验证
```

## 七、注意事项

1. **手机号脱敏**：HR 联系方式在前端展示时脱敏（`138****0012`），避免敏感信息泄露
2. **性别显示**：数据库存储 0-未知、1-男、2-女，前端需转换为中文显示
3. **角色显示**：数据库存储 0-学生、1-企业HR、2-管理员，前端需转换为中文显示
