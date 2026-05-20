package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.convertor.UserConvertor;
import com.mycompany.property_management.dto.UserDTO;
import com.mycompany.property_management.entity.UserEntity;
import com.mycompany.property_management.exception.BusinessException;
import com.mycompany.property_management.exception.ErrorModel;
import com.mycompany.property_management.repository.UserRepository;
import com.mycompany.property_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConvertor userConvertor;

    @Override
    public UserDTO register(UserDTO userDTO) {

        Optional<UserEntity> optionalUserEntity =  userRepository.findByOwnerEmail(userDTO.getOwnerEmail());

        if(optionalUserEntity.isPresent()){
            //Throw an exception
            List<ErrorModel> errorModelList = new ArrayList<>();
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("EMAIL_ALREADY_EXIST");
            errorModel.setMessage("The Email With Which You Are Trying To Register Already Exists");

            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);

        }else{

            UserEntity userEntity =  userConvertor.convertDTOtoEntity(userDTO);
            userEntity = userRepository.save(userEntity);

            userDTO = userConvertor.convertEntitytoDTO(userEntity);

            return userDTO;


        }



    }

    @Override
    public UserDTO login(String email, String password) {
        UserDTO userDTO = null;


        Optional<UserEntity> optionalUserEntity =  userRepository.findByOwnerEmailAndPassword(email,password);
        if(optionalUserEntity.isPresent()){
            userDTO = userConvertor.convertEntitytoDTO(optionalUserEntity.get());

        }else{

            List<ErrorModel> errorModelList = new ArrayList<>();

            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode("Invalid Login");
            errorModel.setMessage("Incorrect Email or Password");
            errorModelList.add(errorModel);

            throw new BusinessException(errorModelList);

        }
        return  userDTO;
    }

}
