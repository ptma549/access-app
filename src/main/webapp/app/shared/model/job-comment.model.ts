export interface IJobComment {
  id?: number;
  comment?: string;
  jobId?: number;
}

export class JobComment implements IJobComment {
  constructor(public id?: number, public comment?: string, public jobId?: number) {}
}
