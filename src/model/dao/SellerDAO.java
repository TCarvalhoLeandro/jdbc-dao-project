package model.dao;

import java.util.List;

import entities.Seller;

public interface SellerDAO {
	
	void insert(Seller seller);
	void update(Seller seller);
	void deleteById(int id);
	Seller findById(int id);
	List<Seller> findAll();

}
