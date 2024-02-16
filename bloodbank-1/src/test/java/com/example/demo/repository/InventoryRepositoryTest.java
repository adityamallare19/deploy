package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Inventory;
import com.example.demo.repository.InventoryRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    @Disabled
    public void testFindByBloodGroup() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setBloodGroup("A++");
        inventoryRepository.save(inventory);

        // Act
        List<Inventory> foundInventory = inventoryRepository.findByBloodGroup("A++");

        // Assert
        assertNotNull(foundInventory);
        assertEquals(1, foundInventory.size());
        assertEquals("A++", foundInventory.get(0).getBloodGroup());
    }

    // Uncomment the following tests based on your specific queries

    // @Test
    // public void testFindQuantityByBloodGroup() {
    //     // Act
    //     List<Object[]> result = inventoryRepository.findQuantityByBloodGroup();

    //     // Assert
    //     // Add assertions based on the expected results
    // }

    // @Test
    // public void testGetCountByBloodGroup() {
    //     // Act
    //     Map<String, Long> result = inventoryRepository.getCountByBloodGroup();

    //     // Assert
    //     // Add assertions based on the expected results
    // }

    @Test
    @Transactional
    public void testDeleteByBloodId() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setBloodId(1001);

        // Act
        Inventory savedInventory = inventoryRepository.save(inventory);
        
        // Ensure that the savedInventory has the actual BloodId assigned by the database
        assertNotNull(savedInventory.getBloodId());

        inventoryRepository.deleteByBloodId(savedInventory.getBloodId());

        // Assert
        assertEquals(21, inventoryRepository.count());
    }

}
