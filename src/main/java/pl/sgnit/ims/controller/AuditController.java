package pl.sgnit.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sgnit.ims.model.Audit;
import pl.sgnit.ims.model.NonConformanceOpportunityForImprovement;
import pl.sgnit.ims.model.ScheduleAudit;
import pl.sgnit.ims.service.AuditService;
import pl.sgnit.ims.service.NonConformanceOpportunityForImprovementService;
import pl.sgnit.ims.service.ScheduleAuditService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;
    private final ScheduleAuditService scheduleAuditService;
    private final NonConformanceOpportunityForImprovementService ncofiService;

    @Autowired
    public AuditController(AuditService auditService, ScheduleAuditService scheduleAuditService, NonConformanceOpportunityForImprovementService ncofiService) {
        this.auditService = auditService;
        this.scheduleAuditService = scheduleAuditService;
        this.ncofiService = ncofiService;
    }

    @GetMapping("/")
    public String allAudits(Model model) {
        List<Audit> allAudits = auditService.findAll();

        model.addAttribute("allAudits", allAudits);
        return "audit/all";
    }

    @GetMapping("/add")
    public String initAdd(Model model, @RequestParam Long scheduleAuditId) {
        model.addAttribute("audit", new Audit());
        model.addAttribute("scheduleAudit", scheduleAuditService.findById(scheduleAuditId).get());
        return "audit/addEdit";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Audit audit, @RequestParam Long scheduleAuditId) {
        Optional<ScheduleAudit> scheduleAudit = scheduleAuditService.findById(scheduleAuditId);

        audit.setScheduleAudit(scheduleAudit.get());
        auditService.save(audit);
        return "redirect:/scheduleperiod/";
    }

    @GetMapping("/edit")
    public String initEdit(Model model, @RequestParam Long idToEdit) {
        Optional<Audit> audit = auditService.findById(idToEdit);

        model.addAttribute("audit", audit.get());
        return "audit/addEdit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Audit audit) {
        Optional<Audit> oldAudit = auditService.findById(audit.getId());

        audit.setScheduleAudit(oldAudit.get().getScheduleAudit());
        audit.setNcofiList(oldAudit.get().getNcofiList());
        auditService.save(audit);
        return "redirect:";
    }

    @GetMapping("/approve")
    public String initApprove(Model model, @RequestParam Long idToApprove) {
        Audit audit = auditService.findById(idToApprove).get();
        List<NonConformanceOpportunityForImprovement> ncofiList = ncofiService.findByAuditIdAndConfirmDateIsNull(idToApprove);

        model.addAttribute("audit", audit);
        if (ncofiList.size()>0) {
            model.addAttribute("ncofiList", ncofiList);
        }
        return "audit/approve";
    }

    @PostMapping("/approve")
    public String approve(@RequestParam Long idToApprove, @RequestParam String approvedBy) {
        Audit audit = auditService.findById(idToApprove).get();

        audit.setApprovedBy(approvedBy);
        audit.setApproveDate(LocalDate.now());
        audit.setState("Zatwierdzony");
        auditService.save(audit);
        return "redirect:/audit/";
    }
}
