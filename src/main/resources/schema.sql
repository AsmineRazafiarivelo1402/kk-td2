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
