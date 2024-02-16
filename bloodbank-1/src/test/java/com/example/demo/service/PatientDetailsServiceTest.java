package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.PatientDetails;
import com.example.demo.repository.PatientDetailsRepository;

public class PatientDetailsServiceTest {

    @Mock
    private PatientDetailsRepository patientDetailsRepository;

    @InjectMocks
    private PatientDetailsService patientDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Set up common values before each test
        PatientDetails patient1 = new PatientDetails();
        patient1.setId(1);
        patient1.setEmail("patient1@example.com");
        patient1.setStatus((byte) 1);

        PatientDetails patient2 = new PatientDetails();
        patient2.setId(2);
        patient2.setEmail("patient2@example.com");
        patient2.setStatus((byte) 1);

        // Create specific lists for different test cases
        List<PatientDetails> allPatients = List.of(patient1, patient2);
        List<PatientDetails> patient1List = List.of(patient1);
        List<PatientDetails> emptyPatientList = Collections.emptyList();

        // Mock repository behavior with more specific responses
        when(patientDetailsRepository.findAll()).thenReturn(allPatients);
        when(patientDetailsRepository.findById(1)).thenReturn(Optional.of(patient1));
        when(patientDetailsRepository.findById(3)).thenReturn(Optional.empty()); // Test for non-existent ID
        when(patientDetailsRepository.findByEmailAndStatus("patient1@example.com", (byte) 1)).thenReturn(patient1List);
        when(patientDetailsRepository.findByEmail("patient3@example.com")).thenReturn(emptyPatientList); // Test for non-existent email
        when(patientDetailsRepository.findByStatus((byte) 1)).thenReturn(allPatients);
        when(patientDetailsRepository.save(any())).thenReturn(patient1);
    }
    
    
    @Test
    public void testSavePatientDetails() {
        // Arrange
        PatientDetails patientToSave = new PatientDetails();
        patientToSave.setId(1);
        patientToSave.setEmail("patient1@example.com");
        patientToSave.setStatus((byte) 1);

        // Mock repository behavior
        when(patientDetailsRepository.save(patientToSave)).thenReturn(patientToSave);

        // Act
        PatientDetails savedPatient = patientDetailsService.savePatientDetails(patientToSave);

        // Assert
        assertEquals(patientToSave, savedPatient);
        assertEquals(patientToSave.getId(), savedPatient.getId());
        assertEquals(patientToSave.getEmail(), savedPatient.getEmail());
        assertEquals(patientToSave.getStatus(), savedPatient.getStatus());

        // Verify that the save method was called
        verify(patientDetailsRepository, times(1)).save(patientToSave);
    }
    
    

    @Test
    public void testGetPatientDetails() {
        // Act
        List<PatientDetails> resultList = patientDetailsService.getPatientDetails();

        // Assert
        assertEquals(2, resultList.size());
    }

    @Test
    public void testGetPatientDetailsByUser() {
        // Act
        Optional<PatientDetails> result = patientDetailsService.getPatientDetailsByUser(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    public void testGetPatientDetailsByEmailAndStatus() {
        // Act
        List<PatientDetails> resultList = patientDetailsService.getPatientDetailsByEmailAndStatus("patient1@example.com", (byte) 1);

        // Assert
        assertEquals(1, resultList.size());
        assertEquals("patient1@example.com", resultList.get(0).getEmail());
    }
    
    @Test
    public void testGetPatientDetailsStatus() {
        // Act
        List<PatientDetails> resultList = patientDetailsService.getPatientDetailsByStatus((byte) 1);

        // Assert
        assertEquals(2, resultList.size());
        assertEquals("patient1@example.com", resultList.get(0).getEmail());
    }
    
    @Test
    public void testGetPatientsDetailsByEmail() {
        // Arrange
        String email = "patient1@example.com";
        PatientDetails patient1 = new PatientDetails();
        patient1.setId(1);
        patient1.setEmail(email);
        patient1.setStatus((byte) 1);

        PatientDetails patient2 = new PatientDetails();
        patient2.setId(2);
        patient2.setEmail(email);
        patient2.setStatus((byte) 1);

        List<PatientDetails> expectedPatients = List.of(patient1, patient2);

        // Mock repository behavior
        when(patientDetailsRepository.findByEmail(email)).thenReturn(expectedPatients);

        // Act
        List<PatientDetails> resultPatients = patientDetailsService.getPatientsDetailsByEmail(email);

        // Assert
        assertEquals(2, resultPatients.size());
        assertEquals(expectedPatients, resultPatients);

        // Verify that the findByEmail method was called once with the correct argument
        verify(patientDetailsRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testUpdateStatus() {
        // Arrange
        PatientDetails detail = new PatientDetails();
        when(patientDetailsRepository.save(detail)).thenReturn(detail);

        // Act
        PatientDetails result = patientDetailsService.updateStatus(detail);

        // Assert
        assertNotNull(result);
        assertEquals(detail, result);
        assertEquals(detail.getStatus(), result.getStatus());
    }
    
    
    
    @Test
    public void testGetBloodRequestsHistory() {
        // Arrange
        PatientDetails patient1 = new PatientDetails();
        patient1.setId(1);
        patient1.setEmail("patient1@example.com");
        patient1.setStatus((byte) 1);

        PatientDetails patient2 = new PatientDetails();
        patient2.setId(2);
        patient2.setEmail("patient2@example.com");
        patient2.setStatus((byte) 1);

        List<PatientDetails> expectedPatients = List.of(patient1, patient2);

        // Mock repository behavior
        when(patientDetailsRepository.findAll()).thenReturn(expectedPatients);

        // Act
        List<PatientDetails> resultPatients = patientDetailsService.getBloodRequestsHistory();

        // Assert
        assertEquals(2, resultPatients.size());
        assertEquals("patient1@example.com", resultPatients.get(0).getEmail());
        assertEquals("patient2@example.com", resultPatients.get(1).getEmail());

        // Verify that the findAll method was called once
        verify(patientDetailsRepository, times(1)).findAll();
    }

    // Add more test cases for other methods as needed
}
