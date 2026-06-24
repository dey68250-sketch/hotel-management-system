# Real World Hotel Management System

## Overview

A full-stack Hotel Management System built with Java, Spring Boot, Spring Security, JWT Authentication, Hibernate, and MySQL. This application helps hotel administrators manage rooms, bookings, payments, and customer information efficiently.

## Features

### Authentication & Authorization

* Admin Registration and Login
* JWT-Based Authentication
* Secure REST APIs using Spring Security
* Role-Based Access Control

### Room Management

* Create Room
* Update Room Details
* Delete Room
* View All Rooms
* Check Room Availability
* Upload Room Images

### Booking Management

* Create Booking
* View Booking Details
* Cancel Booking
* Booking Status Tracking
* Room Availability Validation

### Payment Management

* Payment Processing
* Payment Status Tracking
* Payment History
* Booking Receipt Generation

### Dashboard

* Total Rooms
* Available Rooms
* Active Bookings
* Revenue Insights

### Receipt Generation

* Booking Receipt API
* Guest Information
* Room Information
* Payment Information

## Technology Stack

### Backend

* Java 24
* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* Maven

### Database

* MySQL

### Tools

* IntelliJ IDEA
* Git
* GitHub
* Postman

## Project Structure

src/main/java/com/hotel_management

├── Controller

├── config

├── entity

├── exception

├── payload

├── repository

├── security

├── service

└── HotelManagementApplication

## Database Entities

* Admin
* Room
* RoomImage
* Booking
* Payment

## API Modules

### Authentication APIs

* Register Admin
* Login Admin

### Room APIs

* Add Room
* Get All Rooms
* Get Room By ID
* Update Room
* Delete Room

### Booking APIs

* Create Booking
* Get Booking Details
* Cancel Booking

### Payment APIs

* Make Payment
* Get Payment Details

### Dashboard APIs

* Hotel Statistics
* Revenue Summary

## Installation

1. Clone the repository

git clone https://github.com/your-username/real_world_Hotel_Management.git

2. Configure MySQL database in application.properties

3. Run the application

./mvnw spring-boot:run

4. Access APIs using Postman

## Future Enhancements

* Online Payment Gateway Integration
* Email Notifications
* Customer Portal
* Room Reviews & Ratings
* Hotel Analytics Dashboard
* Multi-Role Authentication

## Author

Dipankar Dey

B.Tech Computer Science Engineering

Java Backend Developer
