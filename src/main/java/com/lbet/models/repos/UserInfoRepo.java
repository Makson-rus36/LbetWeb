package com.lbet.models.repos;

import com.lbet.models.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepo extends CrudRepository<UserInfo, Long> {
    UserInfo findFirstByUserid(long user_id);
    List<UserInfo> findAllByEmailUser(String loginUser);
    List<UserInfo> findFirstByEmailUserAndPasswordUser(String email, String password);
}
