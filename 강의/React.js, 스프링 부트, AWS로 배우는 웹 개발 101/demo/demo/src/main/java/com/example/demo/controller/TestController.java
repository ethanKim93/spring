package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")//���ҽ�
public class TestController {
	@GetMapping("/testGetMapping")
	public String testController() {
		return "Hello World! testGetMapping";
	}

	@GetMapping("/{id}") // http://localhost:8080/test/123
	public String testControllerWithPathVariables(@PathVariable(required = false) int id) 
	{
		return "Hello World! ID" + id;
	}
	
	@GetMapping("/testRequestParam") //http://localhost:8080/test/testRequestParam?id=321
	public String testControllerRequestParam(@RequestParam(required = false) int id) 
	{
		return "Hello World! ID" + id;
	}

	@GetMapping("/testRequestBody") 
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO
			testTestRequestBodyDTO) {
		
		return "Hello World! ID" + testTestRequestBodyDTO.getId() +"Message : " + testTestRequestBodyDTO.getMessage(); 
	}
	
	@GetMapping("/testResponseBody") 
	public ResponseDTO<String> testControllerResponseBody(){
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return response;
		
	}
	@GetMapping("/testResponseEntity") 
	public ResponseEntity<?> testControllerResponseEntity(){
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400!" );
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.badRequest().body(response);

	}
	
}

