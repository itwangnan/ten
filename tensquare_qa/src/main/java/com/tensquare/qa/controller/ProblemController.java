package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import com.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import com.entity.PageResult;
import com.entity.Result;
/**
 * 问题模块
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Problem> findAll(){
		return problemService.findAll();
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Problem findOne(@PathVariable String id){
		return problemService.findOne(id);
	}
	
	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public PageResult<Problem> findPage(@PathVariable int page,@PathVariable int size){		
		Page<Problem> pageList = problemService.findPage(page, size);		
		return new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 分页+多条件查询 
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public PageResult<Problem> findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){		
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){

		problemService.add(problem);	
		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Problem problem,@PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.delete(id);
		return new Result(true, StatusCode.OK,"");
	}

	/**
	 * 最新问答
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/newlist/{label}/{page}/{size}",method=RequestMethod.GET)
	public Result newlist(@PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		List<Problem> newlist = problemService.newlist(label, page, size);
		return new Result(true,StatusCode.OK,"操作成功",newlist);
	}


	/**
	 * 热门问答
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/hotlist/{label}/{page}/{size}",method=RequestMethod.GET)
	public Result hotlist(@PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		List<Problem> hotlist = problemService.hotlist(label, page, size);
		return new Result(true,StatusCode.OK,"操作成功",hotlist);
	}

	/**
	 * 等待回答
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/waitlist/{label}/{page}/{size}",method=RequestMethod.GET)
	public Result waitlist(@PathVariable String label,@PathVariable Integer page,@PathVariable Integer size){
		List<Problem> waitlist = problemService.waitlist(label, page, size);
		return new Result(true,StatusCode.OK,"操作成功",waitlist);
	}
}
