# Java Plugin Manager Demo

一个基于 **Java SPI (`ServiceLoader`)** 的插件管理示例项目，演示如何：

- 定义统一插件接口
- 自动发现并加载插件
- 在独立线程中运行插件
- 做基础资源监控（CPU/内存/线程）

## 项目结构

- `plugin-api`：插件接口定义（`Plugin`）
- `plugin-core`：插件加载、线程隔离、资源监控
- `plugins/sales-plugin`：销售模块，创建订单并发布订单结果
- `plugins/inventory-plugin`：库存模块，执行库存占用与短缺判断
- `plugins/warehouse-plugin`：仓储模块，库存足够时创建拣货任务
- `plugins/procurement-plugin`：采购模块，库存不足时自动发起请购
- `plugin-demo`：Demo 启动入口（串联业务流引擎）

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

## 业务流引擎

`plugin-core` 新增 `BusinessFlowEngine`，支持：

- 业务模块通过 SPI 注册 `BusinessModulePlugin`
- 每个模块声明多个 `BusinessRule`
- 通过 `order` + `runAfterRuleIds()` 同时控制规则优先级和依赖顺序
- 多模块共享 `BusinessFlowContext` 完成协同执行

默认示例流程：销售下单 -> 库存占用 ->（库存不足则触发采购请购）-> 仓库创建拣货任务 -> 销售输出结果。
