# SmartHome-Server
Development project at UTBM

Full project and documentation [HERE !](https://github.com/alexgus/SmartHome "Doc")

## Introduction

This project intends to connect your calendars with your morning's alarm. Calendars can be :
 * ical stream
 * google calendar (not supported yet, or via ical stream)
 * caldav (not supported yet)

You can use Raspberry Pi for this server and [alarm project](https://github.com/alexgus/SmartHome-Clock) but it can also be installed in other UNIX/LINUX environment, in this case alarm can be installed on Raspberry Pi or any other as Arduino. 

Fully configurable with [smart.conf](https://github.com/alexgus/SmartHome-Server/blob/master/smart.conf "Smart.conf file") file

## Developer information
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
 * command for adding event
 * command for trigger the alarm
