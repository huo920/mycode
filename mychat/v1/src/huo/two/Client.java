package huo.two;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Map<String,String> config;

    public Client() throws Exception{
        try {
            System.out.println("正在启动客户端");
            config = loadCofig();
            String host = config.get("serverHost");
            int port = Integer.parseInt(config.get("serverPort"));
            socket = new Socket(host,port);
            System.out.println("客户端启动成功");
        } catch (Exception e) {
            throw e;
        }
    }

    private Map<String,String> loadCofig() throws Exception{
        Map<String,String> config = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("huo/two/config.txt")));
        String line = null;
        try {
            while ((line = br.readLine())!= null){
                String[] info = line.split("=");
                config.put(info[0],info[1]);

            }
            return config;
        } catch (Exception e) {
            throw e;
        } finally {
            if(br != null){
                br.close();
            }
        }


    }

    private void start(){
        PrintWriter pw = null;
        try {
            Scanner scanner = new Scanner(System.in);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os,"utf-8");
            pw = new PrintWriter(osw,true);

            String nickName = null;
            System.out.println("请输入昵称：");
            nickName = scanner.nextLine();
            while (true){
                if(nickName.length()>0){
                    pw.println(nickName);
                    System.out.println("欢迎，"+nickName+"开始聊天吧");
                    break;
                }
                System.out.println("请重新输入");
            }


            ServerHandler handler = new ServerHandler();
            Thread t = new Thread(handler);
            t.start();

            String line = null;
            while (true){
                line = scanner.nextLine();
                pw.println(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pw!=null){
                pw.close();
            }
        }

    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.start();
        } catch (Exception e) {
            System.out.println("客户端启动失败");
            e.printStackTrace();
        }
    }

    private class ServerHandler implements Runnable{
       // private Socket socket;

        @Override
        public void run() {
            BufferedReader br = null;
            try{
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                br = new BufferedReader(isr);

                String message = null;
                while ((message = br.readLine())!=null){
                    System.out.println(message);
                }

            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if(br!=null){
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }
}
