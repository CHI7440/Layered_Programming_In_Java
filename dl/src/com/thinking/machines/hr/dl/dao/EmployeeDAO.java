package com.thinking.machines.hr.dl.dao;

import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;

public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME = "employee.data";


public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
String employeeId;
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid Designation Code: "+designationCode);
DesignationDAO designationDAO = new DesignationDAO();
if(designationDAO.codeExists(designationCode) == false) throw new DAOException("Invalid Designation Code : "+designationCode);
Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth == null) throw new DAOException("Date of Birth is null");
char gender = employeeDTO.getGender();
if(gender == ' ') throw new DAOException("Gender is not set to Male/Female");
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary == null) throw new DAOException("Basic salary is null");
if(basicSalary.signum() == -1) throw new DAOException("Invalid basic salary : "+basicSalary.toPlainString());
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null) throw new DAOException("PAN Number is null");
panNumber = panNumber.trim();
if(panNumber.length() == 0) throw new DAOException("Length of PAN Number is zero");
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null) throw new DAOException("Aadhar Card Number is null");
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) throw new DAOException("Length of Aadhar Card Number is zero");
try
{
File file = new File(FILE_NAME);
RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
int lastGeneratedCode = 0;
String lastGeneratedCodeString = "";
int recordCount = 0;
String recordCountString = "";
if(randomAccessFile.length() == 0)
{
lastGeneratedCode = 10000000;
lastGeneratedCodeString = "10000000";
lastGeneratedCodeString = String.format("%-10s", lastGeneratedCodeString);
recordCount = 0;
recordCountString = "0";
recordCountString = String.format("%-10s", recordCountString);
randomAccessFile.writeBytes(lastGeneratedCodeString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedCode = Integer.parseInt(randomAccessFile.readLine().trim());
recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
}
boolean panNumberExists = false;
boolean aadharCardNumberExists = false;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
for(int i=1; i<=7; i++) randomAccessFile.readLine();
String fPANNumber = randomAccessFile.readLine();
String fAadharCardNumber = randomAccessFile.readLine();
if(panNumberExists == false && fPANNumber.equalsIgnoreCase(panNumber)) panNumberExists = true;
if(aadharCardNumberExists == false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber) ) aadharCardNumberExists = true;
if(aadharCardNumberExists && panNumberExists) break;
}
if(panNumberExists && aadharCardNumberExists) throw new DAOException("PAN Number : ("+panNumber+") and Aadhar Card Number : ("+aadharCardNumber+") exists");
if(panNumberExists) throw new DAOException("PAN Number : ("+panNumber+") exists");
if(aadharCardNumberExists) throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") exists");

lastGeneratedCode++;
lastGeneratedCodeString = String.format("%-10d", lastGeneratedCode);
employeeId = "A"+lastGeneratedCodeString.trim();
recordCount++;
recordCountString = String.format("%-10d",recordCount);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO == null) throw new DAOException("Employee is null");
String employeeId = employeeDTO.getEmployeeId();
if(employeeId == null) throw new DAOException("Employee Id is null");
if(employeeId.length() == 0) throw new DAOException("Length of Employee Id is zero");
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid Designation Code: "+designationCode);
DesignationDAO designationDAO = new DesignationDAO();
if(designationDAO.codeExists(designationCode) == false) throw new DAOException("Invalid Designation Code : "+designationCode);
Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth == null) throw new DAOException("Date of Birth is null");
char gender = employeeDTO.getGender();
if(gender == ' ') throw new DAOException("Gender is not set to Male/Female");
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary == null) throw new DAOException("Basic salary is null");
if(basicSalary.signum() == -1) throw new DAOException("Invalid basic salary : "+basicSalary.toPlainString());
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null) throw new DAOException("PAN Number is null");
panNumber = panNumber.trim();
if(panNumber.length() == 0) throw new DAOException("Length of PAN Number is zero");
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null) throw new DAOException("Aadhar Card Number is null");
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) throw new DAOException("Length of Aadhar Card Number is zero");
try
{
File file = new File(FILE_NAME);
if(file.exists() == false) throw new DAOException("Invalid Employee Id : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fPANNumber;
String fAadharCardNumber;
boolean employeeIdFound = false;
boolean panNumberFound = false;
boolean aadharCardNumberFound = false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
long foundAt = 0;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
if(employeeIdFound == false) foundAt = randomAccessFile.getFilePointer();
fEmployeeId = randomAccessFile.readLine();
for(int i=1; i<=6; i++) randomAccessFile.readLine();
fPANNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(employeeIdFound == false && fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound = true;
}
if(panNumberFound == false && fPANNumber.equalsIgnoreCase(panNumber))
{
panNumberFound = true;
panNumberFoundAgainstEmployeeId = fEmployeeId;
}
if(aadharCardNumberFound == false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound = true;
aadharCardNumberFoundAgainstEmployeeId = fEmployeeId;
}
if(employeeIdFound && panNumberFound && aadharCardNumberFound) break;
}
if(employeeIdFound == false)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
boolean panNumberExists = false;
if(panNumberFound == true && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId) == false)
{
panNumberExists = true;
}
boolean aadharCardNumberExists = false;
if(aadharCardNumberFound == true && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId) == false)
{
aadharCardNumberExists = true;
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN Number : ("+panNumber+") and Aadhar Card Number : ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("Pan Number : ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar Card Number : ("+aadharCardNumber+") exists");
}
File tmpFile = new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(foundAt);
for(int i=1; i<=9; i++) randomAccessFile.readLine();
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public void delete(String employeeId) throws DAOException
{
if(employeeId == null) throw new DAOException("Employee Id is null");
if(employeeId.length() == 0) throw new DAOException("Length of Employee Id is zero");
try
{
File file = new File(FILE_NAME);
if(file.exists() == false) throw new DAOException("Invalid Employee Id : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
String fEmployeeId;
long foundAt = 0;
boolean employeeIdFound = false;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
foundAt = randomAccessFile.getFilePointer();
fEmployeeId = randomAccessFile.readLine();
for(int i=1; i<=8; i++) randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound = true;
break;
}
}
if(employeeIdFound == false)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
File tmpFile = new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile = new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(foundAt);
for(int i=1; i<=9; i++) randomAccessFile.readLine();
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer() < tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
randomAccessFile.seek(0);
randomAccessFile.readLine();
recordCount--;
String recordCountString = String.format("%-10d",recordCount);
randomAccessFile.writeBytes(recordCountString+"\n");
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public Set<EmployeeDTOInterface> getAll() throws DAOException
{
try
{
Set<EmployeeDTOInterface> employees;
employees = new TreeSet<>();
File file = new File(FILE_NAME);
if(file.exists() == false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
char fGender;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
// do nothing
}
fGender = randomAccessFile.readLine().charAt(0);
if(fGender == 'M' || fGender == 'm')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender == 'F' || fGender == 'f')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public Set<EmployeeDTOInterface> getByDesignation(int designationCode) throws DAOException
{
try
{
if(new DesignationDAO().codeExists(designationCode) == false) throw new DAOException("Invalid Designation Code : "+designationCode);
Set<EmployeeDTOInterface> employees;
employees = new TreeSet<>();
File file = new File(FILE_NAME);
if(file.exists() == false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
int fDesignationCode;
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
char fGender;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();
fEmployeeId = randomAccessFile.readLine();
fName = randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode != designationCode)
{
for(int i=1; i<=6; i++) randomAccessFile.readLine();
continue;
}
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
// do nothing
}
fGender = randomAccessFile.readLine().charAt(0);
if(fGender == 'M' || fGender == 'm')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender == 'F' || fGender == 'f')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public boolean isDesignationAlloted(int designationCode) throws DAOException
{
try
{
if(new DesignationDAO().codeExists(designationCode) == false) throw new DAOException("Invalid Designation Code : "+designationCode);
File file = new File(FILE_NAME);
if(file.exists() == false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode != designationCode)
{
for(int i=1; i<=6; i++) randomAccessFile.readLine();
continue;
}
else
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


public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId == null) throw new DAOException("Invalid employee Id : "+employeeId);
employeeId = employeeId.trim();
if(employeeId.length() == 0) throw new DAOException("Invalid employee Id : employee Id is of zero length");
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO = new EmployeeDTO();
File file = new File(FILE_NAME);
if(file.exists() == false) throw new DAOException("Invalid Employee Id : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
char fGender;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId) == false)
{
for(int i=1; i<=8; i++) randomAccessFile.readLine();
continue;
}
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
// do nothing
}
fGender = randomAccessFile.readLine().charAt(0);
if(fGender == 'M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender == 'F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
randomAccessFile.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber == null) throw new DAOException("Invalid PAN Number : "+panNumber);
panNumber = panNumber.trim();
if(panNumber.length() == 0) throw new DAOException("Invalid PAN Number : PAN Number is of zero length");
try
{
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth = null;
GENDER fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
File file = new File(FILE_NAME);
if(file.exists() == false) throw new DAOException("Invalid PAN Number : "+panNumber);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
fName = randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
// do nothing
}
char gender = randomAccessFile.readLine().charAt(0);
if(gender == 'M')
{
fGender = GENDER.MALE;
}
else
{
fGender = GENDER.FEMALE;
}
fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary = new BigDecimal(randomAccessFile.readLine());
fPANNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
{
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(fGender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid PAN Number : "+panNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber == null) throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber);
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) throw new DAOException("Invalid Aadhar Card Number : Aadhar Card Number is of zero length");
try
{
EmployeeDTOInterface employeeDTO;
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth = null;
GENDER fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
File file = new File(FILE_NAME);
if(file.exists() == false) throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber);
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
fName = randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth = simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
// do nothing
}
char gender = randomAccessFile.readLine().charAt(0);
if(gender == 'M')
{
fGender = GENDER.MALE;
}
else
{
fGender = GENDER.FEMALE;
}
fIsIndian = Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary = new BigDecimal(randomAccessFile.readLine());
fPANNumber = randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
employeeDTO.setGender(fGender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId == null) return false;
employeeId = employeeId.trim();
if(employeeId.length() == 0) return false;
try
{
File file = new File(FILE_NAME);
if(file.exists() == false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
fEmployeeId = randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId) == false)
{
for(int i=1; i<=8; i++) randomAccessFile.readLine();
continue;
}
randomAccessFile.close();
return true;
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber == null) return false;
panNumber = panNumber.trim();
if(panNumber.length() == 0) return false;
try
{
String fPANNumber;
File file = new File(FILE_NAME);
if(file.exists() == false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
for(int i=1;i<=7;i++) randomAccessFile.readLine();
fPANNumber = randomAccessFile.readLine();
if(fPANNumber.equalsIgnoreCase(panNumber))
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

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber == null) return false;
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) return false;
try
{
String fAadharCardNumber;
File file = new File(FILE_NAME);
if(file.exists() == false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
SimpleDateFormat simpleDateFormat;
simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
for(int i=1;i<=8;i++) randomAccessFile.readLine();
fAadharCardNumber = randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
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
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount = Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public int getCountByDesignation(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode) == false) throw new DAOException("Invalid Designation Code : "+designationCode);
try
{
File file = new File(FILE_NAME);
if(file.exists() == false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile = new RandomAccessFile(file,"rw");
if(randomAccessFile.length() == 0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int designationCount = 0;
int fDesignationCode;
while(randomAccessFile.getFilePointer() < randomAccessFile.length())
{
for(int i=1; i<=2; i++) randomAccessFile.readLine();
fDesignationCode = Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode == designationCode) designationCount++;
for(int i=1; i<=6; i++) randomAccessFile.readLine();
}
randomAccessFile.close();
return designationCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}