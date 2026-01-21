package application;

import java.util.Date;
import java.util.List;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.dao.implement.SellerDaoJDBC;

public class Program {

	public static void main(String[] args) {
		
		/*SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Department obj = new Department(1, "Car");
			Seller seller = new Seller(1, "Leandro", "leandro@gmail.com", new Date(stf.parse("26/03/1979").getTime()), 
					5000.0, obj);
			
			System.out.println(obj);
			System.out.println(seller);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}*/
		
		/* Aqui eu estou instanciando a interface, dessa forma o meu
		 * programa nao conhece a implementacao de acesso a dados no banco. 
		 * Ele conhece somente a interface e é também uma forma da gente 
		 * fazer uma injecao de dependencia sem explicar a implementacao*/
		SellerDAO sellerDao = DaoFactory.createSellerDao();
		
		/*System.out.println("TESTE 01: Seller findById: ");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\nTESTE 02: Seller findByDepartment: ");
		List<Seller> sellerList = sellerDao.findByDepartment(3);
		for(Seller obj: sellerList) {
			System.out.println(obj);
		}
		
		System.out.println("\nTESTE 03: Seller findAll: ");
		List<Seller> sellerList2 = sellerDao.findAll();
		for(Seller obj: sellerList2) {
			System.out.println(obj);
		}*/
		
		System.out.println("\nTESTE 04: Seller insert: ");
		Department dep5 = new Department(2, null);
		Seller seller2 = new Seller(null, "Simão Bacamarti", "simao@gmail.com", new Date(), 5000.0, dep5); 
		sellerDao.insert(seller2);
		System.out.println("Insert new seller id: " + seller2.getId());
		
		
		
		System.out.println("\nTESTE 05: Seller update: ");
		Seller seller3 = sellerDao.findById(1);
		seller3.setName("Homem Aranha");
		seller3.setEmail("aranha@gmail.com");
		seller3.setDepartment(new Department(4, null));
		sellerDao.update(seller3);
		System.out.println("Update!!");
		
		/*
		System.out.println("\nTESTE 06: Seller deleteById: ");
		sellerDao.deleteById(16);*/
		
		
		

	}

}
