package pl.sgnit.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.service.ProcessService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/process")
public class ProcessController {

    private final ProcessService processService;

    @Autowired
    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @GetMapping("/")
    public String allProcesses(Model model) {
        List<Process> processes = processService.findByOrderByCodeAsc();

        model.addAttribute("allProcesses", processes);
        return "process/all";
    }

    @GetMapping("/add")
    public String initAdd(Model model) {
        Process process = new Process();
        model.addAttribute("process", process);
        return "process/addEdit";
    }

    @PostMapping("/add")
    public String add(@Valid Process process, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "process/addEdit";
        }
        processService.save(process);
        return "redirect:";
    }

    @GetMapping("/edit")
    public String initEdit(@RequestParam Long idToEdit, Model model) {
        Process process = processService.findById(idToEdit).get();
        model.addAttribute("process", process);
        return "process/addEdit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Process process, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "process/addEdit";
        }
        processService.save(process);
        return "redirect:";
    }

    @PostMapping("/changeState")
    public String changeState(@RequestParam Long idChangeState, @RequestParam String newState) {
        Process process = processService.findById(idChangeState).get();

        process.setState(newState);
        processService.save(process);
        return "redirect:";
    }
}
