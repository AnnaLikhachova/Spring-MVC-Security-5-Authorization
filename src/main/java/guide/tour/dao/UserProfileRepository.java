package guide.tour.dao;

import guide.tour.model.UserProfile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier("userProfileRepository")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Override
    void delete(UserProfile userProfile);

    UserProfile findByType(String type);
}