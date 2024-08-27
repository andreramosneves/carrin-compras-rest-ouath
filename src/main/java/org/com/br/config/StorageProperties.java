/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.com.br.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author andre
 */
@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {
	/**
	 * Folder location for storing files
	 */
	private String location = "upload/media/produtos";
        
        
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}    
}
