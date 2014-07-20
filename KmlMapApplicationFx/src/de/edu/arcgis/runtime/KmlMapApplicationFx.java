/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.edu.arcgis.runtime;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jan Tschada
 */
public class KmlMapApplicationFx extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent splitView = FXMLLoader.load(getClass().getResource("KmlServiceView.fxml"));
        
        Scene scene = new Scene(splitView, 800, 600);
        
        primaryStage.setTitle("Kml Map Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
