package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class StatusMilestone implements Job{
	public void execute(JobExecutionContext context) {
	    ConexaoMongo mongo = new ConexaoMongo();
	    Conexao mysqlAux = new Conexao();
		
		
		Document updates;
		Bson filtro;
		List<Bson> filtros = new ArrayList<>();
		Document update;
		String campoNome;
		ResultSet rs;
		
		
			rs = mysqlAux.Consulta("select field_name,rollout_id,rollout_nome,empresa from rollout_campos where field_type='Milestone' order by rollout_id");
			try {
				if(rs.next()) {
					rs.beforeFirst();
					while(rs.next()) {
						System.out.println("Atualizando status de atividades finalizadas");
						updates = new Document();
						update = new Document();
						filtros = new ArrayList<>();
						campoNome = rs.getString("field_name");
						filtro = Filters.eq("Empresa",rs.getInt("empresa"));
						filtros.add(filtro);
						filtro = Filters.eq("rolloutId",rs.getString("rollout_nome"));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.eq("Milestone",campoNome));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.not(Filters.eq("edate_"+campoNome,null)));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.not(Filters.eq("edate_"+campoNome,"")));
						filtros.add(filtro);
						updates.append("Milestone.$."+"status_"+campoNome, "Finalizada");
						update.append("$set", updates);
						mongo.AtualizaMuitos("rollout", filtros, update);
						System.out.println("Atualizando status de atividades iniciadas e nao finalizadas");
						updates = new Document();
						update = new Document();
						filtros = new ArrayList<>();
						campoNome = rs.getString("field_name");
						filtro = Filters.eq("Empresa",rs.getInt("empresa"));
						filtros.add(filtro);
						filtro = Filters.eq("rolloutId",rs.getString("rollout_nome"));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.eq("Milestone",campoNome));
						filtros.add(filtro);
						
						filtro = Filters.elemMatch("Milestone", Filters.eq("edate_"+campoNome,""));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.not(Filters.eq("sdate_"+campoNome,null)));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.not(Filters.eq("sdate_"+campoNome,"")));
						filtros.add(filtro);
						updates.append("Milestone.$."+"status_"+campoNome, "iniciada");
						update.append("$set", updates);
						mongo.AtualizaMuitos("rollout", filtros, update);
						System.out.println("Atualizando status de atividades nao iniciadas");
						updates = new Document();
						update = new Document();
						filtros = new ArrayList<>();
						campoNome = rs.getString("field_name");
						filtro = Filters.eq("Empresa",rs.getInt("empresa"));
						filtros.add(filtro);
						filtro = Filters.eq("rolloutId",rs.getString("rollout_nome"));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.eq("Milestone",campoNome));
						filtros.add(filtro);
						
						filtro = Filters.elemMatch("Milestone", Filters.eq("edate_"+campoNome,""));
						filtros.add(filtro);
						filtro = Filters.elemMatch("Milestone", Filters.eq("sdate_"+campoNome,""));
						filtros.add(filtro);
						
						updates.append("Milestone.$."+"status_"+campoNome, "Nao Iniciada");
						update.append("$set", updates);
						mongo.AtualizaMuitos("rollout", filtros, update);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		mongo.fecharConexao();
		mysqlAux.fecharConexao();
	   
	}
}
