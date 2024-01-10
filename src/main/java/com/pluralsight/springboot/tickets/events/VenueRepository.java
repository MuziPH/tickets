package com.pluralsight.springboot.tickets.events;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VenueRepository {
    private  final List<Venue> venues = List.of(
            new Venue(201,"Globomantics Main Office","325 Test Street", "New York","USA"),
            new Venue(202,"Sea View Hotel","863 Beach Boulevard","Los Angeles","USA")
    );

    public Optional<Venue> findById(int id){
        return venues.stream().filter(venue -> venue.id() == id).findAny();
    }
}
