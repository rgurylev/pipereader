package pipereader;

import event.EventsDispatcher;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;
import pipereader.model.PipeReader;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by roman on 21.11.15.
 */
public class main {
    {
       // PropertyConfigurator.configure(System.getProperty("log4j.configuration"));
        DOMConfigurator.configure(System.getProperty("log4j.configuration"));
       // DOMConfigurator.configure(getClass().getResource("log4j.xml").getPath());
       // String filename = System.getProperty("log4j.configuration");
       // DOMConfigurator(filename);
    }

    private static final Logger log = Logger.getLogger(main.class);


    private void start() {

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
        mainwindow mw = (mainwindow) context.getBean("mainwindow");
        PipeReader pr = (PipeReader) context.getBean("pipereader");
        EventsDispatcher.registerListener(pr, mainwindow.class);
        mw.setVisible(true);
        pr.start();
    }

    public static void main(String[] args) throws SAXException, IOException, SQLException, XPathExpressionException, ParserConfigurationException {
        (new main()).start();
        log.info("поехали!");

    }


}
