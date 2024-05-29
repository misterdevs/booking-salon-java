package com.booking;

import com.booking.service.MenuService;

public class Main {

    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        menuService.mainMenu();
    }
}