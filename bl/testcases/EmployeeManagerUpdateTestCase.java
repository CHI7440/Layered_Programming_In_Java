import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.io.*;
import java.math.*;

class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
String employeeId = "A10000004";
String name = "Mohan Rajput";
DesignationInterface designation = new Designation();
designation.setCode(6);
Date dateOfBirth = new Date();
GENDER gender = GENDER.MALE;
BigDecimal basicSalary = new BigDecimal(44343);
boolean isIndian = true;
String panNumber = "pan000";
String aadharCardNumber = "uid000";
try
{
EmployeeInterface employee = new Employee();
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(gender);
employee.setBasicSalary(basicSalary);
employee.setIsIndian(isIndian);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
employeeManager.updateEmployee(employee);
System.out.println("Employee updated");
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
