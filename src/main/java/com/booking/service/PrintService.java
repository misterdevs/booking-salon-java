package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.utilities.Utility;

public class PrintService {
    Utility utility = new Utility();

    public String printServices(List<Service> serviceList) {
        String result = "";
        for (int i = 0; i < serviceList.size(); i++) {
            if (i > 0) {
                result += ", ";
            }
            result += serviceList.get(i).getServiceName();
        }
        return result;
    }

    public void showRecentReservation(List<Reservation> reservationList) {
        String displayFormat = "| %-3s | %-13s | %-20s | %-78s | %-15s | %-20s | %-15s |%n";
        String displayBorder = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n";
        String displayBorderHeader = "==========================================================================================================================================================================================%n";

        System.out.format(displayBorderHeader);
        System.out.printf(displayFormat, "No", "ID", "Customer Name", "Service", "Total Bill", "Employee Name",
                "Workstage");
        System.out.format(displayBorderHeader);
        int num = 1;
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("On process")) {
                System.out.printf(displayFormat, num++, reservation.getReservationId(),
                        reservation.getCustomer().getName(),
                        printServices(reservation.getServices()),
                        utility.rupiahFormatter((int) reservation.getReservationPrice()),
                        reservation.getEmployee().getName(), reservation.getWorkstage());
                System.out.format(displayBorder);
            }
        }
    }

    public void showAllCustomer(List<Person> personList) {
        String displayFormat = "| %-3s | %-13s | %-20s | %-10s | %-10s | %-15s |%n";
        String displayBorder = "------------------------------------------------------------------------------------------%n";
        String displayBorderHeader = "==========================================================================================%n";

        System.out.format(displayBorderHeader);
        System.out.printf(displayFormat, "No", "ID", "Name", "Address", "Membership", "Balance");
        System.out.format(displayBorderHeader);

        int num = 1;
        for (Person person : personList) {
            if (person instanceof Customer) {
                System.out.printf(displayFormat, num++, person.getId(), person.getName(), person.getAddress(),
                        ((Customer) person).getMember().getMembershipName(),
                        utility.rupiahFormatter((int) ((Customer) person).getWallet()));
                System.out.format(displayBorder);
            }
        }

    }

    public void showAllEmployee(List<Person> personList) {
        String displayFormat = "| %-3s | %-13s | %-20s | %-10s | %-10s |%n";
        String displayBorder = "------------------------------------------------------------------------%n";
        String displayBorderHeader = "========================================================================%n";

        System.out.format(displayBorderHeader);
        System.out.printf(displayFormat, "No", "ID", "Name", "Address", "Experience");
        System.out.format(displayBorderHeader);
        int num = 1;
        for (Person person : personList) {
            if (person instanceof Employee) {
                System.out.printf(displayFormat, num++, person.getId(), person.getName(), person.getAddress(),
                        ((Employee) person).getExperience());
                System.out.format(displayBorder);
            }
        }

    }

    public void showHistoryReservation(List<Reservation> reservationList) {
        String displayFormat = "| %-3s | %-13s | %-20s | %-78s | %-15s | %-20s | %-15s |%n";
        String displayBorder = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n";
        String displayBorderHeader = "==========================================================================================================================================================================================%n";

        System.out.format(displayBorderHeader);
        System.out.printf(displayFormat, "No", "ID", "Customer Name", "Service", "Total Bill", "Employee Name",
                "Workstage");
        System.out.format(displayBorderHeader);
        int num = 1;
        double profit = 0;
        for (Reservation reservation : reservationList) {
            System.out.printf(displayFormat, num++, reservation.getReservationId(),
                    reservation.getCustomer().getName(),
                    printServices(reservation.getServices()),
                    utility.rupiahFormatter((int) reservation.getReservationPrice()),
                    reservation.getEmployee().getName(), reservation.getWorkstage());
            System.out.format(displayBorder);
            if (reservation.getWorkstage().equalsIgnoreCase("finish")) {
                profit += reservation.getReservationPrice();
            }
        }
        System.out.format("| %-123s | %-56s |%n", "Total Profit",
                utility.rupiahFormatter((int) profit));
        System.out.format(displayBorder);

    }

    public void showAllService(List<Service> serviceList) {
        String displayFormat = "| %-3s | %-13s | %-20s | %-15s |%n";
        String displayBorder = "----------------------------------------------------------------%n";
        String displayBorderHeader = "================================================================%n";

        System.out.format(displayBorderHeader);
        System.out.printf(displayFormat, "No", "ID", "Name", "Price");
        System.out.format(displayBorderHeader);
        int num = 1;
        for (Service service : serviceList) {
            System.out.printf(displayFormat, num++, service.getServiceId(), service.getServiceName(),
                    utility.rupiahFormatter((int) service.getPrice()));
            System.out.format(displayBorder);
        }

    }
}
