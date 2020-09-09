// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.surfacefacets.facets;

import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseObjectFacet2D;
import org.terasology.math.geom.Vector3f;

/**
 * Contains the surface normal data for each point on a world.
 */
public class SurfaceNormalFacet extends BaseObjectFacet2D<Vector3f> {
    public SurfaceNormalFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border, Vector3f.class);
    }
}
