package com.reikitubienestar.reiki_rest.application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long id;

    @NotBlank(message = "Customer first name is mandatory")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s'-]+$", message = "First name must contain only letters and valid characters")
    private String firstName;

    @NotBlank(message = "Customer last name is mandatory")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ\\s'-]+$", message = "Last name must contain only letters and valid characters")
    private String lastName;

    @NotBlank(message = "Customer email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Customer phone is mandatory")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number must be valid and between 7 and 15 digits")
    private String tlph;

    @NotNull(message = "Reservation date is mandatory")
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateReservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTlph() {
        return tlph;
    }

    public void setTlph(String tlph) {
        this.tlph = tlph;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }
}
