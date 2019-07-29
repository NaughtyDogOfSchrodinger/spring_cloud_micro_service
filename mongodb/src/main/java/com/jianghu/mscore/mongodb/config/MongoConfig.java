//package com.jianghu.mscore.mongodb.config;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@PropertySource("classpath:config/mongo.properties")
//public class MongoConfig {
//
//    @Value("${mongodb.custom.host}")
//    private String host;
//
//    @Value("${mongodb.custom.port}")
//    private Integer port;
//
////    @Value("${mongodb.custom.replica-set}")
////    private String replicaSet;
//
//    @Value("${mongodb.custom.username}")
//    private String username;
//
//    @Value("${mongodb.custom.password}")
//    private String password;
//
//    @Value("${mongodb.custom.database}")
//    private String database;
//
//    @Value("${mongodb.custom.authentication-database}")
//    private String authenticationDatabase;
//
//    @Value("${mongodb.custom.connections-per-host}")
//    private Integer connectionsPerHost = 10;
//
//    @Value("${mongodb.custom.min-connections-per-host}")
//    private Integer minConnectionsPerHost = 2;
//
//    // 覆盖默认的MongoDbFactory
//    @Bean
//    MongoDbFactory mongoDbFactory() {
//        //客户端配置（连接数、副本集群验证）
//        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
//        builder.connectionsPerHost(connectionsPerHost);
//        builder.minConnectionsPerHost(minConnectionsPerHost);
////        if (replicaSet != null) {
////            builder.requiredReplicaSetName(replicaSet);
////        }
//        MongoClientOptions mongoClientOptions = builder.build();
//
//        // MongoDB地址列表
//        List<ServerAddress> serverAddresses = new ArrayList<>();
//        ServerAddress serverAddress = null;
//        try {
//            serverAddress = new ServerAddress(host, port);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        serverAddresses.add(serverAddress);
//
//        System.out.println("serverAddresses:" + serverAddresses.toString());
//
//        // 连接认证
//        List<MongoCredential> mongoCredentialList = new ArrayList<>();
//        if (username != null) {
//            mongoCredentialList.add(MongoCredential.createScramSha1Credential(
//                    username,
//                    authenticationDatabase != null ? authenticationDatabase : database,
//                    password.toCharArray()));
//        }
//        System.out.println("mongoCredentialList:" + mongoCredentialList.toString());
//
//        //创建客户端和Factory
//        MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredentialList, mongoClientOptions);
//        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, database);
//
//        return mongoDbFactory;
//    }
//}
