package com.lbet.models.repos;

import com.lbet.models.domain.AddedUsersRates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddedRatesRepo extends CrudRepository<AddedUsersRates, Long> {
    List<AddedUsersRates> findFirstByUserIdAndRateId(long user_id, long rate_id);
    List<AddedUsersRates> findAllByUserId(long user_id);
    List<AddedUsersRates> findAllByRateId(long rate_id);
}
