// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.surfacefacets.providers;

import org.terasology.engine.world.generation.facets.SurfaceHeightFacet;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Vector3f;

class NormalUtility {
    static Vector3f getNormalAtPosition(SurfaceHeightFacet surfaceHeightFacet, BaseVector2i position) {
        float slopeX = surfaceHeightFacet.getWorld(position.x() + 1, position.y()) -
                surfaceHeightFacet.getWorld(position.x() - 1, position.y());

        float slopeZ = surfaceHeightFacet.getWorld(position.x(), position.y() + 1) -
                surfaceHeightFacet.getWorld(position.x(), position.y() - 1);

        Vector3f normal = new Vector3f(-slopeX, 2, -slopeZ);
        normal.normalize();
        return normal;
    }
}
