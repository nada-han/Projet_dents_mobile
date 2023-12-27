package ma.ensa.backProject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Angle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double angleRight;
    private double angleLeft;
    private double angleDeConvergence;

    // Constructeurs, getters et setters

    public Angle() {
        // Constructeur par défaut
    }

    public Angle(double angleRight, double angleLeft, double angleDeConvergence) {
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
