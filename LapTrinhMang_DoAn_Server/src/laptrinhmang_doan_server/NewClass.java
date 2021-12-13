/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptrinhmang_doan_server;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 *
 * @author LENOVO
 */
public class NewClass {
        private JSONParser parse;
        private JSONObject jsonXuat;
    private String traCuuCovid(String tenQuocGia, String ngayBatDau, String ngayKetThuc){
            StringTokenizer st =new StringTokenizer(ngayBatDau,"T");
            String dateStart = st.nextToken();
            System.out.println(dateStart);
////            //trừ đi 1 ngày
            LocalDate dateStartT = LocalDate.parse(dateStart);
            LocalDate newNgayBatDau = dateStartT.minusYears(0).minusMonths(0).minusWeeks(0).minusDays(1);
            ngayBatDau = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(newNgayBatDau)+"T00:00:00Z";
            System.out.println(ngayBatDau);
              
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
                int max=arr.size()-1;
                JSONObject dataMax = (JSONObject) arr.get(max);
                JSONObject Tong = new JSONObject();
                Tong.put("Confirmed", dataMax.get("Confirmed"));
                Tong.put("Deaths", dataMax.get("Deaths"));
                Tong.put("Recovered", dataMax.get("Recovered"));
                Tong.put("Active", dataMax.get("Active"));
                jsonXuat.put("Tong", Tong);
                int newConfirmed, newDeaths, newRecovered, newActive;
                for(int i = 1; i<arr.size();i++)
                {
                    JSONObject getObjectArrayB = (JSONObject) arr.get(i-1);
                    JSONObject getObjectArrayA = (JSONObject) arr.get(i);
                    newConfirmed = Integer.parseInt(getObjectArrayA.get("Confirmed").toString())
                            -Integer.parseInt(getObjectArrayB.get("Confirmed").toString());
                    newDeaths = Integer.parseInt(getObjectArrayA.get("Deaths").toString())
                            -Integer.parseInt(getObjectArrayB.get("Deaths").toString());
                    newRecovered = Integer.parseInt(getObjectArrayA.get("Recovered").toString())
                            -Integer.parseInt(getObjectArrayB.get("Recovered").toString());
                    newActive = Integer.parseInt(getObjectArrayA.get("Active").toString())
                            -Integer.parseInt(getObjectArrayB.get("Active").toString());
                    JSONObject newObject = new JSONObject();
                    newObject.put("Confirmed", newConfirmed);
                    newObject.put("Deaths", newDeaths);
                    newObject.put("Recovered", newRecovered);
                    newObject.put("Active", newActive);
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
    
    public static void main(String[] args) {
        NewClass a = new NewClass();
        System.out.println(a.traCuuCovid("VietNam","2021-05-05T00:00:00Z","2021-05-06T00:00:00Z"));
    }
    
}
