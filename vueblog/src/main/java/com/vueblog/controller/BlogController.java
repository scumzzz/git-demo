package com.vueblog.controller;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueblog.common.lang.Result;


import com.vueblog.entity.Blog;
import com.vueblog.service.BlogService;
import com.vueblog.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author leon
 * @since 2022-03-23
 */
@RestController

public class BlogController {

    @Autowired
    BlogService blogService;
    //没有默认值为1
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) {

        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageData );
    }
    //@PathVariable动态路由
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "博客已被删除");

        return Result.succ(blog);
    }
    //@Validated校验
    //@RequestBody
    //添加删除  木有id则添加 有id则编辑
    @RequiresAuthentication//需要认证之后才能操作
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        //一个空对象用于赋值
        Blog temp = null;
        if(blog.getId() != null){//如果有id则是编辑
            temp = blogService.getById(blog.getId());//将数据库的内容传递给temp
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()), "没有权限编辑");
        }else{
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        //将blog的值赋给temp 忽略 id userid created status 引用于hutool
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }

    //@PathVariable动态路由
    @RequiresAuthentication  //需要认证之后才能操作
    @PostMapping("/blogdel/{id}")
    public Result del(@PathVariable Long id){
        boolean b = blogService.removeById(id);
        //判断是否为空 为空则断言异常
        if(b==true){
            return Result.succ("文章删除成功");
        }else{
            return Result.fail("文章删除失败");
        }
    }
}
