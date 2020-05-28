package classes;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.util.concurrent.TimeUnit;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class RolloutAtualizaDuracao implements Job{
	public void execute(JobExecutionContext context) {
	    ConexaoMongo mongo = new ConexaoMongo();
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    System.out.println("INICIANDO ATUALIZACAO DE DURACAO EM" + time);
	    Calendar calendar = Calendar.getInstance();
	 
	
	    Document site = new Document();
	    Document filtro = new Document();
	    Document update = new Document();
	    Document comando = new Document();
	    
	    Long dias = (long) 0 ;
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
	    	
	    	List<Document> milestones=(List<Document>) site.get("Milestone");
	    	
	    	for(int indice2=0;indice2<milestones.size();indice2++) {
	    		dias=(long) 0;
	    		Document milestone=milestones.get(indice2);
	    		if(milestone.get("edate_"+milestone.getString("Milestone")) != null) {
	    			if(milestone.get("edate_"+milestone.getString("Milestone")).toString().equals("")) {
	    				if(milestone.get("sdate_"+milestone.getString("Milestone")) != null){
				    		if(!milestone.get("sdate_"+milestone.getString("Milestone")).toString().equals("")){
				    			dias=TimeUnit.MILLISECONDS.toDays(calendar.getTimeInMillis()-milestone.getDate("sdate_"+milestone.getString("Milestone")).getTime());
				    			if(dias==0) {
				    				dias=(long) 1;
				    			}
					    		
				    		}else {
				    			dias=(long) 0;
				    		}
				    	}else {
				    		dias=(long) 0;
				    	}
	    			}else {
	    				if(milestone.get("sdate_"+milestone.getString("Milestone")) != null){
				    		if(!milestone.get("sdate_"+milestone.getString("Milestone")).toString().equals("")){
				    			dias=TimeUnit.MILLISECONDS.toDays(milestone.getDate("edate_"+milestone.getString("Milestone")).getTime()-milestone.getDate("sdate_"+milestone.getString("Milestone")).getTime());
				    			if(dias==0) {
				    				dias=(long) 1;
				    			}
				    		}else {
				    			dias=(long) 0;
				    		}
	    				}else {
	    					dias=(long) 0;
	    				}
	    			}
	    		}else {
	    			
	    				if(milestone.get("sdate_"+milestone.getString("Milestone")) != null){
				    		if(!milestone.get("sdate_"+milestone.getString("Milestone")).toString().equals("")){
				    			dias=TimeUnit.MILLISECONDS.toDays(calendar.getTimeInMillis()-milestone.getDate("sdate_"+milestone.getString("Milestone")).getTime());
				    			if(dias==0) {
				    				dias=(long) 1;
				    			}
					    		
				    		}else {
				    			dias=(long) 0;
				    		}
				    	}else {
				    		dias=(long) 0;
				    	}
	    			
	    		}
	    		filtro.append("recid",site.get("recid"));
	    		filtro.append("Milestone.Milestone",milestone.getString("Milestone"));
	    		update.append("Milestone.$.duracao_"+milestone.getString("Milestone"),dias);
	    		comando.append("$set",update);
	    		mongo.AtualizaUm("rollout", filtro, comando);
	    		filtro.clear();
	    		update.clear();
	    		comando.clear();
	    	}
	    }
	    }
	    
	  
	    System.out.println("FINALIZANDO ATUALIZAÇÃO de DURACAO DAS ATIVIDADES DO ROLLOUT");
	    mongo.fecharConexao();
	    }catch(Exception e){
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getCause());
	    	System.out.println(e.getLocalizedMessage());
	    }
	}
}
