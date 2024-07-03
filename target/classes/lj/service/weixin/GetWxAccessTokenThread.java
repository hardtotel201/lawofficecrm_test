package lj.service.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nichols
 * @date 2023/5/22 11:37
 */
@Service
public class GetWxAccessTokenThread extends Thread {
    public static String token= "";

    public static String wxOfficialAccountAccessToken = "";

    @Autowired
    private WxBaseService wxBaseService;

    @Override
    public void run() {
        for (;;) {
            try {
                // 间隔一小时获取一次token
                token = wxBaseService.getWxToken();
                wxOfficialAccountAccessToken = wxBaseService.getWxOfficialAccountToken();
                Thread.sleep(60*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
