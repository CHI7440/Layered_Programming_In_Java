package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeeMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeeMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeeMap;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeeMap;
private Set<EmployeeInterface> employeeSet;
private static EmployeeManagerInterface employeeManager = null;

private EmployeeManager() throws BLException
{
populateDataStructure();
}


private void populateDataStructure() throws BLException
{
this.employeeIdWiseEmployeeMap= new HashMap<>();
this.panNumberWiseEmployeeMap = new HashMap<>();
this.aadharCardNumberWiseEmployeeMap = new HashMap<>();
this.employeeSet = new TreeSet<>();
this.designationCodeWiseEmployeeMap = new HashMap<>();
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees = employeeDAO.getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager;
DesignationInterface designation;
designationManager = DesignationManager.getDesignationManager();
Set<EmployeeInterface> ets;

for(EmployeeDTOInterface dlEmployee : dlEmployees)
{
employee = new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
int dlDesignationCode = dlEmployee.getDesignationCode();
designation = designationManager.getDesignationByCode(dlDesignationCode);
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dlEmployee.getDateOfBirth().clone());
if(dlEmployee.getGender() == 'M') employee.setGender(GENDER.MALE);
else employee.setGender(GENDER.FEMALE);
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeeMap.put(employee.getEmployeeId().toUpperCase(), employee);
this.panNumberWiseEmployeeMap.put(employee.getPANNumber().toUpperCase(), employee);
this.aadharCardNumberWiseEmployeeMap.put(employee.getAadharCardNumber().toUpperCase(), employee);
this.employeeSet.add(employee);
ets = this.designationCodeWiseEmployeeMap.get(dlDesignationCode);
if(ets == null)
{
ets = new TreeSet<>();
ets.add(employee);
this.designationCodeWiseEmployeeMap.put(dlDesignationCode,ets);
}else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}


public  static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager == null) employeeManager = new EmployeeManager();
return employeeManager;
}


public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException = new BLException();
if(employee == null)
{
blException.setGenericException("Employee is null");
throw blException;
}
String employeeId = employee.getEmployeeId();
if(employeeId != null && employeeId.trim().length()>0)
{
blException.addException("Employee Id", "Employee Id should be nil/empty");
}
String name = employee.getName();
if(name == null)
{
blException.addException("Name", "Name required");
name = "";
}
name = name.trim();
if(name.length() == 0)
{
blException.addException("Name", "Name required");
}
DesignationInterface designation = employee.getDesignation();
if(designation == null)
{
blException.addException("Designation","Designation required");
}
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
if(!(designationManager.designationCodeExists(designation.getCode())))
{
blException.addException("Designation","Invalid Designation code");
}
Date dateOfBirth = employee.getDateOfBirth();
if(dateOfBirth == null)
{
blException.addException("Date Of Birth","Date Of Birth Required");
}
char gender = employee.getGender();
if(gender == ' ')
{
blException.addException("Gender", "Gender required");
}
boolean isIndian = employee.getIsIndian();
BigDecimal basicSalary = employee.getBasicSalary();
if(basicSalary == null)
{
blException.addException("Basic Salary", "Basic Salary required");
}
if(basicSalary.signum() == -1)
{
blException.addException("Basic Salary", "Invalid basic salary");
}
String panNumber = employee.getPANNumber();
if(panNumber == null)
{
blException.addException("PAN Number", "PAN Number required");
panNumber = "";
}
panNumber = panNumber.trim();
if(panNumber.length() == 0)
{
blException.addException("PAN Number", "PAN Number required");
}
String aadharCardNumber = employee.getAadharCardNumber();
if(aadharCardNumber == null)
{
blException.addException("Aadhar Card Number", "Aadhar Card Number required");
aadharCardNumber="";
}
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0)
{
blException.addException("Aadhar Card Number", "Aadhar Card Number required");
}
if(this.panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase()) == true)
{
blException.addException("PAN Number", "PAN Number exists");
}
if(this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase()) == true)
{
blException.addException("Aadhar Card Number", "Aadhar Card Number exists");
}
if(blException.hasExceptions())
{
throw blException;
}

try
{
EmployeeDTO dlEmployee = new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber.toUpperCase());
dlEmployee.setAadharCardNumber(aadharCardNumber.toUpperCase());
EmployeeDAO employeeDAO = new EmployeeDAO();
employeeDAO.add(dlEmployee);

employeeId = dlEmployee.getEmployeeId();
employee.setEmployeeId(employeeId);
EmployeeInterface dsEmployee= new Employee();
dsEmployee.setEmployeeId(employeeId);
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber.toUpperCase());
dsEmployee.setAadharCardNumber(aadharCardNumber.toUpperCase());
this.employeeIdWiseEmployeeMap.put(employeeId,dsEmployee);
this.panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeeSet.add(dsEmployee);
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeeMap.get(designation.getCode());
if(ets == null)
{
ets = new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeeMap.put(designation.getCode(),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}


public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException = new BLException();
if(employee == null)
{
blException.setGenericException("Employee is null");
throw blException;
}
String employeeId = employee.getEmployeeId();
if(employeeId == null)
{
blException.addException("Employee Id", "Employee Required");
}
else
{
employeeId = employeeId.trim();
if(employeeId.length() == 0)
{
blException.addException("Employee Id", "Employee Required");
}
else
{
if(this.employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase()) == false)
{
blException.addException("Employee Id", "Invalid Employee Id");
throw blException;
}
}
}
String name = employee.getName();
if(name == null)
{
blException.addException("Name", "Name required");
name = "";
}
name = name.trim();
if(name.length() == 0)
{
blException.addException("Name", "Name required");
}
DesignationInterface designation = employee.getDesignation();
if(designation == null)
{
blException.addException("Designation","Designation required");
}
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
if(!(designationManager.designationCodeExists(designation.getCode())))
{
blException.addException("Designation","Invalid Designation code");
}
Date dateOfBirth = employee.getDateOfBirth();
if(dateOfBirth == null)
{
blException.addException("Date Of Birth","Date Of Birth Required");
}
char gender = employee.getGender();
if(gender == ' ')
{
blException.addException("Gender", "Gender required");
}
boolean isIndian = employee.getIsIndian();
BigDecimal basicSalary = employee.getBasicSalary();
if(basicSalary == null)
{
blException.addException("Basic Salary", "Basic Salary required");
}
if(basicSalary.signum() == -1)
{
blException.addException("Basic Salary", "Invalid basic salary");
}
String panNumber = employee.getPANNumber();
if(panNumber == null)
{
blException.addException("PAN Number", "PAN Number required");
panNumber = "";
}
panNumber = panNumber.trim();
if(panNumber.length() == 0)
{
blException.addException("PAN Number", "PAN Number required");
}
else
{
EmployeeInterface ee = this.panNumberWiseEmployeeMap.get(panNumber.toUpperCase());
if(ee != null && ee.getEmployeeId().equalsIgnoreCase(employeeId) == false)
{
blException.addException("PAN Number", "PAN Number exists with another employee Id");
}
}
String aadharCardNumber = employee.getAadharCardNumber();
if(aadharCardNumber == null)
{
blException.addException("Aadhar Card Number", "Aadhar Card Number required");
aadharCardNumber="";
}
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0)
{
blException.addException("Aadhar Card Number", "Aadhar Card Number required");
}
else
{
EmployeeInterface ee = this.aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase());
if(ee != null && ee.getEmployeeId().equalsIgnoreCase(employeeId) == false)
{
blException.addException("Aadhar Card Number", "Aadhar Card Number exists with another employee Id");
}
}
if(blException.hasExceptions())
{
throw blException;
}

try
{
EmployeeInterface dsEmployee;
dsEmployee = this.employeeIdWiseEmployeeMap.get(employeeId);
EmployeeDTO dlEmployee = new EmployeeDTO();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dlEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber.toUpperCase());
dlEmployee.setAadharCardNumber(aadharCardNumber.toUpperCase());
EmployeeDAO employeeDAO = new EmployeeDAO();
employeeDAO.update(dlEmployee);

dsEmployee.setName(name);
int oldDesignationCode = dsEmployee.getDesignation().getCode();
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender(gender=='M'?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
String oldPANNumber = dsEmployee.getPANNumber();
dsEmployee.setPANNumber(panNumber.toUpperCase());
String oldAadharCardNumber = dsEmployee.getAadharCardNumber();
dsEmployee.setAadharCardNumber(aadharCardNumber.toUpperCase());

this.employeeSet.remove(dsEmployee);
this.employeeIdWiseEmployeeMap.remove(employeeId.toUpperCase());
this.panNumberWiseEmployeeMap.remove(oldPANNumber.toUpperCase());
this.aadharCardNumberWiseEmployeeMap.remove(oldAadharCardNumber.toUpperCase());
if(oldDesignationCode != designation.getCode())
{
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeeMap.get(oldDesignationCode);
ets.remove(dsEmployee);
ets = this.designationCodeWiseEmployeeMap.get(designation.getCode());
if(ets == null)
{
ets = new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeeMap.put(designation.getCode(),ets);
}else
{
ets.add(dsEmployee);
}
this.employeeIdWiseEmployeeMap.put(employeeId.toUpperCase(),dsEmployee);
this.panNumberWiseEmployeeMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeeMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeeSet.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}


public void removeEmployee(String employeeId) throws BLException
{
if(employeeId == null)
{
BLException blException = new BLException();
blException.addException("Employee Id", "Employee Required");
throw blException;
}
employeeId = employeeId.trim();
if(employeeId.length()==0)
{
BLException blException = new BLException();
blException.addException("Employee Id", "Employee Required");
throw blException;
}
EmployeeInterface dsEmployee = this.employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());
if(dsEmployee == null)
{
BLException blException = new BLException();
blException.addException("Employee Id", "Invalid Employee Id");
throw blException;
}
String panNumber = dsEmployee.getPANNumber();
String aadharCardNumber = dsEmployee.getAadharCardNumber();
int designationCode = dsEmployee.getDesignation().getCode();
try
{
EmployeeDAO employeeDAO = new EmployeeDAO();
employeeDAO.delete(employeeId);
this.employeeIdWiseEmployeeMap.remove(employeeId);
this.panNumberWiseEmployeeMap.remove(panNumber.toUpperCase());
this.aadharCardNumberWiseEmployeeMap.remove(aadharCardNumber.toUpperCase());
this.employeeSet.remove(dsEmployee);
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeeMap.get(designationCode);
ets.remove(dsEmployee);
}catch(DAOException daoException)
{
BLException blException = new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}

public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
if(employeeId == null)
{
BLException blException = new BLException();
blException.addException("Employee Id", "Employee Required");
throw blException;
}
employeeId = employeeId.trim();
if(employeeId.length() == 0)
{
BLException blException = new BLException();
blException.addException("Employee Id", "Employee Required");
throw blException;
}
EmployeeInterface dsEmployee = this.employeeIdWiseEmployeeMap.get(employeeId.toUpperCase());
if(dsEmployee == null)
{
BLException blException = new BLException();
blException.addException("Employee Id", "Invalid Employee Id");
throw blException;
}
EmployeeInterface employee = new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation = dsEmployee.getDesignation();
DesignationInterface designation = new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}


public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
if(panNumber == null)
{
BLException blException = new BLException();
blException.addException("PAN Number", "PAN Number Required");
throw blException;
}
panNumber = panNumber.trim();
if(panNumber.length() == 0)
{
BLException blException = new BLException();
blException.addException("PAN Number", "PAN Number Required");
throw blException;
}
EmployeeInterface dsEmployee = this.panNumberWiseEmployeeMap.get(panNumber.toUpperCase());
if(dsEmployee == null)
{
BLException blException = new BLException();
blException.addException("PAN Number", "Invalid PAN Number");
throw blException;
}
EmployeeInterface employee = new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation = dsEmployee.getDesignation();
DesignationInterface designation = new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}


public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
if(aadharCardNumber == null)
{
BLException blException = new BLException();
blException.addException("Aadhar Card Number", "Aadhar Card Number Required");
throw blException;
}
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0)
{
BLException blException = new BLException();
blException.addException("Aadhar Card Number", "Aadhar Card Number Required");
throw blException;
}
EmployeeInterface dsEmployee = this.aadharCardNumberWiseEmployeeMap.get(aadharCardNumber.toUpperCase());
if(dsEmployee == null)
{
BLException blException = new BLException();
blException.addException("Aadhar Card Number", "Invalid Aadhar Card Number");
throw blException;
}
EmployeeInterface employee = new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation = dsEmployee.getDesignation();
DesignationInterface designation = new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}


public int getEmployeeCount()
{
return this.employeeSet.size();
}


public boolean employeeIdExists(String employeeId)
{
if(employeeId == null) return false;
employeeId = employeeId.trim();
return this.employeeIdWiseEmployeeMap.containsKey(employeeId.toUpperCase());
}
public boolean employeePANNumberExists(String panNumber)
{
if(panNumber == null) return false;
panNumber = panNumber.trim();
return this.panNumberWiseEmployeeMap.containsKey(panNumber.toUpperCase());
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
if(aadharCardNumber == null) return false;
aadharCardNumber = aadharCardNumber.trim();
return this.aadharCardNumberWiseEmployeeMap.containsKey(aadharCardNumber.toUpperCase());
}
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees = new TreeSet<>();
this.employeeSet.forEach((EmployeeInterface dsEmployee)->
{
EmployeeInterface employee;
DesignationInterface designation;
employee = new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation = dsEmployee.getDesignation();
designation = new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
});
return employees;
}


public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode) == false)
{
BLException blException = new BLException();
blException.addException("Designation", "Invalid Designation Code");
throw blException;
}
Set<EmployeeInterface> employees = new TreeSet<>();
Set<EmployeeInterface> ets = this.designationCodeWiseEmployeeMap.get(designationCode);
if(ets == null)
{
return employees;
}
ets.forEach((EmployeeInterface dsEmployee)->
{
EmployeeInterface employee;
DesignationInterface designation;
employee = new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation = dsEmployee.getDesignation();
designation = new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender(dsEmployee.getGender()=='M'?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
});
return employees;
}
public int getEmployeeCountByDesignationCode(int designationCode)
{
Set<EmployeeInterface> ets;
ets = this.designationCodeWiseEmployeeMap.get(designationCode);
if(ets == null) return 0;
return ets.size();
}
public boolean isDesignationAlloted(int designationCode)
{
return this.designationCodeWiseEmployeeMap.containsKey(designationCode);
}
}
