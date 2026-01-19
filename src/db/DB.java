package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// METODOS PARA CONECTAR E DESCONECTAR O BANCO DE DADOS

public class DB {
	
	private static Connection conn = null;// Objeto de conexao com o Banco de Dados do jdbc
	

	// METODO PARA CARREGAR O ARQUIVO db.properties
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")){
			
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	// METODO PARA CONECTAR COM O BANCO
	public static Connection getConnection() {
		
		if(conn == null) {
			try {
				Properties props = loadProperties();// peguei as propriedades do banco de dados
				
				String url = props.getProperty("dburl");// peguei a url do banco
				
				conn = DriverManager.getConnection(url, props);// conectamos com o banco de dados
				
				//Conectar com o banco de dados pelo jdbc é instanciar um objeto do tipo Connection
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	
	// METODO PARA FECHAR A CONEXAO
	public static void closseConnection() {
		
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	// METODO PARA FECHAR STATEMENT
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
	}
	
	//METODO PARA FECHAR RESULTSET
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
	
}


