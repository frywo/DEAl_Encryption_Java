package com.example.deal;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class Deal {

    public static byte[][] Get_keys(String key, int length){
        byte[] Key_byte = key.getBytes();
        byte[] Key_bits = Deal.Key_ByteToBits(Key_byte,length);
        byte[][] Round_keys;
        if (length==128){
            Round_keys = Deal.Get_round_keys_128(Key_bits);
        } else if (length==192){
            Round_keys = Deal.Get_round_keys_192(Key_bits);
        } else {
            Round_keys = Deal.Get_round_keys_256(Key_bits);
        }
        return Round_keys;
    }
    public static byte[] Key_ByteToBits(byte[] arr, int num) {
        byte[][] y = new byte[arr.length][8];
        if(num ==128){
            byte[] qwe = new byte[128];
            for (int j = 0; j< arr.length;++j){
                for(int i = 0; i< 8;++i) {
                    y[j][y[0].length-i-1] = (byte) ((arr[j] >> i)&1);
                }
                System.arraycopy(y[j], 0, qwe,j*8, 8);
            }
            return qwe;
        }
        if (num==192){
            byte[] qwe = new byte[192];
            for (int j = 0; j< arr.length;++j){
                for(int i = 0; i< 8;++i) {
                    y[j][y[0].length-i-1] = (byte) ((arr[j] >> i)&1);
                }
                System.arraycopy(y[j], 0, qwe,j*8, 8);
            }
            return qwe;

        }
        if(num==256){
            byte[] qwe = new byte[256];
            for (int j = 0; j< arr.length;++j){
                for(int i = 0; i< 8;++i) {
                    y[j][y[0].length-i-1] = (byte) ((arr[j] >> i)&1);
                }
                System.arraycopy(y[j], 0, qwe,j*8, 8);
            }
            return qwe;
        }

        return y[0]; // никогда не должно возвращать это значение
    }
    public static byte[][] Get_round_keys_128(byte[] key){ // ключи по 128 бит разбиваем на 2 K1 и K2
        byte[] Static_key = {0,1,0,0,0,0,1,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,0,1,1,1,0,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,0,1,1,1,0,0,1,0};
        byte[][] Xor_i = new byte[4][64];
        Xor_i[0][1] =1;
        Xor_i[1][2] =1;
        Xor_i[2][4] =1;
        Xor_i[3][8] =1;
        byte [][] Round_keys = new byte[6][64];
        byte[] K1 = new byte[64];
        byte[] K2 = new byte[64];


        System.arraycopy(key,0,K1,0,64);
        System.arraycopy(key,64,K2,0,64);
        Round_keys[0] = Des.Encrypt(K1,Static_key);//шифруем ключ K1 по статическому ключу
        Round_keys[1] = Des.Encrypt(Des.XOR(K2,Round_keys[0]),Static_key);//шифруем K2 xor Round_key1 по статическому ключу
        Round_keys[2] = Des.Encrypt(Des.XOR(Des.XOR(K1,Xor_i[0]),Round_keys[1]),Static_key);
        Round_keys[3] = Des.Encrypt(Des.XOR(Des.XOR(K2,Xor_i[1]),Round_keys[2]),Static_key);
        Round_keys[4] = Des.Encrypt(Des.XOR(Des.XOR(K1,Xor_i[2]),Round_keys[3]),Static_key);
        Round_keys[5] = Des.Encrypt(Des.XOR(Des.XOR(K2,Xor_i[3]),Round_keys[4]),Static_key);

        return Round_keys;
    }

    public static byte[][] Get_round_keys_192(byte[] key){
        byte[] Static_key = {0,1,0,0,0,0,1,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,0,1,1,1,0,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,0,1,1,1,0,0,1,0};
        byte[][] Xor_i = new byte[3][64];
        Xor_i[0][1] = 1;
        Xor_i[1][2] = 1;
        Xor_i[2][4] = 1;

        byte [][] Round_keys = new byte[6][64];
        byte[] K1 = new byte[64];
        byte[] K2 = new byte[64];
        byte[] K3 = new byte[64];
        System.arraycopy(key,0,K1,0,64);
        System.arraycopy(key,64,K2,0,64);
        System.arraycopy(key,128,K2,0,64);

        Round_keys[0] = Des.Encrypt(K1,Static_key);//шифруем ключ K1 по статическому ключу
        Round_keys[1] = Des.Encrypt(Des.XOR(K2,Round_keys[0]),Static_key);//шифруем K2 xor Round_key1 по статическому ключу
        Round_keys[2] = Des.Encrypt(Des.XOR(K1,Round_keys[1]),Static_key);
        Round_keys[3] = Des.Encrypt(Des.XOR(Des.XOR(K2,Xor_i[0]),Round_keys[2]),Static_key);
        Round_keys[4] = Des.Encrypt(Des.XOR(Des.XOR(K1,Xor_i[1]),Round_keys[3]),Static_key);
        Round_keys[5] = Des.Encrypt(Des.XOR(Des.XOR(K3,Xor_i[2]),Round_keys[4]),Static_key);

        return Round_keys;
    }

    public static byte[][] Get_round_keys_256(byte[] key){
        byte[] Static_key = {0,1,0,0,0,0,1,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,0,1,1,1,0,0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,0,1,1,1,0,0,1,0};

        byte[][] Xor_i = new byte[4][64];
        Xor_i[0][1] =1;
        Xor_i[1][2] =1;
        Xor_i[2][4] =1;
        Xor_i[3][8] =1;

        byte [][] Round_keys = new byte[8][64];

        byte[] K1 = new byte[64];
        byte[] K2 = new byte[64];
        byte[] K3 = new byte[64];
        byte[] K4 = new byte[64];
        System.arraycopy(key,0,K1,0,64);
        System.arraycopy(key,64,K2,0,64);
        System.arraycopy(key,128,K2,0,64);
        System.arraycopy(key,192,K2,0,64);

        Round_keys[0] = Des.Encrypt(K1,Static_key);
        Round_keys[1] = Des.Encrypt(Des.XOR(K2,Round_keys[0]),Static_key);
        Round_keys[2] = Des.Encrypt(Des.XOR(K3,Round_keys[1]),Static_key);
        Round_keys[3] = Des.Encrypt(Des.XOR(K4,Round_keys[2]),Static_key);
        Round_keys[4] = Des.Encrypt(Des.XOR(Des.XOR(K1,Xor_i[0]),Round_keys[3]),Static_key);
        Round_keys[5] = Des.Encrypt(Des.XOR(Des.XOR(K2,Xor_i[1]),Round_keys[4]),Static_key);
        Round_keys[6] = Des.Encrypt(Des.XOR(Des.XOR(K3,Xor_i[2]),Round_keys[5]),Static_key);
        Round_keys[7] = Des.Encrypt(Des.XOR(Des.XOR(K4,Xor_i[3]),Round_keys[6]),Static_key);

        return Round_keys;
    }

    public static byte[] Encryption_Deal(byte[] Plain_text, byte[][] Round_keys, int num){
        byte[] Side;
        byte[][] L_and_R = Des.Split(Plain_text);
        byte[] result = new byte[128];
        if(num==256){
            for(int i =0; i<8;++i){
                L_and_R[1] = Des.XOR(Des.Encrypt(L_and_R[0],Round_keys[i]),L_and_R[1]);
                Side = L_and_R[0];
                L_and_R[0] = L_and_R[1];
                L_and_R[1] = Side;
            }
        } else {
            for(int i =0; i<6;++i){
                L_and_R[1] = Des.XOR(Des.Encrypt(L_and_R[0],Round_keys[i]),L_and_R[1]);
                Side = L_and_R[0];
                L_and_R[0] = L_and_R[1];
                L_and_R[1] = Side;
            }
        }

        System.arraycopy(L_and_R[0],0,result,0,64);
        System.arraycopy(L_and_R[1],0,result,64,64);
        return result;
    }
    public static byte[] Decryption_Deal(byte[] Plain_text, byte[][] Round_keys, int num){
        byte[] Side;
        byte[][] L_and_R = Des.Split(Plain_text);
        byte[] result = new byte[128];
        if(num == 256){
            for(int i =7; i>=0;--i){
                L_and_R[0] = Des.XOR(Des.Encrypt(L_and_R[1],Round_keys[i]),L_and_R[0]);
                Side = L_and_R[0];
                L_and_R[0] = L_and_R[1];
                L_and_R[1] = Side;
            }
        } else {
            for(int i =5; i>=0;--i){
                L_and_R[0] = Des.XOR(Des.Encrypt(L_and_R[1],Round_keys[i]),L_and_R[0]);
                Side = L_and_R[0];
                L_and_R[0] = L_and_R[1];
                L_and_R[1] = Side;
            }
        }

        System.arraycopy(L_and_R[0],0,result,0,64);
        System.arraycopy(L_and_R[1],0,result,64,64);
        return result;
    }





    public static byte[] ByteToBits(byte[] arr) {
        byte[][] y = new byte[arr.length][8];
        byte[] qwe = new byte[128];
        for (int j = 0; j< arr.length;++j){
            for(int i = 0; i< 8;++i) {
                y[j][y[0].length-i-1] = (byte) ((arr[j] >> i)&1);
            }
            System.arraycopy(y[j], 0, qwe,j*8, 8);
        }

        return qwe;
    }
    public static byte[] BitsToByte(byte[] in) {
        byte[] str = new byte[16];
        byte[] bits = Arrays.copyOf(in,in.length);
        for(int j = 0; j<16;++j){
            if(bits[8*j]==1){

                /*-1*/
                for(int i =7;i>=0;--i){
                    if(bits[j*8+i]==0){
                        bits[j*8+i]=1;
                    } else {
                        bits[j*8+i]=0;
                        break;
                    }
                }

                /*inversion*/
                for (int i =0; i<8;++i){
                    if(bits[i+8*j]==1){
                        bits[i+8*j]=0;
                    } else {
                        bits[i+8*j]=1;
                    }
                }

                for(int i = 7; i>0;--i){
                    str[j]+= bits[j*8+i]*Math.pow(2,7-i);
                }
                str[j]*=-1;
                if(str[j]==0){
                    str[j]+=128;
                }
            } else {
                for(int i = 7; i>0;--i){
                    str[j]+= bits[j*8+i]*Math.pow(2,7-i);
                }
            }
        }
        return str;
    }


    public static byte[] EBC_Encryption (byte[] arr_buff, byte[][] key,int Length){
        byte[] arr_bytes = new byte[16];
        byte[] arr_bits;
        byte[] arr_res= new byte[arr_buff.length];
        for (int i =0; i<arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_bytes,0,16);
            arr_bits = Deal.ByteToBits(arr_bytes);
            arr_bits = Deal.Encryption_Deal(arr_bits, key, Length);
            arr_bytes = Deal.BitsToByte(arr_bits);
            System.arraycopy(arr_bytes,0,arr_res,i,16);
        }
        return arr_res;
    } //шифрует буффер
    public static byte[] EBC_Decryption (byte[] arr_buff, byte[][] key,int Length){
        byte[] arr_bytes = new byte[16];
        byte[] arr_bits;
        byte[] arr_res= new byte[arr_buff.length];
        for (int i =0; i<arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_bytes,0,16);
            arr_bits = Deal.ByteToBits(arr_bytes);
            arr_bits = Deal.Decryption_Deal(arr_bits, key,Length);
            arr_bytes = Deal.BitsToByte(arr_bits);
            System.arraycopy(arr_bytes,0,arr_res,i,16);
        }
        return arr_res;
    } //дешифрует буффер




    public static byte[] CBC_Encryption (byte[] arr_buff, byte[][] key, byte[] IV1, int Length){
        byte[] IV = Arrays.copyOf(IV1,IV1.length);
        byte[] arr_byte = new byte[16];
        byte[] arr_byte_res;
        byte[] arr_bits;
        byte[] res = new byte[arr_buff.length];

        for(int i =0; i< arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_byte,0,16);
            arr_bits = Deal.ByteToBits(arr_byte);

            arr_bits = Des.XOR(IV,arr_bits);
            arr_bits = Deal.Encryption_Deal(arr_bits,key,Length);

            IV = Arrays.copyOf(arr_bits,arr_bits.length);

            arr_byte_res = Deal.BitsToByte(arr_bits);
            System.arraycopy(arr_byte_res,0,res,i,16);
        }
        return res;
    }
    public static byte[] CBC_Decryption (byte[] arr_buff, byte[][] key, byte[] IV1, int Length){
        byte[] IV = Arrays.copyOf(IV1,IV1.length);
        byte[] IV2 = new byte[128];
        byte[] arr_byte = new byte[16];
        byte[] arr_byte_res;
        byte[] arr_bits;
        byte[] res = new byte[arr_buff.length];

        for(int i =0; i< arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_byte,0,16);
            arr_bits = Deal.ByteToBits(arr_byte);

            IV2 = Arrays.copyOf(arr_bits,arr_bits.length);

            arr_bits = Deal.Decryption_Deal(arr_bits,key,Length);
            arr_bits = Des.XOR(IV,arr_bits);

            IV = Arrays.copyOf(IV2,IV2.length);

            arr_byte_res = Deal.BitsToByte(arr_bits);
            System.arraycopy(arr_byte_res,0,res,i,16);
        }
        return res;
    }


    public static byte[] CFB_Encryption(byte[] arr_buff, byte[][] key, byte[] IV1, int Length){
        byte[] IV = Arrays.copyOf(IV1,IV1.length);
        byte[] arr_byte = new byte[16];
        byte[] arr_byte_res;
        byte[] arr_bits;
        byte[] res = new byte[arr_buff.length];

        for(int i =0; i< arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_byte,0,16);
            arr_bits = Deal.ByteToBits(arr_byte);



            IV  = Deal.Encryption_Deal(IV,key,Length);
            IV = Des.XOR(IV,arr_bits);

            arr_byte_res = Deal.BitsToByte(IV);

            System.arraycopy(arr_byte_res,0,res,i,16);
        }
        return res;
    }
    public static byte[] CFB_Decryption(byte[] arr_buff, byte[][] key, byte[] IV1,int Length){
        byte[] IV = Arrays.copyOf(IV1,IV1.length);

        byte[] arr_byte = new byte[16];
        byte[] arr_byte_res;
        byte[] arr_bits;
        byte[] res = new byte[arr_buff.length];

        for(int i =0; i< arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_byte,0,16);
            arr_bits = Deal.ByteToBits(arr_byte);



            IV  = Deal.Encryption_Deal(IV,key, Length);
            IV = Des.XOR(IV,arr_bits);

            arr_byte_res = Deal.BitsToByte(IV);

            System.arraycopy(arr_byte_res,0,res,i,16);
            IV = Arrays.copyOf(arr_bits,arr_bits.length);

        }
        return res;
    }



    public static byte[] OFB_Encryption(byte[] arr_buff, byte[][] key, byte[] IV1, int Length){
        byte[] arr_byte = new byte[16];
        byte[] arr_byte_res;
        byte[] arr_bits;
        byte[] res = new byte[arr_buff.length];

        for(int i =0; i< arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_byte,0,16);
            arr_bits = Deal.ByteToBits(arr_byte);



            IV1  = Deal.Encryption_Deal(IV1,key,Length);
            arr_bits = Des.XOR(IV1,arr_bits);

            arr_byte_res = Deal.BitsToByte(arr_bits);

            System.arraycopy(arr_byte_res,0,res,i,16);
        }
        return res;
    }
    public static byte[] OFB_Decryption(byte[] arr_buff, byte[][] key, byte[] IV, int Length){
//        byte[] IV;
//        IV = Arrays.copyOf(IV1,IV1.length);

        byte[] arr_byte = new byte[16];
        byte[] arr_byte_res;
        byte[] arr_bits;
        byte[] res = new byte[arr_buff.length];

        for(int i =0; i< arr_buff.length;i+=16){
            System.arraycopy(arr_buff,i,arr_byte,0,16);
            arr_bits = Deal.ByteToBits(arr_byte);



            IV  = Deal.Encryption_Deal(IV,key,Length);
            arr_bits = Des.XOR(IV,arr_bits);

            arr_byte_res = Deal.BitsToByte(arr_bits);

            System.arraycopy(arr_byte_res,0,res,i,16);

        }
        return res;
    }


    public static byte[] File_get_buff(FileInputStream inputStream) throws IOException {
        int bufferSize = 16000;
        byte[] buffer = new byte[bufferSize];

        if (inputStream.available() <= 16000) { // исключение
            byte[] last_buffer = new byte[inputStream.available()];
            inputStream.read(last_buffer, 0, inputStream.available()); // исключение
            return last_buffer;
        } else {
            inputStream.read(buffer, 0, bufferSize); // исключение
            return buffer;
        }
    }
    public static byte[] File_get_buff_Decrypt(FileInputStream inputStream) throws IOException {
        int bufferSize = 16000;
        byte[] buffer = new byte[bufferSize];

        if (inputStream.available() <= 16001) { // исключение
            byte[] last_buffer = new byte[inputStream.available()-1];
            inputStream.read(last_buffer, 0, inputStream.available()-1); // исключение
            return last_buffer;
        } else {
            inputStream.read(buffer, 0, bufferSize); // исключение
            return buffer;
        }
    }





    public static int File_Encrypt(String name_file, String Mode, int length_key, String key, ProgressBar pb) throws Exception {
        byte[] IV_Byte = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        byte[] IV = Deal.ByteToBits(IV_Byte);
        String File_new_name = name_file+"qqq";
        FileOutputStream New_File = new FileOutputStream(File_new_name);  // исключение обработать
        FileInputStream inputStream = new FileInputStream(name_file); // исключение


        double bp_Max = inputStream.available();
        double progress = 0;

        int check = inputStream.available();

        int count = 0; // сколько байт добавлено
        byte[][] Round_keys = Deal.Get_keys(key,length_key); //  надо будет ограничить длинну ключа
        if (inputStream.available()!=0){
            while (inputStream.available() > 0 && !Thread.currentThread().isInterrupted()&& Thread.activeCount()!=5) { // исключение
                byte[] buffer  = Deal.File_get_buff(inputStream);

                if((check-= buffer.length)!=inputStream.available()){
                    inputStream.close();
                    New_File.close();
                    File Decrypted_file = new File(File_new_name);
                    Decrypted_file.delete();
                }

                if (inputStream.available() ==0){
                    while ((buffer.length+count)%16!=0){++count;}
                    byte[] Last_buffer = new byte[buffer.length+count]; // добавил нули к последнему буфферу
                    System.arraycopy(buffer,0,Last_buffer,0,buffer.length);
                    if(Objects.equals(Mode, "EBC")){
                        buffer = Deal.EBC_Encryption(Last_buffer,Round_keys,length_key);
                    } else if (Objects.equals(Mode, "CBC")){
                        buffer = Deal.CBC_Encryption(Last_buffer,Round_keys,IV,length_key);
                    } else if (Objects.equals(Mode, "CFB")){
                        buffer = Deal.CFB_Encryption(Last_buffer,Round_keys,IV,length_key);
                    } else if (Objects.equals(Mode, "OFB")){
                        buffer = Deal.OFB_Encryption(Last_buffer,Round_keys,IV,length_key);
                    }
                    New_File.write(buffer);
                    New_File.write(count);
                } else {
                    if(Objects.equals(Mode, "EBC")){
                        buffer = Deal.EBC_Encryption(buffer,Round_keys,length_key);
                    } else if (Objects.equals(Mode, "CBC")){ // работает
                        buffer = Deal.CBC_Encryption(buffer,Round_keys,IV,length_key);
                        System.arraycopy(buffer,buffer.length-17,IV_Byte,0,16);
                        IV = Deal.ByteToBits(IV_Byte);
                    } else if (Objects.equals(Mode, "CFB")){ // работает
                        buffer = Deal.CFB_Encryption(buffer,Round_keys,IV,length_key);
                        System.arraycopy(buffer,buffer.length-17,IV_Byte,0,16);
                        IV = Deal.ByteToBits(IV_Byte);
                    } else if (Objects.equals(Mode, "OFB")){ // работает
                        buffer = Deal.OFB_Encryption(buffer,Round_keys,IV,length_key);
                        for (int j =0; j< buffer.length/16;++j){
                            IV = Deal.Encryption_Deal(IV,Round_keys,length_key);
                        }
                    }
                    New_File.write(buffer);
                }
                progress += buffer.length;
                pb.setProgress(progress/bp_Max);
            }
            if(Thread.currentThread().isInterrupted()|| Thread.activeCount() == 5){
                inputStream.close();
                New_File.close();
                File Encrypted_file = new File(File_new_name);
                Encrypted_file.delete();
            } else {
                inputStream.close();
                New_File.close();
                File not_encrypted_file = new File(name_file);
                File Encrypted_file = new File(File_new_name);
                if (not_encrypted_file.delete()){
                    Encrypted_file.renameTo(not_encrypted_file);
                } else {
                    return 3;
                }
            }
        } else {
            inputStream.close();
            New_File.close();
            File Encrypted_file = new File(File_new_name);
            Encrypted_file.delete();
            return 1;
        }
        pb.setProgress(0);
        return 0;
    }

    public static int File_Decrypt (String name_file, String Mode, int length_key, String key, ProgressBar pb) throws Exception {
        byte[] IV_Byte = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        byte[] IV = Deal.ByteToBits(IV_Byte);
        String File_new_name = name_file+"qqq";
        FileOutputStream New_File = new FileOutputStream(File_new_name);  // исключение обработать
        FileInputStream inputStream = new FileInputStream(name_file); // исключение

        byte[][] Round_keys = Deal.Get_keys(key,length_key);
        byte[] count = new byte[1];

        double bp_Max = inputStream.available();
        double progress = 0;

        int check = inputStream.available();

        if ((inputStream.available()-1)%16==0 || inputStream.available() == 0){
            while (inputStream.available() > 0 && !Thread.currentThread().isInterrupted()&& Thread.activeCount()!=5) {// исключение


                byte[] buffer  = Deal.File_get_buff_Decrypt(inputStream);
                if((check-= buffer.length)!=inputStream.available()){
                    inputStream.close();
                    New_File.close();
                    File Decrypted_file = new File(File_new_name);
                    Decrypted_file.delete();
                }
                if (inputStream.available() == 1){
                    if(inputStream.read(count)==-1){
                        throw new Exception("Read_Problem") ;
                    }
                    if(Objects.equals(Mode, "EBC")){
                        buffer = Deal.EBC_Decryption(buffer,Round_keys,length_key);
                    } else if (Objects.equals(Mode, "CBC")){
                        buffer = Deal.CBC_Decryption(buffer,Round_keys,IV,length_key);
                    } else if (Objects.equals(Mode, "CFB")){
                        buffer = Deal.CFB_Decryption(buffer,Round_keys,IV,length_key);
                    } else if (Objects.equals(Mode, "OFB")){
                        buffer = Deal.OFB_Decryption(buffer,Round_keys,IV,length_key);
                    }
                    New_File.write(buffer,0,buffer.length-count[0]);
                } else {
                    if(Objects.equals(Mode, "EBC")){
                        buffer = Deal.EBC_Decryption(buffer,Round_keys,length_key);
                    } else if (Objects.equals(Mode, "CBC")){
                        System.arraycopy(buffer,buffer.length-17,IV_Byte,0,16);
                        buffer = Deal.CBC_Decryption(buffer,Round_keys,IV,length_key);
                        IV = Deal.ByteToBits(IV_Byte);
                    } else if (Objects.equals(Mode, "CFB")){
                        System.arraycopy(buffer,buffer.length-17,IV_Byte,0,16);
                        buffer = Deal.CFB_Decryption(buffer,Round_keys,IV,length_key);
                        IV = Deal.ByteToBits(IV_Byte);
                    } else if (Objects.equals(Mode, "OFB")){
                        buffer = Deal.OFB_Decryption(buffer,Round_keys,IV,length_key);
                        for (int j =0; j< buffer.length/16;++j){
                            IV = Deal.Encryption_Deal(IV,Round_keys,length_key);
                        }
                    }
                    New_File.write(buffer);
                }


                progress += buffer.length;
                pb.setProgress(progress/bp_Max);
            }
            if(Thread.currentThread().isInterrupted() || Thread.activeCount()==5){
                inputStream.close();
                New_File.close();
                File Decrypted_file = new File(File_new_name);
                Decrypted_file.delete();
            } else {
                inputStream.close();
                New_File.close();
                File not_Decrypted_file = new File(name_file);
                File Decrypted_file = new File(File_new_name);
                if (not_Decrypted_file.delete()){
                    Decrypted_file.renameTo(not_Decrypted_file);
                } else {
                    return 3;
                }
            }
        } else {
            inputStream.close();
            New_File.close();
            File Decrypted_file = new File(File_new_name);
            Decrypted_file.delete();
            return 1;
        }


        pb.setProgress(0);
        return 0;
    }
}
