package vn.edu.hau.cake.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
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
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hau.cake.conf.Config;
import vn.edu.hau.cake.dto.Cart;
import vn.edu.hau.cake.dto.CartItem;
import vn.edu.hau.cake.model.Product;
import vn.edu.hau.cake.model.SaleOrder;
import vn.edu.hau.cake.model.SaleOrderProducts;
import vn.edu.hau.cake.service.ProductService;
import vn.edu.hau.cake.service.SaleOderService;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SaleOderService saleOderService;


    @RequestMapping(value = { "/addToCart" }, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> ajax_AddToCart(final Model model,
                                                              final HttpServletRequest request,
                                                              final HttpServletResponse response,
                                                              final @RequestBody CartItem cartItem)throws IOException {
        HttpSession session = request.getSession();

        Cart cart = null;

        if (session.getAttribute("cart") != null) {
            cart = (Cart) session.getAttribute("cart");
        } else {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        List<CartItem> cartItems = cart.getCartItems();

        boolean isExists = false;
        for (CartItem item : cartItems) {
            if (item.getProductId() == cartItem.getProductId()) {
                isExists = true;
                item.setQuanlity(item.getQuanlity() + cartItem.getQuanlity());
            }
        }

        if (!isExists) {

            Optional<Product> productInDb = productService.findById(cartItem.getProductId());

            cartItem.setProductName(productInDb.get().getTitle());
            cartItem.setPriceUnit(productInDb.get().getPrice());
            cartItem.setAvatar(productInDb.get().getAvatar());

            cart.getCartItems().add(cartItem);

        }

        session.setAttribute("totalPrice", cart.getTotalPrice());

        Map<String, Object> jsonResult = new HashMap<String, Object>();
        jsonResult.put("code", 200);
        jsonResult.put("status", "TC");
        jsonResult.put("totalItems", getTotalItems(request));
        jsonResult.put("totalPrice", getTotalPrice(request));

        session.setAttribute("totalItems", getTotalItems(request));
        session.setAttribute("totalPrice", getTotalPrice(request));

        return ResponseEntity.ok(jsonResult);
    }

    private int getTotalItems(final HttpServletRequest request) {
        HttpSession httpSession = request.getSession();

        if (httpSession.getAttribute("cart") == null) {
            return 0;
        }

        Cart cart = (Cart) httpSession.getAttribute("cart");
        List<CartItem> cartItems = cart.getCartItems();

        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getQuanlity();
        }

        return total;
    }

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
    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String cartCheckout(final Model model,
        final HttpServletRequest request
    )throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("cart") != null) {
            return "cart";
        } else {
            return "cartnull";
        }
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkOut(final Model model,
                           final @ModelAttribute("saleOrder") SaleOrder SaleOrder,
                           final HttpServletRequest request) throws UnsupportedEncodingException {
//        String url = createPayment(request);
//        model.addAttribute("vnpay", url);
        return "checkout";
    }


    public String createPayment(final HttpServletRequest request) throws UnsupportedEncodingException {

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

        return paymentUrl;
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(final Model model,
                        final HttpServletRequest request){
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        if (vnp_ResponseCode != null && vnp_ResponseCode.equals("00")) {
            return "redirect:/success";
        } else {
            HttpSession session = request.getSession();
            Integer id = (Integer) session.getAttribute("id");
            saleOderService.deleteById(id);
            session.setAttribute("id", null);
            return "redirect:/failure";
        }
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String saveCheckOut(final Model model,
                               final HttpServletRequest request,
                               final @RequestParam("pay") String paymentMethod,
                               final @ModelAttribute("saleOrder") SaleOrder saleOrder)
        throws UnsupportedEncodingException {

        saleOrder.setCode(String.valueOf(System.currentTimeMillis()));
        saleOrder.setTotal(getTotalPrice(request));
        saleOrder.setStatus(false);

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        for(CartItem cartItem : cart.getCartItems()){
            SaleOrderProducts saleOrderProducts = new SaleOrderProducts();
            Optional<Product> productOptional = productService.findById(cartItem.getProductId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                // Gán sản phẩm cho SaleOrderProducts
                saleOrderProducts.setProduct(product);
                saleOrderProducts.setQuality(cartItem.getQuanlity());

                saleOrder.addSaleOrderProducts(saleOrderProducts);
            } else {
//                return "redirect:/home";
            }

        }
        if("VnPay".equals(paymentMethod)){
            String urlVnPay = createPayment(request);
            session.setAttribute("cart", null);
            session.setAttribute("totalItems", 0);

            saleOderService.save(saleOrder);
            Integer id = saleOrder.getId();
            session.setAttribute("id", id);
            return "redirect:" + urlVnPay;
        }
            saleOderService.save(saleOrder);
            session.setAttribute("cart", null);
            session.setAttribute("totalItems", 0);
            return "redirect:/success";
    }




    @RequestMapping(value = { "/cart/deleteitem" }, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> productDelete(final Model model,
                                                             final HttpServletRequest request,
                                                             final @RequestBody CartItem cartItem) {

        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        List<CartItem> cartItems = cart.getCartItems();

        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if(item.getProductId() == cartItem.getProductId()) {
                iterator.remove();
                break;
            }
        }

        Map<String, Object> jsonResult = new HashMap<String, Object>();
        jsonResult.put("code", 200);
        jsonResult.put("message", "Đã xóa thành công");
        jsonResult.put("totalPrice", getTotalPrice(request));

        session.setAttribute("totalItems", getTotalItems(request));
        session.setAttribute("totalPrice", getTotalPrice(request));

        return ResponseEntity.ok(jsonResult);
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String checkOutSuccess(final Model model){
        return "checkoutsuccess";
    }

    @RequestMapping(value = "/failure", method = RequestMethod.GET)
    public String checkOutFaild(final Model model){
        return "failure";
    }

}
