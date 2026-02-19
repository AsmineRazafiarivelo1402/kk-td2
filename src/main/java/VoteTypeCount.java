import java.util.Objects;

public class VoteTypeCount {
    private Votetype vote_type;
    private Integer count;

    public VoteTypeCount() {
    }

    public VoteTypeCount(Integer count, Votetype vote_type) {
        this.count = count;
        this.vote_type = vote_type;
    }



    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Votetype getVote_type() {
        return vote_type;
    }

    public void setVote_type(Votetype vote_type) {
        this.vote_type = vote_type;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VoteTypeCount that)) return false;
        return getVote_type() == that.getVote_type() && Objects.equals(getCount(), that.getCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVote_type(), getCount());
    }

    @Override
    public String toString() {
        return "VoteTypeCount{" +
                "count=" + count +
                ", vote_type=" + vote_type +
                '}';
    }
}
