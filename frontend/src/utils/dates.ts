export const getToday = (seperator: '-' | '.') => {
  const koKRDate = new Date().toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' });
  return koKRDate.replaceAll(' ', '').replaceAll(/\./g, seperator).slice(0, -1);
};

export const getNextYear = (today: string, seperator: '-' | '.') => {
  const [year, month, day] = today.split(seperator);
  return `${Number(year) + 1}${seperator}${month}${seperator}${day}`;
};
