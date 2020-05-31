package pl.sgnit.ims.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends TableTemplate {

    @Column(nullable = false, unique = true, length = 60)
    @NotBlank
    private String userName;

    @Column
    @NotBlank
    @Size(max = 150)
    private String firstName;

    @Column
    @NotBlank
    @Size(max = 150)
    private String lastName;

    private String password;

    private Boolean enabled;

    @Email
    private String email;

    @ColumnDefault("false")
    private Boolean administrator;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ColumnDefault("0")
    private Integer loginCounter;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public Integer getLoginCounter() {
        return loginCounter == null ? 0 : loginCounter;
    }

    public void setLoginCounter(Integer loginCounter) {
        this.loginCounter = loginCounter;
    }

    @PrePersist
    public void prePersist() {
        if (enabled == null) {
            enabled = false;
        }
        if (administrator == null) {
            administrator = false;
        }
        loginCounter = 0;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}
