select count(voter_id) as total_vote from vote;
select count(public.vote.vote_type) from vote group by vote_type;

select vote_type,
     count(vote_type)
from vote group by vote_type;
