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

import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Vector3f;
import org.terasology.world.generation.facets.SurfaceHeightFacet;

class NormalUtility {
    static Vector3f getNormalAtPosition(SurfaceHeightFacet surfaceHeightFacet, BaseVector2i position) {
        float slopeX = surfaceHeightFacet.getWorld(position.x() + 1, position.y()) -
                surfaceHeightFacet.getWorld(position.x() - 1, position.y());

        float slopeZ = surfaceHeightFacet.getWorld(position.x() + 1, position.y()) -
                surfaceHeightFacet.getWorld(position.x() - 1, position.y());

        Vector3f normal = new Vector3f(-slopeX, 2, -slopeZ);
        normal.normalize();
        return normal;
    }
}
