package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import com.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment){
        comment.set_id(idWorker.nextId()+"");
//        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }

    public Comment findById(String articleId) {
        return commentDao.findByArticleid(articleId);
    }

    public void remove(String commentid) {
        commentDao.deleteById(commentid);
    }
}
