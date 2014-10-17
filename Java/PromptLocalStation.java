package main.java;

import java.util.Scanner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PromptLocalStation {
	
	public PromptLocalStation(){};
	
	public String promptUserForLocalStaton(){
		
		String station = null;
		do{
			System.out.println("Please enter your local station: ");
			Scanner scanner = new Scanner(System.in);
			try(ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applications.xml");){
				QueryMetroLink queryMetroLink = (QueryMetroLink)context.getBean("queryMetroLink");
				String sqlString = "SELECT stop_name FROM stops WHERE stop_name LIKE '" + scanner.nextLine() + "'";
				station = queryMetroLink.executeQuery(sqlString);
				scanner.close();
			}
			catch(Exception e){
				throw new RuntimeException(e.toString());
			}
		} while(station == null);
		
		return station;
	}
}