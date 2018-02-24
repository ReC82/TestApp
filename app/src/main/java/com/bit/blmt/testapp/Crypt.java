package com.bit.blmt.testapp;

import android.app.Application;

import java.lang.reflect.Array;

/**
 * Created by blmt on 19/10/2017.
 */

public class Crypt {
    private  String toEncrypt, keyString;
    private int keyInt = 0;
    private boolean isCrypted;

    public boolean isCrypted() {
        return isCrypted;
    }

    public void setCrypted(boolean crypted) {
        isCrypted = crypted;
    }

    public String CaesarCipherInt(String mystring)
    {
        int tmpAsciiChar;
        String finalString = "";
        if(this.getKeyInt() != 0)
        {
            for(char c: mystring.toCharArray())
            {
                tmpAsciiChar = (int) c;
                tmpAsciiChar += keyInt;
                while(tmpAsciiChar > 255)
                {
                    tmpAsciiChar = tmpAsciiChar - 255;
                }
                finalString += Character.toString ((char) tmpAsciiChar);
            }
        }
        return finalString;
    }

    public String CaesarCipherString(String mystring)
    {
        int tmpAsciiChar, count = 0;
        String finalString = "";
        char keyArray[] = keyString.toCharArray();

        for(char c: mystring.toCharArray())
        {

            tmpAsciiChar = (int) c;
            tmpAsciiChar += (int) keyArray[count];
            while(tmpAsciiChar > 255)
            {
                tmpAsciiChar = tmpAsciiChar - 255;
            }

            count++;
            if(count >= keyArray.length)
            {
                count = 0;
            }
            finalString += Character.toString((char) tmpAsciiChar);
        }
        return finalString;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public int getKeyInt() {
        return keyInt;
    }

    public void setKeyInt(int keyInt) {
        this.keyInt = keyInt;
    }

    public void setToEncrypt(String toEncrypt) {
        this.toEncrypt = toEncrypt;
    }
}
