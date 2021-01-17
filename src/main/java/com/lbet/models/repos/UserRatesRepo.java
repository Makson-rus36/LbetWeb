package com.lbet.models.repos;

import com.lbet.models.domain.UserRates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRatesRepo extends CrudRepository<UserRates, Long> {
UserRates findFirstByRateId(long rate_id);
}
