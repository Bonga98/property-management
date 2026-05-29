package com.mycompany.property_management.service;

import com.mycompany.property_management.dto.PropertyDTO;


import java.util.List;

public interface PropertyService {

    PropertyDTO saveProperty(PropertyDTO propertyDTO);

    List<PropertyDTO> getAllproperties();

     PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyID);

    PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyID);

    void deleteProperty(Long propertyID);



}
