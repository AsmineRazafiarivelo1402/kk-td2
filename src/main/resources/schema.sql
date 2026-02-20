select count(voter_id) as total_vote from vote;
select count(public.vote.vote_type) from vote group by vote_type;

select vote_type,
     count(vote_type)
from vote group by vote_type;

 select  candidate.name,  case
           when vote_type =  'VALID' then
       count(vote_type)
         end
from vote join candidate on vote.candidate_id = candidate.id  group by candidate.name , vote_type;
SELECT
    candidate.name,
    COUNT(CASE
              WHEN vote.vote_type = 'VALID'
                  THEN 1
        END) AS total_valid_votes
FROM vote
         JOIN candidate ON vote.candidate_id = candidate.id
GROUP BY candidate.name;
SELECT
    candidate.name,
    COUNT(vote_type) FILTER (WHERE vote.vote_type = 'VALID') AS total_valid_votes
FROM vote
         JOIN candidate ON vote.candidate_id = candidate.id
GROUP BY candidate.name;

select  COUNT(vote_type) FILTER (WHERE vote.vote_type = 'VALID') AS valid_count,
        COUNT(vote_type) FILTER (WHERE vote.vote_type = 'BLANK') AS blank_count,
        COUNT(vote_type) FILTER (WHERE vote.vote_type = 'NULL') AS null_count
from vote ;

select  (count(vote.voter_id) / count(public.voter.id)) * 100 as tax_participation from vote join voter on vote.voter_id = voter.id;
select   candidate.name  from candidate having      max('total_valid_votes') in (

select max(votevalid) from(
select candidate.name as nom, count(vote.vote_type) FILTER ( WHERE vote.vote_type = 'VALID' ) as votevalid  from vote join candidate on vote.candidate_id = candidate.id group by candidate.name ) ;

select name  from candidate join vote  on candidate.id = vote.candidate_id group by name having count(vote_type) in ( select max(vote_type)  as vote_valid from(
select candidate.name as nom, count(vote.vote_type) FILTER ( WHERE vote.vote_type = 'VALID' ) as votevalid  from vote join candidate on vote.candidate_id = candidate.id group by candidate.name ) )  ;

select name from vote join candidate on candidate.id = vote.candidate_id having count(vote_type)  ;
select  max(total_valid_votes)from (

SELECT
    candidate.name,
    COUNT(CASE
              WHEN vote.vote_type = 'VALID'
                  THEN 1
        END) AS total_valid_votes
FROM vote
         JOIN candidate ON vote.candidate_id = candidate.id
GROUP BY candidate.name)
;
SELECT
    candidate.name,
    COUNT(CASE
              WHEN vote.vote_type = 'VALID'
                  THEN 1
        END) AS total_valid_votes
FROM vote
         JOIN candidate ON vote.candidate_id = candidate.id
GROUP BY candidate.name
ORDER BY total_valid_votes DESC
LIMIT 1;