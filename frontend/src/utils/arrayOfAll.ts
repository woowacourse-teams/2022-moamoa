const arrayOfAll =
  <T>() =>
  <U extends T[]>(array: U & ([T] extends [U[number]] ? unknown : 'Invalid')) =>
    array;

export default arrayOfAll;
