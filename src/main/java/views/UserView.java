package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email=email;
    }
    public void home(){
        do{
            System.out.println("Welcome " + this.email);
            System.out.println("Select from below options : ");
            System.out.println("Press 1 to show hidden files : ");
            System.out.println("Press 2 to  hide new a files : ");
            System.out.println("Press 3 to  unhide a  files : ");
            System.out.println("Press 0  to exit");
            Scanner sc=new Scanner(System.in);
            int ch=Integer.parseInt(sc.nextLine());

            switch (ch){
                case 1 ->{
                    try {
                        List<Data> files= DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for(Data data:files){
                            System.out.println(data.getId() + " - " + data.getFileName());
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                case 2->{
                    System.out.print("Enter the file path : ");
                    String path=sc.nextLine();
                    File f=new File(path);

                    Data file =new Data(0,f.getName(),path,this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

                case 3 ->{
                    List<Data> files= null;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("ID - File Name");
                    for(Data data:files){
                        System.out.println(data.getId() + " - " + data.getFileName());
                    }
                    System.out.print("Enter the file id to unhide : ");
                    int id=Integer.parseInt(sc.nextLine());
                    boolean isValid=false;

                    for(Data data:files){
                        if(data.getId()==id){
                            isValid=true;
                            break;
                        }
                    }
                    if(isValid){
                        try {
                            DataDAO.unhide(id);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        System.out.println("Wrong I'd entered");
                    }
                }

                case 0 ->{
                    System.exit(0);
                }
            }

        }while (true);
    }
}
