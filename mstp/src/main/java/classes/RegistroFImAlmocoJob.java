package classes;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

public class RegistroFImAlmocoJob implements org.quartz.Job{
	public RegistroFImAlmocoJob() {
		
	}
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Conexao con= new Conexao();
		JobKey jobKey = context.getJobDetail().getKey();
		  ResultSet rs,rs2 ;
		  String query="";
		  Locale locale_ptBR = new Locale( "pt" , "BR" ); 
		  Locale.setDefault(locale_ptBR);
		  DateFormat f2 = DateFormat.getDateInstance(DateFormat.MEDIUM, locale_ptBR);
		  Timestamp time = new Timestamp(System.currentTimeMillis());
		  
		  DateFormat f3 = DateFormat.getDateTimeInstance();
		  Calendar d = Calendar.getInstance();
		  int aux_hora=d.get(Calendar.HOUR_OF_DAY);
		  int aux_min=numeroAleatorio(8,15);
		  int auxsec=numeroAleatorio(10,59);
		  //String data_mobile=f2.format(d.getTime())+" 14:" +aux_min+ ":"+auxsec;
		  d.set(Calendar.HOUR_OF_DAY,14);
		  d.set(Calendar.MINUTE,aux_min);
		  d.set(Calendar.SECOND,auxsec);
		  time = new Timestamp(d.getTimeInMillis());
		  System.out.println("Chave do JOB - " + jobKey + " executando em " + f3.format(d.getTime()));
		  System.out.println("Chave do TRIGGER - " + context.getTrigger().getKey() + " executando em " + f3.format(d.getTime()));
		  System.out.println("Executando verificação de marcação de Fim intervalo em: "+f3.format(d.getTime()));
		  try {
		  rs=con.Consulta("select distinct id_usuario,email,nome from usuarios where email<>'' and validado='Y' and ativo='Y'");
		  
			if(rs.next()) {
				  rs.beforeFirst();
				  while(rs.next()) {
					  query="SELECT * FROM registros where usuario='"+rs.getString("id_usuario")+"' and data_dia='"+f2.format(time)+"' order by sys_contador desc limit 1";
					  rs2=con.Consulta(query);
					  if(rs2.next()) {
						  if(rs2.getString("tipo_registro").equals("Inicio_intervalo")) {
							  if(con.Inserir_simples("INSERT INTO registros (id_sistema,empresa,usuario,latitude,longitude,data_dia,distancia,datetime_mobile,datetime_servlet,hora,minutos,tipo_registro,local_registro,tipo_local_registro,site_operadora_registro,mes) VALUES ('1','1','"+rs.getString("id_usuario")+"','0','0','"+f2.format(time)+"',0,'"+f3.format(d.getTime())+"','"+time+"',"+aux_hora+","+aux_min+",'Fim_intervalo','AUTOMÁTICO MSTP','AUTOMÁTICO MSTP','-',"+(d.get(Calendar.MONTH)+1)+")")) {
								  System.out.println("Registro de fim de intervalo inserido para usuário : "+rs.getString("id_usuario"));
							  }
						  }else if(rs2.getString("tipo_registro").equals("Fim_intervalo"))  {
								System.out.println("Registro de fim de intervalo ok para usuário : "+rs.getString("id_usuario"));
							}
						  
					  }
				  }
				  time = new Timestamp(System.currentTimeMillis());
				  System.out.println("Encerramento do registro automático de fim de intervalo em - "+f3.format(d.getTime()));
			  }else {
				  time = new Timestamp(System.currentTimeMillis());
				  System.out.println("Encerramento do registro automático de fim de intervalo em - "+f3.format(d.getTime()));
			  }
			con.fecharConexao();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con.fecharConexao();
		}
	}
	public int numeroAleatorio(int min, int max){

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
