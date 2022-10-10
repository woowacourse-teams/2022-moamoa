import type { DateYMD } from '@custom-types';

const isDateYMD = (date: string): date is DateYMD => {
  const regex = /\d{4}-\d{2}-\d{2}/;
  return regex.test(date);
};

export const getToday = (): DateYMD => {
  const koKRDate = new Date().toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' });
  const today = koKRDate.replaceAll(' ', '').replaceAll(/\./g, '-').slice(0, -1);
  if (!isDateYMD(today)) {
    throw new Error('getToday 오류 : 날짜 형식이 맞지않습니다');
  }
  return today;
};

export const getNextYear = (today: DateYMD): DateYMD => {
  const separator = '-';
  const [year, month, day] = today.split(separator);
  const nextYear = `${Number(year) + 1}${separator}${month}${separator}${day}`;

  if (!isDateYMD(nextYear)) {
    throw new Error('getNextYear 오류 : 날짜 형식이 맞지않습니다');
  }
  return nextYear;
};

export const yyyymmddTommdd = (date: DateYMD, seperator = '-') => {
  const arr = date.split(seperator);
  if (arr.length !== 3) {
    console.error('날짜 형식이 올바르지 않습니다');
    return '%%ERROR%%';
  }
  const [m, d] = arr.slice(1, 3);
  return `${m}월 ${d}일`;
};

export const changeDateSeperator = (date: DateYMD, fromSeperator = '-', toSeperator = '.') => {
  return date.replaceAll(fromSeperator, toSeperator);
};

export const compareDateTime = (date1: DateYMD, date2: DateYMD, returnCondition: 'min' | 'max' = 'min'): DateYMD => {
  const date1ToNum = Number(date1.replaceAll('-', ''));
  const date2ToNum = Number(date2.replaceAll('-', ''));

  if (returnCondition === 'min') return date1ToNum < date2ToNum ? date1 : date2;
  return date1ToNum < date2ToNum ? date2 : date1;
};
