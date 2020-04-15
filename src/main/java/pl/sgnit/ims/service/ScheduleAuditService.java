package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sgnit.ims.model.ScheduleAudit;
import pl.sgnit.ims.repository.ScheduleAuditRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleAuditService {

    private final ScheduleAuditRepository scheduleAuditRepository;

    @Autowired
    public ScheduleAuditService(ScheduleAuditRepository scheduleAuditRepository) {
        this.scheduleAuditRepository = scheduleAuditRepository;
    }

    public List<ScheduleAudit> findAll() {
        return scheduleAuditRepository.findAll();
    }

    public Optional<ScheduleAudit> findById(Long id) {
        return scheduleAuditRepository.findById(id);
    }

    public void save(ScheduleAudit scheduleAudit) {
        scheduleAuditRepository.save(scheduleAudit);
    }

    public void removeById(Long toRemoveId) {
        scheduleAuditRepository.deleteById(toRemoveId);
    }

    public List<ScheduleAudit> findBySchedulePeriodId(Long schedulePeriodId) {
        return scheduleAuditRepository.findBySchedulePeriodId(schedulePeriodId);
    }
}
