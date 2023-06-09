<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
     		<div class="box box-primary">
     			<form id="productForm" action="" method="post" enctype="multipart/form-data">
     			<div class="box-header">
     				MODIFY PRODUCT
     			</div>
     			<div class="box-body">	
					  <div class="form-group row">
					    <label for="pdt_name" class="col-sm-2 col-form-label">카테고리</label>
					    <div class="col-sm-3">
                <input type="hidden" name="pageNum" value="${cri.pageNum}">
                <input type="hidden" name="amount" value="${cri.amount}">
                <input type="hidden" name="type" value="${cri.type}">
                <input type="hidden" name="keyword" value="${cri.keyword}">
					    	<select name="cat_prtcode" id="firstCategory" class="form-control">
					    		<option value="">1차카테고리 선택</option>
					    		<c:forEach items="${categoryList }" var="category">
					    			<option value="${category.cat_code }" ${category.cat_code == categoryVO.cat_code ? 'selected': ''}>${category.cat_name }</option>
					    		</c:forEach>
					     	</select>
					    </div>
						<div class="col-sm-3">					    	
					    	<select name="cat_code" id="secondCategory" class="form-control">
					    		<option value="">2차카테고리 선택</option>
					    		<c:forEach items="${subCategoryList }" var="category">
					    			<option value="${category.cat_code }" ${category.cat_code ==  productVO.cat_code ? 'selected' : ''}>${category.cat_name }</option>
					    		</c:forEach>
					    	</select>
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pro_name" class="col-sm-2 col-form-label">상품명</label>
					    <div class="col-sm-4">
					      <input type="hidden" class="form-control" id="pro_num" name="pro_num" value="${productVO.pro_num}">
					      <input type="text" class="form-control" id="pro_name" name="pro_name" value="${productVO.pro_name}">
					    </div>
					    <label for="pro_price" class="col-sm-2 col-form-label">상품가격</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pro_price" name="pro_price" value="${productVO.pro_price}">
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pro_discount" class="col-sm-2 col-form-label">할인율</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pro_discount" name="pro_discount" value="${productVO.pro_discount}">
					    </div>
					    <label for="pro_publisher" class="col-sm-2 col-form-label">제조사</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pro_publisher" name="pro_publisher" value="${productVO.pro_publisher}">
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pdt_img" class="col-sm-2 col-form-label">상품이미지</label>
					    <div class="col-sm-2"> <!-- name="pdt_img" 존재안함.  스프링에서는 Null 이 됨.-->
					      <input type="hidden" name=pro_up_folder value="${productVO.pro_up_folder}">
					      <input type="hidden" name="pro_img" value="${productVO.pro_img}">
					      <input type="file" class="form-control" id="uploadFile" name="uploadFile">
					    </div>
					    <label for="change_img" class="col-sm-2 col-form-label">현재 / 변경 이미지</label>
					    <div class="col-sm-3">
					      <img id="curr_img" src="/admin/product/displayImage?folderName=${productVO.pro_up_folder }&fileName=s_${productVO.pro_img}" style="width: 200px;height: 200px;">
					    </div>
              <div class="col-sm-3">
					      <img id="change_img" style="width: 200px;height: 200px;">
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pro_content" class="col-sm-2 col-form-label">상품설명</label>
					    <div class="col-sm-10">
					      <textarea class="form-control" id="pro_content" name="pro_content" rows="3">${productVO.pro_content}</textarea>
					    </div>
					  </div>
					  <div class="form-group row">
					    <label for="pro_amount" class="col-sm-2 col-form-label">수량</label>
					    <div class="col-sm-4">
					      <input type="text" class="form-control" id="pro_amount" name="pro_amount" value="${productVO.pro_amount}">
					    </div>
					    <label for="pro_buy" class="col-sm-2 col-form-label">판매여부</label>
					    <div class="col-sm-4">
					      <select id="pro_buy" name="pro_buy">
					      	<option value="Y" ${productVO.pro_buy == 'Y' ? 'selected':''}>판매가능</option>
					      	<option value="N" ${productVO.pro_buy == 'N' ? 'selected':''}>판매불가능</option>
					      </select>
					    </div>
					  </div>	  
				 
     			</div>
     			<div class="box-footer">
     				<div class="form-group">
     					<ul class="uploadedList"></ul>
     				</div>    				
     				<div class="form-group row">
						  <div class="col-sm-12 text-center">
						  	<button type="submit" class="btn btn-dark" id="btnProduct">상품수정</button>
						  </div>			
					</div>
     			</div>
     			</form>
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

<!-- CkEditor를 쓸려면 필요함 -->
<script src="/bower_components/ckeditor/ckeditor.js"></script>

<script>
  $(document).ready(function() {
    // ckeditor 버전: 4.12.1 (Standard)환경설정. javascript object구문사용
    // ckeditor 공식 사이트에서 아래 코드 참조
    let ckeditor_config = {
      resize_enabled : false,
      enterMode : CKEDITOR.ENTER_BR,
      shiftEnterMode : CKEDITOR.ENTER_P,
      toolbarCanCollapse : true,
      removePlugins : "elementspath",
      filebrowserUploadUrl : '/admin/product/imageUpload' // 업로드 탭 표시
    }

    // <textarea class="form-control" id="pro_content" name="pro_content" rows="3"></textarea>
    CKEDITOR.replace("pro_content", ckeditor_config);

    console.log("버전: " + CKEDITOR.version);

    // 1차카테고리 선택
    $("#firstCategory").change(function() { // change : 선택 했을 때 현재 적용중인건 선택이 안됨

      let cat_code = $(this).val();

      //console.log("1차 카테고리코드: " + cat_code);

      let url = "/admin/product/subCategory/" + cat_code + ".json";

      $.getJSON(url, function(subCategoryData){

        //console.log(subCategoryData);

        let optionStr = "";
        let secondCategory = $("#secondCategory");

        secondCategory.find("option").remove(); // 카테고리 변경에 따른 이전 상태를 제거
        secondCategory.append("<option value=''>2차 카테고리 선택</option");

        for(let i=0; i<subCategoryData.length; i++) {
          optionStr += "<option value='" + subCategoryData[i].cat_code + "'>" + subCategoryData[i].cat_name + "</option>";
        };

        //console.log(optionStr);
        
        // 1차 선택후 2차 보여주기
        secondCategory.append(optionStr);

      });
    });

    //파일첨부 이미지 미리보기.  <input type="file" class="form-control" id="uploadFile" name="uploadFile">
    $("#uploadFile").on("change", function(e) {
      let file = e.target.files[0];  // 선택한 파일들중 첫번째 파일

      let reader = new FileReader(); // 첨부된 파일을 읽기위한 파일입력객체.

      // reader객체가 파일을 로딩했을 때.
      reader.onload = function(e) {
        $("#change_img").attr("src", e.target.result);
      }

      reader.readAsDataURL(file); // reader객체에 파일객체를 할당
    });

  });
</script>
</body>
</html>