package hw.activemqtest.services;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;

/**
 * Created by zotova on 07.07.2016.
 */
@Component
public class XmlValidator {

    Validator validator;
    Resource schema;

    public XmlValidator(Resource schema) throws IOException, SAXException, NullPointerException {

        this.schema = schema;
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        validator = factory.newSchema(schema.getFile()).newValidator();
    }

    public void validate(String xml) throws Exception  {
        Source source = new StreamSource(new StringReader(xml));
        validator.validate(source);
    }
}
