package com.bit.blmt.testapp;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by blmt on 23/10/2017.
 */

public class Indentity {

    private static String id_name;
    private static String id_mac;
    private static boolean isAuthorized;

    public String Authorized_Mac[] = new String[10];
    public String Authorized_Users[] = new String[10];

    public Indentity() {
        // TEST BLMT
        Authorized_Users[0] = "test_blmt";
        Authorized_Mac[0] = "02:00:00:44:55:66";
        // BLMT PHONE
        Authorized_Users[1] = "blmt";
        Authorized_Mac[1] = "4C:66:41:FA:9A:0E";

        //Check Current Mac if is Authorized
        setAuthorized(CheckIsAuthorized(getMacAddr()));
    }

    public boolean CheckIsAuthorized(String Mac)
    {
        //System.out.println("Check if " + Mac + " is authorized.");
        for(int i = 0; i < Authorized_Mac.length; i++)
        {
            //System.out.println("Compare "+ i + "-" + Authorized_Mac[i] + " to " + Mac );
            if(Authorized_Mac[i].equals(Mac))
            {
                System.out.println("A match has been found !");
                setId_mac(Authorized_Mac[i]);
                setId_name(Authorized_Users[i]);
                this.setAuthorized(true);
                return true;
            }
        }
        System.out.println("No Match found");
        this.setAuthorized(false);
        return false;
    }


    public void ShowObject()
    {
        System.out.println("Name" + this.getId_name());
        System.out.println("Mac" + this.getId_mac());
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) { this.id_name = id_name; }

    public String getId_mac() {
        return id_mac;
    }

    public void setId_mac(String id_mac) {
        this.id_mac = id_mac;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
