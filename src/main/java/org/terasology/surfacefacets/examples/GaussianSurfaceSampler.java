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

import org.joml.Vector2f;
import org.joml.Vector2fc;

public final class GaussianSurfaceSampler {
    public final float height;
    public final Vector2f radius;
    public final Vector2f center;

    public GaussianSurfaceSampler(Vector2fc center, Vector2fc radius, float height) {
        this.height = height;
        this.radius = new Vector2f(radius);
        this.center = new Vector2f(center);
    }

    public float sample(Vector2fc point) {
        Vector2f normalizedOffset = point.sub(center, new Vector2f()).div(radius);
        return (float) (height / Math.exp(normalizedOffset.lengthSquared() / 2));
    }
}
