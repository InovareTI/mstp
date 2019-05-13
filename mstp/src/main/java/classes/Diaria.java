package classes;

import java.io.Serializable;

public class Diaria implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Integer id_diaria;
	private Integer dia_diaria;
	private Integer mes_diaria;
	private Integer ano_diaria;
	private String data_diaria;
	private String semana_diaria;
	private String valor_diaria;
	private String status_pagamento;
	private String site_diaria;
	private String descricao_diaria;
	private String cod_espelho;
	
	public Diaria() {
		
	}

	public Diaria(Integer id_diaria, Integer dia_diaria, Integer mes_diaria, Integer ano_diaria, String data_diaria,
			String semana_diaria, String valor_diaria, String status_pagamento, String site_diaria,
			String descricao_diaria,String cod_espelho) {
		super();
		this.id_diaria = id_diaria;
		this.dia_diaria = dia_diaria;
		this.mes_diaria = mes_diaria;
		this.ano_diaria = ano_diaria;
		this.data_diaria = data_diaria;
		this.semana_diaria = semana_diaria;
		this.valor_diaria = valor_diaria;
		this.status_pagamento = status_pagamento;
		this.site_diaria = site_diaria;
		this.descricao_diaria = descricao_diaria;
		this.setCod_espelho(cod_espelho);
	}

	public Integer getId_diaria() {
		return id_diaria;
	}

	public void setId_diaria(Integer id_diaria) {
		this.id_diaria = id_diaria;
	}

	public Integer getDia_diaria() {
		return dia_diaria;
	}

	public void setDia_diaria(Integer dia_diaria) {
		this.dia_diaria = dia_diaria;
	}

	public Integer getMes_diaria() {
		return mes_diaria;
	}

	public void setMes_diaria(Integer mes_diaria) {
		this.mes_diaria = mes_diaria;
	}

	public Integer getAno_diaria() {
		return ano_diaria;
	}

	public void setAno_diaria(Integer ano_diaria) {
		this.ano_diaria = ano_diaria;
	}

	public String getData_diaria() {
		return data_diaria;
	}

	public void setData_diaria(String data_diaria) {
		this.data_diaria = data_diaria;
	}

	public String getSemana_diaria() {
		return semana_diaria;
	}

	public void setSemana_diaria(String semana_diaria) {
		this.semana_diaria = semana_diaria;
	}

	public String getValor_diaria() {
		return valor_diaria;
	}

	public void setValor_diaria(String valor_diaria) {
		this.valor_diaria = valor_diaria;
	}

	public String getStatus_pagamento() {
		return status_pagamento;
	}

	public void setStatus_pagamento(String status_pagamento) {
		this.status_pagamento = status_pagamento;
	}

	public String getSite_diaria() {
		return site_diaria;
	}

	public void setSite_diaria(String site_diaria) {
		this.site_diaria = site_diaria;
	}

	public String getDescricao_diaria() {
		return descricao_diaria;
	}

	public void setDescricao_diaria(String descricao_diaria) {
		this.descricao_diaria = descricao_diaria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_diaria == null) ? 0 : id_diaria.hashCode());
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
		Diaria other = (Diaria) obj;
		if (id_diaria == null) {
			if (other.id_diaria != null)
				return false;
		} else if (!id_diaria.equals(other.id_diaria))
			return false;
		return true;
	}

	public String getCod_espelho() {
		return cod_espelho;
	}

	public void setCod_espelho(String cod_espelho) {
		this.cod_espelho = cod_espelho;
	}
	
	
	
}
