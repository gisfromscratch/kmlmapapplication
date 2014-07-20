/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.edu.arcgis.runtime;

import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.JMap;
import com.esri.map.LayerList;
import com.esri.runtime.ArcGISRuntime;
import java.io.IOException;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
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
    
    private SwingNode createMapNode() {
        ArcGISRuntime.initialize();
        SwingNode mapNode = new SwingNode();
        JMap map = new JMap();
        LayerList layers = map.getLayers();
        layers.add(new ArcGISTiledMapServiceLayer("http://services.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer"));
        mapNode.setContent(map);
        return mapNode;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
