import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;

class DesignationManagerAddTestCase
{
public static void main(String gg[])
{
DesignationInterface designation = new Designation();
String title = "";
try
{
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter title : ");
title = br.readLine();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
designation.setTitle(title);
try
{
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("Designation add with code as : "+designation.getCode());
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
