package pl.sgnit.ims.controller;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.ui.*;
import pl.sgnit.ims.model.*;
import pl.sgnit.ims.service.*;

import java.util.*;

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
        Long scheduleAuditId = 1L;
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
        Long idToEdit = 1L;
        Optional<Audit> audit = Optional.of(new Audit());

        Mockito.when(auditService.findById(idToEdit)).thenReturn(audit);
        String returnValue = auditController.initEdit(model, idToEdit);
        Assertions.assertEquals("audit/addEdit", returnValue);
        Assertions.assertTrue(model.containsAttribute("audit"));
    }

    @Test
    void edit() {
        Audit audit = new Audit();
        Optional<Audit> oldAudit = Optional.of(new Audit());

        audit.setId(1L);
        Mockito.when(auditService.findById(1L)).thenReturn(oldAudit);
        String returnValue = auditController.edit(audit);
        Assertions.assertEquals("redirect:", returnValue);
    }

    @Test
    void initApprove() {
        Model model = new ExtendedModelMap();
        Long idToApprove = 1L;
        Optional<Audit> audit = Optional.of(new Audit());
        List<NonConformanceOpportunityForImprovement> ncofiList = new ArrayList<>();

        ncofiList.add(new NonConformanceOpportunityForImprovement());
        Mockito.when(auditService.findById(idToApprove)).thenReturn(audit);
        Mockito.when(ncofiService.findByAuditIdAndConfirmDateIsNull(idToApprove)).thenReturn(ncofiList);
        String returnValue = auditController.initApprove(model, idToApprove);
        Assertions.assertEquals("audit/approve", returnValue);
        Assertions.assertTrue(model.containsAttribute("audit"));
        Assertions.assertTrue(model.containsAttribute("ncofiList"));
    }

    @Test
    void approve() {
        Optional<Audit> opAudit = Optional.of(new Audit());
        Long idToApprove = 1L;
        String approvedBy = "admin";

        Mockito.when(auditService.findById(Mockito.anyLong())).thenReturn(opAudit);
        String returnValue = auditController.approve(idToApprove, approvedBy);
        Assertions.assertEquals("redirect:/audit/", returnValue);
    }
}
