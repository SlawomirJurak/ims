package pl.sgnit.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.model.ScheduleAudit;
import pl.sgnit.ims.model.SchedulePeriod;
import pl.sgnit.ims.service.ProcessService;
import pl.sgnit.ims.service.ScheduleAuditService;
import pl.sgnit.ims.service.SchedulePeriodService;
import pl.sgnit.ims.util.Month;
import pl.sgnit.ims.util.ViewHelper;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/scheduleaudit")
public class ScheduleAuditController {

    private final ScheduleAuditService scheduleAuditService;
    private final ProcessService processService;
    private final SchedulePeriodService schedulePeriodService;

    public ScheduleAuditController(ScheduleAuditService scheduleAuditService, ProcessService processService, SchedulePeriodService schedulePeriodService) {
        this.scheduleAuditService = scheduleAuditService;
        this.processService = processService;
        this.schedulePeriodService = schedulePeriodService;
    }

    @ModelAttribute("allProcesses")
    public List<Process> allProcesses() {
        return processService.getActiveProcesses();
    }

    @ModelAttribute("monthList")
    public List<Month> monthList() {
        return Month.monthList();
    }


    @GetMapping("/add")
    public String initAdd(Model model, @RequestParam Long periodId) {
        ScheduleAudit scheduleAudit = new ScheduleAudit();

        model.addAttribute("scheduleAudit", scheduleAudit);
        model.addAttribute("periodId", periodId);
        return "schedule_audit/addEdit";
    }

    @PostMapping("/add")
//    public String add(@ModelAttribute ScheduleAudit scheduleAudit, @RequestParam Long periodId) {
    public String add(@Valid ScheduleAudit scheduleAudit, BindingResult bindingResult, @RequestParam Long periodId) {
        if (bindingResult.hasErrors()) {
            return "schedule_period/addEdit";
        }
        SchedulePeriod schedulePeriod = schedulePeriodService.findById(periodId).get();

        scheduleAudit.setSchedulePeriod(schedulePeriod);
        scheduleAuditService.save(scheduleAudit);
        return "redirect:/scheduleperiod/";
    }

    @GetMapping("/edit")
    public String initEdit(Model model, @RequestParam Long toEditScheduleAuditId) {
        ScheduleAudit scheduleAudit = scheduleAuditService.findById(toEditScheduleAuditId).get();

        model.addAttribute("scheduleAudit", scheduleAudit);
        return "schedule_audit/addEdit";
    }

    @PostMapping("/edit")
    public String edit(@Valid ScheduleAudit scheduleAudit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "schedule_audit/addEdit";
        }
        ScheduleAudit tmp = scheduleAuditService.findById(scheduleAudit.getId()).get();

        scheduleAudit.setSchedulePeriod(tmp.getSchedulePeriod());
        scheduleAuditService.save(scheduleAudit);
        return "redirect:/scheduleperiod/";
    }

    @GetMapping("/remove")
    public String initRemove(Model model, @RequestParam Long toRemoveScheduleAuditId) {
        ViewHelper viewHelper = new ViewHelper();

        model.addAttribute("viewHelper", viewHelper);
        model.addAttribute("scheduleAudit", scheduleAuditService.findById(toRemoveScheduleAuditId).get());
        return "schedule_audit/remove";
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute ViewHelper viewHelper, @RequestParam Long toRemoveId) {
        if ("yes".equals(viewHelper.getOption())) {
            scheduleAuditService.removeById(toRemoveId);
        }
        return "redirect:/scheduleperiod/";
    }
}
