package Handlers;

import Settings.Logging;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static res.RESURSE.INI_FILE;


public class Controller {
    public static Controller current_controller;
    public static final String log_pattern_path="log_pattern_path";
    public static   String getLog_pattern_path_val;



    private  boolean isOn=false;
    private Server_handler server_handler;
    public  void stop_server(){if (server_handler!=null) server_handler.close();};
    @FXML private  BorderPane pane;
    public   static ObservableList<String> observableList;
    @FXML private Button button;
    @FXML
    public void On_Off()
    {
        Logging logging=new Logging();


        if (!isOn)
        {
            try {
                logging.getFileLogger(this.getClass()).info("Сервер включен");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        {
            try {
                logging.getFileLogger(this.getClass()).info("Сервер выключен");
            } catch (IOException e) {
                e.printStackTrace();
            }
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


        //Получение файла jar
            String path = Controller.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            getLog_pattern_path_val=path.substring(1,path.length());//Мы удалим первый символ
            getLog_pattern_path_val= Paths.get(getLog_pattern_path_val).getParent().toString()+"\\logs\\";

//Создадим директорию логов, если ее не существует
        Path dir= Paths.get(getLog_pattern_path_val);
        if (!((Files.exists(dir))&&(Files.isDirectory(dir))))
        {
            try {
                Files.createDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Properties  settings_default=new Properties();
        settings_default.setProperty(lv_width, "250");
        settings_default.setProperty(lv_height, "250");
        settings_default.setProperty(log_pattern_path, getLog_pattern_path_val);
        //
        settings_default.setProperty("port","5000");

        Properties settings=new Properties(settings_default);
        try(FileInputStream fis=new FileInputStream(INI_FILE)) {
            //Запишем из файла
            settings.load(fis);
        } catch (FileNotFoundException e) {
            //Если файл не обнаружен то запишем его
            try (FileOutputStream fos=new FileOutputStream(INI_FILE,true)) {
                settings_default.store(fos,INI_FILE);
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


        Logging logging=new Logging();
        try {
            logging.getFileLogger(this.getClass()).info("Запуск");
        } catch (IOException e) {
            e.printStackTrace();
        }

        pane.setPrefSize(Integer.parseInt(settings.getProperty(lv_width,"250")),
                Integer.parseInt(settings.getProperty(lv_height,"250")));
    }
}
