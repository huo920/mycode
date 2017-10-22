package huo.one;


import jdk.net.Sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;
    protected List<PrintWriter> allout;

    public Server() throws Exception {
        try {
            System.out.println("启动服务端");
            Map<String,String> config = configLoading();
            int port = Integer.parseInt(config.get("serverPort"));
            serverSocket = new ServerSocket(port);
            allout = new ArrayList<PrintWriter>();
            System.out.println("服务端启动成功");
        } catch (IOException e) {
            throw e;
        }
    }

    private Map<String,String> configLoading() throws Exception {
        BufferedReader br = null;
        try {
            Map<String,String> config = new HashMap<String,String>();
            br = new BufferedReader(
                    new InputStreamReader(
                            Server.class.getClassLoader().getResourceAsStream("huo/one/server-config.txt")));
            String line = null;
            while ((line = br.readLine())!=null){
                String[] info = line.split("=");
                config.put(info[0],info[1]);

            }
            return config;
        } catch (Exception e) {
            throw e;
        } finally {
            if(br!=null){
                br.close();
            }
        }

    }

    private void start() throws Exception{
        try {
            while (true){
                System.out.println("等待客户端连接");
                Socket socket = serverSocket.accept();
                System.out.println("一个客户端连接成功");


                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();

            }


        } catch (Exception e) {
            throw e;
        }

    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务端启动失败");
        }

    }

    private class ClientHandler implements Runnable {

        private Socket socket;
        private String host;//IP地址
        private String nickName;//昵称

        public ClientHandler(Socket socket){
            this.socket = socket;
            host = socket.getInetAddress().getHostAddress();

        }

        @Override
        public void run() {
            PrintWriter pw = null;
            try {

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);

                //先读一行作为昵称
                nickName = br.readLine();
                System.out.println(nickName+"上线了");

                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os,"utf-8");
                pw = new PrintWriter(osw,true);

                synchronized (allout){
                    allout.add(pw);
                }


                String message = null;

                while ((message = br.readLine())!=null) {
                    synchronized (allout){
                        for(PrintWriter p : allout){
                            p.println(nickName+"说:"+message);
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                System.out.println(nickName+"下线了");
                synchronized (allout){
                    allout.remove(pw);
                }
                try {
                    //关闭之后流也就关闭了
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
