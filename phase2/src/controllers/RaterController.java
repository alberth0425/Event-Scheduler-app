package controllers;

import entities.Rater;
import entities.Speaker;
import entities.User;
import use_cases.AuthService;
import use_cases.RaterService;

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
            User speaker = AuthService.shared.getUserByUsername(speakerUN);
            Rater rater = (Rater) AuthService.shared.getCurrentUser();

            System.out.println("Please enter a rate between 1 to 10: ");
            int rate = Integer.parseInt(scanner.nextLine());

            RaterService.shared.rateSpeaker(speaker, rater, rate);

            System.out.println("You have rated " + speakerUN + " successfully.");


        } catch (AuthService.UserDoesNotExistException e) {
            System.out.println("User with username " + speakerUN + " does not exist.");
        } catch (NumberFormatException e) {
            System.out.println("Rate entered must be an integer between 1 to 10.");
            rateSpeaker();
        } catch (RaterService.notSpeakerException e) {
            System.out.println("Username given is not a Speaker username.");
        } catch (RaterService.rateOutOfBoundException e) {
            System.out.println("Rate entered must be an integer between 1 to 10.");
        } catch (RaterService.rateRepetitionException e) {
            System.out.println("You have already rated the speaker with username " + speakerUN +
                    ", please choose a speaker you haven't rated before");
        } catch (Exception e) {
            System.out.println("Unknown Exception: " + e.toString());
        }
    }


}
