package pl.sgnit.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sgnit.ims.model.Audit;
import pl.sgnit.ims.model.NonConformanceOpportunityForImprovement;
import pl.sgnit.ims.model.Process;
import pl.sgnit.ims.service.AuditService;
import pl.sgnit.ims.service.NonConformanceOpportunityForImprovementService;
import pl.sgnit.ims.service.ProcessService;
import pl.sgnit.ims.util.ViewHelper;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ncofi")
public class NCOFIController {

    private final NonConformanceOpportunityForImprovementService ncofiService;
    private final AuditService auditService;
    private final ProcessService processService;

    @Autowired
    public NCOFIController(NonConformanceOpportunityForImprovementService ncofiService, AuditService auditService, ProcessService processService) {
        this.ncofiService = ncofiService;
        this.auditService = auditService;
        this.processService = processService;
    }

    @ModelAttribute("ncofiTypes")
    public List<String> ncofiTypes() {
        List<String> result = new ArrayList<>();

        result.add("Niezgodność");
        result.add("Spostrzeżenie");
        return result;
    }

    @ModelAttribute("ncofiAreas")
    public List<String> ncofiAreas() {
        List<String> result = new ArrayList<>();

        result.add("Jakość");
        result.add("Środowisko");
        result.add("BHP");
        return result;
    }

    @GetMapping("/add")
    public String initAdd(Model model, @RequestParam Long auditId) {
        NonConformanceOpportunityForImprovement ncofi = new NonConformanceOpportunityForImprovement();

        model.addAttribute("ncofi", ncofi);
        model.addAttribute("auditId", auditId);
        model.addAttribute("assignedProcesses", auditService.getAssignedProcesses(auditId));
        return "ncofi/addEdit";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("ncofi") NonConformanceOpportunityForImprovement ncofi, BindingResult bindingResult, Model model, @RequestParam Long auditId) {
        Audit audit = auditService.findById(auditId).get();

        ncofi.setAudit(audit);
        if (bindingResult.hasErrors()) {
            model.addAttribute("ncofi", ncofi);
            model.addAttribute("auditId", auditId);
            model.addAttribute("assignedProcesses", auditService.getAssignedProcesses(auditId));
            return "redirect:/ncofi/add?auditId=" + auditId;
        }
        ncofiService.save(ncofi);
        return "redirect:/audit/";
    }

    @GetMapping("/edit")
    public String initEdit(Model model, @RequestParam Long toEditNcofiId) {
        NonConformanceOpportunityForImprovement ncofi = ncofiService.findById(toEditNcofiId).get();

        model.addAttribute("ncofi", ncofi);
        model.addAttribute("auditId", ncofi.getAudit().getId());
        model.addAttribute("assignedProcesses", auditService.getAssignedProcesses(ncofi.getAudit().getId()));
        return "ncofi/addEdit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("ncofi") NonConformanceOpportunityForImprovement ncofi, BindingResult bindingResult, Model model, @RequestParam Long auditId) {
        Audit audit = auditService.findById(auditId).get();
        Process process = processService.findById(ncofi.getProcess().getId()).get();

        ncofi.setAudit(audit);
        ncofi.setProcess(process);
        System.out.println("proces: "+ncofi.getProcess().getId());
        System.out.println("proces: "+ncofi.getProcess().getCode());
        if (bindingResult.hasErrors()) {
            model.addAttribute("ncofi", ncofi);
            model.addAttribute("auditId", ncofi.getAudit().getId());
            model.addAttribute("assignedProcesses", auditService.getAssignedProcesses(ncofi.getAudit().getId()));
            return "ncofi/addEdit";
        }
        ncofiService.save(ncofi);
        return "redirect:/audit/";
    }

    @GetMapping("/remove")
    public String initRemove(Model model, @RequestParam Long toRemoveNcofiId) {
        ViewHelper viewHelper = new ViewHelper();

        model.addAttribute("viewHelper", viewHelper);
        model.addAttribute("ncofi", ncofiService.findById(toRemoveNcofiId).get());
        return "ncofi/remove";
    }

    @PostMapping("/remove")
    public String remove(@ModelAttribute ViewHelper viewHelper, @RequestParam Long toRemoveNcofiId) {
        if ("yes".equals(viewHelper.getOption())) {
            ncofiService.removeById(toRemoveNcofiId);
        }
        return "redirect:/audit/";
    }

    @GetMapping("/confirm/{id}")
    public String initConfirm(Model model, @PathVariable(name = "id") Long ncofiId) {
        ViewHelper viewHelper = new ViewHelper();
        NonConformanceOpportunityForImprovement ncofi = ncofiService.findById(ncofiId).get();

        model.addAttribute("viewHelper", viewHelper);
        model.addAttribute("ncofi", ncofi);
        return "ncofi/confirm";
    }

    @PostMapping("/confirm/{id}")
    public String confirm(@ModelAttribute ViewHelper viewHelper, @PathVariable(name = "id") Long ncofiId,
                          @RequestParam String confirmedBy) {
        NonConformanceOpportunityForImprovement ncofi = ncofiService.findById(ncofiId).get();

        ncofi.setConfirmed(confirmedBy);
        ncofiService.save(ncofi);
        return "redirect:/audit/";
    }

    @GetMapping("/completed/{id}")
    public String initCompleted(Model model, @PathVariable(name = "id") Long ncofiId) {
        NonConformanceOpportunityForImprovement ncofi = ncofiService.findById(ncofiId).get();

        model.addAttribute("ncofi", ncofi);
        return "ncofi/completed";
    }

    @PostMapping("/completed/{id}")
    public String completed(@PathVariable(name = "id") Long ncofiId, @RequestParam LocalDate completeDate,
                            @RequestParam String takenActions, @RequestParam String successfully) {
        NonConformanceOpportunityForImprovement ncofi = ncofiService.findById(ncofiId).get();

        ncofi.setCompleteDate(completeDate);
        ncofi.setTakenActions(takenActions);
        ncofi.setSuccessfully("Skuteczne".equals(successfully) ? true : false);
        ncofiService.save(ncofi);
        return "redirect:/audit/";
    }
}
