package lab1;

/*public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
 */

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        String result;  //use for showing result
        int randomNumber = ((int)(Math.random()*100)); //The random number will be between 1 and 100
        int number = 0;

        do {
            System.out.println("Enter a number between 1 and 100: ");
            number = sc.nextInt();

            if (number == randomNumber) {
                System.out.println("Correct answer. Congratulations");
                result = "Win";
                break;
            }
            else if (number > randomNumber) {
                System.out.println("You're number is large");
                result = "Lose";
            }
            else {
                System.out.println("You're number is small");
                result = "Lose";
            }
        } while (number >= 0);

        System.out.println("My number was: ");
        System.out.println(randomNumber);

        try (FileWriter writer = new FileWriter("Result.txt")) {
            writer.write("Number " + number + " Actual number " + randomNumber + " Result " + result);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}



