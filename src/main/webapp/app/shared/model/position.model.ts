export interface IPosition {
  id?: number;
  value?: string;
  numberId?: number;
}

export class Position implements IPosition {
  constructor(public id?: number, public value?: string, public numberId?: number) {}
}
