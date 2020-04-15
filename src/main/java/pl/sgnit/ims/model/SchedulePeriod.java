package pl.sgnit.ims.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule_period")
public class SchedulePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer year;

    private Integer version;

    private String description;

    private String state;

    @OneToMany(mappedBy = "schedulePeriod")
    @OrderBy("month asc")
    private List<ScheduleAudit> scheduleAudits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public List<ScheduleAudit> getScheduleAudits() {
        return scheduleAudits;
    }

    public void setScheduleAudits(List<ScheduleAudit> scheduleAudits) {
        this.scheduleAudits = scheduleAudits;
    }

    @PrePersist
    public void prePersist() {
        state = "W przygotowaniu";
    }
}
