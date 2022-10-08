import { type Story } from '@storybook/react';
import DescriptionTab from '@study-page/components/description-tab/DescriptionTab';

import { FormProvider, useForm } from '@hooks/useForm';

export default {
  title: 'Pages/CreateStudyPage/DescriptionTab',
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
