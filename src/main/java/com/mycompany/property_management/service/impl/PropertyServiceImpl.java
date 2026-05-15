package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.convertor.PropertyConvertor;
import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.entity.PropertyEntity;
import com.mycompany.property_management.repository.PropertyRepository;
import com.mycompany.property_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private  PropertyConvertor propertyConvertor;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {

       PropertyEntity pe = propertyConvertor.convertDTOtoEntity(propertyDTO);
       pe = propertyRepository.save(pe);

       propertyDTO = propertyConvertor.convertEntitytoDTO(pe);
       return propertyDTO ;
    }

    @Override
    public List<PropertyDTO> getAllproperties() {
       List<PropertyEntity> listofProps =  (List<PropertyEntity>)propertyRepository.findAll();

       List<PropertyDTO> propList = new ArrayList<>();
       for(PropertyEntity pe :listofProps){
          PropertyDTO dto = propertyConvertor.convertEntitytoDTO(pe);
          propList.add(dto);

       }
        return propList;
    }

    @Override
    public PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyID) {

        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyID);
        PropertyDTO dto = null;
        if(optEn.isPresent()){
            PropertyEntity pe = optEn.get();// data from database
            pe.setTitle(propertyDTO.getTitle());
            pe.setAddress(propertyDTO.getAddress());
            pe.setOwnerEmail(propertyDTO.getOwnerEmail());
            pe.setOwnerName(propertyDTO.getOwnerName());
            pe.setPrice(propertyDTO.getPrice());
            pe.setDescription(propertyDTO.getDescription());

            dto = propertyConvertor.convertEntitytoDTO(pe);
            propertyRepository.save(pe);//save


        }
        return dto;
    }

    @Override
    public PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyID) {
        Optional<PropertyEntity> optEn = propertyRepository.findById(propertyID);
        PropertyDTO dto = null;
        if (optEn.isPresent()) {
            PropertyEntity pe = optEn.get();// data from database
            pe.setDescription(propertyDTO.getDescription());
            dto = propertyConvertor.convertEntitytoDTO(pe);
            propertyRepository.save(pe);//save


        }
        return dto;
    }

    @Override
    public void deleteProperty(Long propertyID) {
        propertyRepository.deleteById(propertyID);

    }
}
