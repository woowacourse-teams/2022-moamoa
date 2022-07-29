export const getToday = (seperator: '-' | '.') => {
  const koKRDate = new Date().toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' });
  return koKRDate.replaceAll(' ', '').replaceAll(/\./g, seperator).slice(0, -1);
};

export const getNextYear = (today: string, seperator: '-' | '.') => {
  const [year, month, day] = today.split(seperator);
  return `${Number(year) + 1}${seperator}${month}${seperator}${day}`;
};

export const yyyymmddTommdd = (date: string, seperator = '-') => {
  const arr = date.split(seperator);
  if (arr.length !== 3) throw new Error('날짜 형식이 올바르지 않습니다');
  const [m, d] = arr.slice(1, 3);
  return `${m}월 ${d}일`;
};

export const changeDateSeperator = (date: string, fromSeperator = '-', toSeperator = '.') => {
  return date.replaceAll(fromSeperator, toSeperator);
};
