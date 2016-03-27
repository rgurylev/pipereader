package pipereader;

//import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
            App app = new App();
            app.go();
    }
    public void go () {
        System.out.println(System.getProperty("java.class.path"));
        try {
        InputStream in = getClass().getResourceAsStream("/resources/query.sql");
      //  String s = IOUtils.toString(in, "UTF-8");
     //   System.out.println( s );
    }catch (Exception e) {
            System.out.println(e.getStackTrace());
    }
    }
}
