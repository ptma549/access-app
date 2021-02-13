export interface IJobLine {
  id?: number;
  material?: string;
  quantity?: number;
  unitCost?: number;
  jobId?: number;
}

export class JobLine implements IJobLine {
  constructor(public id?: number, public material?: string, public quantity?: number, public unitCost?: number, public jobId?: number) {}
}
