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
    int dem = 1;
    public LapTrinhMang_DoAn_Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            while(true){
                socket = server.accept();
                System.out.println("Client accepted");
                new ServiceThread(socket, dem++).start();
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
        private String SESSION_KEY = "";
        private SercurityData_Server sercurityData = new SercurityData_Server();
        private JSONParser parse;
        private JSONObject jsonXuat;
                
        public ServiceThread(Socket socketOfServer, int clientNumber) {
           this.clientNumber = clientNumber;
           this.socketOfServer = socketOfServer;

            System.out.println("Kết nối mới từ Client thứ " + this.clientNumber + " tại " + socketOfServer);
        }
        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                String line = in.readLine();//dòng này nhận dữ liệu gửi đầu tiên khi client kết nối server(client gửi session key)
                SESSION_KEY = sercurityData.giaiMaRSA(line);
                System.out.println("SESSION_KEY: "+SESSION_KEY);
                out = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                while(true){
                    try {
                        line = in.readLine();
                         
                        out.write(sercurityData.maHoaAES(Menu(line),SESSION_KEY));
                        out.newLine();
                        out.flush();
                    } catch (IOException e) {
                        break;
                    }
                }
                
                System.out.println("Client đã đóng kết nối.");
                in.close();
                out.close();
                socketOfServer.close();
                
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        private String Menu(String stringReceived){
            //cú pháp chức năng như sau: tenchucnang#Bien1#Bien2#Bien3
            System.out.println("chuỗi mã hóa nhận được: "+stringReceived);
            String xuatKetQua = "";
            if(stringReceived!=null){
                stringReceived = sercurityData.giaiMaAES(stringReceived, SESSION_KEY);
                StringTokenizer st = new StringTokenizer(stringReceived, "#", false);
                String tenChucNang = st.nextToken();//lấy ra tên chức năng nhận được

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
            }
            return xuatKetQua;
        }
        //kết quả trả về của các hàm là 1 chuỗi Json
        private String thongKeCovid(){
            //Xử lý tại đây
            
            return "";
        }
        
        private String traCuuCovid(String tenQuocGia, String ngayBatDau, String ngayKetThuc){
        
            String link = "https://api.covid19api.com/country/"+tenQuocGia+"?from="+ngayBatDau+"&to="+ngayKetThuc;//2021-05-05T00:00:00Z
            JSONArray mangXuat = new JSONArray();
            jsonXuat = new  JSONObject();
            try{
                Connection.Response res = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                parse = new JSONParser();
                JSONArray arr = (JSONArray) parse.parse(res.body());
                for(int i = 0; i<arr.size();i++)
                {
                    JSONObject getObjectArray = (JSONObject) arr.get(i);
                    JSONObject newObject = new JSONObject();
                    newObject.put("Confirmed", getObjectArray.get("Confirmed"));
                    newObject.put("Deaths", getObjectArray.get("Deaths"));
                    newObject.put("Recovered", getObjectArray.get("Recovered"));
                    newObject.put("Active", getObjectArray.get("Active"));
                    mangXuat.add(newObject);
                }
            }catch(IOException | ParseException e){
                jsonXuat.put("status", "fail");
                return "Lỗi từ phía server." + e.getMessage();
            }
            jsonXuat.put("status", "success");
            jsonXuat.put("Solieu", mangXuat);
            jsonXuat.put("function", "tracuucovid");
            return jsonXuat.toString();
        }
        
        private String danhSachThanhPho(){
            String linkJson = "https://countriesnow.space/api/v0.1/countries/population/cities?fbclid=IwAR0cR4DLQN_MYC_rY3HcwbJBEWFvjD1ZxLaW5w_qlBweWiCq6XeKEeoM5Uo";
            try{
                Connection.Response res = Jsoup.connect(linkJson)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                parse = new JSONParser();
                JSONArray data = (JSONArray) parse.parse(res.body());
                
            }catch(IOException | ParseException e){
                return "Lỗi từ phía server.";
            }
            return "";
        }
        
        private String traCuuThanhPho(String tenThanhPho){
            jsonXuat = new  JSONObject();
            JSONObject object;
            String linkJson = "https://spott.p.rapidapi.com/places?type=CITY&skip=0&limit=1&q="+tenThanhPho;
            String linkJsoup = "https://weather.com/vi-VN/weather/today/l/";
            try{
                Connection.Response res = Jsoup.connect(linkJson)
                        .followRedirects(true)
                        .ignoreContentType(true)
                        .header("x-rapidapi-host", "spott.p.rapidapi.com")
                        .header("x-rapidapi-key", "3e63d55898msh9b4abcff5eaa398p198644jsn254394a9fc52")
                        .method(Connection.Method.GET)
                        .execute();
                
                parse = new JSONParser();
                JSONArray data = (JSONArray) parse.parse(res.body());
                JSONObject getObjectArray = (JSONObject) data.get(0);
                jsonXuat.put("TenThanhPho", getObjectArray.get("name"));//tên tp
                jsonXuat.put("DanSo", getObjectArray.get("population"));
                object = (JSONObject) getObjectArray.get("country");
                jsonXuat.put("TenQuocGia", object.get("name"));//tên quốc gia
                jsonXuat.put("MaQuocGia", object.get("id"));//mã quốc gia
                object = (JSONObject) getObjectArray.get("adminDivision1");
                jsonXuat.put("KhuVucHanhChinh", object.get("name")/*.toString() +" "+ object.get("LocalizedType").toString()*/);//khu vực hành chính
                jsonXuat.put("MaGioDiaPhuong", getObjectArray.get("timezoneId"));//múi giờ
                object = (JSONObject) getObjectArray.get("coordinates");
                String Latitude = object.get("latitude").toString();
                String Longitude = object.get("longitude").toString();
                jsonXuat.put("Latitude", Latitude);//lat
                jsonXuat.put("Longitude", Longitude);//long
                //lấy thời tiết
                res = Jsoup.connect(linkJsoup + Latitude + "," + Longitude)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                Document doc = res.parse();
                //lấy nhiệt độ hiện tại
                jsonXuat.put("NhietDoHienTai", doc.select("span[class$=CurrentConditions--tempValue--3a50n]").text());
                jsonXuat.put("TinhHinh", doc.select("div[data-testid$=wxPhrase]").text());
                //lấy thông tin liên quan
                Elements e  = doc.select("div[class$=TodayDetailsCard--detailsContainer--16Hg0]");
                jsonXuat.put("DoAm", e.select("span[data-testid$=PercentageValue]").text());
                String[] gio = e.select("span[data-testid$=Wind]").text().split("Wind Direction ");
                jsonXuat.put("TocDogio", gio[1]);
                jsonXuat.put("UV", e.select("span[data-testid$=UVIndexValue]").text());
                jsonXuat.put("TamNhin", e.select("span[data-testid$=VisibilityValue]").text());
                //lấy dự báo theo giờ
                e = doc.select("section[data-testid$=HourlyWeatherModule]");
                Elements a = e.select("h3");
                for(int i = 0; i<a.size();i++){
                    jsonXuat.put("Gio"+(i+1), a.get(i).text());
                }
                a=e.select("div[data-testid$=SegmentHighTemp]");
                for(int i = 0; i<a.size();i++){
                    jsonXuat.put("NhietDoTheoGio"+(i+1), a.get(i).text());
                }
                StringTokenizer bn = new StringTokenizer(e.select("div[data-testid$=SegmentPrecipPercentage]").text(),"RainKhả năng có mưa");
                int dem = 1;
                while(bn.hasMoreTokens()){
                    jsonXuat.put("KhaNangCoMuaTheoGio"+dem, bn.nextToken());
                    dem++;
                }
                //lấy dự báo hàng ngày
                e = doc.select("section[data-testid$=DailyWeatherModule]");
                a = e.select("h3");
                for(int i = 0; i<a.size();i++){
                    jsonXuat.put("Ngay"+(i+1), a.get(i).text());
                }
                a=e.select("div[data-testid$=SegmentHighTemp]");
                for(int i = 0; i<a.size();i++){
                    jsonXuat.put("NhietDoTheoNgayBanNgay"+(i+1), a.get(i).text());
                }
                a=e.select("div[data-testid$=SegmentLowTemp]");
                for(int i = 0; i<a.size();i++){
                    jsonXuat.put("NhietDoTheoNgayBanDem"+(i+1), a.get(i).text());
                }
                bn = new StringTokenizer(e.select("div[data-testid$=SegmentPrecipPercentage]").text(),"RainKhả năng có mưa");
                dem = 1;
                while(bn.hasMoreTokens()){
                    jsonXuat.put("KhaNangCoMuaTheoNgay"+dem, bn.nextToken());
                    dem++;
                }
            }catch(IOException | ParseException | IndexOutOfBoundsException e){
                jsonXuat.put("status", "fail");
                return jsonXuat.toString();
            }
            jsonXuat.put("status", "success");
            return jsonXuat.toString();
        }
        
        private String danhSachQuocGia(){
            JSONArray mangXuat = new JSONArray();
            String link = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=vi&countarrayListNationry=&username=leminhcuong2988&style=full";
            String linkViTriHienTai = "http://ip-api.com/json/";
            jsonXuat = new  JSONObject();
            
            //Xử lý tại đây
            try{
                Connection.Response getCountryCode = Jsoup.connect(linkViTriHienTai)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                parse = new JSONParser();
                JSONObject code = (JSONObject) parse.parse(getCountryCode.body());
                String maQuocGia = code.get("countryCode").toString();
                
                Connection.Response res = Jsoup.connect(link)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                parse = new JSONParser();
                JSONObject data = (JSONObject) parse.parse(res.body());
                JSONArray arr = (JSONArray) data.get("geonames");
                for(int i=0;i<arr.size();i++){
                    JSONObject getObjectArray = (JSONObject) arr.get(i);
                    JSONObject newObject = new JSONObject();
                    newObject.put("countryCode", getObjectArray.get("countryCode"));
                    newObject.put("countryName", getObjectArray.get("countryName"));
                    if(getObjectArray.get("countryCode").equals(maQuocGia)){
                        jsonXuat.put("QuocGiaHienTai", traCuuQuocGia(getObjectArray.get("countryName").toString()));
                    }
                    mangXuat.add(newObject);
                }
            }catch(IOException | ParseException e){
                jsonXuat.put("status", "fail");
                return "Lỗi từ phía server." + e.getMessage();
            }
            jsonXuat.put("status", "success");
            jsonXuat.put("geonames", mangXuat);
            jsonXuat.put("function", "danhsachquocgia");//để dưới đây để khỏi trùng lập  việc gọi hàm tra cứu
            return jsonXuat.toString();
        }
        private String traCuuQuocGia(String countryName){
            jsonXuat = new JSONObject();
            jsonXuat.put("function", "tracuudialyquocgia");
            String linkJson = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=vi&countarrayListNationry=&username=leminhcuong2988&style=full";
            String linkJsoup = "https://www.geonames.org/countries/";
            //Xử lý tại đây
            try{
                Connection.Response res = Jsoup.connect(linkJson)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                parse = new JSONParser();
                JSONObject data = (JSONObject) parse.parse(res.body());
                JSONArray arr = (JSONArray) data.get("geonames");
                boolean check = false;
                String countryCode = null;
                //String countryID = null;
                for(int i=0;i<arr.size();i++){
                    JSONObject getObjectArray = (JSONObject) arr.get(i);
                    String name = String.valueOf(getObjectArray.get("countryName"));
                    if(name.compareToIgnoreCase(countryName)==0){
                        jsonXuat.put("status", "success");
                        jsonXuat.put("geonameId", getObjectArray.get("geonameId"));
                        jsonXuat.put("countryName", getObjectArray.get("countryName"));
                        jsonXuat.put("capital", getObjectArray.get("capital"));
                        countryCode = getObjectArray.get("countryCode").toString();
                        jsonXuat.put("countryCode", countryCode);
                        jsonXuat.put("continentName", getObjectArray.get("continentName"));
                        check = true;
                        //countryID = getObjectArray.get("geonameId").toString();
                        break;
                    }
                }
                if(check){
                    linkJsoup = linkJsoup + countryCode + "/";
                    res = Jsoup.connect(linkJsoup)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();
                    Document doc = res.parse();
                    Elements e = doc.select("table[cellpadding$=5] tr td:nth-of-type(2)");
                    jsonXuat.put("area", e.get(4).text());
                    jsonXuat.put("population", e.get(5).text());
                    jsonXuat.put("currency", e.get(6).text());
                    jsonXuat.put("languages", e.get(7).text());
                    jsonXuat.put("neighbours", e.get(8).text());
//                    res = Jsoup.connect("https://www.geonames.org/"+countryID)
//                        .ignoreContentType(true)
//                        .method(Connection.Method.GET)
//                        .execute();
                    
                }else{
                    jsonXuat.put("status", "fail");
                }
            }catch(IOException | ParseException e){
                jsonXuat.put("status", "fail");
                return jsonXuat.toString();
            }
            return jsonXuat.toString();
        }
    }
}

//private String traCuuThanhPho(String tenThanhPho){
//    jsonXuat = new  JSONObject();
//    JSONObject object;
//    String linkJson = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=aAEQINHigdUn6V5oX17hOAzreBhQGeRT&q="+tenThanhPho;//aAEQINHigdUn6V5oX17hOAzreBhQGeRT//682500PcukwQUtq1UDd6XimUfAmBA5HL
//    String linkJsoup = "https://weather.com/vi-VN/weather/today/l/";
//    try{
//        Connection.Response res = Jsoup.connect(linkJson)
//                .ignoreContentType(true)
//                .method(Connection.Method.GET)
//                .execute();
//
//        parse = new JSONParser();
//        JSONArray data = (JSONArray) parse.parse(res.body());
//        JSONObject getObjectArray = (JSONObject) data.get(0);
//        jsonXuat.put("TenThanhPho", getObjectArray.get("LocalizedName"));//tên tp
//        object = (JSONObject) getObjectArray.get("Region");
//        jsonXuat.put("ChauLuc", object.get("LocalizedName"));//tên châu lục
//        object = (JSONObject) getObjectArray.get("Country");
//        jsonXuat.put("TenQuocGia", object.get("LocalizedName"));//tên quốc gia
//        jsonXuat.put("MaQuocGia", object.get("ID"));//mã quốc gia
//        object = (JSONObject) getObjectArray.get("AdministrativeArea");
//        jsonXuat.put("KhuVucHanhChinh", object.get("LocalizedName")/*.toString() +" "+ object.get("LocalizedType").toString()*/);//khu vực hành chính
//        object = (JSONObject) getObjectArray.get("TimeZone");
//        jsonXuat.put("MaGioDiaPhuong", object.get("Code"));//múi giờ
//        jsonXuat.put("TenGioDiaPhuong", object.get("Name"));
//        jsonXuat.put("MaGioQuocTe", object.get("GmtOffset"));
//        object = (JSONObject) getObjectArray.get("GeoPosition");
//        String Latitude = object.get("Latitude").toString();
//        String Longitude = object.get("Longitude").toString();
//        jsonXuat.put("Latitude", Latitude);//lat
//        jsonXuat.put("Longitude", Longitude);//long
//        //lấy thời tiết
//        res = Jsoup.connect(linkJsoup + Latitude + "," + Longitude)
//                .ignoreContentType(true)
//                .method(Connection.Method.GET)
//                .execute();
//        Document doc = res.parse();
//        //lấy nhiệt độ hiện tại
//        jsonXuat.put("NhietDoHienTai", doc.select("span[class$=CurrentConditions--tempValue--3a50n]").text());
//        jsonXuat.put("TinhHinh", doc.select("div[data-testid$=wxPhrase]").text());
//        //lấy thông tin liên quan
//        Elements e  = doc.select("div[class$=TodayDetailsCard--detailsContainer--16Hg0]");
//        jsonXuat.put("DoAm", e.select("span[data-testid$=PercentageValue]").text());
//        String[] gio = e.select("span[data-testid$=Wind]").text().split("Wind Direction ");
//        jsonXuat.put("TocDogio", gio[1]);
//        jsonXuat.put("UV", e.select("span[data-testid$=UVIndexValue]").text());
//        jsonXuat.put("TamNhin", e.select("span[data-testid$=VisibilityValue]").text());
//        //lấy dự báo theo giờ
//        e = doc.select("section[data-testid$=HourlyWeatherModule]");
//        Elements a = e.select("h3");
//        for(int i = 0; i<a.size();i++){
//            jsonXuat.put("Gio"+(i+1), a.get(i).text());
//        }
//        a=e.select("div[data-testid$=SegmentHighTemp]");
//        for(int i = 0; i<a.size();i++){
//            jsonXuat.put("NhietDoTheoGio"+(i+1), a.get(i).text());
//        }
//        StringTokenizer bn = new StringTokenizer(e.select("div[data-testid$=SegmentPrecipPercentage]").text(),"RainKhả năng có mưa");
//        int dem = 1;
//        while(bn.hasMoreTokens()){
//            jsonXuat.put("KhaNangCoMuaTheoGio"+dem, bn.nextToken());
//            dem++;
//        }
//        //lấy dự báo hàng ngày
//        e = doc.select("section[data-testid$=DailyWeatherModule]");
//        a = e.select("h3");
//        for(int i = 0; i<a.size();i++){
//            jsonXuat.put("Ngay"+(i+1), a.get(i).text());
//        }
//        a=e.select("div[data-testid$=SegmentHighTemp]");
//        for(int i = 0; i<a.size();i++){
//            jsonXuat.put("NhietDoTheoNgayBanNgay"+(i+1), a.get(i).text());
//        }
//        a=e.select("div[data-testid$=SegmentLowTemp]");
//        for(int i = 0; i<a.size();i++){
//            jsonXuat.put("NhietDoTheoNgayBanDem"+(i+1), a.get(i).text());
//        }
//        bn = new StringTokenizer(e.select("div[data-testid$=SegmentPrecipPercentage]").text(),"RainKhả năng có mưa");
//        dem = 1;
//        while(bn.hasMoreTokens()){
//            jsonXuat.put("KhaNangCoMuaTheoNgay"+dem, bn.nextToken());
//            dem++;
//        }
//    }catch(IOException | ParseException | IndexOutOfBoundsException e){
//        jsonXuat.put("status", "fail");
//        return jsonXuat.toString();
//    }
//    jsonXuat.put("status", "success");
//    return jsonXuat.toString();
//}