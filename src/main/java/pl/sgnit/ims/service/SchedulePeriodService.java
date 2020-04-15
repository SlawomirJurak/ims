package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.model.ScheduleAudit;
import pl.sgnit.ims.model.SchedulePeriod;
import pl.sgnit.ims.repository.ProcessRepository;
import pl.sgnit.ims.repository.ScheduleAuditRepository;
import pl.sgnit.ims.repository.SchedulePeriodRepository;
import pl.sgnit.ims.util.Month;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchedulePeriodService {

    private final SchedulePeriodRepository schedulePeriodRepository;
    private final ProcessRepository processRepository;
    private final ScheduleAuditRepository scheduleAuditRepository;

    @Autowired
    public SchedulePeriodService(SchedulePeriodRepository schedulePeriodRepository, ProcessRepository processRepository, ScheduleAuditRepository scheduleAuditRepository) {
        this.schedulePeriodRepository = schedulePeriodRepository;
        this.processRepository = processRepository;
        this.scheduleAuditRepository = scheduleAuditRepository;
    }

    public List<SchedulePeriod> findAll() {
        return schedulePeriodRepository.findAll();
    }

    public List<SchedulePeriod> findAllByOrderByYearDesc() {
        return schedulePeriodRepository.findAllByOrderByYearDesc();
    }

    public Optional<SchedulePeriod> findById(Long id) {
        return schedulePeriodRepository.findById(id);
    }

    public void save(SchedulePeriod schedulePeriod) {
        schedulePeriodRepository.save(schedulePeriod);
    }

    public Map<String, String> getProcessesListInPeriod(Long periodId) {
        Map<String, String> result = new HashMap<>();
        List<Process> processList = processRepository.findByState("Aktywny");
        Map<String, Set<Integer>> processesMap = new HashMap<>();
        List<ScheduleAudit> scheduleAuditList = scheduleAuditRepository.findBySchedulePeriodId(periodId);

        processList
                .stream()
                .forEach(process -> processesMap.put(process.getCode(), new HashSet<>()));
        for (ScheduleAudit scheduleAudit : scheduleAuditList) {
            scheduleAudit.getProcesses().
                    stream().
                    forEach(process -> {
                        if (processesMap.get(process.getCode()) != null) {
                            processesMap.get(process.getCode()).add(scheduleAudit.getMonth());
                        }
                    });
        }
        for (Map.Entry<String, Set<Integer>> entry : processesMap.entrySet()) {
            String months = (String) entry.getValue()
                    .stream()
                    .map(Month::getMonthName)
                    .collect(Collectors.joining(","));
            result.put(entry.getKey(), months);
        }
        return result;
    }
}
