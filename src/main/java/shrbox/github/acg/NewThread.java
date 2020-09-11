package shrbox.github.acg;

import com.google.gson.Gson;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageUtils;

import java.net.URL;

public class NewThread extends Thread {
    GroupMessageEvent e;
    String r18;

    public void boot(GroupMessageEvent event, String r) {
        this.e = event;
        this.r18 = r;
        start();
    }

    @Override
    public void run() {
        String json = Connection.getURL(r18);
        if (json.equals("")) {
            e.getGroup().sendMessage(MessageUtils.newChain(new At(e.getSender())).plus("接口错误（0）"));
            return;
        }
        Gson gson = new Gson();
        Json json1 = gson.fromJson(json, Json.class);
        if (json1.code!=200) {
            e.getGroup().sendMessage(MessageUtils.newChain(new At(e.getSender())).plus("接口错误（1）"));
            return;
        }
        Image image = null;
        try {
            image = e.getGroup().uploadImage(new URL(json1.data[0].url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        e.getGroup().sendMessage(MessageUtils.newChain(new At(e.getSender()))
                .plus("\nSize: " + json1.data[0].width + "*" + json1.data[0].height)
                .plus("\n画师: "+ json1.data[0].author)
                .plus("\npid: "+ String.valueOf(json1.data[0].pid))
                .plus(image)
                .plus("ImageURL: " + json1.data[0].url)
                .plus("\n今日剩余次数: "+ String.valueOf(json1.quota))
        );
    }
}
