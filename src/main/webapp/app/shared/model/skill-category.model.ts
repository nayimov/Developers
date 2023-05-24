import { ISkill } from 'app/shared/model/skill.model';

export interface ISkillCategory {
  id?: number;
  category?: string;
  skills?: ISkill[] | null;
}

export const defaultValue: Readonly<ISkillCategory> = {};
