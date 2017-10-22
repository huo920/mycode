package huo.two;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server{
    private ServerSocket serverSocket;
    private List<PrintWriter> allout;
    private Map<String,String> config;

    public Server() throws Exception{
        try {
            config = loadCofig();
            int port = Integer.parseInt(config.get("serverPort"));
            serverSocket = new ServerSocket(port);
            allout = new ArrayList<PrintWriter>();
        } catch (Exception e) {
            throw e;
        }
    }

    private Map<String,String> loadCofig() throws Exception{
        Map<String,String> config = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(Server.class.getClassLoader().getResourceAsStream("huo/two/server-config.txt")));
        String line = null;
        try {

            while ((line = br.readLine())!= null){
                String[] info = line.split("=");
                config.put(info[0],info[1]);

            }
            return config;
        } catch (Exception e) {
            throw e;
        }


    }

    private void start(){
        try {
            while (true){
                System.out.println("等待客户端连接");
                Socket socket = serverSocket.accept();
                System.out.println("有一个客户端连接");

                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (Exception e) {
            System.out.println("服务端启动失败");
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable{
        private Socket socket;
        private String host;
        private String nickName;

        public ClientHandler(Socket socket){
            this.socket = socket;
            host = socket.getInetAddress().getHostAddress();
        }

        @Override
        public void run() {
            BufferedReader br = null;
            PrintWriter pw = null;
            try{
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                br = new BufferedReader(isr);

                nickName = br.readLine();
                System.out.println(nickName+"上线了");

                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os,"utf-8");
                pw = new PrintWriter(osw,true);

                synchronized (allout){
                    allout.add(pw);
                }


                String message = null;
                while ((message = br.readLine())!=null){
                    synchronized (allout){
                        for ( PrintWriter p : allout){
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
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }
}
