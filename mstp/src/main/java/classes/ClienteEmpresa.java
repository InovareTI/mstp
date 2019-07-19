package classes;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteEmpresa implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String cnpj;
	
	public ClienteEmpresa() {
	}

	public ClienteEmpresa(Integer id, String nome, String cnpj) {
		super();
		this.id = id;
		this.nome = nome;
		this.cnpj = cnpj;
	}

	public Integer getId() {
		return id;
	}

	public Boolean setClienteEmpresaById(Conexao mysql,Integer id) {
		ResultSet rs;
		
		rs=mysql.Consulta("select * from customer_table where customer_id="+id);
		try {
			if(rs.next()) {
				this.id=rs.getInt("customer_id");
				this.nome=rs.getString("customer_name");
				this.cnpj=rs.getString("customer_cnpj");
				return true;
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
		ClienteEmpresa other = (ClienteEmpresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
