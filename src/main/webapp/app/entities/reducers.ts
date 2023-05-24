import profile from 'app/entities/profile/profile.reducer';
import project from 'app/entities/project/project.reducer';
import skill from 'app/entities/skill/skill.reducer';
import skillCategory from 'app/entities/skill-category/skill-category.reducer';
import career from 'app/entities/career/career.reducer';
import interest from 'app/entities/interest/interest.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  profile,
  project,
  skill,
  skillCategory,
  career,
  interest,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
