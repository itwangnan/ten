package com.tensquare.article.controller;
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

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;

import com.entity.PageResult;
import com.entity.Result;
/**
 * 文章模块
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Article> findAll(){
		return articleService.findAll();
	}
	
	/**
	 * 根据ID查询
	 * @param articleId ID
	 * @return
	 */
	@RequestMapping(value="/{articleId}",method=RequestMethod.GET)
	public Article findOne(@PathVariable String articleId){
		return articleService.findOne(articleId);
	}
	
	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public PageResult<Article> findPage(@PathVariable int page,@PathVariable int size){		
		Page<Article> pageList = articleService.findPage(page, size);		
		return new PageResult<Article>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 分页+多条件查询 
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public PageResult<Article> findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){		
		Page<Article> pageList = articleService.findSearch(searchMap, page, size);
		return new PageResult<Article>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 增加
	 * @param article
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Article article  ){

		articleService.add(article);	
		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@RequestMapping(value="/{articleId}",method=RequestMethod.PUT)
	public Result update(@RequestBody Article article,@PathVariable String articleId ){
		article.setId(articleId);
		articleService.update(article);		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 删除
	 * @param
	 */
	@RequestMapping(value="/{articleId}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String articleId ){
		articleService.delete(articleId);
		return new Result(true,StatusCode.OK,"");
	}

	/**
	 * 文章审核
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="/examine/{articleId}",method=RequestMethod.PUT)
	public Result examine(@PathVariable String articleId ){
		articleService.examine(articleId);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 点赞
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="/thumbup/{articleId}",method=RequestMethod.PUT)
	public Result thumbup(@PathVariable String articleId ){
		articleService.thumbup(articleId);
		return new Result(true,StatusCode.OK,"操作成功");
	}
}
