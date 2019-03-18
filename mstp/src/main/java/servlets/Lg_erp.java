package servlets;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import classes.Conexao;
import classes.ConexaoMongo;
import classes.Pessoa;

/**
 * Servlet implementation class Lg_erp
 */
@WebServlet("/Lg_erp")
public class Lg_erp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger root = (Logger) LoggerFactory
	        .getLogger(Logger.ROOT_LOGGER_NAME);
	static {
	    root.setLevel(Level.OFF);
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Lg_erp() {
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
		run_login(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		run_login(request,response);
	}
	
public void run_login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Conexao con = new Conexao();
		
		//System.out.println("conexao com bd ok");
		//ConexaoMongo c = new ConexaoMongo();
       
        MessageDigest md;
        ResultSet rs ;
        
        //System.out.println("Servlet de Login.");
		try {
			HttpSession session = req.getSession(true);
				md = MessageDigest.getInstance( "SHA-256" );
				md.update( req.getParameter("pwd").getBytes());     
		        BigInteger hash = new BigInteger(1,md.digest() );     
		        String retornaSenha = hash.toString(16);   
	        
	        
	        //System.out.println("senha : "+retornaSenha + "fim da senha.");
        	rs= con.Consulta("select * from usuarios where VALIDADO='Y' and ATIVO='Y' and HASH='"+retornaSenha+"' and (id_usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("user")+"')");
        	
        	if (!rs.first()){
        		System.out.println(" consulta nula");
        		rs.close();
            	con.fecharConexao();
            	resp.sendRedirect("./error.html");
        	}
        	else{
        		
        		Pessoa p = new Pessoa();
        		//HttpSession session = req.getSession();
                session.setAttribute("conexao",con);
                //session.setAttribute("conexaoMongo",c);
                p.set_PessoaUsuario(rs.getString("id_usuario"));
                p.set_PessoaName(rs.getString("nome"));
                p.getEmpresa().define_empresa(con, rs.getString("empresa"));
                p.setCargo(rs.getString("cargo"));
                p.setMatricula(rs.getString("matricula"));
                p.setCtps(rs.getString("ctps"));
                p.setAdmissao(rs.getString("admissao"));
                p.setPis(rs.getString("pis"));
                p.set_PessoaPerfil_nome(rs.getString("perfil"));
                p.set_PessoaUltimoLogin(rs.getString("ultimo_acesso"));
                p.setEmail(rs.getString("email"));
                
                session.setMaxInactiveInterval(14400);
                session.setAttribute("user",req.getParameter("user"));
                p.setPerfil_funcoes(con);
                session.setAttribute("pessoa",p);
                //String query="delete from rollout where update_by='masteradmin' and update_time='2018-10-15'";
				//con.Excluir(query);
                //System.out.println(rs.getString("id_equipe"));
                //session.setAttribute("user_equipe",rs.getString("id_equipe"));
                //session.setAttribute("user_empresa",rs.getString("id_empresa"));
                session.setAttribute("nome_empresa",p.getEmpresa().getNome_fantasia());
                Timestamp time = new Timestamp(System.currentTimeMillis());
                //System.out.println("minha empresa");
                con.Update_simples("UPDATE usuarios SET ultimo_acesso='"+time+"' WHERE (id_usuario='"+req.getParameter("user")+"' or email='"+req.getParameter("user")+"')");
                System.out.println(req.getParameter("user")+" Usuário validado e logado");
                rs.close();
                rs=null;
               resp.sendRedirect("./main.jsp");
        	}
	        
		} catch (NoSuchAlgorithmException e) {
			con.fecharConexao();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (SQLException sqle) {
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
