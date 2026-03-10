# Java Plugin Manager Demo

一个基于 **Java SPI (`ServiceLoader`)** 的插件管理示例项目，演示如何：

- 定义统一插件接口
- 自动发现并加载插件
- 在独立线程中运行插件
- 做基础资源监控（CPU/内存/线程）

## 项目结构

- `plugin-api`：插件接口定义（`Plugin`）
- `plugin-core`：插件加载、线程隔离、资源监控
- `plugins/logging-plugin`：日志插件示例
- `plugins/resource-intensive-plugin`：资源密集型插件示例
- `plugin-demo`：Demo 启动入口

## 快速开始

### 1) 构建

```bash
mvn clean package
```

> 如果环境无法访问 Maven Central，构建可能因为依赖下载失败（例如 403）。

### 2) 运行

在 IDE 中运行以下主类：

- 模块：`plugin-demo`
- 主类：`org.hqf.tutorials.java.Main`

默认会走 `loadPluginsBySpi()`，通过 SPI 自动发现插件并启动。

## SPI 插件注册

每个插件模块都需要提供：

`src/main/resources/META-INF/services/org.hqf.tutorials.api.Plugin`

文件内容为插件实现类全限定名（一行一个）。

## Wiki

- [Wiki Home](wiki/Home.md)
- [Architecture](wiki/Architecture.md)
- [Development Guide](wiki/Development.md)
