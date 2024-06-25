//package com.talan.polaris.controllers;
//
//import javax.validation.Valid;
//
//import com.talan.polaris.dto.UserDTO;
//import com.talan.polaris.mapper.UserMapper;
//import com.talan.polaris.services.UserService;
//import com.talan.polaris.utils.validation.groups.CreateValidationGroup;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.talan.polaris.dto.SignInCredentials;
//import com.talan.polaris.dto.XAuthToken;
//import com.talan.polaris.services.AuthenticationService;
//
//import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_AUTHENTICATION_PREFIX;
//
///**
// * AuthenticationController.
// * 
// * @author Nader Debbabi
// * @since 1.0.0
// */
//@RestController
//@RequestMapping(path = "${" + API_ENDPOINTS_AUTHENTICATION_PREFIX + "}")
//public class AuthenticationController {
//
//    private final AuthenticationService authenticationService;
//    private final UserService userService;
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public AuthenticationController(AuthenticationService authenticationService,UserService userService, ModelMapper modelMapper) {
//        this.authenticationService = authenticationService;
//        this.userService = userService;
//        this.modelMapper=modelMapper;
//    }
//
//
//
//    @PostMapping(path = "/sign-in")
//    public XAuthToken signIn(@RequestBody @Valid SignInCredentials signInCredentials) {
//        return this.authenticationService.signIn(signInCredentials);
//    }
//
//    @PostMapping(path = "/sign-out")
//    public void signOut() {
//        this.authenticationService.signOut();
//    }
//
//
//    @PostMapping(path = "/sign-up")
//    public UserDTO signUp(@RequestBody  @Validated(CreateValidationGroup.class) UserDTO userDTO) {
//        return UserMapper.convertUserEntityToDTO(this.userService.signUp(UserMapper.convertUserDTOToEntity(userDTO,modelMapper)),modelMapper);
//    }
//
//
//}
