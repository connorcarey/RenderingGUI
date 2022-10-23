/**
 * Holds 3 vectors in a 3D space.
 */
public class Triangle {

    private Vector3D v1;
    private Vector3D v2;
    private Vector3D v3;

    public Triangle() {
        v1 = new Vector3D();
        v2 = new Vector3D();
        v3 = new Vector3D();
    }

    public Triangle(Vector3D v1, Vector3D v2, Vector3D v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Triangle(Triangle other) {
        this.v1 = new Vector3D(other.getVector1());
        this.v2 = new Vector3D(other.getVector2());
        this.v3 = new Vector3D(other.getVector3());
    }

    public Vector3D getVector1() {
        return v1;
    }

    public void setVector1(Vector3D vector) {
        v1 = vector;
    }

    public Vector3D getVector2() {
        return v2;
    }

    public void setVector2(Vector3D vector) {
        v2 = vector;
    }

    public Vector3D getVector3() {
        return v3;
    }

    public void setVector3(Vector3D vector) {
        v3 = vector;
    }

    @Override
    public String toString() {
        return v1 + " " + v2 + " " + v3;
    }
}
