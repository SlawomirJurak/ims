package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sgnit.ims.model.Audit;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.model.ScheduleAudit;
import pl.sgnit.ims.repository.AuditRepository;
import pl.sgnit.ims.repository.ScheduleAuditRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuditService {

    private final AuditRepository auditRepository;
    private final ScheduleAuditRepository scheduleAuditRepository;

    @Autowired
    public AuditService(AuditRepository auditRepository, ScheduleAuditRepository scheduleAuditRepository) {
        this.auditRepository = auditRepository;
        this.scheduleAuditRepository = scheduleAuditRepository;
    }

    public List<Audit> findAll() {
        return auditRepository.findAll();
    }

    public Optional<Audit> findById(Long id) {
        return auditRepository.findById(id);
    }

    public void save(Audit audit) {
        auditRepository.save(audit);
    }

    public List<Process> getAssignedProcesses(Long auditId) {
        Audit audit = auditRepository.findById(auditId).get();
        List<ScheduleAudit> scheduleAudits = scheduleAuditRepository.findByAudit(audit);

        return scheduleAudits.get(0).getProcesses();
    }
}
