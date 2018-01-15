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

import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.core.world.generator.facetProviders.SurfaceToDensityProvider;
import org.terasology.core.world.generator.rasterizers.TreeRasterizer;
import org.terasology.engine.SimpleUri;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector2i;
import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.In;
import org.terasology.world.WorldProvider;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.World;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;

import java.math.RoundingMode;

@RegisterWorldGenerator(id = "surfaceFacetsExample", displayName = "SurfaceFacets Example")
public class SurfaceFacetsExampleWorldGenerator extends BaseFacetedWorldGenerator {
    @In
    private WorldGeneratorPluginLibrary worldGeneratorPluginLibrary;

    public SurfaceFacetsExampleWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld() {
        return new WorldBuilder(worldGeneratorPluginLibrary)
                .addProvider(new SeaLevelProvider(0))
                .addProvider(new GaussianSurfaceProvider())
                .addProvider(new SurfaceToDensityProvider())
                .addPlugins()
                .addRasterizer(new SurfaceFacetsExampleRasterizer());
    }
}