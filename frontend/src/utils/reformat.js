const formatByDate = (data, seperator = '-') => {
  return data.reduce((acc, cur) => {
    const { id, attributes } = cur;
    const [year, month, day] = attributes.displayData.split(seperator);
    const newData = { id, ...attributes };
    if (!acc[year]) {
      acc[year] = {
        [month]: [newData],
      };
    } else if (!acc[year][month]) {
      acc[year][month] = [newData];
    } else {
      acc[year][month].push(newData);
    }

    return acc;
  }, {});
};
