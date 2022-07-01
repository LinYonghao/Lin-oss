# Lin-OSS
>Lin-oss is an open source high performance Object Storage Service System
>It is convenient to provide file storage and management for applications, allowing developers to focus on the development of business logic


## Dependency
You can install these dependencies by docker-compose.yaml (later)

**Middleware**
- Mysql
- [Influxdb](https://www.influxdata.com/) (a sequential database)
- Redis
- FastDFS

**Framework**
- springboot
- [sa-token](https://sa-token.dev33.cn/)(a security management framework make in China)
- [fastJson2](https://github.com/alibaba/fastjson2)(a json serialization lib)


## Module Description
- lin-oss-common :Common data interfaces and utils 
- lin-oss-core : Core business logic,
- lin-oss-manager: a manager system for OSS,which include data statistics,Management of data bucket,user,object etc.
- lin-oss-starter: a springboot component for quickly start a project with lin-oss


## Start
### 1.set environment
    LIN_DFS_TRACKER_HOST=wsl;
    LIN_REDIS_HOST=wsl;
    LIN_RABBITMQ_HOST=wsl;
    LIN_TDENGINE_HOST=wsl;
    LIN_INFLUXDB_HOST=wsl
    
## TODO
1.improve the core


## preview
![](https://s2.loli.net/2022/07/01/PG1nXzdqN4Jsk5l.png)
