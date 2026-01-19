package model.dao;

import db.DB;
import model.dao.implement.SellerDaoJDBC;

public class DaoFactory {
	
	/* Esse metodo retorna o tipo da interface mas internamente ela vai instanciar uma implementação
	 * Isso é um macete para nao precisar expor a implementação*/
	public static SellerDAO createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}

	
	
}
