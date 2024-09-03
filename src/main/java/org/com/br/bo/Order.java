package org.com.br.bo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "`order`")
public class Order implements Serializable {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "  Atencao!! e necessario estar logado!")
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")	
	private Login user;
	
	@NotNull(message = " O total nao pode ser 0!")
	@Min(value = 1, message = "O total deve ser maior que 0!!")
	private BigDecimal total;
	
	private String created_at;
	private String updated_at;
	private String dt_cadastro;	
	private String dt_termino;		
	private String dt_cancelamento;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Login getUser() {
		return user;
	}
	public void setUser(Login user) {
		this.user = user;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public void setTotal(double total) {
		this.total = BigDecimal.valueOf(total);
	}

	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getDt_cadastro() {
		return dt_cadastro;
	}
	public void setDt_cadastro(String dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}
	public String getDt_termino() {
		return dt_termino;
	}
	public void setDt_termino(String dt_termino) {
		this.dt_termino = dt_termino;
	}
	public String getDt_cancelamento() {
		return dt_cancelamento;
	}
	public void setDt_cancelamento(String dt_cancelamento) {
		this.dt_cancelamento = dt_cancelamento;
	}
	
	public Order geraValoresDefault() {
		
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date1 = simpleDateFormat.format(new Date());
		created_at = date1;
		updated_at = date1;
		dt_cadastro = date1;
		
		return this;
	}	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return created_at + " " + dt_cadastro + " " + dt_cancelamento + " " + dt_termino + " " + total + " " + updated_at + " " + user;
	}
	
}
