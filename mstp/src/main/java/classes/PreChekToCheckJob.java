package classes;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.apache.commons.mail.EmailException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class PreChekToCheckJob implements Job{
	public void execute(JobExecutionContext context) {
	    ConexaoMongo mongo = new ConexaoMongo();
	    Locale brasil = new Locale("pt", "BR");
	    DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, brasil);
	    Calendar calendar = Calendar.getInstance();
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    System.out.println("Iniciando análise de Ticket em preAgendamento de QC" + time);
	    Document ticket;
	    Document update;
	    Document comandoUpdate;
	    Document filtroUpdate;
	    Document checklist;
	    List<Bson> lista = new ArrayList<>();
	    Bson filtro;
		filtro=Filters.eq("TICKET_STATE","precheck");
		lista.add(filtro);
		FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("TICKETS", lista);
		MongoCursor<Document> resultado = findIterable.iterator();
		if(resultado.hasNext()) {
			List<Bson> lista2 = new ArrayList<>();
			while(resultado.hasNext()) {
				ticket=resultado.next();
				lista2 = new ArrayList<>();
				filtro=Filters.eq("TICKET_NUM",ticket.getString("TICKET_NUM"));
				FindIterable<Document> findIterable2 = mongo.ConsultaCollectioncomFiltrosLista("TICKET_CHECKLIST", lista2);
				MongoCursor<Document> resultado2 = findIterable.iterator();
				if(resultado2.hasNext()) {
					checklist=resultado.next();
					List<Document> atividades = (List<Document>) checklist.get("Atividades");
					for(int indice=0;indice<atividades.size();indice++) {
						if(atividades.get(indice).getString("nome").equals("TICKET_QC_DATE")) {
							Calendar hoje = Calendar.getInstance();
							Calendar qc = Calendar.getInstance();
							qc.setTime(atividades.get(indice).getDate("valor_DATE"));
							if(qc.before(hoje)) {
								update = new Document();
								comandoUpdate= new Document();
								filtroUpdate=new Document();
								filtroUpdate.append("TICKET_NUM", ticket.getString("TICKET_NUM"));
								update.append("TICKET_STATE", "check");
								comandoUpdate.append("$set", update);
								mongo.AtualizaMuitos("TICKETS", filtroUpdate, comandoUpdate);
								System.out.println("Movendo tickect - "+ ticket.getString("TICKET_NUM"));
							}
						}
					}
				}
			}
		}
	    
	   
	    mongo.fecharConexao();
	    
	    
	}
}
