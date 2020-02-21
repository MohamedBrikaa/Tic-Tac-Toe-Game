package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
 /*   public String userName;
    public String password;

    public User(){

    }
    public User(String userName, String password){

        this.userName=userName;
        this.password=password;
    }
*/


    public int userID;
    public String userName;
    public String email;
    public String state;
    public String password;
    public String pic;
    public int score;
    private final SimpleStringProperty firstName = new SimpleStringProperty("");
    private final SimpleStringProperty Score = new SimpleStringProperty("");
    private final SimpleStringProperty Email = new SimpleStringProperty("");
    private final SimpleStringProperty State = new SimpleStringProperty("");

    public User() {


    }

    public User(int userId, String userName, String email, String password, int score, String pic, String state) {
        this.userID = userId;
        this.userName = userName;
        this.email = email;
        this.state = state;
        this.firstName.set(userName);
        this.Score.set(String.valueOf(score));
        this.Email.set(email);
        this.password = password;
        this.score = score;
        this.pic = pic;
    }
public User(int userId,String userName,String email,String state,int score)
{
    this.userID = userId;
    this.userName = userName;
    this.email = email;
    this.state = state;
    this.firstName.set(userName);
    this.Score.set(String.valueOf(score));
    this.Email.set(email);
    this.State.set(state);
    this.score = score;


}
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getScore() {
        return Score.get();
    }

    public void setScore(String score) {
        Score.set(score);
    }

    public String getEmail() {
        return Email.get();
    }

    public void setEmail(String email) {
        Email.set(email);
    }
}