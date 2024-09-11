package views;

import dao.UserDAO;
import model.User;
import service.SendOTPService;
import service.UserService;
import service.generateOTP;

import javax.sql.rowset.spi.SyncResolver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void WelcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the App : 😊");
        System.out.println("Press 1 :  Login");
        System.out.println("Press 2 : Sign Up");
        System.out.println("Press 0 : Exit 😠");
        System.out.print("Enter your choice : ");

        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Email : ");
        String email = sc.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String genOTP = generateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);

                System.out.print("Enter your OTP : ");
                String otp = sc.nextLine();
                if (otp.equals(genOTP)) {
                    new UserView(email).home();
                    System.out.println("Welcome");
                } else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User don't exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name :");
        String name = sc.nextLine();

        System.out.print("Enter email address : ");
        String email= sc.nextLine();

        //Checking for the mail is vaild or not
        String genOTP = generateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);

        System.out.print("Enter your OTP");
        String otp= sc.nextLine();

        if (otp.equals(genOTP)) {
            User user=new User(name,email);
            int response= UserService.saveUser(user);
            switch (response){
                case '0'-> System.out.println("User Registered");
                case '1'-> System.out.println("User already exist");

            }
        } else {
            System.out.println("Wrong OTP");
        }

    }

}
