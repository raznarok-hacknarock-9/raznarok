export class AppError extends Error {
  public code?: string;
  public status?: number;

  constructor(message: string, options?: ErrorOptions);
  constructor(message: string, code: string, options?: ErrorOptions);
  constructor(message: string, status: number, options?: ErrorOptions);
  constructor(
    message: string,
    code: string,
    status: number,
    options?: ErrorOptions,
  );

  constructor(
    message: string,
    arg2?: string | number | ErrorOptions,
    arg3?: number | ErrorOptions,
    arg4?: ErrorOptions,
  ) {
    if (typeof arg2 === 'string' && typeof arg3 === 'number') {
      super(message, arg4);
      this.code = arg2;
      this.status = arg3;
    } else if (typeof arg2 === 'string') {
      super(message, arg3 as ErrorOptions);
      this.code = arg2;
    } else if (arg2 === undefined || typeof arg2 === 'object') {
      super(message, arg2 as ErrorOptions);
    } else {
      super(message, arg3 as ErrorOptions);
      this.status = arg2;
    }
  }
}
