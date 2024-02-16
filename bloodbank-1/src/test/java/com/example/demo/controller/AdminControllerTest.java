package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.example.demo.configuration.CustomUserDetail;
import com.example.demo.emailService.SendEmailService;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;
import com.example.demo.service.AdminService;
import com.example.demo.service.DonorDetailsService;
import com.example.demo.service.InventoryService;
import com.example.demo.service.PatientDetailsService;
import com.example.demo.service.RegistrationDetailsService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private DonorDetailsService donorDetailsService;

    @Mock
    private PatientDetailsService patientDetailsService;

    @Mock
    private RegistrationDetailsService registrationDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private SendEmailService emailService;
    
    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Test
    void testViewProfileDetail() {
//        // Mock session and user
//        CustomUserDetail user = new CustomUserDetail();
//        user.setEmail("admin@example.com");
//        when(session.getAttribute("user")).thenReturn(user);
//
//        // Mock service response
//        List<RegistrationDetails> userDetails = new ArrayList<>(); // Add sample data
//        when(registrationDetailsService.getRegistrationDetailsByEmail("admin@example.com")).thenReturn(userDetails);
//
//        // Test
//        String result = adminController.viewProfileDetail(session, model);
//
//        // Assertions
//        assertEquals("adminProfile", result);
//        verify(model).addAttribute("user", userDetails);
//        verify(model).addAttribute("adminUpdate", "admin detail updated");
    }

    @Test
    void testGetDetails() {
        // ... (previous test)
    }

    @Test
    void testFindDetailsByBloodGroup() {
        // Mock data
        String bloodGroup = "A+";
        List<Inventory> inventoryList = new ArrayList<>(); // Add sample data

        when(inventoryService.findByBloodGroup(bloodGroup)).thenReturn(inventoryList);

        // Test
        List<Inventory> result = adminController.findDetailsByBloodGroup(bloodGroup);

        // Assertions
        assertEquals(inventoryList, result);
    }

    @Test
    void testCountDonorsWithStatusOne() {
        // Mock data
        List<DonorDetails> donorDetailsList = new ArrayList<>(); // Add sample data

        when(donorDetailsService.getTotalDonationCount()).thenReturn(donorDetailsList);

        // Test
        List<Integer> result = adminController.countDonorsWithStatusOne();

        // Assertions
        assertEquals(donorDetailsList.size(), result.get(0));
    }

    @Test
    void testGetDonorDetails() {
        // Mock donor service response
        List<DonorDetails> donorDetailsList = new ArrayList<>(); // Add sample data
        when(donorDetailsService.getDonorDetails()).thenReturn(donorDetailsList);

        // Test
        List<DonorDetails> result = adminController.getDonorDetails();

        // Assertions
        assertEquals(donorDetailsList, result);
    }

    @Test
    void testGetDonorDetailsByEmail() {
        // Mock donor service response
        List<DonorDetails> donorSearchList = new ArrayList<>(); // Add sample data
        when(donorDetailsService.findByemail("test@example.com")).thenReturn(donorSearchList);

        // Test
        String result = adminController.getDonorDetailsByEmail("test@example.com", model);

        // Assertions
        assertEquals("bloodDonationView", result);

        // Verify that the model contains the expected attribute
        verify(model).addAttribute("search", donorSearchList);
    }

    @Test
    void testGetInventoryDetails() {
        // Mock inventory service response
        List<Inventory> inventoryDetailsList = new ArrayList<>(); // Add sample data
        when(inventoryService.getInventoryDetails()).thenReturn(inventoryDetailsList);

        // Test
        String result = adminController.getInventoryDetails(model);

        // Assertions
        assertEquals("bloodExpiry", result);

        // Verify that the model contains the expected attribute
        verify(model).addAttribute("blood", inventoryDetailsList);
    }
    
    @Test
    void testCheckBlood() {
        // Mock inventory service response
        List<Inventory> inventoryDetailsList = new ArrayList<>(); // Add sample data
        when(inventoryService.getInventoryDetails()).thenReturn(inventoryDetailsList);

        // Mock inventory service response for checkForOldBloodSamples
        List<Inventory> resultInventoryList = new ArrayList<>(); // Add sample data
        when(inventoryService.checkForOldBloodSamples(inventoryDetailsList)).thenReturn(resultInventoryList);

        // Test
        List<Inventory> result = adminController.checkBlood();

        // Assertions
        assertEquals(resultInventoryList, result);
    }

//    @Test
//    void testCheckEligibility() {
//        // Mock donor service response
//        List<DonorDetails> donors = new ArrayList<>(); // Add sample data
//        when(donorDetailsService.getDonorDetails()).thenReturn(donors);
//
//        // Mock donor service response for checkEligibility
//        List<DonorDetails> eligibilityData = new ArrayList<>(); // Add sample data
//        when(donorDetailsService.checkEligibility(donors)).thenReturn(eligibilityData);
//
//        // Test
//        String result = adminController.checkEligibility(model);
//
//        // Assertions
//        assertEquals("userDetails", result);
//
//        // Verify that the model contains the expected attribute
//        verify(model).addAttribute("user", eligibilityData);
//    }

//    @Test
//    void testUpdateUserProfile() {
//        // Mock registration service response for updateUserProfile
//        RegistrationDetails registrationDetails = new RegistrationDetails(); // Add sample data
//        doNothing().when(service).updateUserProfile(registrationDetails);
//
//        // Test
//        String result = adminController.updateUserProfile(registrationDetails, model);
//
//        // Assertions
//        assertEquals("redirect:/admin/viewProfileDetail", result);
//
//        // Verify that the service method is called
//        verify(service).updateUserProfile(registrationDetails);
//    }

//    @Test
//    void testViewDonationHistory() {
//        // Mock service response for getDonationHistory
//        List<DonorDetails> donorData = new ArrayList<>(); // Add sample data
//        when(service.getDonationHistory()).thenReturn(donorData);
//
//        // Test
//        String result = adminController.viewDonationHistory(model);
//
//        // Assertions
//        assertEquals("bloodDonationView", result);
//
//        // Verify that the model contains the expected attribute
//        verify(model).addAttribute("donor", donorData);
//    }
    
    @Test
    void testViewDonationRequests() {
        // Mock service response for getDonationRequests
        List<DonorDetails> mockDonorData = new ArrayList<>(); // Add sample data
        when(adminService.getDonationRequests()).thenReturn(mockDonorData);

        // Test
        String result = adminController.viewDonationRequests(mock(Model.class));

        // Assertions
        assertEquals("bloodDonationRequests", result);

        // Verify that the service method is called
        verify(adminService).getDonationRequests();
    }

    @Test
    void testViewBloodRequestHistory() {
        // Mock service response for getBloodRequestsHistory
        List<PatientDetails> mockRequestData = new ArrayList<>(); // Add sample data
        when(patientDetailsService.getBloodRequestsHistory()).thenReturn(mockRequestData);

        // Test
        String result = adminController.viewBloodRequestHistory(mock(Model.class));

        // Assertions
        assertEquals("bloodRequestsViews", result);

        // Verify that the service method is called
        verify(patientDetailsService).getBloodRequestsHistory();
    }

    @Test
    void testViewBloodRequest() {
        // Mock service response for getBloodRequest
        List<PatientDetails> mockRequestData = new ArrayList<>(); // Add sample data
        when(adminService.getBloodRequest()).thenReturn(mockRequestData);

        // Test
        String result = adminController.viewBloodRequest(mock(Model.class));

        // Assertions
        assertEquals("bloodRequestAdmin", result);

        // Verify that the service method is called
        verify(adminService).getBloodRequest();
    }

    @Test
    void testViewUserDetails() {
        // Mock service response for getRegistrationDetails
        List<RegistrationDetails> mockUserData = new ArrayList<>(); // Add sample data
        when(registrationDetailsService.getRegistrationDetails()).thenReturn(mockUserData);

        // Test
        String result = adminController.viewUserDetails(mock(Model.class));

        // Assertions
        assertEquals("userDetails", result);

        // Verify that the service method is called
        verify(registrationDetailsService).getRegistrationDetails();
    }

//    @Test
//    void testAcceptDonationRequest() {
//        // Mock service response for acceptDonationRequest
//        doNothing().when(adminService).acceptDonationRequest(anyString());
//
//        // Test
//        adminController.acceptDonationRequest("test@email.com", mock(Model.class));
//
//        // Verify that the service method is called
//        verify(adminService).acceptDonationRequest("test@email.com");
//
//        // Verify that emailService.sendEmail is called
//        verify(emailService).sendEmail(eq("test@email.com"), eq("HemoHarbor "), eq("you Blood Donation Request is Accepted "));
//    }


}

