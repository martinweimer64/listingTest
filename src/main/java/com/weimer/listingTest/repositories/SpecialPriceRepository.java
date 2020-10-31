package com.weimer.listingTest.repositories;

import com.weimer.listingTest.entities.SpecialPriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SpecialPriceRepository extends CrudRepository<SpecialPriceEntity, String> {

    //@Query("SELECT sp FROM special_prices sp WHERE sp.listing.id=:id")
    Iterable<SpecialPriceEntity> findAllByListingEntity_id(int id);
}
