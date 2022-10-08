import { type Story } from '@storybook/react';

import { type StudyDetail } from '@custom-types';

import StudyMemberSection, {
  type StudyMemberSectionProps,
} from '@detail-page/components/study-member-section/StudyMemberSection';

export default {
  title: 'Pages/DetailPage/StudyMemberSection',
  component: StudyMemberSection,
};

const owner: StudyDetail['owner'] = {
  id: 1,
  username: 'nan-noo',
  profileUrl: '/',
  imageUrl:
    'https://images.unsplash.com/photo-1554151228-14d9def656e4?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
  participationDate: '2022-08-18',
  numberOfStudy: 1,
};

const members: StudyDetail['members'] = [
  {
    id: 9640683,
    username: 'KRlzeg',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 753744,
    username: 'UJPqG7',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 3556702,
    username: 'BtAv_0',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 3761959,
    username: 'G6Ul2n',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 5579984,
    username: 'bqQNzB',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 6340085,
    username: 'MpyBSk',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 3639124,
    username: 'Xe5KS1',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 7768553,
    username: 'QO_j7i',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 7809398,
    username: '_r_Sd4',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 6813815,
    username: '7lzhVG',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 3692005,
    username: 'pdPBWa',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
  {
    id: 6073472,
    username: '3gthUz',
    imageUrl:
      'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
    profileUrl: 'https://github.com/airman5573',
    participationDate: '2022-08-18',
    numberOfStudy: 1,
  },
];

const Template: Story<StudyMemberSectionProps> = props => (
  <div style={{ maxWidth: '400px', minWidth: 'fit-content' }}>
    <StudyMemberSection {...props} />
  </div>
);

export const Default = Template.bind({});
Default.args = { owner, members };
