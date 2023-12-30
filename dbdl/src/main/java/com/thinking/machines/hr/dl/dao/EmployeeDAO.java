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
import java.sql.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME = "employee.data";


public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid Designation Code: "+designationCode);
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation Code : "+designationCode);
}
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender = employeeDTO.getGender();
if(gender == ' ')
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender is not set to Male/Female");
}
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum() == -1)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Invalid basic salary : "+basicSalary.toPlainString());
}
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number is null");
}
panNumber = panNumber.trim();
if(panNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of PAN Number is zero");
}
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number is null");
}
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of Aadhar Card Number is zero");
}
try
{
boolean panNumberExists = false;
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select gender from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
panNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists=false;
preparedStatement = connection.prepareStatement("select gender from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet = preparedStatement.executeQuery();
aadharCardNumberExists = resultSet.next();
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number : ("+panNumber+") and Aadhar Card Number : ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number : ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") exists");
}
preparedStatement = connection.prepareStatement("insert into employee(name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet = preparedStatement.getGeneratedKeys();
resultSet.next();
int generatedEmployeeId = resultSet.getInt(1);
employeeDTO.setEmployeeId("A"+(1000000+generatedEmployeeId));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
employeeId = employeeDTO.getEmployeeId();
if(employeeId == null) throw new DAOException("Employee Id. is null");
employeeId = employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of Employee Id. is zero");
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid Designation Code: "+designationCode);
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation Code : "+designationCode);
}
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
java.util.Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Date of Birth is null");
}
char gender = employeeDTO.getGender();
if(gender == ' ')
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Gender is not set to Male/Female");
}
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Basic salary is null");
}
if(basicSalary.signum() == -1)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Invalid basic salary : "+basicSalary.toPlainString());
}
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number is null");
}
panNumber = panNumber.trim();
if(panNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of PAN Number is zero");
}
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number is null");
}
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Length of Aadhar Card Number is zero");
}
try
{
boolean panNumberExists = false;
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select employee_id from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==true)
{
String tempEmployeeId = "A"+(1000000+resultSet.getInt("employee_id"));
if(employeeId.equalsIgnoreCase(tempEmployeeId)) panNumberExists = false;
else panNumberExists = true;
}
resultSet.close();
preparedStatement.close();
boolean aadharCardNumberExists=false;
preparedStatement = connection.prepareStatement("select employee_id from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==true)
{
String tempEmployeeId = "A"+(1000000+resultSet.getInt("employee_id"));
if(employeeId.equalsIgnoreCase(tempEmployeeId)) aadharCardNumberExists = false;
else aadharCardNumberExists = true;
}
resultSet.close();
preparedStatement.close();
if(panNumberExists && aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number : ("+panNumber+") and Aadhar Card Number : ("+aadharCardNumber+") exists");
}
if(panNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("PAN Number : ("+panNumber+") exists");
}
if(aadharCardNumberExists)
{
try
{
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
throw new DAOException("Aadhar Card Number ("+aadharCardNumber+") exists");
}
if(employeeId.substring(0,1).equalsIgnoreCase("A")==false) throw new DAOException("Invalid Employee Id");
int actualEmployeeId;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid Employee Id");
}
preparedStatement = connection.prepareStatement("update employee set name=?, designation_code=?, date_of_birth=?, gender=?, is_indian=?, basic_salary=?, pan_number=?, aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,name);
preparedStatement.setInt(2,designationCode);
java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getYear(),dateOfBirth.getMonth(),dateOfBirth.getDate());
preparedStatement.setDate(3,sqlDateOfBirth);
preparedStatement.setString(4,String.valueOf(gender));
preparedStatement.setBoolean(5,isIndian);
preparedStatement.setBigDecimal(6,basicSalary);
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public void delete(String employeeId) throws DAOException
{
if(employeeId == null) throw new DAOException("Employee Id. is null");
employeeId = employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of Employee Id. is zero");
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
int actualEmployeeId;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid Employee Id");
}
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select gender from employee where employee_id = ?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
preparedStatement = connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees;
employees = new TreeSet<>();
Connection connection = null;
Statement statement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
statement = connection.createStatement();
resultSet = statement.executeQuery("select * from employee");
EmployeeDTOInterface employeeDTO = null;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next())
{
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth = resultSet.getDate("date_of_birth");
utilDateOfBirth = new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender = resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else if(gender.equalsIgnoreCase("F"))
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}


public Set<EmployeeDTOInterface> getByDesignation(int designationCode) throws DAOException
{
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
Set<EmployeeDTOInterface> employees;
employees = new TreeSet<>();
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code=?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation code : "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select * from employee where designation_code = ?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
EmployeeDTOInterface employeeDTO = null;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
while(resultSet.next())
{
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth = resultSet.getDate("date_of_birth");
utilDateOfBirth = new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender = resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else if(gender.equalsIgnoreCase("F"))
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
employees.add(employeeDTO);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return employees;
}


public boolean isDesignationAlloted(int designationCode) throws DAOException
{

Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean isDesignationAlloted=false;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select gender from employee where designation_code = ?");
preparedStatement.setInt(1,designationCode);
resultSet=preparedStatement.executeQuery();
isDesignationAlloted = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return isDesignationAlloted;
}


public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId == null) throw new DAOException("Invalid employee Id : "+employeeId);
employeeId = employeeId.trim();
if(employeeId.length() == 0) throw new DAOException("Length of Employee Id. is zero");
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
if(employeeId.substring(0,1).equalsIgnoreCase("A")==false) throw new DAOException("Invalid Employee Id: "+employeeId);
int actualEmployeeId = 0;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid Employee Id");
}
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select * from employee where employee_id = ?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Employee Id : "+employeeId);
}
EmployeeDTOInterface employeeDTO = null;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth = resultSet.getDate("date_of_birth");
utilDateOfBirth = new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender = resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else if(gender.equalsIgnoreCase("F"))
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber == null) throw new DAOException("Invalid PAN Number : "+panNumber);
panNumber = panNumber.trim();
if(panNumber.length() == 0) throw new DAOException("Length of PAN Number is zero");
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select * from employee where pan_number = ?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid PAN Number: "+panNumber);
}
EmployeeDTOInterface employeeDTO = null;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth = resultSet.getDate("date_of_birth");
utilDateOfBirth = new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender = resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else if(gender.equalsIgnoreCase("F"))
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber == null) throw new DAOException("Invalid Aadhar Card Number : "+aadharCardNumber);
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) throw new DAOException("Length of Aadhar Card Number is zero");
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select * from employee where aadhar_card_number = ?");
preparedStatement.setString(1,aadharCardNumber);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Aadhar Card Number: "+aadharCardNumber);
}
EmployeeDTOInterface employeeDTO = null;
java.util.Date utilDateOfBirth;
java.sql.Date sqlDateOfBirth;
String gender;
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+(resultSet.getInt("employee_id")+1000000));
employeeDTO.setName(resultSet.getString("name").trim());
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
sqlDateOfBirth = resultSet.getDate("date_of_birth");
utilDateOfBirth = new java.util.Date(sqlDateOfBirth.getYear(),sqlDateOfBirth.getMonth(),sqlDateOfBirth.getDate());
employeeDTO.setDateOfBirth(utilDateOfBirth);
gender = resultSet.getString("gender");
if(gender.equalsIgnoreCase("M"))
{
employeeDTO.setGender(GENDER.MALE);
}
else if(gender.equalsIgnoreCase("F"))
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(resultSet.getBoolean("is_indian"));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.setPANNumber(resultSet.getString("pan_number").trim());
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId == null) return false;
employeeId = employeeId.trim();
if(employeeId.length() == 0) return false;
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean employeeIdExists = false;
try
{
if(employeeId.substring(0,1).equalsIgnoreCase("A")==false) throw new DAOException("Invalid Employee Id : "+employeeId);
int actualEmployeeId = 0;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1))-1000000;
}catch(Exception exception)
{
throw new DAOException("Invalid Employee Id : "+employeeId);
}
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select gender from employee where employee_id = ?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
employeeIdExists = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
return employeeIdExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber == null) return false;
panNumber = panNumber.trim();
if(panNumber.length() == 0) return false;
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean panNumberExists = false;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select gender from employee where pan_number = ?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
panNumberExists = resultSet.next();
return panNumberExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber == null) return false;
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) return false;
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
boolean aadharCardNumberExists = false;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select gender from employee where aadhar_card_number = ?");
preparedStatement.setString(1,aadharCardNumber);
resultSet = preparedStatement.executeQuery();
aadharCardNumberExists = resultSet.next();
return aadharCardNumberExists;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public int getCount() throws DAOException
{
Connection connection = null;
Statement statement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
statement = connection.createStatement();
resultSet = statement.executeQuery("select count(*) as count from employee");
resultSet.next();
int count = resultSet.getInt("count");
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public int getCountByDesignation(int designationCode) throws DAOException
{
Connection connection = null;
PreparedStatement preparedStatement;
ResultSet resultSet;
try
{
connection = DAOConnection.getConnection();
preparedStatement = connection.prepareStatement("select code from designation where code = ?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid Designation Code : "+designationCode);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select count(*) as count from employee where designation_code=?");
preparedStatement.setInt(1,designationCode);
resultSet = preparedStatement.executeQuery();
resultSet.next();
int count = resultSet.getInt("count");
resultSet.close();
preparedStatement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}
