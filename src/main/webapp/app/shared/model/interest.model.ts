import { IProfile } from 'app/shared/model/profile.model';

export interface IInterest {
  id?: number;
  title?: string;
  imagePath?: string | null;
  description?: string | null;
  profile?: IProfile | null;
}

export const defaultValue: Readonly<IInterest> = {};
