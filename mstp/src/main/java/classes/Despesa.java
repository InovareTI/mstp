package classes;

import java.io.Serializable;

public class Despesa implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Integer id_despesa;
	private Integer dia_despesa;
	private Integer mes_despesa;
	private Integer ano_despesa;
	private String data_despesa;
	private String semana_despesa;
	private Double valor_despesa;
	private String status_pagamento;
	private String site_despesa;
	private String descricao_despesa;
	private String cod_espelho;
	
	public Despesa() {
		
	}

	public Despesa(Integer id_despesa, Integer dia_despesa, Integer mes_despesa, Integer ano_despesa, String data_despesa,
			String semana_despesa, Double valor_despesa, String status_pagamento, String site_despesa,
			String descricao_despesa,String cod_espelho) {
		super();
		this.id_despesa = id_despesa;
		this.dia_despesa = dia_despesa;
		this.mes_despesa = mes_despesa;
		this.ano_despesa = ano_despesa;
		this.data_despesa = data_despesa;
		this.semana_despesa = semana_despesa;
		this.valor_despesa = valor_despesa;
		this.status_pagamento = status_pagamento;
		this.site_despesa = site_despesa;
		this.descricao_despesa = descricao_despesa;
		this.setCod_espelho(cod_espelho);
	}

	public Integer getId_despesa() {
		return id_despesa;
	}

	public void setId_despesa(Integer id_despesa) {
		this.id_despesa = id_despesa;
	}

	public Integer getDia_despesa() {
		return dia_despesa;
	}

	public void setDia_despesa(Integer dia_despesa) {
		this.dia_despesa = dia_despesa;
	}

	public Integer getMes_despesa() {
		return mes_despesa;
	}

	public void setMes_despesa(Integer mes_despesa) {
		this.mes_despesa = mes_despesa;
	}

	public Integer getAno_despesa() {
		return ano_despesa;
	}

	public void setAno_despesa(Integer ano_despesa) {
		this.ano_despesa = ano_despesa;
	}

	public String getData_despesa() {
		return data_despesa;
	}

	public void setData_despesa(String data_despesa) {
		this.data_despesa = data_despesa;
	}

	public String getSemana_despesa() {
		return semana_despesa;
	}

	public void setSemana_despesa(String semana_despesa) {
		this.semana_despesa = semana_despesa;
	}

	public Double getValor_despesa() {
		return valor_despesa;
	}

	public void setValor_despesa(String valor_despesa) {
		this.valor_despesa = Double.parseDouble(valor_despesa.replace("R$", "").replaceAll(".", "").replaceAll(",", "."));
	}

	public String getStatus_pagamento() {
		return status_pagamento;
	}

	public void setStatus_pagamento(String status_pagamento) {
		this.status_pagamento = status_pagamento;
	}

	public String getSite_despesa() {
		return site_despesa;
	}

	public void setSite_despesa(String site_despesa) {
		this.site_despesa = site_despesa;
	}

	public String getDescricao_despesa() {
		return descricao_despesa;
	}

	public void setDescricao_despesa(String descricao_despesa) {
		this.descricao_despesa = descricao_despesa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_despesa == null) ? 0 : id_despesa.hashCode());
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
		Despesa other = (Despesa) obj;
		if (id_despesa == null) {
			if (other.id_despesa != null)
				return false;
		} else if (!id_despesa.equals(other.id_despesa))
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
