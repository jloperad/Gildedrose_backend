package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;

    }


    public List<Item> updateQuality() {
        var itemsList = itemRepository.findAll();
        var items = itemsList.toArray(new Item[itemsList.size()]);
        int baseAdjustment = 1;

        for (Item item: itemsList) {
            boolean sellDatePassed = item.sellIn < 1;

            if(item.type.equals(Item.Type.NORMAL)){
                int adjustment = sellDatePassed ? baseAdjustment*2 : baseAdjustment;
                changeQuality(item, -adjustment);
                reduceSellIn(item);
            }

            if(item.type.equals(Item.Type.AGED)){
                int adjustment = sellDatePassed ? baseAdjustment*2 : baseAdjustment;
                changeQuality(item, adjustment);
                reduceSellIn(item);
            }

            if(item.type.equals(Item.Type.TICKETS)){
                changeTicketsQuality(item,sellDatePassed);
                reduceSellIn(item);
            }


            itemRepository.save(item);
        }
        return Arrays.asList(items);
    }


    public void changeQuality(Item item, int adjustment){
        int newQuality = item.quality+adjustment;
        int maxQuality = 50;
        int minQuality = 0;
        boolean inRange = newQuality <=maxQuality && newQuality>=minQuality;
        if (inRange){
            item.quality = newQuality;
        }
    }

    private void changeTicketsQuality(Item item, boolean sellDatePassed) {
        int doubleDate = 10;
        int tripleDate = 5;
        changeQuality(item, 1);
        if (item.sellIn <= doubleDate) {
            changeQuality(item, 1);
        }

        if (item.sellIn <= tripleDate) {
            changeQuality(item, 1);
        }
        if (sellDatePassed) {
            item.quality = 0;
        }
    }


    public void reduceSellIn(Item item){
        int degradeRate = 1;
        item.sellIn -= degradeRate;
    }


    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(int id, Item item) {
        if (itemRepository.findById(id).isPresent()){
            return itemRepository.save(new Item(id, item.name, item.sellIn, item.quality, item.type));
        }
        else{
            throw new ResourceNotFoundException("");
        }
    }

    public void deleteById(int id) {
        Item item = findById(id);
        itemRepository.delete(item);
    }

    public List<Item> listItems(){
        return itemRepository.findAll();
    }

    public Item findById(int id) {
        return itemRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(""));
    }
}
