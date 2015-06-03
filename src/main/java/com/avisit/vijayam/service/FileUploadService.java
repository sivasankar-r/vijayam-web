package com.avisit.vijayam.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisit.vijayam.common.Constant;
import com.avisit.vijayam.model.Option;
import com.avisit.vijayam.model.Question;
import com.avisit.vijayam.model.Topic;

@Service
public class FileUploadService implements Serializable{

	private static final long serialVersionUID = -3601759030045570596L;
	public static final Logger logger = LoggerFactory.getLogger(FileUploadService.class);
	public static List<String> questionSheetHeaderList = new ArrayList<String>();
	public static List<String> examSheetHeaderList = new ArrayList<String>();
	
	static {
		questionSheetHeaderList.add(Constant.QUESTION_CONTENT);
		questionSheetHeaderList.add(Constant.QUESTION_TYPE);
		questionSheetHeaderList.add(Constant.QUESTION_POINTS);
		questionSheetHeaderList.add(Constant.HERE_AFTER_OPTIONS);
		examSheetHeaderList.add(Constant.EXAM_TITLE);
		examSheetHeaderList.add(Constant.EXAM_DESC);
		examSheetHeaderList.add(Constant.EXAM_PASS_CODE);
		examSheetHeaderList.add(Constant.EXAM_DURATION);
	}
	
	@Autowired
	private QuestionService questionService;
	
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void uploadFile(InputStream inputStream, Topic topic) throws Exception {
		try {
			HSSFWorkbook workBook = new HSSFWorkbook(inputStream);
			HSSFSheet questionSheet = workBook.getSheetAt(0);
			boolean validQuestionTemplate = validateHorizontalHeaderTemplate(workBook, questionSheet, FileUploadService.questionSheetHeaderList);
			
			if (validQuestionTemplate) {
				HSSFRow rowData;
				int lastRowNum = questionSheet.getLastRowNum();
				for (int row = 1; row <= lastRowNum; row++) {
					rowData = questionSheet.getRow(row);
					Question question = prepareQuestionFromRow(rowData, workBook);
					question.setTopicId(topic.getId());
					questionService.persistQuestion(question);
				}
			} else {
				throw new Exception("Invalid Questions Sheet. Please download the template");
			}
		} catch (Exception e) {
			throw new Exception("Exception occurred while parsing the uploaded file. Please download the template", e);
		}
	}

	private Question prepareQuestionFromRow(HSSFRow row, HSSFWorkbook workBook) {
		Question question = null;
		if(row!=null && workBook!=null){
			int lastCellNum = row.getLastCellNum();
			List<Option> optionList = new ArrayList<Option>();
			for(int currIndex = row.getFirstCellNum(); currIndex < lastCellNum; currIndex++){
				HSSFCell currCell = row.getCell(currIndex);
				if(currIndex == 0){
					if(currCell == null || "".equals(currCell.toString())){
						break;
					}else{
						question = new Question();
						question.setContent(currCell.toString());
					}
				}else if(currIndex > 0){
					if(currCell == null || "".equals(currCell.toString())){
						continue;
					}else if(currIndex == 1){
						question.setType(getQuestionType(currCell.toString()));
					}else if(currIndex == 2){
						question.setPoints(Integer.valueOf(currCell.toString()));
					}else if(currIndex >= 3){
						Option option = new Option(currIndex-2);
						option.setContent(currCell.toString());
						if(currCell.getCellStyle().getFont(workBook).getBoldweight() == Font.BOLDWEIGHT_BOLD){
							option.setCorrect(true);
						}else{
							option.setCorrect(false);
						}
						optionList.add(option);
					}
				}
			}
			if(question!=null){
				question.setOptionsList(optionList);
			}
		}
		return question;
	}
	
	private boolean validateHorizontalHeaderTemplate(HSSFWorkbook workBook, HSSFSheet dataSheet, List<String> headerList) throws Exception {
		boolean validateTemplate = true;
		try {
			HSSFRow rowData = dataSheet.getRow(0);
			HSSFCellStyle cellStyle = createExcelStyle(workBook, "defaultstyle");
			int headerListSize = headerList.size();
			for (int cellIndex = 0; cellIndex < headerListSize; cellIndex++) {
				validateTemplate = headerList.get(cellIndex).equalsIgnoreCase(readCell(rowData, cellIndex)) ? true : false;
				if (!validateTemplate) {
					break;
				}
				dataSheet.setDefaultColumnStyle((short) cellIndex, cellStyle);
			}
		} catch (Exception e) {
			throw new Exception("Invalid Question Sheet. Please download the template", e);
		}
		return validateTemplate;
	}
	
	private HSSFCellStyle createExcelStyle(HSSFWorkbook workBook, String styleField) {
		HSSFFont font = null;
		HSSFCellStyle style = workBook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		style.setWrapText(true);
		if (styleField.equals(Constant.HEADER_FIELDSTYLE)) {
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
			font = workBook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.ROYAL_BLUE.index);
			font.setFontHeight((short) 200);
		} else if (styleField.equals(Constant.STATUS_HEADER_FIELDSTYLE)) {
			font = workBook.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.RED.index);
			font.setFontHeight((short) 300);
		} else if (styleField.equals(Constant.STATUS_DATA_FIELDSTYLE)) {
			font = workBook.createFont();
			font.setColor(HSSFColor.RED.index);
		}
		if (font != null) {
			style.setFont(font);
		}
		return style;
	}
	
	private boolean validateVerticalHeaderTemplate(HSSFWorkbook workbook, HSSFSheet sheet, List<String> headerList) throws Exception{
		boolean validateTemplate = true;
		try {
			Iterator<Row> rows = sheet.rowIterator();
			while(rows.hasNext()){
				HSSFRow row = (HSSFRow) rows.next();
				int rowNum = row.getRowNum();
				if(!headerList.get(rowNum).equalsIgnoreCase(readCell(row, 0))){
					validateTemplate = false;
				}
			}
		} catch (Exception e) {
			throw new Exception("Invalid Exam Sheet. Please download the template", e);
		}
		return validateTemplate;
	}
	
	private String readCell(HSSFRow rowData, int cellIndex) {
		return rowData.getCell(cellIndex) != null ? rowData.getCell(cellIndex).toString().trim() : null;
	}
	
	private int getQuestionType(String stringCellValue) {
		int type = 1;
		if(stringCellValue!=null){
			if(stringCellValue.equalsIgnoreCase("Multiple Response")){
				type = 2;
			}else if(stringCellValue.equalsIgnoreCase("True/False")){
				type=3;
			}else if(stringCellValue.equalsIgnoreCase("Fill in the Blank")){
				type=4;
			}
		}
		return type;
	}
}
