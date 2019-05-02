	package servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import classes.ConexaoMongo;
import classes.Conexao;
import classes.Feriado;
import classes.Pessoa;
import classes.Semail;

/**
 * Servlet implementation class UserMgmt
 */
@WebServlet("/UserMgmt")
public class UserMgmt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String padrao_data_br = "dd/MM/yyyy";
	private static final String padrao_data_hora_br = "dd/MM/yyyy HH:mm:ss";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserMgmt() {
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
		gerenciamento_usuarios(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamento_usuarios(request,response);
	}
	@SuppressWarnings("null")
	public void gerenciamento_usuarios(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ResultSet rs,rs2 ;
		String param1;
		String param2;
		String param3;
		String param4;
		String param5;
		String param6;
		String param7;
		String param8;
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
		Feriado feriado= new Feriado();
		Conexao conn = (Conexao) session.getAttribute("conexao");
		
		param1="";
		param2="";
		param3="";
		param4="";
		param5="";
		param6="";
		param7="";
		param8="";
		last_id=0;
		
		opt=req.getParameter("opt");
		//System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Operações de Usuários do MSTP Web - "+f3.format(time)+" opt:"+opt);
		//System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" acessando servlet de Usuários opt - "+ opt );
		try {
			Semail email= new Semail();
			if(opt.equals("1")){
				param1=req.getParameter("func");
				param2=req.getParameter("mes");
				param3=req.getParameter("inicio");
				param4=req.getParameter("fim");
				SimpleDateFormat format = new SimpleDateFormat(padrao_data_br); 
				Calendar inicio= Calendar.getInstance();
				Calendar fim= Calendar.getInstance();
				Date dt_inicio=format.parse(param3);
				Date dt_fim=format.parse(param4);
				inicio.setTime(dt_inicio);
				fim.setTime(dt_fim);
				//System.out.println("inicio:"+f2.format(inicio.getTime())+" fim: "+f2.format(fim.getTime()));
				String local;
				String encontrado="n";
				String tipo_registro="Normal";
				String tipo_ajustar=" - ";
				String entrada="00:00:00";
				String saida="00:00:00";
				String [] HH = new String[4];
				int contador=0;
				local="Sem Localização";
				HH[0] = "Caculo Indisponivel";
				HH[1]="0.0";
				HH[2]="0.0";
				HH[3]="0.0";
				query="select distinct data_dia from registros where usuario='"+param1+"' and str_to_date(datetime_servlet,'%Y-%m-%d') >= str_to_date('"+f2.format(inicio.getTime())+"','%d/%m/%Y') and str_to_date(datetime_servlet,'%Y-%m-%d') <= str_to_date('"+f2.format(fim.getTime())+"','%d/%m/%Y') order by datetime_servlet asc" ;
				//System.out.println(query);
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 7 ,\n";
					dados_tabela=dados_tabela+"\"data\":[\n";
					//d.set(Calendar.MONTH, (Integer.parseInt(param2)-1));
					//d.set(Calendar.DAY_OF_MONTH, 1);
					d.setTime(dt_inicio);
					fim.add(Calendar.DAY_OF_MONTH, 1);
					while(d.before(fim)) {
						//System.out.println("entrou no loop");
						//System.out.println("data de pesquisa:"+f2.format(d.getTime()));
						contador=contador+1;
					while(rs.next()) {
						HH[0] = "Caculo Indisponivel";
						HH[1]="0.0";
						HH[2]="0.0";
						HH[3]="0.0";
						if(f2.format(d.getTime()).equals(rs.getString("data_dia"))) {
							if(d.get(Calendar.DAY_OF_WEEK)==1) {
								tipo_registro="Domingo";
								tipo_ajustar=" - ";
							}else if(d.get(Calendar.DAY_OF_WEEK)==7) {
								tipo_registro="Sábado";
								tipo_ajustar=" - ";
							}else if(feriado.verifica_feriado(f2.format(d.getTime()),p.getEstadoUsuario(param1,conn), conn, p.getEmpresa().getEmpresa_id())) {
								tipo_registro="Feriado";
								tipo_ajustar=" - ";
							}else {
								tipo_registro="Normal";
								tipo_ajustar=" - ";
							}
						
						dados_tabela=dados_tabela + "["+"\n";
						dados_tabela=dados_tabela + "\""+rs.getString("data_dia")+"\","+"\n";
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Folga' order by datetime_servlet asc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\"-:-\",\"-:-\",\"-:-\",\"-:-\","+"\n";
							tipo_registro="Folga";
						}else {
							query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Compensação' order by datetime_servlet asc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								dados_tabela=dados_tabela + "\"-:-\",\"-:-\",\"-:-\",\"-:-\","+"\n";
								tipo_registro="Compensação";
						}else {
								query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Licença Médica' order by datetime_servlet asc limit 1";
								rs2=conn.Consulta(query);
								if(rs2.next()) {
									dados_tabela=dados_tabela + "\"-:-\",\"-:-\",\"-:-\",\"-:-\","+"\n";
									tipo_registro="Licença Médica";
						}else {
							query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Férias' order by datetime_servlet asc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								dados_tabela=dados_tabela + "\"-:-\",\"-:-\",\"-:-\",\"-:-\","+"\n";
								tipo_registro="Férias";
					   }else {	
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							entrada=rs2.getString("datetime_servlet");
							local=rs2.getString("tipo_local_registro");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
							entrada="";
						}
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Inicio_intervalo' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							local=rs2.getString("tipo_local_registro");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
						}
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Fim_intervalo' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							local=rs2.getString("tipo_local_registro");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
						}
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							saida=rs2.getString("datetime_servlet");
							
							local=rs2.getString("tipo_local_registro");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
							saida="";
						}
						if(!entrada.equals("") && !saida.equals("")) {
							//System.out.println(entrada);
							//System.out.println(rs.getString("data_dia"));
							HH=calcula_hh(entrada,saida,feriado,conn,p,param1);
						}else if(!entrada.equals("") && saida.equals("")) {
							HH=calcula_hh(entrada,entrada.substring(0, 10)+" 18:30:00",feriado,conn,p,param1);
						}
							}}}
						}
						dados_tabela=dados_tabela + "\""+local+"\","+"\n";
						dados_tabela=dados_tabela + "\""+tipo_registro+"\","+"\n";
						if(tipo_registro.equals("Anormal")) {
							if(p.get_PessoaUsuario().equals(param1)) {
							dados_tabela=dados_tabela + "\"<button class='btn btn-link' style='height:25px' data-toggle='modal' data-target='#modal_ajuste_ponto' data-dia='"+f2.format(d.getTime())+"'>Ajustar</button>\""+"\n";
							}else {
								dados_tabela=dados_tabela + "\" - \""+"\n";	
							}
						}else {
							dados_tabela=dados_tabela + "\"OK\""+"\n";
						}
						
						dados_tabela=dados_tabela + ",\""+HH[0]+":"+HH[1]+"\""+"\n";
						dados_tabela=dados_tabela + "],"+"\n";
						encontrado="s";
					}
					}
					
					if(encontrado.equals("n")) {
						if(d.get(Calendar.DAY_OF_WEEK)==1) {
							tipo_registro="Domingo";
							tipo_ajustar=" - ";
							}else if(d.get(Calendar.DAY_OF_WEEK)==7) {
								tipo_registro="Sábado";
								tipo_ajustar=" - ";
							}else if(feriado.verifica_feriado(f2.format(d.getTime()),p.getEstadoUsuario(param1,conn), conn, p.getEmpresa().getEmpresa_id())) {
								tipo_registro="Feriado";
								tipo_ajustar=" - ";
							}else {
								tipo_registro="Falta";
								if(p.get_PessoaUsuario().equals(param1)) {
								tipo_ajustar="<button class='btn btn-link' style='height:25px' data-toggle='modal' data-target='#modal_ajuste_ponto' data-dia='"+f2.format(d.getTime())+"'>Ajustar</button>";
								}else {
								tipo_ajustar=" - ";
								}
							}
						dados_tabela=dados_tabela+"[\""+f2.format(d.getTime())+"\",\"\",\"\",\"\",\"\",\"\",\""+tipo_registro+"\",\""+tipo_ajustar+"\",\"-\"],\n";
						
					}
					encontrado="n";
					rs.beforeFirst();
					d.add(Calendar.DAY_OF_MONTH, 1);
					}
					
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]}";
					dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
					//dados_tabela=dados_tabela.replace("replace2", Integer.toString(contador));
					
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
					
				}else {
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": 0 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 0 ,\n";
					dados_tabela=dados_tabela+"\"data\":[]\n";
					dados_tabela=dados_tabela+"}";
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("2")){
				if(p.get_PessoaPerfil_nome().equals("tecnico")) {
					query="select * from usuarios where id_usuario='"+p.get_PessoaUsuario()+"' and ativo='Y' and validado='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
				}else {
					query="select * from usuarios where ativo='Y' and validado='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"' and exibe_ponto_analise='Y'";
				}
				rs=conn.Consulta(query);
				if(rs.next()) {
					dados_tabela="";
					rs.beforeFirst();
					dados_tabela=dados_tabela+"<option value=''></option>";
					while(rs.next()) {
						dados_tabela=dados_tabela+"<option value='"+rs.getString("id_usuario")+"'>"+rs.getString("nome")+"</option>";
					}
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime()))+" segundos");
			}else if(opt.equals("3")){
				param1=req.getParameter("users");
				if(param1.indexOf(",")>0) {
					String aux[];
					aux=param1.split(",");
					for(int i=0;i<aux.length;i++) {
						conn.Update_simples("update usuarios set ativo='N' where id_usuario='"+aux[i]+"'");
					}
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					System.out.println("Usuários desabilitados por "+p.get_PessoaUsuario()+":"+param1);
					out.print("Usuários Desabilitados com Sucesso!");
				}else {
					if(conn.Update_simples("update usuarios set ativo='N' where id_usuario='"+param1+"'")){
						resp.setContentType("application/text");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						System.out.println("Usuários desabilitados por "+p.get_PessoaUsuario()+":"+param1);
						out.print("Usuários Desabilitados com Sucesso!");
					}
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("4")){
				param1=req.getParameter("data");
				System.out.println("Inserindo horas de "+p.get_PessoaUsuario()+" em "+f3.format(time));
				//System.out.println(param1);
				
				JSONObject jObj2;
				JSONArray campos = new JSONArray(param1);
				
				for(int b=0;b<campos.length();b++) {
					jObj2=campos.getJSONObject(b);
					//System.out.println(jObj2.getInt("id"));
					conn.Inserir_simples("insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,origen,aprovada,dt_add,horas_noturnas,obs_hh) values('"+p.get_PessoaUsuario()+"','"+jObj2.getString("H_data")+"',"+jObj2.getDouble("H_normais")+",'"+jObj2.getString("H_Entrada")+"','"+jObj2.getString("H_Saida")+"','Manual - MSTP WEB','N','"+f3.format(time)+"',"+jObj2.getDouble("H_noturna")+",'"+jObj2.getString("H_anotacao")+"')");
						
					
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("5")){
				query="select * from horas_extras where id_usuario='"+p.get_PessoaUsuario()+"' and aprovada='Y' and compensada='N'";
				System.out.println("Carregabdo extrato de horas "+p.get_PessoaUsuario()+" em "+f3.format(time));
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="<table style=\"color:black\" id=\"tabela_registro_de_HH\" "
							
							+ "data-use-row-attr-func=\"true\" "
							
							+ "data-show-refresh=\"true\" "
							+ "data-toggle=\"table\"  "
							+ "data-pagination=\"true\" "
							+ "data-height=\"420\" "
							+ "data-locale=\"pt-BR\" "
							+ "data-page-size=\"10\" "
							+ "data-page-list=\"[10, 20, 50, 100, 200]\" "
							+ "data-show-columns=\"true\" "
							+ "data-search=\"true\">" +"\n";
					dados_tabela=dados_tabela + "<thead style=\"color:black\">"+"\n";
					dados_tabela=dados_tabela +"<tr style=\"color:black\">"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"data_he\">Data</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"data_he_entrada\" >Inicio</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"data_he_saida\">Fim</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"data_he_h_normais\">Horas Normais</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"data_he_h_noturna\">Horas Noturnas</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"data_he_origen\">Origen</th>"+"\n";
					dados_tabela=dados_tabela +"</tr>"+"\n";
					dados_tabela=dados_tabela +"</thead>"+"\n";
					dados_tabela=dados_tabela +"<tbody>"+"\n";
					while(rs.next()) {
						dados_tabela=dados_tabela + "<tr>"+"\n";
						dados_tabela=dados_tabela + " <td>"+rs.getString("he_data")+"</td>"+"\n";
						dados_tabela=dados_tabela + " <td>"+rs.getString("entrada")+"</td>"+"\n";
						dados_tabela=dados_tabela + " <td>"+rs.getString("saida")+"</td>"+"\n";
						dados_tabela=dados_tabela + " <td>"+rs.getString("he_quantidade")+"</td>"+"\n";
						dados_tabela=dados_tabela + " <td>"+rs.getString("horas_noturnas")+"</td>"+"\n";
						dados_tabela=dados_tabela + " <td>"+rs.getString("origen")+"</td>"+"\n";
						dados_tabela=dados_tabela + "</tr>"+"\n";
					}
					dados_tabela=dados_tabela + "</tbody>";
					dados_tabela=dados_tabela + "</table>";
					
					//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("6")){
				double horas;
				query="select sum(he_quantidade),sum(horas_noturnas) from horas_extras where id_usuario='"+p.get_PessoaUsuario()+"' and aprovada='Y' and compensada='N'";
				System.out.println("Consultando Horas de  "+p.get_PessoaUsuario()+" em "+f3.format(time));
				rs=conn.Consulta(query);
				if(rs.next()) {
					horas=rs.getDouble(1)+rs.getDouble(2);
					
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(horas);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("7")){
				query="";
				if(p.getPerfil_funcoes().contains("BancoHHApprover")) {
					query="select * from horas_extras where aprovada='N' and empresa="+p.getEmpresa().getEmpresa_id();
					rs=conn.Consulta(query);
				
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="<table id=\"tabela_aprovacoes\" "
							+ "data-filter-control=\"true\" "
							+ "data-use-row-attr-func=\"true\" "
							+ "data-unique-id=\"aprove_id\" "
							+ "data-show-refresh=\"true\" "
							+ "data-toggle=\"table\"  "
							+ "data-pagination=\"true\" "
							+ "data-height=\"520\" "
							+ "data-locale=\"pt-BR\" "
							+ "data-page-size=\"10\" "
							+ "data-page-list=\"[10, 20, 50, 100, 200]\" "
							+ "data-show-columns=\"true\" "
							+ "data-search=\"true\">" +"\n";
					dados_tabela=dados_tabela + "<thead>"+"\n";
					dados_tabela=dados_tabela +"<tr>"+"\n";
					dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_oper\">Operações</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_id\">Id</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_tipo\" data-filter-control=\"select\">Tipo</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_pessoa\" data-filter-control=\"select\">Requisitante</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_desc\">Descrição</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_dt_sub\">Data de Envio</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_anotacoes\">Anotações</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"foto_justificativa\" data-filter-control=\"select\">Foto</th>"+"\n";
					dados_tabela=dados_tabela +"</tr>"+"\n";
					dados_tabela=dados_tabela +"</thead>"+"\n";
					dados_tabela=dados_tabela +"<tbody>"+"\n";
					while(rs.next()) {
						dados_tabela=dados_tabela +"<tr>"+"\n";
						dados_tabela=dados_tabela +" <td></td>"+"\n";
						dados_tabela=dados_tabela +" <td><div style='float:left;padding-left: 10px;'><a href='#' onclick=aprova1_registro('BancoHH','"+rs.getInt("id_he")+"-BH')><i class='fas fa-check fa-lg'></i></a></div><div style='float:left;padding-left: 10px;'><a href='#' onclick=rejeita1_registro('BancoHH','"+rs.getInt("id_he")+"-BH')><i class='fas fa-times fa-lg' style='color:red'></i></a></div></td>"+"\n";
						dados_tabela=dados_tabela +" <td>"+rs.getInt("id_he")+"-BH</td>"+"\n";
						dados_tabela=dados_tabela +" <td>Banco de Horas</td>"+"\n";
						dados_tabela=dados_tabela +" <td>"+rs.getString("id_usuario")+"</td>"+"\n";
						dados_tabela=dados_tabela +" <td>Hora Inicio:"+rs.getString("entrada")+"<br>Hora Fim:"+rs.getString("saida")+"<br>Horas Extras normais:"+rs.getDouble("he_quantidade")+"<br>Horas Extras noturnas:"+rs.getString("horas_noturnas")+"</td>"+"\n";
						dados_tabela=dados_tabela +" <td>"+rs.getString("he_data")+"</td>"+"\n";
						dados_tabela=dados_tabela +" <td>"+rs.getString("obs_hh")+"</td>"+"\n";
						dados_tabela=dados_tabela +" <td>Foto Não requirida</td>"+"\n";
						dados_tabela=dados_tabela +"</tr>"+"\n";
					}
					dados_tabela=dados_tabela + "</tbody>";
					dados_tabela=dados_tabela + "</table>";
					
					//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else {
					dados_tabela="<table id=\"tabela_aprovacoes\" "
							+ "data-filter-control=\"true\" "
							+ "data-use-row-attr-func=\"true\" "
							+ "data-unique-id=\"aprove_id\" "
							+ "data-show-refresh=\"true\" "
							+ "data-toggle=\"table\"  "
							+ "data-pagination=\"true\" "
							+ "data-height=\"520\" "
							+ "data-locale=\"pt-BR\" "
							+ "data-page-size=\"10\" "
							+ "data-page-list=\"[10, 20, 50, 100, 200]\" "
							+ "data-show-columns=\"true\" "
							+ "data-search=\"true\">" +"\n";
					dados_tabela=dados_tabela + "<thead>"+"\n";
					dados_tabela=dados_tabela +"<tr>"+"\n";
					dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_oper\">Operações</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_id\">Id</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_tipo\" data-filter-control=\"select\">Tipo</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_pessoa\" data-filter-control=\"select\">Requisitante</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_desc\">Descrição</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_dt_sub\">Data de Envio</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_anotacoes\">Anotações</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"foto_justificativa\" data-filter-control=\"select\">Origem</th>"+"\n";
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
				}}else {
					dados_tabela="<table id=\"tabela_aprovacoes\" "
							+ "data-filter-control=\"true\" "
							+ "data-use-row-attr-func=\"true\" "
							+ "data-unique-id=\"aprove_id\" "
							+ "data-show-refresh=\"true\" "
							+ "data-toggle=\"table\"  "
							+ "data-pagination=\"true\" "
							+ "data-height=\"520\" "
							+ "data-locale=\"pt-BR\" "
							+ "data-page-size=\"10\" "
							+ "data-page-list=\"[10, 20, 50, 100, 200]\" "
							+ "data-show-columns=\"true\" "
							+ "data-search=\"true\">" +"\n";
					dados_tabela=dados_tabela + "<thead>"+"\n";
					dados_tabela=dados_tabela +"<tr>"+"\n";
					dados_tabela=dados_tabela +" <th data-checkbox=\"true\"></th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_oper\">Operações</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_id\">Id</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_tipo\" data-filter-control=\"select\">Tipo</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"aprove_pessoa\" data-filter-control=\"select\">Requisitante</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_desc\">Descrição</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_dt_sub\">Data de Envio</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"approve_anotacoes\">Anotações</th>"+"\n";
					dados_tabela=dados_tabela +" <th data-field=\"foto_justificativa\" data-filter-control=\"select\">Origem</th>"+"\n";
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
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("8")){
				if(p.getPerfil_funcoes().contains("BancoHHApprover")) {
				param1=req.getParameter("he");
				query="update horas_extras set aprovada='Y' where id_he="+param1;
				if(conn.Update_simples(query)) {
					rs=conn.Consulta("select * from horas_extras where id_he="+param1);
					if(rs.next()) {
						rs2=conn.Consulta("select * from usuarios where id_usuario='"+rs.getString("id_usuario")+"'");
						if(rs2.next()) {
							//Semail email= new Semail();
							email.enviaEmailSimples(rs2.getString("email"),"MSTP WEB - Atualização de Banco de Horas","Prezado "+rs2.getString("nome")+", \n \n Sua solicitação de banco de horas foi aprovada, com as seguintes informações: \n ID Solicitação: "+param1+"\n usuario: "+rs.getString("id_usuario")+"\n Hora Inicio: "+rs.getString("entrada")+"\n Hora Saida: "+rs.getString("saida")+"\n Horas Extras normais: "+rs.getString("he_quantidade")+"\n Horas Extras noturnas: "+rs.getString("horas_noturnas")+"\n \n \n \n ");
						}
						
					}
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Aprovado com Sucesso!");
				}
				}else {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Sem Privilégios para essa Operação");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("9")){
				if(p.getPerfil_funcoes().contains("BancoHHApprover")) {
				param1=req.getParameter("he");
				query="update horas_extras set aprovada='R' where id_he="+param1;
				if(conn.Update_simples(query)) {
					rs=conn.Consulta("select * from horas_extras where id_he="+param1);
					if(rs.next()) {
						rs2=conn.Consulta("select * from usuarios where id_usuario='"+rs.getString("id_usuario")+"'");
						if(rs2.next()) {
							//Semail email= new Semail();
							email.enviaEmailSimples(rs2.getString("email"),"MSTP WEB - Atualização de Banco de Horas","Prezado "+rs2.getString("nome")+", \n \n Sua solicitação de aprovação de banco de horas foi rejeitada, com as seguintes informações: \n ID Solicitação: "+param1+"\n usuario: "+rs.getString("id_usuario")+"\n Hora Inicio: "+rs.getString("entrada")+"\n Hora Saida: "+rs.getString("saida")+"\n Horas Extras normais: "+rs.getString("he_quantidade")+"\n Horas Extras noturnas: "+rs.getString("horas_noturnas")+"\n \n \n \n ");
						}
						
					}
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Rejeitada com Sucesso!");
				}}else {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Sem Privilégios para essa Operação");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("10")){
				param1=req.getParameter("usuario");
				int contador=0;
				if(p.get_PessoaPerfil_nome().equals("tecnico")) {
					query="SELECT * from registros where usuario='"+p.get_PessoaUsuario()+"' and week(datetime_servlet)=week(now()) and empresa='"+p.getEmpresa().getEmpresa_id()+"' and tipo_registro not in('Férias','Folga','Compensação','Licença Médica') order by datetime_servlet desc";
				}else {
				if(param1.equals("todos")) {
					query="SELECT * from registros where week(datetime_servlet)=week(now()) and empresa='"+p.getEmpresa().getEmpresa_id()+"' and tipo_registro not in('Férias','Folga','Compensação','Licença Médica') order by datetime_servlet desc";
				}else {
					query="SELECT * from registros where usuario='"+param1+"' and week(datetime_servlet)=week(now()) and empresa='"+p.getEmpresa().getEmpresa_id()+"' and tipo_registro not in('Férias','Folga','Compensação','Licença Médica') order by datetime_servlet desc";
				}
				}
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="{\n";
					dados_tabela=dados_tabela +"\"draw\":1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 5 ,\n";
					dados_tabela=dados_tabela+"\"data\":[\n";
					while(rs.next()) {
						contador=contador+1;
						dados_tabela=dados_tabela + "["+"\n";
						dados_tabela=dados_tabela + "\""+rs.getString("usuario")+"\","+"\n";
						dados_tabela=dados_tabela + "\""+rs.getString("datetime_mobile")+"\","+"\n";
						dados_tabela=dados_tabela + "\""+rs.getString("tipo_registro")+"\","+"\n";
						dados_tabela=dados_tabela + "\""+rs.getString("local_registro")+" - "+rs.getString("tipo_local_registro")+"\","+"\n";
						dados_tabela=dados_tabela + "\""+rs.getString("distancia")+" KM\""+"\n";
						
						dados_tabela=dados_tabela + "],"+"\n";
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]}";
					dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
					
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else {
					dados_tabela="{\n";
					dados_tabela=dados_tabela +"\"draw\":1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": 0 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 0 ,\n";
					dados_tabela=dados_tabela+"\"data\":[]\n";
					dados_tabela=dados_tabela+"}";
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("11")){
				param1=req.getParameter("entrada");
				param2=req.getParameter("saida");
				param3=req.getParameter("local");
				param4=req.getParameter("motivo");
				param5=req.getParameter("data_folga_compensacao");
				param6=req.getParameter("select_folga_compensacao");
				query="";
				if(param6.equals("1")) {
					query="insert into ajuste_ponto (dt_solicitado,dt_entrada,dt_saida,local,motivo,aprovada,usuario,other2,empresa) values ('"+f3.format(time)+"','','','','FOLGA','N','"+p.get_PessoaUsuario()+"','"+param5+"',"+p.getEmpresa().getEmpresa_id()+")";
				}else if(param6.equals("2")) {
					query="insert into ajuste_ponto (dt_solicitado,dt_entrada,dt_saida,local,motivo,aprovada,usuario,other2,empresa) values ('"+f3.format(time)+"','','','','Banco de Horas - Consumo de 8h','N','"+p.get_PessoaUsuario()+"','"+param5+"',"+p.getEmpresa().getEmpresa_id()+")";
				}else {
					query="insert into ajuste_ponto (dt_solicitado,dt_entrada,dt_saida,local,motivo,aprovada,usuario,empresa) values ('"+f3.format(time)+"','"+param1+"','"+param2+"','"+param3+"','"+param4+"','N','"+p.get_PessoaUsuario()+"',"+p.getEmpresa().getEmpresa_id()+")";
				}
				if(conn.Inserir_simples(query)) {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					rs=conn.Consulta("select * from usuarios where perfil='ADM'");
					if(rs.next()) {
						//Semail email= new Semail();
						email.enviaEmailSimples(rs.getString("email"),"MSTP WEB - Solicitação de Ajuste de Ponto","Prezado "+rs.getString("nome")+", \n \n Voce recebeu uma solicitação de aprovação de ajuste de ponto com as seguintes informações: \n usuario: "+p.get_PessoaUsuario()+"\n Nova Entrada: "+param1+"\n Nova Saida: "+param2+"\n Local: "+param3+"\n Motivo: "+param4+"\n \n \n \n Acesse: Menu > Aprovações em www.mstp.com.br");
						email.enviaEmailSimples(p.getEmail(),"MSTP WEB - Atualização de Ajuste de Ponto","Prezado "+p.get_PessoaName()+", \n \n Sua solicitação de aprovação de ajuste de ponto foi enviada com sucesso, com as seguintes informações: \n usuario: "+p.get_PessoaUsuario()+"\n Nova Entrada: "+param1+"\n Nova Saida: "+param2+"\n Local: "+param3+"\n Motivo: "+param4+"\n \n \n \n");
					}
					
					out.print("Solicitação enviada com sucesso!");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("12")){
				System.out.println("carregando ajustes de ponto para aprovação");
				String caminho_foto="";
				query="";
				query="select * from ajuste_ponto where aprovada='N' and empresa="+p.getEmpresa().getEmpresa_id();
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="[\n";
					while(rs.next()) {
						if((rs.getString("motivo").equals("FOLGA")) && (!rs.getString("other2").equals(""))) {
							dados_tabela=dados_tabela+"{\"aprove_oper\":\"<div style='float:left;padding-left: 10px;'><a href='#' onclick=aprova1_registro('AjustePonto','"+rs.getInt("id_ajuste_ponto")+"-AP')><i class='fas fa-check fa-lg'></i></a></div><div style='float:left;padding-left: 10px;'><a href='#' onclick=rejeita1_registro('AjustePonto','"+rs.getInt("id_ajuste_ponto")+"-AP')><i class='fas fa-times fa-lg' style='color:red'></i></a></div>\",\"aprove_id\":\""+rs.getInt("id_ajuste_ponto")+"-AP\",\"aprove_tipo\":\"Ajuste de Ponto\",\"aprove_pessoa\":\""+rs.getString("usuario")+"\",\"approve_desc\":\"FOLGA - "+rs.getString("other2")+"\",\"approve_dt_sub\":\""+rs.getString("dt_solicitado")+"\",\"approve_anotacoes\":\""+rs.getString("motivo")+"\",\"foto_justificativa\":\"Foto Não Requirida.\"},\n";
						}else if((rs.getString("motivo").equals("Banco de Horas - Consumo de 8h")) && (!rs.getString("other2").equals(""))) {
							dados_tabela=dados_tabela+"{\"aprove_oper\":\"<div style='float:left;padding-left: 10px;'><a href='#' onclick=aprova1_registro('AjustePonto','"+rs.getInt("id_ajuste_ponto")+"-AP')><i class='fas fa-check fa-lg'></i></a></div><div style='float:left;padding-left: 10px;'><a href='#' onclick=rejeita1_registro('AjustePonto','"+rs.getInt("id_ajuste_ponto")+"-AP')><i class='fas fa-times fa-lg' style='color:red'></i></a></div>\",\"aprove_id\":\""+rs.getInt("id_ajuste_ponto")+"-AP\",\"aprove_tipo\":\"Ajuste de Ponto\",\"aprove_pessoa\":\""+rs.getString("usuario")+"\",\"approve_desc\":\"Compensação por Banco de Horas. Consumo de 8H - "+rs.getString("other2")+"\",\"approve_dt_sub\":\""+rs.getString("dt_solicitado")+"\",\"approve_anotacoes\":\""+rs.getString("motivo")+"\",\"foto_justificativa\":\"Foto Não Requirida.\"},\n";
						}else {
							if(rs.getBlob("foto_justificativa")!=null) {
								if(!rs.getString("foto_justificativa").equals("")) {
								caminho_foto="<a class='btn btn-success' href='#' onclick=visualiza_foto_justificativa("+rs.getInt("id_ajuste_ponto")+")>Visualizar Foto</a>";
								}else {
									caminho_foto="Foto Não Requirida.";
								}
							}else {
								caminho_foto="Foto Não Requirida.";
							}
						dados_tabela=dados_tabela+"{\"aprove_oper\":\"<div style='float:left;padding-left: 10px;'><a href='#' onclick=aprova1_registro('AjustePonto','"+rs.getInt("id_ajuste_ponto")+"-AP')><i class='fas fa-check fa-lg'></i></a></div><div style='float:left;padding-left: 10px;'><a href='#' onclick=rejeita1_registro('AjustePonto','"+rs.getInt("id_ajuste_ponto")+"-AP')><i class='fas fa-times fa-lg' style='color:red'></i></a></div>\",\"aprove_id\":\""+rs.getInt("id_ajuste_ponto")+"-AP\",\"aprove_tipo\":\"Ajuste de Ponto\",\"aprove_pessoa\":\""+rs.getString("usuario")+"\",\"approve_desc\":\"Hora Inicio: "+rs.getString("dt_entrada")+"<br>Hora Fim: "+rs.getString("dt_saida")+"\",\"approve_dt_sub\":\""+rs.getString("dt_solicitado")+"\",\"approve_anotacoes\":\""+rs.getString("motivo")+"\",\"foto_justificativa\":\""+caminho_foto+"\"},\n";
						}
						}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"\n]";
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");	
			}else if(opt.equals("13")){
				if(p.getPerfil_funcoes().contains("AjustePontoApprover")) {
				param1=req.getParameter("id");
				query="update ajuste_ponto set aprovada='R',dt_aprovada='"+f3.format(time)+"' where id_ajuste_ponto="+param1;
				if(conn.Update_simples(query)) {
					rs=conn.Consulta("select * from ajuste_ponto where id_ajuste_ponto="+param1);
					if(rs.next()) {
						rs2=conn.Consulta("select * from usuarios where id_usuario='"+rs.getString("usuario")+"'");
						if(rs2.next()) {
							//Semail email= new Semail();
							email.enviaEmailSimples(rs2.getString("email"),"MSTP WEB - Atualização de Ajuste de Ponto","Prezado "+rs2.getString("nome")+", \n \n Sua solicitação de aprovação de ajuste de ponto foi rejeitada. com as seguintes informações: \n ID Solicitação: "+rs.getInt(1)+"\n usuario: "+rs.getString("usuario")+"\n Nova Entrada: "+rs.getString("dt_entrada")+"\n Nova Saida: "+rs.getString("dt_saida")+"\n Local: "+rs.getString("local")+"\n Motivo: "+rs.getString("motivo")+"\n \n \n \n ");
						}
						
					}
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Rejeitada com Sucesso!");
				}
				}else {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Sem privilégio para essa Operação");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("14")){
				if(p.getPerfil_funcoes().contains("AjustePontoApprover")) {
				param1=req.getParameter("id");
				query="update ajuste_ponto set aprovada='Y',dt_aprovada='"+f3.format(time)+"' where id_ajuste_ponto="+param1;
				if(conn.Update_simples(query)) {
					query="select * from ajuste_ponto where id_ajuste_ponto="+param1+" and aprovada='Y'";
					rs=conn.Consulta(query);
					if(rs.next()) {
						if((rs.getString("motivo").toUpperCase().equals("FOLGA")) && (!rs.getString("other2").equals("0"))) {
							insere_regitro(p,rs.getString("usuario"),"Folga",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Folga, - , - ");
						}else if((rs.getString("motivo").equals("Banco de Horas - Consumo de 8h")) && (!rs.getString("other2").equals("0"))) {
							insere_regitro(p,rs.getString("usuario"),"Compensação",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Compensado, - , - ");
							conn.Inserir_simples("insert into horas_extras (he_data,id_usuario,he_quantidade,aprovada,dt_add,compensada,origen) values('"+rs.getString("other2")+"','"+rs.getString("usuario")+"','-8.00','Y','"+f3.format(time)+"','N','Compensação - MSTP WEB')");
						}else if((rs.getString("motivo").equals("Compensação de horas")) && (!rs.getString("other2").equals("0"))) {
							insere_regitro(p,rs.getString("usuario"),"Compensação",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Compensado, - , - ");
							conn.Inserir_simples("insert into horas_extras (he_data,id_usuario,he_quantidade,aprovada,dt_add,compensada,origen) values('"+rs.getString("other2")+"','"+rs.getString("usuario")+"','-8.00','Y','"+f3.format(time)+"','N','Compensação - MSTP WEB')");
						}else if((rs.getString("motivo").equals("Licença Médica")) && (!rs.getString("other2").equals("0"))) {
							insere_regitro(p,rs.getString("usuario"),"Licença Médica",conn,"0","0",rs.getString("other2")+" 00:00:00","0","","Licença Médica, - , - ");
							
						}else {
							insere_regitro(p,rs.getString("usuario"),"Entrada",conn,"0","0",rs.getString("dt_entrada"),"0",rs.getString("dt_entrada"),"PontoAjustado,"+p.getEmpresa().getNome()+","+p.getEmpresa().getNome());
							insere_regitro(p,rs.getString("usuario"),"Saída",conn,"0","0",rs.getString("dt_saida"),"0",rs.getString("dt_saida"),"PontoAjustado,"+p.getEmpresa().getNome()+","+p.getEmpresa().getNome());
							insere_regitro(p,rs.getString("usuario"),"Inicio_intervalo",conn,"0","0",rs.getString("dt_ini_inter"),"0",rs.getString("dt_ini_inter"),"PontoAjustado,"+p.getEmpresa().getNome()+","+p.getEmpresa().getNome());
							insere_regitro(p,rs.getString("usuario"),"Fim_intervalo",conn,"0","0",rs.getString("dt_fim_inter"),"0",rs.getString("dt_fim_inter"),"PontoAjustado,"+p.getEmpresa().getNome()+","+p.getEmpresa().getNome());
						}
							rs2=conn.Consulta("select * from usuarios where id_usuario='"+rs.getString("usuario")+"'");
							if(rs2.next()) {
								//Semail email= new Semail();
								email.enviaEmailSimples(rs2.getString("email"),"MSTP WEB - Atualização de Ajuste de Ponto","Prezado "+rs2.getString("nome")+", \n \n Sua solicitação de aprovação de ajuste de ponto foi aprovada. com as seguintes informações: \n ID Solicitação: "+rs.getInt(1)+"\n usuario: "+rs.getString("usuario")+"\n Nova Entrada: "+rs.getString("dt_entrada")+"\n Nova Saida: "+rs.getString("dt_saida")+"\n Local: "+rs.getString("local")+"\n Motivo: "+rs.getString("motivo")+"\n \n \n \n ");
							}
							
						
					
					}
					
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Aprovada com Sucesso!");
				}}else {
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Sem privilégio para essa Operação");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("15")){
				param1=req.getParameter("usuario");
				query="select * from usuarios where id_usuario='"+param1+"' and ativo='Y' and validado='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					dados_tabela="{\n";
					dados_tabela=dados_tabela+"\"usuario\":\""+rs.getString("id_usuario")+"\",\n";
					dados_tabela=dados_tabela+"\"nome\":\""+rs.getString("nome")+"\",\n";
					dados_tabela=dados_tabela+"\"cargo\":\""+rs.getString("cargo")+"\",\n";
					dados_tabela=dados_tabela+"\"ctps\":\""+rs.getString("ctps")+"\",\n";
					dados_tabela=dados_tabela+"\"admissao\":\""+rs.getString("admissao")+"\",\n";
					dados_tabela=dados_tabela+"\"matricula\":\""+rs.getString("matricula")+"\",\n";
					dados_tabela=dados_tabela+"\"cpf\":\""+rs.getString("cpf")+"\",\n";
					dados_tabela=dados_tabela+"\"pis\":\""+rs.getString("pis")+"\"\n";
					dados_tabela=dados_tabela+"}";
					//System.out.println(dados_tabela);
					resp.setContentType("application/html");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("16")){
				int colIndex = 0;
				int rowIndex = 0;
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("Usuarios");
				Row row;
	            Cell cell;
	            query="select id_usuario_sys,id_usuario,nome,matricula,cpf,email,telefone,admissao,ctps,pis,cargo,validado,ativo from usuarios where ativo='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
	            rs=conn.Consulta(query);
	            if(rs.next()) {
	            	 row = sheet.createRow((short) rowIndex);
	            	 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("MSTP_USER_ID");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("USUARIO");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("NOME");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("MATRICULA");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("CPF");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("EMAIL");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("TELEFONE");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("DT.ADMISSAO");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("NUM CTPS");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("NUM PIS");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("CARGO");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("VALIDADO");
	                 colIndex=colIndex+1;
	                 cell = row.createCell((short) colIndex);
	                 cell.setCellValue("ATIVO");
	                 colIndex=colIndex+1;
	                 rs.beforeFirst();
	                 rowIndex=rowIndex+1;
	                 while(rs.next()) {
	                	 row = sheet.createRow((short) rowIndex);
	                	 for(colIndex=0;colIndex<13;colIndex++) {
		            	 cell = row.createCell((short) colIndex);
		                 cell.setCellValue(rs.getString(colIndex+1));
	                	 }
		                 rowIndex=rowIndex+1;
	                 }
	                // resp.setContentType("application/vnd.ms-excel");
	                 resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	                 resp.setHeader("Content-Disposition", "attachment; filename=usuarios_mstp.xlsx");
	                 workbook.write(resp.getOutputStream());
	                 workbook.close();
	            }
	            Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("17")){
				
				
				if (ServletFileUpload.isMultipartContent(req)) {
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
					//System.out.println("Multipart size: " + multiparts.size());
					Iterator<FileItem> iter = multiparts.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
					       // processFormField(item);
					    } else {
					    	
					    	InputStream inputStream2= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
				    		DataFormatter dataFormatter = new DataFormatter();
				    		int indexCell=1;
				    		XSSFWorkbook wb = new XSSFWorkbook(inputStream2);
				    		Sheet sheet1 = wb.getSheet("Usuarios");
				    		Cell cell;
				    		String cellValue;
				    		last_id=0;
				    		for (Row row : sheet1) {
				    			if(row.getRowNum()>0) {
				    			query="update usuarios set ";
				    			
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"id_usuario='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"nome='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"matricula='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"cpf='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"email='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"telefone='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"admissao='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"ctps='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"pis='"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"cargo='"+cellValue+"',\n";
				    				 indexCell=indexCell+2;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"ativo='"+cellValue+"'\n";
				    				 cell=row.getCell(0);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    			
				    				 query=query+" where id_usuario_sys="+cellValue;
				    				 indexCell=1;
				    			//System.out.println(sql);
				    			if(conn.Update_simples(query)) {
				    				last_id=last_id+1;
				    			}
				    			}
				    		}
				    		wb.close();
					    }
					}
					resp.setContentType("application/json"); 
		        	JSONObject jsonObject = new JSONObject(); 
		        	jsonObject.put("Sucesso", "Sucesso"); 
		        	PrintWriter pw = resp.getWriter();
		        	pw.print(jsonObject); 
		        	pw.close();
		        	
		        	System.out.println(p.get_PessoaUsuario()+" Fim de processamento do arquivo com "+last_id+" linhas atualizadas em "+f3.format(time));
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");    
			}else if(opt.equals("18")){
				param1=req.getParameter("mes");
				param2=req.getParameter("func");
				param7=req.getParameter("inicio");
				param8=req.getParameter("fim");
				SimpleDateFormat format = new SimpleDateFormat(padrao_data_br); 
				
				Date dt_inicio=format.parse(param7);
				Date dt_fim=format.parse(param8);
				
				Double total_horas_acumuladas=0.0;
				Double total_horas_acumuladas_sabado=0.0;
				Double total_horas_acumuladas_domingo=0.0;
				
				resp.setContentType("application/html");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				Calendar data_HH=Calendar.getInstance();
				Calendar data_HH2=Calendar.getInstance();
				data_HH.setTime(dt_inicio);
				data_HH2.setTime(dt_fim);
				String []HH_aux=new String[4];
				NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
				DecimalFormat numberFormat = (DecimalFormat)nf;
				numberFormat.applyPattern("#0.00");
				System.out.println("iniciando recalculo de horas para " + param2 + " no mes " + param1 );
				while(data_HH.before(data_HH2)) {
					param3="";
					param4="";
					param5=f2.format(data_HH.getTime());
					query="SELECT * FROM registros where usuario='"+param2+"' and data_dia='"+f2.format(data_HH.getTime())+"' and tipo_registro='Folga' order by datetime_servlet asc limit 1";
					rs2=conn.Consulta(query);
					if(rs2.next()) {
						
					}
					query="SELECT * FROM registros where usuario='"+param2+"' and data_dia='"+f2.format(data_HH.getTime())+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
					rs2=conn.Consulta(query);
					if(rs2.next()) {
						param3=rs2.getString("datetime_servlet");
					}
					query="SELECT * FROM registros where usuario='"+param2+"' and data_dia='"+f2.format(data_HH.getTime())+"' and tipo_registro='Saída' order by datetime_servlet asc limit 1";
					rs2=conn.Consulta(query);
					if(rs2.next()) {
						param4=rs2.getString("datetime_servlet");
					}else {
						param4="";
					}
					if(!param3.equals("") && !param4.equals("")) {
						HH_aux=calcula_hh(param3,param4,feriado,conn,p,param2);
						
						if(Double.parseDouble(HH_aux[1])>0.0) {
							if(HH_aux[3].equals("Sábado")){
								total_horas_acumuladas_sabado=total_horas_acumuladas_sabado+Double.parseDouble(HH_aux[1]);
							}else if(HH_aux[3].equals("Domingo")) {
								total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH_aux[1]);
							}else if(HH_aux[3].equals("Feriado")) {
								total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH_aux[1]);
							}else {
								total_horas_acumuladas=total_horas_acumuladas+Double.parseDouble(HH_aux[1]);
							}
						}
					}else if(!param3.equals("") && param4.equals("")) {
						HH_aux=calcula_hh(param3,param3.substring(0, 10)+" 18:30:00",feriado,conn,p,param2);
						if(HH_aux[3].equals("Sábado")){
							total_horas_acumuladas_sabado=total_horas_acumuladas_sabado+Double.parseDouble(HH_aux[1]);
						}else if(HH_aux[3].equals("Domingo")) {
							total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH_aux[1]);
						}else if(HH_aux[3].equals("Feriado")) {
							total_horas_acumuladas_domingo=total_horas_acumuladas_domingo+Double.parseDouble(HH_aux[1]);
						}else {
							total_horas_acumuladas=total_horas_acumuladas+Double.parseDouble(HH_aux[1]);
						}
					}
					data_HH.add(Calendar.DAY_OF_MONTH, 1);
				}
				Integer horas_acumuladas=total_horas_acumuladas.intValue();
				Double minutos_acumuladosDouble=(total_horas_acumuladas- Math.floor(total_horas_acumuladas))*60;
				int minutos_acumuladosint=minutos_acumuladosDouble.intValue();
				String total_horas_acumuladastring="";
				String total_horas_acumulada_sabadostring="";
				String total_horas_acumulada_domingostring="";
				if(horas_acumuladas<10) {
					total_horas_acumuladastring="0"+horas_acumuladas;
				}else {
					total_horas_acumuladastring=horas_acumuladas.toString();
				}
				if(minutos_acumuladosint<10) {
					total_horas_acumuladastring=total_horas_acumuladastring+":0"+minutos_acumuladosint;
				}else {
					total_horas_acumuladastring=total_horas_acumuladastring+":"+minutos_acumuladosint;
				}
				horas_acumuladas=total_horas_acumuladas_sabado.intValue();
				minutos_acumuladosDouble=(total_horas_acumuladas_sabado- Math.floor(total_horas_acumuladas_sabado))*60;
				minutos_acumuladosint=minutos_acumuladosDouble.intValue();
				if(horas_acumuladas<10) {
					total_horas_acumulada_sabadostring="0"+horas_acumuladas;
				}else {
					total_horas_acumulada_sabadostring=horas_acumuladas.toString();
				}
				if(minutos_acumuladosint<10) {
					total_horas_acumulada_sabadostring=total_horas_acumulada_sabadostring+":0"+minutos_acumuladosint;
				}else {
					total_horas_acumulada_sabadostring=total_horas_acumulada_sabadostring+":"+minutos_acumuladosint;
				}
				horas_acumuladas=total_horas_acumuladas_domingo.intValue();
				minutos_acumuladosDouble=(total_horas_acumuladas_domingo- Math.floor(total_horas_acumuladas_domingo))*60;
				minutos_acumuladosint=minutos_acumuladosDouble.intValue();
				if(horas_acumuladas<10) {
					total_horas_acumulada_domingostring="0"+horas_acumuladas;
				}else {
					total_horas_acumulada_domingostring=horas_acumuladas.toString();
				}
				if(minutos_acumuladosint<10) {
					total_horas_acumulada_domingostring=total_horas_acumulada_domingostring+":0"+minutos_acumuladosint;
				}else {
					total_horas_acumulada_domingostring=total_horas_acumulada_domingostring+":"+minutos_acumuladosint;
				}
				out.print("Total Horas Acumuluadas:"+total_horas_acumuladastring +";\n Total Horas Sabado:" +total_horas_acumulada_sabadostring+";\n Total Horas Domingo:" +total_horas_acumulada_domingostring+";");
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("19")){

				param1=req.getParameter("func");
				param2=req.getParameter("mes");
				int aux_num;
				String data_mobile;
				String insere="";
				param3=req.getParameter("inicio");
				param4=req.getParameter("fim");
				SimpleDateFormat format = new SimpleDateFormat(padrao_data_br); 
				Calendar inicio= Calendar.getInstance();
				Calendar fim= Calendar.getInstance();
				Date dt_inicio=format.parse(param3);
				Date dt_fim=format.parse(param4);
				inicio.setTime(dt_inicio);
				fim.setTime(dt_fim);
				//query="select distinct data_dia from registros where usuario='"+param1+"' and mes="+param2+" order by datetime_servlet asc" ;
				//System.out.println(query);
				//rs=conn.Consulta(query);
				String data_mobile_inicial="";
					
				d.setTime(dt_inicio);
				fim.add(Calendar.DAY_OF_MONTH, 1);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy H:m:s");
		       

				while(d.before(fim)) {
						
					
						
						//if(f2.format(d.getTime()).equals(rs.getString("data_dia"))) {
							
							
						
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Folga' order by datetime_servlet asc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							
						}else {
							query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Compensação' order by datetime_servlet asc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								
							}else {
						
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Inicio_intervalo' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							data_mobile_inicial=rs2.getString("datetime_mobile");
							if(rs2.getInt("hora")<12 || rs2.getInt("hora")>13) {
								int auxsec=0;
								aux_num=numeroAleatorio(2,7);
								auxsec=numeroAleatorio(10,59);
								data_mobile=f2.format(d.getTime())+" 13:" +aux_num+ ":" + auxsec;
									d.set(Calendar.HOUR_OF_DAY,13);
									d.set(Calendar.MINUTE,aux_num);
									time = new Timestamp(d.getTimeInMillis());
									data_mobile_inicial=data_mobile;
								insere="update registros set datetime_mobile='"+data_mobile+"',datetime_servlet='"+time+"',hora=13,minutos="+aux_num+",almoco_retorno='PTAJ' where sys_contador="+rs2.getInt("sys_contador");
								conn.Alterar(insere);
							}
							if(rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").length()-2, rs2.getString("datetime_mobile").length()).equals("00")) {
								int auxsec=0;
								aux_num=numeroAleatorio(2,7);
								auxsec=numeroAleatorio(10,59);
								data_mobile=f2.format(d.getTime())+" 13:" +aux_num+ ":" + auxsec;
								d.set(Calendar.HOUR_OF_DAY,13);
								d.set(Calendar.MINUTE,aux_num);
								time = new Timestamp(d.getTimeInMillis());
								data_mobile_inicial=data_mobile;
								insere="update registros set datetime_mobile='"+data_mobile+"',datetime_servlet='"+time+"',hora=13,minutos="+aux_num+",almoco_retorno='PTAJ' where sys_contador="+rs2.getInt("sys_contador");
								conn.Alterar(insere);
							}
						}else {
							query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								int auxsec=0;
								aux_num=numeroAleatorio(2,7);
								auxsec=numeroAleatorio(10,59);
								data_mobile=f2.format(d.getTime())+" 13:" +aux_num+ ":"+auxsec;
								d.set(Calendar.HOUR_OF_DAY,13);
								d.set(Calendar.MINUTE,aux_num);
								time = new Timestamp(d.getTimeInMillis());
								data_mobile_inicial=data_mobile;
								insere="INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes,almoco_retorno) VALUES ('1','1','"+param1+"','0','0','"+f2.format(d.getTime())+"',0,'"+data_mobile+"','"+time+"',13,"+aux_num+",'Inicio_intervalo','Ponto','"+p.getPonto_registro(conn, param1)+"','-',"+(d.get(Calendar.MONTH)+1)+",'PTAJ')";
								conn.Inserir_simples(insere);
							}}
						conn.getConnection().commit();
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Fim_intervalo' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							if(TimeUnit.MILLISECONDS.toMinutes(formatter.parse(rs2.getString("datetime_mobile")).getTime() - formatter.parse(data_mobile_inicial).getTime())>72) {
								Calendar cal = Calendar.getInstance(); // creates calendar
							    cal.setTime(formatter.parse(data_mobile_inicial)); // sets calendar time/date
							    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
							    cal.add(Calendar.MINUTE, 2);
							    cal.add(Calendar.SECOND, 7);
							    time = new Timestamp(cal.getTimeInMillis()); 
							    insere="update registros set datetime_mobile='"+f3.format(cal.getTime())+"',datetime_servlet='"+time+"',hora="+cal.get(Calendar.HOUR)+",minutos="+cal.get(Calendar.MINUTE)+",almoco_retorno='PTAJ' where sys_contador="+rs2.getInt("sys_contador");
								conn.Alterar(insere);
								System.out.println("Usuario "+rs2.getString("usuario")+" com almoco inconsistente inicio: "+data_mobile_inicial +" fim: "+rs2.getString("datetime_mobile")+" - Novo fim: "+f3.format(cal.getTime()));
							}
							if(rs2.getInt("hora")<12 || rs2.getInt("hora")>14) {
								int auxsec=0;
								aux_num=numeroAleatorio(8,15);
								auxsec=numeroAleatorio(10,59);
								data_mobile=f2.format(d.getTime())+" 14:" +aux_num+ ":"+auxsec;
								d.set(Calendar.HOUR_OF_DAY,14);
								d.set(Calendar.MINUTE,aux_num);
								time = new Timestamp(d.getTimeInMillis());
								
								insere="update registros set datetime_mobile='"+data_mobile+"',datetime_servlet='"+time+"',hora=14,minutos="+aux_num+",almoco_retorno='PTAJ' where sys_contador="+rs2.getInt("sys_contador");
								conn.Alterar(insere);
							}
							
							if(rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").length()-2, rs2.getString("datetime_mobile").length()).equals("00")) {
								int auxsec=0;
								aux_num=numeroAleatorio(8,15);
								auxsec=numeroAleatorio(10,59);
								data_mobile=f2.format(d.getTime())+" 14:" +aux_num+ ":" + auxsec;
								d.set(Calendar.HOUR_OF_DAY,13);
								d.set(Calendar.MINUTE,aux_num);
								time = new Timestamp(d.getTimeInMillis());
								
								insere="update registros set datetime_mobile='"+data_mobile+"',datetime_servlet='"+time+"',hora=14,minutos="+aux_num+",almoco_retorno='PTAJ' where sys_contador="+rs2.getInt("sys_contador");
								conn.Alterar(insere);
							}
						}else {
							query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Inicio_intervalo' order by datetime_servlet desc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
							int auxsec=0;
							Calendar cal = Calendar.getInstance(); // creates calendar
						    cal.setTime(formatter.parse(data_mobile_inicial)); // sets calendar time/date
						    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
						    cal.add(Calendar.MINUTE, 1);
						    time = new Timestamp(cal.getTimeInMillis());
							auxsec=numeroAleatorio(10,59);
							aux_num=numeroAleatorio(8,15);
							data_mobile=f2.format(d.getTime())+" 14:" +aux_num+ ":"+auxsec;
							d.set(Calendar.HOUR_OF_DAY,14);
							d.set(Calendar.MINUTE,aux_num);
							time = new Timestamp(d.getTimeInMillis());
							
							insere="INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes,almoco_retorno) VALUES ('1','1','"+param1+"','0','0','"+f2.format(d.getTime())+"',0,'"+f3.format(time)+"','"+time+"',14,"+aux_num+",'Fim_intervalo','Ponto','"+p.getPonto_registro(conn, param1)+"','-',"+(d.get(Calendar.MONTH)+1)+",'PTAJ')";
							conn.Inserir_simples(insere);
							}}
						
						
						}
						}
						
						
						
					
					
					
					
					d.add(Calendar.DAY_OF_MONTH, 1);
					}
					
					
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Ponto Corrigido");
					Timestamp time2 = new Timestamp(System.currentTimeMillis());
					System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("20")){
				System.out.println("Caregando tabela de kpi de ponto");
				double total_user=0;
				double total_user_entrada=0;
				double total_user_ii=0;
				double total_user_fi=0;
				double total_user_saida=0;
				double total_user_folga=0;
				double total_user_comp=0;
				dados_tabela="";
				int contador=0;
				NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
				DecimalFormat twoDForm = (DecimalFormat)nf;
				twoDForm.applyPattern("#0.00");
				d = Calendar.getInstance();
				
				dados_tabela="{\"draw\": 1,\n";
				dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
				dados_tabela=dados_tabela+"\"recordsFiltered\": 7 ,\n";
				dados_tabela=dados_tabela+"\"data\":[";
				query="select * from kpi_diario where mes='"+(d.get(Calendar.MONTH)+1)+"' order by id_sys asc";
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					
					while(rs.next()) {
						contador=contador+1;
						dados_tabela=dados_tabela+"[\""+rs.getString("dia")+"\",\""+rs.getInt("total_user")+"\",\""+rs.getString("Entrada")+"\",\""+rs.getString("Inicio_Intervalo")+"\",\""+rs.getString("Fim_Intervalo")+"\",\""+rs.getString("Saida")+"\"],\n";
					}
				}
				
					contador=contador+1;
					query="select count(id_usuario) from usuarios where validado='Y' and ativo='Y'";
					rs=conn.Consulta(query);
					if(rs.next()) {
						//System.out.println("Total de Usuários:"+rs.getDouble(1));
						total_user=rs.getDouble(1);
					}	
				query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Folga'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					//System.out.println("Total de Usuários em folga:"+rs.getDouble(1));
					total_user_folga=rs.getDouble(1);
					total_user=total_user - total_user_folga;
				}
				query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Compensação'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					//System.out.println("Total de Usuários em compensacao:"+rs.getDouble(1));
					total_user_comp=rs.getDouble(1);
					total_user=total_user-total_user_comp;
				}
				
				//dados_tabela=dados_tabela+"\"recordsTotal\": 1 ,\n";
				//dados_tabela=dados_tabela+"\"recordsFiltered\": 1 ,\n";
				dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
				dados_tabela=dados_tabela+"[\""+f2.format(d.getTime())+"\",\""+total_user+"\",";
				query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Entrada'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					total_user_entrada=rs.getDouble(1);
					
					dados_tabela=dados_tabela+"\""+twoDForm.format(((total_user_entrada/total_user) * 100))+"%\",";
				}
				query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Inicio_intervalo'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					total_user_ii=rs.getDouble(1);
					
					dados_tabela=dados_tabela+"\""+twoDForm.format(((total_user_ii/total_user) * 100))+"%\",";
				}
				query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Fim_intervalo'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					//System.out.println("Total de Usuários com Fim_intervalo:"+rs.getDouble(1));
					total_user_fi=rs.getDouble(1);
					
					dados_tabela=dados_tabela+"\""+twoDForm.format(((total_user_fi/total_user) * 100))+"%\",";
				}
				query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Saída'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					//System.out.println("Total de Usuários com Saida:"+rs.getDouble(1));
					total_user_saida=rs.getDouble(1);
					
					dados_tabela=dados_tabela+"\""+twoDForm.format(((total_user_saida/total_user) * 100))+"%\"]\n";
				}
				
				//dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
				dados_tabela=dados_tabela+"]}";
				//System.out.println(dados_tabela);
				resp.setContentType("application/json");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("21")){
				MessageDigest md;
				String retornaSenha;
				time = new Timestamp(d.getTimeInMillis());
				String senha_simples;
				String nome;
				String usuario;
				String emailstr;
				md = MessageDigest.getInstance( "SHA-256" );
				
				
				if (ServletFileUpload.isMultipartContent(req)) {
					List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
					//System.out.println("Multipart size: " + multiparts.size());
					Iterator<FileItem> iter = multiparts.iterator();
					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
					       // processFormField(item);
					    } else {
					    	
					    	InputStream inputStream2= new ByteArrayInputStream(IOUtils.toByteArray(item.getInputStream()));
				    		DataFormatter dataFormatter = new DataFormatter();
				    		int indexCell=1;
				    		XSSFWorkbook wb = new XSSFWorkbook(inputStream2);
				    		Sheet sheet1 = wb.getSheet("Usuarios");
				    		Cell cell;
				    		String cellValue;
				    		last_id=0;
				    		for (Row row : sheet1) {
				    			if(row.getRowNum()>0) {
				    			query="insert into usuarios (id_usuario,"+
				    					"nome,"+
				    					"matricula,"+
				    					"cpf,"+
				    					"email,"+
				    					"telefone,"+
				    					"admissao,"+
				    					"ctps,"+
				    					"pis,"+
				    					"cargo,"
				    					+ "validado,"
				    					+ "ativo,HASH,data_registro,empresa) values(";
				    			
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 if(cellValue.length()>0) {
				    					 usuario=cellValue;
				    					 senha_simples="1234_"+cellValue;
				    				 md.update( ("1234_"+cellValue).getBytes());     
				    			     BigInteger hash = new BigInteger(1,md.digest() );     
				    			     retornaSenha = hash.toString(16);  
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 nome=cellValue;
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 emailstr=cellValue;
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 indexCell=indexCell+1;
				    				 cell=row.getCell(indexCell);
				    				 cellValue = dataFormatter.formatCellValue(cell);
				    	             //System.out.print(cellValue + "\t");
				    				 cellValue=cellValue.replace("'", "_");
				    				 query=query+"'"+cellValue+"',\n";
				    				 cell=row.getCell(0);
				    				 
				    			
				    				 query=query+"'"+retornaSenha+"','"+time+"','"+p.getEmpresa().getEmpresa_id()+"')";
				    				 indexCell=1;
				    			//System.out.println(query);
				    			if(conn.Inserir_simples(query)) {
				    				last_id=last_id+1;
				    				
				    				email.enviaEmailSimples(emailstr, "MSTP - Notificação de criação de conta","Prezado "+nome+", \n Agradecemos seu registro em nosso sistema. \n Sua conta foi criada e esta ativada ! \n \n \n Acesse: http://www.mstp.com.br\n\n Seu usuário:"+usuario+"\n \n Sua Senha provisoria é:"+senha_simples +"\n \n Acesse o link abaixo em seu aparelho celular para baixar o MSTP Mobile (android): \n http://inovareti.jelasticlw.com.br/mstp_mobile/download/mstp_mobile_last_version.apk \n \n  Acesse nosso canal no youtube e saiba mais sobre o MSTP \n \n https://www.youtube.com/channel/UCxac14HGRuq7wcMgpc4tFXw");
				    	        	
				    			}
				    				 }
				    			}
				    		}
				    		wb.close();
					    }
					}
					resp.setContentType("application/json"); 
		        	JSONObject jsonObject = new JSONObject(); 
		        	jsonObject.put("Sucesso", last_id); 
		        	PrintWriter pw = resp.getWriter();
		        	pw.print(jsonObject); 
		        	pw.close();
		        	System.out.println(p.get_PessoaUsuario()+" Fim de processamento do arquivo com "+last_id+" linhas atualizadas em "+f3.format(time));
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("22")){
				double total_user_mensal=0;
				double total_user_mensal_entrada=0;
				int avg_usuarios=0;
				NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
				DecimalFormat twoDForm = (DecimalFormat)nf;
				twoDForm.applyPattern("#0.00");
				d=Calendar.getInstance();
				dados_tabela="";
				query="select sum(total_user) from kpi_diario where mes='"+(d.get(Calendar.MONTH)+1)+"' and fim_d_semana='N'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					total_user_mensal=rs.getInt(1);
					//System.out.println("Total user mensal:"+total_user_mensal);
				}
				query="select avg(total_user) from kpi_diario where mes='"+(d.get(Calendar.MONTH)+1)+"' and fim_d_semana='N'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					avg_usuarios=rs.getInt(1);
					//System.out.println("Total user mensal:"+total_user_mensal);
				}
				query="select count(distinct usuario,data_dia) from registros where mes="+(d.get(Calendar.MONTH)+1)+" and tipo_registro='Entrada'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					total_user_mensal_entrada=rs.getInt(1);
					//System.out.println("Total user mensal Entrada:"+total_user_mensal_entrada);
				}
				dados_tabela=dados_tabela+twoDForm.format(((total_user_mensal_entrada/total_user_mensal) * 100))+"%;";
				query="select count(distinct usuario,data_dia) from registros where mes="+(d.get(Calendar.MONTH)+1)+" and tipo_registro='Inicio_intervalo'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					total_user_mensal_entrada=rs.getInt(1);
					//System.out.println("Total user mensal Entrada:"+total_user_mensal_entrada);
				}
				dados_tabela=dados_tabela+twoDForm.format(((total_user_mensal_entrada/total_user_mensal) * 100))+"%;";
				query="select count(distinct usuario,data_dia) from registros where mes="+(d.get(Calendar.MONTH)+1)+" and tipo_registro='Fim_intervalo'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					total_user_mensal_entrada=rs.getInt(1);
					//System.out.println("Total user mensal Entrada:"+total_user_mensal_entrada);
				}
				dados_tabela=dados_tabela+twoDForm.format(((total_user_mensal_entrada/total_user_mensal) * 100))+"%;";
				query="select count(distinct usuario,data_dia) from registros where mes="+(d.get(Calendar.MONTH)+1)+" and tipo_registro='Saída'";
				rs=conn.Consulta(query);
				if(rs.next()) {
					total_user_mensal_entrada=rs.getInt(1);
					//System.out.println("Total user mensal Entrada:"+total_user_mensal_entrada);
				}
				dados_tabela=dados_tabela+twoDForm.format(((total_user_mensal_entrada/total_user_mensal) * 100))+"%;"+avg_usuarios;
				//System.out.println(dados_tabela);
				resp.setContentType("application/text");  
				resp.setCharacterEncoding("UTF-8"); 
				PrintWriter out = resp.getWriter();
				out.print(dados_tabela);
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("23")){
				
				System.out.println("Carregando Faltas");
				String encontrou="n";
				param1=req.getParameter("func");
				param2=req.getParameter("mes");
				ResultSet rs3;
				
				int contador=0;
				if(param1.equals("")) {
					query="select distinct id_usuario,nome from usuarios where id_usuario<>'masteradmin' and validado='Y' and ativo='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"'"; ;
					rs=conn.Consulta(query);
					if(rs.next()) {
						rs.beforeFirst();
						dados_tabela="{\"draw\": 1,\n";
						dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
						dados_tabela=dados_tabela+"\"recordsFiltered\": 15 ,\n";
						dados_tabela=dados_tabela+"\"data\":[\n";
						d.set(Calendar.MONTH, (Integer.parseInt(param2)-1));
						d.set(Calendar.DAY_OF_MONTH, 1);
						while(rs.next()) {
						while(param2.equals(Integer.toString(d.get(Calendar.MONTH)+1))) {
							
							query="SELECT * FROM registros where usuario='"+rs.getString("id_usuario")+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro in('Folga','Compensação','Licença Médica') order by datetime_servlet asc";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								
							}else {
								if(d.get(Calendar.DAY_OF_WEEK)==1 || d.get(Calendar.DAY_OF_WEEK)==7) {
									
								}else {
									query="SELECT * FROM registros where usuario='"+rs.getString("id_usuario")+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro in('Entrada','Saída') order by datetime_servlet asc";
									rs3=conn.Consulta(query);
									if(rs3.next()) {
										
									}else {
										contador=contador+1;
										dados_tabela=dados_tabela + "[";
										dados_tabela=dados_tabela + "\""+f2.format(d.getTime())+"\",";
										dados_tabela=dados_tabela + "\""+rs.getString("id_usuario")+"\",";
										dados_tabela=dados_tabela + "\""+rs.getString("nome")+"\",";
										dados_tabela=dados_tabela + "\"Falta\"],"+"\n";
										encontrou="s";
									}
								}
							}
						
						//rs.beforeFirst();
						d.add(Calendar.DAY_OF_MONTH, 1);
						}
						d.set(Calendar.MONTH, (Integer.parseInt(param2)-1));
						d.set(Calendar.DAY_OF_MONTH, 1);
						}
						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
						dados_tabela=dados_tabela+"]}";
						dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
						//dados_tabela=dados_tabela.replace("replace2", Integer.toString(contador));
						
						//System.out.println(dados_tabela);
						resp.setContentType("application/json");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
					}else {
						dados_tabela="{\"draw\": 1,\n";
						dados_tabela=dados_tabela+"\"recordsTotal\": 0 ,\n";
						dados_tabela=dados_tabela+"\"recordsFiltered\": 0 ,\n";
						dados_tabela=dados_tabela+"\"data\":[]\n";
						dados_tabela=dados_tabela+"}";
						resp.setContentType("application/json");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print(dados_tabela);
					}
				}else {
					query="select distinct id_usuario,nome from usuarios where id_usuario='"+param1+"'" ;
				
				
				
				rs=conn.Consulta(query);
				if(rs.next()) {
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 7 ,\n";
					dados_tabela=dados_tabela+"\"data\":[\n";
					d.set(Calendar.MONTH, (Integer.parseInt(param2)-1));
					d.set(Calendar.DAY_OF_MONTH, 1);
					encontrou="n";
					while(param2.equals(Integer.toString(d.get(Calendar.MONTH)+1))) {
						
						query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro in('Folga','Compensação','Licença Médica') order by datetime_servlet asc";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							
						}else {
							if(d.get(Calendar.DAY_OF_WEEK)==1 || d.get(Calendar.DAY_OF_WEEK)==7) {
								
							}else {
								query="SELECT * FROM registros where usuario='"+param1+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro in('Entrada','Saída') order by datetime_servlet asc";
								rs3=conn.Consulta(query);
								if(rs3.next()) {
									
								}else {
									contador=contador+1;
									dados_tabela=dados_tabela + "[";
									dados_tabela=dados_tabela + "\""+f2.format(d.getTime())+"\",";
									dados_tabela=dados_tabela + "\""+rs.getString("id_usuario")+"\",";
									dados_tabela=dados_tabela + "\""+rs.getString("nome")+"\",";
									dados_tabela=dados_tabela + "\"Falta\"],"+"\n";
									encontrou="s";
								}
							}
						}
					
					//rs.beforeFirst();
					d.add(Calendar.DAY_OF_MONTH, 1);
					}
					if(encontrou.equals("s")) {
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]}";
					dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
					}else {
						dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
						dados_tabela=dados_tabela+"[]}";
						dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
					}
					//dados_tabela=dados_tabela.replace("replace2", Integer.toString(contador));
					
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else {
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": 0 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 0 ,\n";
					dados_tabela=dados_tabela+"\"data\":[]\n";
					dados_tabela=dados_tabela+"}";
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("24")){
				
				System.out.println("Reset de Senha Iniciado por - "+p.get_PessoaUsuario());
				
				String retornaSenha="";
				MessageDigest md;
				String [] usuarios;
				md = MessageDigest.getInstance( "SHA-256" );
				param1=req.getParameter("usuarios");
				param2=req.getParameter("senha");
				md.update( (param2).getBytes());     
			    BigInteger hash = new BigInteger(1,md.digest() );     
			    retornaSenha = hash.toString(16); 
				//System.out.println(param1);
				//System.out.println(param2);
				if(param1.indexOf(",")>0) {
					usuarios=param1.split(",");
					for(int u=0;u<usuarios.length;u++) {
						query="update usuarios set HASH='"+retornaSenha+"' where id_usuario='"+usuarios[u]+"' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
						if(conn.Alterar(query)) {
							resp.setContentType("application/html");  
							resp.setCharacterEncoding("UTF-8"); 
							PrintWriter out = resp.getWriter();
							out.print("Senhas Redefinidas");
							rs=conn.Consulta("select email,nome from usuarios where id_usuario='"+usuarios[u]+"' and empresa='"+p.getEmpresa().getEmpresa_id()+"'");
							if(rs.next()) {
								email.enviaEmailSimples(rs.getString("email"), "MSTP - Notificação de Alteração de Senha","Prezado "+rs.getString("nome")+", \n Informamos que sua senha foi alterada em "+time+" por "+p.get_PessoaName()+". \n \n \n Caso deseje voce pode alterar sua senha em www.mstp.com.br! \n \n \n Acesse: http://www.mstp.com.br\n\n Seu usuário: "+usuarios[u]+"\n \n Sua Senha é: "+param2 +"\n \n Acesse o link abaixo em seu aparelho celular para baixar o MSTP Mobile (android): \n http://inovareti.jelasticlw.com.br/mstp_mobile/download/mstp_mobile_last_version.apk \n \n  Acesse nosso canal no youtube e saiba mais sobre o MSTP \n \n https://www.youtube.com/channel/UCxac14HGRuq7wcMgpc4tFXw");
								email.enviaEmailSimples(p.getEmail(), "MSTP - Notificação de Alteração de Senha - Cópia","Prezado "+rs.getString("nome")+", \n Informamos que sua senha foi alterada em "+time+" por "+p.get_PessoaName()+". \n \n \n Caso deseje voce pode alterar sua senha em www.mstp.com.br! \n \n \n Acesse: http://www.mstp.com.br\n\n Seu usuário: "+usuarios[u]+"\n \n Sua Senha é: "+param2 +"\n \n Acesse o link abaixo em seu aparelho celular para baixar o MSTP Mobile (android): \n http://inovareti.jelasticlw.com.br/mstp_mobile/download/mstp_mobile_last_version.apk \n \n  Acesse nosso canal no youtube e saiba mais sobre o MSTP \n \n https://www.youtube.com/channel/UCxac14HGRuq7wcMgpc4tFXw");
							}
							}
					}
					
				}else {
					query="update usuarios set HASH='"+retornaSenha+"' where id_usuario='"+param1+"' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
					if(conn.Alterar(query)) {
						resp.setContentType("application/html");  
						resp.setCharacterEncoding("UTF-8"); 
						PrintWriter out = resp.getWriter();
						out.print("Senha Redefinida");
						rs=conn.Consulta("select email,nome from usuarios where id_usuario='"+param1+"' and empresa='"+p.getEmpresa().getEmpresa_id()+"'");
						if(rs.next()) {
							email.enviaEmailSimples(rs.getString("email"), "MSTP - Notificação de Alteração de Senha","Prezado "+rs.getString("nome")+", \n Informamos que sua senha foi alterada em "+time+" por "+p.get_PessoaName()+". \n \n \n Caso deseje voce pode alterar sua senha em www.mstp.com.br! \n \n \n Acesse: http://www.mstp.com.br\n\n Seu usuário: "+param1+"\n \n Sua Senha é: "+param2 +"\n \n Acesse o link abaixo em seu aparelho celular para baixar o MSTP Mobile (android): \n http://inovareti.jelasticlw.com.br/mstp_mobile/download/mstp_mobile_last_version.apk \n \n  Acesse nosso canal no youtube e saiba mais sobre o MSTP \n \n https://www.youtube.com/channel/UCxac14HGRuq7wcMgpc4tFXw");
							email.enviaEmailSimples(p.getEmail(), "MSTP - Notificação de Alteração de Senha - Cópia","Prezado "+rs.getString("nome")+", \n Informamos que sua senha foi alterada em "+time+" por "+p.get_PessoaName()+". \n \n \n Caso deseje voce pode alterar sua senha em www.mstp.com.br! \n \n \n Acesse: http://www.mstp.com.br\n\n Seu usuário: "+param1+"\n \n Sua Senha é: "+param2 +"\n \n Acesse o link abaixo em seu aparelho celular para baixar o MSTP Mobile (android): \n http://inovareti.jelasticlw.com.br/mstp_mobile/download/mstp_mobile_last_version.apk \n \n  Acesse nosso canal no youtube e saiba mais sobre o MSTP \n \n https://www.youtube.com/channel/UCxac14HGRuq7wcMgpc4tFXw");
						}
					}
					
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("25")){
				System.out.println("Carregando Feriados");
				query="select * from feriados where empresa="+p.getEmpresa().getEmpresa_id();
				//System.out.println(query);
				dados_tabela="";
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="[";
					while(rs.next()) {
						dados_tabela=dados_tabela+"{\"id\":"+rs.getInt("id_feriado")+",\"status\":\""+rs.getString("status")+"\",\"name\":\""+rs.getString("nome")+"\",\"start\":\""+rs.getString("dt_inicio")+"\",\"end\":\""+rs.getString("dt_fim")+"\"},\n";
					}
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]";
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("26")) {

    			//System.out.println("chegou aqui!");
				String senhaprovisoria="";
    			param1=req.getParameter("nome");
    			//System.out.println("valor param1:"+param1);
    			param2=req.getParameter("usuario");
    			param3=req.getParameter("email");
    			param4=req.getParameter("matricula");
    			param5=req.getParameter("perfil");
    			//System.out.println(param5);
    			MessageDigest md;
    			 time = new Timestamp(System.currentTimeMillis());
    			md = MessageDigest.getInstance( "SHA-256" );
    			senhaprovisoria=param2+"1234";
				md.update( senhaprovisoria.getBytes());     
		        BigInteger hash = new BigInteger(1,md.digest() );     
		        String retornaSenha = hash.toString(16);   
		        //System.out.println("senha registro: "+senhaprovisoria +" senha código: "+retornaSenha + " fim da senha.");
		        md.update( (param2.concat(param2).getBytes())); 
		        hash = new BigInteger(1,md.digest() );  
		        String cod_valida=hash.toString(16); 
		        
    			String insere = "";
    			insere="INSERT INTO usuarios (id_usuario,nome,validado,ativo,email,hash,CODIGO_VALIDACAO,perfil,empresa) VALUES ('"+param2+"','"+param1+"','N','Y','"+param3+"','"+retornaSenha+"','"+cod_valida+"','"+param5+"','"+p.getEmpresa().getEmpresa_id()+"')";
    			if(conn.Inserir_simples(insere)){
		    		//////////System.out.println("Resposta Consulta 10 Enviada!");
    				email= new Semail();
    				email.enviaEmailSimples(param3, "MSTP - Link de Ativação","Prezado "+param1+", \n Agradecemos seu registro em nosso sistema. \n Para ativar sua conta basta clicar no link abaixo:\n \n \n https://www.mstp.com.br/Reg_Servlet?validaCode="+cod_valida +"\n\n Seu usuário:"+param2+"\n \n Sua Senha provisoria é:"+senhaprovisoria +"\n \n Acesse o link abaixo em seu aparelho celular para baixar o MSTP Mobile (android): \n http://node21664-inovareti.jelastic.saveincloud.net/mstp_mobile/download/mstp_mobile_last_version.apk ou baixe na Google Play - https://play.google.com/store/apps/details?id=com.inovareti.mstp_mobile&hl=pt");
    	        	
    				///***
		    		rs=conn.Consulta("Select id_usuario_sys from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' order by id_usuario_sys desc limit 1");
		    		if(rs.next()){
		    			last_id=rs.getInt(1);
		    		}
		    		resp.setContentType("application/html");  
		    		resp.setCharacterEncoding("UTF-8"); 
		    		PrintWriter out = resp.getWriter();
		    		out.print(last_id);
    			}else{
	    			System.out.println("Consulta returns empty");
    			}
    			Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("27")){
				param1=req.getParameter("id");
				param2=req.getParameter("nome");
				param3=req.getParameter("inicio");
				param4=req.getParameter("fim");
				query="";
				query="insert into feriados (id_jqx,nome,dt_inicio,dt_fim,empresa) values('"+param1+"','"+param2+"','"+param3+"','"+param4+"',"+p.getEmpresa().getEmpresa_id()+")";
				if(conn.Inserir_simples(query)) {
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Feriado Registrado com Sucesso!");
				}else {
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Erro no Registro do Feriado!");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("28")){
				param1=req.getParameter("id");
				query="delete from feriados where id_feriado="+param1+" and empresa="+p.getEmpresa().getEmpresa_id();
				if(conn.Excluir(query)) {
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Feriado Removido com Sucesso!");
				}else {
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Erro na Exclusão do Feriado!");
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("29")){
				param1=req.getParameter("id");
				param2=req.getParameter("nome");
				query="update feriados set nome='"+param2+"' where id_feriado="+param1+" and empresa="+p.getEmpresa().getEmpresa_id();
				//System.out.println(query);
				conn.Alterar(query);
					/*resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Feriado Atualizado com Sucesso!");*/
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("30")) {

				param1=req.getParameter("func");
				
				param3=req.getParameter("inicio");
				param4=req.getParameter("fim");
				SimpleDateFormat format = new SimpleDateFormat(padrao_data_br); 
				Calendar inicio= Calendar.getInstance();
				Calendar fim= Calendar.getInstance();
				Date dt_inicio=format.parse(param3);
				Date dt_fim=format.parse(param4);
				inicio.setTime(dt_inicio);
				fim.setTime(dt_fim);
				System.out.println("inicio:"+f2.format(inicio.getTime())+" fim: "+f2.format(fim.getTime()));
				String local;
				Integer distancia=0;
				String encontrado="n";
				String tipo_registro="Normal";
				String tipo_ajustar=" - ";
				String entrada="00:00:00";
				String saida="00:00:00";
				String [] HH = new String[4];
				int contador=0;
				local="Sem Localização";
				HH[0] = "Caculo Indisponivel";
				HH[1]="0.0";
				HH[2]="0.0";
				HH[3]="0.0";
				String aux_usuario;
				String query2="";
				String[] aux_usuario_dados=new String[2];
				if(param1.equals("TODOS")) {
					//select distinct registros.data_dia,t_aux.id_usuario from (select distinct id_usuario,empresa from usuarios where empresa='5' and ATIVO='Y') as t_aux left join registros on t_aux.empresa='5' and registros.usuario=t_aux.id_usuario and str_to_date(registros.datetime_servlet,'%Y-%m-%d') >= str_to_date('18/04/2019','%d/%m/%Y') and str_to_date(registros.datetime_servlet,'%Y-%m-%d') <= str_to_date('18/04/2019','%d/%m/%Y') order by datetime_servlet,usuario asc
					query="select distinct registros.data_dia,t_aux.id_usuario from (select distinct id_usuario,empresa from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' and ATIVO='Y' and exibe_ponto_analise='Y') as t_aux left join registros on t_aux.empresa='"+p.getEmpresa().getEmpresa_id()+"' and registros.usuario=t_aux.id_usuario and str_to_date(registros.datetime_servlet,'%Y-%m-%d') >= str_to_date('"+f2.format(inicio.getTime())+"','%d/%m/%Y') and str_to_date(registros.datetime_servlet,'%Y-%m-%d') <= str_to_date('"+f2.format(fim.getTime())+"','%d/%m/%Y') order by datetime_servlet,usuario asc" ;
					//query="select distinct data_dia,usuario from registros where usuario in (select distinct id_usuario from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' and ATIVO='Y') and str_to_date(datetime_servlet,'%Y-%m-%d') >= str_to_date('"+f2.format(inicio.getTime())+"','%d/%m/%Y') and str_to_date(datetime_servlet,'%Y-%m-%d') <= str_to_date('"+f2.format(fim.getTime())+"','%d/%m/%Y') order by datetime_servlet asc" ;
					
					query2="select distinct id_usuario from usuarios where empresa='"+p.getEmpresa().getEmpresa_id()+"' and ATIVO='Y' and exibe_ponto_analise='Y' and id_usuario not in (select distinct usuario from registros where str_to_date(datetime_servlet,'%Y-%m-%d') >= str_to_date('"+f2.format(inicio.getTime())+"','%d/%m/%Y') and str_to_date(datetime_servlet,'%Y-%m-%d') <= str_to_date('"+f2.format(fim.getTime())+"','%d/%m/%Y') order by datetime_servlet asc)";
					//rs3=conn.Consulta("select id_usuario,nome,matricula from usuarios where validado='Y and ativo='Y' and empresa='"+p.getEmpresa().getEmpresa_id()+"'");
				}else {
					aux_usuario=param1;
					query="select distinct data_dia,usuario from registros where usuario='"+param1+"' and str_to_date(datetime_servlet,'%Y-%m-%d') >= str_to_date('"+f2.format(inicio.getTime())+"','%d/%m/%Y') and str_to_date(datetime_servlet,'%Y-%m-%d') <= str_to_date('"+f2.format(fim.getTime())+"','%d/%m/%Y') order by datetime_servlet asc" ;
					aux_usuario_dados=p.buscarPessoa(conn, aux_usuario, p.getEmpresa().getEmpresa_id());
				}
				
				
				
				rs=conn.Consulta(query);
				if(rs.next()) {
					rs.beforeFirst();
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": replace1 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 15 ,\n";
					dados_tabela=dados_tabela+"\"data\":[\n";
					//d.set(Calendar.MONTH, (Integer.parseInt(param2)-1));
					//d.set(Calendar.DAY_OF_MONTH, 1);
					d.setTime(dt_inicio);
					fim.add(Calendar.DAY_OF_MONTH, 1);
					while(d.before(fim)) {
						//System.out.println("entrou no loop");
						//System.out.println("data de pesquisa:"+f2.format(d.getTime()));
						contador=contador+1;
						aux_usuario="";
					while(rs.next()) {
						HH[0] = "Caculo Indisponivel";
						HH[1]="0.0";
						HH[2]="0.0";
						HH[3]="0.0";
						if(aux_usuario.equals("")) {
							aux_usuario=rs.getString(2);
							aux_usuario_dados=p.buscarPessoa(conn, aux_usuario, p.getEmpresa().getEmpresa_id());
						}else if(aux_usuario.equals(rs.getString(2))) {
							
						}else {
							aux_usuario=rs.getString(2);
							aux_usuario_dados=p.buscarPessoa(conn, aux_usuario, p.getEmpresa().getEmpresa_id());
						}
						if(rs.getString("data_dia")==null) {
							if(d.get(Calendar.DAY_OF_WEEK)==1) {
								tipo_registro="Domingo";
								tipo_ajustar=" - ";
							}else if(d.get(Calendar.DAY_OF_WEEK)==7) {
								tipo_registro="Sábado";
								tipo_ajustar=" - ";
							}else if(feriado.verifica_feriado(f2.format(d.getTime()),p.getEstadoUsuario(aux_usuario,conn), conn, p.getEmpresa().getEmpresa_id())) {
								tipo_registro="Feriado";
								tipo_ajustar=" - ";
							}else {
								tipo_registro="Sem Marcação";
								tipo_ajustar=" - ";
							}
							if(rs.getString(2)!=null) {
							aux_usuario=rs.getString(2);
							aux_usuario_dados=p.buscarPessoa(conn, aux_usuario, p.getEmpresa().getEmpresa_id());
							dados_tabela=dados_tabela+"[\""+f2.format(d.getTime())+"\",\""+aux_usuario_dados[0]+"\",\""+aux_usuario_dados[1]+"\",\"\",\"\",\"\",\"\",\"\",\""+tipo_registro+"\",\""+tipo_ajustar+"\",\"-\"],\n";
							}
						}else if(f2.format(d.getTime()).equals(rs.getString("data_dia"))) {
							if(d.get(Calendar.DAY_OF_WEEK)==1) {
								tipo_registro="Domingo";
								tipo_ajustar=" - ";
							}else if(d.get(Calendar.DAY_OF_WEEK)==7) {
								tipo_registro="Sábado";
								tipo_ajustar=" - ";
							}else if(feriado.verifica_feriado(f2.format(d.getTime()),p.getEstadoUsuario(aux_usuario, conn),conn, p.getEmpresa().getEmpresa_id())) {
								tipo_registro="Feriado";
								tipo_ajustar=" - ";
							}else {
								tipo_registro="Normal";
								tipo_ajustar=" - ";
							}
						
						dados_tabela=dados_tabela + "["+"\n";
						dados_tabela=dados_tabela + "\""+rs.getString("data_dia")+"\",\""+aux_usuario_dados[0]+"\",\""+aux_usuario_dados[1]+"\","+"\n";
						query="SELECT * FROM registros where usuario='"+aux_usuario+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Folga' order by datetime_servlet asc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\"-:-\",\"-:-\",\"-:-\",\"-:-\","+"\n";
							tipo_registro="Folga";
						}else {
							query="SELECT * FROM registros where usuario='"+aux_usuario+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Compensação' order by datetime_servlet asc limit 1";
							rs2=conn.Consulta(query);
							if(rs2.next()) {
								dados_tabela=dados_tabela + "\"-:-\",\"-:-\",\"-:-\",\"-:-\","+"\n";
								tipo_registro="Compensação";
							}else {
								query="SELECT * FROM registros where usuario='"+aux_usuario+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Licença Médica' order by datetime_servlet asc limit 1";
								rs2=conn.Consulta(query);
								if(rs2.next()) {
									dados_tabela=dados_tabela + "\"-:-\",\"-:-\",\"-:-\",\"-:-\","+"\n";
									tipo_registro="Licença Médica";
								}else {	
						query="SELECT * FROM registros where usuario='"+aux_usuario+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							distancia=rs2.getInt("distancia");
							entrada=rs2.getString("datetime_servlet");
							local=rs2.getString("tipo_local_registro");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
							distancia=0;
							entrada="";
						}
						query="SELECT * FROM registros where usuario='"+aux_usuario+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Inicio_intervalo' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							local=rs2.getString("tipo_local_registro");
							distancia=rs2.getInt("distancia");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
						}
						query="SELECT * FROM registros where usuario='"+aux_usuario+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Fim_intervalo' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							local=rs2.getString("tipo_local_registro");
							distancia=rs2.getInt("distancia");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
						}
						query="SELECT * FROM registros where usuario='"+aux_usuario+"' and data_dia='"+rs.getString("data_dia")+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
						rs2=conn.Consulta(query);
						if(rs2.next()) {
							dados_tabela=dados_tabela + "\""+rs2.getString("datetime_mobile").substring(rs2.getString("datetime_mobile").indexOf("/",3)+5)+"\","+"\n";
							saida=rs2.getString("datetime_servlet");
							distancia=rs2.getInt("distancia");
							local=rs2.getString("tipo_local_registro");
							if(local.equals("Site")) {
								local=rs2.getString("site_operadora_registro")+"-"+rs2.getString("local_registro");
							}
						}else {
							dados_tabela=dados_tabela + "\"\","+"\n";
							tipo_registro="Anormal";
							saida="";
						}
						if(!entrada.equals("") && !saida.equals("")) {
							//System.out.println(entrada);
							//System.out.println(rs.getString("data_dia"));
							HH=calcula_hh(entrada,saida,feriado,conn,p,aux_usuario);
						}else if(!entrada.equals("") && saida.equals("")) {
							HH=calcula_hh(entrada,entrada.substring(0, 10)+" 18:30:00",feriado,conn,p,aux_usuario);
						}
							}}
						}
						dados_tabela=dados_tabela + "\""+local+"\","+"\n";
						dados_tabela=dados_tabela + "\""+tipo_registro+"\","+"\n";
						dados_tabela=dados_tabela + "\""+distancia+"\""+"\n";
						
						
						dados_tabela=dados_tabela + ",\""+HH[0]+":"+HH[1]+"\""+"\n";
						dados_tabela=dados_tabela + "],"+"\n";
						encontrado="s";
					}
					}
					
					if(encontrado.equals("n")) {
						if(d.get(Calendar.DAY_OF_WEEK)==1) {
							tipo_registro="Domingo";
							tipo_ajustar=" - ";
							}else if(d.get(Calendar.DAY_OF_WEEK)==7) {
								tipo_registro="Sábado";
								tipo_ajustar=" - ";
							}else if(feriado.verifica_feriado(f2.format(d.getTime()),p.getEstadoUsuario(aux_usuario, conn), conn, p.getEmpresa().getEmpresa_id())) {
								tipo_registro="Feriado";
								tipo_ajustar=" - ";
							}else {
								tipo_registro="Falta";
								if(p.get_PessoaUsuario().equals(param1)) {
								tipo_ajustar="<button class='btn btn-link' style='height:25px' data-toggle='modal' data-target='#modal_ajuste_ponto' data-dia='"+f2.format(d.getTime())+"'>Ajustar</button>";
								}else {
								tipo_ajustar=" - ";
								}
							}
						dados_tabela=dados_tabela+"[\""+f2.format(d.getTime())+"\",\"\",\"\",\"\",\"\",\"\",\""+tipo_registro+"\",\""+tipo_ajustar+"\",\"-\"],\n";
						
					}
					encontrado="n";
					rs.beforeFirst();
					d.add(Calendar.DAY_OF_MONTH, 1);
					}
					
					dados_tabela=dados_tabela.substring(0,dados_tabela.length()-2);
					dados_tabela=dados_tabela+"]}";
					dados_tabela=dados_tabela.replace("replace1", Integer.toString(contador));
					//dados_tabela=dados_tabela.replace("replace2", Integer.toString(contador));
					
					//System.out.println(dados_tabela);
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}else {
					dados_tabela="{\"draw\": 1,\n";
					dados_tabela=dados_tabela+"\"recordsTotal\": 0 ,\n";
					dados_tabela=dados_tabela+"\"recordsFiltered\": 0 ,\n";
					dados_tabela=dados_tabela+"\"data\":[]\n";
					dados_tabela=dados_tabela+"}";
					resp.setContentType("application/json");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print(dados_tabela);
				}
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("31")) {
				param1 = req.getParameter("inicio");
				param2 = req.getParameter("fim");
				param3 = req.getParameter("duracao");
				param4 = req.getParameter("usuario");
				param5 = req.getParameter("ano_base");
				query="insert into ferias (nome_func,inicio,fim,duracao,ano_ref,dt_add,user_add,processada,empresa) values('"+param4+"','"+param1+"','"+param2+"',"+param3+","+param5+",'"+time+"','"+p.get_PessoaUsuario()+"','Y',"+p.getEmpresa().getEmpresa_id()+")";
				if(conn.Inserir_simples(query)) {
					SimpleDateFormat format = new SimpleDateFormat(padrao_data_br); 
					Calendar inicio= Calendar.getInstance();
					Calendar fim= Calendar.getInstance();
					Date dt_inicio=format.parse(param1);
					Date dt_fim=format.parse(param2);
					inicio.setTime(dt_inicio);
					fim.setTime(dt_fim);
					fim.add(Calendar.DAY_OF_MONTH, 1);
					while(inicio.before(fim)) {
						insere_regitro(p,param4,"Férias",conn,"0","0",f2.format(inicio.getTime())+" 00:00:00","0","","Férias, - , - ");
						inicio.add(Calendar.DAY_OF_MONTH, 1);
					}
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Férias Processadas com sucesso!");
				}else {
					resp.setContentType("application/text");  
					resp.setCharacterEncoding("UTF-8"); 
					PrintWriter out = resp.getWriter();
					out.print("Erro no processamento das Férias!");
				}
				
				
				Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("32")) {
				System.out.println("Buscando fotos de usuários");
				param1=req.getParameter("usuario");
				query="select foto_perfil from usuarios where id_usuario='"+param1+"'";
				//System.out.println(query);
				rs=conn.Consulta(query);
				ServletOutputStream out = resp.getOutputStream();
				if(rs.next()){
					//System.out.println("Entrou no Blob");
					Blob imageBlob = rs.getBlob(1);
					InputStream in = imageBlob.getBinaryStream(1,(int)imageBlob.length());
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
		}else if(opt.equals("33")) {
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Iniciando cadastro de nova justificativas." );
			param1=req.getParameter("justificativa");
			param2=req.getParameter("desc");
			param3=req.getParameter("foto");
			ConexaoMongo c = new ConexaoMongo();
			Document justificativa = new Document();
			justificativa.append("Empresa", p.getEmpresa().getEmpresa_id());
			justificativa.append("Justificativa", param1);
			justificativa.append("Descrição", param2);
			justificativa.append("Foto_requerida", param3);
			justificativa.append("update_by", p.get_PessoaUsuario());
			justificativa.append("update_time", time);
			c.InserirSimpels("Justificativas", justificativa);
			
			resp.setContentType("application/text");  
			resp.setCharacterEncoding("UTF-8"); 
			PrintWriter out = resp.getWriter();
			out.print("Cadastro executado com sucesso.");
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("34")) {
			param1=req.getParameter("ajuste");
			ServletOutputStream out = resp.getOutputStream();
			query="";
			Blob image = null;
			
			query="select foto_justificativa from ajuste_ponto where id_ajuste_ponto="+param1+" and empresa="+p.getEmpresa().getEmpresa_id();
			rs=conn.Consulta(query);
			if(rs.next()) {
				System.out.println(" foto encontrada");
				image=rs.getBlob(1);
				
				byte byteArray[]=image.getBytes(1, (int) image.length());
				resp.setContentType("image/png");
				out.write(byteArray);
				out.flush();
				out.close();
				System.out.println(" foto carregada");
				
			}
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("35")) {
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" carregando tabela de justificativas" );
			Document justificativa=new Document();
			List<Bson> filtro_list = new ArrayList<Bson>();
			ConexaoMongo c = new ConexaoMongo();
			Bson filtrodoc;
			dados_tabela="";
			filtrodoc=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtro_list.add(filtrodoc);
			FindIterable<Document> findIterable= c.ConsultaCollectioncomFiltrosLista("Justificativas", filtro_list);
			MongoCursor<Document> resultado = findIterable.iterator();
			dados_tabela="[";
			if(resultado.hasNext()) {
			while(resultado.hasNext()) {
				justificativa=resultado.next();
				dados_tabela=dados_tabela+"{\"id\":\""+justificativa.get("_id")+"\",";
				dados_tabela=dados_tabela+"\"justificativa\":\""+justificativa.get("Justificativa")+"\",";
				dados_tabela=dados_tabela+"\"descricao\":\""+justificativa.get("Descrição")+"\",";
				dados_tabela=dados_tabela+"\"foto\":\""+justificativa.get("Foto_requerida")+"\",";
				dados_tabela=dados_tabela+"\"owner\":\""+justificativa.get("update_by")+"\",";
				dados_tabela=dados_tabela+"\"dtadd\":\""+f3.format(justificativa.getDate("update_time"))+"\"},";
			}
			
			dados_tabela=dados_tabela.substring(0,dados_tabela.length()-1);
			}
			dados_tabela=dados_tabela+"]";
			//System.out.println(dados_tabela);
			resp.setContentType("application/json");  
  		    resp.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = resp.getWriter();
		    out.print(dados_tabela);
		    c.fecharConexao();
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("36")) {
			param1=req.getParameter("filtros");
			List<Bson> filtro_list = new ArrayList<Bson>();
			Bson filtrodoc;
			JSONObject jObj = new JSONObject(param1);
			JSONArray filtros = jObj.getJSONArray("filtros");
			ConexaoMongo c = new ConexaoMongo();
			filtrodoc=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtro_list.add(filtrodoc);
        	for (Integer i=0; i < filtros.length(); i++)
			{
        		filtrodoc=Filters.eq("_id",new ObjectId(filtros.get(i).toString()));
    			filtro_list.add(filtrodoc);
        		c.RemoverFiltroList("Justificativas", filtro_list);
        		filtro_list.remove(filtro_list.size()-1);
			}
        	resp.setContentType("application/html");  
  		    resp.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = resp.getWriter();
		    out.print("Justificativas Removidas com sucesso!");
		    c.fecharConexao();
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Usuários opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}
		}catch (SQLException e) {
			
			conn.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insere_regitro(Pessoa p,String usuario,String tipo_registro, Conexao conn,String lat,String lng,String timestam,String distancia,String datetime,String Localidade) {
		String query,query2,insere;
		Locale brasil = new Locale("pt", "BR");
		String param1,param2,param3,param4,param5;
		String array_string_aux[];
		Calendar d = Calendar.getInstance();
		Date data = d.getTime();
		SimpleDateFormat format = new SimpleDateFormat(padrao_data_hora_br);
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, brasil);
		DateFormat f3 = DateFormat.getDateTimeInstance();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		java.sql.Date date_sql = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		ResultSet rs ;
		param1=lat;
		param2=lng;
		param3=timestam;
		param4=distancia;
		param5=datetime;
		Document registro=new Document();
		Document geo = new Document();
		Document geometry = new Document();
		Document properties = new Document();
		
		ConexaoMongo cm = new ConexaoMongo();
		array_string_aux=Localidade.split(",");
		
		try {
		
		
		
		data = format.parse(timestam);
		d.setTime(data);
		int aux_hora=d.get(Calendar.HOUR_OF_DAY);
		int aux_min=d.get(Calendar.MINUTE);
		date_sql = new java.sql.Date(d.getTime().getTime());
		//System.out.println(param5.substring(param5.indexOf(" ")+1));
		insere="";
			insere="INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes) VALUES ('1','"+p.getEmpresa().getStr_empresa_id()+"','"+usuario+"','"+param1+"','"+param2+"','"+f2.format(d.getTime())+"',"+param4+",'"+param5+"','"+date_sql.toString()+" "+param5.substring(param5.indexOf(" ")+1)+"',"+aux_hora+","+aux_min+",'"+tipo_registro+"','"+array_string_aux[0]+"','"+array_string_aux[1]+"','"+array_string_aux[2]+"',"+(d.get(Calendar.MONTH)+1)+")";
			//System.out.println(insere);
			if(conn.Inserir_simples(insere)){
	    		System.out.println("Registro Cadastrado");
	    		geo.append("type", "Feature");
				geometry.append("type", "Point");
				geometry.append("coordinates", verfica_coordenadas(param1,param2));
				geo.append("geometry",geometry);
				properties.append("Usuario", p.get_PessoaUsuario());
				properties.append("Local_Registro", array_string_aux[1]);
				properties.append("Tipo_local", array_string_aux[0]);
				properties.append("Hora_Registro", f3.format(d.getTime()));
				properties.append("Distancia_local", param4);
				properties.append("Coordenadas", param1+","+param2);
				geo.append("properties", properties);
				registro.append("Usuario", p.get_PessoaUsuario());
				registro.append("Empresa", p.getEmpresa().getEmpresa_id());
				registro.append("data_dia", d.getTime());
				registro.append("data_dia_string", f2.format(d.getTime()));
				registro.append("datetime_mobile", param5);
				//System.out.println("hora formartada:"+ checa_formato_data_e_hora(f3.format(mobile_time.getTime())));
				registro.append("datetime_mobile_data", checa_formato_data_e_hora(f3.format(d.getTime())));
				registro.append("datetime_servlet", date_sql);
				registro.append("hora", d.get(Calendar.HOUR_OF_DAY));
				registro.append("minuto", d.get(Calendar.MINUTE));
				registro.append("dia", d.get(Calendar.DAY_OF_MONTH));
				registro.append("mes", (d.get(Calendar.MONTH)+1));
				registro.append("ano", d.get(Calendar.YEAR));
				registro.append("tipo_registro", tipo_registro);
				registro.append("local_registro", array_string_aux[0]);
				registro.append("distancia", param4);
				registro.append("tipo_local_registro", array_string_aux[1]);
				registro.append("site_operadora_registro", array_string_aux[2]);
				registro.append("timeStamp_mobile", param3);
				registro.append("GEO", geo);
	    		cm.InserirSimpels("Registros", registro);
			}
			if(tipo_registro.equals("Saída")) {
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//System.out.println("entrou no calculo de horas");
				String dateStart = "";
				String dateStop = "";
				String exp_entrada="";
				String exp_saida="";
				query="SELECT * FROM registros where usuario='"+usuario+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
				rs=conn.Consulta(query);
				if(rs.next()) {
					dateStart=rs.getString("datetime_servlet");
					exp_entrada=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
					//exp_entrada=exp_entrada+" 08:10:00";
				}
				query="SELECT * FROM registros where usuario='"+usuario+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
				rs=conn.Consulta(query);
				if(rs.next()) {
					dateStop=rs.getString("datetime_servlet");
					exp_saida=rs.getString("datetime_servlet").substring(0, rs.getString("datetime_servlet").length()-12);
					//exp_saida=exp_saida+" 18:30:00";
				}
				query="SELECT entrada,saida FROM expediente";
				rs=conn.Consulta(query);
				if(rs.next()) {
					exp_entrada=exp_entrada+" " + rs.getString(1)+":00";
					exp_saida=exp_saida+" " + rs.getString(2)+":00";
				}

				// Custom date format
				

				Date d1 = null;
				Date d2 = null;
				
				//System.out.println("hora inicio:"+dateStart);
				//System.out.println("hora fim:"+dateStop);
				
					double horas_normais=0.0;
					NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
					DecimalFormat numberFormat = (DecimalFormat)nf;
					numberFormat.applyPattern("#0.00");
					
					double horas_noturnas=0.0;
					double horas_tot=0.0;
					double min_noturnas=0.0;
					int hh_saida;
					int hh_entrada;
				    d1 = format.parse(dateStart);
				    Calendar hora_entrada=Calendar.getInstance();
				    hora_entrada.setTime(d1);
				    //System.out.println("teste fabio: "+hora_entrada.get(Calendar.HOUR_OF_DAY));
				    hh_entrada=hora_entrada.get(Calendar.HOUR_OF_DAY);
				    //System.out.println("dt start:"+dateStart);
				    //System.out.println("dt stop:"+dateStop);
				    d2 = format.parse(dateStop);
				    Calendar hora_saida=Calendar.getInstance();
				    hora_saida.setTime(d2);
				    hh_saida=hora_saida.get(Calendar.HOUR_OF_DAY);
				    horas_tot=hh_saida-hh_entrada;
				    //System.out.println(hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE));
				    //System.out.println((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)) / 60.0);
				    horas_tot=horas_tot+((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)) / 60.0);
				    if(hh_entrada>=18 && hh_saida>=22 ) {
				    	System.out.println("opcao 1");
				    	//System.out.println(horas_tot);
				    	horas_noturnas=horas_noturnas+(hh_saida-22);
				    	min_noturnas=hora_saida.get(Calendar.MINUTE);
				    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
				    	horas_normais=horas_tot-horas_noturnas;
				    }else if(hh_entrada<=5 && hh_saida<=5){
				    	System.out.println("opcao 2");
				    	horas_noturnas=hh_saida-hh_entrada;
				    	min_noturnas=hora_saida.get(Calendar.MINUTE)+hora_entrada.get(Calendar.MINUTE);
				    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
				    	horas_normais=horas_tot-horas_noturnas;
				    }else if(hh_entrada<=5 && hh_saida>=22){
				    	System.out.println("opcao 3");
				    	horas_tot=horas_tot-10;
				    	horas_noturnas=horas_noturnas + (hh_saida - 22);
				    	horas_noturnas=horas_noturnas + (5 - hh_entrada);
				    	min_noturnas=hora_saida.get(Calendar.MINUTE)+hora_entrada.get(Calendar.MINUTE);
				    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
				    	horas_normais=horas_tot-horas_noturnas;
				    }else if(hh_entrada>=18){
				    	System.out.println("opcao 4");
				    	//horas_tot=hh_saida-hh_entrada;
				    	if(hh_saida>=22) {
				    		System.out.println("opcao 4.1");
				    		horas_noturnas=horas_noturnas + (hh_saida - 22);
				    		min_noturnas=hora_saida.get(Calendar.MINUTE);
					    	horas_noturnas=horas_noturnas+(min_noturnas / 60.0);
					    	horas_normais=horas_tot-horas_noturnas;
				    	}else {
				    		System.out.println("opcao 4.2");
				    		//System.out.println((hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE)));
				    		horas_normais=horas_tot+((Math.abs(hora_saida.get(Calendar.MINUTE) - hora_entrada.get(Calendar.MINUTE))) / 60.0);
				    	}
				    	
				    }else {
				    	System.out.println("opcao 5");
				    	if(hh_saida>=18 && hh_saida<=22) {
				    		System.out.println("opcao 5.1");
				    		horas_noturnas=0;
				    		horas_normais=hh_saida-18;
				    		min_noturnas=hora_saida.get(Calendar.MINUTE);
				    		horas_normais=horas_normais+(min_noturnas / 60.0);
				    	}
				    }
				    
				    System.out.println("Analisando HH para "+usuario);
				    System.out.println("Hora Entrada mais cedo "+f3.format(hora_entrada.getTime()));
				    System.out.println("Hora Entrada maid tardia "+f3.format(hora_saida.getTime()));
				    System.out.println("Total "+numberFormat.format(horas_tot));
					System.out.println("Total de Horas Extras Normais "+numberFormat.format(horas_normais));
					System.out.println("Total de horas noturnas "+numberFormat.format(horas_noturnas));
				if(horas_normais>0 || horas_noturnas>0) {
				
				query="SELECT * FROM horas_extras WHERE id_usuario='"+usuario+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"' order by he_data desc limit 1";
				rs=conn.Consulta(query);
				
				
				if(rs.next()) {
					query2="update horas_extras set he_quantidade="+numberFormat.format(horas_normais)+",horas_noturnas="+numberFormat.format(horas_noturnas)+",entrada='"+f3.format(hora_entrada.getTime())+"',saida='"+f3.format(hora_saida.getTime())+"',origen='Automatico - MSTP WEB',aprovada='Y',compensada='N' where id_usuario='"+usuario+"' and DATE_FORMAT(STR_TO_DATE(he_data,'%d/%m/%Y'), '%Y-%m-%d')='"+date_sql.toString()+"'";
					conn.Alterar(query2);
				}else {
					insere="";
					insere="insert into horas_extras (id_usuario,he_data,he_quantidade,entrada,saida,horas_noturnas,aprovada,origen,compensada,dt_add) values('"+usuario+"','"+f2.format(time)+"',"+numberFormat.format(horas_normais)+",'"+f3.format(hora_entrada.getTime())+"','"+f3.format(hora_saida.getTime())+"',"+numberFormat.format(horas_noturnas)+",'Y','MANUAL - MSTP WEB','N','"+f3.format(time)+"')";
					
					conn.Inserir_simples(insere);
				
				}
				}}}
				 catch (ParseException e) {
				    e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
	public String[] calcula_hh(String entrada,String saida,Feriado f,Conexao c, Pessoa p,String usuarioPesquisado) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat twoDForm = (DecimalFormat)nf;
		twoDForm.applyPattern("#0.00");
		Date d1 = null;
		Date d2 = null;
		//String msg="";
		String[] retorno=new String[4];
		try {
			d1 = format.parse(entrada);
			d2= format.parse(saida);
			Calendar hora_saida=Calendar.getInstance();
		    hora_saida.setTime(d2);
		    Calendar hora_entrada=Calendar.getInstance();
		    hora_entrada.setTime(d1);
		    int aux_hora_entrada=hora_entrada.get(Calendar.HOUR_OF_DAY);
			int aux_min_entrada=hora_entrada.get(Calendar.MINUTE);
			double total_hora_entrada=aux_hora_entrada + (aux_min_entrada /60.0);
			int aux_hora_saida=hora_saida.get(Calendar.HOUR_OF_DAY);
			int aux_min_saida=hora_saida.get(Calendar.MINUTE);
			double total_hora_saida=aux_hora_saida + (aux_min_saida / 60.0);
			double total_horas=total_hora_saida-total_hora_entrada;
			double horas_extras=0.0;
			double horas_extras_noturnas=0.0;
			if(hora_entrada.get(Calendar.DAY_OF_WEEK)!=1 && hora_entrada.get(Calendar.DAY_OF_WEEK)!=7) {
				if(f.verifica_feriado(f2.format(hora_entrada.getTime()),p.getEstadoUsuario(usuarioPesquisado, c), c, p.getEmpresa().getEmpresa_id())) {

					 
					horas_extras=total_horas-1.2;
					horas_extras_noturnas=0.0;
					if(total_hora_saida>22.0 && total_hora_saida<23.59) {
						horas_extras_noturnas=total_hora_saida-22.0;
						horas_extras=horas_extras-horas_extras_noturnas;
					}
				
				retorno[0]="Hora Extra";
				retorno[1]=String.valueOf(twoDForm.format(horas_extras));
				retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
				
					retorno[3]="Feriado";
				
				//msg="BH:" + String.valueOf(twoDForm.format(horas_extras+horas_extras_noturnas));
				
				}else {
				if(total_horas>10.0) {
					horas_extras=total_horas-10.0;
					horas_extras_noturnas=0.0;
					if(total_hora_saida>22.0 && total_hora_saida<23.59) {
						horas_extras_noturnas=total_hora_saida-22.0;
						horas_extras=horas_extras-horas_extras_noturnas;
					}
				}else {
					horas_extras=total_horas-10.0;
					horas_extras_noturnas=0.0;
				}
				retorno[0]="Banco";
				retorno[1]=String.valueOf(twoDForm.format(horas_extras));
				retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
				retorno[3]="";
				//msg="HE:" + String.valueOf(twoDForm.format(horas_extras)) + " | HEN:" + String.valueOf(twoDForm.format(horas_extras_noturnas));
				}}else {
				 
					horas_extras=total_horas-1.2;
					horas_extras_noturnas=0.0;
					if(total_hora_saida>22.0 && total_hora_saida<23.59) {
						horas_extras_noturnas=total_hora_saida-22.0;
						horas_extras=horas_extras-horas_extras_noturnas;
					}
				
				retorno[0]="Hora Extra";
				retorno[1]=String.valueOf(twoDForm.format(horas_extras));
				retorno[2]=String.valueOf(twoDForm.format(horas_extras_noturnas));
				if(hora_entrada.get(Calendar.DAY_OF_WEEK)==7) {
					retorno[3]="Sábado";
				}else {
					retorno[3]="Domingo";
				}
				//msg="BH:" + String.valueOf(twoDForm.format(horas_extras+horas_extras_noturnas));
				 }
			
			return retorno;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int numeroAleatorio(int min, int max){

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	public List<Double> verfica_coordenadas(String lng,String lat) {
		 try {
			 Double f_lat=Double.parseDouble(lat.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
			 Double f_lng=Double.parseDouble(lng.replace(",", ".").replaceAll("\n", "").replaceAll("\r", "").trim());
			 return Arrays.asList(f_lat,f_lng);
		 }catch (NumberFormatException e) {
				
				
				return Arrays.asList(-10.00,-10.00);
		}
	}
	 public Date checa_formato_data_e_hora(String data) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(padrao_data_hora_br);
				
				Date d1=format.parse(data);
				return d1;
			}catch (ParseException e) {
				//System.out.println(data + " - Data inválida");
				return null;
				
			} 
		}
	}

