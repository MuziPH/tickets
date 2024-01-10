package com.pluralsight.springboot.tickets.registration;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/registrations")
public class RegistrationController {
    private final RegistrationRepository registrationRepository;

    public RegistrationController(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @PostMapping
    public Registration create(@RequestBody @Valid Registration registration) {
        // Generate the ticket code
        String ticketCode = UUID.randomUUID().toString();

        return registrationRepository.save(new Registration(null, registration.productId(), ticketCode, registration.attendeeName()));
    }

    @GetMapping(path = "/{ticketCode}")
    public Registration get(@PathVariable("ticketCode") String ticketCode) {
        return registrationRepository.findByTicketCode(ticketCode).orElseThrow(
                () -> new NoSuchElementException("Registration with ticket code " + ticketCode + " not found")
        );
    }

    @PutMapping
    public Registration update(@RequestBody Registration registration) {
        // Look up the registration by ticket code
        String ticketCode = registration.ticketCode();
        Registration existing = registrationRepository.findByTicketCode(ticketCode).orElseThrow(
                () -> new NoSuchElementException("Registration with ticket code " + ticketCode + " not found")
        );

        // Only update the attendee name
        return registrationRepository.save(
                new Registration(existing.id(), existing.productId(), ticketCode, existing.attendeeName())
        );
    }

    @DeleteMapping(path = "/{ticketCode}")
    public void delete(@PathVariable("ticketCode") String ticketCode) {
        registrationRepository.deleteByTicketCode(ticketCode);
    }
}
