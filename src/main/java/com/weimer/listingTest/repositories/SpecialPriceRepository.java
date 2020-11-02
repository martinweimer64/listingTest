package com.weimer.listingTest.repositories;

import com.weimer.listingTest.entities.SpecialPriceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SpecialPriceRepository extends CrudRepository<SpecialPriceEntity, String> {

    Iterable<SpecialPriceEntity> findAllByListingEntity_id(int id);
    List<SpecialPriceEntity> findAllByListingEntity_idAndDateLessThanEqualAndDateGreaterThanEqual(int id, Date checkIn, Date checkOut);

    Optional<SpecialPriceEntity> findById(int id);

    SpecialPriceEntity findTopByOrderByIdDesc();
}
