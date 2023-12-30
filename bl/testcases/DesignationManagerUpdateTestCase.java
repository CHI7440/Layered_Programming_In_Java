import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.io.*;

class DesignationManagerUpdateTestCase
{
public static void main(String gg[])
{
DesignationInterface designation = new Designation();
int code = 0;
String title = "";
try
{
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
System.out.print("Enter code : ");
try
{
code = Integer.parseInt(br.readLine());
}catch(NumberFormatException nfe)
{
System.out.println(nfe.getMessage());
}
while(br.ready()) br.readLine();
System.out.print("Enter title : ");
title = br.readLine();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
designation.setCode(code);
designation.setTitle(title);
try
{
DesignationManagerInterface designationManager;
designationManager = DesignationManager.getDesignationManager();
System.out.println(designationManager.getDesignationByTitle("Driver").getCode());
designationManager.updateDesignation(designation);
System.out.println("Designation updated with code as : "+designation.getCode()+" and title as : "+designation.getTitle());
System.out.println(designationManager.getDesignationByTitle("Driver").getCode());
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
