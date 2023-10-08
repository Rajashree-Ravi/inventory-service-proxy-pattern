package com.ecommerce.inventoryservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventoryservice.exception.EcommerceException;
import com.ecommerce.inventoryservice.model.InventoryDto;
import com.ecommerce.inventoryservice.service.InventoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage inventory in e-commerce application")
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping
	@ApiOperation(value = "View all inventory", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all inventory"),
			@ApiResponse(code = 204, message = "Inventory list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<InventoryDto>> getAllInventory() {

		List<InventoryDto> inventoryList = inventoryService.getAllInventory();
		if (inventoryList.isEmpty())
			throw new EcommerceException("no-content", "Inventory list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(inventoryList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific inventory with the specified inventory id", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved inventory with the inventory id"),
			@ApiResponse(code = 404, message = "Inventory with specified inventory id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<InventoryDto> getInventoryById(@PathVariable("id") long id) {

		InventoryDto inventory = inventoryService.getInventoryById(id);
		if (inventory != null)
			return new ResponseEntity<>(inventory, HttpStatus.OK);
		else
			throw new EcommerceException("inventory-not-found", String.format("Inventory with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new inventory", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a inventory"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventory) {
		return new ResponseEntity<>(inventoryService.createInventory(inventory), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a inventory information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated inventory information"),
			@ApiResponse(code = 404, message = "Inventory with specified inventory id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<InventoryDto> updateInventory(@PathVariable("id") long id,
			@RequestBody InventoryDto inventory) {

		InventoryDto updatedInventory = inventoryService.updateInventory(id, inventory);
		if (updatedInventory != null)
			return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
		else
			throw new EcommerceException("inventory-not-found", String.format("Inventory with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a inventory", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted inventory information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deleteInventory(@PathVariable("id") long id) {

		inventoryService.deleteInventory(id);
		return new ResponseEntity<>("Inventory deleted successfully", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/product/{id}")
	@ApiOperation(value = "Retrieve specific inventory with the specified product id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved inventory with the product id"),
			@ApiResponse(code = 404, message = "Inventory not available for specified product id"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<InventoryDto>> getInventoryByProductId(@PathVariable("id") long id) {

		List<InventoryDto> inventoryList = inventoryService.getInventoryByProductId(id);
		if (inventoryList.isEmpty())
			throw new EcommerceException("inventory-not-found",
					String.format("Inventory for the product id=%d not found", id), HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(inventoryList, HttpStatus.OK);
	}
}
