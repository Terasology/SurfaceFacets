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

import org.terasology.math.geom.Vector2f;

public final class GaussianSurfaceSampler {
    public final float height;
    public final Vector2f radius;
    public final Vector2f center;

    public GaussianSurfaceSampler(Vector2f center, Vector2f radius, float height) {
        this.height = height;
        this.radius = radius;
        this.center = center;
    }

    public float sample(Vector2f point) {
        Vector2f normalizedOffset = point.sub(center);
        normalizedOffset.divX(radius.x);
        normalizedOffset.divY(radius.y);

        return (float) (height / Math.exp(normalizedOffset.lengthSquared() / 2));
    }
}
