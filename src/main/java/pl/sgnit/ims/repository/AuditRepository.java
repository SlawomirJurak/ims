package pl.sgnit.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sgnit.ims.model.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
