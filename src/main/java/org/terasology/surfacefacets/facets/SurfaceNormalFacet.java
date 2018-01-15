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
package org.terasology.surfacefacets.facets;

import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector3f;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.facets.base.BaseObjectFacet2D;

/**
 * Contains the surface normal data for each point on a world.
 */
public class SurfaceNormalFacet extends BaseObjectFacet2D<Vector3f> {
    public SurfaceNormalFacet(Region3i targetRegion, Border3D border, Class<Vector3f> objectType) {
        super(targetRegion, border, objectType);
    }
}
