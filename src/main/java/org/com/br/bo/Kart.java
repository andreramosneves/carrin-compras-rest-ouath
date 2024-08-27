package org.com.br.bo;

import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity(name = "kart")
public class Kart {

	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")	
	private Product product;
	
	@OneToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")	
	private Order order;

	@NotNull(message = "  Atenção!! É necessário estar logado!")
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")	
	private Login user;
	
	@NotNull(message = " A quantidade nao pode ser 0!")
	@Min(value = 1, message = "A quantidade deve ser maior que 0!!")
	private BigDecimal quantidade;

        @NotNull(message = " O total nao pode ser 0!")
	@Min(value = 1, message = "O total deve ser maior que 0!!")
	private BigDecimal valor_produto;
	private String created_at;
	private String updated_at;
        
        @Column(name = "dt_termino")
	private String dttermino;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Login getUser() {
		return user;
	}
	public void setUser(Login user) {
		this.user = user;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValor_produto() {
		return valor_produto;
	}
	public void setValor_produto(BigDecimal valor_produto) {
		this.valor_produto = valor_produto;
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
	public String getDt_termino() {
		return dttermino;
	}
	public void setDt_termino(String dt_termino) {
		this.dttermino = dt_termino;
	}
	public Kart geraValoresDefault() {
		
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date1 = simpleDateFormat.format(new Date());
		created_at = date1;
		updated_at = date1;
		
		return this;
	}
	public Kart finalizaKart() {
		// TODO Auto-generated method stub
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date1 = simpleDateFormat.format(new Date());
		dttermino = date1;
		updated_at = date1;
		
		return this;
	}        
        
	
	
	
	
}
