	package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;

/**
 * Servlet implementation class SiteMgmt
 */
@WebServlet("/SiteMgmt")
public class SiteMgmt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SiteMgmt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamento_sites(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamento_sites(request,response);
	}
	 public void gerenciamento_sites(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			ResultSet rs;
			String param1;
			String param2;
			String param3;
			String param4;
			String param5;
			String param6;
			String param7;
			String param8;
			String param9;
			String query;
			int last_id;
			String dados_tabela;
			Timestamp time = new Timestamp(System.currentTimeMillis());
			String opt;
			Locale locale_ptBR = new Locale( "pt" , "BR" ); 
			Locale.setDefault(locale_ptBR);
		
			DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
			DateFormat f3 = DateFormat.getDateTimeInstance();
			HttpSession session = req.getSession(true);
			Pessoa p = (Pessoa) session.getAttribute("pessoa");
			Conexao conn = (Conexao) session.getAttribute("conexao");
			ConexaoMongo c = new ConexaoMongo(); 
			
			
			param1="";
			param2="";
			param3="";
			param4="";
			param5="";
			param6="";
			param7="";
			param8="";
			param9="";
			last_id=0;
			opt=req.getParameter("opt");
			//System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" acessando servlet de Sites opt - "+ opt);
			//System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Operações de Sites do MSTP Web - "+f3.format(time)+" opt:"+opt);
			try {
			if(opt.equals("1")){
				param1=req.getParameter("siteid");
				param2=req.getParameter("lat");
				param3=req.getParameter("lng");
				param4=req.getParameter("municipio");
				param5=req.getParameter("bairro");
				param6=req.getParameter("endereco");
				param7=req.getParameter("uf");
				param8=req.getParameter("operadora");
				param9=req.getParameter("nome");
				query="";
				param2=param2.replaceAll(" ", "");
				param2=param2.replaceAll(",", ".");
				param3=param3.replaceAll(" ", "");
				param3=param3.replaceAll(",", ".");
				query="INSERT INTO sites (site_id,site_nome,site_latitude,site_longitude,site_municipio,site_bairro,site_endereco,site_uf,site_operadora,dt_add_site,usuario_add_site,empresa) "
		                + "VALUES ('"+param1+"','"+param9+"','"+param2+"','"+param3+"','"+param4+"','"+param5+"','"+param6+"','"+param7+"','"+param8+"','"+time+"','"+p.get_PessoaUsuario()+"',"+p.getEmpresa().getEmpresa_id()+")";
				if(conn.Inserir_simples(query)){
					System.out.println("Site Cadastrado: " + param1);
					rs=conn.Consulta("Select sys_id from sites order by sys_id desc limit 1");
					if(rs.next()){
						last_id=rs.getInt(1);
					}
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Site "+param1 + " inserido com sucesso. ID:"+last_id);
				}else {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Erro no cadastro do site: "+param1 );
				}
				//System.out.println(param1+"\n"+param2+"\n"+param3+"\n"+param4+"\n"+param5+"\n"+param6+"\n"+param7+"\n"+param8+"\n"+param9);
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("2")) {
				
				param1 = req.getParameter("operadora");
				param2=req.getParameter("pagesize");
				param3=req.getParameter("pagenum");
				String filtervalue="";
				String filtercondition="";
				String filterdatafield="";
				String status_milestone="";
				String filteroperator="";
				Bson filtro;
				Document site = new Document();
				List<Bson> filtro_list= new ArrayList<Bson>();
				int tamanho= Integer.parseInt(param2);
				int pagina= Integer.parseInt(param3);
				//ConexaoMongo c = new ConexaoMongo();
				int filterscount = Integer.parseInt(req.getParameter("filterscount"));
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtro_list.add(filtro);
				filtro=Filters.eq("site_ativo","Y");
				filtro_list.add(filtro);
				filtro=Filters.regex("site_operadora",param1);
				filtro_list.add(filtro);
				for (Integer i=0; i < filterscount; i++)
				{
				 filtervalue = req.getParameter("filtervalue" + i);
				 filtercondition = req.getParameter("filtercondition" + i);
				 filterdatafield = req.getParameter("filterdatafield" + i);
				 //filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
				 filteroperator = req.getParameter("filteroperator" + i);
				 //System.out.println("filtervalue:"+filtervalue);
					System.out.println("filtercondition:"+filtercondition);
					System.out.println("filterdatafield:"+filterdatafield);
					System.out.println("filteroperator:"+filteroperator);
					
				switch(filtercondition)
				{
					case "CONTAINS":
						
							filtro=Filters.regex(filterdatafield, ".*"+filtervalue+".*");
							filtro_list.add(filtro);
						
						
						break;
					case "CONTAINS_CASE_SENSITIVE":
						//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '%" + filtervalue + "%'";
						break;
					case "DOES_NOT_CONTAIN":
						break;
					case "DOES_NOT_CONTAIN_CASE_SENSITIVE":
						
						break;
					case "EQUAL":
						filtro=Filters.eq(filterdatafield, filtervalue);
						filtro_list.add(filtro);
						
						break;
					case "EQUAL_CASE_SENSITIVE":
						
						break;
					case "NOT_EQUAL":
						
						break;
					case "NOT_EQUAL_CASE_SENSITIVE":
						
						break;
					case "GREATER_THAN":
						
								filtro=Filters.gt(filterdatafield,filtervalue);
								filtro_list.add(filtro);
						
						break;
					case "LESS_THAN":
						
								filtro=Filters.lt(filterdatafield, filtervalue);
								filtro_list.add(filtro);
						
						break;
					case "GREATER_THAN_OR_EQUAL":
						
								filtro=Filters.gte(filterdatafield, filtervalue);
								filtro_list.add(filtro);
						
						break;
					case "LESS_THAN_OR_EQUAL":
						
								filtro=Filters.lte(filterdatafield, filtervalue);
								filtro_list.add(filtro);
						
						break;
					case "STARTS_WITH":
						//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE '" + filtervalue + "%'";
						break;
					case "STARTS_WITH_CASE_SENSITIVE":
						//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '" + filtervalue + "%'";
						break;
					case "ENDS_WITH":
						//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE '%" + filtervalue + "'";
						break;
					case "ENDS_WITH_CASE_SENSITIVE":
						//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '%" + filtervalue + "'";
						break;
					case "NULL":
						//where += " milestone='" + filterdatafield + "' and value_atbr_field IS NULL";
						break;
					case "NOT_NULL":
						//where += " milestone='" + filterdatafield + "' and value_atbr_field IS NOT NULL";
						break;
				}
									
						
			}
				
				
				//System.out.println("Iniciando busca de sites operadora:"+param1);
				//long linhas_total= c.ConsultaCountComplexa("sites", lista_filtro);
				
				long linhas_total_operadora= c.ConsultaCountComplexa("sites", filtro_list);
				dados_tabela="";
				dados_tabela=dados_tabela+"[{\"totalRecords\":\""+linhas_total_operadora+"\",";
				//Integer contador=0;
				//System.out.println(dados_tabela);
				FindIterable<Document> findIterable = c.ConsultaSimplesComFiltroInicioLimit("sites", filtro_list,tamanho*pagina,tamanho);
				//FindIterable<Document> findIterable = c.ConsultaCollectioncomFiltrosLista("sites", lista_filtro);
				MongoCursor<Document> resultado = findIterable.iterator();
				String chaves="";
				if(resultado.hasNext()) {
					System.out.println("Site encontrados");
					while(resultado.hasNext()) {
						site=(Document) resultado.next();
						
						dados_tabela=dados_tabela+chaves+"\"site_id\":\""+site.getString("site_id")+"\",";
						
						dados_tabela=dados_tabela+"\"site_operadora\":\""+site.getString("site_operadora")+"\",";
						dados_tabela=dados_tabela+"\"site_uf\":\""+site.getString("site_uf")+"\",";
						dados_tabela=dados_tabela+"\"site_municipio\":\""+site.getString("site_municipio")+"\",";
						dados_tabela=dados_tabela+"\"site_endereco\":\""+site.getString("site_endereco")+"\",";
						dados_tabela=dados_tabela+"\"site_bairro\":\""+site.getString("site_bairro")+"\",";
						dados_tabela=dados_tabela+"\"site_latitude\":\""+site.getString("site_latitude")+"\",";
						dados_tabela=dados_tabela+"\"site_longitude\":\""+site.getString("site_longitude")+"\"},\n";
						chaves="{";
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]";
					//dados_tabela=dados_tabela.replace("replace1", contador.toString());
				}else {
					System.out.println("Site ñ encontrados");
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"}]";
				}
				//System.out.println(dados_tabela);
				resp.setContentType("application/json");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("3")) {
				System.out.println("Carregando sites no mapa");
				//Essa função ainda precisa ser adaptada para excuir folgas, pontos ajustados,férias e licença Médica
				Calendar dia = Calendar.getInstance();
				dia.set(Calendar.HOUR_OF_DAY,00);
				
				dia.set(Calendar.MINUTE,00);
				Document registro=new Document();
				Document geo=new Document();
				Bson filtro;
				List<Bson> lista_filtro = new ArrayList<Bson>();
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				lista_filtro.add(filtro);
				//filtro=Filters.not("Empresa",p.getEmpresa().getEmpresa_id());
				lista_filtro.add(filtro);
				filtro=Filters.gte("data_dia",dia.getTime());
				lista_filtro.add(filtro);
				filtro=Filters.lte("data_dia",time);
				lista_filtro.add(filtro);
				FindIterable<Document> findIterable =c.ConsultaCollectioncomFiltrosLista("Registros", lista_filtro);
				MongoCursor<Document> resultado= findIterable.iterator();
				if(resultado.hasNext()) {
					
					
					dados_tabela="";
					dados_tabela=  "{"+
					    "\"type\": \"FeatureCollection\","+
					    "\"features\": [";
					while(resultado.hasNext()) {
						registro=resultado.next();
						geo = (Document) registro.get("GEO");
						
						dados_tabela=dados_tabela+geo.toJson()+",";
						
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"]";
					dados_tabela=dados_tabela +   "}";
				}else {
					dados_tabela="";
					dados_tabela=  "{"+
					    "\"type\": \"FeatureCollection\","+
					    "\"features\": []}";
				}
				/*rs=conn.Consulta("Select distinct local_registro,usuario,latitude,longitude,tipo_local_registro from registros where data_dia='"+f2.format(d.getTime())+"' and latitude <>'' and longitude <> '' and empresa='"+p.getEmpresa().getEmpresa_id()+"'");
				//System.out.println("Carregando sites no mapa2");
				if(rs.next()){
					rs.beforeFirst();
					dados_tabela="";
					dados_tabela=  "{"+
					    "\"type\": \"FeatureCollection\","+
					    "\"features\": [";
							 
						     
					while(rs.next()) {
						if(rs.getString("local_registro").equals("Ponto")) {
							
						}else {
							query="select * from sites where site_id='"+rs.getString("local_registro")+"' and site_latitude<>'' and site_longitude<>'' and empresa="+p.getEmpresa().getEmpresa_id()+" limit 1";
							//System.out.println(query);
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								rs2.beforeFirst();
							
							while (rs2.next()) {
								dados_tabela=dados_tabela+"{" +
							            "\"type\": \"Feature\","+
							            "\"geometry\": {"+
							            "\"type\": \"Point\","+
							            "\"coordinates\": ";
							                    
							                
								dados_tabela=dados_tabela+verfica_coordenadas(rs2.getString("site_latitude"),rs2.getString("site_longitude"))+"},";
								dados_tabela=dados_tabela+"\"properties\": {"+
					                "\"Site Id\": \""+rs2.getString("site_id")+"\","+
					                "\"Site Nome\": \""+rs2.getString("site_nome")+"\","+
					                "\"Usuário\": \""+rs.getString("usuario")+"\""+
					            "}"+
					        "},";
							}
							
							}
						
						}
						
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"]";
					dados_tabela=dados_tabela +   "}";
					
						*/    		
							
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("4")) {
				
				param1=req.getParameter("site");
				if(conn.Update_simples("update sites set site_ativo='N' where sys_id="+param1)){
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Site Desabilitado com Sucesso!");
				}
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("5")) {
				int tot_linha=0;
				//query="RESET QUERY CACHE";
				//rs=conn.Consulta(query);
				param1=req.getParameter("limit");
				param2=req.getParameter("offset");
				param3=req.getParameter("search");
				param4=req.getParameter("filter");
				//System.out.println(param1);
				//System.out.println(param2);
				//System.out.println(param3);
				//System.out.println(param4);
				if(param3.equals("") || param3.length()==0) {
				query="Select SQL_NO_CACHE count(site_id) from sites where site_ativo='Y' and empresa="+p.getEmpresa().getEmpresa_id();
				rs=conn.Consulta(query);
				if(rs.next()){
					tot_linha=rs.getInt(1);
				}
				rs=conn.Consulta("select SQL_NO_CACHE * from sites where site_ativo='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" order by site_id asc limit "+param1+" OFFSET "+param2);
				if(rs.next()){
					dados_tabela="{"+"\n";
					
					dados_tabela=dados_tabela +"\"total\":"+tot_linha+",\n";
					dados_tabela=dados_tabela +"\"rows\":["+"\n";
					rs.beforeFirst();
					while(rs.next()){
						dados_tabela=dados_tabela +"{"+"\n";
						dados_tabela=dados_tabela +"\"site_tbl_operacoes\":\"<div style='float:left;padding-left: 10px;'><a href='#' onclick=deleta1_site('"+rs.getInt(1)+";"+rs.getString(2)+"')><i class='fas fa-trash-alt'></i></a></div><div style='float:left;padding-left: 10px;'><a href='#' onclick=edita_site('"+rs.getString(2)+"')><i class='fas fa-pencil-alt'></i></a></div>\","+"\n";
						dados_tabela=dados_tabela +"\"siteiDsys_tbl\":\""+rs.getInt(1)+"\","+"\n";
						dados_tabela=dados_tabela +"\"siteOperadora_tbl\":\""+rs.getString("site_operadora")+"\","+"\n";
						dados_tabela=dados_tabela +"\"siteiD_tbl\":\""+rs.getString(2)+"\","+"\n";
						dados_tabela=dados_tabela +"\"Sitelat_tbl\":\""+rs.getString(3)+"\","+"\n";
						dados_tabela=dados_tabela +"\"Sitelng_tbl\":\""+rs.getString(4)+"\","+"\n";
						dados_tabela=dados_tabela +"\"Sitecidade_tbl\":\""+rs.getString(5)+"\","+"\n";
						dados_tabela=dados_tabela +"\"Sitebairro_tbl\":\""+rs.getString(6)+"\","+"\n";
						dados_tabela=dados_tabela +"\"SiteUF_tbl\":\""+rs.getString(7)+"\""+"\n";
						dados_tabela=dados_tabela +"},"+"\n";
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela +"]}"+"\n";
					//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				}else {
					query="Select count(site_id) from sites where site_ativo='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" and "
							+ "(site_id like '%"+param3+"%' or "
							+ "site_latitude like '%"+param3+"%' or "
							+ "site_longitude like '%"+param3+"%' or "
							+ "site_municipio like '%"+param3+"%' or "
							+ "site_bairro like '%"+param3+"%' or "
							+ "site_operadora like '%"+param3+"%' or "
							+ "site_uf like '%"+param3+"%')";
					//System.out.println(query);		
					rs=conn.Consulta(query);
					if(rs.next()){
						tot_linha=rs.getInt(1);
						//System.out.println(tot_linha);	
					}
					query="Select * from sites where site_ativo='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" and "
							+ "(site_id like '%"+param3+"%' or "
							+ "site_latitude like '%"+param3+"%' or "
							+ "site_longitude like '%"+param3+"%' or "
							+ "site_municipio like '%"+param3+"%' or "
							+ "site_bairro like '%"+param3+"%' or "
							+ "site_operadora like '%"+param3+"%' or "
							+ "site_uf like '%"+param3+"%') order by site_id DESC limit "+param1+" OFFSET "+param2;
					
					rs=conn.Consulta(query);
					if(rs.next()){
						dados_tabela="{"+"\n";
						
						dados_tabela=dados_tabela +"\"total\":"+tot_linha+",\n";
						dados_tabela=dados_tabela +"\"rows\":["+"\n";
						rs.beforeFirst();
						while(rs.next()){
							dados_tabela=dados_tabela +"{"+"\n";
							dados_tabela=dados_tabela +"\"site_tbl_operacoes\":\"<div style='float:left;padding-left: 10px;'><a href='#' onclick=deleta1_site('"+rs.getInt(1)+";"+rs.getString(2)+"')><i class='fas fa-trash-alt'></i></a></div><div style='float:left;padding-left: 10px;'><a href='#' onclick=edita_site('"+rs.getString(2)+"')><i class='fas fa-pencil-alt'></i></a></div>\","+"\n";
							dados_tabela=dados_tabela +"\"siteiDsys_tbl\":\""+rs.getInt(1)+"\","+"\n";
							dados_tabela=dados_tabela +"\"siteOperadora_tbl\":\""+rs.getString("site_operadora")+"\","+"\n";
							dados_tabela=dados_tabela +"\"siteiD_tbl\":\""+rs.getString(2)+"\","+"\n";
							dados_tabela=dados_tabela +"\"Sitelat_tbl\":\""+rs.getString(3)+"\","+"\n";
							dados_tabela=dados_tabela +"\"Sitelng_tbl\":\""+rs.getString(4)+"\","+"\n";
							dados_tabela=dados_tabela +"\"Sitecidade_tbl\":\""+rs.getString(5)+"\","+"\n";
							dados_tabela=dados_tabela +"\"Sitebairro_tbl\":\""+rs.getString(6)+"\","+"\n";
							dados_tabela=dados_tabela +"\"SiteUF_tbl\":\""+rs.getString(7)+"\""+"\n";
							dados_tabela=dados_tabela +"},"+"\n";
						}
						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
						dados_tabela=dados_tabela +"]}"+"\n";
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
					}
				}
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("6")) {
				
				param1=req.getParameter("sites");
				//System.out.println(param1);
				if(conn.Update_simples("update sites set site_ativo='N' where sys_id in ("+param1+")")){
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Sites Desabilitados com Sucesso!");
				}
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("7")){
				System.out.println("carregando sites novos para aprovação");
				query="";
				query="select * from site_aprova where aprovado='N' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="[\n";
					while(rs.next()) {
						
						dados_tabela=dados_tabela+"{\"aprove_oper\":\"<div style='float:left;padding-left: 10px;'><a href='#' onclick=aprova1_registro('SiteNovo','"+rs.getInt("sysid")+"-SN')><i class='fas fa-check fa-lg'></i></a></div><div style='float:left;padding-left: 10px;'><a href='#' onclick=rejeita1_registro('SiteNovo','"+rs.getInt("sysid")+"-SN')><i class='fas fa-times fa-lg' style='color:red'></i></a></div>\",\"aprove_id\":\""+rs.getInt("sysid")+"-SN\",\"aprove_tipo\":\"Site Novo\",\"aprove_pessoa\":\""+rs.getString("usuario_pedido")+"\",\"approve_desc\":\"Site Nome: "+rs.getString("site_nome")+"<br>Site ID: "+rs.getString("site_id")+"<br>Lat: "+rs.getString("site_lat")+"<br>Lng: "+rs.getString("site_lng")+"\",\"approve_dt_sub\":\""+rs.getString("dt_pedido")+"\",\"approve_anotacoes\":\""+rs.getString("site_operadora")+" - "+rs.getString("site_estado")+"<br><a href='http://maps.google.com/maps?q="+rs.getString("site_lat")+","+rs.getString("site_lng")+"' target='_blank'>Localização</a>"+"\",\"approve_origem\":\"Manual - MSTP MOBILE\"},\n";
						
						}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"\n]";
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("8")){
				param1=req.getParameter("id");
				query="update site_aprova set aprovado='Y',dt_aprovado='"+f3.format(time)+"' where sysid="+param1;
				if(conn.Update_simples(query)) {
					rs=conn.Consulta("select * from site_aprova where sysid="+param1);
					if(rs.next()) {
						query="INSERT INTO sites (site_id,site_nome,site_latitude,site_longitude,site_uf,site_operadora,dt_add_site,usuario_add_site,empresa) "
				                + "VALUES ('"+rs.getString("site_id")+"','"+rs.getString("site_nome")+"','"+rs.getString("site_lat").trim()+"','"+rs.getString("site_lng").trim()+"','"+rs.getString("site_estado")+"','"+rs.getString("site_operadora")+"','"+time+"','"+rs.getString("usuario_pedido")+"','"+p.getEmpresa().getEmpresa_id()+"')";
						if(conn.Inserir_simples(query)) {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Site Aprovado e criado na base de Sites.");
						}else {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Erro no cadastro do novo Site");
						}
					}
					
					
				}
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("9")){
				param1=req.getParameter("id");
				query="update site_aprova set aprovado='R',dt_aprovado='"+f3.format(time)+"' where sysid="+param1;
				if(conn.Update_simples(query)) {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Solicitação rejeitada com sucesso");
				}
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("10")) {
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" carregando sites e usuários mapa operacional");
		
				List<Document> lista_sites = new ArrayList<Document>();
				//ConexaoMongo c = new ConexaoMongo();
				Document document_operadora = new Document();
				Document document_featurecollection = new Document();
				JSONObject aux_json = new JSONObject();
				dados_tabela="";
				FindIterable<Document> findIterable=c.ConsultaSimplesSemFiltro("operadora");
				findIterable.forEach((Block<Document>) doc -> {
					//System.out.println("Entrou no loope da operadora");
					document_featurecollection.append("type", "FeatureCollection");
					document_featurecollection.append("operadora", doc.get("nome").toString());
					FindIterable<Document> findIterable2=c.ConsultaSimplesComFiltro("Rollout_Sites","GEO.properties.Operadora",doc.get("nome").toString(),p.getEmpresa().getEmpresa_id());
					findIterable2.forEach((Block<Document>) doc2 -> {
						lista_sites.add((Document)doc2.get("GEO"));
						
					});
					
					document_featurecollection.append("features",lista_sites);
					document_operadora.append("\""+doc.get("nome").toString()+"\"", document_featurecollection.toJson());
					aux_json.append(doc.get("nome").toString(), document_featurecollection.toJson());
					//operadora_aux=operadora_aux+"\""+doc.get("nome").toString()+"\":"+document_featurecollection.toJson();
					lista_sites.clear();
					document_featurecollection.clear();
					//System.out.println(document_operadora.toString());
				});
				Bson filtro=Filters.gte("GEO.properties.Data", ChecaFormatoData(f2.format(time).toString()));
				List<Bson> usuarios_logados_filtros= new ArrayList<Bson>();
				usuarios_logados_filtros.add(filtro);
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				usuarios_logados_filtros.add(filtro);
				FindIterable<Document> findIterable2;
				List<String> logins=c.ConsultaSimplesDistinct("Localiza_Usuarios", "GEO.properties.Usuario", usuarios_logados_filtros);
				document_featurecollection.append("type", "FeatureCollection");
				document_featurecollection.append("operadora", "USUARIOS");
				lista_sites.clear();
				for(int indice=0;indice<logins.size();indice++) {
					usuarios_logados_filtros.clear();
					usuarios_logados_filtros= new ArrayList<Bson>();
					filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
					usuarios_logados_filtros.add(filtro);
					filtro=Filters.gte("GEO.properties.Data", ChecaFormatoData(f2.format(time).toString()));
					usuarios_logados_filtros.add(filtro);
					filtro=Filters.eq("GEO.properties.Usuario",logins.get(indice));
					usuarios_logados_filtros.add(filtro);
					findIterable2=c.ConsultaOrdenadaFiltroListaLimit1("Localiza_Usuarios", "GEO.properties.Data", -1, usuarios_logados_filtros);
				
				//findIterable2=c.ConsultaSimplesComFiltroDate("Localiza_Usuarios","GEO.properties.Data",checa_formato_data(f2.format(time).toString()),p.getEmpresa().getEmpresa_id());
				
				
				findIterable2.forEach((Block<Document>) doc2 -> {
					JSONObject aux = new JSONObject(doc2.toJson());
					Document auxdoc=new Document();
					auxdoc=doc2;
					Timestamp time2 = new Timestamp(aux.getJSONObject("GEO").getJSONObject("properties").getJSONObject("Data").getLong("$date"));
					//System.out.println(time2.toString());
					auxdoc.get("GEO", Document.class).get("properties", Document.class).replace("Data", time2.toString());
					//System.out.println("Entrou no loop da data");
					lista_sites.add((Document)auxdoc.get("GEO"));
				});
			}
				document_featurecollection.append("features",lista_sites );
				document_operadora.append("\"USUARIOS\"", document_featurecollection.toJson().toString());
				lista_sites.clear();
				document_featurecollection.clear();
				/*rs=conn.Consulta("SELECT distinct rollout.value_atbr_field,sites.site_operadora FROM rollout,sites WHERE rollout.linha_ativa='Y' and rollout.empresa="+p.getEmpresa().getEmpresa_id()+" and   rollout.value_atbr_field=sites.site_id order by sites.site_operadora");
				dados_tabela="";
				System.out.println("Query executada");
				if(rs.next()) {
					System.out.println("resultados encontrados...Query executada");
					dados_tabela=  "{";
					operadora_aux=rs.getString(2);
					rs.beforeFirst();
					dados_tabela=dados_tabela+"\n\""+operadora_aux+"\":{"+
						    "\"type\": \"FeatureCollection\","+
						    "\"operadora\": \""+operadora_aux+"\","+
						    "\"features\": [";
					while(rs.next()) {
						//System.out.println(rs.getString("site_operadora"));
						//System.out.println(dados_tabela);
						if(operadora_aux.equals(rs.getString(2))) {
							rs2=conn.Consulta("select * from sites where site_id='"+rs.getString(1)+"' and site_ativo='Y' and site_latitude<>'' and site_longitude<>'' and site_operadora<>'' and empresa='"+p.getEmpresa().getEmpresa_id()+"'");
							if(rs2.next()) {
							
										dados_tabela=dados_tabela+"{" +
						
							            "\"type\": \"Feature\","+
							            "\"geometry\": {"+
							            "\"type\": \"Point\","+
							            "\"coordinates\": [";
							                    
							                
								dados_tabela=dados_tabela+verfica_coordenadas(rs2.getString("site_longitude"),rs2.getString("site_latitude"))+"]},\n";
								dados_tabela=dados_tabela+"\"properties\": {"+
					                "\"Site_Id\": \""+rs2.getString("site_id").replaceAll("'", "_")+"\","+
					                "\"Site Nome\": \""+rs2.getString("site_nome").replaceAll("'", "_")+"\","+
					                "\"Site_Operadora\": \""+rs2.getString("site_operadora")+"\""+
					            "}\n"+
					        "},\n";
							}
						}else {
							            dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
										dados_tabela=dados_tabela+"\n]},\n";
										operadora_aux=rs.getString(2);
										dados_tabela=dados_tabela+"\n\""+operadora_aux+"\":{"+
											    "\"type\": \"FeatureCollection\","+
											    "\"operadora\": \""+operadora_aux+"\","+
											    "\"features\": [";
										rs.previous();
						}
						}
					
							
					
						
					
			}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
				dados_tabela=dados_tabela+"\n]},\n";
				System.out.println(dados_tabela);
				Document di= new Document();
				*/
				/*query="select *  from localiza_usuarios where SUBSTRING(dt_add,1,10) = '"+time.toString().substring(0, 10)+"' and empresa="+p.getEmpresa().getEmpresa_id();
				System.out.println(query);
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela=dados_tabela+"\"USUARIOS\":{"+
						    "\"type\": \"FeatureCollection\","+
						    "\"operadora\": \"USUARIOS\","+
						    "\"features\": [";
					while(rs.next()) {

						//System.out.println(rs.getString("usuario"));
						//System.out.println(dados_tabela);
						
						
										dados_tabela=dados_tabela+"{" +
						
							            "\"type\": \"Feature\","+
							            "\"geometry\": {"+
							            "\"type\": \"Point\","+
							            "\"coordinates\": [";
							                    
							                
								dados_tabela=dados_tabela+rs.getString("lng").replaceAll("\n", "").replaceAll("\r", "").trim()+","+rs.getString("lat").replaceAll("\n", "").replaceAll("\r", "").trim()+"]},\n";
								dados_tabela=dados_tabela+"\"properties\": {" +
						                "\"usuario\": \""+rs.getString("usuario")+"\"," +
						                "\"horario\": \""+rs.getString("dt_add")+"\"" +
						            "}\n";
								dados_tabela=dados_tabela+"},";
							}
										dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
										dados_tabela=dados_tabela+"]";
										dados_tabela=dados_tabela+"}\n}";
						
										
					}else {
						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
						dados_tabela=dados_tabela +   "\n}";
					}
				
				   
					
					*/	    		
					String  resultado = document_operadora.toString();
					//System.out.println(resultado);
					resultado=resultado.replaceAll("=", ":");
					
					resultado=resultado.substring(9, resultado.length()-1);
					//dados_tabela="esse é um teste de string com dados \"teste de teste\"sem opcso";
					//System.out.println(resultado);
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(resultado);
					System.out.println("Todos os Sites Carregados");
					c.fecharConexao();
					Timestamp time2 = new Timestamp(System.currentTimeMillis());
					System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("11")) {
				
				param1=req.getParameter("operadora");
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" iniciando export de sites operadora - "+ param1);
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("SitesMSTP");
				Row row;
				long rowIndex=0;
				int colIndex=0;
				Cell cell;
				Bson filtro;
				Document site ;
				List<Bson> filtros = new ArrayList<>();
				filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtros.add(filtro);
				filtro = Filters.eq("site_ativo","Y");
				filtros.add(filtro);
				filtro = Filters.eq("site_operadora",param1);
				filtros.add(filtro);
				FindIterable<Document> findIterable = c.ConsultaCollectioncomFiltrosLista("sites", filtros);
				MongoCursor<Document> resultado=findIterable.iterator();
				
				//query="Select sys_id,site_id,site_latitude,site_longitude,site_municipio,site_bairro,site_uf,site_nome,site_sequencial,site_operadora,site_cep,site_owner from sites where site_ativo='Y' and site_operadora='"+param1+"' and empresa="+p.getEmpresa().getEmpresa_id()+" order by sys_id asc";
				//System.out.println(query);
				//rs=conn.Consulta(query);
				if(resultado.hasNext()) {
					rowIndex=1;
	            	row = sheet.createRow((short) rowIndex);
	            	//total_colunas=rs.getMetaData().getColumnCount();
	            	XSSFCellStyle cellStyle = workbook.createCellStyle();
            	
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
                cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                XSSFCellStyle cellStyle2 = workbook.createCellStyle();
            	
                cellStyle2.setAlignment(HorizontalAlignment.CENTER);
                cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
                
                cellStyle2.setBorderBottom(BorderStyle.THIN);
                cellStyle2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle2.setBorderLeft(BorderStyle.THIN);
                cellStyle2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle2.setBorderRight(BorderStyle.THIN);
                cellStyle2.setRightBorderColor(IndexedColors.BLACK.getIndex());
                cellStyle2.setBorderTop(BorderStyle.THIN);
                cellStyle2.setTopBorderColor(IndexedColors.BLACK.getIndex());
               
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("MSTP ID");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
                cell = row.createCell((short) colIndex);
	            cell.setCellValue("Sequencial");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("UF");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Sigla");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Nome");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Endereço");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Cep");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Latitude");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Longitude");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Altitude");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Area Total");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Area de Tarifacao");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Municipio");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Regiao Operacional");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Bairro");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Tipo de Contrato");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("inicio Operacao");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Proprietario");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Tipo de Construcao");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Tipo de Estrutura");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Link web");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Operadora");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Site Modelo");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	            
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Antena");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Observação 1");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	            colIndex=colIndex+1;
	           
	            cell = row.createCell((short) colIndex);
	            cell.setCellValue("Observação 2");
	            sheet.autoSizeColumn(colIndex);
	            cell.setCellStyle(cellStyle);
	           
	           
	            System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" cabeçalho de arquivo criado export de sites operadora - "+ param1);
	        while(resultado.hasNext()) {
	        		site = resultado.next();
                	rowIndex=rowIndex+1;
	            	row = sheet.createRow((int)rowIndex);
	            	
	            	
	            	colIndex=0;
	            	cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getObjectId("_id").toString());
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		            
	               
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_sequencial"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_uf"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_id"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_nome"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_endereco"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_cep"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_latitude"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_longitude"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_altitude"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_Area_Total"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_area_tarifacao"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_municipio"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_reg_operacional"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_bairro"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_tipo_contrato"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_init_oper"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_owner"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_tipo_construcao"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_tipo_estrutura"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_link"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_operadora"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_modelo"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_antena"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		           
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_obs1"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            colIndex=colIndex+1;
		            
		            cell = row.createCell((short) colIndex);
		            cell.setCellValue(site.getString("site_obs2"));
		            sheet.autoSizeColumn(colIndex);
		            cell.setCellStyle(cellStyle2);
		            
		           
                }
	            System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Arquivo Exporte Sites Criado & finalizado Operadora - "+ param1);

            	resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                resp.setHeader("Content-Disposition", "attachment; filename=sites_mstp.xlsx");
                workbook.write(resp.getOutputStream());
                workbook.close();
				}else {
					System.out.println("Arquivo Exportde Sites Criado & finalizado - Vazio");
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Sites não disponivel para download");
				}
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("12")) {
				
				String lat="";
				String lng ="";
				Document site = new Document();
				Document filtro_site = new Document();
				Document update = new Document();
				Document comando = new Document();
				param1=req.getParameter("filtros");
				param2 =req.getParameter("operadora");
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" iniciando ajustes de coordenadas - " + param1);
				List<Bson> filtro_list = new ArrayList<>();
				List<Double> coordenadas = new ArrayList<>();
				Bson filtrodoc;
				JSONObject jObj = new JSONObject(param1);
				JSONArray filtros = jObj.getJSONArray("filtros");
				
				filtrodoc=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtro_list.add(filtrodoc);
				filtrodoc=Filters.eq("site_operadora",param2);
				filtro_list.add(filtrodoc);
	        	for (Integer i=0; i < filtros.length(); i++)
				{
	        		filtrodoc=Filters.eq("site_id",filtros.get(i).toString());
	    			filtro_list.add(filtrodoc);
	    			FindIterable<Document> findIterable=c.ConsultaCollectioncomFiltrosLista("sites", filtro_list);
	        		MongoCursor<Document> resultado = findIterable.iterator();
	        		if(resultado.hasNext()) {
	        			site =resultado.next();
	        			lat=site.getString("site_latitude");
	    				lng =site.getString("site_longitude");
	    				filtro_site.append("site_id", filtros.get(i).toString());
	    				filtro_site.append("Empresa", p.getEmpresa().getEmpresa_id());
	    				filtro_site.append("site_operadora", param2);
	    				if(Double.parseDouble(lat.replaceAll(",", ".")) < Double.parseDouble(lng.replaceAll(",", "."))) {
	    					update.append("site_latitude", lng);
	    					update.append("site_longitude", lat);
	    				}
	    				coordenadas=verfica_coordenadas(lng, lat);
	    				update.append("GEO.geometry.coordinates", coordenadas);
	    				comando.append("$set", update);
	    				c.AtualizaUm("sites", filtro_site, comando);
	        		}
	        		filtro_list.remove(filtro_list.size()-1);
				}
				
				
				
				//ConexaoMongo c = new ConexaoMongo();
				//Document document = new Document();
				
				/*
				
				//Document geometry = new Document();
				//Document properties = new Document();
				List<Bson> filtroSite = new ArrayList<Bson>();
				Bson filtro;
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtroSite.add(filtro);
				filtro=Filters.eq("site_operadora","TIM");
				filtroSite.add(filtro);
				filtro=Filters.eq("site_uf","SC");
				filtroSite.add(filtro);
				FindIterable<Document> findIterable = c.ConsultaCollectioncomFiltrosLista("sites", filtroSite);
				MongoCursor<Document> resultado = findIterable.iterator();
				while(resultado.hasNext()) {
					site=resultado.next();
					update.clear();
					comando.clear();
					filtro_site.clear();
					filtro_site.append("site_id", site.getString("site_id"));
					filtro_site.append("Empresa", p.getEmpresa().getEmpresa_id());
					filtro_site.append("site_operadora", site.getString("site_operadora"));
					filtro_site.append("site_uf", site.getString("site_uf"));
						lat=site.getString("site_latitude").replaceAll("[^\\x00-\\x7F]", "").replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").replaceAll("\\p{C}", "");
						lng=site.getString("site_longitude").replaceAll("[^\\x00-\\x7F]", "").replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").replaceAll("\\p{C}", "");
						update.append("site_latitude", lat);
						update.append("site_longitude", lng);
						comando.append("$set", update);
						System.out.println("Atualizando site "+site.getString("site_id")+" com novas coordenadas "+ lat+","+lng+".fim");
						c.AtualizaUm("sites", filtro_site, comando);
						
					
				}
				
				*/
    			System.out.println("Sicronia com Mongo Finalizada");
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Sincronização Completa");
				c.fecharConexao();
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("13")) {
				System.out.println("Buscando ultimo registro de usuários");
				param1=req.getParameter("usuario");
				Bson filtro;
				Document localizacao ;
				List<Bson>lista_filtro=new ArrayList<>();
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				lista_filtro.add(filtro);
				filtro=Filters.eq("GEO.properties.Usuario",param1);
				lista_filtro.add(filtro);
				
				FindIterable<Document> findIterable=c.ConsultaOrdenadaFiltroListaLimit1("Localiza_Usuarios", "GEO.properties.Data", -1, lista_filtro);
				MongoCursor<Document> resultado=findIterable.iterator();
				if(resultado.hasNext()) {
					localizacao=resultado.next();
					
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(localizacao.get("GEO",Document.class).get("geometry",Document.class).get("coordinates"));
				}else {
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("");
				}
				
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Sites opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("14")) {
				System.out.println("Iniciando Alteração de Dados do Site");
				if(p.getPerfil_funcoes().contains("Site Manager")) {
				
		                param1=req.getParameter("mudancas");
		                //System.out.println(param1);
		                
		                Document filtros;
		                
		                Document updates;
		                Document update = new Document();
		              
		                time = new Timestamp(System.currentTimeMillis());
		                Calendar d =Calendar.getInstance();
		                //atualiza_sites_integrados(p);
		                
		                JSONObject jObj = new JSONObject(param1); 
		                JSONObject site ; 
		    			JSONArray campos = jObj.getJSONArray("campos");
		    			
		    			for(int cont=0;cont<campos.length();cont++) {
		    				site = campos.getJSONObject(cont);
		    				filtros=new Document();
		    				filtros.append("Empresa", p.getEmpresa().getEmpresa_id());
		    				filtros.append("site_id", site.getString("id"));
		    				filtros.append("site_ativo", "Y");
		    				updates=new Document();
		    				if(site.getString("colum").equals("site_latitude") || site.getString("colum").equals("site_longitude") ) {
		    					Bson filtro;
		    					List<Bson> filtrosLista = new ArrayList<>();
		    					filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
		    					filtrosLista.add(filtro);
		    					filtro=Filters.eq("site_id", site.getString("id"));
		    					filtrosLista.add(filtro);
		    					filtro=Filters.eq("site_ativo", "Y");
		    					filtrosLista.add(filtro);
		    					FindIterable<Document> findInterable = c.ConsultaCollectioncomFiltrosLista("sites", filtrosLista);
		    					MongoCursor<Document> resultado = findInterable.iterator();
		    					if(resultado.hasNext()) {
		    						Document site_buscado = resultado.next();
		    						//System.out.println(site_buscado.toJson());
			    					Document geo = (Document) site_buscado.get("GEO");
			    					//System.out.println(geo.toJson());
			    					Document geometry=(Document) geo.get("geometry");
			    					//System.out.println(geometry.toJson());
			    					List<Double> coordenadas = (List<Double>) geometry.get("coordinates");
			    					if(site.getString("colum").equals("site_latitude")) {
			    						coordenadas.set(1, Double.parseDouble(site.getString("value").replace(",", ".")));
			    						updates.append("site_latitude", site.getString("value"));
			    					}else {
			    						coordenadas.set(0, Double.parseDouble(site.getString("value").replace(",", ".")));
			    						updates.append("site_longitude", site.getString("value"));
			    					}
			    					geometry.append("coordinates", coordenadas);
			    					geo.append("geometry", geometry);
			    					updates.append("GEO", geo);
			    					update=new Document();
			    					update.append("$set", updates);
			    					c.AtualizaUm("sites", filtros, update);
			    					resp.setContentType("application/json");  
			    					resp.setCharacterEncoding("UTF-8"); 
			    					PrintWriter out = resp.getWriter();
			    					out.print("Dados Alterados");
		    					}else {
		    						resp.setContentType("application/json");  
		    						resp.setCharacterEncoding("UTF-8"); 
		    						PrintWriter out = resp.getWriter();
		    						out.print("Site Não Encontrado");
		    					}
		    				}else {
		    					updates.append(site.getString("colum"), site.getString("value"));
		    					update=new Document();
		    					update.append("$set", updates);
		    					c.AtualizaUm("sites", filtros, update);
		    					
		    				}
		    				site=null;
		    			}
		    			resp.setContentType("application/json");  
    					resp.setCharacterEncoding("UTF-8"); 
    					PrintWriter out = resp.getWriter();
    					out.print("Dados Alterados");
					}else {
						resp.setContentType("application/json");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Permissao Negada");
					}
				
				}else if(opt.equals("15")) {
					Bson filtro;
					List<Bson> filtros = new ArrayList<>();
					filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
					filtros.add(filtro);
					filtro=Filters.eq("site_ativo","Y");
					filtros.add(filtro);
					List<String>operadoras = c.ConsultaSimplesDistinct("sites","site_operadora", filtros);
					dados_tabela="";
					for(int cont=0;cont<operadoras.size();cont++) {
						dados_tabela=dados_tabela+"<option value='"+operadoras.get(cont)+"'>"+operadoras.get(cont).toUpperCase()+"</option>";
					}
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else if(opt.equals("16")) {
					Bson filtro;
					List<Bson> filtros = new ArrayList<>();
					filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
					filtros.add(filtro);
					filtro=Filters.eq("site_ativo","Y");
					filtros.add(filtro);
					List<String>operadoras = c.ConsultaSimplesDistinct("sites","site_operadora", filtros);
					dados_tabela="";
					for(int cont=0;cont<operadoras.size();cont++) {
						dados_tabela=dados_tabela+"<li><a class=\"dropdown-item\" href=\"#\" onclick=\"exportarTodosSites('"+operadoras.get(cont)+"')\">"+operadoras.get(cont).toUpperCase()+"</a></li>";
					}
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
			}catch (SQLException e) {
			    
				conn.fecharConexao();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	 }
	 public Date ChecaFormatoData(String data) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				return format.parse(data);
				
			}catch (ParseException e) {
				
				return null;
				
			} 
		}
	 public List<Double> verfica_coordenadas(String lat,String lng) {
	 try {
		 Double f_lat=Double.parseDouble(lat.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
		 Double f_lng=Double.parseDouble(lng.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
		 return Arrays.asList(f_lat,f_lng);
	 }catch (NumberFormatException e) {
			
			
			return Arrays.asList(-10.00,-10.00);
	}
 }
}
