package servlets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailException;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.*;
import classes.Conexao;
import classes.Semail;
/**
 * Servlet implementation class Rel_auto
 */
@WebServlet("/Rel_auto")
public class Rel_auto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ScheduledExecutorService scheduler;		
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rel_auto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		iniciar_servico_relatorio();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public void iniciar_servico_relatorio() {
		System.out.println("MSTP - Serviço de relatório iniciado...");
	    scheduler = Executors.newSingleThreadScheduledExecutor();
	    scheduler.scheduleAtFixedRate(new enviar_relatorio(),0, 1, TimeUnit.HOURS);
	    
	}
	
	public class enviar_relatorio extends TimerTask {
	    public void run() {
	    	Conexao con = new Conexao();
	    	ResultSet rs ;
	    	ResultSet rs2 ;
	    	String query;
	    	String dados="";
	    	try {
	    	rs=con.Consulta("select * from relatorios");
	    	if(rs.next()){
	    		//System.out.println("achou relatorio");
	    		String dt_range_inicio;
	    		String dt_range_fim;
	    		//System.out.println("Enviando Email...");
		    	Semail email;
				email = new Semail();
				rs.beforeFirst();
				while(rs.next()){
					if(rs.getString("relatorio_tipo").equals("Planejado & Realizado - range")){
						dt_range_inicio=rs.getString("relatorio_dt_range_inicio");
						dt_range_fim=rs.getString("relatorio_dt_range_fim");
						query="select t1.*,t2.total_done from (SELECT milestone,count(dt_inicio) as total_plan FROM rollout "
								+ "where str_to_date(dt_inicio,'%d/%m/%Y') >= str_to_date('"+dt_range_inicio+"','%d/%m/%Y') "
								+ "and str_to_date(dt_inicio,'%d/%m/%Y') <= str_to_date('"+dt_range_fim+"','%d/%m/%Y') and tipo_campo='Milestone'  group by milestone) as t1 "
								+ "left join (SELECT milestone,count(dt_fim) as total_done FROM rollout where "
								+ "str_to_date(dt_fim,'%d/%m/%Y') >= str_to_date('"+dt_range_inicio+"','%d/%m/%Y') "
								+ "and str_to_date(dt_fim,'%d/%m/%Y') <= str_to_date('"+dt_range_fim+"','%d/%m/%Y') and tipo_campo='Milestone' group by milestone) as t2 "
								+ "on t1.milestone=t2.milestone";
						//System.out.println(query);
						rs2=con.Consulta(query);
						if(rs2.next()){
							dados="<htm><meta charset=\"UTF-8\"><style>"+"\n"+" .datagrid table { border-collapse: collapse; text-align: left; width: 100%; } .datagrid {font: normal 12px/150% Arial, Helvetica, sans-serif; background: #fff; overflow: hidden; border: 1px solid #8C8C8C; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; }.datagrid table td, .datagrid table th { padding: 3px 10px; }.datagrid table thead th {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #8C8C8C), color-stop(1, #7D7D7D) );background:-moz-linear-gradient( center top, #8C8C8C 5%, #7D7D7D 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#8C8C8C', endColorstr='#7D7D7D');background-color:#8C8C8C; color:#FFFFFF; font-size: 15px; font-weight: bold; border-left: 1px solid #A3A3A3; } .datagrid table thead th:first-child { border: none; }.datagrid table tbody td { color: #7D7D7D; border-left: 1px solid #DBDBDB;font-size: 16px;border-bottom: 1px solid #E1EEF4;font-weight: normal; }.datagrid table tbody .alt td { background: #EBEBEB; color: #7D7D7D; }.datagrid table tbody td:first-child { border-left: none; }.datagrid table tbody tr:last-child td { border-bottom: none; }"+"\n"+"</style>"+"\n";
							
							rs2.beforeFirst();
							dados=dados+"<div style=\"display:table;margin: 0 auto;text-align: center;\"><div><h1>Relat&oacute;rio de atividades por periodo</h1><br><br></div><div><h2>Per&iacute;odo "+dt_range_inicio+" - "+dt_range_fim+"</h2><br><br></div><div class=\"datagrid\">"+"\n"+"<table>"+"\n"+"<thead>"+"\n"+"<tr><th>Atividade</th><th>Total Planejado</th><th>Total Realizado</th></tr></thead>"+"\n" +"<tbody>"+"\n";
							while(rs2.next()){
							
							dados=dados+"<tr><td>"+rs2.getString(1)+"</td><td>"+rs2.getString(2)+"</td><td>"+rs2.getString(3)+"</td></tr>"+"\n";;
							if(rs2.next())
							dados=dados+"<tr class=\"alt\"><td>"+rs2.getString(1)+"</td><td>"+rs2.getString(2)+"</td><td>"+rs2.getString(3)+"</td></tr>"+"\n";;
							}
							
							dados=dados+"</tbody>"+"\n";
							dados=dados+"</table>"+"\n" + "</div>"+"\n"+"</div></html>"+"\n";
							//System.out.println(dados);
							//email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com", "G I S - relatório automatico","Prezado, \n Email automatico de relatório.\n"+ dados);
							email.enviaEmailHtml("fabio.albuquerque@inovare-ti.com", "G I S - relatório automático", dados);
						}
					}else if(rs.getString("relatorio_tipo").equals("Planejado & Realizado - diário")){
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date d= new Date();
						dt_range_inicio=sdf.format(d);
						//dt_range_fim=rs.getString("relatorio_dt_range_fim");
						query="select t1.*,t2.total_done from (SELECT milestone,count(dt_inicio) as total_plan FROM rollout "
								+ "where str_to_date(dt_inicio,'%d/%m/%Y') = str_to_date('"+dt_range_inicio+"','%d/%m/%Y') "
								+ "and tipo_campo='Milestone'  group by milestone) as t1 "
								+ "left join (SELECT milestone,count(dt_fim) as total_done FROM rollout where "
								+ "str_to_date(dt_inicio,'%d/%m/%Y') = str_to_date('"+dt_range_inicio+"','%d/%m/%Y') and dt_fim <> '' and tipo_campo='Milestone' group by milestone) as t2 "
								+ "on t1.milestone=t2.milestone";
						//System.out.println(query);
						rs2=con.Consulta(query);
						if(rs2.next()){
							dados="<htm><meta charset=\"UTF-8\"><style>"+"\n"+" .datagrid table { border-collapse: collapse; text-align: left; width: 100%; } .datagrid {font: normal 12px/150% Arial, Helvetica, sans-serif; background: #fff; overflow: hidden; border: 1px solid #8C8C8C; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; }.datagrid table td, .datagrid table th { padding: 3px 10px; }.datagrid table thead th {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #8C8C8C), color-stop(1, #7D7D7D) );background:-moz-linear-gradient( center top, #8C8C8C 5%, #7D7D7D 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#8C8C8C', endColorstr='#7D7D7D');background-color:#8C8C8C; color:#FFFFFF; font-size: 15px; font-weight: bold; border-left: 1px solid #A3A3A3; } .datagrid table thead th:first-child { border: none; }.datagrid table tbody td { color: #7D7D7D; border-left: 1px solid #DBDBDB;font-size: 16px;border-bottom: 1px solid #E1EEF4;font-weight: normal; }.datagrid table tbody .alt td { background: #EBEBEB; color: #7D7D7D; }.datagrid table tbody td:first-child { border-left: none; }.datagrid table tbody tr:last-child td { border-bottom: none; }"+"\n"+"</style>"+"\n";
							
							rs2.beforeFirst();
							dados=dados+"<div style=\"display:table;margin: 0 auto;text-align: center;\"><div><h1>Relat&oacute;rio di&aacute;rio de atividades</h1><br><br></div><div><h2>Per&iacute;odo Hoje</h2><br><br></div><div class=\"datagrid\">"+"\n"+"<table>"+"\n"+"<thead>"+"\n"+"<tr><th>Atividade</th><th>Total Planejado</th><th>Total Realizado</th></tr></thead>"+"\n" +"<tbody>"+"\n";
							while(rs2.next()){
							
							dados=dados+"<tr><td>"+rs2.getString(1)+"</td><td>"+rs2.getString(2)+"</td><td>"+rs2.getString(3)+"</td></tr>"+"\n";;
							if(rs2.next())
							dados=dados+"<tr class=\"alt\"><td>"+rs2.getString(1)+"</td><td>"+rs2.getString(2)+"</td><td>"+rs2.getString(3)+"</td></tr>"+"\n";;
							}
							
							dados=dados+"</tbody>"+"\n";
							dados=dados+"</table>"+"\n" + "</div>"+"\n"+"</div></html>"+"\n";
							//System.out.println(dados);
							//email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com", "G I S - relatório automatico","Prezado, \n Email automatico de relatório.\n"+ dados);
							email.enviaEmailHtml("fabio.albuquerque@inovare-ti.com", "MSTP - relatório automático", dados);
						}
					}
				}
				
				
				
				
				
		    	
		    	
	    	}
	    	con.fecharConexao();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				con.fecharConexao();
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				con.fecharConexao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				con.fecharConexao();
			}
	    }
	}

}
