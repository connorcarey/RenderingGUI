/**
 * Data for a cube mesh.
 * Hardcoded for learning purposes.
 */
public enum Cube {

    // For each face of the cube, there are two triangles containing 3 Vector3Ds.
    SOUTH(new double[][][]{
            {{0f, 0f, 0f}, {0f, 1f, 0f}, {1f, 1f, 0f}},
            {{0f, 0f, 0f}, {1f, 1f, 0f}, {1f, 0f, 0f}}
    }),
    EAST(new double[][][]{
            {{1f, 0f, 0f}, {1f, 1f, 0f}, {1f, 1f, 1f}},
            {{1f, 0f, 0f}, {1f, 1f, 1f}, {1f, 0f, 1f}}
    }),
    NORTH(new double[][][]{
            {{1f, 0f, 1f}, {1f, 1f, 1f}, {0f, 1f, 1f}},
            {{1f, 0f, 1f}, {0f, 1f, 1f}, {0f, 0f, 1f}}
    }),
    WEST(new double[][][]{
            {{0f, 0f, 1f}, {0f, 1f, 1f}, {0f, 1f, 0f}},
            {{0f, 0f, 1f}, {0f, 1f, 0f}, {0f, 0f, 0f}}
    }),
    TOP(new double[][][]{
            {{0f, 1f, 0f}, {0f, 1f, 1f}, {1f, 1f, 1f}},
            {{0f, 1f, 0f}, {1f, 1f, 1f}, {1f, 1f, 0f}}
    }),
    BOTTOM(new double[][][]{
            {{1f, 0f, 1f}, {0f, 0f, 1f}, {0f, 0f, 0f}},
            {{1f, 0f, 1f}, {0f, 0f, 0f}, {1f, 0f, 0f}}
    });

    final Triangle[] triangles;

    Cube(double[][][] values) {
        triangles = new Triangle[2];
        for (int i = 0; i < 2; i++) {
            Vector3D v1 = new Vector3D(values[i][0][0], values[i][0][1], values[i][0][2]);
            Vector3D v2 = new Vector3D(values[i][1][0], values[i][1][1], values[i][1][2]);
            Vector3D v3 = new Vector3D(values[i][2][0], values[i][2][1], values[i][2][2]);
            triangles[i] = new Triangle(v1, v2, v3);
        }
    }
}
