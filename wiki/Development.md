# 开发与运行指南

## 环境要求

- JDK 8+
- Maven 3.8+

## 本地构建

```bash
mvn clean package
```

如果出现 Maven 依赖下载失败（例如 403），请检查：

- 网络策略/代理
- 镜像源配置（`settings.xml`）

## 运行 Demo

在 IDE 中运行：

- 模块：`plugin-demo`
- 主类：`org.hqf.tutorials.java.Main`

默认执行 `loadPluginsBySpi()`。

## 新增插件（标准流程）

1. 在 `plugins/` 下新增模块（含 `pom.xml`）。
2. 实现 `org.hqf.tutorials.api.Plugin`。
3. 新增 SPI 注册文件：
   - `src/main/resources/META-INF/services/org.hqf.tutorials.api.Plugin`
4. 在根 `pom.xml` 的 `<modules>` 增加该插件模块。
5. 重新构建并运行 demo，确认插件被自动发现。

## 常见问题

### 插件未加载

优先检查 SPI 文件路径与类名是否正确。

### 插件无法优雅停止

确保在 `stop()` 中释放资源（定时器、线程、连接等）。

### 资源监控数据不稳定

当前实现是教学示例，可按业务需求替换为更精确的采样策略。
