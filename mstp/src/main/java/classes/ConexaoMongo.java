package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoWriteException;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

public class ConexaoMongo {
	
	private MongoDatabase db;
	private MongoClient mongoClient;
	private MongoCredential credential;
	public ConexaoMongo() {
		
	
	String host = "mongodb23525-inovareti.jelastic.saveincloud.net";
	String dbname = "mstpDB";
    String user = "mstpwebDB";
    String password = "Xmqxf9qdCXusVYsH";

    //System.out.println("host: " + host + "\ndbname: " + dbname + "\nuser: " + user + "\npassword: " + password);

     credential = MongoCredential.createCredential(user, dbname, password.toCharArray());
     mongoClient = new MongoClient(new ServerAddress(host), Arrays.asList(credential));

     db = mongoClient.getDatabase(dbname);
	}
	public boolean InserirSimpels(String Collection,Document document) {
		
		MongoCollection<Document> coll = db.getCollection(Collection);
		try {
		coll.insertOne(document);
		
		}catch(MongoWriteException e) {
			System.out.println("Inserção NOK!");
			e.getMessage();
		}
		return true;
	}
	public FindIterable<Document> ConsultaSimplesSemFiltro(String Collection){
		FindIterable<Document> findIterable = db.getCollection(Collection).find(new Document());
		return findIterable;
	}
	
	public FindIterable<Document> ConsultaSimplesComFiltro(String Collection,String campo,String valor){
		System.out.println("Realizando consulta em:");
		System.out.println(Collection);
		System.out.println(campo);
		System.out.println(valor);
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.eq(campo, valor));
		return findIterable;
	}
	public FindIterable<Document> ConsultaSimplesComFiltroDate(String Collection,String campo,Date valor){
		System.out.println("Realizando consulta em:");
		System.out.println(Collection);
		System.out.println(campo);
		System.out.println(valor);
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.gte(campo, valor));
		return findIterable;
	}
	public FindIterable<Document> ConsultaComplexaArray(String Collection,String campo,List<String> valores){
		System.out.println("Realizando consulta em:");
		System.out.println(Collection);
		System.out.println(campo);
		//System.out.println(valor);
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.in(campo, valores));
		
		return findIterable;
	}
	public FindIterable<Document> ConsultaComplexaArrayRecID(String Collection,String campo,List<Integer> valores){
		System.out.println("Realizando consulta em:");
		System.out.println(Collection);
		System.out.println(campo);
		//System.out.println(valor);
		FindIterable<Document> findIterable = db.getCollection(Collection).find(Filters.in(campo, valores));
		
		return findIterable;
	}
	public FindIterable<Document> ConsultaComplexaComFiltros(String Collection,List<String>site,List<String>campos,List<String>autor){
		
		FindIterable<Document> findIterable = null;
		if(site.toArray().length>0) {
			if(autor.toArray().length>0) {
				if(campos.toArray().length>0) {
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("SiteID", site),Filters.in("update_by", autor),Filters.in("Campo", campos)));
				}else {
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("SiteID", site),Filters.in("update_by", autor)));
				}
			}else if(campos.toArray().length>0) {
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("SiteID", site),Filters.in("Campo", campos)));
			}else {
				findIterable = db.getCollection(Collection).find(Filters.in("SiteID", site));
			}
			findIterable = findIterable.filter(Filters.in("SiteID", site));
		}else if(autor.toArray().length>0) {
			if(campos.toArray().length>0) {
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.in("update_by", autor),Filters.in("Campo", campos)));
			}else {
				findIterable = db.getCollection(Collection).find(Filters.in("update_by", autor));
			}
		}else if(campos.toArray().length>0) { 
			findIterable = db.getCollection(Collection).find(Filters.in("Campo", campos));
		}
		
		
		return findIterable;
	}
	public FindIterable<Document> ConsultaComplexaComFiltro(String Collection,List<Integer> recid,String tipo_valor,String operador,String campo,String valor){
		System.out.println("Realizando consulta em:");
		System.out.println(Collection);
		System.out.println(campo);
		System.out.println(valor);
		System.out.println(operador);
		
		FindIterable<Document> findIterable=null;
		if(tipo_valor.equals("Data")) {
			if(recid.size()>0) {
				if(operador.equals("GREATER_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.gte(campo, checa_formato_data(valor)),Filters.in("recid", recid)));
				}else if(operador.equals("LESS_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.and(Filters.lte(campo, checa_formato_data(valor)),Filters.in("recid", recid)));
				}else if(operador.equals("EQUAL")) {
				findIterable = db.getCollection(Collection).find(Filters.and(Filters.eq(campo, checa_formato_data(valor)),Filters.in("recid", recid)));
				}
			}else {
				if(operador.equals("GREATER_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.gte(campo, checa_formato_data(valor)));
				}else if(operador.equals("LESS_THAN_OR_EQUAL")) {
					findIterable = db.getCollection(Collection).find(Filters.lte(campo, checa_formato_data(valor)));
				}else if(operador.equals("EQUAL")) {
				findIterable = db.getCollection(Collection).find(Filters.eq(campo, checa_formato_data(valor)));
				}
			}
		}
		
		return findIterable;
	}
	public Date checa_formato_data(String data) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d1=format.parse(data);
			return d1;
		}catch (ParseException e) {
			//System.out.println(data + " - Data inválida");
			return null;
			
		} 
		
	}
	public boolean RemoverMuitosSemFiltro(String Collection) {
		MongoCollection<Document> coll = db.getCollection(Collection);
		try {
		coll.deleteMany(new Document());
		System.out.println("Todos os documentos foram removidos, de "+ Collection );
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	public boolean AtualizaUm(String Collection,Document campo_condicao, Document campo_valor) {
		MongoCollection<Document> coll = db.getCollection(Collection);
		UpdateResult resultado;
		System.out.println("chegou na funcao do update");
		//coll.findOneAndUpdate(campo_condicao, campo_valor);
		resultado=coll.updateOne(campo_condicao, campo_valor);
		System.out.println(resultado.getMatchedCount()+ " - Foram encontrados no MongoDB");
		System.out.println(resultado.getModifiedCount() + " - Foram modificados no MongoDB");
		return true;
	}
	public void fecharConexao() {
		 this.mongoClient.close();
		 System.out.println("Conexão encerrada com sucesso");
	}
}
