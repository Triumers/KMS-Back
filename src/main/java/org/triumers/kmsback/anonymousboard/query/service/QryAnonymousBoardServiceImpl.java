package org.triumers.kmsback.anonymousboard.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.query.repository.QryAnonymousBoardMapper;

import java.util.List;

@Service
public class QryAnonymousBoardServiceImpl implements QryAnonymousBoardService {

    private final QryAnonymousBoardMapper qryAnonymousBoardMapper;

    @Autowired
    public QryAnonymousBoardServiceImpl(QryAnonymousBoardMapper qryAnonymousBoardMapper) {
        this.qryAnonymousBoardMapper = qryAnonymousBoardMapper;
    }

    @Override
    public Page<QryAnonymousBoardDTO> findAllAnonymousBoard(Pageable pageable) {
        List<QryAnonymousBoardDTO> anonymousBoardList = qryAnonymousBoardMapper.findAllAnonymousBoard(pageable);
        long total = qryAnonymousBoardMapper.countAllAnonymousBoard();
        return new PageImpl<>(anonymousBoardList, pageable, total);
    }

    @Override
    public Page<QryAnonymousBoardDTO> searchAnonymousBoard(String type, String keyword, Pageable pageable) {
        List<QryAnonymousBoardDTO> anonymousBoardList;
        long total;
        switch (type) {
            case "title":
                anonymousBoardList = qryAnonymousBoardMapper.searchAnonymousBoardByTitle(keyword, pageable);
                total = qryAnonymousBoardMapper.countAnonymousBoardByTitle(keyword);
                break;
            case "content":
                anonymousBoardList = qryAnonymousBoardMapper.searchAnonymousBoardByContent(keyword, pageable);
                total = qryAnonymousBoardMapper.countAnonymousBoardByContent(keyword);
                break;
            case "titleAndContent":
                anonymousBoardList = qryAnonymousBoardMapper.searchAnonymousBoardByTitleAndContent(keyword, pageable);
                total = qryAnonymousBoardMapper.countAnonymousBoardByTitleAndContent(keyword);
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + type);
        }
        return new PageImpl<>(anonymousBoardList, pageable, total);
    }

    @Override
    public QryAnonymousBoardDTO findAnonymousBoardById(int id) {
        return qryAnonymousBoardMapper.findAnonymousBoardById(id);
    }
}