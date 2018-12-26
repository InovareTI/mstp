package servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.EmailException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
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

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;
import classes.Rollout;
import classes.Semail;

/**
 * Servlet implementation class RolloutServlet
 */
@WebServlet("/RolloutServlet")
public class RolloutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RolloutServlet() {
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
		rolloutmgmt(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		rolloutmgmt(request, response);
	}

	
	public void rolloutmgmt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		String param10;
		String retorno="";
		String query;
		String query2 = "";
		String param12;
		
		
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
		param10="";
		
		param12="";
		insere="";
		opt="";
		dados_tabela="";
		rs2=null;
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		HttpSession session = req.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		Conexao conn = (Conexao) session.getAttribute("conexao");
		Rollout r = new Rollout();
		double money; 
		NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
		String moneyString;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		SimpleDateFormat dt_excel = new SimpleDateFormat("ddd MMM dd HH:mm:ss 'BRST' yyyy");
		DateFormat f3 = DateFormat.getDateTimeInstance();
		opt=req.getParameter("opt");
		System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Operações de Rollout do MSTP Web - "+f3.format(time)+" opt:"+opt);
		try {
			if(opt.equals("1")){

				
				rs= conn.Consulta("select * from rollout_campos where field_type='Milestone' and empresa="+p.getEmpresa().getEmpresa_id()+" order by ordenacao");
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
    			rs= conn.Consulta("select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" order by ordenacao");
    			
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
    						dados_tabela=dados_tabela+"{ \"name\": \"status_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    						
    						campos_aux=campos_aux+"{ \"text\": \"Inicio Previsto\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"sdate_pre_"+rs.getString("field_name")+"\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\", \"filtertype\": \"range\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Fim Previsto\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"edate_pre_"+rs.getString("field_name")+"\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\", \"filtertype\": \"range\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Inicio Real\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"sdate_"+rs.getString("field_name")+"\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\", \"filtertype\": \"range\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Fim Real\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"edate_"+rs.getString("field_name")+"\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\", \"filtertype\": \"range\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Anotacões\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"udate_"+rs.getString("field_name")+"\",\"columntype\": \"textbox\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Responsavel\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"resp_"+rs.getString("field_name")+"\",\"columntype\": \"combobox\",\"width\":200},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Status\", \"columngroup\":\""+rs.getString("field_name")+"\", \"cellsalign\": \"center\",\"datafield\":\"status_"+rs.getString("field_name")+"\",\"columntype\": \"combobox\",\"width\":100},"+ "\n";
    					}else{
    						if(rs.getString("tipo").equals("Texto")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"textbox\",\"width\":80},"+ "\n";
    						}else if(rs.getString("tipo").equals("Data")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"date\"},"+ "\n";	
    							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"datetimeinput\",\"cellsformat\":\"dd/MM/yyyy\", \"filtertype\": \"range\",\"width\":100},"+ "\n";
    						}else if(rs.getString("tipo").equals("Numero")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"int\"},"+ "\n";
    							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"width\":80},"+ "\n";
    						}else if(rs.getString("tipo").equals("Lista")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"filtertype\": \"checkedlist\",\"columntype\": \"combobox\", \"width\":80},"+ "\n";
    						}
    					}
    				}
    			}else{
    				dados_tabela="[]";
    			}
    			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
    			campos_aux=campos_aux.substring(0,campos_aux.length()-2);
    			dados_tabela= dados_tabela+"\n"+"],";
    			campos_aux=campos_aux+"\n"+"],";
    			dados_tabela= dados_tabela+"\n"+campos_aux;
    			rs2=conn.Consulta("select id_usuario,nome from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' and validado='Y' and ativo='Y'");
    			if(rs2.next()) {
    				dados_tabela=dados_tabela+"\n"+"\"people\" : [" +"\n";
    				rs.beforeFirst();
    				while(rs2.next()) {
    					dados_tabela=dados_tabela+"{ \"value\": \""+rs2.getString(1)+"\", \"label\": \""+rs2.getString(2)+"\" },"+"\n";
    				}
    			}
    			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
    			dados_tabela=dados_tabela+"],";
    			String imagem_status="";
    			//System.out.println(dados_tabela);
    			dados_tabela= dados_tabela+"\n"+"\"records\":[";
    			rs2= conn.Consulta("select * from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" order by recid,siteID,ordenacao limit 1600");
    			if(rs2.next()){
    				String site_aux=rs2.getString("siteID");
    				dados_tabela=dados_tabela+"\n{\"id\":"+rs2.getInt("recid")+",";
    				rs2.beforeFirst();
    				while(rs2.next() ){
    					if (rs2.getString("siteID").equals(site_aux)){
    						if(rs2.getString("tipo_campo").equals("Milestone")){
    							if(rs2.getString("status_atividade").equals("Finalizada")) {
    								imagem_status="finished.png";
    							}else if(rs2.getString("status_atividade").equals("parada")) {
    								imagem_status="stopped.png";
    							}else if(rs2.getString("status_atividade").equals("iniciada")) {
    								imagem_status="started.png";
    							}else {
    								imagem_status="notstarted.png";
    							}
    							dados_tabela=dados_tabela+"\"sdate_"+rs2.getString(6)+"\":\""+rs2.getString("dt_inicio")+"\",\"edate_"+rs2.getString(6)+"\":\""+rs2.getString("dt_fim")+"\",\"sdate_pre_"+rs2.getString(6)+"\":\""+rs2.getString("dt_inicio_bl")+"\",\"edate_pre_"+rs2.getString(6)+"\":\""+rs2.getString("dt_fim_bl")+"\",\"udate_"+rs2.getString(6)+"\":\""+rs2.getString(21)+"\",\"resp_"+rs2.getString(6)+"\": \""+rs2.getString("responsavel")+"\",\"status_"+rs2.getString(6)+"\":\"<img src='img/"+imagem_status+"'>\",";
	    					}else{
	    						dados_tabela=dados_tabela+"\""+rs2.getString(6)+"\":\""+rs2.getString(23)+"\",";
	    					}
    					}else{
    						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
    						dados_tabela=dados_tabela+"},";
    						dados_tabela=dados_tabela+"\n{\"id\":"+rs2.getInt("recid")+",";
    						site_aux=rs2.getString("siteID");
    						rs2.previous();
    					}
    				}
    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"}";
	    			dados_tabela= dados_tabela+"\n"+"]}";
    			}else{
    				dados_tabela= dados_tabela+"]}";
    			}
    			
    			//System.out.println(dados_tabela);
    			JSONObject jObj = new JSONObject(dados_tabela);
    			resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(jObj);
					
					
			
			}else if(opt.equals("2")) {

				
                
				rs=conn.Consulta("Select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" order by ordenacao");
	    		
					if(rs.next()){
						
						dados_tabela="<table id=\"tabela_campos_rollout\"  data-filter-control=\"true\" data-toggle=\"table\"  data-pagination=\"true\" data-toolbar=\"#toolbar_campos_rollout\"  data-search=\"true\" data-use-row-attr-func=\"true\" data-reorderable-rows=\"true\">" +"\n";
						dados_tabela=dados_tabela + "<thead>"+"\n";
						dados_tabela=dados_tabela +"<tr>"+"\n";
						dados_tabela=dados_tabela +" <th data-checkbox=\"true\" data-formatter=\"stateFormatter\"></th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"campo_id\" data-sortable=\"true\">Campo Id</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"campo2\" data-sortable=\"true\">Nome</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"campo3\" data-sortable=\"true\" data-filter-control=\"select\">Tipo</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"campo4\" data-sortable=\"true\" data-filter-control=\"select\">Valor</th>"+"\n";
						dados_tabela=dados_tabela +" <th data-field=\"campo5\" data-sortable=\"true\">Status</th>"+"\n";
						dados_tabela=dados_tabela +"</tr>"+"\n";
						dados_tabela=dados_tabela +"</thead>"+"\n";
						dados_tabela=dados_tabela +"<tbody>"+"\n";
						rs.beforeFirst();
						while(rs.next()){
							dados_tabela=dados_tabela + "<tr data-index=\""+rs.getRow()+"\" data-value=\"teste\">"+"\n";
							dados_tabela=dados_tabela + " <td></td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(1)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(2)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(9)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(11)+"</td>"+"\n";
							dados_tabela=dados_tabela + " <td>"+rs.getString(7)+"</td>"+"\n";
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
			
			}else if(opt.equals("3")) {
				System.out.println("Iniciando exporte de rollout");
				int colIndex = 0;
				int rowIndex = 0;
				param1=req.getParameter("filtros");
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
				
				Date aux_date;
				JSONObject jObj = new JSONObject(param1);
				JSONArray filtros = jObj.getJSONArray("filtros");
				query="Select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" order by ordenacao";
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("RolloutMSTP");
				Row row;
				Row row2;
				Cell cell;
	            
	            rs=conn.Consulta(query);
	            if(rs.next()) {
	            	rowIndex=1;
	            	row = sheet.createRow((short) rowIndex);
	            	row2 = sheet.createRow((short) rowIndex+1);
	            	rs.beforeFirst();
	            	//System.out.println("Arquivo Criado");
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
	                row.setRowStyle(cellStyle);
	                row2.setRowStyle(cellStyle);
	                cell = row.createCell((short) colIndex);
	                cell.setCellValue("RecID");
	                sheet.autoSizeColumn(colIndex);
	                cell.setCellStyle(cellStyle3);
	                cell = row2.createCell((short) colIndex);
	                cell.setCellStyle(cellStyle);
	                sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+1,colIndex,colIndex));
	                colIndex=colIndex+1;
	            	while(rs.next()) {
	            		//System.out.println("Adicionando nome "+rs.getString("field_name"));
	            		if(rs.getString("field_type").equals("Atributo")) {
	            			cell = row.createCell((short) colIndex);
			                cell.setCellValue(rs.getString("field_name"));
			                sheet.autoSizeColumn(colIndex);
			                cell.setCellStyle(cellStyle);
			                cell = row2.createCell((short) colIndex);
			                cell.setCellStyle(cellStyle);
			                sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex+1,colIndex,colIndex));
			               
			                colIndex=colIndex+1;
	            		}else if(rs.getString("field_type").equals("Milestone")) {
	            			cell = row.createCell((short) colIndex);
			                cell.setCellValue(rs.getString("field_name"));
			                cell.setCellStyle(cellStyle);
			                sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,colIndex,colIndex+5));
			                sheet.autoSizeColumn(colIndex);
			                cell = row2.createCell((short) colIndex);
			                cell.setCellValue("Inicio Planejado");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                cell = row2.createCell((short) colIndex);
			                cell.setCellValue("Fim Planejado");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                cell = row2.createCell((short) colIndex);
			                cell.setCellValue("Inicio Real");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                cell = row2.createCell((short) colIndex);
			                cell.setCellValue("Fim Real");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                cell = row2.createCell((short) colIndex);
			                cell.setCellValue("Anotações");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
			                cell = row2.createCell((short) colIndex);
			                cell.setCellValue("Reponsável");
			                cell.setCellStyle(cellStyle);
			                sheet.autoSizeColumn(colIndex);
			                colIndex=colIndex+1;
	            		}
	            		
	            	}
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
	                CreationHelper createHelper = workbook.getCreationHelper();
	                XSSFCellStyle cellStyle_date = workbook.createCellStyle();
	            	
	                cellStyle_date.setAlignment(HorizontalAlignment.CENTER);
	                cellStyle_date.setVerticalAlignment(VerticalAlignment.CENTER);
	                
	                cellStyle_date.setBorderBottom(BorderStyle.THIN);
	                cellStyle_date.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	                cellStyle_date.setBorderLeft(BorderStyle.THIN);
	                cellStyle_date.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	                cellStyle_date.setBorderRight(BorderStyle.THIN);
	                cellStyle_date.setRightBorderColor(IndexedColors.BLACK.getIndex());
	                cellStyle_date.setBorderTop(BorderStyle.THIN);
	                cellStyle_date.setTopBorderColor(IndexedColors.BLACK.getIndex());
	                cellStyle_date.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
	                
	                if(filtros.length()>0) {
	                	
							query="Select * from rollout where empresa="+p.getEmpresa().getEmpresa_id()+" and linha_ativa='Y' and recid in (";
							for(int f=0;f<filtros.length();f++) {
							 query=query+filtros.getInt(f)+",";	
							}
							query=query.substring(0,query.length()-1);
							query=query+") order by recid,ordenacao";
						
					}else {
						query="Select * from rollout where empresa="+p.getEmpresa().getEmpresa_id()+" and linha_ativa='Y' order by recid,ordenacao";
					}
	            	//System.out.println(query);
		            rs2=conn.Consulta(query);
		            int recid_aux=0;
		            if(rs2.next()) {
		            	recid_aux=rs2.getInt("recid");
		            	JSONObject jsonObject_campos = r.getCampos().getCampos_tipo(conn, p);
		            	colIndex=0;
		            	rowIndex=3;
		            	rs2.beforeFirst();
		            	row = sheet.createRow((short) rowIndex);
		            	cell = row.createCell((short) colIndex);
		            	cell.setCellValue(recid_aux);
        				cell.setCellStyle(cellStyle3);
        				colIndex=colIndex+1;
		            	//System.out.println("Criando Linha "+ rowIndex);
		            	while(rs2.next()) {
		            		if(recid_aux==rs2.getInt("recid")) {
		            			
		            			cell = row.createCell((short) colIndex);
		            			if(rs2.getString("tipo_campo").equals("Atributo")) {
		            				if(jsonObject_campos.getJSONArray(rs2.getString("milestone")).get(1).equals("Data")){
	    	            				 if(!rs2.getString("value_atbr_field").equals("")) {
	    	            					 cell.setCellStyle(cellStyle_date);
	    	            					    cell.setCellType(CellType.NUMERIC);
	    	            					 cell.setCellValue(checa_formato_data(rs2.getString("value_atbr_field")));
	    	            					 
			 		            			 colIndex=colIndex+1;
		    	            			 }else {
		    	            				 cell.setCellStyle(cellStyle_date);
		   	            					 cell.setCellType(CellType.NUMERIC);
		    	            				
		    	            				cell.setCellValue(rs2.getString("value_atbr_field"));
		 		            				
		 		            				colIndex=colIndex+1;
		    	            			 }
	    	            			 }else {
	    	            				 cell.setCellValue(rs2.getString("value_atbr_field"));
		 		            				cell.setCellStyle(cellStyle2);
		 		            				colIndex=colIndex+1;
	    	            			 }
		            				
		            			}else {
		            				if(!rs2.getString("dt_inicio_bl").equals("")) {
		            					aux_date=checa_formato_data(rs2.getString("dt_inicio_bl"));
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_inicio_bl"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				if(!rs2.getString("dt_fim_bl").equals("")) {
			            				aux_date=checa_formato_data(rs2.getString("dt_fim_bl"));
			            				cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
			            				cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_fim_bl"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				if(!rs2.getString("dt_inicio").equals("")) {
			            				aux_date=checa_formato_data(rs2.getString("dt_inicio"));
			            				cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
			            				cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_inicio"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				if(!rs2.getString("dt_fim").equals("")) {
			            				aux_date=checa_formato_data(rs2.getString("dt_fim"));
			            				cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
			            				cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_fim"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				cell.setCellValue(rs2.getString("remark"));
		            				cell.setCellStyle(cellStyle2);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				cell.setCellValue(rs2.getString("responsavel"));
		            				cell.setCellStyle(cellStyle2);
		            				colIndex=colIndex+1;
		            			}
				                
		            		}else {
		            			colIndex=0;
		            			rowIndex=rowIndex+1;
		            			row = sheet.createRow((short) rowIndex);
		            			cell = row.createCell((short) colIndex);
		            			recid_aux=rs2.getInt("recid");
				            	cell.setCellValue(recid_aux);
		        				cell.setCellStyle(cellStyle3);
		        				colIndex=colIndex+1;
		            			cell = row.createCell((short) colIndex);
		            			//recid_aux=rs2.getInt("recid");
		            			if(rs2.getString("tipo_campo").equals("Atributo")) {
		            				if(jsonObject_campos.getJSONArray(rs2.getString("milestone")).get(1).equals("Data")){
	    	            				 if(!rs2.getString("value_atbr_field").equals("")) {
	    	            					 cell.setCellStyle(cellStyle_date);
	    	            					 cell.setCellType(CellType.NUMERIC);
	    	            					 cell.setCellValue(checa_formato_data(rs2.getString("value_atbr_field")));
	    	            					 
			 		            			 colIndex=colIndex+1;
		    	            			 }else {
		    	            				 cell.setCellStyle(cellStyle_date);
	    	            					 cell.setCellType(CellType.NUMERIC);
		    	            				cell.setCellValue(checa_formato_data(rs2.getString("value_atbr_field")));
		 		            				//cell.setCellStyle(cellStyle2);
		 		            				colIndex=colIndex+1;
		    	            			 }
	    	            			 }else {
	    	            				 cell.setCellValue(rs2.getString("value_atbr_field"));
		 		            				cell.setCellStyle(cellStyle2);
		 		            				colIndex=colIndex+1;
	    	            			 }
		            			}else {
		            				if(!rs2.getString("dt_inicio_bl").equals("")) {
			            				aux_date=checa_formato_data(rs2.getString("dt_inicio_bl"));
			            				cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
			            				cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_inicio_bl"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				if(!rs2.getString("dt_fim_bl").equals("")) {
		            					aux_date=checa_formato_data(rs2.getString("dt_fim_bl"));
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_fim_bl"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				if(!rs2.getString("dt_inicio").equals("")) {
			            				aux_date=checa_formato_data(rs2.getString("dt_inicio"));
			            				cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
			            				cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_inicio"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				if(!rs2.getString("dt_fim").equals("")) {
			            				aux_date=checa_formato_data(rs2.getString("dt_fim"));
			            				cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
			            				cell.setCellValue(aux_date);
		            				}else {
		            					cell.setCellStyle(cellStyle_date);
   	            					    cell.setCellType(CellType.NUMERIC);
		            					cell.setCellValue(rs2.getString("dt_fim"));
		            				}
		            				cell.setCellStyle(cellStyle_date);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				cell.setCellValue(rs2.getString("remark"));
		            				cell.setCellStyle(cellStyle2);
		            				colIndex=colIndex+1;
		            				cell = row.createCell((short) colIndex);
		            				cell.setCellValue(rs2.getString("responsavel"));
		            				cell.setCellStyle(cellStyle2);
		            				colIndex=colIndex+1;
		            			}
		            		}
		            	}
		            }
	            	System.out.println("Arquivo Criado & finalizado");
	            	resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	                resp.setHeader("Content-Disposition", "attachment; filename="+time.toString()+"_rollout_mstp.xlsx");
	                workbook.write(resp.getOutputStream());
	                workbook.close();
	            }else {
	            	resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Rollout não disponivel para download");
	            }
			}else if(opt.equals("4")) {
				insere_linha_rollout(conn,req,resp,p);
			}else if(opt.equals("5")) {
				
				/*ClassLoader classloader =
						   org.apache.poi.poifs.filesystem.POIFSFileSystem.class.getClassLoader();
						URL res = classloader.getResource(
						             "org/apache/poi/poifs/filesystem/POIFSFileSystem.class");
						String path = res.getPath();
						System.out.println("POI Core came from " + path);

						classloader = org.apache.poi.ooxml.POIXMLDocument.class.getClassLoader();
						res = classloader.getResource("org/apache/poi/POIXMLDocument.class");
						path = res.getPath();
						System.out.println("POI OOXML came from " + path);

						*/
				
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				time = new Timestamp(System.currentTimeMillis());
				Semail email= new Semail();
				InputStream inputStream=null;
				if (ServletFileUpload.isMultipartContent(req)) {
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
					//System.out.println("Multipart size: " + multiparts.size());
					Iterator<FileItem> iter = multiparts.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
					       // processFormField(item);
					    } else {
					    	System.out.println("Importando rollout");
					    	inputStream = item.getInputStream();
					    	
					    	 String sql = "Insert into arquivos_importados (arq_dt_inserido,arq,arq_nome,arq_tipo,arq_tamanho,arq_usuario) values ('"+time+"' , ? ,'"+item.getName()+"','"+item.getContentType()+"','"+item.getSize()+"','"+p.get_PessoaUsuario()+"')";
						     //System.out.println(sql);
					    	 PreparedStatement statement;
							 statement = conn.getConnection().prepareStatement(sql);
							 statement.setBlob(1, inputStream);
							 int linha = statement.executeUpdate();
						     statement.getConnection().commit();
						     rs=conn.Consulta("select id_sys_arq from arquivos_importados where arq_usuario='"+p.get_PessoaUsuario()+"' order by id_sys_arq desc limit 1");
						     if(rs.next()) {
						    	 linha=rs.getInt(1);
						     }
					    	InputStream inputStream2= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
				    		DataFormatter dataFormatter = new DataFormatter();
				    		int indexCell=0;
				    		int recid=0;
				    		XSSFWorkbook wb = new XSSFWorkbook(inputStream2);
				    		Sheet sheet1 = wb.getSheet("RolloutMSTP");
				    		Cell cell2;
				    		String cellValue;
				    		last_id=0;
				    		Row row_aux = sheet1.getRow(2);
				    		String []campos = new String[row_aux.getLastCellNum()];
				    		int j=row_aux.getLastCellNum();
				    		row_aux = sheet1.getRow(1);
				    		String aux_nome="";
				    		resp.setContentType("application/json"); 
				        	JSONObject jsonObject = new JSONObject(); 
				        	jsonObject.put("Sucesso", "Sucesso"); 
				        	PrintWriter pw = resp.getWriter();
				        	pw.print(jsonObject); 
				        	pw.close();
				    		for(int i=0;i<j;i++) {
				    			cell2=row_aux.getCell(i);
				    			if(!dataFormatter.formatCellValue(cell2).isEmpty()) {
				    				campos[i]=dataFormatter.formatCellValue(cell2);
				    				aux_nome=dataFormatter.formatCellValue(cell2);
				    			}else {
				    				campos[i]=aux_nome;
				    			}
	    	            		 //System.out.println(campos.toString());
	    	            	 }
				    		JSONObject jsonObject_campos = r.getCampos().getCampos_tipo(conn, p);
				    		query="select recid from rollout where empresa="+p.getEmpresa().getEmpresa_id()+" order by recid desc limit 1";
				    		rs=conn.Consulta(query);
				    		int last_recid=0;
				    		if(rs.next()) {
				    			last_recid=rs.getInt(1);
				    		}
				    		Iterator<Row> rowIterator = sheet1.iterator();
				            rowIterator.next();
				            Timestamp time2 = new Timestamp(System.currentTimeMillis());
				            double total_linhas=sheet1.getLastRowNum();
				            double linha_sheet=0.0;
				            double divisor=total_linhas/10.0;
				            int i=0;
				            int []result;
				            int colunacelula=0;
				            String aux_insert="";
				            String aux_update="";
				            String query_insert_milestone;
				            String aux_insertM="";
				            String aux_updateM="";
				            String status_atividade="nao iniciada";
				            String site_id="";
				            conn.getConnection().setAutoCommit(false);
				            conn.set_stmtBtachUpdateAtributo("update rollout set value_atbr_field=? where recid=? and milestone=? and empresa=?");
				            conn.set_stmtBtachInsertAtributo("insert into rollout (recid,siteid,milestone,tipo_campo,value_atbr_field,update_by,update_time,ordenacao,empresa) values(?,?,?,?,?,?,?,?,?)");
				            conn.set_stmtBtachUpdateMilestone("update rollout set dt_inicio_bl=?,dt_fim_bl=?,dt_inicio=?,dt_fim=?,remark=?,responsavel=? where recid=? and milestone=? and empresa=?");
				            conn.set_stmtBtachInsertMilestone("insert into rollout (recid,siteid,milestone,dt_inicio_bl,dt_fim_bl,dt_inicio,dt_fim,remark,responsavel,tipo_campo,update_by,update_time,ordenacao,empresa,status_atividade,value_atbr_field) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				            System.out.println("Total de linhas no arquivo:"+total_linhas);
				            while (rowIterator.hasNext()) {
				            	Row row = rowIterator.next();
				            	linha_sheet=linha_sheet+1;
				            	//System.out.println("Linha "+row.getRowNum());
				    			
				    			i=0;
				    			query="insert into rollout (recid,siteid,milestone,tipo_campo,value_atbr_field,update_by,update_time,ordenacao,empresa) values";
				    			query_insert_milestone="insert into rollout (recid,siteid,milestone,dt_inicio_bl,dt_fim_bl,dt_inicio,dt_fim,remark,responsavel,tipo_campo,update_by,update_time,ordenacao,empresa,status_atividade,value_atbr_field) values";
				    			 while (i<j)  {
				    				 Cell cell = row.getCell(i);
				    				 
				    				 //System.out.println("Numero da na linha é: "+row.getRowNum());
				    				 //System.out.println("Numero da primeira celula na linha é: "+row.getFirstCellNum());
				    				 //System.out.println("valor de i: "+i);
				    				 //System.out.println("Linha "+row.getRowNum()+", Célula "+ cell.getColumnIndex());
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    				 cellValue=cellValue.replace("'","");
				    				 cellValue=cellValue.replace("\"","");
				    				 cellValue=cellValue.replace("\\","_");
				    				if(row.getRowNum()>2) {
				    					//query="update rollout set ";
				    					//System.out.println("Linha"+row.getRowNum()+"| Coluna:"+cell.getColumnIndex());
					    				 if(row.getFirstCellNum()==0) {
					    					 if(i==0) {
					    					 if(cellValue.length()>0) {
					    						 recid=Integer.parseInt(cellValue);
					    						 System.out.println("valor do recid:"+recid);
					    						 colunacelula=i;
					    					 }else {
					    						 recid=-1;
					    						 last_recid=last_recid+1;
					    						 colunacelula=i;
					    					 }
					    					 }else {
					    						 colunacelula=i;
					    					 }
					    				 }else {
					    					 recid=-1;
					    					 colunacelula=i;
					    					 if(i==0) {
					    						 last_recid=last_recid+1;
					    					 }
					    				 }
					    				 cellValue = dataFormatter.formatCellValue(cell);
					    				 cellValue=cellValue.replace("'","");
					    				 cellValue=cellValue.replace("\"","");
					    				 cellValue=cellValue.replace("\\","_");
					    				 if(campos[colunacelula].equals("Site ID")) {
		    	            				 site_id=cellValue;
		    	            			 }
					    				 if(jsonObject_campos.has(campos[colunacelula])) {
					    					 
					    	            	 if(jsonObject_campos.getJSONArray(campos[colunacelula]).get(0).equals("Atributo")){
					    	            		 if(recid==-1) {
					    	            			 if(jsonObject_campos.getJSONArray(campos[colunacelula]).get(1).equals("Data")){
					    	            				 if(!cellValue.equals("")) {
					    	            					 //cellValue=f2.format(cellValue);
							    	            			if(cell.getCellType() == CellType.NUMERIC) {
								    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	            				 //System.out.println(cell.getDateCellValue());
								    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
								    	            			 }
							    	            			}else {
							    	            				cellValue="";
							    	            			}
						    	            			 }else {
						    	            				 cellValue="";
						    	            			 }
					    	            			 }
					    	            			 query=query+"("+last_recid+",'site"+last_recid+"','"+campos[colunacelula]+"','Atributo','"+cellValue+"','"+p.get_PessoaUsuario()+"','"+time+"',"+(colunacelula+1)+","+p.getEmpresa().getEmpresa_id()+"),\n";
					    	            			 
					    	            			 
					    	            			 /*conn.get_BatchInsertAtributo().setInt(1, last_recid);
					    	            			 conn.get_BatchInsertAtributo().setString(2,"site"+last_recid);
					    	            			 conn.get_BatchInsertAtributo().setString(3, campos[colunacelula]);
					    	            			 conn.get_BatchInsertAtributo().setString(4, "Atributo");
					    	            			 conn.get_BatchInsertAtributo().setString(5, cellValue);
					    	            			 conn.get_BatchInsertAtributo().setString(6, p.get_PessoaUsuario());
					    	            			 conn.get_BatchInsertAtributo().setString(7, time.toString());
					    	            			 conn.get_BatchInsertAtributo().setInt(8, (colunacelula+1));
					    	            			 conn.get_BatchInsertAtributo().setInt(9, p.getEmpresa().getEmpresa_id());
					    	            			 conn.get_BatchInsertAtributo().addBatch();*/
					    	            			 aux_insert="ok";
					    	            			 
					    	            		 }else {
					    	            			 //query="update rollout set value_atbr_field=? where recid=? and milestone=? and empresa=?";
					    	            			 //query=" value_atbr_field='"+cellValue+"' where recid="+recid+" and milestone='"+campos[colunacelula]+"' and empresa="+p.getEmpresa().getEmpresa_id()+";";
					    	            			 //System.out.println("Adicionando batch linha "+row.getRowNum());
					    	            			 if(jsonObject_campos.getJSONArray(campos[colunacelula]).get(1).equals("Data")){
					    	            				 if(!cellValue.equals("")) {
							    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
							    	            			 if(cell.getCellType()==CellType.NUMERIC) {
								    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	            				 //System.out.println(cell.getDateCellValue());
								    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
								    	            			 }
							    	            			 }else {
							    	            				 cellValue="";
							    	            			 }
						    	            			 }else {
						    	            				 cellValue="";
						    	            			 }
					    	            			 }
					    	            			 conn.get_BatchUpdateAtributo().setString(1, cellValue);
					    	            			 conn.get_BatchUpdateAtributo().setInt(2, recid);
					    	            			 conn.get_BatchUpdateAtributo().setString(3, campos[colunacelula]);
					    	            			 conn.get_BatchUpdateAtributo().setInt(4, p.getEmpresa().getEmpresa_id());
					    	            			 conn.get_BatchUpdateAtributo().addBatch();
					    	            			 aux_update="ok";
					    	            			 
					    	            		 }
					    	            	 }else if(jsonObject_campos.getJSONArray(campos[colunacelula]).get(0).equals("Milestone")) {
					    	            		 if(recid==-1) {
					    	            			 //System.out.println("inserindo nova linha "+row.getRowNum());
					    	            			 //query="insert into rollout (recid,siteid,milestone,dt_inicio_bl,dt_fim_bl,dt_inicio,dt_fim,remark,responsavel,tipo_campo,update_by,update_time,ordenacao,empresa,status_atividade,value_atbr_field)"
					    	            			 query_insert_milestone=query_insert_milestone+"("+last_recid+",'site"+last_recid+"','"+campos[colunacelula]+"',";
					    	            			 //conn.get_BatchInsertMilestone().setInt(1, last_recid);
					    	            			 //conn.get_BatchInsertMilestone().setString(2, "site"+last_recid);
					    	            			 //conn.get_BatchInsertMilestone().setString(3, campos[colunacelula]);
					    	            			 if(!cellValue.equals("")) {
						    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
						    	            			 if(cell.getCellType()==CellType.NUMERIC) {
							    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
							    	            				 //System.out.println(cell.getDateCellValue());
							    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
							    	            			 }
						    	            			 }else {
						    	            				 cellValue="";
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }
					    	            			 query_insert_milestone=query_insert_milestone+"'"+cellValue+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(4, cellValue);
					    	            			 
					    	            			 i++;
					    	            			 cell = row.getCell(i);
					    	            			 
					    	            			 cellValue = dataFormatter.formatCellValue(cell);
					    	            			 if(!cellValue.equals("")) {
						    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
						    	            			 if(cell.getCellType()==CellType.NUMERIC) {
							    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
							    	            				 //System.out.println(cell.getDateCellValue());
							    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
							    	            			 }
						    	            			 }else {
						    	            				 cellValue="";
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }
					    	            			 query_insert_milestone=query_insert_milestone+"'"+cellValue+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(5, cellValue);
					    	            			 
					    	            			 i++;
					    	            			 cell = row.getCell(i);
					    	            			 cellValue = dataFormatter.formatCellValue(cell);
					    	            			 if(!cellValue.equals("")) {
						    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
					    	            				 if(cell.getCellType()==CellType.NUMERIC) {
							    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
							    	            				 //System.out.println(cell.getDateCellValue());
							    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
							    	            			 }
						    	            			 }else {
						    	            				 cellValue="";
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }
					    	            			 query_insert_milestone=query_insert_milestone+"'"+cellValue+"',";
					    	            			// conn.get_BatchInsertMilestone().setString(6, cellValue);
					    	            			
					    	            			 if(cellValue.length()>0) {
					    	            				 status_atividade="iniciada";
					    	            			 }else {
					    	            				 status_atividade="nao iniciada";
					    	            			 }
					    	            			 i++;
					    	            			 cell = row.getCell(i);
					    	            			 cellValue = dataFormatter.formatCellValue(cell);
					    	            			 if(!cellValue.equals("")) {
						    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
						    	            			 if(cell.getCellType()==CellType.NUMERIC) {
							    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
							    	            				 //System.out.println(cell.getDateCellValue());
							    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
							    	            			 }
						    	            			 }else {
						    	            				 cellValue="";
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }
					    	            			 query_insert_milestone=query_insert_milestone+"'"+cellValue+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(7, cellValue);
					    	            			 
					    	            			 if(cellValue.length()>0) {
					    	            				 status_atividade="Finalizada";
					    	            			 }
					    	            			 i++;
					    	            			 cell = row.getCell(i);
					    	            			 cellValue = dataFormatter.formatCellValue(cell);
					    	            			 query_insert_milestone=query_insert_milestone+"'"+cellValue+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(8, cellValue);
					    	            			
					    	            			 i++;
					    	            			 cell = row.getCell(i);
					    	            			 
					    	            			 cellValue = dataFormatter.formatCellValue(cell);
					    	            			 query_insert_milestone=query_insert_milestone+"'"+cellValue+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(9, cellValue);
					    	            			 query_insert_milestone=query_insert_milestone+"'Milestone',";
					    	            			// conn.get_BatchInsertMilestone().setString(10, "Milestone");
					    	            			 query_insert_milestone=query_insert_milestone+"'"+p.get_PessoaUsuario()+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(11, p.get_PessoaUsuario());
					    	            			 query_insert_milestone=query_insert_milestone+"'"+time+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(12, time.toString());
					    	            			 query_insert_milestone=query_insert_milestone+(colunacelula+1)+",";
					    	            			 //conn.get_BatchInsertMilestone().setInt(13, (colunacelula+1));
					    	            			 query_insert_milestone=query_insert_milestone+p.getEmpresa().getEmpresa_id()+",";
					    	            			 //conn.get_BatchInsertMilestone().setInt(14, p.getEmpresa().getEmpresa_id());
					    	            			 query_insert_milestone=query_insert_milestone+"'"+status_atividade+"',";
					    	            			 //conn.get_BatchInsertMilestone().setString(15,status_atividade);
					    	            			 query_insert_milestone=query_insert_milestone+"'"+site_id+"'),\n";
					    	            			 //conn.get_BatchInsertMilestone().setString(16,site_id);
					    	            			 //conn.get_BatchInsertMilestone().addBatch();
					    	            			 aux_insertM="ok";
					    	            			 //query=query+"'Milestone','"+p.get_PessoaUsuario()+"','"+time+"',"+(colunacelula+1)+","+p.getEmpresa().getEmpresa_id()+",'"+status_atividade+"','"+site_id+"');";
					    	            			 
					    	            		 }else {
					    	            			 if(!cellValue.equals("")) {
						    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
						    	            			 if(cell.getCellType()==CellType.NUMERIC) {
							    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
							    	            				 //System.out.println(cell.getDateCellValue());
							    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
							    	            			 }
						    	            			 }else {
						    	            				 cellValue="";
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }	 
					    	            		 conn.get_BatchUpdateMilestone().setString(1, cellValue);
					    	            		 //query=query+" dt_inicio_bl='"+cellValue+"',";
					    	            		 
					    	            		 i++;
				    	            			 cell = row.getCell(i);
					    	            		 cellValue = dataFormatter.formatCellValue(cell);
					    	            		 if(!cellValue.equals("")) {
					    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
					    	            			 if(cell.getCellType()==CellType.NUMERIC) {
						    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
						    	            				 //System.out.println(cell.getDateCellValue());
						    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }
				    	            			 }else {
				    	            				 cellValue="";
				    	            			 }
					    	            		 conn.get_BatchUpdateMilestone().setString(2, cellValue);
					    	            		 //query=query+" dt_fim_bl='"+cellValue+"',";
					    	            		 i++;
				    	            			 cell = row.getCell(i);
					    	            		 cellValue = dataFormatter.formatCellValue(cell);
					    	            		 if(!cellValue.equals("")) {
					    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
					    	            			 if(cell.getCellType()==CellType.NUMERIC) {
						    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
						    	            				 //System.out.println(cell.getDateCellValue());
						    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }
				    	            			 }else {
				    	            				 cellValue="";
				    	            			 }
					    	            		 conn.get_BatchUpdateMilestone().setString(3, cellValue);
					    	            		 //query=query+" dt_inicio='"+cellValue+"',";
					    	            		 i++;
				    	            			 cell = row.getCell(i);
					    	            		 cellValue = dataFormatter.formatCellValue(cell);
					    	            		 if(!cellValue.equals("")) {
					    	            			 //cellValue=f2.format(checa_formato_data(cellValue));
					    	            			 if(cell.getCellType()==CellType.NUMERIC) {
						    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
						    	            				 //System.out.println(cell.getDateCellValue());
						    	            				 cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
						    	            			 }
					    	            			 }else {
					    	            				 cellValue="";
					    	            			 }
				    	            			 }else {
				    	            				 cellValue="";
				    	            			 }
					    	            		 conn.get_BatchUpdateMilestone().setString(4, cellValue);
					    	            		 //query=query+" dt_fim='"+cellValue+"',";
					    	            		 i++;
				    	            			 cell = row.getCell(i);
					    	            		 cellValue = dataFormatter.formatCellValue(cell);
					    	            		 conn.get_BatchUpdateMilestone().setString(5, cellValue);
					    	            		 //query=query+" remark='"+cellValue+"',";
					    	            		 i++;
				    	            			 cell = row.getCell(i);
					    	            		 cellValue = dataFormatter.formatCellValue(cell);
					    	            		 conn.get_BatchUpdateMilestone().setString(6, cellValue);
					    	            		 //query=query+" responsavel='"+cellValue+"'";
					    	            		 conn.get_BatchUpdateMilestone().setInt(7, recid);
					    	            		 conn.get_BatchUpdateMilestone().setString(8, campos[colunacelula]);
					    	            		 conn.get_BatchUpdateMilestone().setInt(9, p.getEmpresa().getEmpresa_id());
					    	            		 conn.get_BatchUpdateMilestone().addBatch();
					    	            		 aux_updateM="ok";
					    	            		 //query=query+" where recid="+recid+" and milestone='"+campos[colunacelula]+"' and empresa="+p.getEmpresa().getEmpresa_id()+";";
					    	            		 
					    	            		 }
					    	            	 }
				    				 }
					    	        }
				    				i++;
				    				
				    			}
				    			 //System.out.println(linha_sheet % divisor);
				    			if(linha_sheet % divisor < 1.0) {
				    				 //System.out.println("Atualizando status");
				    				 //System.out.println(linha_sheet);
				    				 //System.out.println(total_linhas);
				    				 //System.out.println(linha_sheet / total_linhas);
				    				 //System.out.println((linha_sheet / total_linhas) * 100);
				    				 //System.out.println("update arquivos_importados set status_processamento='"+(int) ((linha_sheet / total_linhas) * 100)+"' where id_sys_arq="+linha);
				    				conn.Alterar("update arquivos_importados set status_processamento='"+(int) ((linha_sheet / total_linhas) * 100)+"' where id_sys_arq="+linha);
				    				conn.getConnection().commit();
				    			}
				    				//time2 = new Timestamp(System.currentTimeMillis());
						            //System.out.println("executando - "+linha_sheet);
						            if(aux_update.equals("ok")) {
						            	result=conn.get_BatchUpdateAtributo().executeBatch();
							            conn.getConnection().commit();
							            //time2 = new Timestamp(System.currentTimeMillis());
							            //System.out.println("Batch update Atributo finalizado. Linhas alteradas:"+result.length + "em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
						            }
						            if(aux_insert.equals("ok")) {
						            	//result=conn.get_BatchInsertAtributo().executeBatch();
							            //conn.getConnection().commit();
							            //time2 = new Timestamp(System.currentTimeMillis());
							            query=query.substring(0,query.length()-2);
							            //System.out.println(query);
							            if(conn.Inserir_simples(query)) {
							            	query="";
							            	//System.out.println("Batch insert Atributo finalizado. Linhas alteradas em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
							            }
						            }
						            if(aux_insertM.equals("ok")) {
						            	//result=conn.get_BatchInsertMilestone().executeBatch();
							            //conn.getConnection().commit();
							            //time2 = new Timestamp(System.currentTimeMillis());
							            query_insert_milestone=query_insert_milestone.substring(0,query_insert_milestone.length()-2);
							            if(conn.Inserir_simples(query_insert_milestone)) {
							            query_insert_milestone="";
							            //System.out.println("Batch insert Milestone finalizado. Linhas alteradas em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
							            }
						            }
						            if(aux_updateM.equals("ok")) {
						            	result=conn.get_BatchUpdateMilestone().executeBatch();
							            conn.getConnection().commit();
							            //time2 = new Timestamp(System.currentTimeMillis());
							            //System.out.println("Batch update Milestone finalizado. Linhas alteradas:"+result.length + "em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
						            }
						            aux_update="";
						            aux_insert="";
						            aux_insertM="";
						            aux_updateM="";
						            conn.get_BatchUpdateAtributo().clearBatch();
						            conn.get_BatchInsertAtributo().clearBatch();
						            conn.get_BatchInsertMilestone().clearBatch();
						            conn.get_BatchUpdateMilestone().clearBatch();
						            
				    			//}
				    		}
				            /*time2 = new Timestamp(System.currentTimeMillis());
				            System.out.println("Montagem dos Bacths finalizados - executando");
				            result=conn.get_BatchUpdateAtributo().executeBatch();
				            conn.getConnection().commit();
				            time2 = new Timestamp(System.currentTimeMillis());
				            System.out.println("Batch update Atributo finalizado. Linhas alteradas:"+result.length + "em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
				            result=conn.get_BatchInsertAtributo().executeBatch();
				            conn.getConnection().commit();
				            time2 = new Timestamp(System.currentTimeMillis());
				            System.out.println("Batch insert Atributo finalizado. Linhas alteradas:"+result.length + "em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
				            result=conn.get_BatchInsertMilestone().executeBatch();
				            conn.getConnection().commit();
				            time2 = new Timestamp(System.currentTimeMillis());
				            System.out.println("Batch insert Milestone finalizado. Linhas alteradas:"+result.length + "em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
				            result=conn.get_BatchUpdateMilestone().executeBatch();
				            conn.getConnection().commit();
				            time2 = new Timestamp(System.currentTimeMillis());
				            System.out.println("Batch update Milestone finalizado. Linhas alteradas:"+result.length + "em "+TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())));
				            conn.get_BatchUpdateAtributo().clearBatch();
				            conn.get_BatchInsertAtributo().clearBatch();
				            conn.get_BatchInsertMilestone().clearBatch();
				            conn.get_BatchUpdateMilestone().clearBatch();
				           */
				            conn.Alterar("update arquivos_importados set status_processamento='100' where id_sys_arq="+linha);
					    }
					}
					
					System.out.println("Importação Finalizada!");
					conn.Alterar("update rollout set dt_inicio=replace(dt_inicio,'0018','2018') where dt_inicio like '%0018'");
					conn.Alterar("update rollout set dt_inicio=replace(dt_inicio,'0019','2018') where dt_inicio like '%0019'");
					conn.Alterar("update rollout set dt_inicio=replace(dt_inicio,'0020','2018') where dt_inicio like '%0020'");
					
					conn.Alterar("update rollout set dt_fim=replace(dt_fim,'0018','2018') where dt_fim like '%0018'");
					conn.Alterar("update rollout set dt_fim=replace(dt_fim,'0019','2018') where dt_fim like '%0019'");
					conn.Alterar("update rollout set dt_fim=replace(dt_fim,'0020','2018') where dt_fim like '%0020'");
					
					conn.Alterar("update rollout set dt_inicio_bl=replace(dt_inicio_bl,'0018','2018') where dt_inicio_bl like '%0018'");
					conn.Alterar("update rollout set dt_inicio_bl=replace(dt_inicio_bl,'0019','2018') where dt_inicio_bl like '%0019'");
					conn.Alterar("update rollout set dt_inicio_bl=replace(dt_inicio_bl,'0020','2018') where dt_inicio_bl like '%0020'");
					
					conn.Alterar("update rollout set dt_fim_bl=replace(dt_fim_bl,'0018','2018') where dt_fim_bl like '%0018'");
					conn.Alterar("update rollout set dt_fim_bl=replace(dt_fim_bl,'0019','2018') where dt_fim_bl like '%0019'");
					conn.Alterar("update rollout set dt_fim_bl=replace(dt_fim_bl,'0020','2018') where dt_fim_bl like '%0020'");
					
					conn.Alterar("update rollout set value_atbr_field=replace(value_atbr_field,'0018','2018') where value_atbr_field like '%0018'");
					conn.Alterar("update rollout set value_atbr_field=replace(value_atbr_field,'0019','2018') where value_atbr_field like '%0019'");
					conn.Alterar("update rollout set value_atbr_field=replace(value_atbr_field,'0020','2018') where value_atbr_field like '%0020'");
				}
			}else if(opt.equals("6")) {
				param1=req.getParameter("campo");
				query="select tipo from rollout_campos where field_name='"+param1+"' and empresa="+p.getEmpresa().getEmpresa_id();
				System.out.println(query);
				rs=conn.Consulta(query);
				if(rs.next()) {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(rs.getString(1));
				}
			}else if(opt.equals("7")) {
				query="select field_name from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" order by ordenacao";
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="";
					dados_tabela=dados_tabela+"<option value=''>Selecione o Campo</option>";
					while(rs.next()) {
						dados_tabela=dados_tabela+"<option value='"+rs.getString(1)+"'>"+rs.getString(1)+"</option>";
					}
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
			}else if(opt.equals("8")) {
				param1=req.getParameter("linha");
				System.out.println(param1);
    			if(param1.indexOf(",")>0){
    				String []linhas=param1.split(",");
    				for(int j=0;j<linhas.length;j++){
    					if(!linhas[j].equals("")) {
    						conn.Alterar("update rollout set linha_ativa='N',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+linhas[j]+" and empresa="+p.getEmpresa().getEmpresa_id());
    					}
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
			}else if(opt.equals("9")) {
				//System.out.println("server side ");
				int totallinhas=0;
				int total_campos=0;
				int pagina_linhas=0;
				int pagina=0;
				String aux_query="";
				String recid_aux="";
				String filtervalue="";
				String filtercondition="";
				String filterdatafield="";
				String filteroperator="";
				param1=req.getParameter("pagesize");
				param2=req.getParameter("pagenum");
				pagina_linhas=Integer.parseInt(param1);
				pagina=Integer.parseInt(param2);
				rs=conn.Consulta("select count(distinct recid) from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id());
				if(rs.next()) {
					totallinhas=rs.getInt(1);
				}
				rs=conn.Consulta("select count(distinct field_id) from rollout_campos where field_status='ATIVO' and empresa="+p.getEmpresa().getEmpresa_id());
				if(rs.next()) {
					total_campos=rs.getInt(1);
				}
				int filterscount = Integer.parseInt(req.getParameter("filterscount"));
				if(filterscount>0) {
					aux_query=" and value_atbr_field in(";
					 filtervalue = req.getParameter("filtervalue0");
					 filtercondition = req.getParameter("filtercondition0");
					 filterdatafield = req.getParameter("filterdatafield0");
					//filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
					 filteroperator = req.getParameter("filteroperator0");
					String aux_filterdatafield=filterdatafield;
					System.out.println(filtervalue);
					System.out.println(filtercondition);
					System.out.println(filterdatafield);
					System.out.println(filteroperator);
				for (Integer i=0; i < filterscount; i++)
				{
					 filtervalue = req.getParameter("filtervalue" + i);
					 filtercondition = req.getParameter("filtercondition" + i);
					 filterdatafield = req.getParameter("filterdatafield" + i);
					//filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
					 filteroperator = req.getParameter("filteroperator" + i);
					if (filtercondition.equals("EQUAL")) {
						aux_query=aux_query+"'"+filtervalue+"',";
					}
					System.out.println(filtervalue);
					System.out.println(filtercondition);
					System.out.println(filterdatafield);
					System.out.println(filteroperator);
				}
				aux_query=aux_query.substring(0,aux_query.length()-1);
				aux_query=aux_query+")";
				/*query="select distinct recid from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+aux_query;
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.last();
					totallinhas=rs.getRow();
					rs.beforeFirst();
					while(rs.next()) {
						recid_aux=recid_aux+rs.getInt(1)+",";
					}
					recid_aux=recid_aux.substring(0, recid_aux.length()-1);
					aux_query=" and recid in ("+recid_aux+") ";
				}else {
					aux_query="";
				}
				*/
				}else {
					aux_query="";
				}
				String where="";
				aux_query="";
				if (filterscount > 0) {
					where = " WHERE ";
					
					String tmpdatafield = "";
					String tmpfilteroperator = "";
					for (Integer i=0; i < filterscount; i++)
					{
						 filtervalue = req.getParameter("filtervalue" + i);
						 filtercondition = req.getParameter("filtercondition" + i);
						 filterdatafield = req.getParameter("filterdatafield" + i);
						 //filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
						 filteroperator = req.getParameter("filteroperator" + i);
						
						if (tmpdatafield.equals(""))
						{
							tmpdatafield = filterdatafield;			
						}else if (!tmpdatafield.equals(filterdatafield))
						{
							query="select distinct recid from rollout";
							where=where+" ";
							query=query+where+" and linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+aux_query;
							//System.out.println(query);
							rs=conn.Consulta(query);
							if(rs.next()) {
								rs.last();
								totallinhas=totallinhas/total_campos;
								rs.beforeFirst();
								recid_aux="";
								while(rs.next()) {
									recid_aux=recid_aux+rs.getInt(1)+",";
								}
								recid_aux=recid_aux.substring(0, recid_aux.length()-1);
								aux_query=" and recid in ("+recid_aux+") ";
							}else {
								aux_query="";
							}
							where=" WHERE ";
							tmpdatafield = filterdatafield;	
						}else if (tmpdatafield.equals(filterdatafield))
						{
							if (tmpfilteroperator.equals("0"))
							{
								where += " AND ";
							}
							else where += " OR ";	
						}
							
						// build the "WHERE" clause depending on the filter's condition, value and datafield.
						switch(filtercondition)
						{
							case "CONTAINS":
								
								if(filterdatafield.substring(0,7).equals("status_")) {
									String status_aux="";
									if(filtervalue.equals("ok") || filtervalue.equals("completo") || filtervalue.equals("completa") || filtervalue.equals("fim") || filtervalue.equals("finalizada") || filtervalue.equals("feito")) {
										status_aux="Finalizada";
									}else if(filtervalue.equals("iniciada") || filtervalue.equals("iniciado") || filtervalue.equals("ongoing") || filtervalue.equals("started")){
										status_aux="iniciada";
									}else {
										status_aux="*";
									}
									where += " milestone='" + filterdatafield.substring(7,filterdatafield.length()) + "' and status_atividade='" + status_aux + "'";
								}else if(filterdatafield.substring(0,5).equals("resp_")) {
									where += " milestone='" + filterdatafield.substring(5,filterdatafield.length()) + "' and responsavel LIKE '%" + filtervalue + "%'";
								}else if(filterdatafield.substring(0,6).equals("udate_")) {
									where += " milestone='" + filterdatafield.substring(6,filterdatafield.length()) + "' and remark LIKE '%" + filtervalue + "%'";
								}else {
									where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE '%" + filtervalue + "%'";
								}
								break;
							case "CONTAINS_CASE_SENSITIVE":
								where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN":
								where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN_CASE_SENSITIVE":
								where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "EQUAL":
								where += " milestone='" + filterdatafield + "' and value_atbr_field='" + filtervalue + "'";
								break;
							case "EQUAL_CASE_SENSITIVE":
								where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '" + filtervalue + "'";
								break;
							case "NOT_EQUAL":
								where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '" + filtervalue + "'";
								break;
							case "NOT_EQUAL_CASE_SENSITIVE":
								where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE BINARY '" + filtervalue + "'";
								break;
							case "GREATER_THAN":
								where += " milestone='" + filterdatafield + "' and value_atbr_field > '" + filtervalue + "'";
								break;
							case "LESS_THAN":
								where += " milestone='" + filterdatafield + "' and value_atbr_field < '" + filtervalue + "'";
								break;
							case "GREATER_THAN_OR_EQUAL":
								if(filterdatafield.substring(0,9).equals("sdate_pre")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("sdate") + 10, filterdatafield.length()) + "' and str_to_date(dt_inicio_bl,'%d/%m/%Y') >= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else if(filterdatafield.substring(0,9).equals("edate_pre")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("edate") + 10, filterdatafield.length()) + "' and str_to_date(dt_fim_bl,'%d/%m/%Y') >= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else if(filterdatafield.substring(0,5).equals("sdate")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length()) + "' and str_to_date(dt_inicio,'%d/%m/%Y') >= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else if(filterdatafield.substring(0,5).equals("edate")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("edate") + 6, filterdatafield.length()) + "' and str_to_date(dt_fim,'%d/%m/%Y') >= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else {
									where += " milestone='" + filterdatafield + "' and str_to_date(value_atbr_field,'%d/%m/%Y') >= str_to_date('"+filtervalue+"','%d/%m/%Y')";
								}
								break;
							case "LESS_THAN_OR_EQUAL":
								if(filterdatafield.substring(0,9).equals("sdate_pre")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("sdate") + 10, filterdatafield.length()) + "' and str_to_date(dt_inicio_bl,'%d/%m/%Y') <= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else if(filterdatafield.substring(0,9).equals("edate_pre")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("edate") + 10, filterdatafield.length()) + "' and str_to_date(dt_fim_bl,'%d/%m/%Y') <= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else if(filterdatafield.substring(0,5).equals("sdate")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length()) + "' and str_to_date(dt_inicio,'%d/%m/%Y') <= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else if(filterdatafield.substring(0,5).equals("edate")) {
									
									//filterdatafield=filterdatafield.substring(filterdatafield.indexOf("sdate") + 6, filterdatafield.length());
									where += " milestone='" + filterdatafield.substring(filterdatafield.indexOf("edate") + 6, filterdatafield.length()) + "' and str_to_date(dt_fim,'%d/%m/%Y') <= str_to_date('"+filtervalue+"','%d/%m/%Y')";
									//System.out.println("esse e o where do greater than or equal --- "+where);
								}else {
									where += " milestone='" + filterdatafield + "' and str_to_date(value_atbr_field,'%d/%m/%Y') <= str_to_date('"+filtervalue+"','%d/%m/%Y')";
								}
								break;
							case "STARTS_WITH":
								where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE '" + filtervalue + "%'";
								break;
							case "STARTS_WITH_CASE_SENSITIVE":
								where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '" + filtervalue + "%'";
								break;
							case "ENDS_WITH":
								where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE '%" + filtervalue + "'";
								break;
							case "ENDS_WITH_CASE_SENSITIVE":
								where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '%" + filtervalue + "'";
								break;
							case "NULL":
								where += " milestone='" + filterdatafield + "' and value_atbr_field IS NULL";
								break;
							case "NOT_NULL":
								where += " milestone='" + filterdatafield + "' and value_atbr_field IS NOT NULL";
								break;
						}
											
						if (i == filterscount - 1)
						{
							where += " ";
							query="select distinct recid from rollout";
							
							query=query+where+"and linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+aux_query;
							System.out.println(query);
							rs=conn.Consulta(query);
							if(rs.next()) {
								rs.last();
								totallinhas=rs.getRow();
								
								rs.beforeFirst();
								recid_aux="";
								while(rs.next()) {
									recid_aux=recid_aux+rs.getInt(1)+",";
								}
								recid_aux=recid_aux.substring(0, recid_aux.length()-1);
								aux_query=" and recid in ("+recid_aux+") ";
							}else {
								aux_query=" and recid in (0)";
							}
							
						}
							
						tmpfilteroperator = filteroperator;
						tmpdatafield = filterdatafield;			
					}
				}
				//System.out.println(where);
				String imagem_status="";
				dados_tabela="";
				dados_tabela= dados_tabela+"[";
				query="";
				query="select * from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" "+aux_query+" order by recid,siteID,ordenacao limit "+(total_campos*pagina_linhas)+" OFFSET "+(total_campos*pagina_linhas*pagina);
				//System.out.println(query);
    			rs2= conn.Consulta(query);
    			if(rs2.next()){
    				//rs2.last();
					//totallinhas=rs2.getRow();
					//totallinhas=totallinhas/total_campos;
					//rs2.first();
    				String site_aux=rs2.getString("siteID");
    				dados_tabela=dados_tabela+"\n{\"totalRecords\":\""+totallinhas+"\",\"id\":"+rs2.getInt("recid")+",";
    				rs2.beforeFirst();
    				while(rs2.next() ){
    					if (rs2.getString("siteID").equals(site_aux)){
    						if(rs2.getString("tipo_campo").equals("Milestone")){
    							if(rs2.getString("status_atividade").equals("Finalizada")) {
    								imagem_status="finished.png";
    							}else if(rs2.getString("status_atividade").equals("parada")) {
    								imagem_status="stopped.png";
    							}else if(rs2.getString("status_atividade").equals("iniciada")) {
    								imagem_status="started.png";
    							}else {
    								imagem_status="notstarted.png";
    							}
    							dados_tabela=dados_tabela+"\"sdate_"+rs2.getString(6)+"\":\""+rs2.getString("dt_inicio")+"\",\"edate_"+rs2.getString(6)+"\":\""+rs2.getString("dt_fim")+"\",\"sdate_pre_"+rs2.getString(6)+"\":\""+rs2.getString("dt_inicio_bl")+"\",\"edate_pre_"+rs2.getString(6)+"\":\""+rs2.getString("dt_fim_bl")+"\",\"udate_"+rs2.getString(6)+"\":\""+rs2.getString(21)+"\",\"resp_"+rs2.getString(6)+"\": \""+rs2.getString("responsavel")+"\",\"status_"+rs2.getString(6)+"\":\"<img src='img/"+imagem_status+"'>\",";
	    					}else{
	    						dados_tabela=dados_tabela+"\""+rs2.getString(6)+"\":\""+rs2.getString(23)+"\",";
	    					}
    					}else{
    						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
    						dados_tabela=dados_tabela+"},";
    						dados_tabela=dados_tabela+"\n{\"id\":"+rs2.getInt("recid")+",";
    						site_aux=rs2.getString("siteID");
    						rs2.previous();
    					}
    				}
    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"}";
	    			dados_tabela= dados_tabela+"\n"+"]";
    			}else{
    				dados_tabela= dados_tabela+"]";
    			}
    			
    			//System.out.println(dados_tabela);
    			//JSONObject jObj = new JSONObject(dados_tabela);
    			resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
					

			}else if(opt.equals("10")) {
				
			String imagem_status="";
				rs= conn.Consulta("select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" order by ordenacao");
    			
    			dados_tabela= dados_tabela+"{\n"+"\"campos\":[";
    			
    			rs.beforeFirst();
    			if(rs.next()){
    				rs.beforeFirst();
    				
    				while(rs.next()){
    					if(rs.getString("field_type").equals("Milestone")){
    						dados_tabela=dados_tabela+"{ \"name\": \"sdate_pre_"+rs.getString("field_name")+"\", \"type\": \"string\",\"formatStrings\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"edate_pre_"+rs.getString("field_name")+"\", \"type\": \"string\",\"formatStrings\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"sdate_"+rs.getString("field_name")+"\", \"type\": \"string\",\"formatStrings\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"edate_"+rs.getString("field_name")+"\", \"type\": \"string\",\"formatStrings\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"udate_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"resp_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"status_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    						
    						}else{
    						if(rs.getString("tipo").equals("Texto")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Data")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";	
    							
    						}else if(rs.getString("tipo").equals("Numero")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Lista")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							
    						}
    					}
    				}
    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
    				dados_tabela= dados_tabela+"\n"+"],";
    				dados_tabela= dados_tabela+"\n"+"\"campos2\":[";
    				rs.beforeFirst();
    				while(rs.next()) {
    					if(rs.getString("field_type").equals("Milestone")){
    						dados_tabela=dados_tabela+"{ \"id\": \"sdate_pre_"+rs.getString("field_name")+"\", \"label\": \"InicioPlanejado_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"edate_pre_"+rs.getString("field_name")+"\", \"label\": \"FimPlanejado_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"sdate_"+rs.getString("field_name")+"\", \"label\": \"InicioReal_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"edate_"+rs.getString("field_name")+"\", \"label\": \"FimReal_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"udate_"+rs.getString("field_name")+"\", \"label\": \"Comentarios_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"resp_"+rs.getString("field_name")+"\", \"label\": \"Responsavel_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"status_"+rs.getString("field_name")+"\", \"label\": \"Status_"+rs.getString("field_name")+"\"},"+ "\n";
    						
    						}else{
    						if(rs.getString("tipo").equals("Texto")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Data")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";	
    							
    						}else if(rs.getString("tipo").equals("Numero")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Lista")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";
    							
    						}
    					}
    				}
    			}else{
    				dados_tabela="[]";
    			}
    			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
    			
    			dados_tabela= dados_tabela+"\n"+"],";
    			
    			dados_tabela= dados_tabela+"\n"+"\"records\":[";
    			rs2= conn.Consulta("select * from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" order by recid desc,ordenacao asc limit "+(r.getCampos().get_campos_quantidade(conn, p))*200+" ");
    			if(rs2.next()){
    				String site_aux=rs2.getString("siteID");
    				dados_tabela=dados_tabela+"\n{\"id\":"+rs2.getInt("recid")+",";
    				rs2.beforeFirst();
    				while(rs2.next() ){
    					if (rs2.getString("siteID").equals(site_aux)){
    						if(rs2.getString("tipo_campo").equals("Milestone")){
    							if(rs2.getString("status_atividade").equals("Finalizada")) {
    								imagem_status="Finalizada";
    							}else if(rs2.getString("status_atividade").equals("parada")) {
    								imagem_status="Parada";
    							}else if(rs2.getString("status_atividade").equals("iniciada")) {
    								imagem_status="Iniciada";
    							}else {
    								imagem_status="Nao Iniciada";
    							}
    							dados_tabela=dados_tabela+"\"sdate_"+rs2.getString(6)+"\":\""+rs2.getString("dt_inicio")+"\",\"edate_"+rs2.getString(6)+"\":\""+rs2.getString("dt_fim")+"\",\"sdate_pre_"+rs2.getString(6)+"\":\""+rs2.getString("dt_inicio_bl")+"\",\"edate_pre_"+rs2.getString(6)+"\":\""+rs2.getString("dt_fim_bl")+"\",\"udate_"+rs2.getString(6)+"\":\""+rs2.getString(21)+"\",\"resp_"+rs2.getString(6)+"\": \""+rs2.getString("responsavel")+"\",\"status_"+rs2.getString(6)+"\":\""+imagem_status+"\",";
	    					}else{
	    						dados_tabela=dados_tabela+"\""+rs2.getString(6)+"\":\""+rs2.getString(23)+"\",";
	    					}
    					}else{
    						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
    						dados_tabela=dados_tabela+"},";
    						dados_tabela=dados_tabela+"\n{\"id\":"+rs2.getInt("recid")+",";
    						site_aux=rs2.getString("siteID");
    						rs2.previous();
    					}
    				}
    			}else{
    				dados_tabela="[]";
    			}
    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
    				dados_tabela= dados_tabela+"}";
	    			dados_tabela= dados_tabela+"\n"+"]}";
    			//System.out.println(dados_tabela);
    			resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
    			
			}else if(opt.equals("11")) {
				param1=req.getParameter("pivot");
				param2=req.getParameter("nome");
				param3=req.getParameter("filtros");
				param3=param3.replaceAll("'", "*");
				//System.out.println(param1);
				//System.out.println(param2);
				query="";
				query="insert into pivot_table(name_pivot,dt_add,ativo,user_add,empresa,pivot_source,filter_pivot) values ('"+param2+"','"+time+"','Y','"+p.get_PessoaUsuario()+"',"+p.getEmpresa().getEmpresa_id()+",'"+param1+"','"+param3+"')";
				if(conn.Inserir_simples(query)) {
					resp.setContentType("application/html");  
		  		    resp.setCharacterEncoding("UTF-8"); 
		  		    PrintWriter out = resp.getWriter();
				    out.print("Relatório Salvo com sucesso.");
				}else {
					resp.setContentType("application/html");  
		  		    resp.setCharacterEncoding("UTF-8"); 
		  		    PrintWriter out = resp.getWriter();
				    out.print("Erro no salvamento do relatório");
				}
			}else if(opt.equals("12")) {
				query="";
				dados_tabela="";
				query="select * from pivot_table where ativo='Y' and empresa="+p.getEmpresa().getEmpresa_id();
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					while(rs.next()) {
						dados_tabela=dados_tabela+"<option value='"+rs.getInt(1)+"'>"+rs.getString("name_pivot")+"</option>";
					}
					resp.setContentType("application/html");  
		  		    resp.setCharacterEncoding("UTF-8"); 
		  		    PrintWriter out = resp.getWriter();
				    out.print(dados_tabela);
				}
			}else if(opt.equals("13")) {
				System.out.println("Carregando pivot 1");
				param1=req.getParameter("id");
				String recid_aux="";
				String json_aux="";
				rs= conn.Consulta("select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" order by ordenacao");
    			
    			dados_tabela= dados_tabela+"{\n"+"\"campos\":[";
    			
    			rs.beforeFirst();
    			if(rs.next()){
    				rs.beforeFirst();
    				
    				while(rs.next()){
    					if(rs.getString("field_type").equals("Milestone")){
    						dados_tabela=dados_tabela+"{ \"name\": \"sdate_pre_"+rs.getString("field_name")+"\", \"type\": \"date\",\"format\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"edate_pre_"+rs.getString("field_name")+"\", \"type\": \"date\",\"format\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"sdate_"+rs.getString("field_name")+"\", \"type\": \"date\",\"format\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"edate_"+rs.getString("field_name")+"\", \"type\": \"date\",\"format\":\"dd/MM/yyyy\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"udate_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"resp_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"status_"+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    						
    						}else{
    						if(rs.getString("tipo").equals("Texto")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Data")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"date\",\"format\":\"dd/MM/yyyy\"},"+ "\n";	
    							
    						}else if(rs.getString("tipo").equals("Numero")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Lista")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							
    						}
    					}
    				}
    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
    				dados_tabela= dados_tabela+"\n"+"],";
    				System.out.println("Campos 1 definido");
    				dados_tabela= dados_tabela+"\n"+"\"campos2\":[";
    				rs.beforeFirst();
    				while(rs.next()) {
    					if(rs.getString("field_type").equals("Milestone")){
    						dados_tabela=dados_tabela+"{ \"id\": \"sdate_pre_"+rs.getString("field_name")+"\", \"label\": \"InicioPlanejado_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"edate_pre_"+rs.getString("field_name")+"\", \"label\": \"FimPlanejado_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"sdate_"+rs.getString("field_name")+"\", \"label\": \"InicioReal_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"edate_"+rs.getString("field_name")+"\", \"label\": \"FimReal_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"udate_"+rs.getString("field_name")+"\", \"label\": \"Comentarios_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"resp_"+rs.getString("field_name")+"\", \"label\": \"Responsavel_"+rs.getString("field_name")+"\"},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"id\": \"status_"+rs.getString("field_name")+"\", \"label\": \"Status_"+rs.getString("field_name")+"\"},"+ "\n";
    						
    						}else{
    						if(rs.getString("tipo").equals("Texto")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Data")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";	
    							
    						}else if(rs.getString("tipo").equals("Numero")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";
    							
    						}else if(rs.getString("tipo").equals("Lista")){
    							dados_tabela=dados_tabela+"{ \"id\": \""+rs.getString("field_name")+"\", \"label\": \""+rs.getString("field_name")+"\"},"+ "\n";
    							
    						}
    					}
    				}
    			}else{
    				dados_tabela="[]";
    			}
    			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
    			
    			dados_tabela= dados_tabela+"\n"+"],";
    			System.out.println("Campos 2 definido");
    			ConexaoMongo c = new ConexaoMongo();
    			FindIterable<Document> findIterable = c.ConsultaSimplesSemFiltro("rollout");
    			dados_tabela= dados_tabela+"\n"+"\"records\":[";
    			MongoCursor<Document> doccursor = findIterable.iterator();
    			while(doccursor.hasNext()) {
    				json_aux=doccursor.next().toJson();
    				json_aux=json_aux.replace("\"Milestone\" : [{", "");
    				json_aux=json_aux.replace("\" }, {", "\",");
    				json_aux=json_aux.replace("}]", "");
    				dados_tabela= dados_tabela+"\n"+json_aux+",";
    				
    			}
    			
    			    dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
	    			dados_tabela= dados_tabela+"\n"+"],";
	    			time = new Timestamp(System.currentTimeMillis());
    				System.out.println("MOntagem de linhas da pivot finalizada:"+f3.format(time));
	    			System.out.println("records definido");
	    			query="select * from pivot_table where id_pivot="+param1; 
	    			rs=conn.Consulta(query);
	    			if(rs.next()) {
	    				JSONObject jObj = new JSONObject(rs.getString("pivot_source"));
	    				dados_tabela= dados_tabela+"\n \"rows\":";
	    				dados_tabela= dados_tabela+jObj.get("rows").toString();
	    				dados_tabela= dados_tabela+"," ;
	    				dados_tabela= dados_tabela+"\n \"columns\":";
	    				dados_tabela= dados_tabela+jObj.get("columns").toString();
	    				dados_tabela= dados_tabela+"," ;
	    				dados_tabela= dados_tabela+"\n \"values\":";
	    				dados_tabela= dados_tabela+jObj.get("values").toString();
	    				dados_tabela= dados_tabela+"," ;
	    				jObj = new JSONObject(rs.getString("filter_pivot"));
	    				dados_tabela= dados_tabela+"\n \"Filters\":";
	    				dados_tabela= dados_tabela+jObj.toString();
	    				
	    				dados_tabela= dados_tabela+"}" ;
	    			}
    			//System.out.println(dados_tabela);
    			resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
				 
			}else if(opt.equals("14")) {
				System.out.println("iniciando sincronia de rollout com MongoDB");
				String imagem_status="";
				ConexaoMongo c = new ConexaoMongo();
				c.RemoverMuitosSemFiltro("rollout");
				Document document;
				Document milestone = new Document();
				Document atividade = new Document();
				List<Document> lista_atividade = new ArrayList<Document>();
				rs2= conn.Consulta("select * from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" order by recid,siteID,ordenacao");
    			if(rs2.next()){
    				time = new Timestamp(System.currentTimeMillis());
    				System.out.println("Consulta finalizada no rollout:"+f3.format(time));
    				String site_aux=rs2.getString("siteID");
    				document = new Document("recid", rs2.getInt("recid")).append("Empresa",rs2.getInt(28)).append("Linha_ativa", rs2.getString(26));
    				
    				rs2.beforeFirst();
    				while(rs2.next() ){
    					if (rs2.getString(3).equals(site_aux)){
    						if(rs2.getString(22).equals("Milestone")){
    							imagem_status=rs2.getString(30);
    							atividade=new Document("Milestone", rs2.getString(6))
    									.append("sdate_"+rs2.getString(6), rs2.getString(7))
    									.append("edate_"+rs2.getString(6), rs2.getString(8))
    									.append("sdate_pre_"+rs2.getString(6), rs2.getString(11))
    									.append("edate_pre_"+rs2.getString(6), rs2.getString(12))
    									.append("udate_"+rs2.getString(6), rs2.getString(21))
    									.append("resp_"+rs2.getString(6), rs2.getString(10))
    									.append("status_"+rs2.getString(6), imagem_status)
    							        .append("duracao_"+rs2.getString(6), "");
    							lista_atividade.add(atividade);
    						}else{
    							document.append(rs2.getString(6), rs2.getString(23));
	    						
	    					}
    					}else{
    						document.append("Milestone", lista_atividade);
    						document.append("Update_by", "masteradmin");
    						document.append("Update_time", time.toString());
    						
    						c.InserirSimpels("rollout", document);
    						milestone = new Document();
    						lista_atividade.clear();
    						document = new Document("recid", rs2.getInt("recid")).append("Empresa",rs2.getInt(28)).append("Linha_ativa", rs2.getString(26));
    						site_aux=rs2.getString(3);
    						rs2.previous();
    					}
    				}
			}
    			c.RemoverMuitosSemFiltro("rolloutCampos");
    			document= new Document();
    			rs=conn.Consulta("select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and field_status='ATIVO'");
    			if(rs.next()) {
    				System.out.println("entrou no if do rs");
    				String CampoNome="";
    				int colunas = rs.getMetaData().getColumnCount();
    				rs.beforeFirst();
    				while(rs.next()) {
    					System.out.println("entrou no while do rs");
    					for (int i=1;i<=colunas;i++) {
    						CampoNome=rs.getMetaData().getColumnName(i);
        					document.append(CampoNome, rs.getObject(i));
    					}
    					document.append("Update_by", "masteradmin");
						document.append("Update_time", time.toString());
    					c.InserirSimpels("rolloutCampos", document);
    					document.clear();
    				}
    			}
    			c.fecharConexao();
    			System.out.println("Sicronia com Mongo Finalizada");
			}else if(opt.equals("15")) {
				ConexaoMongo c = new ConexaoMongo();
				//System.out.println("Salvando Campos...");
                param1=req.getParameter("mudancas");
                int cont1=0;
                int cont2=0;
                int tamanho=0;
                int tamanho2=0;
                Document filtros=new Document();
                Document updates=new Document();
                Document update = new Document();
                String plano;
                String cmp;
                String[] elementNames;
                 time = new Timestamp(System.currentTimeMillis());
                JSONObject jObj2=null;
                JSONObject jObj = new JSONObject(param1); 
                //System.out.println(jObj.get("campos"));
    			JSONArray campos = jObj.getJSONArray("campos");
    			tamanho=campos.length();
    			if(tamanho!=0){
    			//System.out.println(jObj.getJSONArray("colunas").getJSONObject(1).getJSONObject("editable").get("Type"));
    			//System.out.println(campos);
    			//JSONArray campos2=campos.getJSONArray(0);
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    			//Date date ;
    			LocalDate ldate=null;
				//DateFormat f2 = DateFormat.getDateInstance(DateFormat.SHORT, brasil);
    			while(cont1<tamanho){
    				jObj2=campos.getJSONObject(cont1);
    				elementNames = JSONObject.getNames(jObj2);
    				//System.out.println(jObj2.getString("colum"));
    				//tamanho2=jObj2.length();
    				query="";
    				if(jObj2.getString("colum").contains("sdate_pre")){
						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+5);
						//System.out.println("primeiro if inicio plan");
						//System.out.println(jObj2.getString("colum"));
						plano=jObj2.getString("value");
						//ldate = LocalDate.parse(plano.toString(), formatter);
						query="update rollout set dt_inicio_bl='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
						filtros.append("recid" , jObj2.getInt("id"));
						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
						filtros.append("Milestone.Milestone" , cmp);
						updates.append("Milestone.$.sdate_pre_"+cmp, plano);
						updates.append("update_by", p.get_PessoaUsuario());
						updates.append("update_time", time.toString());
						update = new Document();
				        update.append("$set", updates);
				       
					//System.out.println(filtros.toJson().toString());
					//System.out.println(update.toJson().toString());
					c.AtualizaUm("rollout", filtros, update);
						if(!conn.Alterar(query)){
							//System.out.println("Erro de Update");
							query="insert into rollout (recid,siteID,dt_inicio_bl,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
							conn.Inserir_simples(query);
						}
					}else if(jObj2.getString("colum").contains("edate_pre")){
						//System.out.println("segundo if fim plan");
						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+5);
						plano=jObj2.getString("value");
						//ldate = LocalDate.parse(plano.toString(), formatter);
						query="update rollout set dt_fim_bl='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
						filtros.append("recid" , jObj2.getInt("id"));
						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
						filtros.append("Milestone.Milestone" , cmp);
						updates.append("Milestone.$.edate_pre_"+cmp, plano);
						updates.append("update_by", p.get_PessoaUsuario());
						updates.append("update_time", time.toString());
						update = new Document();
				        update.append("$set", updates);
				       
					//System.out.println(filtros.toJson().toString());
					//System.out.println(update.toJson().toString());
					c.AtualizaUm("rollout", filtros, update);
						if(!conn.Alterar(query)){
							//System.out.println("Erro de Update");
							query="insert into rollout (recid,siteID,dt_fim_bl,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
							conn.Inserir_simples(query);
						}
					}else if(jObj2.getString("colum").contains("sdate")){
    						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+1);
    						//System.out.println("primeiro if inicio plan");
    						//System.out.println(jObj2.getString("colum"));
    						plano=jObj2.getString("value");
    						//ldate = LocalDate.parse(plano.toString(), formatter);
    						query="update rollout set dt_inicio='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    						filtros.append("recid" , jObj2.getInt("id"));
    						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    						filtros.append("Milestone.Milestone" , cmp);
    						updates.append("Milestone.$.sdate_"+cmp, plano);
    						updates.append("update_by", p.get_PessoaUsuario());
    						updates.append("update_time", time.toString());
    						
    				       
    					//System.out.println(filtros.toJson().toString());
    					//System.out.println(update.toJson().toString());
    					
    						if(!conn.Alterar(query)){
    							//System.out.println("Erro de Update");
    							query="insert into rollout (recid,siteID,dt_inicio,milestone,tipo_campo,empresa,update_by,update_time,status_atividade) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"','iniciada')";
    							conn.Inserir_simples(query);
    						}
    							if(!plano.equals("")) {
    								conn.Alterar("update rollout set status_atividade='iniciada',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id());
    								updates.append("Milestone.$.status_"+cmp, "iniciada");
    							}
    							update = new Document();
        				        update.append("$set", updates);
        				        c.AtualizaUm("rollout", filtros, update);
    					}else if(jObj2.getString("colum").contains("edate")){
    						//System.out.println("segundo if fim plan");
    						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+1);
    						plano=jObj2.getString("value");
    						//ldate = LocalDate.parse(plano.toString(), formatter);
    						query="update rollout set dt_fim='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    						filtros.append("recid" , jObj2.getInt("id"));
    						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    						filtros.append("Milestone.Milestone" , cmp);
    						updates.append("Milestone.$.edate_"+cmp, plano);
    						updates.append("update_by", p.get_PessoaUsuario());
    						updates.append("update_time", time.toString());
    						
    				       
    					//System.out.println(filtros.toJson().toString());
    					//System.out.println(update.toJson().toString());
    					
    						if(!conn.Alterar(query)){
    							//System.out.println("Erro de Update");
    							query="insert into rollout (recid,siteID,dt_fim,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    							conn.Inserir_simples(query);
    						}
    							if(!plano.equals("")) {
    								conn.Alterar("update rollout set status_atividade='Finalizada',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id());
    								updates.append("Milestone.$.status_"+cmp, "Finalizada");
    							}
    							update = new Document();
        				        update.append("$set", updates);
        				        c.AtualizaUm("rollout", filtros, update);
    						query="update faturamento set milestone_data_fim_real='"+plano+"',status_faturamento='Liberado para Faturar' where id_rollout='site"+jObj2.getInt("id")+ "' and milestones_trigger='"+cmp+"'";
    						if(conn.Alterar(query)){
    							System.out.println("tabela de Faturamento Atualizada!");
    						}
    					}else if(jObj2.getString("colum").contains("udate")){
    						//System.out.println("terceiro if obs plan");
    						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+1);
    						//time = new Timestamp(jObj2.getString(jObj2.getString("colum")));
    						query="update rollout set remark='"+jObj2.getString("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    						filtros.append("recid" , jObj2.getInt("id"));
    						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    						filtros.append("Milestone.Milestone" , cmp);
    						updates.append("Milestone.$.udate_"+cmp, jObj2.getString("value"));
    						updates.append("update_by", p.get_PessoaUsuario());
    						updates.append("update_time", time.toString());
    						update = new Document();
    				        update.append("$set", updates);
    				       
    					//System.out.println(filtros.toJson().toString());
    					//System.out.println(update.toJson().toString());
    					c.AtualizaUm("rollout", filtros, update);
    						if(!conn.Alterar(query)){
    							System.out.println("Erro de Update");
    							query="insert into rollout (recid,siteID,remark,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+jObj2.getString("value")+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    							conn.Inserir_simples(query);
    						}
    					}else if(!jObj2.getString("colum").contains("id")){
    						cmp=jObj2.getString("colum");
    						//System.out.println("quarto if atributos");
    						
    						if(jObj2.getString("tipoc").equals("textbox")||jObj2.getString("tipoc").equals("combobox")){
    							if(cmp.contains("resp_")) {
    								query="update rollout set responsavel='"+jObj2.getString("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp.substring(5, cmp.length())+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    								filtros.append("recid" , jObj2.getInt("id"));
    								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    								filtros.append("Milestone.Milestone" , cmp.substring(5, cmp.length()));
    								updates.append("Milestone.$."+cmp, jObj2.getString("value"));
    								updates.append("update_by", p.get_PessoaUsuario());
    								updates.append("update_time", time.toString());
    							}else {
    								query="update rollout set value_atbr_field='"+jObj2.getString("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    								query2="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo,empresa,update_by,update_time) values ("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+cmp+"','"+jObj2.getString("value")+"','Atributo',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    								filtros.append("recid" , jObj2.getInt("id"));
    								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    								updates.append(cmp, jObj2.getString("value"));
    								updates.append("update_by", p.get_PessoaUsuario());
    								updates.append("update_time", time.toString());
    							}
    						}else if(jObj2.getString("tipoc").equals("datetimeinput")){
    							plano=jObj2.getString("value");
	    						//ldate = LocalDate.parse(plano.toString(), formatter);
	    						query="update rollout set value_atbr_field='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
	    						query2="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo,empresa,update_by,update_time) values ("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+cmp+"','"+plano+"','Atributo',"+p.getEmpresa().getEmpresa_id()+")";
	    						filtros.append("recid" , jObj2.getInt("id"));
								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
								updates.append(cmp, jObj2.getString("value"));
								updates.append("update_by", p.get_PessoaUsuario());
								updates.append("update_time", time.toString());
    						}else if(jObj2.getString("tipoc").equals("int")){
    							query="update rollout set value_atbr_field='"+jObj2.getInt("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    							query2="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo,empresa,update_by,update_time) values ("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+cmp+"','"+jObj2.getInt("value")+"','Atributo',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    							filtros.append("recid" , jObj2.getInt("id"));
								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
								updates.append(cmp, jObj2.getString("value"));
								updates.append("update_by", p.get_PessoaUsuario());
								updates.append("update_time", time.toString());
    						
    						}
    						
    						    update = new Document();
    					        update.append("$set", updates);
    					       
    						System.out.println(filtros.toJson().toString());
    						System.out.println(update.toJson().toString());
    						c.AtualizaUm("rollout", filtros, update);
    						if(!conn.Alterar(query)){
    							System.out.println("Erro de Update");
    							//conn.Inserir_simples(query2);
    							
    						}
    					}
    				
    				cont1++;
    			}
    			//precisa usar o getnames para pegar os nomes dos campos que foram atualizados);
    			//Timestamp time = new Timestamp(System.currentTimeMillis());
    			}
    			//System.out.println(campos2.get(1));
    			c.fecharConexao();
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Rollout Atualizado!");
				
				
			
			}
		}catch (SQLException e) {
			conn.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public Date checa_formato_data(String data) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date d1=format.parse(data);
			return d1;
		}catch (ParseException e) {
			System.out.println(data + " - Data inválida");
			return null;
			
		} 
	}
	public void insere_linha_rollout(Conexao conn,HttpServletRequest req, HttpServletResponse resp,Pessoa p) {
		
		String query="";
		ResultSet rs;
		String param1;
		Timestamp time = new Timestamp(System.currentTimeMillis());
		System.out.println("Adicionando linha ao rollout ...");
		param1=req.getParameter("linha");
		int linha_id=0;
		String site_id="";
		JSONObject jObj = new JSONObject(param1); 
		//System.out.println(jObj.toString());
		JSONArray campos = jObj.getJSONArray("rows");
		query="select distinct recid from rollout where empresa="+p.getEmpresa().getEmpresa_id()+" order by recid desc limit 1";
		rs=conn.Consulta(query);
		try {
			if(rs.next()){
				linha_id=rs.getInt(1);
			}else{
				linha_id=0;
			}
		
		for(int i=0;i<campos.length();i++){
			
			if(campos.getJSONObject(i).getString("nome_campo").equals("siteID")){
				site_id=campos.getJSONObject(i).getString("valor_campo");
				break;
			}
			//query="insert into rollout (recid,siteID,milestone) values()";
		}
		linha_id=linha_id+1;
		String nome_campo="";
		int ordem=0;
		for(int i=0;i<campos.length();i++){
			if(!campos.getJSONObject(i).getString("nome_campo").equals("recid")){
				nome_campo=campos.getJSONObject(i).getString("nome_campo").replace("_inicio", "");
				nome_campo=campos.getJSONObject(i).getString("nome_campo").replace("_fim", "");
				query="select ordenacao from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and field_name='"+nome_campo+"'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					ordem=rs.getInt(1);
				}else {
					ordem=0;
				}
				if(campos.getJSONObject(i).getString("nome_campo").contains("_inicio")){
					query="select milestone from rollout where milestone='"+campos.getJSONObject(i).getString("nome_campo").replace("_inicio", "")+"' and recid="+linha_id +" and empresa="+p.getEmpresa().getEmpresa_id();
					rs=conn.Consulta(query);
					if(rs.next()){
						query="update rollout set dt_inicio_bl='"+campos.getJSONObject(i).getString("valor_campo")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"',ordenacao="+ordem+" where milestone='"+campos.getJSONObject(i).getString("nome_campo").replace("_inicio", "")+"' and recid="+linha_id+" and empresa="+p.getEmpresa().getEmpresa_id();
						conn.Alterar(query);
						continue;
					}else{
					query="insert into rollout (recid,siteID,milestone,tipo_campo,dt_inicio_bl,empresa,update_by,update_time,ordenacao) values("+linha_id+",'site"+linha_id+"','"+campos.getJSONObject(i).getString("nome_campo").replace("_inicio", "")+"','"+campos.getJSONObject(i).getString("tipo_campo")+"','"+campos.getJSONObject(i).getString("valor_campo")+"',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"',"+ordem+")";
					}
				}else if(campos.getJSONObject(i).getString("nome_campo").contains("_fim")){
					query="select milestone from rollout where milestone='"+campos.getJSONObject(i).getString("nome_campo").replace("_fim", "")+"' and recid="+linha_id;
					rs=conn.Consulta(query);
					if(rs.next()){
						query="update rollout set dt_fim_bl='"+campos.getJSONObject(i).getString("valor_campo")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"',ordenacao="+ordem+" where milestone='"+campos.getJSONObject(i).getString("nome_campo").replace("_fim", "")+"' and recid="+linha_id + " and empresa="+p.getEmpresa().getEmpresa_id();
						conn.Alterar(query);
						continue;
					}else{
					query="insert into rollout (recid,siteID,milestone,tipo_campo,dt_fim_bl,empresa,update_by,update_time,ordencao) values("+linha_id+",'site"+linha_id+"','"+campos.getJSONObject(i).getString("nome_campo").replace("_fim", "")+"','"+campos.getJSONObject(i).getString("tipo_campo")+"','"+campos.getJSONObject(i).getString("valor_campo")+"',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"',"+ordem+")";
					}
				}else{
				query="insert into rollout (recid,siteID,milestone,tipo_campo,value_atbr_field,empresa,update_by,update_time,ordenacao) values("+linha_id+",'site"+linha_id+"','"+campos.getJSONObject(i).getString("nome_campo")+"','"+campos.getJSONObject(i).getString("tipo_campo")+"','"+campos.getJSONObject(i).getString("valor_campo")+"',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"',"+ordem+")";
				}
			}
			conn.Inserir_simples(query);
		}
		resp.setContentType("application/html");  
		resp.setCharacterEncoding("UTF-8"); 
		PrintWriter out = resp.getWriter();
		out.print(linha_id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
