package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class httpURLConectionGET {
   // public static final String GET_URL = "http://fy.iciba.com/ajax.php?a=fy&f=zh&t=en&w=%E6%88%91%E6%98%AF%E8%B0%81dasd";

    /**
     * 接口调用 GET
     */
    public static String GET(String GET_URL) {


        try {
            URL url = new URL(GET_URL);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            //System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
        return "error";
    }

    public static String Translate(String from, String to, String text){

        String GET_URL = "http://fy.iciba.com/ajax.php?a=fy&f="+from+"&t="+to+"&w="+text;
        String getJson = GET(GET_URL);

        //String str = "{ status :1,content:{from:zh,to:en,vendor:tencent,out:I'm a test}}";
        Document document = Document.parse(getJson);
        System.out.println( document.toJson());

        String status =  document.get("status")+"";
        String out;
        if (status.equals("1")){
            Document content = (Document) document.get("content");
            out = content.getString("out");

        }else{

            out="error";
        }

        return out;

    }



}
