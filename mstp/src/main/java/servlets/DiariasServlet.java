package servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.ConexaoMongo;
import classes.EspelhoDiaria;
import classes.Pessoa;

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
		ConexaoMongo cm = new ConexaoMongo();
		opt=request.getParameter("opt");
		HttpSession session = request.getSession(true);
		Pessoa p = (Pessoa) session.getAttribute("pessoa");
		if(p==null) {
			response.sendRedirect("./mstp_login.html");
			return;
		}
		if(opt.equals("1")) {
			String param3="";
			param1=request.getParameter("func");
			param2=request.getParameter("week");
			param3=request.getParameter("saldo");
			espelho.setFunc_espelho(param1);
			espelho.setSemana_espelho(param2);
			espelho.setSaldo_espelho(param3);
			espelho.setEmpresa(p.getEmpresa().getEmpresa_id());
			espelho.SalvaEspelho(cm);
			
		}
		
		
	}
}
