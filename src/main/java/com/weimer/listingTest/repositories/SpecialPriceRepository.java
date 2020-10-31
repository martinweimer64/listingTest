package com.weimer.listingTest.repositories;

import com.weimer.listingTest.entities.SpecialPriceEntity;
import org.springframework.data.repository.CrudRepository;

public interface SpecialPriceRepository extends CrudRepository<SpecialPriceEntity, String> {

    Iterable<SpecialPriceEntity> findAllByListingEntity_id(int id);
}
