package uz.uzkassa.developers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Career.
 */
@Entity
@Table(name = "career")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Career implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 999)
    @Column(name = "step", nullable = false)
    private Integer step;

    @NotNull
    @Size(max = 64)
    @Column(name = "description", length = 64, nullable = false)
    private String description;

    @Size(max = 128)
    @Column(name = "image_path", length = 128)
    private String imagePath;

    @Size(max = 512)
    @Column(name = "synopsis", length = 512)
    private String synopsis;

    @ManyToOne
    @JsonIgnoreProperties(value = { "projects", "skills", "careers", "interests" }, allowSetters = true)
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Career id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStep() {
        return this.step;
    }

    public Career step(Integer step) {
        this.setStep(step);
        return this;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getDescription() {
        return this.description;
    }

    public Career description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Career imagePath(String imagePath) {
        this.setImagePath(imagePath);
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public Career synopsis(String synopsis) {
        this.setSynopsis(synopsis);
        return this;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Career profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Career)) {
            return false;
        }
        return id != null && id.equals(((Career) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Career{" +
            "id=" + getId() +
            ", step=" + getStep() +
            ", description='" + getDescription() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", synopsis='" + getSynopsis() + "'" +
            "}";
    }
}
