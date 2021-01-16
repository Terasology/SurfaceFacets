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
package org.terasology.surfacefacets.providers;

import org.joml.Vector3f;
import org.terasology.surfacefacets.facets.SurfaceNormalFacet;
import org.terasology.world.block.BlockRegion;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProviderPlugin;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.facets.DensityFacet;
import org.terasology.world.generation.facets.SurfacesFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;

/**
 * Produces a {@link SurfaceNormalFacet} with the surface normals for a world obtained from its
 * surface heights. Requires a {@link SurfacesFacet} and a {@link DensityFacet}.
 */
@RegisterPlugin
@Produces(SurfaceNormalFacet.class)
@Requires({
    @Facet(value = SurfacesFacet.class),
    @Facet(value = DensityFacet.class, border = @FacetBorder(sides = 3, top=3, bottom=3)),
})
public class SurfaceNormalProvider implements FacetProviderPlugin {
    @Override
    public void process(GeneratingRegion region) {
        SurfacesFacet surfacesFacet = region.getRegionFacet(SurfacesFacet.class);
        DensityFacet densityFacet = region.getRegionFacet(DensityFacet.class);

        Border3D border = region.getBorderForFacet(SurfaceNormalFacet.class);
        SurfaceNormalFacet facet = new SurfaceNormalFacet(region.getRegion(), border);
        BlockRegion worldRegion = facet.getWorldRegion();

        for (int x = worldRegion.minX(); x <= worldRegion.maxX(); x++) {
            for (int z = worldRegion.minZ(); z <= worldRegion.maxZ(); z++) {
                for (int surface : surfacesFacet.getWorldColumn(x, z)) {
                    Vector3f normal = getNormalAtPosition(densityFacet, x, surface, z);

                    facet.setWorld(x, surface, z, normal);
                }
            }
        }

        region.setRegionFacet(SurfaceNormalFacet.class, facet);
    }

    private Vector3f getNormalAtPosition(DensityFacet densityFacet, int x0, int y0, int z0) {
        Vector3f normal = new Vector3f();
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    // For each position in a sphere
                    if (12 > x * x + y * y + z * z) {
                        float density = densityFacet.getWorld(x0 + x, y0 + y, z0 + z);
                        normal.add(-density * x, -density * y, -density * z);
                    }
                }
            }
        }
        // "safe normalize"
        normal.normalize();
        if (!normal.isFinite() || normal.length() < 0.000001f) {
            normal.set(0, 0, 0);
        }
        return normal;
    }
}
