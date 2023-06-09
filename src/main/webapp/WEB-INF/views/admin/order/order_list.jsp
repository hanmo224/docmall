<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Starter</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <%@include file="/WEB-INF/views/admin/include/plugin1.jsp"%>

</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <%@include file="/WEB-INF/views/admin/include/header.jsp" %>
  <!-- Left side column. contains the logo and sidebar -->
  <%@include file="/WEB-INF/views/admin/include/nav.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Page Header
        <small>Optional description</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

      <!--------------------------
        | Your Page Content Here |
        -------------------------->
       
        <div class="row">
        	<div class="col-md-12">
        	
        		<div class="box">
            <div class="box-header with-border">
                <div class="form-group row">
                  <div class="col-md-12">
                  <form id="searchForm" action="" method="get">
                    주문일 : <input type="date" name="sDate" value="${sDate}"> ~ <input type="date" name="eDate" value="${eDate}">
                    <select name="type">
                      <option value="" <c:out value="${pageMaker.cri.type == null? 'selected':'' }" />>-----</option>
                      <option value="C" <c:out value="${pageMaker.cri.type eq 'C'? 'selected':'' }" />>주문번호</option>
                      <option value="I" <c:out value="${pageMaker.cri.type eq 'I'? 'selected':'' }" />>주문자ID</option>    
                      <option value="R" <c:out value="${pageMaker.cri.type eq 'R'? 'selected':'' }" />>수령인</option>
                    </select>
                    <input type="text" name="keyword" placeholder="검색어를 입력하세요." value='<c:out value="${pageMaker.cri.keyword }" />'>
                    <input type="hidden" name="pageNum" value="1">
                    <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                    <button type="button" id="btn_order_search" class="btn btn-success">Search</button>
                   </form>
                  </div>
                </div>
             
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table class="table table-bordered">
                <tbody><tr>
                  <th style="width: 2%"><input type="checkbox" id="checkAll"></th>
                  <th style="width: 15%">주문일시</th>
                  <th style="width: 18%">주문번호</th>
                  <th style="width: 15%">주문자/수령인</th>
                  <th style="width: 25%">주문금액/배송비</th>
                  <th style="width: 15%">결제방식</th>
                  <th colspan="2" style="width: 10%">주문관리</th>
                </tr>
                <c:forEach items="${orderList }" var="orderVO">
                <tr>
                  <td><input type="checkbox" name="check" value="${orderVO.ord_code }"></td>
                  <td>
                  	<fmt:formatDate value="${orderVO.ord_regdate }" pattern="yyyy-MM-dd HH:mm"/>
                  </td>
                  <td>
                    ${orderVO.ord_code }
                  </td>
                  <td>
                    ${orderVO.mbsp_id } / ${orderVO.ord_name }
                  </td>
                  <td>
                  	${orderVO.ord_price }
                  </td>
                  <td>${orderVO.pay_method }</td>
                  <td>
                    <button type="button" name="btn_orderDetail" data-ord_code="${orderVO.ord_code }" class="btn btn-link">상세</button>
                    <button type="button" name="btn_orderDelete" data-ord_code="${orderVO.ord_code }" class="btn btn-link">삭제</button>
                  </td>     
                </tr>
                </c:forEach>
                
              </tbody></table>
            </div>
            <!-- /.box-body -->
            <!-- [이전] 1 2 3 4 5 [다음] 출력작업 -->
            <div class="box-footer clearfix">
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
              <!-- 추가된 검색 부분이 있으면 추가 -->
              <form id="actionForm" action="" method="get">
                <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
                <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                <input type="hidden" name="type" value="${pageMaker.cri.type}">
                <input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
              </form>
            </div>
          </div>
        	
        	</div>
        
        </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

  <!-- Main Footer -->
  <%@include file="/WEB-INF/views/admin/include/footer.jsp" %>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane active" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">Recent Activity</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                <p>Will be 23 on April 24th</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <h3 class="control-sidebar-heading">Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <h4 class="control-sidebar-subheading">
                Custom Template Design
                <span class="pull-right-container">
                    <span class="label label-danger pull-right">70%</span>
                  </span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->
      <!-- Stats tab content -->
      <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
      <!-- /.tab-pane -->
      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">General Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<%@include file="/WEB-INF/views/admin/include/plugin2.jsp"%>
<!-- 1)Include Handlebars from a CDN -->
<script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
<!-- 2)handlebars template. 댓글UI -->
<script id="orderDetailView" type="text/x-handlebars-template">
 <table class="table table-bordered">
 <tbody>
  <tr>
   <th style="width: 2%"><input type="checkbox" id="checkAll"></th>
   <th style="width: 8%">주문상품</th>
   <th style="width: 25%">판매가</th>
   <th style="width: 10%">수량</th>
   <th style="width: 20%">주문금액</th>
   <th style="width: 15%">상태</th>
   <th style="width: 10%">배송료</th>
   <th style="width: 10%">비고</th>
 </tr>
 {{#each .}}
   <tr> 
   <td style="width: 2%">
	<input type="checkbox" name="checkAll">
    <input type="hidden" name="ord_code" value="{{ord_code}}">
	</td>
   <td style="width: 8%"><img src="{{imageView pro_up_folder pro_img}}"> {{pro_name}}</td>
   <td style="width: 25%">{{dt_price}}</td>
   <td style="width: 10%">{{dt_amount}}</td>
   <td style="width: 20%">주문금액</td>
   <td style="width: 15%">상태</td>
   <td style="width: 10%">배송료</td>
   <!-- 태그에다가 프로그래밍 목적으로 값을 숨길때 사용 data- -->
   <td style="width: 10%"><button type="button" name="btn_order_product_del"  data-pro_num="{{pro_num}}" class="btn btn-link">Delete</button></td>
   </tr>	
 {{/each}}
 </tbody>
 </table>
</script>
<script>
  Handlebars.registerHelper("imageView", function(folder, file) {
    
    let url = "/admin/order/displayImage?folderName=" + folder.replace(/\\/g, "/") + "&fileName=" + "s_" + file;

    return  url;
  
  });


</script>

<script>
  $(document).ready(function() {
   
    // 상세 버튼을 클릭 
    $("button[name='btn_orderDetail']").on("click", function() {

      // ajax요청에서 주문상세정보 json형태로 받는 작업
      
      let ord_code = $(this).data("ord_code");

      let url = "/admin/order/order_detail?ord_code=" + ord_code;

      $.getJSON(url, function(data) {
      
        console.log(data);  
    	  
        let templateObj = Handlebars.compile($("#orderDetailView").html());
        let orderDetailVwHtml = templateObj(data); // 테이블과 댓글데이타가 바인딩된 결과
        
        // 추가된 기존 상세정보를 제거.
        // 동적으로 가져오는 데이터는 계속 누적이 되므로 비워준후 추가 시킨다.
        $("#modalDetailView").empty();

        $("#modalDetailView").append(orderDetailVwHtml);

        $('#orderDetailModal').modal('show');
      });
     });

     // 주문상세내역에서 삭제 클릭
     // 동적으로 생성된 것은 다이렉트로 이벤트 설정이 불가능 정적으로 생성된 것만 가능
     $("div#modalDetailView").on("click", "button[name='btn_order_product_del']", function() {
        
        if(!confirm("주문상세 상품을 삭제하시겠읍니까?")) return;

        // 선택했던 행 미리 확보
        let cur_row = $(this).parent().parent();

        let pro_num = $(this).data("pro_num");
        let ord_code = $(this).parent().parent().find("input[name='ord_code']").val();

        console.log("주문번호: " + ord_code);
        console.log("상품코드: " + pro_num);

        $.ajax({
          url: '/admin/order/order_detail_product_delete',
          type: 'post',
          data: {ord_code : ord_code, pro_num : pro_num},
          dataType: 'text',
          success: function(result) {
            if(result == 'success') {
              alert("주문상품이 삭제되었습니다.");

              // 삭제
              cur_row.remove();
            }
          }
        });
     });

     // 검색 클릭
     let searchForm = $("#searchForm");
     $("#btn_order_search").on("click", function() {
      searchForm.attr("action", "/admin/order/order_list");
      searchForm.find("input[name='pageNum']").val(1);
      searchForm.submit();
     });

     // 페이징
     let actionForm = $("#actionForm");

     $("ul.pagination a").on("click", function(e) {
      e.preventDefault(); // a태그의 하이퍼링크 기능이 취소.

      // 현재 페이지번호 변경작업
      // form태그의 정보  
      // <input type="hidden" name="pageNum" value="1">
      // <input type="hidden" name="amount" value="10">
      actionForm.attr("action", "/admin/order/order_list");
      actionForm.find("input[name='pageNum']").val($(this).attr("href"));

      actionForm.find("input[name='sDate']").remove();
      actionForm.find("input[name='eDate']").remove();

      actionForm.append("<input type='hidden' name='sDate' value='${sDate}'>");
      actionForm.append("<input type='hidden' name='eDate' value='${eDate}'>");

      actionForm.submit();
      });
    

  }); // jquery ready event end
</script>
<!-- Button trigger modal 
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  Launch demo modal
</button>
-->
<!-- Modal -->
<div class="modal fade" id="orderDetailModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">주문상세내역</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="modalDetailView">
        
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-primary">선택삭제</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>        
      </div>
    </div>
  </div>
</div>

</body>
</html>