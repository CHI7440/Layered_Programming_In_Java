import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.io.*;
import java.math.*;

class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
String name = "Mohan Rajput";
DesignationInterface designation = new Designation();
designation.setCode(3);
Date dateOfBirth = new Date("13/2/1999");
GENDER gender = GENDER.MALE;
BigDecimal basicSalary = new BigDecimal(546943);
boolean isIndian = true;
String panNumber = "PAN000";
String aadharCardNumber = "UID000";
try
{
EmployeeInterface employee = new Employee();
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(gender);
employee.setBasicSalary(basicSalary);
employee.setIsIndian(isIndian);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
employeeManager.addEmployee(employee);
System.out.println("Employee added with id as : "+employee.getEmployeeId());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties = blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property));
}
}
}
}
