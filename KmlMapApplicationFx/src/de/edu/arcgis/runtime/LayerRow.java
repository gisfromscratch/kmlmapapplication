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

import com.esri.map.Layer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Row representing a ArcGIS Layer.
 * @author Jan Tschada
 */
public class LayerRow {
    
    private Layer layer;
    
    private final SimpleBooleanProperty visible;
    
    private final ReadOnlyStringProperty url;
    
    public LayerRow(Layer layer) {
        this.layer = layer;
        visible = new SimpleBooleanProperty(layer.isVisible());
        
        // Update the layer visibility
        visible.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                LayerRow.this.layer.setVisible(newValue);
            }
        });
        url = new SimpleStringProperty(layer.getUrl());
    }
    
    public BooleanProperty visibleProperty() {
        return visible;
    }
    
    public ReadOnlyStringProperty urlProperty() {
        return url;
    }
}
