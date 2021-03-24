package com.sebastian.springrestapp.service;

import com.sebastian.springrestapp.model.PermissionEntity;
import com.sebastian.springrestapp.model.PermissionEnum;
import com.sebastian.springrestapp.model.RoleEntity;
import com.sebastian.springrestapp.model.UserEntity;
import com.sebastian.springrestapp.payload.AddUserRequestDTO;
import com.sebastian.springrestapp.payload.RoleResponseDTO;
import com.sebastian.springrestapp.payload.UserResponseDTO;
import com.sebastian.springrestapp.repository.RoleRepository;
import com.sebastian.springrestapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String TEST_USER = "TEST_USER";
    private static final int TEST_USER_ID = 1;
    private static final String TEST_PASSWORD = "TEST_PASSWORD";
    private static final String TEST_ENCODED_PASSWORD = "TEST_ENCODED_PASSWORD";

    private static final String ROLE_NAME = "ROLE_TEST";
    private static final int ROLE_ID = 2;
    private static final List<String> PERMISSION_LIST = List.of(PermissionEnum.CREATE_USER.name());


    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @Before
    public void setUp() {
        modelMapper.getConfiguration()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder());
    }


    @Test
    public void createUserTest() {
        //given
        RoleResponseDTO roleResponseDTO = RoleResponseDTO.builder().id(ROLE_ID).name(ROLE_NAME).permissions(PERMISSION_LIST).build();
        UserResponseDTO userResponseDTO = UserResponseDTO.builder().username(TEST_USER).id(TEST_USER_ID).role(roleResponseDTO).build();

        AddUserRequestDTO requestDTO = AddUserRequestDTO.builder().username(TEST_USER).password(TEST_PASSWORD).roleId(ROLE_ID).build();

        RoleEntity roleEntity = new RoleEntity(ROLE_ID, ROLE_NAME, List.of(new PermissionEntity(PermissionEnum.CREATE_USER.name())));

        UserEntity userEntity = new UserEntity(TEST_USER_ID, TEST_USER, TEST_ENCODED_PASSWORD, roleEntity);

        when(roleRepository.findById(ROLE_ID)).thenReturn(Optional.of(roleEntity));
        when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_ENCODED_PASSWORD);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        // when(modelMapper.map(userEntity, UserResponseDTO.UserResponseDTOBuilder.class)).thenReturn(userResponseDTO);

        //when
        UserResponseDTO result = userService.createUser(requestDTO);
        //then
        assertNotNull(result);
        assertEquals(userResponseDTO, result);
    }
}
