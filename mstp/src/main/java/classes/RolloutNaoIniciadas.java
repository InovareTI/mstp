package classes;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
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

public class RolloutNaoIniciadas implements Job{
	public void execute(JobExecutionContext context) {
	    ConexaoMongo mongo = new ConexaoMongo();
	    Conexao mysql = new Conexao();
	    System.out.println("INICIANDO ALERTAS DE TICKETS NAO ABERTOS!");
	    Calendar calendar = Calendar.getInstance();
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    
	    Document po = new Document();
	    Integer contador= 0;
	    String resp="";
	    String mensagem="";
	    try {
	    Semail email= new Semail();
	    Bson filtro2;
	    List<Bson> filtros2 = new ArrayList<>();
	    filtro2=Filters.eq("PO_ATIVA","Y");
	    filtros2.add(filtro2);
	    filtro2=Filters.not(Filters.eq("Requested Qty","0"));
	    filtros2.add(filtro2);
	    ResultSet rs = mysql.Consulta("select usuarios.* from usuarios,perfil_funcoes where usuarios.ATIVO='Y' and usuarios.id_usuario=perfil_funcoes.usuario_id and perfil_funcoes.funcao_nome='POManager' order by usuarios.empresa");
	    String empresa="";
	    if(rs.next()) {
			empresa=rs.getString("empresa");
			rs.beforeFirst();
    		while(rs.next()) {
    			 	filtro2=Filters.eq("Empresa",Integer.parseInt(empresa));
    			    filtros2.add(filtro2);
    				FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("PO", filtros2);
    				MongoCursor<Document> resultado = findIterable.iterator();
    				if(resultado.hasNext()) {
    					resp="De: MSTP - Monitoramento Rollout";
    					String email_msg="Prezado "+rs.getString("nome")+", \n \n Informamos que as PO's abaixo se encontram sem Ticket.\n\n ";
    					while(resultado.hasNext()) {
    						po=resultado.next();
    						mensagem="Item de PO sem Ticket:\nSite ID: "+po.getString("Site Name")+"\n"+"Atividade: "+po.getString("Item Description");
    						contador=contador+1;
    						mensagem=resp+"\n\n"+mensagem;
    						mensagem=mensagem+"\n\n Data Hora de Envio: "+time;
    						try {
								String jsonResponse;
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
										+   "\"filters\": [{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+rs.getString("id_usuario")+"\"},{\"operator\": \"OR\"},{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+rs.getString("id_usuario").toLowerCase()+"\"},{\"operator\": \"OR\"}, {\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+rs.getString("id_usuario").toUpperCase()+"\"}],"
										+   "\"data\": {\"foo\": \"bar\"},"
										+   "\"contents\": {\"en\": \""+mensagem+"\"}"
										+ "}";
								byte[] sendBytes = strJsonBody.getBytes("UTF-8");
								con.setFixedLengthStreamingMode(sendBytes.length);
							    						  // System.out.println("controle4");
								OutputStream outputStream = con.getOutputStream();
								outputStream.write(sendBytes);
		
								int httpResponse = con.getResponseCode();
							    						  // System.out.println("httpResponse: " + httpResponse);
		
								if (  httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
									Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
								    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
								    scanner.close();
								}else{
								    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
								    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
								    scanner.close();
								 }
								email_msg=email_msg+"PO NO: "+po.getString("PO NO")+"\nItem: "+po.getString("Item Description")+"\nSite: "+po.getString("Site Name")+"\nQuantidade: "+po.getString("Requested Qty")+"\n\n____________________________________________\n\n";
		    				
		    				
							 } catch(Throwable t) {
								 t.printStackTrace();
							 }
    					}
    					email.enviaEmailSimples(rs.getString("email"), "MSTP - Notificação de PO SEM TICKET",email_msg+"\n\nNão possuem um Tickets Associados, logo sugerimos a abertura de um tickect para que mesma possa ser processada \nOperação realizada em: "+time+" \nCaso não seja o responsável correto  solicitamos que entre em contato com o administrador do sistema para sua empresa! \n \n \n Esse é um email Automático gerado pelo sistema. Favor não responder!");
		    		}
		    		
	    		}
				}//fim do while
			
					    
	    System.out.println("Finalizando alertas de TAREFAS Nao INICIADAS DO ROLLOUT - Encontrou "+contador+" tarefas atrasadas!");
	    mongo.fecharConexao();
	    mysql.fecharConexao();
	    }catch(Exception e){
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getCause());
	    	System.out.println(e.getLocalizedMessage());
	    }
	}
}
