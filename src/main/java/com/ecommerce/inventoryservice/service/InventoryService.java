package com.ecommerce.inventoryservice.service;

import java.util.List;

import com.ecommerce.inventoryservice.model.InventoryDto;

public interface InventoryService {

	List<InventoryDto> getAllInventory();

	InventoryDto getInventoryById(long id);

	InventoryDto createInventory(InventoryDto inventory);

	InventoryDto updateInventory(long id, InventoryDto inventory);

	void deleteInventory(long id);

	List<InventoryDto> getInventoryByProductId(long productId);
}
