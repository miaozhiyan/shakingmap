package com.douyu.supermap.shakingmap.config.mongodb;

import com.mongodb.Mongo;

import java.util.List;

/**
 * 原始方式
 */
public class MongoDataBase {

    public Mongo mongoDB(){
        Mongo mongo = new Mongo("127.0.0.1:27017");
        List<String> list =mongo.getDatabaseNames();
        list.forEach(s-> System.out.println(s));
        return mongo;
    }

}
