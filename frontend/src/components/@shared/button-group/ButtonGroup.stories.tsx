import { type Story } from '@storybook/react';

import { BoxButton } from '@shared/button';
import ButtonGroup, { type ButtonGroupProps } from '@shared/button-group/ButtonGroup';

export default {
  title: 'Components/ButtonGroup',
  component: ButtonGroup,
};

const Template: Story<ButtonGroupProps> = props => (
  <ButtonGroup {...props}>
    <BoxButton type="button">hi</BoxButton>
    <BoxButton type="button" fluid variant="secondary">
      hi
    </BoxButton>
    <BoxButton type="button" disabled>
      hi
    </BoxButton>
  </ButtonGroup>
);

export const Default = Template.bind({});
Default.args = {
  orientation: 'vertical',
  gap: '8px',
  custom: {
    width: '200px',
    height: '100px',
  },
};
Default.parameters = { controls: { exclude: ['children'] } };
