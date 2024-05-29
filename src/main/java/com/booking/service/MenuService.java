package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;
import com.booking.utilities.UtilityMenu;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);
    private static UtilityMenu menu = new UtilityMenu();
    private PrintService printService = new PrintService();
    private int idNumber = 1;

    public void mainMenu() {
        String[] mainMenuArr = { "Show Data", "Create Reservation", "Complete/Cancel Reservation" };
        menu.createMenu(s -> handleMainMenu(s), "Aplikasi Booking Salon by MRDevs", mainMenuArr, 0, "Exit");
        System.out.println("==========================================");
        System.out.println("APPLICATION HAS BEEN CLOSED");
        System.out.println("==========================================");
        input.close();
    }

    private void handleMainMenu(String chosenMenu) {
        menu.resetDisplay();
        switch (Integer.valueOf(chosenMenu)) {
            case 1:
                String[] showDataMenuArr = { "Recent Reservation", "Show Customer", "Show Employee",
                        "History Reservation" };
                menu.createMenu(s -> handleShowDataMenu(s), "Show Data", showDataMenuArr, 99, "Back to Main Menu");
                break;
            case 2:
                ReservationService.createReservation(reservationList, personList, serviceList, idNumber++);
                break;
            case 3:
                ReservationService.editReservationWorkstage(reservationList);
                break;
            default:
                break;
        }

    }

    private void handleShowDataMenu(String chosenMenu) {
        menu.resetDisplay();
        switch (Integer.valueOf(chosenMenu)) {
            case 1:
                printService.showRecentReservation(reservationList);
                menu.enterToContinue();
                break;
            case 2:
                printService.showAllCustomer(personList);
                menu.enterToContinue();
                break;
            case 3:
                printService.showAllEmployee(personList);
                menu.enterToContinue();
                break;
            case 4:
                printService.showHistoryReservation(reservationList);
                menu.enterToContinue();
                break;
            default:
                break;
        }

    }
}
