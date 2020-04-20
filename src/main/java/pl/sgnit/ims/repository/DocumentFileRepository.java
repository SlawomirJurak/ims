package pl.sgnit.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sgnit.ims.model.DocumentFile;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {
}
