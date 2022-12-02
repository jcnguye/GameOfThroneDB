package GameOfThroneApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GotApp {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement ps;
    public static void main(String []args){

        System.out.println("Game of Throne Application\n");
        System.out.println("Enter Driver");
        System.out.println("Opt1: Adding character Name, Date of birth, Date of death, Weapon, Age, Type");
        System.out.println("Format - Name, DOB, DOD, WEAPON, TYPE");
        System.out.println("Opt1: Delete character Name, Date of birth, Date of death, Weapon, Age, Type");
        System.out.println("Opt1: Modify character Name, Date of birth, Date of death, Weapon, Age, Type");
        System.out.println("Opt1: Search character Name, Date of birth, Date of death, Weapon, Age, Type");
        System.out.println("Opt1: Display character Name, Date of birth, Date of death, Weapon, Age, Type");

        if(args.equals("Add")){
            
        }else if (args.equals("Delete")) {
            
        }else if (args.equals("Modify")){
            
        }else if (args.equals("Search")){
            
        } else if (args.equals("Display")) {
            
        }

//        try{
//            Class.forName(args[0]);
//        }catch (Exception exec){
//            exec.printStackTrace();
//        }
    }
    public void add(){

    }
    public void displayTables(){
        System.out.println("List of tables in Game of thrones database");
    }


}
