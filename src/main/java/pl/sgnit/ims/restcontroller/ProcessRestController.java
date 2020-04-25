package pl.sgnit.ims.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sgnit.ims.service.ProcessService;

@RestController
@RequestMapping("/process")
public class ProcessRestController {

    private final ProcessService processService;

    @Autowired
    public ProcessRestController(ProcessService processService) {
        this.processService = processService;
    }

    @DeleteMapping("/remove/{id}")
    public String removeProcess(@PathVariable Long id) {
        processService.removeProcess(id);
        return "OK";
    }
}
