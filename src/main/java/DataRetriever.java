import com.sun.jdi.Value;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    long countAllVotes() {
        DBConnection dbConnection = new DBConnection();
        String sql = """
               
                select count(voter_id) as total_vote from vote;
                """;
        try(Connection connection = dbConnection.getConnection
             ();
            PreparedStatement ps = connection.
             prepareStatement(sql);
        ResultSet rs = ps.executeQuery();) {
            if(rs.next()){
                return rs.getLong("total_vote");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    throw new RuntimeException();
    }
    List< VoteTypeCount> countVotesByType(){
        DBConnection dbConnection = new DBConnection();
        String sql = """
                select vote_type,
                     count(vote_type)
                from vote group by vote_type;
                """;
        List<VoteTypeCount> listVote = new ArrayList<>();
        try(Connection connection =
             dbConnection.getConnection();
        PreparedStatement ps =
             connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                VoteTypeCount voteTypeCount = new VoteTypeCount();
                voteTypeCount.setCount(rs.getInt("count"));
                voteTypeCount.setVote_type(Votetype.valueOf(rs.getString("vote_type")));
                listVote.add( voteTypeCount);
            }
            return  listVote;
        } catch (SQLException e) {

    throw new RuntimeException(e);
        }

    }
    List<CandidateVoteCount> countValidVotesByCandidate() {
        DBConnection dbConnection = new DBConnection();
        List<CandidateVoteCount> candidateVoteCountList = new ArrayList<>();
        String sql = """
                SELECT
                    candidate.name,
                    COUNT(vote_type) FILTER (WHERE vote.vote_type = 'VALID') AS total_valid
                FROM vote
                         JOIN candidate ON vote.candidate_id = candidate.id
                GROUP BY candidate.name;
                """;
        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CandidateVoteCount candidateVoteCount = new CandidateVoteCount();
                candidateVoteCount.setCandidateName(rs.getString("name"));
                candidateVoteCount.setValidVoteCount(rs.getInt("total_valid"));
                candidateVoteCountList.add(candidateVoteCount);
            }
            return candidateVoteCountList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    VoteSummary computeVoteSummary(){
    DBConnection dbConnection = new
                DBConnection();
    String sql = """
            select  COUNT(vote_type) FILTER (WHERE vote.vote_type = 'VALID') AS valid_count,
                    COUNT(vote_type) FILTER (WHERE vote.vote_type = 'BLANK') AS blank_count,
                    COUNT(vote_type) FILTER (
                WHERE vote.             vote_type = 'NULL') AS null_count
            from vote ;
            """;

        try(Connection connection = dbConnection.getConnection(
             );
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            if(rs.next()){
                VoteSummary voteSummary = new VoteSummary();
                voteSummary.setBlankCount(rs.getInt("blank_count"));
                voteSummary.setNullCount(rs.getInt("null_count"));
                voteSummary.setValidCount(rs.getInt("valid_count"));
                return voteSummary;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();

    }
    double computeTurnoutRate() {
        DBConnection dbConnection = new DBConnection();
        String sql = """
                select  (count(vote.voter_id) / count(public.voter.id)) * 100 as tax_participation from vote join voter on vote.voter_id = voter.id;
                
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("tax_participation");
            }
        }
catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException();
    }


    }
