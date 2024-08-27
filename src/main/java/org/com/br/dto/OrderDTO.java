/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package org.com.br.dto;

import java.math.BigDecimal;

/**
 *
 * @author andre
 */
public record OrderDTO(int id, BigDecimal total,String created_at, String dt_cadastro, String dt_termino, String dt_cancelamento ) {

}
