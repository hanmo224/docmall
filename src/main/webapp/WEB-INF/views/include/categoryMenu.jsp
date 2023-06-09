<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="categoryMenu">
	<ul class="nav justify-content-center" id="mainCategory">
	  <!--  
	  <li class="nav-item">
	    <a class="nav-link active" href="#">Active</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">Link</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">Link</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link disabled">Disabled</a>
	  </li>
	  -->

		 <c:forEach items="${cateList }" var="categoryVO">
		 	<li class="nav-item">
		   		<a class="nav-link" href="${categoryVO.cat_code }"><c:out value="${categoryVO.cat_name }" /></a>
		  	</li>
		 </c:forEach>
	</ul>
</div>

<script>

  // 1차 카테고리 클릭
  $("ul.nav li.nav-item a").on("mouseover", function(e) {
    e.preventDefault(); // a태그가 가지고 있는 링크기능을 제거

    //console.log("1차 카테고리 선택");

    let selectedCategory = $(this);
    let cat_code = $(this).attr("href"); // 카테고리 코드 값을 가져옴
    //let cat_name =  "<b>" + $(this).html + "</b>";
    let url = "/product/subCategory/" + cat_code; // 서브 카테고리를 가져옴 productController 씀

    $.getJSON(url, function(subCategory) {

      //console.log(subCategory);

      let subCategoryStr = '<ul class="nav justify-content-center" id="subCategory">';

      for(let i=0; i<subCategory.length; i++) {
        subCategoryStr += '<li class="nav-item">';
        subCategoryStr += '<a class="nav-link" href="' + subCategory[i].cat_code + '">' + subCategory[i].cat_name + '</a>';
        subCategoryStr += '</li>';
      }

      subCategoryStr += '</ul>';

      //console.log(subCategoryStr);

      selectedCategory.parent().parent().next().remove();
      // a태그          li태그   ul태그
      selectedCategory.parent().parent().after(subCategoryStr);
    });

    // 2차 카테고리 선택
    // on("click", "2차 카테고리 선택자", function()
    $("div#categoryMenu").on("click", "ul#subCategory li.nav-item a", function(e) {
      e.preventDefault();

      //console.log("2차 카테고리 선택");

      let url = "/product/pro_list/" + $(this).attr("href") + "/" + $(this).html();
      location.href = url;
    });

  });

</script>




















