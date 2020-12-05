/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

/**
 *
 * @author tran minh hai
 */
public class server extends javax.swing.JFrame {
    private int cong = 2000;
    private int Start = 15;
    private int End = 10;
    private DefaultListModel<String> motx = new DefaultListModel<>();
    private DefaultListModel<String> mokh = new DefaultListModel<>();
    Hashtable htbtx = new Hashtable();
    Hashtable htbkh = new Hashtable();
    ArrayList al = new ArrayList();
    ArrayList txonline = new ArrayList();
    private String string1 = null;
    //private String Urlpanel = "C:\\Users\\tran minh hai\\Documents\\JAVA PROJECT\\BaiTapLonCSDL\\picture\\Pink Patterns_Dark Stripe_ZkVj.jpg";
    private String Urlpanel = "C:\\Users\\TranMinhHai\\Documents\\NetBeansProjects\\THCSDL\\BaiTapLonCSDL\\picture\\Pink Patterns_Dark Stripe_ZkVj.jpg";
    /**
     * Creates new form server
     */
    public server() {
        initComponents();
        
        postgres_connect ss = new postgres_connect();
        Connection conn = ss.getConnectToMSSQL();
        Statement stm;
        ResultSet rs;
        try {
            // update trạng thái thành online
            stm = conn.createStatement();
            stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"TrangThai\" = '1' WHERE \"TenTK\" LIKE 'hedspi@hedspi';");
            // đọc danh sách tài xế ==> jlist1
            rs = stm.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"TXID\" ASC;");
            String txid = null,txhoten = null,txname = null;
            while(rs.next()) {
                txid = rs.getString("TXID");
                txhoten = rs.getString("HoTen");
                txname = txid + " | " + txhoten;
                if(rs.getInt("TrangThai")==1) txname=String.format("<html><b><font color=\"red\">" + txname+ "</font></b></html>", txname); 
                else txname=String.format("<html><b><font color=\"black\">" + txname+ "</font></b></html>", txname);
                motx.addElement(txname);
            }
            jList1.setModel(motx);
            // đọc danh sách khách hàng ==> jlist2
            rs = stm.executeQuery("SELECT * FROM \"KhachHang\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"KID\" ASC;");
            String kid = null,khoten = null,kname = null;
            while(rs.next()) {
                kid= rs.getString("KID");
                khoten= rs.getString("HoTen");
                kname = kid + " | " + khoten;
                if(rs.getInt("TrangThai")==1) kname=String.format("<html><b><font color=\"red\">" + kname+ "</font></b></html>", kname); 
                else kname=String.format("<html><b><font color=\"black\">" + kname+ "</font></b></html>", kname);
                mokh.addElement(kname);
            }
            rs.close();
            stm.close();
            conn.close();
            ss.Close();
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        jList2.setModel(mokh);

        Thread t = new Thread(new RunServer());
        t.start();
        
        String url = System.getProperty("user.dir", null);
        String icon_timkiem,icon_themxe,icon_xephang,icon_reset,icon_dangxuat,icon_taixe,icon_khachhang,icon_nhatki;
        icon_timkiem = url + "\\icon\\145.png";
        icon_themxe = url + "\\icon\\order-car-icon.png";
        icon_reset = url + "\\icon\\186.png";
        icon_dangxuat = url + "\\icon\\5.png";
        icon_taixe = url + "\\icon\\204.png";
        icon_khachhang = url + "\\icon\\194.png";
        icon_nhatki = url + "\\icon\\220.png";
        jLabel2.setIcon(new javax.swing.ImageIcon(icon_timkiem));
        jLabel3.setIcon(new javax.swing.ImageIcon(icon_timkiem));
        btn_themxe.setIcon(new javax.swing.ImageIcon(icon_themxe));
        btn_reset.setIcon(new javax.swing.ImageIcon(icon_reset));
        btn_dangxuat.setIcon(new javax.swing.ImageIcon(icon_dangxuat));
        btn_xeploaitaixe.setIcon(new javax.swing.ImageIcon(icon_taixe));
        btn_khachtiemnang.setIcon(new javax.swing.ImageIcon(icon_khachhang));
        btn_nhatki.setIcon(new javax.swing.ImageIcon(icon_nhatki));
        
        
        
        
    }
    
    String HamTachKH(String string) {
        if(string == null) {
            return "KH00";
        }
        String s1 = string.substring(0, 2);
        String s2 = string.substring(2, 4);
        int n = Integer.parseInt(s2);
        String s3;
        if(0<=n && n<=8) {
            n=n+1;
            s3 = s1+"0"+String.valueOf(n);
        }
        else {
            n=n+1;
            s3 = s1+String.valueOf(n); 
        }
        return s3;
    }
    String HamTachTX(String string) {
        if(string == null) {
            return "TX00";
        }
        String s1 = string.substring(0, 2);
        String s2 = string.substring(2, 4);
        int n = Integer.parseInt(s2);
        String s3;
        if(0<=n && n<=8) {
            n=n+1;
            s3 = s1+"0"+String.valueOf(n);
        }
        else {
            n=n+1;
            s3 = s1+String.valueOf(n); 
        }
        return s3;
    }
    
    public class RunServer implements Runnable {

        @Override
        public void run() {
            try {
                ServerSocket serverSock = new ServerSocket(cong);
                while (true) {
                    Socket socket = serverSock.accept();
                    Thread listen = new Thread(new readData(socket));
                    listen.start();
                }
            } catch (IOException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class readData implements Runnable {

        DataInputStream din;
        Socket sock;
        DataOutputStream dout;

        public readData(Socket socket) {
            sock = socket;
            try {
                dout = new DataOutputStream(sock.getOutputStream());
                din = new DataInputStream(sock.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        @Override
        public void run() {
            postgres_connect ss;
            Connection conn;
            Statement stm = null,stm1 = null,stm2 = null;
            ResultSet rs = null,rs1 = null,rs2 = null;
            String s1 = null;
            String[] s2;
            DataOutputStream d = null;
            
            while(true) {
                try {
                    s1 = din.readUTF();
                    System.out.println(s1);
                } catch (IOException ex) {
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                }
                s2 = s1.split("&");
                //_______________________________________________________________________________________________________________________________________
                if(s1.equalsIgnoreCase("quit")) break;
                //_______________________________________________________________________________________________________________________________________
                if(s1.equalsIgnoreCase("xacnhandung")) {
                    try {
                        dout.writeUTF("xacnhandung");
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("chapnhankh")) {
                    String tktx = null;
                    String tkkh = s2[3];
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT * FROM \"TaiXe\" WHERE \"TXID\" LIKE '"+s2[1]+"';");
                        rs.next();
                        tktx = rs.getString("TenTK");
                        String hoten = rs.getString("HoTen");
                        Double diem = rs.getDouble("Diem");
                        txonline.remove(s2[1] + " | " + hoten + " | " + String.valueOf(diem));
                        Enumeration names;
                        String str=null;
                        names = htbkh.keys();
                        while(names.hasMoreElements()) {
                            str = (String) names.nextElement();
                            d = (DataOutputStream)htbkh.get(str);
                            try {
                                d.writeUTF("taixeofline&"+s2[1]+" | "+hoten+" | "+String.valueOf(diem));
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    for(int i=0;i<al.size();i++) {
                        if(al.get(i).toString().equalsIgnoreCase(tktx) == false) {
                            d = (DataOutputStream)htbtx.get(al.get(i).toString());
                            try {
                                d.writeUTF("huybo");
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                            
                    d = (DataOutputStream)htbkh.get(s2[3]);
                    try {
                        d.writeUTF("hienthi&TXID: "+s2[1]+"\n"+"Ho ten: "+s2[2]+"\n----------\nXin vui long doi trong vai phut!");
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        dout.writeUTF("taikhoankhachhang&"+s2[3]);
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("tuchoikh")) {
                    // tru diem tai xe
                }
                //_______________________________________________________________________________________________________________________________________
                if(s1.equalsIgnoreCase("yeucauloaixe")) {
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT * FROM \"LoaiXe\"");
                        while(rs.next()) {
                            try {
                                dout.writeUTF(rs.getString("Ten"));
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        dout.writeUTF("hetxe");
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("thongtintaixe")) {
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    String txid = s2[1];
                    String hoten = null;
                    String diachi = null;
                    String gioitinh = null;
                    String diem = null;
                    String ngaysinh = null;
                    String sdt = null;
                    String tktx = null;
                    String mk = null;
                    String ttt = null;
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" WHERE \"TXID\" LIKE '"+txid+"';");
                        rs.next();
                        hoten = rs.getString("HoTen");
                        diachi = rs.getString("DiaChi");
                        sdt = rs.getString("SDT");
                        ngaysinh = rs.getString("NgaySinh");
                        if(rs.getInt("GioiTinh")==1) gioitinh = "Nam"; else gioitinh = "Nữ";
                        diem = String.valueOf(rs.getDouble("Diem"));
                        tktx = rs.getString("TenTK");
                        ttt = rs.getString("ThongTinThem");
                        mk = rs.getString("MatKhau");
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                        dout.writeUTF("thongtintaixe&"+txid+"&"+hoten+"&"+gioitinh+"&"+ngaysinh+"&"+diachi+"&"+sdt+"&"+ttt+"&"+tktx+"&"+diem+"&"+mk);
                    } catch (SQLException | IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("thongtinkhachhang")) {
                    String kid = s2[1];
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    String hoten = null;
                    String diachi = null;
                    String gioitinh = null;
                    String diem = null;
                    String ngaysinh = null;
                    String sdt = null;
                    String tkk = null;
                    String mk=null;
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT * FROM \"KhachHang\" NATURAL JOIN \"TaiKhoan\" WHERE \"KID\" LIKE '"+kid+"';");
                        rs.next();
                        hoten = rs.getString("HoTen");
                        diachi = rs.getString("DiaChi");
                        sdt = rs.getString("SDT");
                        ngaysinh = rs.getString("NgaySinh");
                        if(rs.getInt("GioiTinh")==1) gioitinh = "Nam"; else gioitinh = "Nữ";
                        tkk = rs.getString("TenTK");
                        mk = rs.getString("MatKhau");
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                        dout.writeUTF("thongtinkhach&"+kid+"&"+hoten+"&"+gioitinh+"&"+diachi+"&"+ngaysinh+"&"+sdt+"&"+tkk+"&"+mk);
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("thongtintaixehoten")) {
                    String hoten = s2[1];
                    System.out.println(hoten);
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    try {
                        stm=conn.createStatement();
                        rs = stm.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" WHERE \"HoTen\" = '"+hoten+"' AND \"TrangThai\" = 1;");
                        String tk=null,txid=null,name=null;
                        Double diem=0.0;
                        int i=0;
                        while(rs.next()) {
                            i=1;
                            tk = rs.getString("TenTK");
                            txid = rs.getString("TXID");
                            hoten = rs.getString("HoTen");
                            diem = rs.getDouble("Diem");
                            name = txid + " | " + hoten + " | " + String.valueOf(diem);
                            dout.writeUTF("timkiemthongtintaixe&"+name);                              
                        }
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                        if(i==0) dout.writeUTF("timkiemthongtintaixe&khongco"); 
                    } catch (SQLException | IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("yeucauxoaxe")) {
                    String txid = s2[1];
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT \"Bien\" FROM \"Xe\" WHERE \"TXID\" LIKE '"+txid+"';");
                        while(rs.next()) {
                            dout.writeUTF(rs.getString("Bien"));
                        }
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        dout.writeUTF("hetxe");
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("xoaxe")) {
                    String bien = s2[1];
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    try {
                        stm = conn.createStatement();
                        stm.executeUpdate("DELETE FROM \"Xe\" WHERE \"Bien\" LIKE '"+bien+"';");
                        stm.close();
                        conn.close();
                        ss.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("themxetaixe")) {
                    String txid = s2[1];
                    String bien = s2[2];
                    String ten = s2[3];
                    Object Gia = new Object();
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    try {
                        stm = conn.createStatement();
                        stm1 = conn.createStatement();
                        rs = stm.executeQuery("SELECT \"Gia\" FROM \"LoaiXe\" WHERE \"Ten\" = '"+ten+"'");
                        rs.next();
                        Gia = rs.getString("Gia");
                        rs.close();
                        stm1.executeUpdate("INSERT INTO \"Xe\" VALUES('"+bien+"','"+ten+"','"+Gia+"','"+txid+"');");
                        stm1.close();
                        stm.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ss.Close();
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("kiemtratien")) {
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    String tk = s2[1];
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT \"Tien\" FROM \"TaiKhoan\" WHERE \"TenTK\" LIKE '"+tk+"';");
                        rs.next();
                        dout.writeUTF("kiemtratien&"+(String)rs.getString("Tien"));
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("naptien")) {
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    String tk = s2[1];
                    String kid=null;
                    Date thoiGian = new Date();
                    SimpleDateFormat dinhDangThoiGian = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
                    String hienThiThoiGian = dinhDangThoiGian.format(thoiGian.getTime());
                    int tien = Integer.parseInt(s2[2]);
                    int stt=0;
                    try {
                        stm = conn.createStatement();
                        stm1 = conn.createStatement();
                        stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"Tien\" = \"Tien\" + '"+tien+"' WHERE \"TenTK\" = '"+tk+"';");
                        rs = stm.executeQuery("SELECT \"KID\" FROM \"KhachHang\" WHERE \"TenTK\" = '"+tk+"';");
                        rs.next();
                        kid = rs.getString("KID");
                        rs = stm.executeQuery("SELECT \"STT\" FROM \"NapTien\";");
                        while(rs.next()) {
                            try {
                                stt = rs.getInt("STT");
                                stt++;
                                rs1 = stm1.executeQuery("SELECT \"STT\" FROM \"NapTien\" WHERE \"STT\" = '"+stt+"';");
                                if(rs1.next()==false) {rs1.close();break;}
                                rs1.close();
                            } catch (Exception e) {
                                stt=0;
                            }
                        }
                        stm.executeUpdate("INSERT INTO \"NapTien\" VALUES ('"+stt+"','"+hienThiThoiGian+"','"+tien+"','"+kid+"','"+tk+"');");
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                        dout.writeUTF("naptienthanhcong");
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            dout.writeUTF("naptienthatbai");
                        } catch (IOException ex1) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            dout.writeUTF("naptienthatbai");
                        } catch (IOException ex1) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                    
                    ta_server.append("\n"+kid+"|"+tien);
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("capnhatthongtin")) {
                    // neu la taixe,cap nhat thong tin va set lai jlist, gui lai thong tin cho tat ca khach hang.
                    // neu l khach hang set lai jlist
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    if(s2[1].equalsIgnoreCase("khachhang")) {
                        String kid = s2[2];
                        String hoten = s2[3];
                        String gioitinh = s2[4];
                        String diachi = s2[5];
                        String ngaysinh = s2[6];
                        String sdt = s2[7];
                        String tk = s2[8];
                        String mk = s2[9];
                        int gt=0;
                        if(gioitinh.equalsIgnoreCase("Nam")) gt=1;
                        try {
                            stm = conn.createStatement();
                            stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"MatKhau\" = '"+mk+"' WHERE \"TenTK\" LIKE '"+tk+"';");
                            stm.executeUpdate("UPDATE \"KhachHang\" SET \"DiaChi\" = '"+diachi+"',\"SDT\" = '"+sdt+"',\"HoTen\" = '"+hoten+"',\"NgaySinh\" = '"+ngaysinh+"',\"GioiTinh\" = '"+gt+"' WHERE \"TenTK\" LIKE '"+tk+"';");
                            stm.close();
                            dout.writeUTF("quit");
                        } catch (SQLException | IOException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        d = (DataOutputStream)htbkh.get(tk);
                        try {
                            d.writeUTF("capnhattenkhachang&"+hoten);
                        } catch (IOException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String txid = s2[2];
                        String hoten = s2[3];
                        String gioitinh = s2[4];
                        String ngaysinh = s2[5];
                        String diachi = s2[6];
                        String sdt = s2[7];
                        int gt=0;
                        if(gioitinh.equalsIgnoreCase("Nam")) gt=1;
                        String ttt = s2[8];
                        String tk = s2[9];
                        String diem = s2[10];
                        String mk = s2[11];
                        String hotenold = null;
                        try {
                            stm = conn.createStatement();      // o tren conn.close() roi
                            rs = stm.executeQuery("SELECT * FROM \"TaiXe\" WHERE \"TenTK\" LIKE '"+tk+"';");
                            rs.next();
                            hotenold = rs.getString("HoTen");
                            rs.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        // gửi đến cho tất cả các khách hàng tài xế này và xóa nó khỏi jlist 
                        Enumeration names;
                        String str=null;
                        names = htbkh.keys();
                        while(names.hasMoreElements()) {
                            str = (String) names.nextElement();
                            d = (DataOutputStream)htbkh.get(str);
                            try {
                                d.writeUTF("taixeofline&"+txid+" | "+hotenold+" | "+diem);
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        // updata lại thông tin tài xế
                        try {
                            stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"MatKhau\" = '"+mk+"' WHERE \"TenTK\" LIKE '"+tk+"';");
                            stm.executeUpdate("UPDATE \"TaiXe\" SET \"DiaChi\" = '"+diachi+"',\"SDT\" = '"+sdt+"',\"HoTen\" = '"+hoten+"',\"NgaySinh\" = '"+ngaysinh+"',\"GioiTinh\" = '"+gt+"',\"ThongTinThem\"  = '"+ttt+"' WHERE \"TenTK\" LIKE '"+tk+"';");
                            dout.writeUTF("quit");
                            stm.close();
                        } catch (SQLException | IOException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        // gửi lại cho toàn bộ khách hàng thông tin của tài xế mới
                        names = htbkh.keys();
                        while(names.hasMoreElements()) {
                            str = (String) names.nextElement();
                            d = (DataOutputStream)htbkh.get(str);
                            try {
                                d.writeUTF("taixeonline&"+txid+" | "+hoten+" | "+diem);
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        d = (DataOutputStream)htbtx.get(tk);
                        try {
                            d.writeUTF("capnhattentaixe&"+hoten);
                        } catch (IOException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    // cập nhật lại jlist của server
                    DefaultListModel<String> mo1 = new DefaultListModel<>();
                    DefaultListModel<String> mo2 = new DefaultListModel<>();
                    try {
                        stm1 = conn.createStatement();
                        rs1 = stm1.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"TXID\" ASC;");
                        String txid = null;
                        String txhoten = null;
                        String txname = null;
                        while(rs1.next()) {
                            txid= rs1.getString("TXID");
                            txhoten= rs1.getString("HoTen");
                            txname = txid + " | " + txhoten;     // sua thu thanh label roi set mau xem duoc khong
                            if(rs1.getInt("TrangThai")==1) txname=String.format("<html><b><font color=\"red\">" + txname+ "</font></b></html>", txname); 
                            else txname=String.format("<html><b><font color=\"black\">" + txname+ "</font></b></html>", txname);
                            mo1.addElement(txname);
                        }
                        rs1 = stm1.executeQuery("SELECT * FROM \"KhachHang\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"KID\" ASC;");
                        String kid = null;
                        String khoten = null;
                        String kname = null;
                        while(rs1.next()) {
                            kid= rs1.getString("KID");
                            khoten= rs1.getString("HoTen");
                            kname = kid + " | " + khoten;
                            if(rs1.getInt("TrangThai")==1) kname=String.format("<html><b><font color=\"red\">" + kname+ "</font></b></html>", kname); 
                            else kname=String.format("<html><b><font color=\"black\">" + kname+ "</font></b></html>", kname);
                            mo2.addElement(kname);
                        }
                        rs1.close();
                        stm1.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jList1.setModel(mo1);
                    jList2.setModel(mo2);
                    ss.Close();
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("dangxuat")) {
                    // neu dang xuat la tai xe thi set trang thai ve 0,gui den tat ca khach hang tai xe do, remove khoi htbtx, break, set lai jlist cua server; 
                    // neu la khach hang thi set trang thai ve 0, remove khoi htbkh, break, set lai jlist cua server;
                    String tk = s2[2];
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    // set lại về ofline
                    try {
                        stm = conn.createStatement();
                        stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"TrangThai\" = '0' WHERE \"TenTK\" LIKE '"+tk+"';");
                        stm.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // xóa khỏi htb
                    if(s2[1].equalsIgnoreCase("khachhang")) {
                        htbkh.remove(tk);
                    } else {
                        htbtx.remove(tk);
                        String txid = null;
                        String hoten = null;
                        Double diem=0.0;
                        try {
                            stm = conn.createStatement();      // o tren conn.close() roi
                            rs = stm.executeQuery("SELECT * FROM \"TaiXe\" WHERE \"TenTK\" LIKE '"+tk+"';");
                                    rs.next();
                                   txid = rs.getString("TXID");
                                    hoten = rs.getString("HoTen");
                                    diem = rs.getDouble("Diem");
                                    rs.close();
                                    stm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Enumeration names;
                        String str=null;
                        names = htbkh.keys();
                        while(names.hasMoreElements()) {
                            str = (String) names.nextElement();
                            d = (DataOutputStream)htbkh.get(str);
                            try {
                                d.writeUTF("taixeofline&"+txid+" | "+hoten+" | "+String.valueOf(diem));
                                txonline.remove(txid+" | "+hoten+" | "+String.valueOf(diem));
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    DefaultListModel<String> mo1 = new DefaultListModel<>();
                    DefaultListModel<String> mo2 = new DefaultListModel<>();
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"TXID\" ASC;");
                        String txid = null;
                        String txhoten = null;
                        String txname = null;
                        while(rs.next()) {
                            txid= rs.getString("TXID");
                            txhoten= rs.getString("HoTen");
                            txname = txid + " | " + txhoten;     // sua thu thanh label roi set mau xem duoc khong
                            if(rs.getInt("TrangThai")==1) txname=String.format("<html><b><font color=\"red\">" + txname+ "</font></b></html>", txname); 
                            else txname=String.format("<html><b><font color=\"black\">" + txname+ "</font></b></html>", txname);
                            mo1.addElement(txname);
                        }
                        rs = stm.executeQuery("SELECT * FROM \"KhachHang\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"KID\" ASC;");
                        String kid = null;
                        String khoten = null;
                        String kname = null;
                        while(rs.next()) {
                            kid= rs.getString("KID");
                            khoten= rs.getString("HoTen");
                            kname = kid + " | " + khoten;
                            if(rs.getInt("TrangThai")==1) kname=String.format("<html><b><font color=\"red\">" + kname+ "</font></b></html>", kname); 
                            else kname=String.format("<html><b><font color=\"black\">" + kname+ "</font></b></html>", kname);
                            mo2.addElement(kname);
                        }
                        rs.close();
                        stm.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    jList1.setModel(mo1);
                    jList2.setModel(mo2);
                    ss.Close(); 
                    break;
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("dangnhap")) {
                    // neu la tai xe, set trang thai len 1, gui den tat ca khach hang tai xe do, set lai jlist1 cua server, add vao htbtx
                    // neu la khach hang thi set trang len 1,gui cho kh do tat ca tx dang online,set lai jlist1 cua server, add vao htbkh
                    String tk = s2[3];
                    String mk = s2[4];
                    String loai = s2[2];
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    String string=null;
                    
                    try {
                        stm = conn.createStatement();
                        stm1 = conn.createStatement();
                        stm2 = conn.createStatement();
                        rs = stm.executeQuery("SELECT * FROM \"TaiKhoan\" WHERE \"TenTK\" LIKE '"+tk+"' AND \"MatKhau\" LIKE '"+mk+"';");
                        if(rs.next()==true) {
                            if(loai.equalsIgnoreCase("khachhang")) {
                                htbkh.put(tk, dout);
                                stm1.executeUpdate("UPDATE \"TaiKhoan\" SET \"TrangThai\" = 1 WHERE \"TenTK\" LIKE '"+tk+"';");
                                rs1 = stm1.executeQuery("SELECT * FROM \"KhachHang\" WHERE \"TenTK\" LIKE '"+tk+"';");
                                rs1.next();
                                String kid = rs1.getString("KID");
                                String hoten = rs1.getString("HoTen");
                                String gioitinh=null;
                                if(rs1.getInt("GioiTinh")==1) gioitinh = "Nam"; else gioitinh = "nu";
                                String diachi = rs1.getString("DiaChi");
                                String sdt = rs1.getString("SDT");
                                String ngaysinh = rs1.getString("NgaySinh");
                                String dangnhapkh = "chapnhan&"+kid+"&"+hoten+"&"+gioitinh+"&"+diachi+"&"+ngaysinh+"&"+sdt+"&"+tk+"&"+mk;
                                try {
                                    dout.writeUTF(dangnhapkh);
                                } catch (IOException ex) {
                                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                /*rs2 = stm2.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" WHERE \"Loai\" = '1' AND \"TrangThai\" = '1';");   // de == nen sai
                                String txid=null;
                                String hotentx = null;
                                String name = null;
                                double diem=0.0;
                                while(rs2.next()) {
                                    txid = rs2.getString("TXID");
                                    hotentx = rs2.getString("HoTen");
                                    diem = rs2.getDouble("Diem");
                                    name = txid + " | " + hotentx + " | " + String.valueOf(diem);
                                    try {
                                        dout.writeUTF("taixeonline&"+name);
                                    } catch (IOException ex) {
                                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }*/
                                for(int i=0;i<txonline.size();i++) {
                                    System.out.println(txonline.get(i).toString());
                                    try {
                                        dout.writeUTF("taixeonline&"+txonline.get(i).toString());
                                    } catch (IOException ex) {
                                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
    //                            rs1.close();
//                                rs2.close();
//                                stm1.close();
  //                              stm2.close();
                                string = "zxcv";
                            } else {
                                htbtx.put(tk, dout);
                                rs1 = stm1.executeQuery("SELECT * FROM \"TaiXe\" WHERE \"TenTK\" LIKE '"+tk+"';");
                                rs1.next();
                                String txid = rs1.getString("TXID");
                                String hoten = rs1.getString("HoTen");
                                String gioitinh=null;
                                if(rs1.getInt("GioiTinh")==1) gioitinh ="Nam"; else gioitinh = "Nu";
                                String diachi = rs1.getString("DiaChi");
                                String sdt = rs1.getString("SDT");
                                String ngaysinh = rs1.getString("NgaySinh");
                                Double diem = rs1.getDouble("Diem");
                                String ttt = rs1.getString("ThongTinThem");
                                String name = txid + " | " + hoten + " | " + String.valueOf(diem);
                                txonline.add(name);
                                String dangnhaptx = "chapnhan&"+txid+"&"+hoten+"&"+gioitinh+"&"+ngaysinh+"&"+diachi+"&"+sdt+"&"+ttt+"&"+tk+"&"+diem.toString()+"&"+mk;
                                try {
                                    dout.writeUTF(dangnhaptx);
                                } catch (IOException ex) {
                                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                rs2 = stm2.executeQuery("SELECT \"Bien\" FROM \"Xe\" WHERE \"TXID\" = '"+txid+"';");
                                while(rs2.next()) {
                                    try {
                                        String bien = rs2.getString("Bien");     // loi sai dat rs1 thay vi dat rs2
                                        dout.writeUTF(bien);
                                    } catch (IOException ex) {
                                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                
                                try {
                                    dout.writeUTF("thoat");
                                    // chặn lại
                                    string = din.readUTF();
                                } catch (IOException ex) {
                                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if(string.equalsIgnoreCase("zxcv")) {
                                    stm1.executeUpdate("UPDATE \"TaiKhoan\" SET \"TrangThai\" = 1 WHERE \"TenTK\" LIKE '"+tk+"';");
                                    Enumeration names;
                                    String str=null;
                                    names = htbkh.keys();
                                    while(names.hasMoreElements()) {
                                       str = (String) names.nextElement();
                                       d = (DataOutputStream)htbkh.get(str);
                                        try {
                                            d.writeUTF("taixeonline&"+txid+" | "+hoten+" | "+diem);
                                        } catch (IOException ex) {
                                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                                
                            }
                            if(string.equalsIgnoreCase("zxcv")) {
                                DefaultListModel<String> mo1 = new DefaultListModel<>();
                                DefaultListModel<String> mo2 = new DefaultListModel<>();
                                try {
                                    stm1 = conn.createStatement();
                                    rs1 = stm1.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"TXID\" ASC;");
                                    String txid = null;
                                    String txhoten = null;
                                    String txname = null;
                                    while(rs1.next()) {
                                        txid= rs1.getString("TXID");
                                        txhoten= rs1.getString("HoTen");
                                        txname = txid + " | " + txhoten;     // sua thu thanh label roi set mau xem duoc khong
                                        if(rs1.getInt("TrangThai")==1) txname=String.format("<html><b><font color=\"red\">" + txname+ "</font></b></html>", txname); 
                                        else txname=String.format("<html><b><font color=\"black\">" + txname+ "</font></b></html>", txname);
                                        mo1.addElement(txname);
                                    }
                                    rs1 = stm1.executeQuery("SELECT * FROM \"KhachHang\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"KID\" ASC;");
                                    String kid = null;
                                    String khoten = null;
                                    String kname = null;
                                    while(rs1.next()) {
                                        kid= rs1.getString("KID");
                                        khoten= rs1.getString("HoTen");
                                        kname = kid + " | " + khoten;
                                        if(rs1.getInt("TrangThai")==1) kname=String.format("<html><b><font color=\"red\">" + kname+ "</font></b></html>", kname); 
                                        else kname=String.format("<html><b><font color=\"black\">" + kname+ "</font></b></html>", kname);
                                        mo2.addElement(kname);
                                    }
                                    rs1.close();
                                    stm1.close();
                                    conn.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                jList1.setModel(mo1);
                                jList2.setModel(mo2);
                            }
                            ss.Close();
                        } else {
                            try {
                                dout.writeUTF("tuchoi");
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("taotaikhoan")) {
                    // insert du lieu khachhang, taixe vao CSDL, set lai jlist
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    if(s2[1].equalsIgnoreCase("khachhang")) {
                        String kid = s2[2];
                        String hoten = s2[3];
                        String gioitinh = s2[4];
                        int gt=0;
                        if(gioitinh.equalsIgnoreCase("Nam")) gt=1;
                        String diachi = s2[5];
                        String ngaysinh = s2[6];
                        String sdt = s2[7];
                        String tk = s2[8];
                        String mk = s2[9];
                        try {       
                            stm = conn.createStatement();
                            stm1 = conn.createStatement();
                            rs = stm.executeQuery("SELECT \"KID\" FROM \"KhachHang\" ORDER BY \"KID\" ASC"); 
                            while(rs.next()) {
                                kid = rs.getString("KID");
                                kid = HamTachKH(kid);
                                rs1 = stm1.executeQuery("SELECT \"KID\" FROM \"KhachHang\" WHERE \"KID\" = '"+kid+"'");
                                if(rs1.next()==false) {rs1.close();break;}
                                rs1.close();
                            }
                            stm1.close();
                            rs.close();
                            int rowCount_tk = stm.executeUpdate("INSERT INTO \"TaiKhoan\" VALUES ('"+tk+"','"+mk+"','50000','2','0');");
                            int rowCount_kh = stm.executeUpdate("INSERT INTO \"KhachHang\" VALUES ('"+kid+"','"+hoten+"','"+gt+"','"+diachi+"','"+ngaysinh+"','"+sdt+"','"+tk+"');");
                            stm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        String TK = s2[2];
                        String MK =s2[3];
                        String HoTen = s2[4];
                        int GioiTinh = 0;
                        if(s2[5].equalsIgnoreCase("Nam")) GioiTinh=1;
                        String NgaySinh = s2[6];
                        String SDT = s2[7];
                        String  MieuTa = s2[8];
                        String DiaChi = s2[9];
                        String TXID = s2[10];
                        String bien1 = s2[11];
                        String bien2 = s2[13];
                        String  bien3 = s2[15];
                        String ten1 = s2[12];
                        String ten2 = s2[14];
                        String ten3 = s2[16];
                        Object tien1 = new Object();
                        Object tien2 = new Object();
                        Object tien3 = new Object();
       
                        try {       
                            stm = conn.createStatement();
                            stm1 = conn.createStatement();
                            rs = stm.executeQuery("SELECT \"TXID\" FROM \"TaiXe\" ORDER BY \"TXID\" ASC;"); 
                            while(rs.next()) {
                                TXID = rs.getString("TXID");
                                TXID = HamTachTX(TXID);
                                rs1 = stm1.executeQuery("SELECT \"TXID\" FROM \"TaiXe\" WHERE \"TXID\" LIKE '"+TXID+"';");
                                if(rs1.next()==false) {rs1.close();break;}
                                rs1.close();
                            }
                            stm1.close();
                            rs.close();
                            rs = stm.executeQuery("SELECT \"Gia\" FROM \"LoaiXe\" WHERE \"Ten\" = '"+ten1+"';");
                            rs.next();
                            tien1 = rs.getString("Gia");
                            rs = stm.executeQuery("SELECT \"Gia\" FROM \"LoaiXe\" WHERE \"Ten\" = '"+ten2+"';");
                            rs.next();
                            tien2 = rs.getString("Gia");
                            rs = stm.executeQuery("SELECT \"Gia\" FROM \"LoaiXe\" WHERE \"Ten\" = '"+ten3+"';");
                            rs.next();
                            tien3 = rs.getString("Gia");
                            rs.close();
                            int rowCount_tk = stm.executeUpdate("INSERT INTO \"TaiKhoan\" VALUES ('"+TK+"','"+MK+"','0','1','0');");
                            int rowCount_tx = stm.executeUpdate("INSERT INTO \"TaiXe\" VALUES ('"+TXID+"','"+HoTen+"','"+GioiTinh+"','"+NgaySinh+"','"+DiaChi+"','"+SDT+"','"+MieuTa+"','1','"+TK+"','10','1');");    // Lỗi sai trùng khóa chính, khi in ra nó không đúng thứ tự ???
                            if(bien1.equalsIgnoreCase("")==false) {
                                int rowCount_x1 = stm.executeUpdate("INSERT INTO \"Xe\" VALUES ('"+bien1+"','"+ten1+"','"+tien1+"','"+TXID+"');");
                            }
                            if(bien2.equalsIgnoreCase("")==false) {
                                int rowCount_x2 = stm.executeUpdate("INSERT INTO \"Xe\" VALUES ('"+bien2+"','"+ten2+"','"+tien2+"','"+TXID+"');");
                            }
                            if(bien3.equalsIgnoreCase("")==false) {
                                int rowCount_x3 = stm.executeUpdate("INSERT INTO \"Xe\" VALUES ('"+bien3+"','"+ten3+"','"+tien3+"','"+TXID+"');");
                            }
                            stm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    DefaultListModel<String> mo1 = new DefaultListModel<>();
                    DefaultListModel<String> mo2 = new DefaultListModel<>();
                    try {
                        stm1 = conn.createStatement();
                        rs1 = stm1.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"TXID\" ASC;");
                        String txid = null;
                        String txhoten = null;
                        String txname = null;
                        while(rs1.next()) {
                            txid= rs1.getString("TXID");
                            txhoten= rs1.getString("HoTen");
                            txname = txid + " | " + txhoten;     // sua thu thanh label roi set mau xem duoc khong
                            if(rs1.getInt("TrangThai")==1) txname=String.format("<html><b><font color=\"red\">" + txname+ "</font></b></html>", txname); 
                            else txname=String.format("<html><b><font color=\"black\">" + txname+ "</font></b></html>", txname);
                            mo1.addElement(txname);
                        }
                        rs1 = stm1.executeQuery("SELECT * FROM \"KhachHang\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"KID\" ASC;");
                        String kid = null;
                        String khoten = null;
                        String kname = null;
                        while(rs1.next()) {
                            kid= rs1.getString("KID");
                            khoten= rs1.getString("HoTen");
                            kname = kid + " | " + khoten;
                            if(rs1.getInt("TrangThai")==1) kname=String.format("<html><b><font color=\"red\">" + kname+ "</font></b></html>", kname); 
                            else kname=String.format("<html><b><font color=\"black\">" + kname+ "</font></b></html>", kname);
                            mo2.addElement(kname);
                        }
                        rs1.close();
                        stm1.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jList1.setModel(mo1);
                    jList2.setModel(mo2);
                    ss.Close();
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("yeucau")) {
                    d = (DataOutputStream)htbtx.get(s2[2]);
                    al.add(s2[2]);
                    try {
                    d.writeUTF("yeucau&"+s2[1]+"&"+s2[3]+"&"+s2[4]+"&"+s2[5]);   // loi sai chua sua ben file taixe
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("dienthongtin")) {
                    String tktx = s2[1];
                    String tkkh = s2[2];
                    String diemdi = s2[3];
                    String diemden = s2[4];
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    String txid =null;
                    String kid =null;
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT \"TXID\" FROM \"TaiXe\" WHERE \"TenTK\" LIKE '"+tktx+"';");
                        rs.next();
                        txid = rs.getString("TXID");
                        rs = stm.executeQuery("SELECT \"KID\" FROM \"KhachHang\" WHERE \"TenTK\" LIKE '"+tkkh+"';");
                        rs.next();
                        kid = rs.getString("KID");
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    string1 = txid+"---"+kid+"\n"+diemdi+"---"+diemden;
                    ta_server.append("\n"+string1);
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("danhgia")) {
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    
                    String tktx = s2[1];
                    String bien = s2[2];
                    String tkkh = s2[3];
                    String tbatdau = s2[4];
                    String tketthuc = s2[5];
                    String diemdi = s2[6];
                    String diemden = s2[7];
                    String thoigian = s2[8];
                       
                    double start = Start;
                    double end = End;
                    double random = new Random().nextDouble();
                    double km = start + (random * (end - start));
                    int thanhtien=0;
                    String hoten=null;
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT \"Gia\" FROM \"Xe\" WHERE \"Bien\" LIKE '"+bien+"';");
                        rs.next();
                        Object Giatien = new Object();
                        Giatien = rs.getString("Gia");   // loi sai int giatien,phai de object
                        String Gia = Giatien.toString();
                        System.out.println(Gia);
                        String[] mang1 = Gia.split("\\.");
                        System.out.println(mang1[0]);
                        System.out.println(mang1[1]);
                        String[] mang2 = mang1[1].split("\\,");
                        String mang = mang1[0]+mang2[0];
                        System.out.println(mang);
                        int giatien = Integer.parseInt(mang);
                        thanhtien = giatien*(int)km+Integer.parseInt(thoigian)*500;
                        rs = stm.executeQuery("SELECT * FROM \"TaiXe\" WHERE \"TenTK\" LIKE '"+tktx+"';");
                        rs.next();
                        hoten = rs.getString("HoTen");
                        Double diem = rs.getDouble("Diem");
                        String txid = rs.getString("TXID");
                        txonline.add(txid + " | " + hoten + " | " + String.valueOf(diem));
                        Enumeration names;
                        String str=null;
                        names = htbkh.keys();
                        while(names.hasMoreElements()) {
                            str = (String) names.nextElement();
                            d = (DataOutputStream)htbkh.get(str);
                            try {
                                d.writeUTF("taixeonline&"+txid+" | "+hoten+" | "+String.valueOf(diem));
                            } catch (IOException ex) {
                                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    d = (DataOutputStream)htbkh.get(tkkh);
                    try {
                        d.writeUTF("danhgia&"+tktx+"&"+bien+"&"+tkkh+"&"+tbatdau+"&"+tketthuc+"&"+diemdi+"&"+diemden+"&"+thoigian+"&"+String.valueOf(km)+"&"+String.valueOf(thanhtien)+"&"+hoten);
                        dout.writeUTF("danhgia&"+thanhtien);
                    } catch (IOException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //_______________________________________________________________________________________________________________________________________
                if(s2[0].equalsIgnoreCase("cuoicunglotrinh")) {
                    ss = new postgres_connect();
                    conn = ss.getConnectToMSSQL();
                    
                    String tktx = s2[1];
                    String bien = s2[2];
                    String tkkh = s2[3];
                    String tbatdau = s2[4];
                    String tketthuc = s2[5];
                    String diemdi = s2[6];
                    String diemden = s2[7];
                    String thoigian = s2[8];
                    Double km = Double.valueOf(s2[9]); 
                    int thanhtien = Integer.parseInt(s2[10]); 
                    String diem = s2[11]; 
                    String danhgia=s2[12];
                    int stt = 0;
                    int sttgd = 0;
                    try {
                        stm = conn.createStatement();
                        rs = stm.executeQuery("SELECT \"HTID\" FROM \"HanhTrinh\" ORDER BY \"HTID\" ASC;");
                        int i=0;
                        while(rs.next()) {
                            stt = rs.getInt("HTID");
                            i=1;
                        }
                        stt++;
                        if(i==0) stt=0;
                        rs = stm.executeQuery("SELECT \"TXID\" FROM \"TaiXe\" WHERE \"TenTK\" LIKE '"+tktx+"';");
                        rs.next();
                        String txid = rs.getString("TXID");
                        rs = stm.executeQuery("SELECT \"KID\" FROM \"KhachHang\" WHERE \"TenTK\" LIKE '"+tkkh+"';");
                        rs.next();
                        String kid = rs.getString("KID");
                        stm.executeUpdate("INSERT INTO \"HanhTrinh\" VALUES('"+stt+"','"+tbatdau+"','"+tketthuc+"','"+diemdi+"','"+diemden+"','"+km+"','"+txid+"','"+kid+"','"+Integer.parseInt(diem)+"','"+danhgia+"','"+bien+"');");
                        rs = stm.executeQuery("SELECT \"GDID\" FROM \"GiaoDich\" ORDER BY \"GDID\" ASC;");
                        i=0;
                        while(rs.next()) {
                            sttgd = rs.getInt("GDID");
                            i=1;
                        }
                        sttgd++;
                        if(i==0) sttgd=0;
                        int thanhtientx = (int)thanhtien/10*8;
                        int thanhtienserver = thanhtien - thanhtientx;
                        stm.executeUpdate("INSERT INTO \"GiaoDich\" VALUES('"+sttgd+"','"+tketthuc+"','"+tkkh+"','"+tktx+"','"+thanhtientx+"','"+stt+"');");
                        sttgd++;
                        stm.executeUpdate("INSERT INTO \"GiaoDich\" VALUES('"+sttgd+"','"+tketthuc+"','"+tkkh+"','hedspi@hedspi','"+thanhtienserver+"','"+stt+"');");
                        rs.close();
                        stm.close();
                        conn.close();
                        ss.Close();
                    } catch (SQLException ex) {
                        Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String s = ta_server.getText();
                    String them = "\n"+tbatdau+"---"+tketthuc+"\n"+thanhtien+"---"+diem;
                    System.out.println("s old: "+s);
                    System.out.println("string1: "+string1);
                    System.out.println("them: "+them);
                    String xau = s.replaceAll(string1, string1+them);
                    System.out.println("s new: "+xau);
                    ta_server.setText(xau);
                }
                //_______________________________________________________________________________________________________________________________________
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btn_nhatki = new javax.swing.JButton();
        btn_khachtiemnang = new javax.swing.JButton();
        btn_xeploaitaixe = new javax.swing.JButton();
        btn_themxe = new javax.swing.JButton();
        btn_dangxuat = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon(Urlpanel);
            public void paintComponent(Graphics g){
                Dimension d = this.getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ta_server = new javax.swing.JTextArea();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tf_tx = new javax.swing.JTextField();
        timkiemtx = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        ta_taixe = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tf_kh = new javax.swing.JTextField();
        timkiemkh = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        ta_khachhang = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setRollover(true);
        jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn_nhatki.setFocusable(false);
        btn_nhatki.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nhatki.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btn_nhatki);

        btn_khachtiemnang.setFocusable(false);
        btn_khachtiemnang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_khachtiemnang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btn_khachtiemnang);

        btn_xeploaitaixe.setFocusable(false);
        btn_xeploaitaixe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_xeploaitaixe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_xeploaitaixe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xeploaitaixeActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_xeploaitaixe);

        btn_themxe.setFocusable(false);
        btn_themxe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_themxe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_themxe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themxeActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_themxe);

        btn_dangxuat.setFocusable(false);
        btn_dangxuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_dangxuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_dangxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dangxuatActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_dangxuat);

        btn_reset.setFocusable(false);
        btn_reset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_reset.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_reset);

        jLabel1.setForeground(new java.awt.Color(170, 140, 50));
        jLabel1.setText("Lịch Trình");

        ta_server.setColumns(20);
        ta_server.setRows(5);
        jScrollPane2.setViewportView(ta_server);

        timkiemtx.setText("OK");
        timkiemtx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiemtxActionPerformed(evt);
            }
        });

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList1);

        ta_taixe.setColumns(20);
        ta_taixe.setRows(5);
        jScrollPane4.setViewportView(ta_taixe);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_tx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timkiemtx)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_tx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timkiemtx))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Tài xế", jPanel5);

        timkiemkh.setText("OK");
        timkiemkh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timkiemkhActionPerformed(evt);
            }
        });

        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jList2);

        ta_khachhang.setColumns(20);
        ta_khachhang.setRows(5);
        jScrollPane6.setViewportView(ta_khachhang);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_kh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timkiemkh)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tf_kh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timkiemkh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Khách hàng", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jTabbedPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_themxeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themxeActionPerformed
        new ThemXeServer().setVisible(true);
    }//GEN-LAST:event_btn_themxeActionPerformed

    private void btn_dangxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dangxuatActionPerformed
        postgres_connect ss = new postgres_connect();
        Connection conn = ss.getConnectToMSSQL();
        Statement stm;
        try {
            stm = conn.createStatement();
            int rowCount = stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"TrangThai\" = '0' WHERE \"TenTK\" LIKE 'hedspi@hedspi';");
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        ss.Close();
        System.exit(0);
    }//GEN-LAST:event_btn_dangxuatActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (evt.getClickCount() == 2) {
            String name=(String)jList1.getSelectedValue();
            String name1[] = name.split("[>,<]");
            postgres_connect ss = new postgres_connect();
            Connection conn = ss.getConnectToMSSQL();
            Statement stm;
            ResultSet rs;
            try {
                stm=conn.createStatement();
                String id = name1[6].substring(0, 4);
                rs = stm.executeQuery("SELECT * FROM \"TaiXe\" WHERE \"TXID\" = '"+id+"';");
                String tk=null,txid=null,hoten=null,gioitinh=null,ngaysinh=null,diachi=null,sdt=null,thongtin=null;
                double diem;
                rs.next();
                tk = rs.getString("TenTK");
                txid = rs.getString("TXID");
                hoten = rs.getString("HoTen");
                if(rs.getInt("GioiTinh") == 1) gioitinh = "Nam"; else gioitinh = "Nữ";
                ngaysinh = rs.getString("NgaySinh");
                diachi = rs.getString("DiaChi");
                sdt = rs.getString("SDT");
                thongtin = rs.getString("ThongTinThem");
                diem = rs.getDouble("Diem");
                ta_taixe.setText("Tài khoản: "+tk+"\nTXID: "+txid+"\nHọ tên: "+hoten+"\nGiới tính: "+gioitinh+"\nNgày sinh: "+ngaysinh+"\nĐịa chỉ: "+diachi+"\nSố ĐT: "+sdt+"\nThông tin thêm: "+thongtin+"\nĐiểm: "+diem+"\n----------------------");
            } catch (SQLException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        if (evt.getClickCount() == 2) {
            String name=(String)jList2.getSelectedValue();
            String name1[] = name.split("[>,<]");
            postgres_connect ss = new postgres_connect();
            Connection conn = ss.getConnectToMSSQL();
            Statement stm;
            ResultSet rs;
            try {
                stm=conn.createStatement();
                String id = name1[6].substring(0, 4);
                rs = stm.executeQuery("SELECT * FROM \"KhachHang\" WHERE \"KID\" = '"+id+"';");
                rs.next();
                String tk=null,kid=null,hoten=null,gioitinh=null,ngaysinh=null,diachi=null,sdt=null;
                tk = rs.getString("TenTK");
                kid = rs.getString("KID");
                hoten = rs.getString("HoTen");
                if(rs.getInt("GioiTinh") == 1) gioitinh = "Nam"; else gioitinh = "Nữ";
                ngaysinh = rs.getString("NgaySinh");
                diachi = rs.getString("DiaChi");
                sdt = rs.getString("SDT");
                ta_khachhang.setText("Tài khoản: "+tk+"\nTXID: "+kid+"\nHọ tên: "+hoten+"\nGiới tính: "+gioitinh+"\nNgày sinh: "+ngaysinh+"\nĐịa chỉ: "+diachi+"\nSố ĐT: "+sdt+"\n----------------------");
                
            } catch (SQLException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        motx.removeAllElements();
        mokh.removeAllElements();
        postgres_connect ss = new postgres_connect();
        Connection conn = ss.getConnectToMSSQL();
        Statement stm;
        ResultSet rs;
        try {
            stm = conn.createStatement();
            stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"TrangThai\" = '1' WHERE \"TenTK\" LIKE 'hedspi@hedspi';");
            rs = stm.executeQuery("SELECT * FROM \"TaiXe\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"TXID\" ASC;");
            String txid = null;
            String txhoten = null;
            String txname = null;
            while(rs.next()) {
                txid= rs.getString("TXID");
                txhoten= rs.getString("HoTen");
                txname = txid + " | " + txhoten;     // sua thu thanh label roi set mau xem duoc khong
                if(rs.getInt("TrangThai")==1) txname=String.format("<html><b><font color=\"red\">" + txname+ "</font></b></html>", txname); 
                else txname=String.format("<html><b><font color=\"black\">" + txname+ "</font></b></html>", txname);
                motx.addElement(txname);
            }
            rs = stm.executeQuery("SELECT * FROM \"KhachHang\" NATURAL JOIN \"TaiKhoan\" ORDER BY \"KID\" ASC;");
            String kid = null;
            String khoten = null;
            String kname = null;
            while(rs.next()) {
                kid= rs.getString("KID");
                khoten= rs.getString("HoTen");
                kname = kid + " | " + khoten;
                if(rs.getInt("TrangThai")==1) kname=String.format("<html><b><font color=\"red\">" + kname+ "</font></b></html>", kname); 
                else kname=String.format("<html><b><font color=\"black\">" + kname+ "</font></b></html>", kname);
                mokh.addElement(kname);
            }
            rs.close();
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jList1.setModel(motx);
        jList2.setModel(mokh);
        ss.Close();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void timkiemtxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiemtxActionPerformed
        String name=tf_tx.getText();
        postgres_connect ss = new postgres_connect();
        Connection conn = ss.getConnectToMSSQL();
        Statement stm;
        ResultSet rs;
        try {
            stm=conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM \"TaiXe\" WHERE \"HoTen\" LIKE '%"+name+"%';");
            String tk=null,txid=null,hoten=null,gioitinh=null,ngaysinh=null,diachi=null,sdt=null,thongtin=null;
            double diem;
            ta_taixe.setText("");
            while(rs.next()) {
                tk = rs.getString("TenTK");
                txid = rs.getString("TXID");
                hoten = rs.getString("HoTen");
                if(rs.getInt("GioiTinh") == 1) gioitinh = "Nam"; else gioitinh = "Nữ";
                ngaysinh = rs.getString("NgaySinh");
                diachi = rs.getString("DiaChi");
                sdt = rs.getString("SDT");
                thongtin = rs.getString("ThongTinThem");
                diem = rs.getDouble("Diem");
                ta_taixe.append("Tài khoản: "+tk+"\nTXID: "+txid+"\nHọ tên: "+hoten+"\nGiới tính: "+gioitinh+"\nNgày sinh: "+ngaysinh+"\nĐịa chỉ: "+diachi+"\nSố ĐT: "+sdt+"\nThông tin thêm: "+thongtin+"\nĐiểm: "+diem+"\n----------------------\n");                              
            }
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_timkiemtxActionPerformed

    private void timkiemkhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timkiemkhActionPerformed
        String name=tf_kh.getText();
        postgres_connect ss = new postgres_connect();
        Connection conn = ss.getConnectToMSSQL();
        Statement stm;
        ResultSet rs;
        try {
            stm=conn.createStatement();
            rs = stm.executeQuery("SELECT * FROM \"KhachHang\" WHERE \"HoTen\" LIKE '%"+name+"%';");
            String tk=null,kid=null,hoten=null,gioitinh=null,ngaysinh=null,diachi=null,sdt=null;
            ta_khachhang.setText("");
            while(rs.next()) {
                tk = rs.getString("TenTK");
                kid = rs.getString("KID");
                hoten = rs.getString("HoTen");
                if(rs.getInt("GioiTinh") == 1) gioitinh = "Nam"; else gioitinh = "Nữ";
                ngaysinh = rs.getString("NgaySinh");
                diachi = rs.getString("DiaChi");
                sdt = rs.getString("SDT");
                ta_khachhang.append("Tài khoản: "+tk+"\nKID: "+kid+"\nHọ tên: "+hoten+"\nGiới tính: "+gioitinh+"\nNgày sinh: "+ngaysinh+"\nĐịa chỉ: "+diachi+"\nSố ĐT: "+sdt+"\n----------------------\n");                              
            }
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_timkiemkhActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        postgres_connect ss = new postgres_connect();
        Connection conn = ss.getConnectToMSSQL();
        Statement stm;
        try {
            stm = conn.createStatement();
            int rowCount = stm.executeUpdate("UPDATE \"TaiKhoan\" SET \"TrangThai\" = '0' WHERE \"TenTK\" LIKE 'hedspi@hedspi';");
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        ss.Close();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void btn_xeploaitaixeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xeploaitaixeActionPerformed
        
    }//GEN-LAST:event_btn_xeploaitaixeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new server().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_dangxuat;
    private javax.swing.JButton btn_khachtiemnang;
    private javax.swing.JButton btn_nhatki;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_themxe;
    private javax.swing.JButton btn_xeploaitaixe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea ta_khachhang;
    private javax.swing.JTextArea ta_server;
    private javax.swing.JTextArea ta_taixe;
    private javax.swing.JTextField tf_kh;
    private javax.swing.JTextField tf_tx;
    private javax.swing.JButton timkiemkh;
    private javax.swing.JButton timkiemtx;
    // End of variables declaration//GEN-END:variables
}
