import dayjs from 'dayjs';
import { IProject } from 'app/shared/model/project.model';
import { ISkill } from 'app/shared/model/skill.model';
import { ICareer } from 'app/shared/model/career.model';
import { IInterest } from 'app/shared/model/interest.model';
import { ProfileStatus } from 'app/shared/model/enumerations/profile-status.model';

export interface IProfile {
  id?: number;
  name?: string;
  location?: string | null;
  status?: ProfileStatus;
  avatarPath?: string | null;
  photoPath?: string | null;
  description?: string | null;
  email?: string;
  github?: string | null;
  linkedin?: string | null;
  twitter?: string | null;
  createdBy?: number;
  createdDt?: string;
  modifiedBy?: number | null;
  modifiedDt?: string | null;
  projects?: IProject[] | null;
  skills?: ISkill[] | null;
  careers?: ICareer[] | null;
  interests?: IInterest[] | null;
}

export const defaultValue: Readonly<IProfile> = {};
