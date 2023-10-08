package com.ecommerce.inventoryservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.exception.EcommerceException;
import com.ecommerce.inventoryservice.model.InventoryDto;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<InventoryDto> getAllInventory() {
		List<InventoryDto> inventoryList = new ArrayList<>();
		inventoryRepository.findAll().forEach(inventory -> {
			inventoryList.add(mapper.map(inventory, InventoryDto.class));
		});
		return inventoryList;
	}

	@Override
	public InventoryDto getInventoryById(long id) {
		Optional<Inventory> inventory = inventoryRepository.findById(id);
		return (inventory.isPresent() ? mapper.map(inventory.get(), InventoryDto.class) : null);
	}

	@Override
	public InventoryDto createInventory(InventoryDto inventoryDto) {
		Inventory inventory = mapper.map(inventoryDto, Inventory.class);
		return mapper.map(inventoryRepository.save(inventory), InventoryDto.class);
	}

	@Override
	public InventoryDto updateInventory(long id, InventoryDto inventoryDto) {
		Optional<Inventory> updatedInventory = inventoryRepository.findById(id).map(existingInventory -> {
			Inventory inventory = mapper.map(inventoryDto, Inventory.class);
			return inventoryRepository.save(existingInventory.updateWith(inventory));
		});

		return (updatedInventory.isPresent() ? mapper.map(updatedInventory.get(), InventoryDto.class) : null);
	}

	@Override
	public void deleteInventory(long id) {
		if (getInventoryById(id) != null) {
			inventoryRepository.deleteById(id);
			LOGGER.info("Inventory deleted Successfully");
		} else {
			throw new EcommerceException("inventory-not-found", String.format("Inventory with id=%d not found", id),
					HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<InventoryDto> getInventoryByProductId(long productId) {
		List<InventoryDto> inventoryList = new ArrayList<>();
		inventoryRepository.findByProductId(productId).forEach(inventory -> {
			inventoryList.add(mapper.map(inventory, InventoryDto.class));
		});
		return inventoryList;
	}
}
