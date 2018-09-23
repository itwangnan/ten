package com.tensquare.base.controller;

import com.entity.PageResult;
import com.entity.Result;
import com.entity.StatusCode;
import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {
    @Autowired
    private LabelService labelService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result remove(@PathVariable String labelId){
        labelService.remove(labelId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
//        int i = 1/0;
        List<Label> all = labelService.findAll();
        return new Result(true,StatusCode.OK,"查询成功",all);
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findOne(@PathVariable String labelId){
        Label label = labelService.findOne(labelId);
        return new Result(true,StatusCode.OK,"查询成功",label);
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result search(@RequestBody Label label){
        List<Label> list = labelService.createSpecification(label);
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label,@PathVariable Integer page,@PathVariable Integer size){
        Page<Label> pages = labelService.findSearch(label, page, size);

        PageResult<Label> result = new PageResult<>(pages.getTotalElements(),pages.getContent());
        return new Result(true, StatusCode.OK,"查询成功",result);
    }
}
