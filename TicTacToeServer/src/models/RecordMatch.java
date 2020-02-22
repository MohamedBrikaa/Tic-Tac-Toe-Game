package models;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import controllers.Match;

/**
 * @author nourfayed
 */
public class RecordMatch {
     static final String DB_URL = "jdbc:mysql://localhost:3306/tic-tac-toe";
   static final String DB_DRV = "com.mysql.jdbc.Driver";
   static final String DB_USER = "root";
   static final String DB_PASSWD = "";

    
     public static Connection connect() throws SQLException {
       return (Connection) DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWD);
   }

   
     
    
    public static boolean addMatch(int player1Id,int player2Id,String[][] grid,int playerTurn){
       
       try {
           Connection connection = connect();
           PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO recorded_match ( User1_ID, User2_ID,Cell1,Cell2,Cell3,Cell4,Cell5,Cell6,Cell7,Cell8,Cell9,player_turn) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
           
           preparedStatement.setInt(1, player1Id);
           preparedStatement.setInt(2, player2Id);
           preparedStatement.setString(3, grid[0][0]);
           preparedStatement.setString(4, grid[0][1]);
           preparedStatement.setString(5, grid[0][2]);
           preparedStatement.setString(6, grid[1][0]);
           preparedStatement.setString(7, grid[1][1]);
           preparedStatement.setString(8, grid[1][2]);
           preparedStatement.setString(9, grid[2][0]);
           preparedStatement.setString(10,grid[2][1]);
           preparedStatement.setString(11, grid[2][2]);
           preparedStatement.setInt(12, playerTurn);
            
           int res=preparedStatement.executeUpdate();
           return res>0;
       } catch (SQLException ex) {
           Logger.getLogger(RecordMatch.class.getName()).log(Level.SEVERE, null, ex);
       }
       return false;
    }
    public static Match getRecordedMatch(int playerId1,int playerId2) {  //howa ana kda 3aiza kaman l ba2i 
       try {
           Connection connection = connect();
           Statement statement =connection.createStatement();
           ResultSet resultSet=statement.executeQuery("SELECT * FROM recorded_match");
           String[][] grid= new String[3][3];
           while(resultSet.next()){
               if(resultSet.getInt("User1_ID")==playerId1 && resultSet.getInt("User2_ID")==playerId2  ){
                   
                   for(int i=0;i<3;i++){
                       for(int j=0;j<3;j++){
                           String colName="Cell";
                           int x= i+(3*j);
                           x++;
                           colName+=String.valueOf(x);
                           grid[i][j]=resultSet.getString(colName);
                       }
                   }
                   
                   Match m= new Match(grid,resultSet.getInt("User1_ID"),resultSet.getInt("User2_ID"),resultSet.getInt("Match_ID"),resultSet.getInt("player_turn"));
                   resultSet.close();
                   return m;
               }
           }
           
       } catch (SQLException ex) {
           Logger.getLogger(RecordMatch.class.getName()).log(Level.SEVERE, null, ex);
       }
       
           return null;
    }
    
  
    public static boolean removeMatch(int matchId){
       try {
           Connection connection = connect();
           PreparedStatement preparedStatement=connection.prepareStatement("DELETE FROM recorded_match where Match_ID =?");
           preparedStatement.setInt(1, matchId);
            
           int res=preparedStatement.executeUpdate();
           
           connection.close();
           preparedStatement.close();
           return res>0;
       } catch (SQLException ex) {
           Logger.getLogger(RecordMatch.class.getName()).log(Level.SEVERE, null, ex);
       }
           return false;
        
    }
  
}
