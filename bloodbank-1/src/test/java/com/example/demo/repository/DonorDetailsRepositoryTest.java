package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.DonorDetails;
import com.example.demo.repository.DonorDetailsRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DonorDetailsRepositoryTest {

    @Autowired
    private DonorDetailsRepository donorDetailsRepository;

    private DonorDetails donor;

    @BeforeEach
    public void setUp() {
        // Common setup for each test
        donor = new DonorDetails();
        donor.setEmail("test@example.com");
        donor.setStatus((byte) 1);
        donorDetailsRepository.save(donor);
    }

    @Test
    public void testFindByEmail() {
        // Act
        List<DonorDetails> foundDonors = donorDetailsRepository.findByEmail("test@example.com");

        // Assert
        assertNotNull(foundDonors);
        assertEquals(1, foundDonors.size());
        assertEquals("test@example.com", foundDonors.get(0).getEmail());
    }

    @Test
    public void testFindByStatus() {
        // Act
        List<DonorDetails> foundDonors = donorDetailsRepository.findByStatus((byte) 1);

        // Assert
        assertNotNull(foundDonors);
        assertEquals(5, foundDonors.size());
        assertEquals((byte) 1, foundDonors.get(0).getStatus());
    }

//    @Test
//    public void testCountByStatus() {
//        // Act
//        List<DonorDetails> donorsWithStatus = donorDetailsRepository.countByStatus((byte) 1);
//
//        // Assert
//        assertNotNull(donorsWithStatus);
//        assertEquals(1, donorsWithStatus.size());
//        assertEquals((byte) 1, donorsWithStatus.get(0).getStatus());
//    }

    @Test
    public void testFindByEmailAndStatus() {
        // Act
        List<DonorDetails> foundDonors = donorDetailsRepository.findByEmailAndStatus("test@example.com", (byte) 1);

        // Assert
        assertNotNull(foundDonors);
        assertEquals(1, foundDonors.size());
        assertEquals("test@example.com", foundDonors.get(0).getEmail());
        assertEquals((byte) 1, foundDonors.get(0).getStatus());
    }
}
