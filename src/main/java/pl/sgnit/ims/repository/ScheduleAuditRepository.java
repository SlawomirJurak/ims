package pl.sgnit.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.sgnit.ims.model.Audit;
import pl.sgnit.ims.model.ScheduleAudit;

import java.util.List;

public interface ScheduleAuditRepository extends JpaRepository<ScheduleAudit, Long> {

    @Query("select sa from ScheduleAudit sa where sa.audit = :audit")
    List<ScheduleAudit> findByAudit(@Param("audit")Audit audit);

    List<ScheduleAudit> findBySchedulePeriodId(Long schedulePeriodId);
}
