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

import org.joml.Vector3fc;
import org.joml.Vector3ic;
import org.terasology.surfacefacets.facets.SurfaceNormalFacet;
import org.terasology.surfacefacets.facets.SurfaceSteepnessFacet;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProviderPlugin;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.Requires;
import org.terasology.world.generator.plugin.RegisterPlugin;

import java.util.Map;

/**
 * Produces a {@link SurfaceSteepnessFacet} with the surface steepness for a world obtained from its
 * surface heights. Requires a {@link SurfaceNormalFacet}.
 */
@RegisterPlugin
@Produces(SurfaceSteepnessFacet.class)
@Requires(@Facet(value = SurfaceNormalFacet.class))
public class SurfaceSteepnessProvider implements FacetProviderPlugin {
    @Override
    public void process(GeneratingRegion region) {
        SurfaceNormalFacet surfaceNormalFacet = region.getRegionFacet(SurfaceNormalFacet.class);

        Border3D border = region.getBorderForFacet(SurfaceSteepnessFacet.class);
        SurfaceSteepnessFacet surfaceSteepnessFacet = new SurfaceSteepnessFacet(region.getRegion(), border);

        Map<Vector3ic, Vector3fc> normals = surfaceNormalFacet.getWorldEntries();
        for (Vector3ic position : normals.keySet()) {
            Vector3fc normal = normals.get(position);
            float steepness = (float) Math.atan2(Math.hypot(normal.x(), normal.z()), normal.y());

            surfaceSteepnessFacet.setWorld(position, steepness);
        }

        region.setRegionFacet(SurfaceSteepnessFacet.class, surfaceSteepnessFacet);
    }
}
