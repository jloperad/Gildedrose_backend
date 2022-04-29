export interface Item {
  id: number;
  name: string;
  sellIn: number;
  quality: number;
  type: Types;
}

export enum Types {
  AGED = 'AGED',
  NORMAL = 'NORMAL',
  LEGENDARY = 'LEGENDARY',
  TICKETS = 'TICKETS',
}
