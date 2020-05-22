package br.com.codenation.service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.codenation.data.ProductsMockedData;
import br.com.codenation.model.OrderItem;
import br.com.codenation.model.Product;
import br.com.codenation.repository.ProductRepository;
import br.com.codenation.repository.ProductRepositoryImpl;
import jdk.javadoc.internal.doclets.toolkit.util.Utils;

public class OrderServiceImpl implements OrderService {

	private ProductRepository productRepository = new ProductRepositoryImpl();

	/**
	 * Calculate the sum of all OrderItems
	 */
	@Override
	public Double calculateOrderValue(List<OrderItem> items) {

		Double comDesc= filterOrders(items,true) *0.8;

		Double semDesc=filterOrders(items,false) ;

		return comDesc+semDesc;
	}

	/**
	 * Map from idProduct List to Product Set
	 */
	@Override
	public Set<Product> findProductsById(List<Long> ids) {
		return ids.stream().map(productRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
	}/**
	 * Calculate the sum of all Orders(List<OrderIten>)
	 */
	@Override
	public Double calculateMultipleOrders(List<List<OrderItem>> orders) {
		return orders.stream().mapToDouble(this::calculateOrderValue).sum();
	}

	/**
	 * Group products using isSale attribute as the map key
	 */
	@Override
	public Map<Boolean, List<Product>> groupProductsBySale(List<Long> productIds) {
		return productIds.stream().map(productRepository::findById).filter(Optional::isPresent)
						 .map(Optional::get).collect(Collectors.groupingBy(p->p.getIsSale()));
	}

	public Double filterOrders(List<OrderItem> items,boolean x){
		return items.stream().filter(p->productRepository.findById(p.getProductId()).get().getIsSale()==x)
				.mapToDouble(p->p.getQuantity()*productRepository.findById(p.getProductId()).get().getValue()).sum();
	}
}