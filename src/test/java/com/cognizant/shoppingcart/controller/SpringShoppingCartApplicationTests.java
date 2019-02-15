package com.cognizant.shoppingcart.controller;


import com.cognizant.shoppingcart.item.model.Item;
import com.cognizant.shoppingcart.item.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringShoppingCartApplicationTests {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	private MockMvc mvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void contextLoads()
	{
	}


	@Test
	public void getAllItemsTest() throws Exception {
		// Setup
		String expectedName = "Apple watch";
		BigDecimal expectedPrice = new BigDecimal("500.00");
		Item expectedItem = new Item(expectedName, expectedPrice, false);

		itemRepository.save(expectedItem);

		//	userRepository.save(expectedUser);

		String response = mvc.perform(get("/api/item"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		List<Item> actual = objectMapper.readValue(response,
				new TypeReference<List<Item>>() {
				});

		assertEquals("GET call to /api/item should return one item.",
				1, actual.size());

		Item actualItem = actual.get(0);
		assertEquals("GET response should match the record in the database.",
		expectedItem, actualItem);
	}

	@Test
	public void createItemTest() throws Exception {
		//Setup

		String expectedName = "Apple TV";
		Item mockItem = new Item(expectedName, new BigDecimal("800.00"), false);
		String mockJSON = objectMapper.writeValueAsString(mockItem);

		// Exercise
		MvcResult mockResult = mvc.perform(post("/api/item").contentType(MediaType.APPLICATION_JSON).content(mockJSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name").value(expectedName))
				.andReturn();

		String mockResultContent = mockResult.getResponse().getContentAsString();
		Item actualItem = objectMapper.readValue(mockResultContent, new TypeReference<Item>(){});
		String actualName = actualItem.getName();

		// Assert

		assertEquals("Newly created Item name should equal ".concat(expectedName),
				expectedName, actualName);
	}


	@Test
	public void updateItemTest() throws Exception {
		// Setup
		String expectedName = "Apple TV";
		Item mockItem = new Item(expectedName, new BigDecimal( "900.00"), false);
		itemRepository.save(mockItem);
		Integer newItemId = mockItem.getId();

		String mockJSON = objectMapper.writeValueAsString(mockItem);

		MvcResult mockResult = mvc.perform(post("/api/item").contentType(MediaType.APPLICATION_JSON).content(mockJSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name").value(expectedName))
				.andReturn();

		String mockResultContent = mockResult.getResponse().getContentAsString();
		Item  actualItem = objectMapper.readValue(mockResultContent, new TypeReference<Item>(){});
		String actualName = actualItem.getName();

		//Assert
		assertEquals(" Item should be Updated".concat(expectedName),
		expectedName, actualName);

	}

	@Test
	public void deleteItemTest() throws Exception{
		//Setup

		String expectedName = "Apple TV";
		Item mockItem = new Item(expectedName, new BigDecimal("1000.00"), false);
		itemRepository.save(mockItem);
		Integer newItemId = mockItem.getId();

		// Exercise
		mvc.perform(delete("/api/item/".concat(newItemId.toString())))
				.andExpect(status().isOk())
				.andReturn();

		//Assert
		assertEquals(" Item should be Deleted".concat(expectedName), itemRepository.findById(newItemId), Optional.empty());
	}



	}
