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
        assertEquals("Oro", itemsUpdated.get(0).name);
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
     * GIVEN a valid ticket type item in the database with a sellIn value equal to or less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be established in 0
     */
    public void testUpdateQualityOfTicketsTypeItemWithNegativeSellIn(){

        var item = new Item(0, "Jamming Ticket", 0, 30, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Jamming Ticket", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Normal type item in the database with a sellIn value equal to or less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be decreased by 2
     */
    public void testUpdateQualityOfNormalTypeItemWithNegativeSellIn(){

        var item = new Item(0, "Chocorramo", 0, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Chocorramo", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(28, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Aged type item in the database with a sellIn value equal to or less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be increased by 2
     */
    public void testUpdateQualityOfAgedTypeItemWithNegativeSellIn(){

        var item = new Item(0, "Wine", 0, 20, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Wine", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(22, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Legendary type item in the database with a sellIn value equal to or less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the values but this scenario it is not a valid case,so the values do not change
     * sellIn should stay the same
     * quality should stay the same
     */
    public void testUpdateQualityOfLegendaryTypeItemWIthNegativeSellIn(){

        var item = new Item(0, "1980 Wine", 0, 80, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("1980 Wine", itemsUpdated.get(0).name);
        assertEquals(0, itemsUpdated.get(0).sellIn);
        assertEquals(80, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }


    @Test
    /**
     * GIVEN a valid Normal type item in the database with a quality value equal to or less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,but quality it can never be negative, so the uptadeQuality does not change this value
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfNormalTypeItemWithNegativeQuality(){

        var item = new Item(0, "Milk", 10, 0, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Milk", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid tickets type item in the database with a sellIn value greater than 11
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * sellIn value should be decreased by 1
     * quality should be increased by 1
     */
    public void testUpdateQualityOfTicketsTypeItemWithSellInGreaterThan11(){

        var item = new Item(0, "Gorillaz Ticket", 12, 40, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Gorillaz Ticket", itemsUpdated.get(0).name);
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

        var item = new Item(0, "Coldplay Ticket", 7, 40, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Coldplay Ticket", itemsUpdated.get(0).name);
        assertEquals(6, itemsUpdated.get(0).sellIn);
        assertEquals(42, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Legendary type item in the database with a sellIn value less than 0 and a positive quality value
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,but this scenario it is not a valid case,so the values do not change
     * sellIn value should be the same
     * quality should be the same
     */
    public void testUpdateQualityOfLegendaryTypeItemWithNegativeSellIn(){

        var item = new Item(0, "Disney's Brain", -1, 80, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Disney's Brain", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(80, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }
    @Test
    /**
     * GIVEN a valid ticket type item in the database with a quality value equal to or greater than 50
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,but quality it can never be greater than 50, so the uptadeQuality does not change this value
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfTicketsTypeItemWithQualityGreaterThan50(){

        var item = new Item(0, "Foo Fighters Ticket", 7, 50, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Foo Fighters Ticket", itemsUpdated.get(0).name);
        assertEquals(6, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }
    @Test
    /**
     * GIVEN a valid Legendary type item in the database with a quality value and sellIn value less than 0
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,but this scenario it is not a valid case,so the values do not change
     * sellIn value should be the same
     * quality should be the same
     */
    public void testUpdateQualityOfLegendaryTypeItemNegativeQualityAndSellIn(){

        var item = new Item(0, "Davinci's book", -1, 0, Item.Type.LEGENDARY);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Davinci's book", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(0, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.LEGENDARY, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN a valid Aged type item in the database with a sellIn value equal to or less than 0 and a Quality value equal to or greater than 50
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,but quality it can never be greater than 50, so the uptadeQuality does not change this value
     * sellIn value should be decreased by 1
     * quality should be the same
     */
    public void testUpdateQualityOfAgedTypeItemNegativeSellInAndQualityGreaterThan50(){

        var item = new Item(0, "Beer", 0, 50, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Beer", itemsUpdated.get(0).name);
        assertEquals(-1, itemsUpdated.get(0).sellIn);
        assertEquals(50, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    /**
     * GIVEN any valid item
     * WHEN createItem method is called
     * THEN the service should create an item
     */
    public void testCreateItem(){

        var item = new Item();
        when(itemService.createItem(item)).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    /**
     * GIVEN any valid id and a created item associated with it
     * WHEN updateItem method is called
     * THEN the service should update the item
     */
    public void testUpdateItemFound(){

        var item = new Item(0, "Alpinito", 60, 35, Item.Type.NORMAL);
        var newItem = new Item(0, "Alpin", 30, 15, Item.Type.NORMAL);
        when(itemRepository.findById(0)).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenReturn(newItem);
        Item updatedItem = itemService.updateItem(0,newItem);

        assertEquals(newItem,updatedItem);
    }

    @Test
    /**
     * GIVEN a non-existent id
     * WHEN updateItem method is called
     * THEN the service should throw an exception
     */
    public void testUpdateItemNotFound(){

        var item = new Item(0, "Bon Yurt", 60, 15, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> itemService.updateItem(0,item));
    }

    @Test
    /**
     * GIVEN one or more valid items
     * WHEN listItems method is called
     * THEN the service should return a list of all created items
     */
    public void testListItems(){

        var item1 = new Item(0, "Pringles", 100, 50, Item.Type.LEGENDARY);
        var item2 = new Item(1, "Bianchi", 40, 35, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item1,item2));

        List<Item> resultList = itemService.listItems();
        assertEquals(item1,resultList.get(0));
        assertEquals(item2,resultList.get(1));
    }
}
