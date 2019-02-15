package com.cognizant.shoppingcart.item.controller;

import com.cognizant.shoppingcart.item.model.Item;
import com.cognizant.shoppingcart.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAllItems() {
        return (List<Item>) this.itemRepository.findAll();
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @PutMapping("{id}")
    public Item updateItemById(@RequestBody Item itemToUpdate, @PathVariable(name = "id") int id) {
        return itemRepository.findById(id)
                .map(item -> {
                    itemToUpdate.setName(itemToUpdate.getName());
                    return itemRepository.save(itemToUpdate);
                })
                .orElseGet(() -> itemRepository.save(itemToUpdate));
    }


    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable(name="id") int id) {
        itemRepository.deleteById(id);
    }
}

