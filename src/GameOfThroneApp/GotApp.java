package GameOfThroneApp;

import java.security.PublicKey;
import java.sql.*;

public class GotApp {
    private static ResultSet resultSet = null;
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement ps;
    public static void main(String []args){

        String driver = "com.mysql.cj.jdbc.Driver";
        //FOR DEBUGGING CLI COMMANDS
        String url = args[0];
        String user = args[1];
        String password = args[2];
        String Choice = args[3];
        String tableName = args[4];

        System.out.println("\t\t\t\t\t\tGAME OF THRONE");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣤⣤⣴⣯⣿⣶⣶⣶⠖⠒⠒⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡄⠀⠀⠠⣦⣀⣛⣛⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡄⢀⣀⣀⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣾⣿⣶⣶⣤⡄⠀⠉⢩⣿⣿⣿⣿⣛⠫⠙⠂⣻⣿⣿⣷⡌⠙⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡴⣾⣿⣿⣿⣿⣿⣿⣿⣿⣷⠀⠈⠋⠁⠀⠀⠀⠀⠀⠀⢹⣿⣿⣿⠀⠀⣠⣼⣿⣿⣿⣷⣶⣦⣄⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⣾⣿⣿⡿⠟⠂⠉⠛⢿⣿⣿⣿⠆⠀⠀⠀⠀⠀⠀⢀⣠⣿⣿⣿⠿⠀⣰⣿⣿⠟⠉⠉⢹⣿⡿⢿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⣠⣾⣿⢿⢿⣿⣟⡋⠀⠀⠀⠀⠘⣻⣿⣿⣦⠀⠀⠀⠀⣀⣿⣿⣿⣿⡿⠟⠀⠀⢹⣿⣿⡀⠀⠀⣼⣿⡿⠈⢻⣿⣿⣶⡆⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠐⣬⣉⠔⢱⣿⡟⠉⠀⠀⠀⠀⠀⠀⣿⣿⣿⡏⠀⣀⣴⣿⣿⣿⣿⡟⠟⠁⠀⠀⠀⠀⠛⣿⣿⣶⣿⣿⡿⠃⠀⠈⠉⣽⣿⣿⣶⡄⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠙⠉⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣧⣾⣿⣿⡿⢿⠏⠉⠀⢀⣀⣠⣤⣤⣤⣤⣬⣿⣿⣍⡉⠀⠀⠀⠀⠀⠀⢽⣿⣿⣿⣤⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⣼⠀⢀⡄⢀⣴⣆⣴⣶⣤⣤⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⠿⠁⠀⣠⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣤⣀⠀⠀⠀⠉⣻⣿⣿⣇⠀⠀⠀⠀\n" +
                "⠀⠀⢰⣏⣰⣿⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣗⣀⠀⣼⣿⣿⣿⣿⡿⠇⠀⠀⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⡀⠀⠈⠻⣿⣿⡆⠀⠀⠀\n" +
                "⠀⢀⣾⣿⣿⣿⣿⠿⠋⠉⠉⠉⠻⠿⣿⣿⣿⣿⣥⣼⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠙⠻⣿⣿⣿⣿⣿⣿⣿⣦⡀⠈⠻⢿⣷⡄⠀⠀\n" +
                "⠀⣟⣿⣿⣿⡿⠛⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⡇⠀⠈⠉⠛⠀⢀⠀⠈⢻⣿⣿⠿⣿⣿⣿⣿⣦⠈⠻⣿⣷⣠⠀\n" +
                "⠀⣿⣿⣿⣿⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣿⣿⣿⣿⣿⣿⣿⣶⣦⣄⣀⣠⠀⣠⣾⣿⣿⠿⠛⠓⠀⠀⠀⠀⢠⣿⣷⣦⣄⠙⠁⠀⠈⢿⣿⣿⣿⣷⡀⢾⣿⣿⡀\n" +
                "⢰⣿⢻⣻⣿⡏⠀⠀⠀⠀⠀⢤⣤⣤⣶⡄⠀⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⣤⣤⣤⡄⢀⣠⣿⣿⣿⣿⣿⣷⡄⠀⠀⠘⣿⣿⣿⣿⣷⡈⢿⣿⡇\n" +
                "⠻⠟⢸⠇⠿⠿⠀⠀⠀⢀⣤⣿⣿⣿⣿⣿⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆⠀⠀⢻⣿⣿⣿⣿⣷⠈⣿⡇\n" +
                "⠐⠶⣋⠀⠀⠀⠀⠀⠀⠁⠙⠻⢿⣿⣿⣿⡆⠀⠀⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠀⠸⡿⣿⣿⣿⣿⡇⠸⡇\n" +
                "⠀⣿⣿⠀⠀⠀⠀⣠⡤⠀⠀⠀⠀⠙⢿⣿⣷⡀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆⠀⠀⠀⠹⣿⣿⣷⠀⠃\n" +
                "⠀⢿⣿⠀⢴⣷⣾⣿⣿⣿⣿⣂⡀⠀⢸⣿⣿⣷⡄⠀⠀⠀⠉⠙⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡀⠀⠀⢀⣿⣿⣿⠀⠀\n" +
                "⠀⣾⣿⣇⣾⡟⠁⠀⠀⠉⢿⣿⡟⠂⢿⣿⠉⢻⣿⣦⣀⠀⠀⠀⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⢿⣿⣿⣿⣿⡇⠀⠀⢸⣿⣿⣿⠀⠀\n" +
                "⠀⠨⣿⣿⣿⠀⠀⠀⠀⠀⠀⣿⣷⡆⠛⠁⠀⠀⠙⠻⣿⣿⣶⣤⣼⣿⣿⣿⣿⠋⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⡇⠀⣿⣿⣿⣿⣿⠀⠀⣾⣿⣿⡿⠀⠀\n" +
                "⠀⠀⠘⣿⣷⣄⠀⠀⠀⢀⣰⣿⡏⠃⠀⠀⠀⠀⠀⠀⠀⠙⠻⢿⣿⣿⣿⣿⠃⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⣿⣿⣿⣿⣿⠀⠀⢹⣿⣿⡇⠀⠀\n" +
                "⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⡟⠹⠃⠀⠀⠀⠀⢠⣤⡀⠀⠀⠀⠀⠈⣻⡿⠃⠀⠀⢸⣿⣿⣿⣿⣿⣿⠟⢻⣿⣿⣿⣿⣿⡉⠃⢠⣿⣿⣿⣿⣿⠀⠀⠀⣿⡿⠀⠀⠀\n" +
                "⠀⠀⠀⠈⣻⣿⡄⠀⠘⠉⠀⠀⠀⠀⠀⢀⣴⣶⣾⣿⣄⠀⠀⠀⠈⠁⠀⠀⠀⠀⠀⠙⣿⣿⣿⣿⡏⠀⣼⣿⣿⣿⣿⣿⡗⠀⠀⠈⣿⣿⣿⡇⠀⠀⢰⣿⠃⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠙⢿⣿⣄⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣷⣤⣀⠀⠀⠀⠀⠀⢀⣠⣴⣿⣿⣿⣿⡇⠀⣿⣿⣿⣿⣿⣇⠃⠀⠀⣼⣿⣿⣿⠁⠀⢠⣿⠃⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⢾⣿⣧⡀⠀⠀⠀⠀⠀⠘⠙⠉⠉⠛⠻⣿⣿⡿⠿⠿⠿⣿⣿⣿⣿⣿⣿⡿⠿⣿⠇⢰⣿⣿⣿⣿⢿⡏⠀⣠⣾⣿⣿⣿⠃⠀⣠⡿⠃⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⢩⣿⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠃⣠⣿⣿⣿⣿⡿⠈⠀⢺⣿⣿⣿⡿⠃⢀⣼⠟⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⣛⣿⣶⣄⡀⠀⠀⠀⠀⠀⠀⢈⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣾⣿⣿⣿⡟⠛⠁⠀⠀⢈⣿⣿⠟⠁⡴⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠻⢿⣿⣶⣦⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⣴⣾⣿⣿⣿⠻⠟⠁⠀⠀⢀⣤⣾⡿⠋⠀⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠻⠿⠿⣿⣿⣿⣶⣶⣶⣶⣶⣶⣶⣿⣿⣿⣿⣿⣿⠟⠻⠟⠁⠀⣰⣶⣶⣿⣿⠟⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠛⠛⠛⢻⣿⣿⡿⠛⣻⣿⡿⠋⠙⠋⠁⠀⠀⢀⣠⣾⣿⡿⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠤⠤⣤⣤⣤⣤⣴⣶⡾⠿⠟⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣄⣀⠀⠀⠀⠀⠙⣟⡓⠲⠶⠤⠤⠶⠒⠛⠙⢓⣲⣦⣤⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢛⣯⣉⠉⠉⠩⣭⡭⠴⠶⠶⠿⢄⠀⠀⠀⠀⠀⣀⣀⡤⠽⠿⠿⣶⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠛⢿⣖⣒⠛⠉⢉⡄⢖⠒⠚⠋⣁⣤⣀⣰⣄⣀⣤⠑⠀⠀⠀⢀⣠⡤⣤⣤⠤⢤⣽⡈⠳⠦⣤⣤⣄⣀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⢀⣀⣀⡤⠤⠖⠛⡽⠋⠉⣹⠏⠀⠀⠙⠲⣄⠰⠬⢭⡀⠁⠈⠁⠑⠲⠯⠑⣦⠀⠙⢄⡈⢙⡾⠷⠒⠉⢉⣉⣀⣉⡉⠛⢷⡶⠦⣤\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣉⣽⡶⠶⠖⣾⠁⠀⠐⠛⡲⠦⠤⢴⠆⠈⠳⣄⠀⠙⠋⠉⠉⠁⠂⠀⠀⠛⢀⣠⠴⠚⣋⡀⠐⠒⠒⠲⣤⡀⠀⠉⠲⡀⠙⣯⠋\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠈⠳⢖⡛⠉⠉⠁⡼⠀⠀⠐⣻⠶⠶⠶⣾⠁⠀⠀⢸⠀⠀⠀⠈⠙⠛⠛⠉⣩⠉⠂⠀⡠⠚⠉⣠⠶⢯⠉⡉⠓⠦⣄⡀⠀⠙⢄⡀⠀⢀⡴⠃⠀\n" +
                "⠀⠀⠀⢀⣀⣀⣠⡤⠴⠒⣻⠟⢛⣽⣃⣀⢀⣼⠃⠀⠀⠀⡏⠀⣀⣀⣼⠴⠒⠒⢶⠁⠀⠀⠉⠁⠀⠀⡘⠁⣠⡎⠙⠦⠚⢯⣩⠷⡖⣬⣭⠷⢦⣤⣤⡤⠞⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠉⢓⡦⢤⣀⡼⠃⠀⢠⠏⠁⠉⣩⣇⣀⣀⣤⡞⠋⠉⠉⢸⠃⠀⠀⠀⢸⠀⠀⠀⢀⡄⠀⠀⠁⡞⠹⡙⢶⣾⣀⡄⠁⠀⠉⠀⠸⠗⢊⣏⡜⠀⠀⠀⠀⠀\n" +
                "⠠⣤⡤⠴⠚⠉⣰⠋⠛⠓⠒⢶⡟⠀⠀⢀⡟⠉⠀⠀⢸⡇⠀⠀⠀⣼⢀⣀⣀⣠⡿⠒⠒⠚⣿⠁⠀⠀⠀⠁⠀⠦⣀⠉⠢⢼⡤⢶⡄⡀⠀⢀⡌⠁⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠙⠲⠤⣼⠃⠀⠀⠀⣐⡿⠒⠶⠶⢾⠀⠀⠀⢀⣾⡥⠤⠴⣾⠛⠉⠉⠉⡏⠀⠀⠀⠀⣿⠀⠀⢀⡰⠀⠀⠀⠀⠙⠲⢄⡈⠛⠯⣼⠚⣡⠃⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠒⢹⡏⠉⠛⠉⡟⠀⠀⠀⢀⣾⡤⠴⠖⡿⠁⠀⠀⠀⡇⠀⠀⠀⢠⣇⣤⣤⡤⢶⡷⠶⡞⠉⢀⡴⠚⠉⠉⠉⠲⡀⠈⠒⠀⠀⡹⠁⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⣸⣀⣠⣤⣴⣧⠤⠤⠶⣾⠁⠀⠀⠀⡇⠀⠀⠀⣰⣧⠤⠤⣶⠋⠁⢠⡇⠀⠀⣀⣼⠁⣰⠋⠀⠀⠀⠀⠀⢀⠷⠶⣾⣃⡴⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠁⠀⠀⠀⡟⠀⠀⠀⢀⡇⠀⠀⣀⣰⡷⠖⠒⣻⠏⠀⠀⠀⣿⠀⠀⠸⠗⣻⠋⢉⣼⣼⠃⠀⠀⠀⠀⠀⠀⠀⠀⠐⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⢀⣇⣀⣀⣤⡾⠛⠉⠉⢹⠏⠀⠀⠀⢹⡀⠀⠀⢀⣿⣀⣤⡤⠞⣿⢀⡞⠈⢿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠀⠀⢸⡇⠀⠀⠀⢸⠀⠀⠀⢀⣸⣧⠴⠖⠋⠁⢸⠃⠀⣴⢿⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣇⣀⣀⣤⣿⠗⠛⠋⠉⢹⡇⠀⠀⠀⢀⣿⠀⡼⠁⠀⢻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠰⡏⠀⠀⠀⠀⣼⣇⣤⡴⠖⢋⣽⣶⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣇⣀⣀⣤⣶⠋⠁⢸⡇⢀⡞⠁⠻⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⡇⠀⠀⣠⣿⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣧⠀⡼⠁⠈⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣴⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⠁⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("\t\t\t\t\t\tGAME OF THRONES");
        for (String arg : args) {
            System.out.println(arg);
        }

        try {
            Class.forName(driver);
        }catch (Exception E){
            E.printStackTrace();
        }

        switch (Choice) {
            case "AddCastleCitieProvince":
                addCastleCitiProv(url,user,password,tableName,args[5]);
                break;
            case "AddCharacters": //Adding to tables that are characters, knights, lords, commoner, monarchs
                String name = args[5];
                String DOB = args[6];
                String DOD = args[7];
                String weapon = args[8];
                String age = args[9];//max 9
                System.out.println(args[5]);
                if(args.length != 10){
                    System.out.println("Incorrect format");
                    return;
                }
                addCharacter(url,user,password,tableName,name,DOB,DOD,weapon,age); //add character format cli name dob dod weapon age
                break;
            case "AddHouses":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddLocatedin":
                System.out.println(args[5]);
                System.out.println(args[6]);
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddKingsGuard_to":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddContains":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddFrom":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddbelongTo":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddLordShipOver":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddPledgeTo":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddSeatOfPower":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddSwearsLoyaltyTo":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
            case "AddWards":
                addTableColumn2(url,user,password,tableName,args[5],args[6]);
                break;
                //--------------------------All methods above pretaining to add
            case "Delete":
                deleteRecord(url,user,password,tableName,args[5],args[6]);
                break;
            case "Modify":
                modifyRecord(url,user,password,tableName,args[5],args[6],args[7],args[8]);
                break;
            case "Search":
                searching(url,user,password,tableName,args[5],args[6]);
                break;
            case "DisplayTable":
                displayTable(url,user,password,tableName);
                break;
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
     * Method to update and modify tables
     * method for deleting records in table
     * @param url for connection
     * @param user for connection
     * @param password for connection
     * @param tableName of table (accounting for characters, knights, lords, commoner, monarchs) due to similar tables
     * @param arg1 first argument in SET clause within update statement
     * @param arg2 second argument in SET clause within update statement
     * @param arg3 first argument in WHERE clause within update statement
     * @param arg4 second argument in WHERE clause within update statement
     */
    public static void modifyRecord(String url, String user, String password, String tableName, String arg1, String arg2, String arg3, String arg4){
        try {
            System.out.println("Modifying "+ tableName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            DatabaseMetaData dbmd = (DatabaseMetaData) connection.getMetaData();
            resultSet = dbmd.getTables(null, null, tableName, null);
            //UPDATE tableName SET arg1 = arg2 WHERE arg3 = arg 4
            ps = connection.prepareStatement("UPDATE" + tableName + " SET (? = ?) WHERE (? = ?)");
            ps.setString(1,arg1);
            ps.setString(2,arg2);
            ps.setString(3,arg3);
            ps.setString(4,arg4);

            if (ps.executeUpdate() > 0) {
                System.out.println("SUCESS!!");
            }

            System.out.println("Updated " + tableName + " where " + arg1 + " = " + arg2 + " and " + arg3 + " = " + arg4);
            ps.clearParameters();
            ps.close();
            connection.close();
            statement.close();
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }

    /**
     * method for deleting records in table
     * @param url for connection
     * @param user for connection
     * @param password for connection
     * @param tableName of table (accounting for characters, knights, lords, commoner, monarchs) due to similar tables
     * @param arg1 first argument in WHERE clause within delete statement
     * @param arg2 second argument in WHERE clause within delete statement
     */
    public static void deleteRecord(String url, String user, String password, String tableName, String arg1, String arg2){
        try {
            System.out.println("Deleting From "+ tableName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            DatabaseMetaData dbmd = (DatabaseMetaData) connection.getMetaData();
            resultSet = dbmd.getTables(null, null, tableName, null);
            ps = connection.prepareStatement("DELETE FROM" + tableName + " WHERE (? = ?)");
            ps.setString(1,arg1);
            ps.setString(2,arg2);

            if (ps.executeUpdate() > 0) {
                System.out.println("SUCESS!!");
            }

            System.out.println("Deleted Record: " + arg1 + " from " + tableName);
            ps.clearParameters();
            ps.close();
            connection.close();
            statement.close();
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }

    /**
     * Method only accounts for adding characters, knights, lords, commoner, monarchs since similar columns
     * @param url for connection
     * @param user for connection
     * @param password for connection
     * @param tableName of table (accounting for characters, knights, lords, commoner, monarchs) due to similar tables
     * @param name name for tables
     * @param DOB Date of birth for tables
     * @param DOD Date of death for tables
     * @param weapon Weapons for tables
     * @param age for tables
     */
    public static void addCharacter(String url, String user, String password, String tableName, String name, String DOB, String DOD, String weapon, String age){
        try {
            System.out.println("Inserted into "+ tableName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            DatabaseMetaData dbmd = (DatabaseMetaData) connection.getMetaData();
            resultSet = dbmd.getTables(null, null, tableName, null);
            ps = connection.prepareStatement("INSERT INTO " + tableName + " values (?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,DOB);
            ps.setString(3,DOD);
            ps.setString(4,weapon);
            ps.setInt(5,Integer.parseInt(age));
            if (ps.executeUpdate() > 0) {
                System.out.println("SUCESS!!");
            }
            System.out.println("Inserted data: "+name+ " "+DOB+ " "+DOD+ " "+weapon+" "+age);
            ps.clearParameters();
            ps.close();
            connection.close();
            statement.close();
        }catch (Exception exec){
            exec.printStackTrace();
        }

    }
    public static void searching(String url, String user, String password, String tableName, String name, String SpecificName){
        System.out.println("Searching " + tableName);

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            String sql = "Select *\n" +
                    "from characters\n" +
                    "where Name = 'Jon Snow'";
            String p1 = "Select *\n";
            String p2 = "from " + tableName + "\n";
            String p3 = "where " + name + " = " + SpecificName;
            String sql2 = p1 + " " + p2 + " "+p3;
            resultSet = statement.executeQuery(sql2);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for (int i=1; i <= rsMetaData.getColumnCount(); i++) {
                System.out.print(rsMetaData.getColumnLabel(i) + "\t\t\t");
            }
            System.out.println("");
            while (resultSet.next()){
                for (int i=1; i <= rsMetaData.getColumnCount(); i++) {
                    Object obj = resultSet.getObject(i);
                    if (obj != null)
                        System.out.print(resultSet.getObject(i).toString() + "\t\t\t");
                }
                System.out.println("");

            }

        }catch (Exception exec){
            exec.printStackTrace();
        }
    }
    public static void addTableColumn2(String url, String user, String password, String tableName,String c1, String c2){
        try {
            System.out.println("Inserted into "+ tableName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            DatabaseMetaData dbmd = (DatabaseMetaData) connection.getMetaData();
            resultSet = dbmd.getTables(null, null, tableName, null);
            ps = connection.prepareStatement("INSERT INTO " + tableName + " values (?,?)");
            ps.setString(1,c1);
            ps.setString(2,c2);
            if (ps.executeUpdate() > 0) {
                System.out.println("SUCESS!!");
            }
            System.out.println("Inserted data: "+c1+ " "+c2);
            System.out.println();
            displayTable(url,user,password, tableName);
            ps.clearParameters();
            ps.close();
            connection.close();
            statement.close();
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }
    public static void addCastleCitiProv(String url, String user, String password, String tableName,String locationName){
        try {
            System.out.println("Inserted into "+ tableName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            DatabaseMetaData dbmd = (DatabaseMetaData) connection.getMetaData();
            resultSet = dbmd.getTables(null, null, tableName, null);
            ps = connection.prepareStatement("INSERT INTO " + tableName + " values (?)");
            ps.setString(1,locationName);
            if (ps.executeUpdate() > 0) {
                System.out.println("SUCESS!!");
            }
            System.out.println("Inserted data: "+locationName);
            ps.clearParameters();
            ps.close();
            connection.close();
            statement.close();
        }catch (Exception exec){
            exec.printStackTrace();
        }
    }
    public static void displayTable(String url, String user, String password, String table){
        System.out.println("Table from " + table);

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from "+ table);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for (int i=1; i <= rsMetaData.getColumnCount(); i++) {
                System.out.print(rsMetaData.getColumnLabel(i) + "\t\t\t");
            }
            System.out.println("");
                while (resultSet.next()){
                    for (int i=1; i <= rsMetaData.getColumnCount(); i++) {
                        Object obj = resultSet.getObject(i);
                        if (obj != null)
                            System.out.print(resultSet.getObject(i).toString() + "\t\t\t");
                    }
                    System.out.println("");

                }

        }catch (Exception exec){
            exec.printStackTrace();
        }
    }
}
