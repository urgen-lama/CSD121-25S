package lab1;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a password: ");
        int password = sc.nextInt();
        int rightPassword = 12345678;
        int card = 4444;
        int [] marks = {98, 97, 95, 88}; //marks
        String [] subjects = {"Physics", "Maths", "Management", "Environment"};

        if ( password == rightPassword ) {
            System.out.println("To see the marks enter the student ID: ");
            int studentID = sc.nextInt();
            if ( studentID == card ) {
                System.out.println(subjects[0] + " = " + marks[0]);
                System.out.println(subjects[1] + " = " + marks[1]);
                System.out.println(subjects[2] + " = " + marks[2]);
                System.out.println(subjects[3] + " = " + marks[3]);
            }
            else {
                System.out.println("Please enter right password");
            }
        }
        else {
            System.out.println("Please enter right password");
        }
    }
}
