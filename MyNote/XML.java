

====================读=======================
		/*
		 * 使用DOM解析XML的大致流程:
		 * 1:创建SAXReader
		 * 2:使用SAXReader读取要解析的XML文档并返回一个Document对象 
		 * 	这一步就是DOM耗时耗资源的地方,因为要先将XML文档全部读取完毕并存入到一个Document对象中
		 * 3:根据Document对象获取根元素
		 * 4:按照XML文档的结构从根元素开始逐级取子元素,以达到解析XML文档获取数据的目的
		 */
		
		
		
		/*
		 * 2解析XML文档,
		 * 提供了读取的相关方法:
		 * Document read(File file)
		 * 解析指定File对象 所表示的XML文档
		 * Document read (InputStream is)
		 * 给定的字节流中读取XML文档数据并解析
		 * 
		 * Document read(Reader reader)
		 * 从给定的字符流中读取XML文档数据并解析
		 * 
		 * 上面最后两个方法类似java中高级流操作
		 * 
		 */
		 
		 /*
			 * 3 获取根元素
			 * Document 提供了获取根元素的方法:
			 * Element getRootElement()
			 * 
			 * Element 的每一个实例用于表示XML文档中的一个元素(一对标签)
			 */
			
			/*
			 * Element表示一个XML文档中的一个元素(标签)
			 * 它提供了获取该元素相关信息的方法:
			 * String getName()
			 * 获取当前元素的名字
			 * 
			 * Element element(String name)
			 * 获取当前元素中指定名字的元素
			 * 
			 * List elements()
			 * 获取当前元素中的所有子元素
			 * 
			 * List elements(String name)
			 * 获取当前元素中所有同名子元素
			 * 
			 * String getText()
			 * 获取当权元素中的文本(从开始标签和结束标签中间的文本信息)
			 * 
			 * Attribute attribute(String name)
			 * 获取当前元素中指定名字的属性
			 */
			 
			 
			 
====================写=======================			 
			 /*
		 * 生成XML文档的大致步骤:
		 * 1:创建一个Document对象表示一个空白文档
		 * 2:向Document中添加根元素
		 * 3:按照XML文档的结构逐级添加子元素
		 * 4:创建XmlWriter对象 ,OutputFormat.createPrettyPrint()
		 * 5:将Document写出
		 * 6:关闭XmlWriter
		 */
		 
		 /*
			 * 2:
			 * Document提供了方法添加根元素的方法
			 * Element addElement(String name)
			 * 添加指定名字的根元素,并将其以Element
			 * 的实例形式返回以便对元素继续操作
			 * 需要注意,该方法只能调用一次~~~
			 */
			 
			 //将集合中的每个员工以<emp>标签形式添加到根元素中

				/*
				 * Element 提供了向其添加相关信息的方法
				 * 1;Element addElement(String name)
				 * 向当前标签中添加给定名字的子标签
				 * 
				 * 2:Element addText(String text)
				 * 向当前标签中添加文本信息
				 * 
				 * 3:Element addAttribute(String name ,String value)
				 * 向当前标签中添加 指定名字以及对应值的属性
				 */
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
