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

import org.terasology.math.ChunkMath;
import org.terasology.math.geom.Vector2i;
import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;
import org.terasology.registry.CoreRegistry;
import org.terasology.surfacefacets.facets.SurfaceNormalFacet;
import org.terasology.surfacefacets.facets.SurfaceSteepnessFacet;
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.facets.DensityFacet;

/**
 * Places:
 * Sand if the surface is less than 15 degrees steep
 * Grass if the surface normal points east
 * Snow otherwise
 */
@Requires({
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
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        DensityFacet densityFacet = chunkRegion.getFacet(DensityFacet.class);
        SurfaceNormalFacet surfaceNormalFacet = chunkRegion.getFacet(SurfaceNormalFacet.class);
        SurfaceSteepnessFacet surfaceSteepnessFacet = chunkRegion.getFacet(SurfaceSteepnessFacet.class);

        for (Vector3i position : chunkRegion.getRegion()) {
            Vector2i terrainPosition = new Vector2i(position.x, position.z);
            Vector3i blockPosition = ChunkMath.calcBlockPos(position);

            Vector3f normal = surfaceNormalFacet.getWorld(terrainPosition);
            float steepness = surfaceSteepnessFacet.getWorld(terrainPosition);
            float density = densityFacet.getWorld(position);

            if (density >= 1) {
                chunk.setBlock(blockPosition, stone);
            } else if (density >= 0) {
                chunk.setBlock(blockPosition, getBlockFor(normal, steepness));
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
    private Block getBlockFor(Vector3f normal, float steepness) {
        if (steepness <= MAX_SAND_STEEPNESS) {
            return sand;
        }

        float eastNormal = normal.dot(Vector3f.east());

        if (eastNormal >= 0) {
            return grass;
        }

        return snow;
    }
}
