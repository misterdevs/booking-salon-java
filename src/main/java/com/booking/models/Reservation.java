package com.booking.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;

    // workStage (On Process, Finish, Canceled)
    public static enum workStageEnum {
        ON_PROCESS, FINISH, CANCELED
    }

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            workStageEnum workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workStageEnumToString(workstage);
    };

    public static String workStageEnumToString(workStageEnum stageEnum) {
        switch (stageEnum) {
            case ON_PROCESS:
                return "On Process";
            case FINISH:
                return "Finish";
            case CANCELED:
                return "Canceled";
            default:
                return "undefined";
        }

    }

    private double calculateReservationPrice() {
        double total = 0;
        for (Service service : services) {
            total += service.getPrice();
        }
        if (this.customer.getMember().getMembershipName().equalsIgnoreCase("silver")) {
            return total - (total * 5 / 100);
        }
        if (this.customer.getMember().getMembershipName().equalsIgnoreCase("gold")) {
            return total - (total * 10 / 100);
        }
        return total;
    }
}
