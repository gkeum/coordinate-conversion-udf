# coordinate-conversion-udf
Starburst user defined function to convert between MGRS and Latitude/Longitude values

## Introduction
Package of two functions: 
- ```mgrs_to_latlong('<sample_mgrs>')```: converts from MGRS (string/varchar) to Latitude/Longitude (string/varchar with delimiter)
- ```latlong_to_mgrs(<sample_latitude>, <sample_longitude>)```: converts from Latitude/Longitude (double) to MGRS (string)

## Build
Requirements:
- java 17
- maven 3.9.7+

Run following to regenerate target folder:
```
mvn clean install
```

If you want to skip unit tests:
```
mvn clean install -DskipTests
```

## Deploy
Copy the coordinate-conversion-function-{version} folder from target directory in your Trino plugin directory and restart Trino server.

## Usage
Call functions in query editor via followiing formats:
- mgrs_to_latlong('<sample_mgrs>'): Outputs as string/varchar with ", " delimiter
- latlong_to_mgrs(<sample_latitude>, <sample_longitude>): Outputs as string/varchar

To get mgrs_to_latlong() output as double or numerical values, use the following formatting:
```sql
select cast(split(mgrs_to_latlong('33XVG7459043590'), ', ')[0] AS double) as latitude,
    cast(split(mgrs_to_latlong('33XVG7459043590'), ', ')[1] AS double) as longitude
```


