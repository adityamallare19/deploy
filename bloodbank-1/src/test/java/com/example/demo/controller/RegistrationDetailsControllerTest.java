package com.example.demo.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import com.example.demo.entity.RegistrationDetails;
import com.example.demo.service.RegistrationDetailsService;
import com.example.demo.service.UserService;



@ExtendWith(MockitoExtension.class)
class RegistrationDetailsControllerTest {



	@InjectMocks
    private RegistrationDetailsController myController;
	
    @Mock
    private RegistrationDetailsService registrationDetailsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationDetailsController controller;

    @Mock
    private Model model;

    @Test
    void testSaveDetail() {
        // Mock data
        RegistrationDetails detail = new RegistrationDetails();
        detail.setEmail("test@example.com");
        detail.setOtp(1234);
        detail.setFirstname("John");
        detail.setLastname("Doe");
        detail.setPassword("password");

        List<RegistrationDetails> savedDetails = new ArrayList<>();
        savedDetails.add(detail);

       // when(userService.checkEmailExistance(detail)).thenReturn(0);
        when(registrationDetailsService.getRegistrationDetailsByEmail(detail.getEmail())).thenReturn(savedDetails);

        // Test the controller method
        String result = controller.saveDetail(detail, model);

        // Assert the result
        assertEquals("registrationStatus", result);
        verify(registrationDetailsService, times(1)).saveRegistrationDetails(any());
    }
    @Test
    public void testGetDetails() {
        // Create a mock RegistrationDetails list
        List<RegistrationDetails> userDetailsList = new ArrayList<>();
        // Add some dummy data to the list
        RegistrationDetails user1 = new RegistrationDetails();
        user1.setEmail("user1@example.com");
        user1.setFirstname("John");
        user1.setLastname("Doe");
        // Add more users as needed

        userDetailsList.add(user1);
        // Add more users as needed

        // Mock registrationDetailsService behavior
        when(registrationDetailsService.getRegistrationDetails()).thenReturn(userDetailsList);

        // Call the controller method
        String viewName = myController.getDetails(model);
        System.out.println(viewName);

        // Verify that the correct view name is returned
        assertEquals("details", viewName);

        // Verify interactions
        Mockito.verify(registrationDetailsService).getRegistrationDetails();
        // Verify that the model attribute is added with the correct value
        Mockito.verify(model).addAttribute("users", userDetailsList);
    }

    @Test
    public void testFindDetailsByEmail() {
        // Create a mock RegistrationDetails list
        List<RegistrationDetails> userDetailsList = new ArrayList<>();
        // Add some dummy data to the list
        RegistrationDetails user1 = new RegistrationDetails();
        user1.setEmail("user1@example.com");
        user1.setFirstname("John");
        user1.setLastname("Doe");
    
        // Add more users as needed

        userDetailsList.add(user1);
        // Add more users as needed

        // Mock registrationDetailsService behavior
        when(registrationDetailsService.getRegistrationDetailsByEmail("user1@example.com")).thenReturn(userDetailsList);

        // Call the controller method
        List<RegistrationDetails> result = myController.findDetailsByEmail("user1@example.com");

        // Verify interactions
        Mockito.verify(registrationDetailsService).getRegistrationDetailsByEmail("user1@example.com");
        // Verify that the correct user list is returned
        assertEquals(userDetailsList, result);
    }

    @Test
    void testResetPassword() {
        // Mock data
        RegistrationDetails detail = new RegistrationDetails();
        detail.setEmail("test@example.com");
        detail.setOtp(1234);
        detail.setPassword("newPassword");

        when(userService.resetPassword(detail.getEmail(), detail.getOtp(), detail.getPassword(), model)).thenReturn(1);

        // Test the controller method
        String result = controller.resetPassword(detail, model);

        // Assert the result
        assertEquals("redirect:/userLogin", result);
    }
    
    
    @Captor
    
    private ArgumentCaptor<String> messageCaptor;

    
    @Autowired
    
    private MockMvc mockMvc;
//    @Test
//    void testSendOtp() throws Exception {
//        // Mock data
//        String email = "test@example.com";
//
//        // Perform the request
//        mockMvc.perform(post("/sendOTP/{email}", email))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("User aleady existing"));
//
//        // Verify the interaction (you might need to adjust this based on your actual implementation)
//        verify(userService, times(1)).sendOtp(email);
//    }


    @SuppressWarnings("deprecation")
	@BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void testGetDetails() {
//        // Mock data using getters and setters
//        RegistrationDetails mockUser = new RegistrationDetails();
//        mockUser.setFirstname("John Doe");
//        mockUser.setEmail("john.doe@example.com");
//        // Set other properties using setters
//
//        List<RegistrationDetails> mockUsers = Arrays.asList(mockUser);
//        when(registrationDetailsService.getRegistrationDetails()).thenReturn(mockUsers);
//
//        // Test the controller method
//        String result = controller.getDetails(model);
//
//        // Assert the result
//        assertEquals("details", result);
//
//        @SuppressWarnings("unchecked")
//        List<RegistrationDetails> resultUsers = (List<RegistrationDetails>) model.getAttribute("users");
//        assertEquals(mockUsers.size(), resultUsers.size());
//
//        // Assert using getters
//        assertEquals(mockUser.getFirstname(), resultUsers.get(0).getFirstname());
//        assertEquals(mockUser.getEmail(), resultUsers.get(0).getEmail());
//        // Add assertions for other properties using getters
//    }


    @Test
    void testFindDetailsById() {
        // Mock data
        int id = 1;
        Optional<RegistrationDetails> mockDetails = Optional.of(new RegistrationDetails(/* sample data */));
        when(registrationDetailsService.getRegistrationDetailsById(id)).thenReturn(mockDetails);

        // Test the controller method
        Optional<RegistrationDetails> result = controller.findDetailsById(id);

        // Assert the result
        assertEquals(mockDetails, result);
    }
}



//    @Test
//    void testFindDetailsByEmail() {
//        // Mock data
//        String email = "test@example.com";
//        List<RegistrationDetails> mockDetailsList = Arrays.asList(new RegistrationDetails(/* sample data */));
//        when(registrationDetailsService.getRegistrationDetailsByEmail(email)).thenReturn(mockDetailsList);
//
//        // Test the controller method
//        List<RegistrationDetails> result = controller.findDetailsByEmail(email);
//
//        // Assert the result
//        assertEquals(mockDetailsList, result);
//    }
//    
//}







//private MockMvc mockMvc;
//
//@MockBean
//private RegistrationDetailsService userService;
//
//private RegistrationDetails user;
//
//@BeforeEach
//public void setup() {
//	RegistrationDetails user= new RegistrationDetails();
////	user.setId(1);
//	user.setFirstname("Test User");
//	user.setEmail("test@example.com");
//	user.setPassword("password");
//	user.setBloodGroup("A+");
//
//	user.setLastname("B");
//    }
//
//@Test
//public void registerUserTest() throws Exception {
//
//	when(userService.saveRegistrationDetails(any(RegistrationDetails.class))).thenReturn("User registered successfully");
//	
//	mockMvc.perform(post("/registerUser")
//			.contentType(MediaType.APPLICATION_JSON)
//
//			.content("{\"name\":\"Test User\",\"email\":\"test@example.com\",\"password\":\"password\"}"))
//
//	.andExpect(status().isOk())
//	.andExpect(content().string("User registered successfully"));
//    }