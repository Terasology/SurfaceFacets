// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.surfacefacets.facets;

import org.terasology.engine.math.Region3i;
import org.terasology.engine.world.generation.Border3D;
import org.terasology.engine.world.generation.facets.base.BaseFieldFacet2D;

/**
 * Contains the surface steepness (angle in radians between the surface normal and the vertical) data for each point in
 * a world.
 */
public class SurfaceSteepnessFacet extends BaseFieldFacet2D {
    public SurfaceSteepnessFacet(Region3i targetRegion, Border3D border) {
        super(targetRegion, border);
    }
}
