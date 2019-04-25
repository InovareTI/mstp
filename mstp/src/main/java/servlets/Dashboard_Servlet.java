package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.Pessoa;
import classes.Conexao;

/**
 * Servlet implementation class Dashboard_Servlet
 */
@WebServlet("/Dashboard_Servlet")
public class Dashboard_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard_Servlet() {
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
		monta_dash_po(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		monta_dash_po(request, response);
	}
	public void monta_dash_po(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		ResultSet rs ;
		ResultSet rs2 ;
		String tabela;
		String opt;
		
		String query;
		
		String dtfrom;
		String dtto;
		
		HttpSession session = req.getSession(true);
		Conexao conn = (Conexao) session.getAttribute("conexao");
		Pessoa p= (Pessoa) session.getAttribute("pessoa");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DateFormat f3 = DateFormat.getDateTimeInstance();
		opt=req.getParameter("opt");
		double money; 
		NumberFormat number_formatter = NumberFormat.getCurrencyInstance();
		String moneyString;
		//System.out.println("Chegou no servlet de Dashboard");
		try {
			//System.out.println(p.get_PessoaUsuario()+" Chegou no servlet de Operações de Gráficos do MSTP Web - "+f3.format(time));
			if(opt.equals("1")){
				//System.out.println("Pie Chart starts...");
    			rs=conn.Consulta("select empresa_emissora,count(po_number) from po_table  group by empresa_emissora");
    			tabela="["+"\n";
	    		//System.out.println(tabela);
	    		
	    		
	    		if(rs.next()){
	    			rs.beforeFirst();
	    			//System.out.println("Consulta 1 Realizada com Sucesso");
	    			
	    			while(rs.next()){
	    				tabela=tabela+"[\""+rs.getString(1)+"\","+rs.getString(2)+"],"+"\n";
	    				
	    			}

	    			
	    		}else{
	    			tabela=tabela+"[\"SEM PO RECEBIDA\",1],"+"\n";
	    		}
	    		tabela=tabela.substring(0,tabela.length()-2);
    			tabela=tabela+"\n"+"]";
				 // System.out.println(tabela);
	    		  resp.setContentType("application/json");  
	    		  resp.setCharacterEncoding("UTF-8"); 
	    		 // resp.getWriter().write(tabela); 
	    		  
	    		  
	    			PrintWriter out = resp.getWriter();
	    			out.print(tabela);
	    			Timestamp time2 = new Timestamp(System.currentTimeMillis());
					System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
			}else if(opt.equals("2")){
				//param1=(req.getParameter("ano"));
				//param2=(req.getParameter("periodo"));
				//System.out.println("Pie Chart starts...");
			//System.out.println("SELECT * FROM opened_task_month_type where YEAR_TASK="+dtano);
			rs=conn.Consulta("selECT distinct empresa_emissora from po_table");
			
			tabela="[";
    		//System.out.println(tabela);
    		tabela=tabela+"{\"name\":\"Mes\","+"\n";
			tabela=tabela+" \"data\":[\"Janeiro\",\"Fevereiro\",\"Março\",\"Abril\",\"Maio\",\"Junho\",\"Julho\",\"Agosto\",\"Setembro\",\"Outubro\",\"Novembro\",\"Dezembro\"]"+"\n";
			tabela=tabela+" },"+"\n";
			if(rs.next()){
    			rs.beforeFirst();
    			while(rs.next()){
    			tabela=tabela+"{\"name\":\""+rs.getString(1)+"\","+"\n";
    			//System.out.println("SELECT id_mes,atividade,quantidade FROM mes left join opened_task_month_type on id_mes=month and YEAR_TASK="+dtano+" and atividade='"+rs.getString(1)+"' order by id_mes");
    			rs2=conn.Consulta("SELECT id_mes,mes,empresa_emissora,sum(total_po) FROM mes left join po_table on month(dt_emitida)=id_mes and empresa_emissora='"+rs.getString(1)+"' group by empresa_emissora,mes order by id_mes,empresa_emissora");
    			tabela=tabela+" \"data\":[";
    			if(rs2.next()){
    				rs2.beforeFirst();
    				int g=1;
	    			while(rs2.next() && g<13){
	    				if(rs.getString(1).equals(rs2.getString(3))){
	    					if(g==rs2.getInt(1)){
	    						money = Double.parseDouble(rs2.getString(4));
								moneyString = number_formatter.format(money);
	    						tabela=tabela+rs2.getString(4)+",";
	    					}else{
	    						tabela=tabela+"0.00,";
	    					}
	    				}else{
	    					tabela=tabela+"0.00,";
	    				}
	    				g++;
	    			}
	    		}
	    			//System.out.println("saiu no whilw");
    			tabela=tabela.substring(0,tabela.length()-1);
    			tabela=tabela+"]},"+"\n";
    			}
    			tabela=tabela.substring(0,tabela.length()-2);
    			tabela=tabela+"]";
			}else{
				tabela=tabela+"{\"name\":\"SEM PO\","+"\n";
				tabela=tabela+" \"data\":[0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00]}]";
			}
			 //System.out.println(tabela);
    		  resp.setContentType("application/json");  
    		  resp.setCharacterEncoding("UTF-8"); 
    		 // resp.getWriter().write(tabela); 
    		  
    		  
    			PrintWriter out = resp.getWriter();
    			out.print(tabela);
    			Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("3")){
			dtfrom=req.getParameter("dtfrom");
			dtto=req.getParameter("dtto");
			//System.out.println("data from:"+dtfrom);
			//System.out.println("data to:"+dtto);
			query="select t1.*,t2.total_done from (SELECT milestone,count(dt_inicio_bl) as total_plan FROM rollout "
					+ "where str_to_date(dt_inicio_bl,'%d/%m/%Y') >= str_to_date('"+dtfrom+"','%d/%m/%Y') "
					+ "and str_to_date(dt_inicio_bl,'%d/%m/%Y') <= str_to_date('"+dtto+"','%d/%m/%Y') and tipo_campo='Milestone' and linha_ativa='Y'  group by milestone) as t1 "
					+ "left join (SELECT milestone,count(dt_fim_bl) as total_done FROM rollout where "
					+ "str_to_date(dt_fim_bl,'%d/%m/%Y') >= str_to_date('"+dtfrom+"','%d/%m/%Y') "
					+ "and str_to_date(dt_fim_bl,'%d/%m/%Y') <= str_to_date('"+dtto+"','%d/%m/%Y') and tipo_campo='Milestone' and linha_ativa='Y' group by milestone) as t2 "
					+ "on t1.milestone=t2.milestone";
			//System.out.println(query);
			rs=conn.Consulta(query); 
			//rs=null;
			tabela="[";
			
			tabela=tabela+"{\"name\":\"Type\","+"\n";
			
			tabela=tabela+" \"data\":[";
			if(rs.next()){
				
				rs.beforeFirst();
				while(rs.next()){
					tabela=tabela+ "\""+rs.getString(1)+"\",";
							
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"]},"+"\n";
				rs.beforeFirst();
				tabela=tabela+"{\"name\":\"Inicio Previsto\","+"\n";
				tabela=tabela+" \"data\":[";
				while(rs.next()){
					
					tabela=tabela+rs.getString(2)+",";
				
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"]},"+"\n";
				tabela=tabela+"{\"name\":\"Fim Previsto\","+"\n";
				tabela=tabela+" \"data\":[";
				rs.beforeFirst();
				while(rs.next()){
					if(rs.getString(3)==null){
						tabela=tabela+"0,";
					}else{
					tabela=tabela+rs.getString(3)+",";
					}
				
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"]},"+"\n";
				//inicio e fim plan
				
				
				//tabela=tabela+"]";
			}else{
				tabela="[{\"name\":\"Type\","+
					 "\"data\":[\"Sem Resultado\"]},"+
				        "{\"name\":\"Inicio Previsto\","+
				         "\"data\":[0]},"+
				        "{\"name\":\"Fim Previsto\","+
				         "\"data\":[0]},";
				        
			}
			
			query="select t1.*,t2.total_done from (SELECT milestone,count(dt_inicio) as total_plan FROM rollout "
					+ "where str_to_date(dt_inicio,'%d/%m/%Y') >= str_to_date('"+dtfrom+"','%d/%m/%Y') "
					+ "and str_to_date(dt_inicio,'%d/%m/%Y') <= str_to_date('"+dtto+"','%d/%m/%Y') and tipo_campo='Milestone' and linha_ativa='Y' group by milestone) as t1 "
					+ "left join (SELECT milestone,count(dt_fim) as total_done FROM rollout where "
					+ "str_to_date(dt_fim,'%d/%m/%Y') >= str_to_date('"+dtfrom+"','%d/%m/%Y') "
					+ "and str_to_date(dt_fim,'%d/%m/%Y') <= str_to_date('"+dtto+"','%d/%m/%Y') and tipo_campo='Milestone' and linha_ativa='Y' group by milestone) as t2 "
					+ "on t1.milestone=t2.milestone";
			//System.out.println(query);
			rs=conn.Consulta(query); 
			if(rs.next()){
				rs.beforeFirst();
			tabela=tabela+"{\"name\":\"Inicio Real\","+"\n";
			tabela=tabela+" \"data\":[";
			while(rs.next()){
				
				tabela=tabela+rs.getString(2)+",";
			
			}
			tabela=tabela.substring(0,tabela.length()-1);
			tabela=tabela+"]},"+"\n";
			tabela=tabela+"{\"name\":\"Fim Real\","+"\n";
			tabela=tabela+" \"data\":[";
			rs.beforeFirst();
			while(rs.next()){
				if(rs.getString(3)==null){
					tabela=tabela+"0,";
				}else{
				tabela=tabela+rs.getString(3)+",";
				}
			
			}
			tabela=tabela.substring(0,tabela.length()-1);
			tabela=tabela+"]}]"+"\n";
			}else{
				tabela=tabela+
					        "{\"name\":\"Inicio Real\","+
					         "\"data\":[0]},"+
					        "{\"name\":\"Fim Real\","+
					         "\"data\":[0]}]";
			}
				//System.out.println(tabela);
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(tabela);
			    Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("4")){
			
			
			if(p.get_PessoaPerfil_nome().equals("Financeiro")){
			query="select status_faturamento,sum(valor_parcela) from  faturamento  group by status_faturamento";
			}else{
				query="select * from  faturamento  where id_faturamento=2343";
				}
			//System.out.println(query);
			rs=conn.Consulta(query); 
			//rs=null;
			tabela="[{";
			
			/*tabela=tabela+"{\"name\":\"Type\","+"\n";
			
			tabela=tabela+" \"data\":[";
			if(rs.next()){
				
				rs.beforeFirst();
				while(rs.next()){
					tabela=tabela+ "\""+rs.getString(1)+"\",";
							
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"]},"+"\n";*/
			    if(rs.next()){
				rs.beforeFirst();
				tabela=tabela+"\"name\": \"Volume Financeiro\","+"\n";
				tabela=tabela+"\"data\":[{"+"\n";
				tabela=tabela+"\"name\": \"Volume Financeiro\","+"\n";
				tabela=tabela+"\"colorByPoint\": true,"+"\n";
				tabela=tabela+"\"data\":["+"\n";
				while(rs.next()){
					tabela=tabela+"{\"name\":\""+rs.getString(1)+"\","+"\n";
					tabela=tabela+" \"y\":"+rs.getString(2)+","+"\n";
					tabela=tabela+ "\"drilldown\": \""+rs.getString(1)+"\"},";
				
				}
				
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"]}]},{"+"\n";
				tabela=tabela+"\"name\": \"drilldown\","+"\n";
				tabela=tabela+ "\"data\":["+"\n";
				
				
				
					rs.beforeFirst();
					while(rs.next()){
						tabela=tabela+"{\"name\":\""+rs.getString(1)+"\","+"\n";
						tabela=tabela+"\"id\":\""+rs.getString(1)+"\","+"\n";
						tabela=tabela+"\"data\":["+"\n";
						if(p.get_PessoaPerfil_nome().equals("Financeiro")){
							query="select cliente,sum(valor_parcela) from  faturamento where status_faturamento='"+rs.getString(1)+"' group by cliente";
							}else{
							query="";
						}
						rs2=conn.Consulta(query); 
						if(rs2.next()){
							rs2.beforeFirst();
							while(rs2.next()){
								tabela=tabela+"[ \""+rs2.getString(1)+"\","+"\n";
								tabela=tabela+rs2.getString(2)+"],";
							}
							tabela=tabela.substring(0,tabela.length()-1);
							tabela=tabela+"]},";
						}
				}
					tabela=tabela.substring(0,tabela.length()-1);
					tabela=tabela+"]}"+"\n";
					
              
					tabela=tabela+"]"+"\n";		
				
				
			}else{
				tabela="[{\"name\":\"Valor Financeiro\","+
						"\"data\":[\"Sem Resultado\"]},"+
				        "{\"name\":\"Valor Financeiro\","+
				         "\"data\":[0.00]}"+
				       
				        "]";
			}
				//System.out.println(tabela);
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(tabela);
			    Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("5")){
			//System.out.println("carregando graficos de registros de usuario por semana");
			if(p.get_PessoaPerfil_nome().equals("tecnico")) {
				query="SELECT usuario,count(usuario) from registros where usuario='"+p.get_PessoaUsuario()+"' and week(datetime_servlet)=week(now()) and empresa='"+p.getEmpresa().getEmpresa_id()+"' and tipo_registro not in('Férias','Folga','Compensação','Licença Médica') group by usuario order by count(usuario) desc";
			}else {
				query="SELECT usuario,count(usuario) from registros where week(datetime_servlet)=week(now()) and empresa='"+p.getEmpresa().getEmpresa_id()+"' and tipo_registro not in('Férias','Folga','Compensação','Licença Médica') group by usuario order by count(usuario) desc";
			}
			//System.out.println(query);
			rs=conn.Consulta(query);
			tabela="[]";
			if(rs.next()) {
				tabela="[{\"data\":[\n";
				rs.beforeFirst();
				while(rs.next()) {
					tabela=tabela+"[\""+rs.getString("usuario")+"\","+rs.getInt(2)+"],\n";
				}
				tabela=tabela.substring(0,tabela.length()-2);
				tabela=tabela+"]}]";
			}
			//System.out.println(tabela);
			resp.setContentType("application/json");  
  		    resp.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = resp.getWriter();
		    out.print(tabela);
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("6")){
			//System.out.println("Carregando horas trabalhadas por usuário na semana");
			Calendar d1 = Calendar.getInstance();
			Calendar d2 = Calendar.getInstance();
			int aux_hora=0;
			double aux_hora2=0.0;
			Date dt_aux = null;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
			DecimalFormat numberFormat = (DecimalFormat)nf;
			numberFormat.applyPattern("#0.00");
			query="";
			query="select entradatbl.datetime_mobile as entrada,saidatbl.datetime_mobile as saida from (select data_dia,tipo_registro,min(datetime_mobile) as datetime_mobile from registros where usuario='"+p.get_PessoaUsuario()+"' and week(datetime_servlet)=week(now()) and tipo_registro='Entrada' and empresa='"+p.getEmpresa().getEmpresa_id()+"' group by data_dia,tipo_registro order by datetime_servlet asc) as entradatbl, (select data_dia,tipo_registro,max(datetime_mobile) as datetime_mobile from registros where usuario='"+p.get_PessoaUsuario()+"' and week(datetime_servlet)=week(now()) and tipo_registro='Saída' and empresa='"+p.getEmpresa().getEmpresa_id()+"' group by data_dia,tipo_registro order by datetime_servlet desc) as saidatbl where entradatbl.data_dia=saidatbl.data_dia";
			rs=conn.Consulta(query);
			if(rs.next()) {
			
				rs.beforeFirst();
				while(rs.next()) {
					
							dt_aux=format.parse(rs.getString(1));
							d1.setTime(dt_aux);
				
							dt_aux=format.parse(rs.getString(2));
							d2.setTime(dt_aux);
							aux_hora=aux_hora+(d2.get(Calendar.HOUR_OF_DAY)-d1.get(Calendar.HOUR_OF_DAY));
							aux_hora2=Math.abs(d2.get(Calendar.MINUTE)-d1.get(Calendar.MINUTE)) / 60.0;
							aux_hora2=aux_hora2+aux_hora;
					}
					
				
			}else {
				aux_hora2=0.00;
			}
			resp.setContentType("application/text");  
  		    resp.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = resp.getWriter();
		    out.print(
		    		"<div class=\"card\">"+
		    		  
		    		  "<div class=\"container\">"+
		    		  "<h4><b>"+numberFormat.format(aux_hora2)+"</b></h4>"+
		    		    "<p>Total de Horas dos meus registros durante semana corrente</p> "+
		    		  "</div>"+
		    		"</div><p><br></p>"+
		    		"<div class=\"card\">"+
		    		  
		    		  "<div class=\"container\">"+
		    		  "<h4><b style=\"color:#d8431f\">"+numberFormat.format(40.0 - aux_hora2)+"</b></h4>"+
		    		    "<p>Total de Horas pendentes</p> "+
		    		  "</div>"+
		    		"</div>"
		    		);
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("7")){
			//System.out.println("carregando dashboard grafico de pendencias");
			query="";
			tabela="";
			query="select count(id_ajuste_ponto) from ajuste_ponto where aprovada='N' and empresa="+p.getEmpresa().getEmpresa_id();
			int conta=0;
			rs=conn.Consulta(query);
			if(rs.next()) {
				conta=conta+rs.getInt(1);
			}
			
			tabela="[{"+
		        "\"name\": \"Aprovações Pendentes\",\n"+
		        "\"colorByPoint\": true,\n"+
		        "\"data\": [\n{\n"+
		            "\"name\": \"Ajuste de Ponto\",\n"+
		            "\"y\": "+conta+",\n"+
		            "\"sliced\": true,\n"+
		            "\"selected\": true\n"+
		        "},\n"; 
			
			query="";
			query="select count(id_he) from horas_extras where aprovada='N' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
			rs=conn.Consulta(query);
			conta=0;
			if(rs.next()) {
				conta=conta+rs.getInt(1);
			}
			tabela=tabela+"{"+
		           " \"name\": \"Horas Extras\",\n"+
		           " \"y\": "+conta+" },";
		    //System.out.println(tabela);  
			query="";
			conta=0;
			query="select count(site_id) from site_aprova where aprovado='N' and empresa='"+p.getEmpresa().getEmpresa_id()+"'";
			rs=conn.Consulta(query);
			if(rs.next()) {
				conta=conta+rs.getInt(1);
			}
			tabela=tabela+"{"+
			           " \"name\": \"Sites Novos\",\n"+
			           " \"y\": "+conta+" }\n]\n}\n]";
			resp.setContentType("application/text");  
  		    resp.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = resp.getWriter();
		    out.print(tabela);
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("8")){
			query="";
			query="select resumo.usuario,sum(resumo.horas) from "
					+ "(select entradatbl.usuario,entradatbl.datetime_mobile as entrada,saidatbl.datetime_mobile as saida, TIMESTAMPDIFF(minute,entradatbl.datetime_mobile,saidatbl.datetime_mobile)/60 as horas from"
					+ " (select usuario,data_dia,tipo_registro,min(STR_TO_DATE(datetime_mobile, '%d/%m/%Y %H:%i:%s')) as datetime_mobile from registros where week(datetime_servlet)=week(now()) and tipo_registro='Entrada' and empresa='"+p.getEmpresa().getEmpresa_id()+"' group by usuario,data_dia,tipo_registro order by datetime_servlet asc) as entradatbl,(select usuario,data_dia,tipo_registro,max(STR_TO_DATE(datetime_mobile, '%d/%m/%Y %H:%i:%s')) as datetime_mobile from"
					+ " registros where week(datetime_servlet)=week(now()) and tipo_registro='Saída' and empresa='"+p.getEmpresa().getEmpresa_id()+"' group by usuario,data_dia,tipo_registro order by datetime_servlet desc) as saidatbl " + 
					"where entradatbl.usuario=saidatbl.usuario and entradatbl.data_dia=saidatbl.data_dia) as resumo  group by resumo.usuario order by sum(resumo.horas) desc";
			//System.out.println(query);
			rs=conn.Consulta(query);
			if(rs.next()) {
				rs.beforeFirst();
				tabela="{\"categoria\":\n[";
				while(rs.next()) {
					if(rs.getInt(2)>0) {
						tabela=tabela+"\""+rs.getString(1)+"\",";
					}
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"],\n";
				tabela=tabela+"\"serie\":[\n";
				tabela=tabela+"{\"name\": \"Horas Semanais\",\n"+
				"\"data\": [";
				rs.beforeFirst();
				while(rs.next()) {
					if(rs.getInt(2)>0) {
					
						tabela=tabela+rs.getString(2)+",";
			    
					}
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"]\n}\n]\n}";
				//System.out.println(tabela);
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(tabela);
			    
			}else {
				tabela="{\"categoria\":\n[\"Sem Registro Completos\"],";
				tabela=tabela+"\"serie\":[\n";
				tabela=tabela+"{\"name\": \"Horas Semanais\",\n"+"\"data\": []}]}";
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(tabela);
			}
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("9")) {

			dtfrom=req.getParameter("dtfrom");
			dtto=req.getParameter("dtto");
			//System.out.println("data from:"+dtfrom);
			//System.out.println("data to:"+dtto);
			query="select milestone,work_days,count(recid) from rollout where tipo_campo='Milestone' and str_to_date(dt_inicio,'%d/%m/%Y') >= str_to_date('"+dtfrom+"','%d/%m/%Y') and  str_to_date(dt_fim,'%d/%m/%Y') <= str_to_date('"+dtto+"','%d/%m/%Y') group by milestone,work_days";
			System.out.println(query);
			rs=conn.Consulta(query); 
			//rs=null;
			tabela="[]";
			if(rs.next()){
			tabela="[";
			
			tabela=tabela+"{\"name\":\""+rs.getString("Milestone")+"\","+"\n";
			
			tabela=tabela+" \"data\":[";
			
				
				rs.beforeFirst();
				while(rs.next()){
					tabela=tabela+ "[\""+rs.getString("work_days")+"\","+rs.getInt(3)+"],\n";
							
				}
				tabela=tabela.substring(0,tabela.length()-2);
				tabela=tabela+"]}]";
			}
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(tabela);
			    Timestamp time2 = new Timestamp(System.currentTimeMillis());
				System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}
				
			
		}catch (Exception e) {
			conn.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
