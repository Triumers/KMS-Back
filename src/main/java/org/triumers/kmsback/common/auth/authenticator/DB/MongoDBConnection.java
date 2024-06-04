package org.triumers.kmsback.common.auth.authenticator.DB;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBConnection {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "otp_db";
    private static final String COLLECTION_NAME = "employees";

    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    public MongoDBConnection() {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            collection = database.getCollection(COLLECTION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to MongoDB", e);
        }
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
