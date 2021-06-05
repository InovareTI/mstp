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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;

/**
 * Servlet implementation class RelatoriosServlet
 */
@WebServlet("/RelatoriosServlet")
public class RelatoriosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelatoriosServlet() {
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
		mgmt_relatorios(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		mgmt_relatorios(request, response);
	}
	public void mgmt_relatorios(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		String query;
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		HttpSession session = req.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null) {
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
		System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Relatório do MSTP Web - "+f3.format(time));
		try {
		if(opt.equals("1")){
			param1=req.getParameter("nome");
			param2=req.getParameter("desc");
			param3=req.getParameter("tipo");
			param4=req.getParameter("opercacao");
			
			query="";
			if(param4.equals("atualizar")) {
				param5=req.getParameter("id_rel");
				if(mysql.Alterar("update vistoria_report set relatorio_nome='"+param1+"',descricao='"+param2+"',tipo_relatorio='"+param3+"' where id="+param5+" and empresa="+p.getEmpresa().getEmpresa_id())) {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Relatório atualizado com sucesso!");
				}
				
			}else {
			query="insert into vistoria_report (relatorio_nome,descricao,tipo_relatorio,dt_generated,generated_by,empresa,obs) values ('"+param1+"','"+param2+"','"+param3+"','"+time+"','"+p.get_PessoaUsuario()+"',"+p.getEmpresa().getEmpresa_id()+",'')";
			if(mysql.Inserir_simples(query)) {
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Relatório criado com sucesso!");
			}else {
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("Falha na Criação do Relatório!");
			}
			}
		}else if(opt.equals("2")){
			query="";
			query="select * from vistoria_report where empresa="+p.getEmpresa().getEmpresa_id()+" and status_ativo='ATIVO'";
			rs=mysql.Consulta(query);
			int contador=0;
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 15 ,\n";
					dados_tabela=dados_tabela+"\"data\":[";
					while(rs.next()) {
						contador=contador+1;
						dados_tabela=dados_tabela+"[\"<a href='#' onclick='edita_relatorio_info("+rs.getInt("id")+")'><i class='far fa-edit fa-lg' style='color:gray;padding:3px;'></i></a><a href='#' data-toggle='modal' data-target='#modal_campos_vistoria2' onclick='carrega_campos_relatorio("+rs.getInt("id")+")'><i class='fas fa-cogs fa-lg' style='color:gray;padding:3px;'></i></a><a href='#' data-toggle='modal' data-target='#modal_upload_template_checklist' onclick='uploadTemplate_rel("+rs.getInt("id")+")'><i class='fas fa-upload fa-lg' style='color:gray;padding:3px;'></i></a><a href='#' onclick='remover_relatorio("+rs.getInt("id")+")'><i class='far fa-trash-alt fa-lg' style='color:red;padding:3px;'></i></a>\",\""+rs.getString("relatorio_nome")+"\",\""+rs.getString("descricao")+"\",\""+rs.getString("tipo_relatorio")+"\",\""+rs.getString("generated_by")+"\",\""+rs.getString("dt_generated")+"\"],\n";
					}
					dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]}";
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
			} else if(opt.equals("3")){
				param1=req.getParameter("nome_item");
				param2=req.getParameter("desc_item");
				param3=req.getParameter("id_item");
				param4=req.getParameter("parent_id_item");
				param5=req.getParameter("relatorio_id_item");
				param6=req.getParameter("tipo_item");
				param7=req.getParameter("linha");
				param8=req.getParameter("coluna");
				param9=req.getParameter("planilha");
				String param10=req.getParameter("rolloutid");
				String param11=req.getParameter("camporollout");
				String tipo="";
				if(param6.equals("rollout_campo")) {
					param6 = param10 +"_"+param11;
				}
				query="select field_id from vistoria_campos where tree_id="+param3+" and empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param5;
				rs=mysql.Consulta(query);
				if(rs.next()) {
					query="select campo_id from vistoria_dados where campo_id="+rs.getInt(1)+" and empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param5;
					rs2=mysql.Consulta(query);
					if(rs2.next()) {
						resp.setContentType("application/text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Atençao, item não pode ser modificado pois já existem relatório e dados coletados para esse campo.");
					}else {
						if(param4.equals("0")) {
							tipo="Grupo";
						}else {
						query="select * from vistoria_campos where tree_id="+param3+" and tree_id=parent_id and empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param5;
						rs=mysql.Consulta(query);
						if(rs.next()) {
							tipo="SubGrupo";
						}else {
							tipo="item";
						}
						mysql.Update_simples("update vistoria_campos set field_type='SubGrupo' where tree_id="+param4+" and empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param5);
						}
						mysql.Alterar("update vistoria_campos set field_type='"+tipo+"',field_name='"+param1+"',tipo='"+param6+"',field_desc='"+param2+"',linhaExcel="+param7+",colunaExcel="+param8+",sheetExcel="+param9+" where tree_id="+param3+" and empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param5);
					}
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Item Salvo com Sucesso!");
				}else {
				if(param4.equals("0")) {
					tipo="Grupo";
				}else {
				query="select * from vistoria_campos where tree_id="+param3+" and tree_id=parent_id and empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param5;
				rs=mysql.Consulta(query);
				if(rs.next()) {
					tipo="SubGrupo";
				}else {
					tipo="item";
				}
				mysql.Update_simples("update vistoria_campos set field_type='SubGrupo' where tree_id="+param4+" and empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param5);
				}
				query="";
				if(param4.equals("")) {
					param4="0";
				}
				query="insert into vistoria_campos (field_name,tree_id,parent_id,empresa,relatorio_id,tipo,field_desc,field_type,linhaExcel,colunaExcel,sheetExcel) values ('"+param1+"',"+param3+","+param4+","+p.getEmpresa().getEmpresa_id()+","+param5+",'"+param6+"','"+param2+"','"+tipo+"',"+param7+","+param8+","+param9+")";
				if(mysql.Inserir_simples(query)) {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Item Inserido com Sucesso!");
				}
				}
			}else if(opt.equals("4")){
				query="";
				param1=req.getParameter("rel_id");
				query="select * from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1+" and field_status<>'CANCELADO' order by tree_id asc";
				rs=mysql.Consulta(query);
				if(rs.next()) {
					
					rs.beforeFirst();
					dados_tabela="[";
					while(rs.next()) {
						dados_tabela=dados_tabela+"{\"id\":\""+rs.getInt("tree_id")+"\",\"parentid\":\""+rs.getInt("parent_id")+"\",\"text\":\""+rs.getString("field_name")+"\",\"nivel\":\""+rs.getString("field_type")+"\",\"value\":"+rs.getInt("field_id")+"},";
			                
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
					dados_tabela=dados_tabela+"]";
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else {
					dados_tabela="[{\"id\":\"1\",\"parentid\":\"0\",\"text\":\"Item\"}]";
					
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
						
			}else if(opt.equals("5")){
				query="";
				param1=req.getParameter("rel_id");
				query="select * from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1;
				rs=mysql.Consulta(query);
				if(rs.next()) {
					resp.setContentType("application/Text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Remoção nao permitida, pois existem campos ativos");
				}else {
					query="update vistoria_campos set field_status='CANCELADO' where relatorio_id="+param1+ "and empresa="+p.getEmpresa().getEmpresa_id();
					mysql.Alterar(query);
					query="update vistoria_report set status_ativo='CANCELADO' where id="+param1;
					mysql.Alterar(query);
					resp.setContentType("application/Text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Relatório e Campos removidos com sucesso. Isso não afeta documentos já gerados com esse template");
				}
			}else if(opt.equals("6")){
				query="";
				param1=req.getParameter("relatorio_id_item");
				param2=req.getParameter("id_item");
				param3=req.getParameter("parent_id_item");
				query="select * from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1+" and tree_id="+param2+" and parent_id="+param3;
				rs=mysql.Consulta(query);
				if(rs.next()) {
					query=" select * from vistoria_dados where empresa="+p.getEmpresa().getEmpresa_id()+" and campo_id="+param2+"  and relatorio_id="+param1+" and relatorio_gerado='Y'";
					rs2=mysql.Consulta(query);
					if(rs2.next()) {
						resp.setContentType("application/Text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Remoção de Campo nao permita, pois existem vistoria em andamento sem documento gerado");
					}else {
						query="update vistoria_campos set field_status='CANCELADO' where empresa="+p.getEmpresa().getEmpresa_id()+" and relatorio_id="+param1+" and tree_id="+param2+" and parent_id="+param3;
						mysql.Alterar(query);
						resp.setContentType("application/Text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Campo Removido com sucesso");
					}
				}
			}else if(opt.equals("7")){
				param1=req.getParameter("rel_id");
				rs=mysql.Consulta("select * from vistoria_report where id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id());
				if(rs.next()) {
					resp.setContentType("application/Text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("[\""+rs.getString("relatorio_nome")+"\",\""+rs.getString("descricao")+"\",\""+rs.getString("tipo_relatorio")+"\"]");
				}
			} else if(opt.equals("8")){
				InputStream inputStream=null;
				
				if (ServletFileUpload.isMultipartContent(req)) {
					List<FileItem> multiparts;
					
						multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
					
					Iterator<FileItem> iter = multiparts.iterator();
					Integer num_rel = 0;
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
							num_rel=Integer.parseInt(item.getString());
						}
					}
					iter = multiparts.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
						       System.out.println("valor do item é:" + item.getString());
						}else{
							System.out.println("Iniciando Uplaod de Template");
							inputStream = item.getInputStream();
							String sql = "update vistoria_report set report_template=?,report_template_nome=?,report_template_tipo=? where id="+num_rel+" and empresa="+p.getEmpresa().getEmpresa_id();
							PreparedStatement statement;
							statement = mysql.getConnection().prepareStatement(sql);
							statement.setBlob(1, inputStream);
							statement.setString(2,item.getName());
							statement.setString(3,item.getContentType());
							int linha = statement.executeUpdate();
						    statement.getConnection().commit();
						    resp.setContentType("application/json"); 
				        	JSONObject jsonObject = new JSONObject(); 
				        	jsonObject.put("Sucesso", "Sucesso"); 
				        	PrintWriter pw = resp.getWriter();
				        	pw.print(jsonObject); 
						}
					}
				}
			}else if(opt.equals("9")){
				ResultSet rs3=null;
				System.out.println("Iniciando geracao de report automático");
				
				param1=req.getParameter("idvistoria");
				dados_tabela="";
				query="select * from vistoria_dados where id_vistoria="+param1+" and empresa="+p.getEmpresa().getEmpresa_id()+" and status_vistoria='APROVADO'";
				rs=mysql.Consulta(query);
				String siteid="";
				String enderecoSite="";
				String UF="";
				int recid=0;
				if(rs.next()) {
					siteid= rs.getString("SiteID");
					recid= rs.getInt("recid");
					Bson filtro;
					List<Bson> filtros = new ArrayList<>();
					filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
					filtros.add(filtro);
					filtro = Filters.eq("site_id",siteid);
					filtros.add(filtro);
					FindIterable<Document> findIterable = mongo.ConsultaCollectioncomFiltrosLista("sites", filtros);
					Document site = findIterable.first();
					query="select * from vistoria_report where id="+rs.getInt("relatorio_id")+" and empresa="+p.getEmpresa().getEmpresa_id();
					rs2=mysql.Consulta(query);
					if(rs2.next()) {
						XSSFWorkbook wb = new XSSFWorkbook(rs2.getBlob("report_template").getBinaryStream());
						Sheet sheet = wb.getSheetAt(0); 
						Row row = sheet.getRow(2);
						Cell cell = row.getCell(1);
						cell.setCellValue(siteid);
						if(site!=null) {
							cell = row.getCell(4);
							String end = "";
							if(site.get("site_endereco")!=null) {
								end = site.getString("site_endereco")+",";
							}
							if(site.get("site_bairro")!=null) {
								end = end + site.getString("site_bairro")+",";
							}
							if(site.get("site_municipio")!=null) {
								end = end + site.getString("site_municipio");
							}
							cell.setCellValue(end);
							cell = row.getCell(13);
							if(site.get("site_uf")!=null) {
								cell.setCellValue(site.get("site_uf").toString());
							}
						}
						CreationHelper helper = wb.getCreationHelper();
						Drawing drawing = sheet.createDrawingPatriarch();
						
						int col1=1;
						int col2=6;
						int row1=7;
						int row2=22;
						
						
			    		rs.beforeFirst();
			    		int pictureIndex;
			    		while(rs.next()) {
			    			ClientAnchor anchor = helper.createClientAnchor();
							
							anchor.setAnchorType( ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
			    			query="select * from vistoria_campos where field_id="+rs.getInt("campo_id")+" and empresa="+p.getEmpresa().getEmpresa_id();
							rs3=mysql.Consulta(query);
							if(rs3.next()) {
								if(rs3.getString("tipo").equals("Foto")) {
									anchor.setCol1(rs3.getInt("colunaExcel"));
									anchor.setCol2(rs3.getInt("colunaExcel")+5);
									anchor.setRow1(rs3.getInt("linhaExcel"));
									anchor.setRow2(rs3.getInt("linhaExcel")+15);
									pictureIndex = wb.addPicture(IOUtils.toByteArray(rs.getBlob("campo_valor_foto").getBinaryStream()), wb.PICTURE_TYPE_JPEG);
									Picture pict = drawing.createPicture( anchor, pictureIndex );
								}else if(rs3.getString("tipo").contains("Rollout")) {
									row = sheet.getRow(rs3.getInt("linhaExcel"));
									cell = row.getCell(rs3.getInt("colunaExcel"));
									filtros.clear();
									filtro = Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
									filtros.add(filtro);
									filtro = Filters.eq("recid",recid);
									filtros.add(filtro);
									findIterable = mongo.ConsultaCollectioncomFiltrosLista("rollout", filtros);
									Document rollout_linha = findIterable.first();
									System.out.println("Campo pesquisado no rollout é: "+ rs3.getString("tipo").substring(rs3.getString("tipo").indexOf("_")+1));
									System.out.println(rollout_linha.get(rs3.getString("tipo").substring(rs3.getString("tipo").indexOf("_")+1)).getClass());
									if(rollout_linha.get(rs3.getString("tipo").substring(rs3.getString("tipo").indexOf("_")+1)).getClass().toString().contains("Double")){
										//System.out.println("Escreveu o Double");
										cell.setCellValue(rollout_linha.getDouble(rs3.getString("tipo").substring(rs3.getString("tipo").indexOf("_")+1)));
									}else {
										cell.setCellValue(rollout_linha.get(rs3.getString("tipo").substring(rs3.getString("tipo").indexOf("_")+1)).toString());
									}
								}
								
							}
			    			
			    			//pict.resize();
			    			
							
			    			
			    		}
			    		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		                resp.setHeader("Content-Disposition", "attachment; filename="+time.toString()+"_rollout_mstp.xlsx");
		                wb.write(resp.getOutputStream());
		                wb.close();
					}
				rs2.close();
				rs3.close();
				}
				rs.close();
			}else if(opt.equals("10")){
				InputStream inputStream=null;
				
				if (ServletFileUpload.isMultipartContent(req)) {
					List<FileItem> multiparts;
					
						multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
					
					Iterator<FileItem> iter = multiparts.iterator();
					Integer num_item = 0;
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
							num_item=Integer.parseInt(item.getString());
							System.out.println("valor do item é:" + item.getString());
						}
					}
					iter = multiparts.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
						       
						}else{
							System.out.println("Iniciando Uplaod de Foto Modelo");
							inputStream = item.getInputStream();
							String sql = "update vistoria_campos set foto_modelo=?,foto_modelo_nome=?,foto_modelo_tipo=? where field_id="+num_item+" and empresa="+p.getEmpresa().getEmpresa_id();
							PreparedStatement statement;
							statement = mysql.getConnection().prepareStatement(sql);
							statement.setBlob(1, inputStream);
							statement.setString(2,item.getName());
							statement.setString(3,item.getContentType());
							int linha = statement.executeUpdate();
						    statement.getConnection().commit();
						    resp.setContentType("application/json"); 
				        	JSONObject jsonObject = new JSONObject(); 
				        	jsonObject.put("Sucesso", "Sucesso"); 
				        	PrintWriter pw = resp.getWriter();
				        	pw.print(jsonObject); 
						}
					}
				}
			}else if(opt.equals("11")) {
				System.out.println("Buscando fotos de checklist");
				param1=req.getParameter("id");
				
				query="select foto_modelo from vistoria_campos where field_id="+param1+" and empresa="+p.getEmpresa().getEmpresa_id();
				//System.out.println(query);
				rs=mysql.Consulta(query);
				ServletOutputStream out = resp.getOutputStream();
				InputStream in=null;
				if(rs.next()){
					//System.out.println("Entrou no Blob");
					Blob imageBlob = rs.getBlob(1);
					in = imageBlob.getBinaryStream(1,(int)imageBlob.length());
					
			        int length = (int) imageBlob.length();

			        int bufferSize = 1024;
			        byte[] buffer = new byte[bufferSize];

			        while ((length = in.read(buffer)) != -1) {
			            //System.out.println("writing " + length + " bytes");
			            out.write(buffer, 0, length);
			        }

			        
				    //System.out.println(dados_tabela);
			        resp.setContentType("image/png");  
			    	//resp.setCharacterEncoding("UTF-8"); 
			    	in.close();
			        out.flush();
			    	//out.print(dados_tabela);
			}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("12")){
			param1=req.getParameter("id_item");
			//System.out.println(param1);
			if(!param1.equals("") && !param1.equals(null)) {
			rs=mysql.Consulta("select *,now() from vistoria_campos where empresa="+p.getEmpresa().getEmpresa_id()+" and field_id="+param1);
			if(rs.next()) {
				resp.setContentType("application/Text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("[\""+rs.getString("field_name")+"\",\""+rs.getString("field_desc")+"\","+rs.getInt("linhaExcel")+","+rs.getInt("colunaExcel")+","+rs.getInt("sheetExcel")+"]");
			
			}else {
				resp.setContentType("application/Text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("[\"vazio\"]");
			
			}
		  }else {
			  resp.setContentType("application/Text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print("[\"vazio\"]");
		  }
		}
		mysql.getConnection().commit();
		mysql.fecharConexao();
		}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
