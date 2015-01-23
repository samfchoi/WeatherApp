package mypackage;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.ForecastIO;

/**
 * WeatherAssistant assesses current weather conditions for a given location
 * and returns various recommendations for the user (e.g. Bring an umbrella).
 */
public class WeatherAssistant 
{
	
	/**
	 *  Based on current weather, get recommendations on clothing and/or 
	 *  accessories to bring for the day (e.g. umbrella).
	 */	
	public static void getRecommendations(ForecastIO fio)	
	{
		// Set to US units to ensure that our calculations and recommendations
		// are correct.
		fio.setUnits(ForecastIO.UNITS_US); 
		FIOCurrently currently = new FIOCurrently(fio);	  
		double apparentTemp = currently.get().apparentTemperature();		
		double precipIntensity = currently.get().precipIntensity();
		
		System.out.println("\nCurrent recommendations");
		System.out.println("Conditions evaluated");
		System.out.println("apparentTemp (what it feels like outside): " +
		                   apparentTemp);
		System.out.println("precipIntensity: " + precipIntensity);
		
		// Recommendations for clothing
		if(apparentTemp < 45)
		{
			System.out.println("Thick, winter coat");
		}
		else if(apparentTemp < 60)
		{
			System.out.println("Medium jacket");
		}
		else if(apparentTemp < 65)
		{
			System.out.println("Light jacket");
		}
		else
		{
			System.out.println("Warm weather, no jacket needed!");
		}
		
		// Recommendations for accessories
		if(precipIntensity > 0)
		{
			System.out.println("Umbrella");
		}				
	}

}
