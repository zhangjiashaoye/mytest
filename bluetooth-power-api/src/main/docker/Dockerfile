FROM zhangyalong/jdk1.8-ubuntu18.04:v1
MAINTAINER zhougs <zhougs@topband.com.cn>

# 设置环境变量
ENV LANG zh_CN.UTF-8
ENV JAR_NAME=@project.build.finalName@
# 工作目录的二级目录使用根项目名
ENV LICENSE_HOME="/home/topband/@artifactId@"

#设置工作目录
WORKDIR ${LICENSE_HOME}

COPY @project.build.finalName@.jar $LICENSE_HOME/
COPY docker-entrypoint.sh $LICENSE_HOME/
RUN chmod u+x docker-entrypoint.sh

EXPOSE 18806/tcp

#配置容器启动后执行的命令
ENTRYPOINT ["./docker-entrypoint.sh"]

