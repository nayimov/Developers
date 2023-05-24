package uz.uzkassa.developers.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.uzkassa.developers.domain.Skill;
import uz.uzkassa.developers.repository.SkillRepository;
import uz.uzkassa.developers.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.uzkassa.developers.domain.Skill}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    private static final String ENTITY_NAME = "skill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillRepository skillRepository;

    public SkillResource(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * {@code POST  /skills} : Create a new skill.
     *
     * @param skill the skill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skill, or with status {@code 400 (Bad Request)} if the skill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            throw new BadRequestAlertException("A new skill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skill result = skillRepository.save(skill);
        return ResponseEntity
            .created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skills/:id} : Updates an existing skill.
     *
     * @param id the id of the skill to save.
     * @param skill the skill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skill,
     * or with status {@code 400 (Bad Request)} if the skill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skills/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Skill skill)
        throws URISyntaxException {
        log.debug("REST request to update Skill : {}, {}", id, skill);
        if (skill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Skill result = skillRepository.save(skill);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skill.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /skills/:id} : Partial updates given fields of an existing skill, field will ignore if it is null
     *
     * @param id the id of the skill to save.
     * @param skill the skill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skill,
     * or with status {@code 400 (Bad Request)} if the skill is not valid,
     * or with status {@code 404 (Not Found)} if the skill is not found,
     * or with status {@code 500 (Internal Server Error)} if the skill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/skills/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Skill> partialUpdateSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Skill skill
    ) throws URISyntaxException {
        log.debug("REST request to partial update Skill partially : {}, {}", id, skill);
        if (skill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Skill> result = skillRepository
            .findById(skill.getId())
            .map(existingSkill -> {
                if (skill.getLevel() != null) {
                    existingSkill.setLevel(skill.getLevel());
                }
                if (skill.getSkill() != null) {
                    existingSkill.setSkill(skill.getSkill());
                }
                if (skill.getDescription() != null) {
                    existingSkill.setDescription(skill.getDescription());
                }

                return existingSkill;
            })
            .map(skillRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skill.getId().toString())
        );
    }

    /**
     * {@code GET  /skills} : get all the skills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skills in body.
     */
    @GetMapping("/skills")
    public List<Skill> getAllSkills() {
        log.debug("REST request to get all Skills");
        return skillRepository.findAll();
    }

    /**
     * {@code GET  /skills/:id} : get the "id" skill.
     *
     * @param id the id of the skill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Optional<Skill> skill = skillRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(skill);
    }

    /**
     * {@code DELETE  /skills/:id} : delete the "id" skill.
     *
     * @param id the id of the skill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
