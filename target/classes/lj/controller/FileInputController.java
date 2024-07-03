package lj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lj.global.AppConst;
import lj.global.AppVar;
import lj.util.FileUtils;
import lj.util.LogUtils;
import lj.util.StringUtils;

/**
 * 文件控制器(配合fileInput-app.js)
 * @author samlv
 *
 */
@Controller
public class FileInputController {
	
//	/**
//	 * 文件上传控制器
//	 * @param request
//	 * @param response
//	 * @param uploadFile
//	 * @return
//	 */
//	@RequestMapping("/upload")
//	public @ResponseBody String uploadFiles(HttpServletRequest request, HttpServletResponse response,
//											  @RequestParam(value="fileInputControl",required = false) MultipartFile uploadFile)
//	{
//		if(uploadFile==null)
//			return new String();
//		List<String> list=new ArrayList<String>();
//		int index=0;
//			String oldFileName=uploadFile.getOriginalFilename();
//			// 1-生成上传文件名称
//			System.out.println("文件上传 原始名称:" + oldFileName);
//			String fileExtension=FileUtils.getExtensionName(oldFileName);
//			// 2-上传文件路径
//			String uploadFileName=StringUtils.timeToString(new Date(),StringUtils.DATETIME_FORMAT_FILENAME)+"_"+(index++)+"."+fileExtension;
//			String uploadPath=AppVar.staticConfig.getUploadDir()+File.separator+uploadFileName;
//			System.out.println("FileInputController.upload file path:" + uploadPath);
//			//3-上传文件
//			try {
//				uploadFile.transferTo(new File(uploadPath));
//				list.add(uploadFileName);
//			} catch (Exception e) {
//				e.printStackTrace();
//				LogUtils.logError("FileInputController.uploadFiles", e);
//				list.add("");
//			}
//		return uploadPath;
//	}

    /**
     * 文件上传
     * @param request
     * @param response
     * @param uploadFiles
     * @return
     */
	@RequestMapping("/upload")
	public @ResponseBody String[] uploadFiles(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="fileInputControl",required = false) MultipartFile[] uploadFiles)
	{
		if(uploadFiles==null)
			return new String[0];
		List<String> list=new ArrayList<String>();
		int index=0;
		for(MultipartFile uploadFile:uploadFiles)
		{
			String oldFileName=uploadFile.getOriginalFilename();
			// 1-生成上传文件名称
			System.out.println("文件上传 原始名称:" + oldFileName);
			String fileExtension=FileUtils.getExtensionName(oldFileName);
			// 2-上传文件路径
			String uploadFileName=StringUtils.timeToString(new Date(),StringUtils.DATETIME_FORMAT_FILENAME)+"_"+(index++)+"."+fileExtension;
			String uploadPath=AppVar.staticConfig.getUploadDir()+File.separator+uploadFileName;
			System.out.println("FileInputController.upload file path:" + uploadPath);
			//3-上传文件
			try {
				uploadFile.transferTo(new File(uploadPath));
				list.add(uploadFileName);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtils.logError("FileInputController.uploadFiles", e);
				list.add("");
			}
		}
		return list.toArray(new String[0]);
	}
}
