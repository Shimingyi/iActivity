package com.me.Activity.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class ExtMultipartResolver extends CommonsMultipartResolver {

	private HttpServletRequest request;
	private static final Logger log = Logger
			.getLogger(ExtMultipartResolver.class);

	/*
	 * @Override protected FileUpload newFileUpload(FileItemFactory
	 * fileItemFactory) { // TODO Auto-generated method stub ServletFileUpload
	 * upload = new ServletFileUpload(fileItemFactory); log.info("intercept");
	 * upload.setSizeMax(-1); if(request!=null) { ///System.out.println("注入监听");
	 * log.info("intercept success"); FileUploadListener uploadProgressListener
	 * = new FileUploadListener();
	 * upload.setProgressListener(uploadProgressListener); HttpSession session =
	 * request.getSession(); session.setAttribute("uploadProgressListener",
	 * uploadProgressListener); } return upload; //return
	 * super.newFileUpload(fileItemFactory); }
	 */
	@Override
	public MultipartHttpServletRequest resolveMultipart(
			HttpServletRequest _request) throws MultipartException {
		// TODO Auto-generated method stub
		request = _request;
		return super.resolveMultipart(request);
	}

	public MultipartParsingResult parseRequest(HttpServletRequest request)
			throws MultipartException {
		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		// progressListener.setSession(request.getSession());
		log.info("intercept");
		FileUploadListener uploadProgressListener = new FileUploadListener();
		fileUpload.setProgressListener(uploadProgressListener);
		HttpSession session = request.getSession();
		session.setAttribute("uploadProgressListener", uploadProgressListener);
		log.info("intercepted");
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload)
					.parseRequest(request);
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(),
					ex);
		} catch (FileUploadException ex) {
			throw new MultipartException(
					"Could not parse multipart servlet request", ex);
		}
	}

}
