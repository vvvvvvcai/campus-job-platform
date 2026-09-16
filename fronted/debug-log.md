# 前后端联调日志

## 日期：2026-09-16

---

### 问题1：前端无HTTP请求，所有数据为Mock
**问题描述：** 前端项目是一个纯UI原型，没有任何真实的HTTP请求，所有数据都硬编码在Vue组件中。
**解决方案：** 
1. 安装axios依赖
2. 创建API服务层（src/api/目录）
3. 配置vite代理到后端Gateway（localhost:9000）
4. 更新各组件使用真实API调用

---

### 问题2：Gateway缺少/api/favorite路由
**问题描述：** 后端Gateway的路由配置中缺少`/api/favorite/**`的路由，导致收藏相关接口无法通过Gateway访问。
**解决方案：** 在gateway-service的application.yml中添加favorite-service路由，指向job-service。
**修改文件：** `campus-job-platform/gateway-service/src/main/resources/application.yml`

---

### 问题3：后端无短信发送接口
**问题描述：** 后端UserController中没有短信发送接口（/api/user/sms），但Gateway白名单中配置了该路径。
**解决方案：** 前端暂时禁用短信登录功能，提示用户使用密码登录。后续需要后端实现短信发送接口。

---

### 问题4：登录返回500错误
**问题描述：** 用户登录时返回"Request failed with status code 500"错误。
**可能原因：**
1. 数据库`job_platform`中不存在`t_user`表
2. Redis服务未启动（user-service依赖Redis）
3. Nacos服务未启动（Gateway需要服务发现）
4. 后端微服务未启动

**解决方案：**
1. 确保MySQL、Redis、Nacos已启动
2. 在MySQL中创建`job_platform`数据库并执行建表脚本
3. 启动user-service和gateway-service
4. **临时方案：** 前端已添加演示模式，当后端不可用时自动切换为模拟登录，不影响前端功能测试

---

### 问题5：前端Profile页面字段与后端UserInfoVO不匹配
**问题描述：** 前端Profile页面有大量字段（gender、birth、wechat、location等），但后端UserInfoVO只返回基本字段。
**解决方案：** 简化Profile页面字段，只保留后端实际返回的字段（username、realName、phone、email）。

---

### 问题6：前端ResumeManage页面数据与后端ResumeInfoVO不匹配
**问题描述：** 前端ResumeManage页面有tags、completeness等字段，但后端ResumeInfoVO没有这些字段。
**解决方案：** 根据后端返回的数据动态计算completeness和tags。

---

## 已接入的接口清单

| 接口 | 方法 | 前端页面 | 状态 |
|------|------|---------|------|
| POST /api/user/login | 登录 | App.vue | ✅ 已接入 |
| POST /api/user/register | 注册 | App.vue | ✅ 已接入 |
| GET /api/user/info | 获取用户信息 | Profile.vue | ✅ 已接入 |
| PUT /api/user/info | 更新用户信息 | Profile.vue | ✅ 已接入 |
| PUT /api/user/password | 修改密码 | ChangePassword.vue | ✅ 已接入 |
| GET /api/job/search | 搜索职位 | JobSearch.vue, Home.vue | ✅ 已接入 |
| GET /api/job/detail/{id} | 职位详情 | JobDetail.vue | ⚠️ 待接入 |
| POST /api/job/publish | 发布职位 | PostJob.vue | ⚠️ 待接入 |
| GET /api/resume/list | 简历列表 | ResumeManage.vue | ✅ 已接入 |
| GET /api/application/list | 投递列表 | Applications.vue | ✅ 已接入 |
| GET /api/favorite/list | 收藏列表 | Favorites.vue | ✅ 已接入 |
| DELETE /api/favorite/{jobId} | 取消收藏 | Favorites.vue | ✅ 已接入 |
| GET /api/company/info | 企业信息 | EnterpriseCert.vue | ⚠️ 待接入 |
| POST /api/company/audit | 企业认证 | EnterpriseCert.vue | ⚠️ 待接入 |

---

### 问题7：user-service启动报ClassNotFoundException: MetaObjectHandler
**问题描述：** `mvn spring-boot:run -pl user-service`启动时报`NoClassDefFoundError: com/baomidou/mybatisplus/core/handlers/MetaObjectHandler`。
**原因：** `campus-job-common`模块未先安装到本地Maven仓库，`spring-boot:run`无法找到该依赖。
**解决方案：** 先在项目根目录执行`mvn clean install -DskipTests`，再单独启动各微服务。

---

### 问题8：Nacos未安装
**问题描述：** 后端微服务需要Nacos进行服务注册和发现，但Nacos未下载。
**解决方案：** 下载Nacos 2.2.3，解压后以standalone模式启动（`startup.cmd -m standalone`），监听8848端口。

---

## 当前服务状态（2026-09-16 04:55）

| 服务 | 端口 | 状态 |
|------|------|------|
| Nacos | 8848 | ✅ 运行中 |
| Redis | 6379 | ✅ 运行中 |
| MySQL | 3306 | ✅ 运行中 |
| Gateway | 9000 | ✅ 运行中 |
| user-service | 8081 | ✅ 运行中 |
| company-service | 8082 | ✅ 运行中 |
| job-service | 8083 | ✅ 运行中 |
| application-service | 8084 | ✅ 运行中 |
| recommend-service | 8085 | ✅ 运行中 |
| Frontend | 5174 | ✅ 运行中 |

---

## 已验证的接口

- ✅ `POST /api/user/login` → 返回token、userId、role（通过Gateway测试成功）
- ✅ `POST /api/user/register` → 注册新用户成功

---

## 待办事项

1. [ ] 后端实现短信发送接口
2. [ ] 后端实现短信验证码登录
3. [ ] 前端接入职位详情页API
4. [ ] 前端接入企业认证API
5. [ ] 前端接入发布职位API
6. [ ] 前端接入投递简历API
7. [ ] 前端接入推荐职位API
8. [ ] 前端接入企业管理相关API
