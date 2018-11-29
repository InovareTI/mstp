package com.inovareti.mstp.bd;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConexaoMongo {

	public ConexaoMongo() {
		
		String user;     // the user name
		String source;   // the source where the user is defined
		char[] password; // the password as a character array
		
		user="mstpuser_DB";
		source="mstpDB";
		password="Xmqxf9qdCXusVYsH".toCharArray();	
	 MongoCredential credential = MongoCredential.createCredential(user, source, password);

		 
	   MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://mstpuser_DB:Xmqxf9qdCXusVYsH@node21828-inovareti.jelastic.saveincloud.net/?authSource=mstpDB"));
		 
		MongoDatabase db = mongoClient.getDatabase(source);
		System.out.println(mongoClient.getConnectPoint());
		MongoCollection<Document> collection = db.getCollection("rollout");
		System.out.println("Conectado");
		Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
		collection.insertOne(doc);
		
	}
	
	}
	
	

