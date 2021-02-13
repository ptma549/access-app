import { ICounty } from 'app/shared/model/county.model';
import { IJob } from 'app/shared/model/job.model';

export interface IClient {
  id?: number;
  name?: string;
  address?: string;
  postcode?: string;
  telephone?: string;
  fax?: string;
  counties?: ICounty[];
  jobs?: IJob[];
  accountId?: number;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public postcode?: string,
    public telephone?: string,
    public fax?: string,
    public counties?: ICounty[],
    public jobs?: IJob[],
    public accountId?: number
  ) {}
}
