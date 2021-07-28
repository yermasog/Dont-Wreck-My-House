# Don't Wreck My House Plan

## High Level Requirements
The application user is an accommodation administrator. They pair guests to hosts to make reservations.

* The administrator may view existing reservations for a host. 
* The administrator may create a reservation for a guest with a host.
* The administrator may edit existing reservations.
* The administrator may cancel a future reservation.

## Technical Requirements 
* Must be a Maven project.
* Spring dependency injection configured with either XML or annotations.
* All financial math must use BigDecimal.
* Dates must be LocalDate, never strings.
* All file data must be represented in models in the application.
* Reservation identifiers are unique per host, not unique across the entire application. Effectively, the combination of a reservation identifier and a host identifier is required to uniquely identify a reservation.

## File Structure
https://github.com/yermasog/Dont-Wreck-My-House/projects/1