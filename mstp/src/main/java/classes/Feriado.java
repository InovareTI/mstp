package classes;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Feriado {
	private String data;
	private String nome;
	private String estado;
	
	public Feriado() {
		data="";
		nome="";
		estado="";
	}
	
	public Boolean verifica_feriado(String data,String estado_usuario, Conexao c,int empresa) {
		ResultSet rs;
		rs=c.Consulta("select * from feriados where dt_inicio='"+data+"' and empresa="+empresa);
		try {
			if(rs.next()) {
				if(rs.getString("nacional").equals("Y")) {
					return true;
				}else {
					rs.beforeFirst();
					//System.out.println("iniciando pesquisa para estado "+ estado_usuario + " na data "+data);
					while(rs.next()) {
						if(rs.getString("estado").equals(estado_usuario)) {
							return true;
						}
					}
					return false;
				}
				
				
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
