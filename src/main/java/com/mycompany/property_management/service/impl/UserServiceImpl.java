package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.convertor.UserConvertor;
import com.mycompany.property_management.dto.UserDTO;
import com.mycompany.property_management.entity.UserEntity;
import com.mycompany.property_management.repository.UserRepository;
import com.mycompany.property_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConvertor userConvertor;

    @Override
    public UserDTO register(UserDTO userDTO) {

        UserEntity userEntity =  userConvertor.convertDTOtoEntity(userDTO);
        userEntity = userRepository.save(userEntity);

        userDTO = userConvertor.convertEntitytoDTO(userEntity);

        return userDTO;
    }

    @Override
    public UserDTO login(String email, String password) {
        return null;
    }
}
