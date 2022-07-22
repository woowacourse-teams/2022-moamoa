const getToday = () => {
  const koKRDate = new Date().toLocaleDateString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' });
  return koKRDate.replaceAll(' ', '').replaceAll(/\./g, '-').slice(0, -1);
};

export default getToday;
