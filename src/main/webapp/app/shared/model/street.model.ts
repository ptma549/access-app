import { INumber } from 'app/shared/model/number.model';

export interface IStreet {
  id?: number;
  value?: string;
  numbers?: INumber[];
  townId?: number;
}

export class Street implements IStreet {
  constructor(public id?: number, public value?: string, public numbers?: INumber[], public townId?: number) {}
}
