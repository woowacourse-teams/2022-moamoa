package com.woowacourse.moamoa.study.domain;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.woowacourse.moamoa.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Study {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String status;
    private String description;

    @Embedded
    private StudyMemberInfo studyMemberInfo;

    @Embedded
    private StudyManagementDate studyManagementDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Member owner;

    @ElementCollection
    @CollectionTable(name = "study_member", joinColumns = @JoinColumn(name = "study_id"))
    private List<Participant> participants = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "study_tag", joinColumns = @JoinColumn(name = "study_id"))
    private List<AttachedTag> attachedTags = new ArrayList<>();

    public Study(final Long id, final String title, final String excerpt,
                 final String thumbnail, final String status
    ) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.thumbnail = thumbnail;
        this.status = status;
    }
}
