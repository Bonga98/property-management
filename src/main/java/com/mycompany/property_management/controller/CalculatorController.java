package com.mycompany.property_management.controller;

import com.mycompany.property_management.dto.CalculatorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/calculator")//class level mapping of url to a controller class
public class CalculatorController {

    @GetMapping("/add/") // method level mapping of url to a controller method
    //http://localhost:8089/api/v1/calculator/add?num1=7.0&num2=10.0
    public Double Add(@RequestParam("num1") Double num1,@RequestParam("num2") Double num2){
        return num1+num2;
    }
    @GetMapping("/sub/{num1}/{num2}")//Map the values of url to java variables by Path variable method
    public Double substract(@PathVariable("num1")Double num1, @PathVariable("num2")Double num2){

        Double result = null;

        if(num1 >= num2){
            result =  num1-num2;

        }else{
            result = num2 - num1;
        }
        return result;

    }
    @PostMapping("/mul")
    public ResponseEntity<Double> multiply(@RequestBody CalculatorDTO calculatorDTO){

        Double result = null;
        result = calculatorDTO.getNum1() * calculatorDTO.getNum2()* calculatorDTO.getNum3() * calculatorDTO.getNum4();

        ResponseEntity<Double> responseEntity = new ResponseEntity<Double>(result, HttpStatus.CREATED);

        return responseEntity;



    }
}

