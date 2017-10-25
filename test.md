---
layout: post
title: Filter过滤器的简单使用
date: 2017-10-24
tags: Java   
---  
## 1.定义:
> servlet规范中定义的一种特殊的组件,用于拦截容器的调用
注:容器收到请求之后,如果有过滤器,会先调用过滤器,然后在调用servlet

![](http://oy2owwigw.bkt.clouddn.com/17-10-24/84021252.jpg)

## 2.准备
##### `CommentServlet.java`文件

```java

package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CommentServlet
 */
public class CommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.println("service begin...");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String content = request.getParameter("content");
		out.println("你的看法是:" + content);
		System.out.println("service end.");
	}

}

```
##### `comment_form.jsp`文件
``` html
<%@ page pageEncoding="utf-8"
contentType="text/html; charset=utf-8" %>
<html>
	<head></head>
	<body style="font-size:30px;">
		<form action="comment" method="post">
			请输入你的看法:<input name="content"/>
			<input type="submit" value="确定"/>
		</form>
	</body>
</html>
```
## 3.如何写一个过滤器
> 1.写一个`CommentFilter.java`类,实现filter接口,在接口方法中,实现拦截处理逻辑

```java
public class CommentFilter implements Filter{

	/**
	 * 容器启动之后，会立即创建过滤器实例
	 * （只会创建一个）。
	 */
	public CommentFilter() {
		System.out.println("CommentFilter()");
	}

	/**
	 * 容器销毁过滤器实例之前，会调用
	 * destroy方法（只会执行一次）
	 */
	public void destroy() {
		System.out.println("destroy()");
	}

	/**
	 * 初始化之后，容器会调用doFilter方法来处理请求。
	 * FilterChain对象（过滤器链）：如果调用了该对象
	 * 的doFilter方法，容器会继续向后调用（即继续调用
	 * 后面的过滤器或者servlet）,否则中断请求，返回结果。
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("doFilter begin...");
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		System.out.println("doFilter end.");

	}

	/**
	 * 实例化之后，会立即初始化。
	 * (init方法只会调用一次)。
	 * 注: FilterConfig对象用于读取初始化参数。
	 *
	 */
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("init()");
	}

}
```

> 2.配置过滤器(web.xml)

```xml
<filter>
  		<filter-name>filterA</filter-name>
  		<filter-class>web.CommentFilter</filter-class>
  </filter>
  <filter-mapping>
  		<filter-name>filterA</filter-name>
  		<url-pattern>/comment</url-pattern>
  </filter-mapping>
  ```     
 
 
 > 3.初始化参数  
 
1.在`<filter>`中配置初始化参数(init-param)  

```xml
<init-param>
	<param-name>illegal</param-name>
	<param-value>猫</param-value>
</init-param>
```  

2.FilterConfig提供的getInitParameter方法来读取初始化参数的值

```java
private FilterConfig config;

public void init(FilterConfig arg0) throws ServletException {
		config = arg0;
		System.out.println("init()");
}

public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
		throws IOException, ServletException {
        String illegal = config.getInitParameter("illegal");
}
```

##### `CommentFilter.java`完整文件

```java
package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommentFilter implements Filter{

	private FilterConfig config;

	/**
	 * 容器启动之后，会立即创建过滤器实例
	 * （只会创建一个）。
	 */
	public CommentFilter() {
		System.out.println("CommentFilter()");
	}

	/**
	 * 容器销毁过滤器实例之前，会调用
	 * destroy方法（只会执行一次）
	 */
	public void destroy() {
		System.out.println("destroy()");
	}

	/**
	 * 初始化之后，容器会调用doFilter方法来处理请求。
	 * FilterChain对象（过滤器链）：如果调用了该对象
	 * 的doFilter方法，容器会继续向后调用（即继续调用
	 * 后面的过滤器或者servlet）,否则中断请求，返回结果。
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("doFilter begin...");
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String content = request.getParameter("content");

		//读取初始化参数
		String illegal = config.getInitParameter("illegal");

		if(content.indexOf(illegal) != -1){
			//中断请求，返回结果
			out.println("评论内容非法");
		}else{
			//继续向后调用
			arg2.doFilter(arg0, arg1);
		}
		System.out.println("doFilter end.");

	}

	/**
	 * 实例化之后，会立即初始化。
	 * (init方法只会调用一次)。
	 * 注: FilterConfig对象用于读取初始化参数。
	 *
	 */
	public void init(FilterConfig arg0) throws ServletException {
		config = arg0;
		System.out.println("init()");
	}

}
```

##### `web.xml`完整文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns="http://java.sun.com/xml/ns/javaee"
xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" version="2.5">
  <filter>
  		<filter-name>filterA</filter-name>
  		<filter-class>web.CommentFilter</filter-class>
  		<!-- 配置初始化参数 -->
  		<init-param>
  			<param-name>illegal</param-name>
  			<param-value>猫</param-value>
  		</init-param>
  </filter>
  <filter-mapping>
  		<filter-name>filterA</filter-name>
  		<url-pattern>/comment</url-pattern>
  </filter-mapping>
  <servlet>
    <servlet-name>CommentServlet</servlet-name>
    <servlet-class>web.CommentServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>CommentServlet</servlet-name>
    <url-pattern>/comment</url-pattern>
  </servlet-mapping>
</web-app>
```


4.优先级
>当有多个过滤器都满足拦截要求,则容器依据<filter-mapping>配置的先后顺序来执行

5.优点:
>通过Filter技术，开发人员可以实现用户在访问某个目标资源之前，对访问的请求和响应进行拦截。
简单说，就是可以实现web容器对某资源的访问前截获进行相关的处理，还可以在某资源向web容器返回响应前进行截获进行处理。

### 练习:
>写一个过滤器检查评论的字数,如果超过指定的字数,(字数显示通过初始化参数配置)
	则提示用户,"评论字数过多"

#### 附上完整源码及答案 : [https://github.com/huo920/mycode](https://github.com/huo920/mycode/tree/master/java-filter/src/main)
