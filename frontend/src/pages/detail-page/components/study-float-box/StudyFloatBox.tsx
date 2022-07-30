import { yyyymmddTommdd } from '@utils/index';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-float-box/StudyFloatBox.style';

export type StudyFloatBoxProps = {
  deadline: string;
  currentMemberCount: number;
  maxMemberCount: number;
  owner: string;
  status: 'OPEN' | 'CLOSE'; // TODO: 스터디에 가입한 사람인지 아닌지 상태도 받아야 함
  handleRegisterBtnClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  deadline,
  currentMemberCount,
  maxMemberCount,
  owner,
  status,
  handleRegisterBtnClick,
}) => {
  const isOpen = status === 'OPEN';

  return (
    <S.StudyFloatBox>
      <S.StudyInfo>
        <S.Deadline>
          {isOpen ? (
            <>
              <span>{yyyymmddTommdd(deadline)}</span>
              까지 가입 가능
            </>
          ) : (
            <span>모집 마감</span>
          )}
        </S.Deadline>
        <S.MemberCount>
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount}
          </span>
        </S.MemberCount>
        <S.Owner>
          <span>스터디장</span>
          <span>{owner}</span>
        </S.Owner>
      </S.StudyInfo>
      <Button disabled={!isOpen} onClick={handleRegisterBtnClick}>
        {isOpen ? '스터디 가입하기' : '모집이 마감되었습니다'}
      </Button>
    </S.StudyFloatBox>
  );
};

export default StudyFloatBox;
