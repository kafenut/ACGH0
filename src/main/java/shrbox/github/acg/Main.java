package shrbox.github.acg;

import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageUtils;

import java.util.Timer;
import java.util.TimerTask;

class Main extends PluginBase {
    public static int count = 0;

    public void onEnable() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count = 0;
            }
        }, 60 * 1000, 60 * 1000);
        System.setProperty("http.agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400)");
        getEventListener().subscribeAlways(GroupMessageEvent.class, (GroupMessageEvent e) -> {
            String inputString = e.getMessage().contentToString().toLowerCase();
            if (inputString.equals("色图") || inputString.equals("涩图") || inputString.equals("不够色") || inputString.equals("不够涩")) {
                if (count > 15) {
                    e.getGroup().sendMessage(MessageUtils.newChain(new At(e.getSender())).plus("一分钟内调用次数上限！"));
                    return;
                }
                count++;
                int random=(int)(Math.random()*10+1);
                if(random == 2) {
                    NewThread newThread = new NewThread();
                    newThread.boot(e,"1");
                }else if(random == 8){
                    NewThread newThread = new NewThread();
                    newThread.boot(e,"2");
                }else {
                    NewThread newThread = new NewThread();
                    newThread.boot(e,"0");
                }

            }

        });
    }
}