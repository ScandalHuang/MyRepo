import { InMemoryDbService } from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const records = [
      {
        id: 0,
        time: 20180511,
        content: 'punch',
        employee: 'Scandal Huang'
      },
      {
        id: 1,
        time: 20180512,
        content: 'punch',
        employee: 'Scandal Li'
      },
      {
        id: 2,
        time: 20180513,
        content: 'punch',
        employee: 'Scandal Wang'
      },
      {
        id: 3,
        time: 20180514,
        content: 'punch',
        employee: 'scandal Chen'
      }
    ];
    return { records };
  }
}
