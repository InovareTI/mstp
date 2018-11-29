package servlets;

import java.io.IOException;
//import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import classes.Conexao;
import classes.Semail;

/**
 * Servlet implementation class Reg_Servlet
 */
@WebServlet("/Reg_Servlet")
public class Reg_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reg_Servlet() {
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
		run_registro(request,response);
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		run_registro(request,response);
	}
	public void run_registro(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
Conexao con = new Conexao();
		
		
        
        //MessageDigest md;
        ResultSet rs = null ;
        
        System.out.println("Entrou no Servlet de registro do MSTP WEB");
		try {
			if(!req.getParameter("validaCode").equals("")){
				//System.out.println("funcao de validao de usuario");
				rs= con.Consulta("select * from usuarios where CODIGO_VALIDACAO='"+req.getParameter("validaCode")+"'");
	        	//System.out.println("Consultou usuario");
	        	if (rs.first()){
	        		Semail email= new Semail();
	        		con.Update_simples("UPDATE usuarios SET VALIDADO='Y',ATIVO='Y' WHERE CODIGO_VALIDACAO='"+req.getParameter("validaCode")+"'");
	        		email.enviaEmailSimples(rs.getString("email"), "MSTP WEB - Link de Ativação","Prezado, \n \n Sua conta foi ativada com sucesso.\n \n Acesse: www.mstp.com.br");
	        	}
	        	con.fecharConexao();
	        	resp.sendRedirect("/mstp/authcc.html");
			
        	}
        	else{
        		 
                resp.setContentType("application/text");  
	    		resp.setCharacterEncoding("UTF-8"); 
	    		//PrintWriter out = resp.getWriter();
	    		//out.print("Usuário ou email ja Existente. Por favor contate o administrador do sistema para sua empresa!");
	    		resp.getWriter().write("Email ou usuário ja Ativado!"); 
	    		rs.close();
                rs=null;
                con.fecharConexao();
        	}
			
		} catch (SQLException sqle) {
			con.fecharConexao();
        	System.out.println(sqle.getMessage());
            sqle.printStackTrace();
        }catch (Exception e) {
        	con.fecharConexao();
        	System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        
		
	
	}
}
