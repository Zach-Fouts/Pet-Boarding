package com.petboarding.models.data;

import com.petboarding.models.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvoiceRepository extends JPARepositoryActiveFiltering<Invoice, Integer>{

    @Query( value = "SELECT (IFNULL(number, 0) + 1) AS nextNumber FROM invoice WHERE YEAR(date) = YEAR(:date) ORDER BY number DESC LIMIT 1",
    nativeQuery = true)
    public Integer findNextNumberByDate(@Param("date") Date date);
}
