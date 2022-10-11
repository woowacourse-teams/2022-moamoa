/* How to use:
 * type BreakPoints = 'xs' | 'sm' | 'md';
 * const arrayOfAllBreakPoints = arrayOfAll<BreakPoints>();
 * const wrongBreakPoints = arrayOfAllBreakPoints(['xs', 'sm']); // Error
 * const breakPoints = arrayOfAllBreakPoints(['xs', 'sm', 'md']); // Ok
 */

const arrayOfAll =
  <T>() =>
  <U extends T[]>(array: U & ([T] extends [U[number]] ? unknown : 'Invalid')) =>
    array;

export default arrayOfAll;
