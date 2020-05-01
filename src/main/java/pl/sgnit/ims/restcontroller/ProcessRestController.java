package pl.sgnit.ims.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.service.ProcessService;

@RestController
@RequestMapping("/process")
public class ProcessRestController {

    private final ProcessService processService;

    @Autowired
    public ProcessRestController(ProcessService processService) {
        this.processService = processService;
    }

    @DeleteMapping("/remove")
    public String removeProcess(@RequestBody Process process) {
        String result = processService.removeProcess(process);

        return result;
    }
}
