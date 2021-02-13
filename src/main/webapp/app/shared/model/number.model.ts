import { IPosition } from 'app/shared/model/position.model';

export interface INumber {
  id?: number;
  value?: string;
  building?: string;
  postcode?: string;
  positions?: IPosition[];
  streetId?: number;
}

export class Number implements INumber {
  constructor(
    public id?: number,
    public value?: string,
    public building?: string,
    public postcode?: string,
    public positions?: IPosition[],
    public streetId?: number
  ) {}
}
