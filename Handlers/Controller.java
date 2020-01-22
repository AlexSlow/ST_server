package Handlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Controller {
    public static Controller current_controller;
    public static final String log_pattern_path="log_pattern_path";
    public static  final String getLog_pattern_path_val="c:\\Users\\PC\\IdeaProjects\\Shulte_table_server\\logs\\";
    public static    FileHandler fh;

    private  final   Logger logger= Logger.getLogger(this.getClass().getName());
    private  boolean isOn=false;
    private Server_handler server_handler;
    public  void stop_server(){if (server_handler!=null) server_handler.close();};
    @FXML private  BorderPane pane;
    public   static ObservableList<String> observableList;
    @FXML private Button button;
    @FXML
    public void On_Off()
    {

        if (!isOn)
        {
            logger.info("Сервер включен");
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    server_handler=new Server_handler();
                    server_handler.listening();
                }
            };
            isOn=true;
            button.setText("Выключить");
            Thread thread=new Thread(runnable);
            thread.start();
        }else
        { logger.info("Сервер выключен");
            server_handler.close();
            button.setText("Включить");
            isOn=false;
        }
    }
    @FXML
    public void initialize()  {
        current_controller=this;
        //Загрузка prpperty
        final String lv_width="lv_width";
        final String lv_height="lv_height";
        Controller.observableList= FXCollections.observableArrayList();
        javafx.scene.control.ListView<String> listView=new ListView<>(Controller.observableList);
        pane.setCenter(listView);

        Properties  settings_default=new Properties();
        settings_default.setProperty(lv_width, "250");
        settings_default.setProperty(lv_height, "250");
        settings_default.setProperty(log_pattern_path, getLog_pattern_path_val);
        //


        Properties settings=new Properties(settings_default);
        try(FileInputStream fis=new FileInputStream("settings.ini")) {
            //Запишем из файла
            settings.load(fis);
        } catch (FileNotFoundException e) {
            //Если файл не обнаружен то запишем его
            try {
                FileOutputStream fos=new FileOutputStream("settings.ini",true);
                settings_default.store(fos,"settings");
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();

                //Связанные исключения
               // Exception exception=new Exception();
               // exception.initCause(e1);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            LogManager.getLogManager().readConfiguration();// чтение файла конфигурации, из майн
            String value=settings.getProperty(log_pattern_path, getLog_pattern_path_val);
            if (fh==null) {
                 fh = new FileHandler(value + "log",
                        100000, 5);
            }
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Запуск");

        pane.setPrefSize(Integer.parseInt(settings.getProperty(lv_width,"250")),
                Integer.parseInt(settings.getProperty(lv_height,"250")));
    }
}
