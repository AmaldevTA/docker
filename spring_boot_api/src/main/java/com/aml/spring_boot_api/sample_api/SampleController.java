package com.aml.spring_boot_api.sample_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RequestMapping("/sample/api_test")
@RestController
public class SampleController {
	private static final boolean SUCCESS_STATUS = true;

	@Autowired
    private HttpServletRequest request;

	@RequestMapping(value = "/{id}/profile", method = RequestMethod.GET)
	public BaseResponse getProfile(@PathVariable("id") String id, @RequestHeader("Country-Code") String code) {
		
		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(id + "-" + code);

		return response;
	}

	@RequestMapping(value = "/fullProfile", params = { "id", "token" }, method = RequestMethod.GET)
	public BaseResponse getFullProfile(@RequestParam(value = "id") String id,
			@RequestParam(value = "token", defaultValue = "dummy_token") String token,
			HttpServletRequest request) {
		
		String code = request.getHeader("Country-Code");
		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(id + "-" + code);

		return response;
		
	}
	
	//raw
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public @ResponseBody
    BaseResponse updateProfile(@RequestBody ProfileUpdateRequest request) {
		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(request.getName());

		return response;
	}
	
	//form url-encoded
	@RequestMapping(value = "/updateProfileForm", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public BaseResponse updateProfileForm(@ModelAttribute ProfileUpdateRequest request) {
		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(request.getName());
		
		return response;
	}
	
	//raw
	@RequestMapping(value = "/updateBasicProfile", method = RequestMethod.POST)
	public BaseResponse updateBasicProfile(@RequestBody HashMap<String, String> request) {
		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(request.get("name"));
		
		return response;
	}
	
	//form url-encoded
	@RequestMapping(value = "/updateBasicProfileForm", method = RequestMethod.POST)
	public BaseResponse updateBasicProfileForm(@RequestBody MultiValueMap<String, String> request) {
		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(request.getFirst("name"));
		
		return response;
	}

	//multi-part
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public BaseResponse uploadFile(@RequestParam("name") String fileName, @RequestParam("file") MultipartFile file) {

		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(fileName + file.getSize());
		return response;
	}



	@RequestMapping(value = "/updateProfilePicture", method = RequestMethod.POST)
	public BaseResponse updateFullProfile(@RequestParam("id") String id, @RequestPart("file") MultipartFile file) {
		BaseResponse response = new BaseResponse();
		response.setStatus(SUCCESS_STATUS);
		response.setResult(id + "--" + file.getSize());
		System.out.println("--" + file.getSize());
		
		String uploadsDir = "/uploads/";
        String realPathUploads =  request.getServletContext().getRealPath(uploadsDir);
        
		System.out.println("--" + realPathUploads);

		 if(! new File(realPathUploads).exists()){
             new File(realPathUploads).mkdir();
             System.out.println("not exists");
         }

		 System.out.println("realPathtoUploads = {}"+ realPathUploads);


         String orgName = file.getOriginalFilename();
         String filePath = realPathUploads + orgName;
         File dest = new File(filePath);
         try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return response;
	}


		
}
