package team.unstudio.minigameapi.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import team.unstudio.minigameapi.MiniGameAPI;
import team.unstudio.minigameapi.data.property.TableProperty;

public class SQL {
	private Connection conn;
	private String schema;
	private String table;
	private boolean useMySQL;
	private File SQLFile;
	/**
	 * 
	 * 采用MySQL的实例化方式
	 * @param host
	 * 			 SQL的HOST
	 * @param port
	 *           SQL的PORT
	 * @param user
	 *           SQL用户名
	 * @param password
	 *           SQL用户密码
	 * @param schema
	 *           数据库名
	 * @param table
	 *           表名
	 * @throws ClassNotFoundException
	 * @throws SQLException 
	 */
	public SQL(String host,String port,String user,String password,String schema,String table) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port, user, password);
		this.schema = schema;
		this.table = table;
		this.useMySQL = true;
	}
	/**
	 * 
	 * 不采用MySQL时的实例化方式
	 * @param schema
	 * 				数据库名
	 * @param table
	 * 				表名
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public SQL(String schema,String table,boolean create) throws ClassNotFoundException, SQLException{
		Class.forName("org.sqlite.JDBC");
		this.table = table;
		this.schema = schema;
		this.SQLFile = new File(MiniGameAPI.INSTANCE.getDataFolder()+"\\DataBase",schema+".db");
		if (create && !SQLFile.exists()){
			try{
				SQLFile.createNewFile();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		this.conn = DriverManager.getConnection("jdbc:sqlite:"+SQLFile);
	}
	
	public boolean isTableExists(){
		try{
			PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM "+this.schema+"."+this.table);
		
			return stmt.execute();
		} catch(Exception e){
			return false;
		}
	}
	
	public boolean isSchemaExists(){
		if (this.useMySQL){
			try{
				PreparedStatement stmt = this.conn.prepareStatement("SHOW SCHEMAS LIKE "+this.schema);
				ResultSet rt = stmt.executeQuery();
				if (rt.next()){
					return true;
				} else return false;
			} catch(Exception e){
				return false;
			}
		} else {
			return SQLFile.exists();
		}
	}
	
	public boolean createSchema() throws SQLException{
		PreparedStatement stmt = this.conn.prepareStatement("CREATE DATABASE "+this.schema);
		return stmt.execute();
	}
	
	public boolean createTable(String table, TableProperty... properties) throws SQLException{
		StringBuilder code = new StringBuilder("CREATE TABLE "+table+" {");
		for (TableProperty property : properties){
			code.append(property.getCode()+",");
		}
		code.delete(code.length()-1, code.length());
		code.append("}");
		PreparedStatement stmt = this.conn.prepareStatement(code.toString());
		return stmt.execute();
	}
	
}
