## SurfaceFacets
This module contains facets and their corresponding providers which available as plugins that provide extra information about the world surface.

## Facets
### `SurfaceNormalFacet`
Contains the surface normal data for each point in a world.

### `SurfaceSteepnessFacet`
Contains the surface steepness (which is the angle in radians between the surface normal and the vertical) data for each point in a world.

## Providers
### `SurfaceNormalProvider`
Produces a `SurfaceNormalFacet` with the surface normals for a world obtained from its surface heights.

### `SurfaceSteepnessProvider`
Produces a `SurfaceSteepnessFacet` with the surface steepness for a world obtained from its surface heights.

## Usage
You can either directly add the providers to your world builder via `addProvider` or add all of them as plugins via `addPlugins`. The facets contain data as a 2D field of the surface points much like `SurfaceHeightProvider` and can be used in rasterizers.

For an example of how the facets can be used to generate worlds, take a look at the world generator and rasterizer under the `examples` subpackage. The example can be run by using the _SurfaceFacets Example_ world generator after activating the module.
