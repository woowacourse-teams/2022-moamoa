import type { DateYMD } from '@custom-types';

export const getToday = (seperator: '-' | '.') => {
  const koKRDate = new Date().toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' });
  return koKRDate.replaceAll(' ', '').replaceAll(/\./g, seperator).slice(0, -1);
};

export const getNextYear = (today: string, seperator: '-' | '.') => {
  const [year, month, day] = today.split(seperator);
  return `${Number(year) + 1}${seperator}${month}${seperator}${day}`;
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

export const changeDateSeperator = (date: string, fromSeperator = '-', toSeperator = '.') => {
  return date.replaceAll(fromSeperator, toSeperator);
};

export const compareDateTime = (date1: DateYMD, date2: DateYMD, returnCondition: 'min' | 'max' = 'min'): DateYMD => {
  const date1ToNum = Number(date1.replaceAll('-', ''));
  const date2ToNum = Number(date2.replaceAll('-', ''));

  if (returnCondition === 'min') return date1ToNum < date2ToNum ? date1 : date2;
  return date1ToNum < date2ToNum ? date2 : date1;
};
