package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.io.image.*;

public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private String [] columnTitle;
private void populateDataStructure()
{
try
{
DesignationManagerInterface blDesignation = DesignationManager.getDesignationManager();
this.designations = new LinkedList<>();
columnTitle = new String[2];
columnTitle[0] = "S.No.";
columnTitle[1] = "Designations";
Set<DesignationInterface> blDesignations = blDesignation.getDesignations();
for(DesignationInterface designation : blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left, DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}catch(BLException blException)
{
// ????? what to do is yet to be decided
}
}
public DesignationModel()
{
populateDataStructure();
}
public int getRowCount()
{
return this.designations.size();
}
public int getColumnCount()
{
return this.columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex, int columnIndex)
{
if(columnIndex == 0) return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class; //special treatement as good as Class.forName(java.lang.Integer)
return String.class;
}
public boolean isCellEditable(int rowIndex, int columnIndex)
{
return false;
}

// Application Specific Method
public void add(DesignationInterface designation) throws BLException
{
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations, new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left, DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}

public int indexOfDesignation(DesignationInterface designation) throws BLException
{
int index=0;
Iterator<DesignationInterface> iterator = this.designations.iterator();
DesignationInterface d;
while(iterator.hasNext())
{
d = iterator.next();
if(d.equals(designation)) return index;
index++;
}
BLException blException = new BLException();
blException.setGenericException("Invalid Desgination : "+designation.getTitle());
throw blException;
}

public int indexOfTitle(String title, boolean partialLeftSearch) throws BLException
{
int index=0;
Iterator<DesignationInterface> iterator = this.designations.iterator();
DesignationInterface d;
while(iterator.hasNext())
{
d = iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase())) return index;
}
else
{
if(d.getTitle().equalsIgnoreCase(title)) return index;
}
index++;
}
BLException blException = new BLException();
blException.setGenericException("Invalid Title : "+title);
throw blException;
}

public void update(DesignationInterface designation) throws BLException
{
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left, DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}

public void remove(int code) throws BLException
{
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator = this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d = iterator.next();
if(d.getCode()==code) break;
index++;
}
if(index == this.designations.size())
{
BLException blException = new BLException();
blException.setGenericException("Invalid Code : "+code);
throw blException;
}
this.designations.remove(index);
}

public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=this.designations.size())
{
BLException blException = new BLException();
blException.setGenericException("Invalid Index : "+index);
throw blException;
}
return this.designations.get(index);
}

public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter = new PdfWriter(file);
PdfDocument pdfDocument = new PdfDocument(pdfWriter);
Document document = new Document(pdfDocument);
Image logo = new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
Paragraph logoParagraph = new Paragraph();
logoParagraph.add(logo);
PdfFont companyTitleFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
Paragraph companyTitle = new Paragraph();
companyTitle.add("CP CORPORATION");
companyTitle.setFont(companyTitleFont);
companyTitle.setFontSize(18);
Paragraph pageNumberParagraph;
Table pageNumberTable;
PdfFont pageNumberFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
PdfFont reportTitleFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
Paragraph reportTitle = new Paragraph("List of Designations");
reportTitle.setFont(reportTitleFont);
reportTitle.setFontSize(15);
PdfFont columnTitleFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
Paragraph columnTitle1 = new Paragraph("S.No.");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(14);
Paragraph columnTitle2 = new Paragraph("Designations");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(14);
PdfFont footerFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
Paragraph footerParagraph = new Paragraph("Made By : Chirag Panjwani");
footerParagraph.setFont(footerFont);
footerParagraph.setFontSize(12);
float topTableWidths[] = {1,5};
float dataTableWidths[] = {1,5};
float bottomTableWidths[] = {1,5};
Table topTable;
Table dataTable=null;
Table bottomTable;
int sno,i,pageNo,pageSize;
pageSize=15;
boolean newPage = true;
Cell cell;
int totalPages = this.designations.size()/pageSize;
if(this.designations.size()%pageSize!=0) totalPages++;
DesignationInterface designation;
sno=0;
i=0;
pageNo=0;
while(i<this.designations.size())
{
if(newPage)
{
//create new Page
pageNo++;
topTable = new Table(UnitValue.createPercentArray(topTableWidths));
cell = new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoParagraph);
topTable.addHeaderCell(cell);
cell = new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyTitle);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
cell.setTextAlignment(TextAlignment.CENTER);
topTable.addHeaderCell(cell);
document.add(topTable);
pageNumberTable = new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
pageNumberParagraph = new Paragraph("Page : "+pageNo+"/"+totalPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(13);
cell = new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
dataTable = new Table(UnitValue.createPercentArray(dataTableWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell = new Cell(1,2);
cell.add(reportTitle);
cell.setHeight(30);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
cell = new Cell();
cell.add(columnTitle1);
cell.setHeight(30);
dataTable.addHeaderCell(cell);
cell = new Cell();
cell.add(columnTitle2);
cell.setHeight(30);
dataTable.addHeaderCell(cell);
newPage=false;
}
//add Row to the table
sno++;
cell = new Cell();
Paragraph dataParagraph = new Paragraph(Integer.toString(sno));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setHeight(30);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
designation = this.designations.get(i);
dataParagraph = new Paragraph(designation.getTitle());
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell = new Cell();
cell.add(dataParagraph);
cell.setHeight(30);
dataTable.addCell(cell);
i++;
if(sno%pageSize==0 || i==this.designations.size())
{
document.add(dataTable);
bottomTable = new Table(UnitValue.createPercentArray(bottomTableWidths));
bottomTable.setWidth(UnitValue.createPercentValue(100));
cell = new Cell(1,2);
cell.setBorder(Border.NO_BORDER);
cell.add(footerParagraph);
cell.setTextAlignment(TextAlignment.LEFT);
bottomTable.addCell(cell);
document.add(bottomTable);
if(i < this.designations.size())
{
//add new page to document
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}
document.close();
}catch(Exception e)
{
BLException blException = new BLException();
blException.setGenericException(e.getMessage());
throw blException;
}
}
}