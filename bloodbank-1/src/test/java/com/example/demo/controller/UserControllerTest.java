package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.configuration.CustomUserDetail;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService loginService;
//
//    @Test
//    void testViewProfileDetails() {
//        HttpSession session = mock(HttpSession.class);
//        Model model = mock(Model.class);
//        CustomUserDetail user = new CustomUserDetail();
//        user.setEmail("test@example.com");
//        when(session.getAttribute("user")).thenReturn(user);
//
//        List<RegistrationDetails> userDetails = new ArrayList<>();
//        // add sample user details to the list
//
//        when(loginService.getProfileDetails("test@example.com")).thenReturn(userDetails);
//
//        String result = userController.viewProfileDetails(session, model);
//
//        assertEquals("userProfile", result);
//        verify(model).addAttribute(eq("user"), eq(userDetails));
//    }

    @Test
    void testUpdateProfile() {
        RegistrationDetails registrationDetails = new RegistrationDetails();
        // set properties for registrationDetails

        Model model = mock(Model.class);

        String result = userController.updateProfile(registrationDetails, model);

        assertEquals("userProfile", result);
        verify(loginService).updateProfile(eq(registrationDetails));
        verify(model).addAttribute(eq("userData"), eq(registrationDetails));
        verify(model).addAttribute(eq("userUpdate"), eq("Details updated successfully"));
        verify(model).addAttribute(eq("user"), anyList()); // assuming getProfileDetails returns a list
    }

    @Test
    void testViewBloodRequestsDetails() {
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        when(session.getAttribute("userEmail")).thenReturn("test@example.com");

        List<PatientDetails> bloodRequests = new ArrayList<>();
        // add sample blood requests to the list

        when(loginService.getBloodRequestsDetails("test@example.com")).thenReturn(bloodRequests);

        String result = userController.viewBloodRequestsDetails(session, model);

        assertEquals("userBloodRequestHistory", result);
        verify(model).addAttribute(eq("bloodRequestHistory"), eq(bloodRequests));
    }

    @Test
    void testGetDonateRequestsDetails() {
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        when(session.getAttribute("userEmail")).thenReturn("test@example.com");

        List<DonorDetails> donateRequests = new ArrayList<>();
        // add sample donate requests to the list

        when(loginService.getDonateRequestsDetails("test@example.com")).thenReturn(donateRequests);

        String result = userController.getDonateRequestsDerails(session, model);

        assertEquals("userDonationRequestHistory", result);
        verify(model).addAttribute(eq("donationRequestHistory"), eq(donateRequests));
    }

//    @Test
//    void testGetBloodDonationCount() {
//        when(loginService.findBloodDonationsCount("test@example.com")).thenReturn(5);
//
//        String result = userController.getBloodDonationCount("test@example.com");
//
//        assertEquals("Expected result", result); // Add the expected result
//    }

//    @Test
//    void testGetBloodRequestCount() {
//        when(loginService.findBloodRequestsCount("test@example.com")).thenReturn(3);
//
//        String result = userController.getBloodRequestCount("test@example.com");
//
//        assertEquals("Expected result", result); // Add the expected result
//    }

    @Test
    void testDonateRequest() {
        DonorDetails donorDetails = new DonorDetails();
        // set properties for donorDetails

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        String result = userController.donateRequest(donorDetails, session, model);

        // Add assertions based on the expected behavior of the method
    }

    @Test
    void testBloodRequestSelf() {
        PatientDetails patientDetails = new PatientDetails();
        // set properties for patientDetails

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        String result = userController.bloodRequestSelf(patientDetails, session, model);

        // Add assertions based on the expected behavior of the method
    }

    @Test
    void testBloodRequestOthers() {
        PatientDetails patientDetails = new PatientDetails();
        // set properties for patientDetails

        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);

        String result = userController.bloodRequestOthers(patientDetails, session, model);

        // Add assertions based on the expected behavior of the method
    }
}
