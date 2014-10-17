package main.java;

import java.sql.Time;
import java.util.Calendar;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MetroLinkArrival {
	
	private String metroLinkStation;
	private String arrivalTime;
	
	public MetroLinkArrival(){}
	
	public void setMetroLinkStation(String metroLinkStation){
		this.metroLinkStation = metroLinkStation;
	}
	
	public String getNextArrivalTime(){
		try(ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applications.xml");){
			QueryMetroLink queryMetroLink = (QueryMetroLink)context.getBean("queryMetroLink");
			String sqlString = 	"SELECT arrival_time FROM metrolink_stops WHERE stop_name = '" + this.metroLinkStation + "' AND strftime('%s', arrival_time) > strftime('%s', time(datetime(CURRENT_TIMESTAMP, 'localtime'))) ORDER BY arrival_time limit 1;";
			this.arrivalTime = queryMetroLink.executeQuery(sqlString);
			return this.arrivalTime;
		}
		catch(Exception e){
			throw new RuntimeException(e.toString());
		}
	}
	
	public int getNextArrivalInMinutes(){
		long current_l = System.currentTimeMillis();
		long difference_l = calculateArrivalTimeInMilliseconds() - current_l;
		return Math.round(difference_l / (60 * 1000) % 60);
	}
	
	private long calculateArrivalTimeInMilliseconds(){
		Time arrival = Time.valueOf(this.arrivalTime);
		Calendar cDateAtStartOfToday = Calendar.getInstance();
		cDateAtStartOfToday.set(Calendar.HOUR_OF_DAY, 0); 
		cDateAtStartOfToday.set(Calendar.AM_PM, 0);
		cDateAtStartOfToday.set(Calendar.MINUTE, 0);
		cDateAtStartOfToday.set(Calendar.SECOND, 0);
		cDateAtStartOfToday.set(Calendar.MILLISECOND, 0);
		return arrival.getTime() + cDateAtStartOfToday.getTimeInMillis();
	}
}






