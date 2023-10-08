package com.ecommerce.inventoryservice.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String sku;

	@NotNull
	private Long productId;

	@NotBlank
	private String vendor;

	@NotNull
	private Integer vendorInventory;

	@NotNull
	private BigDecimal vendorPrice;

	public Inventory updateWith(Inventory inventory) {
		return new Inventory(this.id, inventory.sku, inventory.productId, inventory.vendor, inventory.vendorInventory,
				inventory.vendorPrice);
	}
}
