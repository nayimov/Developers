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
import uz.uzkassa.developers.domain.Career;
import uz.uzkassa.developers.repository.CareerRepository;
import uz.uzkassa.developers.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.uzkassa.developers.domain.Career}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CareerResource {

    private final Logger log = LoggerFactory.getLogger(CareerResource.class);

    private static final String ENTITY_NAME = "career";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CareerRepository careerRepository;

    public CareerResource(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    /**
     * {@code POST  /careers} : Create a new career.
     *
     * @param career the career to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new career, or with status {@code 400 (Bad Request)} if the career has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/careers")
    public ResponseEntity<Career> createCareer(@Valid @RequestBody Career career) throws URISyntaxException {
        log.debug("REST request to save Career : {}", career);
        if (career.getId() != null) {
            throw new BadRequestAlertException("A new career cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Career result = careerRepository.save(career);
        return ResponseEntity
            .created(new URI("/api/careers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /careers/:id} : Updates an existing career.
     *
     * @param id the id of the career to save.
     * @param career the career to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated career,
     * or with status {@code 400 (Bad Request)} if the career is not valid,
     * or with status {@code 500 (Internal Server Error)} if the career couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/careers/{id}")
    public ResponseEntity<Career> updateCareer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Career career
    ) throws URISyntaxException {
        log.debug("REST request to update Career : {}, {}", id, career);
        if (career.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, career.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!careerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Career result = careerRepository.save(career);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, career.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /careers/:id} : Partial updates given fields of an existing career, field will ignore if it is null
     *
     * @param id the id of the career to save.
     * @param career the career to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated career,
     * or with status {@code 400 (Bad Request)} if the career is not valid,
     * or with status {@code 404 (Not Found)} if the career is not found,
     * or with status {@code 500 (Internal Server Error)} if the career couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/careers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Career> partialUpdateCareer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Career career
    ) throws URISyntaxException {
        log.debug("REST request to partial update Career partially : {}, {}", id, career);
        if (career.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, career.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!careerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Career> result = careerRepository
            .findById(career.getId())
            .map(existingCareer -> {
                if (career.getStep() != null) {
                    existingCareer.setStep(career.getStep());
                }
                if (career.getDescription() != null) {
                    existingCareer.setDescription(career.getDescription());
                }
                if (career.getImagePath() != null) {
                    existingCareer.setImagePath(career.getImagePath());
                }
                if (career.getSynopsis() != null) {
                    existingCareer.setSynopsis(career.getSynopsis());
                }

                return existingCareer;
            })
            .map(careerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, career.getId().toString())
        );
    }

    /**
     * {@code GET  /careers} : get all the careers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of careers in body.
     */
    @GetMapping("/careers")
    public List<Career> getAllCareers() {
        log.debug("REST request to get all Careers");
        return careerRepository.findAll();
    }

    /**
     * {@code GET  /careers/:id} : get the "id" career.
     *
     * @param id the id of the career to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the career, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/careers/{id}")
    public ResponseEntity<Career> getCareer(@PathVariable Long id) {
        log.debug("REST request to get Career : {}", id);
        Optional<Career> career = careerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(career);
    }

    /**
     * {@code DELETE  /careers/:id} : delete the "id" career.
     *
     * @param id the id of the career to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/careers/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        log.debug("REST request to delete Career : {}", id);
        careerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
