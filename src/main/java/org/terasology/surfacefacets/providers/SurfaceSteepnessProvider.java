// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.surfacefacets.providers;

import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.Facet;
import org.terasology.engine.world.generation.FacetBorder;
import org.terasology.engine.world.generation.FacetProviderPlugin;
import org.terasology.engine.world.generation.GeneratingRegion;
import org.terasology.engine.world.generation.Produces;
import org.terasology.engine.world.generation.Requires;
import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;
import org.terasology.engine.world.generator.plugin.RegisterPlugin;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Vector3f;
import org.terasology.surfacefacets.facets.SurfaceNormalFacet;
import org.terasology.surfacefacets.facets.SurfaceSteepnessFacet;

/**
 * Produces a {@link SurfaceSteepnessFacet} with the surface steepness for a world obtained from its surface heights.
 * Requires a {@link SurfaceHeightFacet}.
 */
@RegisterPlugin
@Produces(SurfaceSteepnessFacet.class)
@Requires(@Facet(value = SurfaceHeightFacet.class, border = @FacetBorder(sides = 1)))
public class SurfaceSteepnessProvider implements FacetProviderPlugin {
    @Override
    public void process(GeneratingRegion region) {
        SurfaceHeightFacet surfaceHeightFacet = region.getRegionFacet(SurfaceHeightFacet.class);

        Border3D border = region.getBorderForFacet(SurfaceNormalFacet.class);
        SurfaceSteepnessFacet surfaceSteepnessFacet = new SurfaceSteepnessFacet(region.getRegion(), border);

        for (BaseVector2i position : surfaceSteepnessFacet.getWorldRegion().contents()) {
            Vector3f normal = NormalUtility.getNormalAtPosition(surfaceHeightFacet, position);
            float steepness = (float) Math.acos(normal.dot(Vector3f.up()));

            surfaceSteepnessFacet.setWorld(position, steepness);
        }

        region.setRegionFacet(SurfaceSteepnessFacet.class, surfaceSteepnessFacet);
    }
}
