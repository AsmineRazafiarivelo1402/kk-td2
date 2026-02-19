import java.util.Objects;

public class ElectionResult {
    private String candidateName;
 private Integer validVoteCount;

    public ElectionResult() {
    }

    public ElectionResult(String candidateName, Integer validVoteCount) {
        this.candidateName = candidateName;
        this.validVoteCount = validVoteCount;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Integer getValidVoteCount() {
        return validVoteCount;
    }

    public void setValidVoteCount(Integer validVoteCount) {
        this.validVoteCount = validVoteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ElectionResult that)) return false;
        return Objects.equals(getCandidateName(), that.getCandidateName()) && Objects.equals(getValidVoteCount(), that.getValidVoteCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCandidateName(), getValidVoteCount());
    }

    @Override
    public String toString() {
        return "ElectionResult{" +
                "candidateName='" + candidateName + '\'' +
                ", validVoteCount=" + validVoteCount +
                '}';
    }
}
