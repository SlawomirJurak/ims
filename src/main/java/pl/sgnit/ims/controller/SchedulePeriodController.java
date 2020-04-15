package pl.sgnit.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.model.SchedulePeriod;
import pl.sgnit.ims.service.ScheduleAuditService;
import pl.sgnit.ims.service.SchedulePeriodService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

@Controller
@RequestMapping("/scheduleperiod")
public class SchedulePeriodController {

    private final SchedulePeriodService schedulePeriodService;
    private final ScheduleAuditService scheduleAuditService;

    @Autowired
    public SchedulePeriodController(SchedulePeriodService schedulePeriodService, ScheduleAuditService scheduleAuditService) {
        this.schedulePeriodService = schedulePeriodService;
        this.scheduleAuditService = scheduleAuditService;
    }

    @GetMapping("/")
    public String allPeriods(Model model) {
        List<SchedulePeriod> allPeriods;

        allPeriods = schedulePeriodService.findAllByOrderByYearDesc();
        model.addAttribute("allPeriods", allPeriods);
        return "schedule_period/all";
    }

    @GetMapping("/add")
    public String initAdd(Model model) {
        SchedulePeriod schedulePeriod = new SchedulePeriod();

        model.addAttribute("schedulePeriod", schedulePeriod);
        return "schedule_period/addEdit";
    }

    @PostMapping("/add")
    public String add(@Valid SchedulePeriod schedulePeriod, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "schedule_period/addEdit";
        }
        schedulePeriodService.save(schedulePeriod);
        return "redirect:";
    }

    @GetMapping("/approve")
    public String initApprove(Model model, @RequestParam Long toApproveId) {
        SchedulePeriod schedulePeriod = schedulePeriodService.findById(toApproveId).get();
        Map<String, String> processesMap = schedulePeriodService.getProcessesListInPeriod(toApproveId);
        final Boolean[] allProcessesAssigned = {true};

        processesMap.values().stream().forEach(value -> allProcessesAssigned[0] = allProcessesAssigned[0] && !value.isEmpty());
        model.addAttribute("schedulePeriod", schedulePeriod);
        model.addAttribute("processesMap", processesMap);
        model.addAttribute("allProcessesAssigned", allProcessesAssigned[0]);
        return "schedule_period/approve";
    }

    @PostMapping("/approve")
    public String approve(@RequestParam Long toApproveId) {
        SchedulePeriod schedulePeriod = schedulePeriodService.findById(toApproveId).get();

        schedulePeriod.setState("Zatwierdzony");
        schedulePeriodService.save(schedulePeriod);
        return "redirect:";
    }
}
