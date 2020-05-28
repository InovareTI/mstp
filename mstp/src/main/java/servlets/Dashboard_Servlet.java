package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;

/**
 * Servlet implementation class Dashboard_Servlet
 */
@WebServlet("/Dashboard_Servlet")
public class Dashboard_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String padrao_data_br = "dd/MM/yyyy";
	private static final String padrao_data_hora_br = "dd/MM/yyyy HH:mm:ss";
	
       
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
				System.out.println("Pie Chart starts...");
    			rs=conn.Consulta("select empresa_emissora,count(po_number) from po_table where empresa="+p.getEmpresa().getEmpresa_id()+"  group by empresa_emissora");
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
				 System.out.println(tabela);
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
			rs=conn.Consulta("selECT distinct empresa_emissora from po_table where empresa="+p.getEmpresa().getEmpresa_id());
			
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
    			rs2=conn.Consulta("SELECT id_mes,mes,empresa_emissora,sum(total_po) FROM mes left join po_table on month(str_to_date(dt_emitida,'%d/%m/%Y'))=id_mes and empresa_emissora='"+rs.getString(1)+"' group by empresa_emissora,mes order by id_mes,empresa_emissora");
    			tabela=tabela+" \"data\":[";
    			if(rs2.next()){
    				rs2.beforeFirst();
    				int g=1;
	    			while(rs2.next() && g<13){
	    				if(rs.getString(1).equals(rs2.getString(3))){
	    					if(g==rs2.getInt(1)){
	    						money = rs2.getDouble(4);
								moneyString = number_formatter.format(money);
	    						tabela=tabela+rs2.getDouble(4)+",";
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
			String rollout = req.getParameter("rollout");
			//System.out.println(rollout);
			SimpleDateFormat format = new SimpleDateFormat(padrao_data_br);
			Date i = format.parse(dtfrom);
			Date f = format.parse(dtto);
			ConexaoMongo mongo= new ConexaoMongo();
			rs = conn.Consulta("select field_name,rollout_id,rollout_nome from rollout_campos where field_type='Milestone' and empresa="+p.getEmpresa().getEmpresa_id()+" and rollout_nome='"+rollout+"' order by rollout_id");
			Bson filtro;
			//String milestone="";
			List<Bson> filtros = new ArrayList<>();
			if(rs.next()) {
				rs.beforeFirst();
				tabela="{\"categorias\":[\"Incio Planejado\",\"Fim Planejado\",\"Inicio Real\",\"Fim Real\"],";
				tabela=tabela+"\"dados\":[";
				while(rs.next()) {
					filtros.clear();
					filtros = new ArrayList<>();
					filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
					filtros.add(filtro);
					filtro=Filters.eq("Linha_ativa","Y");
					filtros.add(filtro);
					filtro=Filters.eq("rolloutId",rollout);
					filtros.add(filtro);
					filtro=Filters.elemMatch("Milestone", Filters.gte("edate_"+rs.getString("field_name"),i));
					filtros.add(filtro);
					filtro=Filters.elemMatch("Milestone", Filters.lte("edate_"+rs.getString("field_name"),f));
					filtros.add(filtro);
					Long totalFimReal= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);

					filtros.remove(filtros.size()-1);
					filtros.remove(filtros.size()-1);
					
					filtro=Filters.elemMatch("Milestone", Filters.gte("sdate_pre_"+rs.getString("field_name"),i));
					filtros.add(filtro);
					filtro=Filters.elemMatch("Milestone", Filters.lte("sdate_pre_"+rs.getString("field_name"),f));
					filtros.add(filtro);
					
					Long totalInicioPrevisto= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);
					filtros.remove(filtros.size()-1);
					filtros.remove(filtros.size()-1);
					filtro=Filters.elemMatch("Milestone", Filters.gte("edate_pre_"+rs.getString("field_name"),i));
					filtros.add(filtro);
					filtro=Filters.elemMatch("Milestone", Filters.lte("edate_pre_"+rs.getString("field_name"),f));
					filtros.add(filtro);
					Long totalFimPrevisto= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);
					filtros.remove(filtros.size()-1);
					filtros.remove(filtros.size()-1);
					filtro=Filters.elemMatch("Milestone", Filters.gte("sdate_"+rs.getString("field_name"),i));
					filtros.add(filtro);
					filtro=Filters.elemMatch("Milestone", Filters.lte("sdate_"+rs.getString("field_name"),f));
					filtros.add(filtro);
					Long totalInicioReal= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);
					
					tabela=tabela+"{\"name\":\""+rs.getString("field_name")+"\",";
					tabela=tabela+"\"data\":["+totalInicioPrevisto+","+totalFimPrevisto+","+totalInicioReal+","+totalFimReal+"]},";
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"]}";
			}else {
				tabela="{\"categorias\":[\"Incio Planejado\",\"Fim Planejado\",\"Inicio Real\",\"Fim Real\"],";
				tabela=tabela+"\"dados\":[{\"name\":\"Sem Milestone\",\"data\":[0,0,0,0]}]}";
			}
			/*
			if(p.getEmpresa().getEmpresa_id()==8) {
				milestone="TSS";
			}else if(p.getEmpresa().getEmpresa_id()==1) {
				milestone="INSTALAÇÃO";
			}else if(p.getEmpresa().getEmpresa_id()==5) {
				milestone="INSTALAÇÃO";
			}else {
				milestone="Sem Milestone Definido";
			}
			
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("Linha_ativa","Y");
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.gte("sdate_pre_"+milestone,i));
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.lte("sdate_pre_"+milestone,f));
			filtros.add(filtro);
			
			Long totalInicioPrevisto= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);
			tabela="[";
			
			tabela=tabela+"{\"name\":\"Type\",";
			tabela=tabela+" \"data\":[\""+milestone+"\"]},"+"\n";
			tabela=tabela+"{\"name\":\"Inicio Previsto\",";
			tabela=tabela+" \"data\":["+totalInicioPrevisto+"]},"+"\n";
			tabela=tabela+"{\"name\":\"Fim Previsto\",";
			filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("Linha_ativa","Y");
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.gte("edate_pre_"+milestone,i));
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.lte("edate_pre_"+milestone,f));
			filtros.add(filtro);
			Long totalFimPrevisto= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);
			tabela=tabela+" \"data\":["+totalFimPrevisto+"]},";
			filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("Linha_ativa","Y");
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.gte("sdate_TSS",i));
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.lte("sdate_TSS",f));
			filtros.add(filtro);
			Long totalInicioReal= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);
			tabela=tabela+"{\"name\":\"Inicio Real\","+"\n";
			tabela=tabela+" \"data\":["+totalInicioReal+"]},";
			filtros = new ArrayList<>();
			filtro=Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro=Filters.eq("Linha_ativa","Y");
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.gte("edate_"+milestone,i));
			filtros.add(filtro);
			filtro=Filters.elemMatch("Milestone", Filters.lte("edate_"+milestone,f));
			filtros.add(filtro);
			Long totalFimReal= mongo.CountSimplesComFiltroInicioLimit("rollout", filtros);
			tabela=tabela+"{\"name\":\"Fim Real\","+"\n";
			tabela=tabela+" \"data\":["+totalFimReal+"]}]";
			*/
				//System.out.println("Grafico de barras de milestones:"+tabela);
				resp.setContentType("application/json");  
	  		    resp.setCharacterEncoding("UTF-8"); 
	  		    PrintWriter out = resp.getWriter();
			    out.print(tabela);
			    mongo.fecharConexao();
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
		}else if(opt.equals("10")){
			
			ConexaoMongo mongo = new ConexaoMongo();
			Bson filtro;
			List<Bson> filtros = new ArrayList<>();
			filtro= Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro= Filters.or(Filters.not(Filters.eq("Requested Qty","0")),Filters.eq("Tickets",""));
			filtros.add(filtro);
			Long po_item_sem_ticket=mongo.ConsultaCountComplexa("PO", filtros);
			filtros = new ArrayList<>();
			filtro= Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro= Filters.eq("TICKET_STATE","NEW");
			filtros.add(filtro);
			Long po_item_ticket_new=mongo.ConsultaCountComplexa("TICKETS", filtros);
			filtros = new ArrayList<>();
			filtro= Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro= Filters.eq("TICKET_STATE","work");
			filtros.add(filtro);
			Long po_item_ticket_work=mongo.ConsultaCountComplexa("TICKETS", filtros);
			filtros = new ArrayList<>();
			filtro= Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			filtro= Filters.eq("TICKET_STATE","done");
			filtros.add(filtro);
			Long po_item_ticket_done=mongo.ConsultaCountComplexa("TICKETS", filtros);
			resp.setContentType("application/json");  
  		    resp.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = resp.getWriter();
  		    tabela="{\"item_po_sem_ticket\":"+po_item_sem_ticket+",\"item_po_ticket_new\":"+po_item_ticket_new+",\"item_po_ticket_work\":"+po_item_ticket_work+",\"item_po_ticket_done\":"+po_item_ticket_done+"}";
  		    //System.out.println(tabela);
		    out.print(tabela);
		    mongo.fecharConexao();
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}else if(opt.equals("11")){
			tabela="";
			String tabela_aux="";
			tabela="{\"projetos\":[";
			tabela_aux="\"dados_projetos\":[";
			ConexaoMongo mongo = new ConexaoMongo();
			Bson filtro;
			List<Bson> filtros = new ArrayList<>();
			filtro= Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
			filtros.add(filtro);
			List<String> projetos = mongo.ConsultaSimplesDistinct("TICKETS", "PROJETO", filtros);
			if(!projetos.isEmpty()) {
				Long pesquisa;
				for(int i=0;i<projetos.size();i++) {
					tabela=tabela+"\""+projetos.get(i)+"\",";
					filtros = new ArrayList<>();
					filtro= Filters.eq("Empresa",p.getEmpresa().getEmpresa_id());
					filtros.add(filtro);
					filtro= Filters.eq("PROJETO",projetos.get(i));
					filtros.add(filtro);
					pesquisa=mongo.ConsultaCountComplexa("TICKETS", filtros);
					tabela_aux=tabela_aux+pesquisa+",";
				}
				tabela=tabela.substring(0,tabela.length()-1);
				tabela=tabela+"],\n";
				tabela_aux=tabela_aux.substring(0,tabela_aux.length()-1);
				tabela_aux=tabela_aux+"]\n";
				tabela=tabela+tabela_aux;
				tabela=tabela+"}";
			}else {
				tabela=tabela+"],\n";
				tabela_aux=tabela_aux+"]\n";
				tabela=tabela+tabela_aux;
				tabela=tabela+"}";
			}
			
			
			
			
			resp.setContentType("application/json");  
  		    resp.setCharacterEncoding("UTF-8"); 
  		    PrintWriter out = resp.getWriter();
  		    //tabela="{\"item_po_sem_ticket\":"+po_item_sem_ticket+",\"item_po_ticket_new\":"+po_item_ticket_new+",\"item_po_ticket_work\":"+po_item_ticket_work+",\"item_po_ticket_done\":"+po_item_ticket_done+"}";
  		    //System.out.println(tabela);
		    out.print(tabela);
		    mongo.fecharConexao();
		    Timestamp time2 = new Timestamp(System.currentTimeMillis());
			System.out.println("MSTP WEB - "+f3.format(time)+" "+p.getEmpresa().getNome_fantasia()+" - "+ p.get_PessoaUsuario()+" Servlet de Dashboard opt - "+ opt +" tempo de execução " + TimeUnit.MILLISECONDS.toSeconds((time2.getTime()-time.getTime())) +" segundos");
		}
				
			
		}catch (Exception e) {
			//conn.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
