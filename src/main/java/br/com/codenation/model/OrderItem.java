package br.com.codenation.model;

import java.util.Objects;

public class OrderItem {

	private Long productId;
	private Long quantity;
	
	public OrderItem(Long productId, Long quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderItem orderItem = (OrderItem) o;
		return Objects.equals(productId, orderItem.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}
}
