package servlets;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import classes.RelatorioOperacionalDiario;
import classes.RolloutAtualizaDuracao;
import classes.RolloutDataAtrasada;
import classes.RolloutNaoIniciadas;
import classes.StatusMilestone;
import classes.PreChekToCheckJob;
import classes.RegrasRollout;

/**
 * Servlet implementation class rolloutDTAtrasada
 */
@WebServlet("/rolloutDTAtrasada")
public class rolloutDTAtrasada extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger root = (Logger) LoggerFactory
	        .getLogger(Logger.ROOT_LOGGER_NAME);
	static {
	    root.setLevel(Level.OFF);
	}   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public rolloutDTAtrasada() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		SchedulerFactory sf = new StdSchedulerFactory();
		try {
			
			Scheduler sched = sf.getScheduler();
			JobDetail job = newJob(StatusMilestone.class)
				    .withIdentity("jobStatusMilestone", "group1")
				    
				    .build();
			JobDetail job2 = newJob(RolloutAtualizaDuracao.class)
				    .withIdentity("jobRolloutDuracao", "group1")
				    .build();
			JobDetail job3 = newJob(RolloutNaoIniciadas.class)
				    .withIdentity("jobRolloutNaoInicidas", "group1")
				    .build();
			JobDetail job4 = newJob(RelatorioOperacionalDiario.class)
				    .withIdentity("jobRelatorioOperacionalSimples", "group1")
				    .build();
			JobDetail job5 = newJob(PreChekToCheckJob.class)
				    .withIdentity("JobPreChekToCheckJob", "group2")
				    .build();
			JobDetail job6 = newJob(RegrasRollout.class)
				    .withIdentity("RegrasRolloutJob", "group2")
				    .build();
			CronTrigger trigger = newTrigger()
				    .withIdentity("trigger1", "group1")
				    .withSchedule(cronSchedule("0 0 6 1/1 * ? *"))
				    .build();
			CronTrigger trigger2 = newTrigger()
				    .withIdentity("trigger2", "group1")
				    .withSchedule(cronSchedule("0 0 1 1/1 * ? *"))
				    .build();
			CronTrigger trigger3 = newTrigger()
				    .withIdentity("trigger3", "group1")
				    .withSchedule(cronSchedule("0 30 10 ? * MON,TUE,WED,THU,FRI *"))
				    .build();
			CronTrigger trigger4 = newTrigger()
				    .withIdentity("trigger4", "group1")
				    .withSchedule(cronSchedule("0 30 16 ? * MON,TUE,WED,THU,FRI *"))
				    .build();
			CronTrigger trigger5 = newTrigger()
				    .withIdentity("trigger5", "group2")
				    .withSchedule(cronSchedule("0 0 0/2 ? * * *"))
				    .build();
			CronTrigger trigger6 = newTrigger()
				    .withIdentity("trigger6", "group2")
				    .withSchedule(cronSchedule("0 0 6/2 ? * MON,TUE,WED,THU,FRI,SAT,SUN *"))
				    .build();
			sched.scheduleJob(job, trigger);
			sched.scheduleJob(job2, trigger2);
			//sched.scheduleJob(job3, trigger3);
			//sched.scheduleJob(job4, trigger4);
			//sched.scheduleJob(job5, trigger5);
			sched.scheduleJob(job6, trigger6);
			//System.out.println(sched.getSchedulerInstanceId());
			
			sched.start();
			System.out.println("Scheduler MSTP INICIADO");
			
			//Thread.sleep(60L * 1000L);
			//System.out.println("thread dormindo");
			//sched.shutdown(true);
			//System.out.println("shut down iniciado");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
