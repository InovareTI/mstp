package servlets;

import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import classes.ClienteEmpresa;
import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;
import classes.ProjetoCliente;
import classes.Semail;



/**
 * Servlet implementation class POControl_Servlet
 */
@WebServlet("/POControl_Servlet")
public class POControl_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public POControl_Servlet() {
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
		gerenciamento_po(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamento_po(request,response);
	}
    public void gerenciamento_po(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	ResultSet rs ;
		ResultSet rs2 ;
		String dados_tabela;
		String opt;
		String insere;
		String param1;
		String param2;
		String param3;
		String param4;
		String param5;
		String param6;
		String param7;
		String param8;
		String param9;
		
		String retorno="";
		String query;
		
		
		
		
		int last_id;
		
		last_id=0;
		param1="";
		query="";
		param2="";
		param3="";
		param4="";
		param5="";
		param6="";
		param7="";
		param8="";
		param9="";
	
		
		
		insere="";
		opt="";
		dados_tabela="";
		rs2=null;
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		HttpSession session = req.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		Conexao mysql = (Conexao) session.getAttribute("conexao");
		ConexaoMongo mongo = new ConexaoMongo();
		double money; 
		NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
		String moneyString;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		DateFormat f3 = DateFormat.getDateTimeInstance();
		opt=req.getParameter("opt");
		//System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" acessando servlet de Operações Gerais opt - "+ opt );
		//System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Operações Gerais do MSTP Web - "+f3.format(time)+" opt:"+opt);
		try {
		if(opt.equals("1")){
			query="";
			dados_tabela="<table id=\"tabela_de_po\"  data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\"  data-search=\"true\" data-advanced-search=\"true\">" +"\n";
			dados_tabela=dados_tabela + "<thead>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"po_mstp_id\">MSTP ID</th>"+"\n";
			dados_tabela=dados_tabela +"<tr>"+"\n";
			dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"po\" data-filter-control=\"select\">PO</th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"po1\" data-filter-control=\"select\">Emissão</th>"+"\n";
			dados_tabela=dados_tabela +" <th data-field=\"po2\">Validada</th>"+"\n";
			dados_tabela=dados_tabela +" <th style data-field=\"po3\">Data Validação</th>"+"\n";
			dados_tabela=dados_tabela +" <th style data-field=\"po4\">Emissor</th>"+"\n";
			dados_tabela=dados_tabela +" <th style data-field=\"po5\" data-filter-control=\"select\">Data Carregada</th>"+"\n";
			dados_tabela=dados_tabela +" <th style data-field=\"po8\">Valor Total</th>"+"\n";
			dados_tabela=dados_tabela +" <th style data-field=\"po9\">Arquivo PO</th>"+"\n";
			dados_tabela=dados_tabela +"</tr>"+"\n";	
			dados_tabela=dados_tabela +"</thead>"+"\n";
			dados_tabela=dados_tabela +"<tbody>"+"\n";
			if(p.getPerfil_funcoes().contains("POManager")) {
			p.set_PessoaPerfil_campos(mysql,"po_table");
			query="Select "+p.get_PessoaPerfil_campos()+" from po_table where empresa="+p.getEmpresa().getEmpresa_id()+" and PO_ATIVA='Y' order by po_id desc";
			//System.out.println(query);
			rs=mysql.Consulta(query);
			//System.out.println("Consulta Realizada");
			
				if(rs.next()){
					rs.beforeFirst();
					//System.out.println("Entrou no RS");
					if(p.get_PessoaPerfil_nome().equals("Financeiro")){
						while(rs.next()){
							//System.out.println("Entrou no while");
							money = Double.parseDouble(rs.getString("total_po"));
							moneyString = number_formatter.format(money);
							dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("po_id")+"</a></td>"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td><a href='#' onclick=MostraItemPO("+rs.getString("po_number")+")>"+rs.getString("po_number")+"</a></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("dt_emitida")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("validada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getDate("dt_validada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("empresa_emissora")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+f2.format(rs.getDate("dt_registrada"))+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+moneyString+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td><a href='#' onclick=downloadPO("+rs.getInt("id_po_file")+")>PDF</a></td>"+"\n";
							dados_tabela=dados_tabela + "</tr>"+"\n";
						}
					}else{
						while(rs.next()){
							dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("po_number")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getDate("dt_emitida")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("validada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getDate("dt_validada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("empresa_emissora")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("dt_registrada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td> - </td>"+"\n";
							dados_tabela=dados_tabela + " <td><a href='#' onclick=downloadPO("+rs.getInt("id_po_file")+")>PDF</a></td>"+"\n";
							dados_tabela=dados_tabela + "</tr>"+"\n";
						}
					}
					
					//System.out.println("Resposta Consulta 3 Enviada!");
				}
			}
				dados_tabela=dados_tabela + "</tbody>";
				dados_tabela=dados_tabela + "</table>";
				//System.out.println(dados_tabela);
				
			if(p.getEmpresa().getEmpresa_id()==1) {
				dados_tabela="";
				Bson filtro;
				Document poItemLinha;
				List<Bson> filtros= new ArrayList<>();
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtros.add(filtro);
				filtro=Filters.eq("PO_ATIVA","Y");
				filtros.add(filtro);
				FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("PO", filtros);
				MongoCursor<Document> resultado =findIterable.iterator();
				dados_tabela= dados_tabela+"[";
				if(resultado.hasNext()){
					while(resultado.hasNext()) {
						poItemLinha=resultado.next();
						dados_tabela=dados_tabela+"{";
						dados_tabela=dados_tabela+"\"PO NUMBER\":\""+poItemLinha.getString("PO NO")+"\",";
						dados_tabela=dados_tabela+"\"ITEM\":\""+poItemLinha.getString("Item Description")+"\",";
						dados_tabela=dados_tabela+"\"ITEM_CODE\":\""+poItemLinha.getString("Item Code")+"\",";
						dados_tabela=dados_tabela+"\"PO LINE\":\""+poItemLinha.getString("PO Line NO")+"\",";
						if(poItemLinha.get("Publish Date")!=null) {
							dados_tabela=dados_tabela+"\"PUBLISHED\":\""+poItemLinha.getString("Publish Date")+"\",";
						}else if(poItemLinha.get("creation_date")!=null) {
							dados_tabela=dados_tabela+"\"PUBLISHED\":\""+poItemLinha.getString("creation_date")+"\",";
						}else {
							dados_tabela=dados_tabela+"\"PUBLISHED\":\"NO DATE FOUND\",";
						}
						
						dados_tabela=dados_tabela+"\"Carregada\":\""+poItemLinha.getDate("DT_CARREGADA")+"\",";
						dados_tabela=dados_tabela+"\"VALOR UNITARIO\":\""+poItemLinha.getString("Unit Price")+"\",";
						dados_tabela=dados_tabela+"\"QTDE\":\""+poItemLinha.getString("Requested Qty")+"\",";
						dados_tabela=dados_tabela+"\"VALOR TOTAL\":\""+poItemLinha.getString("Line Amount")+"\"},";
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
				}
				dados_tabela=dados_tabela+"]";
				
			}
				//System.out.println(dados_tabela);
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				out.close();
			}else if(opt.equals("2")){
				//System.out.println("Deletando PO's");
				param1=req.getParameter("po");
				//System.out.println(param1);
				if(param1.indexOf("'")>0){
					param1.replace("'","");
				}
				if(param1.indexOf(",")>0){
					String[] po=param1.split(",");
					for(int i=0;i<po.length;i++){
						//System.out.println("delete from po_table where po_number="+po[i]);
						mysql.Alterar("update po_table set PO_ATIVA='N',DT_PO_DESATIVADA='"+time+"',RESP_PO_DEATIVADA='"+p.get_PessoaUsuario()+"' where po_id="+po[i]);
						mysql.Excluir("delete from po_item_table where po_number='"+po[i]+"'");
					}
				}else{
					//System.out.println("delete from po_table where po_number="+param1);
					mysql.Excluir("update po_table set PO_ATIVA='N',DT_PO_DESATIVADA='"+time+"',RESP_PO_DEATIVADA='"+p.get_PessoaUsuario()+"' where po_id="+param1);
					mysql.Excluir("delete from po_item_table where po_number='"+param1+"'");
				}
			}else if(opt.equals("3")){
				param1=req.getParameter("po");
				rs=mysql.Consulta("Select po_item_table.* from po_item_table,po_table where po_table.po_number='"+param1+"' and po_table.PO_ATIVA='Y' and po_table.po_number=po_item_table.po_number and po_item_table.empresa="+p.getEmpresa().getEmpresa_id()+" order by po_item_id");
				dados_tabela="<table id=\"tabela_de_item_po\" data-toolbar=\"#toolbar_tabela_item\" data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\" data-show-refresh=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\" data-search=\"true\">" +"\n";
				dados_tabela=dados_tabela + "<thead>"+"\n";
				dados_tabela=dados_tabela +"<tr>"+"\n";
				dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
				dados_tabela=dados_tabela +" <th data-field=\"item\" data-filter-control=\"select\">PO</th>"+"\n";
				dados_tabela=dados_tabela +" <th data-field=\"item1\" data-filter-control=\"select\">Item</th>"+"\n";
				dados_tabela=dados_tabela +" <th data-field=\"item2\">Cliente Cod Item</th>"+"\n";
				dados_tabela=dados_tabela +" <th style data-field=\"item3\">Item Desc</th>"+"\n";
				dados_tabela=dados_tabela +" <th style data-field=\"item4\">Quantidade</th>"+"\n";
				dados_tabela=dados_tabela +" <th style data-field=\"item5\">Decrição Detalhada</th>"+"\n";
				dados_tabela=dados_tabela +" <th style data-field=\"item6\">Site</th>"+"\n";
				dados_tabela=dados_tabela +" <th style data-field=\"item7\" data-filter-control=\"select\">Valor</th>"+"\n";
				
				dados_tabela=dados_tabela +"</tr>"+"\n";
				dados_tabela=dados_tabela +"</thead>"+"\n";
				dados_tabela=dados_tabela +"<tbody>"+"\n";
				if(rs.next()){
					rs.beforeFirst();
					if(p.get_PessoaPerfil_nome().contains("Financeiro")){
						while(rs.next()){
							money = Double.parseDouble(rs.getString(7));
							moneyString = number_formatter.format(money);
							dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(2)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(4)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(3)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(5)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(6)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(12)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td><input type='text' style='color:black' id='item_po_site_"+rs.getInt(1)+"' value='"+rs.getString("po_item_site")+"'></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+moneyString+"</td>"+"\n";
							dados_tabela=dados_tabela + "</tr>"+"\n";
						}
					}else{
						while(rs.next()){
							dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(2)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(4)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(3)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(5)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(6)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(12)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td><input type='text' id='"+rs.getInt(1)+"' value='"+rs.getString("po_item_site")+"'></td>"+"\n";
							dados_tabela=dados_tabela + " <td> - </td>"+"\n";
							dados_tabela=dados_tabela + "</tr>"+"\n";
						}
					}
					
					//System.out.println("Resposta Consulta 3 Enviada!");
				}
				dados_tabela=dados_tabela + "</tbody>";
				dados_tabela=dados_tabela + "</table>";
				//System.out.println(dados_tabela);
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				out.close();
			}else if(opt.equals("4")){
				//System.out.println("Validando PO's");
				 time = new Timestamp(System.currentTimeMillis());
				param1=req.getParameter("po");
				//System.out.println(param1);
				if(param1.indexOf("'")>0){
					param1.replace("'","");
				}
				if(param1.indexOf(",")>0){
					String[] po=param1.split(",");
					for(int i=0;i<po.length;i++){
						
						mysql.Alterar("update po_table set validada='Y',dt_validada='"+time+"' where po_number='"+po[i]+"'");
					}
				}else{
					
					mysql.Alterar("update po_table set validada='Y',dt_validada='"+time+"' where po_number='"+param1+"'");
				}
			}else if(opt.equals("5")){
				
                //System.out.println("Carregando PO de Subcon");
				rs=mysql.Consulta("Select * from po_table_subcon order by po_id desc");
	    		
					if(rs.next()){
						
						dados_tabela="<table id=\"tabela_de_po_subcon\" data-show-refresh=\"true\" data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\" data-toolbar=\"#toolbar_subcon\" data-search=\"true\">" +"\n";
						dados_tabela=dados_tabela + "<thead>"+"\n";
						dados_tabela=dados_tabela +"<tr>"+"\n";
						dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"po\" data-filter-control=\"select\">PO</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"po1\" data-filter-control=\"select\">Emissão</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"po2\">Validada</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"po3\">Data Validação</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"po4\">Emissor</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"po5\" data-filter-control=\"select\">Data Carregada</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"po6\">Localidade</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"po7\">Nome do Arquivo</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"po8\">Valor Total</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"po9\">Arquivo PO</th>"+"\n";
						dados_tabela=dados_tabela +"</tr>"+"\n";
						dados_tabela=dados_tabela +"</thead>"+"\n";
						dados_tabela=dados_tabela +"<tbody>"+"\n";
						rs.beforeFirst();
						while(rs.next()){
							dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(2)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(3)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(4)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(5)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(6)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(15)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(12)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(17)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(19)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>em construçao</td>"+"\n";
							dados_tabela=dados_tabela + "</tr>"+"\n";
						}
						dados_tabela=dados_tabela + "</tbody>";
						dados_tabela=dados_tabela + "</table>";
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
						//System.out.println("Resposta Consulta 3 Enviada!");
					}else{
						//System.out.println("Consulta returns empty");
					}
				}else if(opt.equals("6")){
					
	                //System.out.println("Carregando tabela de Projetos");
					rs=mysql.Consulta("Select * from project_table where empresa="+p.getEmpresa().getEmpresa_id()+" order by project_id desc");
					dados_tabela="<table id=\"tabela_de_projeto\" data-show-refresh=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\" data-toolbar=\"#toolbar_projetos\" data-search=\"true\" data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\">" +"\n";
					dados_tabela=dados_tabela + "<thead>"+"\n";
					dados_tabela=dados_tabela +"<tr>"+"\n";
					dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"prj\" data-filter-control=\"select\">Projeto Id</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"prj1\" data-filter-control=\"select\">Nome</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"prj2\">Projeto Status</th>"+"\n";
					dados_tabela=dados_tabela +" <th style data-field=\"prj3\">Conta</th>"+"\n";
					dados_tabela=dados_tabela +" <th style data-field=\"prj4\">Código Projeto - Cliente</th>"+"\n";
					dados_tabela=dados_tabela +" <th style data-field=\"prj5\" data-filter-control=\"select\">Nome Projeto - CLiente</th>"+"\n";
					dados_tabela=dados_tabela +" <th style data-field=\"prj6\">Inicio</th>"+"\n";
					dados_tabela=dados_tabela +" <th style data-field=\"prj7\">Fim</th>"+"\n";
					dados_tabela=dados_tabela +" <th style data-field=\"prj8\">Orçamento</th>"+"\n";
					dados_tabela=dados_tabela +" <th style data-field=\"prj9\">PO's Recebida</th>"+"\n";
					dados_tabela=dados_tabela +"</tr>"+"\n";
					dados_tabela=dados_tabela +"</thead>"+"\n";
					dados_tabela=dados_tabela +"<tbody>"+"\n";
						if(rs.next()){
							rs.beforeFirst();
							while(rs.next()){
								dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
								dados_tabela=dados_tabela + " <td></td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(1)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(2)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(8)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(17)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(10)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(11)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(3)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(4)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(9)+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString(14)+"</td>"+"\n";
								dados_tabela=dados_tabela + "</tr>"+"\n";
							}
							
							//System.out.println("Resposta Consulta 3 Enviada!");
						}
						dados_tabela=dados_tabela + "</tbody>";
						dados_tabela=dados_tabela + "</table>";
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("7")){
						
		                //System.out.println("Cadastrando Projeto...");
		                param1=req.getParameter("projeto");
		    			param2=req.getParameter("cliente");
		    			param3=req.getParameter("codigo");
		    			param4=req.getParameter("estado");
		    			param5=req.getParameter("pmcliente");
		    			param6=req.getParameter("mailcliente");
		    			param7=req.getParameter("orcamento");
		    			param8=req.getParameter("dtinicio");
		    			param9=req.getParameter("dtfim");
		    			 time = new Timestamp(System.currentTimeMillis());
		    			
		    			insere="";
		    			
		    			insere="INSERT INTO project_table (project_name,project_pm,project_begin_date,project_end_date,project_creator,project_created_date,project_status,project_customer_budget,project_customer_code,project_customer_name,project_customer_pm,customer_id,customer_name,empresa) "
		    					                + "VALUES ('"+param1+"','"+param5+"','"+param8.substring(0, 10)+"','"+param9.substring(0, 10)+"','"+session.getAttribute("user")+"','"+time+"','Enviado','"+param7+"','"+param3+"','"+param1+"','"+param5+"','"+param2+"','"+param2+"',"+p.getEmpresa().getEmpresa_id()+")";
		    			if(mysql.Inserir_simples(insere)){
				    		//System.out.println("Projeto Cadastrado");
				    		rs=mysql.Consulta("Select project_id from project_table order by project_id desc limit 1");
				    		if(rs.next()){
				    			last_id=rs.getInt(1);
				    		}
		    			}
		    			//System.out.println(param1+"\n"+param2+"\n"+param3+"\n"+param4+"\n"+param5+"\n"+param6+"\n"+param7+"\n"+param8+"\n"+param9);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(last_id);
						//System.out.println("Resposta Consulta 3 Enviada!");
						}else if(opt.equals("8")){
							
			                //System.out.println("Carregando tabela de Clientes");
							rs=mysql.Consulta("Select * from customer_table where empresa="+p.getEmpresa().getEmpresa_id()+" order by customer_id desc");
							dados_tabela="<table id=\"tabela_de_clientes\" data-toggle=\"table\"  data-filter-control=\"true\"  data-pagination=\"true\" data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\" data-toolbar=\"#toolbar_cliente\" data-search=\"true\">" +"\n";
							dados_tabela=dados_tabela + "<thead>"+"\n";
							dados_tabela=dados_tabela +"<tr>"+"\n";
							dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"prj\" data-filter-control=\"select\">Ciente Id</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"prj1\" data-filter-control=\"select\">Nome</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"prj2\">Nome Completo</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"prj3\">Estado</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"prj4\">CNPJ</th>"+"\n";
							
							dados_tabela=dados_tabela +"</tr>"+"\n";
							dados_tabela=dados_tabela +"</thead>"+"\n";
							dados_tabela=dados_tabela +"<tbody>"+"\n";
								if(rs.next()){
									rs.beforeFirst();
									while(rs.next()){
										dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
										dados_tabela=dados_tabela + " <td></td>"+"\n";
										dados_tabela=dados_tabela + " <td>"+rs.getString(1)+"</td>"+"\n";
										dados_tabela=dados_tabela + " <td>"+rs.getString(2)+"</td>"+"\n";
										dados_tabela=dados_tabela + " <td>"+rs.getString(3)+"</td>"+"\n";
										dados_tabela=dados_tabela + " <td>"+rs.getString(9)+"</td>"+"\n";
										dados_tabela=dados_tabela + " <td>"+rs.getString(4)+"</td>"+"\n";
										
										dados_tabela=dados_tabela + "</tr>"+"\n";
									}
									
									//System.out.println("Resposta Consulta 3 Enviada!");
								}
								dados_tabela=dados_tabela + "</tbody>";
								dados_tabela=dados_tabela + "</table>";
								//System.out.println(dados_tabela);
								resp.setContentType("application/html");  
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
								out.close();
						}else if(opt.equals("9")){
							//System.out.println("Cadastrando Cliente...");
			                param1=req.getParameter("nomeCompleto");
			    			param2=req.getParameter("nome");
			    			param3=req.getParameter("cnpj");
			    			param4=req.getParameter("inscEstadual");
			    			param5=req.getParameter("endereco");
			    			param6=req.getParameter("contato");
			    			
			    			 time = new Timestamp(System.currentTimeMillis());
			    			
			    			insere="";
			    			
			    			insere="INSERT INTO customer_table (customer_name,customer_fullname,customer_cnpj,customer_adress,customer_ie,customer_owner_creator,customer_created_date,empresa) "
			    					                + "VALUES ('"+param2+"','"+param1+"','"+param3+"','"+param5+"','"+param4+"','"+p.get_PessoaUsuario()+"','"+time+"',"+p.getEmpresa().getEmpresa_id()+")";
			    			if(mysql.Inserir_simples(insere)){
					    		//System.out.println("Cliente Cadastrado");
					    		rs=mysql.Consulta("Select customer_id from customer_table order by customer_id desc limit 1");
					    		if(rs.next()){
					    			last_id=rs.getInt(1);
					    		}
			    			}
			    			//System.out.println(param1+"\n"+param2+"\n"+param3+"\n"+param4+"\n"+param5+"\n"+param6+"\n"+param7+"\n"+param8+"\n"+param9);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(last_id);
							out.close();
						}else if(opt.equals("10")){
						param1=req.getParameter("cliente");	
						rs= mysql.Consulta("select * from project_table where customer_id='"+param1+"' and empresa="+p.getEmpresa().getEmpresa_id());
		    			dados_tabela="";
		    			if(rs.next()){
		    				rs.beforeFirst();
		    				dados_tabela=dados_tabela+"<option value='0' >- - - - - - - - </option>\n";
		    				while(rs.next()){
		    				dados_tabela=dados_tabela+"<option value='"+rs.getString("project_id")+"' >"+rs.getString("project_name")+"</option>\n";
		    				}
		    			}else{
		    				dados_tabela=dados_tabela+"<option value='' >NENHUM PROJETO ENCONTRADO</option>\n";
		    			}
		    			//System.out.println(dados_tabela);
		    			resp.setContentType("application/html");  
			    		resp.setCharacterEncoding("UTF-8"); 
			    		PrintWriter out = resp.getWriter();
			    		out.print(dados_tabela);
			    		}else if(opt.equals("11")){
							
							rs= mysql.Consulta("select * from customer_table where empresa="+p.getEmpresa().getEmpresa_id());
		    			dados_tabela="";
		    			if(rs.next()){
		    				rs.beforeFirst();
		    				dados_tabela=dados_tabela+"<option value='0' >- - - - - - - - </option>\n";
		    				while(rs.next()){
		    				dados_tabela=dados_tabela+"<option value='"+rs.getString("customer_id")+"' >"+rs.getString("customer_name")+"</option>\n";
		    				}
		    			}else{
		    				dados_tabela=dados_tabela+"<option value='' >NENHUM CLIENTE ENCONTRADO</option>\n";
		    			}
		    			//System.out.println(dados_tabela);
		    			resp.setContentType("application/html");  
			    		resp.setCharacterEncoding("UTF-8"); 
			    		PrintWriter out = resp.getWriter();
			    		out.print(dados_tabela);
			    		out.close();
			    		}else if(opt.equals("12")){
							try {
							String cliente="";
							String projeto="";
							String po_original="N";
							ClienteEmpresa clienteObj = new ClienteEmpresa();
							ProjetoCliente projetoObj = new ProjetoCliente();
							ServletContext context = req.getSession().getServletContext();
							InputStream inputStream=null;
							if (ServletFileUpload.isMultipartContent(req)) {
								List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
								//System.out.println("Multipart size: " + multiparts.size());
								Iterator<FileItem> iter = multiparts.iterator();
								
								while (iter.hasNext()) {
									FileItem item = iter.next();
									
									if (item.isFormField()) {
										if(item.getFieldName().equals("cliente")){
											cliente=item.getString();
											clienteObj.setClienteEmpresaById(mysql, Integer.parseInt(cliente));
												
											
										}else if(item.getFieldName().equals("projeto")){
											projeto=item.getString();
											projetoObj.setProjetoClienteById(mysql, Integer.parseInt(projeto));
										}else if(item.getFieldName().equals("po_orinal")) {
											po_original="Y";
										}
								    }
									}
								
								iter = multiparts.iterator();
								
								while (iter.hasNext()) {
									FileItem item = iter.next();
									
									if (item.isFormField()) {
										
								    } else {
								       // processUploadedFile(item);
								    	 inputStream = item.getInputStream();
								    	 String sql = "Insert into po_table_file (dt_upload,arquivo,nome_arquivo,tipo,tamanho,carregada_por,id_cliente,id_projeto,empresa) values ('"+time+"' , ? ,'"+item.getName()+"','"+item.getContentType()+"','"+item.getSize()+"','"+p.get_PessoaUsuario()+"','"+cliente+"','"+projeto+"',"+p.getEmpresa().getEmpresa_id()+")";
									     PreparedStatement statement;
										 statement = mysql.getConnection().prepareStatement(sql);
										 statement.setBlob(1, inputStream);
										 int row = statement.executeUpdate();
									     statement.getConnection().commit();
									     if (row>0) {
									        	rs=mysql.Consulta("select file_po_id from po_table_file where empresa="+p.getEmpresa().getEmpresa_id() +" and carregada_por='"+p.get_PessoaUsuario()+"' order by file_po_id desc limit 1");
									        	int id_arquivo=0;
									        	if(rs.next()) {
									        		id_arquivo=rs.getInt(1);
									        	}
									        	statement.close();
									        	
									        	if(p.getEmpresa().getEmpresa_id()==8 || p.getEmpresa().getEmpresa_id()==7 || p.getEmpresa().getEmpresa_id()==5 || p.getEmpresa().getEmpresa_id()==2) {
									        		// System.out.println("controle 1");
									        		 //ByteArrayInputStream initialStream = new ByteArrayInputStream(new byte[] { 0, 1, 2 });
									        	     
									        	//byte[] targetArray = IOUtils.toByteArray(item.getInputStream());
									        	//System.out.println("controle 1");
									    		PDDocument document = PDDocument.load(new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream())));
									    		int paginas=document.getNumberOfPages();
									    		String po_number="";
								    			 Integer po_numberNumero=0;
								    			 String valor="";
								    			 String dt_issuePO="";
								    			 Double valordouble=0.0;
								    			
								                 String po_number_string="";
								                 String site = "";
								                 Rectangle itemcod ;
								                 Rectangle PO ;
								                 Rectangle siteid ;
								                 Rectangle valorpo ;
								                 Rectangle POdt ;
								                 Rectangle itemlinha ;
								                 Rectangle itemarea;
								                 Rectangle quantidade ;
								                 Rectangle preco;
									    		if(paginas>0) {
									    			  Statement stmt = mysql.getConnection().createStatement();
										              mysql.getConnection().setAutoCommit(false);
									    			for(int indice=0;indice<paginas;indice++) {
									    			PDPage docPage = (PDPage) document.getPage(indice);
									    			
									    		 if (!document.isEncrypted()) {
									    			// System.out.println("Trabalhando pagina "+ (indice+1)+" de "+ paginas);
									    			 PDFTextStripperByArea stripper = new PDFTextStripperByArea();
									    			 if(indice==0) {
									    			 PDFTextStripper tStripper = new PDFTextStripper();
									                 String pdfFileInText = tStripper.getText(document);
									                 String lines[] = pdfFileInText.split("\\r?\\n");
									                 for (String linha : lines) {
									                     if(linha.contains("Total Value:")) {
									                    	 valor=linha;
									                    	 break;
									                     }
									                 }
									    			
									                
									                 stripper.setSortByPosition(true);
									                
									                 POdt= new Rectangle(168, 96, 65, 12);
									                 PO= new Rectangle(167, 85, 110, 12);
									                 //valorpo= new Rectangle(428, 172, 83, 12);
									                 siteid=new Rectangle(167, 109, 120, 10);
									                
									                 stripper.addRegion("PO", PO);
									                 stripper.addRegion("POdt", POdt);
									                 //stripper.addRegion("valorpo", valorpo);
									                 stripper.addRegion("siteid", siteid);
									              
									                 stripper.extractRegions(docPage);
									                 //System.out.println("controle 4");
									                 po_number = stripper.getTextForRegion("PO").replaceAll("\\n", "").replaceAll("\\r", "").trim();
									                // System.out.println(po_number);
									                 po_number_string="string";
							                    	 if(StringUtils.isNumeric(po_number)) {
							                    		 po_number_string="numero";
							                    		 po_numberNumero=Integer.parseInt(po_number);
							                    	 }else {
							                    		 po_number_string="string";
							                    	 }
									                 dt_issuePO = stripper.getTextForRegion("POdt").replaceAll("\\n", "").replaceAll("\\r", "").trim();
									                // System.out.println(dt_issuePO);
									                 if(dt_issuePO.length()==9) {
								                    		if(dt_issuePO.substring(2, 5).equals("May")) {
								                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-05-"+dt_issuePO.substring(5, dt_issuePO.length());
								                    		}else if(dt_issuePO.substring(2, 5).equals("Jun")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-06-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Jul")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-07-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Aug")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-08-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Sep")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-09-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Oct")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-10-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Nov")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-11-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Dec")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-12-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Jan")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-01-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Feb")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-02-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Mar")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-03-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}else if(dt_issuePO.substring(2, 5).equals("Apr")) {
									                    		dt_issuePO=dt_issuePO.substring(0, 2)+"-04-"+dt_issuePO.substring(5, dt_issuePO.length());
									                    	}
								                    	}
									                 DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							             			 Date d1=format.parse(dt_issuePO);
							             			 dt_issuePO=f2.format(d1);
							             			// System.out.println(dt_issuePO);
									                 //valor= stripper.getTextForRegion("valorpo").replaceAll("\\n", "").replaceAll("\\r", "").trim();
									                 valor=valor.substring(valor.indexOf("Total Value:")+13,valor.indexOf("BRL")).trim();
							                    
							                    	 valor=valor.replace(".", "");
							                    	 valor=valor.replace(",", ".");
							                    
							                    	 valordouble=Double.parseDouble(valor);
							                    	 //System.out.println(valordouble);
									                 site= stripper.getTextForRegion("siteid").replaceAll("\\n", "").replaceAll("\\r", "").trim();
									                 //System.out.println("Site sem parse: "+site);
									                 site=site.trim().substring(0,site.indexOf(',')).trim();
									                 //site=site.substring(0,site.indexOf(" "));
									                // System.out.println("Site com parse: "+site);
									    			 }
									                 Rectangle line ;
									                 Rectangle segunda ;
									    			 
									                 int limite=0;
									                 
									                	 
									                 
									                 String auxLine;
									                if(indice==0) {
									                limite=110;
									                 while(limite<812) {
									                	 line= new Rectangle(35, limite, 55, 8);
									                	 stripper.addRegion("linha", line);
									                	 stripper.extractRegions(docPage);
									                	 auxLine=stripper.getTextForRegion("linha");
									                	 if(auxLine.trim().equals("Line Status")) {
									                		// System.out.println("achou a linha");
									                		 break;
									                	 }else {
									                		 limite=limite+8;
									                		 stripper.removeRegion("linha");
									                	 }
									                 }
									                }
									                if(indice==0) {
									                 limite=limite+36;
									                }else {
									                	limite=9;
									                }
									                 int segunda_linha=0;
									                 
									               
									                 String item_cod;
									                 String item_linha;
									                 String itemString;
									                 String quantidadeStr;
									                 String precoStr;
									                 String insere_item="";
									                 String achoualgo="";
									               
									                 while(limite < 812) {
									                	 stripper = new PDFTextStripperByArea();
									                	 stripper.setSortByPosition(true);
									                	 
									                	 itemlinha= new Rectangle(98, limite, 47, 12);
									                	 stripper.addRegion("linhaItem", itemlinha);
									                	 stripper.extractRegions(docPage);
									                	 item_linha= stripper.getTextForRegion("linhaItem").replaceAll("\\n", "").replaceAll("\\r", "").trim();
									                	 if(item_linha.trim().length()>0) {
									                	 if(StringUtils.isNumeric(item_linha)) {
									                		 if(Integer.parseInt(item_linha.trim().toString())>0) {
									                			 segunda_linha=limite+12;
									                			 itemcod= new Rectangle(149, limite, 60, 12);
											                	 itemarea= new Rectangle(242, limite, 128, 12);
											                	 achoualgo="";
											                	 while(achoualgo.trim().equals("") && segunda_linha<812) {
											                		 segunda= new Rectangle(98, segunda_linha, 47, 12);
											                		 stripper.addRegion("segunda", segunda);
											                		 stripper.extractRegions(docPage);
											                		 achoualgo=stripper.getTextForRegion("segunda").trim();
											                		 //System.out.println("Achou:"+achoualgo);
											                		 segunda_linha=segunda_linha+1;
											                		 stripper.removeRegion("segunda");
											                	 }
											                	 segunda_linha=segunda_linha-1;
											                	 quantidade= new Rectangle(152, segunda_linha, 50, 12);
											                	 preco= new Rectangle(262, segunda_linha, 50, 12);
									                			 stripper.addRegion("codigoItem", itemcod);
											                	 stripper.addRegion("Item", itemarea);
											                	 stripper.addRegion("Quantidade", quantidade);
											                	 stripper.addRegion("Preco", preco);
											                	 stripper.extractRegions(docPage);
									                			 item_cod= stripper.getTextForRegion("codigoItem").replaceAll("\\n", "").replaceAll("\\r", "").trim();
											                	 itemString= stripper.getTextForRegion("Item").replaceAll("\\n", "").replaceAll("\\r", "").trim();
											                	 quantidadeStr= stripper.getTextForRegion("Quantidade").replaceAll("\\n", "").replaceAll("\\r", "").trim();
											                	 precoStr= stripper.getTextForRegion("Preco").replaceAll("\\n", "").replaceAll("\\r", "").trim();
									                			 if(quantidadeStr.trim().equals("") && precoStr.trim().equals("") && limite>=790) {
									                				 docPage = (PDPage) document.getPage(indice+1);
									                				 stripper = new PDFTextStripperByArea();
												                	 stripper.setSortByPosition(true);
												                	 
												                	 segunda_linha=8;
												                	 
												                	 achoualgo="";
												                	 while(achoualgo.trim().equals("") && segunda_linha<812) {
												                		 segunda= new Rectangle(98, segunda_linha, 47, 12);
												                		 stripper.addRegion("segunda", segunda);
												                		 stripper.extractRegions(docPage);
												                		 achoualgo=stripper.getTextForRegion("segunda").trim();
												                		 //System.out.println("Achou:"+achoualgo);
												                		 segunda_linha=segunda_linha+1;
												                		 stripper.removeRegion("segunda");
												                	 }
												                	 segunda_linha=segunda_linha-1;
												                	 quantidade= new Rectangle(152, segunda_linha, 50, 12);
												                	 preco= new Rectangle(262, segunda_linha, 50, 12);
										                			 stripper.addRegion("codigoItem", itemcod);
												                	 stripper.addRegion("Item", itemarea);
												                	 stripper.addRegion("Quantidade", quantidade);
												                	 stripper.addRegion("Preco", preco);
												                	 stripper.extractRegions(docPage);
										                			 item_cod= stripper.getTextForRegion("codigoItem").replaceAll("\\n", "").replaceAll("\\r", "").trim();
												                	 itemString= stripper.getTextForRegion("Item").replaceAll("\\n", "").replaceAll("\\r", "").trim();
												                	 quantidadeStr= stripper.getTextForRegion("Quantidade").replaceAll("\\n", "").replaceAll("\\r", "").trim();
												                	 precoStr= stripper.getTextForRegion("Preco").replaceAll("\\n", "").replaceAll("\\r", "").trim();
									                			 }
									                			 docPage = (PDPage) document.getPage(indice);
											                	 
											                	 //System.out.println("Linha:"+item_linha);
									                			 insere_item="insert into po_item_table(po_number,po_item_cod,po_item_name,po_item_desc,empresa,po_item_qtd,po_item_valor,po_item_site) values('"+po_numberNumero+"','"+item_cod+"','"+itemString+"','"+itemString+"',"+p.getEmpresa().getEmpresa_id()+","+quantidadeStr+",'"+precoStr.replace(".", "").replaceAll(",", ".")+"','"+site+"')";
									                			// System.out.println("query da tabela de itens:"+insere_item);
									                			 stmt.addBatch(insere_item);
										                 	}
									                	 }
									                 }
									                	 
									                	 limite=limite+12;
									                	
									                 }//fim do while
									                 
									                 
									                  
									                 
									                 
									    		 }//fim document encrypted
									    		 
									     	}//for de paginas
									    			if(po_number_string.equals("numero")) {
									                	 query="insert into po_table (po_number,empresa,arquivo_id,id_po_file,dt_emitida,total_po,dt_registrada,empresa_emissora,validada,PO_ATIVA,registrada_por) values ('"+po_numberNumero+"',"+p.getEmpresa().getEmpresa_id()+","+id_arquivo+","+id_arquivo+",'"+dt_issuePO+"','"+valordouble+"','"+time+"','"+cliente+"','Sem Validação','Y','"+p.get_PessoaUsuario()+"')";
									                 }else {
									                	 query="insert into po_table (po_number,empresa,arquivo_id,id_po_file,dt_emitida,total_po,dt_registrada,empresa_emissora,validada,PO_ATIVA,registrada_por) values ('"+po_number+"',"+p.getEmpresa().getEmpresa_id()+","+id_arquivo+","+id_arquivo+",'"+dt_issuePO+"','"+valordouble+"','"+time+"','"+cliente+"','Sem Validação','Y','"+p.get_PessoaUsuario()+"')";
									                 }
									                 //System.out.println("query da tabela de PO:"+query);
									                 rs=mysql.Consulta("select * from po_table where po_number='"+po_numberNumero+"' or po_number='"+po_number+"'");
									                 if(rs.next()) {
									                	 System.out.println("INSERÇÃO DE PO ABORTADA. MOTIVO: PO DUPLICADA");
									                 }else {
									                	 mysql.Inserir_simples(query);
									                	 stmt.executeBatch();
									                	 mysql.getConnection().commit();
									                	 stmt.close();
									                 }
									                  resp.setContentType("application/json"); 
											        	JSONObject jsonObject = new JSONObject(); 
											        	jsonObject.put("Sucesso", "Sucesso"); 
											        	PrintWriter pw = resp.getWriter();
											        	pw.print(jsonObject); 
											        	pw.close();
									                 Semail email= new Semail();
												     rs=mysql.Consulta("select distinct usuarios.nome,usuarios.email from usuarios,perfil_funcoes where perfil_funcoes.funcao_nome='POManager'  and perfil_funcoes.empresa_id="+p.getEmpresa().getEmpresa_id()+" and perfil_funcoes.usuario_id=usuarios.id_usuario");
									    			 if(rs.next()) {
									    				 rs.beforeFirst();
									    				 while(rs.next()) {
									    					 email.enviaEmailSimples(rs.getString("email"), "MSTP - Notificação de Inserção de PO","Prezado "+rs.getString("nome")+", \n \n Informamos que uma nova PO foi carregada no sistema MSTP. \n\nEmpresa Emissora:"+clienteObj.getNome()+". \nPO Número:"+po_numberNumero+" \nProjeto:"+projetoObj.getNome()+" \n\nMensagem Automática.Favor não responder!\n  Operação realizada em: "+time);
									    				 }
									    			 }
									    		}//if numero de paginas maior q zero
									    		document.close();
									     
									    
						    				
									        	}else if(p.getEmpresa().getEmpresa_id()==1 ) {// if de empresas
									        			Bson filtro;
									        			List<Bson> filtros = new ArrayList<>();
									        		    inputStream= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
									        		    String po_numberNumero=item.getName();
													    DataFormatter dataFormatter = new DataFormatter();
													    int indexCell=0;
											    		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
											    		//System.out.println("chegou aqui 2");
											    		Sheet sheet1 = wb.getSheetAt(0);
											    		Cell cell;
											    		String cellValue;
											    		Iterator<Row> rowIterator = sheet1.iterator();
											    		Row row_aux = rowIterator.next();
											    		//System.out.println("numero de colunas:"+row_aux.getLastCellNum());
											    		String []campos = new String[row_aux.getLastCellNum()];
											    		for(int i=0;i<row_aux.getLastCellNum();i++) {
											    			cell = row_aux.getCell(i);
										    				cellValue = dataFormatter.formatCellValue(cell);
										    				cellValue=cellValue.replace("'","");
										    				cellValue=cellValue.replace(".","");
										    				cellValue=cellValue.replace("\"","");
										    				cellValue=cellValue.replace("\\\"","_");
										    				cellValue=cellValue.replace("\n","|");
											    			campos[i]=cellValue;
											    		}
											    		//System.out.println("chegou aqui 3");
											    		Calendar d = Calendar.getInstance();
											    		String po_inserida="Y";
											    		while(rowIterator.hasNext()) {
											    			po_inserida="Y";
											    			filtros = new ArrayList<>();
											    			row_aux = rowIterator.next();
											    			Document linhaPO = new Document();
											    			linhaPO.append("Empresa",p.getEmpresa().getEmpresa_id());
											    			linhaPO.append("PO_ATIVA","Y");
											    			linhaPO.append("PO_VALIDADA","N");
											    			linhaPO.append("DT_CARREGADA",d.getTime());
											    			linhaPO.append("USER_CARREGADA",p.get_PessoaUsuario());
											    			linhaPO.append("CLIENTE",clienteObj.getNome());
											    			linhaPO.append("PROJETO",projetoObj.getNome());
											    			linhaPO.append("ARQUIVO_PO_ORIGINAL",po_original);
											    			for(int i=0;i<row_aux.getLastCellNum();i++) {
											    				
											    				cell = row_aux.getCell(i);
											    				cellValue = dataFormatter.formatCellValue(cell);
											    				cellValue=cellValue.replace("'","");
											    				cellValue=cellValue.replace(".","");
											    				cellValue=cellValue.replace("\"","");
											    				cellValue=cellValue.replace("\\\"","_");
											    				cellValue=cellValue.replace("\n","|");
											    				linhaPO.append(campos[i], cellValue);
											    			}
											    			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
											    			filtros.add(filtro);
											    			filtro=Filters.eq("PO NO",linhaPO.getString("PO NO"));
											    			filtros.add(filtro);
											    			filtro=Filters.eq("PO Line NO",linhaPO.getString("PO Line NO"));
											    			filtros.add(filtro);
											    			filtro=Filters.eq("Item Code",linhaPO.getString("Item Code"));
											    			filtros.add(filtro);
											    			filtro=Filters.eq("Publish Date",linhaPO.getString("Publish Date"));
											    			filtros.add(filtro);
											    			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("PO", filtros);
											    			String recurso="";
											    			if(!findIterable.iterator().hasNext()) {
											    				mongo.InserirSimples("PO", linhaPO);
											    				po_inserida="Y";
											    				JSONObject params = new JSONObject();
											    				Long ticket=time.getTime();
																params.append("po_num",linhaPO.getString("PO NO"));
																params.append("ticket",ticket.toString());
																params.append("item",req.getParameter("item"));
																params.append("item_code",linhaPO.getString("Item Code"));
																params.append("po_line",linhaPO.getString("PO Line NO"));
																params.append("published",linhaPO.getString("Publish Date"));
																params.append("site",linhaPO.getString("Site Name"));
																params.append("projeto",linhaPO.getString("Project Name"));
																params.append("tags",linhaPO.getString("Project Name"));
																if(linhaPO.getString("Project Name").equals("Brazil TIM Lithium Battery Project")) {
																	recurso = "luciano_spazio";
																}else if(linhaPO.getString("Project Name").equals("Brazil TIM Mobile Access - G&U 2014~2020")) {
																	recurso = "peterson_spazio";
																}else if(linhaPO.getString("Project Name").equals("Brazil TIM MW 2018 3 years")) {
																	recurso = "peterson_spazio";
																}else if(linhaPO.getString("Project Name").equals("Brazil VIVO GUL 2020-2021")) {
																	recurso = "peterson_spazio";
																}else {
																	recurso = "324_FERNANDO";
																}
																Calendar plano = Calendar.getInstance();
																params.append("recurso",recurso);
																params.append("incio_planejado_bl",f3.format(plano));
																plano.add(Calendar.DAY_OF_MONTH, 3);
																params.append("fim_planejado_bl",f3.format(plano));
																params.append("todos_itens","Y");
																params.append("quantidade_itens",linhaPO.getString("Requested Qty"));
																params.append("quantidade_pos_restante","0");
																criaTicket(mysql,mongo,params,p);
											    			}else {
											    				po_inserida="N";
											    				System.out.println("___________________________");
											    				System.out.println("PO DUPLICADA NAO INSERIDA!" + time);
											    				System.out.println("___________________________");
											    			}
											    			filtros.clear();
											    		}
											    		
											    		
											    		//
											    		
											    		wb.close();
											    		resp.setContentType("application/json"); 
											        	JSONObject jsonObject = new JSONObject(); 
											        	jsonObject.put("Sucesso", "Sucesso"); 
											        	PrintWriter pw = resp.getWriter();
											        	pw.print(jsonObject); 
											        	pw.close();
											        	mongo.fecharConexao();
											        	if(po_inserida.equals("Y")) {
											        	 Semail email= new Semail();
											        	 rs=mysql.Consulta("select distinct usuarios.nome,usuarios.email from usuarios,perfil_funcoes where perfil_funcoes.funcao_nome='POManager'  and perfil_funcoes.empresa_id="+p.getEmpresa().getEmpresa_id()+" and perfil_funcoes.usuario_id=usuarios.id_usuario and usuarios.ativo='Y'");
										    			 if(rs.next()) {
										    				 rs.beforeFirst();
										    				 while(rs.next()) {
										    					 email.enviaEmailSimples(rs.getString("email"), "MSTP - Notificação de Inserção de PO","Prezado "+rs.getString("nome")+", \n \n Informamos que uma nova PO foi carregada no sistema MSTP. \n\nEmpresa Emissora:"+clienteObj.getNome()+". \nPO Número:"+po_numberNumero+" \nProjeto:"+projetoObj.getNome()+" \n\nMensagem Automática.Favor não responder!\n  Operação realizada em: "+time);
										    				 }
										    			 }
											        	}
									        	}
								    		}// if upload do arquivo ok
									    
									        
								    }//fim do else se for arquivo ou form field
									 
								}//while de itens no req paramenter
							}
							}catch(Exception erro) {
								System.out.println(erro.getCause());
								System.out.println(erro.getStackTrace());
							}
						}else if(opt.equals("13")){
							
							//System.out.println("buscando PO");
							param1=req.getParameter("id_arquivo");
							query="select arquivo from po_table_file where file_po_id="+param1;
							//System.out.println(query);
							rs=mysql.Consulta(query);
							ServletOutputStream out = resp.getOutputStream();
							InputStream in=null;
							if(rs.next()){
								//System.out.println("Entrou no Blob");
								Blob arquivo = rs.getBlob(1);
								if(arquivo==null) {
									
									
								}else {
									 in = arquivo.getBinaryStream(1,(int)arquivo.length());
								}
						        int tamanho = in.available();

						        int bufferSize = 1024;
						        byte[] buffer = new byte[bufferSize];
						        resp.setContentType("application/pdf");  
						        resp.setHeader("Pragma", "no-cache");
					            resp.setHeader("Cache-control", "private");
					            resp.setContentLength(tamanho);
					            resp.setHeader("Content-Disposition", "attachment; filename=\"APP - User Guide.pdf\"");
						        while ((tamanho = in.read(buffer)) != -1) {
						            //System.out.println("writing " + length + " bytes");
						            out.write(buffer, 0, tamanho);
						        }

						        
							    //System.out.println(dados_tabela);
						       
					            //resp.setContentLength(pdf.length);
						    	in.close();
						        out.flush();
						    	//out.print(dados_tabela);
						}
						}else if(opt.equals("14")){
						if(p.getPerfil_funcoes().contains("RolloutManager")) {
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" Inserção/Editando de Campos no rollout.");
		                param1=req.getParameter("nome");
		    			param2=req.getParameter("tipo");
		    			param3=req.getParameter("Desc");
		    			param4=req.getParameter("tipoatrb");
		    			param5=req.getParameter("lista");
		    			param6=req.getParameter("trigger");
		    			param7=req.getParameter("percent");
		    			param8=req.getParameter("rollout");
		    			param9=req.getParameter("id_campo");
		    			if(param9.trim().equals("")) {
		    				param9="-1";
		    			}
		    			int rolloutid=Integer.parseInt(param8.replace("Rollout", ""));
		    			int ordem=0;
		    			 time = new Timestamp(System.currentTimeMillis());
		    			
		    			insere="";
		    			//System.out.println("select * from rollout_campos where field_id="+param9+" and empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param8+"'");
		    			rs=mysql.Consulta("select * from rollout_campos where field_id="+param9+" and empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param8+"'");
		    			if(param2.equals("Milestone")){
		    				param4="Milestone";
		    			}
		    			if(rs.next()){
		    				//System.out.println("update rollout_campos set field_name='"+param1+"',field_type='"+param2+"',tipo='"+param4+"',Atributo_parametro='"+param5+"',trigger_pagamento="+param6+",percent_pagamento='"+param7+"' where field_id="+rs.getInt(1));
		    				Document rename=new Document();
		    				Document rename_comand=new Document();
		    				Document filtro = new Document();
		    				filtro.append("Empresa", p.getEmpresa().getEmpresa_id());
		    				filtro .append("rolloutId", param8);
		    				rename.append(rs.getString("field_name"), param1);
		    				rename_comand.append("$rename", rename);
		    				mongo.AtualizaMuitos("rollout", filtro, rename_comand);
		    				mysql.Alterar("update rollout_campos set field_name='"+param1+"',field_type='"+param2+"',tipo='"+param4+"',Atributo_parametro='"+param5+"',trigger_pagamento="+param6+",percent_pagamento='"+param7+"' where field_id="+rs.getInt(1));
		    				last_id=rs.getInt(1);
		    				
		    				retorno="Campo editado com Sucesso. ID do campo: "+last_id;
		    			}else{
		    			rs=mysql.Consulta("select ordenacao from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param8+"' order by ordenacao desc limit 1");	
		    			if(rs.next()) {
		    				ordem=rs.getInt(1);
		    				ordem=ordem+1;
		    				insere="INSERT INTO rollout_campos (field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,tipo,Atributo_parametro,trigger_pagamento,percent_pagamento,empresa,ordenacao,rollout_id,rollout_nome) "
					                + "VALUES ('"+param1+"','"+param2+"','"+session.getAttribute("user")+"','"+time+"','"+session.getAttribute("user")+"','ATIVO','"+param4+"','"+param5+"',"+param6+",'"+param7+"',"+p.getEmpresa().getEmpresa_id()+","+ordem+","+rolloutid+",'"+param8+"')";
		    			}else {
		    				ordem=1;
		    				insere="INSERT INTO rollout_campos (field_name,field_type,field_owner,field_creation_date,fiel_creation_owner,field_status,tipo,Atributo_parametro,trigger_pagamento,percent_pagamento,empresa,ordenacao,rollout_id,rollout_nome) "
					                + "VALUES ('"+param1+"','"+param2+"','"+session.getAttribute("user")+"','"+time+"','"+session.getAttribute("user")+"','ATIVO','"+param4+"','"+param5+"',"+param6+",'"+param7+"',"+p.getEmpresa().getEmpresa_id()+",1,"+rolloutid+",'"+param8+"')";
		    			}
		    			
		    			if(mysql.Inserir_simples(insere)){
				    		//System.out.println("CAMPO Cadastrado");
				    		rs=mysql.Consulta("Select field_id from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param8+"' order by field_id desc limit 1");
				    		if(rs.next()){
				    			last_id=rs.getInt(1);
				    			retorno="Campo inserido com Sucesso. ID do campo: "+last_id;
				    		}
				    		
		    			}
		    			}
		    			//System.out.println(param1+"\n"+param2+"\n"+param3+"\n"+param4+"\n"+param5+"\n"+param6+"\n"+param7+"\n"+param8+"\n"+param9);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(retorno);
						out.close();
						}
					}else if(opt.equals("15")){
						if(p.getPerfil_funcoes().contains("RolloutManager")) {
		                param1=req.getParameter("ordem");
		                param2=req.getParameter("rollout");
		                
		                JSONObject jObj = new JSONObject(param1); 
		    			JSONArray campos = jObj.getJSONArray("ordem"); 
		    			
		    			
		    			 time = new Timestamp(System.currentTimeMillis());
		    			int tamanho=(int) jObj.get("tamanho");
		    			int i=0;
		    			//System.out.println(jObj.get("ordem"));
		    			while(i<tamanho){
		    				query="update rollout_campos set ordenacao="+campos.getJSONObject(i).getInt("posicao")+" where field_id="+campos.getJSONObject(i).getInt("campoid")+" and empresa="+p.getEmpresa().getEmpresa_id() +" and rollout_nome='"+param2+"'";
		    				mysql.Alterar(query);
		    				//query="update rollout set ordenacao="+campos.getJSONObject(i).getInt("posicao")+" where milestone='"+campos.getJSONObject(i).getString("campo_nome")+"' and empresa="+p.getEmpresa().getEmpresa_id();
		    				//mysql.Alterar(query);
		    				i++;
		    			}
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Ordem de campos atualizada!");
						out.close();
						}
					}else if(opt.equals("16")){
						//System.out.println(p.get_PessoaUsuario()+" pode enviar mensagem:"+p.getPerfil_funcoes().contains("MessageSender"));
						//System.out.println(p.getPerfil_funcoes());
						if(p.getPerfil_funcoes().contains("MessageSender")) {
						param1=req.getParameter("mensagem");
						param2=req.getParameter("usuario");
						//System.out.println("Envio de Mensagem:"+param1);
						//System.out.println("Envio de Mensagem:"+param2);
						param1="De: "+p.get_PessoaName()+"\n\n"+param1;
						param1=param1+"\n\nData Hora de Envio:"+f2.format(time);
						 if(param2.indexOf(",")>0) {
							 String[]user_aux=param2.split(",");
							 for(int i=0;i<user_aux.length;i++) {
								 enviaMensagemPorUsuario(user_aux[i],param1);
							 }
						 }else {
							 enviaMensagemPorUsuario(param2,param1);
						 }
						}
					}else if(opt.equals("17")){
						//System.out.println("Deletando PO's");
						param1=req.getParameter("campos");
						param2=req.getParameter("rollout");
						//System.out.println(param1);
						Document condicao=new Document();
						Document update=new Document();
						Document comando_update=new Document();
						condicao.append("Empresa", p.getEmpresa().getEmpresa_id());
						condicao.append("rolloutId", param2);
						
						if(param1.indexOf("'")>0){
							param1.replace("'","");
						}
						if(param1.indexOf(",")>0){
							String[] campos=param1.split(",");
							for(int i=0;i<campos.length;i++){
								//System.out.println("delete from po_table where po_number="+po[i]);
								rs=mysql.Consulta("Select field_name from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								if(rs.next()) {
									update=new Document();
									update.append(rs.getString("field_name"), "");
									comando_update.append("$unset", update);
									mongo.AtualizaMuitos("rollout", condicao, comando_update);
								}
								mysql.Excluir("delete from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								
								
								if(rs.next()){
								mysql.Excluir("delete from rollout where milestone='"+rs.getString(1)+"' and empresa="+p.getEmpresa().getEmpresa_id());
								}
							}
						}else{
							//System.out.println("delete from po_table where po_number="+param1);
							rs=mysql.Consulta("Select field_name from rollout_campos where field_id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());
							mysql.Excluir("delete from rollout_campos where field_id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());
							if(rs.next()){
							mysql.Excluir("delete from rollout where milestone='"+rs.getString(1)+"' and empresa="+p.getEmpresa().getEmpresa_id());
							update=new Document();
							update.append(rs.getString("field_name"), "");
							comando_update.append("$unset", update);
							mongo.AtualizaMuitos("rollout", condicao, comando_update);
							}
						}
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Campos Removidos");
						out.close();
					}else if(opt.equals("18")){
						if(p.get_PessoaPerfil_nome().equals("tecnico")) {
							query="Select usuarios.* from usuarios where  usuarios.ativo='Y' and usuarios.id_usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
						}else {
							query="Select usuarios.* from usuarios where  usuarios.ativo='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
						}
						rs=mysql.Consulta(query);
						if(rs.next()){
							
							dados_tabela="<table id=\"tabela_usuario\" data-use-row-attr-func=\"true\" data-toolbar=\"#toolbar_tabela_usuario\" data-reorderable-rows=\"true\" data-show-refresh=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\" data-page-size=\"10\" data-search=\"true\">" +"\n";
							dados_tabela=dados_tabela + "<thead>"+"\n";
							dados_tabela=dados_tabela +"<tr>"+"\n";
							dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"user\" data-filter-control=\"select\">Usuario</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"user_name\" data-filter-control=\"select\">Nome</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"user_email\">Email</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"user_profile\">Perfil</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"user_tipo\">Tipo</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"user_valid\">Validado</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"user_lastlogin\">Ultimo Acesso</th>"+"\n";
							dados_tabela=dados_tabela +"</tr>"+"\n";
							dados_tabela=dados_tabela +"</thead>"+"\n";
							dados_tabela=dados_tabela +"<tbody>"+"\n";
							rs.beforeFirst();
							while(rs.next()){
								dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
								dados_tabela=dados_tabela + " <td></td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString("id_usuario")+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString("nome")+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString("email")+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString("perfil")+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString("tipo")+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString("validado")+"</td>"+"\n";
								dados_tabela=dados_tabela + " <td>"+rs.getString("ultimo_acesso")+"</td>"+"\n";
								dados_tabela=dados_tabela + "</tr>"+"\n";
							}
							dados_tabela=dados_tabela + "</tbody>";
							dados_tabela=dados_tabela + "</table>";
							
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
							out.close();
						}
					}else if(opt.equals("19")){
						System.out.println("Função Migrada para Servlet de usuário opt 26");
					}else if(opt.equals("20")){
		    			//System.out.println("Realizando Troca de Senha");
		    			String mensagem_retorno="";
						String senhaprovisoria="";
		    			param1=req.getParameter("atual");
		    			
		    			param2=req.getParameter("nova");
		    			param3=req.getParameter("confirma");
		    			MessageDigest md;
		    			 time = new Timestamp(System.currentTimeMillis());
		    			if(!param1.trim().equals("")){
		    				if(param2.equals(param3)){
				    			md = MessageDigest.getInstance( "SHA-256" );
				    			senhaprovisoria=param2+"1234";
			    				md.update( param1.getBytes());     
			    		        BigInteger hash = new BigInteger(1,md.digest() );     
			    		        String retornaSenha = hash.toString(16);
			    		        
			    		        rs= mysql.Consulta("select * from usuarios where VALIDADO='Y' and ATIVO='Y' and HASH='"+retornaSenha+"' and (id_usuario='"+session.getAttribute("user")+"' or email='"+session.getAttribute("user")+"')");
			    		        if(rs.next()){
			    		        	md.update( param2.getBytes());     
				    		        hash = new BigInteger(1,md.digest() );     
				    		        senhaprovisoria = hash.toString(16);
			    		        	if(mysql.Alterar("update usuarios set HASH='"+senhaprovisoria+"' where id_usuario='"+rs.getString("id_usuario")+"'")) {
			    		        		mensagem_retorno="Troca de senha realizada com sucesso!";
			    		        	}
			    		        	
			    		        }
			    		        
				    				Semail email= new Semail();
				    				email.enviaEmailSimples(rs.getString("email"), "MSTP - Notificação de troca de Senha.","Prezado "+rs.getString("nome")+", \n \n Informamos que houve alteração de senha na sua conta no sistema MSTP. \n \n  Operação realizada em: "+time+" \n \n Caso não tenha realizado essa operação solicitamos que entre em contato com o administrador do sistema para sua empresa! \n \n \n Esse é um email Automático gerado pelo sistema. Favor não responder!");
				    				
				    				
			    			}else{
			    				mensagem_retorno="Confirmação de Senha nao confere com nova Senha";
			    			}
		    			}else{
		    				mensagem_retorno="Senha Atual é obrigatório!";
		    			}
				    		resp.setContentType("application/html");  
				    		resp.setCharacterEncoding("UTF-8"); 
				    		PrintWriter out = resp.getWriter();
				    		out.print(mensagem_retorno);
				    		out.close();
				    }else if(opt.equals("21")){
		    			String charts="";
		    			//System.out.println("Carregando Portal...");
		    			query="select user_portal from portal_user where id_usuario='"+session.getAttribute("user")+"'";
		    			rs=mysql.Consulta(query);
		    			if(rs.next()){
		    				
		    				charts=rs.getString(1);
		    				
		    			}else{
		    				charts="{\"panel0\": {\"window0\":{\"collapsed\":false},\"window3\":{\"collapsed\":false}},\"panel1\": {\"window1\":{\"collapsed\":false},\"window2\":{\"collapsed\":false}},\"floating\":{},\"orientation\": \"horizontal\"}";
		    			}
		    			//System.out.println(charts);
		    			resp.setContentType("application/html");  
				    	resp.setCharacterEncoding("UTF-8"); 
				    	PrintWriter out = resp.getWriter();
				    	out.print(charts);
				    	out.close();
				    }else if(opt.equals("22")){
		    			
		    		//System.out.println("Salvando Portal...");
		    			param1=req.getParameter("portal");
		    			query="select user_portal from portal_user where id_usuario='"+session.getAttribute("user")+"'";
		    			rs=mysql.Consulta(query);
		    			if(rs.next()){
		    				query="update portal_user set user_portal='"+param1+"' where id_usuario='"+session.getAttribute("user")+"'";
			    			mysql.Alterar(query);
		    			}else{
		    				query="insert into portal_user (id_usuario,user_portal) values('"+session.getAttribute("user")+"','"+param1+"')";
		    				mysql.Inserir_simples(query);
		    			}
		    		}else if(opt.equals("23")){
		    			
		    			
		    			param1=req.getParameter("rolloutid");
		    			//System.out.println("Buscando campos do rollout ...");
		    			//param1=req.getParameter("portal");
		    			query="select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param1+"' order by ordenacao";
		    			rs=mysql.Consulta(query);
		    			dados_tabela="<table id=\"table_row_fields\" data-toggle=\"table\" data-use-row-attr-func=\"true\" >" +"\n";
		    			if(rs.next()){
		    				rs.beforeFirst();
		    				
		    				dados_tabela=dados_tabela +"<thead>"+"\n";
		    				dados_tabela=dados_tabela + "<tr>"+"\n";
		    				
		    					dados_tabela=dados_tabela + "<th class=\"label_janelas\"> Campo"+"\n";
		    					
		    					dados_tabela=dados_tabela + "</th>"+"\n";
		    					dados_tabela=dados_tabela + "<th class=\"label_janelas\"> Valor"+"\n";
		    					
		    					dados_tabela=dados_tabela + "</th>"+"\n";
		    				dados_tabela=dados_tabela + "</tr>"+"\n";
		    				dados_tabela=dados_tabela +"</thead>"+"\n";
		    				dados_tabela=dados_tabela +"<tbody>"+"\n";
		    				
		    				rs.beforeFirst();
		    				while(rs.next()){
		    					dados_tabela=dados_tabela + "<tr>"+"\n";
		    					dados_tabela=dados_tabela +"<td>"+rs.getString("field_name")+"</td>";
		    					if(rs.getString("tipo").equals("Data")){
		    						dados_tabela=dados_tabela + " <td><input type=\"data_row\" class=\"form-control row_rollout label_janelas\" name=\""+rs.getString(2)+"\" id=\""+rs.getString(2)+"\" tipo_info=\""+rs.getString("tipo")+"\" tipo_campo=\""+rs.getString("field_type")+"\"></td>"+"\n";	
								}else if(rs.getString("tipo").equals("Milestone")){
									dados_tabela=dados_tabela + "<td class=\"label_janelas\">Plano de Início:<input type=\"data_row\" class=\"form-control row_rollout label_janelas\" tipo_info=\""+rs.getString("tipo")+"\" tipo_campo=\""+rs.getString("field_type")+"\" name=\""+rs.getString(2)+"_inicio\" id=\""+rs.getString(2)+"_inicio\" >Plano de Fim:<input type=\"data_row\" class=\"form-control row_rollout label_janelas\" tipo_campo=\""+rs.getString("field_type")+"\" name=\""+rs.getString(2)+"_fim\" id=\""+rs.getString(2)+"_fim\" ></td>"+"\n";
								}else {
									dados_tabela=dados_tabela + " <td class=\"label_janelas\"><input type=\"text\" class=\"form-control row_rollout label_janelas\" tipo_info=\""+rs.getString("tipo")+"\" tipo_campo=\""+rs.getString("field_type")+"\" name=\""+rs.getString(2)+"\" id=\"row_field_"+rs.getRow()+"\" ></td>"+"\n";
								}
		    					dados_tabela=dados_tabela + "</tr>"+"\n";
		    				}
		    				/*	dados_tabela=dados_tabela + " <td class=\"label_janelas\">"+rs.getString(2)+"</td>"+"\n";
									if(rs.getString("tipo").equals("Data")){
										dados_tabela=dados_tabela + " <td><input type=\"data_row\" class=\"form-control row_rollout label_janelas\" name=\""+rs.getString(2)+"\" id=\""+rs.getString(2)+"\" tipo_campo=\""+rs.getString("field_type")+"\"></td>"+"\n";	
									}else if(rs.getString("tipo").equals("Lista")){
									valores	=rs.getString("Atributo_parametro").split(",");
									dados_tabela=dados_tabela + " <td><div type=\"lista\" class=\"row_rollout label_janelas\" name=\""+rs.getString(2)+"\"  tipo_campo=\""+rs.getString("field_type")+"\" id=\""+rs.getString(2)+"\" valores='";
									for(i=0;i<valores.length;i++){
										dados_tabela=dados_tabela +valores[i]+",";
									}
									dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
									dados_tabela=dados_tabela +"'></div></td>"+"\n";
									}else if(rs.getString("tipo").equals("Milestone")){
										dados_tabela=dados_tabela + "<td class=\"label_janelas\">Início<input type=\"data_row\" class=\"form-control row_rollout label_janelas\" tipo_campo=\""+rs.getString("field_type")+"\" name=\""+rs.getString(2)+"_inicio\" id=\""+rs.getString(2)+"_inicio\" >Fim<input type=\"data_row\" class=\"form-control row_rollout label_janelas\" tipo_campo=\""+rs.getString("field_type")+"\" name=\""+rs.getString(2)+"_fim\" id=\""+rs.getString(2)+"_fim\" ></td>"+"\n";
									}else{
										dados_tabela=dados_tabela + " <td class=\"label_janelas\"><input type=\"text\" class=\"form-control row_rollout label_janelas\" tipo_campo=\""+rs.getString("field_type")+"\" name=\""+rs.getString(2)+"\" id=\"row_field_"+rs.getRow()+"\" ></td>"+"\n";
									}
									
								
								
		    				}*/
		    				
		    				dados_tabela=dados_tabela + "</tbody>";
							dados_tabela=dados_tabela + "</table>";
							//System.out.println(dados_tabela);
							
		    			}else{
		    				//System.out.println("Sem campos do rollout ...");
		    				dados_tabela=dados_tabela +"</table>" +"\n";
		    			}
		    			//System.out.println(dados_tabela);
		    			resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("24")){
		    			System.out.println("funcao migrada para rollout servlet opt 24");
		    		}else if(opt.equals("99")){
		    			//System.out.println("saindo do Servlet");
		    			mysql.fecharConexao();
		    			session.removeAttribute("user");
		    			session.invalidate();
		    		}else if(opt.equals("25")){
		    			//System.out.println("saindo do Servlet");
		    			param1=req.getParameter("linha");
		    			if(param1.indexOf(",")>0){
		    				String []linhas=param1.split(",");
		    				for(int j=0;j<linhas.length;j++){
		    					mysql.Alterar("update rollout set linha_ativa='N',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+linhas[j]+" and empresa="+p.getEmpresa().getEmpresa_id());
		    				}
		    			
		    			}else{
		    				if(param1.length()>0){
		    					mysql.Alterar("update rollout set linha_ativa='N',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());

		    				}
		    			}
		    			resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("OK");
						out.close();
					}else if(opt.equals("26")){
						//System.out.println("Editando CampPO's");
						param1=req.getParameter("campos");
						//System.out.println(param1);
						dados_tabela="";
						if(param1.indexOf("'")>0){
							param1.replace("'","");
						}
						if(param1.indexOf(",")>0){
							String[] campos=param1.split(",");
							for(int i=0;i<1;i++){
								//System.out.println("delete from po_table where po_number="+po[i]);
								rs=mysql.Consulta("Select field_name,field_type,tipo,atributo_parametro,trigger_pagamento,percent_pagamento,field_id from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								//mysql.Excluir("delete from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								if(rs.next()){
									dados_tabela="{nome_campo:\""+rs.getString(1)+"\",tipo_Campo:\""+rs.getString(2)+"\",sub_tipo:\""+rs.getString(3)+"\",atributo:\""+rs.getString(4)+"\",\"trigger\":"+rs.getString(5)+",\"percent\":\""+rs.getString(6)+"\",\"id_campo\":"+rs.getInt("field_id")+"}";
								}
							}
						}else{
							//System.out.println("delete from po_table where po_number="+param1);
							rs=mysql.Consulta("Select field_name,field_type,tipo,atributo_parametro,trigger_pagamento,percent_pagamento,field_id from rollout_campos where field_id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());
							
							if(rs.next()){
								dados_tabela="{\"nome_campo\":\""+rs.getString(1)+"\",\"tipo_Campo\":\""+rs.getString(2)+"\",\"sub_tipo\":\""+rs.getString(3)+"\",\"atributo\":\""+rs.getString(4)+"\",\"trigger\":"+rs.getString(5)+",\"percent\":\""+rs.getString(6)+"\",\"id_campo\":"+rs.getInt("field_id")+"}";
							}
						}
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("27")){
						//System.out.println("carregando ajuda....");
						//param1=req.getParameter("campos");
						//System.out.println(param1);
						dados_tabela="";
						query="select * from ajuda_videos order by video_id desc";
						rs=mysql.Consulta(query);
						if(rs.next()){
							rs.beforeFirst();
							while(rs.next()){
								dados_tabela=dados_tabela+"<label class=\"control-label\"><h2>"+rs.getString("video_nome")+"</h2></label>"+"\n";
								dados_tabela=dados_tabela+"<br>"+"\n";
								dados_tabela=dados_tabela+"<iframe width=\"420\" height=\"315\" src=\""+rs.getString("video_url")+"\" frameborder=\"0\" allowfullscreen></iframe>"+"\n";
								dados_tabela=dados_tabela+"<hr>"+"\n";
							}
						}
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("28")){
						int linha_id=0;
						//System.out.println("linkando po no rollout ....");
						//param1=req.getParameter("campos");
						//System.out.println(param1);
						query="select distinct recid from rollout order by recid desc limit 1";
		    			rs=mysql.Consulta(query);
		    			if(rs.next()){
		    				linha_id=rs.getInt(1);
		    			}else{
		    				linha_id=0;
		    			}
		    			linha_id=linha_id+1;
						dados_tabela="";
						query="select po_item_site,po_number,id_projeto from po_item_table where sync_rollout_table='N' group by po_number,po_item_site";
						rs=mysql.Consulta(query);
						
						if(rs.next()){
							rs.beforeFirst();
							
								while(rs.next()){
									
									query="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo) values("+linha_id+",'site"+linha_id+"','Site ID','"+rs.getString(1)+"','Atributo')";
									mysql.Inserir_simples(query);
									query="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo) values("+linha_id+",'site"+linha_id+"','PO','"+rs.getString(2)+"','Atributo')";
									mysql.Inserir_simples(query);
									query="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo) values("+linha_id+",'site"+linha_id+"','Projeto','"+p.get_ProjetoNome(mysql,rs.getString(3))+"','Atributo')";
									mysql.Inserir_simples(query);
									query="update po_item_table set sync_rollout_table='Y',id_rollout='site"+linha_id+"' where po_item_site='"+rs.getString(1)+"' and po_number='"+rs.getString(2)+"'";
									mysql.Alterar(query);
									linha_id=linha_id+1;
								}
							
						}
						query="select * from po_item_table where sync_faturamento_table='N'";
						rs=mysql.Consulta(query);
						if(rs.next()){
							rs.beforeFirst();
							while(rs.next()){
								query="insert into faturamento (site_id,projeto,cliente,po,po_item,parcela_pagamento,status_faturamento,valor_item,id_rollout) values('"+rs.getString("po_item_site")+"','"+rs.getString("id_projeto")+"','"+rs.getString("id_cliente")+"','"+rs.getString("po_number")+"','"+rs.getString("po_item_desc")+"','1 de 1','Aguardando Execução','"+rs.getString("po_item_valor")+"','"+rs.getString("id_rollout")+"')";
								mysql.Inserir_simples(query);
								query="update po_item_table set sync_faturamento_table='Y' where po_item_site='"+rs.getString("po_item_site")+"' and po_number='"+rs.getString("po_number")+"' and po_item_desc='"+rs.getString("po_item_desc")+"'";
								mysql.Alterar(query);
							}
						}
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Dados Sincronizados!");
						out.close();
					}else if(opt.equals("29")){
						String Classe="";
						//System.out.println("Carregando Faturamento ....");
						param1=req.getParameter("conta");
						param2=req.getParameter("projeto");
						//System.out.println(param1);
						//System.out.println(param2);
						if((param1.equals("0")) && (param2.equals("all"))){ 
						query="select * from faturamento ";
						}else if((!param1.equals("0")) && (!param2.equals("0"))){
							query="select * from faturamento where cliente='"+param1+"' and projeto='"+param2+"'";
						}else if((!param1.equals("0")) && (param2.equals("0"))){
							query="select * from faturamento where cliente='"+param1+"'";
						}
		    			rs=mysql.Consulta(query);
		    			
		    			dados_tabela="<table id=\"tabela_faturamento\" data-use-row-attr-func=\"true\" data-toolbar=\"#toolbar_tabela_faturamento\" data-reorderable-rows=\"true\" data-show-refresh=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\" data-search=\"true\">" +"\n";
						dados_tabela=dados_tabela + "<thead>"+"\n";
						dados_tabela=dados_tabela +"<tr>"+"\n";
						dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"id_item_faturamento\" data-filter-control=\"select\">ID</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"item_faturamento\" data-filter-control=\"select\">Item</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"po_faturamento\" data-filter-control=\"select\">PO</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"valor_item_faturamento\">Valor Item</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"valor_parcela_faturamento\">Valor Parcela</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"parcela_faturamento\" data-filter-control=\"select\">Parcela</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"trigger_faturamento\" data-filter-control=\"select\">Trigger</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"percentual_faturamento\" data-filter-control=\"select\">Percentual</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"dt_trigger\" data-filter-control=\"select\">Data Trigger</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"nf_faturamento\" data-filter-control=\"select\">Nota Fiscal</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"site_faturamento\" data-filter-control=\"select\">Site</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"status_faturamento\" data-filter-control=\"select\">Status</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"conta_faturamento\" data-filter-control=\"select\">Conta</th>"+"\n";
						dados_tabela=dados_tabela +" <th style data-field=\"projeto_faturamento\" data-filter-control=\"select\">Projeto</th>"+"\n";
						dados_tabela=dados_tabela +"</tr>"+"\n";
						dados_tabela=dados_tabela +"</thead>"+"\n";
						dados_tabela=dados_tabela +"<tbody>"+"\n";
						if(rs.next()){
						rs.beforeFirst();
						while(rs.next()){
							if(rs.getString(11).equals("Liberado para Faturar")){
								Classe="success";
							}else{
								Classe="info";
							}
							dados_tabela=dados_tabela + "<tr class=\""+Classe+"\" data-index=\""+rs.getRow()+"\">"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(1)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(6)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(5)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(21)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(18)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(9)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(7)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(8)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(12)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(10)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(2)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(11)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(4)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(3)+"</td>"+"\n";
							
							dados_tabela=dados_tabela + "</tr>"+"\n";
						}
						}
						dados_tabela=dados_tabela + "</tbody>";
						dados_tabela=dados_tabela + "</table>";
						
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("30")){
						
						//System.out.println("Carregando Milestones para o faturamento ....");
						dados_tabela="";
						query="select * from rollout_campos where field_type='Milestone'";
		    			rs=mysql.Consulta(query);
		    			if(rs.next()){
		    				rs.beforeFirst();
		    				dados_tabela="[";
		    				while(rs.next()){
		    					dados_tabela=dados_tabela+"{\"value\":\""+rs.getString("field_name")+"\" , \"text\":\""+rs.getString("field_name")+"\"},";
		    				}
		    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
		    				dados_tabela=dados_tabela+"]";
		    			}
		    			resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("31")){
		    			int aux_id,j,i=0;
		    			int aux_id_qtd=0;
		    			System.out.println("Adicionando Fluxo de Pagamento ...");
		    			param1=req.getParameter("fluxo");
		    			
		    			JSONObject jObj = new JSONObject(param1); 
		    			JSONArray fluxo = jObj.getJSONArray("fluxo");
		    			System.out.println(fluxo.length());
		    			aux_id=fluxo.getJSONObject(0).getInt("id");
		    			for( i=0;i<fluxo.length();i++){
		    				if(aux_id==fluxo.getJSONObject(i).getInt("id")){
		    					aux_id_qtd++;
		    				}else{
		    					break;
		    				}
		    			}
		    			i=0;
		    			
		    			while(i<fluxo.length()){
		    				
		    				rs=mysql.Consulta("select * from faturamento where id_faturamento="+fluxo.getJSONObject(i).getInt("id"));
		    				if(rs.next()){
		    					j=0;
		    					while(j<aux_id_qtd){
		    					if(j==0){
		    						money = Double.parseDouble(rs.getString("valor_item"));
			    					money=money*(Double.parseDouble(fluxo.getJSONObject(i).getString("percentual"))/100);
		    						insere="update faturamento set parcela_pagamento='1 de "+aux_id_qtd+"',milestones_trigger='"+fluxo.getJSONObject(i).getString("milestone")+"',percent='"+fluxo.getJSONObject(i).getString("percentual")+"',valor_parcela='"+money+"' where id_faturamento="+fluxo.getJSONObject(i).getInt("id");
			    					
		    						if(mysql.Alterar(insere)){
		    							System.out.println("Atualizado com sucesso: item id:"+fluxo.getJSONObject(i).getInt("id"));
		    						}
		    						j++;
			    					i++;
		    					}else{
		    					money = Double.parseDouble(rs.getString("valor_item"));
		    					money=money*(Double.parseDouble(fluxo.getJSONObject(i).getString("percentual"))/100);
		    					insere="insert into faturamento (site_id,projeto,cliente,po,po_item,parcela_pagamento,status_faturamento,valor_parcela,valor_item,id_rollout,milestones_trigger,percent,id_item_relacionado) values('"+rs.getString("site_id")+"','"+rs.getString("projeto")+"','"+rs.getString("cliente")+"','"+rs.getString("po")+"','"+rs.getString("po_item")+"','"+(j+1)+" de "+aux_id_qtd+"','Aguardando Execução','"+money+"','"+rs.getString("valor_item")+"','"+rs.getString("id_rollout")+"','"+fluxo.getJSONObject(i).getString("milestone")+"','"+fluxo.getJSONObject(i).getString("percentual")+"','"+fluxo.getJSONObject(i).getInt("id")+"')";
		    					
		    					if(mysql.Inserir_simples(insere)){
		    						System.out.println("Inserido novos itens de pg com sucesso item id original:"+fluxo.getJSONObject(i).getInt("id"));
		    					}
		    					j++;
		    					i++;
		    					}
		    					}
		    				}
		    				//System.out.println(fluxo.getJSONObject(i).getString("milestone"));
		    			}
		    		}else if(opt.equals("32")){
						//System.out.println("Sincronizando tabela de faturamento...");
						query="select * from faturamento where milestones_trigger<>'0' and status_faturamento='Aguardando Execução'";
						rs=mysql.Consulta(query);
						if(rs.next()){
							rs.beforeFirst();
							while(rs.next()){
								query="select * from rollout where siteID='"+rs.getString("id_rollout")+"' and milestone='"+rs.getString("milestones_trigger")+"'";
								//System.out.println(query);
								rs2=mysql.Consulta(query);
								if(rs2.next()){
									rs2.beforeFirst();
									while(rs2.next()){
										if(rs2.getString("dt_fim").equals("01/01/1800")){
											
										}else{
											
											query="update faturamento set milestone_data_fim_real='"+rs2.getString("dt_fim")+"',status_faturamento='Liberado para Faturar' where id_rollout='"+rs.getString("id_rollout")+"'";
											mysql.Alterar(query);
										}
									}
								}
							}
						}
					}else if(opt.equals("33")){
						query="";
						query="select * from perfil_funcoes where usuario_id='"+p.get_PessoaUsuario()+"' and ativo='Y'";
						rs=mysql.Consulta(query);
						if(rs.next()) {
							dados_tabela="{\n";
							rs.beforeFirst();
							dados_tabela=dados_tabela+"\"perfil\":\"";
							while(rs.next()) {
								dados_tabela=dados_tabela+rs.getString("funcao_nome")+";";
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
							dados_tabela=dados_tabela+"\",\n";
							dados_tabela=dados_tabela+"\"usuario\":\""+p.get_PessoaUsuario()+"\",\n";
							dados_tabela=dados_tabela+"\"nome\":\""+p.get_PessoaName()+"\",\n";
							dados_tabela=dados_tabela+"\"admissao\":\""+p.getAdmissao()+"\",\n";
							dados_tabela=dados_tabela+"\"cargo\":\""+p.getCargo()+"\",\n";
							dados_tabela=dados_tabela+"\"pis\":\""+p.getPis()+"\",\n";
							dados_tabela=dados_tabela+"\"ctps\":\""+p.getCtps()+"\",\n";
							dados_tabela=dados_tabela+"\"matricula\":\""+p.getMatricula()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_nome\":\""+p.getEmpresa().getNome()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_id\":"+p.getEmpresa().getEmpresa_id()+",\n";
							dados_tabela=dados_tabela+"\"empresa_fantasia\":\""+p.getEmpresa().getNome_fantasia()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_cnpj\":\""+p.getEmpresa().getCnpj()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_cnae\":\""+p.getEmpresa().getCnae()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_bairro\":\""+p.getEmpresa().getBairro()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_uf\":\""+p.getEmpresa().getUf()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_endereco\":\""+p.getEmpresa().getEndereco()+"\",\n";
							dados_tabela=dados_tabela+"\"empresa_cidade\":\""+p.getEmpresa().getCidade()+"\"\n";
							dados_tabela=dados_tabela+"}";
							//System.out.println(dados_tabela);
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
							out.close();
						}else {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("[\"perfil nao definido\"]");
						}
					}else if(opt.equals("34")){
						param1=req.getParameter("titulo");
						param2=req.getParameter("mensagem");
						int id_message=0;
						query="select id_message from updates_message order by id_message desc limit 1";
						rs=mysql.Consulta(query);
						if(rs.next()) {
							id_message=rs.getInt(1)+1;
						}
						query="select id_usuario from usuarios where validado='Y' and ativo='Y'";
						rs=mysql.Consulta(query);
						if(rs.next()) {
							rs.beforeFirst();
							while(rs.next()) {
								mysql.Inserir_simples("insert into updates_message (id_message,usuario,titulo,message,messagem_lida,dt_message) values("+id_message+",'"+rs.getString(1)+"','"+param1+"','"+param2+"','N','"+time+"')");
							}
						}
					}else if(opt.equals("35")){
						query="update empresas set modulo='Módulo de controle Usuarios',assinado='Y',dt_termo_aceite='"+time+"',valor='R$326,00' where id_empresa="+p.getEmpresa().getEmpresa_id();
						if(mysql.Update_simples(query)) {
							
						}
					}else if(opt.equals("36")){
						query="update empresas set modulo='Módulo de controle Usuarios',assinado='Cancelado',dt_termo_aceite='"+time+"',valor='R$326,00' where id_empresa="+p.getEmpresa().getEmpresa_id();
						if(mysql.Update_simples(query)) {
							
						}
					}else if(opt.equals("37")){
						query="select * from modulo_mstp where empresa="+p.getEmpresa().getEmpresa_id();
						rs=mysql.Consulta(query);
						String Classe;
						String color;
							dados_tabela="<table id=\"tabela_modulo_mstp\" data-use-row-attr-func=\"true\" data-toggle=\"table\"  data-pagination=\"true\" data-search=\"true\">" +"\n";
							dados_tabela=dados_tabela + "<thead>"+"\n";
							dados_tabela=dados_tabela +"<tr>"+"\n";
							dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"id_modulo_nome\" data-filter-control=\"select\">Nome Módulo</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"modulo_desc\" data-filter-control=\"select\">Descrição</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"modulo_dt_assinado\" data-filter-control=\"select\">Data da Assinatura</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"modulo_valor\">Valor Módulo</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"modulo_termo\">Termo de Aceito</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"modulo_dt_termo\">Data de Aceite</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"modulo_Status_Assinatura\">Assinar</th>"+"\n";
							
							dados_tabela=dados_tabela +"</tr>"+"\n";
							dados_tabela=dados_tabela +"</thead>"+"\n";
							dados_tabela=dados_tabela +"<tbody>"+"\n";
							if(rs.next()){
								rs.beforeFirst();
								while(rs.next()){
									
									dados_tabela=dados_tabela + "<tr   data-index=\""+rs.getRow()+"\">"+"\n";
									dados_tabela=dados_tabela + " <td ></td>"+"\n";
									dados_tabela=dados_tabela + " <td >"+rs.getString("modulo_nome")+"</td>"+"\n";
									dados_tabela=dados_tabela + " <td >"+rs.getString("modulo_descricao")+"</td>"+"\n";
									dados_tabela=dados_tabela + " <td >"+rs.getString("dt_assinado")+"</td>"+"\n";
									dados_tabela=dados_tabela + " <td >"+rs.getString("valor")+"</td>"+"\n";
									dados_tabela=dados_tabela + " <td >"+rs.getString("termo_aceite")+"</td>"+"\n";
									dados_tabela=dados_tabela + " <td >"+rs.getString("dt_termo_aceite")+"</td>"+"\n";
									if(rs.getString("assinado").equals("Y")){
										dados_tabela=dados_tabela + " <td >Assinado</td>"+"\n";
									}else{
										if(rs.getString("termo_aceite").equals("Y")){
											dados_tabela=dados_tabela + " <td >"+rs.getString("botao")+"</td>"+"\n";
										}else {
											
											dados_tabela=dados_tabela + " <td ><button type=\"button\" class=\"btn btn-info\" onclick=\"inicia_assinatura(1)\">Termo de Aceite</button></td>"+"\n";
										}
										
									}
									
									
									
									dados_tabela=dados_tabela + "</tr>"+"\n";
								}
								
								dados_tabela=dados_tabela + "</tbody>";
								dados_tabela=dados_tabela + "</table>";
								resp.setContentType("application/text")  ;
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
								out.close();
							}else {
								
								dados_tabela=dados_tabela + "</tbody>";
								dados_tabela=dados_tabela + "</table>";
								resp.setContentType("application/text")  ;
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
								out.close();
							}
					}else if(opt.equals("38")) {
						int contador=0;
						String progress_bar="";
						if(p.get_PessoaUsuario().toUpperCase().contains("MASTERADMIN")) {
							query="select *,now() from arquivos_importados order by id_sys_arq desc";
						}else {
							query="select *,now() from arquivos_importados where arq_usuario='"+p.get_PessoaUsuario()+"' order by id_sys_arq desc";
						}
						rs=mysql.Consulta(query);
						if(rs.next()) {
							rs.beforeFirst();
							
							dados_tabela="{\"draw\": 1,\n";
							dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
							dados_tabela=dados_tabela+"\"recordsFiltered\": 15 ,\n";
							dados_tabela=dados_tabela+"\"data\":[\n";
							while(rs.next()) {
								contador=contador+1;
								progress_bar="<div class='progress'><div class='progress-bar progress-bar-striped active' role='progressbar' aria-valuenow='valor_' aria-valuemin='0' aria-valuemax='100' style='width:valor_%'>valor_%</div></div>";
								progress_bar=progress_bar.replaceAll("valor_", rs.getString("status_processamento"));
								if(rs.getString("status_processamento").equals("100")) {
									progress_bar=progress_bar.replaceAll("progress-bar-striped","progress-bar-success");
								}
								dados_tabela=dados_tabela + "[";
								dados_tabela=dados_tabela + "\""+rs.getString("arq_dt_inserido")+"\",";
								dados_tabela=dados_tabela + "\""+rs.getString("arq_usuario")+"\",";
								dados_tabela=dados_tabela + "\""+rs.getString("arq_nome")+"\",";
								dados_tabela=dados_tabela + "\"<div style='color:black'><a style='color:black' href='./POControl_Servlet?opt=40&idarq="+rs.getInt("id_sys_arq")+"'>Baixar</a></div>\",";
								dados_tabela=dados_tabela + "\""+progress_bar+"\"],"+"\n";
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
							dados_tabela=dados_tabela+"]}";
							dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
							out.close();
						}
					}else if(opt.equals("39")) {
						if(mysql.Alterar("update modulo_mstp set termo_aceite='Y',dt_termo_aceite='"+time+"' where empresa="+p.getEmpresa().getEmpresa_id())) {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Termo aceite com Sucesso");
							out.close();
						}else {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Erro na aceitação do Termo");
							out.close();
						}
					}else if(opt.equals("40")) {
						param1=req.getParameter("idarq");
						//System.out.println("Carregando foto de perfil de "+p.get_PessoaUsuario());
						ServletOutputStream out = resp.getOutputStream();
						query="";
						query="select * from arquivos_importados where id_sys_arq="+param1;
						rs=mysql.Consulta(query);
						if(rs.next()) {
							//System.out.println(" foto encontrada");
							XSSFWorkbook workbook=new XSSFWorkbook(rs.getBlob("arq").getBinaryStream());
							
							//byte byteArray[]=workbook.getBytes(1, (int) image.length());
							resp.setContentType(rs.getString("arq_tipo"));
							resp.setHeader("Content-Disposition", "attachment; filename="+rs.getString("arq_nome"));
			                workbook.write(resp.getOutputStream());
			                workbook.close();
							//System.out.println(" foto carregada");
							
						
						}
					}else if(opt.equals("41")) {
						//System.out.println("chegou aqui na 41");
						param1=req.getParameter("pono");
						param2=req.getParameter("itemcode");
						param3=req.getParameter("poline");
						param4=req.getParameter("publishdate");
						//System.out.println(param1);
						//System.out.println(param2);
						//System.out.println(param3);
						//System.out.println(param4);
						Bson filtro;
						List<Bson> filtros = new ArrayList<>();
						filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						filtro=Filters.eq("PO NO",param1);
						filtros.add(filtro);
						filtro=Filters.eq("PO Line NO",param3);
						filtros.add(filtro);
						filtro=Filters.eq("Item Code",param2);
						filtros.add(filtro);
						if(param4.equals(null) || param4==null) {
							
						}else {
							filtro=Filters.eq("Publish Date",param4);
							filtros.add(filtro);
						}
						FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("PO", filtros);
						if(findIterable.iterator().hasNext()) {
							Document resultado = findIterable.iterator().next();
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(resultado.toJson());
							out.close();
						}else {
							resp.setContentType("application/text");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Informações não encontradas");
							out.close();
						}
						
					}else if(opt.equals("42")) {
						//System.out.println("Buscando PO's nos tickects");
						Bson filtro;
						List<Bson> filtros = new ArrayList<>();
						filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						filtro=Filters.eq("TICKET_TODOS_ITENS","Y");
						filtros.add(filtro);
						List<String> pos_ticket = mongo.ConsultaSimplesDistinct("TICKETS", "PO_NUM", filtros);
						
						//System.out.println("tamanho do resultado das POsé : "+ pos_ticket.size());
						dados_tabela="";
						
						filtros = new ArrayList<>();
						filtro = Filters.nin("PO NO", pos_ticket);
						filtros.add(filtro);
						filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						filtro=Filters.eq("PO_ATIVA","Y");
						filtros.add(filtro);
						List<String> pos = mongo.ConsultaSimplesDistinct("PO","PO NO", filtros);
						//pos.removeAll(pos_ticket);
						if(!pos.isEmpty()) {
							for(int i=0;i<pos.size();i++) {
								
								dados_tabela=dados_tabela+"<option value='"+pos.get(i)+"'>"+pos.get(i)+"</option>";
							}
						}
						//System.out.println("Resultado: "+ dados_tabela);
						resp.setContentType("application/text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
						
					}else if(opt.equals("43")) {
						dados_tabela="";
						param1=req.getParameter("po");
						Bson filtro;
						Document poItemLinha;
						List<Bson> filtros= new ArrayList<>();
						filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						filtro=Filters.eq("PO NO",param1);
						filtros.add(filtro);
						filtro=Filters.eq("PO_ATIVA","Y");
						filtros.add(filtro);
						filtro=Filters.or(Filters.eq("Tickets",""),Filters.not(Filters.eq("Requested Qty","0")));
						filtros.add(filtro);
						FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("PO", filtros);
						MongoCursor<Document> resultado =findIterable.iterator();
						dados_tabela= dados_tabela+"[";
						if(resultado.hasNext()){
							while(resultado.hasNext()) {
								poItemLinha=resultado.next();
								dados_tabela=dados_tabela+"{";
								dados_tabela=dados_tabela+"\"PO_NUM\":\""+poItemLinha.getString("PO NO")+"\",";
								dados_tabela=dados_tabela+"\"ITEM\":\""+poItemLinha.getString("Item Description")+"\",";
								dados_tabela=dados_tabela+"\"ITEM_CODE\":\""+poItemLinha.getString("Item Code")+"\",";
								dados_tabela=dados_tabela+"\"PO_LINE\":\""+poItemLinha.getString("PO Line NO")+"\",";
								if(poItemLinha.get("Publish Date")!=null) {
									dados_tabela=dados_tabela+"\"PUBLISHED\":\""+poItemLinha.getString("Publish Date")+"\",";
								}else if(poItemLinha.get("creation_date")!=null) {
									dados_tabela=dados_tabela+"\"PUBLISHED\":\""+poItemLinha.getString("creation_date")+"\",";
								}else {
									dados_tabela=dados_tabela+"\"PUBLISHED\":\""+poItemLinha.getString("NO DATE FOUND")+"\",";
								}
								
								dados_tabela=dados_tabela+"\"SITE\":\""+poItemLinha.getString("Site Name")+"\",";
								dados_tabela=dados_tabela+"\"PROJETO\":\""+poItemLinha.getString("Project Name")+"\",";
								dados_tabela=dados_tabela+"\"PROJETO_CODE\":\""+poItemLinha.getString("Project Code")+"\",";
								dados_tabela=dados_tabela+"\"QTDE\":"+poItemLinha.getString("Requested Qty").replace(",", ".")+",";
								dados_tabela=dados_tabela+"\"QTDE_TICKET\":"+poItemLinha.getString("Requested Qty").replace(",", ".")+"},";
								
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
						}
						dados_tabela=dados_tabela+"]";
						
					
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("44")) {
						param1 = req.getParameter("ticket");
						JSONObject params = new JSONObject(param1);
						
						criaTicket(mysql,mongo,params,p);
						mongo.fecharConexao();
					}else if(opt.equals("45")) {
						try {
						dados_tabela="";
						param1=req.getParameter("options");
						
						JSONObject filtrosKanbam = new JSONObject(param1);
						Bson filtro;
						Document tickect_item;
						List<Bson> filtros= new ArrayList<>();
						filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						if(!filtrosKanbam.isNull("usuario")) {
							if(!filtrosKanbam.getString("usuario").equals("ALL")) {
								filtro=Filters.eq("RECURSO",filtrosKanbam.getString("usuario"));
								filtros.add(filtro);
							}
						}
						if(!filtrosKanbam.isNull("projeto")) {
							if(!filtrosKanbam.getString("projeto").equals("Todos os Projetos")) {
								filtro=Filters.eq("PROJETO",filtrosKanbam.getString("projeto"));
								filtros.add(filtro);
							}
						}
						if(!filtrosKanbam.isNull("range")) {
							
							Calendar range = Calendar.getInstance();
							if(filtrosKanbam.getString("range").equals("Hoje")) {
								filtro=Filters.gte("INICIO_BL_DATE",range.getTime());
								filtros.add(filtro);
								filtro=Filters.lte("FIM_BL_DATE",range.getTime());
								filtros.add(filtro);
							}else if(filtrosKanbam.getString("range").equals("semanaCorrente")) {
								int dia_inicio=range.get(Calendar.DAY_OF_WEEK);
								int dia_fim = 7-dia_inicio;
								Calendar inicio = Calendar.getInstance();
								Calendar fim = Calendar.getInstance();
								inicio.add(Calendar.DAY_OF_MONTH, dia_inicio*(-1));
								fim.add(Calendar.DAY_OF_MONTH, dia_fim);
								filtro=Filters.gte("INICIO_BL_DATE",inicio.getTime());
								filtros.add(filtro);
								filtro=Filters.lte("FIM_BL_DATE",fim.getTime());
								filtros.add(filtro);
							}
						}
						String aux_ticket="";
						String label_aux="";
						String site_aux="";
						FindIterable<Document> findIterable = mongo.ConsultaOrdenada2("TICKETS", "TICKET_NUM", 1, filtros);
						MongoCursor<Document> resultado =findIterable.iterator();
						dados_tabela= dados_tabela+"{\"source\":\n[\n";
						if(resultado.hasNext()){
							while(resultado.hasNext()) {
								tickect_item=resultado.next();
								if(!aux_ticket.equals(tickect_item.getString("TICKET_NUM"))) {
									if(!aux_ticket.equals("")) {
										dados_tabela=dados_tabela+"\"content\":\""+label_aux+"</div></div><div id='expanderTicketChecklist_"+aux_ticket+"' class='jqxExpanderTicketClass'><div id='expanderHeader'>Atividades</div><div id='expanderContent'> <div id='jqxCheckBoxMOS_"+aux_ticket+"' data-atividade='MOS' style='margin-left: 10px;'><span></span></div> <div id='jqxCheckBoxSiteSign_"+aux_ticket+"' data-atividade='site_sign' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxSiteVerify_"+aux_ticket+"' data-atividade='site_verify' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxMOSWH_"+aux_ticket+"' data-atividade='MOS_WH' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxMOSSITE_"+aux_ticket+"' data-atividade='MOS_SITE' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxEHS_"+aux_ticket+"' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxQCagendado_"+aux_ticket+"' style='margin-left: 10px;'><span></span></div></div></div>\"";
										dados_tabela=dados_tabela+"},\n";
										//System.out.println("Dados Tabela é : =>\n"+dados_tabela);
										label_aux="";
									}
									aux_ticket=tickect_item.getString("TICKET_NUM");
									if(tickect_item.get("SITE")!=null) {
										if(tickect_item.getString("SITE").indexOf("_")>0) {
											site_aux=tickect_item.getString("SITE").substring(0,tickect_item.getString("SITE").indexOf("_"));
										}else {
											site_aux=tickect_item.getString("SITE");
										}
									}else {
										site_aux="SITE Ñ IDENTIFICADO";
									}
									
									//System.out.println(site_aux);
									dados_tabela=dados_tabela+"{";
									dados_tabela=dados_tabela+"\"id\":\""+tickect_item.getString("TICKET_NUM")+"\",";
									dados_tabela=dados_tabela+"\"state\":\""+tickect_item.getString("TICKET_STATE")+"\",";
									dados_tabela=dados_tabela+"\"label\":\"<div><label>Ticket - "+tickect_item.getString("TICKET_NUM")+"</label>\",";
									dados_tabela=dados_tabela+"\"tags\":\""+tickect_item.getString("TAGS")+"\",";
									dados_tabela=dados_tabela+"\"hex\":\""+tickect_item.getString("COR")+"\",";
									dados_tabela=dados_tabela+"\"resourceId\":\""+tickect_item.getString("RECURSO")+"\",";
									label_aux=label_aux+"<div id='expanderTicket_"+tickect_item.getString("TICKET_NUM")+"' class='jqxExpanderTicketClass'><div id='expanderHeader'>"+site_aux+" / "+tickect_item.getString("PO_NUM")+"</div><div id='expanderContent'><br> Site: "+site_aux+"<br>Descrição: "+tickect_item.getString("ITEM")+"<br>Quantidade: "+tickect_item.getString("QTDE_TICKET")+"<br>";
								}else {
									label_aux=label_aux+"<br> Site: "+site_aux+"<br>Descrição: "+tickect_item.getString("ITEM")+"<br>Quantidade: "+tickect_item.getString("QTDE_TICKET")+"<br>";
								}
							}
							dados_tabela=dados_tabela+"\"content\":\""+label_aux+"</div></div><div id='expanderTicketChecklist_"+aux_ticket+"' class='jqxExpanderTicketClass'><div id='expanderHeader'>Atividades</div><div id='expanderContent'><div id='jqxCheckBoxMOS_"+aux_ticket+"' data-atividade='MOS' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxSiteSign_"+aux_ticket+"' class='TickectChecklist' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxSiteVerify_"+aux_ticket+"' class='TickectChecklist' data-atividade='site_verify' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxMOSWH_"+aux_ticket+"' data-atividade='MOS_WH' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxMOSSITE_"+aux_ticket+"' data-atividade='MOS_SITE' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxEHS_"+aux_ticket+"' class='TickectChecklist' style='margin-left: 10px;'><span></span></div><div id='jqxCheckBoxQCagendado_"+aux_ticket+"' style='margin-left: 10px;'><span></span></div></div></div>\"";
							dados_tabela=dados_tabela+"},\n";
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
							
						}
						dados_tabela= dados_tabela+"\n],\n";
						//System.out.println("Dados Tabela é : =>\n"+dados_tabela);
						dados_tabela= dados_tabela+"\"recursos\":[";
						rs=mysql.Consulta("select * from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' and ATIVO='Y'");
						if(rs.next()) {
							rs.beforeFirst();
							while(rs.next()) {
								dados_tabela=dados_tabela+"{";
								dados_tabela=dados_tabela+"\"id\":\""+rs.getString("id_usuario")+"\",";
								dados_tabela=dados_tabela+"\"name\":\""+rs.getString("nome")+"\"},\n";
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
							
						}
						dados_tabela= dados_tabela+"\n]";
						dados_tabela= dados_tabela+"}";
						//System.out.println("Dados Tabela é : =>\n"+dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
						mongo.fecharConexao();
						}catch(Exception e) {
							System.out.println("Dados Tabela é : =>\n"+dados_tabela);
							System.out.println("ERRO");
							System.out.println(e.getMessage());
							System.out.println(e.getCause());
						}
					}else if(opt.equals("46")) {
						param1=req.getParameter("id_");
						param2=req.getParameter("status_");
						//System.out.println(param1);
						//System.out.println(param2);
						Document update_filtros = new Document();
						Document update_comando = new Document();
						Document update = new Document();
						update_filtros.append("Empresa", p.getEmpresa().getEmpresa_id());
						update_filtros.append("TICKET_NUM", param1);
						update.append("TICKET_STATE", param2);
						update_comando.append("$set", update);
						mongo.AtualizaMuitos("TICKETS", update_filtros, update_comando);
					}else if(opt.equals("47")) {
						dados_tabela="";
						Bson filtro;
						Document tickect_item;
						List<Bson> filtros= new ArrayList<>();
						filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						
						
						FindIterable<Document> findIterable = mongo.ConsultaOrdenada2("TICKETS", "TICKET_NUM", 1, filtros);
						MongoCursor<Document> resultado =findIterable.iterator();
						dados_tabela= dados_tabela+"{\"dados\":\n[\n";
						if(resultado.hasNext()){
							while(resultado.hasNext()) {
								tickect_item=resultado.next();
								
									dados_tabela=dados_tabela+"{";
									dados_tabela=dados_tabela+"\"id\":\""+tickect_item.getString("TICKET_NUM")+"\",";
									dados_tabela=dados_tabela+"\"projeto\":\""+tickect_item.getString("PROJETO")+"\",";
									dados_tabela=dados_tabela+"\"recurso\":\""+tickect_item.getString("RECURSO")+"\",";
									dados_tabela=dados_tabela+"\"inicio\":\""+tickect_item.getString("INICIO_BL_STR")+"\",";
									dados_tabela=dados_tabela+"\"fim\":\""+tickect_item.getString("FIM_BL_STR")+"\",";
									dados_tabela=dados_tabela+"\"status\":\""+tickect_item.getString("TICKET_STATE")+"\"},\n";
							}
							
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
							
						}
						dados_tabela= dados_tabela+"\n]}";
						
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
						mongo.fecharConexao();
					}else if(opt.equals("48")) {
						Bson filtro;
						Document po;
						List<Bson> filtros= new ArrayList<>();
						filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						FindIterable<Document> findIterable = mongo.ConsultaOrdenada2("PO", "PO NO", 1, filtros);
						MongoCursor<Document> resultado =findIterable.iterator();
						
						if(resultado.hasNext()){
							System.out.println("Crindo Controle Master");
							XSSFWorkbook workbook = new XSSFWorkbook();
							XSSFSheet sheet = workbook.createSheet("MSTP E2E Detail Table");
							Row row;
							Cell cell;
							int rowIndex = 0;
							int colIndex = 0;
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
			                XSSFCellStyle cellStyle3 = workbook.createCellStyle();
			            	
			                cellStyle3.setAlignment(HorizontalAlignment.CENTER);
			                cellStyle3.setVerticalAlignment(VerticalAlignment.CENTER);
			                cellStyle3.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			                cellStyle3.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			                cellStyle3.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			                cellStyle3.setBorderBottom(BorderStyle.THIN);
			                cellStyle3.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			                cellStyle3.setBorderLeft(BorderStyle.THIN);
			                cellStyle3.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			                cellStyle3.setBorderRight(BorderStyle.THIN);
			                cellStyle3.setRightBorderColor(IndexedColors.BLACK.getIndex());
			                cellStyle3.setBorderTop(BorderStyle.THIN);
			                cellStyle3.setTopBorderColor(IndexedColors.BLACK.getIndex());
			                rowIndex=1;
			            	row = sheet.createRow((short) rowIndex);
			            	row.setRowStyle(cellStyle);
			            	cell = row.createCell((short) colIndex);
			                cell.setCellValue("PO_ID(PO NO + Ship NO)");
			                sheet.autoSizeColumn(colIndex);
			                cell.setCellStyle(cellStyle3);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("PR_NUM");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("PO NO");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("REVISION_NUM");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("PO_LINE_NUM");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("SHIPMENT_NUM");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("SHIPPING_MODE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("SHIP_TO_DETAIL");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("PO+LINE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("CUTOMER_ITEM");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("PROJECT");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("SITE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("CREATION_DATE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("REQUEST_DATE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("EXPIRE_DATE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("NEED_DATE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("QTY_REQUEST");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("DUE_REQUEST");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("UNIT_PRICE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("TOTAL_LINE_PRICE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("UNIT_OF_MEASURE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("INVOICED");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                
			                cell = row.createCell((short) colIndex);
			                cell.setCellValue("INVOICE");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                colIndex=0;
			                rowIndex=rowIndex+1;
							while(resultado.hasNext()) {
								po=resultado.next();
								row = sheet.createRow((short) rowIndex);
								cell = row.createCell((short) colIndex);
								if(po.get("PO NO")!=null) {
									if(po.get("Shipment NO")!=null) {
										cell.setCellValue(po.getString("PO NO")+"_"+po.getString("Shipment NO"));
									}else {
										cell.setCellValue(po.getString("PO NO")+"_");
									}
								}else {
									cell.setCellValue("NO PO ID FOUND");
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("PR NO")!=null) {
									cell.setCellValue(po.getString("PR NO"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				              
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("PO NO")!=null) {
									cell.setCellValue(po.getString("PO NO"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				               
				                cell = row.createCell((short) colIndex);
								if(po.get("Change History")!=null) {
									cell.setCellValue(po.getString("Change History"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("PO Line NO")!=null) {
									cell.setCellValue(po.getString("PO Line NO"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Shipment NO")!=null) {
									cell.setCellValue(po.getString("Shipment NO"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Bill To")!=null) {
									cell.setCellValue(po.getString("Bill To"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Bill To")!=null) {
									cell.setCellValue(po.getString("Bill To"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("PO NO")!=null) {
									if(po.get("PO Line NO")!=null) {
									cell.setCellValue(po.getString("PO NO")+"_"+po.getString("PO Line NO"));
									}
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Item Code")!=null) {
									cell.setCellValue(po.getString("Item Code"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Project Name")!=null) {
									cell.setCellValue(po.getString("Project Name"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Site Name")!=null) {
									cell.setCellValue(po.getString("Site Name"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Publish Date")!=null) {
									
									cell.setCellValue(po.getString("Publish Date"));
								}else if(po.get("creation_date")!=null) {
									
									cell.setCellValue(po.getString("creation_date"));
								}else {
									cell.setCellValue("NO DATE FOUND");
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Start Date")!=null) {
									cell.setCellValue(po.getString("Start Date"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("End Date")!=null) {
									cell.setCellValue(po.getString("End Date"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("End Date")!=null) {
									cell.setCellValue(po.getString("End Date"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Requested Qty")!=null) {
									cell.setCellValue(po.getString("Requested Qty"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Due Qty")!=null) {
									cell.setCellValue(po.getString("Due Qty"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Unit Price")!=null) {
									cell.setCellValue(po.getString("Unit Price"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Line Amount")!=null) {
									cell.setCellValue(po.getString("Line Amount"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                
				                cell = row.createCell((short) colIndex);
								if(po.get("Unit")!=null) {
									cell.setCellValue(po.getString("Unit"));
								}
				                sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                //invoiced
				                cell = row.createCell((short) colIndex);
								cell.setCellValue(po.getString("N"));
								sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                //end invoiced
				                //invoiced
				                cell = row.createCell((short) colIndex);
								cell.setCellValue(po.getString(""));
								sheet.autoSizeColumn(colIndex);
				                colIndex=colIndex+1;
				                //end invoiced
				                rowIndex=rowIndex+1;
				                colIndex=0;
							}
							resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			                resp.setHeader("Content-Disposition", "attachment; filename="+time.toString()+"_Master_Control.xlsx");
			                workbook.write(resp.getOutputStream());
			                workbook.close();
						}
					}else if(opt.equals("49")) {
						Calendar d = Calendar.getInstance();
						param1 = req.getParameter("dados");
						JSONObject detalhes = new JSONObject(param1);
						param6 = req.getParameter("ticket_num");
						param2 = req.getParameter("atividade");
						param3 = req.getParameter("gap");
						param4 = req.getParameter("owner");
						param5 = req.getParameter("desc");
						
						if(detalhes.getString("tipo").equals("RegistroManualEventos")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_FUNC",detalhes.getString("fun").replaceAll(",", ";"));
							historico.append("TICKET_HISTORICO_ATIVIDADE",detalhes.getString("atividade"));
							historico.append("TICKET_HISTORICO_GAP",detalhes.getString("gap"));
							historico.append("TICKET_HISTORICO_OWNER",detalhes.getString("owner"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("ItemMovidoManualmente")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("MudancaResponsavel")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("ReplanejamentoDatas")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("AgendamentoQC")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("SiteSign")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("SiteVerify")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("EHS_OK")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("MOS_Date")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("Site_OK")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}else if(detalhes.getString("tipo").equals("WH_OK")) {
							Document historico = new Document();
							historico.append("TICKET_NUM", detalhes.getString("ticketnum"));
							historico.append("Empresa", p.getEmpresa().getEmpresa_id());
							historico.append("TICKET_HISTORICO_ADD_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_DATE",d.getTime());
							historico.append("TICKET_HISTORICO_ADD_MILIS",d.getTimeInMillis());
							historico.append("TICKET_HISTORICO_ADD_USER",p.get_PessoaUsuario());
							historico.append("TICKET_HISTORICO_TIPO",detalhes.getString("tipo"));
							historico.append("TICKET_HISTORICO_DESC",detalhes.getString("desc"));
							//System.out.println(historico.toJson());
							mongo.InserirSimples("TICKET_HISTORICO", historico);
						}
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("OK");
						out.close();	
					}else if(opt.equals("50")) {
						param1=req.getParameter("ticketnum");
						//System.out.println("buscando historico de "+param1);
						Bson filtro;
						List<Bson> filtros = new ArrayList<>();
						filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						filtro = Filters.eq("TICKET_NUM",param1);
						filtros.add(filtro);
						Document historico;
						dados_tabela="";
						FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("TICKET_HISTORICO", filtros);
						MongoCursor<Document> resultado = findIterable.iterator();
						if(resultado.hasNext()) {
							while(resultado.hasNext()) {
								historico=resultado.next();
								if(historico.get("TICKET_HISTORICO_TIPO")!=null) {
									if(historico.getString("TICKET_HISTORICO_TIPO").equals("RegistroManualEventos")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+" <br>Categorias: "+ historico.getString("TICKET_HISTORICO_ATIVIDADE")+" | "+historico.getString("TICKET_HISTORICO_GAP") +" | "+historico.getString("TICKET_HISTORICO_OWNER")+" <br>Registro: "+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("ItemMovidoManualmente")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("MudancaResponsavel")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("ReplanejamentoDatas")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("AgendamentoQC")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("SiteSign")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("SiteVerify")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("EHS_OK")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("MOS_Date")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("Site_OK")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}else if(historico.getString("TICKET_HISTORICO_TIPO").equals("WH_OK")) {
										dados_tabela=dados_tabela+"<li><label style='color:gray;'>Data do Evento:"+ f3.format(historico.getDate("TICKET_HISTORICO_DATE"))+"</label><br> Registro: <label style='color:green;'>"+historico.getString("TICKET_HISTORICO_DESC")+"</label><br><label style='font-size:10px;color:#D3D3D3;float:right'>Adicionado em : "+f3.format(historico.getDate("TICKET_HISTORICO_ADD_DATE"))+" por "+historico.getString("TICKET_HISTORICO_ADD_USER")+"</label></li>";
									}
								}else {
									dados_tabela=dados_tabela+"<li><label style='color:gray;'>Sem Histórico de Eventos</label></li>";
								}
							}
						}
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}else if(opt.equals("51")) {
						Bson filtro;
						List<Bson> filtros = new ArrayList<>();
						filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						List<String>projeto = mongo.ConsultaSimplesDistinct("TICKETS", "PROJETO", filtros);
						projeto.add(0, "Todos os Projetos");
						JSONArray aux = new JSONArray(projeto.toArray());
						
						//System.out.println(aux.toString());
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(aux.toString());
						out.close();
					}else if(opt.equals("52")) {
						rs=mysql.Consulta("select id_usuario,nome from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' and ATIVO='Y'");
						dados_tabela="";
						dados_tabela="[";
						if(rs.next()) {
							
							rs.beforeFirst();
							dados_tabela=dados_tabela+"{\"usuario\":\"ALL\",";
							dados_tabela=dados_tabela+"\"nome\":\"Todos os Funcionários\"},\n";
							while(rs.next()) {
								dados_tabela=dados_tabela+"{\"usuario\":\""+rs.getString("id_usuario")+"\",";
								dados_tabela=dados_tabela+"\"nome\":\""+rs.getString("nome")+"\"},\n";
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
					}else if(opt.equals("53")) {
						param1=req.getParameter("mudancas");
						JSONObject mudancas = new JSONObject(param1);
						Document update = new Document();
						Document comando = new Document();
						Bson filtro;
						filtro = Filters.and(Filters.eq("Empresa", p.getEmpresa().getEmpresa_id()),Filters.eq("TICKET_NUM", mudancas.getString("ticket")));
						if(!mudancas.isNull("name")) {
							update.append("RECURSO", mudancas.getString("name"));
						}
						if(!mudancas.isNull("from")) {
							update.append("INICIO_BL_STR", mudancas.getString("from"));
							update.append("FIM_BL_STR", mudancas.getString("to"));
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							Date d1=format.parse(mudancas.getString("from"));
							update.append("INICIO_BL_DATE", d1);
							d1=format.parse(mudancas.getString("to"));
							update.append("FIM_BL_DATE", d1);
						}
						comando.append("$set", update);
						
						if(mongo.AtualizaMuitos("TICKETS", filtro, comando)) {
							Semail email = new Semail();
							Document pessoa = p.getPessoaInfo(mudancas.getString("name"), mysql);
							email.enviaEmailSimples(pessoa.getString("email"),"MSTP - Novo Ticket", "Prezado "+pessoa.getString("nome")+",\n\n Informamos que foi atríbuido ao seu usuário o ticket numero: "+mudancas.getString("ticket")+"\n\n Por favor informe diretamente no sistema MSTP o progresso em tempo deste serviço.\n\n\n ESSE É UM EMAIL AUTOMÁTICO . NÃO RESPONDER!");
							
						}
						
					}else if(opt.equals("54")) {
						param1=req.getParameter("ticket");
						param2="";
						param3=req.getParameter("tipo");
						String tipo="";
						Document checklist;
						Document update_filtros = new Document();
						Document update_comando = new Document();
						Document update = new Document();
						Document atividade = new Document();
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date d1;
						List<Document> atividades = new ArrayList<>();
						update_filtros.append("Empresa", p.getEmpresa().getEmpresa_id());
						update_filtros.append("TICKET_NUM", param1);
						
						if(param3.equals("QCAgendamento")) {
							param2=req.getParameter("date");
							tipo="TICKET_QC_DATE";
							atividade.append("nome", tipo);
							atividade.append("tipo", "data");
							atividade.append("valor_STR", req.getParameter("date"));
							d1=format.parse(param2);
							atividade.append("valor_DATE", d1);
							
						}else if(param3.equals("SiteVerify")) {
							tipo="TICKET_SITEVERIFY";
							param2=req.getParameter("date");
							atividade.append("nome", tipo);
							atividade.append("tipo", "data");
							atividade.append("valor_STR", req.getParameter("date"));
							d1=format.parse(param2);
							atividade.append("valor_DATE", d1);
							
						}else if(param3.equals("SiteSign")) {
							tipo="TICKET_SITESIGN";
							param2=req.getParameter("date");
							atividade.append("nome", tipo);
							atividade.append("tipo", "data");
							atividade.append("valor_STR", req.getParameter("date"));
							d1=format.parse(param2);
							atividade.append("valor_DATE", d1);
							
						}else if(param3.equals("EHS_OK")) {
							tipo="TICKET_EHS_OK";
							atividade.append("nome", tipo);
							atividade.append("valor_STR", req.getParameter("valor"));
							atividade.append("tipo", "check");
							
						}else if(param3.equals("MOS_Date")) {
							tipo="TICKET_MOS_DATE";
							param2=req.getParameter("date");
							atividade.append("nome", tipo);
							atividade.append("valor_STR", req.getParameter("date"));
							atividade.append("tipo", "data");
							d1=format.parse(param2);
							atividade.append("valor_DATE", d1);
							
						}else if(param3.equals("WH_OK")) {
							tipo="TICKET_MOS_WH_OK";
							atividade.append("nome", tipo);
							atividade.append("valor_STR", req.getParameter("valor"));
							atividade.append("tipo", "check");
							
						}else if(param3.equals("Site_OK")) {
							tipo="TICKET_MOS_SITE_OK";
							atividade.append("nome", tipo);
							atividade.append("valor_STR", req.getParameter("valor"));
							atividade.append("tipo", "check");
							
						}
						
						atividades.add(atividade);
						Bson filtro;
						List<Bson> filtros = new ArrayList<>();
						filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						filtro = Filters.eq("TICKET_NUM",param1);
						filtros.add(filtro);
						Document historico;
						dados_tabela="";
						FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("TICKET_CHECKLIST", filtros);
						MongoCursor<Document> resultado = findIterable.iterator();
						if(resultado.hasNext()) {
							checklist=resultado.next();
							List<Document> atividadesAux = (List<Document>) checklist.get("Atividades");
							atividadesAux.add(atividade);
							update.append("Atividades", atividadesAux);
							update_comando.append("$set", update);
							mongo.AtualizaMuitos("TICKET_CHECKLIST", update_filtros, update_comando);
						}else {
							update.append("Empresa", p.getEmpresa().getEmpresa_id());
							update.append("TICKET_NUM", param1);
							update.append("Atividades", atividades);
							mongo.InserirSimples("TICKET_CHECKLIST", update);
						}
						
						
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("OK");
						out.close();
					}else if(opt.equals("55")) {
						param1=req.getParameter("ticket");
						Bson filtro;
						Document checklist;
						List<Bson> filtros = new ArrayList<>();
						filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
						filtros.add(filtro);
						filtro = Filters.eq("TICKET_NUM",param1);
						filtros.add(filtro);
						Document historico;
						dados_tabela="[";
						FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("TICKET_CHECKLIST", filtros);
						MongoCursor<Document> resultado = findIterable.iterator();
						if(resultado.hasNext()) {
							checklist=resultado.next();
							List<Document> atividadesAux = (List<Document>) checklist.get("Atividades");
							for(int indice=0;indice<atividadesAux.size();indice++) {
								dados_tabela=dados_tabela+"{";
								dados_tabela=dados_tabela+"\"nome\":\""+atividadesAux.get(indice).getString("nome")+"\",";
								dados_tabela=dados_tabela+"\"valor\":\""+atividadesAux.get(indice).getString("valor_STR")+"\"},";
							}
							dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
						}
						dados_tabela=dados_tabela+"]";
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						out.close();
					}
					mongo.fecharConexao();
					Timestamp time2 = new Timestamp(System.currentTimeMillis());
					System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					
				} catch (SQLException e) {
				mongo.fecharConexao();
				mysql.fecharConexao();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
		
		}
    public Boolean criaTicket(Conexao mysql,ConexaoMongo mongo,JSONObject req, Pessoa p) {
    	try {
    		System.out.println("__________________________________");
    		System.out.println("INICIANDO CRIAÇÃO DE TICKET");
    		System.out.println("__________________________________");
    		String param10,param11,param12,param13,param14,param15;
    		//System.out.println(req.toString());
    		String param1=req.getString("po_num");
    		String param2=req.getString("ticket");
    		String param3=req.getString("item");
    		String param4=req.getString("item_code");
    		String param5=req.getString("po_line");
    		String param6=req.getString("published");
    		String param7=req.getString("site");
    		String param8=req.getString("projeto");
    		String param9=req.getString("tags");
			param10=req.getString("recurso");
			param11=req.getString("incio_planejado_bl");
			param12=req.getString("fim_planejado_bl");
			param13=req.getString("todos_itens");
			//param14=(String) req.get("quantidade_itens");
			//param15=(String) req.get("quantidade_pos_restante");
			Document ticket = new Document();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d1=format.parse(param11);
			Calendar dataAtual = Calendar.getInstance();
			ticket.append("Empresa", p.getEmpresa().getEmpresa_id());
			ticket.append("dt_aberto", dataAtual.getTime());
			ticket.append("usuario_registro", p.get_PessoaUsuario());
			ticket.append("Status", "Aberto");
			ticket.append("PO_NUM", param1);
			ticket.append("TICKET_TODOS_ITENS",param13);
			ticket.append("TICKET_STATE","NEW");
			ticket.append("TICKET_NUM", param2);
			ticket.append("INICIO_BL_STR", param11);
			ticket.append("INICIO_BL_DATE", d1);
			ticket.append("FIM_BL_STR", param12);
			d1=format.parse(param12);
			ticket.append("FIM_BL_DATE", d1);
			ticket.append("ITEM", param3);
			ticket.append("ITEM_CODE", param4);
			ticket.append("PO_LINE", param5);
			ticket.append("PUBLISHED", param6);
			ticket.append("QTDE_TICKET", req.get("quantidade_itens"));
			ticket.append("SITE", param7);
			ticket.append("PROJETO", param8);
			ticket.append("TAGS", param9);
			ticket.append("RECURSO", param10);
			mongo.InserirSimples("TICKETS", ticket);
			
			Document update_filtros = new Document();
			Document update_comando = new Document();
			Document update = new Document();
			update_filtros.append("Empresa", p.getEmpresa().getEmpresa_id());
			update_filtros.append("PO NO", param1);
			update_filtros.append("Item Code", param4);
			update_filtros.append("PO Line NO", param5);
			
				if(!param6.equals("null")) {
					update_filtros.append("Publish Date", param6);
				}
			
			//System.out.println("_______________________________________________");
			//System.out.println("Filtros de atualizacao de PO: ");
			//System.out.println(update_filtros.toJson());
			//System.out.println("_______________________________________________");
			Bson filtro;
			Document po;
			String tickets="";
			List<Bson> filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("PO NO",param1);
			filtros.add(filtro);
			filtro=Filters.eq("Item Code",param4);
			filtros.add(filtro);
			filtro=Filters.eq("PO Line NO",param5);
			filtros.add(filtro);
			filtro=Filters.eq("Publish Date",param6);
			filtros.add(filtro);
			FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("PO", filtros);
			MongoCursor<Document> resultado =findIterable.iterator();
			if(resultado.hasNext()) {
				
				po=resultado.next();
				if(po.get("Tickets")!=null) {
					tickets=po.getString("Tickets");
				}
			}
			if(tickets.equals("")) {
				update.append("Tickets", tickets.concat(param2));
			}else {
				update.append("Tickets", tickets.concat(","+param2));
			}
			update.append("Requested Qty", req.get("quantidade_pos_restante"));
			update_comando.append("$set", update);
			
			//System.out.println("Atualizacao de PO: ");
			//System.out.println(update.toJson());
			//System.out.println("_______________________________________________");
			mongo.AtualizaUm("PO", update_filtros, update_comando);
			Document recurso=p.getPessoaInfo(param10, mysql);
			Semail email= new Semail();
			Timestamp time = new Timestamp(System.currentTimeMillis());
			email.enviaEmailSimples(recurso.getString("email"), "MSTP - Notificação de Ticket - "+param2,"Prezado "+recurso.getString("nome")+", \n \n Informamos que voce possui as seguintes tarefas não iniciadas: \n\n Projeto: "+param8+"\nSite: "+param7+" \n Item: "+param3+" \nQuantidade: "+req.get("quantidade_itens")+" \nPlanejamento: "+param11+" - "+param12+" \n\n  Operação realizada em: "+time+" \n \n Caso não seja o responsável correto  solicitamos que entre em contato com o administrador do sistema para sua empresa! \n \n \n Esse é um email Automático gerado pelo sistema. Favor não responder!");
			
			mongo.fecharConexao();
			System.out.println("__________________________________");
    		System.out.println("TICKET CRIADO COM SUCESSO!");
    		System.out.println("__________________________________");
    		return true;
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		return false;
    	}
    }
    public void enviaMensagemPorUsuario(String usuario,String mensagem) {
    	try {
			   String jsonResponse;
			  // System.out.println("controle1");
			   URL url = new URL("https://onesignal.com/api/v1/notifications");
			   HttpURLConnection con = (HttpURLConnection)url.openConnection();
			   con.setUseCaches(false);
			   con.setDoOutput(true);
			   con.setDoInput(true);
			   System.out.println("Paramentros da função de envio de mensagem:");
			   System.out.println(usuario);
			   System.out.println(mensagem);
			   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			   con.setRequestProperty("Authorization", "Basic ZTFhZmE3YTItMDczNC00OWM3LTk0ZTgtMzUzYjg5OTY1ZGFj");
			   con.setRequestMethod("POST");
			  // System.out.println("controle3");
			   String strJsonBody = "{"
			                      +   "\"app_id\": \"ae9ad50e-520d-436a-b0b0-23aaddedee7b\","
			                      +   "\"filters\": [{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+usuario+"\"},{\"operator\": \"OR\"},{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+usuario.toLowerCase()+"\"},{\"operator\": \"OR\"}, {\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+usuario.toUpperCase()+"\"}],"
			                      +   "\"data\": {\"foo\": \"bar\"},"
			                      +   "\"contents\": {\"en\": \""+mensagem+"\"}"
			                      + "}";
			         
			   
			   System.out.println("strJsonBody:\n" + strJsonBody);

			   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			   con.setFixedLengthStreamingMode(sendBytes.length);
			  // System.out.println("controle4");
			   OutputStream outputStream = con.getOutputStream();
			   outputStream.write(sendBytes);

			   int httpResponse = con.getResponseCode();
			   System.out.println("httpResponse: " + httpResponse);

			   if (  httpResponse >= HttpURLConnection.HTTP_OK
			      && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
			      Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   else {
			      Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			      jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			      scanner.close();
			   }
			   System.out.println("jsonResponse:\n" + jsonResponse);
			   
			} catch(Throwable t) {
			   t.printStackTrace();
			}
    }
}
