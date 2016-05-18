#Band-Tracker
##A practice webpage for Epicodus, 5/15/2016

###by Ryan Harvey

##Description

_This is a fictional website for tracking venues, bands and concerts designed to utilize classes and databases._

##Setup

_Clone this repository_

_Navigate to your database software in your terminal and execute the following commands:_

_CREATE DATABASE band_tracker;_
_CREATE TABLE venues(id serial PRIMARY KEY, venue_name varchar);_
_CREATE TABLE bands(id serial PRIMARY KEY, band_name varchar);_
_CREATE TABLE bands_venues(id serial PRIMARY KEY, band_id int, venue_id int);_
_CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;_

_Make sure that you have Gradle installed on your computer_

_Navigate to the home folder in your terminal and type 'gradle run' then press enter._

_Go to your web browser and navigate to http://localhost:4567_

##Technologies Used

Java Spark Gradle Velocity JUnit FluentLenium PostGreSQL SQL HTML CSS Bootstrap

##Legal

Copyright (c) 2016 Ryan Harvey
