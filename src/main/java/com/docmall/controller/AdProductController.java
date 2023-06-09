package com.docmall.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.dto.PageDTO;
import com.docmall.service.AdProductService;
import com.docmall.util.FileUtils;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/product/*")
@Controller
public class AdProductController {
	
	@Setter(onMethod_ = {@Autowired})
	private AdProductService adProductService;
	
	//업로드폴더 주입
	@Resource(name = "uploadPath") // servlet-context.xml 참조.
	private String uploadPath;  // C:\\dev\\upload\\pds\\
	
	//CkEditor 업로드폴더
	@Resource(name = "uploadCkEditorPath") // 
	private String uploadCkEditorPath;  // C:\\dev\\upload\\ckeditor\\
	
	// 상품등록 폼 - 1차 카테고리
	@GetMapping("/pro_insert")
	public void pro_insert(Model model) {
		
		model.addAttribute("categoryList", adProductService.getCategoryList());
	}
	
	// 2차 카테고리
	@ResponseBody
	@GetMapping("/subCategory/{cat_code}")
	public ResponseEntity<List<CategoryVO>> subCategory(@PathVariable("cat_code") Integer cat_code) {
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryVO>>(adProductService.subCategoryList(cat_code), HttpStatus.OK);
		
		return entity;
		
	}
	
	//상품정보 저장
	@PostMapping("/pro_insert")
	public String pro_insert(ProductVO vo, RedirectAttributes rttr) {
	
		log.info("상품정보: " + vo);
		
		// 상품 업로드 작업
		String uploadDateFolder = FileUtils.getFolder();
		String saveImageName = FileUtils.uploadFile(uploadPath, uploadDateFolder, vo.getUploadFile());
		
		vo.setPro_img(saveImageName); // 실제 업로드된 파일명
		vo.setPro_up_folder(uploadDateFolder); // 날짜폴더명
		
		log.info("상품정보: " + vo);
		
		//상품테이블에 db작업
		// pro_insert
		adProductService.pro_insert(vo);
		
		return "redirect:/admin/product/pro_list";
	}
	
	//CKEditor에서 파일업로드 기능사용 : 파일이 업로드되고, 업로드한 파일정보를 CKEditor에 돌려준다.
	// HttpServletRequest req : request객체
	// HttpServletResponse res : response객체
	// <input type="file" name="upload" size="38">
	@PostMapping("/imageUpload") // 스크립트의 주소랑 동일
	public void imageUpload(HttpServletRequest req, HttpServletResponse res, MultipartFile upload) {
		
		OutputStream out = null; // 출력 스트림
		PrintWriter printWriter = null; // 웹 상에서 서버에서 클라이언트에게 내용을 보낼 때 쓰는 객체
		
		// 클라이언트에게 보내는 정보에 설명.
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html; charset=utf-8");
		
		try {
			// 1)업로드 작업
			String fileName = upload.getOriginalFilename(); // 클라이언트에서 보낸 파일이름.
			byte[] bytes = upload.getBytes(); // 클라이언트에서 보낸 업로드되는 파일을 바이트배열로 확보.
			
			String uploadPath = uploadCkEditorPath + fileName;
			
			log.info("파일업로드: " + uploadPath);
			
			out = new FileOutputStream(new File(uploadPath)); //0byte에 해당하는 파일 생성
			out.write(bytes); // 업로드된 파일
			out.flush();
			
			// 2)업로드된 파일정보를 CKEditor 에게 보내는 작업.
			printWriter = res.getWriter();
			
			// 1)톰캣 Context Path에서 Add External Web Module 작업을 해야 함.
			// Path : /upload, Document Base : C:\\dev\\upload\\ckeditor 설정
			// 2)Tomcat server.xml에서 <Context docBase="업로드경로" path="/upload" reloadable="true"/>
			
			String fileUrl = "/upload/" + fileName;
			// {"filename":"abc.gif", "uploaded":1, "url":"/upload/abc.gif"}
			printWriter.println("{\"filename\":\"" +  fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}");
			printWriter.flush();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			if(out != null) {
				try {
					out.close();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(printWriter != null) printWriter.close();
		}
	}
	
	// 상품 리스트
	// 메소드에 @ModelAttribute 쓰는 이유는  jsp에서 참조하기 위함
	// 카테고리 검색을 할 수도 안 할 수도 있기 때문에 Integer로 준다. int로 주면 null값을 못줌.
	@GetMapping("/pro_list")
	public void pro_list(@ModelAttribute("cri") Criteria cri, Integer cat_code, Model model) {
	
		log.info("cri: " + cri);
		
		// 검색기능 사용후, 카테고리 검색시 검색파라미터 초기화작업.
		if(cat_code != null) {
			cri = new Criteria();
		}
		
		//1차카테고리 목록
		model.addAttribute("categoryList", adProductService.getCategoryList());
		
		//2차카테고리 검색목록
		List<ProductVO> pro_list = adProductService.getListWithPaging(cri, cat_code);
		
		// 상품이 없는데 접근 할려는 코드가 있으면 index, size 오류 메시지가 뜸
		//log.info("폴더경로: " + pro_list.get(0).getPro_up_folder());
		
		// \\를 /로 바꿔주는 작업
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		// jsp에서 "pro_list" 이름으로 참조
		model.addAttribute("pro_list", pro_list);
		
		int totalCount = adProductService.getTotalCount(cri, cat_code);
		model.addAttribute("pageMaker", new PageDTO(totalCount, cri));
	}
	
	// 이미지 매핑주소. // <img src="test.gif">,  <img src="매핑주소">,  
	// pro_list.jsp 파일에서 <img src="/admin/product/displayImage?folderName=값&fileName=값">
	@ResponseBody
	@GetMapping("/displayImage")
	public ResponseEntity<byte[]> displayFile(String folderName, String fileName) throws IOException {
		
		//                  C:\\dev\\upload\\pds\\
		return FileUtils.getFile(uploadPath + folderName, fileName);
	}
	
	// 수정 폼
	@GetMapping("/pro_modify")
	public void modify(Integer	pro_num, int cat_code, @ModelAttribute("cri") Criteria cri, Model model) {
		
		// 상품수정폼
		ProductVO productVO = adProductService.modify(pro_num);
		productVO.setPro_up_folder(productVO.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("productVO", productVO); //상품정보
		
		// 1차카테고리 정보
		CategoryVO categoryVO = adProductService.get(cat_code);
		model.addAttribute("categoryVO", categoryVO); 
		
		// 1차카테고리 목록
		model.addAttribute("categoryList", adProductService.getCategoryList());
		// 1차카테고리를 참조하는 2차카테고리 목록
		model.addAttribute("subCategoryList", adProductService.subCategoryList(categoryVO.getCat_code()));

	}
	
	// 상품 이미지 수정
	@PostMapping("/pro_modify")
	public String modify(ProductVO vo, RedirectAttributes rttr) {
		
		// 상품 이미지 변경 구분. !가 있으면 이미지를 업로드 한 경우
		//상품이미지를 업로드 한 경우(변경)
		if(!vo.getUploadFile().isEmpty()) { // isEmpty() : 업로드한 파일이 없으면 true
			// 1)기존 상품이미지삭제
			FileUtils.deleteFile(uploadPath, vo.getPro_up_folder(), vo.getPro_img());
			
			// 2)변경 상품이미지 업로드작업
			String uploadDateFolder = FileUtils.getFolder();
			String saveImageName = FileUtils.uploadFile(uploadPath, uploadDateFolder, vo.getUploadFile());
			
			vo.setPro_img(saveImageName); // 실제 업로드된 파일명
			vo.setPro_up_folder(uploadDateFolder); // 날짜폴더명
		}
		
		//공통 : 상품수정작업. modify()
		adProductService.update(vo);
		
		rttr.addFlashAttribute("msg", "modify");
		
		
		return "redirect:/admin/product/pro_list";
	}
	
	// 상품 삭제 작업
	@PostMapping("/pro_delete")
	public String pro_delete(Integer pro_num, Criteria cri, RedirectAttributes rttr) {
		
		log.info("상품코드: " + pro_num);
		
		adProductService.delete(pro_num);
		
		rttr.addFlashAttribute("msg", "delete");
				
		return "redirect:/admin/product/pro_list";
	}
	
	// 선택상품수정
	@ResponseBody
	@PostMapping("/pro_checked_modify")
	// ajax로 요청하는 배열성격 이므로 []쓰기
	public ResponseEntity<String> 
			pro_checked_modify(
					@RequestParam("pro_num_arr[]") List<Integer> pro_num_arr,
					@RequestParam("pro_price_arr[]") List<Integer> pro_price_arr,
					@RequestParam("pro_buy_arr[]") List<String> pro_buy_arr) {
		
		ResponseEntity<String> entity = null;
		
		log.info("수정상품코드: " + pro_num_arr);
		log.info("수정상품가격: " + pro_price_arr);
		log.info("수정상품판매여부: " + pro_buy_arr);
		
		// 마이바티스 foreach 구문 : https://devmoony.tistory.com/134
		
		// 수정작업 방법1.
		List<ProductVO> pro_list = new ArrayList<ProductVO>();
		
		for(int i=0; i<pro_num_arr.size(); i++) {
			
			ProductVO productVO = new ProductVO();
			productVO.setPro_num(pro_num_arr.get(i));
			productVO.setPro_price(pro_price_arr.get(i));
			productVO.setPro_buy(pro_buy_arr.get(i));
			
			pro_list.add(productVO);
		}
		
		adProductService.pro_checked_modify(pro_list);

		// 수정작업 방법2.
		/* 
		for(int i=0; i<pro_num_arr.size(); i++) {
			adProductService.pro_checked_modify2(pro_num_arr.get(i), pro_price_arr.get(i), pro_buy_arr.get(i));
		}
		*/
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 선택상품삭제
	@ResponseBody
	@PostMapping("/pro_checked_delete")
	// ajax로 요청하는 배열성격 이므로 []쓰기
	public ResponseEntity<String> 
			pro_checked_modify(
					@RequestParam("pro_num_arr[]") List<Integer> pro_num_arr) {
		
		ResponseEntity<String> entity = null;
	
		// 1)삭제작업. Connection객체 하나로 sql구문 실행 : 성능 향상
		adProductService.pro_checked_delete(pro_num_arr);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		// 2) Connection객체를 for문 반복횟수 만큼 생성, sql구문 개별실행 : 성능떨어짐
		for(int i=0; i<pro_num_arr.size(); i++) {
			
		}
		
		return entity;
	}
	
	
	
}
