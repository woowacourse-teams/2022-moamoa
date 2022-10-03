import { Link } from 'react-router-dom';

import { Theme } from '@emotion/react';

import { PATH } from '@constants';

import { yyyymmddTommdd } from '@utils';
import tw from '@utils/tw';

import type { StudyDetail, StudyId, UserRole } from '@custom-types';

import { theme } from '@styles/theme';

import { BoxButton } from '@components/button';
import { BoxButtonProps } from '@components/button/box-button/BoxButton';
import Card from '@components/card/Card';
import Flex from '@components/flex/Flex';

export type StudyWideFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  userRole?: UserRole;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  studyId,
  userRole,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const isOpen = recruitmentStatus === 'RECRUITMENT_START';

  const renderEnrollmentEndDateContent = () => {
    if (userRole === 'MEMBER' || userRole === 'OWNER') {
      return <span>이미 가입한 스터디입니다</span>;
    }

    if (!isOpen) {
      return <span>모집 마감</span>;
    }

    if (!enrollmentEndDate) {
      return <span>모집중</span>;
    }

    return (
      <>
        <span>{yyyymmddTommdd(enrollmentEndDate)}</span>까지 가입 가능
      </>
    );
  };

  return (
    <Card backgroundColor={theme.colors.white} padding="20px" shadow>
      <Flex justifyContent="space-between" alignItems="center">
        <div>
          <Card.Heading custom={{ fontSize: theme.fontSize.xl }}>{renderEnrollmentEndDateContent()}</Card.Heading>
          <Card.Content custom={{ fontSize: theme.fontSize.md }} maxLine={1}>
            <span css={tw`mr-16`}>모집인원</span>
            <span>
              {currentMemberCount} / {maxMemberCount ?? '∞'}
            </span>
          </Card.Content>
        </div>
        {userRole === 'MEMBER' || userRole === 'OWNER' ? (
          <GoToStudyRoomLinkButton theme={theme} studyId={studyId} />
        ) : (
          <RegisterButton theme={theme} disabled={!isOpen} onClick={handleRegisterButtonClick} />
        )}
      </Flex>
    </Card>
  );
};

type GoToStudyRoomLinkButtonProps = {
  theme: Theme;
  studyId: StudyId;
};
const GoToStudyRoomLinkButton: React.FC<GoToStudyRoomLinkButtonProps> = ({ theme, studyId }) => (
  <Link to={PATH.STUDY_ROOM(studyId)}>
    <BoxButton type="button" custom={{ fontSize: theme.fontSize.lg }}>
      스터디 방으로 이동하기
    </BoxButton>
  </Link>
);

type RegisterButtonProps = { theme: Theme } & Pick<BoxButtonProps, 'disabled' | 'onClick'>;
const RegisterButton: React.FC<RegisterButtonProps> = ({ theme, disabled: isOpen, onClick: handleClick }) => (
  <BoxButton type="submit" disabled={!isOpen} onClick={handleClick} custom={{ fontSize: theme.fontSize.lg }}>
    {isOpen ? '스터디 가입하기' : '모집이 마감되었습니다'}
  </BoxButton>
);

export default StudyWideFloatBox;
