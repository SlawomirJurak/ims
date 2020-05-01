package pl.sgnit.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sgnit.ims.model.Process;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findByOrderByCodeAsc();

    List<Process> findByState(String state);

    Optional<Process> findByIdAndRv(Long id, LocalDateTime rv);
}
