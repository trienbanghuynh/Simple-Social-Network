/**
 * Group Pikachu: Swati Cipra, Anh Khoa Ngo, Trien Bang Huynh, Le Ngoc Thanh Nguyen
 * CIS 22C - Instructor: Mirsaeid Abolghasemi
 * Project: A simple social network
 * Data Structure:  Bag, Graph.
 */
package FinalPart1;

import Graph.*;

import java.util.Iterator;
import java.util.Scanner;

public class Network {

    private static UndirectedGraph<Person> network;

    /**
     * constructor calls the UndirectedGraph constructor
     */
    Network() {
        network = new UndirectedGraph<>();

    }//end constructor

    /**
     * Let a user join the social network
     * ask the user to choose a unique ID
     * Let user input for displayed name and city
     * after creating a keyword (userID) successfully
     *
     * @return nothing
     */
    public void joinNetwork() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please choose a user id: ");
        String userId;
        userId = in.nextLine();

        // Ask for useID input until getting an available user name
        while (isUserExist(userId)) {
            Person currentPerson = getPersonByUserId(userId);

            // CASE 1: userID input is currently on network, ask for another input
            if (currentPerson.isOnNetwork()) {
                System.out.println("The userId is already taken. Please choose a different user id.");
                userId = in.nextLine();

            // CASE 2: userID exist on network but already left the network
            } else {
                currentPerson.rejoinNetwork(); // re-use the used ID
                currentPerson.setPersonInfo(); // let the user enter displayed name and city
                return;
            }
        }
            // CASE 3: input userID is brand new
        System.out.println("Please enter name you want to display: ");
        String name = in.nextLine();

        System.out.println("Please enter the city name you want to display: ");
        String city = in.nextLine();

        Person newUser = new Person(userId, name, city);
        network.addVertex(newUser);

    } //end joinedNetwork

    /**
     * Lets a user leave the social network, checks to see if the user is online
     * before they go ahead with the activity.
     *
     * @param userId user to leave network
     * @return nothing
     */
    public void leaveNetwork(String userId) {

        // currentPerson is not online
        if (!isOnline(userId)) {
            System.out.println(userId + " is not online. " +
                    "Please sign in to take action.");
            return;
        }
        // currentPerson leave network
        else {
            Person currentPerson = getPersonByUserId(userId);
            currentPerson.leaveNetwork();
            this.removeFriendList(currentPerson);
            System.out.println(userId + " just left the network");
        }

    } //end leaveNetwork

    /**
     * remove one person out of the other's friend list
     *
     * @param currentPerson the person to remove from other's friend list
     */
    private void removeFriendList(Person currentPerson) {
        Iterator<VertexInterface<Person>> vertexIterator = network.getVertexIterator();
        while (vertexIterator.hasNext()) {
            Person checkingPerson = (vertexIterator.next()).getLabel();
            network.removeEdge(currentPerson, checkingPerson);
        } // end while

    } // end removeFriendList

    /**
     * search for any profile on the network
     * print the profile if it's exist in the network dictionary
     * @param userId the user to search
     */
    public void searchProfile(String userId) {

        Person checkingPerson = getPersonByUserId(userId);

        if (checkingPerson == null || !checkingPerson.isOnNetwork())
            System.out.println("No available results for searched profile.");
        else
            System.out.println(checkingPerson);

    } // searchProfile

    /**
     * lets a user edit their profile
     * the user must be on the network and online
     *
     * @param userId the user wants to edit profile
     * @return nothing
     */
    public void editProfile(String userId) {
        Person currentPerson = getPersonByUserId(userId);
        if (!isOnline(userId)) {
            System.out.println("Error - " + userId + " is offline");
            System.out.println("Please sign in to edit your profile");
        } else
            currentPerson.editPersonInfo();

    }//end editProfile

    /**
     * Let a user add another user to their friend list
     * only allow user that is exist on the network and online to add friend
     *
     * @param userId the user wants to add friend
     * @return nothing
     */
    public void addToFriendList(String userId) {

        // can not take action because user is offline
        if (!isOnline(userId)) {
            System.out.println(userId + " is not online. " +
                    "Please sign in to take action.");
            return;
        }
        Person currentPerson = getPersonByUserId(userId);
        System.out.println("Enter a user to add to your friend list.");
        Scanner in = new Scanner(System.in);
        String friendToAdd = in.nextLine();

        // can not add yourself
        if (friendToAdd.equals(userId)) {
            System.out.println("You can not add yourself.");
            return;
        }

        // friend is not on network
        if (!isUserExist(friendToAdd)) {
            System.out.println(friendToAdd + " is not on network.");
            return;
        }

        Person friend = getPersonByUserId(friendToAdd);

        // friend left the network (not available to add)
        if (!friend.isOnNetwork()) {
            System.out.println(friendToAdd + " is not on network.");
            return;
        }

        // add a friend who is on network
        if (network.hasEdge(currentPerson, friend)) {
            System.out.println(friendToAdd + " is already in "
                    + userId + "'s friend list.");
        } else
            network.addEdge(currentPerson, friend);

    } // end addToFriendList

    /**
     * remove one person out of the another person's friend list
     *
     * @param userId the user wants to remove friend from his/her friend list
     * @return nothing
     */
    public void removeFromFriendList(String userId) {

        // can not take action because user is offline
        if (!isOnline(userId)) {
            System.out.println(userId + " is not online. " +
                    "Please sign in to take action.");
            return;
        }
        Person currentPerson = getPersonByUserId(userId);
        System.out.println("Enter a user to remove from your friend list.");
        Scanner in = new Scanner(System.in);
        String friendToRemove = in.nextLine();

        // can not add yourself
        if (friendToRemove.equals(userId)) {
            System.out.println("You can not remove yourself.");
            return;
        }

        // friend is not on network
        if (!isUserExist(friendToRemove)) {
            System.out.println(friendToRemove + " is not on network.");
            return;
        }

        Person friend = getPersonByUserId(friendToRemove);

        // friend left the network (not available to add)
        if (!friend.isOnNetwork()) {
            System.out.println(friendToRemove + " is not on network.");
            return;
        }

        // remove a friend who is on network
        if (!network.hasEdge(currentPerson, friend)) {
            System.out.println(friendToRemove + " is not in your friend list.");
        } else
            network.removeEdge(currentPerson, friend);

    } // end removeFromFriendList

    /**
     * Print out a friend list of a user
     * @param userId the current user to display friend list
     */
    public void displayFriendlist(String userId) {
        Person currentPerson = getPersonByUserId(userId);
        BagInterface<Person> friendList = network.getNeighborBag(currentPerson);
        System.out.println("Friendlist: ");

        String[] friendName = new String[friendList.getCurrentSize()];
        if (friendList.getCurrentSize() > 0) {
            Object[] friendArray = friendList.toArray();
            for (int i = 0; i < friendName.length; i++) {
                friendName[i] = ((Person) friendArray[i]).getName();
                System.out.print(friendName[i] + ", ");
            }
            System.out.println();
        } else
            System.out.println("Friend List empty!");
        System.out.println();

    } // end displayFriendlist

    /**
     *  display friend list of all the friends in a person friend list
     * @param userId person who wants to print his/her friend's friend lists
     */
    public void displayListOfFriend(String userId) {
        Person currentPerson = getPersonByUserId(userId);
        BagInterface<Person> friendList = network.getNeighborBag(currentPerson);

        String[] friend = new String[friendList.getCurrentSize()];
        if (friendList.getCurrentSize() > 0) {
            Object[] friendArray = friendList.toArray();
            for (int i = 0; i < friend.length; i++) {
                friend[i] = ((Person) friendArray[i]).getUserId();
                System.out.print(((Person) friendArray[i]).getName() + "'s ");
                displayFriendlist(friend[i]);
            }
        } else
            System.out.println("Friend List empty!!");
        System.out.println();

    } // end displayListOfFriend

    /**
     * Use Iterator to traverse the network
     * Print all the user (ID and Name) that is currently on network
     *
     * @return nothing
     */
    public void printAllUser() {
        System.out.println("List of user currently on this network \n");
        Iterator<VertexInterface<Person>> vertexIterator = network.getVertexIterator();
        String format = "%-20s %-45s %n";
        System.out.format(format, "User ID", "User Name");

        while (vertexIterator.hasNext()) {
            Person checkingPerson = (vertexIterator.next()).getLabel();
            if (checkingPerson.isOnNetwork())
                System.out.format(format, checkingPerson.getUserId(), checkingPerson.getName());
        }
        System.out.println();

    } // end printAllUser

    /**
     * Let an offline user sign in
     * only allow an existing user on the network
     *
     * @param userId user to sign in
     * @return nothing
     */
    public void signIn(String userId) {
        Person currentPerson = getPersonByUserId(userId);
        if (currentPerson.isOnNetwork()) {
            if (isOnline(userId))
                System.out.println(userId + " is currently online.");
            else
                currentPerson.setStatus("online");
        } else
            System.out.println(userId + " is not on the network.");

    } // end signIn

    /**
     * Let an online user sign out
     * only allow an existing user on the network
     *
     * @param userId user to sign out
     * @return nothing
     */
    public void signOut(String userId) {
        Person currentPerson = getPersonByUserId(userId);
        if (currentPerson.isOnNetwork()) {
            if (isOnline(userId))
                currentPerson.setStatus("offline");
            else
                System.out.println(userId + " is already signed out.");
        } else
            System.out.println(userId + " is not on the network.");

    } // end signOut

    /**
     * Let an online user change his/her status to busy
     * only allow an existing user on the network
     *
     * @param userId user to change status
     * @return nothing
     */
    public void setStatusBusy(String userId) {
        Person currentPerson = getPersonByUserId(userId);
        if (currentPerson.isOnNetwork()) {
            if (isOnline(userId))
                currentPerson.setStatus("busy");
            else
                System.out.println(userId + " is offline. Please sign in to take action");
        } else
            System.out.println(userId + " is not on the network.");

    } // end setStatusBusy

    /**
     * Use Iterator to traverse the network
     * get the person by corresponding userID
     *
     * @param userId user to find
     * @return Person - the person found with userId
     */
    public Person getPersonByUserId(String userId) {

        Iterator<VertexInterface<Person>> vertexIterator = network.getVertexIterator();

        while (vertexIterator.hasNext()) {
            Person checkingPerson = (vertexIterator.next()).getLabel();
            if (checkingPerson.getUserId().equals(userId)) {
                return checkingPerson;
            }
        }
        return null;

    } // end getPersonByUserId

    /**
     *  check if the network is empty or not
     *  @return true if the network is empty
     */
    public boolean isNetworkEmpty() {
        if (network.isEmpty()) {
            return true;
        } else
            return false;

    } // end isNetworkEmpty

    /**
     * check if a user is existed on network
     *
     * @param userId the userID to check
     * @return true if the user is exist on network
     */
    public boolean isUserExist(String userId) {

        Person personToCheck = getPersonByUserId(userId);
        return (personToCheck != null);

    } // end isUserExist

    /**
     * Check if a person is online to make sure he/she is available
     * to modify his/her profile or add/remove friend
     *
     * @param userId user to be checked
     * @return true if the user is online
     */
    public boolean isOnline(String userId) {
        boolean online = false;
        Person checkingPerson = getPersonByUserId(userId);
        if (checkingPerson.getStatus().equals("online") || checkingPerson.getStatus().equals("busy"))
            online = true;
        return online;

    } // end isOnline

} // end Network class

