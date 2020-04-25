package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.repository.ProcessRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessService {

    private final ProcessRepository processRepository;

    @Autowired
    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public List<Process> findAll() {
        return processRepository.findAll();
    }

    public List<Process> findByOrderByCodeAsc() {
        return processRepository.findByOrderByCodeAsc();
    }

    public Optional<Process> findById(Long id) {
        return processRepository.findById(id);
    }

    public void save(Process process) {
        processRepository.save(process);
    }

    public List<Process> getActiveProcesses() {
        return processRepository.findByState("Aktywny");
    }

    public void removeProcess(Long processId) {
        processRepository.deleteById(processId);
    }
}
