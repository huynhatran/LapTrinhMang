package laptrinhmang_doan_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LapTrinhMang_DoAn_Server {
    private Socket socket = null;
    private ServerSocket server = null;
    public LapTrinhMang_DoAn_Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            while(true){
                socket = server.accept();
                System.out.println("Client accepted");
                Thread a = new ServiceThread(socket, 1);
                a.start();
            }
            
        } catch (IOException ex) {
            
        }
    }
    
    
    public static void main(String[] args) {
        LapTrinhMang_DoAn_Server server = new LapTrinhMang_DoAn_Server(3456);
    }
    
    private static class ServiceThread extends Thread{
        private int clientNumber;
        private Socket socketOfServer = null;
        private BufferedWriter out = null;
        private BufferedReader in = null;
        private URL url;
        private HttpURLConnection conn;
        private Scanner scanner;
        
        public ServiceThread(Socket socketOfServer, int clientNumber) {
           this.clientNumber = clientNumber;
           this.socketOfServer = socketOfServer;

            System.err.println("Kết nối mới từ Client thứ " + this.clientNumber + " tại " + socketOfServer);
       }
        @Override
        public void run() {
            try {
                out = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                String line = "";
                while(true){
                    try {
                        line = in.readLine();
                        System.out.println("Server received: " + line);
                        
                        out.write(Menu(line));
                        out.newLine();
                        out.flush();
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                }
            } catch (IOException e) {
                System.err.println(e);
                try {
                    System.out.println("Closing connetion");
                    in.close();
                    out.close();
                    socketOfServer.close();
                } catch (IOException ex) {
                    
                }
            }
        }
        private String Menu(String stringReceived){
            //cú pháp chức năng như sau: tenchucnang#Bien1#Bien2#Bien3
            StringTokenizer st = new StringTokenizer(stringReceived, "#", false);
            String tenChucNang = st.nextToken();//lấy ra tên chức năng nhận được
            String xuatKetQua = "";
            switch(tenChucNang){
                case "thongkecovid":
                    //chức năng thống kê
                    xuatKetQua = thongKeCovid();
                    break;
                case "tracuucovid":
                    //chức năng tra cứu covid
                    xuatKetQua = traCuuCovid(st.nextToken(), st.nextToken(), st.nextToken());
                    break;
                case "tracuudialyquocgia":
                    //chức năng tra cứu đại lý của quốc gia
                    xuatKetQua = traCuuQuocGia(st.nextToken());
                    break;
                case "tracuudialythanhpho":
                    //chức năng tra cứu địa lý của thành phố
                    xuatKetQua = traCuuThanhPho(st.nextToken());
                    break;
                case "danhsachquocgia":
                    xuatKetQua = danhSachQuocGia();
                    break;
            }
            return xuatKetQua;
        }
        //kết quả trả về của các hàm là 1 chuỗi Json
        private String thongKeCovid(){
            String xuatKetQua = "";
            //Xử lý tại đây
            
            return xuatKetQua;
        }
        private String traCuuCovid(String tenQuocGia, String ngayBatDau, String ngayKetThuc){
            String xuatKetQua = "";
            //Xử lý tại đây
            
            return xuatKetQua;
        }
        private String danhSachQuocGia(){
            JSONArray mangXuat = new JSONArray();
            String link = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=vi&countarrayListNationry=&username=leminhcuong2988&style=full";
            //Xử lý tại đây
            try{
                Connection.Response res = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                JSONParser parse = new JSONParser();
                JSONObject data = (JSONObject) parse.parse(res.body());
                JSONArray arr = (JSONArray) data.get("geonames");
                for(int i=0;i<arr.size();i++){
                    JSONObject getObjectArray = (JSONObject) arr.get(i);
                    JSONObject newObject = new JSONObject();
                    newObject.put("countryCode", getObjectArray.get("countryCode"));
                    newObject.put("countryName", getObjectArray.get("countryName"));
                    mangXuat.add(newObject);
                }
            }catch(IOException | ParseException e){
                return "Lỗi từ phía server.";
            }
            JSONObject object = new  JSONObject();
            object.put("geonames", mangXuat);
            return object.toString();
        }
        private String traCuuThanhPho(String tenQuocGia){
            String xuatKetQua = "";
            //Xử lý tại đây
            
            return xuatKetQua;
        }
        private String traCuuQuocGia(String countryName){
            String xuatKetQua = "";
            JSONObject mangXuat = new JSONObject();
            String linkJson = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=vi&countarrayListNationry=&username=leminhcuong2988&style=full";
            String linkJsoup = "https://www.geonames.org/countries/";
            //Xử lý tại đây
            try{
                Connection.Response res = Jsoup.connect(linkJson)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                JSONParser parse = new JSONParser();
                JSONObject data = (JSONObject) parse.parse(res.body());
                JSONArray arr = (JSONArray) data.get("geonames");
                boolean check = false;
                String countryCode = null;
                for(int i=0;i<arr.size();i++){
                    JSONObject getObjectArray = (JSONObject) arr.get(i);
                    String name = String.valueOf(getObjectArray.get("countryName"));
                    if(name.compareToIgnoreCase(countryName)==0){
                        mangXuat.put("status", "success");
                        mangXuat.put("geonameId", getObjectArray.get("geonameId"));
                        mangXuat.put("countryName", getObjectArray.get("countryName"));
                        mangXuat.put("capital", getObjectArray.get("capital"));
                        mangXuat.put("countryCode", getObjectArray.get("countryCode"));
                        mangXuat.put("continentName", getObjectArray.get("continentName"));
                        check = true;
                        countryCode = getObjectArray.get("countryCode").toString();
                        break;
                    }
                }
                if(check){
                    linkJsoup = linkJsoup + countryCode + "/";
                    res = Jsoup.connect("https://www.geonames.org/countries/CN/")
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                    Document doc = res.parse();
                    Elements e = doc.select("table[cellpadding$=5] tr td:nth-of-type(2)");
                    mangXuat.put("area", e.get(4).text());
                    mangXuat.put("population", e.get(5).text());
                    mangXuat.put("currency", e.get(6).text());
                    mangXuat.put("languages", e.get(7).text());
                    mangXuat.put("neighbours", e.get(8).text());
                }else{
                    mangXuat.put("status", "fail");
                }
            }catch(IOException | ParseException e){
                return "Lỗi từ phía server.";
            }
            return xuatKetQua;
        }
    }
}
