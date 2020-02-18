
package recordmatch;

/*
 * @author nourfayed
 */
public class Match {
     public int player1Id,player2Id, matchId,playerTurn;
     public String[][] grid={{"","",""},{"","",""},{"","",""}};
     public Match(String[][]grid,int player1Id,int player2Id,int matchId,int playerTurn){
         this.grid=grid;
         this.player1Id=player1Id;
         this.player2Id=player2Id;
         this.matchId=matchId;
         this.playerTurn=playerTurn;
     }
}
