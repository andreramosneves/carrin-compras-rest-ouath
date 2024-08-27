package org.com.br.bo;


import jakarta.persistence.Column;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
/* Essa entidade apesar de não ser necessário mostra um exemplo simples do Builder */
@Entity(name = "products")
public class Product {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int id;
	
	private String photo;
	private String nm_produto;
	private BigDecimal valor_produto;
        @Column(name = "dt_termino")
	private String dttermino;
	private String created_at;
	private String updated_at;

        private Product(Builder builder) {
            this.nm_produto = builder.name;
            this.valor_produto = builder.valor;
        }

        public Product() {
        }
        
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getNm_produto() {
		return nm_produto;
	}
	public void setNm_produto(String nm_produto) {
		this.nm_produto = nm_produto;
	}
	public BigDecimal getValor_produto() {
		return valor_produto;
	}
	public void setValor_produto(BigDecimal valor_produto) {
		this.valor_produto = valor_produto;
	}
	public String getDt_termino() {
		return dttermino;
	}
	public void setDt_termino(String dt_termino) {
		this.dttermino = dt_termino;
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
	public void geraValoresDefault() {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date1 = simpleDateFormat.format(new Date());
		created_at = date1;
		updated_at = date1;
		
	}
	public Product finalizaProduto() {
		// TODO Auto-generated method stub
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date1 = simpleDateFormat.format(new Date());
		dttermino = date1;
		updated_at = date1;
		
		return this;
	}
		
        public static class Builder {
           private String name;
           private BigDecimal valor;
           
           public Builder(String name, BigDecimal valor) {
               if (name == null || valor == null) {
                   throw new IllegalArgumentException("produto não pode ser nulo");
               }
               this.name = name;
               this.valor = valor;
           }

           public Builder() {
           }

           public Builder withName(String name) {
               this.name = name;
               return this;
           }

           public Builder withValue(BigDecimal valor) {
               this.valor = valor;
               return this;
           }
           public Product build() {
               return new Product(this);
           }

 }           
        
        
}
