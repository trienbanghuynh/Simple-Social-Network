package FinalPart1;

import java.util.Scanner;
import Graph.*;


public class Person {
    // data fields
    private String userId;
    private DataInfo userInfo;

    private class DataInfo {
        private boolean joinedNetwork = true;  // not join
        private String name;
        private String status = "online";
        private String city;

        public DataInfo(String personName, String personCity) {
            name = personName;
            city = personCity;
        }
    }

    // class Person constructor
    Person(String id, String personName, String personCity) {
        userId = id;
        userInfo = new DataInfo(personName, personCity);
        //  friendList = new LinkedBag<>();
    }//end constructor

    /**
     * @return String the Name in the Profile
     */
    public String getName() {
        return userInfo.name;
    }//end getName

    /**
     * @param personName name you want to display
     */
    public void setName(String personName) {
        userInfo.name = personName;
    }//end setName

    /**
     * @return String name of the city
     */
    public String getCity() {
        return userInfo.city;
    }//end getCity

    /**
     * @param personCity name of the city in profile info
     */
    public void setCity(String personCity) {
        userInfo.city = personCity;
    }//end setCity

    /**
     * @return String status of the person's profile
     */
    public String getStatus() {
        return userInfo.status;
    }//end getStatus

    /**
     * @param personStatus - string value which sets the status of the user
     * @return nothing
     */
    public void setStatus(String personStatus) {
        userInfo.status = personStatus;
    }//end setStatus

    /**
     * @return String userId of the person
     */
    public String getUserId() {
        return userId;
    }

    /**
     * re-use a userId
     * @return nothing
     */
    public void rejoinNetwork() {
        userInfo.joinedNetwork = true;
    }

    /**
     * let a user leave network
     * @return nothing
     */
    public void leaveNetwork() {
        userInfo.joinedNetwork = false;
    }

    /**
     * check if the current person is on network
     * @return true if a user is on network
     */
    public boolean isOnNetwork() {
        return userInfo.joinedNetwork == true;
    }

    /**
     * lets the user edit info
     *
     * @return nothing
     */
    public void editPersonInfo() {
        Scanner in = new Scanner(System.in);
        String input = " ";

        System.out.println("Do you want to edit profile info?");
        input = in.nextLine();
        if (input.toLowerCase().equals("yes")) {
            System.out.println("Do you want to change name?");
            input = in.nextLine();
            if (input.toLowerCase().equals("yes")) {
                System.out.println("Enter new name:");
                input = in.nextLine();
                setName(input);
            }

            System.out.println("Do you want to change city?");
            input = in.nextLine();
            if (input.toLowerCase().equals("yes")) {
                System.out.println("Enter new city:");
                input = in.nextLine();
                setCity(input);
            }
        }
    }//end editPersonInfo

    /**
     * lets the user input his/her profile
     *
     * @return nothing
     */
    public void setPersonInfo() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter name you want to display: ");
        String name = in.nextLine();
        setName(name);
        System.out.println("Please enter the city name you want to display: ");
        String city = in.nextLine();
        setCity(city);
    }

    /**
     * return String: all information in a user's profile
     */
    public String toString() {
        return ("PROFILE INFO:\n" + "Name: " + this.getName() + "\nCity: "
                + this.getCity() + "\nStatus: " + this.getStatus() + "\n");
    }
}
// end of Person class