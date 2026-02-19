import java.util.Objects;

public class VoteSummary {
   private Integer validCount;
  private Integer  blankCount;
  private Integer nullCount;

    public VoteSummary() {
    }

    public VoteSummary(Integer blankCount, Integer nullCount, Integer validCount) {
        this.blankCount = blankCount;
        this.nullCount = nullCount;
        this.validCount = validCount;
    }

    public Integer getBlankCount() {
        return blankCount;
    }

    public void setBlankCount(Integer blankCount) {
        this.blankCount = blankCount;
    }

    public Integer getNullCount() {
        return nullCount;
    }

    public void setNullCount(Integer nullCount) {
        this.nullCount = nullCount;
    }

    public Integer getValidCount() {
        return validCount;
    }

    public void setValidCount(Integer validCount) {
        this.validCount = validCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VoteSummary that)) return false;
        return Objects.equals(validCount, that.validCount) && Objects.equals(blankCount, that.blankCount) && Objects.equals(nullCount, that.nullCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(validCount, blankCount, nullCount);
    }

    @Override
    public String toString() {
        return "VoteSummary{" +
                "blankCount=" + blankCount +
                ", validCount=" + validCount +
                ", nullCount=" + nullCount +
                '}';
    }
}
