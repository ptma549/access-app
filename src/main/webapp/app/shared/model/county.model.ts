import { ITown } from 'app/shared/model/town.model';

export interface ICounty {
  id?: number;
  name?: string;
  towns?: ITown[];
  clientId?: number;
}

export class County implements ICounty {
  constructor(public id?: number, public name?: string, public towns?: ITown[], public clientId?: number) {}
}
