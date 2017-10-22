package day12;

import java.io.FileInputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 使用XPATH检索XML文档数据
 * @author tedu
 *
 */

public class XPathDemo {
	public static void main(String[] args) {
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new FileInputStream("myemp.xml"));
			
			String xpath = "/list/emp[salary>4000 and gender ='男']/name";
			List<Element> list = doc.selectNodes(xpath);
			for(Element e :list){
				System.out.println(e.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
