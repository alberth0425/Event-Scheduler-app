package controllers;

import entities.Speaker;
import use_cases.AuthService;

import java.util.Scanner;

public class RaterController extends UserController {

    @Override
    void run() {
        while (true) {
            System.out.println("Select an action:");
            System.out.println("1. Rate speaker");
            System.out.println("2. Exit");

            Scanner input = new Scanner(System.in);
            String content = input.nextLine();

            try {
                int choice = Integer.parseInt(content);

                boolean exit = false;

                switch (choice) {
                    case 1:
                        rateSpeaker();
                        break;
                    case 2:
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown action. Please enter digit between 1 and 2.");
                        break;
                }

                save();

                if (exit) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Unknown action. Please enter digit between 1 and 2.");
                break;
            }
        }

    }

    private void rateSpeaker() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the speaker's username you want to rate:");
        String speakerUN = scanner.nextLine();

        try {
            if (!(AuthService.shared.getUserByUsername(speakerUN) instanceof Speaker)) throw new notSpeakerException();

            Speaker speaker = (Speaker) AuthService.shared.getUserByUsername(speakerUN);

            System.out.println("Please enter a rate between 1 to 10: ");
            int rate = Integer.parseInt(scanner.nextLine());
            if (rate < 1 || rate > 10) throw new rateOutOfBoundException();

            speaker.addRate(rate);

            System.out.println("You have rated " + speakerUN + " successfully.");


        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + speakerUN + " does not exist.");
        } catch (notSpeakerException e) {
            System.out.println("Username given is not a Speaker username.");
        } catch (NumberFormatException | rateOutOfBoundException e) {
            System.out.println("Rate entered must be an integer between 1 to 10.");
        } catch (Exception e) {
            System.out.println("Unknown Exception: " + e.toString());
        }
    }

    public static class raterException extends Exception {}
    public static class notSpeakerException extends raterException {}
    public static class rateOutOfBoundException extends raterException {}
}
