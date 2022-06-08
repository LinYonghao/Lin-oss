# Lin-OSS
>Lin-oss is an open source high performance Object Storage Service System
>It is convenient to provide file storage and management for applications, allowing developers to focus on the development of business logic


## Dependency
You can install these dependencies by docker-compose.yaml 
- Mysql
- [Influxdb](https://www.influxdata.com/) (a sequential database)
- Redis
- FastDFS

## Module Description
- lin-oss-common :Common data interfaces and utils 
- lin-oss-core : Core business logic,
- lin-oss-manager: a manager system for OSS,which include data statistics,Management of data bucket,user,object etc.
- lin-oss-starter: a springboot component for quickly start a project with lin-oss


## Start
### 1.set environment
    LIN_DFS_HOST: fastdfs hostr