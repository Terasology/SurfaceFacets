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

import org.joml.Vector3fc;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import org.terasology.engine.math.Direction;
import org.terasology.engine.registry.CoreRegistry;
import org.terasology.engine.world.block.Block;
import org.terasology.engine.world.block.BlockManager;
import org.terasology.engine.world.chunks.Chunk;
import org.terasology.engine.world.chunks.Chunks;
import org.terasology.engine.world.generation.Facet;
import org.terasology.engine.world.generation.Region;
import org.terasology.engine.world.generation.Requires;
import org.terasology.engine.world.generation.WorldRasterizer;
import org.terasology.engine.world.generation.facets.DensityFacet;
import org.terasology.engine.world.generation.facets.SurfacesFacet;
import org.terasology.surfacefacets.facets.SurfaceNormalFacet;
import org.terasology.surfacefacets.facets.SurfaceSteepnessFacet;

/**
 * Places:
 * Sand if the surface is less than 15 degrees steep
 * Grass if the surface normal points east
 * Snow otherwise
 */
@Requires({
        @Facet(SurfacesFacet.class),
        @Facet(SurfaceNormalFacet.class),
        @Facet(SurfaceSteepnessFacet.class),
        @Facet(DensityFacet.class)
})
public class SurfaceFacetsExampleRasterizer implements WorldRasterizer {
    private static final double DEG2RAD = Math.PI / 180;
    private static final double MAX_SAND_STEEPNESS = 15 * DEG2RAD;

    private Block grass;
    private Block snow;
    private Block sand;
    private Block stone;

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        grass = blockManager.getBlock("CoreAssets:Grass");
        snow = blockManager.getBlock("CoreAssets:Snow");
        sand = blockManager.getBlock("CoreAssets:Sand");
        stone = blockManager.getBlock("CoreAssets:Stone");
    }

    @Override
    public void generateChunk(Chunk chunk, Region chunkRegion) {
        SurfacesFacet surfacesFacet = chunkRegion.getFacet(SurfacesFacet.class);
        DensityFacet densityFacet = chunkRegion.getFacet(DensityFacet.class);
        SurfaceNormalFacet surfaceNormalFacet = chunkRegion.getFacet(SurfaceNormalFacet.class);
        SurfaceSteepnessFacet surfaceSteepnessFacet = chunkRegion.getFacet(SurfaceSteepnessFacet.class);

        Vector3i tempPos = new Vector3i();
        for (Vector3ic position : chunkRegion.getRegion()) {
            Vector3i blockPosition = Chunks.toRelative(position, tempPos);
            if (surfacesFacet.getWorld(position)) {
                Vector3fc normal = surfaceNormalFacet.getWorld(position);
                float steepness = surfaceSteepnessFacet.getWorld(position);
                chunk.setBlock(blockPosition, getBlockFor(normal, steepness));
            } else {
                float density = densityFacet.getWorld(position);

                if (density > 0) {
                    chunk.setBlock(blockPosition, stone);
                }
            }
        }
    }

    /**
     * Returns:
     * Sand if the steepness is less than {@code MAX_SAND_STEEPNESS}
     * Grass if the normal points east
     * Snow otherwise
     *
     * @param normal
     * @param steepness
     * @return
     */
    private Block getBlockFor(Vector3fc normal, float steepness) {
        if (steepness <= MAX_SAND_STEEPNESS) {
            return sand;
        }

        float eastNormal = normal.dot(Direction.RIGHT.asVector3f());

        if (eastNormal >= 0) {
            return grass;
        }

        return snow;
    }
}
