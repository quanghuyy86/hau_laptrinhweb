package vn.edu.hau.cake.controller.administrator;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hau.cake.model.Contact;
import vn.edu.hau.cake.model.SaleOrder;
import vn.edu.hau.cake.repository.SaleOrderRepository;
import vn.edu.hau.cake.service.SaleOderService;

@Controller
public class AdminSaleoderController {

  @Autowired
  private SaleOderService saleOderService;

  @RequestMapping(value = "/admin/saleorder", method = RequestMethod.GET)
  public String listSaleorder(final Model model,
                              final @Param("keyword") String keyword,
                              final @RequestParam(name = "page", defaultValue = "0")int page){
    int pageSize = 5;

    Page<SaleOrder> saleOrdersList =  saleOderService.gettAllSaleorder(page, pageSize);
    if(keyword != null){
      saleOrdersList = saleOderService.searchPageSaleorder(keyword, page, pageSize);
      model.addAttribute("keyword", keyword);
    }
    model.addAttribute("saleorder", saleOrdersList);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", saleOrdersList.getTotalPages());
    return "saleorder";
  }

  @RequestMapping(value = "/admin/saleorder/{id}", method = RequestMethod.GET)
  public String saleorderProduct(final Model model,
                                 final @PathVariable("id") Integer id){

    SaleOrder saleOrder = saleOderService.findSaleOrderWithProducts(id);
    if (saleOrder != null) {
      model.addAttribute("saleOrder", saleOrder);
      return "saleorderproduct";
    } else {
      return null;
    }

  }

  @RequestMapping(value = "/admin/saleorder/{id}", method = RequestMethod.POST)
  public String confirmSaleOrder(@PathVariable Integer id) {

    SaleOrder saleOrder = saleOderService.findSaleOrderWithProducts(id);
    // Hoặc thực hiện xử lý khác
    if (saleOrder != null) {
      saleOrder.setStatus(true); // Đánh dấu đơn hàng đã được xác nhận
      saleOderService.save(saleOrder); // Lưu lại đơn hàng đã cập nhật
      return "redirect:/admin/saleorder";
      // Redirect hoặc thực hiện hành động khác sau khi xác nhận thành công
    } else {
      return null;
      // Xử lý trường hợp không tìm thấy đơn hàng
    }

  }



}
