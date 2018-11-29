package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.KPI_diaJob;
import classes.RegistroAlmocoJob;
import classes.RegistroFImAlmocoJob;
import classes.emailJob;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Servlet implementation class Jobs_Servelet
 */
@WebServlet("/Jobs_Servlet")
public class Jobs_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jobs_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		jobsQuartz();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		jobsQuartz();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		jobsQuartz();
	}
	 public void jobsQuartz()  {
		 try {
			 //Job & Trigger do inicio do almoco
		 JobKey jobKey = new JobKey("RegistroAlmoco", "MSTPJobs");
	     JobDetail job = JobBuilder.newJob(RegistroAlmocoJob.class)
		 .withIdentity(jobKey).build();
	     Trigger trigger = TriggerBuilder
	    			.newTrigger()
	    			.withIdentity("RegistroAlmoco", "MSTPJobs")
	    			.withSchedule(
	    				CronScheduleBuilder.cronSchedule("0 1 13 * * ? *"))
	    			.build();
	       //Job & Trigger do fim do almoco
	     JobKey jobKey_fim_intervalo = new JobKey("RegistroFimAlmoco", "MSTPJobs");
	     JobDetail job_fim_intervalo = JobBuilder.newJob(RegistroFImAlmocoJob.class)
		 .withIdentity(jobKey_fim_intervalo).build();
	     Trigger trigger_fim_intervalo = TriggerBuilder
	    			.newTrigger()
	    			.withIdentity("RegistroFimAlmoco", "MSTPJobs")
	    			.withSchedule(
	    				CronScheduleBuilder.cronSchedule("0 13 14 * * ? *"))
	    			.build();
	         //Job & Trigger do alerta de inconsistencia 
	     JobKey jobKey_ponto_erro = new JobKey("Ponto_erro", "MSTPJobs");
	     JobDetail job_ponto_erro = JobBuilder.newJob(emailJob.class)
		 .withIdentity(jobKey_ponto_erro).build();
	     Trigger trigger_ponto_erro = TriggerBuilder
	    			.newTrigger()
	    			.withIdentity("Ponto_erro", "MSTPJobs")
	    			.withSchedule(
	    				CronScheduleBuilder.cronSchedule("0 30 18 * * ? *"))
	    			.build();
	        //Job & Trigger do KPI diario
	     JobKey jobKey_kpi_diario = new JobKey("Kpi_diario", "MSTPJobs");
	     JobDetail job_kpi_diario = JobBuilder.newJob(KPI_diaJob.class)
		 .withIdentity(jobKey_kpi_diario).build();
	     Trigger trigger_kpi_diario = TriggerBuilder
	    			.newTrigger()
	    			.withIdentity("Kpi_diario", "MSTPJobs")
	    			.withSchedule(
	    				CronScheduleBuilder.cronSchedule("0 43 23 * * ? *"))
	    			.build();
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			
			scheduler.start();
	    	scheduler.scheduleJob(job, trigger);
	    	scheduler.scheduleJob(job_fim_intervalo, trigger_fim_intervalo);
	    	scheduler.scheduleJob(job_ponto_erro, trigger_ponto_erro);
	    	scheduler.scheduleJob(job_kpi_diario, trigger_kpi_diario);
	    	System.out.println("MSTP: Jobs carregados com sucesso!");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			System.out.println("MSTP: Erro no carregamento de Jobs !");
			e.printStackTrace();
		}
	 }
}
