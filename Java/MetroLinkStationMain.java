import java.util.ArrayList;

import main.java.MetroLinkArrival;
import main.java.MetroLinkStationStops;
import main.java.PromptLocalStation;
import main.java.Stop;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MetroLinkStationMain {
	
	public static void main(String[] args){
		
		try(ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applications.xml");){
			MetroLinkStationStops mlsStop = (MetroLinkStationStops)context.getBean("metroLinkStationStops");	
			ArrayList<Stop> metroLinkStops = (ArrayList<Stop>) mlsStop.getAllStops();
			for(int i = 1, listLength = metroLinkStops.size(); i < listLength; i++){
				Stop stop = metroLinkStops.get(i);
				System.out.println(stop.getStopName());
			}
		
			PromptLocalStation promptLocalStation = (PromptLocalStation)context.getBean("promptLocalStation");		
			MetroLinkArrival trainArrival = (MetroLinkArrival)context.getBean("metroLinkArrival");
			trainArrival.setMetroLinkStation(promptLocalStation.promptUserForLocalStaton());
			System.out.println("The next train will arrive at " + trainArrival.getNextArrivalTime() + " (approximately " + trainArrival.getNextArrivalInMinutes() + " minutes from now).");
		}
		catch(Exception e){
			throw new RuntimeException(e.toString());
		}
	}
	
}
