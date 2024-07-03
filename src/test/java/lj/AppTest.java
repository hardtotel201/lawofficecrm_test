package lj;

import static org.junit.Assert.assertTrue;

import lj.service.weixin.WxBaseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppTest {

    
  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);
  }

  @Test
  public void testGetWxToken() {
    WxBaseService wxBaseService = new WxBaseService();
    wxBaseService.getWxToken();
  }
  @Test
  public void testCreateQr() {
    WxBaseService wxBaseService = new WxBaseService();
    String value1 = wxBaseService.createQRImg("pages/index/index", "value1");
    System.out.println(value1);
  }
}
