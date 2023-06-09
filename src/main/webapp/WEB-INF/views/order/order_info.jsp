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

    


    <!-- Favicons 
<link rel="apple-touch-icon" href="/docs/4.6/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
<link rel="manifest" href="/docs/4.6/assets/img/favicons/manifest.json">
<link rel="mask-icon" href="/docs/4.6/assets/img/favicons/safari-pinned-tab.svg" color="#563d7c">
<link rel="icon" href="/docs/4.6/assets/img/favicons/favicon.ico">
<meta name="msapplication-config" content="/docs/4.6/assets/img/favicons/browserconfig.xml">
-->
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
  <h4 class="display-4">주문하기</h4>
</div>

<div class="container">
 <div class="row">
  <div class="col-md-12">
      <div>
        <h4 class="mb-3">주문자정보</h4>
        <div class="row border-top pt-3"> <!-- 위쪽 라인과 패딩 여백 5까지 쓸 수 있다. -->
            <div class="col-md-2">
              <label for="mbsp_name">이름</label>
            </div>
            <div class="col-md-10">
              ${memberVO.mbsp_name }
            </div>
        </div>
          
          <div class="row pt-3">
            <div class="col-md-2">
              <label for="mbsp_email">전자우편</label>
            </div>
            <div class="col-md-10">
              <input type="hidden" id="order_productName" value="${order_productName }">
              <input type="text" class="form-control" name="d_mbsp_email" id="d_mbsp_email" value="${memberVO.mbsp_email }">
            </div>
          </div>
          <div class="row pt-3">
            <div class="col-md-2">
              <label for="mbsp_phone">연락처</label>
            </div>
            <div class="col-md-10">
              <input type="text" class="form-control" name="d_mbsp_phone" id="d_mbsp_phone" value="${memberVO.mbsp_phone }">
            </div>
          </div>
          <h4 class="mb-3 mt-3">배송정보<span style="float: right; margin-right: 10px;"><input type="checkbox" id=""><small>위 정보와 같음.</small></span></h4>
          <div class="row border-top pt-3">
            <div class="col-md-2">
              <label for="ord_name">이름</label>
            </div>
            <div class="col-md-10">
              <input type="text" class="form-control" name="ord_name" id="ord_name">
            </div>
        </div>
          
          <div class="row pt-3">
            <div class="col-md-2">
              <label for="ord_tel">연락처</label>
            </div>
            <div class="col-md-10">
              <input type="text" class="form-control" name="ord_tel" id="ord_tel">
            </div>
          </div>
          <div class="row pt-3">
	    	  <div class="col-md-2">
		         <label for="mbsp_addr">주소</label>
		      </div>
	          <div class="col-md-10">
			     <input type="text" class="form-control" name="ord_addr_post" id="sample2_postcode" style="margin-bottom: 15px;">
			     <button type="button" onclick="sample2_execDaumPostcode()" class="btn btn-primary" style="margin-bottom: 15px;">우편번호 찾기</button>
			     <input type="text" class="form-control" name="ord_addr_basic" id="sample2_address" placeholder="서울특별시 강남구" style="margin-bottom: 15px;">
			     <input type="text" class="form-control" name="ord_addr_detail" id="sample2_detailAddress" placeholder="상세주소를 입력해주세요." style="margin-bottom: 15px;">
			     <input type="hidden" id="sample2_extraAddress" placeholder="참고항목">	
			  </div>
		 </div>

		 <div class="row pt-3">
		 	<div class="col-md-2">
		 		<label>결제방식</label>
		 	</div>
		 	<div class="col-md-8">
		 	<!-- 폼태그를 사용할때는 name이라는 속성을 사용해 전송, 하지만 ajax는 id를 이용하여 전송 -->
		 		<select name="pay_method" id="pay_method" class="custom-select w-40" style="margin-bottom: 15px;">
		 			<option value="">결제방식을 선택해주세요.</option>
		 			<option value="무통장입금">무통장입금</option>
		 			<option value="카카오페이">카카오페이</option>
		 		</select>
		 		<select name="pay_bank" id="pay_bank" class="custom-select w-40" style="display:none; margin-bottom: 15px;">
		 			<option value="">입금 은행을 선택해주세요.</option>
		 			<option value="국민은행">국민은행</option>
		 			<option value="하나은행">하나은행</option>
		 		</select>
		 		<div style="display: flex; align-items: center;">
		                       <p id="pay_user_vw" style="display: none;">입금자명 : <input type="text" name="pay_user" id="pay_user" style="margin-left: 10px; width: 150px;"></p>
		        </div>
		 	</div>
		 </div>
		 		           
          <hr class="mb-4">
          <div class="custom-control custom-checkbox">
            <input type="checkbox" class="custom-control-input" name="mbsp_receive" id="mbsp_receive">
            <label class="custom-control-label" for="mbsp_receive">개인정보 수집 및 이용에 동의합니다.</label>
          </div>
          <div class="mb-4 text-center">
          	<input type="hidden" name="ord_price"  id="ord_price" value="${cart_tot_price }">
          	<button class="btn btn-primary" data-paytype="bank" name="btn_buy" id="btn_order" type="button">무통장입금</button>
          	<img src="/image/payment_icon_yellow_medium.png" data-paytype="kakaopay" name="btn_buy" id="kakao_pay">
          </div>
      </div>
      </div>
    </div>
 <div class="row">
  	<div class="col-md-12">

              <table class="table table-bordered">
                <tbody><tr>
                  <th style="width: 50%">상품</th>
                  <th style="width: 10%">수량</th>
                  <th style="width: 10%">포인트</th>
                  <th style="width: 10%">배송비</th>
                  <th style="width: 10%">주문금액</th>
                </tr>
                <c:forEach items="${cart_list }" var="cartListDTO">
                <tr>
                  <td>
                  	<img src="/cart/displayImage?folderName=${cartListDTO.pro_up_folder }&fileName=s_${cartListDTO.pro_img}" />
                    <c:out value="${cartListDTO.pro_name }" ></c:out>
                  </td>
                  <td>
                    ${cartListDTO.cart_amount }
                  </td>
                  <td>포인트 : 0</td>
                  <td>
                  	[기본배송조건]
                 </td>
                  <td><fmt:formatNumber type="currency" pattern="₩#,###" value="${cartListDTO.unitprice }"/></td>               
                </tr>
                </c:forEach>
                
              </tbody>
              <tfoot>
              	<tr>
              		<td colspan="6" class="text-right">총 결제금액 : <fmt:formatNumber type="currency" pattern="₩#,###" value="${cart_tot_price }"/></td>
              	</tr>
              </tfoot>
              </table>
  	</div>
 </div>
  
  <%@include file="/WEB-INF/views/include/footer.jsp" %>
</div>

<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
  <img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
  </div>
  
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script>
      // 우편번호 찾기 화면을 넣을 element
      var element_layer = document.getElementById('layer');
  
      function closeDaumPostcode() {
          // iframe을 넣은 element를 안보이게 한다.
          element_layer.style.display = 'none';
      }
  
      function sample2_execDaumPostcode() {
          new daum.Postcode({
              oncomplete: function(data) {
                  // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
  
                  // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                  // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                  var addr = ''; // 주소 변수
                  var extraAddr = ''; // 참고항목 변수
  
                  //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                  if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                      addr = data.roadAddress;
                  } else { // 사용자가 지번 주소를 선택했을 경우(J)
                      addr = data.jibunAddress;
                  }
  
                  // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                  if(data.userSelectedType === 'R'){
                      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                          extraAddr += data.bname;
                      }
                      // 건물명이 있고, 공동주택일 경우 추가한다.
                      if(data.buildingName !== '' && data.apartment === 'Y'){
                          extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                      }
                      // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                      if(extraAddr !== ''){
                          extraAddr = ' (' + extraAddr + ')';
                      }
                      // 조합된 참고항목을 해당 필드에 넣는다.
                      document.getElementById("sample2_extraAddress").value = extraAddr;
                  
                  } else {
                      document.getElementById("sample2_extraAddress").value = '';
                  }
  
                  // 우편번호와 주소 정보를 해당 필드에 넣는다.
                  document.getElementById('sample2_postcode').value = data.zonecode;
                  document.getElementById("sample2_address").value = addr;
                  // 커서를 상세주소 필드로 이동한다.
                  document.getElementById("sample2_detailAddress").focus();
  
                  // iframe을 넣은 element를 안보이게 한다.
                  // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                  element_layer.style.display = 'none';
              },
              width : '100%',
              height : '100%',
              maxSuggestItems : 5
          }).embed(element_layer);
  
          // iframe을 넣은 element를 보이게 한다.
          element_layer.style.display = 'block';
  
          // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
          initLayerPosition();
      }
  
      // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
      // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
      // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
      function initLayerPosition(){
          var width = 300; //우편번호서비스가 들어갈 element의 width
          var height = 400; //우편번호서비스가 들어갈 element의 height
          var borderWidth = 5; //샘플에서 사용하는 border의 두께
  
          // 위에서 선언한 값들을 실제 element에 넣는다.
          element_layer.style.width = width + 'px';
          element_layer.style.height = height + 'px';
          element_layer.style.border = borderWidth + 'px solid';
          // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
          element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
          element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
      }  
  </script>  

   <script>
    $(document).ready(function() {

      $("#kakao_pay").attr("disabled", true); // 비활성화
      $("#btn_order").attr("disabled", true); // 비활성화

      // 결제 및 주문하기
      // 무통장 또는 카카오페이 결제 선택
      $("[name='btn_buy']").on("click", function() {

        let pay_type = $(this).data("paytype");

        // 주문정보, 결제 데이터 수집 작업
        let order_info = {
          pay_type : pay_type,
          
          order_productName : $("#order_productName").val(),

          ord_name : $("#ord_name").val(), 
          ord_addr_post : $("input[name='ord_addr_post']").val(), 
          ord_addr_basic : $("input[name='ord_addr_basic']").val(), 
          ord_addr_detail : $("input[name='ord_addr_detail']").val(), 
          ord_tel : $("#ord_tel").val(), 
          ord_price : $("#ord_price").val(),

          pay_method : $("#pay_method").val(),
          pay_price : $("#ord_price").val(),
          pay_user : $("#pay_user").val(),
          pay_bank : $("#pay_bank").val(),
          pay_memo : '생략'
        };

        $.ajax({
          url: '/order/orderbuy',
          data: order_info,
          dataType: 'json',
          type: 'get',
          success: function(result) {

            alert(pay_type);

            if(pay_type == "kakaopay") {
              console.log("응답: " + result.next_redirect_pc_url);
            
              // 카카오페이 API서버에서 운영하는 큐알 코드 페이지 주소
              location.href = result.next_redirect_pc_url;

            } else if(pay_type == "bank") {
              location.href = "/order/orderComplete";
            }            
          }
        })
      });

      // 활성화 비활성화 작업
      $("#pay_method").on("click", function() {

        let pay_method = $(this).val();

        if(pay_method == "무통장입금") {
          $("#btn_order").attr("disabled", false); // 활성화
          $("#kakao_pay").attr("disabled", true); // 비활성화
          $("#pay_user_vw").css("display", "block"); // 화면에 보임
          $("#pay_bank").css("display", "block")
          
        }else if(pay_method == "카카오페이") {
          $("#kakao_pay").attr("disabled", false);
          $("#btn_order").attr("disabled", true);
          $("#pay_user_vw").css("display", "none"); // 화면에 안보임
          $("#pay_bank").css("display", "none")
        }
      });

    });

   </script>
  </body>
</html>