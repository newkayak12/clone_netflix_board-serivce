package com.netflix_clone.boardservice.repository.commentRepository;

import com.netflix_clone.boardservice.repository.domains.Comment;
import com.netflix_clone.boardservice.repository.dto.reference.CommentDto;
import com.netflix_clone.boardservice.repository.dto.reference.PageableRequest;
import com.netflix_clone.boardservice.repository.dto.reference.QCommentDto;
import com.netflix_clone.boardservice.repository.dto.reference.QProfileDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

import static com.netflix_clone.boardservice.repository.domains.QBoardProfile.boardProfile;
import static com.netflix_clone.boardservice.repository.domains.QComment.comment;
import static com.netflix_clone.boardservice.repository.domains.QReport.report;

public class CommentRepositoryImpl extends QuerydslRepositorySupport implements CommentRepositoryCustom {
    private JPQLQueryFactory query;

    public CommentRepositoryImpl(JPQLQueryFactory query) {
        super(Comment.class);
        this.query = query;
    }

    @Override
    public PageImpl<CommentDto> comments(Pageable request, PageableRequest parameter) {
        List<CommentDto> list = query.select(
                new QCommentDto(
                        comment.commentNo,
                        new QProfileDto(
                                boardProfile.profileNo,
                                boardProfile.profileName
                        ),
                        comment.contentsNo,
                        comment.comments,
                        comment.star,
                        comment.regDate,
                        comment.modifyDate
                )
        )
        .from(comment)
        .leftJoin(boardProfile).on(comment.profile.profileNo.eq(boardProfile.profileNo))
        .leftJoin(report).on(report.comment.profile.profileNo.eq(comment.profile.profileNo))
        .where(
                report.reportNo.count().loe(0)
            .and(comment.contentsNo.eq(parameter.getTableNo()))
        )
        .limit(request.getPageSize())
        .offset(request.getOffset())
        .orderBy(comment.commentNo.desc())
        .groupBy(comment.commentNo)
        .fetch();


        Long count = from(comment).fetchCount();
        return new PageImpl<CommentDto>(list, request, count);
    }

    @Override
    public CommentDto comment(Long commentNo) {
        return query.select(
                    new QCommentDto(
                            comment.commentNo,
                            new QProfileDto(
                                    boardProfile.profileNo,
                                    boardProfile.profileName
                            ),
                            comment.contentsNo,
                            comment.comments,
                            comment.star,
                            comment.regDate,
                            comment.modifyDate
                    )
                )
                .from(comment)
                .leftJoin(boardProfile).on(boardProfile.profileNo.eq(comment.profile.profileNo))
                .fetchOne();
    }

    @Override
    public Boolean remove(Long commentNo) {
        return query.delete(comment).where(comment.commentNo.eq(commentNo)).execute() > 0;
    }
}
