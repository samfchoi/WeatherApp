package mypackage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.github.dvdme.ForecastIOLib.FIOCurrently;
import com.github.dvdme.ForecastIOLib.FIODaily;
import com.github.dvdme.ForecastIOLib.ForecastIO;

/**
 * DataGenerator has a number of user-friendly methods for getting pertinent
 * weather information that builds upon the basic data that a weather API 
 * provides.
 */
public class DataGenerator 
{
	
	private ForecastIO generatorFio = null;

	/**
	 * Construct a DataGenerator object.
	 * 
	 * @param latitude  Latitude of the location.
	 * @param longitude Longitude of the location.
	 */
	public DataGenerator(String latitude, String longitude)
	{			
		generatorFio = initialize(latitude, longitude);
	}
	
	/**
	 * Uses the user's API key to connect to the ForecastIO API and returns a 
	 * ForecastIO object which will be used for extracting further weather data. 
	 * 
	 * @return ForecastIO This is the object returned after connecting to the 
	 * 		   weather API. It is the starting point for obtaining all weather
	 * 		   data.
	 */
	public ForecastIO initialize(String latitude, String longitude)
	{			
		Properties prop = new Properties();
		InputStream input = null;
		String propFile = "config.properties";
		String apiKey = "";
	 
		try 
		{	 
			// Load our config file to extract the apiKey. User is expected to
			// specify their own apiKey in config.properties.
			input = getClass().getClassLoader().getResourceAsStream(propFile);			
			prop.load(input);
	 
			// get the property value and print it out
			apiKey = prop.getProperty("apiKey");					
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
		} 
		finally 
		{
			if (input != null) 
			{
				try 
				{
					input.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}		
		
		// ForecastIO object connects us to the API service. When the ForecastIO
		// object is created, it expects your API key as the parameter. An API
		// key can be obtained here: https://developer.forecast.io/
		ForecastIO fio = new ForecastIO(apiKey);  
		
		// Optional attribute to set. Currently uses US units (e.g. Fahrenheit)		
		fio.setUnits(ForecastIO.UNITS_US);             		
		
		// Exclude the minutely and hourly reports from the reply since they
		// are not being used in this app.
		fio.setExcludeURL("hourly,minutely");             

		fio.getForecast(latitude, longitude);
	    	    
	    return fio;
	}
	 
	/**
	 * Returns the instance's ForecastIO or null if it has not been created. 
	 */
	public ForecastIO getForecastIO()
	{
		if(generatorFio != null)
		{
			return generatorFio;
		}
		
		System.out.println("ForecastIO object is null! initialize() first.");
		return null;
	}
	
	/**
	 * Set the ForecastIO's location.
	 * 
	 * @param latitude  Latitude of the location.
	 * @param longitude Longitude of the location.
	 */
	public void setLocation(String latitude, String longitude)
	{
		if(generatorFio != null)
		{
			generatorFio.getForecast(latitude, longitude);			
		}
		else
		{
			System.out.println("ForecastIO object is null! initialize() first.");
		}				
	}
	
	/**
	 * Print information about the current location.
	 */
	public void printLocation()
	{
		if(generatorFio != null)
		{			
			System.out.println("\nPrinting current location information.");
			System.out.println("Latitude: "+ generatorFio.getLatitude());
		    System.out.println("Longitude: "+ generatorFio.getLongitude());
		    System.out.println("Timezone: "+ generatorFio.getTimezone());			
		}
		else
		{
			System.out.println("ForecastIO object is null! initialize() first.");
		}			
	}
	
	/**
	 * Print out data for the current weather conditions. 
	 */
	public void printCurrentWeather()
	{
		System.out.println("\nCurrent Weather\n");
		
	    // FIOCurrently object contains data for current weather conditions
	    FIOCurrently currently = new FIOCurrently(generatorFio);	  	    
	    String [] currentWeather  = currently.get().getFieldsArray();
	    
	    // Print all key value pairs for current weather data
	    for(int i = 0; i < currentWeather.length; i++)
	    {
	        System.out.println(currentWeather[i] + ": " +
	                           currently.get().getByKey(currentWeather[i]));
	    }
	    
	}
	
	/**
	 * Print out data for the forecast weather conditions.
	 *	 
	 * @param maxDays Maximum number of days to retrieve for the forecast. The
	 * 				  underlying API supports a maximum of 8 days.
	 */
	public void printForecastWeather(int maxDays)
	{	
		if((maxDays < 1) || (maxDays > 8))
		{
			System.out.println("Please specify between 1 and 8 days for the " +
							   "forecast.");
			return;
		}
		
	    FIODaily daily = new FIODaily(generatorFio);
	    int daysForecast = daily.days();
	    
	    // The number of days forecast should be the lesser of the maxDays 
	    // specified by the user or the total number of daysForecast that is 
	    // available.
	    if(maxDays < daysForecast)
	    {
	    	daysForecast = maxDays;
	    }
	    
	    //In case there is no daily data available
	    if(daysForecast < 0)
	    {
	        System.out.println("No daily data.");
	        return;
	    }
	    else
	    {
	        System.out.println("\nDaily forecast for next " + daysForecast + 
	        		           " days \n");
	    }
	    
	    // Print forecast weather. Print each field, for each day.
	    for(int i = 0; i < daysForecast; i++)
	    {
	        String [] currentFields = daily.getDay(i).getFieldsArray();
	        System.out.println("Day #"+(i + 1));
	        for(int j = 0; j < currentFields.length; j++)
	        {
	            System.out.println(currentFields[j]+": "+daily.getDay(i).getByKey(currentFields[j]));
	        }
	        System.out.println("\n");
	    }	    
	}

}
