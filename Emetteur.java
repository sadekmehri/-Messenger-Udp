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

public class Emetteur {

    private static DatagramSocket socketReceive;
	private static DatagramSocket socketSend;
	private static Scanner sc;

	public static void main(String[] args) {
        // TODO Auto-generated method stub

        try {

            final int portSend = 8080;
            final int portReceive = 8081;
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

                // Send
                System.out.println("Emetteur says ["+dtf.format(now)+"] :");
                String ch = sc.nextLine().trim();
                int lg = ch.length();
                byte tab[] = new byte[lg];
                tab = ch.getBytes();
                DatagramPacket paquetSend = new DatagramPacket(tab, lg, ip, portSend);
                socketSend.send(paquetSend);

                if (ch.equals("end"))
                    break;

                // Receive
                socketReceive.receive(paquetReceived);
                System.out.println("Recepteur said : "+dtf.format(now)+"] :\n"+new String(Buffer));

            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error details", JOptionPane.ERROR_MESSAGE);
        }


    }

}