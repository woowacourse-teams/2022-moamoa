import type { Story } from '@storybook/react';

import Footer from '@layout/footer/Footer';
import type { FooterProps } from '@layout/footer/Footer';

export default {
  title: 'Layout/Footer',
  component: Footer,
};

const Template: Story<FooterProps> = props => <Footer {...props} />;

export const Default = Template.bind({});
Default.args = {};
