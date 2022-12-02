package GameOfThroneApp;

import java.sql.*;

public class GotApp {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement ps;
    public static void main(String []args){

        System.out.println("Game of Throne Application\n");
        System.out.println("Enter Driver");

        try {
            Class.forName(args[0]);
        }catch (Exception E){
            E.printStackTrace();
        }

        if(args.equals("Add")){
            
        }else if (args.equals("Delete")) {
            
        }else if (args.equals("Modify")){
            
        }else if (args.equals("Search")){
            
        } else if (args.equals("Display")) {
            
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
     * @param type of table (accounting for characters, knights, lords, commoner, monarchs) due to similar tables
     * @param name name for tables
     * @param DOB Date of birth for tables
     * @param DOD Date of death for tables
     * @param weapon Weapons for tables
     * @param age for tables
     */
    public void add(String url, String user, String password,String type, String name, String DOB, String DOD,String weapon, String age){
        try {

            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            ps = connection.prepareStatement("INSERT INTO" + type +"values (?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,DOB);
            ps.setString(3,DOD);
            ps.setString(4,weapon);
            ps.setInt(5,Integer.parseInt(age));
            if (ps.executeUpdate() > 0) {
                System.out.println("SUCESS!!");
                System.out.println("Inserted into: "+ type);
            }
            ps.clearParameters();
            ps.close();
            connection.close();
            statement.close();
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }

    public void displayTables(){
        System.out.println("List of tables in Game of thrones database");
    }


}
