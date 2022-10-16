package com.onlineenergyutilityplatform.db.repositories;

import com.onlineenergyutilityplatform.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class used for db access
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
