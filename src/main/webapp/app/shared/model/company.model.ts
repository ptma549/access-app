export interface ICompany {
  id?: number;
  address?: string;
  telephone?: string;
  fax?: string;
}

export class Company implements ICompany {
  constructor(public id?: number, public address?: string, public telephone?: string, public fax?: string) {}
}
