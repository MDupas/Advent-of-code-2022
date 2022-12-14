public class Section {
    final int begin;
    final int end;
    public Section(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public boolean containOrContained(Section otherSection) {
        return (this.getBegin() <= otherSection.getBegin() && this.getEnd() >= otherSection.getEnd()) ||
                (this.getBegin() >= otherSection.getBegin() && this.getEnd() <= otherSection.getEnd());
    }

    public boolean overlap(Section otherSection) {
        return (this.getBegin() <= otherSection.getEnd() && this.getEnd() >= otherSection.getEnd()) ||
                (otherSection.getBegin() <= this.getEnd() && otherSection.getEnd() >= this.getEnd());
    }
}
