package pl.sgnit.ims.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import pl.sgnit.ims.model.Audit;
import pl.sgnit.ims.model.ScheduleAudit;
import pl.sgnit.ims.service.AuditService;
import pl.sgnit.ims.service.NonConformanceOpportunityForImprovementService;
import pl.sgnit.ims.service.ScheduleAuditService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AuditControllerTest {
    private static AuditController auditController;
    private static AuditService auditService;
    private static ScheduleAuditService scheduleAuditService;
    private static NonConformanceOpportunityForImprovementService ncofiService;

    @BeforeAll
    public static void before() {
        auditService = Mockito.mock(AuditService.class);
        scheduleAuditService = Mockito.mock(ScheduleAuditService.class);
        ncofiService = Mockito.mock(NonConformanceOpportunityForImprovementService.class);
        auditController = new AuditController(auditService, scheduleAuditService, ncofiService);
    }

    @Test
    void allAudits() {
        Model model = new ExtendedModelMap();
        List<Audit> list = new ArrayList<>();

        list.add(new Audit());
        list.add(new Audit());
        list.add(new Audit());
        list.add(new Audit());
        Mockito.when(auditService.findAll()).thenReturn(list);
        String returnValue = auditController.allAudits(model);
        Assertions.assertEquals("audit/all", returnValue);
        Assertions.assertTrue(model.containsAttribute("allAudits"));
    }

    @Test
    void initAdd() {
        Model model = new ExtendedModelMap();
        Long scheduleAuditId = 1l;
        Optional<ScheduleAudit> sa = Optional.of(new ScheduleAudit());

        Mockito.when(scheduleAuditService.findById(Mockito.anyLong())).thenReturn(sa);
        String returnValue = auditController.initAdd(model, scheduleAuditId);
        Assertions.assertEquals("audit/addEdit", returnValue);
        Assertions.assertTrue(model.containsAttribute("audit"));
        Assertions.assertTrue(model.containsAttribute("scheduleAudit"));
    }

    @Test
    void add() {
        Audit audit = new Audit();
        Long scheduleAuditId = 1L;
        Optional<ScheduleAudit> sa = Optional.of(new ScheduleAudit());

        Mockito.when(scheduleAuditService.findById(Mockito.anyLong())).thenReturn(sa);
        String returnValue = auditController.add(audit, scheduleAuditId);
        Assertions.assertEquals("redirect:/scheduleperiod/", returnValue);
    }

    @Test
    void initEdit() {
        Model model = new ExtendedModelMap();
        Long idToEdit = 1l;
        Optional<Audit> audit = Optional.of(new Audit());

        Mockito.when(auditService.findById(idToEdit)).thenReturn(audit);
        String returnValue = auditController.initEdit(model, idToEdit);
        Assertions.assertEquals("audit/addEdit", returnValue);
        Assertions.assertTrue(model.containsAttribute("audit"));
    }

    @Test
    void edit() {
    }

    @Test
    void initApprove() {
    }

    @Test
    void approve() {
    }
}