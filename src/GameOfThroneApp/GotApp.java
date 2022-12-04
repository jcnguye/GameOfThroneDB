package GameOfThroneApp;

import java.sql.*;
import java.util.Scanner;

public class GotApp {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement ps;
    public static void main(String []args){
        Scanner scan = new Scanner(System.in);

        String driver = "com.mysql.cj.jdbc.Driver";
        String user = args[1];
        String password = args[2];
        String url = "jdbc:mysql://localhost:3306/got";
        String table = args[4];
        String Choice = args[3];
        System.out.println("Table entered:\t"+ table);
        try {
            Class.forName(driver);
        }catch (Exception E){
            E.printStackTrace();
        }

        switch (Choice) {
            case "AllTables":

                break;
            case "AddCharacter":
                System.out.println("Please Input Character Name: ");
                String name = scan.nextLine();
                System.out.println("Please Input Character Date of Birth: ");
                int DOB = scan.nextInt();
                System.out.println("Please Input Character Date of Death: ");
                int DOD = scan.nextInt();
                System.out.println("Please Input Character Weapon: ");
                String weapon = scan.nextLine();
                System.out.println("Please Input Character Age: ");
                int age = scan.nextInt();

                //addCharacter(url, user, password, type, name, DOB, DOD, weapon, age);


                break;
            case "Delete":
                break;
            case "Modify":

                break;
            case "Search":

                break;
            case "Display":
                displayTables(url,user,password,table);
                break;
        }

        try{
            Class.forName(args[0]);
        }catch (Exception exec){
            exec.printStackTrace();
        }
        try {
            if(connection!= null){
                connection.close();
            }
            if(statement!= null){
                statement.close();
            }
            if(resultSet!= null){
                resultSet.close();
            }
        }catch (Exception sexc){
            sexc.printStackTrace();
        }

    }

    /**
     * Method only accounts for adding characters, knights, lords, commoner, monarchs since similar columns
     * @param url for connection
     * @param user for connection
     * @param password for connection
     * @param tableName name of table (accounting for characters, knights, lords, commoner, monarchs) due to similar tables
     * @param name name for tables
     * @param DOB Date of birth for tables
     * @param DOD Date of death for tables
     * @param weapon Weapons for tables
     * @param age for tables
     */
    public void addCharacter(String url, String user, String password,String tableName, String name, String DOB, String DOD,String weapon, String age){
        try {

            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            ps = connection.prepareStatement("INSERT INTO" + tableName +"values (?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,DOB);
            ps.setString(3,DOD);
            ps.setString(4,weapon);
            ps.setInt(5,Integer.parseInt(age));
            if (ps.executeUpdate() > 0) {
                System.out.println("SUCESS!!");
                System.out.println("Inserted into: "+ tableName);
            }
            ps.clearParameters();
            ps.close();
            connection.close();
            statement.close();
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }

    public static void displayTables(String url, String user, String password, String table){
        System.out.println("List of tables in Game of thrones database");

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from "+ table);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for(int i = 1; i<=rsMetaData.getColumnCount(); i++){
//                System.out.println(rsMetaData.getColumnName(i));
                System.out.println(rsMetaData.getTableName(i));
                for(int k = 0;  k <= rsMetaData.getColumnCount(); k++){

                }
            }

//            while (resultSet.next()){
//                System.out.println(resultSet.getString());
//            }
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }


}
