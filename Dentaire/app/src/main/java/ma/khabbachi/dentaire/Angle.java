package ma.khabbachi.dentaire;

public class Angle {

    private Long id;
    private double angleRight;
    private double angleLeft;
    private double angleDeConvergence;

    // Constructeurs, getters et setters

    public Angle() {
        // Constructeur par défaut
    }

    public Angle(Long id, double angleRight, double angleLeft, double angleDeConvergence) {
        this.id = id;
        this.angleRight = angleRight;
        this.angleLeft = angleLeft;
        this.angleDeConvergence = angleDeConvergence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAngleRight() {
        return angleRight;
    }

    public void setAngleRight(double angleRight) {
        this.angleRight = angleRight;
    }

    public double getAngleLeft() {
        return angleLeft;
    }

    public void setAngleLeft(double angleLeft) {
        this.angleLeft = angleLeft;
    }

    public double getAngleDeConvergence() {
        return angleDeConvergence;
    }

    public void setAngleDeConvergence(double angleDeConvergence) {
        this.angleDeConvergence = angleDeConvergence;
    }
}
