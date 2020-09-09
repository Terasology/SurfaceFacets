// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.surfacefacets.examples;

import org.terasology.engine.entitySystem.Component;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.ConfigurableFacetProvider;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;
import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2f;
import org.terasology.nui.properties.Range;

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
