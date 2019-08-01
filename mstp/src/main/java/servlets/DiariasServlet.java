package servlets;

import java.awt.Color;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.VerticalAlignment;
import be.quodlibet.boxable.line.LineStyle;
import classes.Conexao;
import classes.ConexaoMongo;
import classes.Despesa;
import classes.EspelhoDiaria;
import classes.Pessoa;
import classes.Semail;

/**
 * Servlet implementation class DiariasServlet
 */
@WebServlet("/DiariasServlet")
public class DiariasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String padrao_data_br = "dd/MM/yyyy";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiariasServlet() {
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
		gerenciamento_diarias(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamento_diarias(request, response);
	}
	public void gerenciamento_diarias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EspelhoDiaria espelho= new EspelhoDiaria();
		DateFormat f3 = DateFormat.getDateTimeInstance();
		String opt="";
		String param1="";
		String param2="";
		String param3="";
		String param4="";
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		ConexaoMongo mongo = new ConexaoMongo();
		opt=request.getParameter("opt");
		HttpSession session = request.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null) {
			response.sendRedirect("./mstp_login.html");
			return;
		}
		Conexao mysql = (Conexao) session.getAttribute("conexao");
		ResultSet rs;
		SimpleDateFormat format = new SimpleDateFormat(padrao_data_br);
		NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
		if(opt.equals("1")) {
			try {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				
				param1=request.getParameter("func");
				param2=request.getParameter("inicio");
				param3=request.getParameter("fim");
			
				System.out.println("inicio:"+param2);
				System.out.println("fim:"+param3);
				String dados_tabela="";
				Date dt_inicio=format.parse(param2);
				Date dt_fim=format.parse(param3);
				dados_tabela= dados_tabela+"[";
				Bson filtro;
				Document despesa;
				List<Bson> filtro_list= new ArrayList<>();
				filtro=Filters.eq("Empresa", p.getEmpresa().getEmpresa_id());
				filtro_list.add(filtro);
				filtro=Filters.not(Filters.eq("statusDespesa", "DELETADO"));
				filtro_list.add(filtro);
				if(!param1.trim().equals("")) {
					filtro=Filters.eq("usuarioDespesa", param1);
					filtro_list.add(filtro);
				}
				filtro=Filters.gte("dt_despesa",dt_inicio );
				filtro_list.add(filtro);
				filtro=Filters.lte("dt_despesa", dt_fim);
				filtro_list.add(filtro);
				FindIterable<Document> findIterable = mongo.ConsultaOrdenadaFiltroLista("Despesas", "dt_registro", -1, filtro_list);
				MongoCursor<Document> resultado = findIterable.iterator();
				Integer contador=0;
				if(resultado.hasNext()) {
					dados_tabela=dados_tabela+"{\"totalRecords\":\"replace1\",\n";
					String chaves="";
					while(resultado.hasNext()) {
						despesa=resultado.next();
						contador=contador+1;
						dados_tabela=dados_tabela+chaves+"\"id\":\""+despesa.get("id_despesa").toString()+"\",";
						dados_tabela=dados_tabela+"\"dtdespesa\":\""+despesa.getString("data_despesa")+"\",";
						dados_tabela=dados_tabela+"\"funcdespesa\":\""+despesa.getString("usuarioDespesa")+"\",";
						dados_tabela=dados_tabela+"\"categoria\":\""+despesa.getString("categoria")+"\",";
						dados_tabela=dados_tabela+"\"projeto\":\""+despesa.getString("projetoNomeDespesa")+"\",";
						dados_tabela=dados_tabela+"\"site\":\""+despesa.getString("site_despesa")+"\",";
						dados_tabela=dados_tabela+"\"motivo\":\""+despesa.getString("descricao_despesa")+"\",";
						dados_tabela=dados_tabela+"\"valor\":\""+number_formatter.format(despesa.getDouble("valor_despesa"))+"\",";
						dados_tabela=dados_tabela+"\"statusPagamento\":\""+despesa.getString("status_pagamento")+"\",";
						dados_tabela=dados_tabela+"\"StatusDespesa\":\""+despesa.getString("statusDespesa")+"\",";
						dados_tabela=dados_tabela+"\"comprovante\":\"<a class='icon icon-note' style='color:black' href='#' onclick=visualiza_comprovante('"+despesa.get("id_despesa").toString()+"')></a>\",";
						if(despesa.getString("statusDespesa").equals("EM REVISÃO")) {
							dados_tabela=dados_tabela+"\"operacoes\":\"<a href='#' onclick=despesasStatus('"+despesa.get("id_despesa").toString()+"*EM_ANALISE') style='color:green'>Análisar</a> | <a href='#' onclick=despesasStatus('"+despesa.get("id_despesa").toString()+"*REJEITADO') style='color:red' >Rejeitar</a>\"},\n";
						}else if(despesa.getString("statusDespesa").equals("EM_ANALISE")) {
							dados_tabela=dados_tabela+"\"operacoes\":\"<a href='#' onclick=despesasStatus('"+despesa.get("id_despesa").toString()+"*APROVADA') style='color:green'>Aprovar</a> | <a href='#' onclick=despesasStatus('"+despesa.get("id_despesa").toString()+"*PAGAMENTOOK') style='color:green'>Notificar Pagamento</a> | <a href='#' onclick=despesasStatus('"+despesa.get("id_despesa").toString()+"*REJEITADO') style='color:red' >Rejeitar</a>\"},\n";
						}else if(despesa.getString("statusDespesa").equals("APROVADA") && !despesa.getString("status_pagamento").equals("PAGO")){
							dados_tabela=dados_tabela+"\"operacoes\":\" <a href='#' onclick=despesasStatus('"+despesa.get("id_despesa").toString()+"*PAGAMENTOOK') style='color:green'>Notificar Pagamento</a> | <a href='#' onclick=despesasStatus('"+despesa.get("id_despesa").toString()+"*REJEITADO') style='color:red' >Rejeitar</a>\"},\n";
						}else{
							dados_tabela=dados_tabela+"\"operacoes\":\"Fluxo Encerrado\"},\n";
						}
							chaves="{";
					}
				}else {
					dados_tabela=dados_tabela+"{\"totalRecords\":\"0\"},\n";
				
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
				
    			dados_tabela= dados_tabela+"\n"+"]";
    			dados_tabela=dados_tabela.replace("replace1", contador.toString());
    			//System.out.println(dados_tabela);
    			response.setContentType("application/json");  
	  		    response.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = response.getWriter();
			    out.print(dados_tabela);
					
			    mongo.fecharConexao();
			    Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Despesas|Diarias opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
				
			}catch(Exception e) {
				try {
					System.out.println(e.getMessage());
					System.out.println(e.getCause());
					e.printStackTrace();
					Semail email = new Semail();
					email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com","MSTP WEB - Erro Servlet Diaria", "Erro servlet diaria opt 1\n\n"+e.getMessage()+""+e.getCause());
				} catch (EmailException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}if(opt.equals("2")) {
			Despesa despesa = new Despesa();
			Calendar hoje = Calendar.getInstance();
			despesa.setAno_despesa(hoje.get(Calendar.YEAR));
			despesa.setDia_despesa(hoje.get(Calendar.DAY_OF_MONTH));
			despesa.setMes_despesa(hoje.get(Calendar.MONTH)+1);
			despesa.setValor_despesa(request.getParameter("valor"));
			despesa.setSite_despesa(request.getParameter("valor"));
			despesa.setDescricao_despesa(request.getParameter("desc"));
		}else if(opt.equals("3")) {
			Timestamp time = new Timestamp(System.currentTimeMillis());
			param1=request.getParameter("despesa");
			ServletOutputStream out = response.getOutputStream();
			String query="";
			Blob image = null;
			
			query="select nota from notaImagem where id_despesa='"+param1+"'";
			System.out.println(query);
			rs=mysql.Consulta(query);
			try {
				if(rs.next()) {
					System.out.println(" foto encontrada");
					image=rs.getBlob(1);
					
					byte byteArray[]=image.getBytes(1, (int) image.length());
					response.setContentType("image/png");
					out.write(byteArray);
					out.flush();
					out.close();
					System.out.println(" foto carregada");
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("4")) {
			Document despesafiltro =new Document();
			Document despesaUpdate = new Document();
			Document updatecomando = new Document();
			despesafiltro.append("id_despesa", UUID.fromString(request.getParameter("id")));
			despesaUpdate.append("statusDespesa", request.getParameter("status"));
			updatecomando.append("$set", despesaUpdate);
			mongo.AtualizaUm("Despesas", despesafiltro, updatecomando);
			mongo.fecharConexao();
		}else if(opt.equals("5")) {
			param1=request.getParameter("filtros");
			param2=request.getParameter("func");
			param3=request.getParameter("inicio");
			param4=request.getParameter("fim");
			//System.out.println(param1);
			JSONObject indices = new JSONObject(param1);
			JSONArray indicesArray = indices.getJSONArray("filtros");
			Bson filtro;
			Document despesa;
			Document info_user=p.getPessoaInfo(param2, mysql);
			List<Bson> filtro_list= new ArrayList<>();
			filtro=Filters.eq("Empresa", p.getEmpresa().getEmpresa_id());
			filtro_list.add(filtro);
			
			PDDocument document = new PDDocument();

			// Create a new blank page and add it to the document
			PDPage page = new PDPage();
			document.addPage( page );
			PDFont font = PDType1Font.HELVETICA_BOLD;
			PDFont fontPlain = PDType1Font.HELVETICA;
	        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
	        PDFont fontMono = PDType1Font.COURIER;
	        PDSignature assinatura=new PDSignature();
			// Start a new content stream which will "hold" the to be created content
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			PDRectangle rect = page.getMediaBox();
			float margin = 30;
	        // starting y position is whole page height subtracted by top and bottom margin
	        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
	        // we want table across whole page width (subtracted by left and right margin ofcourse)
	        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
	        boolean drawContent = true;
	        float yStart = yStartNewPage;
	        float bottomMargin = 70;
	        // y position is your coordinate of top left corner of the table
	        float yPosition = 750;
	        int line =0;
	        BaseTable table = new BaseTable(yPosition, yStartNewPage,
	            bottomMargin, tableWidth, margin, document, page, true, drawContent);
	        Row<PDPage> headerRow = table.createRow(50);
	        // the first parameter is the cell width
	        Cell<PDPage> cell = headerRow.createCell(100, "Relatório de Despesas");
	        cell.setFont(fontBold);
	        cell.setFontSize(18);
	        // vertical alignment
	        cell.setValign(VerticalAlignment.MIDDLE);
	        // border style
	        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
	        table.addHeaderRow(headerRow);

	        Row<PDPage> row = table.createRow(20);
	        cell = row.createCell(30, "Funcionário");
	        cell.setFontSize(12);
	        cell = row.createCell(70, info_user.getString("nome"));
	        cell.setFontSize(12);
	        
	        row = table.createRow(20);
	        cell = row.createCell(30, "Período de Despesas");
	        cell.setFontSize(12);
	        cell = row.createCell(70, param3+" - "+param4);
	        cell.setFontSize(12);
	        
	        row = table.createRow(20);
	        cell = row.createCell(30, "Aprovador");
	        cell.setFontSize(12);
	        cell = row.createCell(70, "Masteradmin");
	        cell.setFontSize(12);

	        

	        table.draw();
	        contentStream.setFont( PDType1Font.TIMES_ROMAN, 16 );
	        
	        float tableHeight = table.getHeaderAndDataHeight();
	        System.out.println("tableHeight = "+tableHeight);
	        BaseTable table2 = new BaseTable((yPosition - tableHeight)-50, yStartNewPage,
		            bottomMargin, tableWidth, margin, document, page, true, drawContent);
	        headerRow = table2.createRow(30);
	        cell = headerRow.createCell(20, "Data Despesa");
	        cell.setFont(fontBold);
	        cell.setFontSize(14);
	        // vertical alignment
	        cell.setValign(VerticalAlignment.MIDDLE);
	        // border style
	        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
	        cell = headerRow.createCell(30, "Descrição Despesa");
	        cell.setFont(fontBold);
	        cell.setFontSize(14);
	        // vertical alignment
	        cell.setValign(VerticalAlignment.MIDDLE);
	        // border style
	        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
	        cell = headerRow.createCell(30, "Categoria");
	        cell.setFont(fontBold);
	        cell.setFontSize(14);
	        // vertical alignment
	        cell.setValign(VerticalAlignment.MIDDLE);
	        // border style
	        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
	        cell = headerRow.createCell(20, "Valor Despesa");
	        cell.setFont(fontBold);
	        cell.setFontSize(14);
	        // vertical alignment
	        cell.setValign(VerticalAlignment.MIDDLE);
	        // border style
	        cell.setTopBorderStyle(new LineStyle(Color.BLACK, 10));
	        table2.addHeaderRow(headerRow);
	        // close the content stream 
	        FindIterable<Document> findIterable ;
			MongoCursor<Document> resultado ;
			Double valorTotal=0.0;
	        for(int indicador=0;indicador<indicesArray.length();indicador++) {
	        	filtro=Filters.eq("id_despesa", UUID.fromString(indicesArray.getString(indicador)));
				filtro_list.add(filtro);
				findIterable = mongo.ConsultaOrdenadaFiltroLista("Despesas", "dt_registro", -1, filtro_list);
				resultado = findIterable.iterator();
				if(resultado.hasNext()) {
					while(resultado.hasNext()) {
						despesa=resultado.next();
						row = table2.createRow(20);
				        cell = row.createCell(20, f2.format(despesa.getDate("dt_despesa")));
				        cell.setFontSize(12);
				        cell = row.createCell(30, despesa.getString("descricao_despesa"));
				        cell.setFontSize(12);
				        cell = row.createCell(30, despesa.getString("categoria"));
				        cell.setFontSize(12);
				        cell = row.createCell(20, number_formatter.format(despesa.getDouble("valor_despesa")));
				        cell.setFontSize(12);
				        valorTotal=valorTotal+despesa.getDouble("valor_despesa");
					}
				}
				filtro_list.remove(filtro_list.size()-1);
	        }
	        row = table2.createRow(20);
	        cell = row.createCell(20, "");
	        cell.setFontSize(12);
	        cell = row.createCell(30, "");
	        cell.setFontSize(12);
	        cell = row.createCell(30, "Valor Total:");
	        cell.setAlign(HorizontalAlignment.RIGHT);
	        cell.setFontSize(12);
	        cell = row.createCell(20, number_formatter.format(valorTotal));
	        cell.setFontSize(12);
	        
	        
	        table2.draw();
	        
	        
	        contentStream.setLineWidth(1);
	        contentStream.moveTo(200, 80);
	        contentStream.lineTo((float) ((page.getMediaBox().getWidth())-(page.getMediaBox().getWidth() * 0.90)), 80);
	        contentStream.moveTo(400, 80);
	        contentStream.lineTo((float) ((page.getMediaBox().getWidth() * 0.90)), 80);
	        contentStream.closeAndStroke();
	        
	        contentStream.setFont( PDType1Font.TIMES_ROMAN, 10 );
	        contentStream.beginText();
	        contentStream.newLineAtOffset((float) ((page.getMediaBox().getWidth())-(page.getMediaBox().getWidth() * 0.90)), 70);
	        contentStream.showText("AProvador");
	        contentStream.endText();
	       
	        
	        contentStream.beginText();
	        contentStream.newLineAtOffset((float) (page.getMediaBox().getWidth() * 0.70)-margin, 70);
	        contentStream.showText(info_user.getString("nome"));
	        contentStream.endText();
	       /* MessageDigest md;
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        document.save(byteArrayOutputStream);
	        String codigoIntegridade="";
	        try {
				md = MessageDigest.getInstance( "SHA-256" );
				md.update( assinatura.getContents(byteArrayOutputStream.toByteArray()));     
		        BigInteger hash = new BigInteger(1,md.digest() );     
		        codigoIntegridade = hash.toString();
		        
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        contentStream.setFont( PDType1Font.TIMES_ROMAN, 10 );
	        contentStream.beginText();
	        contentStream.newLineAtOffset((float) ((page.getMediaBox().getWidth())-(page.getMediaBox().getWidth() * 0.90)), 40);
	        contentStream.showText("Código de Integridade: "+codigoIntegridade);
	        contentStream.endText();
	        contentStream.close();
	       */
	        contentStream.close();
	        response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=despesa.pdf");
            document.save(response.getOutputStream());
            document.close();

		}
		
		
	}
}
