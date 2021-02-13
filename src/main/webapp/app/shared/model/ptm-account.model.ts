import { IClient } from 'app/shared/model/client.model';

export interface IPtmAccount {
  id?: number;
  name?: string;
  colour?: string;
  clients?: IClient[];
}

export class PtmAccount implements IPtmAccount {
  constructor(public id?: number, public name?: string, public colour?: string, public clients?: IClient[]) {}
}
