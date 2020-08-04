package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;

/**
 * Servlet implementation class RelatoriosServlet
 */
@WebServlet("/OrcamentoServlet")
public class OrcamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrcamentoServlet() {
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
		mgmt_orcamento(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mgmt_orcamento(request, response);
	}
	public void mgmt_orcamento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ResultSet rs ;
		ResultSet rs2 ;
		String dados_tabela;
		String opt;
		
		String param1;
		
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		HttpSession session = req.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null || p.equals(null)) {
			System.out.println("Pessoal não encontrada no servlet de orcamento");
			return;
		}
		Conexao mysql = new Conexao();
		double money; 
		ConexaoMongo mongo = new ConexaoMongo();
		NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
		String moneyString;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		DateFormat f3 = DateFormat.getDateTimeInstance();
		opt=req.getParameter("opt");
		//System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Orcamento do MSTP Web - "+f3.format(time) +" OPT:"+opt);
		try {
		if(opt.equals("1")){
			dados_tabela="";
			Bson filtro;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			
			List<String> categorias = mongo.ConsultaSimplesDistinct("LPU", "ITEM_CATEGORIA", filtros);
			if(categorias.size()>0) {
				for(int indice=0;indice<categorias.size();indice++) {
					dados_tabela=dados_tabela+"<option value='"+categorias.get(indice)+"'>"+categorias.get(indice)+"</option>";
				}
			}
			resp.setContentType("application/json");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Orcamento opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
		}if(opt.equals("2")){
			param1=req.getParameter("categoria");
			dados_tabela="";
			Bson filtro;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ATIVA","Y");
			filtros.add(filtro);
			if(param1.equals("vazia")) {
				
			}else {
				filtro=Filters.eq("ITEM_CATEGORIA",param1);
				filtros.add(filtro);
			}
			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("LPU", filtros);
			MongoCursor<Document> resultado = findIterable.iterator();
			Document item;
			if(resultado.hasNext()) {
				while(resultado.hasNext()) {
					item = resultado.next();
					dados_tabela=dados_tabela+"<option value='"+item.getString("ITEM_NUM")+"'>"+item.getString("ITEM_NUM")+" - "+item.getString("ITEM_DESC")+"</option>";
				}
			}
			resp.setContentType("application/json");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Orcamento opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
		}if(opt.equals("3")){
			param1=req.getParameter("item");
			dados_tabela="";
			Bson filtro;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			if(param1.equals("vazia")) {
				
			}else {
				filtro=Filters.eq("ITEM_NUM",param1);
				filtros.add(filtro);
			}
			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("LPU", filtros);
			
			Document item=findIterable.first();
			dados_tabela="[\""+item.getString("ITEM_UNIDADE")+"\",\""+number_formatter.format(item.getDouble("ITEM_VALOR_C_ISS"))+"\",\""+item.getString("ITEM_DESC")+"\",\""+item.getDouble("ITEM_VALOR_C_ISS")+"\"]";
			resp.setContentType("application/json");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Orcamento opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
		}if(opt.equals("4")){
			param1=req.getParameter("site");
			dados_tabela="";
			Bson filtro;
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("site_id",param1);
			filtros.add(filtro);
			
			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("sites", filtros);
			
			Document item=findIterable.first();
			if(!item.isEmpty()) {
				dados_tabela="[\""+item.getString("site_endereco")+"\",\""+item.getString("site_bairro")+"\",\""+item.getString("site_municipio")+"\",\""+item.getString("site_uf")+"\"]";
			}else {
				dados_tabela="[\"site nao encontrado\"]";
			}
			resp.setContentType("application/json");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Orcamento opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			
		}if(opt.equals("5")){
			param1 = req.getParameter("orcamento");
			JSONObject orcamento = new JSONObject(param1);
			System.out.println(orcamento.toString());
			Calendar hoje = Calendar.getInstance();
			Bson filtro;
			List<Bson>filtros= new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			Long quantidadeOrcamentos=mongo.CountSimplesComFiltroInicioLimit("ORCAMENTOS", filtros);
			String id_cliente="Jtel-0"+quantidadeOrcamentos.toString()+"-"+ hoje.get(Calendar.YEAR);
			JSONArray itensLPU=orcamento.getJSONArray("itensServico");
			List<Object> itens = itensLPU.toList();
			Double valorTotal=0.0;
			for (int indice=0;indice<itensLPU.length();indice++) {
				JSONObject itenLPU = itensLPU.getJSONObject(indice);
				valorTotal=valorTotal+itenLPU.getDouble("VALORTOTAL");
			}
			/*{"descServico":"teste de cadastro",
			"cliente":"7",
			"site":"ZVT",
			"siteSigla_local":"xx",
			"itensServico":[
			                {"ITEM":"Fornecimento e instalação de cabo flex 10MM 1KVA",
			                	"uid":0,
			                	"QTDE":1,
			                	"VALORTOTAL":11.85865506,
			                	"boundindex":0,
			                	"visibleindex":0,
			                	"ID":0,
			                	"IDITEM":"5.5",
			                	"VALORUNITARIO":11.85865506,
			                	"UNIDADE":"M",
			                	"DESCONTO":0,
			                	"uniqueid":"2727-23-16-23-302420"},
			                {"ITEM":"Fornecimento e instalação de cabo flex 10MM 1KVA",
			                		"uid":1,
			                		"QTDE":1,
			                		"VALORTOTAL":11.85865506,
			                		"boundindex":1,
			                		"visibleindex":1,
			                		"ID":1,
			                		"IDITEM":"5.5",
			                		"VALORUNITARIO":11.85865506,
			                		"UNIDADE":"M",
			                		"DESCONTO":0,
			                		"uniqueid":"2716-29-21-19-261620"}],
			"siteUF":"RJ",
			"siteEnderco":"CUNHA BARBOSA, 41, ",
			"motivoServico":"motivo do cadastro ",
			"siteGRAM":"xx",
			"siteMunicipio":"RIO DE JANEIRO",
			"siteBairro":"GAMBOA",
			"contratoCliente":""}*/
			String uniqueID = UUID.randomUUID().toString();
			Document orcamentoMSTP = new Document();
			orcamentoMSTP.append("Empresa", p.getEmpresa().getEmpresa_id());
			orcamentoMSTP.append("DT_ADD", hoje.getTime());
			orcamentoMSTP.append("ADD_BY_USER", p.get_PessoaUsuario());
			orcamentoMSTP.append("ORCAMENTO_ID", uniqueID);
			orcamentoMSTP.append("ORCAMENTO_ID_CLIENTE", id_cliente);
			orcamentoMSTP.append("ORCAMENTO_ATIVO", "Y");
			orcamentoMSTP.append("ORCAMENTO_STATUS", "EM APROVAÇÃO");
			orcamentoMSTP.append("ORCAMENTO_SITE", orcamento.getString("site"));
			orcamentoMSTP.append("ORCAMENTO_SITE_ENDERECO", orcamento.getString("siteEnderco"));
			orcamentoMSTP.append("ORCAMENTO_SITE_UF", orcamento.getString("siteUF"));
			orcamentoMSTP.append("ORCAMENTO_SITE_BAIRRO", orcamento.getString("siteBairro"));
			orcamentoMSTP.append("ORCAMENTO_SITE_MUNICIPIO", orcamento.getString("siteMunicipio"));
			orcamentoMSTP.append("ORCAMENTO_SITE_GRAM", orcamento.getString("siteGRAM"));
			orcamentoMSTP.append("ORCAMENTO_SITE_SIGLA", orcamento.getString("siteSigla_local"));
			orcamentoMSTP.append("ORCAMENTO_SERVICO_DESCRICAO", orcamento.getString("descServico"));
			orcamentoMSTP.append("ORCAMENTO_SERVICO_MOTIVO", orcamento.getString("descServico"));
			orcamentoMSTP.append("ORCAMENTO_SERVICO_ITENS_LPU", itens);
			orcamentoMSTP.append("ORCAMENTO_VALOR_TOTAL", valorTotal);
			orcamentoMSTP.append("ORCAMENTO_CLIENTE", orcamento.getString("cliente"));
			orcamentoMSTP.append("ORCAMENTO_CLIENTE_CONTRATO", orcamento.getString("contratoCliente"));
			if(mongo.InserirSimples("ORCAMENTOS", orcamentoMSTP)) {
				resp.setContentType("application/json");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Inserção OK");
			}else {
				resp.setContentType("application/json");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Erro na INserção");
			}
		}if(opt.equals("6")){
			dados_tabela="";
			Bson filtro;
			List<Bson>filtros= new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ORCAMENTO_ATIVO","Y");
			filtros.add(filtro);
			Document lpuItemLinha;
			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("ORCAMENTOS", filtros);
			MongoCursor<Document> resultado =findIterable.iterator();
			dados_tabela= dados_tabela+"[";
			if(resultado.hasNext()){
				while(resultado.hasNext()) {
					lpuItemLinha=resultado.next();
					dados_tabela=dados_tabela+"{";
					dados_tabela=dados_tabela+"\"ID\":\""+lpuItemLinha.getString("ORCAMENTO_ID")+"\",";
					dados_tabela=dados_tabela+"\"ID_CLIENTE_MSTP\":\""+lpuItemLinha.getString("ORCAMENTO_ID_CLIENTE")+"\",";
					dados_tabela=dados_tabela+"\"CLIENTE\":\""+lpuItemLinha.getString("ORCAMENTO_CLIENTE")+"\",";
					dados_tabela=dados_tabela+"\"SITE\":\""+lpuItemLinha.getString("ORCAMENTO_SITE")+"\",";
					dados_tabela=dados_tabela+"\"MUNICIPIO\":\""+lpuItemLinha.getString("ORCAMENTO_SITE_MUNICIPIO")+"\",";
					dados_tabela=dados_tabela+"\"UF\":\""+lpuItemLinha.getString("ORCAMENTO_SITE_UF")+"\",";
					dados_tabela=dados_tabela+"\"DESC_SERVICO\":\""+lpuItemLinha.getString("ORCAMENTO_SERVICO_DESCRICAO")+"\",";
					dados_tabela=dados_tabela+"\"VALOR\":\""+lpuItemLinha.getDouble("ORCAMENTO_VALOR_TOTAL")+"\",\n";
					dados_tabela=dados_tabela+"\"STATUS\":\""+lpuItemLinha.getString("ORCAMENTO_STATUS")+"\",\n";
					dados_tabela=dados_tabela+"\"CRIADOPOR\":\""+lpuItemLinha.getString("ADD_BY_USER")+"\",\n";
					dados_tabela=dados_tabela+"\"DT_CRIADO\":\""+f3.format(lpuItemLinha.getDate("DT_ADD"))+"\"},\n";
					
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
			}
			dados_tabela=dados_tabela+"]";
			//System.out.println(dados_tabela);
			resp.setContentType("application/html");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print(dados_tabela);
			out.close();
		}if(opt.equals("7")){
			param1=req.getParameter("orcamentoID");
			dados_tabela="";
			Bson filtro;
			List<Bson>filtros= new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("ORCAMENTO_ATIVO","Y");
			filtros.add(filtro);
			filtro=Filters.eq("ORCAMENTO_ID",param1);
			filtros.add(filtro);
			Document orcamento;
			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("ORCAMENTOS", filtros);
			orcamento = findIterable.first();
			rs=mysql.Consulta("select * from customer_table where customer_id="+orcamento.getString("ORCAMENTO_CLIENTE"));
			if(rs.next()) {
				//System.out.println(rs.getString("logoBase64"));
				orcamento.append("logoCustomer", rs.getString("logoBase64"));
			}
			rs=mysql.Consulta("select * from empresas where id_empresa="+p.getEmpresa().getEmpresa_id());
			if(rs.next()) {
				//System.out.println(rs.getString("logoBase64"));
				orcamento.append("logoEmpresa", rs.getString("logoBase64"));
			}
			if(!orcamento.isEmpty()) {
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(orcamento.toJson());
				out.close();
			}
		}
		mongo.fecharConexao();
		mysql.getConnection().commit();
		mysql.getConnection().close();
		}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} 
		
	}
}
