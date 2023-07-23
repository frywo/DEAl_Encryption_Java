package com.example.deal;

import java.util.Arrays;

public final class Des {
    //начальная перестановка IP
    public static int[] IP = {
            58,50,42,34,26,18,10,2,
            60,52,44,36,28,20,12,4,
            62,54,46,38,30,22,14,6,
            64,56,48,40,32,24,16,8,
            57,49,41,33,25,17, 9,1,
            59,51,43,35,27,19,11,3,
            61,53,45,37,29,21,13,5,
            63,55,47,39,31,23,15,7 }; // 64-bits

    //Конечная перестановка IP-1
    static int[] IP1 = {
            40,8,48,16,56,24,64,32,
            39,7,47,15,55,23,63,31,
            38,6,46,14,54,22,62,30,
            37,5,45,13,53,21,61,29,
            36,4,44,12,52,20,60,28,
            35,3,43,11,51,19,59,27,
            34,2,42,10,50,18,58,26,
            33,1,41,9,49,17,57,25 }; // 64-bits
    static int [] PC1 = {
            57,49,41,33,25,17,9,
            1,58,50,42,34,26,18,
            10,2,59,51,43,35,27,
            19,11,3,60,52,44,36,
            63,55,47,39,31,23,15,
            7,62,54,46,38,30,22,
            14,6,61,53,45,37,29,
            21,13,5,28,20,12,4}; // 56-bits

    // second key-Permutation Table
    static int[] PC2 = {
            14,17,11,24,1,5,
            3,28,15,6,21,10,
            23,19,12,4,26,8,
            16,7,27,20,13,2,
            41,52,31,37,47,55,
            30,40,51,45,33,48,
            44,49,39,56,34,53,
            46,42,50,36,29,32}; //48-bits

    static int[] EP = {
            32,1,2,3,4,5,
            4,5,6,7,8,9,
            8,9,10,11,12,13,
            12,13,14,15,16,17,
            16,17,18,19,20,21,
            20,21,22,23,24,25,
            24,25,26,27,28,29,
            28,29,30,31,32,1}; // 48-bits


    // Straight Permutation Table
    static int[] P = {
            16,7,20,21,
            29,12,28,17,
            1,15,23,26,
            5,18,31,10,
            2,8,24,14,
            32,27,3,9,
            19,13,30,6,
            22,11,4,25}; //32-bits

    // S-box Table
    static int[][][] S_box = {
            {{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                    {0,15,7,4,15,2,13,1,10,6,12,11,9,5,3,8},
                    {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
                    {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}},

            {{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
                    {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
                    {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
                    {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}},

            {{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
                    {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
                    {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
                    {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}},

            {{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
                    {12,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
                    {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
                    {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}},

            {{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
                    {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
                    {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
                    {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}},

            {{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
                    {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
                    {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
                    {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}},

            {{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
                    {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
                    {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
                    {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}},

            {{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
                    {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
                    {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
                    {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}}
    };


    static int[] shiftBits = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};




//    private static byte[] ByteToBits(byte[] arr) {
//        byte[][] y = new byte[arr.length][8];
//        byte[] qwe = new byte[64];
//        for (int j = 0; j< arr.length;++j){
//            for(int i = 0; i< 8;++i) {
//                y[j][y.length-i-1] = (byte) ((arr[j] >> i)&1);
//            }
//            System.arraycopy(y[j], 0, qwe,j*8, 8);
//        }
//
//        return qwe;
//    }


    public static byte[][] Split(byte[] arr){
        byte[][] bits = new byte[2][arr.length/2];
        for (int i = 0; i < arr.length/2;++i) {
            bits[0][i] = arr[i];
            bits[1][i] = arr[i+arr.length/2];

        }
        return bits;
    }


//   private static byte[] BitsToByte(byte[] bits) {
//        byte[] str = new byte[8];
//        for(int j = 0; j<8;++j){
//            if(bits[8*j]==1){
//
//                /*-1*/
//                for(int i =7;i>=0;--i){
//                    if(bits[j*8+i]==0){
//                        bits[j*8+i]=1;
//                    } else {
//                        bits[j*8+i]=0;
//                        break;
//                    }
//                }
//
//                /*inversion*/
//                for (int i =0; i<8;++i){
//                    if(bits[i+8*j]==1){
//                        bits[i+8*j]=0;
//                    } else {
//                        bits[i+8*j]=1;
//                    }
//                }
//
//                for(int i = 7; i>0;--i){
//                    str[j]+= bits[j*8+i]*Math.pow(2,7-i);
//                }
//                str[j]*=-1;
//                if(str[j]==0){
//                    str[j]+=128;
//                }
//            } else {
//                for(int i = 7; i>0;--i){
//                    str[j]+= bits[j*8+i]*Math.pow(2,7-i);
//                }
//            }
//        }
//        return str;
//    }


    public static byte[] XOR(byte[] L, byte[] R) {
        byte[] L1 = Arrays.copyOf(L,L.length);
        byte[] R1 = Arrays.copyOf(R,R.length);
        for (int i = 0; i < L1.length; ++i) {
            if (L1[i] == R1[i]) {
                L1[i] = 0;
            } else {
                L1[i] = 1;
            }
        }
        return L1;
    } /* Работает 100% */


    private static byte[] Permutation(byte[] bits, int[] Table) {
        byte[] FinalPermutation = new byte[Table.length];
        for (int i = 0; i < Table.length; ++i) {
            FinalPermutation[i] =  bits[Table[i] - 1];
        }
        return FinalPermutation;
    } /* Работает 100% */


    public static byte[][] Swap(byte[][]bits){
        byte [] qwe = bits[0];
        bits[0] = bits[1];
        bits[1] = qwe;
        return bits;
    } /* Работает 100% */

    private static byte[][] ShiftLeft(byte[][] a, int iter) {
        for (int m = 0; m < iter; m++) {
            byte tmp1 = a[0][0];
            byte tmp2 = a[1][0];
            for (int i = 0; i < a[0].length - 1; i++) {
                a[0][i] = a[0][i + 1];
                a[1][i] = a[1][i + 1];
            }
            a[0][a[0].length-1] = tmp1;
            a[1][a[1].length-1] = tmp2;
        }
        return a;

    } /* Работает 100% */

    private static byte[][] Get_round_key(byte[] key){
        //key - 64 bits
        byte[][] keys = new byte[16][48];
        byte[] cpy = new byte[56];
        byte[] dop_key;

        dop_key = Permutation(key, PC1); // key - 56 bits
        byte[][] split_key = Split(dop_key); //2 keys - 28 bits

        for(int i = 0; i < 16;++i){
            split_key = ShiftLeft(split_key,shiftBits[i]);
            System.arraycopy(split_key[0], 0, cpy,0, 28);
            System.arraycopy(split_key[1], 0, cpy,28, 28);
            keys[i] = Permutation(cpy, PC2);
        }
        return keys;
    } /* Работает 100% */

    private static byte[] S_function(byte [] in, byte [] key){
        byte[] extension;
        byte[][] Long_S_blocks = new byte[8][6];
        byte[][] Short_S_blocks = new byte[8][4];
        byte[] result = new byte[32];

        int col;
        int str;

        extension = Permutation(in,EP);
        extension = XOR(extension,key);


        for(int i =0;i<8;++i)
            System.arraycopy(extension,i*6,Long_S_blocks[i],0,6); // делим массив на 8 блоков по 6 битов

        for(int i = 0; i<8;++i){
            str = Long_S_blocks[i][0]*2 + Long_S_blocks[i][5];
            col = Long_S_blocks[i][4] + Long_S_blocks[i][3]*2 + Long_S_blocks[i][2]*4 + Long_S_blocks[i][1]*8;
            for(int j =0;j<4;++j){
                Short_S_blocks[i][3-j] = (byte) ((Des.S_box[i][str][col]>> j)&1);
            }
            System.arraycopy(Short_S_blocks[i], 0, result,i*4, 4);
        }

        result = Permutation(result,P);
        return result;
    } /* Работает 100% */


    public static byte[] Encrypt (byte[] Plain_text, byte[] key){
        byte[] qwe;
        byte[] resultByte;
        byte[] resultBit = new byte[64];
        byte[] Encryption_function_part;
        //byte[] bits_Plain_text = ByteToBits(Plain_text);  // переводим в биты
        byte[] bits_Plain_text = Permutation(Plain_text,IP); // начальная перестановка
        byte[][] L_and_R = Split(bits_Plain_text); // делим на левую и правую части
        byte[][] Round_keys = Get_round_key(key); // получаем ключи раундов


        for(int i = 0; i<16;++i) { // 16 раундов
            Encryption_function_part = S_function(L_and_R[1], Round_keys[i]); // шифруем правую часть
            L_and_R[0] = XOR(Encryption_function_part, L_and_R[0]); // xor-им с левой
            L_and_R = Swap(L_and_R); // меняем меставми зашифрованную левую с незашифрованной правой
        }

        System.arraycopy(L_and_R[0],0,resultBit,0,32);
        System.arraycopy(L_and_R[1],0,resultBit,32,32);
        resultBit  = Permutation(resultBit,IP1);
        //resultByte = BitsToByte(resultBit);
        return resultBit;
    }
}




