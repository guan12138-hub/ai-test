# 团队协作规范与Git工作流

## 一、Git分支策略
- main: 生产分支，只合并经过测试的代码
- develop: 开发分支，日常开发的基础分支
- feature/xxx: 功能分支，每人独立开发
- fix/xxx: 修复分支

## 二、工作流程
1. 克隆仓库: git clone [repo-url]
2. 创建功能分支: git checkout -b feature/模块名
3. 开发并提交: git add . -> git commit -m 'feat: 功能描述'
4. 推送: git push origin feature/模块名
5. 创建PR: 在GitHub上创建Pull Request到develop
6. 代码审查: 至少1人review后合并
7. 发布: develop合并到main并打tag

## 三、Commit规范
- feat: 新功能 (feat: 添加食材入库功能)
- fix: 修复bug (fix: 修复保质期预警)
- docs: 文档 (docs: 更新API文档)
- refactor: 重构 (refactor: 重构AI服务层)

## 四、团队分工
| 角色 | 负责内容 |
|------|---------|
| 项目经理 | 项目规划、文档编写、答辩PPT |
| 后端A | 用户认证、食材管理接口 |
| 后端B | AI模块、采购订单、报表 |
| 前端Web | Vue3页面开发 |
| 小程序 | 微信小程序开发 |
| AI集成 | Coze平台对接和配置 |
| 测试部署 | 系统测试、环境部署 |

## 五、代码规范
- Java: 大驼峰类名、小驼峰方法/变量
- Vue: 大驼峰组件名、连字符路由
- 数据库: 小写下划线表名/字段名
- API: RESTful风格, /api/资源名

## 六、项目结构
详见 product-manual.md
