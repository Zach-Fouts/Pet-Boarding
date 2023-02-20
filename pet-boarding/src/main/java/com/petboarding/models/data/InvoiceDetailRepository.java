package com.petboarding.models.data;

import com.petboarding.models.InvoiceDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceDetailRepository extends JPARepositoryActiveFiltering<InvoiceDetail, Integer> {
}
