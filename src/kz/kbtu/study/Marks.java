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

    public void updateAttestation1(double delta) throws DeadlinePassed {
        this.attestation1.updateScore(delta);
    }

    public void updateAttestation1(Date deadline) {
        this.attestation1.setDeadline(deadline);
    }

    public Mark getAttestation2() {
        return attestation2;
    }

    public void updateAttestation2(double delta) throws DeadlinePassed {
        this.attestation2.updateScore(delta);
    }

    public void updateAttestation2(Date deadline) {
        this.attestation2.setDeadline(deadline);
    }

    public Mark getFinale() {
        return finale;
    }

    public void updateFinale(double delta) throws DeadlinePassed {
        this.finale.updateScore(delta);
    }

    public void updateFinale(Date deadline) {
        this.finale.setDeadline(deadline);
    }

    @Override
    public String toString() {
        return String.format("Attestation1: %.2f, Attestation2: %.2f, Final: %.2f",
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
