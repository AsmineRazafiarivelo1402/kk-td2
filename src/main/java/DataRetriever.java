import com.sun.jdi.Value;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    long countAllVotes(){
        DBConnection dbConnection = new DBConnection();
        String sql = """
               select count(voter_id) as total_vote from vote;
                """;
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();) {
            if(rs.next()){
                return rs.getLong("total_vote");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }
    List<VoteTypeCount> countVotesByType(){
        DBConnection dbConnection = new DBConnection();
        String sql = """
                select vote_type,
                     count(vote_type)
                from vote group by vote_type;
                """;
        List<VoteTypeCount> listVote = new ArrayList<>();
        try(Connection connection = dbConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                VoteTypeCount voteTypeCount = new VoteTypeCount();
                voteTypeCount.setCount(rs.getInt("count"));
                voteTypeCount.setVote_type(Votetype.valueOf(rs.getString("vote_type")));
                listVote.add(voteTypeCount);
            }
            return  listVote;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
