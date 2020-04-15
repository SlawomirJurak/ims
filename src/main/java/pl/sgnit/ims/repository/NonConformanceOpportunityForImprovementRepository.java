package pl.sgnit.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sgnit.ims.model.NonConformanceOpportunityForImprovement;

import java.time.LocalDate;
import java.util.List;

public interface NonConformanceOpportunityForImprovementRepository extends JpaRepository<NonConformanceOpportunityForImprovement, Long> {

    List<NonConformanceOpportunityForImprovement> findByAuditIdAndConfirmDateIsNull(Long auditId);
}
