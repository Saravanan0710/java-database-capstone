package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "doctor can not be null")
    private Doctor doctor;

    @ManyToOne
    @NotNull(message = "patient can not be null")
    private Patient patient;

    @Future(message = "Appointment time must be in the future")
    private LocalDateTime appointmentTime;

    @NotNull(message = "status cannot be null")
    private int status;

    @Transient
    public LocalDateTime getEndTime(){
        return appointmentTime.plusHours(1);
    }

    public LocalDate getAppointmentDate(){
        return appointmentTime.toLocalDate();
    }

    public LocalTime getAppointmentTimeOnly(){
        return appointmentTime.toLocalTime();
    }

}

