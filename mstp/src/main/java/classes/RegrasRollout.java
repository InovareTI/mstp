package classes;

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

public class RegrasRollout implements Job{
	public void execute(JobExecutionContext context) {
	    ConexaoMongo mongo = new ConexaoMongo();
	   
	    System.out.println("INICIANDO Verificação de regras");
	    //Calendar calendar = Calendar.getInstance();
	    Timestamp time = new Timestamp(System.currentTimeMillis());
	    Bson filtro;
		JSONObject regra ;
		JSONArray condicao;
		JSONArray acao ;
		
		Document atualizacao ;
		Document atualizacao_comando = new Document();
		List<Bson> filtros = new ArrayList<>();
		
		filtro= Filters.eq("ATIVA","Y");
		filtros.add(filtro);
		
		FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("RegrasRollout", filtros);
		MongoCursor<Document> resultado = findIterable.iterator();
		if(resultado.hasNext()) {
			while(resultado.hasNext()) {
				 regra = new JSONObject(resultado.next().toJson());
				 condicao = regra.getJSONArray("condicao");
				 acao = regra.getJSONArray("acao");
				System.out.println("WEB MSTP - EXECUÇÃO AUTOMÁTICA DE REGRAS. REGRA - "+regra.getString("NomeRegra"));
				filtros = new ArrayList<>();
				filtro = Filters.eq("Empresa",regra.getInt("Empresa"));
				filtros.add(filtro);
				for(int indice=0;indice<condicao.length();indice++) {
					JSONObject condicaoAux = condicao.getJSONObject(indice);
					filtro = Filters.eq("rolloutId",condicaoAux.getString("rollout"));
					filtros.add(filtro);
					if(condicaoAux.getString("condicao").equals("contem")) {
						filtro = Filters.regex(condicaoAux.getString("campo"), condicaoAux.getString("valor"));
						filtros.add(filtro);
					}else if(condicaoAux.getString("condicao").equals("vazio")) {
						filtro = Filters.eq(condicaoAux.getString("campo"), "");
						filtros.add(filtro);
					}else if(condicaoAux.getString("condicao").equals("n_vazio")) {
						filtro = Filters.not((Filters.eq(condicaoAux.getString("campo"), "")));
						filtros.add(filtro);
					}
				}
				atualizacao=new Document();
				for(int indice=0;indice<acao.length();indice++) {
					JSONObject acaoAux = acao.getJSONObject(indice);
					
					if(acaoAux.getString("acao").equals("AtualizaCampoRollout")) {
						atualizacao.append(acaoAux.getString("campo"),acaoAux.getString("valor"));
						
					}
				}
				if(!atualizacao.isEmpty()) {
					atualizacao_comando.append("$set", atualizacao);
					mongo.AtualizaMuitos("rollout", filtros, atualizacao_comando);
					filtros = new ArrayList<>();
					filtro= Filters.eq("Empresa",regra.getInt("Empresa"));
					filtros.add(filtro);
					filtro= Filters.eq("ATIVA","Y");
					filtros.add(filtro);
					filtro= Filters.eq("regraID",regra.getString("regraID"));
					filtros.add(filtro);
					try {
					atualizacao=new Document();
					atualizacao_comando=new Document();
					System.out.println("Documentos reinicializados");
					atualizacao.append("UltimaExecucao",time);
					atualizacao_comando.append("$set", atualizacao);
					//System.out.println(atualizacao.toJson());
					//System.out.println(atualizacao_comando.toJson());
					mongo.AtualizaMuitos("RegrasRollout", filtros, atualizacao_comando);
					}catch(Exception e) {
						System.out.println(e.getMessage());
						System.out.println(e.getLocalizedMessage());
						System.out.println(e.getCause());
					}
				}
			}
		}
		mongo.fecharConexao();
		
	   
	}
}
