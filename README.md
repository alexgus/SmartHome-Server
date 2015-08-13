# SmartHome-Server
Development project at UTBM

Full project and documentation [HERE !](https://github.com/alexgus/SmartHome "Doc")

## Introduction

This project intends to connect your calendars with your morning's alarm. Calendars can be :
 * ical stream
 * google calendar (not supported yet, or via ical stream)
 * caldav (not supported yet)

## Alarm
You can use Raspberry Pi for this server and [alarm project](https://github.com/alexgus/SmartHome-Clock) but it can also be installed in any UNIX/LINUX environment.

## Configuration
Fully configurable with [smart.conf](https://github.com/alexgus/SmartHome-Server/blob/master/smart.conf "Smart.conf file") file

## Developer information
### Compilation
Just type `mvn install` at the root of this project. The executale jar file will be created in `target` folder with all its dependencies (required to run it corrrectly).

For full installation script and dependencies look [here.](https://github.com/alexgus/SmartHome "Doc installation")

### library used
 * cron4j
 * ical4j
 * ical4j-connector
 * google-api-services-calendar
 * google-api-services-gmail
 * org.eclipse.paho.client.mqttv3
 
see pom.xml for full list

### Server
Receiving command MQTT and simple TCP connection :
 * `AddRing [AAAA-MM-DDHhhMmm]` : command for adding event with
     * `AAAA` : the year
     * `MM` : the month
     * `DD` : the day of the month
     * `hh` : the hour (24 hours format)
     * `mm` : the minute
 * `Ring` command for trigger the alarm

### How it works
Calendar are fetched and lines correponding to the wake up time (time of the fisrt event of the day - time choosen in configuration file) are placed in a crontab. The command corresponding to this line is a TCP frame send to the server for waking up the server. Once the frame is received by it, it publish `Ring` on MQTT topic choosen in the configuration file. 
