package application;

import java.util.ArrayList;
import java.util.List;

import entities.Department;
import model.dao.DaoFactory;
import model.dao.DepartmentDAO;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDAO depDao = DaoFactory.createDepartmentDao();
		
		/*
		System.out.println("\nTESTE 01: Department insert: ");
		Department dep = new Department(0, "Food");
		depDao.insert(dep);
		System.out.println("Insert new department id: " + dep.getId());
		
		
		System.out.println("TESTE 02: Department findById: ");
		Department dep2 = depDao.findById(3);
		System.out.println(dep2);
		
		
		System.out.println("\nTESTE 03: Department update: ");
		Department dep3 = depDao.findById(1);
		dep3.setName("Music");
		depDao.update(dep3);
		System.out.println("Update!!");
		
		System.out.println("\nTESTE 04: Departement deleteById: ");
		depDao.deleteById(6);
		System.out.println("DELETE!");*/
		
		System.out.println("\nTESTE 05: Department findAll: ");
		List<Department> list = depDao.findAll();
		for(Department dep4: list) {
			System.out.println(dep4);
		}
		

	}

}
