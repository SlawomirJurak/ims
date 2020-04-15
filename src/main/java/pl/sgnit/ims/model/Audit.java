package pl.sgnit.ims.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "audits")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 4000)
    private String description;

    @NotBlank
    @Size(max = 50)
    private String state;

    @Size(max = 150)
    private String approvedBy;

    private LocalDate approveDate;

    @OneToOne
    @JoinColumn(name = "scheduleAudit_id")
    private ScheduleAudit scheduleAudit;

    @OneToMany(mappedBy = "audit")
    private List<NonConformanceOpportunityForImprovement> ncofiList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDate getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(LocalDate approveDate) {
        this.approveDate = approveDate;
    }

    public ScheduleAudit getScheduleAudit() {
        return scheduleAudit;
    }

    public void setScheduleAudit(ScheduleAudit scheduleAudit) {
        this.scheduleAudit = scheduleAudit;
    }

    public List<NonConformanceOpportunityForImprovement> getNcofiList() {
        return ncofiList;
    }

    public void setNcofiList(List<NonConformanceOpportunityForImprovement> ncofi) {
        this.ncofiList = ncofi;
    }

    @PrePersist
    public void prePersist() {
        state = "W przygotowaniu";
    }

    public long getCompletedNcofiCount() {
        return ncofiList
                .stream()
                .filter(ncofi -> ncofi.getCompleteDate()!=null)
                .count();
    }
}
