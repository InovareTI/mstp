package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class KPI_diaJob implements org.quartz.Job{
public KPI_diaJob() {
		
	}
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Conexao conn= new Conexao();
		//JobKey jobKey = context.getJobDetail().getKey();
		ResultSet rs ;
		String query="";
		double total_user=0;
		double total_user_entrada=0;
		double total_user_ii=0;
		double total_user_fi=0;
		double total_user_saida=0;
		double total_user_folga=0;
		double total_user_comp=0;
		String dados_tabela="";
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat twoDForm = (DecimalFormat)nf;
		twoDForm.applyPattern("#0.00");
		Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		Locale.setDefault(locale_ptBR);
		DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		//Timestamp time = new Timestamp(System.currentTimeMillis());
		Calendar d = Calendar.getInstance();
		  try {
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
		
		dados_tabela=dados_tabela+"insert into kpi_diario (dia,Entrada,Inicio_Intervalo,Fim_Intervalo,Saida,mes,total_user,fim_d_semana) values (";
		dados_tabela=dados_tabela+"'"+f2.format(d.getTime())+"',";
		//dados_tabela=dados_tabela+"[\""+f2.format(d.getTime())+"\",\""+total_user+"\",";
		query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Entrada'";
		rs=conn.Consulta(query);
		if(rs.next()) {
			//System.out.println("Total de Usuários com Entrada:"+rs.getDouble(1));
			total_user_entrada=rs.getDouble(1);
			dados_tabela=dados_tabela+"'"+twoDForm.format(((total_user_entrada/total_user) * 100))+"%',";
		}else {
			dados_tabela=dados_tabela+"'0.00%',";
		}
		query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Inicio_intervalo'";
		rs=conn.Consulta(query);
		if(rs.next()) {
			//System.out.println("Total de Usuários com Inicio_intervalo:"+rs.getDouble(1));
			total_user_ii=rs.getDouble(1);
			dados_tabela=dados_tabela+"'"+twoDForm.format(((total_user_ii/total_user) * 100))+"%',";
		}else {
			dados_tabela=dados_tabela+"'0.00%',";
		}
		query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Fim_intervalo'";
		rs=conn.Consulta(query);
		if(rs.next()) {
			//System.out.println("Total de Usuários com Fim_intervalo:"+rs.getDouble(1));
			total_user_fi=rs.getDouble(1);
			dados_tabela=dados_tabela+"'"+twoDForm.format(((total_user_fi/total_user) * 100))+"%',";
		}else {
			dados_tabela=dados_tabela+"'0.00%',";
		}
		query="select count(distinct usuario) from registros where data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Saída'";
		rs=conn.Consulta(query);
		if(rs.next()) {
			//System.out.println("Total de Usuários com Saida:"+rs.getDouble(1));
			total_user_saida=rs.getDouble(1);
			dados_tabela=dados_tabela+"'"+twoDForm.format(((total_user_saida/total_user) * 100))+"%',";
		}else {
			dados_tabela=dados_tabela+"'0.00%',";
		}
		dados_tabela=dados_tabela+"'"+(d.get(Calendar.MONTH)+1)+"',"+total_user;
		if(d.get(Calendar.DAY_OF_WEEK)==1 || d.get(Calendar.DAY_OF_WEEK)==7 ) {
			dados_tabela=dados_tabela+",'Y')";
		}else {
			dados_tabela=dados_tabela+",'N')";
		}
		conn.Inserir_simples(dados_tabela);
		conn.fecharConexao();
		  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				conn.fecharConexao();
			}	
	}
}
