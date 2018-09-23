package com.tensquare.spit.controller;

import com.entity.PageResult;
import com.entity.Result;
import com.entity.StatusCode;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 吐糟
 */
@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public Result insert(@RequestBody Spit spit) {
        spitService.insert(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List<Spit> list = spitService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.POST)
    public Result findById(@PathVariable String spitId) {
        Spit spit = spitService.findById(spitId);
        return new Result(true, StatusCode.OK, "查询成功", spit);
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit) {
        spitService.update(spitId, spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result remove(@PathVariable String spitId) {
        spitService.remove(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Spit spit) {
        List<Spit> list = spitService.search(spit);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result searchPage(@PathVariable Integer page, @PathVariable Integer size, @RequestBody Spit spit) {
        Page<Spit> pag = spitService.searchPage(spit, page, size);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<Spit>(pag.getTotalElements(), pag.getContent()));
    }

    /**
     * 吐糟点赞
     *
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId) {
        String username = "wangnan";
        Boolean ifThumbup = redisTemplate.boundSetOps("ifThumbup").isMember(username+"_thumbup_"+spitId);
        if (ifThumbup) {
            return new Result(false, StatusCode.REPERROR, "你已经点过赞了");
        }
        spitService.thumbup(spitId);
        redisTemplate.boundSetOps("ifThumbup").add(username+"_thumbup_"+spitId);
        return new Result(true, StatusCode.OK, "点赞成功");
    }

    /**
     * 根据上级Id查询吐糟数据(分页)
     *
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result comment(@PathVariable String parentid,
                          @PathVariable Integer page,
                          @PathVariable Integer size) {
        Page<Spit> pag = spitService.comment(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功",
                new PageResult<>(pag.getTotalElements(), pag.getContent()));
    }
}
