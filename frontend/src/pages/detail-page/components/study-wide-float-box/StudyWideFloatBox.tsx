import { css } from '@emotion/react';

import yyyymmddTommdd from '@utils/yyyymmddTommdd';

import Button from '@components/button/Button';

import * as S from '@detail-page/components/study-wide-float-box/StudyWideFloatBox.style';

export type StudyWideFloatBoxProps = {
  studyId: number;
  deadline: string;
  currentMemberCount: number;
  maxMemberCount: number;
  owner: string;
  handleRegisterBtnClick: (studyId: number) => React.MouseEventHandler<HTMLButtonElement>;
};

const StudyWideFloatBox: React.FC<StudyWideFloatBoxProps> = ({
  studyId,
  deadline,
  currentMemberCount,
  maxMemberCount,
  owner,
  handleRegisterBtnClick,
}) => {
  return (
    <S.StudyWideFloatBox>
      <S.StudyInfo>
        <S.Deadline>
          <span>{yyyymmddTommdd(deadline)}</span>
          까지 가입 가능
        </S.Deadline>
        <S.ExtraInfo>
          <S.MemberCount>
            <span>모집인원</span>
            <span>
              {currentMemberCount} / {maxMemberCount}
            </span>
          </S.MemberCount>
        </S.ExtraInfo>
      </S.StudyInfo>
      <div>
        <Button
          css={css`
            height: 100%;
            padding: 0 20px;
          `}
          fluid={true}
          onClick={handleRegisterBtnClick(studyId)}
        >
          가입
        </Button>
      </div>
    </S.StudyWideFloatBox>
  );
};

export default StudyWideFloatBox;
