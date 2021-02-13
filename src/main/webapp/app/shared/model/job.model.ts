import { IJobComment } from 'app/shared/model/job-comment.model';
import { IJobLine } from 'app/shared/model/job-line.model';
import { IJobVisit } from 'app/shared/model/job-visit.model';

export interface IJob {
  id?: number;
  reportedBy?: string;
  clientOrderRef?: string;
  raiseDate?: string;
  priority?: string;
  fault?: string;
  instructions?: string;
  occupier?: string;
  homeTel?: string;
  workTel?: string;
  mobileTel?: string;
  tenantName?: string;
  complete?: string;
  position?: string;
  invoice?: string;
  invoiceDetails?: string;
  comments?: IJobComment[];
  lines?: IJobLine[];
  visits?: IJobVisit[];
  clientId?: number;
}

export class Job implements IJob {
  constructor(
    public id?: number,
    public reportedBy?: string,
    public clientOrderRef?: string,
    public raiseDate?: string,
    public priority?: string,
    public fault?: string,
    public instructions?: string,
    public occupier?: string,
    public homeTel?: string,
    public workTel?: string,
    public mobileTel?: string,
    public tenantName?: string,
    public complete?: string,
    public position?: string,
    public invoice?: string,
    public invoiceDetails?: string,
    public comments?: IJobComment[],
    public lines?: IJobLine[],
    public visits?: IJobVisit[],
    public clientId?: number
  ) {}
}
