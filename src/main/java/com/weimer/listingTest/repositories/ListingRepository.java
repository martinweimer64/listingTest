package com.weimer.listingTest.repositories;

import com.weimer.listingTest.entities.ListingEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface ListingRepository extends CrudRepository<ListingEntity, String> {
    Optional<ListingEntity> findById(int id);

    ListingEntity findTopByOrderByIdDesc();
}
