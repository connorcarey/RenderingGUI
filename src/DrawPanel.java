import javax.swing.*;
import java.awt.*;

/**
 * The panel responsible for drawing the render method utilizing projection.
 */
public class DrawPanel extends JPanel implements Runnable {

    private final double zNear = 0.1; // Distance the camera is from the image.
    private final double zFar = 1000f; // Distance the camera stops looking.
    private final double aspectRatio; // Get the aspect ratio to multiply the points.
    private final double fov = 90f; // The field of view of the camera.
    private final double fovRad = 1f / Math.tan(fov * 0.5f / 180f * Math.PI); // The field of view in radians.

    // The matrix projection to project a given vector's data onto a 2D surface at a distance from camera.
    private final double[][] matrixProjection;
    // The rotation matrices.
    private final double[][] matRotZ;
    private final double[][] matRotX;

    /**
     * Constructs a new DrawPanel containing a BufferedImage with the default dimensions of 800x800.
     */
    public DrawPanel() {
        this(800, 800);
    }

    /**
     * Constructs a new DrawPanel containing a BufferedImage with specified dimensions.
     *
     * @param width
     * @param height
     */
    public DrawPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        aspectRatio = ((double) height) / width;
        matrixProjection = new double[4][4];
        matRotZ = new double[4][4];
        matRotX = new double[4][4];

    }

    /**
     * Multiply a given vector3D by the matrix and save the output to another vector3D.
     *
     * @param input  Vector to multiply by m
     * @param output Vector that the multiplication is saved to
     * @param m      Matrix that is multiplying
     */
    private void matrixMultiplication(Vector3D input, Vector3D output, double[][] m) {
        output.setX(input.getX() * m[0][0] + input.getY() * m[1][0] + input.getZ() * m[2][0] + m[3][0]);
        output.setY(input.getX() * m[0][1] + input.getY() * m[1][1] + input.getZ() * m[2][1] + m[3][1]);
        output.setZ(input.getX() * m[0][2] + input.getY() * m[1][2] + input.getZ() * m[2][2] + m[3][2]);

        double w = input.getX() * m[0][3] + input.getY() * m[1][3] + input.getZ() * m[2][3] + m[3][3];

        if (Math.abs(w) > 0.00001) {
            output.setX(output.getX() / w);
            output.setY(output.getY() / w);
            output.setZ(output.getZ() / w);
        }
    }

    /**
     * Draw components to the screen.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Drawing all the triangles that make up a mesh.
        for (Cube c : Cube.values()) {
            for (Triangle t : c.triangles) {
                Triangle projected = new Triangle();
                Triangle rotatedZ = new Triangle();
                Triangle rotatedZX = new Triangle();

                // Rotate the 3D vectors Z of the cube
                matrixMultiplication(t.getVector1(), rotatedZ.getVector1(), matRotZ);
                matrixMultiplication(t.getVector2(), rotatedZ.getVector2(), matRotZ);
                matrixMultiplication(t.getVector3(), rotatedZ.getVector3(), matRotZ);

                // Rotate the 3D vectors X of the cube
                matrixMultiplication(rotatedZ.getVector1(), rotatedZX.getVector1(), matRotX);
                matrixMultiplication(rotatedZ.getVector2(), rotatedZX.getVector2(), matRotX);
                matrixMultiplication(rotatedZ.getVector3(), rotatedZX.getVector3(), matRotX);

                Triangle translated = new Triangle(rotatedZX);

                // Translate the rotated cube to the screen dimensions
                translated.getVector1().setZ(rotatedZX.getVector1().getZ() + 3f);
                translated.getVector2().setZ(rotatedZX.getVector2().getZ() + 3f);
                translated.getVector3().setZ(rotatedZX.getVector3().getZ() + 3f);

                // Created variables for brevity.
                Vector3D v1 = projected.getVector1();
                Vector3D v2 = projected.getVector2();
                Vector3D v3 = projected.getVector3();

                // Mutating the vector's x and y coordinates via the matrix projection.
                matrixMultiplication(translated.getVector1(), v1, matrixProjection);
                matrixMultiplication(translated.getVector2(), v2, matrixProjection);
                matrixMultiplication(translated.getVector3(), v3, matrixProjection);

                // Scaling the vector's x and y positions relative to the screen.
                v1.setX((v1.getX() + 1f) * 0.5 * getWidth());
                v1.setY((v1.getY() + 1f) * 0.5 * getHeight());
                v2.setX((v2.getX() + 1f) * 0.5 * getWidth());
                v2.setY((v2.getY() + 1f) * 0.5 * getHeight());
                v3.setX((v3.getX() + 1f) * 0.5 * getWidth());
                v3.setY((v3.getY() + 1f) * 0.5 * getHeight());

                // Display lines between vector points.
                int[] xPoints = {(int) v1.getX(), (int) v2.getX(), (int) v3.getX()};
                int[] yPoints = {(int) v1.getY(), (int) v2.getY(), (int) v3.getY()};

                // Color whichever side it is.
                if (c == Cube.BOTTOM) {
                    g.setColor(Color.YELLOW);
                } else if (c == Cube.TOP) {
                    g.setColor(Color.WHITE);
                } else if (c == Cube.NORTH) {
                    g.setColor(Color.BLUE);
                } else if (c == Cube.EAST) {
                    g.setColor(Color.RED);
                } else if (c == Cube.SOUTH) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.PINK);
                }

                g.drawPolygon(xPoints, yPoints, 3);

            }
        }
    }

    /**
     * Constantly refresh and keep track of the Cube rotation and image.
     */
    @Override
    public void run() {
        double theta = 0f; // Keeps track of the angle at which the cube is rotating.
        // Initialize the matrix projection values.
        matrixProjection[0][0] = aspectRatio * fovRad;
        matrixProjection[1][1] = fovRad;
        matrixProjection[2][2] = zFar / (zFar - zNear);
        matrixProjection[2][3] = 1f;
        matrixProjection[3][2] = (-zFar * zNear) / (zFar - zNear);

        while (true) {
            if (theta >= 4 * Math.PI) {
                theta = 0f;
            }
            theta += 0.0000001;

            // Rotation Z matrix
            // Hardcoded for learning purposes
            matRotZ[0][0] = Math.cos(theta);
            matRotZ[0][1] = Math.sin(theta);
            matRotZ[1][0] = -Math.sin(theta);
            matRotZ[1][1] = Math.cos(theta);
            matRotZ[2][2] = 1f;
            matRotZ[3][3] = 1f;

            // Rotation X matrix
            // Hardcoded for learning purposes
            matRotX[0][0] = 1f;
            matRotX[1][1] = Math.cos(theta / 2f);
            matRotX[1][2] = Math.sin(theta / 2f);
            matRotX[2][1] = -Math.sin(theta / 2f);
            matRotX[2][2] = Math.cos(theta / 2f);
            matRotX[3][3] = 1f;

            repaint();
        }
    }
}
