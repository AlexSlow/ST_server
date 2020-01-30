package Settings;

import Handlers.Controller;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static res.RESURSE.INI_FILE;

public class Logging {
    private static FileHandler fh;
    private Logger logger;



    static {
        try(FileReader reader=new FileReader(INI_FILE))
        {
            Properties properties=new Properties();
            properties.load(reader);
            String value=properties.getProperty(Controller.log_pattern_path,
                    Controller.getLog_pattern_path_val);

                Path file_handler= Paths.get(Controller.getLog_pattern_path_val);
                if ((Files.exists(file_handler))&&(Files.isDirectory(file_handler))) {
                        fh = new FileHandler(value + "log",
                                100000, 5);
                    }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  Logger getFileLogger(Class<?> ref_class) throws IOException
    {
        if (fh==null) throw new NullPointerException();
        if (logger!=null) return logger;
        logger=Logger.getLogger(ref_class.getName());
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        return logger;
    }
}
