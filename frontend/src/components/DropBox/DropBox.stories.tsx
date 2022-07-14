import { Story } from '@storybook/react';
import { useState } from 'react';

import CheckListItem from '@components/CheckListItem/CheckListItem';
import type { DropBoxProps } from '@components/DropBox/DropBox';
import DropBox from '@components/DropBox/DropBox';
import type { TagItem } from '@components/Filter/Filter';

export default {
  title: 'Components/DropBox',
  component: DropBox,
};

export const filters: Array<TagItem & { isChecked: boolean }> = [
  { id: 1, tagName: 'FE', isChecked: true },
  { id: 2, tagName: 'BE', isChecked: false },
  { id: 3, tagName: '4기', isChecked: true },
  { id: 4, tagName: '1기', isChecked: false },
  { id: 5, tagName: 'Java', isChecked: false },
  { id: 6, tagName: 'JavaScript', isChecked: true },
  { id: 7, tagName: 'Spring', isChecked: false },
  { id: 8, tagName: 'React', isChecked: false },
  { id: 9, tagName: 'FE', isChecked: false },
  { id: 10, tagName: 'FE', isChecked: false },
];

const Template: Story<DropBoxProps> = () => {
  const [open, setOpen] = useState(false);

  return (
    <>
      <button
        type="button"
        onClick={() => {
          setOpen(prev => !prev);
        }}
      >
        클릭시 아래에 열림
      </button>
      {open && (
        <DropBox>
          {filters.map(({ id, tagName, isChecked }) => (
            <li key={id}>
              <CheckListItem isChecked={isChecked}>{tagName}</CheckListItem>
            </li>
          ))}
        </DropBox>
      )}
    </>
  );
};

export const Default = Template.bind({});
