import type { Story } from '@storybook/react';

import { FormProvider, useForm } from '@hooks/useForm';

import DescriptionTab from '@create-study-page/components/description-tab/DescriptionTab';

export default {
  title: 'Components/DescriptionTab',
  component: DescriptionTab,
};

const Template: Story = props => {
  const formMethods = useForm();
  return (
    <FormProvider {...formMethods}>
      <DescriptionTab {...props} />
    </FormProvider>
  );
};

export const Default = Template.bind({});
Default.args = {};
