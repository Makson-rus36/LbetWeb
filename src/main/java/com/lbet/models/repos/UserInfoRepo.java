package com.lbet.models.repos;

import com.lbet.models.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserInfoRepo extends CrudRepository<UserInfo, Long> {
    @Transactional
    UserInfo findFirstByUserid(long user_id);
    @Transactional
    List<UserInfo> findAllByEmailUser(String loginUser);
    @Transactional
    List<UserInfo> findFirstByEmailUserAndPasswordUser(String email, String password);
}
