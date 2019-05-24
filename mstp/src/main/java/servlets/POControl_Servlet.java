package servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;


import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;
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
		Conexao conn = (Conexao) session.getAttribute("conexao");
		ConexaoMongo c = new ConexaoMongo();
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
			p.set_PessoaPerfil_campos("po_table");
			query="Select "+p.get_PessoaPerfil_campos()+" from po_table order by po_id desc";
			//System.out.println(query);
			rs=conn.Consulta(query);
			dados_tabela="<table id=\"tabela_de_po\"  data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\"  data-search=\"true\" data-advanced-search=\"true\">" +"\n";
			dados_tabela=dados_tabela + "<thead>"+"\n";
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
				if(rs.next()){
					rs.beforeFirst();
					if(p.get_PessoaPerfil_nome().equals("Financeiro")){
						while(rs.next()){
							money = Double.parseDouble(rs.getString("total_po"));
							moneyString = number_formatter.format(money);
							dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\">"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("po_number")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("dt_emitida")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("validada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getDate("dt_validada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString("empresa_emissora")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getDate("dt_registrada")+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+moneyString+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>em construçao</td>"+"\n";
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
							dados_tabela=dados_tabela + " <td>em construçao</td>"+"\n";
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
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
						conn.Excluir("delete from po_table where po_number='"+po[i]+"'");
						conn.Excluir("delete from po_item_table where po_number='"+po[i]+"'");
					}
				}else{
					//System.out.println("delete from po_table where po_number="+param1);
					conn.Excluir("delete from po_table where po_number='"+param1+"'");
					conn.Excluir("delete from po_item_table where po_number='"+param1+"'");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("3")){
				
				rs=conn.Consulta("Select po_item_table.* from po_item_table,po_table where  po_table.validada='Y' and po_table.po_number=po_item_table.po_number order by po_item_id");
				if(rs.next()){
					
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
							dados_tabela=dados_tabela + " <td><input type='text' id='"+rs.getInt(1)+"' value='"+rs.getString("po_item_site")+"'></td>"+"\n";
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
					dados_tabela=dados_tabela + "</tbody>";
					dados_tabela=dados_tabela + "</table>";
					//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
					//System.out.println("Resposta Consulta 3 Enviada!");
				}else{
					//System.out.println("Consulta returns empty");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
				
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
						
						conn.Alterar("update po_table set validada='Y',dt_validada='"+time+"' where po_number='"+po[i]+"'");
					}
				}else{
					
					conn.Alterar("update po_table set validada='Y',dt_validada='"+time+"' where po_number='"+param1+"'");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("5")){
				
                //System.out.println("Carregando PO de Subcon");
				rs=conn.Consulta("Select * from po_table_subcon order by po_id desc");
	    		
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
						//System.out.println("Resposta Consulta 3 Enviada!");
					}else{
						//System.out.println("Consulta returns empty");
					}
					Timestamp time2 = new Timestamp(System.currentTimeMillis());
					System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
				}else if(opt.equals("6")){
					
	                //System.out.println("Carregando tabela de Projetos");
					rs=conn.Consulta("Select * from project_table order by project_id desc");
		    		
						if(rs.next()){
							
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
							dados_tabela=dados_tabela + "</tbody>";
							dados_tabela=dados_tabela + "</table>";
							//System.out.println(dados_tabela);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(dados_tabela);
							//System.out.println("Resposta Consulta 3 Enviada!");
						}else{
							//System.out.println("Consulta returns empty");
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("7")){
						
		                System.out.println("Cadastrando Projeto...");
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
		    			
		    			insere="INSERT INTO project_table (project_name,project_pm,project_begin_date,project_end_date,project_creator,project_created_date,project_status,project_customer_budget,project_customer_code,project_customer_name,project_customer_pm,customer_id,customer_name) "
		    					                + "VALUES ('"+param1+"','"+param5+"','"+param8.substring(0, 10)+"','"+param9.substring(0, 10)+"','"+session.getAttribute("user")+"','"+time+"','Enviado','"+param7+"','"+param3+"','"+param1+"','"+param5+"','"+param2+"','"+param2+"')";
		    			if(conn.Inserir_simples(insere)){
				    		System.out.println("Projeto Cadastrado");
				    		rs=conn.Consulta("Select project_id from project_table order by project_id desc limit 1");
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
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("8")){
							
			                System.out.println("Carregando tabela de Clientes");
							rs=conn.Consulta("Select * from customer_table order by customer_id desc");
				    		
								if(rs.next()){
									
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
									dados_tabela=dados_tabela + "</tbody>";
									dados_tabela=dados_tabela + "</table>";
									//System.out.println(dados_tabela);
									resp.setContentType("application/html");  
									resp.setCharacterEncoding("UTF-8"); 
									PrintWriter out = resp.getWriter();
									out.print(dados_tabela);
									//System.out.println("Resposta Consulta 3 Enviada!");
								}else{
									//System.out.println("Consulta returns empty");
								}
								Timestamp time2 = new Timestamp(System.currentTimeMillis());
								System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}else if(opt.equals("9")){
							System.out.println("Cadastrando Cliente...");
			                param1=req.getParameter("nomeCompleto");
			    			param2=req.getParameter("nome");
			    			param3=req.getParameter("cnpj");
			    			param4=req.getParameter("inscEstadual");
			    			param5=req.getParameter("endereco");
			    			param6=req.getParameter("contato");
			    			
			    			 time = new Timestamp(System.currentTimeMillis());
			    			
			    			insere="";
			    			
			    			insere="INSERT INTO customer_table (customer_name,customer_fullname,customer_cnpj,customer_adress,customer_ie,customer_owner_creator,customer_created_date) "
			    					                + "VALUES ('"+param2+"','"+param1+"','"+param3+"','"+param5+"','"+param4+"','"+p.get_PessoaUsuario()+"','"+time+"')";
			    			if(conn.Inserir_simples(insere)){
					    		System.out.println("Cliente Cadastrado");
					    		rs=conn.Consulta("Select customer_id from customer_table order by customer_id desc limit 1");
					    		if(rs.next()){
					    			last_id=rs.getInt(1);
					    		}
			    			}
			    			//System.out.println(param1+"\n"+param2+"\n"+param3+"\n"+param4+"\n"+param5+"\n"+param6+"\n"+param7+"\n"+param8+"\n"+param9);
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print(last_id);
							Timestamp time2 = new Timestamp(System.currentTimeMillis());
							System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
							
						}else if(opt.equals("10")){
						param1=req.getParameter("cliente");	
						rs= conn.Consulta("select * from project_table where customer_id='"+param1+"'");
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
			    		Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
							
						}else if(opt.equals("11")){
							
							rs= conn.Consulta("select * from customer_table");
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
			    		Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
							
						}else if(opt.equals("12")){
							System.out.println("função migrada para o rollout servlet opt 12");
						}else if(opt.equals("13")){
							System.out.println("Função Migrada para servlet de rollout opt 13");
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
		    			rs=conn.Consulta("select * from rollout_campos where field_id="+param9+" and empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param8+"'");
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
		    				c.AtualizaMuitos("rollout", filtro, rename_comand);
		    				conn.Alterar("update rollout_campos set field_name='"+param1+"',field_type='"+param2+"',tipo='"+param4+"',Atributo_parametro='"+param5+"',trigger_pagamento="+param6+",percent_pagamento='"+param7+"' where field_id="+rs.getInt(1));
		    				last_id=rs.getInt(1);
		    				
		    				retorno="Campo editado com Sucesso. ID do campo: "+last_id;
		    			}else{
		    			rs=conn.Consulta("select ordenacao from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param8+"' order by ordenacao desc limit 1");	
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
		    			
		    			if(conn.Inserir_simples(insere)){
				    		//System.out.println("CAMPO Cadastrado");
				    		rs=conn.Consulta("Select field_id from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param8+"' order by field_id desc limit 1");
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
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
		    				conn.Alterar(query);
		    				//query="update rollout set ordenacao="+campos.getJSONObject(i).getInt("posicao")+" where milestone='"+campos.getJSONObject(i).getString("campo_nome")+"' and empresa="+p.getEmpresa().getEmpresa_id();
		    				//conn.Alterar(query);
		    				i++;
		    			}
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Ordem de campos atualizada!");
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						}
					}else if(opt.equals("16")){
						//param1=req.getParameter("mensagem");
						param1="teste fora do app";
						//param2=req.getParameter("grupo");
						//param3=req.getParameter("user_target");
						 try {
							   String jsonResponse;
							  // System.out.println("controle1");
							   URL url = new URL("https://onesignal.com/api/v1/notifications");
							   HttpURLConnection con = (HttpURLConnection)url.openConnection();
							   con.setUseCaches(false);
							   con.setDoOutput(true);
							   con.setDoInput(true);
							  // System.out.println("controle2");
							   con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
							   con.setRequestProperty("Authorization", "Basic ZTFhZmE3YTItMDczNC00OWM3LTk0ZTgtMzUzYjg5OTY1ZGFj");
							   con.setRequestMethod("POST");
							  // System.out.println("controle3");
							   String strJsonBody = "{"
							                      +   "\"app_id\": \"ae9ad50e-520d-436a-b0b0-23aaddedee7b\","
							                      +   "\"filters\": [{\"field\": \"tag\", \"key\": \"User\", \"relation\": \"=\", \"value\": \""+p.get_PessoaUsuario()+"\"}],"
							                      +   "\"included_segments\": [\"All\"],"
							                      +   "\"data\": {\"foo\": \"bar\"},"
							                      +   "\"contents\": {\"en\": \""+param1+"\"}"
							                      + "}";
							         
							   
							   //System.out.println("strJsonBody:\n" + strJsonBody);

							   byte[] sendBytes = strJsonBody.getBytes("UTF-8");
							   con.setFixedLengthStreamingMode(sendBytes.length);
							  // System.out.println("controle4");
							   OutputStream outputStream = con.getOutputStream();
							   outputStream.write(sendBytes);

							   int httpResponse = con.getResponseCode();
							  // System.out.println("httpResponse: " + httpResponse);

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
							   //System.out.println("jsonResponse:\n" + jsonResponse);
							   
							} catch(Throwable t) {
							   t.printStackTrace();
							}
						 Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
							
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
								rs=conn.Consulta("Select field_name from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								if(rs.next()) {
									update=new Document();
									update.append(rs.getString("field_name"), "");
									comando_update.append("$unset", update);
									c.AtualizaMuitos("rollout", condicao, comando_update);
								}
								conn.Excluir("delete from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								
								
								if(rs.next()){
								conn.Excluir("delete from rollout where milestone='"+rs.getString(1)+"' and empresa="+p.getEmpresa().getEmpresa_id());
								}
							}
						}else{
							//System.out.println("delete from po_table where po_number="+param1);
							rs=conn.Consulta("Select field_name from rollout_campos where field_id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());
							conn.Excluir("delete from rollout_campos where field_id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());
							if(rs.next()){
							conn.Excluir("delete from rollout where milestone='"+rs.getString(1)+"' and empresa="+p.getEmpresa().getEmpresa_id());
							update=new Document();
							update.append(rs.getString("field_name"), "");
							comando_update.append("$unset", update);
							c.AtualizaMuitos("rollout", condicao, comando_update);
							}
						}
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Campos Removidos");
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("18")){
						if(p.get_PessoaPerfil_nome().equals("tecnico")) {
							query="Select usuarios.* from usuarios where  usuarios.ativo='Y' and usuarios.id_usuario='"+p.get_PessoaUsuario()+"' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
						}else {
							query="Select usuarios.* from usuarios where  usuarios.ativo='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
						}
						rs=conn.Consulta(query);
						if(rs.next()){
							
							dados_tabela="<table id=\"tabela_usuario\" data-use-row-attr-func=\"true\" data-toolbar=\"#toolbar_tabela_usuario\" data-reorderable-rows=\"true\" data-show-refresh=\"true\" data-toggle=\"table\"  data-filter-control=\"true\" data-click-to-select=\"true\" data-pagination=\"true\" data-page-size=\"10\" data-search=\"true\">" +"\n";
							dados_tabela=dados_tabela + "<thead>"+"\n";
							dados_tabela=dados_tabela +"<tr>"+"\n";
							dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"user\" data-filter-control=\"select\">Usuario</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"user_name\" data-filter-control=\"select\">Nome</th>"+"\n";
							dados_tabela=dados_tabela +" <th data-field=\"user_email\">Email</th>"+"\n";
							dados_tabela=dados_tabela +" <th style data-field=\"user_profile\">Perfil</th>"+"\n";
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
							
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
						
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
			    		        
			    		        rs= conn.Consulta("select * from usuarios where VALIDADO='Y' and ATIVO='Y' and HASH='"+retornaSenha+"' and (id_usuario='"+session.getAttribute("user")+"' or email='"+session.getAttribute("user")+"')");
			    		        if(rs.next()){
			    		        	md.update( param2.getBytes());     
				    		        hash = new BigInteger(1,md.digest() );     
				    		        senhaprovisoria = hash.toString(16);
			    		        	if(conn.Alterar("update usuarios set HASH='"+senhaprovisoria+"' where id_usuario='"+rs.getString("id_usuario")+"'")) {
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
				    		Timestamp time2 = new Timestamp(System.currentTimeMillis());
							System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		    		}else if(opt.equals("21")){
		    			String charts="";
		    			System.out.println("Carregando Portal...");
		    			query="select user_portal from portal_user where id_usuario='"+session.getAttribute("user")+"'";
		    			rs=conn.Consulta(query);
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
				    	Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		    		}else if(opt.equals("22")){
		    			
		    		//System.out.println("Salvando Portal...");
		    			param1=req.getParameter("portal");
		    			query="select user_portal from portal_user where id_usuario='"+session.getAttribute("user")+"'";
		    			rs=conn.Consulta(query);
		    			if(rs.next()){
		    				query="update portal_user set user_portal='"+param1+"' where id_usuario='"+session.getAttribute("user")+"'";
			    			conn.Alterar(query);
		    			}else{
		    				query="insert into portal_user (id_usuario,user_portal) values('"+session.getAttribute("user")+"','"+param1+"')";
		    				conn.Inserir_simples(query);
		    			}
		    			Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		    			
		    		}else if(opt.equals("23")){
		    			
		    			
		    			param1=req.getParameter("rolloutid");
		    			//System.out.println("Buscando campos do rollout ...");
		    			//param1=req.getParameter("portal");
		    			query="select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+param1+"' order by ordenacao";
		    			rs=conn.Consulta(query);
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
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		    		}else if(opt.equals("24")){
		    			System.out.println("funcao migrada para rollout servlet opt 24");
		    		}else if(opt.equals("99")){
		    			//System.out.println("saindo do Servlet");
		    			conn.fecharConexao();
		    			session.removeAttribute("user");
		    			session.invalidate();
		    			Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		    			
		    		}else if(opt.equals("25")){
		    			//System.out.println("saindo do Servlet");
		    			param1=req.getParameter("linha");
		    			if(param1.indexOf(",")>0){
		    				String []linhas=param1.split(",");
		    				for(int j=0;j<linhas.length;j++){
		    					conn.Alterar("update rollout set linha_ativa='N',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+linhas[j]+" and empresa="+p.getEmpresa().getEmpresa_id());
		    				}
		    			
		    			}else{
		    				if(param1.length()>0){
		    					conn.Alterar("update rollout set linha_ativa='N',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());

		    				}
		    			}
		    			resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("OK");
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
								rs=conn.Consulta("Select field_name,field_type,tipo,atributo_parametro,trigger_pagamento,percent_pagamento,field_id from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								//conn.Excluir("delete from rollout_campos where field_id="+campos[i]+" and empresa="+p.getEmpresa().getEmpresa_id());
								if(rs.next()){
									dados_tabela="{nome_campo:\""+rs.getString(1)+"\",tipo_Campo:\""+rs.getString(2)+"\",sub_tipo:\""+rs.getString(3)+"\",atributo:\""+rs.getString(4)+"\",\"trigger\":"+rs.getString(5)+",\"percent\":\""+rs.getString(6)+"\",\"id_campo\":"+rs.getInt("field_id")+"}";
								}
							}
						}else{
							//System.out.println("delete from po_table where po_number="+param1);
							rs=conn.Consulta("Select field_name,field_type,tipo,atributo_parametro,trigger_pagamento,percent_pagamento,field_id from rollout_campos where field_id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());
							
							if(rs.next()){
								dados_tabela="{\"nome_campo\":\""+rs.getString(1)+"\",\"tipo_Campo\":\""+rs.getString(2)+"\",\"sub_tipo\":\""+rs.getString(3)+"\",\"atributo\":\""+rs.getString(4)+"\",\"trigger\":"+rs.getString(5)+",\"percent\":\""+rs.getString(6)+"\",\"id_campo\":"+rs.getInt("field_id")+"}";
							}
						}
						System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("27")){
						//System.out.println("carregando ajuda....");
						//param1=req.getParameter("campos");
						//System.out.println(param1);
						dados_tabela="";
						query="select * from ajuda_videos order by video_id desc";
						rs=conn.Consulta(query);
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
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("28")){
						int linha_id=0;
						//System.out.println("linkando po no rollout ....");
						//param1=req.getParameter("campos");
						//System.out.println(param1);
						query="select distinct recid from rollout order by recid desc limit 1";
		    			rs=conn.Consulta(query);
		    			if(rs.next()){
		    				linha_id=rs.getInt(1);
		    			}else{
		    				linha_id=0;
		    			}
		    			linha_id=linha_id+1;
						dados_tabela="";
						query="select po_item_site,po_number,id_projeto from po_item_table where sync_rollout_table='N' group by po_number,po_item_site";
						rs=conn.Consulta(query);
						
						if(rs.next()){
							rs.beforeFirst();
							
								while(rs.next()){
									
									query="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo) values("+linha_id+",'site"+linha_id+"','Site ID','"+rs.getString(1)+"','Atributo')";
									conn.Inserir_simples(query);
									query="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo) values("+linha_id+",'site"+linha_id+"','PO','"+rs.getString(2)+"','Atributo')";
									conn.Inserir_simples(query);
									query="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo) values("+linha_id+",'site"+linha_id+"','Projeto','"+p.get_ProjetoNome(conn,rs.getString(3))+"','Atributo')";
									conn.Inserir_simples(query);
									query="update po_item_table set sync_rollout_table='Y',id_rollout='site"+linha_id+"' where po_item_site='"+rs.getString(1)+"' and po_number='"+rs.getString(2)+"'";
									conn.Alterar(query);
									linha_id=linha_id+1;
								}
							
						}
						query="select * from po_item_table where sync_faturamento_table='N'";
						rs=conn.Consulta(query);
						if(rs.next()){
							rs.beforeFirst();
							while(rs.next()){
								query="insert into faturamento (site_id,projeto,cliente,po,po_item,parcela_pagamento,status_faturamento,valor_item,id_rollout) values('"+rs.getString("po_item_site")+"','"+rs.getString("id_projeto")+"','"+rs.getString("id_cliente")+"','"+rs.getString("po_number")+"','"+rs.getString("po_item_desc")+"','1 de 1','Aguardando Execução','"+rs.getString("po_item_valor")+"','"+rs.getString("id_rollout")+"')";
								conn.Inserir_simples(query);
								query="update po_item_table set sync_faturamento_table='Y' where po_item_site='"+rs.getString("po_item_site")+"' and po_number='"+rs.getString("po_number")+"' and po_item_desc='"+rs.getString("po_item_desc")+"'";
								conn.Alterar(query);
							}
						}
						//System.out.println(dados_tabela);
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Dados Sincronizados!");
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
		    			rs=conn.Consulta(query);
		    			
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
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("30")){
						
						//System.out.println("Carregando Milestones para o faturamento ....");
						dados_tabela="";
						query="select * from rollout_campos where field_type='Milestone'";
		    			rs=conn.Consulta(query);
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
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
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
		    				
		    				rs=conn.Consulta("select * from faturamento where id_faturamento="+fluxo.getJSONObject(i).getInt("id"));
		    				if(rs.next()){
		    					j=0;
		    					while(j<aux_id_qtd){
		    					if(j==0){
		    						money = Double.parseDouble(rs.getString("valor_item"));
			    					money=money*(Double.parseDouble(fluxo.getJSONObject(i).getString("percentual"))/100);
		    						insere="update faturamento set parcela_pagamento='1 de "+aux_id_qtd+"',milestones_trigger='"+fluxo.getJSONObject(i).getString("milestone")+"',percent='"+fluxo.getJSONObject(i).getString("percentual")+"',valor_parcela='"+money+"' where id_faturamento="+fluxo.getJSONObject(i).getInt("id");
			    					
		    						if(conn.Alterar(insere)){
		    							System.out.println("Atualizado com sucesso: item id:"+fluxo.getJSONObject(i).getInt("id"));
		    						}
		    						j++;
			    					i++;
		    					}else{
		    					money = Double.parseDouble(rs.getString("valor_item"));
		    					money=money*(Double.parseDouble(fluxo.getJSONObject(i).getString("percentual"))/100);
		    					insere="insert into faturamento (site_id,projeto,cliente,po,po_item,parcela_pagamento,status_faturamento,valor_parcela,valor_item,id_rollout,milestones_trigger,percent,id_item_relacionado) values('"+rs.getString("site_id")+"','"+rs.getString("projeto")+"','"+rs.getString("cliente")+"','"+rs.getString("po")+"','"+rs.getString("po_item")+"','"+(j+1)+" de "+aux_id_qtd+"','Aguardando Execução','"+money+"','"+rs.getString("valor_item")+"','"+rs.getString("id_rollout")+"','"+fluxo.getJSONObject(i).getString("milestone")+"','"+fluxo.getJSONObject(i).getString("percentual")+"','"+fluxo.getJSONObject(i).getInt("id")+"')";
		    					
		    					if(conn.Inserir_simples(insere)){
		    						System.out.println("Inserido novos itens de pg com sucesso item id original:"+fluxo.getJSONObject(i).getInt("id"));
		    					}
		    					j++;
		    					i++;
		    					}
		    					}
		    				}
		    				//System.out.println(fluxo.getJSONObject(i).getString("milestone"));
		    			}
		    			Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("32")){
						//System.out.println("Sincronizando tabela de faturamento...");
						query="select * from faturamento where milestones_trigger<>'0' and status_faturamento='Aguardando Execução'";
						rs=conn.Consulta(query);
						if(rs.next()){
							rs.beforeFirst();
							while(rs.next()){
								query="select * from rollout where siteID='"+rs.getString("id_rollout")+"' and milestone='"+rs.getString("milestones_trigger")+"'";
								System.out.println(query);
								rs2=conn.Consulta(query);
								if(rs2.next()){
									rs2.beforeFirst();
									while(rs2.next()){
										if(rs2.getString("dt_fim").equals("01/01/1800")){
											
										}else{
											
											query="update faturamento set milestone_data_fim_real='"+rs2.getString("dt_fim")+"',status_faturamento='Liberado para Faturar' where id_rollout='"+rs.getString("id_rollout")+"'";
											conn.Alterar(query);
										}
									}
								}
							}
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("33")){
						query="";
						query="select * from perfil_funcoes where usuario_id='"+p.get_PessoaUsuario()+"' and ativo='Y'";
						rs=conn.Consulta(query);
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
						}else {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("[\"perfil nao definido\"]");
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("34")){
						param1=req.getParameter("titulo");
						param2=req.getParameter("mensagem");
						int id_message=0;
						query="select id_message from updates_message order by id_message desc limit 1";
						rs=conn.Consulta(query);
						if(rs.next()) {
							id_message=rs.getInt(1)+1;
						}
						query="select id_usuario from usuarios where validado='Y' and ativo='Y'";
						rs=conn.Consulta(query);
						if(rs.next()) {
							rs.beforeFirst();
							while(rs.next()) {
								conn.Inserir_simples("insert into updates_message (id_message,usuario,titulo,message,messagem_lida,dt_message) values("+id_message+",'"+rs.getString(1)+"','"+param1+"','"+param2+"','N','"+time+"')");
							}
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("35")){
						query="update empresas set modulo='Módulo de controle Usuarios',assinado='Y',dt_termo_aceite='"+time+"',valor='R$326,00' where id_empresa="+p.getEmpresa().getEmpresa_id();
						if(conn.Update_simples(query)) {
							
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("36")){
						query="update empresas set modulo='Módulo de controle Usuarios',assinado='Cancelado',dt_termo_aceite='"+time+"',valor='R$326,00' where id_empresa="+p.getEmpresa().getEmpresa_id();
						if(conn.Update_simples(query)) {
							
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("37")){
						query="select * from modulo_mstp where empresa="+p.getEmpresa().getEmpresa_id();
						rs=conn.Consulta(query);
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
							}else {
								
								dados_tabela=dados_tabela + "</tbody>";
								dados_tabela=dados_tabela + "</table>";
								resp.setContentType("application/text")  ;
								resp.setCharacterEncoding("UTF-8"); 
								PrintWriter out = resp.getWriter();
								out.print(dados_tabela);
							}
							Timestamp time2 = new Timestamp(System.currentTimeMillis());
							System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("38")) {
						int contador=0;
						String progress_bar="";
						if(p.get_PessoaUsuario().toUpperCase().contains("MASTERADMIN")) {
							query="select *,now() from arquivos_importados order by id_sys_arq desc";
						}else {
							query="select *,now() from arquivos_importados where arq_usuario='"+p.get_PessoaUsuario()+"' order by id_sys_arq desc";
						}
						rs=conn.Consulta(query);
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
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("39")) {
						if(conn.Alterar("update modulo_mstp set termo_aceite='Y',dt_termo_aceite='"+time+"' where empresa="+p.getEmpresa().getEmpresa_id())) {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Termo aceite com Sucesso");
						}else {
							resp.setContentType("application/json");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Erro na aceitação do Termo");
						}
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}else if(opt.equals("40")) {
						param1=req.getParameter("idarq");
						//System.out.println("Carregando foto de perfil de "+p.get_PessoaUsuario());
						ServletOutputStream out = resp.getOutputStream();
						query="";
						query="select * from arquivos_importados where id_sys_arq="+param1;
						rs=conn.Consulta(query);
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
						Timestamp time2 = new Timestamp(System.currentTimeMillis());
						System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Operações Gerais opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
					}
				
				} catch (SQLException e) {
			
				conn.fecharConexao();
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
				}
    		
		
		}
    
}
