package day12;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/*
 * 使用DOM生成XML文档
 */
public class WriteXMLDemo {
	public static void main(String[] args) {
		/*
		 * 生成XML文档的大致步骤:
		 * 1:创建一个Document对象表示一个空白文档
		 * 2:向Document中添加根元素
		 * 3:按照XML文档的结构逐级添加子元素
		 * 4:创建XmlWriter对象 
		 * 5:将Document写出
		 * 6:关闭XmlWriter
		 */
		
		List<Emp> list = new ArrayList<Emp>();
		list.add(new Emp(1,"张三",22,"男",3000));
		list.add(new Emp(2,"李四",23,"男",5000));
		list.add(new Emp(3,"王五",22,"女",7000));
		list.add(new Emp(4,"赵六",29,"男",2000));
		list.add(new Emp(5,"哈哈",32,"男",9000));
		
		try {
			//1:创建一个Document对象表示一个空白文档
			Document doc = DocumentHelper.createDocument();
			
			/*
			 * 2:
			 * Document提供了方法添加根元素的方法
			 * Element addElement(String name)
			 * 添加指定名字的根元素,并将其以Element
			 * 的实例形式返回以便对元素继续操作
			 * 需要注意,该方法只能调用一次~~~
			 */
			Element root = doc.addElement("list");
			
			//将集合中的每个员工以<emp>标签形式添加到根元素中
			for(Emp emp :list){
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
				//向根元素中添加<emp>
				Element empEle = root.addElement("emp");
				
				
				//添加<name>
				Element nameEle = empEle.addElement("name");
				nameEle.addText(emp.getName());
				
				//添加<age>
				Element ageEle = empEle.addElement("age");
				ageEle.addText(String.valueOf(emp.getAge()));
//				ageEle.addText(emp.getAge()+"");
				
				//添加<gender>
				Element genderEle = empEle.addElement("gender");
				genderEle.addText(emp.getGender());
				
				//添加<salary>
				Element salaryEle = empEle.addElement("salary");
				salaryEle.addText(String.valueOf(emp.getSalary()));
				
				//添加<id>
				empEle.addAttribute("id", emp.getId()+"");
			}
			
			FileOutputStream fos = new FileOutputStream("myemp.xml");
			XMLWriter writer = new XMLWriter(fos,OutputFormat.createPrettyPrint());
			writer.write(doc);		
			System.out.println("写出完毕");
			writer.close();
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
