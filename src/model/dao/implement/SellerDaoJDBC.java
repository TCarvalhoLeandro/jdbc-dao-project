package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import entities.Department;
import entities.Seller;
import model.dao.SellerDAO;

/*Essa classe vai implementar a interface SellerDAO com os 
 * metodos necessarios para manipulacao no banco de dados*/

public class SellerDaoJDBC implements SellerDAO{
	
	/*SellerDaoJDBC vai ter uma dependencia com a conexao
	 * Que é forçadda atraves do construtor do Connection*/
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {// Forçando a dependencia
		this.conn = conn;
	}
	
	

	@Override
	public void insert(Seller Seller) {
		
		
		
	}

	@Override
	public void update(Seller Seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {	
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();// O comando SQL vai ser executado e vai retornar um ResultSet
			
			/*O ResultSet começa apontando para a posição zero da tabela do banco
			 * Então o if abaixo testa se veio algum valor diferente zero da consulta ao banco
			 * tem um objeto no banco e fazemos a instanciação*/
			if(rs.next()) {
				
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				
				Seller seller = new Seller();
				seller.setId(rs.getInt("Id"));
				seller.setName(rs.getString("Name"));
				seller.setEmail(rs.getString("Email"));
				seller.setBirthDate(rs.getDate("BirthDate"));
				seller.setBaseSalary(rs.getDouble("BaseSalary"));
				seller.setDepartment(dep);
				
				return seller;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
