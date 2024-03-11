package it.uniroma1.commons.repository;

import it.uniroma1.commons.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface FineRepository extends JpaRepository<Fine, Long> {
    @Query(
            value = "SELECT * FROM fines f WHERE f.manager_code IS null AND f.speed_camera_id IN (SELECT id FROM speed_cameras WHERE region=?1)",
            nativeQuery = true)
    Collection<Fine> findAllNewFines(String region);

    @Query(
            value = "SELECT * FROM fines f WHERE f.manager_code IS NOT null AND f.speed_camera_id IN (SELECT id FROM speed_cameras WHERE region=?1)",
            nativeQuery = true)
    Collection<Fine> findAllManagedFines(String region);
    @Query(
            value = "SELECT * FROM fines f WHERE f.manager_code IS NOT null AND f.speed_camera_id=?2 AND f.speed_camera_id IN (SELECT id FROM speed_cameras WHERE region=?1)",
            nativeQuery = true)
    Collection<Fine> findFilterAllManagedFines(String region,int camera_id);

    @Query(
            value = "SELECT * FROM fines f WHERE f.manager_code IS null AND f.speed_camera_id=?2 AND f.speed_camera_id IN (SELECT id FROM speed_cameras WHERE region=?1)",
            nativeQuery = true)
    Collection<Fine> findFilterAllNewFines(String region,int camera_id);

    @Query(
            value = "SELECT * FROM fines f WHERE f.manager_code=?1",
            nativeQuery = true)
    Collection<Fine> findAllManagedFinesByManager(String username);

    @Query(
            value = "SELECT * FROM fines f WHERE f.manager_code=?2 AND f.speed_camera_id=?1",
            nativeQuery = true)
    Collection<Fine> findFilterAllManagedFinesByManager(int camera_id, String username);
}
