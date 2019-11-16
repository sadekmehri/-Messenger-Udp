/*
 * Copyright (C) Sdigos to present all rights reserved.
 */

package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Recepteur {

    private static Scanner sc;
	private static DatagramSocket socketSend;
	private static DatagramSocket socketReceive;

	public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub

        try {

            final int portSend = 8081;
            final int portReceive = 8080;
            final int Length = 1024;
            byte Buffer[] = new byte[Length];
            sc = new Scanner(System.in);

            InetAddress ip = InetAddress.getLocalHost();
            socketSend = new DatagramSocket();
            socketReceive = new DatagramSocket(portReceive);
            DatagramPacket paquetReceived = new DatagramPacket(Buffer, Buffer.length);
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        	LocalDateTime now = LocalDateTime.now();

            while (true) {

                // Receive
                socketReceive.receive(paquetReceived);
                System.out.println("Emetteur said : "+dtf.format(now)+"] :\n"+new String(Buffer));

                // Send
                System.out.println("Recepteur says ["+dtf.format(now)+"] :");
                String ch = sc.nextLine().trim();
                int lg = ch.length();
                byte tab[] = new byte[lg];
                tab = ch.getBytes();
                DatagramPacket paquetSend = new DatagramPacket(tab, lg, ip, portSend);
                socketSend.send(paquetSend);

                if (ch.equals("end"))
                    break;

            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error details", JOptionPane.ERROR_MESSAGE);
        }

    }

}