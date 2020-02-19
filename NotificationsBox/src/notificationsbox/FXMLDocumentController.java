/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificationsbox;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.Notifications;

import org.controlsfx.control.action.Action;
import org.controlsfx.tools.Utils;

/**
 *
 * @author pc
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
 //    private Duration hideAfterDuration = Duration.ofSeconds(seconds);
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        Notifications.create()
              .title("Hey!")
               .text("A notification !!").darkStyle()
               .showWarning();
        
    }
    
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
