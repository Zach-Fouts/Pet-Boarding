package com.petboarding.models.data;

import com.petboarding.models.Invoice;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JPARepositoryActiveFiltering<Invoice, Integer>{
}
