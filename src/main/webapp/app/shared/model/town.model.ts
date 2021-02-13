import { IStreet } from 'app/shared/model/street.model';

export interface ITown {
  id?: number;
  value?: string;
  streets?: IStreet[];
  countyId?: number;
}

export class Town implements ITown {
  constructor(public id?: number, public value?: string, public streets?: IStreet[], public countyId?: number) {}
}
