import { IProfile } from 'app/shared/model/profile.model';
import { ISkillCategory } from 'app/shared/model/skill-category.model';
import { SkillLevel } from 'app/shared/model/enumerations/skill-level.model';

export interface ISkill {
  id?: number;
  level?: SkillLevel;
  skill?: string | null;
  description?: string | null;
  profile?: IProfile | null;
  skillCategory?: ISkillCategory | null;
}

export const defaultValue: Readonly<ISkill> = {};
