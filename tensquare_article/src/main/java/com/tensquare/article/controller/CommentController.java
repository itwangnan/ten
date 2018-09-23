package com.tensquare.article.controller;

import com.entity.Result;
import com.entity.StatusCode;
import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK,"提交成功");
    }

    /**
     * 根据articleid查找Comment
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/article/{articleId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String articleId){
        Comment comment = commentService.findById(articleId);
        return new Result(true, StatusCode.OK,"查询成功",comment);
    }

    /**
     * 删除
     * @param commentid
     * @return
     */
    @RequestMapping(value = "/{commentid}",method = RequestMethod.GET)
    public Result remove(@PathVariable String commentid){
        commentService.remove(commentid);
        return new Result(true, StatusCode.OK,"删除成功");
    }
}
