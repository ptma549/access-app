import { Moment } from 'moment';

export interface IJobVisit {
  id?: number;
  arrived?: Moment;
  departed?: Moment;
  charge?: number;
  workCarriedOut?: string;
  jobId?: number;
}

export class JobVisit implements IJobVisit {
  constructor(
    public id?: number,
    public arrived?: Moment,
    public departed?: Moment,
    public charge?: number,
    public workCarriedOut?: string,
    public jobId?: number
  ) {}
}
