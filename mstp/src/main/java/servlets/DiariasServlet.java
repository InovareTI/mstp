package servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;

import classes.ConexaoMongo;
import classes.Despesa;
import classes.EspelhoDiaria;
import classes.Pessoa;
import classes.Semail;

/**
 * Servlet implementation class DiariasServlet
 */
@WebServlet("/DiariasServlet")
public class DiariasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DiariasServlet() {
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
		gerenciamento_diarias(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		gerenciamento_diarias(request, response);
	}
	public void gerenciamento_diarias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EspelhoDiaria espelho= new EspelhoDiaria();
	
		String opt="";
		String param1="";
		String param2="";
		ConexaoMongo mongo = new ConexaoMongo();
		opt=request.getParameter("opt");
		HttpSession session = request.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null) {
			response.sendRedirect("./mstp_login.html");
			return;
		}
		if(opt.equals("1")) {
			try {
				String param3="";
				param1=request.getParameter("func");
				param2=request.getParameter("week");
				param3=request.getParameter("saldo");
				espelho.setFunc_espelho(param1);
				espelho.setSemana_espelho(param2);
				espelho.setSaldo_espelho(param3);
				espelho.setEmpresa(p.getEmpresa().getEmpresa_id());
				espelho.SalvaEspelho(mongo);
			}catch(Exception e) {
				try {
					Semail email = new Semail();
					email.enviaEmailSimples("fabio.albuquerque@inovare-ti.com","MSTP WEB - Erro Servlet Diaria", "Erro servlet diaria opt 1");
				} catch (EmailException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}if(opt.equals("2")) {
			Despesa despesa = new Despesa();
			Calendar hoje = Calendar.getInstance();
			despesa.setAno_despesa(hoje.get(Calendar.YEAR));
			despesa.setDia_despesa(hoje.get(Calendar.DAY_OF_MONTH));
			despesa.setMes_despesa(hoje.get(Calendar.MONTH)+1);
			despesa.setValor_despesa(request.getParameter("valor"));
			despesa.setSite_despesa(request.getParameter("valor"));
			despesa.setDescricao_despesa(request.getParameter("desc"));
		}
		
		
	}
}
