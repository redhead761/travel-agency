package com.epam.finaltask.repository;

import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
    @Query("SELECT v FROM Voucher v " +
            "WHERE (:tourType IS NULL OR v.tourType = :tourType) " +
            "AND (:transferType IS NULL OR v.transferType = :transferType) " +
            "AND (:hotelType IS NULL OR v.hotelType = :hotelType) " +
            "AND (:userId IS NULL OR v.user.id = :userId) " +
            "AND (:minPrice IS NULL OR v.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR v.price <= :maxPrice) ")
    Page<Voucher> findByFilters(
            TourType tourType, TransferType transferType,
            HotelType hotelType, UUID userId,
            Double minPrice, Double maxPrice,
            Pageable pageable
    );
}
