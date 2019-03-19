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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
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
import javax.swing.text.ChangedCharSetException;

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

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mysql.cj.x.protobuf.MysqlxCrud.Update;
import com.sun.mail.imap.protocol.Status;

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
		HttpSession session = req.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null) {
			resp.sendRedirect("./mstp_login.html");
			return;
		}
		
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
		
		Conexao conn = (Conexao) session.getAttribute("conexao");
		ConexaoMongo c = new ConexaoMongo();
		Rollout r = new Rollout();
		JSONObject campo_tipo=r.getCampos().getCampos_tipo(conn, p);
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
				//atualiza_sites_integrados(p);
				JSONObject rollout_campos=r.getCampos().getCampos_tipo(conn,p);
				Iterator<String>campos_nomes=rollout_campos.keys();
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
    						dados_tabela=dados_tabela+"{ \"name\": \"duracao_"+rs.getString("field_name")+"\", \"type\": \"int\",\"editable\":False},"+ "\n";
    						dados_tabela=dados_tabela+"{ \"name\": \"status_"+rs.getString("field_name")+"\", \"type\": \"string\",\"editable\":False},"+ "\n";
    						
    						campos_aux=campos_aux+"{ \"text\": \"Inicio Previsto\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"sdate_pre_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Fim Previsto\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"edate_pre_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Inicio Real\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"sdate_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100,\"validation\":\"validaDataActual\"},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Fim Real\", \"columngroup\":\""+rs.getString("field_name")+"\" , \"datafield\":\"edate_"+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\", \"cellsformat\":\"dd/MM/yyyy\",\"width\":100,\"validation\":\"validaDataActual\"},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Anotacões\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"udate_"+rs.getString("field_name")+"\",\"columntype\": \"textbox\",\"width\":100},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Responsavel\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"resp_"+rs.getString("field_name")+"\",\"columntype\": \"combobox\",\"width\":200},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Duração(D)\", \"columngroup\":\""+rs.getString("field_name")+"\", \"datafield\":\"duracao_"+rs.getString("field_name")+"\",\"editable\":False,\"width\":100,\"cellsalign\": \"center\"},"+ "\n";
    						campos_aux=campos_aux+"{ \"text\": \"Status\", \"columngroup\":\""+rs.getString("field_name")+"\", \"filtertype\": \"checkedlist\",\"cellsalign\": \"center\",\"datafield\":\"status_"+rs.getString("field_name")+"\",\"editable\":False,\"columntype\": \"combobox\",\"width\":100},"+ "\n";
    					}else{
    						if(rs.getString("tipo").equals("Texto")){
    							
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							if(rs.getString("field_name").equals("Site ID")) {
    								campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"cellsrenderer\": \"siteMarcadoIntegrado\",\"columntype\": \"textbox\",\"width\":80},"+ "\n";
    							}else {
    								campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"textbox\",\"width\":80},"+ "\n";
    							}
    						}else if(rs.getString("tipo").equals("Data")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"date\"},"+ "\n";	
    							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"filtertype\": \"date\",\"columntype\": \"datetimeinput\",\"cellsformat\":\"dd/MM/yyyy\",\"width\":100},"+ "\n";
    						}else if(rs.getString("tipo").equals("Numero")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"int\"},"+ "\n";
    							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"width\":80},"+ "\n";
    						}else if(rs.getString("tipo").equals("Lista")){
    							dados_tabela=dados_tabela+"{ \"name\": \""+rs.getString("field_name")+"\", \"type\": \"string\"},"+ "\n";
    							campos_aux=campos_aux+"{ \"text\": \""+rs.getString("field_name")+"\",\"datafield\":\""+rs.getString("field_name")+"\",\"columntype\": \"combobox\", \"width\":80},"+ "\n";
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
    			//rs2= conn.Consulta("select * from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" order by recid,siteID,ordenacao limit 1600");
    			/*FindIterable<Document> findIterable = c.ConsultaSimplesSemFiltroInicioLimit("rollout",0,40);
    			MongoCursor<Document> resultado = findIterable.iterator();
    			Document linha=new Document();
    			Document milestone_doc=new Document();
    			String nome_campo_aux="";
    			while(resultado.hasNext()) {
    				linha=(Document) resultado.next();
    				List<Document> milestones=(List<Document>) linha.get("Milestone");
    				//String site_aux=linha.getString("Site ID");
    				dados_tabela=dados_tabela+"\n{\"id\":"+linha.getInteger("recid")+",";
    				//System.out.println("recID:"+linha.getInteger("recid"));
    				campos_nomes=rollout_campos.keys();
    				while(campos_nomes.hasNext()) {
    					nome_campo_aux=campos_nomes.next();
    					if(rollout_campos.getJSONArray(nome_campo_aux).get(0).equals("Atributo")) {
		    				if(rollout_campos.getJSONArray(nome_campo_aux).get(1).equals("Data")) {
		    					//System.out.println("transformando data de :"+nome_campo_aux +" - valor string:"+linha.getString(nome_campo_aux));
		    					if(linha.get(nome_campo_aux)!=null && !linha.get(nome_campo_aux).equals("")) {
		    						dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\""+f2.format(linha.getDate(nome_campo_aux))+"\",";
		    					}else {
		    						dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\""+linha.getString(nome_campo_aux)+"\",";
		    					}
		    				}else {
		    					dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\""+linha.getString(nome_campo_aux)+"\",";
		    				}
    					}else {
    						int i=0;
    						while(i<milestones.size()) {
    							milestone_doc=milestones.get(i);
    							if(milestone_doc.getString("Milestone").equals(nome_campo_aux)) {
    								if(milestone_doc.getDate("sdate_"+nome_campo_aux)!=null) {
    									dados_tabela=dados_tabela+"\"sdate_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("sdate_"+nome_campo_aux))+"\",";
    								}else {
    									dados_tabela=dados_tabela+"\"sdate_"+nome_campo_aux+"\":\"\",";
    								}
    								if(milestone_doc.getDate("edate_"+nome_campo_aux)!=null) {
    									dados_tabela=dados_tabela+"\"edate_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("edate_"+nome_campo_aux))+"\",";
    								}else {
    									dados_tabela=dados_tabela+"\"edate_"+nome_campo_aux+"\":\"\",";
    								}
    								if(milestone_doc.getDate("sdate_pre_"+nome_campo_aux)!=null) {
    									dados_tabela=dados_tabela+"\"sdate_pre_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("sdate_pre_"+nome_campo_aux))+"\",";
    								}else {
    									dados_tabela=dados_tabela+"\"sdate_pre_"+nome_campo_aux+"\":\"\",";
    								}
    								if(milestone_doc.getDate("edate_pre_"+nome_campo_aux)!=null) {
    									dados_tabela=dados_tabela+"\"edate_pre_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("edate_pre_"+nome_campo_aux))+"\",";
    								}else{
    									dados_tabela=dados_tabela+"\"edate_pre_"+nome_campo_aux+"\":\"\",";
    								}
    								dados_tabela=dados_tabela+"\"udate_"+nome_campo_aux+"\":\""+milestone_doc.getString("udate_"+nome_campo_aux)+"\",";
    								dados_tabela=dados_tabela+"\"resp_"+nome_campo_aux+"\":\""+milestone_doc.getString("resp_"+nome_campo_aux)+"\",";
    								imagem_status="";
    								if(milestone_doc.getString("status_"+nome_campo_aux).equals("Finalizada")) {
        								imagem_status="finished.png";
        							}else if(milestone_doc.getString("status_"+nome_campo_aux).equals("parada")) {
        								imagem_status="stopped.png";
        							}else if(milestone_doc.getString("status_"+nome_campo_aux).equals("iniciada")) {
        								imagem_status="started.png";
        							}else {
        								imagem_status="notstarted.png";
        							}
    								dados_tabela=dados_tabela+"\"status_"+nome_campo_aux+"\":\"<img src='img/"+imagem_status+"'>\",";
    								dados_tabela=dados_tabela+"\"duracao_"+nome_campo_aux+"\":\""+milestone_doc.getString("duracao_"+nome_campo_aux)+"\",";
    								i=999;
    							}
    							i=i+1;
    						}
    					}
	    			}
    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
    				dados_tabela=dados_tabela+"},";
    				}
    			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);*/
    			//dados_tabela= dados_tabela+"],";
    			/*if(rs2.next()){
    				
    				
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
    				}*/
    			
    				List<String> rollout_filtrado_integrado = new ArrayList<String>();
    				Bson filtro;
    				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
    				List<Bson> rollout_filtrado_integrado_filtro = new ArrayList<Bson>();
    				rollout_filtrado_integrado_filtro.add(filtro);
    				FindIterable<Document> findIterable=c.ConsultaCollectioncomFiltrosLista("Rollout_Sites", rollout_filtrado_integrado_filtro); 
    				findIterable.forEach((Block<Document>) doc -> {
    					
    					rollout_filtrado_integrado.add(((Document)((Document)doc.get("GEO")).get("properties")).getString("SiteID"));
    				});
    				campos_aux=campos_aux+rollout_filtrado_integrado.toString();
    				/*rs2= conn.Consulta("select distinct value_atbr_field from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" and milestone='Site ID'");
    				if(rs2.next()) {
    					rs2.beforeFirst();
    					while(rs2.next()) {
    						campos_aux=campos_aux+"\""+rs2.getString(1)+"\",\n";
    					}
    				}
    				campos_aux=campos_aux.substring(0,campos_aux.length()-2);*/
					//System.out.println(campos_aux);
    				//dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					//dados_tabela=dados_tabela+"}";
	    			//dados_tabela= dados_tabela+"\n"+"],";
	    			dados_tabela=dados_tabela+campos_aux;
	    			dados_tabela= dados_tabela+"}";
	    			
    			//}else{
    			//	dados_tabela= dados_tabela+"]}";
    			//}
    			
    			//System.out.println(dados_tabela);
    			
    			JSONObject jObj = new JSONObject(dados_tabela);
    			resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(jObj);
					
					
			c.fecharConexao();
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
					c.fecharConexao();
			}else if(opt.equals("3")) {
				System.out.println("Iniciando exporte de rollout");
				int colIndex = 0;
				int rowIndex = 0;
				param1=req.getParameter("filtros");
				param2=req.getParameter("tipo");
				//System.out.println("Filtros:"+param1);
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
				
				FindIterable<Document> findIterable = null;
				Document Linha= new Document();
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
			                cell = row2.createCell((short) colIndex);
			                cell.setCellValue("Duração(D)");
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
	                if(param2.equals("selecionadas")) {
	                	List<Integer> recids= new ArrayList<Integer>();
	                	
	                	for (Integer i=0; i < filtros.length(); i++)
						{
	                		recids.add(filtros.getInt(i));
						}
	                	findIterable=c.ConsultaComplexaArrayRecID("rollout", "recid", recids);
	                }else if(param2.equals("filtro")) {
	                if(filtros.length()>0) {
	                	
	    				//JSONArray filtros = jObj.getJSONArray("filtros");
	                	List<Bson> filtro_list = new ArrayList<Bson>();
	    				Bson filtro;
	                	
	                	String filtervalue="";
	                	String filtercondition="";
	                	String filterdatafield="";
	                	String filteroperator="";
	                	for (Integer i=0; i < filtros.length(); i++)
						{
							JSONObject elemento = filtros.getJSONObject(i);
							
	                		 filtervalue = elemento.getString("filtersvalue");
							 filtercondition = elemento.getString("filtercondition");
							 filterdatafield = elemento.getString("datafield");
							 //filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
							 //filteroperator = req.getParameter("filteroperator" + i);
							
									
							switch(filtercondition)
							{
						case "CONTAINS":
							if(filterdatafield.startsWith("udate_") || filterdatafield.startsWith("status_") || filterdatafield.startsWith("resp_")) {
								filtro=Filters.elemMatch("Milestone", Filters.regex(filterdatafield,".*"+filtervalue+".*"));
								filtro_list.add(filtro);
							}else {
								filtro=Filters.regex(filterdatafield, ".*"+filtervalue+".*");
								filtro_list.add(filtro);
							}
							
							/*if(filterdatafield.indexOf("status_")>-1) {
								String status_aux="";
								if(filtervalue.equals("ok") || filtervalue.equals("completo") || filtervalue.equals("completa") || filtervalue.equals("fim") || filtervalue.equals("finalizada") || filtervalue.equals("feito")) {
									status_aux="Finalizada";
								}else if(filtervalue.equals("iniciada") || filtervalue.equals("iniciado") || filtervalue.equals("ongoing") || filtervalue.equals("started")){
									status_aux="iniciada";
								}else {
									status_aux="*";
								}
								where += " milestone='" + filterdatafield.substring(7,filterdatafield.length()) + "' and status_atividade='" + status_aux + "'";
							}*/
							break;
						case "CONTAINS_CASE_SENSITIVE":
							//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '%" + filtervalue + "%'";
							break;
						case "DOES_NOT_CONTAIN":
							if(filterdatafield.substring(0,5).equals("resp_")) {
							//	where += " milestone='" + filterdatafield.substring(5,filterdatafield.length()) + "' and responsavel NOT LIKE '%" + filtervalue + "%'";
							}else if(filterdatafield.substring(0,6).equals("udate_")) {
							//	where += " milestone='" + filterdatafield.substring(6,filterdatafield.length()) + "' and remark NOT LIKE '%" + filtervalue + "%'";
							}else {
							//	where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '%" + filtervalue + "%'";
							}
							//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '%" + filtervalue + "%'";
							break;
						case "DOES_NOT_CONTAIN_CASE_SENSITIVE":
							//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE BINARY '%" + filtervalue + "%'";
							break;
						case "EQUAL":
							
							if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
								filtro=Filters.elemMatch("Milestone", Filters.gte(filterdatafield,checa_formato_data(filtervalue)));
								filtro_list.add(filtro);
								filtro=Filters.elemMatch("Milestone", Filters.lte(filterdatafield,checa_formato_data(filtervalue)));
								filtro_list.add(filtro);
							}else {
								if(filterdatafield.startsWith("status_")) {
									if(filtervalue.indexOf("finished")>0) {
										filtro=Filters.elemMatch("Milestone", Filters.eq(filterdatafield,"Finalizada"));
										filtro_list.add(filtro);
									}else if(filtervalue.indexOf("img/started")>0) {
										filtro=Filters.elemMatch("Milestone", Filters.eq(filterdatafield,"iniciada"));
										filtro_list.add(filtro);
									}else if(filtervalue.indexOf("img/notstarted")>0) {
										filtro=Filters.elemMatch("Milestone", Filters.eq(filterdatafield,"Nao Iniciada"));
										filtro_list.add(filtro);
										
									}
									
								}else if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
									filtro=Filters.gte(filterdatafield, checa_formato_data(filtervalue));
									filtro_list.add(filtro);
									filtro=Filters.lte(filterdatafield, checa_formato_data(filtervalue));
									filtro_list.add(filtro);
								}else {
									filtro=Filters.eq(filterdatafield, filtervalue);
									filtro_list.add(filtro);
								}
							}
							
							break;
						case "EQUAL_CASE_SENSITIVE":
							//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '" + filtervalue + "'";
							break;
						case "NOT_EQUAL":
							//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '" + filtervalue + "'";
							break;
						case "NOT_EQUAL_CASE_SENSITIVE":
							//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE BINARY '" + filtervalue + "'";
							break;
						case "GREATER_THAN":
							
							if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
								filtro=Filters.elemMatch("Milestone", Filters.gt(filterdatafield,checa_formato_data(filtervalue)));
								filtro_list.add(filtro);
								
							}else {
								if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
									filtro=Filters.gt(filterdatafield, checa_formato_data(filtervalue));
									filtro_list.add(filtro);
								}else {
									filtro=Filters.gt(filterdatafield,filtervalue);
									filtro_list.add(filtro);
								}
							}
							break;
						case "LESS_THAN":
							if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
								filtro=Filters.elemMatch("Milestone", Filters.lt(filterdatafield,checa_formato_data(filtervalue)));
								filtro_list.add(filtro);
								
							}else {
								if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
									filtro=Filters.lt(filterdatafield, checa_formato_data(filtervalue));
									filtro_list.add(filtro);
								}else {
									filtro=Filters.lt(filterdatafield, filtervalue);
									filtro_list.add(filtro);
								}
							}
							break;
						case "GREATER_THAN_OR_EQUAL":
							if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
								System.out.println("entrou no filtro de milestone");
								filtro=Filters.elemMatch("Milestone", Filters.gte(filterdatafield,checa_formato_data(filtervalue)));
								filtro_list.add(filtro);
								
							}else {
								if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
									filtro=Filters.gte(filterdatafield, checa_formato_data(filtervalue));
									filtro_list.add(filtro);
								}else {
									filtro=Filters.gte(filterdatafield, filtervalue);
									filtro_list.add(filtro);
								}
							}
							break;
						case "LESS_THAN_OR_EQUAL":
							if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
								filtro=Filters.elemMatch("Milestone", Filters.lte(filterdatafield,checa_formato_data(filtervalue)));
								filtro_list.add(filtro);
								
							}else {
								if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
									filtro=Filters.lte(filterdatafield, checa_formato_data(filtervalue));
									filtro_list.add(filtro);
								}else {
									filtro=Filters.lte(filterdatafield, filtervalue);
									filtro_list.add(filtro);
								}
							}
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
	                	filtro=Filters.eq("Empresa", p.getEmpresa().getEmpresa_id());
						filtro_list.add(filtro);
	                	findIterable = c.ConsultaCollectioncomFiltrosLista("rollout",filtro_list);
	                }else {
	                	findIterable=c.ConsultaOrdenadaRolloutCompleto("rollout",p.getEmpresa().getEmpresa_id());
	                }
	                }else if(param2.equals("completo")){
						findIterable=c.ConsultaOrdenadaRolloutCompleto("rollout",p.getEmpresa().getEmpresa_id());
						//query="Select * from rollout where empresa="+p.getEmpresa().getEmpresa_id()+" and linha_ativa='Y' order by recid,ordenacao";
					}
	               MongoCursor<Document> resultado_rollout=findIterable.iterator();
	               String[]campos_nome=JSONObject.getNames(campo_tipo);
	               String[]campos=new String[campo_tipo.length()];
	               //System.out.println(campo_tipo.toString());
	               for(int j=0;j<campo_tipo.length();j++) {
	            	    campos[campo_tipo.getJSONArray(campos_nome[j]).getInt(2)-1]=campos_nome[j];
	            	   
	               }
	              
	               Document milestone_doc= new Document();
	               rowIndex=2;
	               while(resultado_rollout.hasNext()) {
	            	   Linha=resultado_rollout.next();
	            	   //System.out.println("iniciando leitura da linha recid:"+Linha.getInteger("recid"));
	            	   List<Document> milestones_list=(List<Document>) Linha.get("Milestone");
	            	   milestone_doc.clear();
	            	   colIndex=0;
	            	   rowIndex=rowIndex+1;
	            	   row = sheet.createRow((short) rowIndex);
	            	   cell = row.createCell((short) colIndex);
	            	   cell.setCellValue(Linha.getInteger("recid"));
	            	   cell.setCellStyle(cellStyle3);
	            	   colIndex=colIndex+1;
	            	   for(int indice_campos=0;indice_campos<campo_tipo.length();indice_campos++) {
	            		   cell = row.createCell((short) colIndex);
	            		   //System.out.println("Lendo campo :"+campos[indice_campos]+" , tipo:"+ campo_tipo.getJSONArray(campos[indice_campos]).get(0)+"/"+campo_tipo.getJSONArray(campos[indice_campos]).get(1));
	            		   if(campo_tipo.getJSONArray(campos[indice_campos]).get(0).equals("Atributo")){
		            		   if(campo_tipo.getJSONArray(campos[indice_campos]).get(1).equals("Data")){
		            			   if(Linha.get(campos[indice_campos])!=null) {
		            				   if(!Linha.get(campos[indice_campos]).equals("")) {
		            					   cell.setCellStyle(cellStyle_date);
	    	            				   cell.setCellType(CellType.NUMERIC);
	    	            				   cell.setCellValue(checa_formato_data(f2.format(Linha.getDate(campos[indice_campos]))));
	    	            				   colIndex=colIndex+1;
		            				   }else {
		            					   	cell.setCellStyle(cellStyle_date);
		   	            					cell.setCellType(CellType.NUMERIC);
		    	            				cell.setCellValue(Linha.getString(campos[indice_campos]));
		 		            				colIndex=colIndex+1;
		            				   }
		            			   }else {
		            				    cell.setCellStyle(cellStyle_date);
	   	            					cell.setCellType(CellType.NUMERIC);
	    	            				cell.setCellValue("");
	 		            				colIndex=colIndex+1;
		            			   }
		            		   }else {
		            			   if(Linha.get(campos[indice_campos])!=null) {
			            			   cell.setCellValue(Linha.getString(campos[indice_campos]));
			            			   cell.setCellStyle(cellStyle2);
			            			   colIndex=colIndex+1;
		            			   }else {
		            				   cell.setCellValue("");
			            			   cell.setCellStyle(cellStyle2);
			            			   colIndex=colIndex+1;
		            			   }
		            		   }
	            		   }else {
	            			for(int f=0;f<milestones_list.size();f++){
	            				milestone_doc=milestones_list.get(f);
  								//indice_milestone=f;
  								if(milestone_doc.getString("Milestone").equals(campos[indice_campos])) {
  									 f=999;
  								 }
  							 }
	            			if(milestone_doc.get("sdate_pre_"+campos[indice_campos])!=null) {
	            				if(!milestone_doc.get("sdate_pre_"+campos[indice_campos]).equals("")) {
	            					aux_date=checa_formato_data(f2.format(milestone_doc.getDate("sdate_pre_"+campos[indice_campos])));
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue(aux_date);
	            				}else {
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue("");
	            				}
            				}else {
            					cell.setCellStyle(cellStyle_date);
           					    cell.setCellType(CellType.NUMERIC);
            					cell.setCellValue("");
            				}
	            			cell.setCellStyle(cellStyle_date);
            				colIndex=colIndex+1;
            				cell = row.createCell((short) colIndex);
            				if(milestone_doc.get("edate_pre_"+campos[indice_campos])!=null) {
	            				if(!milestone_doc.get("edate_pre_"+campos[indice_campos]).equals("")) {
	            					aux_date=checa_formato_data(f2.format(milestone_doc.getDate("edate_pre_"+campos[indice_campos])));
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue(aux_date);
	            				}else {
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue("");
	            				}
            				}else {
            					cell.setCellStyle(cellStyle_date);
           					    cell.setCellType(CellType.NUMERIC);
            					cell.setCellValue("");
            				}
            				cell.setCellStyle(cellStyle_date);
            				colIndex=colIndex+1;
            				cell = row.createCell((short) colIndex);
            				if(milestone_doc.get("sdate_"+campos[indice_campos])!=null) {
	            				if(!milestone_doc.get("sdate_"+campos[indice_campos]).equals("")) {
	            					aux_date=checa_formato_data(f2.format(milestone_doc.getDate("sdate_"+campos[indice_campos])));
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue(aux_date);
	            				}else {
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue("");
	            				}
            				}else {
            					cell.setCellStyle(cellStyle_date);
           					    cell.setCellType(CellType.NUMERIC);
            					cell.setCellValue("");
            				}
            				cell.setCellStyle(cellStyle_date);
            				colIndex=colIndex+1;
            				cell = row.createCell((short) colIndex);
            				if(milestone_doc.get("edate_"+campos[indice_campos])!=null) {
	            				if(!milestone_doc.get("edate_"+campos[indice_campos]).equals("")) {
	            					aux_date=checa_formato_data(f2.format(milestone_doc.getDate("edate_"+campos[indice_campos])));
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue(aux_date);
	            				}else {
	            					cell.setCellStyle(cellStyle_date);
	           					    cell.setCellType(CellType.NUMERIC);
	            					cell.setCellValue("");
	            				}
            				}else {
            					cell.setCellStyle(cellStyle_date);
           					    cell.setCellType(CellType.NUMERIC);
            					cell.setCellValue("");
            				}
            				cell.setCellStyle(cellStyle_date);
            				colIndex=colIndex+1;
            				cell = row.createCell((short) colIndex);
            				if(milestone_doc.get("udate_"+campos[indice_campos])!=null) {
	            				if(!milestone_doc.get("udate_"+campos[indice_campos]).equals("")) {
	            					cell.setCellStyle(cellStyle2);
	           					    cell.setCellValue(milestone_doc.getString("udate_"+campos[indice_campos]));
	            				}else {
	            					cell.setCellStyle(cellStyle2);
	            					cell.setCellValue("");
	            				}
            				}else {
            					cell.setCellStyle(cellStyle2);
            					cell.setCellValue("");
            				}
            				colIndex=colIndex+1;
            				cell = row.createCell((short) colIndex);
            				if(milestone_doc.get("resp_"+campos[indice_campos])!=null) {
	            				if(!milestone_doc.get("resp_"+campos[indice_campos]).equals("")) {
	            					cell.setCellStyle(cellStyle2);
	           					    cell.setCellValue(milestone_doc.getString("resp_"+campos[indice_campos]));
	            				}else {
	            					cell.setCellStyle(cellStyle2);
	            					cell.setCellValue("");
	            				}
            				}else {
            					cell.setCellStyle(cellStyle2);
            					cell.setCellValue("");
            				}
            				colIndex=colIndex+1;
            				cell = row.createCell((short) colIndex);
            				if(milestone_doc.get("duracao_"+campos[indice_campos])!=null) {
	            				if(!milestone_doc.get("duracao_"+campos[indice_campos]).equals("")) {
	            					cell.setCellStyle(cellStyle2);
	            					//System.out.println(milestone_doc.get("duracao_"+campos[indice_campos]).getClass());
	           					    cell.setCellValue(milestone_doc.get("duracao_"+campos[indice_campos]).toString());
	            				}else {
	            					cell.setCellStyle(cellStyle2);
	            					cell.setCellValue("");
	            				}
            				}else {
            					cell.setCellStyle(cellStyle2);
            					cell.setCellValue("");
            				}
            				colIndex=colIndex+1;
	            		   }//fim da leitura tipo milestone
	            	   }//fim do for de campos
	            	   
	               }//fim do while do mongodb
	            	
		            
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
	            c.fecharConexao();
			}else if(opt.equals("4")) {
				insere_linha_rollout(conn,req,resp,p);
				c.fecharConexao();
			}else if(opt.equals("5")) {
				
				System.out.println("Iniciando Import de Rollout. usuario:"+p.get_PessoaUsuario()+" | " + f3.format(time));
				InputStream inputStream=null;
				if (ServletFileUpload.isMultipartContent(req)) {
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
					Iterator<FileItem> iter = multiparts.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
						       // processFormField(item);
						}else {
							inputStream = item.getInputStream();
							String sql = "Insert into arquivos_importados (arq_dt_inserido,arq,arq_nome,arq_tipo,arq_tamanho,arq_usuario) values ('"+time+"' , ? ,'"+item.getName()+"','"+item.getContentType()+"','"+item.getSize()+"','"+p.get_PessoaUsuario()+"')";
							PreparedStatement statement;
							statement = conn.getConnection().prepareStatement(sql);
							statement.setBlob(1, inputStream);
							int linha = statement.executeUpdate();
						    statement.getConnection().commit();
						    inputStream= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
						    DataFormatter dataFormatter = new DataFormatter();
						    int indexCell=0;
				    		int recid=0;
				    		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
				    		Sheet sheet1 = wb.getSheet("RolloutMSTP");
				    		Cell cell2;
				    		String cellValue;
				    		last_id=0;
				    		Row row_aux = sheet1.getRow(2);
				    		String []campos = new String[row_aux.getLastCellNum()];
				    		String operacao="";
				    		Calendar d = Calendar.getInstance();
				    		int j=row_aux.getLastCellNum();
				    		row_aux = sheet1.getRow(1);
				    		String aux_nome="";
				    		resp.setContentType("application/json"); 
				        	JSONObject jsonObject = new JSONObject(); 
				        	jsonObject.put("Sucesso", "Sucesso"); 
				        	PrintWriter pw = resp.getWriter();
				        	pw.print(jsonObject); 
				        	
			    			FindIterable<Document> findIterable;
			    			Document changes;
			    			Document linha_doc;
			    			Document filtros;
			    			Document milestone_doc;
			    			Document update;
			    			List<Document> milestones = new ArrayList<Document>();
			    			Document historico;
			    			List<Document> lista_hitorico = new ArrayList<Document>();
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
				        	int last_recid=0;
				        	findIterable=c.LastRegisterCollention("rollout", "recid");
				        	changes=findIterable.first();
				        	last_recid=changes.getInteger("recid");
				        	Iterator<Row> rowIterator = sheet1.iterator();
				            rowIterator.next();
				            double total_linhas=sheet1.getLastRowNum();
				            System.out.println("Total de linhas no arquivo:"+total_linhas);
				            //Bson filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				            //List<Bson> list = new ArrayList<Bson>();
				            //list.add(filtro);
				            //c.RemoverFiltroList("rollout", list);
				            int i=0;
				            int colunacelula=0;
				            while (rowIterator.hasNext()) {
				            	Row row = rowIterator.next();
				            	
				            	linha_doc=new Document();
				            	operacao="";
				            	changes=new Document();
				            	filtros=new Document();
				            	historico=new Document();
				            	lista_hitorico.clear();
				            	milestones.clear();
				    			i=0;
				    			if(row.getRowNum()>2) {
				    				Cell cell = row.getCell(i);
				    				cellValue = dataFormatter.formatCellValue(cell);
				    				cellValue=cellValue.replace("'","");
				    				cellValue=cellValue.replace("\"","");
				    				cellValue=cellValue.replace("\\","_");
				    				if(row.getFirstCellNum()==0) {
			    						if(i==0) {
			    							 if(cellValue.length()>0) {
					    						 recid=Integer.parseInt(cellValue);
					    						 linha_doc=c.ConsultaSimplesComFiltro("rollout", "recid", recid, p.getEmpresa().getEmpresa_id()).first();
					    						 operacao="update";
					    						 colunacelula=i;
					    					 }else {
					    						 recid=-1;
					    						 operacao="insert";
					    						 last_recid=last_recid+1;
					    						 colunacelula=i;
					    					 }
			    						}else {
				    						colunacelula=i;
				    					 }
			    					}else {
			    						recid=-1;
			    						operacao="insert";
				    					colunacelula=i;
				    					 if(i==0) {
				    						 last_recid=last_recid+1;
				    					 }
			    					}
				    				
				    			while (i<j && !operacao.equals("abortar"))  {
				    				 cell = row.getCell(i);
				    				 colunacelula=i;
				    				//System.out.println("Recid:"+recid+", Linha:"+row.getRowNum()+", celula:"+cell.getColumnIndex()+", operacao:"+operacao+", coluna:"+campos[colunacelula]);
				    				cellValue = dataFormatter.formatCellValue(cell);
				    				cellValue=cellValue.replace("'","");
				    				cellValue=cellValue.replace("\"","");
				    				cellValue=cellValue.replace("\\","_");
				    				
				    					
				    					
				    					//campos[colunacelula])
					    				if(campo_tipo.has(campos[colunacelula])) {
					    					 if(recid==-1) {
					    						 //System.out.println("Iniciando Operação de insert siteID:");
					    						 changes.append("recid", last_recid);
					    						 changes.append("Empresa", p.getEmpresa().getEmpresa_id());
					    						 changes.append("Linha_ativa", "Y");
					    						 if(campo_tipo.getJSONArray(campos[colunacelula]).get(0).equals("Atributo")){
					    							 if(campo_tipo.getJSONArray(campos[colunacelula]).get(1).equals("Data")){
					    								 if(!cellValue.equals("")) {
					    	            					if(cell.getCellType() == CellType.NUMERIC) {
								    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	            				 changes.append(campos[colunacelula], checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
								    	            				 //cellValue=f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime());
								    	            			 }
							    	            			}else {
							    	            				changes.append(campos[colunacelula],"");
							    	            			}
						    	            			 }else {
						    	            				 changes.append(campos[colunacelula],"");
						    	            			 }
					    							 }else {
					    								 if(campos[colunacelula].equals("Site ID") && cellValue.equals("")) {
					    									 operacao="aborta";
					    								 }
					    								 changes.append(campos[colunacelula],cellValue);
					    							 }
					    						 }else {//lendo os campos de milestones para insert
					    							 milestone_doc=new Document();
					    							 String status_milestone="";
					    							 milestone_doc.append("Milestone", campos[colunacelula]);
					    							 //System.out.println(campos[colunacelula]+ " começando na coluna" + i);
					    							 if(!cellValue.equals("")) {
						    	            			if(cell.getCellType() == CellType.NUMERIC) {
									    	            	if(HSSFDateUtil.isCellDateFormatted(cell)) {
									    	            		milestone_doc.append("sdate_pre_"+campos[colunacelula], checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
									    	            	}
								    	            	}else {
								    	            		milestone_doc.append("sdate_pre_"+campos[colunacelula],"");
								    	            		}
							    	            	}else {
							    	            			milestone_doc.append("sdate_pre_"+campos[colunacelula],"");
							    	            	}
						    						i=i+1;
					    							cell = row.getCell(i);
						    	            		cellValue = dataFormatter.formatCellValue(cell);
						    	            		if(!cellValue.equals("")) {
					    	            				if(cell.getCellType() == CellType.NUMERIC) {
								    	            		if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	            			milestone_doc.append("edate_pre_"+campos[colunacelula], checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
								    	            		}
							    	            		}else {
							    	            			milestone_doc.append("edate_pre_"+campos[colunacelula],"");
							    	            		}
						    	            		}else {
						    	            			milestone_doc.append("edate_pre_"+campos[colunacelula],"");
						    	            		}
						    	            		i=i+1;
					    							cell = row.getCell(i);
						    	            		cellValue = dataFormatter.formatCellValue(cell);
						    	            		if(!cellValue.equals("")) {
					    	            				if(cell.getCellType() == CellType.NUMERIC) {
								    	            		if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	            			status_milestone="iniciada";
								    	            			milestone_doc.append("sdate_"+campos[colunacelula], checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
								    	            		}
							    	            		}else {
							    	            			status_milestone="não iniciada";
							    	            			milestone_doc.append("sdate_"+campos[colunacelula],"");
							    	            		}
						    	            		}else {
						    	            			status_milestone="não iniciada";
						    	            			milestone_doc.append("sdate_"+campos[colunacelula],"");
						    	            		}	 
						    	            		i=i+1;
					    							cell = row.getCell(i);
						    	            		cellValue = dataFormatter.formatCellValue(cell);
						    	            		if(!cellValue.equals("")) {
					    	            				if(cell.getCellType() == CellType.NUMERIC) {
								    	            		if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	            			status_milestone="Finalizada";
								    	            			milestone_doc.append("edate_"+campos[colunacelula], checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
								    	            		}
							    	            		}else {
							    	            			milestone_doc.append("edate_"+campos[colunacelula],"");
							    	            		}
						    	            		}else {
						    	            			milestone_doc.append("edate_"+campos[colunacelula],"");
						    	            		}
						    	            		i=i+1;
					    							cell = row.getCell(i);
					    							//System.out.println("coluna:"+cell.getColumnIndex());
					    							//System.out.println("coluna:"+"udate_"+campos[colunacelula]);
						    	            		cellValue = dataFormatter.formatCellValue(cell);
						    	            		if(!cellValue.equals("")) {
						    	            			milestone_doc.append("udate_"+campos[colunacelula],cellValue);
						    	            		}else {
						    	            			milestone_doc.append("udate_"+campos[colunacelula],"");
						    	            		} 
						    	            		i=i+1;
					    							cell = row.getCell(i);
					    							//System.out.println("coluna:"+cell.getColumnIndex());
					    							//System.out.println("coluna:"+"resp_"+campos[colunacelula]);
						    	            		cellValue = dataFormatter.formatCellValue(cell);
						    	            		if(!cellValue.equals("")) {
						    	            			milestone_doc.append("resp_"+campos[colunacelula],cellValue);
						    	            		}else {
						    	            			milestone_doc.append("resp_"+campos[colunacelula],"");
						    	            		}
						    	            		i=i+1;
						    	            		cell = row.getCell(i);
						    	            		milestone_doc.append("status_"+campos[colunacelula],status_milestone);
						    	            		milestone_doc.append("duracao_"+campos[colunacelula],0);
						    	            		
						    	            		milestones.add(milestone_doc);
					    						 }//fim do else do campo de milestone
					    					 }else{///if de recid=-1
					    						 //System.out.println("recid:"+recid);
			    	            				 //System.out.println(campos[colunacelula]+":"+linha_doc.get(campos[colunacelula]));
			    	            				 //System.out.println(campos[colunacelula]+":"+cellValue);
					    						 if(campo_tipo.getJSONArray(campos[colunacelula]).get(0).equals("Atributo")){
					    							 if(campo_tipo.getJSONArray(campos[colunacelula]).get(1).equals("Data")){
					    								 if(!cellValue.equals("")) {
					    	            					if(cell.getCellType() == CellType.NUMERIC) {
								    	            			 if(HSSFDateUtil.isCellDateFormatted(cell)) {
								    	            				 if(linha_doc.get(campos[colunacelula])!=null && !linha_doc.get(campos[colunacelula]).equals("")) {
									    	            				 if(linha_doc.getDate(campos[colunacelula])!=checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime()))) {
									    	            					changes.append(campos[colunacelula], checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
									    	            					historico=new Document();
									    	            					historico.append("recid" , recid);
									    	         				        historico.append("SiteID" , linha_doc.getString("Site ID"));
									    	         				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
									    	         				        historico.append("TipoCampo" , "Atributo");
									    	         				        historico.append("Milestone" , "");
									    	         				        historico.append("Campo",campos[colunacelula]);
									    	         				        historico.append("Valor Anterior" , linha_doc.get(campos[colunacelula]));
									    	         				        historico.append("Novo Valor" , f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime()));
									    	         				        historico.append("update_by", p.get_PessoaUsuario());
									    	         				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
									    	         				        lista_hitorico.add(historico);
									    	         				        
									    	            				 }
								    	            				 }else {
								    	            					 changes.append(campos[colunacelula], checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
								    	            					 historico=new Document();	
								    	            					 historico.append("recid" , recid);
									    	         				        historico.append("SiteID" , linha_doc.getString("Site ID"));
									    	         				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
									    	         				        historico.append("TipoCampo" , "Atributo");
									    	         				        historico.append("Milestone" , "");
									    	         				        historico.append("Campo",campos[colunacelula]);
									    	         				        historico.append("Valor Anterior" , linha_doc.get(campos[colunacelula]));
									    	         				        historico.append("Novo Valor" , f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime()));
									    	         				        historico.append("update_by", p.get_PessoaUsuario());
									    	         				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
									    	         				        lista_hitorico.add(historico);
									    	         				        
								    	            				 }
								    	            			 }
							    	            			}
						    	            			 }else {
						    	            				if(linha_doc.get(campos[colunacelula])!=null) {
						    	            				 if(!linha_doc.get(campos[colunacelula]).equals(cellValue)) {
						    	            					 changes.append(campos[colunacelula], "");
						    	            					 historico=new Document();
						    	            					 historico.append("recid" , recid);
							    	         				       historico.append("SiteID" , linha_doc.getString("Site ID"));
							    	         				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
							    	         				        historico.append("TipoCampo" , "Atributo");
							    	         				        historico.append("Milestone" , "");
							    	         				        historico.append("Campo",campos[colunacelula]);
							    	         				        historico.append("Valor Anterior" , linha_doc.get(campos[colunacelula]));
							    	         				        historico.append("Novo Valor" , "");
							    	         				        historico.append("update_by", p.get_PessoaUsuario());
							    	         				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
							    	         				        lista_hitorico.add(historico);
							    	         				       
						    	            				 }
						    	            				}
						    	            			 }
					    							 }else {
					    								 if(linha_doc.get(campos[colunacelula])!=null) {
					    									 
							    								 if(!linha_doc.get(campos[colunacelula]).equals(cellValue)) {
							    									 changes.append(campos[colunacelula],cellValue);
							    									 historico=new Document();
							    									 historico.append("recid" , recid);
								    	         				        historico.append("SiteID" , linha_doc.getString("Site ID"));
								    	         				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
								    	         				        historico.append("TipoCampo" , "Atributo");
								    	         				        historico.append("Milestone" , "");
								    	         				        historico.append("Campo",campos[colunacelula]);
								    	         				        historico.append("Valor Anterior" , linha_doc.get(campos[colunacelula]));
								    	         				        historico.append("Novo Valor" , cellValue);
								    	         				        historico.append("update_by", p.get_PessoaUsuario());
								    	         				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
								    	         				        lista_hitorico.add(historico);
								    	         				        
							    								 }
					    									 
					    								 }else {
					    									 if(!cellValue.equals("")) {
					    										 changes.append(campos[colunacelula],cellValue);
					    										 historico=new Document();
					    										 historico.append("recid" , recid);
							    	         				        historico.append("SiteID" , linha_doc.getString("Site ID"));
							    	         				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
							    	         				        historico.append("TipoCampo" , "Atributo");
							    	         				        historico.append("Milestone" , "");
							    	         				        historico.append("Campo",campos[colunacelula]);
							    	         				        historico.append("Valor Anterior" , linha_doc.get(campos[colunacelula]));
							    	         				        historico.append("Novo Valor" , cellValue);
							    	         				        historico.append("update_by", p.get_PessoaUsuario());
							    	         				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
							    	         				        lista_hitorico.add(historico);
							    	         				        
					    									 }
					    								 }
					    							 }
					    						 }else {//lendo os campos de milestones para update
					    							 List<Document> milestones_list=(List<Document>) linha_doc.get("Milestone");
					    							 milestone_doc=new Document();
					    							 int indice_milestone=0;
					    							 for(int f=0;f<milestones_list.size();f++){
					    								 milestone_doc=milestones_list.get(f);
					    								 indice_milestone=f;
					    								if(milestone_doc.getString("Milestone").equals(campos[colunacelula])) {
					    									 f=999;
					    								 }
					    							 }
					    							 if(!cellValue.equals("")) {
							    	            			if(cell.getCellType() == CellType.NUMERIC) {
										    	            	if(HSSFDateUtil.isCellDateFormatted(cell)) {
										    	            		if(milestone_doc.get("sdate_pre_"+campos[colunacelula])!=null && !milestone_doc.get("sdate_pre_"+campos[colunacelula]).equals("")) {
										    	            			if(milestone_doc.getDate("sdate_pre_"+campos[colunacelula])!=checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime()))) {
										    	            				changes.append("Milestone."+indice_milestone+".sdate_pre_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
										    	            				historico=new Document();
										    	            				historico.append("recid" , recid);
										    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
										    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
										    	         				    historico.append("TipoCampo" , "Milestone");
										    	         				    historico.append("Milestone" , campos[colunacelula]);
										    	         				    historico.append("Campo","sdate_pre_"+campos[colunacelula]);
										    	         				    historico.append("Valor Anterior" , milestone_doc.get("sdate_pre_"+campos[colunacelula]));
										    	         				    historico.append("Novo Valor" , cellValue);
										    	         				    historico.append("update_by", p.get_PessoaUsuario());
										    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
										    	         				    lista_hitorico.add(historico);
									    	         				        
										    	            			}
										    	            		}else {
										    	            			changes.append("Milestone."+indice_milestone+".sdate_pre_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
										    	            			historico=new Document();
										    	            			historico.append("recid" , recid);
									    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
									    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
									    	         				    historico.append("TipoCampo" , "Milestone");
									    	         				    historico.append("Milestone" , campos[colunacelula]);
									    	         				    historico.append("Campo","sdate_pre_"+campos[colunacelula]);
									    	         				    historico.append("Valor Anterior" , "");
									    	         				    historico.append("Novo Valor" , cellValue);
									    	         				    historico.append("update_by", p.get_PessoaUsuario());
									    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
									    	         				    lista_hitorico.add(historico);
								    	         				       
										    	            		}
										    	            	}
									    	            	}
								    	            	}else {
								    	            		if(milestone_doc.get("sdate_pre_"+campos[colunacelula])!=null) {
									    	            		if(!milestone_doc.get("sdate_pre_"+campos[colunacelula]).equals(cellValue)) {
									    	            			changes.append("Milestone."+indice_milestone+".sdate_pre_"+campos[colunacelula],cellValue);
									    	            			historico=new Document();
									    	            			historico.append("recid" , recid);
								    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
								    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
								    	         				    historico.append("TipoCampo" , "Milestone");
								    	         				    historico.append("Milestone" , campos[colunacelula]);
								    	         				    historico.append("Campo","sdate_pre_"+campos[colunacelula]);
								    	         				    historico.append("Valor Anterior" , milestone_doc.get("sdate_pre_"+campos[colunacelula]));
								    	         				    historico.append("Novo Valor" , cellValue);
								    	         				    historico.append("update_by", p.get_PessoaUsuario());
								    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
								    	         				    lista_hitorico.add(historico);
							    	         				       
									    	            		}
								    	            		}
								    	            	}
							    						i=i+1;
							    						cell = row.getCell(i);
							    	            		cellValue = dataFormatter.formatCellValue(cell);
							    	            		if(!cellValue.equals("")) {
							    	            			if(cell.getCellType() == CellType.NUMERIC) {
										    	            	if(HSSFDateUtil.isCellDateFormatted(cell)) {
										    	            		if(milestone_doc.get("edate_pre_"+campos[colunacelula])!=null && !milestone_doc.get("edate_pre_"+campos[colunacelula]).equals("")) {
										    	            			if(milestone_doc.getDate("edate_pre_"+campos[colunacelula])!=checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime()))) {
										    	            				changes.append("Milestone."+indice_milestone+".edate_pre_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
										    	            				historico=new Document();
										    	            				historico.append("recid" , recid);
										    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
										    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
										    	         				    historico.append("TipoCampo" , "Milestone");
										    	         				    historico.append("Milestone" , campos[colunacelula]);
										    	         				    historico.append("Campo","edate_pre_"+campos[colunacelula]);
										    	         				    historico.append("Valor Anterior" , milestone_doc.get("edate_pre_"+campos[colunacelula]));
										    	         				    historico.append("Novo Valor" , cellValue);
										    	         				    historico.append("update_by", p.get_PessoaUsuario());
										    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
										    	         				    lista_hitorico.add(historico);
									    	         				        
										    	            			}
										    	            		}else {
										    	            			changes.append("Milestone."+indice_milestone+".edate_pre_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
										    	            			historico=new Document();
										    	            			historico.append("recid" , recid);
									    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
									    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
									    	         				    historico.append("TipoCampo" , "Milestone");
									    	         				    historico.append("Milestone" , campos[colunacelula]);
									    	         				    historico.append("Campo","edate_pre_"+campos[colunacelula]);
									    	         				    historico.append("Valor Anterior" , "");
									    	         				    historico.append("Novo Valor" , cellValue);
									    	         				    historico.append("update_by", p.get_PessoaUsuario());
									    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
									    	         				    lista_hitorico.add(historico);
								    	         				       
										    	            		}
										    	            	}
									    	            	}
								    	            	}else {
								    	            		if(milestone_doc.get("edate_pre_"+campos[colunacelula])!=null) {
									    	            		if(!milestone_doc.get("edate_pre_"+campos[colunacelula]).equals(cellValue)) {
									    	            			changes.append("Milestone."+indice_milestone+".edate_pre_"+campos[colunacelula],cellValue);
									    	            			historico=new Document();
									    	            			historico.append("recid" , recid);
								    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
								    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
								    	         				    historico.append("TipoCampo" , "Milestone");
								    	         				    historico.append("Milestone" , campos[colunacelula]);
								    	         				    historico.append("Campo","edate_pre_"+campos[colunacelula]);
								    	         				    historico.append("Valor Anterior" , milestone_doc.get("edate_pre_"+campos[colunacelula]));
								    	         				    historico.append("Novo Valor" , cellValue);
								    	         				    historico.append("update_by", p.get_PessoaUsuario());
								    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
								    	         				    lista_hitorico.add(historico);
							    	         				        
									    	            		}
								    	            		}
								    	            	}
							    	            		i=i+1;
							    	            		cell = row.getCell(i);
							    	            		cellValue = dataFormatter.formatCellValue(cell);
							    	            		if(!cellValue.equals("")) {
							    	            			if(cell.getCellType() == CellType.NUMERIC) {
										    	            	if(HSSFDateUtil.isCellDateFormatted(cell)) {
										    	            		if(milestone_doc.get("sdate_"+campos[colunacelula])!=null && !milestone_doc.get("sdate_"+campos[colunacelula]).equals("")) {
										    	            			if(milestone_doc.getDate("sdate_"+campos[colunacelula])!=checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime()))) {
										    	            				historico=new Document();
										    	            				changes.append("Milestone."+indice_milestone+".sdate_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
									    									historico.append("recid" , recid);
										    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
										    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
										    	         				    historico.append("TipoCampo" , "Milestone");
										    	         				    historico.append("Milestone" , campos[colunacelula]);
										    	         				    historico.append("Campo","sdate_"+campos[colunacelula]);
										    	         				    historico.append("Valor Anterior" , milestone_doc.get("sdate_"+campos[colunacelula]));
										    	         				    historico.append("Novo Valor" , cellValue);
										    	         				    historico.append("update_by", p.get_PessoaUsuario());
										    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
										    	         				    lista_hitorico.add(historico);
									    	         				        
										    	            			}
										    	            		}else {
										    	            			changes.append("Milestone."+indice_milestone+".sdate_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
										    	            			historico=new Document();
										    	            			historico.append("recid" , recid);
									    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
									    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
									    	         				    historico.append("TipoCampo" , "Milestone");
									    	         				    historico.append("Milestone" , campos[colunacelula]);
									    	         				    historico.append("Campo","sdate_"+campos[colunacelula]);
									    	         				    historico.append("Valor Anterior" , "");
									    	         				    historico.append("Novo Valor" , cellValue);
									    	         				    historico.append("update_by", p.get_PessoaUsuario());
									    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
									    	         				    lista_hitorico.add(historico);
								    	         				        
										    	            		}
										    	            	}
									    	            	}
								    	            	}else {
								    	            		if(milestone_doc.get("sdate_"+campos[colunacelula])!=null) {
									    	            		if(!milestone_doc.get("sdate_"+campos[colunacelula]).equals(cellValue)) {
									    	            			changes.append("Milestone."+indice_milestone+".sdate_"+campos[colunacelula],cellValue);
									    	            			historico=new Document();
									    	            			historico.append("recid" , recid);
								    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
								    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
								    	         				    historico.append("TipoCampo" , "Milestone");
								    	         				    historico.append("Milestone" , campos[colunacelula]);
								    	         				    historico.append("Campo","sdate_"+campos[colunacelula]);
								    	         				    historico.append("Valor Anterior" , milestone_doc.get("sdate_"+campos[colunacelula]));
								    	         				    historico.append("Novo Valor" , cellValue);
								    	         				    historico.append("update_by", p.get_PessoaUsuario());
								    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
								    	         				    lista_hitorico.add(historico);
							    	         				        
									    	            		}
								    	            		}
								    	            	}
							    	            		i=i+1;
							    	            		cell = row.getCell(i);
							    	            		cellValue = dataFormatter.formatCellValue(cell);
							    	            		if(!cellValue.equals("")) {
							    	            			if(cell.getCellType() == CellType.NUMERIC) {
										    	            	if(HSSFDateUtil.isCellDateFormatted(cell)) {
										    	            		if(milestone_doc.get("edate_"+campos[colunacelula])!=null && !milestone_doc.get("edate_"+campos[colunacelula]).equals("")) {
										    	            			if(milestone_doc.getDate("edate_"+campos[colunacelula])!=checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime()))) {
										    	            				changes.append("Milestone."+indice_milestone+".edate_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
										    	            				historico=new Document();
										    	            				historico.append("recid" , recid);
										    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
										    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
										    	         				    historico.append("TipoCampo" , "Milestone");
										    	         				    historico.append("Milestone" , campos[colunacelula]);
										    	         				    historico.append("Campo","edate_"+campos[colunacelula]);
										    	         				    historico.append("Valor Anterior" , milestone_doc.get("edate_"+campos[colunacelula]));
										    	         				    historico.append("Novo Valor" , cellValue);
										    	         				    historico.append("update_by", p.get_PessoaUsuario());
										    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
										    	         				    lista_hitorico.add(historico);
									    	         				        
										    	            			}
										    	            		}else {
										    	            			changes.append("Milestone."+indice_milestone+".edate_"+campos[colunacelula],checa_formato_data(f2.format(HSSFDateUtil.getJavaCalendar(cell.getNumericCellValue()).getTime())));
										    	            			historico=new Document();
										    	            			historico.append("recid" , recid);
									    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
									    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
									    	         				    historico.append("TipoCampo" , "Atributo");
									    	         				    historico.append("TipoCampo" , "Milestone");
									    	         				    historico.append("Milestone" , campos[colunacelula]);
									    	         				    historico.append("Campo","edate_"+campos[colunacelula]);
									    	         				    historico.append("Novo Valor" , cellValue);
									    	         				    historico.append("update_by", p.get_PessoaUsuario());
									    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
									    	         				    lista_hitorico.add(historico);
								    	         				        
										    	            		}
										    	            	}
									    	            	}
								    	            	}else {
								    	            		if(milestone_doc.get("edate_"+campos[colunacelula])!=null) {
									    	            		if(!milestone_doc.get("edate_"+campos[colunacelula]).equals(cellValue)) {
									    	            			changes.append("Milestone."+indice_milestone+".edate_"+campos[colunacelula],cellValue);
									    	            			historico=new Document();
									    	            			historico.append("recid" , recid);
								    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
								    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
								    	         				    historico.append("TipoCampo" , "Milestone");
								    	         				    historico.append("Milestone" , campos[colunacelula]);
								    	         				    historico.append("Campo","edate_"+campos[colunacelula]);
								    	         				    historico.append("Valor Anterior" , milestone_doc.get("edate_"+campos[colunacelula]));
								    	         				    historico.append("Novo Valor" , cellValue);
								    	         				    historico.append("update_by", p.get_PessoaUsuario());
								    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
								    	         				    lista_hitorico.add(historico);
							    	         				       
									    	            		}
								    	            		}
								    	            	}
							    	            		
							    	            		i=i+1;
							    	            		cell = row.getCell(i);
							    	            		cellValue = dataFormatter.formatCellValue(cell);
							    	            		if(!milestone_doc.get("udate_"+campos[colunacelula]).equals(cellValue)) {
							    	            				changes.append("Milestone."+indice_milestone+".udate_"+campos[colunacelula],cellValue);
							    	            				historico=new Document();
							    	            				historico.append("recid" , recid);
							    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
							    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
							    	         				    historico.append("TipoCampo" , "Milestone");
							    	         				    historico.append("Milestone" , campos[colunacelula]);
							    	         				    historico.append("Campo","udate_"+campos[colunacelula]);
							    	         				    historico.append("Valor Anterior" , milestone_doc.get("udate_"+campos[colunacelula]));
							    	         				    historico.append("Novo Valor" , cellValue);
							    	         				    historico.append("update_by", p.get_PessoaUsuario());
							    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
							    	         				    lista_hitorico.add(historico);
						    	         				        
							    	            		}
							    	            		i=i+1;
							    	            		cell = row.getCell(i);
							    	            		cellValue = dataFormatter.formatCellValue(cell);
							    	            		if(!milestone_doc.get("resp_"+campos[colunacelula]).equals(cellValue)) {
							    	            				changes.append("Milestone."+indice_milestone+".resp_"+campos[colunacelula],cellValue);
							    	            				historico=new Document();
							    	            				historico.append("recid" , recid);
							    	         				    historico.append("SiteID" , linha_doc.getString("Site ID"));
							    	         				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
							    	         				    historico.append("TipoCampo" , "Milestone");
							    	         				    historico.append("Milestone" , campos[colunacelula]);
							    	         				    historico.append("Campo","resp_"+campos[colunacelula]);
							    	         				    historico.append("Valor Anterior" , milestone_doc.get("resp_"+campos[colunacelula]));
							    	         				    historico.append("Novo Valor" , cellValue);
							    	         				    historico.append("update_by", p.get_PessoaUsuario());
							    	         				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
							    	         				    lista_hitorico.add(historico);
						    	         				       
							    	            		}
							    	            		
					    						 }
					    					 }
					    				}//if do campo válido. existe no rollout
					    				i=i+1;
				    				}//loop de celulas
				    			
    							if(operacao.equals("insert")) {
					    			changes.append("Milestone", milestones);
					    			changes.append("update_by", p.get_PessoaUsuario());
					    			changes.append("update_time",time);
					    			c.InserirSimpels("rollout", changes);
    							}else if(operacao.equals("update")){
    								filtros.append("recid",recid);
        							filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
        							update = new Document();
        							if(!changes.isEmpty()) {
        								System.out.println(changes.toJson());
	        					        update.append("$set", changes);
	        					        c.AtualizaUm("rollout", filtros, update);
	        					        System.out.println(lista_hitorico.toString());
	        					        c.InserirMuitos("rollout_history", lista_hitorico);
	        					        
        							}else {
        								System.out.println("sem registro de mudança para recid - "+recid);
        							}
    							}
				    			}//if linha maior que 2
				    			
				            }//loop de linhas
						}//else do arquivo multipart
					}//loop de arquivos
					atualiza_sites_integrados(p);
				}//if de upload de arquivo
				c.fecharConexao();
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
				c.fecharConexao();
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
				c.fecharConexao();
			}else if(opt.equals("8")) {
				param1=req.getParameter("linha");
				Document filtro=new Document();
				Document valor=new Document();
				Document updates= new Document();
				System.out.println(param1);
    			if(param1.indexOf(",")>0){
    				String []linhas=param1.split(",");
    				for(int j=0;j<linhas.length;j++){
    					if(!linhas[j].equals("")) {
    						filtro=new Document();
    						filtro.append("Empresa", p.getEmpresa().getEmpresa_id());
    						filtro.append("recid",Integer.parseInt(linhas[j]));
    						valor=new Document();
    						valor.append("Linha_ativa", "N");
    						valor.append("update_by", p.get_PessoaUsuario());
    						valor.append("update_time", checa_formato_data(f2.format(time.getTime())));
    						
    						updates=new Document();
    						updates.append("$set",valor);
    						c.AtualizaUm("rollout", filtro, updates);
    						
    						//conn.Alterar("update rollout set linha_ativa='N',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+linhas[j]+" and empresa="+p.getEmpresa().getEmpresa_id());
    					}
    				}
    			
    			}else{
    				if(param1.length()>0){
    					
    					filtro=new Document();
						filtro.append("Empresa", p.getEmpresa().getEmpresa_id());
						filtro.append("recid",Integer.parseInt(param1));
						valor=new Document();
						valor.append("Linha_ativa", "N");
						valor.append("update_by", p.get_PessoaUsuario());
						valor.append("update_time", checa_formato_data(f2.format(time.getTime())));
						
						updates=new Document();
						updates.append("$set",valor);
						c.AtualizaUm("rollout", filtro, updates);
    					
    					//conn.Alterar("update rollout set linha_ativa='N',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());

    				}
    			}
    			resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("OK");
				 c.fecharConexao();
			}else if(opt.equals("9")) {
				//System.out.println("server side ");
				//JSONObject rollout_campos=r.getCampos().getCampos_tipo(conn,p);
				Iterator<String>campos_nomes=campo_tipo.keys();
				Long totallinhas;
				int pagina_linhas=0;
				int pagina=0;
				List<Bson> filtro_list = new ArrayList<Bson>();
				Bson filtro;
				String filtervalue="";
				String filtercondition="";
				String filterdatafield="";
				String status_milestone="";
				String filteroperator="";
				param1=req.getParameter("pagesize");
				param2=req.getParameter("pagenum");
				pagina_linhas=Integer.parseInt(param1);
				pagina=Integer.parseInt(param2);
				System.out.println("pagina_linhas:"+pagina_linhas);
				System.out.println("pagina:"+pagina);
				
				
				int filterscount = Integer.parseInt(req.getParameter("filterscount"));
				/*if(filterscount>0) {
					 aux_query=" and value_atbr_field in(";
					 filtervalue = req.getParameter("filtervalue0");
					 filtercondition = req.getParameter("filtercondition0");
					 filterdatafield = req.getParameter("filterdatafield0");
					//filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
					 filteroperator = req.getParameter("filteroperator0");
					 String aux_filterdatafield=filterdatafield;
					System.out.println("filtervalue:"+filtervalue);
					System.out.println("filtercondition:"+filtercondition);
					System.out.println("filterdatafield:"+filterdatafield);
					System.out.println("filteroperator:"+filteroperator);
				}else {
					aux_query="";
				}*/
				//String where="";
				//aux_query="";
				List<String> status_m=new ArrayList<String>();
				if (filterscount > 0) {
					/*where = " WHERE ";
					
					String tmpdatafield = "";
					String tmpfilteroperator = "";*/
					
					for (Integer i=0; i < filterscount; i++)
					{
						 filtervalue = req.getParameter("filtervalue" + i);
						 filtercondition = req.getParameter("filtercondition" + i);
						 filterdatafield = req.getParameter("filterdatafield" + i);
						 //filterdatafield = filterdatafield.replaceAll("([^A-Za-z0-9])", "");
						 filteroperator = req.getParameter("filteroperator" + i);
						 System.out.println("filtervalue:"+filtervalue);
							System.out.println("filtercondition:"+filtercondition);
							System.out.println("filterdatafield:"+filterdatafield);
							System.out.println("filteroperator:"+filteroperator);
							filtro=Filters.eq("Empresa", p.getEmpresa().getEmpresa_id());
							filtro_list.add(filtro);
							filtro=Filters.eq("Linha_ativa", "Y");
							filtro_list.add(filtro);
						switch(filtercondition)
						{
							case "CONTAINS":
								if(filterdatafield.startsWith("udate_") || filterdatafield.startsWith("status_") || filterdatafield.startsWith("resp_")) {
									filtro=Filters.elemMatch("Milestone", Filters.regex(filterdatafield,".*"+filtervalue+".*"));
									filtro_list.add(filtro);
								}else {
									filtro=Filters.regex(filterdatafield, ".*"+filtervalue+".*");
									filtro_list.add(filtro);
								}
								
								/*if(filterdatafield.indexOf("status_")>-1) {
									String status_aux="";
									if(filtervalue.equals("ok") || filtervalue.equals("completo") || filtervalue.equals("completa") || filtervalue.equals("fim") || filtervalue.equals("finalizada") || filtervalue.equals("feito")) {
										status_aux="Finalizada";
									}else if(filtervalue.equals("iniciada") || filtervalue.equals("iniciado") || filtervalue.equals("ongoing") || filtervalue.equals("started")){
										status_aux="iniciada";
									}else {
										status_aux="*";
									}
									where += " milestone='" + filterdatafield.substring(7,filterdatafield.length()) + "' and status_atividade='" + status_aux + "'";
								}*/
								break;
							case "CONTAINS_CASE_SENSITIVE":
								//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN":
								if(filterdatafield.substring(0,5).equals("resp_")) {
								//	where += " milestone='" + filterdatafield.substring(5,filterdatafield.length()) + "' and responsavel NOT LIKE '%" + filtervalue + "%'";
								}else if(filterdatafield.substring(0,6).equals("udate_")) {
								//	where += " milestone='" + filterdatafield.substring(6,filterdatafield.length()) + "' and remark NOT LIKE '%" + filtervalue + "%'";
								}else {
								//	where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '%" + filtervalue + "%'";
								}
								//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '%" + filtervalue + "%'";
								break;
							case "DOES_NOT_CONTAIN_CASE_SENSITIVE":
								//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE BINARY '%" + filtervalue + "%'";
								break;
							case "EQUAL":
								
								if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
									filtro=Filters.elemMatch("Milestone", Filters.gte(filterdatafield,checa_formato_data(filtervalue)));
									filtro_list.add(filtro);
									filtro=Filters.elemMatch("Milestone", Filters.lte(filterdatafield,checa_formato_data(filtervalue)));
									filtro_list.add(filtro);
								}else if(filterdatafield.startsWith("udate")) {
									filtro=Filters.elemMatch("Milestone", Filters.eq(filterdatafield,filtervalue));
									filtro_list.add(filtro);
								}else if(filterdatafield.startsWith("status_")) {
									status_milestone=filterdatafield;
									if(filtervalue.indexOf("finished")>0) {
										status_m.add("Finalizada");
										//filtro_list.add(filtro);
									}else if(filtervalue.indexOf("img/started")>0) {
										status_m.add("iniciada");
										//filtro_list.add(filtro);
									}else if(filtervalue.indexOf("img/notstarted")>0) {
										status_m.add("Nao Iniciada");
									}
								}
								else {
									if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
										filtro=Filters.gte(filterdatafield, checa_formato_data(filtervalue));
										filtro_list.add(filtro);
										filtro=Filters.lte(filterdatafield, checa_formato_data(filtervalue));
										filtro_list.add(filtro);
									}else {
										filtro=Filters.eq(filterdatafield, filtervalue);
										filtro_list.add(filtro);
									}
								}
								
								break;
							case "EQUAL_CASE_SENSITIVE":
								//where += " milestone='" + filterdatafield + "' and value_atbr_field LIKE BINARY '" + filtervalue + "'";
								break;
							case "NOT_EQUAL":
								//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE '" + filtervalue + "'";
								break;
							case "NOT_EQUAL_CASE_SENSITIVE":
								//where += " milestone='" + filterdatafield + "' and value_atbr_field NOT LIKE BINARY '" + filtervalue + "'";
								break;
							case "GREATER_THAN":
								
								if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
									filtro=Filters.elemMatch("Milestone", Filters.gt(filterdatafield,checa_formato_data(filtervalue)));
									filtro_list.add(filtro);
									
								}else if(filterdatafield.startsWith("duracao_")){
									filtro=Filters.elemMatch("Milestone", Filters.gt(filterdatafield,Integer.parseInt(filtervalue)));
									filtro_list.add(filtro);
								}else {
									if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
										filtro=Filters.gt(filterdatafield, checa_formato_data(filtervalue));
										filtro_list.add(filtro);
									}else {
										filtro=Filters.gt(filterdatafield,filtervalue);
										filtro_list.add(filtro);
									}
								}
								break;
							case "LESS_THAN":
								if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
									filtro=Filters.elemMatch("Milestone", Filters.lt(filterdatafield,checa_formato_data(filtervalue)));
									filtro_list.add(filtro);
									
								}else if(filterdatafield.startsWith("duracao_")){
									filtro=Filters.elemMatch("Milestone", Filters.lt(filterdatafield,Integer.parseInt(filtervalue)));
									filtro_list.add(filtro);
								}else {
									if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
										filtro=Filters.lt(filterdatafield, checa_formato_data(filtervalue));
										filtro_list.add(filtro);
									}else {
										filtro=Filters.lt(filterdatafield, filtervalue);
										filtro_list.add(filtro);
									}
								}
								break;
							case "GREATER_THAN_OR_EQUAL":
								if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
									System.out.println("entrou no filtro de milestone");
									filtro=Filters.elemMatch("Milestone", Filters.gte(filterdatafield,checa_formato_data(filtervalue)));
									filtro_list.add(filtro);
									
								}else {
									if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
										filtro=Filters.gte(filterdatafield, checa_formato_data(filtervalue));
										filtro_list.add(filtro);
									}else {
										filtro=Filters.gte(filterdatafield, filtervalue);
										filtro_list.add(filtro);
									}
								}
								break;
							case "LESS_THAN_OR_EQUAL":
								if(filterdatafield.startsWith("sdate") || filterdatafield.startsWith("edate")) {
									filtro=Filters.elemMatch("Milestone", Filters.lte(filterdatafield,checa_formato_data(filtervalue)));
									filtro_list.add(filtro);
									
								}else {
									if(campo_tipo.getJSONArray(filterdatafield).get(1).equals("Data")){
										filtro=Filters.lte(filterdatafield, checa_formato_data(filtervalue));
										filtro_list.add(filtro);
									}else {
										filtro=Filters.lte(filterdatafield, filtervalue);
										filtro_list.add(filtro);
									}
								}
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
											
						/*if (i == filterscount - 1)
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
							
						}*/
							
						//tmpfilteroperator = filteroperator;
						//tmpdatafield = filterdatafield;			
					}
				}
				//System.out.println(where);
				if(status_m.size()>0) {
					filtro=Filters.elemMatch("Milestone", Filters.in(status_milestone,status_m));
					filtro_list.add(filtro);
				}
				String imagem_status="";
				dados_tabela="";
				dados_tabela= dados_tabela+"[";
				query="";
				//query="select * from rollout where linha_ativa='Y' and empresa="+p.getEmpresa().getEmpresa_id()+" "+aux_query+" order by recid,siteID,ordenacao limit "+(total_campos*pagina_linhas)+" OFFSET "+(total_campos*pagina_linhas*pagina);
				//System.out.println(query);
				
				FindIterable<Document> findIterable;
				
				if(filterscount>0) {
					System.out.println("Executando consulta com filtros");
					totallinhas=c.CountSimplesComFiltroInicioLimit("rollout",filtro_list);
					findIterable = c.ConsultaSimplesComFiltroInicioLimit("rollout",filtro_list,pagina_linhas*pagina,pagina_linhas);
				}else {
					filtro_list = new ArrayList<Bson>();
					filtro=Filters.eq("Linha_ativa", "Y");
					filtro_list.add(filtro);
					filtro=Filters.eq("Empresa", p.getEmpresa().getEmpresa_id());
					filtro_list.add(filtro);
					
					System.out.println("Executando consulta sem filtros");
					totallinhas=c.CountSimplesComFiltroInicioLimit("rollout",filtro_list);
					System.out.println("Encontrou "+ totallinhas);
					findIterable = c.ConsultaSimplesComFiltroInicioLimit("rollout",filtro_list,pagina_linhas*pagina,pagina_linhas);
				}
				
				MongoCursor<Document> resultado = findIterable.iterator();
		
    			Document linha=new Document();
    			Document milestone_doc=new Document();
    			String nome_campo_aux="";
    			String chaves="";
    			
    			if(!resultado.hasNext()) {
    				dados_tabela=dados_tabela+"{\"totalRecords\":\"0\"},\n";
    			}else {
    				dados_tabela=dados_tabela+"{\"totalRecords\":\""+totallinhas+"\",";
    			while(resultado.hasNext()) {
    				linha=(Document) resultado.next();
    				List<Document> milestones=(List<Document>) linha.get("Milestone");
    				//String site_aux=linha.getString("Site ID");
    				dados_tabela=dados_tabela+chaves+"\"id\":"+linha.getInteger("recid")+",";
    				//System.out.println("recID:"+linha.getInteger("recid"));
    				campos_nomes=campo_tipo.keys();
    				while(campos_nomes.hasNext()) {
    					nome_campo_aux=campos_nomes.next();
    					if(campo_tipo.getJSONArray(nome_campo_aux).get(0).equals("Atributo")) {
		    				if(campo_tipo.getJSONArray(nome_campo_aux).get(1).equals("Data")) {
		    					//System.out.println("transformando data de :"+nome_campo_aux +" - valor string:"+linha.getString(nome_campo_aux));
		    					if(linha.get(nome_campo_aux)!=null) {
		    						if( !linha.get(nome_campo_aux).equals("")) {
		    							dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\""+f2.format(linha.getDate(nome_campo_aux))+"\",";
		    						}else {
		    							dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\"\",";
		    						}
		    					}else {
		    						dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\"\",";
		    					}
		    				}else {
		    					if(linha.getString(nome_campo_aux)!=null) {
		    						dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\""+linha.getString(nome_campo_aux)+"\",";
		    					}else {
		    						dados_tabela=dados_tabela+"\""+nome_campo_aux+"\":\"\",";
		    					}
		    				}
    					}else {
    						int i=0;
    						while(i<milestones.size()) {
    							milestone_doc=milestones.get(i);
    							if(milestone_doc.getString("Milestone").equals(nome_campo_aux)) {
    							    if(milestone_doc.get("sdate_"+nome_campo_aux)!=null && !milestone_doc.get("sdate_"+nome_campo_aux).equals("")) {
	    								if(milestone_doc.getDate("sdate_"+nome_campo_aux)!=null) {
	    									dados_tabela=dados_tabela+"\"sdate_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("sdate_"+nome_campo_aux))+"\",";
	    								}else {
	    									dados_tabela=dados_tabela+"\"sdate_"+nome_campo_aux+"\":\"\",";
	    								}
    							    }else {
    							    	dados_tabela=dados_tabela+"\"sdate_"+nome_campo_aux+"\":\"\",";
    							    }
    							    if(milestone_doc.get("edate_"+nome_campo_aux)!=null && !milestone_doc.get("edate_"+nome_campo_aux).equals("")) {
	    								if(milestone_doc.getDate("edate_"+nome_campo_aux)!=null) {
	    									dados_tabela=dados_tabela+"\"edate_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("edate_"+nome_campo_aux))+"\",";
	    								}else {
	    									dados_tabela=dados_tabela+"\"edate_"+nome_campo_aux+"\":\"\",";
	    								}
    							    }else {
    							    	dados_tabela=dados_tabela+"\"edate_"+nome_campo_aux+"\":\"\",";
    							    }
    							    if(milestone_doc.get("sdate_pre_"+nome_campo_aux)!=null && !milestone_doc.get("sdate_pre_"+nome_campo_aux).equals("")) {
	    								if(milestone_doc.getDate("sdate_pre_"+nome_campo_aux)!=null) {
	    									dados_tabela=dados_tabela+"\"sdate_pre_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("sdate_pre_"+nome_campo_aux))+"\",";
	    								}else {
	    									dados_tabela=dados_tabela+"\"sdate_pre_"+nome_campo_aux+"\":\"\",";
	    								}
    							    }else {
    							    	dados_tabela=dados_tabela+"\"sdate_pre_"+nome_campo_aux+"\":\"\",";
    							    }
    							    if(milestone_doc.get("edate_pre_"+nome_campo_aux)!=null && !milestone_doc.get("edate_pre_"+nome_campo_aux).equals("")) {
	    								if(milestone_doc.getDate("edate_pre_"+nome_campo_aux)!=null) {
	    									dados_tabela=dados_tabela+"\"edate_pre_"+nome_campo_aux+"\":\""+f2.format(milestone_doc.getDate("edate_pre_"+nome_campo_aux))+"\",";
	    								}else{
	    									dados_tabela=dados_tabela+"\"edate_pre_"+nome_campo_aux+"\":\"\",";
	    								}
    							    }else {
    							    	dados_tabela=dados_tabela+"\"edate_pre_"+nome_campo_aux+"\":\"\",";
    							    }
    								dados_tabela=dados_tabela+"\"udate_"+nome_campo_aux+"\":\""+milestone_doc.getString("udate_"+nome_campo_aux)+"\",";
    								dados_tabela=dados_tabela+"\"resp_"+nome_campo_aux+"\":\""+milestone_doc.getString("resp_"+nome_campo_aux)+"\",";
    								if(milestone_doc.get("duracao_"+nome_campo_aux).equals("") || milestone_doc.get("duracao_"+nome_campo_aux)==null) {
    									dados_tabela=dados_tabela+"\"duracao_"+nome_campo_aux+"\":0,";
    								}else {
    									dados_tabela=dados_tabela+"\"duracao_"+nome_campo_aux+"\":"+milestone_doc.get("duracao_"+nome_campo_aux)+",";
    								}
    								imagem_status="";
    								if(milestone_doc.getString("status_"+nome_campo_aux).equals("Finalizada")) {
        								imagem_status="finished.png";
        							}else if(milestone_doc.getString("status_"+nome_campo_aux).equals("parada")) {
        								imagem_status="stopped.png";
        							}else if(milestone_doc.getString("status_"+nome_campo_aux).equals("iniciada")) {
        								imagem_status="started.png";
        							}else {
        								imagem_status="notstarted.png";
        							}
    								dados_tabela=dados_tabela+"\"status_"+nome_campo_aux+"\":\"<img src='img/"+imagem_status+"'>\",";
    								
    								i=999;
    							}
    							i=i+1;
    						}
    					}
	    			}
    				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
    				dados_tabela=dados_tabela+"},\n";
    				chaves="{";
    				}}
    			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
				//dados_tabela=dados_tabela+"}";
    			dados_tabela= dados_tabela+"\n"+"]";
				
    			/*rs2= conn.Consulta(query);
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
	    			dados_tabela= dados_tabela+"\n"+"]";*/
    			//}else{
    			//	dados_tabela= dados_tabela+"]";
    			//}
    			
    			//System.out.println(dados_tabela);
    			//JSONObject jObj = new JSONObject(dados_tabela);
    			resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
					
			    c.fecharConexao();
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
			    c.fecharConexao();
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
				 c.fecharConexao();
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
				 c.fecharConexao();
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
			    c.fecharConexao();
			}else if(opt.equals("14")) {
				System.out.println("iniciando sincronia de rollout com MongoDB");
				String imagem_status="";
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
				
				Document document;
				//Document milestone = new Document();
				Document atividade = new Document();
				List<Document> lista_atividade = new ArrayList<Document>();
				rs=conn.Consulta("select * from rollout_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and field_status='ATIVO'");
				//JSONObject campos_tipo = r.getCampos().getCampos_tipo(conn, p);
    			document=new Document();
    			if(rs.next()) {
    				c.RemoverMuitosSemFiltro("rolloutCampos",p.getEmpresa().getEmpresa_id());
    				//System.out.println("entrou no if do rs");
    				String CampoNome="";
    				int colunas = rs.getMetaData().getColumnCount();
    				rs.beforeFirst();
    				while(rs.next()) {
    					//System.out.println("entrou no while do rs");
    					for (int i=1;i<=colunas;i++) {
    						CampoNome=rs.getMetaData().getColumnName(i);
    						if(CampoNome.equals("empresa")) {
    							CampoNome="Empresa";
    						}
        					document.append(CampoNome, rs.getObject(i));
    					}
    					document.append("Update_by", "masteradmin");
						document.append("Update_time", time);
    					c.InserirSimpels("rolloutCampos", document);
    					document.clear();
    				}
    			}else {
    				System.out.println("Sicronia com Mongo Finalizada porém sem sincronia dos campos do rollout");
    			}
    			System.out.println("Sicronia com Mongo Finalizada");
    			 c.fecharConexao();
			}else if(opt.equals("15")) {
				
				
                param1=req.getParameter("mudancas");
                //System.out.println(param1);
                int cont1=0;
                int cont2=0;
                int tamanho=0;
                int tamanho2=0;
                Document filtros=new Document();
                Document historico=new Document();
                Document updates=new Document();
                Document update = new Document();
                String plano;
                String cmp;
                String[] elementNames;
                time = new Timestamp(System.currentTimeMillis());
                Calendar d =Calendar.getInstance();
                //atualiza_sites_integrados(p);
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
    					d =Calendar.getInstance();
						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+5);
						//System.out.println("primeiro if inicio plan");
						//System.out.println(jObj2.getString("colum"));
						plano=jObj2.getString("value");
						//ldate = LocalDate.parse(plano.toString(), formatter);
						//query="update rollout set dt_inicio_bl='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
						filtros.append("recid" , jObj2.getInt("id"));
						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
						filtros.append("Milestone.Milestone" , cmp);
						updates.append("Milestone.$.sdate_pre_"+cmp, checa_formato_data(plano));
						updates.append("update_by", p.get_PessoaUsuario());
						
						updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
						update = new Document();
				        update.append("$set", updates);
				        historico.append("recid" , jObj2.getInt("id"));
				        historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
				        historico.append("TipoCampo" , "Milestone");
				        historico.append("Milestone" , cmp);
				        historico.append("Campo",jObj2.getString("colum"));
				        if(jObj2.getString("oldvalue").equals("")) {
				        	 historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
				        }else {
				        	 historico.append("Valor Anterior" , jObj2.getString("oldvalue").substring(0, 10));
				        }
				        if(plano.equals("")) {
				        	historico.append("Novo Valor" , plano);
				        }else {
				        	historico.append("Novo Valor" , plano.substring(0, 10));
				        }
				        historico.append("update_by", p.get_PessoaUsuario());
				        
				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
					
					c.AtualizaUm("rollout", filtros, update);
					c.InserirSimpels("rollout_history", historico);
					historico.clear();
					filtros.clear();
					update.clear();
					updates.clear();
						//if(!conn.Alterar(query)){
							//System.out.println("Erro de Update");
						//	query="insert into rollout (recid,siteID,dt_inicio_bl,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
						//	conn.Inserir_simples(query);
						//}
					}else if(jObj2.getString("colum").contains("edate_pre")){
						d =Calendar.getInstance();
						//System.out.println("segundo if fim plan");
						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+5);
						plano=jObj2.getString("value");
						//ldate = LocalDate.parse(plano.toString(), formatter);
						//query="update rollout set dt_fim_bl='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
						filtros.append("recid" , jObj2.getInt("id"));
						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
						filtros.append("Milestone.Milestone" , cmp);
						updates.append("Milestone.$.edate_pre_"+cmp, checa_formato_data(plano));
						updates.append("update_by", p.get_PessoaUsuario());
						updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
						update = new Document();
				        update.append("$set", updates);
				        historico.append("recid" , jObj2.getInt("id"));
				        historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
				        historico.append("TipoCampo" , "Milestone");
				        historico.append("Milestone" , cmp);
				        historico.append("Campo",jObj2.getString("colum"));
				        if(jObj2.getString("oldvalue").equals("")) {
				        	 historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
				        }else {
				        	 historico.append("Valor Anterior" , jObj2.getString("oldvalue").substring(0, 10));
				        }
				        if(plano.equals("")) {
				        	historico.append("Novo Valor" , plano);
				        }else {
				        	historico.append("Novo Valor" , plano.substring(0, 10));
				        }
				        historico.append("update_by", p.get_PessoaUsuario());
				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
				        c.InserirSimpels("rollout_history", historico);
						historico.clear();
				
					c.AtualizaUm("rollout", filtros, update);
					filtros.clear();
					update.clear();
					updates.clear();
						//if(!conn.Alterar(query)){
							//System.out.println("Erro de Update");
						//	query="insert into rollout (recid,siteID,dt_fim_bl,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
						//	conn.Inserir_simples(query);
						//}
					}else if(jObj2.getString("colum").contains("sdate")){
						d =Calendar.getInstance();
    						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+1);
    						//System.out.println("primeiro if inicio plan");
    						//System.out.println(jObj2.getString("colum"));
    						plano=jObj2.getString("value");
    						//ldate = LocalDate.parse(plano.toString(), formatter);
    						//query="update rollout set dt_inicio='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    						filtros.append("recid" , jObj2.getInt("id"));
    						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    						filtros.append("Milestone.Milestone" , cmp);
    						updates.append("Milestone.$.sdate_"+cmp, checa_formato_data(plano));
    						//updates.append("Milestone.$.status_"+cmp, "iniciada");
    						updates.append("update_by", p.get_PessoaUsuario());
    						updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
    						historico.append("recid" , jObj2.getInt("id"));
    				        historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
    				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
    				        historico.append("TipoCampo" , "Milestone");
    				        historico.append("Milestone" , cmp);
    				        historico.append("Campo",jObj2.getString("colum"));
    				        if(jObj2.getString("oldvalue").equals("")) {
    				        	 historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
    				        }else {
    				        	 historico.append("Valor Anterior" , jObj2.getString("oldvalue").substring(0, 10));
    				        }
    				        if(plano.equals("")) {
    				        	historico.append("Novo Valor" , plano);
    				        }else {
    				        	historico.append("Novo Valor" , plano.substring(0, 10));
    				        }
    				        
    				        historico.append("update_by", p.get_PessoaUsuario());
    				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
    				        c.InserirSimpels("rollout_history", historico);
    						historico.clear();
    					
    					
    						//if(!conn.Alterar(query)){
    							//System.out.println("Erro de Update");
    						//	query="insert into rollout (recid,siteID,dt_inicio,milestone,tipo_campo,empresa,update_by,update_time,status_atividade) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"','iniciada')";
    						//	conn.Inserir_simples(query);
    						//}
    							if(!plano.equals("")) {
    								//conn.Alterar("update rollout set status_atividade='iniciada',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id());
    								updates.append("Milestone.$.status_"+cmp, "iniciada");
    								Long duracao_dias=TimeUnit.MILLISECONDS.toDays((time.getTime())-(checa_formato_data(plano).getTime()));
									updates.append("Milestone.$."+"duracao_"+cmp,duracao_dias);
    							}
    							update = new Document();
        				        update.append("$set", updates);
        				        c.AtualizaUm("rollout", filtros, update);
        				        filtros.clear();
        						update.clear();
        						updates.clear();
    					}else if(jObj2.getString("colum").contains("edate")){
    						d =Calendar.getInstance();
    						//System.out.println("segundo if fim plan");
    						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+1);
    						plano=jObj2.getString("value");
    						//ldate = LocalDate.parse(plano.toString(), formatter);
    						//query="update rollout set dt_fim='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    						filtros.append("recid" , jObj2.getInt("id"));
    						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    						filtros.append("Milestone.Milestone" , cmp);
    						updates.append("Milestone.$.edate_"+cmp, checa_formato_data(plano));
    						//updates.append("Milestone.$.status_"+cmp, "Finalizada");
    						updates.append("update_by", p.get_PessoaUsuario());
    						updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
    						historico.append("recid" , jObj2.getInt("id"));
    				        historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
    				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
    				        historico.append("TipoCampo" , "Milestone");
    				        historico.append("Milestone" , cmp);
    				        historico.append("Campo",jObj2.getString("colum"));
    				        if(jObj2.getString("oldvalue").equals("")) {
    				        	historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
    				        }else {
    				        	 historico.append("Valor Anterior" , jObj2.getString("oldvalue").substring(0, 10));
    				        }
    				        if(plano.equals("")) {
    				        	historico.append("Novo Valor" , plano);
    				        }else {
    				        	historico.append("Novo Valor" , plano.substring(0, 10));
    				        }
    				        
    				        historico.append("update_by", p.get_PessoaUsuario());
    				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
    				        c.InserirSimpels("rollout_history", historico);
    						historico.clear();
    						//if(!conn.Alterar(query)){
    							//System.out.println("Erro de Update");
    						//	query="insert into rollout (recid,siteID,dt_fim,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+plano+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    						//	conn.Inserir_simples(query);
    						//}
    							if(!plano.equals("")) {
    								//conn.Alterar("update rollout set status_atividade='Finalizada',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id());
    								updates.append("Milestone.$.status_"+cmp, "Finalizada");
    								//Long duracao_dias=TimeUnit.MILLISECONDS.toDays((time.getTime())-(checa_formato_data(plano).getTime()));
									//updates.append("Milestone.$."+"duracao_"+cmp,duracao_dias);
    							}
    							update = new Document();
        				        update.append("$set", updates);
        				        //System.out.println(filtros.toJson());
        				        c.AtualizaUm("rollout", filtros, update);
        				        filtros.clear();
        						update.clear();
        						updates.clear();
    						//query="update faturamento set milestone_data_fim_real='"+plano+"',status_faturamento='Liberado para Faturar' where id_rollout='site"+jObj2.getInt("id")+ "' and milestones_trigger='"+cmp+"'";
    						//if(conn.Alterar(query)){
    						//	System.out.println("tabela de Faturamento Atualizada!");
    						//}
    					}else if(jObj2.getString("colum").contains("udate")){
    						d =Calendar.getInstance();
    						//System.out.println("terceiro if obs plan");
    						cmp=jObj2.getString("colum").substring(jObj2.getString("colum").indexOf("_")+1);
    						//time = new Timestamp(jObj2.getString(jObj2.getString("colum")));
    						query="update rollout set remark='"+jObj2.getString("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    						filtros.append("recid" , jObj2.getInt("id"));
    						filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    						filtros.append("Milestone.Milestone" , cmp);
    						updates.append("Milestone.$.udate_"+cmp, jObj2.getString("value"));
    						updates.append("update_by", p.get_PessoaUsuario());
    						updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
    						update = new Document();
    				        update.append("$set", updates);
    				        historico.append("recid" , jObj2.getInt("id"));
    				        historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
    				        historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
    				        historico.append("TipoCampo" , "Milestone");
    				        historico.append("Milestone" , cmp);
    				        historico.append("Campo",jObj2.getString("colum"));
    				        historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
    				        historico.append("Novo Valor" , jObj2.getString("value"));
    				        historico.append("update_by", p.get_PessoaUsuario());
    				        historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
    				        c.InserirSimpels("rollout_history", historico);
    						historico.clear();
    					//System.out.println(filtros.toJson().toString());
    					//System.out.println(update.toJson().toString());
    					c.AtualizaUm("rollout", filtros, update);
    					filtros.clear();
    					update.clear();
    					updates.clear();
    						//if(!conn.Alterar(query)){
    						//	System.out.println("Erro de Update");
    						//	query="insert into rollout (recid,siteID,remark,milestone,tipo_campo,empresa,update_by,update_time) values("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+jObj2.getString("value")+"','"+cmp+"','Milestone',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    						//	conn.Inserir_simples(query);
    						//}
    					}else if(!jObj2.getString("colum").contains("id")){
    						cmp=jObj2.getString("colum");
    						//System.out.println("quarto if atributos");
    						
    						if(jObj2.getString("tipoc").equals("textbox")||jObj2.getString("tipoc").equals("combobox")){
    							if(cmp.contains("resp_")) {
    								d =Calendar.getInstance();
    								//query="update rollout set responsavel='"+jObj2.getString("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp.substring(5, cmp.length())+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    								filtros.append("recid" , jObj2.getInt("id"));
    								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    								filtros.append("Milestone.Milestone" , cmp.substring(5, cmp.length()));
    								updates.append("Milestone.$."+cmp, jObj2.getString("value"));
    								updates.append("update_by", p.get_PessoaUsuario());
    								updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
    								historico.append("recid" , jObj2.getInt("id"));
    		    				    historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
    		    				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
    		    				    historico.append("TipoCampo" , "Milestone");
    		    				    historico.append("Milestone" , cmp.substring(5,cmp.length()));
    		    				    historico.append("Campo",jObj2.getString("colum"));
    		    				    historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
    		    				    historico.append("Novo Valor" , jObj2.getString("value"));
    		    				    historico.append("update_by", p.get_PessoaUsuario());
    		    				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
    		    				   
    							}else {
    								d =Calendar.getInstance();
    								//query="update rollout set value_atbr_field='"+jObj2.getString("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    								//query2="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo,empresa,update_by,update_time) values ("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+cmp+"','"+jObj2.getString("value")+"','Atributo',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    								filtros.append("recid" , jObj2.getInt("id"));
    								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
    								updates.append(cmp, jObj2.getString("value"));
    								updates.append("update_by", p.get_PessoaUsuario());
    								updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
    								historico.append("recid" , jObj2.getInt("id"));
    		    				    historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
    		    				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
    		    				    historico.append("TipoCampo" , "Atributo");
    		    				    historico.append("Milestone" , "");
    		    				    historico.append("Campo",jObj2.getString("colum"));
    		    				    historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
    		    				    historico.append("Novo Valor" , jObj2.getString("value"));
    		    				    historico.append("update_by", p.get_PessoaUsuario());
    		    				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
    							}
    						}else if(jObj2.getString("tipoc").equals("datetimeinput")){
    							plano=jObj2.getString("value");
    							d =Calendar.getInstance();
	    						//ldate = LocalDate.parse(plano.toString(), formatter);
	    						//query="update rollout set value_atbr_field='"+plano+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
	    						//query2="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo,empresa,update_by,update_time) values ("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+cmp+"','"+plano+"','Atributo',"+p.getEmpresa().getEmpresa_id()+")";
	    						filtros.append("recid" , jObj2.getInt("id"));
								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
								updates.append(cmp, checa_formato_data(jObj2.getString("value")));
								updates.append("update_by", p.get_PessoaUsuario());
								updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
								historico.append("recid" , jObj2.getInt("id"));
		    				    historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
		    				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
		    				    historico.append("TipoCampo" , "Atributo");
		    				    historico.append("Milestone" , "");
		    				    historico.append("Campo",jObj2.getString("colum"));
		    				    historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
		    				    historico.append("Novo Valor" , jObj2.getString("value"));
		    				    historico.append("update_by", p.get_PessoaUsuario());
		    				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
    						}else if(jObj2.getString("tipoc").equals("int")){
    							d =Calendar.getInstance();
    							//query="update rollout set value_atbr_field='"+jObj2.getInt("value")+"',update_by='"+p.get_PessoaUsuario()+"',update_time='"+time+"' where recid="+jObj2.getInt("id")+ " and milestone='"+cmp+"' and empresa="+p.getEmpresa().getEmpresa_id() ;
    							//query2="insert into rollout (recid,siteID,milestone,value_atbr_field,tipo_campo,empresa,update_by,update_time) values ("+jObj2.getInt("id")+",'site"+jObj2.getInt("id")+"','"+cmp+"','"+jObj2.getInt("value")+"','Atributo',"+p.getEmpresa().getEmpresa_id()+",'"+p.get_PessoaUsuario()+"','"+time+"')";
    							filtros.append("recid" , jObj2.getInt("id"));
								filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
								updates.append(cmp, jObj2.getString("value"));
								updates.append("update_by", p.get_PessoaUsuario());
								updates.append("update_time", checa_formato_data(f2.format(d.getTime())));
								historico.append("recid" , jObj2.getInt("id"));
		    				    historico.append("SiteID" , r.getCampos().BuscaSitebyRecID(conn,jObj2.getInt("id")));
		    				    historico.append("Empresa" , p.getEmpresa().getEmpresa_id());
		    				    historico.append("TipoCampo" , "Atributo");
		    				    historico.append("Milestone" , "");
		    				    historico.append("Campo",jObj2.getString("colum"));
		    				    historico.append("Valor Anterior" , jObj2.getString("oldvalue"));
		    				    historico.append("Novo Valor" , jObj2.getString("value"));
		    				    historico.append("update_by", p.get_PessoaUsuario());
		    				    historico.append("update_time", checa_formato_data(f2.format(d.getTime())));
    						
    						}
    						    c.InserirSimpels("rollout_history", historico);
		    			        historico.clear();
    						    update = new Document();
    					        update.append("$set", updates);
    					        
    						//System.out.println(filtros.toJson().toString());
    						//System.out.println(update.toJson().toString());
    						c.AtualizaUm("rollout", filtros, update);
    						filtros.clear();
    						update.clear();
    						updates.clear();
    						//if(!conn.Alterar(query)){
    						//	System.out.println("Erro de Update");
    							//conn.Inserir_simples(query2);
    							
    						//}
    					}
    				
    				cont1++;
    			}
    			//precisa usar o getnames para pegar os nomes dos campos que foram atualizados);
    			//Timestamp time = new Timestamp(System.currentTimeMillis());
    			}
    			//System.out.println(campos2.get(1));
    			
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Rollout Atualizado!");
				
				
				 c.fecharConexao();
			}else if(opt.equals("16")) {
				System.out.println("chegou no opt 16 - plota no mapa os sites filtrados do rollout");
				
				param1=req.getParameter("filtros");
				//System.out.println(param1);
				
				JSONObject filtros=new JSONObject(param1);
				JSONArray filtrosArray=filtros.getJSONArray("filtros");
				Document rollout_filtrado=new Document();
				List<Integer> rollout_filtrado_lista=new ArrayList<Integer>();
				List<String> rollout_filtrado_site=new ArrayList<String>();
				List<Document> rollout_filtrado_site_Geo=new ArrayList<Document>();
				for(int i=0;i<filtrosArray.length();i++) {
					JSONObject filtros_campo=filtrosArray.getJSONObject(i);
					
						FindIterable<Document> findIterable=c.ConsultaComplexaComFiltro("rollout", rollout_filtrado_lista, campo_tipo.getJSONArray(filtros_campo.getString("datafield")).get(1).toString(), filtros_campo.getString("filtercondition"), filtros_campo.getString("datafield"), filtros_campo.getString("filtersvalue"));
						rollout_filtrado_lista.clear();
						rollout_filtrado_site.clear();
					findIterable.forEach((Block<Document>) doc -> {
						rollout_filtrado_site.add(doc.getString("Site ID"));
						rollout_filtrado_lista.add(doc.getInteger("recid"));
					});
					
					rollout_filtrado.append("rollout", rollout_filtrado_lista);
					
				}
				//System.out.println("numero de registros : "+rollout_filtrado_lista.size());
				FindIterable<Document> findIterable=c.ConsultaComplexaArray("Rollout_Sites", "GEO.properties.SiteID",rollout_filtrado_site); 
				findIterable.forEach((Block<Document>) doc -> {
					rollout_filtrado_site_Geo.add((Document)doc.get("GEO"));
				});
				rollout_filtrado.clear();
				Document document_featurecollection=new Document();
				document_featurecollection.append("type", "FeatureCollection");
				document_featurecollection.append("operadora", "Rollout");
				document_featurecollection.append("features", rollout_filtrado_site_Geo);
				rollout_filtrado.append("\"rollout\"", document_featurecollection.toJson().toString());
				
				FindIterable<Document> findIterable2=c.ConsultaSimplesComFiltroDate("Localiza_Usuarios","GEO.properties.Data",checa_formato_data(f2.format(time).toString()),p.getEmpresa().getEmpresa_id());
				List<Document> lista_sites= new ArrayList();
				document_featurecollection.clear();
				document_featurecollection.append("type", "FeatureCollection");
				document_featurecollection.append("operadora", "USUARIOS");
				
				findIterable2.forEach((Block<Document>) doc2 -> {
					JSONObject aux = new JSONObject(doc2.toJson());
					Document auxdoc=new Document();
					auxdoc=doc2;
					//System.out.println(aux.getJSONObject("GEO").getJSONObject("properties").getJSONObject("Data").getLong("$date"));
					//System.out.println(doc2.get("GEO",Document.class).get("properties",Document.class).get("Data", Document.class)("$date"));
					Timestamp time2 = new Timestamp(aux.getJSONObject("GEO").getJSONObject("properties").getJSONObject("Data").getLong("$date"));
					//System.out.println(time2.toString());
					auxdoc.get("GEO", Document.class).get("properties", Document.class).replace("Data", time2.toString());
					lista_sites.add((Document)auxdoc.get("GEO"));
				});
				document_featurecollection.append("features",lista_sites );
				rollout_filtrado.append("\"usuarios\"", document_featurecollection.toJson().toString());
				
				
				String  resultado = rollout_filtrado.toString();
				//System.out.println(resultado);
				resultado=resultado.replaceAll("=", ":");
				
				resultado=resultado.substring(9, resultado.length()-1);
				//System.out.println(resultado);
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(resultado);
				 c.fecharConexao();
			}else if(opt.equals("17")) {
				System.out.println("buscando sites integrados no rollout") ;
				
				List<String> rollout_filtrado_site=new ArrayList<String>();
				FindIterable<Document> findIterable=c.ConsultaSimplesSemFiltro("Rollout_Sites");
				findIterable.forEach((Block<Document>) doc -> {
					
					rollout_filtrado_site.add(doc.get("GEO.properties.SiteID").toString());
				});
				System.out.println("total de sites integrados:"+rollout_filtrado_site.size());
				
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				//System.out.println(rollout_filtrado_site.toString());
				out.print(rollout_filtrado_site.toString());
				 c.fecharConexao();
			}else if(opt.equals("18")) {
				param1=req.getParameter("filtros");
				List<String> historico_site= new ArrayList<String>();
				List<Integer> id_aux= new ArrayList<Integer>();
				Document resultado = new Document();
				
				JSONObject filtros= new JSONObject(param1);
				JSONArray filtrosArray=filtros.getJSONArray("filtros");
				for(int i=0;i<filtrosArray.length();i++) {
					id_aux.add(filtrosArray.getInt(i));
					
				}
				//System.out.println(id_aux.toString());
				FindIterable<Document> findIterable=c.ConsultaComplexaArrayRecID("rollout_history", "recid",id_aux);
				
				findIterable.forEach((Block<Document>) doc -> {
					
					resultado.append("id",doc.getObjectId("_id").toString());
					resultado.append("recid", doc.get("recid"));
					resultado.append("Site ID", doc.get("SiteID"));
					resultado.append("Tipo Campo", doc.get("TipoCampo"));
					resultado.append("Milestone", doc.get("Milestone"));
					resultado.append("Campo", doc.get("Campo"));
					resultado.append("Valor Anterior", doc.get("Valor Anterior"));
					resultado.append("Novo Valor", doc.get("Novo Valor"));
					resultado.append("Atualizado Por", doc.get("update_by"));
					resultado.append("Data da Atualização", f3.format(doc.getDate("update_time")));
					historico_site.add(resultado.toJson());
					resultado.clear();
					
				});
				
				//System.out.println(historico_site.toString());
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				//System.out.println(rollout_filtrado_site.toString());
				out.print(historico_site.toString());
				 c.fecharConexao();
			}else if(opt.equals("19")) {
				JSONObject jsonObject_campos = r.getCampos().getCampos_tipo(conn, p);
				String resultado="";
				String aux="";
				Iterator<String> campos=jsonObject_campos.keys();
				while(campos.hasNext()) {
					
					aux=campos.next();
					resultado=resultado+"<option value='"+aux+"'>"+aux+"</option>\n";
				}
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				//System.out.println(rollout_filtrado_site.toString());
				out.print(resultado);
				 c.fecharConexao();
			}else if(opt.equals("20")) {
				param1=req.getParameter("siteid");
				param2=req.getParameter("campos");
				param3=req.getParameter("autor");
				param4=req.getParameter("inicio");
				param5=req.getParameter("fim");
				List<String> autor = new ArrayList<String>();
				List<String> campos = new ArrayList<String>();
				List<String> site = new ArrayList<String>();
				
				
				
				Document resultado =new Document();
				
				List<String> historico_site= new ArrayList<String>();
				if(!param1.equals("")) {
					if(param1.indexOf(",")>0) {
						for(int i=0;i<param1.split(",").length;i++) {
						site.add(param1.split(",")[i]);
						}
					}else {
						site.add(param1);
					}
				}
				
				if(!param2.equals("")) { 
					if(param2.indexOf(",")>0) {
						for(int i=0;i<param2.split(",").length;i++) {
						campos.add(param2.split(",")[i]);
						}
					}else {
						campos.add(param2);
				}
				}
				
				if(!param3.equals("")) { 
					
					if(param3.indexOf(",")>0) {
						for(int i=0;i<param3.split(",").length;i++) {
						autor.add(param3.split(",")[i]);
						}
					}else {
						autor.add(param3);
					}
				}
				//System.out.println("site:"+site.toString());
				//System.out.println("campos:"+campos.toString());
				//System.out.println("autor:"+autor.toString());
				FindIterable<Document> findIterable=c.ConsultaFiltrosHistoricoRollout("rollout_history", site,campos,autor,param4,param5,p.getEmpresa().getEmpresa_id());
				
				findIterable.forEach((Block<Document>) doc -> {
					
					resultado.append("id",doc.getObjectId("_id").toString());
					resultado.append("recid", doc.get("recid"));
					resultado.append("Site ID", doc.get("SiteID"));
					resultado.append("Tipo Campo", doc.get("TipoCampo"));
					resultado.append("Milestone", doc.get("Milestone"));
					resultado.append("Campo", doc.get("Campo"));
					resultado.append("Valor Anterior", doc.get("Valor Anterior"));
					resultado.append("Novo Valor", doc.get("Novo Valor"));
					resultado.append("Atualizado Por", doc.get("update_by"));
					resultado.append("Data da Atualização", f3.format(doc.getDate("update_time")));
					historico_site.add(resultado.toJson());
					resultado.clear();
					
				});
				
				//System.out.println(historico_site.toString());
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				//System.out.println(rollout_filtrado_site.toString());
				out.print(historico_site.toString());
				 c.fecharConexao();
			}else if(opt.equals("21")) {
				System.out.println("buscando registros mapa_operacinal");
				Long totallinhas;
				param1=req.getParameter("filtros");
				List<Bson> filtro_list = new ArrayList<Bson>();
				Bson filtrodoc;
				//param2=req.getParameter("valor_campo");
				//param3=req.getParameter("func");
				if(!param1.equals("") && param1!=null) {
					//System.out.println(param1);
				JSONObject filtros = new JSONObject(param1);
				JSONArray filtrosArray=filtros.getJSONArray("filtros");
				
				for(int indice=0;indice<filtrosArray.length();indice++) {
					JSONObject filtro=filtrosArray.getJSONObject(indice);
					switch(filtro.getString("filtercondition"))
					{
						case "contem":
							filtrodoc=Filters.regex(filtro.getString("datafield"), ".*"+filtro.getString("filtersvalue")+".*");
							filtro_list.add(filtrodoc);
							break;
						case "equal":
							filtrodoc=Filters.eq(filtro.getString("datafield"), filtro.getString("filtersvalue"));
							filtro_list.add(filtrodoc);
							break;
						case "GREATHER EQUAL THAN":
							filtrodoc=Filters.gte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
						case "LESS EQUAL THAN":
							filtrodoc=Filters.lte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
					}
				}
				}
				dados_tabela="";
				dados_tabela= dados_tabela+"[";
				filtrodoc=Filters.eq("Empresa", p.getEmpresa().getEmpresa_id());
				filtro_list.add(filtrodoc);
				
				FindIterable<Document> findIterable;
				
				System.out.println("Executando consulta MAPA OPERACIONAL 1");
				//totallinhas=c.CountSimplesSemFiltroInicioLimit("rollout");
				findIterable = c.ConsultaRolloutPeriodoData("rollout",filtro_list,p.getEmpresa().getEmpresa_id());
				MongoCursor<Document> resultado = findIterable.iterator();
		
    			Document linha=new Document();
    			List<Document> milestone_list=new ArrayList<Document>();
    			Document milestone=new Document();
    			String nome="";
    			String chaves="";
    			if(resultado.hasNext()) {
    				
					while(resultado.hasNext()) {
						linha=resultado.next();
						//System.out.println(linha.toJson());
						milestone_list= (List<Document>) linha.get("Milestone");
						milestone=milestone_list.get(0);
						dados_tabela=dados_tabela+"{\"recid\":"+linha.getInteger("recid")+",";
						dados_tabela=dados_tabela+"\"usuario\":\""+milestone.get("resp_INSTALAÇÃO")+"\",";
						rs=conn.Consulta("select nome from usuarios where id_usuario='"+milestone.get("resp_INSTALAÇÃO")+"'");
						if(rs.next()) {
							dados_tabela=dados_tabela+"\"nome\":\""+rs.getString(1)+"\",";
						}
						dados_tabela=dados_tabela+"\"site_id\":\""+linha.getString("Site ID")+"\",";
						dados_tabela=dados_tabela+"\"h_entrada\":\"hoje 00:00:00\",";
						dados_tabela=dados_tabela+"\"u_registro\":\"hoje 00:00:00\"},\n";
						}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
    			}
				dados_tabela=dados_tabela+"]";
				//System.out.println(dados_tabela);
				
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
			    c.fecharConexao();
			}else if(opt.equals("22")) {
				System.out.println("Executando consulta MAPA OPERACIONAL 2");
				List<String> projetos = new ArrayList<String>();
				param1=req.getParameter("filtros");
				List<Bson> filtro_list = new ArrayList<Bson>();
				List<Bson> filtro_list_aux = new ArrayList<Bson>();
				Bson filtrodoc;
				//param2=req.getParameter("valor_campo");
				//param3=req.getParameter("func");
				if(!param1.equals("") && param1!=null) {
					//System.out.println(param1);
				JSONObject filtros = new JSONObject(param1);
				JSONArray filtrosArray=filtros.getJSONArray("filtros");
				
				for(int indice=0;indice<filtrosArray.length();indice++) {
					JSONObject filtro=filtrosArray.getJSONObject(indice);
					switch(filtro.getString("filtercondition"))
					{
						case "contem":
							filtrodoc=Filters.regex(filtro.getString("datafield"), ".*"+filtro.getString("filtersvalue")+".*");
							filtro_list.add(filtrodoc);
							break;
						case "equal":
							filtrodoc=Filters.eq(filtro.getString("datafield"), filtro.getString("filtersvalue"));
							filtro_list.add(filtrodoc);
							break;
						case "GREATHER EQUAL THAN":
							filtrodoc=Filters.gte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
						case "LESS EQUAL THAN":
							filtrodoc=Filters.lte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
					}
				}
				}
				filtrodoc=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtro_list.add(filtrodoc);
				Bson filtro;
				Bson filtro2;
				projetos=c.ConsultaSimplesDistinct("rollout", "PROJETO",filtro_list);
				Long projeto_sites_total = null;
				Long projeto_sites_total_realizado = null;
				Long projeto_sites_total_planejado = null;
				Long projeto_sites_total_planejado_realizado = null;
				dados_tabela="";
				dados_tabela= dados_tabela+"[";
				if(projetos.size()>0) {
					filtro_list_aux=new ArrayList<Bson>(filtro_list);
					//System.out.println("tamanho original:"+filtro_list.size());
					//System.out.println("tamanho bkp:"+filtro_list_aux.size());
					for(int indice=0;indice<projetos.size();indice++) {
						
						dados_tabela=dados_tabela+"{\"id\":"+indice+",";
						dados_tabela=dados_tabela+"\"projeto\":\""+projetos.get(indice)+"\",";
						filtro=Filters.eq("PROJETO",projetos.get(indice));
						filtro_list.add(filtro);
						projeto_sites_total=c.ConsultaCountComplexa("rollout", filtro_list);
						dados_tabela=dados_tabela+"\"totalsites\":"+projeto_sites_total+",";
						filtro2=Filters.eq("status_INSTALAÇÃO","Finalizada");
						filtro=Filters.elemMatch("Milestone", filtro2);
						filtro_list.add(filtro);
						projeto_sites_total_realizado=c.ConsultaCountComplexa("rollout", filtro_list);
						dados_tabela=dados_tabela+"\"sites_done\":"+projeto_sites_total_realizado+",";
						//System.out.println("tamanho antes do primeiro clear:"+filtro_list.size()+" dos filtros list na rodada:"+indice);
						filtro_list.clear();
						filtro_list=new ArrayList<Bson>(filtro_list_aux);
						//System.out.println("tamanho original:"+filtro_list.size()+" dos filtros list na rodada:"+indice);
						filtro=Filters.eq("PROJETO",projetos.get(indice));
						filtro_list.add(filtro);
						filtro2=Filters.eq("status_INSTALAÇÃO","iniciada");
						filtro=Filters.elemMatch("Milestone", filtro2);
						filtro_list.add(filtro);
						//System.out.println("tamanho refeito:"+filtro_list.size()+" dos filtros list na rodada:"+indice);
						projeto_sites_total_planejado=c.ConsultaCountComplexa("rollout", filtro_list);
						dados_tabela=dados_tabela+"\"sites_planejados\":"+projeto_sites_total_planejado+",";
						filtro_list.clear();
						filtro_list=new ArrayList<Bson>(filtro_list_aux);
						//System.out.println("tamanho original:"+filtro_list.size()+" dos filtros list na rodada:"+indice);
						filtro=Filters.eq("PROJETO",projetos.get(indice));
						filtro_list.add(filtro);
						filtro2=Filters.eq("status_INSTALAÇÃO","Nao Iniciada");
						filtro=Filters.elemMatch("Milestone", filtro2);
						filtro_list.add(filtro);
						projeto_sites_total_planejado_realizado=c.ConsultaCountComplexa("rollout", filtro_list);
						dados_tabela=dados_tabela+"\"sites_planejados_done\":"+projeto_sites_total_planejado_realizado+"},\n";
						filtro_list.clear();
						filtro_list=new ArrayList<Bson>(filtro_list_aux);
						projeto_sites_total_planejado=(long) 0;
						projeto_sites_total_planejado_realizado=(long) 0;
						projeto_sites_total=(long) 0;
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
				}
				dados_tabela=dados_tabela+"]";
				//System.out.println(dados_tabela);
				System.out.println("Fim de consulta consulta MAPA OPERACIONAL 2");
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
			    c.fecharConexao();
			}else if(opt.equals("23")) {
				System.out.println("Executando consulta consulta MAPA OPERACIONAL 4");
				List<String> projetos = new ArrayList<String>();
				List<Document> doc_lista = new ArrayList<Document>();
				List<Bson> filtro_list = new ArrayList<Bson>();
				List<Bson> filtro_list_aux = new ArrayList<Bson>();
				Bson filtrodoc;
				param1=req.getParameter("filtros");
				
				//param2=req.getParameter("valor_campo");
				//param3=req.getParameter("func");
				if(!param1.equals("") && param1!=null) {
					//System.out.println("Filtros Operacional 4:"+param1);
					JSONObject filtros = new JSONObject(param1);
					JSONArray filtrosArray=filtros.getJSONArray("filtros");
				
				for(int indice=0;indice<filtrosArray.length();indice++) {
					JSONObject filtro=filtrosArray.getJSONObject(indice);
					switch(filtro.getString("filtercondition"))
					{
						case "contem":
							filtrodoc=Filters.regex(filtro.getString("datafield"), ".*"+filtro.getString("filtersvalue")+".*");
							filtro_list.add(filtrodoc);
							break;
						case "equal":
							filtrodoc=Filters.eq(filtro.getString("datafield"), filtro.getString("filtersvalue"));
							filtro_list.add(filtrodoc);
							break;
						case "GREATHER EQUAL THAN":
							filtrodoc=Filters.gte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
						case "LESS EQUAL THAN":
							filtrodoc=Filters.lte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
					}
				}
				}
				filtrodoc=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtro_list.add(filtrodoc);
				Document escopo=new Document();
				projetos=c.ConsultaSimplesDistinct("rollout", "PROJETO",filtro_list);
				dados_tabela="";
				dados_tabela= dados_tabela+"[";
				int id=0;
				if(projetos.size()>0) {
				for(int indice=0;indice<projetos.size();indice++) {
					filtrodoc=Filters.eq("PROJETO",projetos.get(indice));
					filtro_list.add(filtro_list.size(),filtrodoc);
					doc_lista=c.consultaaggregation("rollout", filtro_list,"ESCOPO");
					if(doc_lista.size()>0) {
						for(int indice2=0;indice2<doc_lista.size();indice2++) {
							escopo=doc_lista.get(indice2);
							dados_tabela=dados_tabela+"{\"id\":"+id+",";
							dados_tabela=dados_tabela+"\"atividade\":\""+escopo.get("_id")+"\",";
							dados_tabela=dados_tabela+"\"projeto\":\""+projetos.get(indice)+"\",";
							dados_tabela=dados_tabela+"\"totalatividades\":"+escopo.getInteger("count")+"},";
							
							id=id+1;
						}
						
					}
					filtro_list.remove(filtro_list.size()-1);
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
				}
				dados_tabela=dados_tabela+"]";
				//System.out.println(dados_tabela);
				System.out.println("Fim da consulta consulta MAPA OPERACIONAL 4");
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
			    c.fecharConexao();
			}else if(opt.equals("24")) {
				System.out.println("Excutando consulta consulta MAPA OPERACIONAL 3");
				List<String> projetos = new ArrayList<String>();
				List<Document> doc_lista = new ArrayList<Document>();
				Document escopo=new Document();
				List<Bson> filtro_list = new ArrayList<Bson>();
				List<Bson> filtro_list_aux = new ArrayList<Bson>();
				Bson filtrodoc;
				param1=req.getParameter("filtros");
				
				//param2=req.getParameter("valor_campo");
				//param3=req.getParameter("func");
				if(!param1.equals("") && param1!=null) {
					//System.out.println("Filtros operacional 3:"+param1);
					JSONObject filtros = new JSONObject(param1);
					JSONArray filtrosArray=filtros.getJSONArray("filtros");
				
				for(int indice=0;indice<filtrosArray.length();indice++) {
					JSONObject filtro=filtrosArray.getJSONObject(indice);
					switch(filtro.getString("filtercondition"))
					{
						case "contem":
							filtrodoc=Filters.regex(filtro.getString("datafield"), ".*"+filtro.getString("filtersvalue")+".*");
							filtro_list.add(filtrodoc);
							break;
						case "equal":
							filtrodoc=Filters.eq(filtro.getString("datafield"), filtro.getString("filtersvalue"));
							filtro_list.add(filtrodoc);
							break;
						case "GREATHER EQUAL THAN":
							filtrodoc=Filters.gte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
						case "LESS EQUAL THAN":
							filtrodoc=Filters.lte(filtro.getString("datafield"), checa_formato_data(filtro.getString("filtersvalue")));
							filtro_list.add(filtrodoc);
							break;
					}
				}
				}
				filtrodoc=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtro_list.add(filtrodoc);
				projetos=c.ConsultaSimplesDistinct("rollout", "Milestone.0.resp_INSTALAÇÃO",filtro_list);
				dados_tabela="";
				dados_tabela= dados_tabela+"[";
				int id=0;
				if(projetos.size()>0) {
				for(int indice=0;indice<projetos.size();indice++) {
					filtrodoc=Filters.elemMatch("Milestone", Filters.eq("resp_INSTALAÇÃO",projetos.get(indice)));
					filtro_list.add(filtro_list.size(),filtrodoc);
					doc_lista=c.consultaaggregationMilestone("rollout", filtro_list,"PROJETO");
					if(doc_lista.size()>0) {
						for(int indice2=0;indice2<doc_lista.size();indice2++) {
							escopo=doc_lista.get(indice2);
							dados_tabela=dados_tabela+"{\"id\":"+id+",";
							dados_tabela=dados_tabela+"\"projeto\":\""+escopo.get("_id")+"\",";
							dados_tabela=dados_tabela+"\"usuario\":\""+projetos.get(indice)+"\",";
							dados_tabela=dados_tabela+"\"totalatividades_usuario\":"+escopo.getInteger("count")+"},";
							
							id=id+1;
						}
						
					}
					filtro_list.remove(filtro_list.size()-1);
				}
				dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
				}
				dados_tabela=dados_tabela+"]";
				//System.out.println(dados_tabela);
				System.out.println("Fim da consulta consulta MAPA OPERACIONAL 3");
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
			    c.fecharConexao();
			}else if(opt.equals("25")) {
				List<String> projetos = new ArrayList<String>();
				dados_tabela="";
				Document campo= new Document();
				FindIterable<Document> findIterable = c.ConsultaOrdenada("rolloutCampos", "ordenacao", 1, p.getEmpresa().getEmpresa_id());
				MongoCursor<Document> resultado=findIterable.iterator();
				if(resultado.hasNext()) {
					while(resultado.hasNext()) {
						campo=(Document) resultado.next();
						dados_tabela=dados_tabela+"<option value='"+campo.getString("field_name")+"'>"+campo.getString("field_name")+"</option>";
						
					}
				}
				System.out.println("Busca por Campos Finalizadas");
				resp.setContentType("application/html");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(dados_tabela);
			    c.fecharConexao();
			}else if(opt.equals("26")) {
				System.out.println("Iniciando ajuste de status");
				
				
				Document campo = new Document();
				Document updates = new Document();
				Document filtros = new Document();
				Document update = new Document();
				//filtros.append("Empresa", p.getEmpresa().getEmpresa_id());
				//filtros.append("recid", 5251);
				//updates.append("Milestone.1.status_INSTALAÇÃO","");
				//update.append("$unset", updates);
				//c.AtualizaMuitos("rollout", filtros, update);
				
				Long duracao_dias;
				Document milestone_aux = new Document();
				List<Document> milestone= new ArrayList<Document>();
				FindIterable<Document> findIterable = c.ConsultaOrdenada("rollout", "recid", 1, p.getEmpresa().getEmpresa_id());
				MongoCursor<Document> resultado=findIterable.iterator();
				if(resultado.hasNext()) {
					
					filtros.append("Empresa" , p.getEmpresa().getEmpresa_id());
					while(resultado.hasNext()) {
						campo=(Document) resultado.next();
						
						milestone=(List<Document>) campo.get("Milestone");
						for (int i=0;i<milestone.size();i++) {
							milestone_aux=milestone.get(i);
							filtros.append("recid" , campo.getInteger("recid"));
							if(milestone_aux.get("status_"+milestone_aux.getString("Milestone")).equals("parada")){
								
							}else if(milestone_aux.get("edate_"+milestone_aux.getString("Milestone"))!=null) {
								if(!milestone_aux.get("edate_"+milestone_aux.getString("Milestone")).toString().equals("")) {
									filtros.append("Milestone.Milestone" , milestone_aux.getString("Milestone"));
									updates.append("Milestone.$."+"status_"+milestone_aux.getString("Milestone"),"Finalizada");
									if(milestone_aux.get("sdate_"+milestone_aux.getString("Milestone"))!=null) {
										if(!milestone_aux.get("sdate_"+milestone_aux.getString("Milestone")).toString().equals("")) {
											duracao_dias=TimeUnit.MILLISECONDS.toDays((milestone_aux.getDate("edate_"+milestone_aux.getString("Milestone")).getTime())-(milestone_aux.getDate("sdate_"+milestone_aux.getString("Milestone")).getTime()));
											updates.append("Milestone.$."+"duracao_"+milestone_aux.getString("Milestone"),duracao_dias);
										}
									}
								}else if(milestone_aux.get("sdate_"+milestone_aux.getString("Milestone"))!=null) {
									if(!milestone_aux.get("sdate_"+milestone_aux.getString("Milestone")).toString().equals("")) {	
										filtros.append("Milestone.Milestone" , milestone_aux.getString("Milestone"));
										updates.append("Milestone.$."+"status_"+milestone_aux.getString("Milestone"),"iniciada");
										duracao_dias=TimeUnit.MILLISECONDS.toDays((time.getTime())-(milestone_aux.getDate("sdate_"+milestone_aux.getString("Milestone")).getTime()));
										updates.append("Milestone.$."+"duracao_"+milestone_aux.getString("Milestone"),duracao_dias);
									}else {
										filtros.append("Milestone.Milestone" , milestone_aux.getString("Milestone"));
										updates.append("Milestone.$."+"status_"+milestone_aux.getString("Milestone"),"Nao Iniciada");
									}
								}else {
									filtros.append("Milestone.Milestone" , milestone_aux.getString("Milestone"));
									updates.append("Milestone.$."+"status_"+milestone_aux.getString("Milestone"),"Nao Iniciada");
								}
							}else if(milestone_aux.get("sdate_"+milestone_aux.getString("Milestone"))!=null) {
								if(!milestone_aux.get("sdate_"+milestone_aux.getString("Milestone")).toString().equals("")) {	
									filtros.append("Milestone.Milestone" , milestone_aux.getString("Milestone"));
									updates.append("Milestone.$."+"status_"+milestone_aux.getString("Milestone"),"iniciada");
									duracao_dias=TimeUnit.MILLISECONDS.toDays((time.getTime())-(milestone_aux.getDate("sdate_"+milestone_aux.getString("Milestone")).getTime()));
									updates.append("Milestone.$."+"duracao_"+milestone_aux.getString("Milestone"),duracao_dias);
								}else {
									filtros.append("Milestone.Milestone" , milestone_aux.getString("Milestone"));
									updates.append("Milestone.$."+"status_"+milestone_aux.getString("Milestone"),"Nao Iniciada");
								}
							}else {
								filtros.append("Milestone.Milestone" , milestone_aux.getString("Milestone"));
								updates.append("Milestone.$."+"status_"+milestone_aux.getString("Milestone"),"Nao Iniciada");
							}
							if(!updates.isEmpty()) {
								update.append("$set", updates);
								c.AtualizaUm("rollout", filtros, update);
							}
							update.clear();
							filtros.clear();
							milestone_aux.clear();
							updates.clear();
						}
						milestone.clear();
						update.clear();
						
						filtros.clear();
						updates.clear();
						milestone_aux.clear();
					}
				}
				
				System.out.println("Finalizado ajuste de status");
				resp.setContentType("application/html");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print("tarefa finalizada!");
			}else if(opt.equals("27")) {
				Document arvore = new Document();
				dados_tabela="[";
				List<Bson> filtro_list_aux = new ArrayList<Bson>();
				List<Document> lista_itens = new ArrayList<Document>();
				Bson filtrodoc;
				filtrodoc=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				filtro_list_aux.add(filtrodoc);
				FindIterable<Document> findIterable = c.ConsultaCollectioncomFiltrosLista("rollouts_ids",filtro_list_aux);
				MongoCursor<Document> resultado=findIterable.iterator();
				if(resultado.hasNext()) {
					arvore=resultado.next();
					dados_tabela=dados_tabela+arvore.toJson();
					dados_tabela=dados_tabela+"]";
					resp.setContentType("application/json");  
		  		    resp.setCharacterEncoding("UTF-8"); 
		  		    PrintWriter out = resp.getWriter();
				    out.print(dados_tabela);
				}
				System.out.println("Arvore montada " + lista_itens.toString());
			}
		}catch (SQLException e) {
			conn.fecharConexao();
			c.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			c.fecharConexao();
			e.printStackTrace();
		} catch (FileUploadException e) {
			c.fecharConexao();
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
			//System.out.println(data + " - Data inválida");
			return null;
			
		} 
	}
	public void atualiza_sites_integrados(Pessoa p) {
		System.out.println("Iniciando sincornia de sites");
		ConexaoMongo c = new ConexaoMongo();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		Bson filtro;
		Document site = new Document();
		Document site_novo = new Document();
		Document geo  = new Document();
		Document propertie  = new Document();
		Document geometry  = new Document();
		List<Bson> lista_filtro=new ArrayList<Bson>();
		List<String> resultado=new ArrayList<String>();
		FindIterable<Document> findIterable;
		MongoCursor<Document> resultado_site;
		filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
		lista_filtro.add(filtro);
		//c.RemoverFiltroList("Rollout_Sites", lista_filtro);
		resultado = c.ConsultaSimplesDistinct("rollout", "Site ID", lista_filtro);
		
		for(int indice=0;indice<resultado.size();indice++) {
			filtro=Filters.eq("GEO.properties.SiteID",resultado.get(indice));
			lista_filtro.add(filtro);
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			lista_filtro.add(filtro);
			findIterable=c.ConsultaCollectioncomFiltrosLista("Rollout_Sites", lista_filtro);
			resultado_site=findIterable.iterator();
			if(resultado_site.hasNext()) {
				
			}else {
				lista_filtro.clear();
				filtro=Filters.eq("site_id",resultado.get(indice));
				lista_filtro.add(filtro);
				filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
				lista_filtro.add(filtro);
				findIterable=c.ConsultaCollectioncomFiltrosLista("sites", lista_filtro);
				resultado_site=findIterable.iterator();
				if(resultado_site.hasNext()) {
					site=(Document) resultado_site.next();
					//System.out.println(site.toJson());
					site_novo.append("Empresa", p.getEmpresa().getEmpresa_id());
					site_novo.append("Site_id", site.getString("site_id"));
					geometry =  (Document)site.get("GEO",Document.class).get("geometry");
					propertie= (Document)site.get("GEO",Document.class).get("properties");
					geo.append("type", "Feature");
					geo.append("geometry", geometry);
					geo.append("properties", propertie);
					site_novo.append("GEO", geo);
					site_novo.append("Update_by", p.get_PessoaUsuario());
					site_novo.append("Update_time", time);
					c.InserirSimpels("Rollout_Sites", site_novo);
					site_novo.clear();
				}
			}
			lista_filtro.clear();
		}
		System.out.println("Sincronia de sites finalizado ");
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
