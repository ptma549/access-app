export interface IPriority {
  id?: number;
  value?: string;
}

export class Priority implements IPriority {
  constructor(public id?: number, public value?: string) {}
}
