const yyyymmddTommdd = (date: string) => {
  const arr = date.split('-');
  if (arr.length !== 3) throw new Error('날짜 형식이 올바르지 않습니다');
  const [m, d] = arr.slice(1, 3);
  return `${m}월 ${d}일`;
};

export default yyyymmddTommdd;
