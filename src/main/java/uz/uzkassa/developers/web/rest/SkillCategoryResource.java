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
import uz.uzkassa.developers.domain.SkillCategory;
import uz.uzkassa.developers.repository.SkillCategoryRepository;
import uz.uzkassa.developers.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.uzkassa.developers.domain.SkillCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SkillCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SkillCategoryResource.class);

    private static final String ENTITY_NAME = "skillCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillCategoryRepository skillCategoryRepository;

    public SkillCategoryResource(SkillCategoryRepository skillCategoryRepository) {
        this.skillCategoryRepository = skillCategoryRepository;
    }

    /**
     * {@code POST  /skill-categories} : Create a new skillCategory.
     *
     * @param skillCategory the skillCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillCategory, or with status {@code 400 (Bad Request)} if the skillCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-categories")
    public ResponseEntity<SkillCategory> createSkillCategory(@Valid @RequestBody SkillCategory skillCategory) throws URISyntaxException {
        log.debug("REST request to save SkillCategory : {}", skillCategory);
        if (skillCategory.getId() != null) {
            throw new BadRequestAlertException("A new skillCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillCategory result = skillCategoryRepository.save(skillCategory);
        return ResponseEntity
            .created(new URI("/api/skill-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-categories/:id} : Updates an existing skillCategory.
     *
     * @param id the id of the skillCategory to save.
     * @param skillCategory the skillCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillCategory,
     * or with status {@code 400 (Bad Request)} if the skillCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-categories/{id}")
    public ResponseEntity<SkillCategory> updateSkillCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SkillCategory skillCategory
    ) throws URISyntaxException {
        log.debug("REST request to update SkillCategory : {}, {}", id, skillCategory);
        if (skillCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SkillCategory result = skillCategoryRepository.save(skillCategory);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /skill-categories/:id} : Partial updates given fields of an existing skillCategory, field will ignore if it is null
     *
     * @param id the id of the skillCategory to save.
     * @param skillCategory the skillCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillCategory,
     * or with status {@code 400 (Bad Request)} if the skillCategory is not valid,
     * or with status {@code 404 (Not Found)} if the skillCategory is not found,
     * or with status {@code 500 (Internal Server Error)} if the skillCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/skill-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SkillCategory> partialUpdateSkillCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SkillCategory skillCategory
    ) throws URISyntaxException {
        log.debug("REST request to partial update SkillCategory partially : {}, {}", id, skillCategory);
        if (skillCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skillCategory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SkillCategory> result = skillCategoryRepository
            .findById(skillCategory.getId())
            .map(existingSkillCategory -> {
                if (skillCategory.getCategory() != null) {
                    existingSkillCategory.setCategory(skillCategory.getCategory());
                }

                return existingSkillCategory;
            })
            .map(skillCategoryRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillCategory.getId().toString())
        );
    }

    /**
     * {@code GET  /skill-categories} : get all the skillCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillCategories in body.
     */
    @GetMapping("/skill-categories")
    public List<SkillCategory> getAllSkillCategories() {
        log.debug("REST request to get all SkillCategories");
        return skillCategoryRepository.findAll();
    }

    /**
     * {@code GET  /skill-categories/:id} : get the "id" skillCategory.
     *
     * @param id the id of the skillCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-categories/{id}")
    public ResponseEntity<SkillCategory> getSkillCategory(@PathVariable Long id) {
        log.debug("REST request to get SkillCategory : {}", id);
        Optional<SkillCategory> skillCategory = skillCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(skillCategory);
    }

    /**
     * {@code DELETE  /skill-categories/:id} : delete the "id" skillCategory.
     *
     * @param id the id of the skillCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-categories/{id}")
    public ResponseEntity<Void> deleteSkillCategory(@PathVariable Long id) {
        log.debug("REST request to delete SkillCategory : {}", id);
        skillCategoryRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
