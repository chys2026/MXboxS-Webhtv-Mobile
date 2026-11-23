# GitHub Actions 自动编译指南

## 项目概览

本项目支持通过 GitHub Actions 自动编译 APK，无需本地环境。

---

## 快速开始（只需 3 步）

### 1. Fork 本仓库到 GitHub

点击 GitHub 页面右上角的 "Fork" 按钮。

### 2. 配置 Secrets（可选）

如需生成正式签名 APK，在仓库 Settings → Secrets and variables → Actions 中添加：

| Secret 名称 | 说明 |
|-------------|------|
| `RELEASE_KEYSTORE_BASE64` | keystore 文件的 base64 编码内容 |
| `RELEASE_KEY_ALIAS` | key alias |
| `RELEASE_STORE_PASSWORD` | keystore 密码 |
| `RELEASE_KEY_PASSWORD` | key 密码（可省略，默认复用 store password）|

如果不配置签名，将自动生成未签名的 Release APK（仅用于测试）。

### 3. 触发编译

有两种方式触发：

**方式 A: Push 代码时自动编译**
```bash
git add .
git commit -m "Initial commit"
git push origin main
```

**方式 B: 手动触发工作流**
访问仓库 Actions 标签页，点击 "Build Android App" → "Run workflow"

---

## 输出产物

编译完成后，可在 Actions 页面的 Artifacts 中下载：

| Artifact | 说明 | 保留时间 |
|----------|------|----------|
| MXboxS-TV-debug | Debug APK（免签名，可直接安装） | 30 天 |
| MXboxS-TV-release | Release APK（需签名或仅用于测试） | 90 天 |

APK 路径示例：`app/build/outputs/apk/mobile/debug/*.apk`

---

## 版本发布（Tag）

当需要正式发布时，推送 tag 触发带签名的 Release 构建：

```bash
git tag v5.6.0
git push origin v5.6.0
```

工作流会自动创建 GitHub Release 并上传 APK。

---

## 常见问题

### Q: 为什么编译失败？

1. **检查 JDK 版本**: 当前项目要求 Java 17+，GitHub Actions 使用默认的 Temurin JDK 17
2. **检查依赖下载**: 中国大陆用户可能需要代理，在 workflow_dispatch 中输入代理参数
3. **检查签名配置**: Release 构建需要正确的 keystore secrets

### Q: 如何修改 APK 包名？

编辑 `app/build.gradle`:
```groovy
applicationId "com.silent.android.mxboxs"
```

### Q: 如何添加新的 flavor？

编辑 `app/build.gradle` 中的 `productFlavors` 块，或联系开发者修改构建脚本。

---

## Workflow 文件位置

```
.github/workflows/build.yml
```

可直接编辑此文件自定义编译行为。

---

## 贡献指南

欢迎提交 PR！请确保：

1. 代码风格符合 Google Java Style Guide
2. 修改后本地编译通过
3. CI 流程全部 green

---

*最后更新: 2026-11-23*
