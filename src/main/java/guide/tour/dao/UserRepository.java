package guide.tour.dao;

import guide.tour.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier("userRepository")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByName(String name);

    User findUserByEmail(String email);

    @Override
    void delete(User user);

}