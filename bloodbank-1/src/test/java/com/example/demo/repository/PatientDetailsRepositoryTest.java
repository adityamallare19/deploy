package com.example.demo.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.PatientDetails;
import com.example.demo.repository.PatientDetailsRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientDetailsRepositoryTest {

    @Autowired
    private PatientDetailsRepository patientDetailsRepository;

    private PatientDetails patient;

    @BeforeEach
    public void setUp() {
        // Common setup for each test
        patient = new PatientDetails();
        patient.setEmail("test@example.com");
        patient.setStatus((byte) 1);
        patientDetailsRepository.save(patient);
    }

    @Test
    public void testFindByEmail() {
        // Act
        List<PatientDetails> foundPatients = patientDetailsRepository.findByEmail("test@example.com");

        // Assert
        assertNotNull(foundPatients);
        assertEquals(1, foundPatients.size());
        assertEquals("test@example.com", foundPatients.get(0).getEmail());
    }

    @Test
    public void testFindByStatus() {
        // Act
        List<PatientDetails> foundPatients = patientDetailsRepository.findByStatus((byte) 1);

        // Assert
        assertNotNull(foundPatients);
        assertEquals(1, foundPatients.size());
        assertEquals((byte) 1, foundPatients.get(0).getStatus());
    }

    @Test
    public void testFindByEmailAndStatus() {
        // Act
        List<PatientDetails> foundPatients = patientDetailsRepository.findByEmailAndStatus("test@example.com", (byte) 1);

        // Assert
        assertNotNull(foundPatients);
        assertEquals(1, foundPatients.size());
        assertEquals("test@example.com", foundPatients.get(0).getEmail());
        assertEquals((byte) 1, foundPatients.get(0).getStatus());
    }
}

