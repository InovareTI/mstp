package servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.Conexao;
import classes.Pessoa;
import classes.emailJob;

import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import static org.quartz.CronScheduleBuilder.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

/**
 * Servlet implementation class EmailAlertServlet
 */
@WebServlet("/EmailAlertServlet")
public class EmailAlertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailAlertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
			email_alert(config.getServletContext());
		
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
	
	 public void email_alert(ServletContext context)  {
		 String key = "org.quartz.impl.StdSchedulerFactory.KEY";
		 System.out.println("MSTP - Alertas automático de emails iniciado!");
		 SchedulerFactory schedFact2=(StdSchedulerFactory) context.getAttribute(key);
		 SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		 Scheduler scheduler;
		try {
			scheduler = schedFact.getScheduler();
		    if(!scheduler.checkExists((JobKey.jobKey("EmailAlert.Ponto")))) {
		    	if(!scheduler.checkExists(TriggerKey.triggerKey("EmailAlert.EmailAlert-Trigger"))) {
		    		scheduler.clear();
			Date startTime = DateBuilder.dateOf(18,00,0);
		 JobDetail job = newJob(emailJob.class)
			      .withIdentity("Ponto", "EmailAlert")
			      .build();
		 Trigger trigger = newTrigger()
			      .withIdentity("EmailAlert-Trigger", "EmailAlert")
			      .withSchedule(cronSchedule("0 0 18 * * ?"))
			      .build();
		 scheduler.scheduleJob(job, trigger);
		 scheduler.start();
		 System.out.println("MSTP - Alertas agendado com sucesso!");
		 
		    }
		   }
		 
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
