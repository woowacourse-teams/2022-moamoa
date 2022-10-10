import { Link } from 'react-router-dom';

import { PATH, RECRUITMENT_STATUS, USER_ROLE } from '@constants';

import { yyyymmddTommdd } from '@utils';
import tw from '@utils/tw';

import type { StudyDetail, UserRole } from '@custom-types';

import { theme } from '@styles/theme';

import { BoxButton } from '@components/button';
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
  const isOpen = recruitmentStatus === RECRUITMENT_STATUS.START;

  const renderEnrollmentEndDateContent = () => {
    if (userRole === USER_ROLE.MEMBER || userRole === USER_ROLE.OWNER) {
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

  const renderButton = () => {
    if (userRole === USER_ROLE.MEMBER || userRole === USER_ROLE.OWNER) {
      return (
        <Link to={PATH.STUDY_ROOM(studyId)}>
          <BoxButton type="button" fontSize="lg" fluid>
            이동하기
          </BoxButton>
        </Link>
      );
    }

    return (
      <BoxButton type="submit" fontSize="lg" fluid disabled={!isOpen} onClick={handleRegisterButtonClick}>
        {isOpen ? '가입하기' : '모집 마감'}
      </BoxButton>
    );
  };

  return (
    <Card backgroundColor={theme.colors.white} padding="20px" shadow>
      <Flex justifyContent="space-between" alignItems="center">
        <div>
          <Card.Heading fontSize="xl">{renderEnrollmentEndDateContent()}</Card.Heading>
          <Card.Content fontSize="md" maxLine={1}>
            <span css={tw`mr-16`}>모집인원</span>
            <span>
              {currentMemberCount} / {maxMemberCount ?? '∞'}
            </span>
          </Card.Content>
        </div>
        <div>{renderButton()}</div>
      </Flex>
    </Card>
  );
};

export default StudyWideFloatBox;
