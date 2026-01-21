package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import entities.Department;
import model.dao.DepartmentDAO;

public class DepartmentDaoJDBC implements DepartmentDAO{

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	
	//METODO PARA INSERIR NO BANCO DE DADOS
	@Override
	public void insert(Department department) {
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(// Comandos SQL para inserir dados no banco
					  			  "INSERT INTO department "
								+ "(Name) "
								+ "VALUES (?)",
								Statement.RETURN_GENERATED_KEYS);
			
			// O banco vai setar o nome do departement pelo que vier do objeto department
			st.setString(1, department.getName());
			int rowsAffected = st.executeUpdate();// Executa o comando e retorna a quantidade de linhas afetadas
			if(rowsAffected > 0) {// se for maior que zero significa que inseriu
				
				//Essa linha serve para recuperar o ID (chave primária) que o 
				//banco de dados acabou de criar automaticamente.
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					department.setId(id);// Preenche o id do objeto inserido com o id que foi gerado pelo banco
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

	// METODO PARA ATUALIZAR DADOS DO BANCO 
	@Override
	public void update(Department department) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement( 
										  "UPDATE department "
										+ "SET Name = ? "
										+ "WHERE Id = ?");
			st.setString(1, department.getName());
			st.setInt(2, department.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(int id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
				
		
		
	}

	// METODO DE BUSCA NO BANCO POR ID
	@Override
	public Department findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dep = instanciaDepartment(rs);
				return dep;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*METODO PARA INSTANCIAR UM DEPARTMENT A PARTIR DO BANCO DE DADOS*/
	public Department instanciaDepartment(ResultSet rs) throws SQLException {
		
		Department dep = new Department();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		
		return dep;
	}

}
