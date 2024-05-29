package com.booking.service;

import java.util.ArrayList;
import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Reservation.workStageEnum;
import com.booking.models.Service;
import com.booking.utilities.UtilityInput;
import com.booking.utilities.UtilityMenu;

public class ReservationService {

    public static void createReservation(List<Reservation> reservationList, List<Person> personList,
            List<Service> serviceList, int idNumber) {
        // initialized class
        PrintService printService = new PrintService();
        UtilityInput input = new UtilityInput();
        UtilityMenu menu = new UtilityMenu();
        List<Service> chosenServices = new ArrayList<Service>();

        // table all customers view
        printService.showAllCustomer(personList);

        // confirmation before action. so, user can canceled the action.
        if (menu.confirmation("Do you wanna make a reservation?")) {

            // input with validation to check if the customer id is registered
            String customerId = input.validate("Enter Customer ID", "Customer ID not registered!",
                    s -> getCustomerByCustomerId(personList, s) != null);
            menu.resetDisplay();
            // table all employees view
            printService.showAllEmployee(personList);
            // input with validation to check if the employee id is registered
            String employeeId = input.validate("Enter Employee ID", "Employee ID not registered!",
                    s -> getEmployeeByEmployeeId(personList, s) != null);
            menu.resetDisplay();
            // table all services view
            printService.showAllService(serviceList);

            Boolean isClosed = false;
            // looping to adding several services
            do {
                // looping for validation to make sure the service hasn't been added yet
                Boolean isValid = false;
                do {
                    Service chosenService = getServiceByServiceId(serviceList,
                            input.validate("Enter Service ID", "Service not available!",
                                    s -> getServiceByServiceId(serviceList, s) != null));
                    // make sure if there is no chosenService in chosenServices
                    if (getServiceByServiceId(chosenServices, chosenService.getServiceId()) == null) {
                        // add chosenService to chosenServices
                        chosenServices.add(chosenService);
                        isValid = true;
                    } else {
                        System.out.println("Service has been chosen!");
                    }
                } while (isValid != true);
                // restrict to add more services if it has choose all service
                if (chosenServices.size() == serviceList.size()) {
                    isClosed = true;
                } else {
                    isClosed = !menu.confirmation("Do you wanna add more services?");
                }
            } while (isClosed != true);

            // initialize reservation object
            Reservation reservation = new Reservation(input.createIdPattern("Rsv-00",
                    idNumber), getCustomerByCustomerId(personList, customerId),
                    getEmployeeByEmployeeId(personList, employeeId), chosenServices,
                    Reservation.workStageEnum.ON_PROCESS);

            // check balance of customer
            // can't create reservation if customer hasn't enough balance
            if (reservation.getReservationPrice() <= reservation.getCustomer().getWallet()) {
                // reservation created
                reservationList.add(reservation);
                System.out.println("Reservation Successful!");
                // show total bill after discount cuts(if get a discount)
                System.out.println("Total Bill:" + input.rupiahFormatter((int) reservation.getReservationPrice()));
            } else {
                System.out.println("Reservation failed. Balance not enough!");
            }
            menu.enterToContinue();
        }
    }

    public static void editReservationWorkstage(List<Reservation> reservationList) {
        // initialize class
        PrintService printService = new PrintService();
        UtilityInput input = new UtilityInput();
        UtilityMenu menu = new UtilityMenu();

        // table recent reservations (reservation with 'on process' workstage status)
        printService.showRecentReservation(reservationList);

        // confirmation before action. so, user can canceled the action.
        if (menu.confirmation("Do you wanna update workstage status?")) {

            // input with validation to check if the reservation id is registered
            String reservationId = input.validate("Enter Reservation ID", "Reservation ID not registered",
                    s -> getReservationByReservationId(reservationList, s) != null);
            // initialized reservation object
            Reservation reservation = getReservationByReservationId(reservationList, reservationId);

            // check if the reservation has 'on process' workstage status
            if (reservation.getWorkstage()
                    .equalsIgnoreCase(Reservation.workStageEnumToString(workStageEnum.ON_PROCESS))) {
                // choose action
                System.out.println("Update Workstage Status");
                System.out.println("(1) Finish");
                System.out.println("(2) Cancel");
                String chosenStatus = input.validate("Pilih Status", "Hanya dapat memasukkan angka yang tersedia",
                        s -> input.isNumber(s) && (Integer.valueOf(s) == 1 || Integer.valueOf(s) == 2));

                switch (Integer.valueOf(chosenStatus)) {
                    case 1:
                        // update status to finish
                        reservation.setWorkstage(Reservation.workStageEnumToString(workStageEnum.FINISH));
                        System.out
                                .println("Reservation " + reservationId + " for " + reservation.getCustomer().getName()
                                        + " has been finished");

                        // update balance customer. decrease balance to pay the bill
                        reservation.getCustomer()
                                .setWallet(reservation.getCustomer().getWallet() - reservation.getReservationPrice());
                        menu.enterToContinue();
                        break;
                    case 2:
                        // update status to canceled
                        reservation.setWorkstage(Reservation.workStageEnumToString(workStageEnum.CANCELED));
                        System.out
                                .println("Reservation " + reservationId + " for " + reservation.getCustomer().getName()
                                        + " has been canceled");
                        menu.enterToContinue();
                        break;
                    default:
                        break;
                }
            } else {
                System.out.println("Reservation has been " + reservation.getWorkstage());
                menu.enterToContinue();
            }

        }
    }

    public static Customer getCustomerByCustomerId(List<Person> personList, String id) {
        return (Customer) personList.stream().filter((a) -> a.getId().equalsIgnoreCase(id)).findFirst().orElse(null);

    }

    public static Employee getEmployeeByEmployeeId(List<Person> personList, String id) {
        return (Employee) personList.stream().filter((a) -> a.getId().equalsIgnoreCase(id)).findFirst().orElse(null);

    }

    public static Service getServiceByServiceId(List<Service> serviceList, String id) {
        return serviceList.stream().filter((a) -> a.getServiceId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public static Reservation getReservationByReservationId(List<Reservation> reservationList, String id) {
        return reservationList.stream().filter((a) -> a.getReservationId().equalsIgnoreCase(id)).findFirst()
                .orElse(null);
    }

}
