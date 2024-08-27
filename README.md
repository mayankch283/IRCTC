# IRCTC-Like Ticket Booking System

This project is a simplified backend system for a train ticket booking application, inspired by the IRCTC platform. It simulates essential features of a real-world ticket booking system, allowing users to sign up, log in, search for trains, book seats, and manage their bookings.
Features

* User Management:
    Sign up for a new account.
    Log in to an existing account using a username and password.
* Train Search:
    Search for trains between a source and a destination station.
    View train details, including train ID and station-wise timings.
* Booking Management:
    View available seats on a selected train.
    Book a seat by selecting the desired row and column.
    Fetch and display all bookings made by the user.
    Cancel a booking if necessary.

How It Works

* User Sign Up & Login:
    New users can sign up by providing a username and password. The password is hashed for secure storage.
    Existing users can log in to access the booking features.

* Train Search:
    Users can search for trains by specifying the source and destination stations.
    The system returns a list of available trains with detailed station timings.

* Seat Booking:
    Users can view and select available seats on their chosen train.
    The system checks the availability and books the seat if it's free.

* Booking Management:
    Users can view their booking history, see the trains they have booked, and manage their reservations.
