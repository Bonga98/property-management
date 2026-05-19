package com.mycompany.property_management.controller;

import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PropertyController {

    @Value("${pms.dummy}")

    //Dependency injection
    @Autowired
    private PropertyService propertyService;

        //Restful API is just a mapping of a url to a java class function
       //http://localhost:8089/api/v1/properties/hello
      @GetMapping("/hello")
      public String SayHello(){
          return "Hello";
      }

   @PostMapping("/properties")
    public ResponseEntity<PropertyDTO> saveProperty(@RequestBody PropertyDTO propertyDTO){

          propertyDTO =  propertyService.saveProperty(propertyDTO);
       return  new ResponseEntity<>(propertyDTO, HttpStatus.CREATED);

    }
    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getAllProperties(){

          List<PropertyDTO> propertyList =  propertyService.getAllproperties();
        return new ResponseEntity<>(propertyList, HttpStatus.OK);
    }
    @PutMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(@RequestBody PropertyDTO propertyDTO, @PathVariable Long propertyId){

          propertyDTO =  propertyService.updateProperty(propertyDTO,propertyId);
        return new ResponseEntity<>(propertyDTO, HttpStatus.OK);

    }
    @PatchMapping("/properties/update-description/{propertyId}")
    public ResponseEntity<PropertyDTO> updatePropertyDescription(@RequestBody PropertyDTO propertyDTO, @PathVariable  Long propertyId){

          propertyDTO =  propertyService.updatePropertyDescription(propertyDTO,propertyId);
        return new ResponseEntity<>(propertyDTO, HttpStatus.OK);

    }
    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity deleteProperty(@PathVariable Long propertyId){

          propertyService.deleteProperty(propertyId);

        return new ResponseEntity<>((Object) null,HttpStatus.NO_CONTENT);

    }
    }


