	package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

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

import classes.Conexao;
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
			ResultSet rs,rs2 ;
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
			Calendar d = Calendar.getInstance();
			DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
			DateFormat f3 = DateFormat.getDateTimeInstance();
			HttpSession session = req.getSession(true);
			Pessoa p = (Pessoa) session.getAttribute("pessoa");
			Conexao conn = (Conexao) session.getAttribute("conexao");
			double money; 
			NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
			String moneyString;
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
				
			}else if(opt.equals("2")) {
				//System.out.println("Carregando Sites");
				//System.out.println("conexao ativa:"+ conn.getConnection().isClosed());
				
				//System.out.println("Carregando Sites");
				
					//System.out.println("achou Sites");
					dados_tabela="<table id=\"tabela_sites\" "
							+ "data-unique-id=\"siteiD_tbl\" "
							+ "data-use-row-attr-func=\"true\" "
							+ "data-toolbar=\"#toolbar_tabela_Sites\"  "
							+ "data-show-refresh=\"true\" "
							+ "data-toggle=\"table\"  "
							+ "data-filter-control=\"true\"  "
							+ "data-pagination=\"true\" "
							+ "data-height=\"525\" "
							+ "data-side-pagination=\"server\" "
							+ "data-locale=\"pt-BR\" "
							+ "data-url=\"./SiteMgmt?opt=5\" "
							+ "data-sort-name=\"siteiD_tbl\" "
							+ "data-page-size=\"10\" "
							+ "data-page-list=\"[10, 20, 50, 100, 200]\" "
							+ "data-sort-order=\"desc\" "
							+ "data-show-columns=\"true\" "
							+ "data-search=\"true\">" +"\n";
					dados_tabela=dados_tabela + "<thead>"+"\n";
					dados_tabela=dados_tabela +"<tr>"+"\n";
					dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"site_tbl_operacoes\">Operações</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"siteiDsys_tbl\" data-visible=\"false\">ID Sistema</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"siteOperadora_tbl\">Site Operadora</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"siteiD_tbl\">SiteID</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"Sitelat_tbl\">Latitude</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"Sitelng_tbl\">Longitude</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"Sitecidade_tbl\">Cidade</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"Sitebairro_tbl\">Bairro</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"SiteUF_tbl\">UF</th>"+"\n";
					dados_tabela=dados_tabela +"</tr>"+"\n";
					dados_tabela=dados_tabela +"</thead>"+"\n";
					dados_tabela=dados_tabela +"<tbody>"+"\n";
					
					
					dados_tabela=dados_tabela + "</tbody>";
					dados_tabela=dados_tabela + "</table>";
					//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
					//System.out.println("Resposta Consulta 3 Enviada!");
				
			}else if(opt.equals("3")) {
				System.out.println("Carregando sites no mapa");
				rs=conn.Consulta("Select distinct local_registro,usuario from registros where data_dia='"+f2.format(d.getTime())+"' and empresa='"+p.getEmpresa().getEmpresa_id()+"'");
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
							            "\"coordinates\": [";
							                    
							                
								dados_tabela=dados_tabela+verfica_coordenadas(rs2.getString("site_longitude"),rs2.getString("site_latitude"),rs2.getString("site_id"))+"]},";
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
					
						    		
							
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else {
					PrintWriter out = resp.getWriter();
					out.print("vazia");
				}
				
			}else if(opt.equals("4")) {
				
				param1=req.getParameter("site");
				if(conn.Update_simples("update sites set site_ativo='N' where sys_id="+param1)){
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Site Desabilitado com Sucesso!");
				}
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
			}else if(opt.equals("6")) {
				
				param1=req.getParameter("sites");
				//System.out.println(param1);
				if(conn.Update_simples("update sites set site_ativo='N' where sys_id in ("+param1+")")){
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Sites Desabilitados com Sucesso!");
				}
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
			}else if(opt.equals("9")){
				param1=req.getParameter("id");
				query="update site_aprova set aprovado='R',dt_aprovado='"+f3.format(time)+"' where sysid="+param1;
				if(conn.Update_simples(query)) {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Solicitação rejeitada com sucesso");
				}
			}else if(opt.equals("10")) {
				System.out.println("Carregando TODOS os sites no mapa");
				rs=conn.Consulta("select distinct site_operadora from sites where site_ativo='Y' and site_latitude<>'' and site_longitude<>'' and site_operadora<>'' and empresa='"+p.getEmpresa().getEmpresa_id()+"'");
				dados_tabela="";
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela=  "{";
					while(rs.next()) {
						//System.out.println(rs.getString("site_operadora"));
						//System.out.println(dados_tabela);
						dados_tabela=dados_tabela+"\n\""+rs.getString("site_operadora")+"\":{"+
							    "\"type\": \"FeatureCollection\","+
							    "\"operadora\": \""+rs.getString("site_operadora")+"\","+
							    "\"features\": [";
						rs2=conn.Consulta("select * from sites where site_operadora='"+rs.getString("site_operadora")+"' and site_ativo='Y' and site_latitude<>'' and site_longitude<>'' and empresa='"+p.getEmpresa().getEmpresa_id()+"' limit 600");
						if(rs2.next()) {
										rs2.beforeFirst();
										while(rs2.next()) {
										dados_tabela=dados_tabela+"{" +
						
							            "\"type\": \"Feature\","+
							            "\"geometry\": {"+
							            "\"type\": \"Point\","+
							            "\"coordinates\": [";
							                    
							                
								dados_tabela=dados_tabela+verfica_coordenadas(rs2.getString("site_longitude"),rs2.getString("site_latitude"),rs2.getString("site_id"))+"]},\n";
								dados_tabela=dados_tabela+"\"properties\": {"+
					                "\"Site_Id\": \""+rs2.getString("site_id").replaceAll("'", "_")+"\","+
					                "\"Site Nome\": \""+rs2.getString("site_nome").replaceAll("'", "_")+"\","+
					                "\"Site_Operadora\": \""+rs2.getString("site_operadora")+"\""+
					            "}\n"+
					        "},\n";
							}
										dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
										dados_tabela=dados_tabela+"\n]},\n";
												
						}else {
							
							dados_tabela=dados_tabela+"]},\n";
						}
					}
							
					//dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					//dados_tabela=dados_tabela+"}";	
						
					
			}
				//System.out.println(dados_tabela);
				
				query="select *  from localiza_usuarios where SUBSTRING(dt_add,1,10) = '"+time.toString().substring(0, 10)+"' and empresa="+p.getEmpresa().getEmpresa_id();
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
				
				   
					
						    		
						
					System.out.println("TODOS OS SITE CARREGADOS COM SUCESSO!");
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				
				
			}else if(opt.equals("11")) {
				param1=req.getParameter("operadora");
				System.out.println("Iniciando export de Sites da Operadora - " + param1);
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("SitesMSTP");
				Row row;
				long rowIndex=0;
				int colIndex=0;
				int contador=1;
				int total_colunas;
				Cell cell;
				query="Select sys_id,site_id,site_latitude,site_longitude,site_municipio,site_bairro,site_uf,site_nome,site_sequencial,site_operadora,site_cep,site_owner from sites where site_operadora='"+param1+"' and empresa="+p.getEmpresa().getEmpresa_id()+" order by sys_id asc";
				System.out.println(query);
				rs=conn.Consulta(query);
				if(rs.next()) {
					rowIndex=1;
	            	row = sheet.createRow((short) rowIndex);
	            	total_colunas=rs.getMetaData().getColumnCount();
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
                while(contador<total_colunas) {
	                cell = row.createCell((short) colIndex);
	                cell.setCellValue(rs.getMetaData().getColumnLabel(contador));
	                sheet.autoSizeColumn(colIndex);
	                cell.setCellStyle(cellStyle);
	                colIndex=colIndex+1;
	                contador=contador+1;
                }
                rs.beforeFirst();
                while(rs.next()) {
                	rowIndex=rowIndex+1;
	            	row = sheet.createRow((int)rowIndex);
	            	//System.out.println("linha:"+rowIndex);
	            	contador=1;
	            	colIndex=0;
	            	for(contador=1;contador<total_colunas;contador++) {
	            		//System.out.println("coluna:"+contador);
	            		cell = row.createCell((short) colIndex);
	                    cell.setCellValue(rs.getString(contador));
	                    //sheet.autoSizeColumn(colIndex);
	                    cell.setCellStyle(cellStyle2);
	                    colIndex=colIndex+1;
	                    
	            	}
                }
                
				System.out.println("Arquivo Exportde Sites Criado & finalizado");
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
			}
			}catch (SQLException e) {
			
				conn.fecharConexao();
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
 
	 public String verfica_coordenadas(String lng,String lat,String site) {
	 try {
		 Float f_lat=Float.parseFloat(lat.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
		 Float f_lng=Float.parseFloat(lng.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
		 return f_lng+","+f_lat;
	 }catch (NumberFormatException e) {
			
			System.out.println(site+ " com dados incorretos");
			return "51.505, -0.09";
	}
 }
}
