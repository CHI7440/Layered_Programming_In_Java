import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.io.*;
import java.math.*;

class EmployeeManagerAadharCardNumberExistsTestCase
{
public static void main(String gg[])
{
String aadharCardNumber = "uid1234";
try
{
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
System.out.println("Aadhar Card Number : "+aadharCardNumber+" exists : "+employeeManager.employeeAadharCardNumberExists(aadharCardNumber));
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
