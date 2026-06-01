package com.mycompany.property_management.service.impl;

import com.mycompany.property_management.convertor.UserConvertor;
import com.mycompany.property_management.dto.UserDTO;
import com.mycompany.property_management.entity.UserEntity;
import com.mycompany.property_management.exception.BusinessException;
import com.mycompany.property_management.exception.ErrorModel;
import com.mycompany.property_management.repository.UserRepository;
import com.mycompany.property_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConvertor userConvertor;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(UserDTO userDTO) {

        Optional<UserEntity> optionalUserEntity = userRepository.findByOwnerEmail(userDTO.getOwnerEmail());

        if (optionalUserEntity.isPresent()) {
            throw new BusinessException(List.of(new ErrorModel("EMAIL_ALREADY_EXISTS", "The email you are trying to register already exists")));
        }

        // Encode the password before saving
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserEntity userEntity = userConvertor.convertDTOtoEntity(userDTO);
        userEntity = userRepository.save(userEntity);

        return userConvertor.convertEntitytoDTO(userEntity);
    }

    @Override
    public UserDTO login(String email, String password) {

        Optional<UserEntity> optionalUserEntity = userRepository.findByOwnerEmail(email);

        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();

            // Compare raw password against the stored BCrypt hash
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userConvertor.convertEntitytoDTO(userEntity);
            }
        }

        throw new BusinessException(List.of(new ErrorModel("INVALID_LOGIN", "Incorrect email or password")));
    }

}
