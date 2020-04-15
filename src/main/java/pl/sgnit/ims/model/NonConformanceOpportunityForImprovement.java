package pl.sgnit.ims.model;

import pl.sgnit.ims.validator.AssignedProcess;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "ncofi")
public class NonConformanceOpportunityForImprovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    @NotBlank
    @Size(max = 500)
    private String description;

    @Size(max = 4000)
    private String notes;

    @NotNull
    private LocalDate dueDate;

    private String responsible;

    @NotBlank
    private String area;

    @ManyToOne
    @JoinColumn(name = "process_id")
    @NotNull
    private Process process;

    @ManyToOne
    private Audit audit;

    private String confirmedBy;

    private LocalDate confirmDate;

    @Size(max = 4000)
    private String takenActions;

    private LocalDate completeDate;

    private Boolean successfully;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getTakenActions() {
        return takenActions;
    }

    public void setTakenActions(String takenActions) {
        this.takenActions = takenActions;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    public Boolean getSuccessfully() {
        return successfully;
    }

    public void setSuccessfully(Boolean successfully) {
        this.successfully = successfully;
    }

    public void setConfirmed(String confirmedBy) {
        setConfirmedBy(confirmedBy);
        setConfirmDate(LocalDate.now());
    }

    @PrePersist
    public void prePersist() {
        successfully = false;
    }
}
