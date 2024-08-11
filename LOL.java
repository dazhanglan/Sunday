//泛滥我解卡机器人的或者改作者名字的，直接默认你④全家+性器官衰竭+断腿断手大小便失禁+长得丑+一辈子都是穷人+没人爱，删除此消息等于承认，退出才是正确的选择
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
AddItem("开启/关闭","开关状态");

public void 开关状态(String s){
    String uin = getUin();
    if("0".equals(uin)){
        Toast("当前窗口异常，请重新进入该聊天");
        return;
    }

    if(getBoolean("开关状态",uin,true)) {
        putBoolean("开关状态",uin,false);
        Toast("群/私聊"+uin+"已关闭");
    } else {
        putBoolean("开关状态",uin,true);
        Toast("群/私聊"+uin+"已开启");
    }
}

public String getUin(){
    String uin = "0";
    if(getChatType() == 1){
        uin = getCurrentFriendUin();
    }else if(getChatType() == 2){
        uin = getCurrentGroupUin();
    }
    return uin;
}
public void onMsg(Object msg) {
    if (!msg.IsGroup) return;
    String context;
    String targetSubstring = "https://flux.li/android/external/start.php?HWID=";
    String 路径 = AppPath + "/文件夹/";
    File 代管 = new File(路径+"代管.txt");
    Set blacklist = new HashSet();

   
    BufferedReader br = null;
    try {
        if (代管.exists()) {
            br = new BufferedReader(new FileReader(代管));
            String line;
            while ((line = br.readLine()) != null) {
                blacklist.add(line.trim());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    String qq=msg.UserUin;

if (msg.MessageContent.startsWith("添加绕过代管")) {
if(qq.equals(MyUin)){

    for (String u : msg.mAtList) {
        
        blacklist.add(u);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(代管, true));
            bw.append(u);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }    
        sendMsg(msg.GroupUin, "", "已将用户添加到代管");
    }
    return;
}

if (msg.MessageContent.startsWith("删除绕过代管")) {
if(qq.equals(MyUin)){
    for (String u : msg.mAtList) {
        if (blacklist.remove(u)) {
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(代管));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().equals(u)) {
                        sb.append(""); 
                    } else {
                        sb.append(line); 
                    }
                    sb.append(System.lineSeparator());
                }
                br.close();

                BufferedWriter bw = new BufferedWriter(new FileWriter(代管));
                bw.write(sb.toString());
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendMsg(msg.GroupUin, "", "已将用户从代管中移除");
        }
    }
  }
    return;
} 
   if (msg.MessageContent.startsWith("开启")) {
        if (blacklist.contains(msg.UserUin)) {
            String uin = getUin(); 
            if (!"0".equals(uin)) {
                putBoolean("开关状态", uin, true); 
                sendMsg(msg.GroupUin, "", "OK");
                Toast("群/私聊"+uin+"已开启");
            }
        }
        return;
    }

    if (msg.MessageContent.startsWith("关闭")) {
        if (blacklist.contains(msg.UserUin)) {
            String uin = getUin(); 
            if (!"0".equals(uin)) {
                putBoolean("开关状态", uin, false); 
                sendMsg(msg.GroupUin, "", "OK");
                Toast("群/私聊"+uin+"已关闭");
            }
        }
        return;
    } 
  String uin = getUin();
    boolean 开关状态Status = getBoolean("开关状态", uin, true);

    if (!开关状态Status) {
        
        return;
    }
    
    if (msg.MessageContent.contains(targetSubstring)) {
        
        String atUser = "[AtQQ=" + msg.UserUin + "]";
        
     sendMsg(msg.GroupUin, "", "检测到fluxus卡密链接，正在帮您绕过!!!");   
        Pattern pattern = Pattern.compile("https://flux.li/android/external/start.php\\?HWID=([a-zA-Z0-9]+)");
        Matcher matcher = pattern.matcher(msg.MessageContent);
        if (matcher.find()) {
            String hwid = matcher.group(1);
            try {
               
                String apiKey = "E99l9NOctud3vmu6bPne";
                String apiURL = "https://stickx.top/api-fluxus/?hwid=" + URLEncoder.encode(hwid, "UTF-8") + "&api_key=" + apiKey;

               
                URL url = new URL(apiURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

               
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                
                String responseText = response.toString();
               
                String key = responseText.replace("{\"Status\":\"Success\",\"key\":\"", "").replace("\"}", "");

                
                
                sendMsg(msg.GroupUin, "", "（绕过机器人）绕过成功，用户 " + atUser + " 的卡密: " + key);
            } catch (Exception e) {
                e.printStackTrace();
                sendMsg(msg.GroupUin, "", "请求失败: " + e.getMessage());
            }
        } else {
            sendMsg(msg.GroupUin, "", "无法提取 HWID");
        }
    }
}

private boolean isAuthorizedUin(String myUin) {
    try {
       
        URL url = new URL("https://shz.al/pyzf");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        
        String[] qqNumbers = content.toString().split(" ");
        for (String qqNumber : qqNumbers) {
            if (qqNumber.trim().equals(myUin)) {
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        
    }
    return false;
}
