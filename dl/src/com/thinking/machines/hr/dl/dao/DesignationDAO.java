package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;

public class DesignationDAO implements DesignationDAOInterface
{

private final static String FILE_NAME = "designation.data";

public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO == null) throw new DAOException("Designation is null");
String title = designationDTO.getTitle();
if(title == null) throw new DAOException("Designation is null");
title = title.trim();
if(title.length() == 0) throw new DAOException("Length of designation is zero");
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
int lastGeneratedCode = 0;
int recordCount = 0;
String lastGeneratedCodeString = "";
String recordCountString = "";
if(randomAccessFile.length() == 0)
{
lastGeneratedCode = 0;
recordCount = 0;
lastGeneratedCodeString = "0";
while(lastGeneratedCodeString.length() < 10) lastGeneratedCodeString+=" ";
recordCountString = "0";
while(recordCountString.length() < 10) recordCountString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCodeString = randomAccessFile.readLine();
recordCountString = randomAccessFile.readLine();
lastGeneratedCodeString = lastGeneratedCodeString.trim();
recordCountString = recordCountString.trim();
lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
recordCount = Integer.parseInt(recordCountString);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
throw new DAOException("Designation: "+title+" exists");
}
}
int code = lastGeneratedCode + 1;
randomAccessFile.writeBytes(String.valueOf(code));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n");
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString = String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length() < 10) lastGeneratedCodeString += " ";
recordCountString = String.valueOf(recordCount);
while(recordCountString.length() < 10) recordCountString += " ";
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
designationDTO.setCode(code);
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO == null) throw new DAOException("Designation is null");
int code = designationDTO.getCode();
if(code<=0) throw new DAOException("Invalid Designation code : "+code);
String title = designationDTO.getTitle();
if(title == null) throw new DAOException("Designation is null");
title = title.trim();
if(title.length() == 0) throw new DAOException("Length of designation is zero");
try
{
File file = new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Invalid Designation Code: "+code);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0) 
{
randomAccessFile.close();
throw new DAOException("Invalid Designation Code: "+code);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
boolean found = false;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
if(fCode == code)
{
found = true;
break;
}
randomAccessFile.readLine();
}
if(found == false)
{
randomAccessFile.close();
throw new DAOException("Invalid Designatiion Code : "+code);
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode != code && title.equalsIgnoreCase(fTitle))
{
randomAccessFile.close();
throw new DAOException("Designation : "+title+" exists");
}
}
randomAccessFile.seek(0);
File tmpFile = new File("tmp.data");
RandomAccessFile tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode != code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
else
{
tmpRandomAccessFile.writeBytes(String.valueOf(code));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(title);
tmpRandomAccessFile.writeBytes("\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public void delete(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid Designation code : "+code);
try
{
File file = new File(FILE_NAME);
if(!file.exists()) throw new DAOException("Invalid Designation Code: "+code);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0) 
{
randomAccessFile.close();
throw new DAOException("Invalid Designation Code: "+code);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle = "";
boolean found = false;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode == code)
{
found = true;
break;
}
}
if(found == false)
{
randomAccessFile.close();
throw new DAOException("Invalid Designatiion Code : "+code);
}
if(new EmployeeDAO().isDesignationAlloted(code) == true)
{
randomAccessFile.close();
throw new DAOException("Employee exists with Designation : "+fTitle);
}
randomAccessFile.seek(0);
File tmpFile = new File("tmp.data");
RandomAccessFile tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine());
tmpRandomAccessFile.writeBytes("\n");
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
recordCount--;
String recordCountString = String.valueOf(recordCount);
while(recordCountString.length() < 10) recordCountString = recordCountString+" ";
tmpRandomAccessFile.writeBytes(recordCountString);
tmpRandomAccessFile.writeBytes("\n");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(fCode != code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine());
randomAccessFile.writeBytes("\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public Set<DesignationDTOInterface> getAll() throws DAOException
{
try
{
Set<DesignationDTOInterface> designations;
designations = new TreeSet<>();
File file = new File(FILE_NAME);
if(!file.exists()) return designations;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return designations;
}
DesignationDTOInterface designationDTO;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
designationDTO = new DesignationDTO();
designationDTO.setCode(Integer.parseInt(randomAccessFile.readLine()));
designationDTO.setTitle(randomAccessFile.readLine());
designations.add(designationDTO);
}
randomAccessFile.close();
return designations;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid Designation Code : "+code);
try
{
File file = new File(FILE_NAME);
if(file.exists()== false) throw new DAOException("Invalid Designation Code : "+code);
RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation Code : "+code);
}
int lastGeneratedCode = Integer.parseInt(randomAccessFile.readLine().trim());
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation Code : "+code);
}
if(code > lastGeneratedCode)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation Code : "+code);
}
int fCode = 0;
String fTitle = "";
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(code == fCode)
{
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
randomAccessFile.close();
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Designation Code : "+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title == null) throw new DAOException("Invalid Designation");
title = title.trim();
if(title.length() == 0) throw new DAOException("Invalid Designation");
try
{
File file = new File(FILE_NAME);
if(file.exists()== false) throw new DAOException("Invalid Designation: "+title);
RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation: "+title);
}
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Designation: "+title);
}
int fCode = 0;
String fTitle = "";
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
fTitle = randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
{
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
randomAccessFile.close();
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Designation: "+title);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()== false) return false;
RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return false;
}
int lastGeneratedCode = Integer.parseInt(randomAccessFile.readLine().trim());
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
return false;
}
if(code > lastGeneratedCode)
{
randomAccessFile.close();
return false;
}
int fCode = 0;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fCode = Integer.parseInt(randomAccessFile.readLine());
if(code == fCode)
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public boolean titleExists(String title) throws DAOException
{
if(title == null) return false;
title = title.trim();
if(title.length() == 0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists()== false) return false;
RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount == 0)
{
randomAccessFile.close();
return false;
}
String fTitle = "";
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
randomAccessFile.readLine();
fTitle = randomAccessFile.readLine();
if(title.equalsIgnoreCase(fTitle))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public int getCount() throws DAOException
{
try
{
File file = new File(FILE_NAME);
if(file.exists() == false) return 0;
RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount;
recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}