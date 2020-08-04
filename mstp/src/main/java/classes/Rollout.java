package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

public class Rollout {
 private CamposRollout campos;
 
 public Rollout() {
	 campos=new CamposRollout();
 }

 public String montaEstruturaRollout(Pessoa p,HttpServletRequest req, HttpServletResponse resp,Conexao mysql,ConexaoMongo mongo) {
	 	String dados_tabela="";
	 	DateFormat f3 = DateFormat.getDateTimeInstance();
	 	Timestamp time = new Timestamp(System.currentTimeMillis());
	 	ResultSet rs ;
		ResultSet rs2 ;
		String param1;
		try {
		if(p.getPerfil_funcoes().contains("RolloutManager")) {
		param1=req.getParameter("rolloutid");
		System.out.println("MSTP WEB - "+f3.format(time)+" "+ p.get_PessoaUsuario()+" carregando estrutura do rolloutID "+ param1);
		//JSONObject rollout_campos=r.getCampos().getCampos_tipo(conn,p,param1);
		//Iterator<String>campos_nomes=rollout_campos.keys();
		rs= mysql.Consulta("select * from rollout_campos where field_type='Milestone' and empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param1+"' order by ordenacao");
		JSONObject detalhesCampos = this.getCampos().getCampos_tipo(mysql, p, param1);
		String campos_aux="";
		dados_tabela="{ \"columgroup\": ["+"\n";
		if(rs.next()){
			rs.beforeFirst();
			
			//dados_tabela=dados_tabela+"{ \"caption\": \"\", \"master\": true },"+ "\n";
         
			while(rs.next()){
				if(rs.getString("field_type").equals("Milestone")){
					dados_tabela=dados_tabela+"{ \"text\": \""+rs.getString(2)+"\", \"align\": \"center\",\"name\": \""+rs.getString(2)+"\"},"+ "\n";
				
				}
			}
			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
			dados_tabela= dados_tabela+"\n"+"],";
		}else{
			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
			dados_tabela=dados_tabela+"{}],"+ "\n";
		}
		rs= mysql.Consulta("select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param1+"' order by ordenacao");
		
		dados_tabela= dados_tabela+"\n"+"\"campos\":[";
		campos_aux= "\"campos2\":[";
		rs.beforeFirst();
		if(rs.next()){
			rs.beforeFirst();
			
			while(rs.next()){
				if(rs.getString("field_type").equals("Milestone")){
					dados_tabela=dados_tabela+"{ \"name\": \"sdate_pre_"+rs.getString("field_name")+"\", \"type\": \"date\"},"+ "\n";
					dados_tabela=dados_tabela+"{ \"name\": \"edate_pre_"+rs.getString("field_name")+"\", \"type\": \"date\"},"+ "\n";
					dados_tabela=dados_tabela+"{ \"name\": \"sdate_"+rs.getString("field_name")+"\", \"type\": \"date\"},"+ "\n";
					dados_tabela=dados_tabela+"{ \"name\": \"edate_"+rs.getString("field_name")+"\", \"type\": \"date\"},"+ "\n";
					dados_tabela=dados_tabela+"{ \"name\": \"udate_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
					dados_tabela=dados_tabela+"{ \"name\": \"resp_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
					dados_tabela=dados_tabela+"{ \"name\": \"duracao_"+rs.getString("field_name")+"\", \"type\": \"int\",\"editable\":False},"+ "\n";
					dados_tabela=dados_tabela+"{ \"name\": \"status_"+rs.getString("field_name")+"\", \"type\": \"string\",\"editable\":False},"+ "\n";
					
					campos_aux=campos_aux+"{ \"text\": \"Inicio Previsto\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"sdate_pre_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100},"+ "\n";
					campos_aux=campos_aux+"{ \"text\": \"Fim Previsto\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"edate_pre_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100},"+ "\n";
					campos_aux=campos_aux+"{ \"text\": \"Inicio Real\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"sdate_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100,\"cellclassname\": \"cellclass\",\"validation\":\"validaDataActual\"},"+ "\n";
					campos_aux=campos_aux+"{ \"text\": \"Fim Real\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"edate_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100,\"cellclassname\": \"cellclass\",\"validation\":\"validaDataActual\"},"+ "\n";
					campos_aux=campos_aux+"{ \"text\": \"Anotacões\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"udate_"+rs.getString("field_name")+"\",\"columntype\": \"textbox\",\"width\":100},"+ "\n";
					campos_aux=campos_aux+"{ \"text\": \"Responsavel\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"resp_"+rs.getString("field_name")+"\",\"columntype\": \"combobox\",\"width\":200},"+ "\n";
					campos_aux=campos_aux+"{ \"text\": \"Duração(D)\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"duracao_"+rs.getString("field_name")+"\",\"editable\":False,\"width\":100,\"cellsalign\": \"center\"},"+ "\n";
					campos_aux=campos_aux+"{ \"text\": \"Status\", \"columngroup\":\""+rs.getString("field_name")+"\", \"filtertype\": \"checkedlist\",\"cellsalign\": \"center\",\"datafield\":\"status_"+rs.getString("field_name")+"\",\"editable\":False,\"columntype\": \"combobox\",\"width\":100},"+ "\n";
				}else{
					if(rs.getString("tipo").equals("Texto")){
						
						dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
						if(rs.getString("field_name").equals("Site ID")) {
							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"cellsrenderer\": \"siteMarcadoIntegrado\",\"columntype\": \"textbox\",\"width\":80,\"editable\":False},"+ "\n";
						}else if(rs.getString("field_name").equals("Equipes no Site")) {
							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"combobox\",\"filtertype\": \"checkedlist\",\"width\":90,\"editable\":False},"+ "\n";
						}else if(rs.getString("field_name").equals("ChecklistNome")) {
							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"combobox\",\"filtertype\": \"checkedlist\",\"width\":90,\"editable\":False},"+ "\n";
						}else if(rs.getString("field_name").equals("ChecklistStatus")) {
							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"combobox\",\"filtertype\": \"checkedlist\",\"width\":90,\"editable\":False},"+ "\n";
						}else {
							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"textbox\",\"width\":80,\"cellclassname\": \""+detalhesCampos.getJSONArray(rs.getString("field_name")).get(4)+"\"},"+ "\n";
						}
					}else if(rs.getString("tipo").equals("Data")){
						dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"date\"},"+ "\n";	
						campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\",\"cellsformat\":\"dd/MM/yyyy\",\"width\":100},"+ "\n";
					}else if(rs.getString("tipo").equals("Numero")){
						dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"number\"},"+ "\n";
						campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"width\":80,\"columntype\": \"numberinput\"},"+ "\n";
					}else if(rs.getString("tipo").equals("Moeda(R$)") || rs.getString("tipo").equals("Numero_I")){
						dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"number\"},"+ "\n";
						campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"width\":80,\"columntype\": \"numberinput\",cellsformat: \"c2\"},"+ "\n";
					}else if(rs.getString("tipo").equals("Lista")){
						dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
						campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"combobox\", \"width\":80},"+ "\n";
					}
				}
			}
		}else{
			dados_tabela=dados_tabela+"{},"+"\n";
			campos_aux=campos_aux+"{},"+"\n";
		}
		dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
		campos_aux=campos_aux.substring(0,campos_aux.length()-2);
		dados_tabela= dados_tabela+"\n"+"],";
		campos_aux=campos_aux+"\n"+"],";
		dados_tabela= dados_tabela+"\n"+campos_aux;
		rs2=mysql.Consulta("select id_usuario,nome from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' and validado='Y' and ativo='Y'");
		//System.out.println(dados_tabela);
		if(rs2.next()) {
			dados_tabela=dados_tabela+"\n"+"\"people\" : [" +"\n";
			rs2.beforeFirst();
			while(rs2.next()) {
				dados_tabela=dados_tabela+"{ \"value\": \""+rs2.getString(1)+"\", \"label\": \""+rs2.getString(2)+"\" },"+"\n";
			}
		}
		dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
		dados_tabela=dados_tabela+"],";
		String imagem_status="";
		//System.out.println(dados_tabela);
		campos_aux="";
		campos_aux="\n"+"\"sites\":";
		
		dados_tabela= dados_tabela+"\n"+"\"records\":[],";
		
		
			List<String> rollout_filtrado_integrado = new ArrayList<>();
			Bson filtro;
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			List<Bson> rollout_filtrado_integrado_filtro = new ArrayList<>();
			rollout_filtrado_integrado_filtro.add(filtro);
			FindIterable<Document> findIterable=mongo.ConsultaCollectioncomFiltrosLista("Rollout_Sites", rollout_filtrado_integrado_filtro); 
			findIterable.forEach((Block<Document>) doc -> {
				
				rollout_filtrado_integrado.add(((Document)((Document)doc.get("GEO")).get("properties")).getString("SiteID"));
			});
			campos_aux=campos_aux+rollout_filtrado_integrado.toString();
			
			dados_tabela=dados_tabela+campos_aux;
			dados_tabela= dados_tabela+"}";
		}
			return dados_tabela;
		}catch(SQLException e) {
			
			mongo.fecharConexao();
			mysql.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
 }
 
public CamposRollout getCampos() {
	return campos;
}

public void setCampos(CamposRollout campos) {
	this.campos = campos;
}
 
public String getUsuariosSitex(String site,Pessoa p) {
	Locale locale_ptBR = new Locale( "pt" , "BR" ); 
	Locale.setDefault(locale_ptBR);
	DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
	Calendar hoje = Calendar.getInstance();
	
	hoje.set(Calendar.HOUR_OF_DAY,00);
	hoje.set(Calendar.MINUTE,01);
	
	String equipes="";
	Bson Filtro;
	Document usuarios;
	List<Bson> filtroLista = new ArrayList<>();
	Filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
	filtroLista.add(Filtro);
	System.out.println("dia da pesquisa:"+f2.format(hoje.getTime()));
	Filtro=Filters.eq("data_dia_string",f2.format(hoje.getTime()));
	filtroLista.add(Filtro);
	Filtro=Filters.eq("local_registro",site);
	filtroLista.add(Filtro);
	ConexaoMongo mongo =  new ConexaoMongo();
	FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("Registros", filtroLista);
	MongoCursor<Document> resultado = findIterable.iterator();
	if(resultado.hasNext()) {
		System.out.println("achou algum registro!");
		while(resultado.hasNext()) {
			equipes=resultado.next().getString("Usuario")+" | ";
			//System.out.println("Site com registro hoje: "+resultado.next().getString("local_registro"));
		}
		mongo.fecharConexao();
		return equipes;
	}
	
	return equipes;
}
 
}
