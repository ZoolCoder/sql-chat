package de.sql.chat.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A utility class for converting XML to Java objects using JAXB.
 *
 * @param <T> the type of the target Java object
 *
 * @since 27-11-2023
 * @author Abdallah Emad
 */
public class XmlToObjectUtil<T> {
  private static final Logger LOGGER = LogManager.getLogger(XmlToObjectUtil.class);

  private final T targetObject;

  /**
   * Constructs a new XmlToObjectUtil instance and converts the XML to the target Java object.
   *
   * @param xmlBody      the XML body to be converted
   * @param targetClass  the class of the target Java object
   * @throws JAXBException if an error occurs during unmarshalling
   */
  public XmlToObjectUtil(String xmlBody, Class<T> targetClass) throws JAXBException {
    this.targetObject = convertXmlToClass(xmlBody, targetClass);
  }

  /**
   * Returns the target Java object.
   *
   * @return the target Java object
   */
  public T getTargetObject() {
    return targetObject;
  }

  /**
   * Converts an XML string to an instance of the specified class.
   *
   * @param xmlBody      the XML string to convert
   * @param targetClass  the class to convert the XML to
   * @return the converted instance of the specified class
   * @throws JAXBException if an error occurs during unmarshalling
   */
  private T convertXmlToClass(String xmlBody, Class<T> targetClass) throws JAXBException {
    try {
      return unmarshal(xmlBody, targetClass);
    } catch (JAXBException | ClassCastException e) {
      LOGGER.error("Error during JAXB unmarshalling", e);
      throw e;
    }
  }

  /**
   * Unmarshals the given XML body into an object of type T.
   *
   * @param xmlBody      the XML body to unmarshal
   * @param targetClass  the class of the target object
   * @return the unmarshalled object of type T
   * @throws JAXBException if an error occurs during unmarshalling
   */
  private T unmarshal(String xmlBody, Class<T> targetClass) throws JAXBException {
    JAXBContext jaxbContext = JAXBContext.newInstance(targetClass);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

    try (StringReader reader = new StringReader(xmlBody)) {
      return targetClass.cast(unmarshaller.unmarshal(reader));
    }
  }
}