package com.lbet.models.repos;

import com.lbet.models.domain.ImagesData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesDataRepo extends CrudRepository<ImagesData, Long> {
}
