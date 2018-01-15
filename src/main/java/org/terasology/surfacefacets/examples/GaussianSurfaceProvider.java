/*
 * Copyright 2017 MovingBlocks
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
package org.terasology.surfacefacets.examples;

import org.terasology.entitySystem.Component;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2f;
import org.terasology.rendering.nui.properties.Range;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.ConfigurableFacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

@Produces(SurfaceHeightFacet.class)
public class GaussianSurfaceProvider implements ConfigurableFacetProvider {
    public static final Vector2f CENTER = Vector2f.zero();

    private GaussianSurfaceSampler surfaceSampler;

    private GaussianSurfaceConfiguration configuration = new GaussianSurfaceConfiguration();

    @Override
    public void initialize() {
        Vector2f radius = new Vector2f(configuration.mountainRadiusX, configuration.mountainRadiusY);
        surfaceSampler = new GaussianSurfaceSampler(CENTER, radius, configuration.mountainHeight);
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(SurfaceHeightFacet.class);
        SurfaceHeightFacet facet = new SurfaceHeightFacet(region.getRegion(), border);

        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents()) {
            facet.setWorld(position, surfaceSampler.sample(new Vector2f(position.x(), position.y())));
        }

        region.setRegionFacet(SurfaceHeightFacet.class, facet);
    }

    @Override
    public String getConfigurationName() {
        return "Lone Mountain";
    }

    @Override
    public Component getConfiguration() {
        return configuration;
    }

    @Override
    public void setConfiguration(Component configuration) {
        this.configuration = (GaussianSurfaceConfiguration) configuration;
    }

    private static class GaussianSurfaceConfiguration implements Component {
        @Range(min = 20, max = 500, increment = 10, description = "Mountain Height")
        public float mountainHeight = 400;

        @Range(min = 10, max = 1000, increment = 10, description = "Mountain Radius X")
        public float mountainRadiusX = 200;

        @Range(min = 10, max = 1000, increment = 10, description = "Mountain Radius Y")
        public float mountainRadiusY = 200;
    }
}