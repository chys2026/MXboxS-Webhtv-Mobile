# 推送到 GitHub 指南

## 当前状态 ✅
- Git 已初始化
- 5228 个文件已暂存
- 初始提交已完成: `feat: merge MXboxS + Webhtv into mobile-only project`
- 分支: main

---

## 步骤 1: 在 GitHub 创建仓库

1. 访问 https://github.com/new
2. 填写以下信息：
   - **Repository name**: `MXboxS-Webhtv-Mobile`
   - **Description**: `MXboxS + Webhtv 合成项目 - 仅手机版本 (Android)`
   - **Visibility**: Public (推荐) 或 Private
   - 取消勾选 "Initialize this repository with a README"
3. 点击 "Create repository"

---

## 步骤 2: 执行推送命令

复制并执行以下命令（替换 `[你的GitHub用户名]`）：

```bash
cd /workspace/MXboxS-Webhtv-Mobile

# 添加远程仓库
git remote add origin https://github.com/[你的GitHub用户名]/MXboxS-Webhtv-Mobile.git

# 推送代码
git push -u origin main
```

### 示例（假设用户名是 myuser）：
```bash
git remote add origin https://github.com/myuser/MXboxS-Webhtv-Mobile.git
git push -u origin main
```

---

## 如果需要 Personal Access Token (PAT)

由于命令行需要认证，使用 PAT 代替密码：

1. 访问 https://github.com/settings/tokens -> Generate new token (classic)
2. 选择过期时间（建议 90 天）
3. Scopes 勾选: `repo`
4. 点击 Generate token
5. 复制生成的 token

推送时使用：
```bash
git push -u origin main
# Username: [你的GitHub用户名]
# Password: [粘贴刚才复制的 token]
```

---

## 验证推送成功

推送完成后，访问 https://github.com/[你的GitHub用户名]/MXboxS-Webhtv-Mobile
确认文件已上传，然后点击 "Actions" 标签页查看 CI/CD 流水线运行状态。
