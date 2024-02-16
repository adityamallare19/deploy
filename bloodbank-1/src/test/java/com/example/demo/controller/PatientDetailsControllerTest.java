package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.PatientDetails;
import com.example.demo.service.PatientDetailsService;

@SpringBootTest
class PatientDetailsControllerTest {

    @InjectMocks
    private PatientDetailsController controller;

    @Mock
    private PatientDetailsService service;

    @Test
    void testSavePatientDetailsC() {
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setId(1L);
        patientDetails.setFirstname("John Doe");
        patientDetails.setEmail("john@example.com");

        when(service.savePatientDetails(patientDetails)).thenReturn(patientDetails);

        PatientDetails result = controller.savePatientDetailsC(patientDetails);

        assertEquals(patientDetails, result);
    }

    @Test
    void testGetPatientsDetailsC() {
        PatientDetails patient1 = new PatientDetails();
        patient1.setId(1L);
        patient1.setFirstname("John Doe");
        patient1.setEmail("john@example.com");

        PatientDetails patient2 = new PatientDetails();
        patient2.setId(2L);
        patient2.setFirstname("Jane Doe");
        patient2.setEmail("jane@example.com");

        List<PatientDetails> patientDetailsList = Arrays.asList(patient1, patient2);

        when(service.getPatientDetails()).thenReturn(patientDetailsList);

        List<PatientDetails> result = controller.getPatientsDetailsC();

        assertEquals(patientDetailsList, result);
    }

    // Add more tests as needed for other methods in the controller class.
}
