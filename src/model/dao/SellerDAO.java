package model.dao;

import java.util.List;

import entities.Seller;

public interface SellerDAO {
	
	void insert(Seller Seller);
	void update(Seller Seller);
	void deleteById(int id);
	Seller findById(int id);
	List<Seller> findAll();

}

/*O que esse SellerDao faz???????*/
