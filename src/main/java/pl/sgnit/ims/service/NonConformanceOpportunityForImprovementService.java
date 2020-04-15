package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sgnit.ims.model.NonConformanceOpportunityForImprovement;
import pl.sgnit.ims.repository.NonConformanceOpportunityForImprovementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NonConformanceOpportunityForImprovementService {

    private final NonConformanceOpportunityForImprovementRepository nonConformanceOpportunityForImprovementRepository;

    @Autowired
    public NonConformanceOpportunityForImprovementService(NonConformanceOpportunityForImprovementRepository nonConformanceOpportunityForImprovementRepository) {
        this.nonConformanceOpportunityForImprovementRepository = nonConformanceOpportunityForImprovementRepository;
    }

    public List<NonConformanceOpportunityForImprovement> findAll() {
        return nonConformanceOpportunityForImprovementRepository.findAll();
    }

    public Optional<NonConformanceOpportunityForImprovement> findById(Long id) {
        return nonConformanceOpportunityForImprovementRepository.findById(id);
    }

    public void save(NonConformanceOpportunityForImprovement ncofi) {
        nonConformanceOpportunityForImprovementRepository.save(ncofi);
    }

    public void removeById(Long toRemoveId) {
        nonConformanceOpportunityForImprovementRepository.deleteById(toRemoveId);
    }

    public List<NonConformanceOpportunityForImprovement> findByAuditIdAndConfirmDateIsNull(Long auditId) {
        return nonConformanceOpportunityForImprovementRepository.findByAuditIdAndConfirmDateIsNull(auditId);
    }
}
