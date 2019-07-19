package classes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjetoCliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private Integer cliente_id;
	
	public ProjetoCliente() {
	}

	public Boolean setProjetoClienteById(Conexao mysql, Integer id) {
		ResultSet rs;
		
		rs=mysql.Consulta("select * from project_table where project_id="+id);
		try {
			if(rs.next()) {
				this.id=rs.getInt("project_id");
				this.nome=rs.getString("project_name");
				this.cliente_id=rs.getInt("customer_id");
				return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjetoCliente other = (ProjetoCliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public ProjetoCliente(Integer id, String nome,Integer cliente_id) {
		super();
		this.id = id;
		this.nome = nome;
		this.setCliente_id(cliente_id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Integer cliente_id) {
		this.cliente_id = cliente_id;
	}
	
	
}
