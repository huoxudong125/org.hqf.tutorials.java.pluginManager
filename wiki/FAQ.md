# FAQ

## 1. 为什么插件没有被加载？

按顺序检查：

1. 是否实现了 `org.hqf.tutorials.api.Plugin` 接口。
2. 是否存在 SPI 声明文件：
   `src/main/resources/META-INF/services/org.hqf.tutorials.api.Plugin`
3. SPI 文件里是否填写了**实现类全限定名**，且每行一个。
4. 插件模块是否已加入根 `pom.xml` 的 `<modules>`。

## 2. 为什么程序里看不到插件日志？

- 确认运行的是 `plugin-demo` 模块中的 `org.hqf.tutorials.java.Main`。
- 确认入口调用的是 `loadPluginsBySpi()`（默认方式）。
- 确认 `logging-plugin` 模块已正确打包并在运行 classpath 中。

## 3. 如何优雅停止插件？

建议在插件实现中：

- 在 `stop()` 中释放定时器、线程、连接等资源。
- 结合 `isStopping()` 标记，避免在停止后继续提交新任务。

## 4. 资源监控告警是什么意思？

`PluginResourceManager` 会根据 CPU/内存/线程阈值输出告警。当前实现用于教学演示：

- 主要用于提示风险
- 不会自动熔断/强杀插件

你可以在业务场景中扩展为自动限流、隔离或重启策略。

## 5. 构建时报 Maven 403 怎么办？

一般是仓库访问受限：

- 检查公司代理/网络策略
- 检查 `~/.m2/settings.xml` 是否配置了可用镜像
- 必要时切换到内网仓库
