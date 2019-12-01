package kz.kbtu.study;

import kz.kbtu.study.throwable.DeadlinePassed;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Mark {
    private double score;
    private Date deadline;

    public Mark(Date deadline) {
        this.deadline = deadline;
    }

    public double getScore() {
        return score;
    }

    public void updateScore(double delta) throws DeadlinePassed {
        Date now = Calendar.getInstance().getTime();
        if (now.compareTo(deadline) <= 0) {
            this.score += delta;
        }
        else {
            throw new DeadlinePassed(deadline);
        }
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "score=" + score +
                ", deadline=" + deadline +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark mark = (Mark) o;
        return Double.compare(mark.score, score) == 0 &&
                deadline.equals(mark.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, deadline);
    }
}
