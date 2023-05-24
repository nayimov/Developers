package uz.uzkassa.developers.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import uz.uzkassa.developers.domain.Profile;
import uz.uzkassa.developers.domain.enumeration.ProfileStatus;
import uz.uzkassa.developers.repository.ProfileRepository;

/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfileResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final ProfileStatus DEFAULT_STATUS = ProfileStatus.AVAILABLE;
    private static final ProfileStatus UPDATED_STATUS = ProfileStatus.UNAVAILABLE;

    private static final String DEFAULT_AVATAR_PATH = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "]SJ=@O&jn(j.9qxD";
    private static final String UPDATED_EMAIL = "]Fm'@Mc.=A";

    private static final String DEFAULT_GITHUB = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 0L;
    private static final Long UPDATED_CREATED_BY = 1L;

    private static final Instant DEFAULT_CREATED_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_MODIFIED_BY = 0L;
    private static final Long UPDATED_MODIFIED_BY = 1L;

    private static final Instant DEFAULT_MODIFIED_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .name(DEFAULT_NAME)
            .location(DEFAULT_LOCATION)
            .status(DEFAULT_STATUS)
            .avatarPath(DEFAULT_AVATAR_PATH)
            .photoPath(DEFAULT_PHOTO_PATH)
            .description(DEFAULT_DESCRIPTION)
            .email(DEFAULT_EMAIL)
            .github(DEFAULT_GITHUB)
            .linkedin(DEFAULT_LINKEDIN)
            .twitter(DEFAULT_TWITTER)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDt(DEFAULT_CREATED_DT)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDt(DEFAULT_MODIFIED_DT);
        return profile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS)
            .avatarPath(UPDATED_AVATAR_PATH)
            .photoPath(UPDATED_PHOTO_PATH)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .github(UPDATED_GITHUB)
            .linkedin(UPDATED_LINKEDIN)
            .twitter(UPDATED_TWITTER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDt(UPDATED_CREATED_DT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDt(UPDATED_MODIFIED_DT);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProfile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProfile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProfile.getAvatarPath()).isEqualTo(DEFAULT_AVATAR_PATH);
        assertThat(testProfile.getPhotoPath()).isEqualTo(DEFAULT_PHOTO_PATH);
        assertThat(testProfile.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfile.getGithub()).isEqualTo(DEFAULT_GITHUB);
        assertThat(testProfile.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testProfile.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testProfile.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProfile.getCreatedDt()).isEqualTo(DEFAULT_CREATED_DT);
        assertThat(testProfile.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testProfile.getModifiedDt()).isEqualTo(DEFAULT_MODIFIED_DT);
    }

    @Test
    @Transactional
    void createProfileWithExistingId() throws Exception {
        // Create the Profile with an existing ID
        profile.setId(1L);

        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setName(null);

        // Create the Profile, which fails.

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setStatus(null);

        // Create the Profile, which fails.

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setEmail(null);

        // Create the Profile, which fails.

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setCreatedBy(null);

        // Create the Profile, which fails.

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDtIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setCreatedDt(null);

        // Create the Profile, which fails.

        restProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].avatarPath").value(hasItem(DEFAULT_AVATAR_PATH)))
            .andExpect(jsonPath("$.[*].photoPath").value(hasItem(DEFAULT_PHOTO_PATH)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].github").value(hasItem(DEFAULT_GITHUB)))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN)))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDt").value(hasItem(DEFAULT_CREATED_DT.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].modifiedDt").value(hasItem(DEFAULT_MODIFIED_DT.toString())));
    }

    @Test
    @Transactional
    void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.avatarPath").value(DEFAULT_AVATAR_PATH))
            .andExpect(jsonPath("$.photoPath").value(DEFAULT_PHOTO_PATH))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.github").value(DEFAULT_GITHUB))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDt").value(DEFAULT_CREATED_DT.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.modifiedDt").value(DEFAULT_MODIFIED_DT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS)
            .avatarPath(UPDATED_AVATAR_PATH)
            .photoPath(UPDATED_PHOTO_PATH)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .github(UPDATED_GITHUB)
            .linkedin(UPDATED_LINKEDIN)
            .twitter(UPDATED_TWITTER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDt(UPDATED_CREATED_DT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDt(UPDATED_MODIFIED_DT);

        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProfile.getAvatarPath()).isEqualTo(UPDATED_AVATAR_PATH);
        assertThat(testProfile.getPhotoPath()).isEqualTo(UPDATED_PHOTO_PATH);
        assertThat(testProfile.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfile.getGithub()).isEqualTo(UPDATED_GITHUB);
        assertThat(testProfile.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testProfile.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProfile.getCreatedDt()).isEqualTo(UPDATED_CREATED_DT);
        assertThat(testProfile.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testProfile.getModifiedDt()).isEqualTo(UPDATED_MODIFIED_DT);
    }

    @Test
    @Transactional
    void putNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, profile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDt(UPDATED_CREATED_DT)
            .modifiedDt(UPDATED_MODIFIED_DT);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProfile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProfile.getAvatarPath()).isEqualTo(DEFAULT_AVATAR_PATH);
        assertThat(testProfile.getPhotoPath()).isEqualTo(DEFAULT_PHOTO_PATH);
        assertThat(testProfile.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfile.getGithub()).isEqualTo(DEFAULT_GITHUB);
        assertThat(testProfile.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testProfile.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProfile.getCreatedDt()).isEqualTo(UPDATED_CREATED_DT);
        assertThat(testProfile.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testProfile.getModifiedDt()).isEqualTo(UPDATED_MODIFIED_DT);
    }

    @Test
    @Transactional
    void fullUpdateProfileWithPatch() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile using partial update
        Profile partialUpdatedProfile = new Profile();
        partialUpdatedProfile.setId(profile.getId());

        partialUpdatedProfile
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS)
            .avatarPath(UPDATED_AVATAR_PATH)
            .photoPath(UPDATED_PHOTO_PATH)
            .description(UPDATED_DESCRIPTION)
            .email(UPDATED_EMAIL)
            .github(UPDATED_GITHUB)
            .linkedin(UPDATED_LINKEDIN)
            .twitter(UPDATED_TWITTER)
            .createdBy(UPDATED_CREATED_BY)
            .createdDt(UPDATED_CREATED_DT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDt(UPDATED_MODIFIED_DT);

        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProfile))
            )
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProfile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProfile.getAvatarPath()).isEqualTo(UPDATED_AVATAR_PATH);
        assertThat(testProfile.getPhotoPath()).isEqualTo(UPDATED_PHOTO_PATH);
        assertThat(testProfile.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfile.getGithub()).isEqualTo(UPDATED_GITHUB);
        assertThat(testProfile.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testProfile.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testProfile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProfile.getCreatedDt()).isEqualTo(UPDATED_CREATED_DT);
        assertThat(testProfile.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testProfile.getModifiedDt()).isEqualTo(UPDATED_MODIFIED_DT);
    }

    @Test
    @Transactional
    void patchNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, profile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(profile))
            )
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();
        profile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, profile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
