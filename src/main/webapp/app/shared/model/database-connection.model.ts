export interface IDatabaseConnection {
  id?: number;
  connectionName?: string;
  host?: string;
  port?: string;
  databaseName?: string;
  params?: string;
  type?: string;
  driver?: string;
  url?: string;
  username?: string;
  password?: string;
  test?: boolean;
}

export class DatabaseConnection implements IDatabaseConnection {
  constructor(
    public id?: number,
    public connectionName?: string,
    public host?: string,
    public port?: string,
    public databaseName?: string,
    public params?: string,
    public type?: string,
    public driver?: string,
    public url?: string,
    public username?: string,
    public password?: string,
    public test?: boolean
  ) {
    this.test = this.test || false;
  }
}
