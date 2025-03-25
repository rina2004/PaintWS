/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package func;

import java.util.Random;

/**
 *
 * @author Rinaaaa
 */
public class TestDataGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final Random RANDOM = new Random();

    public static String genRandomUsername() {
        StringBuilder username = new StringBuilder();

        for (int i = 0; i < RANDOM.nextInt(10, 21); i++) {
            username.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return username.toString();
    }
    
    public static String genRandomFullname() {
        StringBuilder fullname = new StringBuilder();
        for (int i = 0; i < RANDOM.nextInt(2, 5); i++) {
            fullname.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
            for (int j = 0; j < RANDOM.nextInt(10); j++) {
                fullname.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
            }
            fullname.append(" ");
        }
        
        return fullname.toString();
    }

    public static String genRandomEmail() {
        StringBuilder email = new StringBuilder();

        for (int i = 0; i < RANDOM.nextInt(10,21); i++) {
            email.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        String[] domains = {"@gmail.com", "@yahoo.com", "@outlook.com", "@example.com"};
        email.append(domains[RANDOM.nextInt(domains.length)]);

        return email.toString();
    }
    
    public static String genRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            phoneNumber.append(RANDOM.nextInt(10));
        }
        return phoneNumber.toString();
    }
}
