/**
 * I believe this class is pretty self-explanatory.
 * Vector3D represents a vector in a 3 dimensional space.
 */
public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D() {
        this(0f, 0f, 0f);
    }

    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public Vector3D(Vector3D other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y + ", Z: " + z;
    }
}
