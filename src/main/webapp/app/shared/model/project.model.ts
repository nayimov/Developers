import { IProfile } from 'app/shared/model/profile.model';

export interface IProject {
  id?: number;
  name?: string;
  url?: string;
  profile?: IProfile | null;
}

export const defaultValue: Readonly<IProject> = {};
