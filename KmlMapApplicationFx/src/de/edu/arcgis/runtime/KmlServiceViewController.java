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
import com.esri.map.LayerList;
import com.esri.runtime.ArcGISRuntime;
import java.awt.Dimension;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;

/**
 *
 * @author Jan Tschada
 */
public class KmlServiceViewController implements Initializable {
    
    @FXML private SplitPane splitPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add the map on top
        splitPane.getItems().set(0, createMapNode());
    }
    
    private SwingNode createMapNode() {
        ArcGISRuntime.initialize();
        SwingNode mapNode = new SwingNode();
        JMap map = new JMap();
        LayerList layers = map.getLayers();
        layers.add(new ArcGISTiledMapServiceLayer("http://services.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer"));
        mapNode.setContent(map);
        map.setMinimumSize(new Dimension(400, 300));
        return mapNode;
    }
}
