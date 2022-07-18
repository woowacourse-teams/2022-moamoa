import { Story } from '@storybook/react';

import Footer from '@layout/footer/Footer';

export default {
  title: 'Components/Footer',
  component: Footer,
};

const Template: Story = () => <Footer />;

export const Default = Template.bind({});
Default.args = {};
