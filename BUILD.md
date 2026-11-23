# MXboxS + Webhtv 合成项目 - 编译指南

## 项目信息
- **名称**: MXboxS TV
- **包名**: com.silent.android.mxboxs
- **版本**: 5.6.0
- **最低 SDK**: 21 (Android 5.0)
- **目标 SDK**: 34 (Android 14)

## 已完成修复 ✅

| 项目 | 状态 |
|------|------|
| 包名统一替换 (com.fongmi/android.tv → com.silent.android.mxboxs) | ✅ |
| 移除 leanback/TV flavor，仅保留 mobile | ✅ |
| 移除 abi flavor dimensions | ✅ |
| 添加 thunder/jianpian/zlive/tvbus/forcetech 模块 | ✅ |
| 更新 settings.gradle 引用新模块 | ✅ |
| 更新 app/build.gradle 依赖配置 | ✅ |
| 清理 leanback 源目录 | ✅ |
| 应用名称更新为 "MXboxS TV" | ✅ |

## 新增功能（来自 MXboxS）

### 特色模块
1. **thunder** - 迅雷下载支持
   - 包名: com.ghost.thunder
   - 依赖: :catvod, coreLibraryDesugaring

2. **jianpian** - 荐片 P2P 流媒体
   - 包名: com.p2p
   - 依赖: :catvod

3. **zlive** - ZLive 直播源解析
   - 包名: com.east.android.zlive
   - 依赖: JNA native library

4. **tvbus** - TVBus 引擎
   - 包名: com.tvbus.engine
   - 依赖: :catvod

5. **forcetech** - ForceTech IPTV
   - 包名: com.forcetech
   - 依赖: :catvod

### 独有 Activity
- KamiActivity - 会员卡激活界面
- SettingAdvancedActivity - 高级设置
- SettingAiActivity - AI 相关设置
- KamiUtil - 卡密工具类
- Notify - 通知工具类
- Task - 后台任务管理

## 编译步骤

### 1. 准备环境
```bash
# 确保已安装 Android SDK
# 推荐使用 Android Studio 打开项目
```

### 2. 配置签名（可选）
创建 `local.properties` 文件：
```properties
sdk.dir=/path/to/android-sdk
storeFile=/path/to/your-keystore.jks
keyAlias=my-key-alias
storePassword=your-store-password
keyPassword=your-key-password
```

### 3. 同步项目
在 Android Studio 中:
- File → Sync Project with Gradle Files
- 或使用命令行: `./gradlew sync`

### 4. 编译 Debug 版本
```bash
./gradlew assembleMobileDebug
```

### 5. 编译 Release 版本（需签名）
```bash
./gradlew assembleMobileRelease
```

## 已知问题与解决方案

### 问题 1: R 类引用错误
如果编译报错找不到 R 类，检查是否有 import 语句使用了旧包名:
```bash
# 搜索并替换
find . -name "*.java" -exec sed -i 's/com\.fongmi\.android\.tv/com\.silent\.android\.mxboxs/g' {} \;
```

### 问题 2: NDK 库缺失
部分模块 (zlive/tvbus/forcetech) 使用 armeabi-v7a native 库。
如果需要编译 arm64-v8a 版本，需要确认这些库是否提供对应 ABI。

### 问题 3: 依赖冲突
如果发现依赖冲突，检查:
- catvod 模块版本兼容性
- jna/native library 版本

## 项目结构
```
MXboxS-Webhtv-Mobile/
├── app/                    # 主应用模块
├── catvod/                 # CatVod 数据源框架
├── chaquo/                 # Python 运行时
├── nodejs/                 # Node.js 运行时
├── quickjs/                # QuickJS 脚本引擎
├── thunder/                # 迅雷下载模块 ⭐
├── jianpian/               # 荐片 P2P 模块 ⭐
├── zlive/                  # ZLive 直播模块 ⭐
├── tvbus/                  # TVBus 引擎模块 ⭐
└── forcetech/              # ForceTech IPTV 模块 ⭐
```

## 后续优化建议
1. [ ] 测试各新增模块的功能完整性
2. [ ] 清理未使用的资源文件
3. [ ] 优化 proguard-rules.pro 混淆规则
4. [ ] 添加 lint 检查和代码规范扫描
5. [ ] 编写单元测试覆盖核心功能
