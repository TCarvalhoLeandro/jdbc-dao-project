package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDAO;

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
		
		System.out.println("TESTE 01: Seller findById: ");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\nTESTE 02: Seller findByDepartment: ");
		List<Seller> sellerList = sellerDao.findByDepartment(3);
		for(Seller obj: sellerList) {
			System.out.println(obj);
		}
		

	}

}
