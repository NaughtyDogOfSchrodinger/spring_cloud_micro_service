FROM openjdk:8-jdk-alpine

MAINTAINER jianghu <a15174027322@icloud.com>

# 拷贝文件到容器
ADD ./seata-server-0.6.1.tar /work/server


# Set environment variables.
ENV HOME /work

# 开放端口
EXPOSE 8091

# 配置容器启动后执行的命令
ENTRYPOINT [ "sh", "/work/server/distribution/bin/seata-server.sh", "8091", "file"]

# Define working directory.
WORKDIR /work

# Define default command.
CMD ["bash"]
