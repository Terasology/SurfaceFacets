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
import org.terasology.world.block.Block;
import org.terasology.world.block.BlockManager;
import org.terasology.world.chunks.CoreChunk;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.Region;
import org.terasology.world.generation.Requires;
import org.terasology.world.generation.WorldRasterizer;
import org.terasology.world.generation.WorldRasterizerPlugin;
import org.terasology.world.generation.facets.DensityFacet;
import org.terasology.world.generator.plugin.RegisterPlugin;

/**
 * Places snow on the surface if the surface normal points west, otherwise places grass.
 */
@RegisterPlugin
@Requires({
        @Facet(SurfaceNormalFacet.class),
        @Facet(DensityFacet.class)
})
public class GrassSnowNormalRasterizer implements WorldRasterizerPlugin {
    private Block grass;
    private Block snow;
    private Block stone;

    @Override
    public void initialize() {
        BlockManager blockManager = CoreRegistry.get(BlockManager.class);
        grass = blockManager.getBlock("core:grass");
        snow = blockManager.getBlock("core:snow");
        stone = blockManager.getBlock("core:stone");
    }

    @Override
    public void generateChunk(CoreChunk chunk, Region chunkRegion) {
        DensityFacet densityFacet = chunkRegion.getFacet(DensityFacet.class);
        SurfaceNormalFacet surfaceNormalFacet = chunkRegion.getFacet(SurfaceNormalFacet.class);

        for (Vector3i position : chunkRegion.getRegion()) {
            Vector2i terrainPosition = new Vector2i(position.x, position.z);
            Vector3i blockPosition = ChunkMath.calcBlockPos(position);

            Vector3f normal = surfaceNormalFacet.getWorld(terrainPosition);
            float density = densityFacet.getWorld(position);

            if (density >= 1) {
                chunk.setBlock(blockPosition, stone);
            } else if (density >= 0) {
                chunk.setBlock(blockPosition, getBlockForNormal(normal));
            }
        }
    }

    /**
     * Returns snow if the normal points towards the west, otherwise returns grass
     * @param normal
     * @return
     */
    private Block getBlockForNormal(Vector3f normal) {
        float eastNormal = normal.dot(Vector3f.east());

        if (eastNormal >= 0) {
            return grass;
        }

        return snow;
    }
}
