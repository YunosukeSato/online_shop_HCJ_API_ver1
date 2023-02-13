package com.example.e_commerce.controller;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce.dto.CartRequest;
import com.example.e_commerce.entity.Cart;
import com.example.e_commerce.entity.Item;
import com.example.e_commerce.model.HomeModel;
import com.example.e_commerce.repository.CartRepository;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/items")
public class HomeController {
	
	@Autowired
	private CartRepository cartRepository;

	HomeModel homeModel = new HomeModel();

	@GetMapping("/all")
	public ResponseEntity<String> allItems() {
//		Create Http Headers
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Content-Type", "application/json");
	    responseHeaders.set("Access-Controll-Allow-Origin", "*");
	    responseHeaders.set("Access-Controll-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
	    
	    try {
//	    	Get all items from e_commerce_items
	    	JSONArray result = homeModel.getItems();
	    	
	    	return ResponseEntity.ok()
	    			.headers(responseHeaders)
	    			.body(result.toString());
	    } catch (JSONException | SQLException e) {
	    	return ResponseEntity.internalServerError().headers(responseHeaders).body(null);
	    }

	}
	
	@PostMapping("/id")
	public ResponseEntity<String> getItemById(@RequestBody Item item) {
		
//		Create Http Headers
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Content-Type", "application/json");
	    responseHeaders.set("Access-Controll-Allow-Origin", "*");
	    responseHeaders.set("Access-Controll-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
	
	    try {
//	    	itemRepository.findById(id);
	    	JSONObject result = homeModel.getItemById(item);
	    	
	    	return ResponseEntity.ok()
	    			.headers(responseHeaders)
	    			.body(result.toString());
	    	
	    } catch (JSONException | SQLException e) {
	    	return ResponseEntity.internalServerError().headers(responseHeaders).body(null);
	    }

	}
	
	@PostMapping("/add/cart")
	public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
		
//		Create Http Headers
	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Content-Type", "application/json");
	    responseHeaders.set("Access-Controll-Allow-Origin", "*");
	    responseHeaders.set("Access-Controll-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
	    
		try {

			Cart cart = new Cart();
			cart.setBrand(cartRequest.getBrand());
			cart.setName(cartRequest.getName());
			cart.setImage(cartRequest.getImage());
			cart.setPrice(cartRequest.getPrice());
			
//			Save an item into cart
			cartRepository.save(cart);
			
			JSONObject result = homeModel.addedItem(cartRequest);

			
	    	return ResponseEntity.ok()
	    			.headers(responseHeaders)
	    			.body(result.toString());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().headers(responseHeaders).body(null);
		}
	}
}
