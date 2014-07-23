/*
 * Copyright 2014 Jan Tschada.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.edu.arcgis.runtime;

import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.JMap;
import com.esri.map.KMLLayer;
import com.esri.map.Layer;
import com.esri.map.LayerList;
import com.esri.map.MapEvent;
import com.esri.map.MapEventListenerAdapter;
import com.esri.runtime.ArcGISRuntime;
import java.awt.Container;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jan Tschada
 */
public class KmlServiceViewController implements Initializable {
    
    @FXML
    private SplitPane splitPane;
    
    @FXML
    private TextField urlInputTextField;
    
    @FXML
    private Button addKmlLayerButton;
    
    @FXML
    private Button removeKmlLayerButton;
    
    @FXML
    private TableView<LayerRow> layerTable;
    
    @FXML
    private TableColumn<LayerRow, Boolean> visibleColumn;
    
    @FXML
    private TableColumn<LayerRow, String> urlColumn;
    
    private final LayerRowFactory rowFactory;
    
    public KmlServiceViewController() {
        rowFactory = new LayerRowFactory();
    }
    
    private static JMap map;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add the map on top
        SwingNode mapNode = createMapNode();
        splitPane.getItems().set(0, mapNode);
        
        // Intialize the table columns
        visibleColumn.setCellValueFactory(new PropertyValueFactory<LayerRow, Boolean>("visible"));
        urlColumn.setCellValueFactory(new PropertyValueFactory<LayerRow, String>("url"));

        // Set the cell factory
        visibleColumn.setCellFactory(CheckBoxTableCell.forTableColumn(visibleColumn));
        urlColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        // Setup the data source
        ObservableList<LayerRow> layerItems = FXCollections.observableArrayList();
        layerTable.setItems(layerItems);
        
        // Bind selecting layers with remove button
        TableView.TableViewSelectionModel<LayerRow> tableSelectionModel = layerTable.getSelectionModel();
        removeKmlLayerButton.disableProperty().bind(tableSelectionModel.selectedItemProperty().isNull());
        
        if (null != map) {
            map.addMapEventListener(new MapEventListenerAdapter() {
                
                @Override
                public void mapReady(MapEvent mapEvent) {
                    // Add a KML layer
                    addKmlLayer("https://maps.google.com/maps/ms?hl=de&ie=UTF8&oe=UTF8&msa=0&msid=201976294070805075493.0004fc4b042b1cae45153&dg=feature&output=kml");
                    
                    // Delegate to FX thread
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            addKmlLayerButton.setDisable(false);
                        }                        
                    });
                }
            });
        }
    }
    
    public void addKmlLayer(ActionEvent event) {
        if (null != map) {
            addKmlLayer(urlInputTextField.getText());
        }
    }
    
    public void removeKmlLayer(ActionEvent event) {
        if (null != map) {
            TableView.TableViewSelectionModel<LayerRow> tableSelectionModel = layerTable.getSelectionModel();
            ObservableList<LayerRow> selectedLayerItems = tableSelectionModel.getSelectedItems();
            LayerList layers = map.getLayers();
            ObservableList<LayerRow> layerItems = layerTable.getItems();
            for (LayerRow selectedLayerItem : selectedLayerItems) {
                Layer layer = selectedLayerItem.getLayer();
                layers.remove(layer);
                layerItems.remove(selectedLayerItem);
            }
        }
    }

    private void addKmlLayer(String layerUrl) {
        KMLLayer kmlLayer = new KMLLayer(layerUrl);
        LayerList layers = map.getLayers();
        layers.add(kmlLayer);
        
        LayerRow layerRow = rowFactory.createRow(kmlLayer);
        ObservableList<LayerRow> layerItems = layerTable.getItems();
        layerItems.add(layerRow);
    }
    
    public static void stop() {
        if (null != map) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    
                    @Override
                    public void run() {
                        // TODO: Application won't stop here
                        map.dispose();
                        map = null;
                    }
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(KmlServiceViewController.class.getName()).log(Level.SEVERE, "Disposing the map failed!", ex);
            }
        }
    }
    
    private SwingNode createMapNode() {
        SwingNode mapNode = new SwingNode();
        if (null == map) {
            // TODO: Yields to an access violation
            //ArcGISRuntime.setRenderEngine(ArcGISRuntime.RenderEngine.OpenGL);
            ArcGISRuntime.initialize();
            
            map = new JMap();
            LayerList layers = map.getLayers();
            layers.add(new ArcGISTiledMapServiceLayer("http://services.arcgisonline.com/arcgis/rest/services/ESRI_StreetMap_World_2D/MapServer"));
            mapNode.setContent(map);
            map.setMinimumSize(new Dimension(400, 300));
        }
        return mapNode;
    }
}
