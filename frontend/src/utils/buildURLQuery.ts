const isValidEntry = (entry: [string, unknown]): entry is [string, string | number] => {
  const val = entry[1];
  return (typeof val === 'string' && val.length > 0) || typeof val === 'number';
};

const buildURLQuery = (baseURL: string, obj: Record<string, unknown>) => {
  const validEntries = Object.entries(obj).filter<[string, string | number]>(isValidEntry);
  return `${baseURL}?${validEntries.map(pair => pair.map(encodeURIComponent).join('=')).join('&')}`;
};

export default buildURLQuery;
