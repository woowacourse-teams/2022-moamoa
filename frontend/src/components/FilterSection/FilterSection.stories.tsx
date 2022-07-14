import { Story } from '@storybook/react';

import FilterSection from '@components/FilterSection/FilterSection';

export default {
  title: 'Components/FilterSection',
  component: FilterSection,
};

const Template: Story = () => {
  return <FilterSection />;
};

export const Default = Template.bind({});
