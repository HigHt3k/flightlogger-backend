package com.home_app.model.dump1090;

import com.home_app.model.planespotting.SightingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageLogRepository extends JpaRepository<Dump1090Data, Integer> {

    List<Dump1090Data> findAll();

    Optional<Dump1090Data> findById(Integer id);
}
