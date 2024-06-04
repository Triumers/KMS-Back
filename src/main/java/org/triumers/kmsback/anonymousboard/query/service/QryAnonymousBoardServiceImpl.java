package org.triumers.kmsback.anonymousboard.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.query.repository.QryAnonymousBoardMapper;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QryAnonymousBoardServiceImpl implements QryAnonymousBoardService {

    private final QryAnonymousBoardMapper qryAnonymousBoardMapper;

    @Autowired
    public QryAnonymousBoardServiceImpl(QryAnonymousBoardMapper qryAnonymousBoardMapper) {
        this.qryAnonymousBoardMapper = qryAnonymousBoardMapper;
    }

    @Override
    public Page<QryAnonymousBoardDTO> findAllAnonymousBoard(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        Pageable adjustedPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
        List<QryAnonymousBoardDTO> anonymousBoardList = qryAnonymousBoardMapper.findAllAnonymousBoard(adjustedPageable);
        long total = qryAnonymousBoardMapper.countAllAnonymousBoard();
        return new PageImpl<>(anonymousBoardList, adjustedPageable, total);
    }

    @Override
    public Page<QryAnonymousBoardDTO> searchAnonymousBoard(String keyword, String type, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        Pageable adjustedPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
        List<QryAnonymousBoardDTO> anonymousBoardList;
        long total;
        switch (type.toLowerCase()) {
            case "title":
                anonymousBoardList = qryAnonymousBoardMapper.searchAnonymousBoardByTitle(keyword, adjustedPageable);
                total = qryAnonymousBoardMapper.countAnonymousBoardByTitle(keyword);
                break;
            case "content":
                anonymousBoardList = qryAnonymousBoardMapper.searchAnonymousBoardByContent(keyword, adjustedPageable);
                total = qryAnonymousBoardMapper.countAnonymousBoardByContent(keyword);
                break;
            case "titleandcontent":
                anonymousBoardList = qryAnonymousBoardMapper.searchAnonymousBoardByTitleAndContent(keyword, adjustedPageable);
                total = qryAnonymousBoardMapper.countAnonymousBoardByTitleAndContent(keyword);
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + type);
        }
        return new PageImpl<>(anonymousBoardList, adjustedPageable, total);
    }

    @Override
    public QryAnonymousBoardDTO findAnonymousBoardById(int id) {
        QryAnonymousBoardDTO anonymousBoard = qryAnonymousBoardMapper.findAnonymousBoardById(id);
        if (anonymousBoard == null) {
            throw new NoSuchElementException("Anonymous board not found with id: " + id);
        }
        return anonymousBoard;
    }
}