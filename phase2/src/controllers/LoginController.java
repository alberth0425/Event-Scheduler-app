package controllers;

import src.entities.*;
import src.gateway.PersistenceStorage;
import src.use_cases.AuthService;
import src.use_cases.EventService;
import src.use_cases.MessageService;
import src.use_cases.RoomService;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LoginController extends BaseController {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        (new LoginController()).run();
    }

    /**
     * Entry point of the program. Prompts the user to login and redirects to a UserController.
     */
    public void run() {
        load();

        while (!login()) {
            System.out.println("Invalid credentials, please try again.");
        }

        User user = AuthService.shared.getCurrentUser();

        System.out.println("You have logged in as " + user.getFullname() + ".");

        UserController userController;

        if (user instanceof Attendee) {
            userController = new AttendeeController();
        } else if (user instanceof Organizer) {
            userController = new OrganizerController();
        } else {
            userController = new SpeakerController();
        }

        userController.run();
    }

    private boolean login() {
        System.out.println("Login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        return AuthService.shared.loginUser(username, password);
    }
}
