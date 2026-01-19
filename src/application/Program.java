package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Department;
import entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Department obj = new Department(1, "Car");
			Seller seller = new Seller(1, "Leandro", "leandro@gmail.com", new Date(stf.parse("26/03/1979").getTime()), 
					5000.0, obj);
			
			System.out.println(obj);
			System.out.println(seller);
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		
		
		

	}

}
