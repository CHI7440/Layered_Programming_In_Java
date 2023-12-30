import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.io.*;
import java.math.*;

class EmployeeManagerRemoveTestCase
{
public static void main(String gg[])
{
String employeeId = "A10000004";
try
{
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
employeeManager.removeEmployee(employeeId);
System.out.println("Employee removed");
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
