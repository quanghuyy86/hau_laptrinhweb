function AddProductToCart(productId, quanlity){
    // javascript object.  data la du lieu ma day len action cua controller
    let data = {
        productId: productId, 	// Id sản phẩm
        quanlity: quanlity, 	// Số lượng cho vào giỏ hàng
    };

    // $ === jQuery
    // json == javascript object
    jQuery.ajax({
        url: "http://localhost:8080" + "/addToCart", //->action
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(data),

        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            // alert("Tổng số lượng sản phẩm có trong giỏ hàng là: " + jsonResult.totalItems)

            // tăng số lượng sản phẩm trong giỏ hàng trong icon
            $("#showTotalItemInCart").html(jsonResult.totalItems);
            $("#total_price").html(jsonResult.totalPrice);
            /*$([document.documentElement, document.body]).animate({
                scrollTop: $("#iconShowTotalItemsInCart").offset().top
            }, 2000);*/

        },
        error: function(jqXhr, textStatus, errorMessage) {

        }
    });
}

function DeleteProductTocart(btnDeleteObj, productId, deleteUrl) {
    //tạo javascript object
    let data = {
        productId: productId,
    };
    //$===jQuery
    //json == javascript object
    jQuery.ajax({
        url: deleteUrl,
        type: "post",
        contentType: "application/json",
        data: JSON.stringify(data),

        dataType: "json",
        success: function(jsonResult) {
                $(btnDeleteObj).closest('.hang').remove();
                $("#total_price").text(jsonResult.totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }));
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert("error");
        }
    });
}

$(document).ready(function (e) {
   $('.btn-delete-sanpham').on('click', function () {
       let productId = $(this).attr('data-productId');
       let deleteUrl = $(this).attr('data-deleteUrl');
       DeleteProductTocart($(this), productId, deleteUrl);
   });
});