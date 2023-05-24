import { IProfile } from 'app/shared/model/profile.model';

export interface ICareer {
  id?: number;
  step?: number;
  description?: string;
  imagePath?: string | null;
  synopsis?: string | null;
  profile?: IProfile | null;
}

export const defaultValue: Readonly<ICareer> = {};
