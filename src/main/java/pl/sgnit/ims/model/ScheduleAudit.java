package pl.sgnit.ims.model;

import pl.sgnit.ims.util.Month;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "schedule_audits")
public class ScheduleAudit extends TableTemplate {

    @Min(1)
    @Max(12)
    @NotNull
    private Integer month;

    @Transient
    private String monthName;

    @Size(max = 4000)
    private String description;

    @Size(min = 1)
    @ManyToMany
    private List<Process> processes = new ArrayList<>();

    @OneToOne(mappedBy = "scheduleAudit")
    private Audit audit;

    @ManyToOne
    private SchedulePeriod schedulePeriod;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getMonthName() {
        return monthName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public void setProcesses(List<Process> processes) {
        this.processes = processes;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public SchedulePeriod getSchedulePeriod() {
        return schedulePeriod;
    }

    public void setSchedulePeriod(SchedulePeriod schedulePeriod) {
        this.schedulePeriod = schedulePeriod;
    }

    public String getAssignedProcesses() {
        return processes.stream()
                .map(process -> process.getCode())
                .collect(Collectors.joining(","));
    }

    @PostLoad
    public void postLoad() {
        monthName = Month.getMonthName(month-1);
    }
}
