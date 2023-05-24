package uz.uzkassa.developers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uz.uzkassa.developers.domain.enumeration.SkillLevel;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private SkillLevel level;

    @Size(max = 64)
    @Column(name = "skill", length = 64)
    private String skill;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "projects", "skills", "careers", "interests" }, allowSetters = true)
    private Profile profile;

    @ManyToOne
    @JsonIgnoreProperties(value = { "skills" }, allowSetters = true)
    private SkillCategory skillCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Skill id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SkillLevel getLevel() {
        return this.level;
    }

    public Skill level(SkillLevel level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    public String getSkill() {
        return this.skill;
    }

    public Skill skill(String skill) {
        this.setSkill(skill);
        return this;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDescription() {
        return this.description;
    }

    public Skill description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Skill profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    public SkillCategory getSkillCategory() {
        return this.skillCategory;
    }

    public void setSkillCategory(SkillCategory skillCategory) {
        this.skillCategory = skillCategory;
    }

    public Skill skillCategory(SkillCategory skillCategory) {
        this.setSkillCategory(skillCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", skill='" + getSkill() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
