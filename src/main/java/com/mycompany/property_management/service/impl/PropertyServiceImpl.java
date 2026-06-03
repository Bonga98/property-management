package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.convertor.PropertyConvertor;
import com.mycompany.property_management.dto.PropertyDTO;
import com.mycompany.property_management.entity.PropertyEntity;
import com.mycompany.property_management.entity.UserEntity;
import com.mycompany.property_management.exception.BusinessException;
import com.mycompany.property_management.exception.ErrorModel;
import com.mycompany.property_management.repository.PropertyRepository;
import com.mycompany.property_management.repository.UserRepository;
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
    private PropertyConvertor propertyConvertor;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO, String email) {
        // Find the logged-in user by their email
        UserEntity user = userRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new BusinessException(
                        List.of(new ErrorModel("USER_NOT_FOUND", "User not found"))));

        // Link the property to the user and save
        PropertyEntity pe = propertyConvertor.convertDTOtoEntity(propertyDTO, user);
        pe = propertyRepository.save(pe);

        return propertyConvertor.convertEntitytoDTO(pe);
    }

    @Override
    public List<PropertyDTO> getAllproperties(String email) {
        // Find the logged-in user by their email
        UserEntity user = userRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new BusinessException(
                        List.of(new ErrorModel("USER_NOT_FOUND", "User not found"))));

        // Only return properties belonging to this user
        List<PropertyEntity> listofProps = propertyRepository.findByUser(user);

        List<PropertyDTO> propList = new ArrayList<>();
        for (PropertyEntity pe : listofProps) {
            propList.add(propertyConvertor.convertEntitytoDTO(pe));
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
