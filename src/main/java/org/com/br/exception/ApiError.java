/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package org.com.br.exception;

import java.time.LocalDateTime;

/**
 *
 * @author andre
 */
public record ApiError(
        String path,
        String message,
        int statusCode,
        LocalDateTime localDataTime) {

}
