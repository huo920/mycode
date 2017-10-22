package day12;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 使用DOM解析XML
 * @author tedu
 *
 */
public class ParseXMLDemo {
	public static void main(String[] args) {
		/*
		 * 解析XML文档emplist.xml,将其中的所有员工信息转换为若干个Emp实例并存入集合
		 */
		ParseXMLDemo a = new ParseXMLDemo();
		a.testReadXml();
		
		
	}
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
		
	 public void testReadXml(){
		try {
			List<Emp> list = new ArrayList<Emp>();
			SAXReader reader = new SAXReader();
			
			Document doc  =  reader.read(new FileInputStream("emplist.xml"));
			
			Element root = doc.getRootElement();
			 
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
			List<Element> emplist = root.elements();
			
			for(Element empEle : emplist){
//				System.out.println(empEle.getName());
				
				//获取名字
				Element nameFile = empEle.element("name");
				String name = nameFile.getText();
				System.out.println(name);
				
			/*	Element ageFile = empEle.element("age");
				int age = Integer.parseInt(ageFile.getText());
				System.out.println(age);*/
				
				int age = Integer.parseInt(empEle.elementText("age"));
				String gender = empEle.elementText("gender");
				int salary = Integer.parseInt(empEle.elementText("salary"));
				
				//获取id的属性的值
//				empEle.attributeValue("id");
				Attribute attr = empEle.attribute("id");
				int id = Integer.parseInt(attr.getValue());
				
				Emp emp = new Emp(id,name,age,gender,salary);
				list.add(emp);
				
			}
			//解析完毕
			System.out.println("解析完毕");
			for(Emp emp : list){
				System.out.println(emp);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	 }
	
}
