package com.cognizant.shoppingcart.controller;

import com.cognizant.shoppingcart.item.model.Item;
import com.cognizant.shoppingcart.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController
{
    private ItemRepository repository;

    public ShoppingCartController(@Autowired ItemRepository repository) {
        this.repository = repository;
    }




}
