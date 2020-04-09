# activiti demo示例

### 通过本demo实例你可以学到
- 流程表关系
- 流程部署启动，任务提交
- 人员或者角色任务列表获取，任务提交
- 流程变量设计与获取（task，实例，启动，local）
- 排他网关（ExclusiveGateway）
- 并行网关（ParallelGateway）
- 

###
- 本项目主要通过流程示例项目主要代码与流程设计都在 src/test 目录下
- 流程设计图在src/resources下流程设计图建议在Eclipse中进行设计,IDEA里的bpmn设计器不好用。

###  Activiti 工作流学习

Activiti工作流以Task驱动工作模式定义-->发布-->执行-->监控-->优化-->重新定义-->发布特点- 持久化
- activiti采用Mybatis与数据库进行交互，总共25张表
- 七大Serrvice引擎接口，引擎通过ProcessEngine获取，

###  数据库表格表前缀类型含义
- ACT_RE_*repository包含了流程定义和流程静态资源 （图片，规则，等等）
- ACT_RU_*runtime运行时的表，包含流程实例，任务，变量，异步任务等运行中的数据,只在流程实例执行过程中保存数据，在流程结束时删除记录, 这样运行时表可以一直很小速度很快。
- ACT_ID_*identity包含身份信息，比如用户，组等
- ACT_HI_*history包含历史数据，比如历史流程实例， 变量，任务等
- ACT_GE_*general用于不同场景下，如存放资源文件
###  资源库流程规则表
- act_re_deployment 部署信息表
- act_re_model 流程设计模型部署表
- act_re_procdef 流程定义数据表
运行时数据库表
- act_ru_execution 运行时流程执行实例表
- act_ru_identitylink 运行时流程人员表，主要存储任务节点与参与者的相关信息
- act_ru_task 运行时任务节点表
- act_ru_variable 运行时流程变量数据表
- act_ru_job 工作数据表
- act_ru_event_subscr 事件描述表
历史数据库表
- act_hi_actinst 历史节点表
- act_hi_attachment 历史附件表
- act_hi_comment 历史意见表
- act_hi_identitylink 历史流程人员表
- act_hi_detail 历史详情表，提供历史变量的查询
- act_hi_procinst 历史流程实例表
- act_hi_taskinst 历史任务实例表
- act_hi_varinst 历史变量表
组织机构表
- act_id_group 用户组信息表
- act_id_info 用户扩展信息表
- act_id_membership 用户与用户组对应信息表
- act_id_user 用户信息表
通用数据表
- act_ge_bytearray 二进制数据表
- act_ge_property 属性数据表存储整个流程引擎级别的数据,初始化表结构时，会默认插入三条记录

### 引擎介绍
1. RepositoryService：流程仓库Service，用于管理流程仓库，例如部署删除读取流程资源
3. RuntimeService：运行时service，在流程运行时对流程实例进行管理与控制。
4. TaskService：任务管理，对流程任务进行管理，例如任务提醒、任务完成和创建任务等。
5. IdentityService：身份Service，组织机构管理，提供对流程角色数据进行管理的API，这些角色数据包括用户组、用户及它们之间的关系。
6. ManagementService：引擎管理Service，提供对流程引擎进行管理和维护的服务。
7. HistoryService：历史Service，对流程的历史数据进行操作，包括查询、删除这些历史数据。
8. FormService：表单服务，可选服务



