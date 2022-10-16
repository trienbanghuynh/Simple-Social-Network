/**
 
 * Group Pikachu: Swati Cipra, Anh Khoa Ngo, Trien Bang Huynh, Le Ngoc Thanh Nguyen
 * CIS 22C - Instructor: Mirsaeid Abolghasemi
 * Project: A simple social network
 * Data Structure: Bag, Graph.
 */
package FinalPart1;

import java.util.Scanner;
import java.util.InputMismatchException;

// This class is the Driver to test the network

public class User {
    public static void main(String[] args) {
        Network pikachu = new Network();
        testNetwork(pikachu);
    } // end main

    /**
     * Use to test a network
     * Get the choice from user's input and take corresponding action
     * @param network the network to test
     * @return nothing
     */
    public static void testNetwork(Network network) {

        //String Array contain 4 action to control the network
        String[] action = {"Join network", "Display all users", "let a user take action", "Exit Network"};

        // format to print the option output in 2 columns
        String format = "%-5d %-45s %n";

        // set default value to get into the loop
        int actionChoice = 0;
        Scanner in = new Scanner(System.in);

        while (actionChoice != 4) {
            for (int i = 0; i < action.length; i++)
                System.out.format(format, i + 1, action[i]);
            System.out.println("Please choose an action from the list above:");

                // CASE 1: user input the right option on the list (an int from 1 to 4)
            try {
                actionChoice = in.nextInt(); // override actionChoice with user input
                switch (actionChoice) {
                    case 1:
                        network.joinNetwork();
                        break;
                    case 2:
                        network.printAllUser();
                        break;
                    case 3:
                        letUserTakeAction(network);
                        break;
                    case 4:
                        System.out.println("Exiting the network");
                        break;
                // CASE 2: user input is an int that is not in the option list
                    default:
                        System.out.println("Invalid option, please enter a number from the action list ");
                }
                // CASE 3: user input is not an integer
            } catch (InputMismatchException imeOption) {
                System.out.println("Invalid option, please enter a number from the action list ");
                in.nextLine();
            } // end try-catch
        } // end while
    } // end testNetwork

    /**
     * This method let a user take action on a network
     * Get the choice from user's input and take corresponding action
     * User have to be online to take action
     * @param network the network that taking-action user is in
     * @return nothing
     */
    public static void letUserTakeAction(Network network) {

        //String Array contain 10 action a person can take on a network
        String[] userAction = {"add to friend list", "remove from friend list",
                "sign in", "set status to busy", "edit profile",
                "display profile", "search for another user",
                "see a friend's friend list", "leave network", "Sign out and Exit"};

        // format to print the option output in 2 columns
        String format = "%-5d %-45s %n"; // format the option output

        // Case 1: Network is empty. Stop taking action
        if (network.isNetworkEmpty()) {
            System.out.println("The network is empty. Can not take any action");
            return;
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a userId to take action:");
        String userId = in.nextLine();

        Person currentPerson = network.getPersonByUserId(userId);

        // Case 2: userId input is not on the network
        // loop util getting an existing userId
        // this might get a userId that already left the network
        while(!network.isUserExist(userId)) {
            System.out.println(userId +" is not on this network");
            System.out.println("Please input another userid: ");
            userId = in.nextLine();
        }
        currentPerson = network.getPersonByUserId(userId);

        // Case 3: existing userId but already left the network
        // loop util getting a userId that is currently on network
        try {
            while(!currentPerson.isOnNetwork()) {
                System.out.println(userId + " is not on this network");
                System.out.println("Please input another userId: ");
                userId = in.nextLine();
                currentPerson = network.getPersonByUserId(userId);
            }
        }catch (NullPointerException ne) {
            System.out.println("Please input a userId to continue");
            in.nextLine();
        }

        // Start taking action
        int choice = 0;
        while (choice != 10) {

            // Print the list of action for user to choose
            for (int i = 0; i < userAction.length; i++)
                System.out.format(format, i + 1, userAction[i]);
            System.out.println("Please choose an action from the list above:");

            try {
                choice = in.nextInt(); // over write the option by user input

                // CASE 1: user input the right option on the list (an int from 1 to 12)
                switch (choice) {
                    case 1: // add a friend
                        network.addToFriendList(userId);
                        break;
                    case 2: // remove a friend from friend list
                        network.removeFromFriendList(userId);
                        break;
                    case 3: // sign in
                        network.signIn(userId);
                        break;
                    case 4: // set user's status to busy
                        network.setStatusBusy(userId);
                        break;
                    case 5: // edit user's profile (name, city)
                        network.editProfile(userId);
                        break;
                    case 6: // display the current user profile and friend list
                    {
                        network.searchProfile(userId);
                        network.displayFriendlist(userId);
                    }
                    break;
                    case 7: // search for another user's profile
                    {
                        if (!network.isOnline(userId)){
                            System.out.println("Error - " + userId + " is offline");
                            System.out.println("Please sign in to search for other user");
                            break;
                        }
                        else {
                            System.out.println("Please input a userId to search");
                            Scanner input = new Scanner(System.in);
                            String personToFind = input.nextLine();
                            network.searchProfile(personToFind);
                            break;
                        }
                    }
                    case 8: // display the friend list of all friends of the current user
                    {
                        if (!network.isOnline(userId)){
                            System.out.println("Error - " + userId + " is offline");
                            System.out.println("Please sign in to look up a friend's friend list");
                            break;
                        }
                        else {
                            network.displayListOfFriend(userId);
                            break;
                        }
                    }
                    case 9: // let a user leave network (deactivate)
                            // Stop taking action immediately after leaving network
                        System.out.println("deleting " + userId + " from network.\n");
                        network.leaveNetwork(userId);
                        System.out.println( userId + " won't be able to take any further action.\n");
                        choice = 10;
                        break;
                    case 10: // let a user log out
                             // Stop taking action
                        System.out.println("Signing " + userId + " out.\n");
                        network.signOut(userId);
                        System.out.println("Stop taking action.\n");
                        break;

                    // CASE 2: user input is an int that is not in the option list
                    default:
                        System.out.println("Invalid option, please enter a number from the action list ");
                }
                // CASE 3: user input is not an integer
            } catch (InputMismatchException imeOption) {
                    System.out.println("Invalid option, please enter a number from the action list ");
                    in.nextLine();
            } // end try-catch
        } // end while
    } // end letUserTakeAction
} // end User class