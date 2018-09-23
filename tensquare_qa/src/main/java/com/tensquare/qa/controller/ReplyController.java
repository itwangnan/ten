package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Reply;
import com.tensquare.qa.service.ReplyService;

import com.entity.PageResult;
import com.entity.Result;
/**
 * 回答模块
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Reply> findAll(){
		return replyService.findAll();
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Reply findOne(@PathVariable String id){
		return replyService.findOne(id);
	}
	
	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public PageResult<Reply> findPage(@PathVariable int page,@PathVariable int size){		
		Page<Reply> pageList = replyService.findPage(page, size);		
		return new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 分页+多条件查询 
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public PageResult<Reply> findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){		
		Page<Reply> pageList = replyService.findSearch(searchMap, page, size);
		return new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 增加
	 * @param reply
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Reply reply  ){

		replyService.add(reply);	
		
		return new Result(true,0,"");
	}
	
	/**
	 * 修改
	 * @param reply
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Reply reply,@PathVariable String id ){
		reply.setId(id);
		replyService.update(reply);		
		return new Result(true,0,"");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		replyService.delete(id);
		return new Result(true,0,"");
	}


}
