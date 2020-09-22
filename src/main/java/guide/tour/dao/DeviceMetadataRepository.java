package guide.tour.dao;

import guide.tour.model.DeviceMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Qualifier("deviceMetadataRepository")
@Repository
public interface DeviceMetadataRepository extends JpaRepository<DeviceMetadata, Long> {

    List<DeviceMetadata> findByUserId(Long userId);
}