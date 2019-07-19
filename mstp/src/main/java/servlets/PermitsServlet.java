package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;

/**
 * Servlet implementation class PermitsServlet
 */
@WebServlet("/PermitsServlet")
public class PermitsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PermitsServlet() {
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
		gerenciamentoPermits(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamentoPermits(request, response);
	}
 public void gerenciamentoPermits(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
	 HttpSession session = req.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null) {
			resp.sendRedirect("./mstp_login.html");
			return;
		}
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		Conexao mysql = (Conexao) session.getAttribute("conexao");
		ConexaoMongo mongo = new ConexaoMongo();
		DateFormat f3 = DateFormat.getDateTimeInstance();
		String opt;
		String param1;
		String param2;
		String param3;
		String param4;
		String param5;
		String param6;
		String query;
		String dados_tabela="";
		opt=req.getParameter("opt");
		if(opt.equals("1")) {
			Document campo = new Document();
			param1=req.getParameter("campo");
			param2=req.getParameter("grupo");
			param3=req.getParameter("tipo");
			param4=req.getParameter("posicao");
			param5=req.getParameter("tabelaMaster");
			param6=req.getParameter("desc");
			campo.append("campo", param1);
			campo.append("descricao", param6);
			campo.append("grupo", param2);
			campo.append("tipo", param3);
			campo.append("Empresa", p.getEmpresa().getEmpresa_id());
			campo.append("tabela_master", param5);
			campo.append("ordenacao", Integer.parseInt(param4));
			campo.append("ativo", "Y");
			campo.append("dt_add", time);
			campo.append("add_by", p.get_PessoaUsuario());
			mongo.InserirSimpels("PermitsCampos", campo);
			mongo.fecharConexao();
		}else if(opt.equals("2")) {
			Bson filtro;
			Document campo;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ativo","Y");
			filtros.add(filtro);
			dados_tabela="<table id=\"tabela_campos_permits\"  data-filter-control=\"true\" data-toggle=\"table\"  data-pagination=\"true\" data-toolbar=\"#toolbar_campos_permits\"  data-search=\"true\" data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\">" +"\n";
			dados_tabela=dados_tabela + "<thead>"+"\n";
			dados_tabela=dados_tabela +"<tr>"+"\n";
			dados_tabela=dados_tabela +" <th data-checkbox=\"true\" data-formatter=\"stateFormatter\"></th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"campo_id_permits\" data-visible=\"false\">id</th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"campo1_permits\" data-sortable=\"true\">Campo Id</th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"campo2_permits\" data-sortable=\"true\">Grupo</th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"campo3_permits\" data-sortable=\"true\" data-filter-control=\"select\">Tipo</th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"campo4_permits\" data-sortable=\"true\" data-filter-control=\"select\">Tabela Master</th>"+"\n";
			
			dados_tabela=dados_tabela +"</tr>"+"\n";
			dados_tabela=dados_tabela +"</thead>"+"\n";
			dados_tabela=dados_tabela +"<tbody>"+"\n";
			FindIterable<Document> findIterable= mongo.ConsultaOrdenada2("PermitsCampos", "ordenacao", 1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			if(resultado.hasNext()){
				
				while(resultado.hasNext()){
					campo=resultado.next();
					dados_tabela=dados_tabela + "<tr data-index=\"\" data-value=\"teste\">"+"\n";
					dados_tabela=dados_tabela + " <td></td>"+"\n";
					dados_tabela=dados_tabela + " <td>"+campo.getObjectId("_id")+"</td>"+"\n";
					dados_tabela=dados_tabela + " <td>"+campo.getString("campo")+"</td>"+"\n";
					dados_tabela=dados_tabela + " <td>"+campo.getString("grupo")+"</td>"+"\n";
					dados_tabela=dados_tabela + " <td>"+campo.getString("tipo")+"</td>"+"\n";
					dados_tabela=dados_tabela + " <td>"+campo.getString("tabela_master")+"</td>"+"\n";
			
					dados_tabela=dados_tabela + "</tr>"+"\n";
				}
				
				//System.out.println("Resposta Consulta 3 Enviada!");
			}
			
			resultado.close();
			dados_tabela=dados_tabela + "</tbody>";
			dados_tabela=dados_tabela + "</table>";
			//System.out.println(dados_tabela);
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			mongo.fecharConexao();
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Permits opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
	}else if(opt.equals("3")){
		if(p.getPerfil_funcoes().contains("PermitsManager")) {
            param1=req.getParameter("ordem");
            Document update;
            Document comando;
            Document filtro;
            
            JSONObject jObj = new JSONObject(param1); 
			JSONArray campos = jObj.getJSONArray("ordem"); 
			
		
			 time = new Timestamp(System.currentTimeMillis());
			int tamanho=(int) jObj.get("tamanho");
			int i=0;
			//filtro.append("Empresa", p.getEmpresa().getEmpresa_id());
			ObjectId id= new ObjectId("4f693d40e4b04cde19f17205"); 
			while(i<tamanho){
				update=new Document();
				filtro=new Document();
				comando=new Document();
				id= new ObjectId(campos.getJSONObject(i).getString("campoid")); 
				filtro.append("_id", id);
				update.append("ordenacao",campos.getJSONObject(i).getInt("posicao"));
				comando.append("$set", update);
				mongo.AtualizaUm("PermitsCampos", filtro, comando);
				//query="update rollout_campos set ordenacao="+campos.getJSONObject(i).getInt("posicao")+" where field_id="+campos.getJSONObject(i).getString("campoid")+" and empresa="+p.getEmpresa().getEmpresa_id() +" and rollout_nome='"+param2+"'";
				//mysql.Alterar(query);
				//query="update rollout set ordenacao="+campos.getJSONObject(i).getInt("posicao")+" where milestone='"+campos.getJSONObject(i).getString("campo_nome")+"' and empresa="+p.getEmpresa().getEmpresa_id();
				//mysql.Alterar(query);
				i++;
			}
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print("Ordem de campos atualizada!");
			out.close();
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}
		mongo.fecharConexao();
		}else if(opt.equals("4")){
			if(p.getPerfil_funcoes().contains("PermitsManager")) {
			 param1=req.getParameter("campos");
	            Document update = new Document();
	            Document comando = new Document();
	            Document filtro = new Document();
	            ObjectId id= new ObjectId(param1); 
				filtro.append("_id", id);
				update.append("ativo","N");
				comando.append("$set", update);
				mongo.AtualizaUm("PermitsCampos", filtro, comando);
			}
			mongo.fecharConexao();
		}else if(opt.equals("5")){
			Bson filtro;
			Document campo;
			String campos_aux;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ativo","Y");
			filtros.add(filtro);
			filtro=Filters.eq("tabela_master","true");
			filtros.add(filtro);
			FindIterable<Document> findIterable= mongo.ConsultaOrdenada2("PermitsCampos", "ordenacao", 1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			dados_tabela="{ \"campos\": ["+"\n";
			campos_aux= "\"campos2\":["+"\n";
			if(resultado.hasNext()){
				while(resultado.hasNext()) {
					campo=resultado.next();
					if(campo.getString("tipo").equals("Texto")) {
						dados_tabela=dados_tabela+"{ \"name\": \""+campo.getString("campo")+"\", \"type\": \"string\"},"+ "\n";
						campos_aux=campos_aux+"{ \"text\": \""+campo.getString("campo")+"\",\"datafield\":\""+campo.getString("campo")+"\",\"columntype\": \"textbox\",\"width\":80},"+ "\n";
					}else if(campo.getString("tipo").equals("Data")) {
						dados_tabela=dados_tabela+"{ \"name\": \""+campo.getString("campo")+"\", \"type\": \"date\"},"+ "\n";
						campos_aux=campos_aux+"{ \"text\": \""+campo.getString("campo")+"\",\"datafield\":\""+campo.getString("campo")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\",\"cellsformat\":\"dd/MM/yyyy\",\"width\":100},"+ "\n";
					}else if(campo.getString("tipo").equals("Numero")) {
						dados_tabela=dados_tabela+"{ \"name\": \""+campo.getString("campo")+"\", \"type\": \"number\"},"+ "\n";
						campos_aux=campos_aux+"{ \"text\": \""+campo.getString("campo")+"\",\"datafield\":\""+campo.getString("campo")+"\",\"width\":80},"+ "\n";
					}
					
				}
				campos_aux=campos_aux.substring(0,campos_aux.length()-2);
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
				dados_tabela=dados_tabela+"],"+"\n";
				dados_tabela=dados_tabela+campos_aux+"]";
			}
			dados_tabela=dados_tabela+"}";
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			out.close();
			mongo.fecharConexao();
		}else if(opt.equals("6")){
			Bson filtro;
			Document campo;
			String campos_aux;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ativo","Y");
			filtros.add(filtro);
			filtro=Filters.eq("grupo","Aquisição");
			filtros.add(filtro);
			FindIterable<Document> findIterable= mongo.ConsultaOrdenada2("PermitsCampos", "ordenacao", 1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			dados_tabela="["+"\n";
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					campo=resultado.next();
					if(campo.getString("tipo").equals("Texto")) {
						dados_tabela=dados_tabela+"{\"bind\":\"textBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"text\","+"\n";
					}else if(campo.getString("tipo").equals("Data")) {
						dados_tabela=dados_tabela+"{\"bind\":\"dateBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"date\","+"\n";
					}else if(campo.getString("tipo").equals("Numero")) {
						dados_tabela=dados_tabela+"{\"bind\":\"nubmberBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"number\","+"\n";
					}
					dados_tabela=dados_tabela+"\"label\": \""+campo.getString("campo")+"\","+"\n";
					dados_tabela=dados_tabela+"\"labelPosition\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"labelWidth\": \"30%\","+"\n";
					dados_tabela=dados_tabela+"\"align\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"width\": \"250px\","+"\n";
					dados_tabela=dados_tabela+"\"required\": \"true\"},"+"\n";
	                    
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
			}
			dados_tabela=dados_tabela+"]"+"\n";
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			out.close();
			mongo.fecharConexao();
		}else if(opt.equals("7")){
			Bson filtro;
			Document campo;
			String campos_aux;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ativo","Y");
			filtros.add(filtro);
			filtro=Filters.eq("grupo","Cadastro");
			filtros.add(filtro);
			FindIterable<Document> findIterable= mongo.ConsultaOrdenada2("PermitsCampos", "ordenacao", 1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			dados_tabela="["+"\n";
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					campo=resultado.next();
					if(campo.getString("tipo").equals("Texto")) {
						dados_tabela=dados_tabela+"{\"bind\":\""+campo.getString("campo")+"\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"text\","+"\n";
					}else if(campo.getString("tipo").equals("Data")) {
						dados_tabela=dados_tabela+"{\"bind\":\""+campo.getString("campo")+"\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"date\","+"\n";
					}else if(campo.getString("tipo").equals("Numero")) {
						dados_tabela=dados_tabela+"{\"bind\":\""+campo.getString("campo")+"\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"number\","+"\n";
					}
					dados_tabela=dados_tabela+"\"label\": \""+campo.getString("campo")+"\","+"\n";
					dados_tabela=dados_tabela+"\"labelPosition\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"labelWidth\": \"30%\","+"\n";
					dados_tabela=dados_tabela+"\"align\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"width\": \"250px\","+"\n";
					dados_tabela=dados_tabela+"\"required\": \"true\"},"+"\n";
	                    
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
			}
			dados_tabela=dados_tabela+"]"+"\n";
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			out.close();
			mongo.fecharConexao();
		}else if(opt.equals("8")){
			Bson filtro;
			Document campo;
			String campos_aux;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ativo","Y");
			filtros.add(filtro);
			filtro=Filters.eq("grupo","Licenciamento");
			filtros.add(filtro);
			FindIterable<Document> findIterable= mongo.ConsultaOrdenada2("PermitsCampos", "ordenacao", 1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			dados_tabela="["+"\n";
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					campo=resultado.next();
					if(campo.getString("tipo").equals("Texto")) {
						dados_tabela=dados_tabela+"{\"bind\":\"textBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"text\","+"\n";
					}else if(campo.getString("tipo").equals("Data")) {
						dados_tabela=dados_tabela+"{\"bind\":\"dateBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"date\","+"\n";
					}else if(campo.getString("tipo").equals("Numero")) {
						dados_tabela=dados_tabela+"{\"bind\":\"nubmberBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"number\","+"\n";
					}
					dados_tabela=dados_tabela+"\"label\": \""+campo.getString("campo")+"\","+"\n";
					dados_tabela=dados_tabela+"\"labelPosition\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"labelWidth\": \"30%\","+"\n";
					dados_tabela=dados_tabela+"\"align\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"width\": \"250px\","+"\n";
					dados_tabela=dados_tabela+"\"required\": \"true\"},"+"\n";
	                    
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
			}
			dados_tabela=dados_tabela+"]"+"\n";
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			out.close();
			mongo.fecharConexao();
		}else if(opt.equals("9")){
			Bson filtro;
			Document campo;
			String campos_aux;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ativo","Y");
			filtros.add(filtro);
			filtro=Filters.eq("grupo","Financeiro");
			filtros.add(filtro);
			FindIterable<Document> findIterable= mongo.ConsultaOrdenada2("PermitsCampos", "ordenacao", 1, filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			dados_tabela="["+"\n";
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					campo=resultado.next();
					if(campo.getString("tipo").equals("Texto")) {
						dados_tabela=dados_tabela+"{\"bind\":\"textBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"text\","+"\n";
					}else if(campo.getString("tipo").equals("Data")) {
						dados_tabela=dados_tabela+"{\"bind\":\"dateBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"date\","+"\n";
					}else if(campo.getString("tipo").equals("Numero")) {
						dados_tabela=dados_tabela+"{\"bind\":\"nubmberBoxValue\","+"\n";
						dados_tabela=dados_tabela+"\"type\":\"number\","+"\n";
					}
					dados_tabela=dados_tabela+"\"label\": \""+campo.getString("campo")+"\","+"\n";
					dados_tabela=dados_tabela+"\"labelPosition\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"labelWidth\": \"30%\","+"\n";
					dados_tabela=dados_tabela+"\"align\": \"left\","+"\n";
					dados_tabela=dados_tabela+"\"width\": \"250px\","+"\n";
					dados_tabela=dados_tabela+"\"required\": \"true\"},"+"\n";
	                    
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
			}
			dados_tabela=dados_tabela+"]"+"\n";
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			out.close();
			mongo.fecharConexao();
		}else if(opt.equals("10")){
			param1=req.getParameter("cadastroCampos");
			param2=req.getParameter("AquisicaoCampos");
			param3=req.getParameter("LicenciamentoCampos");
			param4=req.getParameter("FinanceiroCampos");
			Document campo_cadastro = Document.parse(param1);
			Document campo_aquisicao = Document.parse(param2);
			Document campo_licenciamento= Document.parse(param3);
			Document campo_financeiro = Document.parse(param4);
			campo_cadastro.append("Empresa", p.getEmpresa().getEmpresa_id());
			campo_cadastro.append("ativo", "Y");
			campo_cadastro.append("Aquisicao", campo_aquisicao);
			campo_cadastro.append("Licenciamento", campo_licenciamento);
			campo_cadastro.append("Financeiro", campo_financeiro);
			mongo.InserirSimpels("Permits", campo_cadastro);
			mongo.fecharConexao();
		}else if(opt.equals("11")){
			param1=req.getParameter("pagesize");
			param2=req.getParameter("pagenum");
			Bson filtro;
			Document campo;
			Document linha;
			String campos_aux;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ativo","Y");
			filtros.add(filtro);
			filtro=Filters.eq("tabela_master","true");
			filtros.add(filtro);
			List<String> campos= mongo.ConsultaSimplesDistinct("PermitsCampos", "campo", filtros);
			dados_tabela="";
			dados_tabela= dados_tabela+"[";
			if(campos.size()>0) {
				filtros = new ArrayList<>();
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtros.add(filtro);
				filtro=Filters.eq("ativo","Y");
				filtros.add(filtro);
				FindIterable<Document> findIterable= mongo.ConsultaCollectioncomFiltrosLista("Permits", filtros);
				MongoCursor<Document> resultado = findIterable.iterator();
				if(resultado.hasNext()) {
					Long totallinhas=mongo.CountSimplesComFiltroInicioLimit("Permits", filtros);
					dados_tabela=dados_tabela+"{\"totalRecords\":\""+totallinhas+"\",\n";
					while(resultado.hasNext()) {
						linha=resultado.next();
						for(int indice=0;indice<campos.size();indice++) {
							dados_tabela=dados_tabela+"\""+campos.get(indice)+"\":\""+linha.getString(campos.get(indice))+"\",";
						}
						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
						dados_tabela=dados_tabela+"},"+"\n"+"{";
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-3);
				}else {
					dados_tabela=dados_tabela+"{\"totalRecords\":\"0\"}";
				}
			}
			dados_tabela=dados_tabela+"]";
			System.out.println(dados_tabela);
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			out.close();
			mongo.fecharConexao();
		}
 }
}
