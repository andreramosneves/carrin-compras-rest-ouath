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
public record KartDTO(Integer id,org.com.br.bo.Product produto, BigDecimal quantidade, BigDecimal valor, String dt_termino ) {

}
