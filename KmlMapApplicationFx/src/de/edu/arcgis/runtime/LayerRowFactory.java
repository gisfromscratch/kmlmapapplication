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

/**
 * Creates instances of {@link LayerRow}.
 * @author Jan Tschada
 */
public class LayerRowFactory {
    
    /**
     * Creates a new instance of {@link LayerRow}
     * @param layer
     * @return a new instance of {@link LayerRow}
     */
    public LayerRow createRow(Layer layer) {
        return new LayerRow(layer);
    }
}
