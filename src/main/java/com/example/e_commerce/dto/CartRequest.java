package com.example.e_commerce.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CartRequest implements Serializable{

	private String brand;
	
	private String name;
	
	private String image;
	
	private int price;
}