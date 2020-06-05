package scs.iss.agentie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import scs.iss.agentie.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select m from User m where m.userName = :userName and m.password=:password")
    User findUser(@Param("userName") String userName, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value="insert into Users(user_name, password, email) VALUES(?#{#currentUser.userName}, ?#{#currentUser.password}, ?#{#currentUser.email})", nativeQuery = true)
    void registerUser(@Param("currentUser") User currentUser);
}
