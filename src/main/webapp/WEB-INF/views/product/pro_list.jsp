<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>Pricing example · Bootstrap v4.6</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/pricing/">

    <%@ include file="/WEB-INF/views/include/config.jsp" %>

<meta name="theme-color" content="#563d7c">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
  </head>
  <body>
    
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<%@ include file="/WEB-INF/views/include/categoryMenu.jsp" %>


<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
  <h4 class="display-4">${cat_name }</h4>
</div>

<div class="container">
 <div class="row">
  <div class="card-deck mb-3 text-center row">
    <c:forEach items="${pro_list }" var="productVO">
    <div class="col-md-3">
	    <div class="card mb-4 shadow-sm">
	      <div class="card-header">
	        <h5>상품명: <a class="move" href="${productVO.pro_num}">${productVO.pro_name}</a></h5>
	      </div>
	      <div class="card-body">
	        <h5> <fmt:formatNumber type="currency" pattern="₩#,###" value="${productVO.pro_price}"/></h5>
	        <a class="move" href="${productVO.pro_num}"><img style="width: 100px; height: 67px" src="/product/displayImage?folderName=${productVO.pro_up_folder }&fileName=s_${productVO.pro_img}"></a> 
	        <button type="button" name="btn_cart"  data-pro_num="${productVO.pro_num}" class="btn btn-link">장바구니</button>
	        <button type="button" name="btn_direct_order" data-pro_num="${productVO.pro_num}" class="btn btn-link">바로구매</button>
	      </div>
	    </div>
    </div>
    </c:forEach>
    
  </div>
 </div>
 <div class="row">
 	<div class="col-md-12">
 		<ul class="pagination pagination-sm no-margin pull-right">
                <c:if test="${pageMaker.prev }">
                	<li><a href="${pageMaker.startPage - 1}">[prev]</a></li>
                </c:if>
                
                <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pageNum">
                	<li ${pageMaker.cri.pageNum == pageNum ? "class='active'": ""}><a href="${pageNum }">${pageNum }</a></li>
                </c:forEach>
                
                
                <c:if test="${pageMaker.next }">
                	<li><a href="${pageMaker.endPage + 1}">[next]</a></li>
                </c:if>
              </ul>
              <!-- 페이징정보. Criteria 클래스 필드정보작업 -->
              <form id="actionForm" action="/product/pro_list" method="get">
                <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
                <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                <!-- 
                <input type="hidden" name="type" value="${pageMaker.cri.type}">
                <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
                 -->              
              </form>
 	</div>
 </div>
  <%@include file="/WEB-INF/views/include/footer.jsp" %>
</div>

<script>

    $(document).ready(function() {

      let actionForm = $("#actionForm"); // 페이징정보가 있는 form태그. Criteria클래스 필드정보

      // 상품명 또는 상품이미지 클릭
      $("a.move").on("click", function(e) {
        e.preventDefault(); // a태그의 하이퍼링크 기능이 취소.

        let pro_num = $(this).attr("href");
        
        console.log("상품코드: " + pro_num);

        //location.href = "/board/get?bno=" + bno;
        // 목록에서 상품번호 선택후, 다시 뒤로가기버튼 클릭시 나타나는 문제발생때문에 아래코드 작업이 필요.
        actionForm.find("input[name='pro_num']").remove();

        actionForm.append("<input type='hidden' name='pro_num' value='" + pro_num + "'>");
        actionForm.attr("action", "/product/pro_detail");

        actionForm.submit();

      });

      // 페이지 번호, 이전, 다음 클릭시 actionForm 태그가 전송으로 사용된다.(중요)
      // 페이지번호, 이전, 다음 클릭    <ul class="pagination.  
      $("ul.pagination a").on("click", function(e) {
        e.preventDefault(); // a태그의 하이퍼링크 기능이 취소.

        // /board/list?pageNum=선택한페이지번호&amount=10

        // 현재 페이지번호 변경작업
        // form태그의 정보  
        // <input type="hidden" name="pageNum" value="1">
        // <input type="hidden" name="amount" value="10">
        actionForm.attr("action", "/product/pro_list/${cat_code}/${cat_name}");
        actionForm.find("input[name='pageNum']").val($(this).attr("href"));

        actionForm.submit();
      });

      // 장바구니 클릭
      $("button[name='btn_cart']").on("click", function() {

        $.ajax({
          url: '/cart/cart_add',
          type: 'post',
          data: {pro_num : $(this).data("pro_num"), cart_amount : 1},
          success: function(result) {
            if(result == "success") {
              alert("장바구니에 추가됨");
              if(confirm("장바구니로 이동 하시겠습니까?")) {
                location.href = "/cart/cart_list";
              }
            }
          }
        });
      });

      // 바로구매 클릭(주문정보 입력폼으로 이동, 장바구니에 상품 담고 이동이 된다.)
      $("button[name='btn_direct_order']").on("click", function() {

        // /cart/direct_cart_add?pro_num=1&cart_amount=1
        let url = "/cart/direct_cart_add?pro_num=" + $(this).data("pro_num") + "&cart_amount=1";
        
        console.log("url: " + url);
        
        location.href = url;
      });

    }); // ready event end 
  </script>
    
  </body>
</html>