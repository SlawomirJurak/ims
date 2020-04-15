package pl.sgnit.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sgnit.ims.model.SchedulePeriod;

import java.util.List;

public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod, Long> {

    List<SchedulePeriod> findAllByOrderByYearDesc();
}
