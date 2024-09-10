package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Welcome {
        public void WelcomeScreen(){
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Welcome to the App : 😊");
            System.out.println("Press 1 :  Login");
            System.out.println("Press 2 : Sign Up");
            System.out.println("Press 0 : Exit 😠");
            System.out.print("Enter your choice : ");

            int choice=0;
            try {
                choice=Integer.parseInt(br.readLine());
            }catch (IOException e){
                e.printStackTrace();
            }

            switch (choice){
                case 1->login();
                case 2->signUp();
                case 0->System.exit(0);
            }
        }

    private void login() {

    }
    private void signUp() {

    }

}
