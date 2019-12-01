package kz.kbtu.study;

import kz.kbtu.Database;
import kz.kbtu.study.throwable.DeadlinePassed;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Marks implements Serializable {
    private Mark attestation1;
    private Mark attestation2;
    private Mark finale;

    public Marks() {
        attestation1 = new Mark((Date) Database.DEADLINE1.clone());
        attestation2 = new Mark((Date) Database.DEADLINE2.clone());
        finale = new Mark((Date) Database.DEADLINE3.clone());
    }

    public Mark getAttestation1() {
        return attestation1;
    }

    public boolean updateAttestation1(double delta) {
        try {
            this.attestation1.updateScore(delta);

            return true;
        }
        catch (DeadlinePassed e) {
            // TODO: logging
            return false;
        }
    }

    public void updateAttestation1(Date deadline) {
        this.attestation1.setDeadline(deadline);
    }

    public Mark getAttestation2() {
        return attestation2;
    }

    public boolean updateAttestation2(double delta) {
        try {
            this.attestation2.updateScore(delta);

            return true;
        }
        catch (DeadlinePassed e) {
            // TODO: logging
            return false;
        }
    }

    public void updateAttestation2(Date deadline) {
        this.attestation2.setDeadline(deadline);
    }

    public Mark getFinale() {
        return finale;
    }

    public boolean updateFinale(double delta) {
        try {
            this.finale.updateScore(delta);

            return true;
        }
        catch (DeadlinePassed e) {
            // TODO: logging
            return false;
        }
    }

    public void updateFinale(Date deadline) {
        this.finale.setDeadline(deadline);
    }

    @Override
    public String toString() {
        return String.format("Marks { attestation1: %.2f, attestation2: %.2f, final: %.2f }",
                attestation1.getScore(), attestation2.getScore(), finale.getScore());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marks)) return false;
        Marks marks = (Marks) o;
        return attestation1.equals(marks.attestation1) &&
                attestation2.equals(marks.attestation2) &&
                finale.equals(marks.finale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attestation1, attestation2, finale);
    }
}
