# FortuneTelling

使用技术：springboot + shiro + mybatis-plus

功能：调用第三方算命api、登录注册、计费功能

使用指南

    1.修改application.yml的url为本地；并创建user表，包含id, name, pwd, salt, balance字段

    2.修改FateUtils中的APP_KEY为自己的，才能使用第三方提供的算命服务

    3.修改UserController两个接口中的FileWriter的地址为自己本地

    4.删除com.baizhi.logs下的所有文件。因为程序会自动存储每个用户算命的结果到自动生成的对应文件

---- 注：前端页面为我队友@xiongpu1所著 ----

    

