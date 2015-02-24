package fr.isima.ejb.container;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.isima.ejb.annotations.PersistenceContext;
import fr.isima.ejb.annotations.Stateless;
import fr.isima.ejb.exceptions.PersistanceUnitNotExist;

public class PersistanceManager {

	public static void persistanceOnCreate(Object bean) throws Exception {
		if (!ReflectionServices.isClassAnnotatedWith(bean.getClass(),
				Stateless.class))
			persiste(bean);
	}

	public static void persistanceOnCall(Object bean) throws Exception {
		if (ReflectionServices.isClassAnnotatedWith(bean.getClass(),
				Stateless.class))
			persiste(bean);
	}

	public static void persistancePostCall(Object bean) throws Exception {
		if (ReflectionServices.isClassAnnotatedWith(bean.getClass(),
				Stateless.class)) {
			unpersiste(bean);
		}
	}

	private static void unpersiste(Object bean)
			throws IllegalArgumentException, IllegalAccessException {
		Reflections reflections = new Reflections(bean.getClass(),
				new FieldAnnotationsScanner());
		Set<Field> Persistancefields = reflections
				.getFieldsAnnotatedWith(PersistenceContext.class);
		for (Field fild : Persistancefields) {
			fild.setAccessible(true);
			fild.set(bean, null);
		}
	}

	private static void persiste(Object bean) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException,
			PersistanceUnitNotExist, IllegalArgumentException,
			IllegalAccessException, InstantiationException,
			ClassNotFoundException {
		Reflections reflections = new Reflections(bean.getClass(),
				new FieldAnnotationsScanner());
		Set<Field> Persistancefields = reflections
				.getFieldsAnnotatedWith(PersistenceContext.class);
		for (Field fild : Persistancefields) {
			PersistenceContext annotation = fild
					.getAnnotation(PersistenceContext.class);
			String dataSourceName = annotation.unitName();
			String classImplementingDataSource = "";
			classImplementingDataSource = getEntityManagerImplementationByDataSource(dataSourceName);
			fild.setAccessible(true);
			fild.set(bean, Class.forName(classImplementingDataSource)
					.newInstance());
		}

	}

	private static String getEntityManagerImplementationByDataSource(
			String dataSourceName) throws ParserConfigurationException,
			SAXException, IOException, XPathExpressionException,
			PersistanceUnitNotExist {

		File fXmlFile = new File("src/persistance.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		// System.out.println("Root element :" +
		// doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("persistence-unit");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;
			if (eElement.getAttribute("name").equals(dataSourceName)) {
				// System.out.println("Entity Manager Implementation : " +
				// eElement.getElementsByTagName("provider").item(0).getTextContent());
				return eElement.getElementsByTagName("provider").item(0)
						.getTextContent();
			}
		}
		throw new PersistanceUnitNotExist(dataSourceName);
	}
}
