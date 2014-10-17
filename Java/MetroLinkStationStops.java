package main.java;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MetroLinkStationStops {
	
	public MetroLinkStationStops(){};
	
	public List<Stop> getAllStops(){
		try(ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applications.xml");){
			QueryMetroLink queryMetroLink = (QueryMetroLink)context.getBean("queryMetroLink");
			List<Stop> stops = new ArrayList<>();
			String sqlString = ("SELECT stop_name FROM stops WHERE stop_name LIKE '%METROLINK STATION%' ORDER BY stop_name");
			Stop stop = (Stop)context.getBean("stop");
			stops = queryMetroLink.executeQuery(sqlString, stop);
			return stops;
		}
		catch(Exception e){
			throw new RuntimeException(e.toString());
		}
			
	}
}
