package com.tian.apply.network;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by logan on 18/5/9.
 */
public class NetworkUtil {

    public static ArrayList<String> getLocalIP(String startWiths) throws Exception {
        ArrayList<String> iplist = new ArrayList<String>();
        boolean loop = true;
        String bindip = null;
        Enumeration<?> network;
        List<NetworkInterface> netlist = new ArrayList<NetworkInterface>();
        try {
            network = NetworkInterface.getNetworkInterfaces();

            while (network.hasMoreElements()) {
                loop = true;
                NetworkInterface ni = (NetworkInterface) network.nextElement();

                if (ni.isLoopback() || ni.getName().startsWith("eth") == false) {
                    continue;
                }
                netlist.add(0, ni);

                InetAddress ip = null;
//System.out.println(ni.getDisplayName() + " " + ni.getName());
                for (NetworkInterface list : netlist) {
                    if (loop == false) break;
                    Enumeration<?> card = list.getInetAddresses();
                    while (card.hasMoreElements()) {
                        while (true) {
                            ip = null;
                            try {
                                ip = (InetAddress) card.nextElement();
                            } catch (Exception e) {

                            }
                            if (ip == null) {
                                break;
                            }
                            if (!ip.isLoopbackAddress()) {
                                if (ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
                                    continue;
                                }
                            }
                            if (ip instanceof Inet6Address) {
                                continue;
                            }
                            if (ip instanceof Inet4Address) {
                                bindip = ip.getHostAddress();

                                if (bindip.startsWith(startWiths)) {
                                    boolean addto = true;
                                    for (int n = 0; n < iplist.size(); n++) {
                                        if (bindip.equals((String) iplist.get(n))) {
                                            addto = false;
                                            break;
                                        }
                                    }
                                    if (addto) {
                                        iplist.add(bindip);
                                    }
                                } else {
//System.out.println(bindip + " is out");
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        netlist = null;
        System.out.println("==============");
        for (int n = 0; n < iplist.size(); n++) {
            System.out.println(n + ": " + iplist.get(n));
        }
        System.out.println("==============");
        return iplist;
    }

    public static void main(String[] args) throws Exception{
        getLocalIP("192");
    }
}
