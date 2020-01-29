package DataBase;

import Handlers.Controller;
import Sql_commands.Command_factory;
import Sql_commands.SQLcommand;
import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static res.RESURSE.INI_FILE;


public class FXMLparser {
public  static final Logger logger= Logger.getLogger(FXMLparser.class.getName()); //Иерархия логгера имеет заначение
    static
    {

        try(FileReader reader=new FileReader(INI_FILE)) {
            Properties properties=new Properties();
            properties.load(reader);
            String value=properties.getProperty(Controller.log_pattern_path,
                    Controller.getLog_pattern_path_val);
            if (Controller.fh==null) {
                Path file_handler= Paths.get(Controller.getLog_pattern_path_val);
                if ((Files.exists(file_handler))&&(Files.isDirectory(file_handler))) {
                    if (Controller.fh == null) {
                        Controller.fh = new FileHandler(value + "log",
                                100000, 5);

                    }
                    logger.addHandler(Controller.fh);
                    logger.setUseParentHandlers(false);
                }else
                {
                    //Нет каталога
                }
            }else
            {
                //Поставим существующий
                logger.addHandler(Controller.fh);
                logger.setUseParentHandlers(false); // Отключение других контроллеров
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * метод декодирует входящий запрос
     *@param xml файл
     * @return sqLcommand
     * @author  Терехов А.С
     * @version 1.0
     */
  public   static SQLcommand decode_client_querry(String xml)
  {
      logger.info("Обрабатываю запрос от пользователя");

      //Парсим документ, а  и просто формируем параметры, формируем комманду
      Document document=convertStringToXMLDocument(xml);
      try {
         NodeList command=document.getElementsByTagName("command");
          NodeList sql=document.getElementsByTagName("sql");
          NodeList type=document.getElementsByTagName("type");
         NodeList params=document.getElementsByTagName("params");
         NodeList children=params.item(0).getChildNodes();

         Command_factory command_factory=new Command_factory();
         //System.out.println(command.item(0).getTextContent());
          SQLcommand sqLcommand=command_factory.getCommand((type.item(0).getTextContent().toLowerCase()),
                  sql.item(0).getTextContent().toLowerCase(),command.item(0).getTextContent().toLowerCase());
          ArrayList<String> params_list=new ArrayList<>();



        for (int i=0; i<children.getLength();i++)
        {
            String str="";
            if (!children.item(i).getNodeName().equals("#text")) {
                params_list.add(children.item(i).getTextContent());
            }
        }
        sqLcommand.setParams(params_list);
        return  sqLcommand;

      } catch (Exception e) {
          e.printStackTrace();
      }
    return  null;
  }

    /**
     * метод  парсит ответ в формате xml
     *@param command
     * @param list - список параметров
     * @return xml файл-ответ
     * @author  Терехов А.С
     * @version 1.0
     */
    public  static String   response(String command, ArrayList<ArrayList<String>> list,
                                     String status_str,ArrayList<String> collumn)
    {
        logger.info("Фотмирование ответа");
      DocumentBuilderFactory  documentBuilderFactory=DocumentBuilderFactory.newInstance();//Получаем фабрику
        try {
          DocumentBuilder  documentBuilder=documentBuilderFactory.newDocumentBuilder();
          Document  document=documentBuilder.newDocument();
            Element root= document.createElement("root");
            Element operation=document.createElement("command");
            Element status=document.createElement("status");
            Element params=document.createElement("params");
            document.appendChild(root);
            root.appendChild(operation);
            root.appendChild(status);
            root.appendChild(params);
           // Text status_text=document.createTextNode(status_str);
           status.setTextContent(status_str);
           operation.setTextContent(command);



           if ((list!=null)&&(collumn!=null)) {
               int i=0;
               for (ArrayList<String> list_param : list) {
                   int j = 0;
                   Element row = document.createElement("row_" + i++);
                   params.appendChild(row);
                   for (String str : list_param) {
                       Element str_element = document.createElement(collumn.get(j++));
                       str_element.setTextContent(str);
                       row.appendChild(str_element);

                   }

               }
           }

            //Вывод результата в строку
           DOMImplementation   domImplementation=document.getImplementation();
           DOMImplementationLS  domImplementationLS=(DOMImplementationLS)domImplementation.getFeature("LS","3.0");
           LSSerializer lsSerializer=domImplementationLS.createLSSerializer();
            lsSerializer.getDomConfig().setParameter("format-pretty-print",true);
            String xml=lsSerializer.writeToString(document);
            //System.out.println(xml);
            return  xml;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            logger.warning("ошибка формирования ответа");
            return null;
        }
    }



  //Строка в xml dom
    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
