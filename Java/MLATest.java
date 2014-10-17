package test.java;
import static org.junit.Assert.assertEquals;
import main.java.MetroLinkStationStops;
import main.java.Stop;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;
import java.util.ArrayList;


public class MLATest {

	@Test
	public void allStopsContainTheWordsMetroLinkStation(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext();
		MetroLinkStationStops metroLinkStationStop = new MetroLinkStationStops();
		
		boolean hasMetroLinkStation = true;
		ArrayList<Stop> metroLinkStops = (ArrayList<Stop>) metroLinkStationStop.getAllStops();
		for(int i = 1, listLength = metroLinkStops.size(); i < listLength; i++){
			Stop stop = metroLinkStops.get(i);
			String stopName = stop.getStopName();
			if (stopName.contains("METROLINK STATION") == false) {
				hasMetroLinkStation = false;
			}
		}
		assertEquals(hasMetroLinkStation,true);
	}
}
