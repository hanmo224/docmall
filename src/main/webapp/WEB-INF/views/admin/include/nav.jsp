<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar user panel (optional) -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>Alexander Pierce</p>
          <!-- Status -->
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>

      <!-- search form (Optional) -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
          <span class="input-group-btn">
              <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
              </button>
            </span>
        </div>
      </form>
      <!-- /.search form -->

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu tree" data-widget="tree">
        <li class="header">HEADER</li>
        <!-- Optionally, you can add icons to the links -->
        <li class="active"><a href="#"><i class="fa fa-link"></i> <span>Link</span></a></li>
        <li><a href="#"><i class="fa fa-link"></i> <span>Another Link</span></a></li>
        
        <!-- 각 기능별 메뉴를 만들고 싶을때 복사 -->
        <!-- 상품관리 시작 -->
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>상품관리</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="/admin/product/pro_insert">상품등록</a></li>
            <li><a href="/admin/product/pro_list">상품목록</a></li>
          </ul>
        </li>
         <!-- 상품관리 끝 -->
         
          <!-- 주문관리 시작 -->
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>주문관리</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">        
            <li><a href="/admin/order/order_list">주문목록</a></li>
            <li><a href="#">장바구니관리</a></li>
          </ul>
        </li>
         <!-- 주문관리 끝 -->
         
          <!-- 회원관리 시작 -->
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>회원관리</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#">회원목록</a></li>
            <li><a href="#">탈퇴회원목록</a></li>
          </ul>
        </li>
         <!-- 회원관리 끝  -->
         
         <!-- 커뮤니티 - 게시판및상품후기관리 시작 -->
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>커뮤니티관리</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#">게시판</a></li>
            <li><a href="#">상품후기</a></li>
          </ul>
        </li>
         <!-- 게시판및상품후기관리 끝  -->
         
         <!-- 통계관리 시작 -->
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>통계관리</span>
            <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="/admin/chart/overall">매출통계</a></li>
            <li><a href="#">매출통계</a></li>
          </ul>
        </li>
         <!-- 통계관리 끝  -->
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>