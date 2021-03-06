

# 纳税服务系统总结 #


纳税服务系统是我第一个做得比较大的项目(不同于javaWeb小项目),该项目系统来源于传智Java32期，十天的视频课程(想要视频的同学关注我的公众号就可以直接获取了)

我跟着练习一步一步完成需求，才发觉原来Java是这样用来做网站的，Java有那么多的类库，页面的效果(图表、日期选择器等等)是通过JavaScript组件来显示，调用后端代码来获取数据从而显示出来的。


通过这次的项目开阔了我的视野，也解决了我当初学习Java时很多的疑问，自己练习完我将项目的代码放到了GitHub中：[https://github.com/ZhongFuCheng3y/NSFW_Systen](https://github.com/ZhongFuCheng3y/NSFW_Systen)，

同时在练习的过程中也用博文记录下来了，一共7篇。

[纳税服务系统一（用户模块）【简单增删改查、日期组件、上传和修改头像】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484000&idx=2&sn=3b637234b3911d94cfb291b01329027b&chksm=ebd74361dca0ca77d9208dee547374613e0b8d09a49c9580580f35d0154a6e116dc0e69a463f#rd)

[纳税服务系统二（用户模块）【POI、用户唯一性校验】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484000&idx=3&sn=01c3acd7ae24cee1c1a608df7a004e21&chksm=ebd74361dca0ca7786d54f9e70507c7472cca02c8f5d506d76ccee394b4be643fb0de1ce27a7#rd)

[纳税服务系统三(优化处理)【异常处理、抽取BaseAction】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484000&idx=4&sn=e832cad131b63b150ea372a414c82436&chksm=ebd74361dca0ca77348a604ffba6880d0302ac5052c6fe0b6ffa41d951cf7f71978500ae972a#rd)

[纳税服务系统四（角色模块）【角色与权限、角色与用户】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484000&idx=5&sn=feab77a450feaa805cb5b4e8dad22b74&chksm=ebd74361dca0ca77794e40dfb28289b658922c8e2ca34d0ac897cd7efdf53d43ca066282c180#rd)

[纳税服务系统五（登陆与系统拦截）【配置系统、子系统首页、登陆与拦截】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484000&idx=6&sn=2e51fff489d778a8eee656e4e54361d5&chksm=ebd74361dca0ca77475ed3cf2080533106dcbf61629d1dfbb43486b11b9be89b7ff2e1ed6eb8#rd)

[纳税服务系统六（信息发布管理模块）【Ueditor、异步信息交互、抽取BaseService、条件查询、分页】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484000&idx=7&sn=9ecf5932232658f8263cf9282433ec69&chksm=ebd74361dca0ca7789eb016b9cef31ac4ac9666798a15f2a5e585c4f1a6b2991ca9d727845e2#rd)

[纳税服务系统七（投诉管理模块）【显示投诉信息、处理回复、我要投诉、Quartz自动受理、统计图FusionCharts】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484000&idx=8&sn=15b91ba39935e4a38bf168726e7d22da&chksm=ebd74361dca0ca77497ebc37fe6ad26fb346e84a659774ccc411adc7262cf274bfa153f11ab4#rd)


练习时截取部分的gif：

![](https://i.imgur.com/cEVB5Vr.gif)


![](https://i.imgur.com/hVsS2xk.gif)


该项目涉及到的知识：

- Struts2,Spring,Hibernate
- JSP
- Javascript，jQuery，AJAX
- 某些工具类及组件如(FileUtil,POI，DateUtils)
- JS组件：日期组件、富文本编辑器、Fusionchart(图表组件)
- 抽取Action、Service、Dao，全局异常处理
- 权限控制的模型设计(用户、角色、权限)
- 数据回显和分页
- Hibernate逆向工程
- Quartz定时调度工具



写这篇博文的目的也是为了总结一下在这次项目中学到了什么东西，除了上面这些知识点大纲，还有**很多实用的小技巧**：

- **JSP重复的代码最好抽取**出来，用到的页面再重新`include`进去就行了
- **多用一些常用的工具类**(FileUtil,DateUtils)，简化我们的开发
- 在前端页面上想要达到一些绚丽的效果(自己很难写出来和功能比较复杂那种),**找找有没有对应的组件来使用**(日期组件、富文本编辑器、Fusionchart)。
- 同理，在后端也是一样。Java操作Excel有对应的组件POI等，Java实现"自动处理"有Quartz开源框架
- **AJAX无刷新特定能干很多的功能**
- **三层架构(Controller、Service、Dao)的代码很多都能够抽取出来**，Controller抽取的是通用的属性和方法，Service抽取的是相同的业务方法，Dao层抽取CURD通用方法
- **统一处理异常能够提高系统的健壮性**，避免异常信息给到用户看(用户也看不懂，发生错误应该提示友好的提示)
- 在设计模型对象关系时，**如果功能简单的话也不用“死板”地创建对象，创建表，看能不能用常量(集合)来代替**。
- 在修改多个数据的时候可以换个思路：**先把原有的数据删除了，再增加用户勾选的记录(达到修改的效果)**
- 在页面上定位一个标签，我们可以**使用特殊的前缀+上我们的Id**
- 在条件查询的时候，**尽量把like字段的数据放在后边，以提高我们的查询性能!**当在数据库查询时，如果某些数据是不存在但有这个字段时，想想能不能用左外连接查询
- 在Struts2指定name为root的话，我们可以指定哪个属性是返回JSON格式的。
- **要是使用到日历的话，我们想到使用Calendar这个日历类**



上面仅仅是我个人总结的要点，如果有错误的地方还请大家给我指正。


如果SSH还不太熟悉的同学可先看我其他的基础博文：

**Hibernate：**

- [Hibernate入门这一篇就够了](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483906&idx=2&sn=e8b7ab76aeb7a4895eab63f410938be9&chksm=ebd74303dca0ca1558a1015a5baf35dcfd59a59756ce52ebd8db84797b5dbff28b95929ddc09#rd)
- [Hibernate【映射】知识要点](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483906&idx=3&sn=8f21078918d5950fe468be8e62d39b91&chksm=ebd74303dca0ca1576bf3a7f44f837468eb23ac465ac94e39dd497a9423e7a3d090b566ea1b9#rd)
- [Hibernate【映射】续篇](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483906&idx=4&sn=2d8e9ee24886dc859a65605ba0ddf941&chksm=ebd74303dca0ca153fccc522fe9adf6e985e419c17717aed092235810b4685161d3df0c481c9#rd)
- [Hibernate【inverse和cascade属性】知识要点](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483906&idx=5&sn=3cd626ef18d0c1ac5d81dac393b745b8&chksm=ebd74303dca0ca15e29693486169255e83dd3a1bc1b5d373916560522095cd41a129b7051aaf#rd)
- [Hibernate【查询详解、连接池、逆向工程】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483906&idx=6&sn=e6b5be7f205ac5765d086966e446aeff&chksm=ebd74303dca0ca15bd553410e5e76c8e1457f175faeb9c61bc6a526977c98375242f1d2ab258#rd)
- [Hibernate【缓存】知识要点](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483906&idx=7&sn=a8538b4f42d29ce059204e8db8fcfaaa&chksm=ebd74303dca0ca1572d1e3d66605548e62827df7f1d03f023927867c5487c920fc26762ec5bb#rd)
- [Hibernate【与Spring整合】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483906&idx=8&sn=080a5eb746769c9f27d5f0150d177174&chksm=ebd74303dca0ca1554f00e5873eb88481f1760cbe35a814df97f768a9d0b902edc15abf65f29#rd)

**Struts2：**

- [Struts2【入门】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483925&idx=1&sn=8dad0b63d416fd01f3619a9fb573ab52&chksm=ebd74314dca0ca02d4a46fa3bced4fa5e032c6c4fafee58d3ce8ba7bdfae25e0d1ecc13bfc9c#rd)
- [Struts2【配置】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483925&idx=2&sn=e4f967ad559428f6dd11f30756b7bab6&chksm=ebd74314dca0ca02278d3de5c8802127c8b9b6d96ed44ef8f95cf333af392f6c35ea45e28580#rd)
- [Struts2【开发Action】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483925&idx=3&sn=193022078f80ce248ec44f17a148cec8&chksm=ebd74314dca0ca02381a11e06c5a878bc498908db7d8e4eebcfe431721b42893ea3301a8da6e#rd)
- [Struts2【拦截器】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483925&idx=4&sn=be49636ea484e2813ce28379e7249ff5&chksm=ebd74314dca0ca025cef018e46ccb365582dc1d71678f29a2a55a4581c8c6ab36611c527962f#rd)
- [Struts2【UI标签、数据回显、资源国际化】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483925&idx=5&sn=4455f9f652f90dbe2b061681d7a864f7&chksm=ebd74314dca0ca02eeb067d22008c3b45e473bc97d836ae477ec26e48ea0660348099bb2788a#rd)
- [Struts2【OGNL、ValueStack】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483925&idx=6&sn=7b7a54254e83e1adb3ec84e411f577f3&chksm=ebd74314dca0ca025b7a624fad2c8ac076f7dff63433a8e8606c4c04985d93ab5b4e51fdad56#rd)
- [Struts2【整合Spring】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483925&idx=7&sn=db75b295a785701d37d0e9d7db6aed4c&chksm=ebd74314dca0ca024eb749187c499d6cdaa6cd853e3fe19c3c41e052fdad084b47cd69204321#rd)


**Spring：**

- [Spring入门这一篇就够了](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483942&idx=1&sn=f71e1adeeaea3430dd989ef47cf9a0b3&chksm=ebd74327dca0ca3141c8636e95d41629843d2623d82be799cf72701fb02a665763140b480aec#rd)
- [Spring【依赖注入】就是这么简单](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483946&idx=1&sn=bb21dfd83cf51214b2789c9ae214410f&chksm=ebd7432bdca0ca3ded6ad9b50128d29267f1204bf5722e5a0501a1d38af995c1ee8e37ae27e7#rd)
- [Spring【AOP模块】就这么简单](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483954&idx=1&sn=b34e385ed716edf6f58998ec329f9867&chksm=ebd74333dca0ca257a77c02ab458300ef982adff3cf37eb6d8d2f985f11df5cc07ef17f659d4#rd)
- [Spring【DAO模块】知识要点](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483965&idx=1&sn=2cd6c1530e3f81ca5ad35335755ed287&chksm=ebd7433cdca0ca2a70cb8419306eb9b3ccaa45b524ddc5ea549bf88cf017d6e5c63c45f62c6e#rd)



**SSH整合与阅读项目：**

- [SSH【史上最详细整合】](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483954&idx=2&sn=9ced4f7fc184503b401b3b50630c13bb&chksm=ebd74333dca0ca25cf402253711d85e709fc76b1e695e264b40c24d88407e7781b1745073824#rd)
- [【SSH测试整合Demo】企业人事管理系统](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483954&idx=4&sn=fd607d79a9a12ab5e6759527c707a59e&chksm=ebd74333dca0ca254ea6d1bd080586a6422168e920a9e21aa26b2f720dab2539a999fc90eeaa#rd)
- [阅读SSH项目之ERP](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483965&idx=4&sn=de8ea16b46a8bb2598373a16482a3101&chksm=ebd7433cdca0ca2a4007cea384ccade12cb82a649d75557638c84cfccd0917d356990af15624#rd)


> 如果文章有错的地方欢迎指正，大家互相交流。习惯在微信看技术文章，想要获取更多的Java资源的同学，可以**关注微信公众号:Java3y**







