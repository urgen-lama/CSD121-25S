package lab2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AvatarGenerator {

    public static void main(String[] args) { //It is a reference type.

        try {
            var avatarStream = AvatarGenerator.getRandomAvatarStream();
            AvatarGenerator.showAvatar(avatarStream); //Call method and it calls to getRandomAvatarStream() of class AvatarGenerator. In AvatarGenerator.showAvatar(avatarStream) it is using call method as it calls to class AvatarGenerator.
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); //It is an instance method because it can use the object's data and it prints error when error occurs.
        }

    }

    public static InputStream getRandomAvatarStream() throws IOException, InterruptedException {
        // Pick a random style
        String[] styles = { "adventurer", "adventurer-neutral", "avataaars", "big-ears", "big-ears-neutral", "big-smile", "bottts", "croodles", "croodles-neutral", "fun-emoji", "icons", "identicon", "initials", "lorelei", "micah", "miniavs", "open-peeps", "personas", "pixel-art", "pixel-art-neutral" }; // It is a string type also reference type. It stores the avatar style options.
        var style = styles[(int)(Math.random() * styles.length)]; //Math.random is a class method. (int) is a primitive type. styles.length is an instance variable. This line of code is use to randomly select the style.

        // Generate a random seed
        var seed = (int)(Math.random() * 10000); //.random is a class method. It is a primitive.

        // Create an HTTP request for a random avatar
        var uri = URI.create("https://api.dicebear.com/9.x/%s/png?seed=%d".formatted(style, seed)); //.create is use to construct URI object and it is a reference type. .format is an instance method to format the URL. This line of code is use to return URI object. It is a reference type.
        var request = HttpRequest.newBuilder(uri).build(); //.newBuilder is a static type and request is a reference variable of HttpRequest. .build is an instance method.

        // Send the request
        try (var client = HttpClient.newHttpClient()) { //.newHttpClient is a static method.
            var response = client.send(request, HttpResponse.BodyHandlers.ofInputStream()); //.send is an instance method.
            return response.body(); //.body is an instance method and returns as InputStream.
        }
    }

    public static void showAvatar(InputStream imageStream) //showAvatar displays the avatar and is reference type
    {
            JFrame frame = new JFrame("PNG Viewer"); //It is a constructor method and it creates a new window as "PNG Viewer".
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //.setDefaultCloseOperation is an instance method and sets the close behaviour.
            frame.setResizable(false); //.setResizable is an instance method and it does not allow to set the display resizing.
            frame.setSize(200, 200); //.setSize is an instance method and it sets the windows dimensions.
            frame.getContentPane().setBackground(Color.BLACK); //.getContentPane is an instance method. .setBackground is an instance method. .BLACK is a static constant. It is a reference type.

            try {
                // Load the PNG image
                Image image = ImageIO.read(imageStream); //Static method and returns an image object.

                // Create a JLabel to display the image
                JLabel imageLabel = new JLabel(new ImageIcon(image)); // It is a constructor method and ImageIcon(image) wraps the image. It is a reference type.
                frame.add(imageLabel, BorderLayout.CENTER); //.add is an instance method and it adds the image label to the center od the frame. It is a reference type.

            } catch (IOException e) {
                e.printStackTrace(); //It is an instance method.
            }

            frame.setVisible(true); // .setVisible is an instance method and it displays the frame.
    }
}
