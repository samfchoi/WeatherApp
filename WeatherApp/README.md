# WeatherApp

### Notes
- While having an app that can directly take an address/zip code would be ideal,
 the well-known, reliable APIs that perform geocoding appear to be paid services
 or have restrictions on usage. Thus, they were not suitable for this particular
 proof-of-concept app.
- The app currently just provides a convenience API that essentially lets a 
 user create a weather report of their liking. Of course, it would make much 
 more sense to use these APIs as the backend of a mobile or web app if it were
 a product.

 ### Usage
 - User will need to get an API key from https://developer.forecast.io/ and
 add it to config.properties.
 - The Java weather API used was tested on Java 1.7. Thus, this project was also
 tested against Java 1.7.
 - Maven is used for dependencies.