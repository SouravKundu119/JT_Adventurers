package com.gems.psl.repo;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gems.psl.model.tour.Email;
import com.gems.psl.model.tour.User;
@Repository
public interface UserRepository extends JpaRepository<User,Email>{

	Optional<User> findByUserName(Email userName);


	
}
