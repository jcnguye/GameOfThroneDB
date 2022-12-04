package GameOfThroneApp;

import java.sql.*;

public class GotApp {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement ps;
    public static void main(String []args){

        String driver = "com.mysql.cj.jdbc.Driver";
        String user = args[2];
        String password = args[3];
        String url = args[1];
        String tableName = args[5];
        String Choice = args[4];
        System.out.println("\t\t\tGAME OF THRONE");

        System.out.println(" <>=======() \n" +
                "(/\\___   /|\\\\          ()==========<>_\n" +
                "      \\_/ | \\\\        //|\\   ______/ \\)\n" +
                "        \\_|  \\\\      // | \\_/\n" +
                "          \\|\\/|\\_   //  /\\/\n" +
                "           (oo)\\ \\_//  /\n" +
                "          //_/\\_\\/ /  |\n" +
                "         @@/  |=\\  \\  |\n" +
                "              \\_=\\_ \\ |\n" +
                "                \\==\\ \\|\\_    \n" +
                "             __(\\===\\(  )\\\n" +
                "            (((~) __(_/   |\n" +
                "                 (((~) \\  /\n" +
                "                 ______/ /\n" +
                "                 '------'");

        try {
            Class.forName(driver);
        }catch (Exception E){
            E.printStackTrace();
        }

        switch (Choice) {
            case "AllTables":
                break;
            case "AddCharacters": //Adding to tables that are characters, knights, lords, commoner, monarchs
                addCharacter(url,user,password,args[6],args[7],args[8],args[9],args[10],args[11]);
                break;
            case "Delete":
                break;
            case "Modify":

                break;
            case "Search":

                break;
            case "Display":
                displayTables(url,user,password,tableName);
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
     * @param type of table (accounting for characters, knights, lords, commoner, monarchs) due to similar tables
     * @param name name for tables
     * @param DOB Date of birth for tables
     * @param DOD Date of death for tables
     * @param weapon Weapons for tables
     * @param age for tables
     */
    public static void addCharacter(String url, String user, String password, String type, String name, String DOB, String DOD, String weapon, String age){
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

    public static void displayTables(String url, String user, String password, String table){
        System.out.println("List of tables in Game of thrones database");

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from "+ table);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for(int i = 1; i<=rsMetaData.getColumnCount(); i++){
                System.out.println(rsMetaData.getColumnName(i));

            }

//            while (resultSet.next()){
//                System.out.println(resultSet.getString());
//            }
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }
}
