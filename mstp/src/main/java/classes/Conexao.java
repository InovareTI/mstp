package classes;



import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * Created on 19/12/2014
 */


import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;   





/**
 * @author Fabio Albuquerque
 */
public class Conexao
{
	
	private Connection connection;
	//private PreparedStatement stmtBatchUpdateAtributo;
	//private PreparedStatement stmtBatchUpdateMilestone;
	//private PreparedStatement stmtBatchInsertMilestone;
	//private PreparedStatement stmtBatchInsertAtributo;
	
	public Conexao() {
		connection =null;
		try	{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			String databaseURL = "jdbc:mysql://10.100.20.30/mstpDB?user=mstpwebDB&password=mafXuv-h1vbyd-Kynfy8";
			//String databaseURL = "jdbc:mysql://127.0.0.1/mstpDB?user=root&password=r2d2c3p0";
			connection = DriverManager.getConnection(databaseURL);
			connection.setAutoCommit(false);
			//this.connection=c;
			} 
			
			catch (SQLException e) {
				//Não estã conseguindo se conectar ao banco 
				System.out.println("Não foi possivel abrir a conexao. Motivo: "+e.getCause()+"-"+e.getErrorCode()+"-"+e.getMessage());
				e.printStackTrace(); 
			}
			catch(Exception e1){
				//Não estã conseguindo se conectar ao banco 
				System.out.println("Não foi possivel abrir a conexao. Motivo: "+e1.getCause()+"-"+e1.getMessage());
				e1.printStackTrace();
			}
		
	}
	
	
	
	
	
	
	
	public Connection getConnection() {
		return this.connection; 
		//connection;
	}
	
	public boolean Inserir_simples (String query)
	{
		try{
			
			//this.connection.setAutoCommit(false); 
		   //   PreparedStatement pstmt;
		        // preparando um statement
		   //    pstmt = this.connection.prepareStatement(query);
		        //executando a consulta
		   ///   pstmt.executeQuery();
		        // fechando o statement
		   //   this.connection.commit();
			// pstmt.close();
		
			 this.connection.setAutoCommit(false);
			
			 Statement stmt = this.connection.createStatement();
			
			 stmt.executeUpdate(query);
			
			 this.connection.commit();
			stmt.close();
			
			//System.out.println("Inserção OK.");
			return true;
			
		}
		catch (SQLException e){
			System.out.println (query);
			System.out.println("Não foi possivel inserir dados. Motivo:"+e.getCause()+e.getMessage()+e.getErrorCode());
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean Update_simples (String query)
	{
		try{
			
			this.connection.setAutoCommit(false);
			Statement stmt;
			stmt=null;
			stmt= this.connection.createStatement();
			
			stmt.executeUpdate(query);
			
			this.connection.commit();
			
			//this.connection.commit();
			
			stmt.close();
			stmt=null;
			//System.out.println("Inserção OK.");
			return true;
			
		}
		catch (SQLException e){
			System.out.println (query);
			System.out.println("Não foi possivel inserir dados. Motivo:"+e.getCause()+e.getMessage()+e.getErrorCode());
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void InserirImagem (String query)
	{
		try{
			PreparedStatement sqlimagem = this.connection.prepareStatement("INSERT INTO imagem (imagem) VALUES (LOAD_FILE(?));");   
		      sqlimagem.setString(1,"C:\\Imagem\\televisao.jpg");   
		      int i = sqlimagem.executeUpdate();   
		      if (i == 1){   
		         System.out.println("Gravou imagem no BD agora ã carregar a imagem");   
		         PreparedStatement sql = this.connection.prepareStatement("SELECT * FROM teste_de_imagem;");   
		         ResultSet resultado = sql.executeQuery();   
		         resultado.last();   
		            Blob blob = resultado.getBlob("imagem");   
		        //    ImageIcon imageIcon = new ImageIcon(blob.getBytes(1, (int)blob.length()));    
		      //   JFrame janela = new JFrame();   
		       //     JLabel lblimagem = new JLabel(imageIcon);   
		       //     janela.getContentPane().add(lblimagem);   
		      //      janela.pack();   
		       //     janela.setVisible(true);   

		      }
			
			
			Statement stmt = this.connection.createStatement();
			stmt.execute(query);
			stmt.close();
			System.out.println("Inserção de imagem OK.");
		}
		catch (SQLException e){
			System.out.println (query);
			System.out.println("Não foi possivel inserir dados. Motivo:"+e.getCause());
			e.printStackTrace();
		}
		
	}
	
	public Boolean Alterar (String query)
	{
		
		try{
			Statement stmt = this.connection.createStatement();
			this.connection.setAutoCommit(false);
				stmt.executeUpdate(query);
			this.connection.commit();
			
			System.out.println("Update realizado. Linhas afetadas:"+stmt.getUpdateCount());
			if(stmt.getUpdateCount()>0){
				stmt.close();
				return true;
			}
				stmt.close();
				return false;
			
			
		}
		catch (SQLException e){
		    
		    try{
			    this.connection.rollback(); 
			   // return false;
			}
		    catch(SQLException x){
		        System.out.println(x.getMessage());
				System.out.println("Não foi possivel reverter a ação. ");
				x.printStackTrace();
				return false;
		    }
		    
		    System.out.println (query);
		    System.out.println(e.getMessage());
			System.out.println("Não foi possivel alterar dados. ");
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	
	public boolean Excluir (String query)
	{
		try{
			Statement stmt = this.connection.createStatement();
			stmt.execute(query);
			this.connection.commit();
			stmt.close();
			System.out.println("Exclusão OK.");
			return true;
		}
		catch (SQLException e){
			System.out.println (query);
			System.out.println("Não foi possivel excluir dados. Motivo:"+e.getCause());
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void fecharConexao()
	{
		try{
			connection.close();
			System.out.println("Conexao encerrada.");
			
		}
		catch (SQLException e)
		{
		    System.out.println("Não foi possivel fechar a conexao. Motivo:"+e.getCause());
			e.printStackTrace();
		}
	}
	
	
	public ResultSet ConsultaLogin(String query,String param1){
		PreparedStatement statement;
		try {
			statement = this.connection.prepareStatement(query);
			statement.setString(1, param1);
			return statement.executeQuery();
		    //statement.getConnection().commit();
		} catch (SQLException e) {
			return null;
			// TODO Auto-generated catch block
			
		}
		 
	}
	public boolean InsereUsuario(String query,List<String> parametros){
		PreparedStatement statement;
		try {
			statement = this.connection.prepareStatement(query);
			for(int indice=0;indice<parametros.size();indice++) {
				statement.setString(indice+1, parametros.get(indice));
			}
			int resultado= statement.executeUpdate();
			if(resultado>0) {
				return true;

			}
			this.connection.commit();
			statement.close();
		    return false;
		} catch (SQLException e) {
			return false;
			// TODO Auto-generated catch block
			
		}
		 
	}
	public ResultSet ConsultaLogin(String query,String param1, String param2){
		PreparedStatement statement;
		try {
			statement = this.connection.prepareStatement(query);
			statement.setString(1, param1);
			statement.setString(2, param2);
			statement.setString(3, param2);
			return statement.executeQuery();
		    //statement.getConnection().commit();
		} catch (SQLException e) {
			return null;
			// TODO Auto-generated catch block
			
		}
		 
	}
	
	public ResultSet Consulta(String query){
	    
	    
	    try{
	    	ResultSet rs_funcao;
	        Statement stm = this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
	        
	        rs_funcao = stm.executeQuery(query);
	        //System.out.println("Estou aqui3");
	        //stm.close();
	        //System.out.println("Estou aqui4");
	        return rs_funcao;
	        
	    }
	    catch(SQLException e){
	        System.out.println("Não foi possivel executar a consulta. Motivo:"+e.getCause());
	        e.printStackTrace();
	        
			return null;
	    }
	    
	}
	protected void finalize(){
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

