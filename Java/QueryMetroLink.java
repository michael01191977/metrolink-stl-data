package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QueryMetroLink {
	
	public static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";
	public static final String JDBC_SQLITE_METROLINK_DB = "jdbc:sqlite:Resources/metrolink.db";
	
	public QueryMetroLink(){};
	
	public String executeQuery(String sqlString){
		String resultString = null;
		try(Connection connection = getConnection();){
			PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				resultString = resultSet.getString(1).toString();
			}
		}
		catch(SQLException e){
			throw new RuntimeException(e.toString());
		}
		
		return resultString;
	}
	
	public List<Stop> executeQuery(String sqlString, Stop stop){
		
		List<Stop> stops = new ArrayList<>();
		try(Connection connection = getConnection();){
			PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				try(ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applications.xml");){
					stop = (Stop)context.getBean("stop");
					stop.setStopName(resultSet.getString("stop_name"));
					stops.add(stop);
				}
				catch(Exception e){
					throw new RuntimeException(e.toString());
				}
			}
		}
		catch(SQLException e){
			throw new RuntimeException(e.toString());
		}
		
		return stops;
	}
	
	private Connection getConnection() throws SQLException {
		try{
			Class.forName(ORG_SQLITE_JDBC);
		} catch (ClassNotFoundException e){
			throw new RuntimeException("Unable to find class for loading the database", e);
		}
		return DriverManager.getConnection(JDBC_SQLITE_METROLINK_DB);
	}
}
