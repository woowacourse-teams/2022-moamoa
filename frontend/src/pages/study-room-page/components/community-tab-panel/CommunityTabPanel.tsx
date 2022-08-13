import { useState } from 'react';

import Avatar from '@components/avatar/Avatar';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@study-room-page/components/community-tab-panel/CommunityTabPanel.style';
import useGetCommunityArticles from '@study-room-page/components/community-tab-panel/hooks/useGetCommunityAticles';

type CommunityTabPanelProps = {
  studyId: number;
};

const CommunityTabPanel: React.FC<CommunityTabPanelProps> = ({ studyId }) => {
  const [page, setPage] = useState<number>(0);
  const { isFetching, data } = useGetCommunityArticles(studyId, page);

  return (
    <Wrapper>
      <S.CommunityTabPanel>
        <S.Board>
          <ul>
            <S.ListItem>
              <S.Main>
                <S.Title>테스트글 써봅니다</S.Title>
                <S.Content>이것은 테스트 게시글 입니다</S.Content>
              </S.Main>
              <S.MoreInfo>
                <S.Author>
                  <Avatar
                    size="xs"
                    profileImg="https://secure.gravatar.com/avatar/b6f842a419522e8965a624fbf9680013?s=20&d=mm&r=g"
                    profileAlt="airman5573"
                  ></Avatar>
                  <S.Username>airman5573</S.Username>
                </S.Author>
                <S.Date>
                  <div>작성일</div>
                  <div>2020.09.26</div>
                </S.Date>
              </S.MoreInfo>
            </S.ListItem>
          </ul>
        </S.Board>
      </S.CommunityTabPanel>
    </Wrapper>
  );
};

export default CommunityTabPanel;
