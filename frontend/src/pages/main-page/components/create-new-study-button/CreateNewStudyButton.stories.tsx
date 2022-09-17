import type { Story } from '@storybook/react';

import CreateNewStudyButton, {
  type CreateNewStudyButtonProps,
} from '@main-page/components/create-new-study-button/CreateNewStudyButton';

export default {
  title: 'Pages/MainPage/CreateNewStudyButton',
  component: CreateNewStudyButton,
};

const Template: Story<CreateNewStudyButtonProps> = props => <CreateNewStudyButton {...props} />;

export const Default = Template.bind({});
Default.args = {};
Default.parameters = { controls: { exclude: ['onClick'] } };
