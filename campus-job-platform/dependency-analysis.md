# 微服务依赖重复分析报告

## 当前依赖结构

### 根pom.xml（父依赖）
已定义的公共依赖：
- spring-boot-starter-web
- spring-cloud-starter-alibaba-nacos-discovery
- spring-cloud-starter-alibaba-nacos-config
- spring-cloud-starter-bootstrap
- spring-cloud-starter-loadbalancer
- lombok

### 各子模块依赖重复情况

| 依赖 | campus-job-common | gateway-service | user-service | company-service | job-service | application-service | recommend-service |
|------|-------------------|-----------------|--------------|-----------------|-------------|---------------------|-------------------|
| mybatis-plus-boot-starter | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| mysql-connector-j | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| druid-spring-boot-starter | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| knife4j-openapi2-spring-boot-starter | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| spring-boot-starter-validation | ✅ | ❌ | ✅ | ✅ | ✅ | ✅ | ❌ |
| spring-cloud-starter-openfeign | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |
| spring-boot-starter-data-redis | ❌ | ❌ | ✅ | ❌ | ✅ | ❌ | ✅ |
| jjwt-api/impl/jackson | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| hutool-all | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| campus-job-common | ❌ | ❌ | ✅ | ✅ | ✅ | ✅ | ✅ |

## 重复依赖分析

### 1. 数据库相关依赖（5个服务重复）
- **mybatis-plus-boot-starter**
- **mysql-connector-j**
- **druid-spring-boot-starter**

### 2. API文档依赖（5个服务重复）
- **knife4j-openapi2-spring-boot-starter**

### 3. 服务调用依赖（5个服务重复）
- **spring-cloud-starter-openfeign**

### 4. 验证依赖（4个服务重复）
- **spring-boot-starter-validation**

### 5. 缓存依赖（3个服务重复）
- **spring-boot-starter-data-redis**

## 优化建议

### 方案一：在父pom的dependencyManagement中统一管理版本（推荐）
已经在父pom中使用`dependencyManagement`管理了版本，但子模块仍在重复声明依赖。

### 方案二：创建微服务公共模块
创建一个新的`microservice-common`模块，包含所有微服务都需要的依赖。

### 方案三：将通用依赖放入父pom的dependencies中
对于所有微服务都需要的依赖，可以直接放入父pom的dependencies中。