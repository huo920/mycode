package huo.one;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private Socket socket;

    public Client() throws Exception {
        try {
            System.out.println("正在启动客户端");
            Map<String,String> config = configLoading();
            String host = config.get("serverHost");
            int port = Integer.parseInt(config.get("serverPort"));
            socket = new Socket(host,port);
            System.out.println("客户端启动成功");
        } catch (IOException e) {
            throw e;
        }
    }

    private Map<String,String> configLoading() throws Exception {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(Client.class.getClassLoader().getResourceAsStream("huo/one/config.txt")));
            Map<String, String> config = new HashMap<String, String>();
            String line = null;
            while ((line = br.readLine()) != null) {
                String info[] = line.split("=");
                config.put(info[0], info[1]);
            }
            return config;
        } catch (IOException e) {
            throw e;
        } finally {
            if (br != null) {
                    br.close();
            }
        }

    }

    private void start(){
        try {
            Scanner scanner = new Scanner(System.in);
            //先要求用户输入一个昵称
            String nickName = null;
            while (true){
                System.out.println("请输入昵称：");
                nickName = scanner.nextLine();
                if(nickName.length()>0){
                    break;
                }
                System.out.println("输入有误！");
            }
            System.out.println("欢迎，"+nickName+"!开始聊天吧！");

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(outputStream,"utf-8");
            PrintWriter pw = new PrintWriter(osw,true);

            //先将昵称发送给服务器
            pw.println(nickName);

            ServerHandler handler = new ServerHandler();
            Thread t = new Thread(handler);
            t.start();

            String line = null;
            while (true){
                line = scanner.nextLine();
                pw.println(line);

            }





        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {

        try {
            Client client = new Client();
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端启动失败");
        }
    }

    private class ServerHandler implements Runnable{


        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);

                String message = null;
                while ((message=br.readLine())!=null){
                    System.out.println(message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }
}
