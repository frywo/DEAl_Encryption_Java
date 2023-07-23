package com.example.deal;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

class Thread_Encrypt implements Runnable  {

    String file; String Mode_CBC; int length; String Key;
    ProgressBar PB; ChoiceBox<String> BCM; ChoiceBox<String> DM; Button FF; Button EF; Button DF; Button C;
    PasswordField PF; Text TexT;

    public Thread_Encrypt(String name_file, String Mode, int length_key, String key, ProgressBar pb,
                          ChoiceBox<String> bcm, ChoiceBox<String> dm, Button ff, Button ef, Button df, Button c,
                          PasswordField pf, Text text) {
        this.file = name_file;
        this.Mode_CBC = Mode;
        this.length = length_key;
        this.Key = key;
        this.TexT = text;

        this.PB = pb; this.BCM = bcm; this.DM = dm; this.FF = ff; this.EF = ef; this.DF = df; this.C = c; this.PF = pf;
    }

    @Override
    public void run() {
        PB.setVisible(true); BCM.setDisable(true); DM.setDisable(true); FF.setDisable(true); EF.setDisable(true);
        DF.setDisable(true); PF.setDisable(true); C.setDisable(false); C.setVisible(true);
        try {
            int check = Deal.File_Encrypt(file,Mode_CBC,length,Key,PB);
            if(check==1){
                TexT.setText("Файл пуст или открыт пользователем!");
            } else if(check==2){
                TexT.setText("нет доступа к файлу, возможно файл открыт пользователем!");
            } else if(check ==3){
                TexT.setText("Вспомогательный файл невозможно удалить, т.к. он возможно открыт");
            }

        } catch (Exception e) {
            TexT.setText("СпЕцИфИчНаЯ Ошибка! "+e.getMessage());
        }
        PF.setText("");
        PB.setVisible(false); BCM.setDisable(false); DM.setDisable(false); FF.setDisable(false); EF.setDisable(false);
        DF.setDisable(false); PF.setDisable(false); C.setDisable(true); C.setVisible(false);
    }
}

class Thread_Decrypt implements Runnable  {


    String file; String Mode_CBC; int length; String Key;

    ProgressBar PB; ChoiceBox<String> BCM; ChoiceBox<String> DM; Button FF; Button EF; Button DF; Button C;
    PasswordField PF; Text TexT;


    public Thread_Decrypt(String name_file, String Mode, int length_key, String key, ProgressBar pb,
                          ChoiceBox<String> bcm, ChoiceBox<String> dm, Button ff, Button ef, Button df, Button c,
                          PasswordField pf, Text text) {
        this.file = name_file;
        this.Mode_CBC = Mode;
        this.length = length_key;
        this.Key = key;
        this.TexT =text;

        this.PB = pb; this.BCM = bcm; this.DM = dm; this.FF = ff; this.EF = ef; this.DF = df; this.C = c; this.PF = pf;
    }

    public void run() {
        PB.setVisible(true); BCM.setDisable(true); DM.setDisable(true); FF.setDisable(true); EF.setDisable(true);
        DF.setDisable(true); PF.setDisable(true); C.setDisable(false); C.setVisible(true);
        try {
            int check = Deal.File_Decrypt(file,Mode_CBC,length,Key,PB);
            if(check==1){
                TexT.setText("Файл пуст или не зашифрован или открыт пользователем!");
            } else if(check==2){
                TexT.setText("нет доступа к файлу, возможно файл открыт пользователем!");
            } else if(check ==3){
                TexT.setText("Вспомогательный файл невозможно удлить, т.к он возможно открыт");
            }
        } catch (Exception e) {
            TexT.setText("СпЕцИфИчНаЯ Ошибка! "+e.getMessage());
        }
        PF.setText("");
        PB.setVisible(false); BCM.setDisable(false); DM.setDisable(false); FF.setDisable(false); EF.setDisable(false);
        DF.setDisable(false); PF.setDisable(false); C.setDisable(true); C.setVisible(false);
    }
}

public class Controller {

    private final String[] Modes_Deal ={"Deal-128", "Deal-192", "Deal-256"};
    private final String[] CipherModes ={"EBC", "CBC", "CFB", "OFB"};
    String absolutePath;
    Thread thread;

    @FXML
    ResourceBundle resources;

    @FXML
    URL location;

    @FXML
    ChoiceBox<String> BlockCipherMode;
    @FXML
    ChoiceBox<String> Deal_Mode;


    @FXML
    Button Cancel_button;
    @FXML
    Button Decrypt_Button;
    @FXML
    Button Encrypt_Button;
    @FXML
    Button FindToFile;


    @FXML
    Text PathToFile;
    @FXML
    Text Error_Text;


    @FXML
    PasswordField Get_String_Key;


    @FXML
    ProgressBar ProgressBar;



    @FXML
    void ButtonFindFileAction(){
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if (f!=null){
            absolutePath = f.getAbsolutePath();
            PathToFile.setText(f.getPath());
        } else {
            PathToFile.setText("Файл не выбран!");
        }
    }




    @FXML
    public void initialize(){
        Deal_Mode.getItems().addAll(Modes_Deal);
        BlockCipherMode.getItems().addAll(CipherModes);
        Deal_Mode.setValue(Modes_Deal[0]);
        BlockCipherMode.setValue(CipherModes[0]);
    }

    @FXML
    int Button_File_Encrypt() {
        String Key = Get_String_Key.getText();
        int Deal_mode_num =128;
        if(Key.length()!=0 && absolutePath!= null){
            Error_Text.setText("");
            if(Deal_Mode.getValue().equals("Deal-128")){
                if(Key.length()>8){
                    Error_Text.setText("Ошибка, вы превысили длинну ключа!");
                    return 1;
                }
            } else if (Deal_Mode.getValue().equals("Deal-192")){
                Deal_mode_num = 192;
                if(Key.length()>12){
                    Error_Text.setText("Ошибка, вы превысили длинну ключа!");
                    return 1;
                }
            } else if(Deal_Mode.getValue().equals("Deal-256")) {
                Deal_mode_num = 256;
                if(Key.length()>16){
                    Error_Text.setText("Ошибка, вы превысили длину ключа!");
                    return 1;
                }
            }
            try {
                Thread t = new Thread(new Thread_Encrypt(absolutePath,BlockCipherMode.getValue(),Deal_mode_num,Key,
                        ProgressBar,BlockCipherMode,Deal_Mode,FindToFile,Encrypt_Button,Decrypt_Button,
                        Cancel_button, Get_String_Key, Error_Text));
                this.thread = t;
                t.setPriority(Thread.MAX_PRIORITY);
                t.start();

            } catch (SecurityException S){
                Error_Text.setText("Ошибка, файл зашифровки невозможно переименовать "+S.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Error_Text.setText("Ошибка, вы не ввели ключ, либо не указали путь к файлу!");
        }
        return 0;
    }

    @FXML
    int Button_File_Decrypt() {
        String Key = Get_String_Key.getText();
        int Deal_mode_num =128;
        if(Key.length()!= 0 && absolutePath!= null){
            Error_Text.setText("");
            if(Deal_Mode.getValue().equals("Deal-128")){
                if(Key.length()>8){
                    Error_Text.setText("Ошибка, вы превысили длину ключа!");
                    return 1;
                }
            } else if (Deal_Mode.getValue().equals("Deal-192")){
                Deal_mode_num = 192;
                if(Key.length()>12){
                    Error_Text.setText("Ошибка, вы превысили длину ключа!");
                    return 1;
                }
            } else if(Deal_Mode.getValue().equals("Deal-256")) {
                Deal_mode_num = 256;
                if(Key.length()>16){
                    Error_Text.setText("Ошибка, вы превысили длину ключа!");
                    return 1;
                }
            }
            try {
                Thread t = new Thread(new Thread_Decrypt(absolutePath,BlockCipherMode.getValue(),Deal_mode_num,Key,
                        ProgressBar,BlockCipherMode,Deal_Mode,FindToFile,Encrypt_Button,Decrypt_Button,
                        Cancel_button, Get_String_Key, Error_Text));
                thread = t;
                t.setPriority(Thread.MAX_PRIORITY);
                t.start();
            } catch (Exception S){
                Error_Text.setText("Ошибка, файл зашифровки невозможно переименовать "+S.getMessage());
            }

        } else {
            Error_Text.setText("Ошибка, вы не ввели ключ, либо не указали путь к файлу!");
        }
        return 0;
    }


    @FXML
    void Button_Cancel(){
        thread.interrupt();
    }

}


