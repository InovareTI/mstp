package classes;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class RegistroFaltantes implements Job{
	public void execute(JobExecutionContext context) {
	    ConexaoMongo mongo = new ConexaoMongo();
	    System.out.println("INICIANDO alertas de Falta de Registros");
	    Calendar calendar = Calendar.getInstance();
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    calendar.add(Calendar.DAY_OF_MONTH, -3);
	    Document site = new Document();
	    Integer contador= 0;
	    String resp="";
	    String mensagem="";
	    try {
	    
	    Bson filtro2;
	    List<Bson> filtros2 = new ArrayList<>();
	    filtro2=Filters.eq("Linha_ativa","Y");
	    filtros2.add(filtro2);
	    
	    FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("rollout", filtros2);
	    MongoCursor<Document> resultado = findIterable.iterator();
	    if(resultado.hasNext()) {
	    	while(resultado.hasNext()) {
	    		site=resultado.next();
	    	
	    	//System.out.println("analisando site: "+site.getString("Site ID"));
	    	List<Document> milestones=(List<Document>) site.get("Milestone");
	    	
	    	for(int indice2=0;indice2<milestones.size();indice2++) {
	    		
	    		Document milestone=milestones.get(indice2);
	    		if(milestone.get("edate_"+milestone.getString("Milestone")) != null) {
	    			if(milestone.get("edate_"+milestone.getString("Milestone")).toString().equals("")) {
				    	if(milestone.get("sdate_"+milestone.getString("Milestone")) != null){
				    		if(!milestone.get("sdate_"+milestone.getString("Milestone")).toString().equals("")){
					    		if(milestone.getDate("sdate_"+milestone.getString("Milestone")).before(calendar.getTime())) {
					    			if(milestone.get("resp_"+milestone.getString("Milestone"))!=null) {
					    				if(!milestone.getString("resp_"+milestone.getString("Milestone")).equals("")) {
					    					System.out.println(site.get("Site ID")+"-"+milestone.getString("Milestone")+"-"+milestone.get("resp_"+milestone.getString("Milestone")));
					    					resp="De: MSTP - Monitoramento Rollout";
					    					mensagem="Tarefa atrada:\nSite ID: "+site.get("Site ID")+"\n"+"Atividade: "+milestone.getString("Milestone");
					    					contador=contador+1;
					    					mensagem=resp+"\n\n"+mensagem;
					    					mensagem=mensagem+"\n\n Data Hora de Envio: "+time;
					    			    	try {
					    						   String jsonResponse;
					    						  // System.out.println("controle1");
					    						   URL url = new URL("https://onesignal.com/api/v1/notifications");
					    						   HttpURLConnection con = (HttpURLConnection)url.openConnection();
					    						   con.setUseCaches(false);
					    						   con.setDoOutput(true);
					    						   con.setDoInput(true);
					    						  // System.out.println("controle2");
					    						   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
					    						   con.setRequestProperty("Authorization", "Basic ZTFhZmE3YTItMDczNC00OWM3LTk0ZTgtMzUzYjg5OTY1ZGFj");
					    						   con.setRequestMethod("POST");
					    						  // System.out.println("controle3");
					    						   String strJsonBody = "{"
					    						                      +   "\"app_id\": \"ae9ad50e-520d-436a-b0b0-23aaddedee7b\","
					    						                      +   "\"filters\": [{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+milestone.get("resp_"+milestone.getString("Milestone"))+"\"}],"
					    						                      +   "\"included_segments\": [\"All\"],"
					    						                      +   "\"data\": {\"foo\": \"bar\"},"
					    						                      +   "\"contents\": {\"en\": \""+mensagem+"\"}"
					    						                      + "}";
					    						         
					    						   
					    						   //System.out.println("strJsonBody:\n" + strJsonBody);

					    						   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
					    						   con.setFixedLengthStreamingMode(sendBytes.length);
					    						  // System.out.println("controle4");
					    						   OutputStream outputStream = con.getOutputStream();
					    						   outputStream.write(sendBytes);

					    						   int httpResponse = con.getResponseCode();
					    						  // System.out.println("httpResponse: " + httpResponse);

					    						   if (  httpResponse >= HttpURLConnection.HTTP_OK
					    						      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
					    						      Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
					    						      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
					    						      scanner.close();
					    						   }
					    						   else {
					    						      Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
					    						      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
					    						      scanner.close();
					    						   }
					    						   //System.out.println("jsonResponse:\n" + jsonResponse);
					    						   
					    						} catch(Throwable t) {
					    						   t.printStackTrace();
					    						}
					    				}
					    			}
					    		}
				    		}
				    	}
	    			}
	    		}
	    	}
	    }
	    }
	    
	  
	    System.out.println("Finalizando alertas de TAREFAS ATRASADAS DO ROLLOUT - Encontrou "+contador+" tarefas atrasadas!");
	    mongo.fecharConexao();
	    }catch(Exception e){
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getCause());
	    	System.out.println(e.getLocalizedMessage());
	    }
	}
}
