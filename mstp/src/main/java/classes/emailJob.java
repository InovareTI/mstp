package classes;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.mail.EmailException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;




public class emailJob implements org.quartz.Job{
	

	      public emailJob() {
	      }

	      public void execute(JobExecutionContext context) throws JobExecutionException {
	          //System.out.println("Hello World!  MyJob is executing.");
	    	  
	    		    JobKey jobKey = context.getJobDetail().getKey();
	    		    
	    		
	          Conexao con= new Conexao();
			  ResultSet rs,rs2 ;
			  String query="";
			  String entrada_status;
			  String saida_status;
			  
			  Locale locale_ptBR = new Locale( "pt" , "BR" ); 
			  Locale.setDefault(locale_ptBR);
			  Calendar d = Calendar.getInstance();
			  DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
			  DateFormat f3 = DateFormat.getDateTimeInstance();
			  System.out.println("Chave do JOB - " + jobKey + " executando em " + f3.format(d.getTime()));
			  System.out.println("Chave do TRIGGER - " + context.getTrigger().getKey() + " executando em " + f3.format(d.getTime()));
			  
			  d.add(Calendar.DAY_OF_MONTH, -1);
			  System.out.println("Executando Alertas de ponto para data: "+f2.format(d.getTime()));
			  try {
			  rs=con.Consulta("select distinct id_usuario,email,nome from usuarios where email<>'' and validado='Y' and ativo='Y'");
			  
				  
				if(rs.next()) {
					rs.beforeFirst();
					Semail email= new Semail();
					while(rs.next()) {
						query="SELECT * FROM registros where usuario='"+rs.getString(1)+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Entrada' order by datetime_servlet asc limit 1";
						rs2=con.Consulta(query);
						if(rs2.next()) {
							entrada_status="ok";
						}else {
							entrada_status="nok";
						}
						query="SELECT * FROM registros where usuario='"+rs.getString(1)+"' and data_dia='"+f2.format(d.getTime())+"' and tipo_registro='Saída' order by datetime_servlet desc limit 1";
						rs2=con.Consulta(query);
						if(rs2.next()) {
							saida_status="ok";
						}else {
							saida_status="nok";
						}
						if(entrada_status.equals("nok") || saida_status.equals("nok")) {
							
							email.enviaEmailSimples(rs.getString("email"),"MSTP WEB - Inconsistência de Ponto","Prezado "+rs.getString("nome")+", \n \n Voce possui uma inconsistencia de ponto referente a data "+f2.format(d.getTime())+" \n\n\n Acesse www.mstp.com.br (mesmas credenciais do app MSTP Mobile) e vá ao menu \"Relatórios>Folha de Ponto\" para ajustar ou justificar a inconsistência de ponto. \n\n\n\n\n EMAIL AUTOMÁTICO ENVIADO POR SISTEMA - NÃO RESPONDER! \n\n\n MSTP - Managed Services Telecom Platform");
						}else {
							System.out.println("Sem inconsistencia de ponto para "+rs.getString(1));
						}
					}
				  }
				con.fecharConexao();
				System.out.println("Alertas de ponto para data:"+f2.format(d.getTime()) +" encerrados");
			} catch (SQLException | EmailException | MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				con.fecharConexao();
			}
			  
			  
			  
	      }
	  

}
