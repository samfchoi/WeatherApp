package mypackage;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.ForecastIO;

/** This class simply uses the DataGenerator to show examples of how to generate
 *  weather reports using DataGenerator's methods. 
 */ 
public class Test {

	public static void main(String[] args) 
	{		
		// San Jose, CA, USA 37.3333° N, 121.9000° W
		DataGenerator generator = new DataGenerator("37.3333", "-121.9000");
		WeatherAssistant assistant = new WeatherAssistant();
		generator.printLocation();
		ForecastIO fio = generator.getForecastIO();				
		generator.printCurrentWeather();
		//generator.printForecastWeather(2);	
		assistant.getRecommendations(fio);
				
		// Seattle, WA, USA 47.6097° N, 122.3331° W
		generator.setLocation("47.6097", "-122.3331");
		generator.printLocation();
		generator.printCurrentWeather();		
		assistant.getRecommendations(fio);
	}   	    	

}
