package DataBase;

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
import java.util.ArrayList;

public class FXMLparser {

  public   static SQLcommand decode_client_querry(String xml)
  {
      Document document=convertStringToXMLDocument(xml);
      try {
         NodeList command=document.getElementsByTagName("command");
         NodeList params=document.getElementsByTagName("params");
         NodeList children=params.item(0).getChildNodes();

         Command_factory command_factory=new Command_factory();
         //System.out.println(command.item(0).getTextContent());
          SQLcommand sqLcommand=command_factory.getCommand((command.item(0).getTextContent().toLowerCase()));
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
    public  static String   response(String command, ArrayList<ArrayList<String>> list,
                                     String status_str,ArrayList<String> collumn)
    {
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


           int i=0;

           for (ArrayList<String> list_param:list)
           {
               int j=0;
               Element row=document.createElement("row_"+i++);
               params.appendChild(row);
               for (String str:list_param)
               {
                   Element str_element=document.createElement(collumn.get(j++));
                   str_element.setTextContent(str);
                   row.appendChild(str_element);

               }

           }

            //Вывод результата в строку
           DOMImplementation   domImplementation=document.getImplementation();
           DOMImplementationLS  domImplementationLS=(DOMImplementationLS)domImplementation.getFeature("LS","3.0");
           LSSerializer lsSerializer=domImplementationLS.createLSSerializer();
            lsSerializer.getDomConfig().setParameter("format-pretty-print",true);
            String fxml=lsSerializer.writeToString(document);
            return  fxml;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
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
