package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	//METODO PARA INSERIR SELLER 
	@Override
	public void insert(Seller seller) {
		
		PreparedStatement st = null;
				
		try {
		st = conn.prepareStatement(
				  "INSERT INTO seller "
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {// se for maior que zero significa que inseriu
				
				//Essa linha serve para recuperar o ID (chave primária) que o 
				//banco de dados acabou de criar automaticamente.
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);// Preenche o id do objeto inserido com o id que foi gerado pelo banco
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error!!");
			}
		
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
		
	}

	@Override
	public void update(Seller Seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	//METODO PARA BUSCAR UM VENDEDOR POR ID
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
				
				Department dep = instanciaDepartment(rs);
				return instanciaSeller(rs, dep);
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

	
	// METODO PARA BUSCAR TODOS OS VENDEDORES
	@Override
	public List<Seller> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
									  "SELECT seller.*,department.Name as DepName "
									+ "FROM seller INNER JOIN department "
									+ "ON seller.DepartmentId = department.Id "
									+ "ORDER BY Name");
			rs = st.executeQuery();
			
			List<Seller> sellerList = new ArrayList<Seller>();
			
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instanciaDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instanciaSeller(rs, dep);
				sellerList.add(seller);
			}
			return sellerList;
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	
	// METODO PARA BUSCAR VENDEDORES POR DEPARTAMENTO
	@Override
	public List<Seller> findByDepartment(int id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					  "SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			List<Seller> sellerList = new ArrayList<Seller>();
			
			// Esse Map está sendo implementado para não instanciarmos varios Department iguais, já que
			// um Department pode ter varios vendedores no mesmo Department 
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			
			while(rs.next()) {
				
				// Aqui to tentando ver se existe no Map um Department com o DepartmentId passado pelo rs
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				// Se o DepartmentId passado pelo rs não existir no Map vai retornar null e entao eu 
				// instancio um Department e adiciono o Department passado pelo rs no Map
				if(dep == null) {
					dep = instanciaDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instanciaSeller(rs, dep);
				sellerList.add(obj);
			}
			return sellerList;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	

	/*METODO PARA INSTANCIAR UM DEPARTMENT A PARTIR DO BANCO DE DADOS*/
	public Department instanciaDepartment(ResultSet rs) throws SQLException {
		
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		
		return dep;
	}
	
	/*METODO PARA INSTANCIAR UM SELLER A PARTIR DO BANCO DE DADOS*/
	public Seller instanciaSeller(ResultSet rs, Department dep) throws SQLException {
		
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		
		return seller;
	}

}
