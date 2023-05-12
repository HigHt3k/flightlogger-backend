# smart-home-integration-app
A free-time project I started mostly to get more into Spring and Bootstrap :)

### The App
This app is going to be an integrated, all-in-one smart-home solution
to monitor and control different sensor or devices such as lamps and more.
Furthermore, there is a database with houseplants in the background to notify
the user about necessary waterings based on the standard watering
cycle and in the future taking into consideration current
temperature, soil wetness and other factors.

### Setup
Please note, that this app will not work correctly/completely on your machine unless you are willing to set up a local
database and register for all the necessary api keys.

The following would be needed to do:

add to properties:
```
weather.api.key={your openweatherapi key}
hue.api.key={your philips hue api key}
hue.bridge.ip={your philips hue bridge ip}
spring.datasource.url={database url}
spring.datasource.username={username for database}
spring.datasource.password={password for database user}
```
set up MySQL Database:
```sql
CREATE database;
    
USE database;
    
CREATE 'user' IDENTIFIED BY 'password';

GRANT ALL on database.* to 'user';
    
CREATE TABLE houseplants (
    id(int),
    name(varchar(255)),
    species(varchar(255)),
    location(varchar(255)),
    direction(varchar(255)),
    last_fertilized(datetime(6)),
    last_watered(datetime(6)),
    next_fertilizing(datetime(6)),
    next_watering(datetime(6)),
    preferred_temperature(int),
    sunlight_needs(varchar(255)),
    watering_cycle(int),
    water_needed(tinyint(1))
);
```