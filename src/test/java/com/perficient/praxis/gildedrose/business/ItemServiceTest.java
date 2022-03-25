package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;


    @Test
    public void testGetItemByIdWhenItemWasNotFound(){

        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.findById(0));
    }

    @Test
    public void testGetItemByIdSuccess(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        Item itemFound = itemService.findById(0);
        assertEquals(item, itemFound);
    }

    @Test
    /**
     * GIVEN a valid normal type item in the database
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * both will be decreased by 1
     */
    public void testUpdateQualityOfNormalTypeItem(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Oreo", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(29, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid ticket type item in the database with a sellIn value less than 6
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be increased by 3
     */
    public void testUpdateQualityOfTicketsTypeItem(){

        var item = new Item(0, "Bad Bunny Ticket", 2, 30, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Bad Bunny Ticket", itemsUpdated.get(0).name);
        assertEquals(1, itemsUpdated.get(0).sellIn);
        assertEquals(33, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }


    @Test
    /**
     * GIVEN a valid ticket type item in the database with a sellIn value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be established in 0
     */
    public void testUpdateQualityOfTicketsTypeItemNegativeSellIn(){

        var item = new Item(0, "Bad Bunny Ticket", -1, 30, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Bad Bunny Ticket", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Normal type item in the database with a sellIn value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be decreased by 2
     */
    public void testUpdateQualityOfNormalTypeItemNegativeSellIn(){

        var item = new Item(0, "Chocorramo", -1, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Chocorramo", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(28, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Aged type item in the database with a sellIn value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be increased by 2
     */
    public void testUpdateQualityOfAgedTypeItemNegativeSellIn(){

        var item = new Item(0, "Vino", -1, 20, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Vino", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(22, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Legendary type item in the database with a sellIn value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfLegendaryTypeItemNegativeSellIn(){

        var item = new Item(0, "Vino 1980", 0, 80, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Vino 1980", itemsUpdated.get(0).name);
        assertEquals(0, itemsUpdated.get(0).sellIn);
        assertEquals(80, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }


    @Test
    /**
     * GIVEN a valid Normal type item in the database with a quality value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfNormalTypeItemNegativeQuality(){

        var item = new Item(0, "Chocorramo", 10, -1, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Chocorramo", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(-1, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid ticket type item in the database with a sellIn value greater than 11
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be increased by 1
     */
    public void testUpdateQualityOfTicketsTypeItemWithSellInGreaterThan11(){

        var item = new Item(0, "Bad Bunny Ticket", 12, 40, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Bad Bunny Ticket", itemsUpdated.get(0).name);
        assertEquals(11, itemsUpdated.get(0).sellIn);
        assertEquals(41, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid ticket type item in the database with a sellIn value less than 11 and greater than 6
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be increased by 2
     */
    public void testUpdateQualityOfTicketsTypeItemWithSellInValueIn7(){

        var item = new Item(0, "Bad Bunny Ticket", 7, 40, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Bad Bunny Ticket", itemsUpdated.get(0).name);
        assertEquals(6, itemsUpdated.get(0).sellIn);
        assertEquals(42, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Legendary type item in the database with a sellIn value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfLegendaryTypeItemWithNegativeSellIn(){

        var item = new Item(0, "Cerebro", -7, 80, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Cerebro", itemsUpdated.get(0).name);
        assertEquals(-7, itemsUpdated.get(0).sellIn);
        assertEquals(80, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }
    @Test
    /**
     * GIVEN a valid ticket type item in the database with a quality value greater than 50
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfTicketsTypeItemWithQualityGreaterThan50(){

        var item = new Item(0, "Bad Bunny Ticket", 7, 60, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Bad Bunny Ticket", itemsUpdated.get(0).name);
        assertEquals(6, itemsUpdated.get(0).sellIn);
        assertEquals(60, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }
    @Test
    /**
     * GIVEN a valid Legendary type item in the database with a quality value and sellIn value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfLegendaryTypeItemNegativeQualityanSellIn(){

        var item = new Item(0, "Vino 1980", -1, -10, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Vino 1980", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(-10, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Aged type item in the database with a sellIn value less than 0 and a Quality value greater than 50
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be increased by 2
     */
    public void testUpdateQualityOfAgedTypeItemNegativeSellInAndQualityGreaterThan50(){

        var item = new Item(0, "Vino", -1, 70, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Vino", itemsUpdated.get(0).name);
        assertEquals(-2, itemsUpdated.get(0).sellIn);
        assertEquals(70, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

}
