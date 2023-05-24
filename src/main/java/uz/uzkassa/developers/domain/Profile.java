package uz.uzkassa.developers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.uzkassa.developers.domain.enumeration.ProfileStatus;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Size(max = 64)
    @Column(name = "location", length = 64)
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProfileStatus status;

    @Size(max = 128)
    @Column(name = "avatar_path", length = 128)
    private String avatarPath;

    @Size(max = 128)
    @Column(name = "photo_path", length = 128)
    private String photoPath;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @NotNull
    @Size(min = 5, max = 256)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", length = 256, nullable = false, unique = true)
    private String email;

    @Size(max = 2048)
    @Column(name = "github", length = 2048)
    private String github;

    @Size(max = 2048)
    @Column(name = "linkedin", length = 2048)
    private String linkedin;

    @Size(max = 2048)
    @Column(name = "twitter", length = 2048)
    private String twitter;

    @NotNull
    @Min(value = 0L)
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @NotNull
    @Column(name = "created_dt", nullable = false)
    private Instant createdDt;

    @Min(value = 0L)
    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modified_dt")
    private Instant modifiedDt;

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profile" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profile", "skillCategory" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profile" }, allowSetters = true)
    private Set<Career> careers = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "profile" }, allowSetters = true)
    private Set<Interest> interests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Profile name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public Profile location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProfileStatus getStatus() {
        return this.status;
    }

    public Profile status(ProfileStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

    public String getAvatarPath() {
        return this.avatarPath;
    }

    public Profile avatarPath(String avatarPath) {
        this.setAvatarPath(avatarPath);
        return this;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public Profile photoPath(String photoPath) {
        this.setPhotoPath(photoPath);
        return this;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getDescription() {
        return this.description;
    }

    public Profile description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return this.email;
    }

    public Profile email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return this.github;
    }

    public Profile github(String github) {
        this.setGithub(github);
        return this;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedin() {
        return this.linkedin;
    }

    public Profile linkedin(String linkedin) {
        this.setLinkedin(linkedin);
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public Profile twitter(String twitter) {
        this.setTwitter(twitter);
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Profile createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDt() {
        return this.createdDt;
    }

    public Profile createdDt(Instant createdDt) {
        this.setCreatedDt(createdDt);
        return this;
    }

    public void setCreatedDt(Instant createdDt) {
        this.createdDt = createdDt;
    }

    public Long getModifiedBy() {
        return this.modifiedBy;
    }

    public Profile modifiedBy(Long modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDt() {
        return this.modifiedDt;
    }

    public Profile modifiedDt(Instant modifiedDt) {
        this.setModifiedDt(modifiedDt);
        return this;
    }

    public void setModifiedDt(Instant modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setProfile(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setProfile(this));
        }
        this.projects = projects;
    }

    public Profile projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Profile addProject(Project project) {
        this.projects.add(project);
        project.setProfile(this);
        return this;
    }

    public Profile removeProject(Project project) {
        this.projects.remove(project);
        project.setProfile(null);
        return this;
    }

    public Set<Skill> getSkills() {
        return this.skills;
    }

    public void setSkills(Set<Skill> skills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setProfile(null));
        }
        if (skills != null) {
            skills.forEach(i -> i.setProfile(this));
        }
        this.skills = skills;
    }

    public Profile skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    public Profile addSkill(Skill skill) {
        this.skills.add(skill);
        skill.setProfile(this);
        return this;
    }

    public Profile removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.setProfile(null);
        return this;
    }

    public Set<Career> getCareers() {
        return this.careers;
    }

    public void setCareers(Set<Career> careers) {
        if (this.careers != null) {
            this.careers.forEach(i -> i.setProfile(null));
        }
        if (careers != null) {
            careers.forEach(i -> i.setProfile(this));
        }
        this.careers = careers;
    }

    public Profile careers(Set<Career> careers) {
        this.setCareers(careers);
        return this;
    }

    public Profile addCareer(Career career) {
        this.careers.add(career);
        career.setProfile(this);
        return this;
    }

    public Profile removeCareer(Career career) {
        this.careers.remove(career);
        career.setProfile(null);
        return this;
    }

    public Set<Interest> getInterests() {
        return this.interests;
    }

    public void setInterests(Set<Interest> interests) {
        if (this.interests != null) {
            this.interests.forEach(i -> i.setProfile(null));
        }
        if (interests != null) {
            interests.forEach(i -> i.setProfile(this));
        }
        this.interests = interests;
    }

    public Profile interests(Set<Interest> interests) {
        this.setInterests(interests);
        return this;
    }

    public Profile addInterest(Interest interest) {
        this.interests.add(interest);
        interest.setProfile(this);
        return this;
    }

    public Profile removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.setProfile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", status='" + getStatus() + "'" +
            ", avatarPath='" + getAvatarPath() + "'" +
            ", photoPath='" + getPhotoPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
            ", github='" + getGithub() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDt='" + getCreatedDt() + "'" +
            ", modifiedBy=" + getModifiedBy() +
            ", modifiedDt='" + getModifiedDt() + "'" +
            "}";
    }
}
