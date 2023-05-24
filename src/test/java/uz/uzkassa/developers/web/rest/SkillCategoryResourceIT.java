package uz.uzkassa.developers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.uzkassa.developers.IntegrationTest;
import uz.uzkassa.developers.domain.SkillCategory;
import uz.uzkassa.developers.repository.SkillCategoryRepository;

/**
 * Integration tests for the {@link SkillCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SkillCategoryResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/skill-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillCategoryMockMvc;

    private SkillCategory skillCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillCategory createEntity(EntityManager em) {
        SkillCategory skillCategory = new SkillCategory().category(DEFAULT_CATEGORY);
        return skillCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillCategory createUpdatedEntity(EntityManager em) {
        SkillCategory skillCategory = new SkillCategory().category(UPDATED_CATEGORY);
        return skillCategory;
    }

    @BeforeEach
    public void initTest() {
        skillCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createSkillCategory() throws Exception {
        int databaseSizeBeforeCreate = skillCategoryRepository.findAll().size();
        // Create the SkillCategory
        restSkillCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isCreated());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void createSkillCategoryWithExistingId() throws Exception {
        // Create the SkillCategory with an existing ID
        skillCategory.setId(1L);

        int databaseSizeBeforeCreate = skillCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillCategoryRepository.findAll().size();
        // set the field null
        skillCategory.setCategory(null);

        // Create the SkillCategory, which fails.

        restSkillCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSkillCategories() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList
        restSkillCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)));
    }

    @Test
    @Transactional
    void getSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get the skillCategory
        restSkillCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, skillCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skillCategory.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY));
    }

    @Test
    @Transactional
    void getNonExistingSkillCategory() throws Exception {
        // Get the skillCategory
        restSkillCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();

        // Update the skillCategory
        SkillCategory updatedSkillCategory = skillCategoryRepository.findById(skillCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSkillCategory are not directly saved in db
        em.detach(updatedSkillCategory);
        updatedSkillCategory.category(UPDATED_CATEGORY);

        restSkillCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSkillCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSkillCategory))
            )
            .andExpect(status().isOk());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void putNonExistingSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();
        skillCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, skillCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();
        skillCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(skillCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();
        skillCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkillCategoryWithPatch() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();

        // Update the skillCategory using partial update
        SkillCategory partialUpdatedSkillCategory = new SkillCategory();
        partialUpdatedSkillCategory.setId(skillCategory.getId());

        restSkillCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkillCategory))
            )
            .andExpect(status().isOk());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    void fullUpdateSkillCategoryWithPatch() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();

        // Update the skillCategory using partial update
        SkillCategory partialUpdatedSkillCategory = new SkillCategory();
        partialUpdatedSkillCategory.setId(skillCategory.getId());

        partialUpdatedSkillCategory.category(UPDATED_CATEGORY);

        restSkillCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkillCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkillCategory))
            )
            .andExpect(status().isOk());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void patchNonExistingSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();
        skillCategory.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skillCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();
        skillCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skillCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();
        skillCategory.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(skillCategory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        int databaseSizeBeforeDelete = skillCategoryRepository.findAll().size();

        // Delete the skillCategory
        restSkillCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, skillCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
