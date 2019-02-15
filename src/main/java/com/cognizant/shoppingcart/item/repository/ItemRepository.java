package com.cognizant.shoppingcart.item.repository;

import com.cognizant.shoppingcart.item.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ItemRepository extends CrudRepository<Item, Integer> {


}
