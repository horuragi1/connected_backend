package com.example.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IPAddressUtil {

    public static String getIPAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                // 루프백 인터페이스 및 비활성 인터페이스 제외
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();

                    // IPv4 주소만 반환 (IPv6 제외)
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().indexOf(':') == -1) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unable to determine IP address";
    }
}
