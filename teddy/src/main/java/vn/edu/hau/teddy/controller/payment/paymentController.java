package vn.edu.hau.teddy.controller.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hau.teddy.conf.Config;
import vn.edu.hau.teddy.dto.PaymentResDTO;
import vn.edu.hau.teddy.dto.TransactionStatusDTO;
import vn.edu.hau.teddy.dto.Cart;
import vn.edu.hau.teddy.dto.CartItem;

@RestController
@RequestMapping("/api/payment")
public class paymentController {
  public BigDecimal getTotalPrice(final HttpServletRequest request) {
    HttpSession httpSession = request.getSession();

    if (httpSession.getAttribute("cart") == null) {
      return BigDecimal.ZERO;
    }

    Cart cart = (Cart) httpSession.getAttribute("cart");
    List<CartItem> cartItems = cart.getCartItems();

    BigDecimal total = BigDecimal.ZERO;
    for (CartItem item : cartItems) {
      BigDecimal itemTotal = item.getPriceUnit().multiply(BigDecimal.valueOf(item.getQuanlity()));
      total = total.add(itemTotal);
    }

    return total;
  }


  @GetMapping("/create_payment")
  public ResponseEntity<?> createPayment(final HttpServletRequest request) throws UnsupportedEncodingException {

    BigDecimal amount = getTotalPrice(request).multiply(new BigDecimal(100));
    String orderType = "other";
    String vnp_TxnRef = Config.getRandomNumber(8);
    String Vnp_IpAddr = "127.0.0.1";
    String vnp_TmnCode = Config.vnp_TmnCode;

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", Config.vnp_Version);
    vnp_Params.put("vnp_Command", Config.vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_CurrCode", "VND");
//    vnp_Params.put("vnp_BankCode", "NCB");
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
    vnp_Params.put("vnp_OrderType", orderType);
    vnp_Params.put("vnp_Locale", "vn");
    vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
    vnp_Params.put("vnp_IpAddr", Vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

    PaymentResDTO paymentResDTO = new PaymentResDTO();
    paymentResDTO.setStatus("OK");
    paymentResDTO.setMessage("Successfully");
    paymentResDTO.setURL(paymentUrl);

    return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
  }

  @GetMapping("/payment_infor")
  public ResponseEntity<?> transaction(@RequestParam(value = "vnp_Amount") String amount,
                                       @RequestParam(value = "vnp_BankCode") String bankCode,
                                       @RequestParam(value = "vnp_OrderInfo") String order,
                                       @RequestParam(value = "vnp_ResponseCode") String responseCode) {
    TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
    if (responseCode.equals("00")){
      transactionStatusDTO.setStatus("OK");
      transactionStatusDTO.setMessage("Successfully");
      transactionStatusDTO.setData("");
    }else {
      transactionStatusDTO.setStatus("No");
      transactionStatusDTO.setMessage("Failse");
      transactionStatusDTO.setData("");
    }
    return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
  }

}
